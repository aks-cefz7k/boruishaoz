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
  customInfo: {
    areaid: Number,
    intersectionid: Number,
    siteid: '',
    netcard: [{
      ip: '',
      subnetmask: '',
      gateway: ''
    },
    {
      ip: '',
      subnetmask: '',
      gateway: ''
    }],
    centerip: {
      ip: '',
      port: Number
    },
    cascade: {
      lampboards: 0,
      detectorboards: 0,
      ioboards: 0,
      joinoffset: 0
    },
    startsequence: {
      startyellowflash: 6,
      startallred: 6,
      greenwavecycle: 5
    }
  },
  manualpanel: {
    mingreen: 15
  }
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
    routers: []
  },
  mutations: {
    SAVE_PARAM: (state, data) => {
      let customInfo = data.customInfo
      data.customInfo = {
        ...tscParam.customInfo,
        ...customInfo
      }
      state.tscParam = data
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
    }
    // ,
    // SET_ROUTERS: (state, routers) => {
    //   state.routers = JSON.parse(JSON.stringify(routers))
    // }
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
    }
    // ,
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
  }
}
export default Global
