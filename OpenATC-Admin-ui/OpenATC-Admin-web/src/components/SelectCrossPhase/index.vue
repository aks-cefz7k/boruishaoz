<template>
  <el-select
    v-model="value"
    :multiple="multiple"
    :disabled="disabled"
    collapse-tags
    clearable
    filterable
    :placeholder="$t('openatc.bottleneckcontrol.phaseid')"
    @change="onChange"
  >
    <el-option
      v-for="(item, index) in Options"
      :key="index"
      :label="item.label"
      :value="item.value"
    >
      <div class="single-model" @click="selectStages(item.value)" >
        <xdrdirselector Width="40px" Height="40px" :showlist="item.item"></xdrdirselector>
        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.label }}</span>
      </div>
    </el-option>
  </el-select>
</template>
<script>
import { getTscControl } from '@/api/control'
import { uploadSingleTscParam } from '@/api/param'
import { getMessageByCode } from '@/utils/responseMessage'
import xdrdirselector from '@/components/XRDDirSelector'
import { getDirName } from './utils.js'
export default {
  name: 'SelectCrossPhase',
  components: {
    xdrdirselector
  },
  props: {
    multiple: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    agentid: {
      type: String,
      default: '10501'
    }
  },
  data () {
    return {
      list: [],
      value: [],
      Options: {
      }
    }
  },
  watch: {
    agentid (val) {
      this.value = ''
      this.Options = []
      this.onLoad()
    }
  },
  created () {
    this.onLoad()
  },
  methods: {
    onLoad () {
      this.getPhase()
      this.initData()
    },
    setOption () {
      this.Options = this.list.map((item, index) => {
        let nameArr = getDirName(item)
        let name = ''
        nameArr = nameArr.map(record => this.$t(record))
        name = nameArr.join(',')
        return {
          label: name,
          item: item,
          value: index
        }
      })
    },
    onChange (value) {
      let item = this.Options[value]
      this.$emit('onChange', item)
    },
    selectStages (value) {
      let item = this.Options[value]
      this.$emit('selectStages', item)
    },
    getPhase () {
      let type = 'phase'
      let agentid = this.agentid
      uploadSingleTscParam(type, agentid).then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        this.phaseList = res.data.data.phaseList
      })
    },
    initData () {
      let agentId = this.agentid
      getTscControl(agentId).then((data) => {
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.devStatus = 2
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let TscData = JSON.parse(JSON.stringify(data.data.data.data))
        this.handleStageData(TscData) // 处理阶段（驻留）stage数据
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    handleStageData (data) {
      this.stagesList = []
      let stages = data.stages
      for (let stage of stages) {
        let tempList = []
        let directionList = []
        for (let stg of stage) {
          let currPhase = this.phaseList.filter((item) => {
            return item.id === stg
          })[0]
          if (currPhase !== undefined) {
            directionList = [...currPhase.direction, ...directionList]
          }
          // directionList = [...currPhase.direction, ...directionList]
        }
        directionList = [...new Set(directionList)]
        tempList = directionList.map(dir => ({
          id: dir,
          color: '#606266'
        }))
        this.stagesList.push(tempList)
      }
      this.list = this.stagesList
      this.setOption()
    },
    init (val) {
      let target = val
      if (this.multiple) {
        target = [val]
      }
      this.value = target
    },
    getNameById (id) {
      let res = ''
      for (const item of this.Options) {
        if (id === item.agentid) {
          res = item.name
          break
        }
      }
      return res
    }
  }
}
</script>
<style scoped>

</style>
