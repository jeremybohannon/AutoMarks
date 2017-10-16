import { Context } from 'koa'

export default function() {
  return function(ctx: Context, next: Function) {
    ctx.response.status = 200
    next()
  }
}