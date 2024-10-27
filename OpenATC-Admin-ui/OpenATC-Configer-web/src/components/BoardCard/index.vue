<template>
    <div class="boradCard">
        <StageStatus :patternStatusList="patternStatusList"></StageStatus>
        <PatternStatus :cycle="cycle" :syncTime="syncTime"  :patternStatusList="patternStatusList" :cycles="cycles" style="margin-top: 25px;"></PatternStatus>
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
    // barrierList: {
    //   type: Array
    // },
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
