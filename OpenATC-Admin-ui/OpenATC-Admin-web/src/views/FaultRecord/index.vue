<template>
<div class="faultPage openatc-faultrecord">
  <div class="tabButton" id="tabButton" :style="{'top': btnTop + 'px'}">
    <el-button-group>
        <span v-for="(item, index) in chooseButtons"
          :key="index" v-on:click="()=>handleSwitchPage(item.key)"
          class="btn"
          :class="{'btnFocus': item.active === true}"
          :style="{'borderRadius': item.borderRadius}">{{item.value}}</span>
    </el-button-group>
  </div>
  <CurrentFault v-if="curpagekey === 'realtimefault'" @changeBtnPosition="changeBtnPosition"/>
  <HistoryFault v-if="curpagekey === 'historicalfault'" @changeBtnPosition="changeBtnPosition"/>
</div>
</template>

<script>
import HistoryFault from './historyFault'
import CurrentFault from './realtimeFault'
export default {
  components: { HistoryFault, CurrentFault },
  data () {
    return {
      curpagekey: 'realtimefault',
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
      ],
      btnTop: 94
    }
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
      if (this.$route.params.filter !== undefined && key === 'realtimefault') {
        // 再次点击实时故障，需要显示全部，清除已有的路由过滤参数。由于路由没变化，只能刷新
        this.$router.replace('/refresh')
      }
      this.changeBtnStyle(key)
    },
    changeBtnPosition (offsetTop) {
      // 按钮位置不能通过给父元素设置relative用定位实现，定位会让刷新时表格获取的offsetTop值不准，因此计算的自适应高度也就不准。
      this.btnTop = offsetTop + 20
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
