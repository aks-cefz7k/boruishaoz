<template>
  <div class="kiss-time-picker">
    <input id="datepicker"
           v-model="inputtext">
  </div>
</template>

<script>
import flatpickr from 'flatpickr'
// require('./timectrl.css')
// require('flatpickr/dist/themes/dark.css')
export default {
  name: 'kiss-date-picker',
  props: {
    enableTime: { // <是否使用时间
      type: Boolean,
      default: true
    },
    inputDateFormat: { // <时间格式化字符串
      type: String,
      default: 'Y-m-d H:i:S'
    },
    inputwidth: {
      type: String,
      default: '200'
    },
    inputheight: {
      type: String,
      default: '30'
    },
    inputtext: {
      type: String,
      default: ''
    }
  },
  mounted () {
    this.Init()
  },
  methods: {
    Init () {
      flatpickr('#datepicker', {
        enableTime: this.enableTime,
        dateFormat: this.inputDateFormat,
        nableTime: true,
        time_24hr: true,
        enableSeconds: true
      })
    }
  }
}
</script>
<style >
/* @import url(./timectrl.css); */
@import './timectrl.css';
.kiss-time-picker {
  width: 200px;
  height: 30px;
}
#datepicker {
  font-size: 14px;
  color: #42daff;
  background-color: transparent;
  border-width: 1px;
  border-color: #42daff;
  line-height: 30px;
}
</style>
