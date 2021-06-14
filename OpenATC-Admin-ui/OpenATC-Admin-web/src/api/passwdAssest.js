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
/*
@brief 密码相关辅助
@auth caijiadong
@date 2018-09-10
*/

/*
@brief 加密 取strAstrB的MD5
*/
export const Encode = (strA, strB) => {
  let strData = `${strA}${strB}`
  let crypto = require('crypto')
  return crypto.createHash('md5').update(strData, 'utf-8').digest('base64')
}

/*
@brief 根据用户名密码获取加密字符串
@param[in] user password 用户名密码
@return 加密字符串
*/
export const GetPassswordEncode = (user, password) => {
  return Encode(user, password)
}

/*
@brief 获取时间戳和时间戳运算MD5
@param[in] data 需要运算的字符串
@return {
  timestramp,
  data
}
*/
export const GetTimestampEncode = (data) => {
  let timestamp = new Date().getTime()
  return {
    timestamp: timestamp,
    data: Encode(data, timestamp.toString())
  }
}
