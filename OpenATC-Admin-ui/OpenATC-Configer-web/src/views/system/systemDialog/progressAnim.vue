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
<div class="progressAnim">
  <div :style="{width: width, height: height}" class="bar"></div>
  <div class="bartext">上传中...</div>
</div>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  props: {
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '5px'
    }
  },
  created () {
  },
  methods: {
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
// $main-color:#4169AA;
// $sub-color: rgb(49, 81, 133);
// .bar {
//   position: relative;
//   margin: auto;
//   border-radius: 20px;
//   background: $main-color;
//   overflow: hidden;

//   &::before {
//     position: absolute;
//     content: "";
//     top: 0;
//     left: 0;
//     width: 1200px;
//     height: 85px;
//     background: repeating-linear-gradient(45deg, $sub-color, $sub-color 10px, transparent 11px, transparent 19px, $sub-color 20px) no-repeat 0 0;
//     animation: move 1s linear infinite;
//   }
// }

// @keyframes move {
//   from {
//     background-position: 0 0;
//   }
//   to {
//     background-position: 0 -56px;
//   }
// }

// .bartext {
//   color: #fff;
//   margin-top: 8px;
//   font-size: 12px;
// }
</style>
