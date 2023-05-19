<template>
<div>
  <iframe :src="Url"
        id="kissframe"
        frameborder="0"
        scrolling="yes"
        :height="iframeH"
        :width="Width"
  ></iframe>
</div>
</template>

<script>
export default {
  name: 'kiss-iframe-container',
  data () {
    return {
      iframeH: ''
    }
  },
  props: {
    Width: {
      type: String,
      default: '1800px'
    },
    Height: {
      type: String,
      default: '800px'
    },
    Url: {
      type: String,
      default: ''
    }
  },
  methods: {
  },
  created () {
    this.iframeH = this.Height
  }
}
</script>
