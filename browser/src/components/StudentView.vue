<template>
  <div class="student-view-wrapper">
    
    <assignment-description 
        :isStudent="true"
        :content="description"
    ></assignment-description>
    
    <upload-file 
        :url="requestUrl"
        @success="success"
        @error="error"
        :descriptor="assignment.id"
    />
    
    <div v-if="syntaxError" class="syntaxError">
        <span>{{ error }}</span>
    </div>
    <div class="block testCases">
        <ul class="testCasesList">
            <li v-for="testCase in assignment.cases" v-bind:key="testCase.text">
                <div class="testCaseRow">
                    <i v-if="testCase.passed" class="fa fa-check-circle" aria-hidden="true"></i>
                    <i v-else class="fa fa-times-circle" aria-hidden="true"></i>
                    <p class="markdown-body" v-html="$options.filters.markdown(testCase.text)">
                    </p>
                </div>
                <p v-if="testCase.passed">Passed</p>
                <p v-else>Failed</p>
            </li>
        </ul>
    </div>
  </div>
</template>

<script>
import 'github-markdown-css/github-markdown.css'
import 'highlight.js/styles/github.css'

import * as highlight from 'highlight.js'
import AssignmentDescription from './AssignmentDescription'
import marked from 'marked'
import UploadFile from './UploadFile'

export default {
  name: 'StudentView',
  data: () => ({
    error: "Syntax Error",
    syntaxError: false,
    description: ''
  }), 
  props: [
    'assignment', 
    'setAssignment', 
    'studentId'
  ],
  components: {
    'upload-file': UploadFile,
    'assignment-description': AssignmentDescription
  },
  computed: {
    requestUrl () {
        return `/api/v1/assignment/${this.assignment.id}/submission/${this.studentId}`
    }
  },
  methods: {
      success (vueTransmitFile, responseData) {
          this.setAssignment(responseData.cases)
      },
      error (message) {
          console.error(message)
      }
  },
  mounted () {
    this.description = this.assignment.description
    
    highlight.initHighlightingOnLoad()
  }, 
  updated () {
    highlight.initHighlighting()
  }
}
    

</script>

<style scoped>

.testCases {
    border-radius: 2px 0px !important;
}

.testCases ul {
    list-style-type: none;
}

.testCases li {
    display: flex;
    align-items: center;
    height: 70px;
}

.testCases li p {
    line-height: 38px;
    font-size: 25px;
    font-family: helvetica;
    color: grey;
}

.testCases .fa {
    font-size: 38px;
}

.testCases .fa-check-circle {
    color: #00AC18;
}

.testCases .fa-times-circle {
    color: #EE0612;
}

.testCases .fa-clock-o{
    color: #333;
}
.testCases .fa-circle{
    color: #f5f5f5;
}

.testCaseRow {
    display: flex;
    width: 85%;
}

.testCaseRow p:not(:first-child) {
    margin-left: 15px;
}

.student-view-wrapper {
    width: 100%;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}

.syntaxError {
    height: 50px;
    width: 90%;
    max-width: 1000px;
    background: rgba(255, 13, 13, 0.38);
    border-radius: 10px 10px;
    border: 2px solid red;
    text-align: center;
}

.title {
    text-align: center;
    font-size: 1.8em;
    color: grey;
    font-weight: 100;
    font-family: helvetica;
}

.syntaxError span {
    display: block;
    height: 100%;
    width: 100%;
    line-height: 50px;
    font-size: 1.8em;
    font-weight: 100;
    font-family: helvetica;
    color: #FFF;
}
.block {
    margin-top: 25px;
    margin-bottom: 25px;
    width: 90%;
    max-width: 1000px;
    padding: 15px 15px;
    background: #FFF;
    border: 1px solid #C7CDD1;
    border-radius: 10px 10px; 
}
</style>