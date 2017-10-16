import { Context } from 'koa'

export default function(...files: string[]) {
  return function(ctx: Context, next: Function) {
    
    // check if all defined files are present on request
    const missing = files.filter(file => {
      const request = ctx.request as any
      return !request.body.files || !request.body.files.hasOwnProperty(file)
    })

    if (missing.length > 0) {
      // bad request
      ctx.status = 400
      ctx.body = 'Bad request – missing the following files:\n' + missing.join('\n')
    } else {
      // continue to next middleware
      return next()
    }

  }
}