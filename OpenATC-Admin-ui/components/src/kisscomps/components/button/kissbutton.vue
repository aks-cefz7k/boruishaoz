<template>
  <button
    @click="handleClick"
    :class="inbtnType"
    :style="btnstyle"
    :disabled="disabled"
    >
    <slot></slot>
  </button>
</template>

<script>
import { setToken } from '@/utils/auth'
import { getTscControl } from '@/api/control'
export default {
  name: 'kiss-button',
  props: {
    btnType: {
      type: String,
      default: 'kiss-btn-single-bk'
    },
    FontSize: {
      type: String,
      default: '16px'
    },
    disabled: {
      type: Boolean,
      default: false
    },
    token: {
      type: String,
      default: ''
    }
  },
  watch: {
    token: {
      handler: function (val) {
        if (val && val !== '') {
          setToken(val)
          this.getTscdataApi()
        }
      }
    }
  },
  data () {
    return {
      btnstyle: {
        'font-size': this.FontSize
      },
      inbtnType: ''
    }
  },
  methods: {
    handleClick (evt) {
      this.$emit('click', evt)
    },
    getTscdataApi () {
      getTscControl('1511-154').then((data) => {
        if (!data.data.success) {
          console.log('error')
          return
        }
        console.log(data.data.data.data)
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    }
  },
  mounted () {
    this.inbtnType = this.btnType
    if (this.disabled) {
      this.inbtnType = this.btnType + '-disabled'
    }
    if (this.token && this.token !== '') {
      setToken(this.token)
      this.getTscdataApi()
    }
  }
}
</script>

<style scoped>

.kiss-btn-left-bk{
  width: 104px;
  height: 43px;
  outline: 0px;
  color: #b6f0ff;
  border: 0px;
  /* font-size: 20px; */
  background-color: transparent;
  background: url("../image/button/btnLeft.png");
  background-repeat: no-repeat;
}

.kiss-btn-left-bk:hover,
.kiss-btn-left-bk:focus{
  color: #fcffb6;
  background: url("../image/button/btnLeftClick.png");
  background-repeat: no-repeat;
}
.kiss-btn-center-bk{
  width: 104px;
  height: 43px;
  outline: 0px;
  border: 0px;
  color: #b6f0ff;
  /* font-size: 16px; */
  background-color: transparent;
  background: url("../image/button/btnCenter.png");
  background-repeat: no-repeat;
}
.kiss-btn-center-bk:hover,
.kiss-btn-center-bk:focus{
  color: #fcffb6;
  background: url("../image/button/btnCenterClick.png");
  background-repeat: no-repeat;
}
.kiss-btn-right-bk{
  width: 104px;
  height: 43px;
  outline: 0px;
  border: 0px;
  color: #b6f0ff;
  /* font-size: 20px; */
  background-color: transparent;
  background: url("../image/button/btnRight.png");
  background-repeat: no-repeat;
}
.kiss-btn-right-bk:hover,
.kiss-btn-right-bk:focus{
  color: #fcffb6;
  background: url("../image/button/btnRightClick.png");
  background-repeat: no-repeat;
}
.kiss-btn-single-bk{
  width: 104px;
  height: 43px;
  outline: 0px;
  border: 0px;
  /* font-size: 20px; */
  color: #b6f0ff;
  background-color:transparent;
  background: url("../image/button/btnSingle.png");
  background-repeat: no-repeat;
}

.kiss-btn-single-bk:hover,
.kiss-btn-single-bk:focus{
  color: #fcffb6;
  background: url("../image/button/btnSingleClick.png");
  background-repeat: no-repeat;
}

.kiss-btn-single-bk-disabled{
  width: 104px;
  height: 43px;
  outline: 0px;
  border: 0px;
  /* font-size: 20px; */
  color: #0096ba;
  background-color:transparent;
  background: url("../image/button/btnSingleDisable.png");
  background-repeat: no-repeat;
}
.kiss-btn-center-bk-disabled{
  width: 104px;
  height: 43px;
  outline: 0px;
  border: 0px;
  /* font-size: 20px; */
  color: #0096ba;
  background-color:transparent;
  background: url("../image/button/btnCenterDisable.png");
  background-repeat: no-repeat;
}
.kiss-btn-right-bk-disabled{
  width: 104px;
  height: 43px;
  outline: 0px;
  border: 0px;
  /* font-size: 20px; */
  color: #0096ba;
  background-color:transparent;
  background: url("../image/button/btnRightDisable.png");
  background-repeat: no-repeat;
}
.kiss-btn-left-bk-disabled{
  width: 104px;
  height: 43px;
  outline: 0px;
  border: 0px;
  /* font-size: 20px; */
  color: #0096ba;
  background-color:transparent;
  background: url("../image/button/btnLeftDisable.png");
  background-repeat: no-repeat;
}
</style>
