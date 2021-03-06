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
  <div class="gis-devicePanel">
    <div class="title">{{this.$t('openatc.gis.deviceState') }}</div>
    <div>
      <div class="devicePanel">
        <el-table
          ref="singleTable"
          class="deviceTable"
          :data="devicesTableData"
          row-key="id"
          :max-height="maxHeight"
          highlight-current-row
          @current-change="handleCurrentChange"
          @row-click="onRowClick"
          v-loading.body="listLoading"
          style="width: 100%"
        >
          <el-table-column type="index" label="#" align="center" width="40">
          </el-table-column>
          <el-table-column
            prop="agentid"
            :label="$t('openatc.devicemanager.crossid')"
            align="center"
            width="130"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            :label="$t('openatc.greenwaveoptimize.devicename')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="roles"
            :label="$t('openatc.devicemanager.state')"
            align="center"
            width="60"
          >
            <template slot-scope="scope">
              <div>
                <el-tag
                  size="medium"
                  effect="plain"
                  :type="getTag(scope.row).type"
                  >{{ getTag(scope.row).label }}</el-tag
                >
              </div>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('openatc.greenwaveoptimize.operation')"
            align="center"
            width="100"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="handleEdit(scope.row)">{{
                $t("openatc.common.edit")
              }}</el-button>
              <el-button type="text" @click="onDetailClick(scope.row)">{{
                $t("openatc.common.detail")
              }}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <Update ref="updateChild"></Update>
    <EdgeModal ref="edgeModalChild"></EdgeModal>
    <router-view></router-view>
  </div>
</template>
<script>
// import router from '@/router'
import L from 'leaflet'
import { GetAllDevice } from '@/api/device'
import Update from './update'
import EdgeModal from './edgeModal'
import XRDDirSelector from '@/components/XRDDirSelector'
import { getMessageByCode } from '@/utils/responseMessage'
// import PhaseMarkerVue from './index'
export default {
  name: 'device',
  components: { Update, EdgeModal, XRDDirSelector },
  props: {
    // devicesData: {
    //   type: Array
    // }
  },
  watch: {
  },
  data () {
    return {
      maxHeight: screen.height < 1080 ? screen.height * 0.6 : screen.height * 0.7,
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
      devList: []
    }
  },
  mounted () {
    let _this = this
    this.$nextTick(() => {
      _this.getAllAdevice()
    })
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
        this.devicesTableData = this.devList
        console.log(window.map)
        this.map = window.map
        this.handleMapDevice(this.devList)
      })
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
        let iconUrl = this.deviceFaultIcon
        if (dev.state === 'UP') {
          iconUrl = this.deviceOnlineIcon
        } else if (dev.state === 'DOWN') {
          iconUrl = this.deviceNotOnlineIcon
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
      // let htmlStr =
      //   `
      //     <div id="test1">
      //     </div>
      //   `
      // let coordinates = row.geometry.coordinates.slice().reverse()
      // let iconAnchor = [coordinates[0] + 110, coordinates[1] - 20]
      // let icon = L.divIcon({
      //   html: htmlStr,
      //   iconSize: [80, 80],
      //   iconAnchor: iconAnchor,
      //   className: 'map-circle'
      // })
      // L.marker(coordinates, {icon: icon}).addTo(this.deviceLayer)
      // let dom = new PhaseMarkerVue({})
      // dom.$mount(`#test1`)

      // var svgElement = document.createElementNS('http://www.w3.org/2000/svg', 'svg')
      // svgElement.setAttribute('xmlns', 'http://www.w3.org/2000/svg')
      // svgElement.setAttribute('viewBox', '0 0 2000 200')
      // svgElement.innerHTML = '<rect width="200" height="200"/><rect x="75" y="23" width="50" height="50" style="fill:red"/><rect x="75" y="123" width="50" height="50" style="fill:#0013ff"/>'
      // var svgElementBounds = [coordinates, [coordinates[0] + 20, coordinates[1] + 20]]
      // L.svgOverlay(svgElement, svgElementBounds).addTo(this.deviceLayer)

      // var imageUrl = 'http://www.lib.utexas.edu/maps/historical/newark_nj_1922.jpg'
      // let imageBounds = [coordinates, [coordinates[0] + 20, coordinates[1] + 20]]
      // L.imageOverlay(imageUrl, imageBounds).addTo(this.deviceLayer)
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
      // let agentid = devData.agentid
      let crossRoadName = devData.name
      let date = devData.lastTime
      let status = this.$t('openatc.devicemanager.online')
      if (devData.state === 'UP') {
        status = this.$t('openatc.devicemanager.online')
      } else if (devData.state === 'DOWN') {
        status = this.$t('openatc.devicemanager.offline')
      } else {
        status = this.$t('openatc.devicemanager.fault')
      }
      let content =
      `
        <div>${crossRoadName}</div>
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
      } else if (row.state === 'FAULT') {
        return {
          label: this.$t('openatc.devicemanager.fault'),
          type: 'danger'
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
      this.hideLayer(this.deviceLayer)
      this.getAllAdevice()
    },
    hideLayer () {
      this.map.removeLayer(this.deviceLayer)
    },
    showLayer () {
      this.map.addLayer(this.deviceLayer)
    },
    setCurrent (row) {
      this.$refs.singleTable.setCurrentRow(row)
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
    }
  }
}
</script>
