import * as Koa from 'koa';
import * as cors from '@koa/cors'
import * as koaBody from 'koa-body'
import * as fs from 'fs'
import * as streamToString from 'stream-to-string'

const app = new Koa();

app.use(cors())
app.use(koaBody({ multipart: true }))

app.use(async ctx => {
  if ('POST' != ctx.method) return
  try {
    const file = ctx.request.body.files.file
    const out = await streamToString(fs.createReadStream(file.path))
    console.log(out)
  } catch (error) {
    console.log(error)
  }
});

app.listen(3000);