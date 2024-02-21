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
    viewBox="0 0 200 200"
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
    <!-- <path d="M998.4 327.168h-403.456v-46.08l-105.984 61.44 105.984 60.928v-45.568H998.4z"
        p-id="2201"
        :stroke-width="PathWidth"
        :fill="status[0].color"
        :class="status[0].isshow ? '' : 'invisible'"
        ></path> -->
    <polygon points="195.3,60.9 121.7,60.9 121.7,48.9 95.8,63.9 121.7,78.8 121.7,66.9 195.3,66.9"
        p-id="2201"
        :stroke-width="PathWidth"
        :fill="status[0].color"
        :class="status[0].isshow ? '' : 'invisible'"/>
    <!-- ====================================[2 East-Left     东向左转]============================================= -->
    <!-- <path d="M821.76 357.888v-30.72c-2.048 0-184.32 2.56-230.4 192l-46.08-4.096 51.2 111.616 71.168-99.84-45.056-4.096c41.472-162.304 192.512-164.864 199.168-164.864z"
        p-id="2202"
        :stroke-width="PathWidth"
        :fill="status[1].color"
        :class="status[1].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M161.5,66.8l-0.8-2.9L160,61c-1.6,0.4-36.9,9.9-48.6,45.4l-12-1.6l11.3,27.7l18.3-23.6l-11.6-1.6
        C128.6,75.7,161.2,66.9,161.5,66.8z"
        p-id="2202"
        :stroke-width="PathWidth"
        :fill="status[1].color"
        :class="status[1].isshow ? '' : 'invisible'"/>
    <!-- ====================================[3 East-Right    东向右转]============================================= -->
    <!-- <path d="M654.336 180.736l43.52 8.192-40.96-115.712-79.872 93.184 45.568 8.704c3.072 69.632 43.008 171.52 247.296 182.784l1.536-30.72c-134.656-7.68-209.408-58.368-217.088-146.432z"
        p-id="2203"
        :stroke-width="PathWidth"
        :fill="status[2].color"
        :class="status[2].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M123.6,34.3l11.5-0.7l-16.5-25l-13.4,26.8l12.1-0.7c4.9,14.4,18.2,30.2,53,32.2l0.3-6C146,59.5,129.8,50.4,123.6,34.3z"
        p-id="2203"
        :stroke-width="PathWidth"
        :fill="status[2].color"
        :class="status[2].isshow ? '' : 'invisible'"/>
    <!-- ====================================[4 East-Back     东向掉头]============================================= -->
    <!-- <path d="M832.512 357.888H998.4v-30.72h-165.888c-39.936 0-72.704 29.184-72.704 65.536 0 35.84 31.744 65.024 71.168 65.536v46.08l105.984-61.44-105.984-61.44v46.08c-22.528-0.512-40.448-15.872-40.448-34.816 0-19.456 18.944-34.816 41.984-34.816z"
        p-id="2204"
        :stroke-width="PathWidth"
        :fill="status[3].color"
        :class="status[3].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M166.9,66.9h28.4v-6h-28.4c-7.1,0-12.8,5.7-12.8,12.8c0,6.6,5,12,11.3,12.7l0,12l25.9-14.9l-25.9-15l0,11.8
        c-3-0.7-5.3-3.4-5.3-6.6C160.1,69.9,163.1,66.9,166.9,66.9z"
        p-id="2204"
        :stroke-width="PathWidth"
        :fill="status[3].color"
        :class="status[3].isshow ? '' : 'invisible'"/>
    <!-- ====================================[5 West-Straight 西向直行]============================================= -->
    <!-- <path d="M535.04 681.472l-105.984-60.928v45.568H25.6v30.72h403.456v46.08z"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[4].color"
        :class="status[4].isshow ? '' : 'invisible'"
        ></path> -->
    <polygon points="104.2,136.1 78.3,121.2 78.3,133.1 4.7,133.1 4.7,139.1 78.3,139.1 78.3,151.1"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[4].color"
        :class="status[4].isshow ? '' : 'invisible'"/>
    <!-- ====================================[6 West-Left     西向左转]============================================= -->
    <!-- <path d="M479.232 509.44l-51.2-111.616-71.168 99.84 45.056 4.096c-41.984 161.28-193.024 164.352-199.68 164.352v30.72c2.048 0 184.32-2.56 230.4-192l46.592 4.608z"
        p-id="2206"
        :stroke-width="PathWidth"
        :fill="status[5].color"
        :class="status[5].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M89.3,67.6L71,91.2l11.6,1.6c-11.1,31.5-43.7,40.3-44,40.4l0.8,2.9L40,139c1.6-0.4,36.9-9.9,48.6-45.4l12,1.6L89.3,67.6z"
        p-id="2206"
        :stroke-width="PathWidth"
        :fill="status[5].color"
        :class="status[5].isshow ? '' : 'invisible'"/>
    <!-- ====================================[7 West-Right    西向右转]============================================= -->
    <!-- <path d="M400.384 849.408c-3.072-69.632-43.008-171.52-247.296-182.784l-1.536 30.72c135.68 7.68 210.432 57.856 218.112 146.432l-43.52-8.192 40.96 115.712 79.872-93.184-46.592-8.704z"
        p-id="2207"
        :stroke-width="PathWidth"
        :fill="status[6].color"
        :class="status[6].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M82.7,165.3c-4.9-14.4-18.2-30.2-53-32.2l-0.3,6c24.7,1.4,40.9,10.5,47.1,26.6l-11.5,0.7l16.5,25l13.4-26.8L82.7,165.3z"
        p-id="2207"
        :stroke-width="PathWidth"
        :fill="status[6].color"
        :class="status[6].isshow ? '' : 'invisible'"/>
    <!-- ====================================[8 West-Back     西向掉头]============================================= -->
    <!-- <path d="M193.024 566.272v-46.08l-105.984 61.44 105.984 61.44v-46.08c22.528 0.512 40.448 15.872 40.448 34.816 0 18.944-18.944 34.816-41.984 34.816H25.6v30.72h165.888c39.936 0 72.704-29.184 72.704-65.536 0-35.84-31.744-65.024-71.168-65.536z"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[7].color"
        :class="status[7].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M34.6,113.6l0-12L8.7,116.5l25.9,15l0-11.8c3,0.7,5.3,3.4,5.3,6.6c0,3.7-3,6.8-6.8,6.8H4.7v6h28.4
        c7.1,0,12.8-5.7,12.8-12.8C45.9,119.8,41,114.4,34.6,113.6z"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[7].color"
        :class="status[7].isshow ? '' : 'invisible'"  />
    <!-- ====================================[9 North-Straight北向直行]============================================= -->
    <!-- <path d="M357.888 429.056V25.6h-30.72v403.456h-46.08l61.44 105.984 60.928-105.984z"
        p-id="2209"
        :stroke-width="PathWidth"
        :fill="status[8].color"
        :class="status[8].isshow ? '' : 'invisible'"
        ></path> -->
    <polygon points="66.9,78.3 66.9,4.7 60.9,4.7 60.9,78.3 48.9,78.3 63.9,104.2 78.8,78.3"
        p-id="2209"
        :stroke-width="PathWidth"
        :fill="status[8].color"
        :class="status[8].isshow ? '' : 'invisible'"/>
    <!-- ====================================[10 North-Left   北向左转]============================================= -->
    <!-- <path d="M626.176 428.032l-99.84-71.168-4.096 45.056c-161.28-41.984-164.352-193.024-164.352-199.68h-30.72c0 2.048 2.56 184.32 192 230.4l-4.096 46.08 111.104-50.688z"
        p-id="2210"
        :stroke-width="PathWidth"
        :fill="status[9].color"
        :class="status[9].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M132.4,89.3L108.8,71l-1.6,11.6c-31.5-11.1-40.3-43.7-40.4-44L61,40c0.4,1.6,9.9,36.9,45.4,48.6l-1.6,12L132.4,89.3z"
      p-id="2210"
      :stroke-width="PathWidth"
      :fill="status[9].color"
      :class="status[9].isshow ? '' : 'invisible'"/>
    <!-- ====================================[11 North-Right  北向右转]============================================ -->
    <!-- <path d="M357.888 153.088l-30.72-1.536c-7.68 135.68-57.856 210.432-146.432 218.112l8.192-43.52-115.712 40.96 93.184 79.872 8.704-45.568c69.632-4.096 171.52-44.032 182.784-248.32z"
        p-id="2211"
        :stroke-width="PathWidth"
        :fill="status[10].color"
        :class="status[10].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M66.9,29.6l-6-0.3C59.5,54,50.4,70.2,34.3,76.4l-0.7-11.5l-25,16.5l26.8,13.4l-0.7-12.1C49.1,77.8,64.9,64.4,66.9,29.6z"
      p-id="2211"
      :stroke-width="PathWidth"
      :fill="status[10].color"
      :class="status[10].isshow ? '' : 'invisible'"/>
    <!-- ====================================[12 North-Back   北向掉头]============================================= -->
    <!-- <path d="M503.808 193.024L442.368 87.04l-61.44 105.984h46.08c-0.512 22.528-15.872 40.448-34.816 40.448-18.944 0-34.816-18.944-34.816-41.984V25.6h-30.72v165.888c0 39.936 29.184 72.704 65.536 72.704 35.84 0 65.024-31.744 65.536-71.168h46.08z"
        p-id="2212"
        :stroke-width="PathWidth"
        :fill="status[11].color"
        :class="status[11].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M98.3,35.4L83.5,9.4l-15,25.9l11.7,0c-0.9,2.7-3.4,4.6-6.4,4.6c-3.7,0-6.8-3-6.8-6.8V4.7h-6v28.4c0,7.1,5.7,12.8,12.8,12.8
      c6.3,0,11.5-4.6,12.6-10.6L98.3,35.4z"
        p-id="2212"
        :stroke-width="PathWidth"
        :fill="status[11].color"
        :class="status[11].isshow ? '' : 'invisible'"/>
    <!-- ====================================[13 South-Straight 南向直行]============================================= -->
    <!-- <path d="M742.912 594.944l-61.44-105.984-60.928 105.984h45.568V998.4h30.72v-403.456z"
        p-id="2213"
        :stroke-width="PathWidth"
        :fill="status[12].color"
        :class="status[12].isshow ? '' : 'invisible'"
        ></path> -->
    <polygon points="151.1,121.7 136.1,95.8 121.2,121.7 133.1,121.7 133.1,195.3 139.1,195.3 139.1,121.7"
        p-id="2213"
        :stroke-width="PathWidth"
        :fill="status[12].color"
        :class="status[12].isshow ? '' : 'invisible'"/>
    <!-- ====================================[14 South-Letf    南向左转]============================================= -->
    <!-- <path d="M504.832 590.848l4.096-46.08-111.616 51.2 99.84 71.168 4.096-45.056c161.28 41.984 164.352 193.024 164.352 199.68h30.72c0.512-2.048-2.048-184.832-191.488-230.912z"
        p-id="2214"
        :stroke-width="PathWidth"
        :fill="status[13].color"
        :class="status[13].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M93.6,111.4l1.6-12l-27.7,11.3L91.2,129l1.6-11.6c31.5,11.1,40.3,43.7,40.4,44l2.9-0.8l2.9-0.7
        C138.6,158.5,129.1,123.1,93.6,111.4z"
        p-id="2214"
        :stroke-width="PathWidth"
        :fill="status[13].color"
        :class="status[13].isshow ? '' : 'invisible'"/>
    <!-- ====================================[15 South-Right   南向右转]============================================= -->
    <!-- <path d="M950.784 657.408l-93.184-79.872-8.704 45.568c-69.632 3.072-171.52 43.008-182.784 247.296l30.72 1.536c7.68-135.68 58.368-210.432 146.432-218.112l-8.192 43.52 115.712-39.936z"
        p-id="2215"
        :stroke-width="PathWidth"
        :fill="status[14].color"
        :class="status[14].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M191.3,118.6l-26.8-13.4l0.7,12.1c-14.4,4.9-30.2,18.2-32.2,53l6,0.3c1.4-24.7,10.5-40.9,26.6-47.1l0.7,11.5L191.3,118.6z"
        p-id="2215"
        :stroke-width="PathWidth"
        :fill="status[14].color"
        :class="status[14].isshow ? '' : 'invisible'"/>
    <!-- ====================================[16 South-Back    南向掉头]============================================ -->
    <!-- <path d="M631.296 759.808c-35.84 0-65.024 31.744-65.536 71.168h-46.08l61.44 105.984 61.44-105.984h-46.08c0.512-22.528 15.872-40.448 34.816-40.448 18.944 0 34.816 18.944 34.816 41.984V998.4h30.72v-165.888c0-39.936-29.184-72.704-65.536-72.704z"
        p-id="2216"
        :stroke-width="PathWidth"
        :fill="status[15].color"
        :class="status[15].isshow ? '' : 'invisible'"
        ></path> -->
    <path d="M126.3,154.1c-6.4,0-11.7,4.7-12.6,10.9l-12.1,0l14.9,25.9l15-25.9l-11.7,0c0.8-2.8,3.4-4.9,6.5-4.9c3.7,0,6.8,3,6.8,6.8
        v28.4h6v-28.4C139.1,159.8,133.4,154.1,126.3,154.1z"
        p-id="2216"
        :stroke-width="PathWidth"
        :fill="status[15].color"
        :class="status[15].isshow ? '' : 'invisible'"/>
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
