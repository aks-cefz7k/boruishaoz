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
export class UrlMgr {
  init (data) {
    if (data === undefined) {
      return
    }

    if (this.urlMap === undefined) {
      this.urlMap = new Map()
    }

    for (let sJson in data) {
      let sObj = data[sJson]
      this.urlMap.set(sObj.key, sObj)
    }
  }

  /*
  @brief 获取路径信息,key不存在返回undefine
  @return {
    key    ///<对应key
    url    ///<接口地址
    method ///<接口方法
  }
  */
  GetUrlInfo (key) {
    if (this.urlMap.has(key)) {
      return this.urlMap.get(key)
    }
    console.log(`can not find key:${key}`)
    return undefined
  }
}

export const HttpUrlMgr = new UrlMgr()
