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
const monthsModel = [{
  value: 0,
  label: '全选'
}, {
  value: 1,
  label: '1'
}, {
  value: 2,
  label: '2'
}, {
  value: 3,
  label: '3'
}, {
  value: 4,
  label: '4'
}, {
  value: 5,
  label: '5'
}, {
  value: 6,
  label: '6'
}, {
  value: 7,
  label: '7'
}, {
  value: 8,
  label: '8'
}, {
  value: 9,
  label: '9'
}, {
  value: 10,
  label: '10'
}, {
  value: 11,
  label: '11'
}, {
  value: 12,
  label: '12'
}]
const daysModelCh = [{
  value: 8,
  label: '全选'
}, {
  value: 0,
  label: '星期日'
}, {
  value: 1,
  label: '星期一'
}, {
  value: 2,
  label: '星期二'
}, {
  value: 3,
  label: '星期三'
}, {
  value: 4,
  label: '星期四'
}, {
  value: 5,
  label: '星期五'
}, {
  value: 6,
  label: '星期六'
}]
const daysModelEn = [{
  value: 8,
  label: 'all'
}, {
  value: 0,
  label: 'Sun.'
}, {
  value: 1,
  label: 'Mon.'
}, {
  value: 2,
  label: 'Tues.'
}, {
  value: 3,
  label: 'Wed.'
}, {
  value: 4,
  label: 'Thur.'
}, {
  value: 5,
  label: 'Fir.'
}, {
  value: 6,
  label: 'Sat.'
}]
const datesModel = ['全选', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]
const dateObj = {
  monthsModel,
  daysModelCh,
  daysModelEn,
  datesModel
}
export default dateObj
