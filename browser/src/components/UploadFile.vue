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
      this.$parent.$emit('error', true);
      
      fetch(`/api`, {
        method: 'get'
      }).then(response => {
        return response.json()
      }).then(json => {
        this.$parent.$emit('results', json)
      }).catch(err => {
        console.log(err)
      });

      console.log(message)
    },
    success(upload, responseData) {
      this.$parent.$emit('error', false);
      this.$parent.$emit('results', responseData.results)
    }
  }
}
</script>

<style>
    .uploader {
        display: flex;
        height: 100px;
        margin-top: 25px;
        margin-bottom: 25px;
        width: 90%;
        max-width: 1000px;
        justify-content: center;
        align-items: center;
        background: #FFF;
        border: 1px solid #C7CDD1;
        border-radius: 10px 10px; 
    }

    .v-transmit__upload-area {
      height: 100%;
      width: 100%;
      line-height: 100px;
      text-align: center;
    }
   
   .v-transmit__upload-area:hover {
      cursor: pointer;
   }

    h1{
        font-size: 1.8em;
        color: grey;
        font-weight: 100;
        font-family: helvetica;
    }

</style>
