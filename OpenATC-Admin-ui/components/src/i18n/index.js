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
import locale from 'element-ui/lib/locale'
import VueI18n from 'vue-i18n'
import messages from './language'
import Vue from 'vue'

Vue.use(VueI18n)

const i18n = new VueI18n({
  locale: 'zh', // 语言标识
  messages
})

locale.i18n((key, value) => i18n.t(key, value))

export default i18n
