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
      <intersection-with-interface
        ref="intersectionWithInterface"
        :AgentId="agentId"
        Token="eyJraWQiOiIxNjQ4MDIxOTczNDU1IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczNDQyMTk3MywiaWF0IjoxNjQ4MDIxOTczfQ.mlLa7WtA9jyNMTAlklrnw9gDLa5nPl0gqqf54p1LHsU"
        ></intersection-with-interface>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'demo',
  data () {
    return {
      boxVisible: false,
      dialogWidth: '100%',
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
        this.dialogWidth = '100%'
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
