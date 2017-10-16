FROM node

WORKDIR /app

COPY . .

RUN pwd
RUN ls

RUN npm install && \
    npm run build && \
    npm prune --production

CMD node dist