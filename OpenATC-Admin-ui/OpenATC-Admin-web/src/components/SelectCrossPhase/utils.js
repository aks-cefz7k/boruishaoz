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
import Edgebase from '@openatc/edgebase-front'
let images = [{
  id: 1,
  // name: 'East-Straight',
  name: 'edge.phase.es',
  // img: require('./images/East-Straight.svg'),
  class: 'iconfont icon-icon-test2'
},
{
  id: 2,
  // name: 'East-Left',
  name: 'edge.phase.el',
  // img: require('./images/East-Left.svg'),
  class: 'iconfont icon-icon-test4'
},
{
  id: 3,
  // name: 'East-Right',
  name: 'edge.phase.er',
  // img: require('./images/East-Right.svg'),
  class: 'iconfont icon-icon-test3'
},
{
  id: 4,
  // name: 'East-Back',
  name: 'edge.phase.eb',
  // img: require('./images/East-Back.svg'),
  class: 'iconfont icon-icon-test6'
},
{
  id: 5,
  // name: 'West-Straight',
  name: 'edge.phase.ws',
  // img: require('./images/West-Straight.svg'),
  class: 'iconfont icon-icon-test5'
},
{
  id: 6,
  // name: 'West-Left',
  name: 'edge.phase.wl',
  // img: require('./images/West-Left.svg'),
  class: 'iconfont icon-icon-test7'
},
{
  id: 7,
  // name: 'West-Right',
  name: 'edge.phase.wr',
  // img: require('./images/West-Right.svg'),
  class: 'iconfont icon-icon-test9'
},
{
  id: 8,
  // name: 'West-Back',
  name: 'edge.phase.wb',
  // img: require('./images/West-Back.svg'),
  class: 'iconfont icon-icon-test8'
},
{
  id: 9,
  // name: 'North-Straight',
  name: 'edge.phase.ns',
  // img: require('./images/North-Straight.svg'),
  class: 'iconfont icon-icon-test10'
},
{
  id: 10,
  // name: 'North-Left',
  name: 'edge.phase.nl',
  // img: require('./images/North-Left.svg'),
  class: 'iconfont icon-icon-test12'
},
{
  id: 11,
  // name: 'North-Right',
  name: 'edge.phase.nr',
  // img: require('./images/North-Right.svg'),
  class: 'iconfont icon-icon-test13'
},
{
  id: 12,
  // name: 'North-Back',
  name: 'edge.phase.nb',
  // img: require('./images/North-Back.svg'),
  class: 'iconfont icon-icon-test11'
},
{
  id: 13,
  // name: 'South-Straight',
  name: 'edge.phase.ss',
  // img: require('./images/South-Straight.svg'),
  class: 'iconfont icon-icon-test15'
},
{
  id: 14,
  // name: 'South-Left',
  name: 'edge.phase.sl',
  // img: require('./images/South-Left.svg'),
  class: 'iconfont icon-icon-test14'
},
{
  id: 15,
  // name: 'South-Right',
  name: 'edge.phase.sr',
  // img: require('./images/South-Right.svg'),
  class: 'iconfont icon-icon-test'
},
{
  id: 16,
  // name: 'South-Back',
  name: 'edge.phase.sb',
  // img: require('./images/South-Back.svg'),
  class: 'iconfont icon-icon-test1'
}]

const getImages = () => {
  if (Edgebase.Store.getters.roadDirection === 'left') {
    // 左行下，掉头图标替换
    images.forEach((img) => {
      if (img.id === 4) {
        img.class = 'iconfont icon-dongtiaotou-yinni'
      }
      if (img.id === 8) {
        img.class = 'iconfont icon-xitiaotou-yinni'
      }
      if (img.id === 12) {
        img.class = 'iconfont icon-beitiaotou-yinni'
      }
      if (img.id === 16) {
        img.class = 'iconfont icon-nantiaotou-yinni'
      }
    })
  }
  return images
}

const getDirName = (arr) => {
  let res = ''
  let nameArr = []
  for (let item of arr) {
    let id = item.id
    for (let img of images) {
      if (id === img.id) {
        let name = img.name
        nameArr.push(name)
      }
    }
  }
  res = nameArr
  return res
}

export {getImages, getDirName}
