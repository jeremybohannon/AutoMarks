import { Context } from 'koa'

export default function() {
  return function(ctx: Context, next: Function) {
    return ctx.method === 'POST' ? next() : ctx.status = 404
  }
}