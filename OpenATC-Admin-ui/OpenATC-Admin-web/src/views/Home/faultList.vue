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
<div class="faultList">
    <div class="total" :style="{ width: '100%', marginBottom: totalMarginBottom }">
        <div class="Img"></div>
        <div class="text">
            故障总数
        </div>
        <div class="num">{{maxValue}}</div>
    </div>
    <div class="title" :style="{marginBottom: titleMarginBottom}">
        <div class="faultName" :style="{width: typeNameWidth}">故障类型</div>
        <div class="faultNum" :style="{width: faultNumWidth}">
           数量
        </div>
    </div>
    <div class="content" :style="{maxHeight: maxListHeight}">
        <div v-for="(item, index) in faultDevsData"
            :key=index
            style="height: 48px;">
          <div class="typeName" :style="{width: typeNameWidth}">{{item.typeName}}</div>
          <div class="list">
            <HorizontalChildren :name="item.typeName"
                backColor='#ebeef5'
                :isShowText="false"
                :dataList="[{value: item.number, maxNum: maxValue, color: '#e94f4f' }]"
                :mainwidth="chartWidth">
            </HorizontalChildren>
          </div>
        </div>
    </div>
</div>
</template>

<script>
import HorizontalChildren from '@/components/HorizontalChildren/index'
export default {
  name: 'faultList',
  props: {
    faultList: {
      type: Object
    }
  },
  watch: {
    faultList: {
      handler: function (val, oldVal) {
        this.initData()
      },
      // 深度观察监听
      deep: true
    }
  },
  data () {
    return {
      maxValue: 0,
      faultDevsData: [],
      totalMarginBottom: '24px',
      titleMarginBottom: '30px',
      maxListHeight: '330px',
      typeNameWidth: '142px',
      faultNumWidth: '295px',
      chartWidth: '400px'
    }
  },
  components: {
    HorizontalChildren
  },
  methods: {
    initData () {
      const data = []
      this.maxValue = this.faultList.maxValue
      this.faultList.data.forEach(function (value, key) {
        data.push({
          typeName: key,
          number: value
        })
      })
      this.faultDevsData = data
    }
  },
  created () {
    let viewH = document.documentElement.clientHeight - 40
    let viewW = document.documentElement.clientWidth - 40
    this.totalMarginBottom = (24 / 1080 * viewH).toFixed(0) + 'px'
    this.titleMarginBottom = (30 / 1080 * viewH).toFixed(0) + 'px'
    this.maxListHeight = (330 / 1080 * viewH).toFixed(0) + 'px'
    this.typeNameWidth = (142 / 1920 * viewW).toFixed(0) + 'px'
    this.faultNumWidth = (295 / 1920 * viewW).toFixed(0) + 'px'
    this.chartWidth = (400 / 1920 * viewW).toFixed(0) + 'px'
  },
  mounted () {
    this.initData()
  }
}
</script>

<style lang="scss" scoped>
.faultList {
    padding: 12px 6px;
    .total {
        // width: 554px;
        height: 54px;
        background: #f8f8f8;
        // margin-bottom: 24px;
        display: flex;
        align-items: center;
        position: relative;
        .Img {
          float: left;
          margin-left: 13px;
          margin-right: 6px;
          height: 30px;
          width: 30px;
          background-size: 30px 30px;
          background-repeat: no-repeat;
          background-position:center center;
          background-image: url("../../assets/home/trouble.png");
        }
        .text {
            float: left;
            font-family: MicrosoftYaHei;
            font-size: 14px;
            letter-spacing: 0px;
            color: #666666;
        }
        .num {
            // float: right;
            position: absolute;
            right: 36px;
            font-family: SourceHanSansCN-Regular;
            font-size: 32px;
            letter-spacing: 0px;
            color: #333333;
        }
    }
    .title {
      overflow: hidden;
      width: 100%;
      // margin-bottom: 30px;
      > div {
        float: left;
        text-align: center;
        font-family: MicrosoftYaHei;
        font-size: 14px;
        letter-spacing: 0px;
        color: #999999;
      }
      .faultNum {
        float: left;
        // width: 295px;
        text-align: center;
      }
    }
    .content {
      overflow-y: auto;
      .typeName {
        float: left;
        // width: 116px;
        text-align: center;
        font-family: MicrosoftYaHei;
        font-size: 14px;
        letter-spacing: 0px;
        color: #666666;
      }
      .list {
        float: left;
      }
    }
}
</style>
