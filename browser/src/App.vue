<template>
<div id="wrapper">
  <student-view v-if="!loading" :assignment = "assignment" :setAssignment="setAssignment"/>
  </div>
</div>
</template>

<script>
import StudentView from './components/StudentView'

export default {
  name: 'app',
  data: () => {
    return {
      loading: true,
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
  },
  methods: {
    setAssignment(cases) {
      console.log(cases)

      this.assignment.results = cases
      
    },
  },
  components: {
      'student-view': StudentView,
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
  }
</style>
