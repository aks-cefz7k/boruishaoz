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
// https://github.com/michael-ciniawsky/postcss-load-config

module.exports = {
  "plugins": {
    "postcss-import": {},
    "postcss-url": {},
    // to edit target browsers: use "browserslist" field in package.json
    "autoprefixer": {},
    "postcss-pxtorem": {
      "rootValue": 192, // 设计稿宽度的1/10
      "propList": ["*"], // 需要做转化处理的属性，如`hight`、`width`、`margin`等，`*`表示全部
      // "selectorBlackList": ['.tagsList', '.el', '.el-dialog__footer'],
      "selectorBlackList": ['.el', '.leaflet', '.el-dialog__footer'],
      "exclude": /node_modules/i
    },
    // "postcss-px2rem-exclude": {
    //     "remUnit": 192,
    //     "exclude": /node_modules/i,
    //     "selectorBlackList": ['.tagsList'] 
    //     // ,
    //     // "selectorBlackList": ['iconfont'],
    //     // "propBlackList": ['border']
    //     // "exclude": "/node_modules/element-theme|node_modules/element-theme-chalk|node_modules/element-ui/"
    // }
  }
}
