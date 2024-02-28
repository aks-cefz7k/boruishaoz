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
<svg t="1545378648902" class="icon"
    viewBox="0 0 50 50"
    style="enable-background:new 0 0 50 50;"
    version="1.1"
    xmlns="http://www.w3.org/2000/svg"
    p-id="6773"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    :width="Width" :height="Height">
    <!-- <mask id="shade"> -->
    <path d="M893.44 158.72
      H130.56C99.84 158.72 76.8 181.76 76.8 212.48v599.04c0 30.72 23.04 53.76 53.76 53.76
      h762.88c30.72 0 53.76-23.04 53.76-53.76V212.48c0-30.72-23.04-53.76-53.76-53.76z
      M130.56 194.56
      h762.88c10.24 0 17.92 7.68 17.92 17.92
      v537.6l-209.92-189.44-171.52 143.36-245.76-209.92-171.52 186.88
      V212.48c0-10.24 7.68-17.92 17.92-17.92z
      m-17.92 616.96v-76.8l174.08-189.44 332.8 284.16
      H130.56c-10.24 0-17.92-7.68-17.92-17.92z
      m780.8 17.92
      h-217.6l-120.32-102.4 143.36-120.32 209.92 189.44v12.8c2.56 12.8-5.12 20.48-15.36 20.48z"
      :fill="MaskColor" p-id="2761"
      :class="maskmark ? '' : 'invisible'"></path>
    <path d="M668.16 463.36c56.32 0 104.96-46.08 104.96-104.96 0-56.32-46.08-104.96-104.96-104.96S563.2 302.08 563.2 358.4c0 56.32 46.08 104.96 104.96 104.96z"
      :fill="MaskColor" p-id="2762"
      :class="maskmark ? '' : 'invisible'"></path>
    <!-- </mask> -->
    <!-- ====================================[1 East-Straight 东向直行] ============================================= -->
    <polygon id="东直行" points="48.6,11.7 25.4,11.7 25.4,9.2 19.3,12.7 25.4,16.1 25.4,13.7 48.6,13.7"
      :stroke-width="PathWidth"
      :fill="status[0].color"
      :class="status[0].isshow ? '' : 'invisible'"/>
    <!-- ====================================[2 East-Left     东向左转]============================================= -->
    <path id="东左转" d="M32.7,13.7h15.9v-2h-16c-2.1,0-3.8,1.7-3.9,3.8l-0.3,1.9L26,17.1l2.6,6.4l4.3-5.4l-2.5-0.3
      l0.3-2.2C30.8,14.5,31.6,13.7,32.7,13.7L32.7,13.7z"
      :stroke-width="PathWidth"
      :fill="status[1].color"
      :class="status[1].isshow ? '' : 'invisible'"/>
    <!-- ====================================[3 East-Right    东向右转]============================================= -->
    <path id="东右转" d="M30.7,9.6l-0.3-2l2.5-0.3l-4.3-5.4L26,8.3L28.5,8l0.3,1.9c0,2.1,1.7,3.8,3.9,3.8h16v-2h-16
      c-1,0-1.9-0.9-1.9-1.9V9.6z"
      :stroke-width="PathWidth"
      :fill="status[2].color"
      :class="status[2].isshow ? '' : 'invisible'"/>
    <!-- ====================================[4 East-Back     东向掉头]============================================= -->
    <path id="东调头" d="M38.2,20.6h2.9V23l6-3.5l-6-3.5v2.5h-2.9c-1,0-1.9-1.1-1.9-2.5c0-1.3,0.9-2.5,1.9-2.5h10.4v-2
      H38.2c-2.1,0-3.9,2-3.9,4.5S36.1,20.6,38.2,20.6z"
      :stroke-width="PathWidth"
      :fill="status[3].color"
      :class="roadDirection === 'right' && status[3].isshow ? '' : 'invisible'"/>

    <path id="东调头-印尼" d="M34.4,9.2c0,2.5,1.7,4.5,3.9,4.5h10.4v-2H38.2c-1,0-1.9-1.1-1.9-2.5s0.9-2.5,1.9-2.5H41
      v2.5l6-3.5l-6-3.5v2.5h-2.9C36.1,4.8,34.4,6.8,34.4,9.2z"
      :stroke-width="PathWidth"
      :fill="status[3].color"
      :class="roadDirection === 'left' && status[3].isshow ? '' : 'invisible'"/>
    <!-- ====================================[5 West-Straight 西向直行]============================================= -->
    <polygon id="西直行" points="1.4,38.3 24.6,38.3 24.6,40.8 30.7,37.3 24.6,33.9 24.6,36.3 1.4,36.3"
      :stroke-width="PathWidth"
      :fill="status[4].color"
      :class="status[4].isshow ? '' : 'invisible'"/>
    <!-- ====================================[6 West-Left     西向左转]============================================= -->
    <path id="西左转" d="M17.3,36.3H1.4v2h16c2.1,0,3.8-1.7,3.9-3.8l0.3-1.9l2.5,0.3l-2.6-6.4l-4.3,5.4l2.5,0.3
      l-0.3,2.2C19.2,35.5,18.4,36.3,17.3,36.3L17.3,36.3z"
      :stroke-width="PathWidth"
      :fill="status[5].color"
      :class="status[5].isshow ? '' : 'invisible'"/>
    <!-- ====================================[7 West-Right    西向右转]============================================= -->
    <path id="西右转" d="M19.3,40.4l0.3,2l-2.5,0.3l4.3,5.4l2.6-6.4L21.5,42l-0.3-1.9c0-2.1-1.7-3.8-3.9-3.8h-16v2h16
      c1,0,1.9,0.9,1.9,1.9V40.4z"
      :stroke-width="PathWidth"
      :fill="status[6].color"
      :class="status[6].isshow ? '' : 'invisible'"/>
    <!-- ====================================[8 West-Back     西向掉头]============================================= -->
    <path id="西调头" d="M11.8,29.4H8.9V27l-6,3.5l6,3.5v-2.5h2.9c1,0,1.9,1.1,1.9,2.5c0,1.3-0.9,2.5-1.9,2.5H1.4v2
      h10.4c2.1,0,3.9-2,3.9-4.5C15.6,31.4,13.9,29.4,11.8,29.4z"
      :stroke-width="PathWidth"
      :fill="status[7].color"
      :class="roadDirection === 'right' && status[7].isshow ? '' : 'invisible'"/>

    <path id="西调头-印尼" d="M15.6,40.8c0-2.5-1.7-4.5-3.9-4.5H1.4v2h10.4c1,0,1.9,1.1,1.9,2.5
      c0,1.3-0.9,2.5-1.9,2.5H9v-2.5l-6,3.5l6,3.5v-2.5h2.9C13.9,45.2,15.6,43.2,15.6,40.8z"
      :stroke-width="PathWidth"
      :fill="status[7].color"
      :class="roadDirection === 'left' && status[7].isshow ? '' : 'invisible'"/>
    <!-- ====================================[9 North-Straight北向直行]============================================= -->
    <polygon id="北直行" points="11.2,0 11.2,23.2 8.8,23.2 12.2,29.3 15.7,23.2 13.2,23.2 13.2,0"
      :stroke-width="PathWidth"
      :fill="status[8].color"
      :class="status[8].isshow ? '' : 'invisible'"/>
    <!-- ====================================[10 North-Left   北向左转]============================================= -->
    <path id="北左转" d="M13.2,15.9V0h-2v16c0,2.1,1.7,3.8,3.8,3.9l1.9,0.3l-0.3,2.5L23,20l-5.4-4.3l-0.3,2.5
      l-2.2-0.3C14.1,17.8,13.2,17,13.2,15.9L13.2,15.9z"
      :stroke-width="PathWidth"
      :fill="status[9].color"
      :class="status[9].isshow ? '' : 'invisible'"/>
    <!-- ====================================[11 North-Right  北向右转]============================================ -->
    <path id="北右转" d="M9.2,17.9l-2,0.3l-0.3-2.5L1.4,20l6.4,2.6l-0.3-2.5l1.9-0.3c2.1,0,3.8-1.7,3.8-3.9V0h-2v16
      c0,1-0.9,1.9-1.9,1.9H9.2z"
      :stroke-width="PathWidth"
      :fill="status[10].color"
      :class="status[10].isshow ? '' : 'invisible'"/>
    <!-- ====================================[12 North-Back   北向掉头]============================================= -->
    <path id="北调头" d="M20.1,10.4V7.5h2.5l-3.5-6l-3.5,6h2.5v2.9c0,1-1.1,1.9-2.5,1.9s-2.5-0.9-2.5-1.9V0h-2v10.4
      c0,2.1,2,3.9,4.5,3.9C18.1,14.2,20.1,12.5,20.1,10.4z"
      :stroke-width="PathWidth"
      :fill="status[11].color"
      :class="roadDirection === 'right' && status[11].isshow ? '' : 'invisible'"/>

    <path id="北调头-印尼" d="M8.8,14.2c2.5,0,4.5-1.7,4.5-3.9V0l-2,0v10.4c0,1-1.1,1.9-2.5,1.9s-2.5-0.9-2.5-1.9V7.6
      h2.5l-3.5-6l-3.5,6h2.5v2.9C4.3,12.5,6.3,14.2,8.8,14.2z"
      :stroke-width="PathWidth"
      :fill="status[11].color"
      :class="roadDirection === 'left' && status[11].isshow ? '' : 'invisible'"/>
    <!-- ====================================[13 South-Straight 南向直行]============================================= -->
    <polygon id="南直行" points="38.8,50 38.8,26.8 41.2,26.8 37.8,20.7 34.3,26.8 36.8,26.8 36.8,50"
      :stroke-width="PathWidth"
      :fill="status[12].color"
      :class="status[12].isshow ? '' : 'invisible'"/>
    <!-- ====================================[14 South-Letf    南向左转]============================================= -->
    <path id="南左转" d="M36.8,34.1V50h2V34c0-2.1-1.7-3.8-3.8-3.9l-1.9-0.3l0.3-2.5L27,30l5.4,4.3l0.3-2.5l2.2,0.3
      C35.9,32.2,36.8,33,36.8,34.1L36.8,34.1z"
      :stroke-width="PathWidth"
      :fill="status[13].color"
      :class="status[13].isshow ? '' : 'invisible'"/>
    <!-- ====================================[15 South-Right   南向右转]============================================= -->
    <path id="南右转" d="M40.8,32.1l2-0.3l0.3,2.5l5.4-4.3l-6.4-2.6l0.3,2.5l-1.9,0.3c-2.1,0-3.8,1.7-3.8,3.9v16h2V34
      c0-1,0.9-1.9,1.9-1.9H40.8z"
      :stroke-width="PathWidth"
      :fill="status[14].color"
      :class="status[14].isshow ? '' : 'invisible'"/>
    <!-- ====================================[16 South-Back    南向掉头]============================================ -->
    <path id="南调头" d="M29.9,39.6v2.9h-2.5l3.5,6l3.5-6h-2.5v-2.9c0-1,1.1-1.9,2.5-1.9c1.3,0,2.5,0.9,2.5,1.9V50h2
      V39.6c0-2.1-2-3.9-4.5-3.9C31.9,35.8,29.9,37.5,29.9,39.6z"
      :stroke-width="PathWidth"
      :fill="status[15].color"
      :class="roadDirection === 'right' && status[15].isshow ? '' : 'invisible'"/>

    <path id="南调头-印尼" d="M41.2,35.8c-2.5,0-4.5,1.7-4.5,3.9V50h2V39.6c0-1,1.1-1.9,2.5-1.9
      c1.3,0,2.5,0.9,2.5,1.9v2.9h-2.5l3.5,6l3.5-6h-2.5v-2.9C45.7,37.5,43.7,35.8,41.2,35.8z"
      :stroke-width="PathWidth"
      :fill="status[15].color"
      :class="roadDirection === 'left' && status[15].isshow ? '' : 'invisible'"/>
  </svg>
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'xdr-dir-selector',
  props: {
    showlist: {
      type: Array,
      default: function () {
        return [
          {
            id: 1,
            color: 'red'
          },
          {
            id: 2,
            color: 'yellow'
          }
        ]
      }
    },
    Width: {
      type: String,
      default: '200px'
    },
    Height: {
      type: String,
      default: '200px'
    },
    PathWidth: {
      type: Number,
      default: 18
    },
    ISActiveMask: {
      type: Boolean,
      default: false
    },
    MaskColor: {
      type: String,
      default: '#0096ba'
    }
  },
  computed: {
    ...mapState({
      roadDirection: state => state.globalParam.roadDirection
    })
  },
  data () {
    return {
      maskmark: false,
      status: [
        {
          id: 1,
          name: 'East-Straight',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 2,
          name: 'East-Left',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 3,
          name: 'East-Right',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 4,
          name: 'East-Back ',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 5,
          name: 'West-Straight',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 6,
          name: 'West-Left',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 7,
          name: 'West-Right',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 8,
          name: 'West-Back',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 9,
          name: 'North-Straight',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 10,
          name: 'North-Left',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 11,
          name: 'North-Right',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 12,
          name: 'North-Back',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 13,
          name: 'South-Straight',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 14,
          name: 'South-Left',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 15,
          name: 'South-Right',
          isshow: false,
          color: '#0096ba'
        },
        {
          id: 16,
          name: 'South-Back',
          isshow: false,
          color: '#0096ba'
        }
      ]
    }
  },
  methods: {
    randShow () {
      let num1 = parseInt(Math.random() * (15 - 0 + 1) + 0)
      let num2 = parseInt(Math.random() * (15 - 0 + 1) + 0)
      for (let i = 0; i < 16; i++) {
        if (this.status[i].isshow) {
          this.status[i].isshow = false
        }
        if (i === num1 || i === num2) {
          this.status[i].isshow = true
        }
      }
    },
    refreshShow (showList) {
      if (showList.length > 16) {
        console.log('list can not be bigger than 16!')
      }
      this.maskmark = false
      if (showList.length <= 0) {
        if (this.ISActiveMask) {
          this.maskmark = true
        }
      }

      for (let i = 0; i < 16; i++) {
        if (this.status[i].isshow) {
          this.status[i].isshow = false
        }
      }
      for (let i = 0; i < 16; i++) {
        if (i < showList.length) {
          let id = showList[i].id
          if (id > 16 || id <= 0) {
            console.log('Id is invalied!')
            continue
          }
          this.status[id - 1].isshow = true
          this.status[id - 1].color = showList[i].color
        }
      }
    }
  },
  created () {
    this.refreshShow(this.showlist)
  },
  watch: {
    showlist: {
      handler: function (newList) {
        this.refreshShow(newList)
      },
      deep: true // 深度监听
    }
  }
}
</script>

<style scoped>
.invisible {
  visibility: hidden;
}
</style>
