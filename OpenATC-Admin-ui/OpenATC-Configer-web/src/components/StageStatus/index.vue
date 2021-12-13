/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
<template>
    <div class="main-statgestatus">
        <div class="stage-line" v-show="stageLineStatus"></div>
        <div v-for="(item, index) in stageList" :key="index + '1'">
          <div class="stage-verticalline" :style="{'margin-left':item}"></div>
        </div>
        <div v-for="(item, index) in stageStatusList" :key="index + '2'">
          <div class="stage-text" :style="{'left':item}"><span class="stage-span">S{{index + 1}}</span></div>
        </div>
    </div>
</template>
<script>

export default {
  name: 'stagestatus',
  components: {},
  data () {
    return {
      stageList: [],
      stageStatusList: [],
      stageLineStatus: false
    }
  },
  props: {
    patternStatusList: {
      type: Array
    }
  },
  watch: {
    patternStatusList: {
      handler: function (val, oldVal) {
        this.handleStages() // 计算屏障高度
      },
      // 深度观察监听
      deep: true
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    handleStages () {
      this.stageList = []
      this.stageStatusList = []
      if (this.patternStatusList.length === 0) {
        this.stageLineStatus = false
        return
      }
      this.stageLineStatus = true
      let firstPatternStatusList = this.patternStatusList[0]
      this.stageList.push(0)
      let stageLength = 0
      let stageStatusLength = 0
      for (let i = 0; i < firstPatternStatusList.length; i++) {
        let tempLength = Number.parseFloat(firstPatternStatusList[i].greenWidth) + Number.parseFloat(firstPatternStatusList[i].redWidth) + Number.parseFloat(firstPatternStatusList[i].yellowWidth)
        stageStatusLength = stageLength + tempLength / 2
        stageLength = stageLength + tempLength
        this.stageList.push(stageLength + '%')
        this.stageStatusList.push(stageStatusLength + '%')
      }
    }
  },
  computed: {
  }
}
</script>
<style lang="scss" scoped>
.main-statgestatus {
  position: relative;
}
.stage-line {
  position: relative;
  top: 7px;
  width: 100%;
  height: 1px;
  background-color: #999999;
  z-index:1;
}
.stage-verticalline {
  position: absolute;
  float: left;
  width: 1px;
  height: 14px;
  background-color: #999999;
  z-index:2;
}
.stage-text {
  position: absolute;
  float: left;
  width: 25px;
  background-color: #ffffff;
  z-index:3;
}
.stage-span {
  position: relative;
  bottom: 3px;
  left: 5px;
  width: 15px;
  height: 14px;
  font-family: SourceHanSansCN-Regular;
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 20px;
  letter-spacing: 0px;
  color: #333333;
}
</style>
