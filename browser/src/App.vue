<template>
<div id="wrapper">
  <student-view :assignment = "assignment"/>
  </div>
</div>
</template>

<script>
import StudentView from './components/StudentView'

export default {
  name: 'app',
  data: () => {
    return {
      assignment: {
        name: "Assignment #1",
        description: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        testCases: [
              { name: 'Test Case #1', status: 'Pass' },
              { name: 'Test Case #2', status: 'Failed' },
              { name: 'Test Case #3', status: 'Failed' },
              { name: 'Test Case #4', status: 'Pass' },
              { name: 'Test Case #5', status: 'Pass' },
              { name: 'Test Case #6', status: 'Failed' },
              { name: 'Test Case #7', status: 'Pass' },
              { name: 'Test Case #8', status: 'Failed' }
        ]
      }
    }
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
    border-top: none;
  }
</style>
