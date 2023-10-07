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
  <div class="gis-duteRoute">
    <div class="title">{{route ? route.name : '特勤路线'}}</div>
    <div>
      <div class="devicePanel">
        <routePreview ref="updateChild" :route="route"></routePreview>
      </div>
    </div>
    <select-control v-show="false" ref="selectControl"></select-control>
  </div>
</template>
<script>
import L from 'leaflet'
import 'leaflet-polylinedecorator'
import { GetAllViproutes, GetSingleViproute, GetViprouteStatus } from '@/api/service'
import routePreview from './routePreview'
import SelectControl from '@/views/Service/components/SelectControl'

import { GetDeviceByIds } from '@/api/device'
export default {
  name: 'device',
  components: { routePreview, SelectControl },
  props: {
  },
  data () {
    return {
      map: null,
      lngLat: {
        lng: '0.00000000',
        lat: '0.00000000'
      },
      editDevicevisible: false,
      devicesTableData: [],
      messageboxVisible: false,
      listLoading: false, // 表格数据加载等待动画
      curDevice: undefined, // 当前所选设备信息
      loading: false,
      deviceFaultIcon: require('@/assets/gis/devicefault.png'),
      deviceOnlineIcon: require('@/assets/gis/deviceonline.png'),
      deviceNotOnlineIcon: require('@/assets/gis/devicenotonline.png'),
      deviceLayer: null,
      devList: [],
      routeList: [],
      lineArr: [],
      chooseId: 0,
      options: {
        weight: 8
      },
      newOptions: {
        weight: 12,
        color: 'green'
      },
      route: {}
    }
  },
  watch: {
    chooseId (val) {
      this.setRoute()
    }
  },
  mounted () {
    let _this = this
    this.$nextTick(() => {
      _this.map = window.map
      _this.getList()
    })
  },
  destroyed () {
    this.hideLayer()
  },
  methods: {
    getControlName (control) {
      let res
      res = this.$refs.selectControl.getNameById(control)
      return res
    },
    getSingleViproute () {
      let _this = this
      return new Promise((resolve, reject) => {
        GetSingleViproute(_this.chooseId).then(res => {
          if (!res.data.success) {
            _this.$message.error(res.data.message)
            return
          }
          _this.routeData = res.data.data
          if (!_this.routeData || _this.routeData.length === 0) {
            _this.maskVisible = true
            // 模拟一次手动点击，触发tip显示
            _this.$refs.editbtn.click()
          }
          resolve(_this.routeData)
        })
      })
    },
    getViprouteStatus () {
      let _this = this
      return new Promise((resolve, reject) => {
        GetViprouteStatus(_this.chooseId).then(res => {
          if (!res.data.success) {
            _this.$message.error(res.data.message)
            resolve()
          }
          let stateList = res.data.data
          _this.stateList = stateList
          resolve(stateList)
        })
      })
    },
    getDeviceByIds () {
      // 获取设备表格信息
      this.devicesData = []
      let _this = this
      return new Promise((resolve, reject) => {
        _this.deviceIds = _this.routeData.devs.map(ele => ele.agentid)
        GetDeviceByIds(_this.deviceIds).then(res => {
          if (!res.data.success) {
            _this.$message.error(res.data.message)
            resolve()
          }
          _this.devicesData = res.data.data
          resolve(_this.devicesData)
        })
      })
    },
    async setRoute () {
      if (!this.chooseId || this.chooseId === 0) {
        return false
      }
      await this.getSingleViproute()
      await this.getViprouteStatus()
      await this.getDeviceByIds()
      for (let item of this.routeData.devs) {
        for (let state of this.stateList) {
          if (item.agentid === state.agentid) {
            item.state = state.state
            item.resttime = state.resttime
            let controlName = this.getControlName(item.control)
            item.controlName = controlName
            for (let dev of this.devicesData) {
              if (item.agentid === dev.agentid) {
                item.stateName = dev.state
                break
              }
            }
            break
          }
        }
      }
      if (!this.routeData || this.routeData.devs.length === 0) {
        this.maskVisible = true
        // 模拟一次手动点击，触发tip显示
        this.$refs.editbtn.click()
      }
      this.route = this.routeData
    },
    getAllAdevice () {
      GetAllViproutes().then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.routeList = res.data.data
        this.devicesTableData = this.routeList
        this.chooseId = this.routeList[0].id
        this.drawMarkers()
        this.drawLines()
      })
    },
    drawMarkers () {
      this.devList = []
      for (let item of this.routeList) {
        for (let dev of item.devs) {
          this.devList.push(dev)
        }
      }
      this.handleMapDevice(this.devList)
    },
    drawLines () {
      // this.hideLayer()
      for (let item of this.routeList) {
        let latlngs = []
        for (let dev of item.devs) {
          let coordinates = dev.geometry.coordinates
          if (!coordinates) {
            console.log('no geometry: ' + dev)
            continue
          }
          latlngs.push(coordinates.reverse())
        }
        if (latlngs) {
          let options = {
            weight: 8
          }
          if (item.id === this.chooseId) {
            options = {
              weight: 12,
              color: 'green'
            }
          }
          let res = this.drawLine(latlngs, options)
          let record = {
            id: res.lineId,
            routeLine: res.routeLine,
            decorator: res.decorator,
            route: item
          }
          this.lineArr.push(record)
          console.log(this.lineArr)
        }
      }
    },
    handleMapDevice (devs) {
      if (this.deviceLayer) {
        this.deviceLayer.clearLayers()
      }
      let markers = []
      for (let dev of devs) {
        if (dev.geometry === undefined) continue
        let coordinates = dev.geometry.coordinates
        let devPoint = [coordinates[1], coordinates[0]]
        let iconUrl = this.deviceOnlineIcon
        if (dev.state === 'UP') {
          iconUrl = this.deviceOnlineIcon
        } else if (dev.state === 'DOWN') {
          iconUrl = this.deviceNotOnlineIcon
        } else if (dev.state === 'Fault') {
          iconUrl = this.deviceFaultIcon
        }
        let notOnlineIcon = L.icon({
          iconUrl: iconUrl,
          iconSize: [24, 27],
          title: dev.state,
          alt: dev,
          iconAnchor: [12, 27]
        })
        let marker = L.marker(devPoint, { icon: notOnlineIcon }).on('click', this.onMarkerClick)
        // 添加marker来设置点击事件
        markers.push(marker)
      }
      this.deviceLayer = L.layerGroup(markers)
      this.map.addLayer(this.deviceLayer)
      this.addMessage()
    },
    onMarkerClick (e) {
      let dev = e.target.options.icon.options.alt
      let row = this.devList.filter(item => item.id === dev.id)[0]
      this.setCurrent(row)
    },
    addMessage () {
      let _this = this
      this.deviceLayer.eachLayer(function (layer) {
        let options = layer.options.icon.options
        let devData = options.alt
        let content = _this.getPopupContent(devData)
        layer.bindPopup(content)
      })
    },
    getPopupContent (devData) {
      let agentid = devData.agentid
      let date = devData.lastTime
      let status = '在线'
      if (devData.state === 'UP') {
        status = '在线'
      } else if (devData.state === 'DOWN') {
        status = '离线'
      } else {
        status = '故障'
      }
      let content =
      `
        <div>设备${agentid}</div>
        <div>${status}</div>
        <div>${date}</div>
      `
      return content
    },
    getTag (row) {
      if (row.state === 'DOWN') {
        return {
          label: this.$t('openatc.devicemanager.offline'),
          type: 'info'
        }
      } else {
        if (row.status === 0) {
          // 数据从设备端来，暂时写死，0代表正常状态，其余数字均代表一种类型的故障
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
    },
    handleEdit (row) {
      this.curDevice = row
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick(row)
    },
    setNewLocation (lngLat) {
      this.curDevice.lng = lngLat.lng
      this.curDevice.lat = lngLat.lat
      let updateChild = this.$refs.updateChild
      updateChild.onUpdateClick(this.curDevice, true)
    },
    onLocationClick (row) {
      this.setDeviceLocation(row)
      this.$message.info(this.$t('openatc.gis.chooseLocationInfo'))
    },
    setDeviceLocation () {
      this.editMode = true
      let _this = this
      _this.map.on('click', function (e) {
        _this.lngLat.lng = String(e.latlng.lng)
        _this.lngLat.lat = String(e.latlng.lat)
        _this.setNewLocation(_this.lngLat)
        _this.map.off('click')
      })
    },
    onDetailClick (row) {
      const dev = row
      let edgeModalChild = this.$refs.edgeModalChild
      edgeModalChild.openSingleEdge(dev)
    },
    getList () {
      // this.hideLayer(this.deviceLayer)
      this.getAllAdevice()
    },
    hideLayer () {
      this.map.removeLayer(this.deviceLayer)
      for (let i = 0; i < this.lineArr.length; i++) {
        let item = this.lineArr[i]
        this.map.removeLayer(item.routeLine)
        this.map.removeLayer(item.decorator)
      }
      this.lineArr = []
    },
    showLayer () {
      this.map.addLayer(this.deviceLayer)
    },
    setCurrent (row) {
      this.$refs.updateChild.setCurrentRow(row)
    },
    handleCurrentChange (val) {
      this.currentRow = val
    },
    setCurrentMarker (dev) {
      let marker
      let layers = this.deviceLayer._layers
      for (var x in layers) {
        let layer = layers[x]
        if (layer.options.icon.options.alt.id === dev.id) {
          marker = layer
          break
        }
      }
      if (marker) {
        let _latlng = marker._latlng
        this.map.flyTo(_latlng)
        marker.openPopup(_latlng)
      }
    },
    onRowClick (row) {
      this.setCurrentMarker(row)
    },
    highLightLine (latlngs, id) {
      let _this = this
      for (let i = 0; i < _this.lineArr.length; i++) {
        let item = _this.lineArr[i]
        _this.map.removeLayer(item.routeLine)
        _this.map.removeLayer(item.decorator)
        let lineId = item.id
        let newOptions = _this.options
        if (id === lineId) {
          newOptions = _this.newOptions
          let res = _this.drawLine(latlngs, newOptions)
          let record = {
            id: res.lineId,
            routeLine: res.routeLine,
            decorator: res.decorator,
            route: item.route
          }
          _this.lineArr.splice(i, 1, record)
        }
      }
    },
    drawLine (latlngs, options) {
      let res
      // 轨迹线
      let _this = this
      let routeLine = L.polyline(latlngs, options).addTo(_this.map).on('click', function (e) {
        let id = e.target._leaflet_id
        let item = _this.lineArr.filter(ele => ele.id === id)[0]
        if (_this.chooseId === item.route.id) {
          console.log('no change')
          return false
        }
        // tab
        _this.chooseId = item.route.id
        // _this.hideLayer()
        // debugger
        // _this.drawLines()
        // 高亮
        // _this.highLightLine(latlngs)
      })
      // 轨迹方向箭头
      let decorator = L.polylineDecorator(routeLine, {
        patterns: [{
          repeat: 50,
          symbol: L.Symbol.arrowHead({
            pixelSize: 5,
            headAngle: 75,
            polygon: false,
            pathOptions: {
              stroke: true,
              weight: 2,
              color: '#FFFFFF'
            }
          })
        }]
      }).addTo(this.map)
      let lineId = routeLine._leaflet_id
      res = {
        lineId: lineId,
        routeLine: routeLine,
        decorator: decorator
      }
      return res
    }
  }
}
</script>
