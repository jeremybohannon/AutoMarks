<template>
<div id="wrapper">
  <loading v-if="loading"/>
  <professor-view v-if="!loading && !studentId || !assignmentId" />
  <student-view v-if="!loading && studentId && assignmentId" 
    :assignment = "assignment" 
    :setAssignment="setAssignment"
    :studentId="studentId"/>
</div>
</template>

<script>
import StudentView from './components/StudentView'
import ProfessorView from './components/ProfessorView'
import Loading from './components/Loading'
import * as highlight from 'highlight.js'

export default {
  name: 'app',
  data: () => {
    return {
      loading: true,
      studentId: null,
      assignmentId: null,
      assignment: {}
    }
  },
  async mounted () {
    function getParameterByName(name, url) {
      if (!url) url = window.location.href;
      name = name.replace(/[\[\]]/g, "\\$&");
      var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
          results = regex.exec(url);
      if (!results) return null;
      if (!results[2]) return '';
      return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    this.studentId = getParameterByName('student')
    this.assignmentId = getParameterByName('assignmentId')
  
    if(this.studentId && this.assignmentId){
      fetch(`/api/v1/assignment/${this.assignmentId}`, {
        method: 'GET'
      }).then(response => {
        return response.json()
      }).then(json => {
        this.assignment = json
        this.loading = false
      })
    }
  },
  methods: {
    setAssignment(cases) {
      this.assignment.cases = cases
    },
  },
  components: {
      'student-view': StudentView,
      'professor-view': ProfessorView,
      'loading': Loading
  }
}
</script>

<style>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  
  #wrapper{
    height: 100%;
    width: 100%;
    background: #f5f5f5;
    border: 1px solid #C7CDD1;
    min-width: 700px;
  }
</style>
