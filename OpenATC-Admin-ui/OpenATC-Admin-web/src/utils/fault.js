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
import i18n from '@/i18n/index.js'
export const BoardType = [{
  label: i18n.t('edge.fault.tab1'),
  value: 1
}, {
  label: i18n.t('edge.fault.tab2'),
  value: 2
}, {
  label: i18n.t('edge.fault.tab3'),
  value: 3
}, {
  label: i18n.t('edge.fault.tab4'),
  value: 4
}]
export function formatBoardType (dev) {
  let typecode = dev.m_byFaultBoardType
  let type
  switch (typecode) {
    case 1:
      type = i18n.t('edge.fault.tab1')
      break
    case 2:
      type = i18n.t('edge.fault.tab2')
      break
    case 3:
      type = i18n.t('edge.fault.tab3')
      break
    case 4:
      type = i18n.t('edge.fault.tab4')
      break
    default:
      type = ''
  }
  return type
}
