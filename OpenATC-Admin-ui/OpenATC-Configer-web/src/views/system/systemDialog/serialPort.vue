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
  <div class="system-signip">
    <el-dialog
      title="设备识别码设置"
      :visible.sync="dialogFormVisible"
      width="600px"
      @close='closeFormDialog'>
      <el-form
        class="dialog-footer"
        ref="serialPortInfo"
        :model="serialPortInfo"
        label-width="15%">
        <el-form-item
            label="波特率"
            prop="Baudrate">
            <el-select v-model="serialPortInfo.Baudrate" placeholder="请选择" style="width:90%" size="small">
                <el-option
                v-for="item in baudrateOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            label="数据位"
            prop="databit">
            <el-select v-model="serialPortInfo.databit" placeholder="请选择" style="width:90%" size="small">
                <el-option
                v-for="item in databitOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            label="停止位"
            prop="stopbit">
            <el-select v-model="serialPortInfo.stopbit" placeholder="请选择" style="width:90%" size="small">
                <el-option
                v-for="item in stopbitOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item
            label="奇偶校验位"
            prop="paritybit">
            <el-select v-model="serialPortInfo.paritybit" placeholder="请选择" style="width:90%" size="small">
                <el-option
                v-for="item in paritybitOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button @click="resetForm()">取消</el-button>
          <el-button type="primary" @click="ok">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2019/12/11
*/
import { setSerialPort } from '@/api/system'
export default {
  data () {
    return {
      dialogFormVisible: false,
      serialPortInfo: {
        Baudrate: 0,
        databit: 0,
        stopbit: 0,
        paritybit: ''
      },
      baudrateOptions: [{
        value: 1200,
        label: 1200
      },
      {
        value: 2400,
        label: 2400
      },
      {
        value: 4800,
        label: 4800
      },
      {
        value: 9600,
        label: 9600
      },
      {
        value: 19200,
        label: 19200
      },
      {
        value: 38400,
        label: 38400
      }],
      databitOptions: [{
        value: 5,
        label: 5
      },
      {
        value: 6,
        label: 6
      },
      {
        value: 7,
        label: 7
      },
      {
        value: 8,
        label: 8
      }],
      stopbitOptions: [{
        value: 0,
        label: 0
      },
      {
        value: 1,
        label: 1
      },
      {
        value: 2,
        label: 2
      }],
      paritybitOptions: [{
        value: 'EVEN',
        label: 'EVEN'
      },
      {
        value: 'ODD',
        label: 'ODD'
      }]
    }
  },
  name: 'serialport',
  props: {},
  created () {
  },
  methods: {
    onEditSerialPort (Baudrate, databit, stopbit, paritybit) {
      if (Baudrate !== 0) {
        this.serialPortInfo.Baudrate = Baudrate
      }
      if (databit !== 0) {
        this.serialPortInfo.databit = databit
      }
      if (stopbit !== 0) {
        this.serialPortInfo.stopbit = stopbit
      }
      if (paritybit !== '--') {
        this.serialPortInfo.paritybit = paritybit
      }
      this.dialogFormVisible = !this.dialogFormVisible
    },
    closeFormDialog () {
      this.resetForm()
    },
    resetForm (formData) {
      // 取消重置表单
      this.dialogFormVisible = false
    },
    ok () {
      setSerialPort(this.serialPortInfo.Baudrate, this.serialPortInfo.databit, this.serialPortInfo.stopbit, this.serialPortInfo.paritybit).then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error('设备不在线！')
            return
          }
          this.$message.error(data.data.message)
          return
        }
        let msg = '设置成功！'
        this.closeFormDialog()
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000,
          onClose: () => {
            this.$parent.getSerialPort()
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.system-signip .el-form-item__label {
    text-align: right;
    float: left;
    font-size: 14px;
    padding: 0 12px 0 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    font-family: SourceHanSansCN-Regular;
    font-weight: normal;
    font-stretch: normal;
    line-height: 40px;
    letter-spacing: 0px;
    color: #999999;
}
</style>
