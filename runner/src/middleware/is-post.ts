import { Context } from 'koa'

export default function() {
  return function(ctx: Context, next: Function) {
    ctx.method === 'POST' ? next() : ctx.status = 404
  }
}