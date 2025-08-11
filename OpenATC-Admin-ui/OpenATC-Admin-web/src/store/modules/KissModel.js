import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const KissModelObj = {
  state: {
    KissModelObject: Object
  },
  mutations: {
    SET_KISSMODEL: (state, obj) => {
      state.KissModelObject = obj
    }
  },
  actions: {
    InitKissModel ({ commit }, obj) {
      commit('SET_KISSMODEL', obj)
    }
  }
}
export default KissModelObj
