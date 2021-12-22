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
  <div class="greenWaveOptimize">
    <el-container>
      <el-aside width="200px">
        <PlanMenu :planData="planData"
        :zIndexObj="zIndexObj"
        :loading="loading"
        @maskChange="maskChange"
        @chooseRoute="chooseRoute"
        @resetContentComponent="resetContentComponent"
        @refresh="getAllPlan"/>
      </el-aside>
      <el-main>
        <PlanContent v-if="reset"
        :schemeData="schemeData"
        :step="step"
        :zIndexObj="zIndexObj"
        :chooseId="chooseId"
        @resetContentComponent="resetContentComponent"/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import PlanMenu from './planMenu'
import PlanContent from './planContent'
import { GetSimpleRoute } from '@/api/route'
export default {
  name: 'GreenWaveOptimize',
  components: {
    PlanMenu,
    PlanContent
  },
  data () {
    return {
      planData: [],
      schemeData: [],
      step: false, // 控制第二个蒙层是否可以显示
      zIndexObj: {
        // 由于“新增”“编辑”均有蒙层，因此需动态改变可点击按钮层级
        addZIndex: 98,
        editZIndex: 98
      },
      reset: false,
      activeName: 'first',
      chooseId: 0, // 选中的计划id
      loading: false
    }
  },
  methods: {
    maskChange (val) {
      // 控制两个蒙层下按钮的层级关系，并判断第二个蒙层显示的时机
      if (val) {
        this.step = false
        this.zIndexObj = {
          addZIndex: 99,
          editZIndex: 96
        }
      } else {
        this.step = true
        this.zIndexObj = {
          addZIndex: 96,
          editZIndex: 99
        }
      }
    },
    resetContentComponent () {
      // 每次切换侧边栏方案时，重置销毁planContent组件
      this.reset = false
      this.$nextTick(() => {
        this.reset = true
      })
    },
    getAllPlan () {
      this.loading = true
      GetSimpleRoute().then(res => {
        this.loading = false
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.planData = res.data.data
      })
    },
    chooseRoute (id) {
      this.chooseId = id
    }
  },
  created () {
  },
  mounted () {
    this.getAllPlan()
  }
}
</script>

<style lang="scss" scoped>
.greenWaveOptimize {
  height: calc(100vh - 50px);
}
.el-aside {
    height: calc(100vh - 50px);
    color: $--color-text-primary;
    border-right: solid 1px $--border-color-lighter;
}

.el-main {
  height: calc(100vh - 50px);
}
</style>
