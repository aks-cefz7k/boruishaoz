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
  <div class="app-container plan-container">
    <div class="tabs-style">
      <el-tabs v-model="curTabsValue" type="card" editable @edit="handleTabsEdit" :before-leave="beforeLeave">
        <el-tab-pane v-for="item in planList" :key="item.id" :label="item.desc" :name="String(item.id)">
          <tabPane :plan="item.plan" :planid="item.id" :planname="item.desc"/>
        </el-tab-pane>
        <el-tab-pane key="add" name="add" :closable="false">
          <span slot="label" style="padding: 8px;font-size:20px;font-weight:bold;">+</span>
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
      curTabsValue: '1',
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
        this.curTabsValue = this.isAddTab || this.isDeleteTab ? this.curTabsValue : String(planList[0].id)
        if (this.isAddTab) {
          this.isAddTab = !this.isAddTab
        }
        if (this.isDeleteTab) {
          this.isDeleteTab = !this.isDeleteTab
        }
      }
    },
    handleTabsEdit (targetName, action) {
      const planList = this.globalParamModel.getParamsByType('planList')
      if (action === 'add') {
        if (planList.length >= 16) {
          this.$message.error(this.$t('edge.plan.mostplandata'))
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
              if (String(tab.id) === targetName) {
                let nextTab = tabs[index + 1] || tabs[index - 1]
                if (nextTab) {
                  activeName = String(nextTab.id)
                }
              }
            })
          }
          this.curTabsValue = activeName
          this.isDeleteTab = true
          this.$store.getters.tscParam.planList = tabs.filter(tab => String(tab.id) !== targetName)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('edge.common.deletecancel')
          })
        })
      }
    },
    // GetUnUesdPlanNum () {
    //   var planKey = new Date()
    //   return Number(planKey.getFullYear().toString() + (planKey.getMonth() + 1).toString() + planKey.getHours().toString() + planKey.getMinutes() + planKey.getSeconds())
    // },
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
        cancelButtonText: this.$t('edge.plan.cancel'),
        inputValidator: (value) => {
          if (value === undefined || value === null || value.replace(/\s/g, '') === '') {
            // 计划名必填校验
            return this.$t('edge.plan.plannamerequired')
          }
          // 计划名不能重复校验
          let inputvalue = value.replace(/\s/g, '')
          const planList = this.globalParamModel.getParamsByType('planList')
          for (let obj of planList) {
            let curdesc = obj.desc
            if (curdesc) {
              curdesc = curdesc.replace(/\s/g, '')
            }
            if (curdesc === inputvalue) {
              return this.$t('edge.plan.plannamerepeated')
            }
          }
          return true
        }
      }).then(({ value }) => {
        let inputvalue = value.replace(/\s/g, '') // 去掉字符串空格
        let planItem = {}
        // this.tabIndex = this.GetUnUesdPlanNum()
        // planItem.index = String(this.tabIndex)
        planItem.id = this.getIdOfByte()
        planItem.desc = inputvalue
        planItem.plan = []
        // planList.push(planItem)
        this.globalParamModel.addParamsByType('planList', planItem)
        this.curTabsValue = String(planItem.id)
        this.isAddTab = true
      }).catch(() => {
        // this.$message({
        //   type: 'info',
        //   message: 'Input canceled'
        // })
      })
    },
    /* 活动标签切换时触发 */
    beforeLeave (currentName, oldName) {
      // 如果name是add，则什么都不触发
      if (currentName === 'add') {
        this.AddTab()
        return false
      }
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.tabs-style .el-icon-plus {
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
    display: none;
    // float: right;
    // border: 1px solid #409eff;
    // height: 30px;
    // width: 30px;
    // line-height: 18px;
    // margin: 12px 0 9px 10px;
    // border-radius: 3px;
    // text-align: center;
    // font-size: 12px;
    // color: #409eff;
    // cursor: pointer;
    // -webkit-transition: all .15s;
    // transition: all .15s;
}
#tab-add {
  .el-icon-close { // 隐藏增加tab的右上角删除图标
    display: none;
  }
}
</style>
