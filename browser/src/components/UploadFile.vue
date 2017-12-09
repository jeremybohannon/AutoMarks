<template>
  <vue-transmit 
      :clickable="true"
      :url="url"
      :params="params"
      @success="success" 
      @error="error"
      class="uploader"
      ref="uploader"
    >
    <h1>
      Click or Drag to upload {{ descriptor }}
      <i class="fa fa-cloud-upload" aria-hidden="true"></i>
    </h1>
  </vue-transmit>  
</template>
  
<script>
  export default {
    name: 'uploadfile',
    props: ['descriptor', 'url', 'params'],
    methods: {
      success (...args) {
        this.$emit('success', ...args);
      },
      error (...args) {
        this.$emit('error', args[1]);
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
