<template>
  <nav class="KissHorizontalMenuContainer">
    <ul style="display: inline-block; margin: 0 10px;"  v-for="(item, index) in menuList" :key="index" >
      <li class="KissHorizontalMenuItem" style="float:left;">
        <div
          :id="item.MenuName"
          :class="menuSelList[index] ? 'KissHorizontalMenuBorderClick' : 'KissHorizontalMenuBorder'"
          v-on:click="handleClick($event, index)"
          v-on:mouseenter="onMouseEnter(item)"
          v-on:mouseleave="onMouseLeave(item)"
          ref="menu"
        >
          <div
            class="KissHorizontalMenuIcon"
            :style="getBg(item, index)"
          ></div>
        </div>
        <div class="KissHorizontalMenuName" :style="getTextColor(item, index)">{{item.MenuName}}</div>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'kiss-horizontal-menu',
  props: {
    menuList: {
      type: Array,
      default: function () {
        return [
          {
            MenuName: '强度',
            BaseImgPosition: '0px 0px',
            HoverImgPosition: '0px -40px',
            isHv: 0,
            BaseTextColor: '#0096ba',
            ClickTextColor: '#fff'
          },
          {
            MenuName: '流量',
            BaseImgPosition: '-40px 0',
            HoverImgPosition: '-40px -40px',
            isHv: 0,
            BaseTextColor: '#0096ba',
            ClickTextColor: '#fff'
          },
          {
            MenuName: '速度',
            BaseImgPosition: '-80px 0',
            HoverImgPosition: '-80px -40px',
            isHv: 0,
            BaseTextColor: '#0096ba',
            ClickTextColor: '#fff'
          },
          {
            MenuName: '饱和度',
            BaseImgPosition: '-120px 0',
            HoverImgPosition: '-120px -40px',
            isHv: 0,
            BaseTextColor: '#0096ba',
            ClickTextColor: '#fff'
          },
          {
            MenuName: '通行效率',
            BaseImgPosition: '-160px 0',
            HoverImgPosition: '-160px -40px',
            isHv: 0,
            BaseTextColor: '#0096ba',
            ClickTextColor: '#fff'
          },
          {
            MenuName: '排队长度',
            BaseImgPosition: '-200px 0',
            HoverImgPosition: '-200px -40px',
            isHv: 0,
            BaseTextColor: '#0096ba',
            ClickTextColor: '#fff'
          }
        ]
      }
    }
  },
  data () {
    return {
      active: false,
      styleObject: {
        background: ''
      },
      style: {
        background: ''
      },
      menuShowList: [],
      menuSelList: [],
      curSelIndex: -1
    }
  },
  mounted () {
    for (let menu of this.menuList.values()) {
      this.menuShowList.push({
        name: menu.MenuName,
        show: false
      })
      this.menuSelList.push(false)
    }
    this.setActive(0)
  },
  methods: {
    handleClick (event, index) {
      this.$emit('click', event, index)
      this.setActive(index)
    },
    setActive (index) {
      if (this.curSelIndex !== -1) {
        this.$set(this.menuSelList, this.curSelIndex, false)
      }
      this.menuSelList[index] = true
      this.curSelIndex = index
    },
    addStyle (index) {
      index.isHv = 1
      this.styleObject.backgroundPosition = index.HoverImgPosition
    },
    removeStyle (index) {
      index.isHv = 0
      this.styleObject.backgroundPosition = index.BaseImgPosition
    },
    getBg (item, index) {
      if (this.curSelIndex === -1 && index === 0) {
        let position = (item.isHv === 0 && !this.menuSelList[index]) ? item.HoverImgPosition : item.BaseImgPosition
        return {
          backgroundPosition: position
        }
      }
      let position = (item.isHv === 0 && !this.menuSelList[index]) ? item.BaseImgPosition : item.HoverImgPosition
      return {
        backgroundPosition: position
      }
    },
    getTextColor (item, index) {
      let color = (item.isHv === 0 && !this.menuSelList[index]) ? item.BaseTextColor : item.ClickTextColor
      return {
        color
      }
    },
    onMouseEnter (menuData) {
      let index = this.menuShowList.findIndex(val => val.name === menuData.MenuName)
      this.menuShowList[index].show = true
      this.addStyle(menuData)
    },
    onMouseLeave (menuData) {
      let index = this.menuShowList.findIndex(val => val.name === menuData.MenuName)
      this.menuShowList[index].show = false
      this.removeStyle(menuData)
    }
  }
}
</script>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
}

.KissHorizontalMenuContainer {
  width: 100%;
  height: 100%;
  text-align: center;
}

.KissHorizontalMenuItem {
  outline: 0px;
  border: 0px;
  width: 68px;
  height: 105px;
  background-color: transparent;
  list-style: none;
}

.KissHorizontalMenuBorder {
  position: relative;
  z-index: 1;
  width: 68px;
  height: 78px;
  cursor: pointer;
  background: url("../image/trafficJudgementMenu/bg.png");
  background-size: 73px 78px;
  background-repeat: no-repeat;
  background-position-x: -2px;
}

.KissHorizontalMenuBorder:hover {
  background: url("../image/trafficJudgementMenu/bgchoosed.png");
  background-size: 73px 78px;
  background-repeat: no-repeat;
  background-position-x: -2px;
}

.KissHorizontalMenuBorderClick {
  position: relative;
  z-index: 1;
  width: 68px;
  height: 78px;
  cursor: pointer;
  background: url("../image/trafficJudgementMenu/bgchoosed.png");
  background-size: 73px 78px;
  background-repeat: no-repeat;
  background-position-x: -2px;
}

.KissHorizontalMenuIcon {
  position: relative;
  z-index: 2;
  pointer-events: none; /*这个属性为none  鼠标事件将不起作用直接响应父组件的鼠标事件*/
  width: 40px;
  height: 40px;
  top: 19px;
  left: 14px;
  background-image: url("../image/trafficJudgementMenu/trafficJudgement.png");
  background-repeat: no-repeat;
}

.KissHorizontalMenuName {
  font-family: 'FZLTZHK--GBK1-0';
  margin-top: 8px;
  font-size: 14px;
  color: #0096ba;
  letter-spacing: 1px;
}

.KissHorizontalMenuBorder:hover .KissHorizontalMenuIcon {
  -webkit-animation: toBig 0.4s linear 1;
  -moz-animation: toBig 0.4s linear 1;
  -ms-animation: toBig 0.4s linear 1;
  -o-animation: toBig 0.4s linear 1;
  animation: toBig 0.4s linear 1;
}
/* 定义keyframe动画效果：逐渐放大到原比例*/
@keyframes toBig {
  0% {
    transform: scale(0.3);
  }
  33% {
    transform: scale(0.7);
  }
  66% {
    transform: scale(0.85);
  }
  100% {
    transform: scale(1);
  }
}
</style>
