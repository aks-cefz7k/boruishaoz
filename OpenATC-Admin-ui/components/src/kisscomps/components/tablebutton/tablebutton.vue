<template>
  <div>
    <div v-for="(name, index) in tabList"
         :key="index"
         @click="onTabClick(index)"
         :class="index===curIndex ? 'tablebutton-click' : 'tablebutton'">
      <span :class="index===curIndex ? 'text-click' : 'text'"
            :style="{width: width}"> {{name}}</span>
    </div>
  </div>
</template>
<script>
export default {
  name: 'kiss-tablebutton',
  props: {
    tabList: {
      type: Array
    },
    width: {
      type: String,
      default: '90px'
    },
    defaultCheckedName: {
      type: String,
      default: undefined
    }
  },
  data () {
    return {
      curIndex: 0
    }
  },
  methods: {
    onTabClick (index) {
      if (this.curIndex === index) { return }
      this.$emit('leaveStates', this.curIndex)
      this.curIndex = index
      this.$emit('entryStates', this.curIndex)
    }
  },
  mounted () {
    if (!this.defaultCheckedName) return
    this.tabList.filter((item, index) => {
      if (item === this.defaultCheckedName) {
        this.onTabClick(index)
      }
    })
  }
}
</script>
<style scoped>
.text {
  display: block;
  font-size: 14px;
  color: #0096ba;
  height: 34px;
  text-align: center;
  line-height: 34px;
  -moz-transform: skewX(25deg);
  -ms-transform: skewX(25deg);
  -webkit-transform: skewX(25deg);
  transform: skewX(25deg);
}
.text-click {
  display: block;
  font-size: 14px;
  color: #ffffff;
  height: 34px;
  text-align: center;
  line-height: 34px;
  -moz-transform: skewX(25deg);
  -ms-transform: skewX(25deg);
  -webkit-transform: skewX(25deg);
  transform: skewX(25deg);
}
.tablebutton {
  background: rgba(22, 45, 51, 0.5);
  float: left;
  border: 1px solid rgba(10, 73, 88, 0.5);
  -moz-transform: skewX(-25deg);
  -ms-transform: skewX(-25deg);
  -webkit-transform: skewX(-25deg);
  transform: skewX(-25deg);
}
.tablebutton-click {
  background: #0585a5;
  border: 1px solid #42daff;
  float: left;
  -moz-transform: skewX(-25deg);
  -ms-transform: skewX(-25deg);
  -webkit-transform: skewX(-25deg);
  transform: skewX(-25deg);
}
.text:hover {
  color: #ffffff;
}
</style>
