<template>
  <div class="student-view-wrapper">
    
    <assignment-description 
        :isStudent="true"
        :content="this.description"
    ></assignment-description>
    
    <upload-file descriptor="assignment" />
    
    <div v-if="syntaxError" class="syntaxError">
        <span>{{ error }}</span>
    </div>
    <div class="block testCases">
        <ul class="testCasesList">
            <li v-for="testCase in assignment.results" v-bind:key="testCase.case">
                <div class="testCaseRow">
                    <i v-if="testCase.pass" class="fa fa-check-circle" aria-hidden="true"></i>
                    <i v-if="!testCase.pass" class="fa fa-times-circle" aria-hidden="true"></i>
                    <p class="markdown-body" v-html="$options.filters.markdown(testCase.case)">
                    </p>
                </div>
                <p v-if="testCase.pass">Passed</p>
                <p v-if="!testCase.pass">Failed</p>
            </li>
        </ul>
    </div>

    <textarea ref="markdown"
        style="visibility: hidden; width: 0; height: 0"
    >
## Description

For this assignment we will learn how to write basic functions in `Ruby`

A `function` can take multiple `inputs` and return one `output`.
Functions consist of a `signature` which is comprised of a `method name` and input `parameters`, and a `method body`.

### Example
```rb 
def add(a, b)
  a + b
end
```

### Requirements

Write `3` functions in `Ruby` that `subtract`, `multiply`, and `divide` two numbers. 
These functions should maintain the respective method names, the method body is entirely up to you.
    </textarea>
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
  data: () => {
    return {
        error: "Syntax Error",
        syntaxError: false,
        description: ''
    }
  }, 
  props: ['assignment', 'setAssignment', 'studentId'],
  components: {
      'upload-file': UploadFile,
      'assignment-description': AssignmentDescription
  },
  mounted(){
    this.$on('file', function(value){
        console.log(value.name)
        console.log(this.studentId)
    });

    this.$on('results', function(value){
        this.setAssignment(value)
    });

    this.description = this.$refs.markdown.value
    
    highlight.initHighlightingOnLoad()
  }, 
  updated(){
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