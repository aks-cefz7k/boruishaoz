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

class OverflowDetector {
  GetDetector () {
    let api = new Authapi('get_overflow_detector')
    return api.Send({}, {}, [])
  }
  DeleteDetectorById (id) {
    let api = new Authapi('del_overflow_detector')
    let list = []
    list.push(id)
    return api.Send({}, {}, list)
  }
  UpdateDetector (data) {
    let api = new Authapi('update_overflow_detector')
    return api.Send({}, data, [])
  }
  AddDetector (data) {
    let api = new Authapi('add_overflow_detector')
    return api.Send({}, data, [])
  }
  UpdateOverflows (data) {
    let api = new Authapi('update_overflows')
    return api.Send({}, data, [data[0].patternid])
  }
}

export const OverflowDecApi = new OverflowDetector()
