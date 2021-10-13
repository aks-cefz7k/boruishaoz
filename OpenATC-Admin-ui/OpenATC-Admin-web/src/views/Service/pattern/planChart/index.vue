<template>
  <v-stage :config="configKonva" ref="stage" @wheel="zoomStage">
    <v-layer ref="layer">
      <v-line
        v-for="config in lineList"
        :key="config.id"
        :config="config"
        ref="line"
      />
      <v-circle
        v-for="config in circleList"
        :key="config.id"
        :config="config"
        :draggable="true"
        ref="circle"
        @dragstart="onCircleDragStart"
        @dragmove="onCircleDragMove"
        @mouseenter="onMouseEnter"
        @mouseout="onMouseOut"
        @click="onClicleClick"
      ></v-circle>
    </v-layer>
  </v-stage>
</template>

<script>
export default {
  name: 'serviceLine',
  props: {
    route: {
      type: Object
    }
  },
  data () {
    return {
      configKonva: {
        width: 1120,
        height: 750,
        draggable: true
      },
      configCircle: {
        x: 0,
        y: 0,
        radius: 10,
        fill: '#DCDCDC',
        stroke: 'black',
        strokeWidth: 2,
        id: ''
      },
      highLightFill: '#4D9AFF',
      configEdge: {
        points: [],
        stroke: '#BFBFBF',
        strokeWidth: 1,
        opacity: 1
      },
      circleList: [
        {
          x: 0,
          y: 0,
          radius: 70,
          fill: 'red',
          stroke: 'black',
          strokeWidth: 4
        }
      ],
      reactList: [
      ],
      lineList: [
      ],
      minZoomScale: 1,
      maxZoomScale: 20,
      scaleBy: 1.2, // 滚轮缩放比例，默认
      stageX: 0,
      stageY: 0,
      newScale: 1,
      anchorDragFromX: 0,
      anchorDragFromY: 0
    }
  },
  computed: {},
  created () {
    let intersections = this.route.intersections
    this.setCircleList(intersections)
  },
  mounted () {
    let line = Object.assign({}, this.configEdge)
    let pointsList = []
    let circleNodeList = this.$refs.circle
    for (let item of circleNodeList) {
      let node = item.getNode()
      console.log(node)
      pointsList.push(node.x())
      pointsList.push(node.y())
    }
    line.points = pointsList
    console.log(line)
    this.lineList.push(line)
  },
  methods: {
    onMouseEnter () {
      document.body.style.cursor = 'pointer'
    },
    onMouseOut () {
      // document.body.style.cursor = 'default'
    },
    onClicleClick (e) {
      let id = e.target.attrs.id
      this.$emit('onNodeClick', id)
      this.highLightNode(id)
    },
    highLightNode (id) {
      for (let c of this.circleList) {
        c.fill = this.configCircle.fill
        if (c.id === id) {
          c.fill = this.highLightFill
        }
      }
      // this.$refs.layer.getNode().batchDraw()
    },
    setCircleList (junctionList) {
      this.circleList = []
      for (let item of junctionList) {
        let x = item.location[0]
        let y = item.location[1]
        let circle = Object.assign({}, this.configCircle)
        circle.x = x + 400
        circle.y = y
        circle.id = item.id
        console.log(circle)
        this.circleList.push(circle)
      }
    },
    setLineList (edgeList) {
      for (let item of edgeList) {
        let from = item.from
        let to = item.to
        if (!from || !to) {
          continue
        }
        let itemList = this.circleList.filter(node => node.id === from || node.id === to)
        let pointsList = []
        for (let circle of itemList) {
          pointsList.push(circle.x)
          pointsList.push(circle.y)
        }
        let line = Object.assign({}, this.configEdge)
        line.points = pointsList
        console.log(line)
        this.lineList.push(line)
        console.log(this.lineList)
      }
    },
    onCircleDragStart (e) {
      console.log('From')
    },
    onCircleDragMove (e) {
      let lineNodeList = this.$refs.line
      if (!lineNodeList || lineNodeList.length > 1) {
        console.error('Exception:' + e)
        return false
      }
      let line = lineNodeList[0].getNode()
      let pointsList = []
      let circleNodeList = this.$refs.circle
      for (let item of circleNodeList) {
        let node = item.getNode()
        console.log(node)
        pointsList.push(node.x())
        pointsList.push(node.y())
      }
      line.points(pointsList)
      console.log(line)
    },
    zoomStage (e) {
      let _this = this
      _this.stage = _this.$refs.stage.getNode()
      let scaleBy = _this.scaleBy
      console.log(_this.stage)
      e = e.evt
      console.log(e)
      if (_this.stage.getPointerPosition() === undefined) {
        return false
      }
      e.preventDefault()
      var oldScale = _this.stage.scaleX()
      var mousePointTo = {
        x:
          _this.stage.getPointerPosition().x / oldScale -
          _this.stage.x() / oldScale,
        y:
          _this.stage.getPointerPosition().y / oldScale -
          _this.stage.y() / oldScale
      }
      var newScale = e.deltaY > 0 ? oldScale / scaleBy : oldScale * scaleBy
      // 限制最小最大缩放比例
      newScale =
        newScale > _this.maxZoomScale
          ? _this.maxZoomScale
          : newScale < _this.minZoomScale ? _this.minZoomScale : newScale
      if (newScale === _this.minZoomScale) {
        _this.cancelZoom()
        return false
      }
      _this.stage.scale({ x: newScale, y: newScale })
      var newPos = {
        x:
          -(mousePointTo.x - _this.stage.getPointerPosition().x / newScale) *
          newScale,
        y:
          -(mousePointTo.y - _this.stage.getPointerPosition().y / newScale) *
          newScale
      }
      if (
        newPos.x > 0 ||
        newPos.y > 0 ||
        newPos.x < _this.stageWidth * (1 - newScale) ||
        newPos.y < _this.stageHeight * (1 - newScale)
      ) {
        _this.cancelZoom()
        return false
      }
      console.log(newPos)
      console.log(_this.stageX)
      console.log(_this.stageY)
      console.log(newScale)
      _this.stage.position(newPos)
      _this.stage.batchDraw()
      _this.stageX = newPos.x
      _this.stageY = newPos.y
      _this.newScale = newScale
    },
    cancelZoom () {
      var newScale = 1
      this.stage.scale({ x: newScale, y: newScale })
      var newPos = {
        x: 0,
        y: 0
      }
      this.stage.position(newPos)
      this.stage.batchDraw()

      this.stageX = newPos.x
      this.stageY = newPos.y
      this.newScale = newScale
    }
  }
}

</script>
