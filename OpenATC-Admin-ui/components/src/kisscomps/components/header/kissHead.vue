<template>
  <div class="headerStyle">
    <div class="left" :style="{'max-width':sideWidth + 'px'}">
      <div ref="leftPart">
        <slot name="left"></slot>
      </div>
    </div>
    <div class="title" v-text="title">
    </div>
    <div class="right" ref="rightPart" :style="{'max-width':sideWidth + 'px'}">
      <slot name="right"></slot>
    </div>
  </div>
</template>
<script>
export default {
  name: 'kiss-head',
  props: {
    title: {
      type: String,
      default: null
    },
    Width: {
      type: String,
      default: '1920'
    }
  },
  data () {
    return {
      centerWidth: '640'
    }
  },
  created () {
    if (this.Width) {
      this.sideWidth = (Number(this.Width) - Number(this.centerWidth)) / 2
    }
  },
  mounted () {
    if (this.$refs.leftPart.clientWidth === this.sideWidth) {
      console.log('头部左侧栏宽度过长，请调整')
    }
    if (this.$refs.rightPart.clientWidth === this.sideWidth) {
      console.log('头部右侧栏宽度过长，请调整')
    }
  }
}
</script>
<style scoped>
.headerStyle {
  width: 100%;
  min-height: 74px;
  background-image: url("../image/KissHead/headbg.png");
  background-position: center;
  background-repeat: no-repeat;
  position: relative;
  color: #42daff;
}
.title{
  position: absolute;
  left: 50%;
  top: 0;
  font-size: 40px;
  margin: 11px 0;
  transform: translateX(-50%);
  color: #ebfbff;
}
.left {
  height: 50px;
  float: left;
  margin-left: 18px;
  overflow: hidden;
  white-space: nowrap;
  max-height: 50px;
}
.right {
  height: 50px;
  float: right;
  margin-right: 18px;
  overflow: hidden;
  white-space: nowrap;
  max-height: 50px;
}
</style>
