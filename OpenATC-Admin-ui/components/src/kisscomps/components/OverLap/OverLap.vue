<template>
<div class="overLap" v-if="checked" :style="{'width':'852px','margin':this.controlData ? '20px 0 0 0' : '0 0 0 377px'}">
    <div class="ring-first" v-for="(list, index1) in this.overlapDatas" :key="index1">
        <div v-for="(item,index2) in list.stageLists" :key="index2" >
            <div class="first-2">
              <el-tooltip v-if="item.greenWidth" placement="top-start" effect="light">
                <div slot="content">
                    O{{list.id}}-
                    <span v-for="(ids,index3) in list.includedphases" :key="index3">
                        P{{ids}}<span v-if="index3+1!==list.includedphases.length">,</span>
                    </span>
                </div>
                <div style="cursor:pointer;">
                    <div class="ring-phase">
                        <PatternWalkSvg v-if="item.peddirection"  :Data="item.peddirection[0]" :Width="'32'" :Height="'34'" />
                        <xdrdirselector  Width="36px" Height="34px" :showlist="item.direction"></xdrdirselector>
                    </div>
                    <div class="box">
                        <div class="ring-nums">
                            O{{list.id}}-
                            <span v-for="(ids,index3) in list.includedphases" :key="index3">
                                P{{ids}}<span v-if="index3+1!==list.includedphases.length">,</span>
                            </span>
                        </div>
                    </div>
                </div>
            </el-tooltip>
            </div>
            <div class="first-1" :style="{'width':item.greenWidth,'height':'34px','background':'#7ccc66'}"></div>
            <div class="first-1" :style="{'width':item.redWidth,'height':'34px','background':'#f27979'}"></div>
        </div>
    </div>
</div>
</template>

<script>
import PhaseDataModel from '../IntersectionMap/crossDirection/utils.js'
import CrossDiagramMgr from '../../../EdgeMgr/controller/crossDiagramMgr.js'
import xdrdirselector from '../XRDDirSelector/XRDDirSelector'
import PatternWalkSvg from '../IntersectionMap/crossDirection/baseImg/PatternWalkSvg'
export default {
  name: 'over-lap',
  components: {
    PatternWalkSvg,
    xdrdirselector
  },
  data () {
    return {
      overlapDatas: []
    }
  },
  props: {
    overlap: {
      type: Array,
      default () {
        return []
      }
    },
    stageList: {
      type: Array
    },
    checked: {
      type: Boolean
    },
    cycle: {
      type: Number
    },
    controlData: {
      type: Object
    }
  },
  watch: {
    controlData: {
      handler: function (val, oldVal) {
        this.doAdd(this.stageList, this.overlap)
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
    this.PhaseDataModel = new PhaseDataModel()
    this.CrossDiagramMgr = new CrossDiagramMgr()
    this.doAdd(this.stageList, this.overlap)
  },
  methods: {
    getRoad (i) {
      let direction = []
      if (i) {
        i.map(item => {
          direction.push({
            id: item,
            color: '#454545'
          })
        })
      }
      return direction
    },
    getPedPhasePos (i) {
      // 行人相位信息
      let sidewalkPhaseData = []
      if (i) {
        i.forEach((dir, index) => {
          // 行人相位
          if (this.PhaseDataModel.getSidePos(dir)) {
            sidewalkPhaseData.push({
              key: this.CrossDiagramMgr.getUniqueKey('pedphase'),
              phaseid: i.id, // 相位id，用于对应相位状态
              id: dir,
              name: this.PhaseDataModel.getSidePos(dir).name
            })
          }
        })
      }
      return sidewalkPhaseData
    },
    doAdd (stageList, overlap) { // 阶段显示颜色
      let overlapData = overlap.map(item => {
        let findIndexs = []
        item.includedphases.forEach(i => {
          const findItems = stageList.filter(j => j.stages.includes(i))
          if (findItems.length) findIndexs.push(...findItems.map(m => m.key))
        })
        findIndexs = Array.from(new Set(findIndexs))
        return {
          ...item,
          stageLists: stageList.map(sta => {
            const find = findIndexs.includes(sta.key)
            return {
              ...sta,
              direction: this.getRoad(item.direction),
              peddirection: this.getPedPhasePos(item.peddirection),
              greenWidth: find ? (sta.split / this.cycle * 100).toFixed(3) + '%' : '0%',
              redWidth: !find ? (sta.split / this.cycle * 100).toFixed(3) + '%' : '0%'
            }
          })
        }
      })
      this.overlapDatas = overlapData
    }
  }
}
</script>

<style lang="scss" scoped>
.ring-first {
  margin-top: 1px;
  width: 100%;
  height: 34px;
  .first-1 {
   float: left;
// text-align: center;
  }
  .first-2{
   position: absolute;
   color: #333333;
  }
  .ring-nums {
    // float: left;
    // margin-top:7px;
    // margin-left: 5px;
    padding-top: 10px;
    padding-left: 43px;
    font-size: 12px;
    height: 14px;
    font-weight: normal;
    font-stretch: normal;
    // line-height: 22px;
    letter-spacing: 0px;
    color: #333333;//#33333361
  }
  .ring-phase {
    position: relative;
    float: left;
    // margin-left: 5px;
    height: 34px;
    z-index: 999;
    background: rgba(30, 96, 12, 0.07);
    // margin-top:1px;
  }
  .ring-phase::after {
    position: absolute;
    display: block;
    content: '';
    height: 0;
    width: 0;
    top: 11px;
    left: 36px;
    border: 5px solid transparent;
    border-left-color: rgba(30, 96, 12, 0.07);
  }
}
</style>
