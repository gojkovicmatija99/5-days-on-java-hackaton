import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    message : ""
  },
  mutations: {
    set_message: function (state, message) {
      state.message = message.message
    },
  },
  actions: {
    load_message: function ({ commit }) {
      fetch('http://localhost:8080/api/message', { method: 'get' }).then((response) => {
        if (!response.ok)
          throw response;
        return response.json()
      }).then((jsonData) => {
        console.log(jsonData);
        console.log("here")
        commit('set_message', jsonData)
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
