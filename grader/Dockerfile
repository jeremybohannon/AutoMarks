FROM node

WORKDIR /app

COPY . .

RUN npm install && \
    npm run build && \
    npm prune --production

CMD node dist