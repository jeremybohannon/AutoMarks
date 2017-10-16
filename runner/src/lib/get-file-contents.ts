import { Context } from 'koa'
import * as fs from 'fs'
import * as streamToString from 'stream-to-string'

type FileContents = { [key: string]: string }

export default async function(files: string[], ctx: Context): Promise<FileContents> {
  const contents = {}
  
  await Promise.all(files.map(async (file: string) => {
    contents[file] = await streamToString(fs.createReadStream(
      ctx.request.body.files[file].path
    )) 
  }))
  
  return contents
}
