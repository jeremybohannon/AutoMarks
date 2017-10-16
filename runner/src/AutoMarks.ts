import { resolve } from 'path'
import * as fs from 'fs-extra'
import * as Koa from 'koa'
import * as os from 'os'
import * as uuid from 'uuid'

interface Config {
  
}

interface Context extends Koa.Context {
  state: {
    workDir?: string
  }
}

export default class AutoMarks {
  constructor(private config: Config = {}) {}  

  public async createWorkDir(ctx: Context, next: Function) {
    const path = resolve(os.tmpdir(), uuid.v4())
    await fs.mkdirp(path)
    ctx.state.workDir = await fs.realpath(path)
    await next()
    await fs.remove(ctx.state.workDir)
  }

  
}