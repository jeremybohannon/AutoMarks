import * as fs from 'fs'
import * as streamToString from 'stream-to-string'

export default function(...files: string[]) {
  return async function(ctx, next) {
    
    // check if all defined files are present on request
    const missing = files.filter(file => {
      return !ctx.request.body.files || !ctx.request.body.files.hasOwnProperty(file)
    })

    if (missing.length == 0) {
      // unpack all files
      ctx.files = {}
      await Promise.all(files.map(async file => {
        ctx.files[file] = await streamToString(fs.createReadStream(
          ctx.request.body.files[file].path
        ))
      }))
      next()
    } else {
      // bad request
      ctx.status = 400
      ctx.body = 'Bad request – missing the following files:\n' + missing.join('\n')
    }

  }
}