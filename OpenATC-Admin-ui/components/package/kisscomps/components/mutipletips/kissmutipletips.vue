<template>
   <div class="kissmutipletipscontainer">
      <button :class="bgclass" @click="handleCliked" :id="btnId" ref="btnbox"></button>
      <div ref="showbody" :class="divClass" :style="DiaglogStyleObj" v-if="isShow">
        <slot></slot>
      </div>
    </div>
</template>

<script>
import $ from 'jquery'
import './Tdrag.js'
export default {
  name: 'kiss-mutiple-tips',
  props: {
    btnType: {
      type: String,
      default: 'kiss-mutipletips-chart-button-bg'
    },
    size: {
      type: String,
      default: 'medium' // large / medium / small
    },
    btnId: {
      type: Number,
      required: true
    },
    defaultShow: {
      type: Boolean,
      default: false
    },
    isTrag: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      bgclass: this.btnType,
      divClass: 'kiss-mutipletip-dialog-bg-large',
      clickflag: true,
      DiaglogStyleObj: {
        'z-index': '100',
        'position': 'absolute',
        'left': '176px',
        'top': '353px'
        // 'display': 'none'
      },
      isShow: false
    }
  },
  methods: {
    handleCliked (event) {
      if (this.clickflag) {
        this.bgclass = 'kiss-mutipletips-select-button-bg'
        this.clickflag = false
        let target = event.currentTarget
        let seatX = target.offsetLeft + target.offsetWidth - 424 - 11
        let seatY = target.offsetTop - 9
        this.DiaglogStyleObj.left = seatX + 'px'
        this.DiaglogStyleObj.top = seatY + 'px'
        this.isShow = true
      } else {
        this.isShow = false
        this.clickflag = true
        this.bgclass = this.btnType
      }
    }
  },
  mounted () {
    this.isShow = this.defaultShow
    let target = this.$refs.btnbox
    target.style.zIndex = 1000
    let seatX = target.offsetLeft + target.offsetWidth - 424
    let seatY = target.offsetTop
    this.DiaglogStyleObj.left = seatX + 'px'
    this.DiaglogStyleObj.top = seatY + 'px'

    var self = this
    if (this.isTrag) {
      $(function () {
        $('.' + self.btnType).Tdrag({})
      })
    }
  }
}
</script>
<style>
.kissmutipletipscontainer{
    width: auto;
    height: auto;
    margin: 0px;
    padding: 0px;
}
.kiss-mutipletips-message-button-bg{
    background: url("../image/mutipletips/messageshowclose.png") no-repeat;
    width: 60px;
    height: 60px;
    outline: none;
    border: none;
}
.kiss-mutipletips-chart-button-bg{
    background: url("../image/mutipletips/chartshowclose.png") no-repeat;
    width: 60px;
    height: 60px;
    outline: none;
    border: none;
}
.kiss-mutipletips-select-button-bg{
  background: url("../image/mutipletips/min.png") no-repeat;
  width: 40px;
  height: 40px;
  outline: none;
  border: none;
}
.kiss-mutipletip-dialog-bg-large{
    background: url("../image/mutipletips/mutiplebglarge.png") no-repeat;
    width: 424px;
    height: 988px;
    animation: bounceInRight 1.3s ;
}

.kiss-mutipletip-dialog-bg-medium{
  background: url("../image/mutipletips/mutiplebgmid.png") no-repeat;
  width: 456px;
  height: 633px;
  animation: bounceInRight 1.3s ;
}

.kiss-mutipletip-dialog-bg-small{
  background: url("../image/mutipletips/mutiplebgmid.png") no-repeat;
  width: 424px;
  height: 988px;
  animation: bounceInRight 1.3s ;
}

.kiss-mutipletip-dialog-bg-remove{
  animation: bounceInDown 1.3s ;
}
@-webkit-keyframes bounceInDown {
    from,
    60%,
    75%,
    90%,
    to {
      -webkit-animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
      animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
    }

    0% {
      opacity: 0;
      -webkit-transform: translate3d(0, -100px, 0);
      transform: translate3d(0, -100px, 0);
    }
    60% {
      opacity: 1;
      -webkit-transform: translate3d(0, 25px, 0);
      transform: translate3d(0, 25px, 0);
    }

    75% {
      -webkit-transform: translate3d(0, -10px, 0);
      transform: translate3d(0, -10px, 0);
    }

    90% {
      -webkit-transform: translate3d(0, 5px, 0);
      transform: translate3d(0, 5px, 0);
    }

    to {
      -webkit-transform: translate3d(0, 0, 0);
      transform: translate3d(0, 0, 0);
    }
  }
  @keyframes bounceInRight {
    from,
    to {
      -webkit-animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
      animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
    }
    from {
      opacity: 0;
      -webkit-transform: translate3d(100px, 0, 0);
      transform: translate3d(100px, 0, 0);
    }
    to {
      -webkit-transform: translate3d(0, 0, 0);
      transform: translate3d(0, 0, 0);
    }
  }
</style>
