<template>
    <div class="boradCard">
        <StageStatus v-show="isPhase" :patternStatusList="patternStatusList"></StageStatus>
        <PatternStatus :cycle="cycle" :syncTime="syncTime" :style="{'margin-top':cycle ? '25px':'0'}" :patternStatusList="patternStatusList" :patternId="patternId" :cycles="cycles"></PatternStatus>
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
    patternStatusList: {
      type: Array
    },
    patternId: {
      type: Number
    },
    isPhase: {
      type: Boolean
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
