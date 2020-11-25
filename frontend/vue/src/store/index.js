import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

class Game {
  constructor(hostName, guestName, hostScore, guestScore, isFinished) {
    this.hostName = hostName;
    this.guestName = guestName;
    this.hostScore = hostScore;
    this.guestScore = guestScore;
    if(!isFinished)
      this._rowVariant = "success" 
  }
}

export default new Vuex.Store({
  state: {
    games : []
  },
  mutations: {
    set_games: function (state, gamesJson) {
      console.log(gamesJson[0]);
      for(var index in gamesJson) {
        console.log(index);
        var hostName = gamesJson[index].hostName;
        console.log(hostName);
        var hostScore = gamesJson[index].hostScore;
        var guestName = gamesJson[index].guestName;
        var guestScore = gamesJson[index].guestScore;
        var isFinished = gamesJson[index].IsFinished;
        const game = new Game(hostName, guestName, hostScore, guestScore, isFinished);
        state.games.push(game);
      }
    },
  },
  actions: {
    load_games: function ({ commit }) {
      fetch('http://localhost:8080/api/1', { method: 'get' }).then((response) => {
        if (!response.ok)
          throw response;
        return response.json()
      }).then((gamesJson) => {
        commit('set_games', gamesJson)
      }).catch((error) => {
        if (typeof error.text === 'function')
          error.text().then((errorMessage) => {
            alert(errorMessage);
          });
        else
          alert(error);
      });
    },
  },
  modules: {
  }
})
