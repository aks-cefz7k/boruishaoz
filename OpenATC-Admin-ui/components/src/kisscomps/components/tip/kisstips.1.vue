<template>
  <div :class="kisstipsbgclass" :value="value" v-if="visable" :style="bgstyle">
    <div class="kiss-tips-close" @click="handleClose" v-if="isClose" ref="close" style=""></div>
    <div class="kiss-tips-contents"><slot></slot></div>
  </div>
</template>
<script>
import $ from 'jquery'
import './Tdrag.js'
export default {
  name: 'kiss-tip',
  data () {
    return {
      visable: true,
      bgstyle: {
        width: '',
        height: ''
      },
      kisstipsbgclass: ''
    }
  },
  props: {
    value: {
      type: Boolean,
      default: true
    },
    isClose: {
      type: Boolean,
      default: true
    },
    width: {
      type: String,
      default: '523px'
    },
    height: {
      type: String,
      default: '364px'
    },
    type: {
      type: String,
      default: 'default' // alarm
    }
  },
  watch: {
    value: function (val) {
      this.visable = val
    },
    visable: function (val) {
      this.change(val)
    },
    deep: true,
    immediate: true
  },
  methods: {
    handleClose () {
      this.visable = false
    },
    change (val) {
      this.$emit('input', val)
    }
  },
  mounted () {
    this.visable = this.value
    switch (this.type) {
      case 'default':
        this.bgstyle.height = this.height
        this.bgstyle.width = this.width
        this.kisstipsbgclass = 'kiss-tips-bg'
        break
      case 'alarm':
        this.bgstyle.height = '99px'
        this.bgstyle.width = '276px'
        this.kisstipsbgclass = 'kiss-alarm-tips-bg'
        this.$refs.close.style.marginRight = '-1px'
        this.$refs.close.style.marginTop = '0.5px'
        break
      default: break
    }
    var self = this
    if (this.isTrag) {
      $(function () {
        $('.' + self.kisstipsbgclass).Tdrag({})
      })
    }
  },
  updated () {
    this.bgstyle.height = this.height
    this.bgstyle.width = this.width
    if (this.type === 'alarm') {
      if (this.$refs.close === undefined) {
        return false
      }
      this.$refs.close.style.marginRight = '-1px'
      this.$refs.close.style.marginTop = '0.5px'
    }
  }
}
</script>

<style scoped>
  .kiss-tips-bg{
    /* display: inline-block; */
    display: block;
    /* text-align: center; */
    position: relative;
    z-index: 100;
    margin: 0;
    padding: none;
    /* background: #1f2e2e;
    opacity: 0.8; */
    background: rgb(31, 46, 46, 0.8);
    border: 1px solid #0096ba;
    box-shadow: 0.1 0.1 0.1 0.1 #0096ba;
  }
  /* alarm 报警的弹框 */
  .kiss-alarm-tips-bg{
    /* display: inline-block; */
    display: block;
    /* text-align: center; */
    position: relative;
    z-index: 9999;
    margin: 0;
    padding: none;
    background: url("../image/tipdlg/alarmbg.png");
    background-repeat: no-repeat;
  }
/* .kips-tips-header{
    padding-top: 1px;
    display: block;
    width:522px;
    height: 50;
} */
.kiss-tips-close{
    width:47px;
    height: 47px;
    margin-right: -2px;
    margin-top: -1px;
    cursor:pointer;
    float: right;
    background: url("../image/tipdlg/close.png");
    background-repeat: no-repeat;
}

.kiss-tips-close:hover{
    background: url("../image/tipdlg/closehover.png");
    background-repeat: no-repeat;
}

.kiss-tips-contents{
    width: 100%;
    height: 100%;
}

</style>
