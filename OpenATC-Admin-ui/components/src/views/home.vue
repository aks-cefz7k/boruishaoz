<template>
  <div class="wrap">
    <xdr-dir-selector Width="300px"
                      Height="300px"
                      :showlist="dirshow"></xdr-dir-selector>

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
      </kiss-horizontalchildren>
      <kiss-simulation-progress :curseconds="curseconds" :totalseconds="totalseconds" :starttime="starttime" :endtime="endtime"></kiss-simulation-progress>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dirshow: [
        {
          id: 1,
          color: '#77FB65'
        }, {
          id: 2,
          color: '#E24B4B'
        }
      ],
      datalist: [
        {
          value: 5,
          name: '测试',
          color: '#77FB65'
        },
        {
          value: 1,
          name: '吃饭'
        }
      ],
      mainwidth1: '400px',
      mainheight1: '25px',
      mainwidth: '400px',
      mainheight: '25px',
      display: true,
      name: ['曹操', '刘备', '孙权'],
      list1: [{value: 39, maxNum: 50}],
      list2: [{value: 55, maxNum: 50}],
      list3: [{value: 7, maxNum: 10, color: 'red'}, {value: 80, maxNum: 100}],
      progressPercentage: 0,
      curseconds: 0,
      totalseconds: 86400,
      starttime: '2019-04-25 00:00:00',
      endtime: '2019-04-26 00:00:00'
    }
  },
  methods: {
  },
  watch: {
  },
  mounted () {
  }
}
</script>
<style scoped>
.wrap {
  /* background: #333333; */
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
