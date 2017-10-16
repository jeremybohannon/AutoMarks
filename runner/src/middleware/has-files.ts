import { Context } from 'koa'

export default function(...files: string[]) {
  return async function(ctx: Context, next: Function) {
    
    // check if all defined files are present on request
    const missing = files.filter(file => {
      return !ctx.request.body.files || !ctx.request.body.files.hasOwnProperty(file)
    })

    if (missing.length > 0) {
      // bad request
      ctx.status = 400
      ctx.body = 'Bad request – missing the following files:\n' + missing.join('\n')
    } else {
      // continue to next middleware
<<<<<<< HEAD
      await next()
=======
      next()
>>>>>>> 31624f99d89084b11f87cb6704444e468543bcf6
    }

  }
}