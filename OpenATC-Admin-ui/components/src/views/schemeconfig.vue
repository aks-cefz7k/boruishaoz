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
      <scheme-config
        ref="rightpanel"
        agentId="10001-928"
        roadDirection="right"
        :statusData="crossStatusData"
        :realtimeStatusModalvisible="false" />
    </el-dialog>
  </div>
</template>
<script>
import { getTscControl } from '../api/control.js'
import { getMessageByCode } from '../utils/responseMessage.js'
import {
  getIframdevid
} from '../utils/auth'
export default {
  name: 'democonfig',
  data () {
    return {
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
    }
  },
  created () {
    this.setDialogWidth()
  },
  mounted () {
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
