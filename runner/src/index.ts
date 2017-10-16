import * as Koa from 'koa';
import * as body from 'koa-body'
import * as cors from '@koa/cors'

import readFiles from './read-files'
import isPost from './is-post'

const app = new Koa()

declare module 'koa' {
  interface Context {
    files: { [key: string]: string }
  }
}

app.use(cors())
app.use(body({ multipart: true }))

app.use(isPost())
app.use(readFiles('code', 'test'))

app.use(ctx => {
  console.log(ctx.files)
})

app.listen(3000)