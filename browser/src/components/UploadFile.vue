<template>
<vue-transmit 
    class="uploader"
    v-bind="options" 
    @success="success" 
    @error="error" 
    @added-file="add" 
    ref="uploader"
  >
    <h1>Click or Drag to upload {{assignmentName}}</h1>
</vue-transmit>  
</template>
  
<script>
export default {
name: 'uploadfile',
  data: () => ({
    options: {
      url: '//localhost:3000/',
      clickable: true
    }
  }),
  props: ['assignmentName'],
  methods: {
    add(file) {
      console.log(file.name)
    },
    error(file, message) {
      //Emit event 
      this.syntaxError = true;
      console.log(message)
    },
    success() {
      console.log('success')
    }
  }
}
</script>

<style scoped>
    .uploader {
        display: flex;
        height: 100px;
        margin-top: 25px;
        margin-bottom: 25px;
        width: 90%;
        justify-content: center;
        align-items: center;
        background: #FFF;
        border: 1px solid #C7CDD1;
        border-radius: 10px 10px;   
    }

    h1{
        font-size: 1.8em;
        color: grey;
        font-weight: 100;
        font-family: helvetica;
    }

</style>
