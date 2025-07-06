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
  <div class="layer-control">
  </div>
</template>
<script>
import L from 'leaflet'
import { GetAllDevice } from '@/api/device'
import PhaseMarker from '@/components/PhaseMarker'
import { getMessageByCode } from '@/utils/responseMessage'
import PhaseMarkerVue from './index'
import store from '@/store'
import { getPhaseControlName } from '@/utils/PhaseStateUtil.js'
import { getTscPhase } from '@/api/route'
export default {
  name: 'layerControl',
  components: { PhaseMarker },
  props: {
    targetDeviceIds: {
      type: Array,
      default: () => []
    },
    showLevel: {
      type: Number,
      default: 3
    },
    isShowPattern: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    targetDeviceIds (val, old) {
      this.hideLayer()
      if (this.isShowPattern) {
        this.showLayer()
      }
    },
    isShowPattern (val) {
      if (val) {
        this.showLayer()
      } else {
        this.hideLayer()
      }
    }
  },
  data () {
    return {
      map: null,
      patternLayer: null,
      devList: [],
      dirshow: [],
      showList: [],
      domMap: new Map()
    }
  },
  destroyed () {
    this.hideLayer()
  },
  methods: {
    getAllAdevice () {
      GetAllDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(getMessageByCode(res.data.code, this.$i18n.locale))
          return
        }
        this.devList = res.data.data
        this.map = window.map
        let list = this.getShowList()
        this.handleMapDevice(list)
      })
    },
    getShowList () {
      let list = this.devList.filter(dev => {
        return dev.state !== 'DOWN'
      })
      if (this.targetDeviceIds && this.targetDeviceIds.length > 0) {
        list = list.filter(dev => {
          return this.targetDeviceIds.includes(dev.agentid)
        })
      }
      this.showList = list
      return list
    },
    handleMapDevice (devs) {
      if (this.patternLayer) {
        this.patternLayer.clearLayers()
      }
      let markers = []
      this.patternLayer = L.layerGroup(markers)
      this.map.addLayer(this.patternLayer)
      for (let dev of devs) {
        let row = this.devList.filter(item => item.id === dev.id)[0]
        let htmlStr =
          `
            <div id="test1">
            </div>
          `
        let coordinates = row.geometry.coordinates.slice().reverse()
        // let iconAnchor = [coordinates[0] + 110, coordinates[1] - 20]
        let iconAnchor = [coordinates[0] - 50, coordinates[1] - 80]
        let icon = L.divIcon({
          html: htmlStr,
          iconSize: [80, 80],
          iconAnchor: iconAnchor,
          className: 'map-circle'
        })
        L.marker(coordinates, {icon: icon}).addTo(this.patternLayer)
        let dom = new PhaseMarkerVue({})
        dom.$mount(`#test1`)
        this.domMap.set(dev.agentid, dom)
      }
      this.doSub(true)
    },
    getList () {
      this.hideLayer(this.patternLayer)
      this.getAllAdevice()
    },
    hideLayer () {
      this.doSub(false)
      if (this.map) {
        this.map.removeLayer(this.patternLayer)
      }
    },
    showLayer () {
      let _this = this
      this.$nextTick(() => {
        _this.getAllAdevice()
      })
    },
    async recvMessage (message) {
      let item = message
      if (item.hasOwnProperty('agentid')) {
        console.log(item.agentid)
        console.log(item)
        let phase = item.data.phase
        let curpattern = item.data.name
        let cycle = item.data.cycle + 's'
        let offset = item.data.offset + 's'
        let control = getPhaseControlName(item.data.control)
        let currentPhase = item.data.current_phase
        let res = this.getCutDown(currentPhase, phase)
        let dom = this.domMap.get(item.agentid)
        let dirshow = []
        await this.getTscParam(item.agentid)
        this.getCurPhaseStatus(currentPhase, phase)
        dirshow = this.dirshow
        let params = {
          phaseCountdownColor: this.phaseCountdownColor,
          countdown: res.countdown,
          split: res.split,
          control: control,
          cycle: cycle,
          offset: offset,
          curpattern: curpattern
        }
        dom.show(dirshow, params, this.showLevel)
      }
    },
    // 获取当前相位列表
    getCurPhaseStatus (currentPhase, phase) {
      this.dirshow = []
      for (let phaseId of currentPhase) {
        this.getDirShow(phaseId, phase)
      }
    },
    // 根据类型获取相色
    getColorByType (type) {
      let color = '#333333'
      switch (type) {
        case 1:
          color = 'red'
          break
        case 2:
          color = '#eabb24'
          break
        case 3:
          color = 'green'
          break
        case 4:
          color = 'green'
          break
        case 5:
          color = '#eabb24'
          break
        default: break
      }
      return color
    },
    getCutDown (currentPhase, phase) {
      let res = {
        id: 0,
        countdown: 0,
        split: 0,
        type: 1
      }
      if (currentPhase.length >= 2) {
        let curPhaseState = phase.filter((item) => {
          return item.id === currentPhase.find((val) => {
            return item.id === val
          })
        })
        let min = Math.min.apply(Math, curPhaseState.map(o => { return o.countdown === undefined ? 0 : o.countdown }))
        let ret = phase.filter(v => v.countdown === min)
        if (ret && ret.length > 0) {
          res = ret[0]
        }
      } else if (currentPhase.length <= 0) {
        res = {
          id: 0,
          countdown: 0,
          split: 0,
          type: 1
        }
      } else { // 1
        res = phase.filter(v => v.id === currentPhase[0])[0]
      }
      return res
    },
    // 根据id获取相位的方向数组
    getDirShow (id, phase) {
      let phaseStatus = phase.filter(item => item.id === id)[0]
      let planphaseDir = this.planphaselist.filter(item => item.id === id)[0]
      let color = this.getColorByType(phaseStatus.type)
      this.phaseCountdownColor = color
      for (let dir of planphaseDir.direction) {
        this.dirshow.push({
          id: dir,
          color: color
        })
      }
    },
    getTscParam (devid) {
      let _this = this
      return new Promise((resolve, reject) => {
        getTscPhase(devid).then((res) => {
          if (res.data.success !== true) {
            return
          }
          let params
          try {
            params = res.data.data.data
            _this.planphaselist = params.phaseList
          } catch (error) {
            reject(error)
          }
          resolve(params)
        })
      })
    },
    getReqData () {
      let reqData = []
      for (let dev of this.showList) {
        let devtype = dev.type
        let agentid = dev.agentid
        let simuid = devtype + ':status/pattern:' + agentid
        reqData.push(simuid)
      }
      return reqData
    },
    async doSub (flag) {
      let ssSubMgr = store.getters['wsSubMgr']
      this.wsSubMgr = ssSubMgr
      let CrossStatSubMgr = this.wsSubMgr.getByName('CrossState')
      this.sub = CrossStatSubMgr.getSubByType('status/pattern')
      // this.sub.start([simuid]) // wholist = []
      let reqData = this.getReqData()
      if (!flag) {
        this.sub.stop(reqData)
      } else {
        this.sub.start(reqData)
        this.sub.addCare(this.recvMessage, this)
      }
    }
  }
}
</script>
