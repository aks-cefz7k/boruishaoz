<template>
  <div class="wrap">
    <!-- <kiss-map mapWidth="1922px" mapHeight="1080px" class="zindex"></kiss-map> -->
    <div style="width: 800px; height: 800px; margin: 0; padding:0; display: inline-block">
      <kiss-button @click="handleClick">submit</kiss-button>
      <kiss-button btn-type="kiss-btn-left-bk">submit</kiss-button>
      <kiss-button btn-type="kiss-btn-center-bk"
                   :disabled=true>submit</kiss-button>
      <kiss-button btn-type="kiss-btn-right-bk"
                   FontSize="14px">submit</kiss-button>
      <kiss-button @click="openMessageBox">openMessBox</kiss-button>
      <kiss-input />
      <kiss-search-input />
      <input type="text"
             v-model="Height">
      <input type="text"
             v-model="Width">
      <div style=" width:1200px;">
        <div style="height: 600px; width: 300px; border: 1px solid red;">
          <kiss-nav-menu @click="menuClick"></kiss-nav-menu>
        </div>
        <div style="height: 105px; width: 100%; border: 1px solid red;box-sizing:border-box;">
          <kiss-horizontal-menu @click="horizontalMenuClick"></kiss-horizontal-menu>
        </div>
        <div style="height: 600px; width: 300px; float:left;">
          <kiss-select :list="[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]" />
        </div>
        <div style="height: 600px; width: 300px; float:left;">
          <kiss-tip v-model="showTips"
                    type="alarm">
            <h2>asdfasdaaaaaaaa</h2><input type="text"
                   style="width: 50px; height: 30px; margin: none;">
            <div style="width:200px; height:100px;"></div>
          </kiss-tip>
        </div>
        <div style="height: 600px; width: 300px; float:left;">
          <kiss-tip v-if="showTips1"
                    v-model="showTips1"
                    :dragable="true"
                    :resizeable="true"
                    type="default"
                    :width="Width"
                    :height="Height"
                    :zindex=9999>
            <span style="float: left">jjjjjjjj</span>
            <input type="number"
                   name=""
                   id="">
            <input type="text">
            <div style="width:80%; height:100px; background: yellow; float: left; opacity: 0.1"></div>
            <div style="width:200px; height:100px;"></div>
          </kiss-tip>
        </div>
        <div style="height: 600px; width: 300px; float:left;">
          <kiss-mutiple-tips :btn-id="2">
            <div class='header'></div>
          </kiss-mutiple-tips>
        </div>
        <kiss-compass />
        <div style="clear:both;"></div>
      </div>
    </div>
    <kiss-date-picker />

    <xdr-dir-selector Width="300px"
                      Height="300px"
                      :showlist="dirshow"></xdr-dir-selector>
    <!-- <div class="test">
    <CirleMenu type="middle-around" :number="4" :colors="colorsArr">
      <button type="button" slot="item_btn"></button>
      <a slot="item_1" class="fa fa-twitter fa-lg" herf="#" ></a>
      <a slot="item_2" class="fa fa-weixin fa-lg" herf="#" ></a>
      <a slot="item_3" class="fa fa-weibo fa-lg" herf="#" ></a>
      <a slot="item_4" class="fa fa-github fa-lg" herf="#" ></a>
    </CirleMenu>
  </div> -->
    <SatausBar :LevelsInfo="StatusBarTetsInfoList"
               ArrowColor="#ffffff"
               Width="300px"
               Height="100px"
               :TotalLevel="10"
               :ViewBox="ViewBoxCut"
               :CurLevel="curlevel"></SatausBar>
    <div style="width:800px;height:500px;">
      <kiss-panel tittle="kiss-panel">
        kisspanel test
      </kiss-panel>
      <kiss-tablebutton :tabList="tabletittle"
                        :defaultCheckedName="'table3'"
                        @leaveStates="onTableButtonLeave"
                        @entryStates="onTableButtonEntry"
                        width="90px">
      </kiss-tablebutton>
    </div>
    <kiss-head title="XXX路口"
               Width="1920">
      <div slot="left"
           class="leftpart">
        <div>leftPart1</div>
        <div>leftPart2</div>
      </div>
      <div slot="right">
        <kiss-button>button1</kiss-button>
        <kiss-button>button2</kiss-button>
      </div>
    </kiss-head>
    <dash-board style="width: 170px; height: 170px;"
                :curPercent="percent"
                :option="dashboardOpt"
                id="test1" />
    <dash-board style="width: 170px; height: 170px;"
                :curPercent="percent"
                :option="dashboardOpt"
                id="test2" />
    <div style="height:100px">
      <kiss-horizontal :datalist="datalist"
                       :mainwidth="mainwidth1"
                       :mainheight="mainheight1"
                       :display="display"
                       horizontalDistance="3px">
        <div class="phase-description"
             slot="fatherslot">
          <xdr-dir-selector Width="23px"
                            Height="23px"
                            :showlist="dirshow"></xdr-dir-selector>
        </div>
      </kiss-horizontal>
    </div>
    <div style="height:100%">
      <kiss-horizontalchildren
                               :name="name[2]"
                               :dataList="list1"
                               :display="display"
                               mainheight="50px"
                               backColor="#366069"
                               horizontalDistance="3px">
        <div class="phase-description"
             slot="leftslot">
          <xdr-dir-selector Width="23px"
                            Height="23px"
                            :showlist="dirshow"></xdr-dir-selector>
        </div>
        <!-- <div slot="leftslot" style="color: #fff; text-align:center; background: #0a4958; width: 25px; height: 23px;">55</div> -->
      </kiss-horizontalchildren>
      <kiss-horizontalchildren :name="name[0]"
                               :dataList="list2"
                               :display="display"
                               mainheight="50px"
                               backColor="rgba(87, 108, 113, 0.2)"
                               horizontalDistance="3px">
        <div class="phase-description"
             slot="leftslot">
          <xdr-dir-selector Width="23px"
                            Height="23px"
                            :showlist="dirshow"></xdr-dir-selector>
        </div>
        <!-- <div slot="leftslot" style="color: #fff; text-align:center; background: #0a4958; width: 25px; height: 23px;">55</div> -->
      </kiss-horizontalchildren>
      <kiss-horizontalchildren :name="name[1]"
                               :dataList="list3"
                               :display="display"
                               mainheight="50px"
                               horizontalDistance="10px">
        <div style="position: relative"  slot="leftslot">
          <div class="phase-description">
          <xdr-dir-selector Width="23px"
                            Height="23px"
                            :showlist="dirshow"></xdr-dir-selector>
        </div>
        </div>
        <!-- <div slot="leftslot" style="color: #fff; text-align:center; background: #0a4958; width: 25px; height: 23px;">55</div> -->
      </kiss-horizontalchildren>
      <kiss-simulation-progress :curseconds="curseconds" :totalseconds="totalseconds" :starttime="starttime" :endtime="endtime"></kiss-simulation-progress>
      <kiss-message-box :visible="messageboxVisible" text="是否返回场景列表?" @cancle="handleMessageboxCancle" @ok="handleMessageboxOk"/>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      showTips: true,
      Tragable: false,
      showTips1: false,
      Width: '',
      Height: '',
      count: 4,
      percent: 0,
      icons: [],
      dirshow: [
        {
          id: 1,
          color: 'yellow'
        }, {
          id: 2,
          color: 'red'
        }
      ],
      list: [{ 'isLink': false }, { 'isLink': true, url: '/doo' }, { 'isLink': true, url: '/foo' }, { 'isLink': false }],
      position: 'bottom-left',
      colorsArr: ['#0a4958', '#42daff', '#42daff', '#42daff', '#42daff'],
      colorsList: ['#9AFF9A', '#42daff', '#545454', '#00FF00', '#EEEE00', '#FF0000',
        '#C1FFC1', '#BF3EFF', '#9AFF9A', '#42daff', '#545454', '#00FF00', '#EEEE00', '#FF0000', '#C1FFC1', '#BF3EFF'],
      colorsList2: ['#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff', '#42daff'],
      StatusBarTetsInfoList: [
        {
          level: 1,
          levelname: '可以飙车了',
          color: '#00841C'
        },
        {
          level: 2,
          levelname: '还好吧',
          color: '#31C750'
        },
        {
          level: 3,
          levelname: '有点拥堵',
          color: '#F6D40A'
        },
        {
          level: 4,
          levelname: '不行了受不了了',
          color: '#FCA824'
        },
        {
          level: 5,
          levelname: '堵死了',
          color: '#EA423B'
        }
      ],
      curlevel: 0,
      ViewBoxCut:
      {
        x: 0,
        y: 380,
        w: 1024,
        h: 350
      },
      tabletittle: ['table1', 'table2', 'table3'],
      dashboardOpt: {
        style: 'bar',
        bar_radius: 200,
        bar_bg_w: 20,
        bar_w: 18,
        bar_bg_color: '#193b43',
        bar_bg_ring_color: '#193b43',
        bar_start_color: '#42daff',
        bar_stop_color: 'red',
        title: '交通强度',
        sides_show: true,
        title_show: true,
        title_font_color: '#FFFFFF',
        title_font_size: 40,
        title_font_family: 'HanHei SC, PingFang SC, Helvetica Neue Thin, Helvetica, STHeitiSC-Light, Arial, sans-serif',
        sides_font_color: '#FFFFFF',
        sides_font_size: 30,
        sides_font_family: 'HanHei SC, PingFang SC, Helvetica Neue Thin, Helvetica, STHeitiSC-Light, Arial, sans-serif',
        show_center_num: true,
        center_font_size: 100,
        center_font_color: '#FFFFFF'
      },
      mainwidth1: '400px',
      mainheight1: '25px',
      mainwidth: '400px',
      mainheight: '25px',
      display: true,
      datalist: [
        {
          value: 5,
          name: '测试',
          color: 'yellow'
        },
        {
          value: 1,
          name: '吃饭'
        }
      ],
      name: ['曹操', '刘备', '孙权'],
      value: [55, 80, 39],
      maxNum: 50,
      maxNum1: 50,
      maxNum2: 10,
      // horizontalDistance: '10px'
      list1: [{value: 39, maxNum: 50}],
      list2: [{value: 55, maxNum: 50}],
      list3: [{value: 7, maxNum: 10, color: 'red'}, {value: 80, maxNum: 100}],
      progressPercentage: 0,
      curseconds: 0,
      totalseconds: 86400,
      starttime: '2019-04-25 00:00:00',
      endtime: '2019-04-26 00:00:00',
      messageboxVisible: false
    }
  },
  methods: {
    handleClick () {
      if (this.showTips) {
        this.showTips = false
        console.log('close')
      } else {
        this.showTips = true
        console.log('show')
      }
    },
    menuClick (event, index) {
      console.log(event)
      console.log(index)
    },
    horizontalMenuClick (event, index) {
      console.log(event)
      console.log(index)
    },
    showRandom () {
      /** max - 期望的最大值
      * min - 期望的最小值
      * parseInt(Math.random()*(max-min+1)+min,10);
      * Math.floor(Math.random()*(max-min+1)+min);
      **/
      let num1 = parseInt(Math.random() * (16 - 1 + 1) + 1)
      let num2 = parseInt(Math.random() * (16 - 1 + 1) + 1)
      // this.curlevel = parseInt(Math.random() * (10 - 1 + 1) + 1)
      this.curlevel = 0
      // console.log(this.curlevel)
      // let num3 = parseInt(Math.random() * (16 - 1 + 1) + 1)
      // let num4 = parseInt(Math.random() * (16 - 1 + 1) + 1)
      // this.dirshow = []
      this.dirshow[0].id = num1
      this.dirshow[0].color = this.colorsList[num1 - 1]
      this.dirshow[1].id = num2
      this.dirshow[1].color = this.colorsList[num2 - 1]

      this.Width = parseInt(Math.random() * (300 - 1 + 1) + 1) + 'px'
      this.Height = parseInt(Math.random() * (300 - 1 + 1) + 1) + 'px'
      this.datalist[0].value = num1
      this.datalist[1].value = num2
      this.maxNum1 = num2
      //   this.dirshow.push(
      //     {
      //       id: num1,
      //       color: this.colorsList2[num1 - 1]
      //     }, {
      //       id: num2,
      //       color: this.colorsList2[num2 - 1]
      //     },
      //     {
      //       id: num3,
      //       color: this.colorsList2[num3 - 1]
      //     }, {
      //       id: num4,
      //       color: this.colorsList2[num4 - 1]
      //     }
      //   )
      // }
    },
    onTableButtonLeave (index) {
      console.log(`onTableButtonLeave`)
      console.log(index)
    },
    onTableButtonEntry (index) {
      console.log(`onTableButtonEntry`)
      console.log(index)
    },
    getPercentage (nowProgress) {
      const step = 864
      return nowProgress + step
    },
    openMessageBox () {
      this.messageboxVisible = true
    },
    handleMessageboxCancle () {
      this.messageboxVisible = false
    },
    handleMessageboxOk () {
      this.messageboxVisible = false
    }
  },
  watch: {
    showTips: function (val) {
      this.showTips1 = !val
    },
    showTips1: function (val) {
      this.showTips = !val
    }
  },
  mounted () {
    const timer = setInterval(() => {
      this.showRandom()
      this.percent = 130
      if (this.percent === 100) this.percent = 0
      if (this.curseconds < this.totalseconds) {
        this.curseconds = this.getPercentage(this.curseconds)
      } else {
        clearInterval(timer)
      }
    }, 1000)
  }
}
</script>
<style scoped>
.wrap {
  background: #333333;
  width: 100%;
  height: 100%;
}
.zindex {
  position: absolute;
  z-index: -1;
}
.select-containaer {
  float: left;
  margin: 0;
  padding: 0;
  width: 200px;
  height: 300px;
}

