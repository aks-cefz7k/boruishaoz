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
 * @Description: 根据行人相位的描述id获取对应的描述名字
 */
const images = [{
  id: 1,
  zname: '东人行横道',
  ename: 'East-Pedestrian'
},
{
  id: 2,
  zname: '西人行横道',
  ename: 'West-Pedestrian'
},
{
  id: 3,
  zname: '南人行横道',
  ename: 'South-Pedestrian'
},
{
  id: 4,
  zname: '北人行横道',
  ename: 'North-Pedestrian'
},
{
  id: 5,
  zname: '东人行横道-上',
  ename: 'East-Top-Pedestrian'
},
{
  id: 6,
  zname: '东人行横道-下',
  ename: 'East-Bottom-Pedestrian'
},
{
  id: 7,
  zname: '西人行横道-上',
  ename: 'West-Top-Pedestrian'
},
{
  id: 8,
  zname: '西人行横道-下',
  ename: 'West-Bottom-Pedestrian'
},
{
  id: 9,
  zname: '南人行横道-左',
  ename: 'South-Left-Pedestrian'
},
{
  id: 10,
  zname: '南人行横道-右',
  ename: 'South-Right-Pedestrian'
},
{
  id: 11,
  zname: '北人行横道-左',
  ename: 'North-Left-Pedestrian'
},
{
  id: 12,
  zname: '北人行横道-右',
  ename: 'North-Right-Pedestrian'
},
{
  id: 13,
  zname: 'X人行横道-/',
  ename: 'X-/-Pedestrian'
},
{
  id: 14,
  zname: 'X人行横道-\\',
  ename: 'X-\\-Pedestrian'
},
{
  id: 15,
  zname: '东西路段人行横道',
  ename: 'East-West-Pedestrian'
},
{
  id: 16,
  zname: '南北路段人行横道',
  ename: 'South-North-Pedestrian'
}]

export function getPedPhaseDesc (list, language) {
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
