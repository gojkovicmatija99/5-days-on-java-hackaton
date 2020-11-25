<template>
  <div class="hello">
    <h1>{{ games }}</h1>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'
import { mapActions, mapState } from 'vuex';

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  computed: {
      ...mapState(['games'])
    },
   mounted: function() {
            this.load_games();
        },
  methods: {
            ...mapActions(['load_games'])
            },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
