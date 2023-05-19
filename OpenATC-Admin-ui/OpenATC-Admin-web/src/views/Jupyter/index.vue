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

export default {
  data () {
    return {
      isShow: false,
      url: 'http://192.168.14.168:8888/tree'
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
    }
  },
  mounted () {
    this.toIframe()
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
