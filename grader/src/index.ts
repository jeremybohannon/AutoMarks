import * as body from 'koa-body'
import * as cors from '@koa/cors'
import * as Koa from 'koa'
import AutoMarks from './AutoMarks'

const app = new Koa()

app.use(cors())
app.use(body({ multipart: true }))

const autoMarks = new AutoMarks({
  containerUpTimeLimit: 1000 * 15,
  containerStopTimeLimit: 1000 * 5
})

import * as fs from 'fs-extra'

app.use(async function (ctx, next) {
  if (ctx.request.method === 'GET') {
    ctx.state.skip = true
    ctx.state.spec = Buffer.from(
        await fs.readFile('./artifacts/test.spec.rb')
    ).toString('base64')
    ctx.state.source = Buffer.from('').toString('base64')
  } else if (ctx.request.method === 'POST') {
    const buffers = await Promise.all([
      fs.readFile(ctx.request.body.files.spec.path),
      ctx.request.body.files.source ? fs.readFile(ctx.request.body.files.source.path) : Buffer.from('')
    ])
    const [ spec, source ] = buffers.map(
      buffer => Buffer.from(buffer).toString('base64')
    )
    ctx.state.skip = true
    ctx.state.spec = spec
    ctx.state.source = source
  }
  return next()
})

app.use(autoMarks.validateMethod)
app.use(autoMarks.validateRequest)
app.use(autoMarks.parseInputs)
app.use(autoMarks.execute)
app.use(autoMarks.parseResults)
app.use(autoMarks.success)

app.listen(3000)