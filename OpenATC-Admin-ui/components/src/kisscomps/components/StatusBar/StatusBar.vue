<template>
  <div>
    <svg
      t="1545830627147"
      class="icon"
      style=""
      :viewBox="`${ViewBox.x} ${ViewBox.y} ${ViewBox.w} ${ViewBox.h}`"
      version="1.1"
      xmlns="http://www.w3.org/2000/svg" p-id="3189"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      :width="Width"
      :height="Height">
      <path d="M79.095172 436.788966c-15.889655 0-29.307586 10.24-34.251034 24.717241-0.706207 3.884138-1.05931 8.121379-1.05931 12.358621v2.824827c0 1.412414 0 2.824828 0.353103 4.237242 3.884138 15.889655 18.008276 27.895172 35.310345 27.895172h152.893793v-72.386207H79.095172z"
            :fill="LevelsInfo[4].color" p-id="3190"></path>
      <path d="M231.988966 436.788966H420.193103v72.386206H231.988966z"
            :fill="LevelsInfo[3].color" p-id="3191"></path>
      <path d="M420.193103 436.788966h188.204138v72.386206H420.193103z"
            :fill="LevelsInfo[2].color" p-id="3192"></path>
      <path d="M608.397241 436.788966h188.204138v72.386206h-188.204138z"
            :fill="LevelsInfo[1].color" p-id="3193"></path>
      <path d="M948.788966 436.788966h-152.187587v72.386206h152.187587c19.773793 0 36.016552-15.889655 36.016551-35.663448 0-1.412414 0-3.177931-0.353103-4.590345-2.118621-18.361379-17.302069-32.132414-35.663448-32.132413z"
            :fill="LevelsInfo[0].color" p-id="3194"></path>
      <path d="M139.475862 521.18069l-18.008276 31.426207h36.016552z"
            :fill="ArrowColor" p-id="3195"
            :class="CurLevelStatus === 0x0101 ? '' : 'invisible'"></path>
      <path d="M342.863448 521.18069l-18.008276 31.426207h36.016552z"
            :fill="ArrowColor" p-id="3196"
            :class="CurLevelStatus === 0x0100 ? '' : 'invisible'"></path>
      <path d="M532.48 521.18069l-18.008276 31.426207H550.841379z"
            :fill="ArrowColor" p-id="3197"
            :class="CurLevelStatus === 0x0011 ? '' : 'invisible'"></path>
      <path d="M722.449655 521.18069l-18.361379 31.426207h36.369655z"
            :fill="ArrowColor" p-id="3198"
            :class="CurLevelStatus === 0x0010 ? '' : 'invisible'"></path>
      <path d="M890.88 521.18069l-18.361379 31.426207h36.369655z"
            :fill="ArrowColor" p-id="3199"
            :class="CurLevelStatus === 0x0001 ? '' : 'invisible'"></path>
      <text text-anchor='middle' x = "512" y = "650" :fill="CurLevelColor"  style="font-size:50px;">{{CurLevelName}}</text>
    </svg>
  </div>
</template>
<script>
export default {
  name: 'SatausBar',
  props: {
    LevelsInfo: {
      type: Array,
      default: function () {
        return [
          {
            level: 1,
            levelname: '',
            color: '#EA423B'
          },
          {
            level: 2,
            levelname: '',
            color: '#FCA824'
          },
          {
            level: 3,
            levelname: '',
            color: '#F6D40A'
          },
          {
            level: 4,
            levelname: '',
            color: '#31C750'
          },
          {
            level: 5,
            levelname: '',
            color: '#00841C'
          }
        ]
      }
    },
    ViewBox: {
      type: Object,
      default: function () {
        return {
          x: 0,
          y: 0,
          w: 1024,
          h: 1024
        }
      }
    },
    ArrowColor: {
      type: String,
      default: 'yellow'
    },
    TotalLevel: {
      type: Number,
      default: 100
    },
    /**
     * 说明：等分模式： 只需要传入五个等级的总数，和每个等级的颜色
     *      自定义模式：不需要传入总数值，但是需要在LevelsInfo的对象中指出范围例如
     *                [{
     *                    level: 1,
     *                    levelname: '',
     *                    Upperlimit: Number,
     *                    Lowerlimit: Number,
     *                }]
     */
    Model: {
      type: String,
      default: 'Divide' // Custom
    },
    CurLevel: {
      type: Number,
      default: 0
    },
    Width: {
      type: String,
      default: '200px'
    },
    Height: {
      type: String,
      default: '200px'
    }
  },
  data () {
    return {
      CurLevelStatus: 0x0001,
      CurLevelName: 'status name',
      CurLevelColor: 'red'
    }
  },
  methods: {
    SetCurLevelInfo (level) {
      switch (level) {
        case 1:
          this.CurLevelStatus = 0x0001
          this.CurLevelName = this.LevelsInfo[0].levelname
          this.CurLevelColor = this.LevelsInfo[0].color
          break
        case 2:
          this.CurLevelStatus = 0x0010
          this.CurLevelName = this.LevelsInfo[1].levelname
          this.CurLevelColor = this.LevelsInfo[1].color
          break
        case 3:
          this.CurLevelStatus = 0x0011
          this.CurLevelName = this.LevelsInfo[2].levelname
          this.CurLevelColor = this.LevelsInfo[2].color
          break
        case 4:
          this.CurLevelStatus = 0x0100
          this.CurLevelName = this.LevelsInfo[3].levelname
          this.CurLevelColor = this.LevelsInfo[3].color
          break
        case 5:
          this.CurLevelStatus = 0x0101
          this.CurLevelName = this.LevelsInfo[4].levelname
          this.CurLevelColor = this.LevelsInfo[4].color
          break
        default: break
      }
    },
    handleDivideModel (cur) {
      if (this.TotalLevel === undefined) {
        throw new Error('The TotalLevel not set!')
      }
      let interval = this.TotalLevel / 5
      if (cur >= 0 && cur <= interval) {
        this.SetCurLevelInfo(1)
      } else if (interval < cur && cur <= 2 * interval) {
        this.SetCurLevelInfo(2)
      } else if (2 * interval < cur && cur <= 3 * interval) {
        this.SetCurLevelInfo(3)
      } else if (3 * interval < cur && cur < 4 * interval) {
        this.SetCurLevelInfo(4)
      } else {
        this.SetCurLevelInfo(5)
      }
    },
    handleCustomModel (cur) {
      for (let i = 0; i < 0; i++) {
        let Upperlimit = this.LevelsInfo[i].Upperlimit
        let Lowerlimit = this.LevelsInfo[i].Lowerlimit
        if (Upperlimit === undefined || Lowerlimit === undefined) {
          throw new Error(`Do you define level${this.LevelsInfo[i].level}'s Upperlimit or Lowerlimit`)
        }
        if (Lowerlimit < cur <= Upperlimit) {
          this.SetCurLevelInfo(i)
        }
      }
    }
  },
  watch: {
    CurLevel: {
      handler: function (cur) {
        if (this.Model === 'Divide') {
          this.handleDivideModel(cur)
        }
        if (this.Model === 'Custom') {
          this.handleCustomModel(cur)
        }
        // console.log(`the cur status: ${this.CurLevelStatus}`)
      }
    }
  },
  created () {
    // console.log(this.ViewBox)
    let cur = this.CurLevel
    if (this.Model === 'Divide') {
      this.handleDivideModel(cur)
    }
    if (this.Model === 'Custom') {
      this.handleCustomModel(cur)
    }
  }
}
</script>

<style scoped>
.invisible {
  visibility: hidden;
}
</style>
