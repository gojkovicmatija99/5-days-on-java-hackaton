<template>
  <div class="home">
      <b-jumbotron header="5 dana na Javi" lead="Sanja nas organizovala">
      </b-jumbotron>
      <b-container>
        <b-table striped hover :items="games" @row-clicked="showGameStats">
        </b-table>
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
    ...mapActions(['load_games']),
    showGameStats: function (item, index, event) {
      console.log(item);
    }
  }
}
</script>
