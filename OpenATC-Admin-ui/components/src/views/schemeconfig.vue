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
  <div>
    <el-button type="primary" @click="handleOpenConfigPanel" style="margin: 20px;">打开控制面板</el-button>
    <el-dialog
      refs="intersectionMapDialog"
      class="abow_dialog"
      :width="dialogWidth"
      :visible.sync="boxVisible"
      :close-on-click-modal="false"
      @close="oncancle"
      append-to-body>
      <!-- <scheme-config
        ref="rightpanel"
        agentId="10001-928"
        :statusData="crossStatusData"
        :realtimeStatusModalvisible="false" /> -->
      <scheme-config
        ref="rightpanel"
        :agentId="agentId"
        :lockPhaseBtnName="lockPhaseBtnName"
        :statusData="crossStatusData"
        roadDirection="left"
        funcSort="basicFunc" />
    </el-dialog>
  </div>
</template>
<script>
import { getTscControl } from '../api/control.js'
import { getMessageByCode } from '../utils/responseMessage.js'
import {
  getIframdevid, setToken
} from '../utils/auth'
export default {
  name: 'democonfig',
  data () {
    return {
      lockPhaseBtnName: this.$t('openatccomponents.overview.comfirm'),
      agentId: '10002-994',
      Token: 'eyJraWQiOiIxNjQ5MzM4NzA5MTA0IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczNTczODcwOSwiaWF0IjoxNjQ5MzM4NzA5fQ.JDSkpT1SbB61dEKvorhVdZJHKJzoQZEY5DPKHs1Imo0',
      Token103: 'eyJraWQiOiIxNjQ5MzM4NzA5MTA0IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczNTczODcwOSwiaWF0IjoxNjQ5MzM4NzA5fQ.JDSkpT1SbB61dEKvorhVdZJHKJzoQZEY5DPKHs1Imo0',
      boxVisible: false,
      dialogWidth: '80%',
      crossStatusData: {} // 路口状态数据
    }
  },
  methods: {
    oncancle () {
      this.boxVisible = false
    },
    handleOpenConfigPanel () {
      this.boxVisible = true
      this.$nextTick(() => {
        // this.$refs.rightpanel.selectSpecialModel(22)
      })
    },
    setDialogWidth () {
      var val = document.body.offsetWidth
      const def = 450 // 默认宽度
      if (val < def) {
        this.dialogWidth = '80%'
      } else {
        this.dialogWidth = def + 'px'
      }
    },
    initData () {
      let iframdevid = getIframdevid()
      getTscControl(iframdevid).then((data) => {
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            return
          }
          let parrenterror = getMessageByCode(data.data.code, this.$i18n.locale)
          if (data.data.data) {
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              let childerror = getMessageByCode(data.data.data.errorCode, this.$i18n.locale)
              this.$message.error(parrenterror + ',' + childerror)
            }
          } else {
            this.$message.error(parrenterror)
          }
          return
        }
        this.crossStatusData = JSON.parse(JSON.stringify(data.data.data.data))
      }).catch(error => {
        console.log(error)
      })
    },
    setPropsToken (token) {
      // 获取组件外传入的token，便于独立组件调用接口
      if (token && token !== '') {
        setToken(token)
      }
    }
  },
  created () {
    this.setDialogWidth()
  },
  mounted () {
    this.setPropsToken(this.Token)
    this.initData()
    window.onresize = () => {
      return (() => {
        this.setDialogWidth()
      })()
    }
  }
}
</script>
<style lang="scss">
.abow_dialog {
    display: flex;
    justify-content: center;
    align-items: Center;
    overflow: hidden;
    .el-dialog {
        margin: 0 auto !important;
        height: 90%;
        overflow: hidden;
        .el-dialog__body {
            position: absolute;
            left: 0;
            top: 54px;
            bottom: 0;
            right: 0;
            padding: 0;
            z-index: 1;
            overflow-y: auto;
            overflow-x: auto;
        }
    }
}
</style>
