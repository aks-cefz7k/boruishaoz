<template>
  <div class="block">
    <el-scrollbar style="height: 100%">
        <el-row
          v-for="(step, index) in route.devs"
          ref="timeline"
          :key="index">
      <!-- <el-timeline>
        <el-timeline-item
          v-for="(step, index) in route.devs"
          ref="timeline"
          :key="index"
          placement="top"
          icon="icon success"
          :type="getType(step.state, index)"
          size="large"
          @click.native="onStepClick(index)" > -->
          <div class="resttime"
               v-if="step.name != ''">
               <strong>
                 <i class="el-icon-location-outline" type="primary"></i>
                  {{ step.name }}
               </strong>
            <!-- <el-tag size="medium" effect="plain" style="float:right;" :type="getTag(step).type">{{ getTag(step).label }}</el-tag> -->
            <div style="float:right;">
              <div style="display:inline-block;margin-right:40px;" class="grid-content bg-purple">{{ $t("openatc.greenwaveoptimize.pattern") }}: {{ step.terminalname }}</div>
              <el-button  v-show="tabName === 'second'"
                          style="float: right;margin-bottom:5px;margin-top:-5px;"
                          :type="getButtonType(step)"
                          size="small"
                          @click="executeViproute(step)">
                <template v-if="!step.state || step.state === 0">
                  {{ $t("openatc.dutyroute.executen") }}
                </template>
                <template v-else>
                  {{ $t("openatc.dutyroute.cancel") }}
                </template>
              </el-button>
            </div>
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
        <!-- </el-timeline-item>
      </el-timeline> -->
      </el-row>
    </el-scrollbar>
  </div>
</template>

<script>
import RouteCard from './routeCard'
import { ExecuteViproute } from '@/api/service'
import { getMessageByCode } from '@/utils/responseMessage'
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
      this.changeCard(index)
      this.$emit('onCardClick', this.route.devs[index])
    },
    changeCard (index) {
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
        this.changeCard(index)
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
    },
    executeViproute (node) {
      let reqData = {
        'agentid': node.agentid,
        'viprouteid': node.viprouteid,
        'operation': 1
      }
      if (node.state === 1) {
        reqData.operation = 0
      }
      // this.isBtnDisabled = true
      ExecuteViproute(reqData).then(res => {
        // this.isBtnDisabled = false
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return false
        }
        this.$emit('research')
      })
    },
    getButtonType (node) {
      let res = 'warning'
      if (!node.state || node.state === 0) {
        res = 'primary'
      }
      return res
    }
  }
}

</script>
<style scoped>
.block {
  width: auto;
  height: 750px;
  /* margin: 0 -30px 0 -30px; */
  /* margin-left: -30px; */
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
  left: -4px;
  width: 20px;
  height: 20px;
}
.block >>> .el-timeline-item__tail {
  position: absolute;
  left: 4px;
  height: 100%;
  border-left: 5px solid #e4e7ed;
}
.resttime {
  padding: 5px;
  margin-top: 5px;
}
</style>
