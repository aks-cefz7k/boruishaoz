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
  <div class="edgeModal">
    <el-dialog
      :visible.sync="dialogVisible"
      width="90%"
      top="70px"
      :close-on-click-modal="false"
      :modal-append-to-body="false"
      @close="closeFormDialog"
    >
     <iframe :src="url"
        v-if="dialogVisible"
        id="kissframe"
        frameborder="0"
        scrolling="yes"
        :height="ModalHeight"
        width="100%"
    ></iframe>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'edgeModal',
  data () {
    return {
      dialogVisible: false,
      url: '',
      ModalHeight: '800px'
    }
  },
  methods: {
    getHost () {
      let host = `${window.location.href.split('//')[1].split('/')[0]}`
      if (process.env.NODE_ENV === 'development') {
        return host
      } else {
        return `${host}/openatc`
      }
    },
    closeFormDialog () {
      // 弹窗关闭，将标识恢复默认值
      sessionStorage.setItem('toSingleEdge', '0')
      this.dialogVisible = false
    },
    openSingleEdge (dev) {
      this.url = `http://${this.getHost()}/#/overviewNew/index?agentid=${dev.agentid}&isfromatc=true`
      this.dialogVisible = !this.dialogVisible
    },
    getClientHeight () {
      // 获取视口高度
      var clientHeight = 0
      if (document.body.clientHeight && document.documentElement.clientHeight) {
        clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight
      } else {
        clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight
      }
      return clientHeight
    }
  },
  mounted () {
    this.ModalHeight = `${this.getClientHeight() - 180}px`
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.edgeModal .el-dialog__body {
  padding: 10px;
}
</style>
