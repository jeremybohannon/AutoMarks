# AutoMarks / API

Brains of the operation.

## Endpoints

`GET` `/assignment/:id`

Returns assignment description.

```json
{
  "description": "...",
  "cases": [
    { 
      "id": 1, 
      "text": "isLess(10,15) should return true"
    },
    { 
      "id": 2, 
      "text": "isLess(10,8) should return false"
    }
  ]
}
```

`POST` `/assignment/:id/submit`

Accepts a file and passes that file, along with a hard-coded test case file, to the runner API.

Returns assignment cases status:
```json
[
  { "id": 1, "pass": true },
  { "id": 2, "pass": false }
]
```