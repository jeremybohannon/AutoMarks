<template>
<div id="wrapper">
  <loading v-if="loading"/>
  <professor-view v-if="!loading && isStudent" />
  <student-view v-if="!loading && !isStudent" :assignment = "assignment" :setAssignment="setAssignment"/>
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
      isStudent: false,
      assignment: {}
    }
  },
  mounted(){
    function getParameterByName(name, url) {
      if (!url) url = window.location.href;
      name = name.replace(/[\[\]]/g, "\\$&");
      var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
          results = regex.exec(url);
      if (!results) return null;
      if (!results[2]) return '';
      return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    const id = getParameterByName('id')
    const user = getParameterByName('user')
    this.isStudent = getParameterByName('student')
  
    if(this.isStudent){
      fetch(`/api`, {
        method: 'get'
      }).then(response => {
        return response.json()
      }).then(json => {
        this.assignment = json
        this.loading = false
      }).catch(err => {
        console.log(err)
      });
    } else {
      this.loading = false
    }
  },
  methods: {
    setAssignment(cases) {
      console.log(cases)

      this.assignment.results = cases
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
