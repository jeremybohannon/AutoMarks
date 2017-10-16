import { Context } from 'koa'

export default function() {
<<<<<<< HEAD
  return async function(ctx: Context, next: Function) {
    ctx.method === 'POST' ? await next() : ctx.status = 404
=======
  return function(ctx: Context, next: Function) {
    ctx.method === 'POST' ? next() : ctx.status = 404
>>>>>>> 31624f99d89084b11f87cb6704444e468543bcf6
  }
}