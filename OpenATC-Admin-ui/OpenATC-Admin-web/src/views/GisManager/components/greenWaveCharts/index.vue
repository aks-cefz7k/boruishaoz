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
  <div class="gis-planchart" style="width: 100%; height: 600px;">
    <div class="title">{{routeName}}</div>
    <div class="planchart-name">{{$t('openatc.devicemanager.timeSpaceGraph')}}</div>
    <div id="echarts" style="width: 100%; height: 540px;"></div>
</div>
</template>

<script>
import echarts from 'echarts'
import L from 'leaflet'
import CDTModel from '../../../compose/components/planChart/coordinationModel.js'
import { GetAllRoute, getAllPatternOfRoute } from '@/api/route'
import { GetAllDevice } from '@/api/device'
export default {
  name: 'echartsStyle',
  components: {},
  data () {
    return {
      routeOptimizeMap: new Map(),
      allDevice: [],
      routeName: '--',
      routeLayer: '',
      deviceMarks: [],
      deviceGroupLayer: '',
      deviceFaultIcon: require('@/assets/gis/devicefault.png'),
      deviceOnlineIcon: require('@/assets/gis/deviceonline.png'),
      deviceNotOnlineIcon: require('@/assets/gis/devicenotonline.png'),
      direction: '',
      routerData: {},
      isShowUpCard: false,
      isShowDownCard: false,
      green: [],
      newRouteOptions: {
        color: '#409EFF'
      },
      routeOptions: {
        color: '#909399'
      },
      chooseRouteId: 0,
      oldPolyline: '',
      containerWidth: 0
    }
  },
  mounted () {
    const _this = this
    var w = document.getElementsByClassName('showLayout')[0]
    this.containerWidth = w.style.width
    w.style.width = '700px'
    var dom = document.getElementById('echarts')
    _this.myChart = echarts.init(dom)
    _this.CDTModel = new CDTModel()
    window.onresize = function () {
      _this.myChart.resize()
    }
    _this.$nextTick(() => {
      _this.init()
    })
  },
  created () {
  },
  methods: {
    async init () {
      this.map = window.map
      await this.getAllDevice()
      this.getAllRouteOptimize()
    },
    getAllDevice () {
      return new Promise((resolve, reject) => {
        GetAllDevice().then((data) => {
          let res = data.data
          if (res.success) {
            this.allDevice = res.data
            resolve(res.data)
          } else {
            console.log(res.message)
            resolve([])
          }
        }, () => {
          console.log('GetAllDevice Error')
          reject(new Error('GetAllDevice Error'))
        })
      })
    },
    getAllRouteOptimize () {
      if (this.routeLayer !== '') {
        console.log('协调路线图层已存在！')
      }
      GetAllRoute().then((data) => {
        // 获取所有路线信息成功
        let res = data.data
        if (res.success) {
          this.allRoute = res.data
          this.createRouteOptimize(this.allRoute)
        } else {
          console.log(data.message)
        }
      }, () => {
        console.log('getAllRouteOptimize Error')
      })
    },
    createRouteOptimize (dataList) {
      let _this = this
      let polylines = []
      this.deviceMarks = []
      let i = 0
      for (let data of dataList) {
        if (data.devs.length === 0) continue
        i++
        let geometry = _this.getGeometry(data)
        // let routeId = data.id
        let status = data.enable
        var polylineOptions = {}
        // var polylineOptions = {
        //   // color: status ? '#67c23a' : '#007dc5',
        //   color: '#909399',
        //   weight: 8,
        //   routeId: data.id,
        //   status: status,
        //   name: data.name,
        //   keyintsid: data.keyintsid
        //   // opacity: 0.9
        // }
        if (i === 1) {
          polylineOptions = {
          // color: status ? '#67c23a' : '#007dc5',
            color: '#409EFF',
            weight: 8,
            routeId: data.id,
            status: status,
            name: data.name,
            keyintsid: data.keyintsid,
            geometry: geometry
          // opacity: 0.9
          }
          this.chooseRouteId = data.id
        } else {
          polylineOptions = {
          // color: status ? '#67c23a' : '#007dc5',
            color: '#909399',
            weight: 8,
            routeId: data.id,
            status: status,
            name: data.name,
            keyintsid: data.keyintsid,
            geometry: geometry
          // opacity: 0.9
          }
        }
        let polyline = L.polyline(geometry, polylineOptions).on('click', this.onPolylineClick)
        if (i === 1) {
          this.oldPolyline = polyline
          this.showPlanchart(data.id)
          this.$emit('onRouteChange', geometry)
        }
        polylines.push(polyline)
        // _this.map.addLayer(polyline)
      }
      this.deviceGroupLayer = L.layerGroup(this.deviceMarks)
      _this.map.addLayer(this.deviceGroupLayer)
      _this.routeLayer = L.layerGroup(polylines)
      _this.map.addLayer(_this.routeLayer)
      this.addRoutePopup()
    },
    onPolylineClick (e) {
      let routeId = e.target.options.routeId
      if (this.chooseRouteId !== routeId) {
        e.target.setStyle(this.newRouteOptions)
        this.oldPolyline.setStyle(this.routeOptions)
        this.oldPolyline = e.target
        this.chooseRouteId = routeId
        let geometry = e.target.options.geometry
        this.$emit('onRouteChange', geometry)
      }
      // e.target.setStyle(this.newRouteOptions)
      this.showPlanchart(routeId)
    },
    addRoutePopup () {
      let _this = this
      _this.routeLayer.eachLayer(function (layer) {
        let content = _this.getPopupContent(layer.options)
        layer.bindPopup(content)
      })
    },
    getPopupContent (data) {
      let routeName = data.name
      // let status = data.status
      let keyintsid = data.keyintsid === undefined ? '--' : data.keyintsid
      let html = ''
      // if (status) {
      //   // html = '<div><div style="font-size: 16px; font-weight: bold; font-stretch: normal; color: #303133;">' + routeName + '<div style="float: right; height: 22px; background-color: #ecf5ff; border-radius: 2px; border: solid 1px #d9ecff;"><span style="margin: 5px; font-size: 12px; font-weight: normal; font-stretch: normal; letter-spacing: 0px; color: #409eff;">启用</span></div></div><div style="width: 150px; height: 1px; background-color: #004e61; border: solid 1px #e7e7e7; margin-top: 10px;"></div><div style="font-size: 14px; font-weight: normal; font-stretch: normal; color: #303133; margin-top: 10px;">' + this.$t('openatc.devicemanager.keyintersection') + keyintsid + '</div></div></div>'
      //   html = '<div><div style="font-size: 16px; font-weight: bold; font-stretch: normal; color: #303133;">' + routeName + '</div><div style="width: 150px; height: 1px; background-color: #004e61; border: solid 1px #e7e7e7; margin-top: 10px;"></div><div style="font-size: 14px; font-weight: normal; font-stretch: normal; color: #303133; margin-top: 10px;">' + this.$t('openatc.devicemanager.keyintersection') + keyintsid + '</div></div></div>'
      // } else {
      //   // html = '<div><div style="font-size: 16px; font-weight: bold; font-stretch: normal; color: #303133;">' + routeName + '<div style="float: right; height: 22px; background-color: #f4f4f5; border-radius: 2px; border: solid 1px #e9e9eb;"><span style="margin: 5px; font-size: 12px; font-weight: normal; font-stretch: normal; letter-spacing: 0px; color: #909399;">未启用</span></div></div><div style="width: 150px; height: 1px; background-color: #004e61; border: solid 1px #e7e7e7; margin-top: 10px;"></div><div style="font-size: 14px; font-weight: normal; font-stretch: normal; color: #303133; margin-top: 10px;">' + this.$t('openatc.devicemanager.keyintersection') + keyintsid + '</div></div></div>'
      //   html = '<div><div style="font-size: 16px; font-weight: bold; font-stretch: normal; color: #303133;">' + routeName + '</div><div style="width: 150px; height: 1px; background-color: #004e61; border: solid 1px #e7e7e7; margin-top: 10px;"></div><div style="font-size: 14px; font-weight: normal; font-stretch: normal; color: #303133; margin-top: 10px;">' + this.$t('openatc.devicemanager.keyintersection') + keyintsid + '</div></div></div>'
      // }
      html = '<div><div style="font-size: 16px; font-weight: bold; font-stretch: normal; color: #303133;">' + routeName + '</div><div style="width: 150px; height: 1px; background-color: #004e61; border: solid 1px #e7e7e7; margin-top: 10px;"></div><div style="font-size: 14px; font-weight: normal; font-stretch: normal; color: #303133; margin-top: 10px;">' + this.$t('openatc.devicemanager.keyintersection') + keyintsid + '</div></div></div>'
      return html
    },
    getGeometry (data) {
      let devs = data.devs
      devs.sort((a, b) => {
        return a.sortid > b.sortid
      })
      let coordinatesList = []
      for (let dev of devs) {
        let agentid = dev.agentid
        let deviceInfo = this.allDevice.filter((item) => {
          return item.agentid === agentid
        })
        if (deviceInfo.length === 0 || deviceInfo[0].geometry === undefined) continue
        let latlngs = [deviceInfo[0].geometry.coordinates[1], deviceInfo[0].geometry.coordinates[0]]
        coordinatesList.push(latlngs)
        let iconUrl = this.deviceFaultIcon
        if (deviceInfo[0].state === 'UP') {
          iconUrl = this.deviceOnlineIcon
        } else if (deviceInfo[0].state === 'DOWN') {
          iconUrl = this.deviceNotOnlineIcon
        }
        let notOnlineIcon = L.icon({
          iconUrl: iconUrl,
          iconSize: [24, 27],
          // title: dev.state,
          // alt: dev,
          iconAnchor: [12, 27]
        })
        let deviceMark = L.marker(latlngs, { icon: notOnlineIcon }).addTo(this.map)
        this.deviceMarks.push(deviceMark)
      }
      return coordinatesList
    },
    getAllPatternOfRoute (id) {
      return new Promise((resolve, reject) => {
        getAllPatternOfRoute(id).then((data) => {
          let res = data.data
          if (res.success) {
            // this.allDevice = res.data
            resolve(res.data.devs)
          } else {
            if (res.code === '4003') {
              let agentid = res.data.agentid
              // this.$message.error('设备' + agentid + '不在线!')
              this.$message.error(this.$t('openatc.greenwaveoptimize.device') + agentid + this.$t('openatc.greenwaveoptimize.notonline'))
              // return
            }
            console.log(res.message)
            resolve([])
          }
        }, () => {
          console.log('GetAllDevice Error')
          reject(new Error('GetAllDevice Error'))
        })
      })
    },
    async showPlanchart (routeId) {
      this.clearMyChart()
      let greenwave = []
      let routeData = this.allRoute.filter((item) => {
        return item.id === routeId
      })[0]
      this.routeName = routeData.name
      let patternList = await this.getAllPatternOfRoute(routeId)
      if (greenwave.length === 0) {
        this.isShowUpCard = false
        this.isShowDownCard = false
      } else {
        this.green = greenwave
        if (this.direction === 'up') {
          this.isShowUpCard = true
        } else if (this.direction === 'down') {
          this.isShowDownCard = true
        } else if (this.direction === 'all') {
          this.isShowUpCard = true
          this.isShowDownCard = true
        }
        this.CDTModel.greenwave = greenwave
      }
      if (patternList.length === 0) {
        // this.$message.error('方案为空！')
        return
      }
      let devs = routeData.devs
      for (let inter of devs) {
        // let obj = {};
        // obj.label = inter.agentid;
        // obj.value = inter.agentid;
        // this.keyintsidOptions.push(obj);
        inter.patternList = []
        let id = inter.agentid
        let patternId = inter.patternid
        let pattern = patternList.filter(pat => pat.agentid === id)[0]
        if (pattern.feature !== undefined && pattern.feature.patternList.length !== 0) {
          let allPatternList = pattern.feature.patternList
          if (Array.isArray(allPatternList)) {
            let currPatternList = allPatternList.filter(apl => apl.id === patternId)
            inter.patternList = currPatternList
          } else {
            let tempList = []
            tempList.push(allPatternList)
            inter.patternList = tempList
          }
        }
      }
      this.CDTModel.rourte = routeData
      this.routerData = routeData
      this.clearMyChart()
      this.myChart.setOption(this.CDTModel.RenderOption())
    },
    clearMyChart () {
      if (this.myChart.getOption() !== undefined) {
        this.myChart.clear()
      }
    },
    clearLayer () {
      let _this = this
      if (_this.routeLayer !== '') {
        _this.map.removeLayer(_this.routeLayer)
        _this.routeLayer = ''
      }
      if (_this.deviceGroupLayer !== '') {
        _this.map.removeLayer(_this.deviceGroupLayer)
        // _this.deviceGroupLayer.clearLayers()
        _this.deviceGroupLayer = ''
      }
      if (_this.chooseRouteId !== 0) {
        _this.chooseRouteId = 0
      }
      if (_this.oldPolyline !== '') {
        _this.oldPolyline = ''
      }
    }
  },
  destroyed () {
    this.clearLayer()
    var w = document.getElementsByClassName('showLayout')[0]
    w.style.width = this.containerWidth
  }
}
</script>

<style lang="scss" scoped>
.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 5px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
</style>
