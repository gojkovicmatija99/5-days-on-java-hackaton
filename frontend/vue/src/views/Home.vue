<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import { mapActions, mapState } from 'vuex';

export default {
  name: 'Home',
  components: {
    HelloWorld
  },
  computed: {
      ...mapState(['message'])
    },
  mounted: function() {
     this.load_message();
  },
  methods: {
    ...mapActions(['load_message'])
  }
}
</script>
