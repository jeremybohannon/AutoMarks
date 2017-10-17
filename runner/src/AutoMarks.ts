import { resolve } from 'path'
import * as body from 'koa-body'
import * as Docker from 'dockerode'
import * as fs from 'fs-extra'
import * as Koa from 'koa'
import * as os from 'os'
import * as streamToString from 'stream-to-string'
import * as uuid from 'uuid'
import autobind from 'class-autobind'

interface Config {}

interface Context extends Koa.Context {
  state: {
    workDir?: string
    resultsFile?: string
    container?: Docker.Container
  }
}

export default class AutoMarks {
  private docker: Docker

  constructor(private config: Config = {}) {
    this.docker = new Docker()
    autobind(this)
  }

  public async createContainer(ctx: Context, next: Function) {
    // create container and results file
    ctx.state.resultsFile = `${uuid.v4()}.xml`
    ctx.state.container = await this.docker.createContainer({
      Image: 'automarks:latest',
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
    await ctx.state.container.start();
    await ctx.state.container.stop()
    // get results from output file
    ctx.body = await fs.readFile(
      resolve(ctx.state.workDir, ctx.state.resultsFile)
    )
    // set content type of results
    ctx.set('Content-Type', 'application/xml')
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

  public success(ctx: Context, next: Function) {
    // set OK status
    ctx.status = 200
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