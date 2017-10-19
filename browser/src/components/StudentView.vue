<template>
  <div class="student-view-wrapper">
    <upload-file :assignmentName="assignment.name"/>
    <div class="block assignmentDescription">
        <span>{{ assignment.description }}</span>
    </div>
    <div v-if="syntaxError" class="syntaxError">
        <span>{{ error }}</span>
    </div>
    <div class="block testCases">
        <ul class="testCasesList">
            <li v-for="testCase in assignment.testCases">
                <div class="needsAName">
                    <i v-if="testCase.status == 'Pass'" class="fa fa-check-circle" aria-hidden="true"></i>
                    <i v-if="testCase.status == 'Failed'" class="fa fa-times-circle" aria-hidden="true"></i>
                    <p>{{ testCase.name }}</p>
                </div>
                <p>{{ testCase.status }}</p>
            </li>
        </ul>
    </div>
  </div>
</template>

<script>
import UploadFile from './UploadFile'
export default {
  name: 'StudentView',
  data: () => {
    return {
        error: "Syntax Error",
        syntaxError: true,
    }
  },
  props: ['assignment'],
  components: {
      'upload-file': UploadFile
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

.needsAName {
    display: flex;
    width: 85%;
}

.needsAName p:not(:first-child) {
    margin-left: 15px;
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

.assignmentDescription {
    text-align: justify;
}
.assignmentDescription span {
    font-size: 1.0em;
    color: grey;
    font-weight: 100;
    font-family: helvetica;
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
</style>