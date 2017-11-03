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
    ctx.response.body = JSON.parse(
      (await fs.readFile('./assignment.json')).toString()
    )
  } else {
    return next()
  }
})

app.use(autoMarks.validateMethod)
app.use(autoMarks.validateRequest)
app.use(autoMarks.parseInputs)
app.use(autoMarks.execute)
app.use(autoMarks.parseResults)
app.use(autoMarks.success)

app.listen(3000)