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
 <!--动态路口图，只需要设备AgentId，此组件会处理方案状态接口轮询-->
<template>
  <div class="intersection-with-interface">
      <IntersectionMap
        ref="intersectionMap"
        :crossStatusData="crossStatusData"
        :devStatus="devStatus"
        :agentId="agentId"
        :graphicMode="true"/>
  </div>
</template>
<script>
import IntersectionMap from '@/components/IntersectionMap'
import { getMessageByCode } from '../../utils/responseMessage'
import { getTscControl, queryDevice } from '@/api/control'
import { registerMessage } from '@/api/param'
import { getIframdevid, setIframdevid, setToken } from '@/utils/auth'
export default {
  name: 'intersection-with-interface',
  components: {
    IntersectionMap
  },
  data () {
    return {
      boxVisible: false,
      dialogWidth: '100%',
      crossStatusData: {}, // 路口状态数据
      devStatus: 1,
      isResend: true,
      intervalFlag: true,
      phaseControlTimer: null, // 定时器
      registerMessageTimer: null // 延时器
    }
  },
  props: {
    AgentId: {
      type: String,
      default: '0'
    },
    Token: {
      type: String,
      default: ''
    }
  },
  watch: {
    AgentId: {
      handler: function (val, oldVal) {
        this.agentId = val
      },
      // 深度观察监听
      deep: true
    },
    Token: {
      handler: function (val) {
        this.setPropsToken(val)
      }
    }
  },
  methods: {
    oncancle () {
      this.boxVisible = false
    },
    handleOpenConfigPanel () {
      this.boxVisible = true
    },
    setDialogWidth () {
      var val = document.body.offsetWidth
      const def = 1200 // 默认宽度
      if (val < def) {
        this.dialogWidth = '100%'
      } else {
        this.dialogWidth = def + 'px'
      }
    },
    resetIntersectionMap () {
      this.setPropsToken(this.Token)
      this.firstInit()
      this.$refs.intersectionMap.resetCrossDiagram()
      this.initData()
    },
    registerMessage () {
      registerMessage(this.agentId).then(data => {
        if (!data.data.success) {
          this.devStatus = 2
          let parrenterror = getMessageByCode(data.data.code, this.$i18n.locale)
          if (data.data.data) {
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              let childerror = getMessageByCode(data.data.data.errorCode, this.$i18n.locale)
              this.$message.error(parrenterror + ',' + childerror)
            }
          } else {
            this.$message.error(parrenterror)
          }
          if (this.isResend) {
            this.reSend()
          }
          return
        }
        this.devStatus = 3
        this.clearPatternInterval() // 清除其他定时器
        this.phaseControlTimer = setInterval(() => {
          if (this.intervalFlag) {
            this.initData()
          }
        }, 1000)
      })
    },
    reSend () { // 设备掉线重连机制
      this.devStatus = 1
      this.clearRegisterMessageTimer()
      this.registerMessageTimer = setTimeout(() => {
        this.reconnectionDev()
      }, 5000)
    },
    clearPatternInterval () {
      if (this.phaseControlTimer !== null) {
        clearInterval(this.phaseControlTimer) // 清除定时器
        this.phaseControlTimer = null
      }
    },
    clearRegisterMessageTimer () {
      if (this.registerMessageTimer !== null) {
        clearTimeout(this.registerMessageTimer) // 清除延时器
        this.registerMessageTimer = null
      }
    },
    initData () {
      this.intervalFlag = false
      let iframdevid = getIframdevid()
      console.log(iframdevid)
      let startTime = new Date().getTime()
      getTscControl(this.agentId).then((data) => {
        let endTime = new Date().getTime()
        let diffTime = endTime - startTime
        this.responseTime = diffTime
        this.intervalFlag = true
        if (!data.data.success) {
          if (data.data.code === '4003') {
            this.devStatus = 2
            this.clearPatternInterval() // 清除其他定时器
            this.$message.error(getMessageByCode(data.data.code, this.$i18n.locale))
            if (this.isResend) {
              this.reSend()
            }
            return
          }
          let parrenterror = getMessageByCode(data.data.code, this.$i18n.locale)
          if (data.data.data) {
            // 子类型错误
            let childErrorCode = data.data.data.errorCode
            if (childErrorCode) {
              let childerror = getMessageByCode(data.data.data.errorCode, this.$i18n.locale)
              this.$message.error(parrenterror + ',' + childerror)
            }
          } else {
            this.$message.error(parrenterror)
          }
          this.clearPatternInterval() // 清除其他定时器
          if (this.isResend) {
            this.reSend()
          }
          return
        }
        this.crossStatusData = JSON.parse(JSON.stringify(data.data.data.data))
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    reconnectionDev () {
      this.registerMessage()
    },
    queryDevParams () {
      let _this = this
      queryDevice().then(res => {
        if (!res.data.success) {
          _this.$message.error(getMessageByCode(res.data.code, _this.$i18n.locale))
          return
        }
        let devParams = res.data.data.jsonparam
        _this.ip = devParams.ip
        _this.port = String(devParams.port)
        _this.protocol = res.data.data.protocol
        _this.agentId = res.data.data.agentid
        if (res.data.data.name) {
          _this.agentName = res.data.data.name
        }
        _this.platform = res.data.data.platform
        _this.$refs.intersectionMap.resetCrossDiagram()
        _this.registerMessage() // 注册消息
      })
    },
    firstInit () {
      if (this.$route.query !== undefined && Object.keys(this.$route.query).length) {
        this.agentId = this.$route.query.agentid
        setIframdevid(this.agentId)
        this.registerMessage() // 注册消息
      } else {
        this.queryDevParams() // 查询设备信息
      }
    },
    destroyIntersectionMap () {
      this.isResend = false
      this.clearPatternInterval() // 清除定时器
      this.clearRegisterMessageTimer() // 清除定时器
    },
    setPropsToken (token) {
      // 获取组件外传入的token，便于独立组件调用接口
      if (token && token !== '') {
        setToken(token)
      }
    }
  },
  created () {
    this.agentId = this.AgentId
    this.setDialogWidth()
  },
  mounted () {
    this.resetIntersectionMap()
    window.onresize = () => {
      return (() => {
        this.setDialogWidth()
      })()
    }
  },
  destroyed () {
    this.destroyIntersectionMap()
  }
}
</script>
<style lang="scss">
.abow_dialog {
    display: flex;
    justify-content: center;
    align-items: Center;
    overflow: hidden;
    .el-dialog {
        margin: 0 auto !important;
        height: 90%;
        overflow: hidden;
        .el-dialog__body {
            position: absolute;
            left: 0;
            top: 54px;
            bottom: 0;
            right: 0;
            padding: 0;
            z-index: 1;
            overflow-y: auto;
            overflow-x: auto;
        }
    }
}
</style>
