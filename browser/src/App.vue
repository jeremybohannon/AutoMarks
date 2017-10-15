<template>
  <vue-transmit 
    v-bind="options" 
    @sucess="success" 
    @error="error" 
    @added-file="add" 
    ref="uploader"
  >
    <h1>Click or Drag</h1>
  </vue-transmit>
</template>

<script>
export default {
  name: 'app',
  data: () => ({
    options: {
      url: './upload.php',
      clickable: true
    }
  }),
  methods: {
    add(file) {
      console.log(file.name)
    },
    error(file, message) {
      console.log(message)
    },
    success() {
      console.log('success')
    }
  }
}
</script>

<style>

</style>
