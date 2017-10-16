import * as Koa from 'koa';
import * as body from 'koa-body'
import * as cors from '@koa/cors'
import * as execa from 'execa'

import isPost from './middleware/is-post'
import hasFiles from './middleware/has-files'

import getFileContents from './lib/get-file-contents'

const app = new Koa()

const files = ['source', 'test']

app.use(cors())
app.use(body({ multipart: true }))

app.use(isPost())
app.use(hasFiles(...files))

app.use(async (ctx: Koa.Context) => {
  const contents = await getFileContents(files, ctx)
 
})

app.listen(3000)