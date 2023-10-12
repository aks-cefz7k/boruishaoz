<template>
  <div class="block">
    <el-scrollbar style="height: 100%">
      <el-timeline>
        <el-timeline-item
          v-for="(step, index) in route.devs"
          ref="timeline"
          :key="index"
          :timestamp="step.name"
          placement="top"
          icon="icon success"
          :type="getType(step.state, index)"
          size="large"
          @click.native="onStepClick(index)" >
          <div class="resttime"
               v-if="step.name != ''">
               <i class="el-icon-location-outline" type="primary"></i>
            {{ $t("openatc.gis.crossRoad") }}{{ step.agentid }}
            <el-tag size="medium" effect="plain" style="float:right;" :type="getTag(step).type">{{ getTag(step).label }}</el-tag>
          </div>
          <div>
          </div>
          <!-- <div class="resttime"
               v-show="tabName === 'second' && step.state === 1">
            {{ step.resttime }}
          </div> -->
          <div class="card-div">
            <route-card
              :node="step"
              :tabName="tabName"
              :isShowVideo="route.isShowVideo"
              ref="routeCard"
              @research="research"
            ></route-card>
          </div>
        </el-timeline-item>
      </el-timeline>
    </el-scrollbar>
  </div>
</template>

<script>
import RouteCard from './routeCard'
export default {
  name: 'routeState',
  components: {
    RouteCard
  },
  props: {
    route: {
      type: Object
    },
    tabName: {
      type: String,
      default: 'second'
    }
  },
  data () {
    return {
      activeIndex: 0,
      isPopoverShow: true,
      showIndex: -1,
      intervalId: 0
    }
  },
  watch: {
    route (val) {
      if (!val) { return false }
      let devs = val.devs
      if (!devs || devs.length === 0) {
        return false
      }
      let isOn = false
      for (let dev of devs) {
        if (dev.state === 1) {
          let _this = this
          if (_this.intervalId !== 0) { // 已有定时器
            return false
          }
          isOn = true
          _this.intervalId = setInterval(function () {
            _this.$emit('research')
          }, 2000)
        }
      }
      if (!isOn) {
        clearInterval(this.intervalId)
        this.intervalId = 0
      }
    },
    deep: true
  },
  methods: {
    getType (state, index) {
      let res = 'primary'
      if (this.showIndex === index) {
        res = 'warning'
      }
      if (state === 1) {
        res = 'success'
      }
      return res
    },
    onStepClick (index) {
      this.activeIndex = index
      this.$refs.routeCard[index].show()
      this.showIndex = index
    },
    setCurrentRow (dev) {
      let index = -1
      for (let i = 0; i < this.route.devs.length; i++) {
        let d = this.route.devs[i]
        if (d.agentid === dev.agentid) {
          index = i
          break
        }
      }
      if (index >= 0) {
        // let stepDom = this.$refs.timeline[index]
        // stepDom.click()
        this.onStepClick(index)
      }
    },
    research () {
      this.$emit('research')
    },
    getTag (row) {
      if (row.stateName === 'DOWN') {
        return {
          label: this.$t('openatc.devicemanager.offline'),
          type: 'info'
        }
      } else if (row.stateName === 'UP') {
        return {
          label: this.$t('openatc.devicemanager.online'),
          type: 'success'
        }
      } else {
        return {
          label: this.$t('openatc.devicemanager.fault'),
          type: 'danger'
        }
      }
    }
  }
}

</script>
<style scoped>
.block {
  width: auto;
  height: 750px;
  /* margin: 0 -30px 0 -30px; */
  margin-left: -30px;
  /* border: 1px solid red; */
}
.card-div {
  overflow: hidden;
  padding-left: 0px;
  width: 100%;
}
.block >>> .el-scrollbar__wrap {
  overflow-x: hidden;
}

.block >>> .el-timeline-item__node--large {
  left: -8px;
  width: 28px;
  height: 28px;
}
.block >>> .el-timeline-item__tail {
  position: absolute;
  left: 4px;
  height: 100%;
  border-left: 5px solid #e4e7ed;
}
.resttime {
  padding: 5px;
}
</style>
