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
  <el-card class="serviceroute-routeCard" v-show="isShow">
    <div slot="header" class="clearfix">
      <span style="vertical-align:middle;">
        <span class="grid-content-label" style="display:inline-block;margin-right:10px;">{{ $t("openatc.dutyroute.currentControl") }} : </span> {{node.currentControlName}}
      </span>
      <el-button
        v-show="tabName === 'second'"
        style="float: right; padding: 3px 0"
        type="text"
        @click="executeViproute"
        >
        <template v-if="!node.state || node.state === 0">
          {{ $t("openatc.dutyroute.executenow") }}
        </template>
        <template v-else>
          {{ $t("openatc.dutyroute.cancelexecute") }}
        </template>
      </el-button>
    </div>
    <div class="text item" style="border:0px solid red;height:10px;">
      <el-row :gutter="20">
        <div class="grid-content-label" style="display:inline-block;margin-right:10px;margin-left:10px;">
          {{ $t("openatc.dutyroute.executionway") }} :
        </div>
        <div class="grid-content bg-purple" style="display:inline-block;margin-right:80px;">
          {{ getContent(node) }}
        </div>
        <div class="grid-content-label" style="display:inline-block;margin-right:10px;">
          {{ $t("openatc.dutyroute.lasttime") }} :
        </div>
        <div class="grid-content bg-purple" style="display:inline-block;margin-right:10px;">
          {{ node.totaltime }}
        </div>
      </el-row>
    </div>
  </el-card>
</template>

<script>
import { ExecuteViproute } from '@/api/service'
import { getMessageByCode } from '@/utils/responseMessage'
import ServiceUtil from '../serviceUtil.js'
export default {
  name: 'nodeCard',
  props: {
    node: {
      type: Object
    },
    tabName: {
      type: String,
      default: 'first'
    }
  },
  data () {
    return {
      isShow: true,
      isBtnDisabled: false
    }
  },
  created () {
    this.serviceUtil = new ServiceUtil()
  },
  methods: {
    onCloseClick () {
      this.isShow = false
    },
    show () {
      this.isShow = true
    },
    executeViproute () {
      let reqData = {
        'agentid': this.node.agentid,
        'viprouteid': this.node.viprouteid,
        'operation': 1
      }
      if (this.node.state === 1) {
        reqData.operation = 0
      }
      // this.isBtnDisabled = true
      ExecuteViproute(reqData).then(res => {
        // this.isBtnDisabled = false
        if (!res.data.success) {
          let msg = getMessageByCode(res.data.code, this.$i18n.locale)
          if (res.data.data) {
            let errorCode = res.data.data.errorCode
            if (errorCode) {
              msg = msg + ' - ' + getMessageByCode(errorCode, this.$i18n.locale)
            }
          }
          this.$message.error(msg)
          return false
        } else {
          if (res.data.data && res.data.data.data) {
            let success = res.data.data.data.success
            if (success !== 0) {
              // 手动面板控制提示
              let errormsg = 'edge.overview.putTscControlError' + success
              this.$message.error(this.$t(errormsg))
              return false
            }
          }
        }
        this.$emit('research')
      })
    },
    getTag (row) {
      if (row.stateName === 'DOWN') {
        return {
          label: this.$t('openatc.devicemanager.offline'),
          type: 'info'
        }
      } else if (row.stateName === 'UP') {
        return {
          label: this.$t('openatc.devicemanager.online'),
          type: 'success'
        }
      } else {
        return {
          label: this.$t('openatc.devicemanager.fault'),
          type: 'danger'
        }
      }
    },
    getContent (row) {
      let res = this.serviceUtil.getContent(row)
      // if (res === '') {
      //   res = row.controlName
      // }
      return res
    }
  }
}
</script>
