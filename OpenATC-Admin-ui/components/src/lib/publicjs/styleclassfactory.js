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
@brief 样式类生成器
@author caijiadong
@date 2018-11-01
*/

/*
@brief 返回添加样式后的类名
@example 当前样式 blue 传入类名 div 返回 div-blue
@        样式从window.cssStyle字段中获取
*/

export const getStyleClassName = (primyclass) => {
  const style = window.cssStyle
  const defaultList = ['', 'default']
  if (style === undefined ||
    defaultList.find(val => val === style) !== undefined) {
    return primyclass
  }
  return `${primyclass}-${style}`
}
