<template>
  <div class="kiss-search-input">
      <div class="kiss-input-container">
        <input type="text" class="kiss-input-default" :placeholder="placeHolder" v-model="textdata">
        <button type="submit" @click="handleClick"></button>
      </div>
  </div>
</template>
<script>
export default {
  name: 'kiss-search-input',
  props: {
    placeHolder: {
      type: String,
      default: '请输入您要搜索的内容...'
    },
    textdata: {
      type: String,
      default: ''
    }
  },
  methods: {
    handleClick (evtFunc) { // 添加点击事件响应
      this.$emit('click', evtFunc)
    }
  }
}
</script>
<style scoped>
  .kiss-input-default{
    width: 230px;
    height: 24px;
    padding: 0;
    margin: 10px 2px 0px 19px;
    color: #42daff;
    font-size: 14px;
    outline: 0px;
    border: 0px;
    border-radius: 6px;
    background-color: transparent;
  }
  .kiss-input-container{
    display: block;
    width: 248px;
    height: 43px;
    background: url("../image/KissSearchInput/searchinput.png");
    /* background-size: cover; */
    background-repeat: no-repeat;
  }
  .kiss-search-input button{
    float: right;
    display: inline;
    margin-top: 10px;
    margin-right: 10px;
    width: 25px;
    height: 25px;
    background: url("../image/KissSearchInput/search.png");
    background-repeat: no-repeat;
    outline: none;
    border: none;
    cursor:pointer;
  }

  .kiss-search-input button:hover{
    background: url("../image/KissSearchInput/searchhover.png");
    background-repeat: no-repeat;
  }
  .kiss-search-input input{
    width: 182px;
    margin: 12px, 2px, 0px, 19px;
  }
  .kiss-search-input input::-webkit-input-placeholder {
    color: #0096ba;
    font-size: 15px;
    text-align: left;
  }
</style>
