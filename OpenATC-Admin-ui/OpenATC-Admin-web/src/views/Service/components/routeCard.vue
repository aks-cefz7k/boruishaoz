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
      <span
        ><strong>{{ $t("openatc.dutyroute.attributes") }}</strong></span
      >
      <el-button
        style="float: right; padding: 3px 0"
        type="text"
        icon="el-icon-close"
        @click.stop="onCloseClick"
      ></el-button>
    </div>
    <div class="text item">
      <!-- <el-tag type="info" class="tag">已执勤</el-tag> -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="10">
              <div class="grid-content-label">
                {{ $t("openatc.greenwaveoptimize.deviceid") }}:
              </div>
            </el-col>
            <el-col :span="14">
              <div class="grid-content bg-purple">{{ node.agentid }}</div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row :gutter="10">
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
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="10">
              <div class="grid-content-label">
                {{ $t("openatc.dutyroute.residentphase") }}:
              </div>
            </el-col>
            <el-col :span="14">
              <div class="grid-content bg-purple">{{ node.value }}</div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="10">
              <div class="grid-content-label">
                {{ $t('openatc.devicemanager.state') }}:
              </div>
            </el-col>
            <el-col :span="14">
              <div class="grid-content bg-purple">
                <template>
                  <div>
                      <el-tag size="medium" effect="plain" :type="getTag(node).type">{{ getTag(node).label }}</el-tag>
                  </div>
                </template>
                <!-- {{ getDeviceState (node)}} -->
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
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
        <el-col :span="8">
          <el-row :gutter="10">
            <el-col :span="10">
              <div class="grid-content-label">
                {{ $t("openatc.dutyroute.timeleft") }}:
              </div>
            </el-col>
            <el-col :span="14">
              <template class="grid-content bg-purple">{{ node.resttime }}</template>
              <el-button
                v-show="tabName === 'second'"
                style="float: right;"
                type="primary"
                size="small"
                @click="executeViproute"
                >
                <template v-if="!node.state || node.state === 0">
                  {{ $t("openatc.dutyroute.executenow") }}
                </template>
                <template v-else>
                  {{ $t("openatc.dutyroute.cancelexecute") }}
                </template>
                </el-button
              >
            </el-col>
            <!-- <div class="btn-bottom" v-show="tabName === 'second'">
            </div> -->
          <!-- </el-row>
          <el-row :gutter="10"> -->
          </el-row>
        </el-col>
        <!-- <el-col :span="8">
        </el-col> -->
      </el-row>
    </div>
  </el-card>
</template>

<script>
import { ExecuteViproute } from '@/api/service'
import { getMessageByCode } from '@/utils/responseMessage'
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
      isShow: false,
      isBtnDisabled: false
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
    }

  }
}
</script>
