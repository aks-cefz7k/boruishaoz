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
    <div class="showLayout">
      <transition name="slide">
        <div class="tabsconatiner" v-show="toggleShow">
          <device
            :devicesData="devList"
            ref="device"
            @setCurrent="setCurrentMarker"
            @setDeviceLocation="setDeviceLocation"
          ></device>
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
    </div>
    <div class="map-position">
      经度{{ lngLat.lng }} 纬度{{ lngLat.lat }} 层级 {{ zoom }}
    </div>
  </div>
</template>
<script>

import L from 'leaflet'
import { GetAllDevice } from '@/api/device'
import lottie from 'vue-lottie'
import device from './components/device'
import Anim from './toggleData.json'
export default {
  components: {
    lottie,
    device
  },
  data () {
    return {
      citiesLayer: null,
      devList: [],
      lngLat: {
        lng: '0.00000000',
        lat: '0.00000000'
      },
      zoom: 12,
      toggleShow: true,
      toggleshowisActive: true,
      defaultOptions: { animationData: Anim, loop: false, autoplay: false },
      deviceInfo: null
    }
  },
  mounted () {
    this.initMap()
    this.addMapEvent()
  },
  methods: {
    initMap () {
      let map = L.map('map', {
        minZoom: 3,
        maxZoom: 18,
        // center: [39.550339, 116.114129],
        center: [31.23636, 121.53413],
        zoom: 12,
        zoomControl: false,
        attributionControl: false,
        crs: L.CRS.EPSG3857,
        dragging: true,
        editMode: false
      })
      window.map = map
      L.tileLayer(
        'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
      ).addTo(map)
      // L.tileLayer(
      //   'http://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=7&x={x}&y={y}&z={z}'
      // ).addTo(map)
      this.map = map// data上需要挂载
      window.map = map
      this.getAllAdevice()
      // var markers = []
      // // 系统默认的marker,有一个蓝色图标
      // //   var marker1 = L.marker([39.550339, 116.115129])
      // //   // 自定义的circleMarker
      // //   var marker2 = L.circleMarker([39.550339, 116.126129], {
      // //     stroke: true,
      // //     color: '#aaaaaa',
      // //     weight: 1,
      // //     opacity: 1,
      // //     fillColor: '#00e400',
      // //     fillOpacity: 1,
      // //     radius: 10
      // //   })
      // //   // 用html的div来创建icon，但是有缺陷
      // //   var icon3 = L.divIcon({
      // //     html: '<div style=\'width: 15px;height:15px;border-radius: 50%;background-color:#00e400 ;\'></div>',
      // //     iconAnchor: [1, 1]})
      // //   var marker3 = L.marker([39.550339, 116.197129], { icon: icon3 })
      // //   // 用html的div的样式来创建marker的icon，注意需要加className属性，否则会出现marker1的问题
      // //   var icon4 = L.divIcon({
      // //     html: '<div style=\'width:24px;height:24px;border-radius:4px;background-color:#00e400 ;\'></div>',
      // //     className: 'ss' })
      // //   var marker4 = L.marker([39.550339, 116.148129], { icon: icon4 })
      // var myIcon = L.icon({
      //   iconUrl: require('../../assets/gis/deviceonline.png'),
      //   title: '在线设备'
      //   // iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
      //   // iconSize: [10, 40]
      //   // iconAnchor: [22, 94],
      //   // popupAnchor: [-3, -76],
      //   // shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
      //   // shadowUrl: '../../assets/images/deviceonline.png',
      //   // shadowSize: [68, 95],
      //   // shadowAnchor: [22, 94]
      // })
      // // L.marker([39.550339, 116.114129], {icon: myIcon}).addTo(map)
      // var marker5 = L.marker([31.23636, 121.53413], { icon: myIcon })
      // var onlineIcon = L.icon({
      //   iconUrl: require('../../assets/gis/devicenotonline.png'),
      //   title: '离线设备',
      //   bubblingMouseEvents: true
      // })
      // var marker6 = L.marker([31.25636, 121.53413], { icon: onlineIcon })
      // var faultIcon = L.icon({
      //   iconUrl: require('../../assets/gis/devicefault.png'),
      //   title: '故障设备',
      //   bubblingMouseEvents: true
      // })
      // var marker7 = L.marker([31.27636, 121.53413], { icon: faultIcon })
      // //   markers.push(marker1)
      // //   markers.push(marker2)
      // //   markers.push(marker3)
      // //   markers.push(marker4)
      // markers.push(marker5)
      // markers.push(marker6)
      // markers.push(marker7)
      // this.citiesLayer = L.layerGroup(markers)
      // console.log(this.citiesLayer)
      // map.addLayer(this.citiesLayer)
      // this.addMessage()
      // // map.removeLayer(citiesLayer)
    },
    addMapEvent () {
      let _this = this
      _this.map.on('mousemove', function (e) {
        _this.lngLat.lng = _this.computedLngLat(String(e.latlng.lat))
        _this.lngLat.lat = _this.computedLngLat(String(e.latlng.lng))
        // L.popup().setLatLng(e.latlng).setContent(e.latlng.toString()).openOn(map)
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
        if (dev.state === 'UP') {
          let onlineIcon = L.icon({
            iconUrl: require('../../assets/gis/deviceonline.png'),
            iconSize: [24, 27],
            title: 'UP',
            alt: dev,
            iconAnchor: [12, 27]
          })
          let marker = L.marker(devPoint, { icon: onlineIcon })
          markers.push(marker)
        } else if (dev.state === 'DOWN') {
          let notOnlineIcon = L.icon({
            iconUrl: require('../../assets/gis/devicenotonline.png'),
            iconSize: [24, 27],
            title: 'DOWN',
            alt: dev,
            iconAnchor: [12, 27]
          })
          let marker = L.marker(devPoint, { icon: notOnlineIcon }).on('click', this.onMarkerClick)
          // 添加marker来设置点击事件
          markers.push(marker)
        }
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
      this.citiesLayer.eachLayer(function (layer) {
        let options = layer.options.icon.options
        let devData = options.alt
        let date = devData.lastTime
        if (devData.state === 'UP') {
          let status = '在线'
          layer.bindPopup('<div style=\'font-size: 14px;\'><span style=\'color: #005bac ;\'>' + status + '</span>&nbsp&nbsp<span style=\'color: #666666;\'>' + date + '</span></div>')
        } else if (devData.state === 'DOWN') {
          let status = '离线'
          layer.bindPopup('<div style=\'font-size: 14px;\'><span style=\'color: #676767 ;\'>' + status + '</span>&nbsp&nbsp<span style=\'color: #666666;\'>' + date + '</span></div>')
        }
      })
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
    setDeviceLocation (dev) {
      this.editMode = true
      this.deviceInfo = dev
      let _this = this
      _this.map.on('click', function (e) {
        _this.lngLat.lng = _this.computedLngLat(String(e.latlng.lng))
        _this.lngLat.lat = _this.computedLngLat(String(e.latlng.lat))
        _this.deviceInfo.lng = _this.lngLat.lng
        _this.deviceInfo.lat = _this.lngLat.lat
        let dev = _this.deviceInfo
        let updateChild = _this.$refs.device.$refs.updateChild
        updateChild.onUpdateClick(dev, true)
        // L.popup().setLatLng(e.latlng).setContent(e.latlng.toString()).openOn(this.map)
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

.showLayout {
  position: fixed;
  top: 70px;
  right: 38px;
  width: 442px;
  height: auto;
  z-index: 904;
  background-color: #ffffff;
}
.tabsconatiner {
  margin: 10px;
  position: relative;
  width: 100% - 20px;
  height: 100% - 20px;
}
.addbtn {
  position: absolute;
  right: 5px;
  z-index: 99;
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
  right: -25px;
  top: 0px;
}
.active {
  position: absolute;
  cursor: pointer;
  right: -25px;
  top: 0px;
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
