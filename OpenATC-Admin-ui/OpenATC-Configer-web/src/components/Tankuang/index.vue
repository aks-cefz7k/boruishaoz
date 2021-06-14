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
    <div>
        <el-popover
            placement="bottom"
            width="260"
            v-model="visible2">
            <div class="main" v-for="(item, index) in imgs" v-if="index < sum" :key="index">
                <!-- <div class="box" :class="status[index] ? 'Selected' : 'noSelected'" @click="boxShow(index)"><img :src="item.img"></div> -->
                <div :style="tankuangSize" :class="status[index] ? 'phaseSelected' : 'phaseNoSelected'" @click="boxShow(index)">
                  <div :class="item.class" style="border:0px"></div>
                </div>
            </div>
            <span v-text="name" :class="showBottomName ? 'showSpan' : 'hiddenSpan'"></span>
            <el-input slot="reference" size="small" v-model="name" :readonly="true"></el-input>
          </el-popover>
          <span v-text="name" class="showSpan"></span>
    </div>
</template>
<script>

export default {
  name: 'dragTankang-demo',
  data () {
    return {
      visible2: false,
      name: '',
      status: [],
      sum: 0 // 弹框一共分几个小图标（弹框的布局）
    }
  },
  props: {
    imgs: {
      type: [Array, Object],
      required: true
    },
    list: {
      type: Array,
      default () {
        return []
      }
    },
    index: {
      type: Number
    },
    showBottomName: {
      type: Boolean,
      default: false
    },
    lines: {
      type: Number
    },
    rows: {
      type: Number
    }
  },
  created () {
    for (let obj of this.imgs) {
      if (this.list.length > 0 && this.list.includes(obj.id)) {
        this.status.push(1)
      } else {
        this.status.push(0)
      }
    }
    this.name = this.getName()
    // 根据行列计算最多展示的图标
    this.sum = this.lines * this.rows
  },
  methods: {
    boxShow (index) {
      this.status[index] = !this.status[index]
      this.name = this.getName()
      this.toParent()
    },
    toParent () {
      let parameter = {}
      parameter.status = this.status
      parameter.index = this.index
      this.$emit('finsh', parameter)
    },
    getName () {
      let name = ''
      for (let i = 0; i < this.status.length; i++) {
        if (!this.status[i]) continue
        name = name + ',' + this.imgs[i].name
      }
      if (name !== '') {
        return name.substr(1)
      } else {
        return ''
      }
    }
  },
  computed: {
    // 根据行列计算弹框排版
    tankuangSize () {
      let lines = this.lines
      let widths = ((100 / lines) - 2) + '%'
      let styleObj = {
        width: widths
      }
      return styleObj
    }
  }
}
</script>
<style lang="scss">
// * {
//     margin: 0;
//     padding: 0;
// }
.main {
  // background-color: #fff;
  width: 100%;
  // padding-bottom: 100%;
  padding-left: 0.5%;
  padding-top: 0.5%;
}

// .main>div{
//     width: 23%;
//     /* padding-bottom: 23%; */
//     border: 1% solid #fff;
//     background-color: #ecf5ff;
//     border-radius: 3%;
//     float: left;
//     margin: 1%;
//     cursor: pointer;
// }
// .phaseNoSelected{
//     // width: 23%;
//     /* padding-bottom: 23%; */
//     border: 1% solid #fff;
//     background-color: #ecf5ff;
//     border-radius: 3%;
//     float: left;
//     margin: 1%;
//     cursor: pointer;
// }
// .phaseSelected{
//     // width: 23%;
//     /* padding-bottom: 23%; */
//     border: 1% solid #fff;
//     background-color: #409EFF;
//     border-radius: 3%;
//     float: left;
//     margin: 1%;
//     cursor: pointer;
// }
body {
    overflow: auto;
    font-weight: 400;
    -webkit-font-smoothing: antialiased;
}
.showSpan {
  display: block;
}
.hiddenSpan {
  display: none;
}
.phase-icon {
  background-image: red;
}
</style>
