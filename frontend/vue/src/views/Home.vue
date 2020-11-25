<template>
  <div class="home">
      <b-container>
        <b-table striped hover :items="games"></b-table>
     </b-container>
  </div>
</template>

<script>
// @ is an alias to /src
import { mapActions, mapState } from 'vuex';

export default {
  name: 'Home',
  computed: {
      ...mapState(['games'])
    },
  mounted: function() {
     this.load_games();
  },
  methods: {
    ...mapActions(['load_games'])
  }
}
</script>
