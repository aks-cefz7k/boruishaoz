
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
  <div class="openatc-serviceroute">
    <el-container>
      <el-aside width="200px">
        <PlanMenu
          :planData="planData"
          :zIndexObj="zIndexObj"
          :loading="loading"
          @maskChange="maskChange"
          @chooseRoute="chooseRoute"
          @resetContentComponent="resetContentComponent"
          @refresh="getAllPlan"
        />
      </el-aside>
      <el-main>
        <PlanContent
          v-show="reset"
          :schemeData="schemeData"
          :step="step"
          :zIndexObj="zIndexObj"
          :chooseId="chooseId"
          @resetContentComponent="resetContentComponent"
        />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import PlanMenu from './planMenu'
import PlanContent from './planContent'
import { GetAllViproutes } from '@/api/service'
import '@/mock/index.js'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'Service',
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
  computed: {
    route () {
      let res = this.planData.filter(ele => ele.id === this.chooseId)[0]
      return res
    }
  },
  mounted () {
    this.getAllPlan()
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
      GetAllViproutes().then(res => {
        this.loading = false
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.planData = res.data.data
      })
    },
    chooseRoute (id) {
      this.chooseId = id
    }
  }
}
</script>
