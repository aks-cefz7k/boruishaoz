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
  <div class="updateDecForm">
    <el-form ref="detectorform" :model="formData" label-width="23%" :rules="rules">
      <el-form-item :label="$t('openatc.bottleneckcontrol.detectorname')" prop="description">
        <el-input v-model="formData.description" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="$t('openatc.bottleneckcontrol.type')">
        <el-select v-model="formData.type" size="small" style="width:100%">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="onSubmit" size="small" style="margin-left: 10px;">{{$t('openatc.button.OK')}}</el-button>
      <el-button type="primary" @click="cancel" size="small">{{$t('openatc.button.Cancel')}}</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'optedit',
  props: {
    editData: {
      type: Object
    }
  },
  data () {
    return {
      formData: {},
      temp: {
        // id: '',
        type: '',
        description: ''
      },
      typeList: [
        {
          label: this.$t('openatc.bottleneckcontrol.overflow'),
          value: '1'
        },
        {
          label: this.$t('openatc.bottleneckcontrol.queue'),
          value: '2'
        }
      ],
      rules: {
        description: [
          { required: true, message: this.$t('openatc.bottleneckcontrol.entername'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    onSubmit () {
      this.$refs.detectorform.validate((valid) => {
        if (valid) {
          this.$emit('submitForm', this.formData)
        } else {
          this.$message.error(this.$t('openatc.devicemanager.enterrequired'))
          return false
        }
      })
    },
    cancel () {
      this.$emit('closeModal')
    }
  },
  mounted () {
    if (this.editData === null) {
      this.formData = JSON.parse(JSON.stringify(this.temp))
    } else {
      this.formData = JSON.parse(JSON.stringify(this.editData))
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.updateDecForm {
  padding-right: 30px;
}
.dialog-footer {
  overflow: hidden;
  margin-top: 38px;
  button {
    float: right;
  }
}
</style>
