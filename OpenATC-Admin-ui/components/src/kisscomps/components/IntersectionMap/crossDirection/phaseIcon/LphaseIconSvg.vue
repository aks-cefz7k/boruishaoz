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
  <div :style="{position: 'absolute', left: Data.left, top: Data.top}">
    <div :class="Data.id < 9 ? '' : 'hide'">
      <svg version="1.1" id="图层_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
        viewBox="0 0 36 26" style="enable-background:new 0 0 36 26;" xml:space="preserve" :width="IconLengh" :height="IconWdith">
          <!-- 东相位 -->
          <path id="东左转" :class="Data.name === '东左转' ? '' : 'invisible'" d="M11,7.1h24v-3H10.9c-3.1,0-5.7,2.6-5.8,5.7l-0.4,2.9L1,12.2l3.9,9.7l6.5-8.2l-3.7-0.5l0.5-3.3
            C8.2,8.4,9.4,7.1,11,7.1L11,7.1z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <path id="东右转" :class="Data.name === '东右转' ? '' : 'invisible'" d="M8.1,15.8l-0.4-3l3.7-0.5L4.9,4.1L1,13.8l3.7-0.5l0.4,2.9c0,3.2,2.6,5.7,5.8,5.7H35v-3H10.9
            c-1.5,0-2.8-1.3-2.8-2.8V15.8z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <polygon id="东直行" :class="Data.name === '东直行' ? '' : 'invisible'" points="35,11.5 10.1,11.5 10.1,7.8 0.9,13 10.1,18.2 10.1,14.5 35,14.5 " :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <path id="东掉头" :class="Data.name === '东掉头' ? '' : 'invisible'" d="M13.6,15.2c0,3.7,2.6,6.7,5.8,6.7H35v-3H19.4c-1.5,0-2.8-1.7-2.8-3.7s1.3-3.7,2.8-3.7h4.3v3.7
l9.1-5.2l-9.1-5.2v3.7h-4.3C16.2,8.5,13.6,11.5,13.6,15.2z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <!-- 西相位 -->
          <path id="西左转" :class="Data.name === '西左转' ? '' : 'invisible'" d="M25,18.9H1v3h24.1c3.1,0,5.7-2.6,5.8-5.7l0.4-2.9l3.7,0.5l-3.9-9.7l-6.5,8.2l3.7,0.5l-0.5,3.3
            C27.8,17.6,26.6,18.9,25,18.9L25,18.9z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <path id="西右转" :class="Data.name === '西右转' ? '' : 'invisible'" d="M27.9,10.2l0.4,3l-3.7,0.5l6.5,8.2l3.9-9.7l-3.7,0.5l-0.4-2.9c0-3.2-2.6-5.7-5.8-5.7H1v3h24.1
            c1.5,0,2.8,1.3,2.8,2.8V10.2z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <polygon id="西直行" :class="Data.name === '西直行' ? '' : 'invisible'" points="1,14.5 26,14.5 26,18.2 35.1,13 26,7.8 26,11.5 1,11.5 " :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <path id="西掉头" :class="Data.name === '西掉头' ? '' : 'invisible'" d="M22.4,10.8c0-3.7-2.6-6.7-5.8-6.7H1v3h15.7c1.5,0,2.8,1.7,2.8,3.7s-1.3,3.7-2.8,3.7h-4.3v-3.7
