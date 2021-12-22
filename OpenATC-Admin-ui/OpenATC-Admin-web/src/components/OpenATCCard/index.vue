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
  <div class="cardBox" :style="{width, height}">
    <div class="cardTitle" v-if="ishasTitle">{{title}}</div>
    <slot name="cardContent"></slot>
  </div>
</template>

<script>
export default {
  name: 'open-ATC-card',
  props: {
    width: {
      type: String
    },
    height: {
      type: String
    },
    title: {
      type: String,
      default: ''
    },
    ishasTitle: {
      type: Boolean,
      default: true
    }
  }
}
</script>

<style lang="scss" scoped>
.cardBox {
  background-color: $--color-white;
  padding: 0 22px;
}
.cardTitle {
  height: 52px;
  line-height: 52px;
  border-bottom: 1px solid #eee;
  font-size: 18px;
  color: $--color-text-regular;
  font-family: MicrosoftYaHei;
}
</style>
