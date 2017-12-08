<template>
  <div 
    v-if="isStudent" 
    v-html="htmlStudentDescription" 
    class="block assignmentDescription markdown-body"></div>
  <div v-else class="block">
    <button @click="togglePreview">Toggle Preview</button>
    <textarea 
      :placeholder="placeholder" 
      v-model="rawContent"
      @input="handleInput"
    ></textarea>
    <div 
      v-if="inPreview" 
      v-html="htmlContent" 
      ref="code"
      class="assignmentDescription markdown-body"></div>
  </div>
</template>

<script>
  import 'highlight.js/styles/github.css'
  import marked from 'marked'
  import * as highlight from 'highlight.js'

  export default {
    name: 'AssignmentDescription',
    props: ['isStudent', 'content'],
    data: () => ({
      placeholder: `# Add an assignment description`,
      inPreview: false,
      rawContent: ''
    }),
    methods: {
      togglePreview () {
        this.inPreview = !this.inPreview
        requestAnimationFrame(() => {
          highlight.highlightBlock(this.$refs.code)
        })
      },
      handleInput (event) {
        this.$emit('input', event.target.value)
      }
    },
    computed: {
      htmlContent () {
        return marked(this.rawContent)
      },
      htmlStudentDescription () {
        return marked(this.content)
      }
    }
  }
</script>

<style scoped>
  button {
    position: absolute;
    top: 1rem;
    right: 1rem;
    z-index: 3;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    background: #00703C;
    color: white;
    opacity: 0.9;
    font-size: .8rem;
  }
  button:hover {
    opacity: 0.5;
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