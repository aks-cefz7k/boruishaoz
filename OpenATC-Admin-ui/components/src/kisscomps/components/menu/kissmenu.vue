<template>
  <nav class="KissNavMenuContainer">
    <ul
      v-for="(item, index) in menuList"
      :key="index"
    >
      <li class="KissNavMenuItem">
        <div
          :id="item.MenuName"
          :class="menuSelList[index] ? 'KissNavMenuBorderClick' : 'KissNavMenuBorder'"
          v-on:click="handleClick($event, index)"
          v-on:mouseenter="onMouseEnter(item)"
          v-on:mouseleave="onMouseLeave(item)"
          ref="menu"
        >
          <div
            class="KissNavMenuIcon"
            :style="getBg(item, index)"
          ></div>
          <transition name="bounce">
            <div
              class="KissNavMenuName"
              v-show="isShow(item.MenuName)"
            >{{item.MenuName}}</div>
          </transition>
        </div>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'kiss-nav-menu',
  props: {
    // 定义一个组件被外部使用的时候可变的行为的。可以通过props来配置这个组件:style= "{background : index.isHv == 0 ? index.BaseImg : index.HoverImg}"
    menuList: {
      type: Array,
      default: function () {
        return [
          {
            MenuName: 'asdfasdfadsfaaaaa',
            BaseImg: 'url("./static/img/KissNavMenu/home.png")',
            HoverImg: 'url("./static/img/KissNavMenu/homeselect.png")',
            isHv: 0
          },
          {
            MenuName: '路网',
            BaseImg: 'url("./static/img/KissNavMenu/net.png")',
            HoverImg: 'url("./static/img/KissNavMenu/netselect.png")',
            isHv: 0
          },
          {
            MenuName: '路口',
            BaseImg: 'url("./static/img/KissNavMenu/cross.png")',
            HoverImg: 'url("./static/img/KissNavMenu/crossselect.png")',
            isHv: 0
          },
          {
            MenuName: '统计',
            BaseImg: 'url("./static/img/KissNavMenu/count.png")',
            HoverImg: 'url("./static/img/KissNavMenu/countselect.png")',
            isHv: 0
          },
          {
            MenuName: '报警',
            BaseImg: 'url("./static/img/KissNavMenu/alart.png")',
            HoverImg: 'url("./static/img/KissNavMenu/alartselect.png")',
            isHv: 0
          },
          {
            MenuName: '帮助',
            BaseImg: 'url("./static/img/KissNavMenu/help.png")',
            HoverImg: 'url("./static/img/KissNavMenu/helpselect.png")',
            isHv: 0
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
  },
  methods: {
    handleClick (event, index) {
      this.$emit('click', event, index)
      this.setActive(index)
    },
    setActive (index) {
      if (this.curSelIndex !== -1) {
        // this.menuSelList[this.curSelIndex] = false
        this.$set(this.menuSelList, this.curSelIndex, false)
      }
      this.menuSelList[index] = true
      this.curSelIndex = index
    },
    addStyle (index) {
      index.isHv = 1
      this.styleObject.background = index.HoverImg
    },
    removeStyle (index) {
      index.isHv = 0
      this.styleObject.background = index.BaseImg
    },
    getBg (item, index) {
      let bg = (item.isHv === 0 && !this.menuSelList[index]) ? item.BaseImg : item.HoverImg
      // let img = require(bg)
      return {
        background: bg
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
    },
    isShow (menuName) {
      let index = this.menuShowList.findIndex(val => val.name === menuName)
      if (index < 0) return false
      return this.menuShowList[index].show
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/************** 这里是通用样式, 是不用抽取的 **********/
* {
  margin: 0px;
  padding: 0px;
}
/*整个nav的容器, 大小随着里面的子元素变动而变动*/
.KissNavMenuContainer {
  width: auto;
  height: auto;
}

.KissNavMenuItem {
  outline: 0px;
  border: 0px;
  width: 82;
  height: 92px;
  background-color: transparent;
  list-style: none;
}
/***************************************************/

.KissNavMenuBorder {
  position: relative;
  z-index: 1;
  width: 64px;
  height: 74px;
  cursor: pointer;
  /* border: 1px solid red; */
  background: url("../image/KissNavMenu/menubg.png");
  background-repeat: no-repeat;
}

.KissNavMenuBorder:hover {
  background: url("../image/KissNavMenu/menubgselect.png");
  background-repeat: no-repeat;
}

.KissNavMenuBorderClick {
  position: relative;
  z-index: 1;
  width: 64px;
  height: 74px;
  cursor: pointer;
  background: url("../image/KissNavMenu/menubgselect.png");
  background-repeat: no-repeat;
}

.KissNavMenuIcon {
  position: relative;
  z-index: 2;
  pointer-events: none; /*这个属性为none  鼠标事件将不起作用直接响应父组件的鼠标事件*/
  width: 38px;
  height: 38px;
  top: 18px;
  bottom: 0px;
  /* left: 0px;
  right: 0px; */
  /* border: 1px solid red; */
  margin-left: auto;
  margin-right: auto;
  background-repeat: no-repeat;
}

.KissNavMenuBorder:hover .KissNavMenuIcon {
  -webkit-animation: dese 0.75s linear infinite;
  -moz-animation: dese 0.75s linear infinite;
  -ms-animation: dese 0.75s linear infinite;
  -o-animation: dese 0.75s linear infinite;
  animation: dese 0.75s linear infinite;
}
.KissNavMenuBorder:hover .KissNavMenuName {
  opacity: 1;
}

.KissNavMenuBorderClick:hover .KissNavMenuIcon {
  -webkit-animation: dese 0.75s linear infinite;
  -moz-animation: dese 0.75s linear infinite;
  -ms-animation: dese 0.75s linear infinite;
  -o-animation: dese 0.75s linear infinite;
  animation: dese 0.75s linear infinite;
}
.KissNavMenuBorderClick:hover .KissNavMenuName {
  opacity: 1;
}

/*****************图标文字***************/
.KissNavMenuName {
  opacity: 0;
  position: relative;
  display: inline-block;
  pointer-events: none;
  z-index: 0;
  height: 35px;
  white-space: nowrap;
  min-width: 100px;
  top: -17px;
  left: 67px;
  text-align: center;
  color: #ffde00;
  background-color: #724e01;
  border: #ffba00 solid 1px;

  font-size: 14px;
  line-height: 38px;
  padding-left: 5px;
  padding-right: 5px;
}

/* 定义keyframe动画效果：摇摆*/
@keyframes dese {
  0% {
    transform: rotate(-12deg);
  } /* 添加旋转角度，以下类似 */
  20% {
    transform: rotate(8deg);
  }
  30% {
    transform: rotate(6deg);
  }
  40% {
    transform: rotate(3deg);
  }
  55% {
    transform: rotate(0deg);
  }
  70% {
    transform: rotate(-4deg);
  }
  80% {
    transform: rotate(7deg);
  }
  90% {
    transform: rotate(-8deg);
  }
  90% {
    transform: rotate(12deg);
  }
}

.bounce-leave-to {
  animation: bounceInLeft 0.5s reverse;
}
.bounce-enter-active {
  animation: bounceInLeft 0.5s;
}

@keyframes bounceInLeft {
  0% {
    opacity: 0;
    transform: translate3d(-2000px, 0, 0);
  }

  60% {
    opacity: 0.6;
    transform: translate3d(-1000px, 0, 0);
  }

  75% {
    opacity: 0.75;
    transform: translate3d(-500px, 0, 0);
  }

  90% {
    opacity: 0.9;
    transform: translate3d(-20px, 0, 0);
  }

  to {
    transform: none;
  }
}
</style>
