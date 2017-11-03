// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueTransmit from "vue-transmit"
import marked from 'marked'

Vue.config.productionTip = false

Vue.filter('markdown', text => marked(text, { sanatize: true }))
Vue.use(VueTransmit)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  template: '<App/>',
  components: { App }
})
