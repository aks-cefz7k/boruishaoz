<template>
<div class="faultPage openatc-faultrecord">
  <div class="tabButton">
    <el-button-group>
        <span v-for="(item, index) in chooseButtons"
          :key="index" v-on:click="()=>handleSwitchPage(item.key)"
          class="btn"
          :class="{'btnFocus': item.active === true}"
          :style="{'borderRadius': item.borderRadius}">{{item.value}}</span>
    </el-button-group>
  </div>
  <HistoryFault v-if="curpagekey === 'historicalfault'"/>
</div>
</template>

<script>
import HistoryFault from './historyFault'
export default {
  components: { HistoryFault },
  data () {
    return {
      curpagekey: 'historicalfault',
      chooseButtons: [
        {
          key: 'realtimefault',
          value: this.$t('openatc.faultrecord.realtimefault'),
          active: true,
          borderRadius: '4px 0 0 4px'
        },
        {
          key: 'historicalfault',
          value: this.$t('openatc.faultrecord.historicalfault'),
          active: false,
          borderRadius: '0px 4px 4px 0'
        }
      ]
    }
  },
  created () {
  },
  methods: {
    changeBtnStyle (key) {
      // 选中按钮高亮
      this.chooseButtons.forEach(button => {
        button.active = false
        if (button.key === key) {
          button.active = true
          this.curpagekey = key
        }
      })
    },
    handleSwitchPage (key) {
      this.changeBtnStyle(key)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
