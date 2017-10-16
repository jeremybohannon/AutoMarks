export default function() {
  return function(ctx, next) {
    if (ctx.method === 'POST') {
      next()
    } else {
      ctx.status = 404
    }
  }
}