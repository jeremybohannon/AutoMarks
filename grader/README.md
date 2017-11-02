# AutoMarks / Runner

Spins up containers given test cases, source code, and a specified environment.

## API

`POST` `/`

### Request Parameters

`spec`: `[FILE text/*]`

`source`: `[FILE text/*]`

### Response Codes

`200`: A [JUnit XML](http://help.catchsoftware.com/display/ET/JUnit+Format) file.

`422`: Unprocessable file.

`500`: Unkown error.

