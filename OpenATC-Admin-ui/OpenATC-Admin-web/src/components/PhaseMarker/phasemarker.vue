<template>
  <div class="wrap" style="width: 200px; height: 60px;"
                    v-if="isdestroy"
                    v-show="PhaseMarkerState.isShow">
    <phase v-if="showLevel === 1"></phase>
    <phaseCount v-if="showLevel === 2"></phaseCount>
    <phaseCountCycle v-if="showLevel === 3"></phaseCountCycle>
    <div class="dirwrap">
      <xdrdirselector
        Width="40px" Height="40px"
        :showlist="dirshow">
      </xdrdirselector>
    </div>
    <div class="operation-pattern" v-if="showLevel > 1">
        <div class="cutdown-num" :style="phaseCountdownColor">
            <span>{{params.countdown}}/{{params.split}}</span>
        </div>
        <div class="cutdown-name">
            <span>{{params.control}}</span>
        </div>
    </div>
    <div class="pattern-name" v-if="showLevel > 2">
        <div class="planname-top">
        <div class="cycle">
          <span style="margin-left: 6px;">{{params.cycle}}</span>
        </div>
        <div class="offset">
          <span style="margin-right: 6px;">{{params.offset}}</span>
        </div>
      </div>
      <div class="planname-bottom">
        <span>{{params.curpattern}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import xdrdirselector from '../XRDDirSelector'
import PatternSvg from './svg/patternSvg'
import PhaseSvg from './svg/phaseSvg'
import phase from './svg/phase'
import phaseCount from './svg/phaseCount'
import phaseCountCycle from './svg/phaseCountCycle'
export default {
  name: 'phasemarker',
  components: {
    xdrdirselector,
    PatternSvg,
    PhaseSvg,
    phaseCountCycle,
    phaseCount,
    phase
  },
  props: {
    dirshow: {
      type: Array,
      default: () => []
    },
    params: {
      type: Object,
      default: function () {
        return {
          countdown: 0,
          split: 0,
          control: 0,
          cycle: 0,
          offset: 0,
          curpattern: 'NO PLAN'
        }
      }
    },
    showLevel: {
      type: Number,
      default: 3
    }
  },
  data () {
    return {
      timer: 0,
      isdestroy: true,
      phaseCountdownColor: { // 控制倒计时的颜色与相位方向颜色一致
        color: '#333333'
      },
      PhaseMarkerState: {
        isShow: true
      }
    }
  },
  computed: {
  },
  methods: {
    show (dirshow, params, showLevel) {
      this.dirshow = dirshow
      this.params = params
      this.showLevel = showLevel
      this.phaseCountdownColor = {
        color: params.phaseCountdownColor
      }
      clearInterval(this.timer)
      let countdown = params.countdown
      if (countdown) {
        this.timer = setInterval(() => {
          params.countdown = params.countdown > 0 ? params.countdown - 1 : 0
        }, 1000)
      }
    }
  },
  mounted () {
  },
  created () {
  },
  destroyed () {
    clearInterval(this.timer)
  }
}
</script>

<style scoped>
.patternsvg {
  height: 60PX;
  position: relative;
}
.test {
  position: absolute;
  top: 10PX;
  z-index: 9;
  padding-left: 16PX;
}
.pattern-mess {
  float: left;
}
.dirwrap {
  position:absolute;
  top: 7PX;
  left: 7PX;
  width: 40PX;
  height: 39PX;
  z-index: 9;
}
.dirwrap-right {
  position:absolute;
  width: 140PX;
  height: 40PX;
  border-radius: 0PX 4PX 4PX 0PX;
  margin-left: 40PX;
}
.operation-pattern {
  position:absolute;
  width: 60PX;
  height: 39PX;
  top: 10PX;
  left: 60PX;
  z-index: 9;
}
.pattern-name {
  position:absolute;
  width: 79PX;
  height: 39PX;
  top: 10PX;
  left: 120PX;
  z-index: 9;
}
.center-line {
  position:absolute;
  top: 3PX;
  width: 1PX;
  height: 34PX;
  z-index: 1;
  background: #d3e0eb;
  margin-left: 60PX;
}
.cycle {
  position:absolute;
  width: 39.5PX;
  height: 18PX;
  font-size: 15PX;
  text-align: center;
  margin-right: 5PX;
  color: #666666;
}
.offset {
  position:absolute;
  width: 39.5PX;
  height: 18PX;
  font-size: 15PX;
  text-align: center;
  color: #666666;
  margin-left: 39.5PX;
}
.cutdown-num {
  width: 50PX;
  height: 19PX;
  margin-left: auto;
  margin-top: 2PX;
  margin-right: auto;
  font-size: 15PX;
  text-align: center;
}
.cutdown-name {
  width: 50PX;
  height: 19PX;
  margin-left: auto;
  margin-bottom: 2PX;
  margin-right: auto;
  font-size: 12PX;
  text-align: center;
  color: #333333;
}
.planname-top {
  height: 18PX;
  margin-left: auto;
  margin-right: auto;
  margin-top: 2PX;
  font-size: 15PX;
  text-align: center;
  color: #666666;
}
.planname-bottom {
  height: 18PX;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 2PX;
  font-size: 12PX;
  text-align: center;
  color: #333333;
}
.wrap span{
  -moz-user-select: -moz-none;
  -moz-user-select: none;
  -o-user-select:none;
  -khtml-user-select:none;
  -webkit-user-select:none;
  -ms-user-select:none;
  user-select:none;
}
</style>
