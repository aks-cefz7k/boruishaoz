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
  <div class="cross-abnormal" :class="{'cross-abnormal-en': getLang() !== 'zh'}">
    <el-row>
      <el-col :span="8" v-for="faultitem in mainFaultList" :key="faultitem.id">
        <div class="abnormal-item">
          <div class="markIcon">
            <svg-icon :icon-class="faultitem.imgClass" className="model-icon"></svg-icon>
          </div>
          <div class="content" @click="jumpToCurrentFault(faultitem.key)">
            <div class="num">{{faultitem.crossnum}}</div>
            <div class="name">{{faultitem.name}}</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getLanguage } from '@/utils/auth'
export default {
  name: 'cross-abnormal',
  props: {
    faultList: {
      type: Array
    }
  },
  watch: {
    faultList: {
      handler: function (val, oldVal) {
        this.getFaultCross()
      },
      // 深度观察监听
      deep: true
    }
  },
  data () {
    return {
      faultCrossMap: new Map(), // 不同故障类型下的路口信息列表
      mainFaultList: [{
        key: 1,
        name: this.$t('openatc.faultrecord.maincontrolboard'),
        imgClass: 'maincontrolboard',
        crossnum: 0
      }, {
        key: 2,
        name: this.$t('openatc.faultrecord.lightcontrolboard'),
        imgClass: 'lightcontrolboard',
        crossnum: 0
      }, {
        key: 3,
        name: this.$t('openatc.faultrecord.carinspectionboard'),
        imgClass: 'carinspectionboard',
        crossnum: 0
      }, {
        key: 4,
        name: this.$t('openatc.faultrecord.ioboard'),
        imgClass: 'ioboard',
        crossnum: 0
      }, {
        key: 5,
        name: this.$t('openatc.faultrecord.faultboard'),
        imgClass: 'faultboard',
        crossnum: 0
      }, {
        key: 6,
        name: this.$t('openatc.faultrecord.characteristicparams'),
        imgClass: 'characteristicparams',
        crossnum: 0
      }]
    }
  },
  methods: {
    removeDuplicateCross () {
      this.faultCrossMap = new Map()
      this.faultList.forEach(fault => {
        if (fault.m_byFaultBoardType !== undefined) {
          let faultTypeCross = this.faultCrossMap.get(fault.m_byFaultBoardType)
          if (faultTypeCross !== undefined) {
            faultTypeCross.push(fault)
            this.faultCrossMap.set(fault.m_byFaultBoardType, faultTypeCross)
          } else {
            let value = []
            value.push(fault)
            this.faultCrossMap.set(fault.m_byFaultBoardType, value)
          }
        }
      })
      // 同一故障类型去重，根据agentid
      for (let item of this.faultCrossMap.entries()) {
        let type = item[0]
        let faultcrosslist = item[1]
        var obj = {}
        faultcrosslist = faultcrosslist.reduce(function (item, next) {
          // eslint-disable-next-line no-unused-expressions
          obj[next.agentid] ? '' : obj[next.agentid] = true && item.push(next)
          return item
        }, [])
        this.faultCrossMap.set(type, faultcrosslist)
      }
    },
    getFaultCross () {
      this.removeDuplicateCross()
      this.mainFaultList = this.mainFaultList.map(faultitem => {
        let value = this.faultCrossMap.get(faultitem.key)
        if (value !== undefined) {
          faultitem.crossnum = value.length
        }
        return faultitem
      })
      // console.log(this.mainFaultList)
    },
    jumpToCurrentFault (filter) {
      this.$router.push({
        name: 'faultMgr',
        params: {filter}
      })
    },
    getLang () {
      return getLanguage()
    }
  },
  created () {
  },
  mounted () {
    this.getFaultCross()
  }
}
</script>
