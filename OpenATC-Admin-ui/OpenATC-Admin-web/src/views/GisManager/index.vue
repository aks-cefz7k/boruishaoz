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
          <div class="route-info" v-if="bizType != 'deviceState'">
            <div class="route-length" v-show="toggleshowisActive">
              <el-row>
                <div class="route-length-value">{{routLength }}</div>
                <div class="route-length-company">m</div>
              </el-row>
              <el-row>
                <div class="route-length-name">路线总长度</div>
              </el-row>
            </div>
          </div>
          <device v-if="bizType === 'deviceState'" ref="device"> </device>
          <dutyRoute v-if="bizType === 'dutyRoute'"
                     ref="dutyRoute"
                     @onRouteChange="onRouteChange"> </dutyRoute>
          <greenWaveCharts v-if="bizType === 'coordinateRoute'"
                           ref="greenwavecharts"
                           @onRouteChange="onRouteChange"></greenWaveCharts>
        </div>
      </div>
    </transition>
    <div
      :class="[toggleshowisActive ? 'toggle_show' : 'active']"
      @click="handletoggleshow">
      <lottie
        :options="defaultOptions"
        :width="30"
        :height="30"
        v-on:animCreated="handleAnimation"/>
    </div>
    <div class="header">
      <el-radio-group v-model="bizType">
        <el-radio label="deviceState">{{this.$t('openatc.gis.deviceState') }}</el-radio>
        <el-radio label="dutyRoute">{{this.$t('openatc.gis.dutyRoute') }}</el-radio>
        <el-radio label="coordinateRoute">{{this.$t('openatc.gis.coordinateRoute') }}</el-radio>
      </el-radio-group>
    </div>
    <div class="footer-right">
      {{this.$t('openatc.devicemanager.longitude') }} {{ lngLat.lng }}
      {{this.$t('openatc.devicemanager.latitude') }}  {{ lngLat.lat }}
      {{this.$t('openatc.devicemanager.layerLevel') }} {{ zoom }}
    </div>
  </div>
