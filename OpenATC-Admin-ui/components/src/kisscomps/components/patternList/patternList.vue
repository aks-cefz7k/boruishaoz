<template>
  <div>
    <BoardCard
    :patternId="patternId"
    :contrloType="contrloType"
    :stagesChange="stagesChange"
    :patternStatusList="patternStatusList"
    :cycles="cycles"
    :cycle="cycle"
    :localPatternList="localPatternList"
    :phaseList="phaseList"
    :agentId="agentId"
    :controlData="controlData"
    :syncTime="syncTime"
    :isPhase="isPhase"
    >
    </BoardCard>
  </div>
</template>

<script>
import BoardCard from '../BoardCard/BoardCard.vue'
export default {
  name: 'pattern-list',
  components: {
    BoardCard
  },
  data () {
    return {
      // patternStatusList: [
      //   {
      //     'id': 1,
      //     'desc': '',
      //     'offset': 0,
      //     'cycle': 200,
      //     'rings': [
      //       [
      //         {
      //           'name': 'phase1',
      //           'id': 1,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         },
      //         {
      //           'name': 'phase2',
      //           'id': 2,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         },
      //         {
      //           'name': 'phase3',
      //           'id': 3,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         },
      //         {
      //           'name': 'phase4',
      //           'id': 4,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         }
      //       ],
      //       [
      //         {
      //           'name': 'phase5',
      //           'id': 5,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         },
      //         {
      //           'name': 'phase6',
      //           'id': 6,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         },
      //         {
      //           'name': 'phase7',
      //           'id': 7,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         },
      //         {
      //           'name': 'phase8',
      //           'id': 8,
      //           'value': 30,
      //           'mode': 2,
      //           'options': [
      //             0,
      //             0,
      //             0
      //           ],
      //           'minSplit': 13
      //         }
      //       ],
      //       [],
      //       []
      //     ],
      //     'barriers': [
      //       {
      //         'barrier': 1,
      //         'length': 60,
      //         'items': [
      //           {
      //             'ring': 2,
      //             'data': [
      //               5,
      //               6
      //             ]
      //           },
      //           {
      //             'ring': 1,
      //             'data': [
      //               1,
      //               2
      //             ]
      //           }
      //         ]
      //       },
      //       {
      //         'barrier': 2,
      //         'length': 60,
      //         'items': [
      //           {
      //             'ring': 2,
      //             'data': [
      //               7,
      //               8
      //             ]
      //           },
      //           {
      //             'ring': 1,
      //             'data': [
      //               3,
      //               4
      //             ]
      //           }
      //         ]
      //       }
      //     ],
      //     'stagesList': [
      //       {
      //         'key': 0,
      //         'split': 30,
      //         'stages': [
      //           1,
      //           5
      //         ],
      //         'green': 20,
      //         'yellow': 10,
      //         'red': 10,
      //         'phases': [
      //           1,
      //           5
      //         ],
      //         'stageSplit': 40
      //       },
      //       {
      //         'key': 1,
      //         'split': 30,
      //         'stages': [
      //           2,
      //           6
      //         ],
      //         'green': 20,
      //         'yellow': 10,
      //         'red': 10,
      //         'phases': [
      //           2,
      //           6
      //         ],
      //         'stageSplit': 40
      //       },
      //       {
      //         'key': 2,
      //         'split': 30,
      //         'stages': [
      //           3,
      //           7
      //         ],
      //         'green': 20,
      //         'yellow': 10,
      //         'red': 10,
      //         'phases': [
      //           3,
      //           7
      //         ],
      //         'stageSplit': 40
      //       },
      //       {
      //         'key': 3,
      //         'split': 30,
      //         'stages': [
      //           4,
      //           8
      //         ],
      //         'green': 20,
      //         'yellow': 10,
      //         'red': 10,
      //         'phases': [
      //           4,
      //           8
      //         ],
      //         'stageSplit': 40
      //       },
      //       {
      //         'key': 4,
      //         'green': 20,
      //         'yellow': 10,
      //         'red': 10,
      //         'phases': [
      //           6,
      //           4
      //         ],
      //         'stageSplit': 40
      //       }
      //     ],
      //     'stages': [
      //       [
      //         1,
      //         5
      //       ],
      //       [
      //         2,
      //         6
      //       ],
      //       [
      //         3,
      //         7
      //       ],
      //       [
      //         4,
      //         8
      //       ]
      //     ]
      //   }
      // ],
      // phaseList: [
      //   {
      //     'id': 1,
      //     'direction': [
      //       13
      //     ],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 1,
      //     'concurrent': [
      //       5,
      //       6
      //     ],
      //     'greenpulse': 10,
      //     'redpulse': 5,
      //     'controltype': 0,
      //     'peddirection': []
      //   },
      //   {
      //     'id': 2,
      //     'direction': [
      //       10,
      //       12
      //     ],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 1,
      //     'concurrent': [
      //       5,
      //       6
      //     ],
      //     'greenpulse': 10,
      //     'redpulse': 5,
      //     'controltype': 0,
      //     'peddirection': []
      //   },
      //   {
      //     'id': 3,
      //     'direction': [
      //       3
      //     ],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 1,
      //     'concurrent': [
      //       7,
      //       8
      //     ],
      //     'controltype': 2,
      //     'peddirection': [
      //       2,
      //       3,
      //       4
      //     ],
      //     'greenpulse': 10,
      //     'redpulse': 5
      //   },
      //   {
      //     'id': 4,
      //     'direction': [
      //       6,
      //       8
      //     ],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 1,
      //     'concurrent': [
      //       7,
      //       8
      //     ],
      //     'controltype': 0,
      //     'peddirection': [],
      //     'greenpulse': 10,
      //     'redpulse': 5
      //   },
      //   {
      //     'id': 5,
      //     'direction': [
      //       9
      //     ],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 2,
      //     'concurrent': [
      //       1,
      //       2
      //     ],
      //     'controltype': 0,
      //     'peddirection': [],
      //     'greenpulse': 10,
      //     'redpulse': 5
      //   },
      //   {
      //     'id': 6,
      //     'direction': [
      //       14,
      //       16
      //     ],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 2,
      //     'concurrent': [
      //       2,
      //       1
      //     ],
      //     'controltype': 0,
      //     'peddirection': [],
      //     'greenpulse': 10,
      //     'redpulse': 5
      //   },
      //   {
      //     'id': 7,
      //     'direction': [],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 2,
      //     'concurrent': [
      //       3,
      //       4
      //     ],
      //     'controltype': 2,
      //     'peddirection': [
      //       5,
      //       6
      //     ],
      //     'greenpulse': 10,
      //     'redpulse': 5
      //   },
      //   {
      //     'id': 8,
      //     'direction': [
      //       2,
      //       4
      //     ],
      //     'mingreen': 15,
      //     'max1': 65,
      //     'max2': 70,
      //     'passage': 3,
      //     'phasewalk': 0,
      //     'pedclear': 8,
      //     'yellow': 3,
      //     'redclear': 2,
      //     'flashgreen': 3,
      //     'redyellow': 0,
      //     'ring': 2,
      //     'concurrent': [
      //       4,
      //       3
      //     ],
      //     'controltype': 0,
      //     'peddirection': [],
      //     'greenpulse': 10,
      //     'redpulse': 5
      //   }
      // ],
      // cycles: 120
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
  },
  watch: {
    // $route: {
    //   handler: function (val, oldVal) {
    //     if (val.query !== undefined) {
    //       this.resetCrossDiagram()
    //     }
    //   },
    //   // 深度观察监听
    //   deep: true
    // },
    // Token: {
    //   handler: function (val) {
    //     this.setPropsToken(val)
    //   }
    // }
  },
  created () {
    // if (this.$route.query !== undefined && Object.keys(this.$route.query).length) {
    //   this.resetCrossDiagram()
    // }
  }
}
</script>

<style>

</style>
