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
const GlobalVariable = {
  state: {
    deviceTags: [], // 所有打开的设备页，包含的设备信息
    operateId: undefined, // 当前操作页的设备id
    multDevicesData: new Map(), // 存储所操作设备参数，与id映射
    multDevicesPath: new Map(), // 存储所操作设备对应路由位置
    openedDevice: undefined, // 当前操作页的设备参数
    openedPath: undefined, // 当前打开页签对应的路由
    devicePath: '/device', // 当前设备管理页面的路由
    gisBizType: 'deviceState', // 当前地图业务类型
    isShowGisMenu: false
  },
  mutations: {
    SET_DEV_TAG: (state, tag) => {
      // 增加tag，存储设备信息
      let ids = state.deviceTags.map(ele => ele.id)
      if (ids.includes(tag.id)) return
      state.deviceTags.push(tag)
    },
    DELETE_DEV_TAG: (state, id) => {
      // 删除tag，删除对应的设备信息
      state.deviceTags = state.deviceTags.filter(ele => ele.id !== id)
    },
    SAVE_MULT_DEVICE_DATA: (state, data) => {
      // 存储设备参数
      const id = data[0]
      const deviceTscParams = data[1]
      const path = data[2]
      state.multDevicesData.set(id, deviceTscParams)
      state.multDevicesPath.set(id, path)
    },
    GET_MULT_DEVICE_DATA: (state, id) => {
      // 获取设备参数
      state.openedDevice = state.multDevicesData.get(id)
      state.openedPath = state.multDevicesPath.get(id)
    },
    DELETE_DEVICE_DATA: (state, id) => {
      // 删除id对应的设备参数
      state.multDevicesData.delete(id)
      state.multDevicesPath.delete(id)
    },
    SET_ID: (state, curid) => {
      // 存储当前操作的页签对应的设备id
      state.operateId = curid
    },
    RESET_TAGS: (state) => {
      state.deviceTags = []
      state.operateId = undefined
      state.multDevicesData.clear()
      state.multDevicesPath.clear()
      state.openedDevice = undefined
      state.openedPath = undefined
    },
    SET_DEV_PATH: (state, path) => {
      state.devicePath = path
    },
    SET_GIS_BIZ_TYPE: (state, path) => {
      state.gisBizType = path
    },
    SET_MENU_VISIBLE: (state, controlMenu) => {
      for (let menu in controlMenu) {
        if (menu === 'gis') {
          state.isShowGisMenu = controlMenu[menu]
        }
      }
    }
  },
  actions: {
    SaveDevsTags ({ commit }, tag) {
      commit('SET_DEV_TAG', tag)
    },
    DeleteDevTag ({ commit }, id) {
      commit('DELETE_DEV_TAG', id)
    },
    SaveMultDeviceData ({ commit }, data) {
      commit('SAVE_MULT_DEVICE_DATA', data)
    },
    GetMultDeviceData ({ commit }, id) {
      commit('GET_MULT_DEVICE_DATA', id)
    },
    DeleteDeviceData ({ commit }, id) {
      commit('DELETE_DEVICE_DATA', id)
    },
    SetOperatedDeviceId ({ commit }, curid) {
      commit('SET_ID', curid)
    },
    ResetTags ({ commit }) {
      commit('RESET_TAGS')
    },
    SetDevPath ({ commit }, path) {
      commit('SET_DEV_PATH', path)
    },
    SetGisBizType ({ commit }, path) {
      commit('SET_GIS_BIZ_TYPE', path)
    },
    SetMenuVisible  ({ commit }, controlMenu) {
      commit('SET_MENU_VISIBLE', controlMenu)
    }
  }
}
export default GlobalVariable
