<template>
   <div class="camera" ref='cubewraper'>
      <div class="space" ref='cube'
        @mousedown="onmousedown"
        @mousemove="onmousemove"
        @mouseup="onmouseup"
        @mousewheel="onmousewheel"
        @dblclick="doubleclick">
        <div class="box1" >
          <div class="face1"></div>
          <div class="face2"></div>
          <div class="face3"></div>
          <div class="face4"></div>
        </div>
        <div class="box2">
          <div class="face1"></div>
          <div class="face2"></div>
          <div class="face3"></div>
          <div class="face4"></div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: 'kiss-compass',
  data () {
    return {
      downX: 0,
      downY: 0,
      moveX: 0,
      moveY: 0,
      tempX: 0,
      tempY: 0,
      degX: 0,
      degY: 0,
      isMouseDown: false
    }
  },
  methods: {
    doTrag () {
      setTimeout(function () {
      }, 5000)
    },
    onmousedown (e) {
      console.log('onmousedowm')
      this.isMouseDown = true
      e = e || event
      this.downX = e.clientX // 获取鼠标点下去时的坐标
      this.downY = e.clientY
      // this.onmousemove(e)
    },
    onmousemove (e) {
      console.log('onmousemove')
      if (this.isMouseDown) {
        e = e || event
        this.moveX = e.clientX - this.downX // 算出鼠标移动的距离
        this.moveY = e.clientY - this.downY
        // 根据一定比例将变化反应在盒子上，改变比例5可以调节拖动的速度
        this.tempX = this.degX + this.moveX * 5
        this.tempY = this.degY - this.moveY * 5
        this.$refs.cube.style.transform = 'rotatex(' + this.tempY + 'deg) rotatey(' + this.tempX + 'deg)'
        this.$emit('rotateAndPitch', this.tempX, this.tempY)
      }
    },
    onmouseup (e) {
      console.log('onmouseup')
      this.isMouseDown = false
      e = e || event
      this.degX += this.moveX * 5 // 鼠标松开时将拖动期间改变的最终结果保存
      this.degY += -this.moveY * 5
    },
    onmousewheel (e) {
      console.log('onmousewheel')
    },
    doubleclick (e) {
      console.log('double click')
      this.$emit('signalClick', e)
      this.$refs.cube.style.transform = 'rotatex(0deg) rotatey(0deg)'
    }
  }
}
</script>

<style scoped>
.camera{
  width: 30px;
  height: 30px;
  perspective-origin: 50% -50%;
  -webkit-perspective-origin: 50% -50%;
  perspective:2000px;
  -webkit-perspective:2000px;
}
.space{
  position:relative;
  width:100%;
  height:100%;
  transform-style:preserve-3d;
  -moz-transform-style:preserve-3d;
  -webkit-transform-style:preserve-3d;
  transform: rotateY(0deg) rotateX(0deg);
  transform-origin:center center -10px;
  -moz-transform-origin:center center -10px;
  -webkit-transform-origin:center center -10px;
}

.space div{
  position:absolute;
}
.box1, .box2{
  transform-style:preserve-3d;
  -moz-transform-style:preserve-3d;
  -webkit-transform-style:preserve-3d;
}
.box1 div,.box2 div{
  width:0;
  height:0;
  border-width:0 10px 20px;
  border-style:solid;
  opacity:.4;
}
.box2{
  transform-origin: center 17px -10px;
  transform: rotateX(180deg) translateY(-25px);
  -moz-transform-origin: center 17px -10px;
  -moz-transform: rotateX(180deg) translateY(-25px);
  -webkit-transform-origin: center 17px -10px;
  -webkit-transform: rotateX(180deg) translateY(-25px);
}
.face1{
  border-color:transparent transparent #41d3f7;
  transform-origin:center bottom;
  transform:translateX(10px) translateY(10px) rotateX(35.36deg);
  -moz-transform-origin:center bottom;
  -moz-transform:translateX(10px) translateY(10px) rotateX(35.36deg);
  -webkit-transform-origin:center bottom;
  -webkit-transform:translateX(10px) translateY(10px) rotateX(35.36deg);
}
.face2{
  border-color:transparent transparent #173d47;
  transform-origin:right bottom;
  transform:translateX(10px) translateY(10px) rotateY(-90deg) rotateX(-35.36deg);
  -moz-transform-origin:right bottom;
  -moz-transform:translateX(10px) translateY(10px) rotateY(-90deg) rotateX(-35.36deg);
  -webkit-transform-origin:right bottom;
  -webkit-transform:translateX(10px) translateY(10px) rotateY(-90deg) rotateX(-35.36deg);
}
.face3{
  border-color:transparent transparent #1b748a;
  transform-origin:left bottom;
  transform:translateX(10px) translateY(10px)  translateZ(-20px) rotateX(-35.36deg);
  -moz-transform-origin:left bottom;
  -moz-transform:translateX(10px) translateY(10px)  translateZ(-20px) rotateX(-35.36deg);
  -webkit-transform-origin:left bottom;
  -webkit-transform:translateX(10px) translateY(10px)  translateZ(-20px) rotateX(-35.36deg);
}
.face4{
  border-color:transparent transparent #1c96b8;
  transform-origin:left bottom;
  transform:translateX(10px) translateY(10px) rotateY(90deg) rotateX(-35.36deg);
  -moz-transform-origin:left bottom;
  -moz-transform:translateX(10px) translateY(10px) rotateY(90deg) rotateX(-35.36deg);
  -webkit-transform-origin:left bottom;
  -webkit-transform:translateX(10px) translateY(10px) rotateY(90deg) rotateX(-35.36deg);
}
.box2 .face1{
  border-color:transparent transparent #142f36;
}
.box2 .face2{
  border-color:transparent transparent #724e01;
}
.box2 .face3{
  border-color:transparent transparent yellow;
}
.box2 .face4{
  border-color:transparent transparent #154d5c;
}

</style>
