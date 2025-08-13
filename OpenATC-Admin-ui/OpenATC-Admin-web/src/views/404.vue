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
  <div style="margin-top: -20px;">
    <div class="wscn-http404">
      <div class="pic-404">
        <img class="pic-404__parent" :src="img404" alt="404">
      </div>
      <div class="bullshit">
        <div class="bullshit__headline">抱歉，页面暂时无法访问!</div>
        <div class="bullshit__info">请检查您输入的网址是否正确，请点击以下按钮返回主页或者刷新重试</div>
        <div class="bullshit__link" >
          <a href="" class="bullshit__return-home">返回首页</a>
          <a class="bullshit__refresh" v-on:click.stop="handleRefresh">刷新</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import img404 from '@/assets/404_images/404.png'

export default {
  data () {
    return {
      img404,
      fromPath: '/'
    }
  },
  methods: {
    handleRefresh () {
      this.$router.push(this.fromPath)
    }
  },
  mounted () {
    this.fromPath = this.$route.redirectedFrom
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.wscn-http404 {
  position: relative;
  width: 1200px;
  margin: 20px auto 60px;
  padding: 0 100px;
  overflow: hidden;
  .pic-404 {
    position: relative;
    // float: left;
    width: 524px;
    padding: 80px 0 34px 0;
    overflow: hidden;
    margin: 0 auto;
    &__parent {
      width: 100%;
    }
    &__child {
      position: absolute;
      &.left {
        width: 80px;
        top: 17px;
        left: 220px;
        opacity: 0;
        animation-name: cloudLeft;
        animation-duration: 2s;
        animation-timing-function: linear;
        animation-fill-mode: forwards;
        animation-delay: 1s;
      }
      &.mid {
        width: 46px;
        top: 10px;
        left: 420px;
        opacity: 0;
        animation-name: cloudMid;
        animation-duration: 2s;
        animation-timing-function: linear;
        animation-fill-mode: forwards;
        animation-delay: 1.2s;
      }
      &.right {
        width: 62px;
        top: 100px;
        left: 500px;
        opacity: 0;
        animation-name: cloudRight;
        animation-duration: 2s;
        animation-timing-function: linear;
        animation-fill-mode: forwards;
        animation-delay: 1s;
      }
      @keyframes cloudLeft {
        0% {
          top: 17px;
          left: 220px;
          opacity: 0;
        }
        20% {
          top: 33px;
          left: 188px;
          opacity: 1;
        }
        80% {
          top: 81px;
          left: 92px;
          opacity: 1;
        }
        100% {
          top: 97px;
          left: 60px;
          opacity: 0;
        }
      }
      @keyframes cloudMid {
        0% {
          top: 10px;
          left: 420px;
          opacity: 0;
        }
        20% {
          top: 40px;
          left: 360px;
          opacity: 1;
        }
        70% {
          top: 130px;
          left: 180px;
          opacity: 1;
        }
        100% {
          top: 160px;
          left: 120px;
          opacity: 0;
        }
      }
      @keyframes cloudRight {
        0% {
          top: 100px;
          left: 500px;
          opacity: 0;
        }
        20% {
          top: 120px;
          left: 460px;
          opacity: 1;
        }
        80% {
          top: 180px;
          left: 340px;
          opacity: 1;
        }
        100% {
          top: 200px;
          left: 300px;
          opacity: 0;
        }
      }
    }
  }
  .bullshit {
    position: relative;
    // float: left;
    width: 500px;
    // padding: 150px 0;
    overflow: hidden;
    margin: 0 auto;
    text-align: center;
    &__headline {
      font-size: 24px;
      line-height: 24px;
      font-weight: 400;
      color: #409EFF;
      opacity: 0;
      margin-bottom: 24px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.1s;
      animation-fill-mode: forwards;
    }
    &__info {
      font-size: 14px;
      font-weight: 400;
      line-height: 21px;
      color: #909399;
      opacity: 0;
      margin-bottom: 34px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.2s;
      animation-fill-mode: forwards;
    }
    &__link {
      width: 220px;
      margin-left: 192px;
      overflow: hidden;
    }
    &__return-home {
      display: block;
      float: left;
      width: 140px;
      height: 50px;
      background: #1482f0;
      border-radius: 100px;
      text-align: center;
      color: #ffffff;
      opacity: 0;
      font-size: 16px;
      font-weight: 400;
      line-height: 50px;
      cursor: pointer;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.3s;
      animation-fill-mode: forwards;
    }
    &__refresh {
      display: block;
      float: left;
      // width: 110px;
      height: 50px;
      text-align: center;
      color: #909399;
      font-size: 16px;
      line-height: 50px;
      margin-left: 21px;
      cursor: pointer;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.3s;
      animation-fill-mode: forwards;
    }
    @keyframes slideUp {
      0% {
        transform: translateY(60px);
        opacity: 0;
      }
      100% {
        transform: translateY(0);
        opacity: 1;
      }
    }
  }
}

</style>
