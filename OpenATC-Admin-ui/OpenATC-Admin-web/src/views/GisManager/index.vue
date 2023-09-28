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
          <device v-if="bizType === 'deviceState'" ref="device"> </device>
          <dutyRoute v-if="bizType === 'dutyRoute'" ref="dutyRoute"> </dutyRoute>
          <greenWaveCharts v-if="bizType === 'coordinateRoute'" ref="greenwavecharts"> </greenWaveCharts>
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
        <el-radio label="deviceState">设备状态</el-radio>
        <el-radio label="dutyRoute">特勤路线</el-radio>
        <el-radio label="coordinateRoute">协调路线</el-radio>
      </el-radio-group>
    </div>
    <!-- <div class="footer-left">
      <div>
        <el-radio-group v-model="mapType">
          <el-radio-button label="2D">地图</el-radio-button>
          <el-radio-button label="3D">卫星</el-radio-button>
        </el-radio-group>
      </div>
    </div> -->
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
import device from './components/device'
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
      toggleShow: true,
      toggleshowisActive: true,
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
      let map = _this.loadMapType()
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
      window.map = map
      L.tileLayer(
        _this.gis
      ).addTo(map)
      // L.tileLayer(
      //   'http://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=7&x={x}&y={y}&z={z}'
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
              if (config['key'] === 'gis') {
                this.gis = config['value'] + ''
                // this.gis = 'http://192.168.14.168:7080/PBS/rest/services/MyPBSService1/MapServer/tile/{z}/{y}/{x}'
              }
            }
            resolve(data.data.data)
          }
        })
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
  margin: 10px;
  position: relative;
  width: 100% - 20px;
  height: 100% - 20px;
  opacity: 0.95;
}
</style>
