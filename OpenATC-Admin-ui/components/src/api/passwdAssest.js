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

/*
@brief 计算密码安全等级
@param[in] passwnd 密码
@return 安全等级 0->3 低->高
*/
export const GetPasswdSafeLeavel = (password) => {
  let leavel = ''
  let str = password.split('')
  let i = 0
  for (let s of str) {
    if (/[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/.test(s)) {
      i++
    }
  }
  if (i === 0) {
    if ((/[0-9]/.test(password)) || /[a-z]/.test(password) || /[A-Z]/.test(password)) {
      leavel = '1'
    }
  } else if (i > 1) {
    if ((/[0-9]/.test(password)) && (/[a-z]/.test(password) || /[A-Z]/.test(password))) {
      leavel = '3'
    } else {
      leavel = '1'
    }
  } else {
    if ((/[0-9]/.test(password)) && (/[a-z]/.test(password) || /[A-Z]/.test(password))) {
      leavel = '2'
    } else {
      leavel = '1'
    }
  }
  return leavel
}
/*
@brief 检查密码安全等级
@param[in] passwnd 密码
@return 数组，里面包含"1","2"两个状态，分别表示密码长度和包含数字、字母和特殊字符
*/
export const CheckPasswdSafeLeavel = (password) => {
  let check = []
  // eslint-disable-next-line no-mixed-operators
  if ((/[0-9]/.test(password) && (/[a-z]/.test(password)) || /[A-Z]/.test(password)) && (/[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/.test(password) && /\w/.test(password))) {
    check.push('1')
  }

  if (password.length >= 8) {
    check.push('2')
  }
  return check
}
