<template>
  <div v-if="isStudent" class="block assignmentDescription markdown-body">
    <!-- content generated from description -->  
  </div>
  <div v-else class="block">
    <button @click="togglePreview">Toggle</button>
    <textarea :placeholder="placeholder" v-model="rawContent"></textarea>
    <div 
      v-if="inPreview" 
      v-html="htmlContent" 
      class="assignmentDescription markdown-body"></div>
  </div>
</template>

<script>
  import marked from 'marked'

  export default {
    name: 'AssignmentDescription',
    props: ['isStudent', 'content'],
    data: () => ({
      placeholder: `# Add an assignment description`,
      inPreview: false,
      rawContent: ''
    }),
    methods: {
      togglePreview() {
        this.inPreview = !this.inPreview
      }
    },
    computed: {
      htmlContent() {
        return marked(this.rawContent)
      } 
    }
  }
</script>

<style scoped>
  button {
    position: fixed;
    top: 0;
    left: 0;
  }
  textarea {
    width: 100%;
    height: 25rem;
    border: none;
    background: none;
    resize: none;
  }
  .assignmentDescription {
      text-align: justify;
      position: absolute;
      z-index: 2;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      padding: 15px;
      background: white;
      overflow: scroll;
  }
  .assignmentDescription span {
      font-size: 1.0em;
      color: grey;
      font-weight: 100;
      font-family: helvetica;
  }
  .block {
      overflow: hidden;
      margin-top: 25px;
      margin-bottom: 25px;
      width: 90%;
      max-width: 1000px;
      padding: 15px 15px;
      background: #FFF;
      border: 1px solid #C7CDD1;
      border-radius: 10px 10px; 
      position: relative;
  }
</style>