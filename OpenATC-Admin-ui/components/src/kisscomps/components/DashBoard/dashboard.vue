<template>
  <div class="wrap" ref="body">
    <canvas :width="cw" :height="ch" :id="id" style="margin: auto; width: 100%;"></canvas>
  </div>
</template>

<script>
import DashBoard from './dashboard.js'
export default {
  name: 'DashBoard',
  data () {
    return {
      dashboard: Object,
      cw: 510,
      ch: 510
    }
  },
  props: {
    option: {
      type: Object,
      default: function () {
        return {}
      }
    },
    curPercent: {
      type: Number,
      default: 50
    },
    id: {
      type: String,
      default: 'dashboard'
    }
  },
  watch: {
    curPercent (newVal, oldVal) {
      this.dashboard.updatedPercent(this.curPercent)
    }
  },
  created () {
  },
  mounted () {
    // let screenWidth = window.screen.width
    // let screenHeight = window.screen.height
    // console.log(this.$refs.body.offsetHeight)
    // console.log(this.$refs.body.offsetWeight)
    // this.cw = parseInt(this.$refs.body.style.weight)
    // // console.log(this.$refs.body)
    // this.ch = parseInt(this.$refs.body.style.height)
    this.dashboard = new DashBoard(this.id, this.option)
    this.dashboard.render()
    // document.getElementById(this.id).width = 300
    // document.getElementById(this.id).height = 300
  }
}
</script>
<style scoped>
</style>
