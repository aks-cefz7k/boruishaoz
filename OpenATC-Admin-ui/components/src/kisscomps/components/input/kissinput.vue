<template>
   <div class="kiss-input-container">
        <input
            v-model="inputData"
            :type="inputType"
            class = "kiss-input-default"
            :placeholder="placeHolder"
        >
    </div>
</template>
<script>
export default {
  name: 'kiss-input',
  props: {// 这里是组件可以接受的参数，也就是相当于面向原型写组件时的配置参数，用户可以传递不同参数，自己定义组件
    placeHolder: {// 输入框默认显示的内容
      type: String,
      default: ''
    },
    inputType: {
      type: String,
      default: 'text'
    },
    inputData: {// 输入框内容
      type: String,
      default: ''
    }
  }
}
</script>
<style scoped>
.kiss-input-default{
    width: 230px;
    height: 24px;
    padding:0;
    margin: 17px 2px 0px 19px;
    color: #42daff;
    font-size: 14px;
    outline: 0px;
    border: 0px;
    border-radius: 6px;
    background-color:transparent;
  }
  .kiss-input-container{
    display: block;
    margin: 0;
    padding: 0;
    width: 270px;
    height: 53px;
    background: url("../image/KissInput/input.png");
    background-size: cover;
  }
  .kiss-input-default::-webkit-input-placeholder{
    color: #0096ba;;
    font-size: 15px;
    text-align: left;
  }
</style>
