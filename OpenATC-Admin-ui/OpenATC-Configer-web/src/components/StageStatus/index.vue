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
        <div class="stage-line"></div>
        <div v-for="(item, index) in stageList" :key="index + '1'">
            <div class="stage-verticalline" :style="{'margin-left':item}"></div>
        </div>
    </div>
</template>
<script>

export default {
  name: 'stagestatus',
  components: {},
  data () {
    return {
      stageList: []
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
      if (this.patternStatusList.length === 0) return
      //   let stageLength = 0
      let firstPatternStatusList = this.patternStatusList[0]
      this.stageList.push(0)
      let stageLength = 0
      for (let i = 0; i < firstPatternStatusList.length; i++) {
      // for (let stage of this.patternStatusList[0]) {
        let tempLength = Number.parseFloat(firstPatternStatusList[i].greenWidth) + Number.parseFloat(firstPatternStatusList[i].redWidth) + Number.parseFloat(firstPatternStatusList[i].yellowWidth)
        stageLength = stageLength + tempLength
        this.stageList.push(stageLength + '%')
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
</style>
