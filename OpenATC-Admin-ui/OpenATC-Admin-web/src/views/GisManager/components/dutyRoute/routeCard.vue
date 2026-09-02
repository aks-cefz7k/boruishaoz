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
  <el-card class="dutyRoute-routeCard" v-show="isShow">
    <div class="text item">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-row :gutter="0">
            <el-col :span="10">
              <div class="grid-content-label">
                {{ $t("openatc.dutyroute.executionway") }}:
              </div>
            </el-col>
            <el-col :span="14">
              <div class="grid-content bg-purple">
                {{ node.controlName }}
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row :gutter="0">
            <el-col :span="10">
              <div class="grid-content-label" style="margin-left: -10px;">
                {{ $t("openatc.dutyroute.residentphase") }}:
              </div>
            </el-col>
            <el-col :span="14">
              <div class="grid-content bg-purple">{{ node.value }}</div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row :gutter="0">
        <el-col :span="12">
          <el-row :gutter="10">
            <el-col :span="10">
              <div class="grid-content-label">
                {{ $t("openatc.greenwaveoptimize.pattern") }}:
              </div>
            </el-col>
            <el-col :span="14">
              <div class="grid-content bg-purple">{{ node.terminalname }}</div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row :gutter="0">
            <el-col :span="8">
              <div class="grid-content-label">
                {{ $t("openatc.dutyroute.timeleft") }}:
              </div>
            </el-col>
            <el-col :span="16">
              <div style="display:inline-block;">
                <div style="display:inline-block; vertical-align:top;">{{ node.resttime }}</div>
                <div style="display:inline-block;margin-left:60px;">
                  <el-button  v-show="tabName === 'second'"
                              style="float: right;padding:5px;"
                              :type="getButtonType(node)"
                              size="small"
                              @click="executeViproute">
                    <template v-if="!node.state || node.state === 0">
                      {{ $t("openatc.dutyroute.executenow") }}
                    </template>
                    <template v-else>
                      {{ $t("openatc.dutyroute.cancelexecute") }}
                    </template>
                  </el-button>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row :gutter="0" v-if="isShowVideo">
        <el-col :span="24">
          <Flv :curDevVideos="node.videos" :autoPlay="autoPlay"></Flv>
        </el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script>
import { ExecuteViproute } from '@/api/service'
import Flv from '@/components/Flvjs/index.vue'
import { getMessageByCode } from '@/utils/responseMessage'
export default {
  name: 'nodeCard',
  components: {
    Flv
  },
  props: {
    node: {
      type: Object
    },
    tabName: {
      type: String,
      default: 'second'
    },
    isShowVideo: {
      type: Boolean,
      defaultValue: false
    }
  },
  data () {
    return {
      isShow: true,
      isBtnDisabled: false,
      autoPlay: true
    }
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
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return false
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
    getButtonType (node) {
      let res = 'warning'
      if (!node.state || node.state === 0) {
        res = 'primary'
      }
      return res
    }
  }
}
</script>
<style scoped>
.dutyRoute-routeCard >>> .el-card__body {
  padding: 10px 0 10px 10px;
}
</style>
