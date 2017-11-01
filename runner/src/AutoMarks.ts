import { resolve } from 'path'
import * as body from 'koa-body'
import * as Docker from 'dockerode'
import * as fs from 'fs-extra'
import * as Koa from 'koa'
import * as os from 'os'
import * as streamToString from 'stream-to-string'
import * as uuid from 'uuid'
import * as delay from 'delay'
import * as parse from 'xml-parser'
import autobind from 'class-autobind'

interface Config {
  containerUpTimeLimit: number
  containerStopTimeLimit: number
}

interface Context extends Koa.Context {
  state: {
    workDir?: string
    resultsFile?: string
    resultsXML?: string
    container?: Docker.Container
  }
}

export default class AutoMarks {
  private docker: Docker

  constructor(private config: Config) {
    this.docker = new Docker()
    autobind(this)
  }

  public async createContainer(ctx: Context, next: Function) {
    // create container and results file
    ctx.state.resultsFile = `${uuid.v4()}.xml`
    ctx.state.container = await this.docker.createContainer({
      Image: 'automarks/runner-rspec:latest',
      WorkingDir: '/automarks',
      Cmd: [
        'rspec', '_.spec.rb', 
        '-f', 'RspecJunitFormatter', '-o', ctx.state.resultsFile
      ],
      HostConfig: { Binds: [`${ctx.state.workDir}:/automarks`] }
    })
    // continue to next middleware
    await next()
    // destory container
    await ctx.state.container.remove()
  }

  public async createWorkDir(ctx: Context, next: Function) {
    // get valid tmp path
    const path = resolve(os.tmpdir(), uuid.v4())
    // create tmp directory
    await fs.mkdirp(path)
    // resolve to non-symlink path and save
    ctx.state.workDir = await fs.realpath(path)
    // proceed with middleware
    await next()
    // clean up
    await fs.remove(ctx.state.workDir)
  }

  public async executeSpecs(ctx: Context, next: Function) {
    // spin up container to run specs
    await Promise.race([
      ctx.state.container.start(), 
      delay(this.config.containerUpTimeLimit)
    ])
    await ctx.state.container.stop({ 
      StopGracePeriod: this.config.containerStopTimeLimit
    })
    // get results from output file
    const results = await fs.readFile(
      resolve(ctx.state.workDir, ctx.state.resultsFile)
    )
    // set results back
    if (results.length === 0) {
      // send UNPROCESSABLE
      ctx.status = 422
    } else {
      // save XML to context
      ctx.state.resultsXML = results.toString()
    }
    // continue middleware
    return next()
  }

  public async saveFileContents(ctx: Context, next: Function) {
    // wait for all files to finish
    await Promise.all([
      fs.copy(
        ctx.request.body.files.source.path,
        resolve(ctx.state.workDir, '_.rb')
      ),
      fs.copy(
        ctx.request.body.files.spec.path,
        resolve(ctx.state.workDir, '_.spec.rb')
      )
    ])
    // continue to next middleware
    return next()
  }

  public parseResults(ctx: Context, next: Function) {
    const parsed = parse(ctx.state.resultsXML)
    const results = parsed.root.children
      .filter(child => child.name === 'testcase')
      .map(testcase => ({
        case: testcase.attributes.name,
        pass: testcase.children.length === 0
      }))
    
    ctx.response.body = {
      assignment: ctx.request.body.fields.assignment,
      user: ctx.request.body.fields.user,
      results
    }
  }

  public success(ctx: Context, next: Function) {
    // set OK status
    ctx.status = ctx.status === 404 ? 200 : ctx.status
    // continue to next middleware
    return next()
  }

  public validateMethod(ctx: Context, next: Function) {
    // continue with middleware if POST method
    return ctx.method === 'POST' ? next() : ctx.status = 404
  }

  public validateRequest(ctx: Context, next: Function) {
    const isValid = ctx.request
      && ctx.request.body
      && ctx.request.body.files
      && ctx.request.body.files.hasOwnProperty('spec')
      && ctx.request.body.files.hasOwnProperty('source')
    // continue if valid request
    return isValid ? next() : ctx.status = 400
  }
}