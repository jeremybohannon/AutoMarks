import { resolve } from 'path'
import * as fs from 'fs-extra'
import * as os from 'os'
import * as uuid from 'uuid'

export default async function() {
  const origPath = resolve(os.tmpdir(), uuid.v4());
  
  await fs.mkdirp(origPath)

  const path = await fs.realpath(origPath)

  async function destroy() {
    await fs.remove(path)
  }

  return { path, destroy }
}
