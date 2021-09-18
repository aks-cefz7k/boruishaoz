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
          :timestamp="step.name"
          placement="top"
          icon="icon success"
          :type="getType(step.state, index)"
          size="large"
          @click.native="onStepClick(index)"
        >
          <div v-show="tabName === 'second'">
            <el-tag type="info" class="tag" v-show="step.state === 0"
              >待执勤</el-tag
            >
            <el-tag type="success" class="tag" v-show="step.state === 1"
              >执勤中</el-tag
            >
          </div>
          <div
            class="resttime"
            v-show="tabName === 'second' && step.state === 1"
          >
            {{ step.resttime }}
          </div>
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
      showIndex: -1
    }
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
    }
  }
}

</script>
<style scoped>
.block {
  width: 100%;
  height: 650px;
  margin: 50px;
  /* border: 1px solid red; */
}
.card-div {
  /* position: absolute; */
  padding-left: 80px;
  width: 650px;
}
.block >>> .el-scrollbar__wrap {
  overflow-x: hidden;
}

.block >>> .el-timeline-item__node--large {
  left: -8px;
  width: 28px;
  height: 28px;
}
/* .block >>> .el-timeline-item__icon {
  color: #409eff;
  font-size: 40px;
} */
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
