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
  <div :style="{position: 'absolute', left: Data.left, top: Data.top}">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 252.1 250.2"
      :width="Width"
      :height="Height"
    >
      <!-- <path id="西" v-if="crossType !== 'TypeT-east'" :class="Data.name === '西人行横道' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M2,230v-4H18v4H2Zm0-12H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-9H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2v-4Zm0-8H18v4H2V97Zm0-8H18v4H2V89Zm0-8H18v4H2V81Zm0-9H18v4H2V72Zm0-8H18v4H2V64Zm0-8H18v4H2V56Zm0-8H18v4H2V48Zm0-8H18v4H2V40Zm0-8H18v4H2V32Zm0-8H18v4H2V24Z" transform="translate(-2 -1)"/>
      <path id="东" v-if="crossType !== 'TypeT-west'" :class="Data.name === '东人行横道' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M238,230v-4h16v4H238Zm0-12h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-9h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238v-4Zm0-8h16v4H238V97Zm0-8h16v4H238V89Zm0-8h16v4H238V81Zm0-9h16v4H238V72Zm0-8h16v4H238V64Zm0-8h16v4H238V56Zm0-8h16v4H238V48Zm0-8h16v4H238V40Zm0-8h16v4H238V32Zm0-8h16v4H238V24Z" transform="translate(-2 -1)"/>
      <path id="北" v-if="crossType !== 'TypeT-south'" :class="Data.name === '北人行横道' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M227,17V1h4V17h-4ZM219,1h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-9,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1Zm-8,0h4V17h-4V1ZM98,1h4V17H98V1ZM90,1h4V17H90V1ZM82,1h4V17H82V1ZM73,1h4V17H73V1ZM65,1h4V17H65V1ZM57,1h4V17H57V1ZM49,1h4V17H49V1ZM41,1h4V17H41V1ZM33,1h4V17H33V1ZM25,1h4V17H25V1Z" transform="translate(-2 -1)"/>
      <path id="南" v-if="crossType !== 'TypeT-north'" :class="Data.name === '南人行横道' ? '' : 'invisible'" :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)" d="M227,253V237h4v16h-4Zm-8-16h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-9,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16h-4V237Zm-8,0h4v16H98V237Zm-8,0h4v16H90V237Zm-8,0h4v16H82V237Zm-9,0h4v16H73V237Zm-8,0h4v16H65V237Zm-8,0h4v16H57V237Zm-8,0h4v16H49V237Zm-8,0h4v16H41V237Zm-8,0h4v16H33V237Zm-8,0h4v16H25V237Z" transform="translate(-2 -1)"/>-->
      <g
        id="西"
        :class="Data.name === '西人行横道' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <g>
          <rect y="225" class="st0" width="16" height="4"></rect>
          <rect y="217" class="st0" width="16" height="4"></rect>
          <rect y="209" class="st0" width="16" height="4"></rect>
          <rect y="201" class="st0" width="16" height="4"></rect>
          <rect y="193" class="st0" width="16" height="4"></rect>
          <rect y="185" class="st0" width="16" height="4"></rect>
          <rect y="177" class="st0" width="16" height="4"></rect>
          <rect y="168" class="st0" width="16" height="4"></rect>
          <rect y="160" class="st0" width="16" height="4"></rect>
          <rect y="152" class="st0" width="16" height="4"></rect>
          <rect y="144" class="st0" width="16" height="4"></rect>
          <rect y="136" class="st0" width="16" height="4"></rect>
        </g>
        <g>
          <rect y="128" class="st0" width="16" height="4"></rect>
          <rect y="120" class="st0" width="16" height="4"></rect>
        </g>
        <g>
          <rect y="112" class="st0" width="16" height="4"></rect>
          <rect y="104" class="st0" width="16" height="4"></rect>
          <rect y="96" class="st0" width="16" height="4"></rect>
          <rect y="88" class="st0" width="16" height="4"></rect>
          <rect y="80" class="st0" width="16" height="4"></rect>
          <rect y="71" class="st0" width="16" height="4"></rect>
          <rect y="63" class="st0" width="16" height="4"></rect>
          <rect y="55" class="st0" width="16" height="4"></rect>
          <rect y="47" class="st0" width="16" height="4"></rect>
          <rect y="39" class="st0" width="16" height="4"></rect>
          <rect y="31" class="st0" width="16" height="4"></rect>
          <rect y="23" class="st0" width="16" height="4"></rect>
        </g>
      </g>
      <g id="西-上下">
        <g
          :class="Data.name === '西人行横道-下' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect y="225" class="st0" width="16" height="4"></rect>
          <rect y="217" class="st0" width="16" height="4"></rect>
          <rect y="209" class="st0" width="16" height="4"></rect>
          <rect y="201" class="st0" width="16" height="4"></rect>
          <rect y="193" class="st0" width="16" height="4"></rect>
          <rect y="185" class="st0" width="16" height="4"></rect>
          <rect y="177" class="st0" width="16" height="4"></rect>
          <rect y="168" class="st0" width="16" height="4"></rect>
          <rect y="160" class="st0" width="16" height="4"></rect>
          <rect y="152" class="st0" width="16" height="4"></rect>
          <rect y="144" class="st0" width="16" height="4"></rect>
          <rect y="136" class="st0" width="16" height="4"></rect>
        </g>
        <g
          :class="Data.name === '西人行横道-上' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect y="112" class="st0" width="16" height="4"></rect>
          <rect y="104" class="st0" width="16" height="4"></rect>
          <rect y="96" class="st0" width="16" height="4"></rect>
          <rect y="88" class="st0" width="16" height="4"></rect>
          <rect y="80" class="st0" width="16" height="4"></rect>
          <rect y="71" class="st0" width="16" height="4"></rect>
          <rect y="63" class="st0" width="16" height="4"></rect>
          <rect y="55" class="st0" width="16" height="4"></rect>
          <rect y="47" class="st0" width="16" height="4"></rect>
          <rect y="39" class="st0" width="16" height="4"></rect>
          <rect y="31" class="st0" width="16" height="4"></rect>
          <rect y="23" class="st0" width="16" height="4"></rect>
        </g>
      </g>

      <g
        id="东"
        :class="Data.name === '东人行横道' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <g>
          <rect x="236" y="225" class="st0" width="16" height="4"></rect>
          <rect x="236" y="217" class="st0" width="16" height="4"></rect>
          <rect x="236" y="209" class="st0" width="16" height="4"></rect>
          <rect x="236" y="201" class="st0" width="16" height="4"></rect>
          <rect x="236" y="193" class="st0" width="16" height="4"></rect>
          <rect x="236" y="185" class="st0" width="16" height="4"></rect>
          <rect x="236" y="177" class="st0" width="16" height="4"></rect>
          <rect x="236" y="168" class="st0" width="16" height="4"></rect>
          <rect x="236" y="160" class="st0" width="16" height="4"></rect>
          <rect x="236" y="152" class="st0" width="16" height="4"></rect>
          <rect x="236" y="144" class="st0" width="16" height="4"></rect>
          <rect x="236" y="136" class="st0" width="16" height="4"></rect>
        </g>
        <g>
          <rect x="236" y="128" class="st0" width="16" height="4"></rect>
          <rect x="236" y="120" class="st0" width="16" height="4"></rect>
        </g>
        <g>
          <rect x="236" y="112" class="st0" width="16" height="4"></rect>
          <rect x="236" y="104" class="st0" width="16" height="4"></rect>
          <rect x="236" y="96" class="st0" width="16" height="4"></rect>
          <rect x="236" y="88" class="st0" width="16" height="4"></rect>
          <rect x="236" y="80" class="st0" width="16" height="4"></rect>
          <rect x="236" y="71" class="st0" width="16" height="4"></rect>
          <rect x="236" y="63" class="st0" width="16" height="4"></rect>
          <rect x="236" y="55" class="st0" width="16" height="4"></rect>
          <rect x="236" y="47" class="st0" width="16" height="4"></rect>
          <rect x="236" y="39" class="st0" width="16" height="4"></rect>
          <rect x="236" y="31" class="st0" width="16" height="4"></rect>
          <rect x="236" y="23" class="st0" width="16" height="4"></rect>
        </g>
      </g>

      <g id="东-上下">
        <g
          :class="Data.name === '东人行横道-下' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect x="236" y="225" class="st0" width="16" height="4"></rect>
          <rect x="236" y="217" class="st0" width="16" height="4"></rect>
          <rect x="236" y="209" class="st0" width="16" height="4"></rect>
          <rect x="236" y="201" class="st0" width="16" height="4"></rect>
          <rect x="236" y="193" class="st0" width="16" height="4"></rect>
          <rect x="236" y="185" class="st0" width="16" height="4"></rect>
          <rect x="236" y="177" class="st0" width="16" height="4"></rect>
          <rect x="236" y="168" class="st0" width="16" height="4"></rect>
          <rect x="236" y="160" class="st0" width="16" height="4"></rect>
          <rect x="236" y="152" class="st0" width="16" height="4"></rect>
          <rect x="236" y="144" class="st0" width="16" height="4"></rect>
          <rect x="236" y="136" class="st0" width="16" height="4"></rect>
        </g>
        <g
          :class="Data.name === '东人行横道-上' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect x="236" y="112" class="st0" width="16" height="4"></rect>
          <rect x="236" y="104" class="st0" width="16" height="4"></rect>
          <rect x="236" y="96" class="st0" width="16" height="4"></rect>
          <rect x="236" y="88" class="st0" width="16" height="4"></rect>
          <rect x="236" y="80" class="st0" width="16" height="4"></rect>
          <rect x="236" y="71" class="st0" width="16" height="4"></rect>
          <rect x="236" y="63" class="st0" width="16" height="4"></rect>
          <rect x="236" y="55" class="st0" width="16" height="4"></rect>
          <rect x="236" y="47" class="st0" width="16" height="4"></rect>
          <rect x="236" y="39" class="st0" width="16" height="4"></rect>
          <rect x="236" y="31" class="st0" width="16" height="4"></rect>
          <rect x="236" y="23" class="st0" width="16" height="4"></rect>
        </g>
      </g>

      <g
        id="北"
        :class="Data.name === '北人行横道' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <g>
          <rect x="225" class="st0" width="4" height="16"></rect>
          <rect x="217" class="st0" width="4" height="16"></rect>
          <rect x="209" class="st0" width="4" height="16"></rect>
          <rect x="201" class="st0" width="4" height="16"></rect>
          <rect x="193" class="st0" width="4" height="16"></rect>
          <rect x="185" class="st0" width="4" height="16"></rect>
          <rect x="177" class="st0" width="4" height="16"></rect>
          <rect x="168" class="st0" width="4" height="16"></rect>
          <rect x="160" class="st0" width="4" height="16"></rect>
          <rect x="152" class="st0" width="4" height="16"></rect>
          <rect x="144" class="st0" width="4" height="16"></rect>
          <rect x="136" class="st0" width="4" height="16"></rect>
        </g>
        <g>
          <rect x="128" class="st0" width="4" height="16"></rect>
          <rect x="120" class="st0" width="4" height="16"></rect>
        </g>
        <g>
          <rect x="112" class="st0" width="4" height="16"></rect>
          <rect x="104" class="st0" width="4" height="16"></rect>
          <rect x="96" class="st0" width="4" height="16"></rect>
          <rect x="88" class="st0" width="4" height="16"></rect>
          <rect x="80" class="st0" width="4" height="16"></rect>
          <rect x="71" class="st0" width="4" height="16"></rect>
          <rect x="63" class="st0" width="4" height="16"></rect>
          <rect x="55" class="st0" width="4" height="16"></rect>
          <rect x="47" class="st0" width="4" height="16"></rect>
          <rect x="39" class="st0" width="4" height="16"></rect>
          <rect x="31" class="st0" width="4" height="16"></rect>
          <rect x="23" class="st0" width="4" height="16"></rect>
        </g>
      </g>

      <g id="北-左右">
        <g
          :class="Data.name === '北人行横道-右' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect x="225" class="st0" width="4" height="16"></rect>
          <rect x="217" class="st0" width="4" height="16"></rect>
          <rect x="209" class="st0" width="4" height="16"></rect>
          <rect x="201" class="st0" width="4" height="16"></rect>
          <rect x="193" class="st0" width="4" height="16"></rect>
          <rect x="185" class="st0" width="4" height="16"></rect>
          <rect x="177" class="st0" width="4" height="16"></rect>
          <rect x="168" class="st0" width="4" height="16"></rect>
          <rect x="160" class="st0" width="4" height="16"></rect>
          <rect x="152" class="st0" width="4" height="16"></rect>
          <rect x="144" class="st0" width="4" height="16"></rect>
          <rect x="136" class="st0" width="4" height="16"></rect>
        </g>
        <g
          :class="Data.name === '北人行横道-左' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect x="112" class="st0" width="4" height="16"></rect>
          <rect x="104" class="st0" width="4" height="16"></rect>
          <rect x="96" class="st0" width="4" height="16"></rect>
          <rect x="88" class="st0" width="4" height="16"></rect>
          <rect x="80" class="st0" width="4" height="16"></rect>
          <rect x="71" class="st0" width="4" height="16"></rect>
          <rect x="63" class="st0" width="4" height="16"></rect>
          <rect x="55" class="st0" width="4" height="16"></rect>
          <rect x="47" class="st0" width="4" height="16"></rect>
          <rect x="39" class="st0" width="4" height="16"></rect>
          <rect x="31" class="st0" width="4" height="16"></rect>
          <rect x="23" class="st0" width="4" height="16"></rect>
        </g>
      </g>

      <g
        id="南"
        :class="Data.name === '南人行横道' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <g>
          <rect x="225" y="236" class="st0" width="4" height="16"></rect>
          <rect x="217" y="236" class="st0" width="4" height="16"></rect>
          <rect x="209" y="236" class="st0" width="4" height="16"></rect>
          <rect x="201" y="236" class="st0" width="4" height="16"></rect>
          <rect x="193" y="236" class="st0" width="4" height="16"></rect>
          <rect x="185" y="236" class="st0" width="4" height="16"></rect>
          <rect x="177" y="236" class="st0" width="4" height="16"></rect>
          <rect x="168" y="236" class="st0" width="4" height="16"></rect>
          <rect x="160" y="236" class="st0" width="4" height="16"></rect>
          <rect x="152" y="236" class="st0" width="4" height="16"></rect>
          <rect x="144" y="236" class="st0" width="4" height="16"></rect>
          <rect x="136" y="236" class="st0" width="4" height="16"></rect>
        </g>
        <g>
          <rect x="128" y="236" class="st0" width="4" height="16"></rect>
          <rect x="120" y="236" class="st0" width="4" height="16"></rect>
        </g>
        <g>
          <rect x="112" y="236" class="st0" width="4" height="16"></rect>
          <rect x="104" y="236" class="st0" width="4" height="16"></rect>
          <rect x="96" y="236" class="st0" width="4" height="16"></rect>
          <rect x="88" y="236" class="st0" width="4" height="16"></rect>
          <rect x="80" y="236" class="st0" width="4" height="16"></rect>
          <rect x="71" y="236" class="st0" width="4" height="16"></rect>
          <rect x="63" y="236" class="st0" width="4" height="16"></rect>
          <rect x="55" y="236" class="st0" width="4" height="16"></rect>
          <rect x="47" y="236" class="st0" width="4" height="16"></rect>
          <rect x="39" y="236" class="st0" width="4" height="16"></rect>
          <rect x="31" y="236" class="st0" width="4" height="16"></rect>
          <rect x="23" y="236" class="st0" width="4" height="16"></rect>
        </g>
      </g>

      <g id="南-左右">
        <g
          :class="Data.name === '南人行横道-右' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect x="225" y="236" class="st0" width="4" height="16"></rect>
          <rect x="217" y="236" class="st0" width="4" height="16"></rect>
          <rect x="209" y="236" class="st0" width="4" height="16"></rect>
          <rect x="201" y="236" class="st0" width="4" height="16"></rect>
          <rect x="193" y="236" class="st0" width="4" height="16"></rect>
          <rect x="185" y="236" class="st0" width="4" height="16"></rect>
          <rect x="177" y="236" class="st0" width="4" height="16"></rect>
          <rect x="168" y="236" class="st0" width="4" height="16"></rect>
          <rect x="160" y="236" class="st0" width="4" height="16"></rect>
          <rect x="152" y="236" class="st0" width="4" height="16"></rect>
          <rect x="144" y="236" class="st0" width="4" height="16"></rect>
          <rect x="136" y="236" class="st0" width="4" height="16"></rect>
        </g>
        <g
          :class="Data.name === '南人行横道-左' ? '' : 'invisible'"
          :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
        >
          <rect x="112" y="236" class="st0" width="4" height="16"></rect>
          <rect x="104" y="236" class="st0" width="4" height="16"></rect>
          <rect x="96" y="236" class="st0" width="4" height="16"></rect>
          <rect x="88" y="236" class="st0" width="4" height="16"></rect>
          <rect x="80" y="236" class="st0" width="4" height="16"></rect>
          <rect x="71" y="236" class="st0" width="4" height="16"></rect>
          <rect x="63" y="236" class="st0" width="4" height="16"></rect>
          <rect x="55" y="236" class="st0" width="4" height="16"></rect>
          <rect x="47" y="236" class="st0" width="4" height="16"></rect>
          <rect x="39" y="236" class="st0" width="4" height="16"></rect>
          <rect x="31" y="236" class="st0" width="4" height="16"></rect>
          <rect x="23" y="236" class="st0" width="4" height="16"></rect>
        </g>
      </g>

      <g
        id="斜向行人过街"
        :class="Data.name === 'X人行横道-/' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <rect
          x="78"
          y="164"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -98.1528 106.9619)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="83.7"
          y="158.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -92.496 109.3051)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="89.4"
          y="152.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -86.8391 111.6482)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="95"
          y="147"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -81.1823 113.9914)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="55.4"
          y="186.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -120.7803 97.5894)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="61.1"
          y="180.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -115.1234 99.9325)"
          class="st0"
          width="4"
          height="16"
        ></rect>

        <rect
          x="66.7"
          y="175.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -109.4666 102.2757)"
          class="st0"
          width="4"
          height="16"
        ></rect>

        <rect
          x="72.4"
          y="169.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -103.8097 104.6188)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="49.8"
          y="192.2"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -126.4371 95.2462)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="27.1"
          y="214.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -149.0645 85.8736)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="32.8"
          y="209.2"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -143.4077 88.2168)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="38.4"
          y="203.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -137.7508 90.5599)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="44.1"
          y="197.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -132.094 92.9031)"
          class="st0"
          width="4"
          height="16"
        ></rect>

        <rect
          x="100.7"
          y="141.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -75.5254 116.3345)"
          class="st0"
          width="4"
          height="16"
        ></rect>

        <rect
          x="106.3"
          y="135.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -69.8686 118.6777)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="112"
          y="130"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -64.2117 121.0208)"
          class="st0"
          width="4"
          height="16"
        ></rect>

        <rect
          x="118.3"
          y="123.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -57.8478 123.6569)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="124"
          y="118"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -52.1909 126)"
          class="st0"
          width="4"
          height="16"
        ></rect>

        <rect
          x="129.7"
          y="112.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -46.5341 128.3431)"
          class="st0"
          width="4"
          height="16"
        ></rect>

        <rect
          x="135.3"
          y="106.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -40.8772 130.6863)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="141"
          y="101"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -35.2203 133.0294)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="146.6"
          y="95.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -29.5635 135.3726)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="152.3"
          y="89.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -23.9066 137.7157)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="157.9"
          y="84.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -18.2498 140.0589)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="163.6"
          y="78.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -12.5929 142.402)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="169.3"
          y="72.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -6.9361 144.7452)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="174.9"
          y="67.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -1.2792 147.0883)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="180.6"
          y="61.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 4.3776 149.4315)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="186.9"
          y="55.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 10.7416 152.0675)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="192.6"
          y="49.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 16.3984 154.4106)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="198.2"
          y="43.8"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 22.0553 156.7538)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="203.9"
          y="38.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 27.7122 159.0969)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="209.6"
          y="32.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 33.369 161.4401)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="215.2"
          y="26.8"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 39.0259 163.7832)"
          class="st0"
          width="4"
          height="16"
        ></rect>
        <rect
          x="220.9"
          y="21.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 44.6827 166.1264)"
          class="st0"
          width="4"
          height="16"
        ></rect>
      </g>
      <g
        id="斜向行人过街1"
        :class="Data.name === 'X人行横道-\\' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <rect
          x="72"
          y="78"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -33.1529 80.0381)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="77.7"
          y="83.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -35.496 85.6949)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="83.4"
          y="89.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -37.8391 91.3518)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="89"
          y="95"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -40.1823 97.0086)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="49.4"
          y="55.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -23.7803 57.4106)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="55.1"
          y="61.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -26.1234 63.0675)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="60.7"
          y="66.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -28.4666 68.7243)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="66.4"
          y="72.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -30.8097 74.3812)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="43.8"
          y="49.8"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -21.4371 51.7538)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="21.1"
          y="27.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -12.0645 29.1264)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="26.8"
          y="32.8"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -14.4077 34.7832)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="32.4"
          y="38.4"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -16.7508 40.4401)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="38.1"
          y="44.1"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -19.094 46.0969)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="94.7"
          y="100.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -42.5254 102.6655)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="100.3"
          y="106.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -44.8686 108.3223)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="106"
          y="112"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -47.2117 113.9792)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="112.3"
          y="118.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -49.8478 120.3431)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="118"
          y="124"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -52.1909 126)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="123.7"
          y="129.7"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -54.5341 131.6569)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="129.3"
          y="135.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -56.8772 137.3137)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="135"
          y="141"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -59.2203 142.9706)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="140.6"
          y="146.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -61.5635 148.6274)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="146.3"
          y="152.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -63.9066 154.2843)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="151.9"
          y="157.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -66.2498 159.9411)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="157.6"
          y="163.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -68.5929 165.598)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="163.3"
          y="169.3"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -70.9361 171.2548)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="168.9"
          y="174.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -73.2792 176.9117)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="174.6"
          y="180.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -75.6224 182.5685)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="180.9"
          y="186.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -78.2584 188.9325)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="186.6"
          y="192.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -80.6016 194.5894)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="192.2"
          y="198.2"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -82.9447 200.2462)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="197.9"
          y="203.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -85.2878 205.9031)"
          class="st0"
          width="16"
          height="4"
        ></rect>
        <rect
          x="203.6"
          y="209.6"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -87.631 211.5599)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="209.2"
          y="215.2"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -89.9741 217.2168)"
          class="st0"
          width="16"
          height="4"
        ></rect>

        <rect
          x="214.9"
          y="220.9"
          transform="matrix(0.7071 -0.7071 0.7071 0.7071 -92.3173 222.8736)"
          class="st0"
          width="16"
          height="4"
        ></rect>
      </g>
      <g
        id="路段行人过街-东西"
        :class="Data.name === '东西路段人行横道' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <g>
          <rect x="37.9" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="30.1" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="22.5" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="45.7" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="53.5" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="61.3" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="69.1" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="76.9" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="84.7" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="92.5" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="100.3" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="108.1" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="116" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="123.8" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="131.6" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="139.4" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="147.2" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="155" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="162.8" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="170.6" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="178.4" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="186.2" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="194" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="201.8" y="97.9" width="4" height="56.3"></rect>
        </g>
        <g>
          <rect x="209.6" y="97.9" width="4" height="56.3"></rect>
          <rect x="217.6" y="97.9" width="4" height="56.3"></rect>
          <rect x="225.5" y="97.9" width="4" height="56.3"></rect>
        </g>
      </g>
      <g
        id="路段行人过街-南北"
        :class="Data.name === '南北路段人行横道' ? '' : 'invisible'"
        :fill="this.GreenFlashColor ? this.GreenFlashColor : (Data.color ? Data.color : defaultColor)"
      >
        <g>
          <rect x="97.9" y="37.9" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="30.1" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="22.5" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="45.7" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="53.5" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="61.3" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="69.1" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="76.9" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="84.7" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="92.5" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="100.3" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="108.1" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="116" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="123.8" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="131.6" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="139.4" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="147.2" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="155" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="162.8" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="170.6" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="178.4" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="186.2" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="194" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="201.8" width="56.3" height="4"></rect>
        </g>
        <g>
          <rect x="97.9" y="209.6" width="56.3" height="4"></rect>
          <rect x="97.9" y="217.6" width="56.3" height="4"></rect>
          <rect x="97.9" y="225.5" width="56.3" height="4"></rect>
        </g>
      </g>
    </svg>
  </div>
