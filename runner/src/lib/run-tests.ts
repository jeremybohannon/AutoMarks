import * as Docker from 'dockerode'
import * as uuid from 'uuid'
import * as path from 'path'
import * as fs from 'fs-extra'
import generateTmpDir from './generate-tmp-dir'

const docker = new Docker()

export default async function(testContents: string, sourceContents: string): Promise<string> {
  const tmpDir = await generateTmpDir()
  const resultsFileName = `${uuid.v4()}.xml`
  const containerDirPath = '/automarks'

  await fs.writeFile(path.resolve(tmpDir.path, '_.rb'), sourceContents)
  await fs.writeFile(path.resolve(tmpDir.path, '_.spec.rb'), testContents)
  
  const container = await docker.createContainer({
    Image: 'automarks:latest',
    WorkingDir: containerDirPath,
    Cmd: [
      'rspec', '_.spec.rb', '-f', 'RspecJunitFormatter', '-o', resultsFileName 
    ],
    HostConfig: {
      Binds: [ 
        `${tmpDir.path}:${containerDirPath}` 
      ]
    }
  })
  
  await container.start()
  await container.stop()
  await container.remove()

  const resultsXML = await fs.readFile(
    path.resolve(tmpDir.path, resultsFileName)
  )
  
  await tmpDir.destroy()

  return resultsXML.toString()
}