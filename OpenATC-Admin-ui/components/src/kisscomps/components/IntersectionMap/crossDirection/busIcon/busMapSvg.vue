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
<template>
  <div>
    <!-- 公交图标底图 -->
      <!-- 东 -->
      <EastBusIcon :Data="Data"/>
      <!-- 西 -->
      <WestBusSvg :Data="Data"/>
      <!-- 南 -->
      <SouthBusSvg :Data="Data"/>
      <!-- 北 -->
      <NorthBusSvg :Data="Data"/>
  </div>
</template>
<script>
import EastBusIcon from './eastBusSvg'
import WestBusSvg from './westBusSvg'
import NorthBusSvg from './northBusSvg'
import SouthBusSvg from './southBusSvg'
export default {
  name: 'busmapsvg',
  data () {
    return {
    }
  },
  components: {
    EastBusIcon,
    WestBusSvg,
    NorthBusSvg,
    SouthBusSvg
  },
  props: {
    Data: {
      type: Object
    }
  },
  methods: {},
  mounted () {}
}
</script>
