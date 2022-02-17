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
  <!-- <div class="ys-float-btn" :style="{'width':itemWidth+'px','height':itemHeight+'px','left':left+'px','top':top+'px'}" -->
  <div class="ys-float-btn" :style="{'width':itemWidth+'px','height':itemHeight+'px','right':'30px','bottom':'30px'}"
       ref="div"
       @click ="onBtnClicked">
    <slot name="icon"></slot>
    <p>{{text}}</p>
  </div>
</template>
<script>
export default {
  name: 'FloatImgBtn',
  props: {
    text: {
      type: String,
      default: ''
    },
    itemWidth: {
      type: Number,
      default: 60
    },
    itemHeight: {
      type: Number,
      default: 60
    },
    gapWidth: {
      type: Number,
      default: 80
    },
    coefficientHeight: {
      type: Number,
      default: 0.85
    }
  },
  created () {
    this.clientWidth = document.documentElement.clientWidth
    this.clientHeight = document.documentElement.clientHeight
    this.left = this.clientWidth - this.itemWidth - this.gapWidth
    this.top = this.clientHeight * this.coefficientHeight
  },
  mounted () {
    var _this = this
    window.addEventListener('scroll', _this.handleScrollStart)
    _this.$nextTick(() => {
      const div = _this.$refs.div
      div.addEventListener('touchstart', () => {
        div.style.transition = 'none'
      })
      div.addEventListener('touchmove', (e) => {
        if (e.targetTouches.length === 1) {
          let touch = event.targetTouches[0]
          _this.left = touch.clientX - _this.itemWidth / 2
          _this.top = touch.clientY - _this.itemHeight / 2
        }
      })
      div.addEventListener('touchend', () => {
        div.style.transition = 'all 0.3s'
        if (_this.left > _this.clientWidth / 2) {
          _this.left = _this.clientWidth - _this.itemWidth - _this.gapWidth
        } else {
          _this.left = _this.gapWidth
        }
      })
      window.onresize = function () {
        // 定义窗口大小变更通知事件
        _this.screenHeight = window.innerHeight // 窗口高度
      }
    })
  },
  watch: {
    screenHeight: function () {
      // 监听屏幕高度变化
      this.clientWidth = document.documentElement.clientWidth
      this.clientHeight = document.documentElement.clientHeight
      this.left = this.clientWidth - this.itemWidth - this.gapWidth
      this.top = this.clientHeight * this.coefficientHeight
    }
  },
  beforeDestroy () {
    window.removeEventListener('scroll', this.handleScrollStart)
  },
  methods: {
    onBtnClicked () {
      this.$emit('onFloatBtnClicked')
    },
    handleScrollStart () {
      this.timer && clearTimeout(this.timer)
      this.timer = setTimeout(() => {
        this.handleScrollEnd()
      }, 300)
      this.currentTop = document.documentElement.scrollTop || document.body.scrollTop
      if (this.left > this.clientWidth / 2) {
        this.left = this.clientWidth - this.itemWidth / 2
      } else {
        this.left = -this.itemWidth / 2
      }
    },
    handleScrollEnd () {
      let scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      if (scrollTop === this.currentTop) {
        if (this.left > this.clientWidth / 2) {
          this.left = this.clientWidth - this.itemWidth - this.gapWidth
        } else {
          this.left = this.gapWidth
        }
        clearTimeout(this.timer)
      }
    }
  },
  data () {
    return {
      timer: null,
      currentTop: 0,
      clientWidth: 0,
      clientHeight: 0,
      left: 0,
      top: 0,
      screenHeight: window.innerHeight // 屏幕高度
    }
  }
}
</script>
<style lang="scss" scoped>
  // .ys-float-btn {
  //   background:#20a0ff;
  //   box-shadow:0 2px 10px 0 rgba(0,0,0,0.1);
  //   border-radius:50%;
  //   color: #666666;
  //   z-index: 20;
  //   transition: all 0.3s;
  //   display: flex;
  //   flex-direction: column;
  //   justify-content: center;
  //   align-items: center;
  //   position: fixed;
  //   bottom: 20vw;
  //   img{
  //     width: 50%;
  //     height: 50%;
  //     object-fit: contain;
  //     margin-bottom: 3px;
  //   }
  //   p{
  //     font-size:7px;
  //   }
  // }
</style>
