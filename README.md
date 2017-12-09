# AutoMarks

## Setup

Create a file at the root of the project called `private.env`. This file is ignored by git. Add the following contents:

```bash
CANVAS_TOKEN=<your_canvas_token>
```

You can generate a Canvas token from [here](https://uncc.instructure.com/profile/settings).

<br>
<br>

## Frontend Development

Enter the `browser` directory and issue the following command to start the development server.

```bash
npm start
```

<br>
<br>

In a second terminal at the root of the project, run the following command to start all other services:

```bash
docker-compose up
```

<br>
<br>

__Note__: You will need to rebuild the image of any service that changes. 

<br>
<br>

To rebuild a specific image run:

```bash
docker-compose build <servce_name>
```

<br>
<br>

To rebuild all images run:

```bash
docker-compose build
```
