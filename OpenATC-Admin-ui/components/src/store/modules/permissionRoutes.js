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
function getPerRouterMap (routerArr, noper, parent) {
  for (let per of routerArr) {
    if (per.name && per.name === noper) {
      parent.hidden = true
      break
    } else if (per.children && per.children.length) {
      getPerRouterMap(per.children, noper, per)
    }
  }
}

const permissionRoutes = {
  state: {
    routers: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.routers = JSON.parse(JSON.stringify(routers))
    }
  },
  actions: {
    GenerateRoutes ({
      commit
    }, {curRoutes, noPermission}) {
      // 生成路由(仅支持sidebar一级菜单的隐藏)
      // noPermission: Array 需要隐藏的路由name属性组成的数组
      if (noPermission && noPermission.length) {
        let perRouter = JSON.parse(JSON.stringify(curRoutes))
        noPermission.forEach(noper => {
          getPerRouterMap(perRouter, noper)
        })
        commit('SET_ROUTERS', perRouter)
      } else {
        commit('SET_ROUTERS', curRoutes)
      }
    }
  }
}
export default permissionRoutes
