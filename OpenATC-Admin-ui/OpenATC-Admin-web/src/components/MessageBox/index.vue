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
    <el-dialog
      center
      width="424px"
      :visible.sync="boxVisible"
      :title="title"
      :close-on-click-modal="false"
      custom-class="MessageBox"
      @close="oncancle"
      append-to-body>
      <div class="text">{{text}}</div>
      <div slot="footer" class="boxFooter">
        <el-button class="btn canclebtn" @click="oncancle">取 消</el-button>
        <el-button class="btn okbtn" type="primary" @click="onok">确 认</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'message-box',
  data () {
    return {
      boxVisible: this.visible
    }
  },
  watch: {
    visible: function (val) {
      this.boxVisible = val
    }
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: '提示'
    },
    text: {
      type: String,
      default: ''
    }
  },
  methods: {
    onok () {
      this.$emit('ok')
    },
    oncancle () {
      this.$emit('cancle')
    }
  },
  mounted () {
  }
}
</script>
<style>
.MessageBox .el-dialog__body {
  text-align: center;
}
.MessageBox .el-dialog__title {
  font-size: 16px;
}
.MessageBox .text {
  font-family: MicrosoftYaHei;
  font-size: 14px;
}
.MessageBox .boxFooter .btn {
  font-size: 14px;
  font-family: MicrosoftYaHei;
  width: 64px;
  height: 34px;
  padding: 2px 10px;
}
.MessageBox .el-dialog__footer {
  padding: 10px 20px 20px;
}
</style>
