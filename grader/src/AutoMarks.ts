import { PassThrough } from 'stream'
import * as body from 'koa-body'
import * as Docker from 'dockerode'
import * as fs from 'fs-extra'
import * as Koa from 'koa'
import * as parse from 'xml-parser'
import * as streamToString from 'stream-to-string'
import autobind from 'class-autobind'

interface Config {
  containerUpTimeLimit: number
  containerStopTimeLimit: number
}

interface Context extends Koa.Context {
  state: {
    skip?: boolean // tmp
    container?: Docker.Container
    spec?: string
    source?: string
    resultsXML?: string
  }
}

export default class AutoMarks {
  private docker: Docker

  constructor(private config: Config) {
    this.docker = new Docker()
    autobind(this)
  }

  public async execute(ctx: Context, next: Function) {
    const stream = new PassThrough()
    // run files in docker using rspec
    await this.docker.run(
      'automarks_grader_container', 
      ['RUBY', ctx.state.spec, ctx.state.source], 
      stream
    )
    ctx.state.resultsXML = await streamToString(stream);
    // continue to next middleware
    await next()
  }

  public async parseInputs(ctx: Context, next: Function) {
    if (ctx.state.skip) return next()
    // // wait for both files to be read in
    // await Promise.all(['spec', 'source'].map(async filename => {
    //   // read file into base64 string for transfer to container
    //   ctx.state[filename] = Buffer.from(
    //     await fs.readFile(ctx.request.body.files[filename].path)
    //   ).toString('base64')

    ctx.state.source = Buffer.from(
        await fs.readFile(ctx.request.body.files.file.path)
    ).toString('base64')

    ctx.state.spec = Buffer.from(
        await fs.readFile('./artifacts/test.spec.rb')
    ).toString('base64')
    
    await next()
  }

  public parseResults(ctx: Context, next: Function) {
    const parsedXML = parse(ctx.state.resultsXML)
    // turn Junit format into usable JSON
    const results = parsedXML.root.children
      .filter(child => child.name === 'testcase')
      .map(testcase => ({
        case: testcase.attributes.name,
        pass: testcase.children.length === 0
      }))
    // return results to the client
    ctx.response.body = {
      assignment: ctx.request.body.fields && ctx.request.body.fields.assignment,
      user: ctx.request.body.fields && ctx.request.body.fields.user,
      results
    }
    // next middleware
    return next()
  }

  public success(ctx: Context, next: Function) {
    // set OK status
    ctx.status = ctx.status === 404 ? 200 : ctx.status
    // continue to next middleware
    return next()
  }

  public validateMethod(ctx: Context, next: Function) {
    if (ctx.state.skip) return next()
    // continue with middleware if POST method
    return ctx.method === 'POST' ? next() : ctx.status = 404
  }

  public validateRequest(ctx: Context, next: Function) {
    if (ctx.state.skip) return next()
    const isValid = ctx.request
      && ctx.request.body
      && ctx.request.body.files
      && ctx.request.body.files.hasOwnProperty('file')
      // && ctx.request.body.files.hasOwnProperty('spec')
      // && ctx.request.body.files.hasOwnProperty('source')
    // continue if valid request
    return isValid ? next() : ctx.status = 400
  }
}