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
<div class="customRoadsIcon">
  <svg width="179" height="146" viewBox="0 0 179 146" xmlns="http://www.w3.org/2000/svg">
    <path id="默认路口" class="cls-1" d="M1101.92,2792H945.085A11.127,11.127,0,0,0,934,2803.14v123.72A11.127,11.127,0,0,0,945.085,2938H1101.92a11.124,11.124,0,0,0,11.08-11.14V2803.14A11.124,11.124,0,0,0,1101.92,2792Zm-156.835,7.42H1101.92a3.713,3.713,0,0,1,3.69,3.72v110.83l-43.01-39.04-35.51,29.73-50.281-43.15-35.419,38.42v-96.79A3.712,3.712,0,0,1,945.085,2799.42Zm-3.7,127.44v-16l36.017-39.07,68.513,58.79H945.085A3.712,3.712,0,0,1,941.39,2926.86Zm160.53,3.72H1057.3l-24.51-21.03,29.61-24.79,43.21,39.22v2.88A3.713,3.713,0,0,1,1101.92,2930.58Zm-46.4-75.89a21.445,21.445,0,1,0-21.35-21.45A21.424,21.424,0,0,0,1055.52,2854.69Zm0-35.47a14.025,14.025,0,1,1-13.96,14.02A14,14,0,0,1,1055.52,2819.22Z" transform="translate(-934 -2792)"/>
  </svg>
  <p class="text">未显示路口图</p>
</div>
</template>
<script>
export default {
  name: 'customRoads',
  data () {
    return {}
  },
  methods: {},
  mounted () {}
}
</script>
<style scoped>
.cls-1 {
  fill: #e3f0ff;
  fill-rule: evenodd;
}
.customRoadsIcon {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
.customRoadsIcon .text {
  color: #999;
  font-size: 16px;
  text-align: center;
}
</style>
