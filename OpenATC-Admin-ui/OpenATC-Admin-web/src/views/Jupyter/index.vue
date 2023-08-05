<template>
  <div class="jupyter-content">
    <!-- iframe -->
    <kissiframeontainer
      v-if="isShow"
      @close="handleIframClose"
      :Url="url"
      class="jupyter-iframe"
      width="100%"
      height="100%"
    ></kissiframeontainer>
  </div>
</template>
<script>
import kissiframeontainer from './KissIframeContainer/IframeContainer.vue'
import { SystemconfigApi } from '../../api/systemconfig.js'

export default {
  data () {
    return {
      isShow: false,
      // url: 'http://192.168.14.168:8888/tree'
      url: ''
    }
  },
  components: {
    kissiframeontainer
  },
  methods: {
    toIframe () {
      this.isShow = true
    },
    handleIframClose (value) {
      this.isShow = value
    },
    getJupyterUrl () {
      // 从系统配置中获取jupyter notebook 跳转url
      return new Promise((resolve, reject) => {
        SystemconfigApi.GetSystemconfigByModule('jupyter').then((data) => {
          let res = data.data
          if (!res.success) {
            console.log('datas:' + res)
            throw new Error('get JupyterUrl error')
          } else {
            for (let config of data.data.data) {
              if (config['key'] === 'url') {
                this.url = config['value']
              }
            }
            resolve(data.data.data)
          }
        })
      })
    }
  },
  mounted () {
    this.getJupyterUrl().then(res => {
      if (res.length) { this.toIframe() }
    })
  }
}
</script>
<style scoped>
.jupyter-content {
height: 100%;
}
.jupyter-iframe {
  height: 100%;
}
</style>
