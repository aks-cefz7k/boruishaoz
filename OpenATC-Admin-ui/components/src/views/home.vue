<template>
  <div class="wrap">
    <xdr-dir-selector Width="300px"
                      Height="300px"
                      :showlist="dirshow"></xdr-dir-selector>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dirshow: [
        {
          id: 1,
          color: '#77FB65'
        }, {
          id: 2,
          color: '#E24B4B'
        }
      ],
      datalist: [
        {
          value: 5,
          name: '测试',
          color: '#77FB65'
        },
        {
          value: 1,
          name: '吃饭'
        }
      ],
      mainwidth1: '400px',
      mainheight1: '25px',
      mainwidth: '400px',
      mainheight: '25px',
      display: true,
      name: ['曹操', '刘备', '孙权'],
      list1: [{value: 39, maxNum: 50}],
      list2: [{value: 55, maxNum: 50}],
      list3: [{value: 7, maxNum: 10, color: 'red'}, {value: 80, maxNum: 100}],
      progressPercentage: 0,
      curseconds: 0,
      totalseconds: 86400,
      starttime: '2019-04-25 00:00:00',
      endtime: '2019-04-26 00:00:00'
    }
  },
  methods: {
  },
  watch: {
  },
  mounted () {
  }
}
</script>
<style scoped>
.wrap {
  /* background: #333333; */
  width: 100%;
  height: 100%;
}
</style>
