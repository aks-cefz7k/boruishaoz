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
import Edgebase from 'edgebase-front'
const getter = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  roles: state => state.user.roles,
  userInfo: state => state.user.userInfo,
  logo: state => state.globalVariable.logo,
  deviceTags: state => state.globalVariable.deviceTags,
  multDevicesData: state => state.globalVariable.multDevicesData,
  multDevicesPath: state => state.globalVariable.multDevicesPath,
  operateId: state => state.globalVariable.operateId,
  openedDevice: state => state.globalVariable.openedDevice,
  openedPath: state => state.globalVariable.openedPath,
  deviceInfos: state => state.globalVariable.deviceTags,
  devicePath: state => state.globalVariable.devicePath
}
const getters = Object.assign(getter, Edgebase.Store.getters)
export default getters
