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
    <el-form-item :label="$t('edge.auth.new')" prop="newpass">
      <el-input type="password" v-model="passData" @keyup.enter.native="updateUser" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false">
      </el-input>
    </el-form-item>
    <el-form-item v-if="!needHideContain" :label="$t('edge.auth.contain')" size="mini" id="pass-label">
      <div>
        <i :class="this.contaionclass1" :id="this.contaionid1">
          <label>{{$t('edge.auth.contain1')}}</label>
        </i>
      </div>
      <br>
      <div>
        <i :class="this.contaionclass2" :id="this.contaionid2">
          <label> {{$t('edge.auth.contain2')}}</label>
          <br><br>
          <label> &nbsp;&nbsp;&nbsp;&nbsp;{{$t('edge.auth.contain3')}}</label>
        </i>
      </div>
    </el-form-item>
    <el-form-item v-if="!needHideContain" label="" prop="newpass" size="mini">
      <div class="progressContainer">
        <div :class="strengthclass" :style="{width:progress+'%'}" v-if="strength !== 0"></div>
      </div>
      <label style="float:left;">{{$t('edge.auth.strength')}}:{{$t(strong)}}</label>
    </el-form-item>
  </div>
</template>
<script>
import { GetPasswdSafeLeavel, CheckPasswdSafeLeavel } from '../../../api/passwdAssest'

export default {
  name: 'passCheck',
  props: {
    newpassdata: {
      type: String,
      default: ''
    },
    needHideContain: {
      type: Boolean,
      default: false
    }
  },
  data () {
    // var validatePass2 = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('Please input the password !'))
    //   } else if (value !== this.passData) {
    //     callback(new Error('The two input password is inconsistent !'))
    //   } else {
    //     callback()
    //   }
    // }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password !'))
      } else if (!this.check_length || !this.check_char) {
        callback(new Error('Not Satisfiable !'))
      } else {
        callback()
      }
    }
    return {
      strength: 0, // < 0:weak, 1:normal, 2:strong
      strengthclass: 'progress',
      progress: 0,
      strong: 'edge.auth.none',
      check_length: false, // 检验密码长度状态
      check_char: false, // 检验密码应包含数字、字母和特殊字符
      rules: {
        newpassdata: [
          {
            required: true,
            validator: validatePass,
            trigger: 'blur'
          }
        ]
      },
      contaionclass1: 'el-icon-error',
      contaionid1: 'pass-state-err',
      contaionclass2: 'el-icon-error',
      contaionid2: 'pass-state-err',
      passData: this.newpassdata
    }
  },
  watch: {
    strength: function (curVal, oldVal) {
      let className
      switch (curVal) {
        case '0':
          className = 'progress'
          break
        case '1':
          className = 'progress'
          break
        case '2':
          className = 'progress-1'
          break
        case '3':
          className = 'progress-2'
          break
        default:
          break
      }
      this.progress = curVal / 3 * 100
      this.strengthclass = className
    },
    check_length: function (curVal, oldVal) {
      this.contaionclass1 = curVal ? 'el-icon-success' : 'el-icon-error'
      this.contaionid1 = curVal ? 'pass-state-succ' : 'pass-state-err'
    },
    check_char: function (curVal, oldVal) {
      this.contaionclass2 = curVal ? 'el-icon-success' : 'el-icon-error'
      this.contaionid2 = curVal ? 'pass-state-succ' : 'pass-state-err'
    },
    passData: function (curVal, oldVal) {
      if (oldVal !== curVal) {
        let i = GetPasswdSafeLeavel(curVal)
        this.strength = i
        switch (this.strength) {
          case '0':
            this.strong = 'edge.auth.none'
            break
          case '1':
            this.strong = 'edge.auth.weak'
            break
          case '2':
            this.strong = 'edge.auth.normal'
            break
          case '3':
            this.strong = 'edge.auth.strong'
            break
          default:
            break
        }
        let stateList = CheckPasswdSafeLeavel(curVal)
        this.check_length = false
        this.check_char = false
        for (let st of stateList) {
          if (st === '1') {
            this.check_char = true
          }
          if (st === '2') {
            this.check_length = true
          }
        }
        this.$emit('updateData', curVal)
      }
    },
    deep: true
  },
  methods: {
    reset () {
      this.strong = 'edge.auth.none'
      this.passData = ''
    },
    checkNewPass () {
      if (this.passData === '') {
        this.$message.error('Please input the password !')
        return false
      } else if (!this.check_length || !this.check_char) {
        this.$message.error(this.$t('auth.check.requirementspassword'))
        return false
      }
      return true
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.progressContainer {
    height: 10px;
    width: 100%;
    float: right;
    border-radius: 10px;
    background-color: #ddd;
    margin-left: 2%;
}
.progress {
    background-color: rgba(209, 63, 43, 158);
    border-radius: 10px;
    height: 10px;
    //width: 0;
    line-height: 20px;
}
.progress-1 {
    background-color: #ff7744;
    border-radius: 10px;
    height: 10px;
    //width: 0;
    line-height: 20px;
}
.progress-2 {
    background-color: #01b468;
    border-radius: 10px;
    height: 10px;
    //width: 0;
    line-height: 20px;
}
.password-strength .el-form-item__label {
    color: #9999;
}
</style>
