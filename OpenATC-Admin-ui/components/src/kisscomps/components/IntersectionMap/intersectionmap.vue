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
<div class="intersection-map">
  <div class="crossDirection-display" :class="{
    'superlargeCrossImg': bodyDomWidth <= 1680 && bodyDomWidth > 1440,
    'largeCrossImg': bodyDomWidth <= 1440 && bodyDomWidth > 1280,
    'middleCrossImg': bodyDomWidth <= 1280 && bodyDomWidth > 960,
    'smallCrossImg': bodyDomWidth <= 960 && bodyDomWidth > 720,
    'miniCrossImg': bodyDomWidth <= 720 && bodyDomWidth > 650,
    'superminiCrossImg': bodyDomWidth <= 650 && bodyDomWidth > 450,
    'transMiddleCrossImg': bodyDomWidth <= 450 && bodyDomWidth > 350,
    'transMiddleCrossImg2': bodyDomWidth <= 350 && bodyDomWidth > 300,
    'transMiddleCrossImg3': bodyDomWidth <= 300 && bodyDomWidth > 260,
    'transMiniCrossImg': bodyDomWidth <= 260,
    'changePaddingBottom': graphicMode }">
    <CrossDiagram v-if="reset"
      :crossStatusData="crossStatusData"
      :agentId="agentId"
      :isShowInterval="isShowInterval"
      :devStatus="devStatus" />
  </div>
</div>
</template>

<script>
import CrossDiagram from './crossDirection/crossDiagram'
import { setToken } from '../../../utils/auth.js'
export default {
  name: 'intersection-map',
  components: {
    CrossDiagram
  },
  data () {
    return {
      reset: false,
      bodyDomWidth: 352,
      bodyDomSize: {
        width: 1920,
        height: 1080
      }
    }
  },
  props: {
    crossStatusData: {
      type: Object,
      required: true
    },
    devStatus: {
      type: Number,
      default: 0
    },
    agentId: {
      type: String,
      default: '0'
    },
    graphicMode: {
      type: Boolean,
      default: false
    },
    isShowInterval: {
      type: Boolean,
      default: true
    },
    Token: {
      handler: function (val) {
        this.setPropsToken(val)
      }
    }
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        if (val.query !== undefined) {
          this.resetCrossDiagram()
        }
      },
      // 深度观察监听
      deep: true
    },
    Token: {
      handler: function (val) {
        this.setPropsToken(val)
      }
    }
  },
  created () {
    if (this.$route.query !== undefined && Object.keys(this.$route.query).length) {
      this.resetCrossDiagram()
    }
  },
  mounted () {
    this.getParentSize()
    this.setPropsToken(this.Token)
  },
  updated () {
  },
  methods: {
    resetCrossDiagram () {
      this.reset = false
      this.$nextTick(() => {
        this.reset = true
      })
    },
    getParentSize () {
      // 获取最外层dom尺寸，适配准备
      var _this = this
      this.$nextTick(function () {
        if (this.$el.parentElement === null || this.$el.parentElement === undefined) return
        this.bodyDomSize.width = this.$el.parentElement.clientWidth
        this.bodyDomWidth = this.bodyDomSize.width
        window.addEventListener('resize', () => {
        // 定义窗口大小变更通知事件
          _this.bodyDomSize.width = _this.$el.parentElement.clientWidth
          this.bodyDomWidth = this.bodyDomSize.width
          console.log('resize this.bodyDomSize.width', _this.bodyDomSize.width)
        }, false)
      })
    },
    setPropsToken (token) {
      // 获取组件外传入的token，便于独立组件调用接口
      if (token && token !== '') {
        setToken(token)
      }
    }
  },
  destroyed () {
  }
}
</script>
