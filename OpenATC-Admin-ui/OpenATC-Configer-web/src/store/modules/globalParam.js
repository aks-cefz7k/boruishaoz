/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
const tscParam = {
  phaseList: [],
  patternList: [],
  planList: [],
  dateList: [],
  overlaplList: [],
  channelList: [],
  detectorList: [],
  pedestrainDetectorList: [],
  channellock: [],
  // customInfo: {
  //   areaid: Number,
  //   intersectionid: Number,
  //   siteid: '',
  //   selflearning: 0,
  //   fixintersectioninfo: '',
  //   commuport: 0,
  //   commutype: '',
  //   steptype: ''
  // },
  manualpanel: {
    mingreen: 15
  },
  singleoptim: [
  ]
}
let defaultCopiedTscParam = null
try {
  if (sessionStorage.tscParam) {
    defaultCopiedTscParam = JSON.parse(sessionStorage.tscParam)
  }
} catch (e) {
  console.log(e)
}

// function getPerRouterMap (routerArr, noper, parent) {
//   for (let per of routerArr) {
//     if (per.name && per.name === noper) {
//       parent.hidden = true
//       break
//     } else if (per.children && per.children.length) {
//       getPerRouterMap(per.children, noper, per)
//     }
//   }
// }

const Global = {
  state: {
    tscParam: JSON.parse(JSON.stringify(tscParam)),
    curPath: '/overview/index',
    copiedTscParam: defaultCopiedTscParam,
    routers: [],
    curBodyWidth: 1920,
    curBodyHeight: 1080,
    FuncSort: 'allFunc',
    hideMenu: false,
    graphicMode: false, // 为true时，切换到图形界面模式，只显示路口图部分
    roadDirection: 'right', // 当前路口行车方向：默认右行
    channelDescMap: new Map() // 管理实时通道描述数据
  },
  mutations: {
    SAVE_PARAM: (state, data) => {
      // let customInfo = data.customInfo
      // data.customInfo = {
      //   ...tscParam.customInfo,
      //   ...customInfo
      // }
      state.tscParam = JSON.parse(JSON.stringify(data))
    },
    SAVE_SINGLE_PARAM: (state, param) => {
      state.tscParam[param.type] = param.data
    },
    RESET_PARAM: (state) => {
      state.tscParam = JSON.parse(JSON.stringify(tscParam))
    },
    SAVE_CUR_PATH: (state, path) => {
      state.curPath = path
    },
    SET_COPY: (state, param) => {
      state.copiedTscParam = param
      sessionStorage.tscParam = JSON.stringify(param)
    },
    CLEAR_COPY: (state) => {
      state.copiedTscParam = null
      sessionStorage.removeItem('tscParam')
    },
    // ,
    // SET_ROUTERS: (state, routers) => {
    //   state.routers = JSON.parse(JSON.stringify(routers))
    // }
    SAVE_BODY_DOM_SIZE: (state, size) => {
      state.curBodyWidth = size.width
      state.curBodyHeight = size.height
    },
    SAVE_FUNNCTION_LEVEL: (state, FUNC) => {
      state.FuncSort = FUNC
    },
    SET_MENU_VISIBLE: (state, isHideMenu) => {
      state.hideMenu = isHideMenu
    },
    SET_GRAPHIC_MODE: (state, isSwitchGraphicMode) => {
      state.graphicMode = isSwitchGraphicMode
    },
    SET_ROAD_RIRECTION: (state, DIR) => {
      state.roadDirection = DIR
    },
    SET_CHANNEL_DESC: (state, descmap) => {
      state.channelDescMap = descmap
    },
    CLEAR_MANUAL_PANEL: (state) => {
      state.tscParam.manualpanel = tscParam.manualpanel
    }
  },
  actions: {
    SaveTscParam ({ commit }, data) {
      commit('SAVE_PARAM', data)
    },
    SaveSingleParam ({ commit }, param) {
      commit('SAVE_SINGLE_PARAM', param)
    },
    ResetTscParam ({ commit }) {
      commit('RESET_PARAM')
    },
    SaveCurPath ({ commit }, path) {
      commit('SAVE_CUR_PATH', path)
    },
    SetCopy ({ commit }, data) {
      commit('SET_COPY', data)
    },
    ClearCopy ({ commit }) {
      commit('CLEAR_COPY')
    },
    // GenerateRoutes ({
    //   commit
    // }, {curRoutes, noPermission}) {
    //   // 生成路由(仅支持sidebar一级菜单的隐藏)
    //   // noPermission: Array 需要隐藏的路由name属性组成的数组
    //   if (noPermission && noPermission.length) {
    //     let perRouter = JSON.parse(JSON.stringify(curRoutes))
    //     noPermission.forEach(noper => {
    //       getPerRouterMap(perRouter, noper)
    //     })
    //     commit('SET_ROUTERS', perRouter)
    //   } else {
    //     commit('SET_ROUTERS', curRoutes)
    //   }
    // }
    SaveBodyDomSize ({ commit }, size) {
      commit('SAVE_BODY_DOM_SIZE', size)
    },
    SaveFunctionLevel ({ commit }, FUNC) {
      commit('SAVE_FUNNCTION_LEVEL', FUNC)
    },
    SetMenuVisible ({ commit }, isHideMenu) {
      commit('SET_MENU_VISIBLE', isHideMenu)
    },
    SetGraphicMode ({ commit }, isSwitchGraphicMode) {
      commit('SET_GRAPHIC_MODE', isSwitchGraphicMode)
    },
    SetRoadDirection ({ commit }, DIR) {
      commit('SET_ROAD_RIRECTION', DIR)
    },
    SetChannelDesc ({ commit }, descmap) {
      commit('SET_CHANNEL_DESC', descmap)
    },
    ClearManualPanel ({ commit }) {
      commit('CLEAR_MANUAL_PANEL')
    }
  }
}
export default Global
