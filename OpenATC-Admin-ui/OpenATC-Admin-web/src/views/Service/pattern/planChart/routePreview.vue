<template>
  <div class="block">
    <!-- <i class="iconfont icon-daizhiqin"></i>
    <i class="iconfont icon-bujin"></i> -->
    <el-scrollbar style="height: 100%">
      <el-timeline>
        <el-timeline-item
          v-for="(step, index) in route.devs"
          ref="timeline"
          :key="index"
          timestamp=""
          placement="top"
          icon="icon success"
          :type="getType(step.state, index)"
          size="large"
          @click.native="onStepClick(index)"
        >
          <div v-show="tabName === 'second'" style="border:0px solid red;margin-top:-20px;margin-left:30px;margin-top:-8px;">
            <div style="display:inline-block;margin-right:20px;">
              {{step.name }}
            </div>
            <div style="display:inline-block;margin-right:10px;">
              <template>
                <div>
                    <el-tag size="medium" effect="plain" :type="getTag(step).type">{{ getTag(step).label }}</el-tag>
                </div>
              </template>
            </div>
            <div style="display:inline-block;margin-right:10px;">
              {{ step.controlName }}
            </div>
            <span v-show="tabName === 'second' && step.state === 1">
              <div style="display:inline-block;margin-right:10px;">
                {{ '|' }}
              </div>
              <div style="display:inline-block;margin-right:10px;">
                <el-tag type="info" class="tag" v-show="step.state === 0">{{
                  $t("openatc.dutyroute.notonduty")
                }}</el-tag>
                <el-tag type="success" class="tag" v-show="step.state === 1">{{
                  $t("openatc.dutyroute.onduty")
                }}</el-tag>
              </div>
              <div style="display:inline-block;">
                {{ step.resttime }}
              </div>
            </span>

          </div>
          <!-- <div
            class="resttime"
            v-show="tabName === 'second' && step.state === 1"
          >
            {{ step.resttime }}
          </div> -->
          <div class="card-div">
            <route-card
              :node="step"
              :tabName="tabName"
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
import RouteCard from '@/views/Service/components/routeCard'
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
      default: 'first'
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
      // let isOn = false
      for (let dev of devs) {
        if (dev.state === 1) {
          let _this = this
          // if (_this.intervalId !== 0) { // 已有定时器
          //   return false
          // }
          // isOn = true
          // _this.intervalId = setInterval(function () {
          //   _this.$emit('research')
          // }, 2000)
          setTimeout(() => {
            _this.$emit('research')
          }, 1 * 1000)
          break
        }
      }
      // if (!isOn) {
      //   clearInterval(this.intervalId)
      //   this.intervalId = 0
      // }
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
  width: 100%;
  height: 80%;
  margin: 50px;
  /* border: 1px solid red; */
}
.card-div {
  /* position: absolute; */
  /* padding-left: 80px; */
  margin-left: 30px;
  margin-top: 10px;
  width: 45%;
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
