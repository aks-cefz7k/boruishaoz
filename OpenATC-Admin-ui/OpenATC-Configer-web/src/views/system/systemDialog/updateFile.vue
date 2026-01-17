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
      :title="$t('edge.system.tips')"
      :visible.sync="dialogFormVisible"
      width="400px"
      @close='closeFormDialog'>
      <div>
          <form>
              <input type="file" @change="getFile($event)">
          </form>
      </div>
      <div slot="footer" class="dialog-footer">
          <el-button @click="resetForm()">{{$t('edge.common.cancel')}}</el-button>
          <el-button type="primary" @click="submitForm($event)">{{$t('edge.system.update')}}</el-button>
      </div>
      <div class="progressmask" v-if="animvisible"></div>
      <div style="margin-top: 18px;">
        <ProgressAnim v-if="animvisible"/>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { updateFile } from '@/api/param'
import { setRemoteControl } from '@/api/system'
import ProgressAnim from './progressAnim'
import { getMessageByCode } from '@/utils/responseMessage'
import {
  getIframdevid
} from '../../../utils/auth'
export default {
  data () {
    return {
      dialogFormVisible: false,
      file: '',
      animvisible: false
    }
  },
  name: 'updatefile',
  props: {},
  components: {
    ProgressAnim
  },
  created () {
  },
  destroyed () {
    if (this.updateTimeoutTimer) {
      clearTimeout(this.updateTimeoutTimer)
    }
  },
  methods: {
    onUpdateFile () {
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
    },
    getFile (event) {
      this.file = event.target.files[0]
    },
    submitForm (event) {
      event.preventDefault()
      if (this.file === '') {
        this.$message.error(this.$t('edge.system.noselectfile'))
        return
      }
      let formData = new FormData()
      let iframdevid = getIframdevid()
      formData.append('file', this.file)
      formData.append('agentid', iframdevid)
      this.animvisible = true
      this.updateTimeoutTimer = setTimeout(() => {
        this.$message.error(this.$t('edge.system.updateovertime'))
        this.animvisible = false
      }, 60000)
      updateFile(formData).then((data) => {
        let res = data.data
        if (this.updateTimeoutTimer) {
          clearTimeout(this.updateTimeoutTimer)
        }
        this.animvisible = false
        if (!res.success) {
          let errormsg = this.$t('edge.system.filefailed')
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(errormsg)
          return
        }
        let msg = this.$t('edge.system.filesuccess')
        this.closeFormDialog()
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
        })
        this.$confirm(this.$t('edge.system.isReboot'),
          this.$t('edge.common.alarm'), {
            confirmButtonText: this.$t('edge.common.confirm'),
            cancelButtonText: this.$t('edge.common.cancel'),
            type: 'warning'
          }).then(() => {
          this.setRemoteControl()
          // this.$message({
          //   type: 'success',
          //   message: this.$t('edge.system.rebootSuccess')
          // })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('edge.system.rebootFaile')
          })
        })
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    setRemoteControl () {
      setRemoteControl('reboot').then(data => {
        let res = data.data
        if (!res.success) {
          if (res.code === '4003') {
            this.$message.error(this.$t('edge.errorTip.devicenotonline'))
            return
          }
          this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
          return
        }
        let msg = this.$t('edge.system.rebootSuccess')
        this.$message({
          message: msg,
          type: 'success',
          duration: 1 * 1000
          // onClose: () => {
          //   this.$parent.getCode()
          // }
        })
      })
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.progressmask{
    // 进度条蒙层
    width: 100%;
    height: 100%;
    // background-color: rgba(0, 0, 0, 0.3);
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
}
</style>
