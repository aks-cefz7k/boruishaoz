<template>
  <div>
    <el-dialog
      center
      :top="boxTop"
      :width="boxWidth"
      :visible.sync="boxVisible"
      :title="title"
      :close-on-click-modal="false"
      custom-class="kissMessageBox"
      @close="oncancle"
      append-to-body>
      <div class="text">{{text}}</div>
      <div slot="footer" class="boxFooter">
        <el-button class="btn canclebtn" type="primary" @click="oncancle">取 消</el-button>
        <el-button class="btn okbtn" type="primary" @click="onok">确 认</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'kiss-message-box',
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
    },
    boxTop: {
      type: String,
      default: '380px'
    },
    boxWidth: {
      type: String,
      default: '424px'
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
.kissMessageBox {
  background: rgba(6, 63, 77, 0.8);
  border: 1px solid #085c71;
}
.kissMessageBox .el-dialog__header {
  border-bottom: 1px solid #004e61;
}
.kissMessageBox .el-dialog__header .el-dialog__headerbtn i {
  color: #0096ba;
}
.kissMessageBox .el-dialog__body {
  text-align: center;
}
.kissMessageBox .el-dialog__title {
  font-size: 16px;
  color: #42daff;
}
.kissMessageBox .text {
  font-family: MicrosoftYaHei;
  font-size: 14px;
  color: #ffffff;
}
.kissMessageBox .boxFooter .btn {
  font-size: 14px;
  font-family: MicrosoftYaHei;
  width: 64px;
  height: 34px;
  padding: 2px 10px;
}
.kissMessageBox .boxFooter .canclebtn {
  background-color: #09647a;
  color: #42daff;
  border-color: #09647a;
}
.kissMessageBox .boxFooter .okbtn {
  background-color: #0096ba;
  color: #fff;
  border-color: #0096ba;
}
</style>
