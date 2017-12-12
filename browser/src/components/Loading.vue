<template>
  <div :class="'loading-wrapper' + this.floatingClass">
    <i class="fa fa-spinner rotating" aria-hidden="true"></i>Loading...
  </div>
</template>

<script>
export default {
  name: 'Loading',
  props: [
      'floating'
  ],
  computed: {
      floatingClass () {
          return this.floating ? ' floating ' : ''
      }
  }
}
</script>

<style scoped>
.loading-wrapper {
    height: 600px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

    font-size: 1.8em;
    color: grey;
    font-weight: 100;
    font-family: helvetica;
}

.floating {
    width: 100vw;
    height: 100vh;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 100;
    background-color: rgba(0,0,0,0.5);
    color: white;
}

.fa-spinner {
  margin-right: 15px;
}

@keyframes rotating {
    from{
        transform: rotate(0deg);
    }
    to{
        transform: rotate(360deg);
    }
}

.rotating {
    animation: rotating 2s linear infinite;
}
</style>