</template>
<script>
import L from 'leaflet'
import 'leaflet.chinatmsproviders'
import { SystemconfigApi } from '@/api/systemconfig.js'
import lottie from 'vue-lottie'
import device from './components/device/device'
import dutyRoute from './components/dutyRoute/dutyRoute'
import greenWaveCharts from './components/greenWaveCharts/index'
import { getTheme } from '@/utils/auth'
import AnimDark from './toggleDataDark.json'
import Anim from './toggleData.json'
export default {
  components: {
    lottie,
    device,
    dutyRoute,
    greenWaveCharts
  },
  data () {
    return {
      bizType: 'deviceState',
      mapType: '2D',
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
      gisNormal: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      gisStatellite: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      toggleShow: true,
      toggleshowisActive: true,
      gisBoundLeftTop: [31.36360615, 121.30622863],
      gisBoundRightBottom: [31.11040156, 121.95270538],
      routLength: 0,
      tianDiTuKey: '3bfb2112c0920226f0592fd64cd2c70d'
    }
  },
  mounted () {
    this.init()
  },
  computed: {
    defaultOptions () {
      let res = { animationData: Anim, loop: false, autoplay: false }
      if (getTheme() === 'dark') {
        res = { animationData: AnimDark, loop: false, autoplay: false }
      }
      return res
    }
  },
  methods: {
    async init () {
      await this.getGisConfig()
      await this.initMap()
      await this.addMapEvent()
    },
    initMap () {
      let _this = this
      // let map = _this.loadMapType()
      let map = _this.loadLocalMapType()
      // let map = L.map('map', {
      //   minZoom: _this.minZoom,
      //   maxZoom: _this.maxZoom,
      //   // center: [39.550339, 116.114129],
      //   center: _this.center,
      //   zoom: _this.zoom,
      //   zoomControl: false,
      //   attributionControl: false,
      //   crs: L.CRS.EPSG3857,
      //   dragging: true,
      //   editMode: false
      // })
      let corner1 = L.latLng(this.gisBoundLeftTop[0], this.gisBoundLeftTop[1]) // 设置左上角经纬度
      let corner2 = L.latLng(this.gisBoundRightBottom[0], this.gisBoundRightBottom[1]) // 设置右下点经纬度
      let bounds = L.latLngBounds(corner1, corner2) // 构建视图限制范围
      map.setMaxBounds(bounds)

      // L.tileLayer(
      //   _this.gis
      // ).addTo(map)
      _this.map = map// data上需要挂载
      window.map = map
    },
    addMapEvent () {
      let _this = this
      _this.map.on('mousemove', function (e) {
        _this.lngLat.lng = _this.computedLngLat(String(e.latlng.lng))
        _this.lngLat.lat = _this.computedLngLat(String(e.latlng.lat))
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
    loadLocalMapType () {
      // let mbUrl = 'https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw'

      let grayscale = L.tileLayer(this.gisNormal, {id: 'mapbox/light-v9', tileSize: 512, zoomOffset: -1})
      let streets = L.tileLayer(this.gisStatellite, {id: 'mapbox/streets-v11', tileSize: 512, zoomOffset: -1})

      let baseLayers = {
        '地图': grayscale,
        '影像': streets
      }
      let overlayLayers = {}
      let map = L.map('map', {
        layers: [grayscale],
        minZoom: this.minZoom,
        maxZoom: this.maxZoom,
        center: this.center,
        zoom: this.zoom,
        zoomControl: false,
        attributionControl: false,
        crs: L.CRS.EPSG3857,
        dragging: true,
        editMode: false,
        preferCanvas: true
      })
      let options = {
        position: 'bottomleft'
      }
      L.control.layers(baseLayers, overlayLayers, options).addTo(map)
      return map
    },
    loadMapType () {
      let normalm = L.tileLayer.chinaProvider('TianDiTu.Normal.Map', {
        key: this.tianDiTuKey,
        maxZoom: this.maxZoom,
        minZoom: this.minZoom
      })
      let normala = L.tileLayer.chinaProvider('TianDiTu.Normal.Annotion', {
        key: this.tianDiTuKey,
        maxZoom: this.maxZoom,
        minZoom: this.minZoom
      })
      let imgm = L.tileLayer.chinaProvider('TianDiTu.Satellite.Map', {
        key: this.tianDiTuKey,
        maxZoom: this.maxZoom,
        minZoom: this.minZoom
      })
      let imga = L.tileLayer.chinaProvider('TianDiTu.Satellite.Annotion', {
        key: this.tianDiTuKey,
        maxZoom: this.maxZoom,
        minZoom: this.minZoom
      })
      let normal = L.layerGroup([normalm, normala])
      let image = L.layerGroup([imgm, imga])
      let baseLayers = {
        '地图': normal,
        '影像': image
      }
      let overlayLayers = {}
      let map = L.map('map', {
        layers: [normal],
        minZoom: this.minZoom,
        maxZoom: this.maxZoom,
        center: this.center,
        zoom: this.zoom,
        zoomControl: false,
        attributionControl: false,
        crs: L.CRS.EPSG3857,
        dragging: true,
        editMode: false,
        preferCanvas: true
      })
      let options = {
        position: 'bottomleft'
      }
      L.control.layers(baseLayers, overlayLayers, options).addTo(map)
      return map
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
              if (config['key'] === 'gisNormal') {
                this.gisNormal = config['value']
              }
              if (config['key'] === 'gisStatellite') {
                this.gisStatellite = config['value']
              }
              if (config['key'] === 'gisBoundLeftTop') {
                let cen = config['value']
                let ss = cen.split(',')
                let s1 = ss[0].replace('[', '')
                let s2 = ss[1].replace(']', '').trim()
                this.gisBoundLeftTop = [Number(s1), Number(s2)]
              }
              if (config['key'] === 'gisBoundRightBottom') {
                let cen = config['value']
                let ss = cen.split(',')
                let s1 = ss[0].replace('[', '')
                let s2 = ss[1].replace(']', '').trim()
                this.gisBoundRightBottom = [Number(s1), Number(s2)]
              }
              if (config['key'] === 'gis') {
                this.gis = config['value'] + ''
                // this.gis = 'http://192.168.14.168:7080/PBS/rest/services/MyPBSService/MapServer/tile/{z}/{y}/{x}'
                // let gisNormal = 'http://192.168.14.168:7080/PBS/rest/services/MyPBSService1/MapServer/tile/{z}/{y}/{x}'
                // let gisStatellite = 'http://192.168.14.168:7081/PBS/rest/services/MyPBSService2/MapServer/tile/{z}/{y}/{x}'
                // this.gisNormal = gisNormal
                // this.gisStatellite = gisStatellite
                // this.gis = gisStatellite

                // this.center = [31.22784056, 121.68148040]
                // this.zoom = 12
                // this.minZoom = 12
                // this.maxZoom = 18
              }
            }
            resolve(data.data.data)
          }
        })
      })
    },
    onRouteChange (pointArr) {
      this.getRouteDistande(pointArr)
    },
    getRouteDistande (pointArr) {
      if (!this.map) {
        return 0
      }
      let res = 0
      let curPoint = pointArr[0].slice().reverse()
      let curLatlng = L.latLng(curPoint[0], curPoint[1])
      for (let i = 1; i < pointArr.length; i++) {
        let point = pointArr[i].slice().reverse()
        let latlng = L.latLng(point[0], point[1])
        let len = this.map.distance(curLatlng, latlng)
        res = res + len
        curLatlng = latlng
      }
      res = Math.round(res)
      this.routLength = res
      return res
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
.tabsconatiner {
  /* overflow: hidden; */
  margin: 10px;
  position: relative;
  width: 100% - 20px;
  height: 100% - 20px;
  opacity: 0.95;
}
</style>
