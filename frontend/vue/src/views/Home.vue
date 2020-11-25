<template>
  <div class="home">
      <b-jumbotron header="5 dana na Javi" lead="Sanja nas organizovala">
      </b-jumbotron>
      <b-container>
        <h1> Games </h1><br>
        <b-table striped hover :items="games" @row-clicked="showGameStats"></b-table>
        <div v-if="gameStat.length != 0">
          <h1> Game stats </h1><br>
          <b-table striped hover :items="gameStat"></b-table>
        </div>
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
      ...mapState(['games']),
      ...mapState(['gameStat'])
    },
  mounted: function() {
     this.load_games();
  },
  methods: {
    ...mapActions(['load_games']),
    ...mapActions(['load_game_stats']),
    showGameStats: function (item, index, event) {
      this.load_game_stats(item.gameId);
    }
  }
}
</script>
