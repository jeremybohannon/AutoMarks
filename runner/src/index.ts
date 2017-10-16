import * as Koa from 'koa';
import * as body from 'koa-body'
import * as cors from '@koa/cors'

import hasFiles from './middleware/has-files'
import isPost from './middleware/is-post'
import ok from './middleware/ok'

import getFileContents from './lib/get-file-contents'
import runTests from './lib/run-tests'

const app = new Koa()

const files = ['source', 'test']

app.use(cors())
app.use(body({ multipart: true }))

app.use(isPost())
app.use(hasFiles(...files))
app.use(ok())

app.use(async (ctx: Koa.Context) => {
  const contents = await getFileContents(files, ctx)
  ctx.set('Content-Type', 'application/xml')
  ctx.body = await runTests(contents.test, contents.source)
})

app.listen(3000) 