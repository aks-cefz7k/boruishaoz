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
/**
 * @Description: 根据相位的描述id获取对应的描述名字
 * @Author: yangdongyang
 * @Date: Create in 13:41 2019/3/26
 * @Modified By:
 */
const images = [{
  id: 1,
  zname: '东直行',
  ename: 'East-Straight'
},
{
  id: 2,
  zname: '东左转',
  ename: 'East-Left'
},
{
  id: 3,
  zname: '东右转',
  ename: 'East-Right'
},
{
  id: 4,
  zname: '东掉头',
  ename: 'East-Back'
},
{
  id: 5,
  zname: '西直行',
  ename: 'West-Straight'
},
{
  id: 6,
  zname: '西左转',
  ename: 'West-Left'
},
{
  id: 7,
  zname: '西右转',
  ename: 'West-Right'
},
{
  id: 8,
  zname: '西掉头',
  ename: 'West-Back'
},
{
  id: 9,
  zname: '北直行',
  ename: 'North-Straight'
},
{
  id: 10,
  zname: '北左转',
  ename: 'North-Left'
},
{
  id: 11,
  zname: '北右转',
  ename: 'North-Right'
},
{
  id: 12,
  zname: '北掉头',
  ename: 'North-Back'
},
{
  id: 13,
  zname: '南直行',
  ename: 'South-Straight'
},
{
  id: 14,
  zname: '南左转',
  ename: 'South-Left'
},
{
  id: 15,
  zname: '南右转',
  ename: 'South-Right'
},
{
  id: 16,
  zname: '南掉头',
  ename: 'South-Back'
}]

export function getPhaseDesc (list, language) {
  // let language = this.$i18n.locale
  // console.log(language)
  let str = ''
  if (language === 'en') {
    for (let ll of list) {
      for (let image of images) {
        if (image.id === ll) {
          str = str + ',' + image.ename
        }
      }
    }
  } else {
    for (let ll of list) {
      for (let image of images) {
        if (image.id === ll) {
          str = str + ',' + image.zname
        }
      }
    }
  }
  if (str !== '') {
    str = str.substr(1)
  }
  return str
}
