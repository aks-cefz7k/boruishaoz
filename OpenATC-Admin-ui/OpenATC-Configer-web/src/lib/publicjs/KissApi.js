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
import {
  HttpUrlMgr
} from './HttpurlMgr'
import axios from 'axios'
import * as localStorge from './localStorage'
import { removeToken } from '@/utils/auth'

export const getAxios = () => {
  return axios
}

export class KissApi {
  constructor (key) {
    this.key = key
    this.urlMgr = HttpUrlMgr
    this.headerInfo = {}
    this.defaultParam = JSON.parse('{}')
  }
  /*
  获取服务器地址 需要在storage中设置 key=host的value
  */
  GetHost () {
    return localStorge.getStorage('host')
  }

  /*
  获取token 需要在storage中设置  key=token的value
  */
  GetToken () {
    return localStorge.getStorage('token')
  }

  /*
  获取username 需要在storage中设置  key=user_name的value
  */
  GetSimuUserKey () {
    return localStorge.getStorage('simu_key')
  }

  createAxios () {
    // 创建axios实例
    const service = axios.create()
    return service
  }

  /*
  @brief 发送http请求
  @param[in] param 添加到url中的参数json
  @param[in] data 添加到body中的参数json
  @param[in] urlParamList 添加在url中的动态参数 ['1', 'states'] 则 host/url/1/states?params...
  @param[in, out] 回调函数 是否成功及返回的数据 function(bool, json)
  @param[in] hasToken 是否使用token
  @param[in] isFile 是否为文件格式
  @return 成功调用 返回promise对象可以用.then关联回调 失败返回undefine
  */
  Send (param, data, urlParamList = [], hasToken = true, isFile = false) {
    let host = this.GetHost()
    if (host === undefined) {
      console.log('host is empty')
      return undefined
    }
    if (hasToken) {
      let token = this.GetToken()
      if (token === undefined) {
        console.log('token is empty')
        return undefined
      }
      this.headerInfo.Authorization = token
      if (isFile) {
        this.headerInfo['Content-Type'] = 'multipart/form-data'
      }
    }

    // headerInfo['Simukey'] = this.GetSimuUserKey()

    let urlInfo = this.urlMgr.GetUrlInfo(this.key)
    if (urlInfo === undefined) {
      console.log('urlinfo is empty')
      return undefined
    }
    let apiUrl = urlInfo.url
    for (let item of urlParamList) {
      apiUrl += '/'
      apiUrl += item
    }
    const config = {
      baseURL: host,
      url: apiUrl,
      method: urlInfo.method,
      headers: this.headerInfo,
      timeout: 1000 * 10,
      data: data,
      params: this._assingJson(param, this.defaultParam)
    }
    const service = this.createAxios()
    // 响应错误拦截
    if (apiUrl !== '/auth/login') {
      this.responseInterception(service)
    }
    return service({...config})
  }

  responseInterception (service) {
    // respone拦截器
    service.interceptors.response.use(
      response => {
      /**
      * success === false就抛错
      */
        const res = response.data
        if (!res.success) {
          this.handleErrorMechanism(res)
          return response
        } else {
          return response
        }
      },
      error => {
        console.log(error)
      }
    )
  }

  handleErrorMechanism (res) {
    // 全局api错误拦截机制
    if (res.code === '3013' || res.code === '3015') {
      removeToken()
      location.reload()
    }
  }

  _assingJson (json1, json2) {
    if (JSON.stringify(json1) === '{}') return json2
    if (JSON.stringify(json2) === '{}') return json1
    return JSON.parse((JSON.stringify(json1) + JSON.stringify(json2)).replace(/}{/, ','))
  }
}

export class SimuApi extends KissApi {
  constructor (key, simuId) {
    super(key)
    this.defaultParam['scenestype'] = 'simu'
    this.defaultParam['scenesid'] = simuId
  }
}