L3.3,16l9,5.3v-3.8h4.3C19.8,17.5,22.4,14.5,22.4,10.8z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
        </svg>
      </div>
      <div :class="Data.id >= 9 ? '' : 'hide'">
        <svg version="1.1" id="图层_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
          viewBox="0 0 26 36" style="enable-background:new 0 0 26 36;" xml:space="preserve" :width="IconWdith" :height="IconLengh">
          <!-- 南相位 -->
          <path id="南左转" :class="Data.name === '南左转' ? '' : 'invisible'" d="M18.9,11v24h3V10.9c0-3.1-2.6-5.7-5.7-5.8l-2.9-0.4L13.8,1L4.1,4.9l8.2,6.5l0.5-3.7l3.3,0.5
            C17.6,8.2,18.9,9.4,18.9,11L18.9,11z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <path id="南右转" :class="Data.name === '南右转' ? '' : 'invisible'" d="M10.2,8.1l3-0.4l0.5,3.7l8.2-6.5L12.2,1l0.5,3.7L9.8,5.1c-3.2,0-5.7,2.6-5.7,5.8V35h3V10.9
            c0-1.5,1.3-2.8,2.8-2.8H10.2z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <polygon id="南直行" :class="Data.name === '南直行' ? '' : 'invisible'" points="14.5,35 14.5,10.1 18.2,10.1 13,0.9 7.8,10.1 11.5,10.1 11.5,35 " :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <path id="南掉头" :class="Data.name === '南掉头' ? '' : 'invisible'" d="M10.8,13.6c-3.7,0-6.7,2.6-6.7,5.8V35h3V19.4c0-1.5,1.7-2.8,3.7-2.8s3.7,1.3,3.7,2.8v4.3h-3.7
l5.2,9.1l5.2-9.1h-3.8v-4.3C17.4,16.2,14.4,13.6,10.8,13.6z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
          <!-- 北相位 -->
            <path id="北左转" :class="Data.name === '北左转' ? '' : 'invisible'" d="M7.1,25V1h-3v24.1c0,3.1,2.6,5.7,5.7,5.8l2.9,0.4L12.2,35l9.7-3.9l-8.2-6.5l-0.5,3.7l-3.3-0.5
              C8.4,27.8,7.1,26.6,7.1,25L7.1,25z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
            <path id="北右转" :class="Data.name === '北右转' ? '' : 'invisible'" d="M15.8,27.9l-3,0.4l-0.5-3.7l-8.2,6.5l9.7,3.9l-0.5-3.7l2.9-0.4c3.2,0,5.7-2.6,5.7-5.8V1h-3v24.1
              c0,1.5-1.3,2.8-2.8,2.8H15.8z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
            <polygon id="北直行" :class="Data.name === '北直行' ? '' : 'invisible'" points="11.5,0.9 11.5,25.9 7.8,25.9 13,35 18.2,25.9 14.5,25.9 14.5,0.9 " :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
            <path id="北掉头" :class="Data.name === '北掉头' ? '' : 'invisible'" d="M15.2,22.4c3.7,0,6.7-2.6,6.7-5.8V1h-3v15.7c0,1.5-1.7,2.8-3.7,2.8s-3.7-1.3-3.7-2.8v-4.3h3.7
L10,3.3l-5.2,9h3.7v4.3C8.5,19.8,11.5,22.4,15.2,22.4z" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"/>
        </svg>
      </div>
  </div>
</template>
<script>
export default {
  name: 'iconsvg',
  data () {
    return {
      defaultColor: '#fff', // 默认状态颜色
      GreenFlashColor: undefined,
      lastType: ''
    }
  },
  watch: {
    Data: {
      handler: function (val) {
        if (val.type === 4 && this.lastType === '') {
          // 绿闪：绿-》灰-》绿 循环效果
          this.GreenFlashColor = '#77fb65'
          this.GreenIntervalId = setInterval(() => {
            this.GreenFlashColor = !this.GreenFlashColor || this.GreenFlashColor === '#828282' ? '#77fb65' : '#828282'
          }, 500)
          this.lastType = val.type
        }
        if (this.GreenIntervalId && val.type !== 4 && val.type !== this.lastType) {
          clearInterval(this.GreenIntervalId)
          this.GreenFlashColor = undefined
          this.lastType = ''
        }
      },
      deep: true
    }
  },
  props: {
    IconLengh: { // 相位图标长度
      type: String,
      default: '34px'
    },
    IconWdith: { // 相位图标宽度
      type: String,
      default: '22px'
    },
    Data: {
      type: Object
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style scoped>
.invisible {
  visibility: hidden;
}
.hide {
  display: none;
}
</style>
