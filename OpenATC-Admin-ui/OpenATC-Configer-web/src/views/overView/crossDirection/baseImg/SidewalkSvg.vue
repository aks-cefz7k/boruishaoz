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
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 252.1 250.2" :width="Width" :height="Height">
      <path id="西" v-if="crossType !== 'TypeT-east'" :class="Data.name === '北直行' ||  Data.name === '北左转' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M2,230v-4H18v4H2Zm0-12H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-9H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2V97Zm0-8H18v4H2V89Zm0-8H18v4H2V81Zm0-9H18v4H2V72Zm0-8H18v4H2V64Zm0-8H18v4H2V56Zm0-8H18v4H2V48Zm0-8H18v4H2V40Zm0-8H18v4H2V32Zm0-8H18v4H2V24Z" transform="translate(-2 -1)"/>
      <path id="东" v-if="crossType !== 'TypeT-west'" :class="Data.name === '南直行' ||  Data.name === '南左转' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M238,230v-4h16v4H238Zm0-12h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-9h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238V97Zm0-8h16v4H238V89Zm0-8h16v4H238V81Zm0-9h16v4H238V72Zm0-8h16v4H238V64Zm0-8h16v4H238V56Zm0-8h16v4H238V48Zm0-8h16v4H238V40Zm0-8h16v4H238V32Zm0-8h16v4H238V24Z" transform="translate(-2 -1)"/>
      <path id="北" v-if="crossType !== 'TypeT-south'" :class="Data.name === '东直行' ||  Data.name === '东左转' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M227,17V1h4V17h-4ZM219,1h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-9,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1ZM98,1h4V17H98V1ZM90,1h4V17H90V1ZM82,1h4V17H82V1ZM73,1h4V17H73V1ZM65,1h4V17H65V1ZM57,1h4V17H57V1ZM49,1h4V17H49V1ZM41,1h4V17H41V1ZM33,1h4V17H33V1ZM25,1h4V17H25V1Z" transform="translate(-2 -1)"/>
      <path id="南" v-if="crossType !== 'TypeT-north'" :class="Data.name === '西直行' ||  Data.name === '西左转' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M227,253V237h4v16h-4Zm-8-16h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-9,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16H98V237Zm-8,0h4v16H90V237Zm-8,0h4v16H82V237Zm-9,0h4v16H73V237Zm-8,0h4v16H65V237Zm-8,0h4v16H57V237Zm-8,0h4v16H49V237Zm-8,0h4v16H41V237Zm-8,0h4v16H33V237Zm-8,0h4v16H25V237Z" transform="translate(-2 -1)"/>
    </svg>
  </div>
</template>
<script>
export default {
  name: 'sidewalksvg',
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
        if (!val.pedtype) return
        if (val.pedtype === 4 && this.lastType === '') {
          // 绿闪：绿-》灰-》绿 循环效果
          this.GreenFlashColor = '#7bd66b'
          this.GreenIntervalId = setInterval(() => {
            this.GreenFlashColor = !this.GreenFlashColor || this.GreenFlashColor === '#828282' ? '#7bd66b' : '#828282'
          }, 1000)
          this.lastType = val.pedtype
        }
        if (this.GreenIntervalId && val.pedtype !== 4 && val.pedtype !== this.lastType) {
          clearInterval(this.GreenIntervalId)
          this.GreenFlashColor = undefined
          this.lastType = ''
        }
      },
      deep: true
    }
  },
  props: {
    Width: {
      type: String,
      default: '252.1px'
    },
    Height: {
      type: String,
      default: '250.2px'
    },
    Data: {
      type: Object
    },
    crossType: {
      type: String
    }
  },
  methods: {
  },
  mounted () {
  }
}
</script>
<style scoped>
.invisible {
  visibility: hidden;
}
.hide {
  display: none;
}
.northWalkItem, .southWalkItem, .eastWalkItem, .westWalkItem {
  fill: #f2f2f2;
}
.green {
  fill: #7bd66b;
}
.red {
  fill: #e24b4b;
}
.yellow {
  fill: #ce932c;
}
.gray {
  fill: #828282;
}
.cls-1 {
  fill: #f2f2f2;
  fill-rule: evenodd;
}
</style>
