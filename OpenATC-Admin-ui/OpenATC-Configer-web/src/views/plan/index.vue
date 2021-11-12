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
  <div class="app-container">
    <div class="tabs-style">
      <el-tabs v-model="curTabsValue" type="card" editable @edit="handleTabsEdit">
        <el-tab-pane v-for="item in planList" :key="item.index" :label="item.desc" :name="item.index">
          <tabPane :plan="item.plan" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import tabPane from './tabPane'
import { mapState } from 'vuex'
export default {
  name: 'plan',
  components: { tabPane },
  data () {
    return {
      curTabsValue: '0',
      tabIndex: 0
    }
  },
  computed: {
    ...mapState({
      planList: state => state.globalParam.tscParam.planList
    })
  },
  watch: {
    planList: function () {
      this.init()
    }
  },
  created () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.init()
  },
  methods: {
    init () {
      const planList = this.globalParamModel.getParamsByType('planList')
      if (planList.length > 0) {
        // 保证增加tab页后，当前页展示新加的tab内容
        this.curTabsValue = this.isAddTab ? String(this.tabIndex) : planList[0].index
        if (this.isAddTab) {
          this.isAddTab = !this.isAddTab
        }
      }
    },
    handleTabsEdit (targetName, action) {
      const planList = this.globalParamModel.getParamsByType('planList')
      if (action === 'add') {
        if (planList.length >= 16) {
          this.$message.error(
            'There are at most 16 data !'
          )
          return
        }
        this.AddTab()
      }
      if (action === 'remove') {
        let tabs = JSON.parse(JSON.stringify(planList))
        let activeName = this.curTabsValue
        this.$confirm(this.$t('edge.plan.tabtip'),
          this.$t('edge.common.alarm'), {
            confirmButtonText: this.$t('edge.common.confirm'),
            cancelButtonText: this.$t('edge.common.cancel'),
            type: 'warning'
          }).then(() => {
          if (activeName === targetName) {
            tabs.forEach((tab, index) => {
              if (tab.desc === targetName) {
                let nextTab = tabs[index + 1] || tabs[index - 1]
                if (nextTab) {
                  activeName = nextTab.index
                }
              }
            })
          }
          this.curTabsValue = activeName
          this.$store.getters.tscParam.planList = tabs.filter(tab => tab.index !== targetName)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('edge.common.deletecancel')
          })
        })
      }
    },
    GetUnUesdPlanNum () {
      var planKey = new Date()
      return Number(planKey.getFullYear().toString() + (planKey.getMonth() + 1).toString() + planKey.getHours().toString() + planKey.getMinutes() + planKey.getSeconds())
    },
    getIdOfByte () { // 获取id的规则为，范围在1-255之间，且是与其他id不能重复的最小的值。
      let planList = this.globalParamModel.getParamsByType('planList')
      if (planList.length === 0) {
        return 1
      } else {
        let list = []
        for (let plan of planList) {
          list.push(plan.id)
        }
        for (let i = 1; i < 255; i++) {
          if (!list.includes(i)) {
            return i
          }
        }
      }
    },
    AddTab () {
      this.$prompt(this.$t('edge.plan.tipcontext'), this.$t('edge.plan.tip'), {
        confirmButtonText: this.$t('edge.plan.ok'),
        cancelButtonText: this.$t('edge.plan.cancel')
        // inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        // inputErrorMessage: '邮箱格式不正确'
      }).then(({ value }) => {
        const planList = this.globalParamModel.getParamsByType('planList')
        for (let obj of planList) {
          if (obj.desc === value) {
            this.$message.error("This plan_name can't be repeated")
            return
          }
        }
        let planItem = {}
        this.tabIndex = this.GetUnUesdPlanNum()
        planItem.index = String(this.tabIndex)
        planItem.id = this.getIdOfByte()
        planItem.desc = value
        planItem.plan = []
        // planList.push(planItem)
        this.globalParamModel.addParamsByType('planList', planItem)
        this.curTabsValue = String(this.tabIndex)
        this.isAddTab = true
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Input canceled'
        })
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.tabs-style .el-icon-plus {
    font-family: element-icons!important;
    speak: none;
    font-style: normal;
    font-weight: 400;
    font-size: 28px;
    font-variant: normal;
    text-transform: none;
    line-height: 1;
    vertical-align: baseline;
    display: inline-block;
    -webkit-font-smoothing: antialiased;
}
.tabs-style .el-tabs__new-tab {
    float: right;
    border: 1px solid #409eff;
    height: 30px;
    width: 30px;
    line-height: 18px;
    margin: 12px 0 9px 10px;
    border-radius: 3px;
    text-align: center;
    font-size: 12px;
    color: #409eff;
    cursor: pointer;
    -webkit-transition: all .15s;
    transition: all .15s;
}
</style>
