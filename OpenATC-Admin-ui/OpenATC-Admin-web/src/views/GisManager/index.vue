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
  <div class="openatc-gis">
    <div id="map"></div>
      <transition name="slide">
        <div class="showLayout"  v-show="toggleShow">
            <div class="tabsconatiner">
              <div class="tabBox">
                <el-tabs :tab-position="tabPosition" style="height: 200px;">
                  <el-tab-pane label="设备状态">
                    <device
                      :devicesData="devList"
                      ref="device"
                      @setCurrent="setCurrentMarker"
                      @setDeviceLocation="setDeviceLocation">
                    </device>
                  </el-tab-pane>
                  <el-tab-pane label="特勤路线">特勤路线</el-tab-pane>
                  <el-tab-pane label="协调路线">协调路线</el-tab-pane>
                </el-tabs>
              </div>
            </div>
        </div>
      </transition>
      <div
        :class="[toggleshowisActive ? 'toggle_show' : 'active']"
        @click="handletoggleshow"
      >
        <lottie
          :options="defaultOptions"
          :width="30"
          :height="30"
          v-on:animCreated="handleAnimation"
        />
      </div>
    <div class="map-position">
      {{this.$t('openatc.devicemanager.longitude') }} {{ lngLat.lng }}
      {{this.$t('openatc.devicemanager.latitude') }}  {{ lngLat.lat }}
      {{this.$t('openatc.devicemanager.layerLevel') }} {{ zoom }}
    </div>
  </div>
</template>
<script>

