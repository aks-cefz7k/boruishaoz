<template>
<svg t="1545378648902" class="icon"
    style=""
    viewBox="0 0 1024 1024"
    version="1.1"
    xmlns="http://www.w3.org/2000/svg"
    p-id="2200"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    :width="Width" :height="Height">
    <!-- ====================================[1 East-Straight 东向直行]         :fill="this.getStatus(0).color"
        :class="this.getStatus(0).isshow ? '' : 'invisible'"============================================= -->
    <path d="M854.231579 262.736842H269.473684l18.863158-43.115789-118.568421 49.852631 118.568421 49.852632-18.863158-43.11579h584.757895z"
        p-id="2201"
        :stroke-width="PathWidth"
        :fill="status[0].color"
        :class="status[0].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[2 East-Left     东向左转]============================================= -->
    <path d="M679.073684 262.736842c-2.694737 0-216.926316-6.736842-319.326316 161.684211L327.410526 390.736842l2.694737 128 90.273684-91.621053-45.810526 1.347369C471.578947 269.473684 676.378947 276.210526 679.073684 276.210526h175.157895v-13.473684h-175.157895z"
        p-id="2202"
        :stroke-width="PathWidth"
        :fill="status[1].color"
        :class="status[1].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[3 East-Right    东向右转]============================================= -->
    <path d="M679.073684 262.736842c-2.694737 0-207.494737 6.736842-304.505263-152.252631l45.810526 1.347368-90.273684-91.621053-2.694737 128 33.684211-33.68421c92.968421 154.947368 282.947368 161.684211 313.936842 161.68421h179.2v-13.473684h-175.157895z"
        p-id="2203"
        :stroke-width="PathWidth"
        :fill="status[2].color"
        :class="status[2].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[4 East-Back     东向掉头]============================================= -->
    <path d="M619.789474 276.210526h234.442105v-13.473684H619.789474c-48.505263 0-87.578947 39.073684-87.578948 87.578947s39.073684 87.578947 87.578948 87.578948h48.505263l-18.863158 43.115789L768 431.157895l-118.568421-49.852632 18.863158 43.11579H619.789474c-40.421053 0-74.105263-33.684211-74.105263-74.105264s33.684211-74.105263 74.105263-74.105263z"
        p-id="2204"
        :stroke-width="PathWidth"
        :fill="status[3].color"
        :class="status[3].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[5 West-Straight 西向直行]============================================= -->
    <path d="M854.231579 754.526316l-118.568421-49.852632 18.863158 43.11579H169.768421v13.473684H754.526316l-18.863158 43.115789z"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[4].color"
        :class="status[4].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[6 West-Left     西向左转]============================================= -->
    <path d="M696.589474 633.263158l-2.694737-128-90.273684 91.621053 45.810526-1.347369C552.421053 754.526316 347.621053 747.789474 344.926316 747.789474h-175.157895v13.473684h179.2c32.336842 0 220.968421-6.736842 313.936842-161.684211l33.684211 33.684211z"
        p-id="2206"
        :stroke-width="PathWidth"
        :fill="status[5].color"
        :class="status[5].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[7 West-Right    西向右转]============================================= -->
    <path xmlns="http://www.w3.org/2000/svg" d="M662.905263 909.473684c-101.052632-168.421053-316.631579-161.684211-317.978947-161.68421h-175.157895v13.473684h175.157895c2.694737 0 206.147368-6.736842 304.505263 152.252631l-45.810526-1.347368 90.273684 91.621053 2.694737-128-33.684211 33.68421z"
        p-id="2207"
        :stroke-width="PathWidth"
        :fill="status[6].color"
        :class="status[6].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[8 West-Back     西向掉头]============================================= -->
    <path d="M404.210526 586.105263h-48.505263l18.863158-43.115789L256 592.842105l118.568421 49.852632-18.863158-43.11579H404.210526c40.421053 0 74.105263 33.684211 74.105263 74.105264S444.631579 747.789474 404.210526 747.789474H169.768421v13.473684H404.210526c48.505263 0 87.578947-39.073684 87.578948-87.578947s-39.073684-87.578947-87.578948-87.578948z"
        p-id="2205"
        :stroke-width="PathWidth"
        :fill="status[7].color"
        :class="status[7].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[9 North-Straight北向直行]============================================= -->
    <path d="M262.736842 169.768421V754.526316l-43.115789-18.863158 49.852631 118.568421 49.852632-118.568421-43.11579 18.863158V169.768421z"
        p-id="2209"
        :stroke-width="PathWidth"
        :fill="status[8].color"
        :class="status[8].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[10 North-Left   北向左转]============================================= -->
    <path d="M262.736842 344.926316c0 2.694737-6.736842 216.926316 161.684211 319.326316L390.736842 696.589474l128-2.694737-91.621053-90.273684 1.347369 45.810526C269.473684 552.421053 276.210526 347.621053 276.210526 344.926316v-175.157895h-13.473684v175.157895z"
        p-id="2210"
        :stroke-width="PathWidth"
        :fill="status[9].color"
        :class="status[9].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[11 North-Right  北向右转]============================================ -->
    <path d="M262.736842 344.926316c0 2.694737 6.736842 206.147368-152.252631 304.505263l1.347368-45.810526-91.621053 90.273684 128 2.694737-33.68421-33.684211c154.947368-92.968421 161.684211-282.947368 161.68421-313.936842V169.768421h-13.473684v175.157895z"
        p-id="2211"
        :stroke-width="PathWidth"
        :fill="status[10].color"
        :class="status[10].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[12 North-Back   北向掉头]============================================= -->
    <path xmlns="http://www.w3.org/2000/svg" d="M276.210526 404.210526V169.768421h-13.473684V404.210526c0 48.505263 39.073684 87.578947 87.578947 87.578948s87.578947-39.073684 87.578948-87.578948v-48.505263l43.115789 18.863158L431.157895 256l-49.852632 118.568421 43.11579-18.863158V404.210526c0 40.421053-33.684211 74.105263-74.105264 74.105263S276.210526 444.631579 276.210526 404.210526z"
        p-id="2212"
        :stroke-width="PathWidth"
        :fill="status[11].color"
        :class="status[11].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[13 South-Straight 南向直行]============================================= -->
    <path d="M754.526316 169.768421l-49.852632 118.568421 43.11579-18.863158v584.757895h13.473684V269.473684l43.115789 18.863158z"
        p-id="2213"
        :stroke-width="PathWidth"
        :fill="status[12].color"
        :class="status[12].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[14 South-Letf    南向左转]============================================= -->
    <path d="M633.263158 327.410526l-128 2.694737 91.621053 90.273684-1.347369-45.810526C754.526316 471.578947 747.789474 676.378947 747.789474 679.073684v175.157895h13.473684v-175.157895-4.042105c0-32.336842-6.736842-220.968421-161.684211-313.936842l33.684211-33.684211z"
        p-id="2214"
        :stroke-width="PathWidth"
        :fill="status[13].color"
        :class="status[13].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[15 South-Right   南向右转]============================================= -->
    <path d="M909.473684 361.094737c-168.421053 101.052632-161.684211 316.631579-161.68421 317.978947v175.157895h13.473684v-175.157895c0-2.694737-6.736842-206.147368 152.252631-304.505263l-1.347368 45.810526 91.621053-90.273684-128-2.694737 33.68421 33.684211z"
        p-id="2215"
        :stroke-width="PathWidth"
        :fill="status[14].color"
        :class="status[14].isshow ? '' : 'invisible'"
        ></path>
    <!-- ====================================[16 South-Back    南向掉头]============================================ -->
    <path xmlns="http://www.w3.org/2000/svg"
        d="M586.105263 619.789474v48.505263l-43.115789-18.863158L592.842105 768l49.852632-118.568421-43.11579 18.863158V619.789474c0-40.421053 33.684211-74.105263 74.105264-74.105263s74.105263 33.684211 74.105263 74.105263v234.442105h13.473684V619.789474c0-48.505263-39.073684-87.578947-87.578947-87.578948s-87.578947 39.073684-87.578948 87.578948z"
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
    }
  },
  data () {
    return {
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
      console.log(num1)
      console.log(num2)
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
    // console.log('start.......')
    // setInterval(() => {
    //   this.randShow()
    // }, 3000)
    this.refreshShow(this.showlist)
  },
  watch: {
    showlist: {
      handler: function (newList) {
        console.log(newList)
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
