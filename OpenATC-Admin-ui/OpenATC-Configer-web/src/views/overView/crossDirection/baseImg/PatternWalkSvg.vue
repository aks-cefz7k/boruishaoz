/* eslint-disable no-tabs */
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
  <div :style="{position: 'absolute', left: Data.left?Data.left:'2px', top: Data.top}">
    <svg
       :width="Width"
       :height="Height"
        version="1.1"
        id="图层_1"
        xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
        viewBox="0 0 34 34"
        style="enable-background:new 0 0 34 34;"
        xml:space="preserve">
    <g id="路段行人过街-东西"
    :class="Data.name === '东西路段人行横道' ? '' : 'invisible'"
    class="st0">
    <rect x="19.9" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="17.4" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="22.4" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="24.9" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="27.4" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="29.9" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="32.4" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="7.5" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="5" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="2.5" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="0" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="10" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="12.5" y="14.2" class="st1" width="1.6" height="5.6"/>
    <rect x="14.9" y="14.2" class="st1" width="1.6" height="5.6"/>
    </g>
    <g id="路段行人过街-南北" class="st0"
    :class="Data.name === '南北路段人行横道' ? '' : 'invisible'"
    >
    <rect x="14.2" y="19.9" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="17.4" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="22.4" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="24.9" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="27.4" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="29.9" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="32.4" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="7.5" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="5" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="2.5" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="0" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="10" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="12.5" class="st1" width="5.6" height="1.6"/>
    <rect x="14.2" y="14.9" class="st1" width="5.6" height="1.6"/>
    </g>
    <g id="斜向行人1" class="st0"
    :class="Data.name === 'X人行横道-/' ? '' : 'invisible'"
    >
    <rect x="14.2" y="16.2" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -7.0438 17.0053)" class="st1" width="5.6" height="1.6"/>
    <rect x="12.4" y="14.4" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -6.3149 15.2456)" class="st1" width="5.6" height="1.6"/>
    <rect x="15.9" y="17.9" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -7.7727 18.7651)" class="st1" width="5.6" height="1.6"/>
    <rect x="17.7" y="19.7" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -8.5017 20.5248)" class="st1" width="5.6" height="1.6"/>
    <rect x="19.5" y="21.5" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -9.2306 22.2845)" class="st1" width="5.6" height="1.6"/>
    <rect x="21.2" y="23.2" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -9.9595 24.0443)" class="st1" width="5.6" height="1.6"/>
    <rect x="23" y="25" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -10.6884 25.804)" class="st1" width="5.6" height="1.6"/>
    <rect x="24.7" y="26.7" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -11.4128 27.5531)" class="st1" width="5.6" height="1.6"/>
    <rect x="26.5" y="28.5" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -12.1418 29.3128)" class="st1" width="5.6" height="1.6"/>
    <rect x="28.3" y="30.3" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -12.8707 31.0725)" class="st1" width="5.6" height="1.6"/>
    <rect x="5.4" y="7.4" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -3.3993 8.2067)" class="st1" width="5.6" height="1.6"/>
    <rect x="3.6" y="5.6" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -2.6704 6.4469)" class="st1" width="5.6" height="1.6"/>
    <rect x="1.9" y="3.9" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -1.9415 4.6872)" class="st1" width="5.6" height="1.6"/>
    <rect x="0.1" y="2.1" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -1.2126 2.9275)" class="st1" width="5.6" height="1.6"/>
    <rect x="7.1" y="9.2" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -4.1282 9.9664)" class="st1" width="5.6" height="1.6"/>
    <rect x="8.9" y="10.9" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -4.8571 11.7261)" class="st1" width="5.6" height="1.6"/>
    <rect x="10.7" y="12.7" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -5.586 13.4859)" class="st1" width="5.6" height="1.6"/>
    </g>
    <g id="斜向行人2" class="st0"
    :class="Data.name === 'X人行横道-\\' ? '' : 'invisible'"
    >
    <rect x="16.2" y="14.2" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -7.047 16.9978)" class="st1" width="1.6" height="5.6"/>
    <rect x="17.9" y="12.4" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -5.2872 17.7267)" class="st1" width="1.6" height="5.6"/>
    <rect x="14.4" y="15.9" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -8.8067 16.2689)" class="st1" width="1.6" height="5.6"/>
    <rect x="12.7" y="17.7" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -10.5664 15.54)" class="st1" width="1.6" height="5.6"/>

    <rect x="10.9" y="19.5" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -12.3262 14.8111)" class="st1" width="1.6" height="5.6"/>
    <rect x="9.1" y="21.2" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -14.0859 14.0822)" class="st1" width="1.6" height="5.6"/>
    <rect x="7.4" y="23" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -15.8456 13.3533)" class="st1" width="1.6" height="5.6"/>
    <rect x="5.6" y="24.7" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -17.5947 12.6288)" class="st1" width="1.6" height="5.6"/>
    <rect x="3.9" y="26.5" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -19.3544 11.8999)" class="st1" width="1.6" height="5.6"/>
    <rect x="2.1" y="28.3" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -21.1142 11.171)" class="st1" width="1.6" height="5.6"/>
    <rect x="25" y="5.4" transform="matrix(0.7071 -0.7071 0.7071 0.7071 1.7517 20.6423)" class="st1" width="1.6" height="5.6"/>
    <rect x="26.7" y="3.6" transform="matrix(0.7071 -0.7071 0.7071 0.7071 3.5114 21.3712)" class="st1" width="1.6" height="5.6"/>
    <rect x="28.5" y="1.9" transform="matrix(0.7071 -0.7071 0.7071 0.7071 5.2712 22.1001)" class="st1" width="1.6" height="5.6"/>
    <rect x="30.3" y="0.1" transform="matrix(0.7071 -0.7071 0.7071 0.7071 7.0309 22.829)" class="st1" width="1.6" height="5.6"/>

    <rect x="23.2" y="7.1" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -8.040609e-03 19.9134)" class="st1" width="1.6" height="5.6"/>
    <rect x="21.5" y="8.9" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -1.7678 19.1845)" class="st1" width="1.6" height="5.6"/>
    <rect x="19.7" y="10.7" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -3.5275 18.4556)" class="st1" width="1.6" height="5.6"/>
    </g>
    <g id="北行人"
    :class="Data.name === '北人行横道' ? '' : 'invisible'"
    class="st0">
    <rect x="19.9" class="st1" width="1.6" height="5.6"/>
    <rect x="17.4" class="st1" width="1.6" height="5.6"/>
    <rect x="22.4" class="st1" width="1.6" height="5.6"/>
    <rect x="24.9" class="st1" width="1.6" height="5.6"/>
    <rect x="27.4" class="st1" width="1.6" height="5.6"/>
    <rect x="29.9" class="st1" width="1.6" height="5.6"/>
    <rect x="32.4" class="st1" width="1.6" height="5.6"/>
    <rect x="7.5" class="st1" width="1.6" height="5.6"/>
    <rect x="5" class="st1" width="1.6" height="5.6"/>
    <rect x="2.5" class="st1" width="1.6" height="5.6"/>
    <rect x="0" class="st1" width="1.6" height="5.6"/>
    <rect x="10" class="st1" width="1.6" height="5.6"/>
    <rect x="12.5" class="st1" width="1.6" height="5.6"/>
    <rect x="14.9" class="st1" width="1.6" height="5.6"/>
    </g>
    <g id="南行人"
    :class="Data.name === '南人行横道' ? '' : 'invisible'"
    class="st0">
    <rect x="19.9" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="17.4" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="22.4" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="24.9" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="27.4" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="29.9" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="32.4" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="7.5" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="5" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="2.5" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="0" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="10" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="12.5" y="28.4" class="st1" width="1.6" height="5.6"/>
    <rect x="14.9" y="28.4" class="st1" width="1.6" height="5.6"/>
    </g>
    <g id="东行人"
    :class="Data.name === '东人行横道' ? '' : 'invisible'"
    class="st0">
    <rect x="28.4" y="19.9" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="17.4" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="22.4" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="24.9" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="27.4" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="29.9" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="32.4" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="7.5" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="5" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="2.5" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="0" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="10" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="12.5" class="st1" width="5.6" height="1.6"/>
    <rect x="28.4" y="14.9" class="st1" width="5.6" height="1.6"/>
    </g>
    <g id="西行人"
    :class="Data.name === '西人行横道' ? '' : 'invisible'"
    class="st0">
    <rect y="19.9" class="st1" width="5.6" height="1.6"/>
    <rect y="17.4" class="st1" width="5.6" height="1.6"/>
    <rect y="22.4" class="st1" width="5.6" height="1.6"/>
    <rect y="24.9" class="st1" width="5.6" height="1.6"/>
    <rect y="27.4" class="st1" width="5.6" height="1.6"/>
    <rect y="29.9" class="st1" width="5.6" height="1.6"/>
    <rect y="32.4" class="st1" width="5.6" height="1.6"/>
    <rect y="7.5" class="st1" width="5.6" height="1.6"/>
    <rect y="5" class="st1" width="5.6" height="1.6"/>
    <rect y="2.5" class="st1" width="5.6" height="1.6"/>
    <rect y="0" class="st1" width="5.6" height="1.6"/>
    <rect y="10" class="st1" width="5.6" height="1.6"/>
    <rect y="12.5" class="st1" width="5.6" height="1.6"/>
    <rect y="14.9" class="st1" width="5.6" height="1.6"/>
    </g>
    <g id="二次过街-西" class="st0">
    <g id="西下"
    :class="Data.name === '西人行横道-下' ? '' : 'invisible'"
    >
        <rect y="19.9" class="st1" width="5.6" height="1.6"/>
        <rect y="22.4" class="st1" width="5.6" height="1.6"/>
        <rect y="24.9" class="st1" width="5.6" height="1.6"/>
        <rect y="27.4" class="st1" width="5.6" height="1.6"/>
        <rect y="29.9" class="st1" width="5.6" height="1.6"/>
        <rect y="32.4" class="st1" width="5.6" height="1.6"/>
    </g>
    <g id="西上"
    :class="Data.name === '西人行横道-上' ? '' : 'invisible'"
    >
        <rect y="7.5" class="st1" width="5.6" height="1.6"/>
        <rect y="5" class="st1" width="5.6" height="1.6"/>
        <rect y="2.5" class="st1" width="5.6" height="1.6"/>
        <rect y="0" class="st1" width="5.6" height="1.6"/>
        <rect y="10" class="st1" width="5.6" height="1.6"/>
        <rect y="12.5" class="st1" width="5.6" height="1.6"/>
    </g>
    </g>
    <g id="二次过街-东" class="st0">
    <g id="东上"
     :class="Data.name === '东人行横道-上' ? '' : 'invisible'"
    >
        <rect x="28.4" y="12.5" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="10" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="7.5" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="5" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="2.5" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="0" class="st1" width="5.6" height="1.6"/>
    </g>
    <g id="东下"
    :class="Data.name === '东人行横道-下' ? '' : 'invisible'"
    >
        <rect x="28.4" y="24.9" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="27.4" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="29.9" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="32.4" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="22.4" class="st1" width="5.6" height="1.6"/>
        <rect x="28.4" y="19.9" class="st1" width="5.6" height="1.6"/>
    </g>
    </g>
    <g id="二次过街-南" class="st0">
    <g id="南右"
    :class="Data.name === '南人行横道-右' ? '' : 'invisible'"
    >
        <rect x="19.9" y="28.4" class="st1" width="1.6" height="5.6"/>
        <rect x="22.4" y="28.4" class="st1" width="1.6" height="5.6"/>
        <rect x="24.9" y="28.4" class="st1" width="1.6" height="5.6"/>
        <rect x="27.4" y="28.4" class="st1" width="1.6" height="5.6"/>
        <rect x="29.9" y="28.4" class="st1" width="1.6" height="5.6"/>
        <rect x="32.4" y="28.4" class="st1" width="1.6" height="5.6"/>
    </g>
        <g id="南左"
        :class="Data.name === '南人行横道-左' ? '' : 'invisible'"
        >
            <rect x="7.5" y="28.4" class="st1" width="1.6" height="5.6"/>
            <rect x="5" y="28.4" class="st1" width="1.6" height="5.6"/>
            <rect x="2.5" y="28.4" class="st1" width="1.6" height="5.6"/>
            <rect x="0" y="28.4" class="st1" width="1.6" height="5.6"/>
            <rect x="10" y="28.4" class="st1" width="1.6" height="5.6"/>
            <rect x="12.5" y="28.4" class="st1" width="1.6" height="5.6"/>
        </g>
    </g>
        <g id="二次过街-北_1_" class="st0">
        <g id="北右_1_"
        :class="Data.name === '北人行横道-右' ? '' : 'invisible'"
        >
            <rect x="19.9" class="st1" width="1.6" height="5.6"/>
            <rect x="22.4" class="st1" width="1.6" height="5.6"/>
            <rect x="24.9" class="st1" width="1.6" height="5.6"/>
            <rect x="27.4" class="st1" width="1.6" height="5.6"/>
            <rect x="29.9" class="st1" width="1.6" height="5.6"/>
            <rect x="32.4" class="st1" width="1.6" height="5.6"/>
        </g>
        <g id="北左_1_"
        :class="Data.name === '北人行横道-左' ? '' : 'invisible'"
        >
            <rect x="7.5" class="st1" width="1.6" height="5.6"/>
            <rect x="5" class="st1" width="1.6" height="5.6"/>
            <rect x="2.5" class="st1" width="1.6" height="5.6"/>
            <rect x="0" class="st1" width="1.6" height="5.6"/>
            <rect x="10" class="st1" width="1.6" height="5.6"/>
            <rect x="12.5" class="st1" width="1.6" height="5.6"/>
        </g>
    </g>
    </svg>
  </div>
</template>
<script>
export default {
  name: 'PatternWalkSvg',
  data () {
    return {
    }
  },
  props: {
    Width: {
      type: String,
      default: '34px'
    },
    Height: {
      type: String,
      default: '34px'
    },
    Data: {
      type: Object
    }
  }
}
</script>
<style scoped>
.invisible {
  visibility: hidden;
}
</style>