import L from 'leaflet'
import { GetAllDevice } from '@/api/device'
import { SystemconfigApi } from '@/api/systemconfig.js'
import lottie from 'vue-lottie'
import device from './components/device'
import Anim from './toggleDataDark.json'
export default {
  components: {
    lottie,
    device
  },
  data () {
    return {
      tabPosition: 'right',
      citiesLayer: null,
      devList: [],
      lngLat: {
        lng: '0.00000000',
        lat: '0.00000000'
      },
      zoom: 12,
      minZoom: 3,
      maxZoom: 18,
      center: [31.23636, 121.53413],
      gis: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      toggleShow: true,
      toggleshowisActive: true,
      defaultOptions: { animationData: Anim, loop: false, autoplay: false },
      deviceInfo: null
    }
  },
  mounted () {
    this.init()
    // this.getGisConfig()
    // this.initMap()
    // this.addMapEvent()
  },
  methods: {
    async init () {
      await this.getGisConfig()
      await this.initMap()
      await this.addMapEvent()
    },
    initMap () {
      let _this = this
      let map = L.map('map', {
        minZoom: _this.minZoom,
        maxZoom: _this.maxZoom,
        // center: [39.550339, 116.114129],
        center: _this.center,
        zoom: _this.zoom,
        zoomControl: false,
        attributionControl: false,
        crs: L.CRS.EPSG3857,
        dragging: true,
        editMode: false
      })
      window.map = map
      L.tileLayer(
        _this.gis
      ).addTo(map)
      // L.tileLayer(
      //   'http://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=7&x={x}&y={y}&z={z}'
      // ).addTo(map)
      _this.map = map// data上需要挂载
      window.map = map
      _this.getAllAdevice()
    },
    addMapEvent () {
      let _this = this
      _this.map.on('mousemove', function (e) {
        _this.lngLat.lng = _this.computedLngLat(String(e.latlng.lat))
        _this.lngLat.lat = _this.computedLngLat(String(e.latlng.lng))
      })
      var boxMap = document.getElementById('map')
      L.DomEvent.on(boxMap, 'wheel', function (e) {
        _this.zoom = _this.map.getZoom()
      })
    },
    // 计算经纬度
    computedLngLat (num) {
      var text = ''
      var index = num.indexOf('.')
      if (index !== -1) {
        text = num.substring(0, index + 9)
      } else {
        text = num
      }
      return text
    },
    getAllAdevice () {
      this.map.off('click')
      GetAllDevice().then(res => {
        if (!res.data.success) {
          this.$message.error(res.data.message)
          return
        }
        this.devList = res.data.data
        this.handleMapDevice(this.devList)
      })
    },
    handleMapDevice (devs) {
      if (this.citiesLayer) {
        this.citiesLayer.clearLayers()
      }
      let markers = []
      for (let dev of devs) {
        if (dev.geometry === undefined) continue
        let coordinates = dev.geometry.coordinates
        let devPoint = [coordinates[1], coordinates[0]]
        let iconUrl = require('../../assets/gis/devicefault.png')
        if (dev.state === 'UP') {
          iconUrl = require('../../assets/gis/deviceonline.png')
        } else if (dev.state === 'DOWN') {
          iconUrl = require('../../assets/gis/devicenotonline.png')
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
      this.citiesLayer = L.layerGroup(markers)
      this.map.addLayer(this.citiesLayer)
      this.addMessage()
    },
    setCurrentMarker (dev) {
      let marker
      let layers = this.citiesLayer._layers
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
    onMarkerClick (e) {
      let dev = e.target.options.icon.options.alt
      let devicesTableData = this.$refs.device.devicesTableData
      let row = devicesTableData.filter(item => item.id === dev.id)[0]
      this.$refs.device.setCurrent(row)
    },
    hideLayer () {
      this.map.removeLayer(this.citiesLayer)
    },
    showLayer () {
      this.map.addLayer(this.citiesLayer)
    },
    addMessage () {
      let _this = this
      this.citiesLayer.eachLayer(function (layer) {
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
    handleAnimation (anim) {
      this.anim = anim
      this.anim.addEventListener('loopComplete', () => {
        console.log('当前循环下播放完成！')
      })
    },
    onSpeedChange (speed) {
      this.anim.setSpeed(speed)
    },
    handletoggleshow () {
      this.toggleshowisActive = !this.toggleshowisActive
      this.toggleShow = !this.toggleShow
      if (!this.toggleshowisActive) {
        this.onSpeedChange(0.2)
        this.anim.playSegments([0, 8], true)
      } else {
        this.onSpeedChange(0.2)
        this.anim.playSegments([17, 25], true)
      }
    },
    setDeviceLocation () {
      this.editMode = true
      let _this = this
      _this.map.on('click', function (e) {
        _this.lngLat.lng = _this.computedLngLat(String(e.latlng.lng))
        _this.lngLat.lat = _this.computedLngLat(String(e.latlng.lat))
        let device = _this.$refs.device
        device.setNewLocation(_this.lngLat)
        _this.map.off('click')
      })
    },
    getGisConfig () {
      return new Promise((resolve, reject) => {
        SystemconfigApi.GetSystemconfigByModule('gis').then((data) => {
          let res = data.data
          if (!res.success) {
            console.log('datas:' + res)
            throw new Error('get gis error')
          } else {
            for (let config of data.data.data) {
              if (config['key'] === 'minZoom') {
                this.minZoom = Number(config['value'])
              }
              if (config['key'] === 'maxZoom') {
                this.maxZoom = Number(config['value'])
              }
              if (config['key'] === 'center') {
                let cen = config['value']
                let ss = cen.split(',')
                let s1 = ss[0].replace('[', '')
                let s2 = ss[1].replace(']', '').trim()
                this.center = [Number(s1), Number(s2)]
              }
              if (config['key'] === 'zoom') {
                this.zoom = Number(config['value'])
              }
              if (config['key'] === 'gis') {
                this.gis = config['value'] + ''
                // this.gis = 'http://192.168.14.168:7080/PBS/rest/services/MyPBSService1/MapServer/tile/{z}/{y}/{x}'
              }
            }
            resolve(data.data.data)
          }
        })
      })
    }
  }
}
</script>
<style scoped>
#map {
  position: relative;
  width: 100%;
  height: 94.5vh;
}
.map-position {
  width: 18rem;
  height: 1.1rem;
  line-height: 1.1rem;
  background-color: #30353b;
  border-radius: 0.1rem;
  text-align: center;
  user-select: none;
  position: absolute;
  bottom: 1rem;
  right: 3.3rem;
  z-index: 903;
  font-size: 0.6rem;
  color: rgba(254, 254, 254, 0.7);
}

.tabsconatiner {
  margin: 10px;
  position: relative;
  width: 100% - 20px;
  height: 100% - 20px;
  opacity: 0.95;
}
.addbtn {
  position: absolute;
  right: 5px;
  /* z-index: 99; */
  top: 7px;
  width: auto;
  height: auto;
}
.addicon {
  color: #42daff;
  font-size: 26px;
}
.addicon:hover {
  color: rgb(32, 163, 195);
}
.toggle_show {
  position: absolute;
  cursor: pointer;
  right: 17px;
  top: 70px;
  z-index:100001;
}
.active {
  position: absolute;
  cursor: pointer;
  right: 17px;
  top: 70px;
  z-index:100001;
}
.init-toggle {
  position: absolute;
  cursor: pointer;
  right: 440px;
  top: 0px;
}
.slide-enter-active {
  transition: all 0.5s linear;
}
.slide-leave-active {
  transition: all 0.5s linear;
}
.slide-enter {
  transform: translateX(100%);
  opacity: 0;
}
.slide-leave-to {
  transform: translateX(100%);
  opacity: 0;
}
</style>
<style>
.leaflet-popup-content-wrapper {
  padding: 1px;
  text-align: left;
  border-radius: 6px;
}
.leaflet-popup {
  position: absolute;
  text-align: center;
  margin-bottom: 40px;
}
</style>

<style lang="scss">
.tabBox{
  margin-top: 20px;
  .el-tabs--right{
    height: auto !important;
    .el-tabs__item{
      width: 40px;
      line-height: 24px;
      height: auto;
      word-wrap: break-word;
      white-space: pre-line;
      margin-bottom: 20px;
    }
  }
}
</style>
