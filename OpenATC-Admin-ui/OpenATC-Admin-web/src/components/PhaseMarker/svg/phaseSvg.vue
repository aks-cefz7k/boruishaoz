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
    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 96.09 46.54"><defs>
          <filter id="luminosity-invert" filterUnits="userSpaceOnUse" color-interpolation-filters="sRGB">
              <feColorMatrix values="-1 0 0 0 1 0 -1 0 0 1 0 0 -1 0 1 0 0 0 1 0"/></filter>
              <mask id="mask" x="1.15" y="1.03" width="44" height="44" maskUnits="userSpaceOnUse">
                  <g class="cls-7"><image width="44" height="44" transform="translate(1.15 1.03)" xlink:href="data:image/png;base64,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"/></g></mask></defs>
                  <title>方案状态背景短</title>
                  <g id="图层_2" data-name="图层 2"><g id="图层_1-2" data-name="图层 1">
                      <path :style="{'fill':backgroundColor}" id="更换颜色1" class="cls-1" d="M23.27,4A19.28,19.28,0,1,0,42.55,23.27,19.28,19.28,0,0,0,23.27,4Z"/>
                      <path :style="{'fill':backgroundColor}" id="更换颜色" class="cls-2" d="M89.14,7.67H40.06a23.23,23.23,0,0,1,.46,30.68H89.15l6.44-4.86V13.09Z"/>
                      <path class="cls-3" d="M14.45,43.72A22.27,22.27,0,0,1,11,4.67.5.5,0,0,0,11.16,4a.49.49,0,0,0-.69-.15,23.28,23.28,0,0,0,3.58,40.81.45.45,0,0,0,.2,0,.5.5,0,0,0,.46-.3A.51.51,0,0,0,14.45,43.72Z"/>
                      <path class="cls-3" d="M15,2.59A22.3,22.3,0,0,1,35.11,4.41a.52.52,0,0,0,.27.07.5.5,0,0,0,.26-.92,23.31,23.31,0,0,0-21-1.9.5.5,0,0,0-.28.65A.5.5,0,0,0,15,2.59Z"/>
                      <path class="cls-3" d="M3.49,23.27A19.78,19.78,0,1,0,23.27,3.49,19.8,19.8,0,0,0,3.49,23.27Zm38.56,0A18.78,18.78,0,1,1,23.27,4.49,18.8,18.8,0,0,1,42.05,23.27Z"/>
                      <path class="cls-3" d="M89.32,7.17H40a.5.5,0,0,0-.59.06.5.5,0,0,0,0,.71,22.27,22.27,0,0,1-16.15,37.6,22.44,22.44,0,0,1-4.41-.44.51.51,0,0,0-.59.39.5.5,0,0,0,.39.59,23,23,0,0,0,4.61.46,23.2,23.2,0,0,0,17.26-7.69H89.32l6.77-5.11V12.85Zm5.77,26.07L89,37.85H41.57a23.73,23.73,0,0,0-.41-29.68H89l6.13,5.15Z"/>
                      <path class="cls-3" d="M9.5,34.08c.22.28.45.55.68.81l.37-.33c-.22-.26-.44-.52-.65-.79Z"/>
                      <path class="cls-3" d="M7.32,30.48c.14.32.3.64.46.95l.44-.24c-.16-.3-.31-.6-.45-.92Z"/>
                      <path class="cls-3" d="M6.55,26.37l-.5.09c.07.35.14.69.23,1l.48-.12C6.68,27,6.61,26.7,6.55,26.37Z"/>
                      <path class="cls-3" d="M10.91,35.66c.25.25.5.49.77.72L12,36c-.26-.23-.51-.46-.75-.7Z"/>
                      <path class="cls-3" d="M7,28.36l-.48.15c.11.34.22.67.35,1l.47-.18C7.25,29,7.14,28.69,7,28.36Z"/>
                      <path class="cls-3" d="M8.3,32.35c.18.3.37.59.58.88L9.29,33c-.2-.28-.39-.57-.56-.86Z"/>
                      <path class="cls-3" d="M21.11,5.9l.06.49,1-.09,0-.5C21.81,5.82,21.46,5.85,21.11,5.9Z"/>
                      <path class="cls-3" d="M12.49,37.06c.28.22.56.43.85.63l.28-.42c-.28-.19-.55-.39-.82-.6Z"/>
                      <path class="cls-3" d="M6.3,24.33l-.5,0c0,.35,0,.7.1,1.05l.49-.06Q6.33,24.84,6.3,24.33Z"/>
                      <path class="cls-3" d="M20.13,40.49c.35.07.7.12,1.05.16l.06-.5q-.51-.06-1-.15Z"/>
                      <path class="cls-3" d="M14.23,38.26c.3.18.61.36.92.52l.23-.44c-.3-.16-.6-.33-.89-.51Z"/>
                      <path class="cls-3" d="M16.1,39.24c.32.15.65.28,1,.41l.18-.47c-.33-.12-.64-.25-1-.39Z"/>
                      <path class="cls-3" d="M18.08,40c.34.11.68.2,1,.28l.12-.48c-.34-.08-.67-.18-1-.28Z"/>
                      <path class="cls-3" d="M16.25,7.78,16,7.32c-.32.15-.63.3-.95.47l.24.44C15.63,8.07,15.94,7.92,16.25,7.78Z"/>
                      <path class="cls-3" d="M14.44,8.74l-.26-.43c-.3.18-.6.38-.89.58l.29.41A9.74,9.74,0,0,1,14.44,8.74Z"/>
                      <path class="cls-3" d="M12.75,9.91l-.31-.4-.81.68.34.38Q12.35,10.23,12.75,9.91Z"/>
                      <path class="cls-3" d="M11.22,11.27l-.35-.35c-.25.25-.49.51-.73.77l.38.33C10.75,11.76,11,11.51,11.22,11.27Z"/>
                      <path class="cls-3" d="M18.16,7.05,18,6.57c-.33.1-.67.22-1,.35l.18.46C17.51,7.26,17.84,7.15,18.16,7.05Z"/>
                      <path class="cls-3" d="M20.16,6.55l-.09-.49c-.35.06-.7.14-1,.22l.12.49C19.48,6.69,19.82,6.61,20.16,6.55Z"/>
                      <path class="cls-3" d="M6.29,22.28l-.5,0c0,.34,0,.68,0,1v0l.5,0C6.27,22.94,6.28,22.61,6.29,22.28Z"/>
                      <path class="cls-3" d="M9.86,12.81l-.39-.31c-.22.28-.43.56-.63.85l.41.29C9.45,13.36,9.65,13.08,9.86,12.81Z"/>
                      <path class="cls-3" d="M6.54,20.24,6,20.15c-.06.35-.11.69-.15,1.05l.49.05C6.42,20.91,6.47,20.58,6.54,20.24Z"/>
                      <path class="cls-3" d="M7,18.25l-.48-.15c-.1.33-.2.67-.28,1l.49.12Q6.87,18.73,7,18.25Z"/>
                      <path class="cls-3" d="M7.75,16.32l-.46-.2c-.15.32-.28.65-.41,1l.47.17C7.47,17,7.61,16.63,7.75,16.32Z"/>
                      <path class="cls-3" d="M8.7,14.5l-.43-.26c-.18.31-.36.61-.52.93l.44.23C8.35,15.09,8.52,14.8,8.7,14.5Z"/>
                      <path class="cls-3" d="M40,20.14l.5-.09c-.07-.35-.14-.7-.23-1l-.48.13C39.85,19.47,39.92,19.8,40,20.14Z"/>
                      <path class="cls-3" d="M37,12.43a10.41,10.41,0,0,0-.68-.81L36,12c.23.26.45.52.66.79Z"/>
                      <path class="cls-3" d="M40.24,22.17l.5,0c0-.35-.06-.7-.1-1.05l-.5.06C40.18,21.49,40.22,21.83,40.24,22.17Z"/>
                      <path class="cls-3" d="M34,9.46c-.28-.22-.56-.43-.85-.63l-.28.41c.28.2.56.4.83.61Z"/>
                      <path class="cls-3" d="M35.61,10.85q-.37-.37-.78-.72l-.33.38c.26.22.51.46.76.7Z"/>
                      <path class="cls-3" d="M39.21,16c-.15-.32-.3-.64-.47-1l-.44.23c.16.3.31.61.45.92Z"/>
                      <path class="cls-3" d="M39.49,18.15,40,18c-.1-.34-.22-.67-.34-1l-.47.18C39.27,17.5,39.38,17.82,39.49,18.15Z"/>
                      <path class="cls-3" d="M22.26,40.24l0,.5,1,0h.05l0-.5C22.93,40.27,22.59,40.26,22.26,40.24Z"/>
                      <path class="cls-3" d="M32.28,8.26c-.3-.18-.61-.35-.92-.52l-.23.45c.3.15.6.32.89.5Z"/>
                      <path class="cls-3" d="M26.37,6c-.34-.06-.69-.11-1-.15l-.06.49c.34,0,.68.09,1,.15Z"/>
                      <path class="cls-3" d="M23.22,5.77l.05.5c.33,0,.65,0,1,0l0-.5c-.33,0-.66,0-1,0Z"/>
                      <path class="cls-3" d="M25.4,40.65l-.07-.5q-.49.06-1,.09l0,.5C24.7,40.72,25.05,40.69,25.4,40.65Z"/>
                      <path class="cls-3" d="M28.42,6.54c-.33-.11-.67-.2-1-.28l-.11.48,1,.27Z"/>
                      <path class="cls-3" d="M30.41,7.28c-.33-.14-.65-.28-1-.4l-.18.47c.32.12.64.25.95.39Z"/>
                      <path class="cls-3" d="M38.22,14.16c-.18-.3-.38-.59-.58-.88l-.41.28c.2.28.38.57.56.86Z"/>
                      <path class="cls-3" d="M32.07,37.82l.26.43c.3-.19.6-.38.89-.58l-.29-.41C32.65,37.46,32.37,37.64,32.07,37.82Z"/>
                      <path class="cls-3" d="M30.26,38.77l.2.46c.33-.14.64-.3.95-.46l-.23-.45C30.88,38.48,30.57,38.63,30.26,38.77Z"/>
                      <path class="cls-3" d="M33.76,36.65l.31.4c.27-.22.55-.45.81-.68L34.55,36C34.29,36.22,34,36.44,33.76,36.65Z"/>
                      <path class="cls-3" d="M35.29,35.29l.36.36c.25-.25.49-.51.72-.77L36,34.54C35.77,34.8,35.54,35.05,35.29,35.29Z"/>
                      <path class="cls-3" d="M40.27,23.27c0,.34,0,.69,0,1l.5,0c0-.34,0-.7,0-1.05V23.2h-.5Z"/>
                      <path class="cls-3" d="M28.34,39.5l.15.48c.34-.1.67-.22,1-.34l-.18-.47C29,39.29,28.67,39.4,28.34,39.5Z"/>
                      <path class="cls-3" d="M26.35,40l.09.5c.35-.07.69-.14,1-.23l-.12-.48C27,39.86,26.69,39.93,26.35,40Z"/>
                      <path class="cls-3" d="M40,26.33l.49.09c.06-.34.11-.69.16-1l-.5-.06C40.11,25.66,40.06,26,40,26.33Z"/>
                      <path class="cls-3" d="M39.51,28.32l.47.15c.11-.33.2-.67.29-1l-.49-.12C39.7,27.67,39.61,28,39.51,28.32Z"/>
                      <path class="cls-3" d="M37.82,32.06l.43.26c.18-.3.36-.6.52-.92l-.44-.23C38.17,31.47,38,31.77,37.82,32.06Z"/>
                      <path class="cls-3" d="M36.66,33.75l.39.31c.22-.27.42-.56.63-.85l-.41-.28C37.07,33.21,36.87,33.49,36.66,33.75Z"/>
                      <path class="cls-3" d="M38.78,30.25l.46.2c.14-.32.27-.64.4-1l-.47-.18C39.05,29.62,38.92,29.94,38.78,30.25Z"/>
                      <g class="cls-4"><g class="cls-5"><path class="cls-6" d="M23.27,4A19.28,19.28,0,1,0,42.55,23.27,19.28,19.28,0,0,0,23.27,4Z"/></g></g></g></g>
    </svg>
</template>
<script>
export default {
  name: 'phasesvg',
  data () {
    return {
    }
  },
  props: {
    backgroundColor: {
      type: String,
      default: '#fff'
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style scoped>
.cls-2 {
    opacity:0.85;
}
.cls-3 {
    fill:#4c9fe8;
}
.cls-4 {
    mask:url(#mask);
}
.cls-5 {
    opacity:0.59;
}
.cls-6 {
    fill:#4c9fe8;
}
.cls-7 {
    filter:url(#luminosity-invert);
}
</style>
