<template>
<div>
  <div v-for="(item, index) in datalist" :key="index" style="margin-bottom: 14px">
    <kiss-horizontalchildren :dataList="[{value: item.value, color: item.color, maxNum: theMaxNum}]" :name="item.name" :horizontalDistance="horizontalDistance" :display="display" :mainwidth="mainwidth" :mainheight="mainheight">
      <div slot="leftslot">
        <slot name="fatherslot"></slot>
      </div>
    </kiss-horizontalchildren>
  </div>
</div>
</template>
<script>
export default {
  name: 'kiss-horizontal',
  props: {
    datalist: { // 数据集合
      type: Array,
      default: function () {
        return [
          {
            value: 5,
            name: '',
            color: '#43b4cf'
          },
          {
            value: 5,
            name: '',
            color: '#43b4cf'
          }
        ]
      }
    },
    maxNum: { // 最大数值(如果不传的话就以datalist中的最大value值为最大值)
      type: Number,
      default: 0
    },
    horizontalDistance: { // 上下两个的间距
      type: String,
      default: '3px'
    },
    mainwidth: {
      type: String,
      default: '400px'
    },
    mainheight: {
      type: String,
      default: '25px'
    },
    display: { // 是否展示右侧数值
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      bgStyle: {
        'position': 'relative',
        'marginBottom': '',
        'width': '',
        'height': ''
      },
      theMaxNum: 0
      // rightNum: {
      //   'color': '#fff',
      //   'display': 'block'
      // }
    }
  },
  watch: {
    datalist: {
      handler: function (val) {
        this.init()
        // this.theMaxNum = this.getMaxnum()
      },
      deep: true
    }
  },
  methods: {
    getMaxnum () {
      let max = this.maxNum
      if (max !== 0) return max
      let temp = 0
      for (let data of this.datalist) {
        if (data.value > temp) {
          max = data.value
          temp = max
        }
      }
      return max
    },
    init () {
      if (this.maxNum === 0) {
        this.theMaxNum = this.getMaxnum()
      } else {
        this.theMaxNum = this.maxNum
      }
    }
    // isShowrightnum () {
    //   if (this.display) return
    //   this.rightNum.display = 'none'
    // }
  },
  mounted () {
    this.bgStyle.width = this.mainwidth
    this.bgStyle.height = this.mainheight
    this.bgStyle.marginBottom = this.horizontalDistance
    this.init()
    // if (this.maxNum === 0) {
    //   this.theMaxNum = this.getMaxnum()
    // } else {
    //   this.theMaxNum = this.maxNum
    // }
    // this.isShowrightnum()
  }
}
</script>
<style scoped>
.horizontalcontext {
  position: absolute;
  width: 85%;
  height: 100%;
  left: 10%;
  top: 0;
}
.horizontal {
  position: absolute;
  top: 0;
  left: 0;
  width: 80%;
  height: 50%;
}
.hor_num {
  position: absolute;
  top: 0;
  left: 85%;
  width: 10%;
  height: 66.6%;
}
.hor_text {
  position: absolute;
  top: 30%;
  left: 0;
  /* width: 20px; */
  height: 42%;
}
</style>
