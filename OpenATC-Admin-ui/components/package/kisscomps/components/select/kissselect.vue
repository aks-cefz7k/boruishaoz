<template>
  <select :data-placeholder="placeHolder"
    name="country"
    class="chosen-select"
    style="width:253px">
    <option v-for="item of list" :value="item" :key="item">{{item}}</option>
  </select>
</template>
<script>
import $ from 'jquery'
// import chosen from 'chosen-js'
import './chosen.jquery.min.js'
export default {
  name: 'kiss-select',
  props: {
    list: {
      type: Array,
      default () { return [] }
    },
    placeHolder: {
      type: String,
      default: '请输入选项'
    }
  },
  watch: {
    selValue: function (val, oldvalue) {
      console.log(val)
      this.$emit('valchange', val)
    }
  },
  data () {
    return {
      'selValue': ''
    }
  },
  mounted  () {
    /* chosen 下拉框样式 */
    $('.chosen-select').chosen({
      isable_search: false
    })
    $('.chosen-select').chosen().change(() => {
      this.selValue = $('.chosen-select').val()
    })
  }
}

</script>
<style>
  /* @import './chosen.css' */
/* @group Base */
.chosen-container {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  font-size: 14px;
  width: 253px;
  height:52px;
  *display: inline;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

/*为元素设定的宽度和高度决定了元素的边框盒*/
.chosen-container * {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  text-align:left;
}

.chosen-container .chosen-drop {
  position: absolute;
  left: -9999px;
  z-index: 1010;
  margin-left: 12px;
  width: 90%;
  /*下拉状态下的边框*/
  border: 1px solid  #00BFFF;
  border-radius: 5px;
  background: #232f2f;
  box-shadow: 0px 0px 4px #40d8ff;
  /*这里实现了一个侧面的动画效果*/
  /* transform:translate3d(0, 0px,0);
  transition:all 0.3s; */
  /*上下浮动的一个动画测试*/
  animation: heart 1.3s ease-in-out 2.7s infinite alternate;
}

.chosen-container.chosen-with-drop .chosen-drop {
  left: 0;
}
.chosen-container a {
  cursor: pointer;
}

/* @end */
/* @group Single Chosen */
.chosen-container-single .chosen-single {
  position: relative;
  display: block;
  overflow: hidden;
  padding: 0 0 0 8px;
  height: 62px;
  border: none;
  outline: none;
  /*暂定其为选择框的背景图片*/
  background: url('../image/KissSelect/selectbg.png');
  /* 这里是被选择的文字的颜色 */
  color: #00BFFF;
  text-decoration: none;
  white-space: nowrap;
  line-height: 29px;
}
.chosen-container-single .chosen-default {
  color: #999;
}
.chosen-container-single .chosen-single span {
  display: block;
  overflow: hidden;
  margin-top: 15px;
  margin-left: 10px;
  margin-right: 53px;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #42daff;
  font-size: 14px;
}

.chosen-container-single .chosen-single-with-deselect span {
  margin-right: 38px;
}

.chosen-container-single .chosen-single abbr {
  position: absolute;
  top: 6px;
  right: 26px;
  display: block;
  width: 12px;
  height: 12px;
  font-size: 1px;
}
/*这个是下拉框的上下图标的样式*/
.chosen-container-single .chosen-single div {
  position: absolute;
  top: 7px;
  right: 23px;
  /* border-left:1px solid #D1D1D1; */
  display: block;
  width: 30px;
  height: 100%;
}

.chosen-container-single .chosen-single div b {
  display: block;
  width: 100%;
  height: 100%;
}
.chosen-container-single .chosen-search {
  position: relative;
  z-index: 1010;
  margin: 0;
  padding: 3px 4px;
  white-space: nowrap;
}
/************************这个是搜索框************************/
.chosen-container-single .chosen-search input[type="text"] {
  margin: 1px 0;
  padding: 4px 20px 4px 5px;
  /*修改 100*/
  width: 100%;
  height: auto;
  outline: 0;
  border: none;
  font-size: 1em;
  font-family: sans-serif;
  background: url() no-repeat 100% -20px;
  line-height: normal;
  border-radius: 4px;
  color: #00BFFF;
}

.chosen-search-icon{
  display: block;
  float: left;
  margin-top: 5px;
  margin-left: 5px;
  width:20px;
  height: 20px;
  background-image: url('../image/KissSelect/search.png');
  padding: none;
}

.chosen-container-single .chosen-drop {
  margin-top: -1px;
  border-radius: 0 0 4px 4px;
  background-clip: padding-box;
}

.chosen-container-single.chosen-container-single-nosearch .chosen-search {
  position: absolute;
  left: -9999px;
}

/* @end */
/* @group Results */
.chosen-container .chosen-results {
  /* color: #787878; */
  position: relative;
  overflow-x: hidden;
  overflow-y: auto;
  margin: 0 4px 4px 0;
  padding: 0 0 0 4px;
  max-height: 240px;
  -webkit-overflow-scrolling: touch;
}
.chosen-container .chosen-results li {
  display: none;
  margin: 0;
  padding: 5px 6px;
  list-style: none;
  line-height: 20px;
  word-wrap: break-word;
  -webkit-touch-callout: none;
  color: #fffff3;
}
.chosen-container .chosen-results li.active-result {
  display: list-item;
  cursor: pointer;
}
.chosen-container .chosen-results li.disabled-result {
  display: list-item;
  color: #ccc;
  cursor: default;
}
/*当一个项目选中的时候高亮效果*/
.chosen-container .chosen-results li.highlighted {
  background-color: #0088AB;
  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(20%, #0088AB), color-stop(90%, #00718e));
  background-image: -webkit-linear-gradient(#0088AB 20%, #00718e 90%);
  background-image: -moz-linear-gradient(#0088AB 20%, #00718e 90%);
  background-image: -o-linear-gradient(#0088AB 20%, #00718e 90%);
  background-image: linear-gradient(#0088AB 20%, #00718e 90%);
  color: rgb(10, 230, 211);
}
.chosen-container .chosen-results li.no-results {
  color: #777;
  display: list-item;
  background: #f4f4f4;
}
.chosen-container .chosen-results li.group-result {
  display: list-item;
  font-weight: bold;
  cursor: default;
}
.chosen-container .chosen-results li.group-option {
  padding-left: 15px;
}
.chosen-container .chosen-results li em {
  font-style: normal;
  text-decoration: underline;
}

/* @end */
/* @group Multi Chosen */
.chosen-container-multi .chosen-choices {
  position: relative;
  overflow: hidden;
  margin: 0;
  padding: 0 5px;
  width: 100%;
  height: auto !important;
  height: 1%;
 /*  border: 1px solid #D1D1D1; */
  background-color: #fff;
  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(1%, #eeeeee), color-stop(15%, #ffffff));
  background-image: -webkit-linear-gradient(#eeeeee 1%, #ffffff 15%);
  background-image: -moz-linear-gradient(#eeeeee 1%, #ffffff 15%);
  background-image: -o-linear-gradient(#eeeeee 1%, #ffffff 15%);
  background-image: linear-gradient(#eeeeee 1%, #ffffff 15%);
  cursor: text;
}
.chosen-container-multi .chosen-choices li {
  float: left;
  list-style: none;
}
.chosen-container-multi .chosen-choices li.search-field {
  margin: 0;
  padding: 0;
  white-space: nowrap;
}
.chosen-container-multi .chosen-choices li.search-field input[type="text"] {
  margin: 1px 0;
  padding: 0;
  height: 25px;
  outline: 0;
  border: 0 !important;
  background: transparent !important;
  box-shadow: none;
 /*  color: #999 !important; */
  color: #055272 !important;
  font-size: 100%;
  font-family: sans-serif;
  line-height: normal;
  border-radius: 0;
}
.chosen-container-multi .chosen-choices li.search-choice {
  position: relative;
  margin: 3px 5px 3px 0;
  padding: 3px 20px 3px 5px;
  border: 1px solid #D1D1D1;
  max-width: 100%;
  border-radius: 3px;
  background-color: #eeeeee;
  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(20%, #f4f4f4), color-stop(50%, #f0f0f0), color-stop(52%, #e8e8e8), color-stop(100%, #eeeeee));
  background-image: -webkit-linear-gradient(#f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  background-image: -moz-linear-gradient(#f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  background-image: -o-linear-gradient(#f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  background-image: linear-gradient(#f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  background-size: 100% 19px;
  background-repeat: repeat-x;
  background-clip: padding-box;
  box-shadow: 0 0 2px white inset, 0 1px 0 rgba(0, 0, 0, 0.05);
 /*  color: #333; */
  line-height: 13px;
  cursor: default;
}
.chosen-container-multi .chosen-choices li.search-choice span {
  word-wrap: break-word;
}
.chosen-container-multi .chosen-choices li.search-choice .search-choice-close {
  position: absolute;
  top: 4px;
  right: 3px;
  display: block;
  width: 12px;
  height: 12px;
  font-size: 1px;
}
.chosen-container-multi .chosen-choices li.search-choice .search-choice-close:hover {
  background-position: -42px -10px;
}
.chosen-container-multi .chosen-choices li.search-choice-disabled {
  padding-right: 5px;
  border: 1px solid #ccc;
  background-color: #e4e4e4;
  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(20%, #f4f4f4), color-stop(50%, #f0f0f0), color-stop(52%, #e8e8e8), color-stop(100%, #eeeeee));
  background-image: -webkit-linear-gradient(top, #f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  background-image: -moz-linear-gradient(top, #f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  background-image: -o-linear-gradient(top, #f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  background-image: linear-gradient(top, #f4f4f4 20%, #f0f0f0 50%, #e8e8e8 52%, #eeeeee 100%);
  color: #666;
}
.chosen-container-multi .chosen-choices li.search-choice-focus {
  background: #d4d4d4;
}
.chosen-container-multi .chosen-choices li.search-choice-focus .search-choice-close {
  background-position: -42px -10px;
}
.chosen-container-multi .chosen-results {
  margin: 0;
  padding: 0;
}
.chosen-container-multi .chosen-drop .result-selected {
  display: list-item;
  /* color: #ccc; */
  color: #055272;
  cursor: default;
}
/* @end */
.chosen-container-active.chosen-with-drop .chosen-single {
  /*这里是当下拉框出现的时候的背景样式*/
  background: url('../image/KissSelect/selectbg.png');
  box-shadow: 0 0px 0 #fff inset;
}
/********************  @brief   这里是选中前后箭头的效果 **************************/
/* 选中前  */
.chosen-single div {
  width: 25px;
  height: 25px;
  background: url("../image/KissSelect/selectdown.png");
  /* background: transparent; */
}
.chosen-container-active.chosen-with-drop .chosen-single div {
  width: 30px;
  height: 30px;
  background: url("../image/KissSelect/selectup.png");
  /* background: transparent; */
}

.chosen-single div:hover {
  background: url("../image/KissSelect/selectdownhover.png");
}
.chosen-container-active.chosen-with-drop .chosen-single div:hover {
  background: url("../image/KissSelect/selectuphover.png");
}
/*******************************************************************************/
.chosen-container-active .chosen-choices {
  border: 1px solid #5897fb;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
}
.chosen-container-active .chosen-choices li.search-field input[type="text"] {
  color: #222 !important;
}
/* @end */
/* @group Disabled Support */
.chosen-disabled, .chosen-disabled *{
  opacity: 0.5 !important;
  filter: alpha(opacity=50) !important;
  cursor: default;
}

.chosen-disabled .chosen-single {
  cursor: default;
  outline: none;
}
.chosen-disabled .chosen-choices .search-choice .search-choice-close {
  cursor: default;
}

/* @end */
/* @group Right to Left */
.chosen-rtl {
  text-align: right;
}
.chosen-rtl .chosen-single {
  overflow: visible;
  padding: 0 8px 0 0;
}
.chosen-rtl .chosen-single span {
  margin-right: 0;
  margin-left: 26px;
  direction: rtl;
}
.chosen-rtl .chosen-single-with-deselect span {
  margin-top: 10px;
  margin-left: 38px;
}
.chosen-rtl .chosen-single div {
  right: auto;
  left: 3px;
}
.chosen-rtl .chosen-single abbr {
  right: auto;
  left: 26px;
}
.chosen-rtl .chosen-choices li {
  float: right;
}
.chosen-rtl .chosen-choices li.search-field input[type="text"] {
  direction: rtl;
}
.chosen-rtl .chosen-choices li.search-choice {
  margin: 3px 5px 3px 0;
  padding: 3px 5px 3px 19px;
}
.chosen-rtl .chosen-choices li.search-choice .search-choice-close {
  right: auto;
  left: 4px;
}
.chosen-rtl.chosen-container-single-nosearch .chosen-search,
.chosen-rtl .chosen-drop {
  left: 9999px;
}
.chosen-rtl.chosen-container-single .chosen-results {
  margin: 0 0 4px 4px;
  padding: 0 4px 0 0;
}
.chosen-rtl .chosen-results li.group-option {
  padding-right: 15px;
  padding-left: 0;
}
.chosen-rtl.chosen-container-active.chosen-with-drop .chosen-single div {
  border-right: none;
}
.chosen-rtl .chosen-search input[type="text"] {
  padding: 4px 5px 4px 20px;
  direction: rtl;
}
.chosen-rtl.chosen-container-single .chosen-single div b {
  background-position: 6px 2px;
}
.chosen-rtl.chosen-container-single.chosen-with-drop .chosen-single div b {
  background-position: -12px 2px;
}

/* @end */
/* @group Retina compatibility */
@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min-resolution: 144dpi) {
  .chosen-rtl .chosen-search input[type="text"],
  .chosen-container-single .chosen-single abbr,
  .chosen-container-single .chosen-single div b,
  .chosen-container-single .chosen-search input[type="text"],
  .chosen-container-multi .chosen-choices .search-choice .search-choice-close,
  .chosen-container .chosen-results-scroll-down span,
  .chosen-container .chosen-results-scroll-up span {
    /* background-image: url('chosen-sprite@2x.png') !important; */
    background-size: 52px 37px !important;
    background-repeat: no-repeat !important;
  }
}

/*动画测试*/
/* @-webkit-keyframes slide-down{
  0%{transform: scale(1,0);}
  25%{transform: scale(1,1.2);}
  50%{transform: scale(1,0.85);}
  75%{transform: scale(1,1.05);}
  100%{transform: scale(1,1);}
} */

@keyframes heart{
  from{transform:translate(0,0)}
  to{transform:translate(0,6px)}
}

/* @end */
/* 滚动条的样式 */
::-webkit-scrollbar{width:5px;}
::-webkit-scrollbar-button:vertical{display:none}
::-webkit-scrollbar-track:vertical{background-color:black;}
::-webkit-scrollbar-track-piece{background:#00738f}
::-webkit-scrollbar-thumb:vertical{background-color:#40d8ff ;border-radius:5px} /*拉条的样式*/
::-webkit-scrollbar-thumb:vertical:hover{background-color:#3B3B3B}  /*鼠标覆盖上去拉的时候的样式*/
::-webkit-scrollbar-corner:vertical{background-color:#535353}   /*即两个滚动条的交汇处*/
::-webkit-scrollbar-resizer:vertical{background-color:#FF6E00} /*两个滚动条的交汇处上用于通过拖动调整元素大小的小控件*/
</style>
