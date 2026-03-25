import Vue from 'vue'
import Vuex from 'vuex'
import WsSubMgr from '../../api/WebsocketMgr/WsSubMgr'
Vue.use(Vuex)
const WebsocketModel = {
  state: {
    WsSubMgr: Object
  },
  mutations: {
    SET_WSSUBMGROBJ: (state, obj) => {
      state.WsSubMgr = obj
    }
  },
  actions: {
    InitWsSubMgrObj ({
      commit
    }) {
      let obj = new WsSubMgr()
      commit('SET_WSSUBMGROBJ', obj)
    }
  }
}
export default WebsocketModel
