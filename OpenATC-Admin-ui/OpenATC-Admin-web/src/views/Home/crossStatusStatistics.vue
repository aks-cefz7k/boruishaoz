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
  <div class="status-type">
    <el-row :gutter="26">
      <el-col :span="6" v-for="item in list" :key="item.id">
        <div class="tpye-item" :style="{'background': item.background}">
            <svg-icon :icon-class="item.imgClass" className="Img"></svg-icon>
            <!-- <div class="Img" :class="item.imgClass"></div> -->
            <div class="state">
              <div class="type">{{item.type}}</div>
              <div class="compYest">
                <span>{{$t('openatc.home.compareyesterday')}}</span>
                <span class="compnum">{{ item.comparenum }}</span>
              </div>
            </div>
            <div class="num" :class="{'pointernum': item.key === 'online' || item.key === 'offline'}" @click="handleJumpToCrossMgr(item.key)">{{item.num}}</div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getMessageByCode } from '@/utils/responseMessage'
import { getYesterdayStatusCollect } from '@/api/device'
import { clearCrossFilter } from '@/utils/crossFilterMgr'
export default {
  name: 'cross-status-statistic',
  props: {
    chartData: {
      type: Array
    }
  },
  watch: {
    chartData: {
      handler: function (val) {
        // console.log(val)
        for (const value of Object.values(val)) {
          this.CurrStatusMap.set(value.key, value.value)
        }
        this.list = this.list.map(ele => {
          ele.num = this.CurrStatusMap.get(ele.key)
          if (this.CurrStatusMap.get(ele.key) === undefined) {
            ele.comparenum = '--'
            return ele
          }
          if (ele.num !== undefined && ele.yestnum !== undefined) {
            let comparenum = ele.num - ele.yestnum
            if (comparenum < 0) {
              ele.comparenum = comparenum
            } else {
              ele.comparenum = `+${comparenum}`
            }
          }
          return ele
        })
      },
      deep: true
    }
  },
  data () {
    return {
      list: [
        {
          id: 1,
          key: 'online',
          imgClass: 'online',
          background: 'rgba(185, 185, 185, 0.05)',
          type: this.$t('openatc.home.online'),
          num: 0,
          yestnum: 0,
          comparenum: 0
        },
        {
          id: 2,
          key: 'offline',
          imgClass: 'offline',
          background: 'rgba(185, 185, 185, 0.05)',
          type: this.$t('openatc.home.offline'),
          num: 0,
          yestnum: 0,
          comparenum: 0
        },
        {
          id: 3,
          key: 'fault',
          imgClass: 'fault',
          background: 'rgba(185, 185, 185, 0.05)',
          type: this.$t('openatc.home.fault'),
          num: 0,
          yestnum: 0,
          comparenum: 0
        },
        {
          id: 4,
          key: 'manual',
          imgClass: 'manual',
          background: 'rgba(185, 185, 185, 0.05)',
          type: this.$t('openatc.home.intervene'),
          num: 0,
          yestnum: 0,
          comparenum: 0
        }
      ],
      CurrStatusMap: new Map(),
      YestStatusMap: new Map()
    }
  },
  methods: {
    getYesterdayStatus () {
      getYesterdayStatusCollect().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        let data = res.data.data
        for (const [key, value] of Object.entries(data)) {
          this.YestStatusMap.set(key, value)
        }
        // console.log(this.YestStatusMap)
        this.list = this.list.map(ele => {
          ele.yestnum = this.YestStatusMap.get(ele.key)
          if (this.YestStatusMap.get(ele.key) === undefined) {
            ele.comparenum = '--'
            return ele
          }
          let comparenum = ele.num - ele.yestnum
          if (comparenum < 0) {
            ele.comparenum = comparenum
          } else {
            ele.comparenum = `+${comparenum}`
          }
          return ele
        })
        // console.log(this.list)
      })
    },
    handleJumpToCrossMgr (filter) {
      if (filter === 'online' || filter === 'offline') {
        clearCrossFilter() // 清除路口管理过滤条件
        this.$router.push({
          name: 'deviceMgr',
          params: {filter}
        })
      }
    }
  },
  created () {},
  mounted () {
    this.getYesterdayStatus()
  }
}
</script>
