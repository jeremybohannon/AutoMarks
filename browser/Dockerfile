FROM node

WORKDIR /build

RUN apt update && \
    apt install nginx -y

COPY . .

RUN npm install && \
    npm run build && \
    mv ./dist /app && \
    cd / && \
    rm -rf /build

WORKDIR /app

COPY nginx.conf /etc/nginx/sites-enabled/default

CMD nginx -g 'daemon off;'