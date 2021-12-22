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
<div class="tagsList">
  <el-button
    class="toListBtn"
    type="primary"
    @click="backToDeviceList"
    >{{$t('openatc.devicemanager.devicelist')}}</el-button>
  <el-tag size="mini"
    closable
    v-for="(tag, index) in tags"
    :key="index"
    :class="tag.id === operateId ? 'highlightTag' : 'normalTag'"
    @click.native="handleClickTag(tag)"
    @close="handleCloseTag(tag)">{{tag.agentid}}</el-tag>
</div>
</template>
<script>
import { mapState } from 'vuex'
import router from '@/router'
export default {
  name: 'deviceTags',
  data () {
    return {
    }
  },
  computed: {
    ...mapState({
      tags: state => state.globalVariable.deviceTags,
      operateId: state => state.globalVariable.operateId,
      devicePath: state => state.globalVariable.devicePath
    })
  },
  created () {
    const currRouter = this.$route.path
    // 如果是打开的单一设备详情，此处阻止根据数据长度判断tags为0后，重定向回设备列表（此处理逻辑是多设备管理的逻辑）
    const stopJumpFlag = sessionStorage.getItem('toSingleEdge')
    if (stopJumpFlag === '1') return
    if (this.tags.length === 0 && currRouter !== '/device' && currRouter !== '/deviceNew') {
      router.push({ path: this.devicePath })
    }
  },
  methods: {
    backToDeviceList () {
      this.handleSaveTscParam()
      router.push({
        path: this.devicePath
      })
      this.$store.dispatch('SetOperatedDeviceId', undefined)
    },
    handleClickTag (tag) {
      const clickedId = tag.id
      this.saveCurDevInfo(clickedId)
      // 点击的页签是当前打开的页签，不做任何操作
      if (clickedId === this.operateId) return
      //   跳转前存储前一个页签下的tscParam信息
      this.handleSaveTscParam()
      //   跳转后获取后一个页签对应的tscParam信息
      this.handleSetTscParam(tag)
    },
    handleCloseTag (tag) {
      let closedIndex
      const closedId = tag.id
      if (this.operateId === closedId) {
        // 关闭当前高亮的页签（即当前页）
        this.tags.forEach((ele, index) => {
          if (ele.id === closedId) {
            // 记录被关闭的页签对应的index
            closedIndex = index
          }
        })
      }
      if (closedIndex !== undefined) {
        // 关闭当前高亮页签后，安排后一个默认高亮显示的页签
        if (this.tags.length === 1) {
          // 只有一个页签，删除后，返回设备列表，清空存储
          this.$store.dispatch('ResetTags')
          this.$store.dispatch('SaveCurPath', '/overview/index')
          this.backToDeviceList()
        } else if (closedIndex < this.tags.length - 1) {
          // 当前页不是最后一个页签，关闭后，则显示后一页的内容
          let nextTag = this.tags[closedIndex + 1]
          this.handleSetTscParam(nextTag)
          this.saveCurDevInfo(nextTag.id)
        } else {
          // 当前页是最后一个页签，关闭后，则显示前一页的内容
          let lastTag = this.tags[closedIndex - 1]
          this.handleSetTscParam(lastTag)
          this.saveCurDevInfo(lastTag.id)
        }
      }
      this.$store.dispatch('DeleteDevTag', closedId)
      this.$store.dispatch('DeleteDeviceData', closedId)
    },
    handleSaveTscParam () {
      // 存储baseEdge的参数tscParam进openATC维护的映射结构
      if (!this.operateId) return
      const tscParam = this.$store.getters.tscParam
      const curPath = this.$store.getters.curPath
      this.$store.dispatch('SaveMultDeviceData', [this.operateId, tscParam, curPath])
    },
    handleSetTscParam (tag) {
      // 设置baseEdge的全局参数tscParam
      this.$store.dispatch('SetOperatedDeviceId', tag.id)
      this.$store.dispatch('GetMultDeviceData', tag.id)
      const curTscParam = this.$store.getters.openedDevice
      const curPath = this.$store.getters.openedPath
      router.push({
        path: curPath,
        query: {IP: tag.jsonparam.ip, port: tag.jsonparam.port, agentid: tag.agentid, protocol: tag.protocol, isfromatc: true}
      })
      this.$store.dispatch('ResetTscParam')
      setTimeout(() => {
        this.$store.dispatch('SaveTscParam', curTscParam)
        this.$store.dispatch('SaveCurPath', curPath)
      }, 50)
    },
    saveCurDevInfo (operateId) {
      let allDevsInfo = this.$store.getters.deviceInfos
      // 当前操作设备变化时
      const curDevInfo = allDevsInfo.filter(dev => dev.id === operateId)[0]
      let query = {IP: curDevInfo.jsonparam.ip, port: curDevInfo.jsonparam.port, agentid: curDevInfo.agentid, protocol: curDevInfo.protocol, isfromatc: true}
      window.sessionStorage.setItem('curTagDevInfo', JSON.stringify(query))
    }
  }
}
</script>

<style lang="scss">
 .tagsList .highlightTag .el-icon-close{
    color: $--color-white;
  }
  .tagsList .highlightTag .el-icon-close:hover {
    background-color: $--border-color-extra-light;
    color: $--color-primary;
  }
 .tagsList .normalTag .el-icon-close{
    color: $--color-primary;
  }
  .tagsList .normalTag .el-icon-close:hover {
    background-color: $--color-primary;
    color: $--color-white;
  }
</style>
<style lang="scss" scoped>
.tagsList {
  height: 84px;
  padding: 24px 29px;
  box-sizing: border-box;
  .el-tag {
    cursor: pointer;
    margin-left: 2px;
  }
  .el-tag--mini {
    min-width: 50px;
    height: 26px;
    padding: 0 5px;
    line-height: 26px;
    text-align: center;
  }
  .toListBtn {
    width: 70px;
    height: 26px;
    line-height: 26px;
    padding: 0;
    font-family: MicrosoftYaHei;
    font-size: 12px;
    font-weight: normal;
    font-stretch: normal;
    letter-spacing: 0px;
    margin-bottom: 4px;
  }
  .highlightTag {
    background-color: $--color-primary;
    border-color: $--color-primary;
    color: $--color-white;
  }
  // .normalTag {
  //   background-color: $--border-color-extra-light;
  //   // border-color: #d9ecff;
  //   // border-color: $--color-primary;
  //   color: $--color-primary;
  // }
}
</style>
