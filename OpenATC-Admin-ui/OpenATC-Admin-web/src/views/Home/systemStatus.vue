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
  <div class="system-status">
    <el-table
        class="system-status-table"
        style="width: 100%"
        height="100%"
        size="small"
        :data="list"
        >
        <el-table-column
          prop="server"
          :label="$t('openatc.home.server')"
          align="left">
        </el-table-column>
        <el-table-column
          prop="state"
          :label="$t('openatc.home.state')"
          align="left">
        </el-table-column>
        <el-table-column
          :label="$t('openatc.home.detail')"
          align="center">
          <template slot-scope="scope">
            <el-popover
              placement="bottom"
              trigger="click">
              <div class="system-status-pop-content">
                <div class="tittle">{{scope.row.server}}</div>
                <div class="detail">
                  <div class="detail-item" v-for="(datailitem, index) in scope.row.detailarr" :key="index">
                    <span class="label">{{datailitem[0]}} : </span>
                    <span class="content">{{datailitem[1]}}</span>
                  </div>
                </div>
              </div>
              <el-button
                slot="reference"
                @click.native.prevent="openDetails(scope.row)"
                type="text"
                size="small"
              >{{$t('openatc.home.view')}}</el-button>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
  </div>
</template>

<script>
import moment from 'moment'
import { getMessageByCode } from '@/utils/responseMessage'
import { getAllServerStatus } from '@/api/systemStatus'
export default {
  name: 'system-status',
  data () {
    return {
      getDevsDataTimer: Object,
      list: []
    }
  },
  methods: {
    openDetails () {

    },
    getAllServerStatus () {
      getAllServerStatus().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        let data = res.data.data
        this.list = data.map(ele => {
          let secondtime = this.diffSecond(ele.updatetime, moment(new Date()).format('YYYY-MM-DD HH:mm:ss'))
          if (secondtime > 5) {
            ele.state = 'DOWN'
          } else {
            ele.state = 'UP'
          }
          ele.detailarr = []
          for (let i in ele.data) {
            ele.detailarr.push([i, ele.data[i]])
          }
          return ele
        })
        // console.log(this.list)
      })
    },
    diffSecond (start, end) {
      const startTime = new Date(start)
      const endTime = new Date(end)
      const secondtime = Math.abs(startTime - endTime) / 1000 / 60 // 得到相差的分钟数
      return secondtime
    }
  },
  created () {},
  mounted () {
    this.getAllServerStatus()
    this.getAllServerTimer = setInterval(() => {
      this.getAllServerStatus()
    }, 60000)
  },
  destroyed () {
    clearInterval(this.getDevsDataTimer)
  }
}
</script>
