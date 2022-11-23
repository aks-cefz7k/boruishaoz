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
    <div :class="Data.controlType === 1 ? '' : 'hide'" :style="{position: 'absolute', left: Data.left, top: Data.top}">
      <svg version="1.1" id="图层_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
        viewBox="0 0 61.6 61.6" style="enable-background:new 0 0 61.6 61.6;" xml:space="preserve" :width="IconLengh" :height="IconWdith">
         <g id="西向支路" :class="Data.name === '西支路' ? '' : 'invisible'">
           <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="3.1,43.9 45.5,29 47.8,35.4 60.3,20.9 41.5,17.7 43.7,23.9 1.3,38.8"/>
        </g>

        <g id="东向支路" :class="Data.name === '东支路' ? '' : 'invisible'">
          <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="58.5,17.7 16.1,32.6 13.8,26.2 1.3,40.7 20.1,43.9 17.9,37.7 60.3,22.8"/>
        </g>

        <g id="北向支路" :class="Data.name === '北支路' ? '' : 'invisible'">
          <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="17.7,3.1 32.6,45.5 26.2,47.8 40.7,60.3 43.9,41.5 37.7,43.7 22.8,1.3"/>
        </g>

        <g id="南向支路" :class="Data.name === '南支路' ? '' : 'invisible'">
          <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="43.9,58.5 29,16.1 35.4,13.8 20.9,1.3 17.7,20.1 23.9,17.9 38.8,60.3"/>
        </g>

        </svg>
      </div>
      <div :class="Data.controlType === 0 ? '' : 'hide'" :style="{position: 'absolute', left: Data.left, top: Data.top}">
        <svg version="1.1" id="图层_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
          viewBox="0 0 61.6 61.6" style="enable-background:new 0 0 61.6 61.6;" xml:space="preserve" :width="IconWdith" :height="IconLengh">
        <g id="西直行" :class="Data.name === '西直行' ? '' : 'invisible'">
          <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="0,33.4 45,33.4 45,40.2 61.6,30.7 45,21.4 45,28 0,28"/>
        </g>

        <g id="东直行" :class="Data.name === '东直行' ? '' : 'invisible'">
          <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="61.6,28.2 16.6,28.2 16.6,21.4 0,30.9 16.6,40.2 16.6,33.6 61.6,33.6"/>
        </g>

        <g id="北直行" :class="Data.name === '北直行' ? '' : 'invisible'">
          <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="28.2,0 28.2,45 21.4,45 30.9,61.6 40.2,45 33.6,45 33.6,0"/>
        </g>

        <g id="南直行" :class="Data.name === '南直行' ? '' : 'invisible'">
          <polygon :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" points="33.4,61.6 33.4,16.6 40.2,16.6 30.7,0 21.4,16.6 28,16.6 28,61.6"/>
        </g>
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
          }, 1000)
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
      default: '61.6px'
    },
    IconWdith: { // 相位图标宽度
      type: String,
      default: '61.6px'
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
