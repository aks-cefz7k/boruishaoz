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
<!-- 单视频播放组件 -->
  <div class="flvplayer" v-if="!!curVideoInfo">
    <div class="flv-header">
      <span class="title">{{ this.Title || title }}</span>
      <span class="list-icon">
        <div>
          <i class="iconfont icon-shipinshuaxin" @click="reload(curVideoInfo.flvPlayer)"></i>
        </div>
        <slot name="vediolist"></slot>
      </span>
    </div>
    <div class="flv-content">
      <video autoplay controls :width="Width" :height="Height" :id='"videoElement" + curVideoInfo.id'></video>
    </div>
  </div>
</template>

<script>
import flvjs from 'flv.js'

export default {
  name: 'flvplayer',
  props: {
    curVideoInfo: { // 当前播放的视频信息
      type: Object
    },
    Width: {
      type: String,
      default: '100%'
    },
    Height: {
      type: String,
      default: '237'
    },
    Title: { // 播放窗口的标题，如果不传，默认是视频信息里name
      type: String
    }
  },
  data () {
    return {
      title: this.Title
    }
  },
  mounted () {
    if (!this.curVideoInfo || this.curVideoInfo === {}) return
    if (this.curVideoInfo.hasOwnProperty('name')) {
      this.title = this.curVideoInfo.name
    }
    this.$nextTick(() => {
      this.createVideo()
    })
  },
  watch: {
    curVideoInfo: {
      handler: function (val) {
        if (!val) return
        if (val.hasOwnProperty('name')) {
          this.title = val.name
        }
      },
      deep: true
    }
  },
  methods: {
    createVideo () {
      if (flvjs.isSupported()) {
        if (!this.curVideoInfo) return
        this.curVideoInfo.videoElement = document.getElementById(`videoElement${this.curVideoInfo.id}`)
        let flvPlayer = flvjs.createPlayer({
          type: 'flv',
          url: this.curVideoInfo.url
        })
        this.curVideoInfo.flvPlayer = flvPlayer
        flvPlayer.attachMediaElement(this.curVideoInfo.videoElement)
        flvPlayer.load()
        flvPlayer.pause()
      }
    },
    play (media) {
      // 播放
      media.play()
    },
    reload (media) {
      // 重新加载监控视频（以当前时间为准）
      this.flv_destroy(media)
      this.createVideo()
    },
    flv_pause (media) {
      // 暂停
      media.pause()
    },
    flv_destroy (media) {
      // 销毁
      media.pause()
      media.unload()
      media.detachMediaElement()
      media.destroy()
      media = null
    }
  },
  destroyed () {
    if (!this.curVideoInfo) return
    this.flv_destroy(this.curVideoInfo.flvPlayer)
  }
}
</script>