</template>
<script>
export default {
  name: 'sidewalksvg',
  data () {
    return {
      defaultColor: '#fff', // 默认状态颜色
      GreenFlashColor: undefined,
      lastType: ''
    }
  },
  watch: {
    Data: {
      handler: function (val) {
        if (!val.pedtype) return
        if (val.pedtype === 4 && this.lastType === '') {
          // 绿闪：绿-》灰-》绿 循环效果
          this.GreenFlashColor = '#7bd66b'
          this.GreenIntervalId = setInterval(() => {
            this.GreenFlashColor =
              !this.GreenFlashColor || this.GreenFlashColor === '#828282'
                ? '#7bd66b'
                : '#828282'
          }, 500)
          this.lastType = val.pedtype
        }
        if (
          this.GreenIntervalId &&
          val.pedtype !== 4 &&
          val.pedtype !== this.lastType
        ) {
          clearInterval(this.GreenIntervalId)
          this.GreenFlashColor = undefined
          this.lastType = ''
        }
      },
      deep: true
    }
  },
  props: {
    Width: {
      type: String,
      default: '252.1px'
    },
    Height: {
      type: String,
      default: '250.2px'
    },
    Data: {
      type: Object
    },
    crossType: {
      type: String
    }
  },
  methods: {},
  mounted () {}
}
</script>
<style scoped>
.invisible {
  visibility: hidden;
}
.hide {
  display: none;
}

.green {
  fill: #7bd66b;
}
.red {
  fill: #e24b4b;
}
.yellow {
  fill: #ce932c;
}
.gray {
  fill: #828282;
}
.cls-1 {
  fill: #f2f2f2;
  fill-rule: evenodd;
}
.st1 {
  opacity: 0.9;
}
.st2 {
  fill: #606060;
}
</style>
