<template>
  <div :style="bgstyle">
    <div class="topleft"></div>
    <div class="topcenter"></div>
    <div class="topright"></div>
    <div class="leftcenter"></div>
    <div class=contents>
      <div class="head"
           v-if="tittle">
        <div class="tittle">
          {{this.tittle}}
        </div>
        <div class="dashed"></div>
      </div>
      <div class="body">
        <slot></slot>
      </div>
    </div>
    <div class="rightcenter"></div>
    <div class="bottomleft"></div>
    <div class=bottomcenter></div>
    <div class="bottomright"></div>
  </div>
</template>
<script>
export default {
  name: 'kiss-panel',
  props: {
    tittle: {
      type: String,
      default: null
    },
    width: {
      type: String,
      default: '523px'
    },
    height: {
      type: String,
      default: '364px'
    }
  },
  data () {
    return {
      bgstyle: {
        position: 'relative',
        background: 'rgb(22, 45, 51, 0.6)',
        width: '',
        height: ''
      }
    }
  },
  mounted () {
    this.bgstyle.height = this.height
    this.bgstyle.width = this.width
  }
}
</script>
<style scoped>
.topleft {
  position: absolute;
  top: 0;
  left: 0;
  width: 12px;
  height: 12px;
  background: url("../image/panel/lefticon.png");
  background-size: cover;
}
.topcenter {
  position: absolute;
  top: 0;
  left: 12px;
  right: 12px;
  border-top: 1px solid rgba(10, 73, 88, 0.6);
}
.topright {
  position: absolute;
  top: 0;
  right: 0;
  width: 12px;
  height: 12px;
  border-top: 1px solid #42daff;
  border-right: 1px solid #42daff;
}
.leftcenter {
  position: absolute;
  top: 12px;
  left: 0;
  bottom: 12px;
  border-left: 1px solid rgba(10, 73, 88, 0.6);
}
.rightcenter {
  position: absolute;
  top: 12px;
  right: 0;
  bottom: 12px;
  border-right: 1px solid rgba(10, 73, 88, 0.6);
}
.bottomleft {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 12px;
  height: 12px;
  border-bottom: 1px solid #42daff;
  border-left: 1px solid #42daff;
}
.bottomcenter {
  position: absolute;
  bottom: 0;
  left: 12px;
  right: 12px;
  border-bottom: 1px solid rgba(10, 73, 88, 0.6);
}
.bottomright {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 12px;
  height: 12px;
  border-bottom: 1px solid #42daff;
  border-right: 1px solid #42daff;
}
.contents {
  position: absolute;
  top: 12px;
  right: 12px;
  left: 12px;
  bottom: 12px;
}
.head {
  display: block;
}
.tittle {
  border-bottom: 1px solid #42daff;
  /* display: inline-block !important; */
  width: auto;
  float: left;
  font-size: 18px;
  color: #42daff;
  padding-bottom: 5px;
}
.dashed {
  border-bottom: 1px dashed #366069;
  overflow: hidden;
  height: 29px;
}
.body {
  padding-top: 5px;
  margin-top: 5px;
}
</style>
