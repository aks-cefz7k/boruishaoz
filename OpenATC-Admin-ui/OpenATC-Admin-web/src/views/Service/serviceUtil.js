/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use i18n software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
import i18n from '../../i18n/index.js'
export default class ServiceUtil {
  // eslint-disable-next-line no-useless-constructor
  constructor () {}

  getContent (row) {
    let control = row.control
    let res = ''
    if (control === 22) {
      res = i18n.t('openatc.greenwaveoptimize.phase')
      let phases = row.phases
      if (phases) {
        let phaseIdArr = []
        for (let dir of phases) {
          if (dir.type !== 0) {
            let phaseId = dir.id
            phaseIdArr.push(phaseId)
          }
        }
        res = res + ' ' + phaseIdArr.join('、')
      }
    } else if (control === 4) {
      let value = row.value ? row.value : 1
      res = i18n.t('openatc.dutyroute.stage') + ' ' + value
    }
    return res
  }
}
