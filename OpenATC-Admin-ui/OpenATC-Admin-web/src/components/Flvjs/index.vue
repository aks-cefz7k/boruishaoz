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
  <div class="flvlist">
    <FlvPlayer  v-if="!resetVedioDom"
                :Width="Width"
                :Height="Height"
                :Title="Title"
                :curVideoInfo="curVideoInfo"
                :autoPlay="autoPlay"
                @resetComponent="reset">
      <!-- 多视频切换控制 -->
      <el-dropdown slot="vediolist" trigger="click" @command="switchVideo">
          <span class="el-dropdown-link"><i class="iconfont icon-shipincaidan"></i></span>
          <el-dropdown-menu slot="dropdown" class="flvlist-dropdown">
            <el-dropdown-item v-for="video in curDevVideos"
              :command="video.id"
              :key="video.id"
              :class="video.id === curVideoInfo.id ? 'active' : ''">
              <i :class="video.id === curVideoInfo.id ? 'iconfont icon-fangzhenzanting' : 'iconfont icon-fangzhenkaishi'"></i>
              <span>{{video.name}}</span>
              </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
    </FlvPlayer>
  </div>
</template>

<script>
import FlvPlayer from './singlevideo'
export default {
  name: 'flvlist',
  components: {
    FlvPlayer
  },
  props: {
    curDevVideos: { // 当前设备的所有视频信息
      type: Array,
      default: () => []
    },
    Width: {
      type: String,
      default: '100%'
    },
    Height: {
      type: String,
      default: '237'
    },
    Title: {
      type: String
    },
    autoPlay: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    curDevVideos: {
      handler: function (val) {
        if (!val.length) return
        this.switchVideo(val[0].id)
      },
      deep: true
    }
  },
  data () {
    return {
      resetVedioDom: false,
      curVideoInfo: {}
    }
  },
  mounted () {
    this.curVideoInfo = this.curDevVideos[0]
  },
  methods: {
    reset (nextmediaid) {
      this.resetVedioDom = true
      let _this = this
      this.$nextTick(() => {
        _this.resetVedioDom = false
        this.curVideoInfo = this.curDevVideos.filter(ele => ele.id === nextmediaid)[0]
      })
    },
    switchVideo (nextid) {
      if (nextid === this.curVideoInfo.id) return
      this.reset(nextid)
    }
  }
}
</script>