.functionLayout {
  position: fixed;
  top: 100px;
  width: 80px;
  height: 550px;
}
.test {
  width: 100px;
  height: 100px;
  left: 300px;
  top: 300xp;
  margin-left: 300px;
}
.leftpart > div {
  float: left;
  line-height: 50px;
}
.phase-description {
  /* background: #43b4cf;
  width: 25px;
  height: 23px; */

  /* width: 18px;
  height: 30px;
  background: yellow;
  position: absolute;
  top: 0;
  left: 9px; */
  width: 34px;
  height: 30px;
  background: url('../kisscomps/components/image/phaseHexagon/phasebg.png');
  background-size: 34px 30px;
  box-sizing: border-box;
  padding-left: 5px;
  padding-top: 4px;
}
/* .phase-description::before {
  content: "";
  width: 0;
  height: 0;
  border-right: 9px yellow solid;
  border-left: none;
  border-top: 15px solid transparent;
  border-bottom: 15px solid transparent;
  position: absolute;
  top: 0;
  left: -9px;
}
.phase-description::after {
  content: "";
  width: 0;
  height: 0;
  border-left: 9px yellow solid;
  border-right: none;
  border-top: 15px solid transparent;
  border-bottom: 15px solid transparent;
  position: absolute;
  top: 0;
  right: -9px;
} */
</style>
