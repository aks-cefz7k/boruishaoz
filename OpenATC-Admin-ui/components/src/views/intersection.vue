<template>
  <div>
    <el-button type="primary" @click="handleOpenConfigPanel" style="margin: 20px;">打开路口图面板</el-button>
    <el-dialog
      refs="intersectionMapDialog"
      class="abow_dialog"
      :width="dialogWidth"
      :visible.sync="boxVisible"
      :close-on-click-modal="false"
      @close="oncancle"
      append-to-body>
      <intersection-with-interface
        ref="intersectionWithInterface"
        :AgentId="agentId"
        roadDirection="left"
        :Token="Token"
        ></intersection-with-interface>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'demo',
  data () {
    return {
      Token: 'eyJraWQiOiIxNjQ5MzM4NzA5MTA0IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczNTczODcwOSwiaWF0IjoxNjQ5MzM4NzA5fQ.JDSkpT1SbB61dEKvorhVdZJHKJzoQZEY5DPKHs1Imo0',
      boxVisible: false,
      dialogWidth: '80%',
      agentId: '40003-132'
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
      const def = 1200 // 默认宽度
      if (val < def) {
        this.dialogWidth = '80%'
      } else {
        this.dialogWidth = def + 'px'
      }
    }
  },
  created () {
    this.setDialogWidth()
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.setDialogWidth()
      })()
    }
  },
  destroyed () {
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
