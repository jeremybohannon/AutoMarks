import { Context } from 'koa'

export default function() {
  return async function(ctx: Context, next: Function) {
    ctx.method === 'POST' ? await next() : ctx.status = 404
  }
}