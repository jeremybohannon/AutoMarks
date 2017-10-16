import * as Docker from 'dockerode'
import * as uuid from 'uuid'
import * as path from 'path'
import * as fs from 'fs-extra'
import generateTmpDir from './generate-tmp-dir'

const docker = new Docker()

export default async function(testContents: string, sourceContents: string): Promise<string> {
  const tmpDir = await generateTmpDir()
  const resultsFileName = `${uuid.v4()}.xml`

  await fs.writeFile(path.resolve(tmpDir.path, '_.rb'), sourceContents)
  await fs.writeFile(path.resolve(tmpDir.path, '_.spec.rb'), testContents)
  
  const container = await docker.createContainer({
    Image: 'bash',
    Cmd: ['bash', '-c', `echo '<xml>' > /automarks/${resultsFileName}`],
    Env: ['TEST=hello'],
    Tty: true,
    HostConfig: {
      Binds: [
        `${tmpDir.path}:/automarks`
      ]
    }
  })

  container.attach({stream: true, stdout: true, stderr: true}, function (err, stream) {
    stream.pipe(process.stdout);
  });

  await container.start()

  const resultsXML = await fs.readFile(path.resolve(tmpDir.path, resultsFileName))

  await container.stop()
  await container.remove()
  await tmpDir.destroy()

  return resultsXML.toString()
}