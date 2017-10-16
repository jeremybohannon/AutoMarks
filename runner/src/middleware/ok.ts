import { Context } from 'koa'

export default function() {
  return async function(ctx: Context, next: Function) {
    ctx.response.status = 200
    await next()
  }
}