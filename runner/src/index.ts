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

app.use(async (ctx: Koa.Context, next) => {
  const contents = await getFileContents(files, ctx)
  const resultsXML = runTests(contents.test, contents.source)
  console.log(resultsXML)
})

app.listen(3000) 