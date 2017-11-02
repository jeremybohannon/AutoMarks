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

app.use(autoMarks.validateMethod)
app.use(autoMarks.validateRequest)
app.use(autoMarks.parseInputs)
app.use(autoMarks.execute)
app.use(autoMarks.parseResults)
app.use(autoMarks.success)

app.listen(3000)