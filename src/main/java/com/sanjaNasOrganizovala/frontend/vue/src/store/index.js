import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

class Game {
  constructor(gameId, hostName, guestName, hostScore, guestScore, isFinished) {
    this.gameId = gameId;
    this.hostName = hostName;
    this.guestName = guestName;
    this.hostScore = hostScore;
    this.guestScore = guestScore;
    if(!isFinished)
      this._rowVariant = "success" 
  }
}

class Player {
  constructor(firstName, lastName, points, assists, jumps) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.points = points;
    this.assists = assists;
    this.jumps = jumps;
  }
}

export default new Vuex.Store({
  state: {
    games: [],
    gameStat: []
  },
  mutations: {
    set_games: function (state, gamesJson) {
      for(var index in gamesJson) {
        var gameId = gamesJson[index].gameId;
        var hostName = gamesJson[index].hostName;
        var hostScore = gamesJson[index].hostScore;
        var guestName = gamesJson[index].guestName;
        var guestScore = gamesJson[index].guestScore;
        var isFinished = gamesJson[index].IsFinished;
        const game = new Game(gameId, hostName, guestName, hostScore, guestScore, isFinished);
        state.games.push(game);
      }
    },
    set_game_stats: function(state, gameStatsJson) {
      state.gameStat = []
      for(var index in gameStatsJson) {
        var firstName = gameStatsJson[index].firstName;
        var lastName = gameStatsJson[index].lastName;
        var points = gameStatsJson[index].points;
        var assists = gameStatsJson[index].assists;
        var jumps = gameStatsJson[index].jumps;
        const player = new Player(firstName, lastName, points, assists, jumps);
        state.gameStat.push(player);
      }
    }
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
  load_game_stats: function ({ commit }, gameId) {
    fetch(`http://localhost:8080/api/2/${gameId}`, { method: 'get' }).then((response) => {
      if (!response.ok)
        throw response;
      return response.json()
    }).then((gameStatsJson) => {
      commit('set_game_stats', gameStatsJson)
    }).catch((error) => {
      if (typeof error.text === 'function')
        error.text().then((errorMessage) => {
          alert(errorMessage);
        });
      else
        alert(error);
    });
  },
}
});
