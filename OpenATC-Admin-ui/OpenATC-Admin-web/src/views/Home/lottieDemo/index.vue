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
  <div class="animation" @mouseover="handleHover">
    <lottie :options="defaultOptions" :width="Width" :height="Height" v-on:animCreated="handleAnimation"/>
  </div>
</template>
<script>
import lottie from 'vue-lottie'

export default {
  data () {
    return {
      defaultOptions: {},
      restart: false // 控制当前动画播放完成前，重复hover不会导致动画重复播放
    }
  },
  components: {
    lottie
  },
  props: {
    Width: {
      type: Number,
      default: 340
    },
    Height: {
      type: Number,
      default: 236
    },
    Options: {
      type: Object,
      default: null
    }
  },
  watch: {
    Options: {
      handler: function (val) {
        this.defaultOptions = JSON.parse(JSON.stringify(val))
      },
      deep: true
    }
  },
  methods: {
    handleAnimation (anim) {
      this.anim = anim
      this.anim.addEventListener('complete', () => {
        console.log('播放完成！')
        this.restart = true
      })
      this.play()
      // this.restart = true
    },
    stop () {
      this.anim.stop()
    },
    play () {
      this.anim.play()
    },
    pause () {
      this.anim.pause()
    },
    onSpeedChange (speed) {
      this.anim.setSpeed(speed)
    },
    handleHover () {
      if (!this.restart) return
      this.anim.goToAndPlay(1)
      this.restart = false
    }
  },
  created () {
    this.defaultOptions = JSON.parse(JSON.stringify(this.Options))
  },
  mounted () {
  }
}
</script>
<style lang="scss" scoped>
.animation {
  position: absolute;
}
</style>
