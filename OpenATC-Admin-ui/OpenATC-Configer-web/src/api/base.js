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
import Authapi from './authapi'
import {
  GetPassswordEncode
} from './passwdAssest'

export const UpdateCurUsrPassWd = (user, oldPass, newPass) => {
  let api = new Authapi('postPassword')
  let param = {
    'old_password': GetPassswordEncode(user, oldPass),
    'new_password': GetPassswordEncode(user, newPass)
  }

  return api.Send({}, param)
}

export default {
  UpdateCurUsrPassWd
}
