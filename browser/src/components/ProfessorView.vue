<template>
  <div class="professor-view-wrapper">
    <span class="title">Automarks</span>
    <assignment-description @input="output"/>
    <upload-file descriptor="Test File" />
    <div class="widthBlock">
      <button v-on:click="submit" class="submit" >Submit</button>
    </div>
  </div>
</template>
  
<script>
import UploadFile from './UploadFile'
import AssignmentDescription from './AssignmentDescription'

export default {
name: 'ProfessorView',
  data: () => ({
    
  }),
  props: ['assignmentName'],
  methods: {
    submit(){
      console.log('test')
    },
    output(content){
      console.log(content)
    }
  },
  components: {
      'upload-file': UploadFile,
      'assignment-description': AssignmentDescription
  },
  mounted(){
    this.$on('error', function(value){
        console.log('error')
    });
    this.$on('results', function(value){
        console.log(value)
    });
  }
}
</script>

<style scoped>
.professor-view-wrapper {
  width: 100%;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.block {
  margin-top: 25px;
  margin-bottom: 25px;
  width: 70%;
  max-width: 1000px;
  padding: 15px 15px;
  background: #FFF;
  border: 1px solid #C7CDD1;
  border-radius: 10px 10px; 
}

.widthBlock {
  display: flex;
  justify-content: flex-end;
  margin-top: 25px;
  margin-bottom: 25px;
  width: 70%;
  max-width: 1000px;
}

.uploader {
  width: 70% !important;
}

.submit {
  width: 30%;
  padding: 15px 15px;
  margin-bottom: 25px;
  background: #FFF;
  border: 1px solid #C7CDD1;
  border-radius: 10px 10px; 
  font-family: Helvetica, sans-serif;
  font-size: 1.2em;
  color: grey;
}

.submit:active {
  background: #dedede;
}

.title {
  width: 90%;
  height:fit-content;
  max-width: 1000px;
  margin-top: 25px;
  text-align: center;
  font-family: 'Lobster', cursive;
  letter-spacing: 3px;
  font-size: 47px;
}

</style>