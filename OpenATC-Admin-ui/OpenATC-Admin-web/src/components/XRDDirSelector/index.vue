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
    style=""
    viewBox="0 0 1024 1024"
    version="1.1"
    xmlns="http://www.w3.org/2000/svg"
    p-id="2200"
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
    <path d="M668.16 463.36c56.32 0 104.96-46.08 104.96-104.96 0-56.32-46.08-104.96-104.96-104.96S563.2 302.08 563.2 358.4c0 56.32 46.08 104.96 104.96 104.96z m0-174.08c38.4 0 69.12 30.72 69.12 69.12 0 38.4-30.72 69.12-69.12 69.12-38.4 0-69.12-30.72-69.12-69.12 0-38.4 30.72-69.12 69.12-69.12z"
      :fill="MaskColor" p-id="2762"
      :class="maskmark ? '' : 'invisible'"></path>
    <!-- </mask> -->
    <!-- ====================================[1 East-Straight 东向直行] ============================================= -->
    <path d="M999.936 316.928h-410.624l23.04-39.936-121.856 50.176 121.856 49.664-23.04-39.424h410.624z"
        p-id="2201"
        :stroke-width="PathWidth"
        :fill="status[0].color"
        :class="status[0].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[2 East-Left     东向左转]============================================= -->
    <path d="M825.856 336.896l-5.12-19.968c-2.048 0.512-207.36 55.808-254.464 261.12l-37.376-26.624 38.4 126.464 60.928-117.248-40.96 18.944c45.056-190.464 236.544-242.176 238.592-242.688z"
        p-id="2202"
        :stroke-width="PathWidth"
        :fill="status[1].color"
        :class="status[1].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[3 East-Right    东向右转]============================================= -->
    <path d="M617.984 143.872l39.424 22.528-50.176-122.368-49.664 122.368 39.424-23.04c15.872 84.992 77.824 182.784 275.456 193.536l1.024-20.48c-180.736-9.728-239.616-96.256-255.488-172.544z"
        p-id="2203"
        :stroke-width="PathWidth"
        :fill="status[2].color"
        :class="status[2].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[4 East-Back     东向掉头]============================================= -->
    <path d="M854.528 337.408h145.408v-20.48h-145.408c-33.28 0-60.416 27.136-60.416 60.416 0 32.768 26.624 59.904 59.392 60.416l-14.848 35.328 108.032-45.568-108.032-45.568 14.848 35.328c-21.504-0.512-38.912-18.432-38.912-39.936 0-22.016 17.92-39.936 39.936-39.936z"
        p-id="2204"
        :stroke-width="PathWidth"
        :fill="status[3].color"
        :class="status[3].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[5 West-Straight 西向直行]============================================= -->
    <path d="M533.504 696.832l-121.856-49.664 23.04 39.424H24.064v20.48h410.624l-23.04 39.936z"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[4].color"
        :class="status[4].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[6 West-Left     西向左转]============================================= -->
    <path d="M495.616 472.576l-38.4-126.464-61.44 117.248 40.96-18.944c-45.568 190.464-236.544 242.176-238.592 242.688l5.12 19.968c2.048-0.512 207.36-55.808 254.464-261.12l37.888 26.624z"
        p-id="2206"
        :stroke-width="PathWidth"
        :fill="status[5].color"
        :class="status[5].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[7 West-Right    西向右转]============================================= -->
    <path d="M427.008 880.128c-15.872-84.992-77.824-182.784-275.456-193.536l-1.024 20.48c181.248 10.24 239.616 96.768 255.488 173.056l-39.424-22.528 50.176 122.368 49.664-122.368-39.424 22.528z"
        p-id="2207"
        :stroke-width="PathWidth"
        :fill="status[6].color"
        :class="status[6].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[8 West-Back     西向掉头]============================================= -->
    <path d="M170.496 586.752l14.848-35.328-108.032 45.568 108.032 45.568-14.848-35.328c21.504 0.512 38.912 18.432 38.912 39.936 0 22.016-17.92 39.936-39.936 39.936H24.064v20.48h145.408c33.28 0 60.416-27.136 60.416-60.416 0-33.28-26.624-59.904-59.392-60.416z"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[7].color"
        :class="status[7].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[9 North-Straight北向直行]============================================= -->
    <path d="M337.408 434.688V24.064h-20.48v410.624l-39.936-23.04 50.176 121.856 49.664-121.856z"
        p-id="2209"
        :stroke-width="PathWidth"
        :fill="status[8].color"
        :class="status[8].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[10 North-Left   北向左转]============================================= -->
    <path d="M677.888 457.216l-117.248-60.928 18.944 40.96C389.12 391.68 337.408 200.192 336.896 198.144l-19.968 5.12c0.512 2.048 55.808 207.36 261.12 254.464l-26.624 37.376 126.464-37.888z"
        p-id="2210"
        :stroke-width="PathWidth"
        :fill="status[9].color"
        :class="status[9].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[11 North-Right  北向右转]============================================ -->
    <path d="M337.408 151.552l-20.48-1.024c-10.24 181.248-96.768 239.616-173.056 255.488l22.528-39.424-121.856 50.176 122.368 49.664-23.04-39.424c84.48-15.872 182.272-77.824 193.536-275.456z"
        p-id="2211"
        :stroke-width="PathWidth"
        :fill="status[10].color"
        :class="status[10].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[12 North-Back   北向掉头]============================================= -->
    <path d="M472.576 185.344l-45.568-108.032-45.568 108.032 35.328-14.848c-0.512 21.504-18.432 38.912-39.936 38.912-22.016 0-39.936-17.92-39.936-39.936V24.064h-20.48v145.408c0 33.28 27.136 60.416 60.416 60.416 32.768 0 59.904-26.624 60.416-59.392l35.328 14.848z"
        p-id="2212"
        :stroke-width="PathWidth"
        :fill="status[11].color"
        :class="status[11].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[13 South-Straight 南向直行]============================================= -->
    <path d="M747.008 612.352l-50.176-121.856-49.664 121.856 39.424-23.04v410.624h20.48v-410.624z"
        p-id="2213"
        :stroke-width="PathWidth"
        :fill="status[12].color"
        :class="status[12].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[14 South-Letf    南向左转]============================================= -->
    <path d="M445.44 566.272l26.624-37.376-126.464 38.4 117.248 60.928-18.944-40.96c190.464 45.568 242.176 236.544 242.688 238.592l19.968-5.12c0-2.56-55.296-207.36-261.12-254.464z"
        p-id="2214"
        :stroke-width="PathWidth"
        :fill="status[13].color"
        :class="status[13].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[15 South-Right   南向右转]============================================= -->
    <path d="M979.456 607.232l-122.368-49.664 23.04 39.424c-84.992 15.872-182.784 77.824-193.536 275.456l20.48 1.024c10.24-181.248 96.768-239.616 173.056-255.488l-22.528 39.424 121.856-50.176z"
        p-id="2215"
        :stroke-width="PathWidth"
        :fill="status[14].color"
        :class="status[14].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[16 South-Back    南向掉头]============================================ -->
    <path d="M646.656 794.112c-32.768 0-59.904 26.624-60.416 59.392l-35.328-14.848 45.568 108.032 45.568-108.032-35.328 14.848c0.512-21.504 18.432-38.912 39.936-38.912 22.016 0 39.936 17.92 39.936 39.936v145.408h20.48v-145.408c0-33.28-27.136-60.416-60.416-60.416z"
        p-id="2216"
        :stroke-width="PathWidth"
        :fill="status[15].color"
        :class="status[15].isshow ? '' : 'invisible'"
        ></path>
  </svg>
</template>
<script>
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
