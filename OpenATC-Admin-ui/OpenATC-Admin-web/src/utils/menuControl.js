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
import store from '../store'
import { setMenuVisible } from '@/utils/auth'

export function HandleMenuVisible (sysconfig) {
  // 根据配置控制菜单显示/隐藏
  let controlMenu = { // 不显示的菜单列表，可扩充
    gis: false
  }
  for (let config of sysconfig) {
    if (config['key'] === 'isShowGisMenu') {
      if (config['value'] === 'true') {
        controlMenu.gis = true
      } else {
        controlMenu.gis = false
      }
    }
    // if ... 从系统配置增加控制项，可扩充
  }
  // 放入对应存储管理
  for (let menu in controlMenu) {
    if (menu === 'gis') {
      setMenuVisible('gis', controlMenu[menu])
      store.dispatch('SetGisVisible', controlMenu[menu])
    }
  }
}
