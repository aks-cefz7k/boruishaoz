<template>
    <div class="boradCard">
        <StageStatus v-show="isPhase" :localPatternList="localPatternList" :contrloType="contrloType" :controlData="controlData" :patternStatusList="patternStatusList"></StageStatus>
        <PatternStatus :localPatternList="localPatternList" :contrloType="contrloType" :stagesChange="stagesChange" :agentId="agentId" :controlData="controlData" :phaseList="phaseList" :cycle="cycle" :syncTime="syncTime" :style="{'margin-top':cycle ? '25px':'0'}" :patternStatusList="patternStatusList" :patternId="patternId" :cycles="cycles"></PatternStatus>
    </div>
</template>

<script>
import PatternStatus from '@/components/PatternStatus'
import StageStatus from '@/components/StageStatus'
export default {
  components: {
    PatternStatus,
    StageStatus
  },
  data () {
    return {

    }
  },
  props: {
    contrloType: {
      type: Boolean
    },
    localPatternList: {
      type: Array
    },
    stagesChange: {
      type: Array
    },
    phaseList: {
      type: Array
    },
    controlData: {
      type: Object
    },
    patternStatusList: {
      type: Array
    },
    patternId: {
      type: Number
    },
    agentId: {
      type: String
    },
    isPhase: {
      type: Boolean,
      default: false
    },
    cycles: {
      type: Number
    },
    cycle: {
      type: Number
    },
    syncTime: {
      type: Number
    }
  }
}
</script>

<style>

</style>
