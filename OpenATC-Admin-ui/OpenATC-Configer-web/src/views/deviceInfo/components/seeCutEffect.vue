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
  <div class="deviceinfo-seeCutDetail">
    <el-dialog
      :title="$t('edge.deviceinfo.siteIdCutEffect')"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose">
      <div>
        <el-row :gutter="20">
          <el-col :span="4">{{$t('edge.deviceinfo.addresscode')}}</el-col>
          <el-col :span="20">{{code }}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">{{$t('edge.deviceinfo.cutData')}}</el-col>
          <el-col :span="20">
            {{cutMsg }}
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">{{$t('edge.deviceinfo.cutEffect')}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <div class="effect-pic">
              <div class="x-container" v-for="(numberList,index) of xList" :key="index">
                <div class="x-title">{{titleList[index] }}</div>
                <div class="x-item-container">
                  <div class="content">
                    <el-row :gutter="0" style="hight:20%">
                      <el-col :span="6"><div>8</div></el-col>
                      <el-col :span="6"><div>4</div></el-col>
                      <el-col :span="6"><div>2</div></el-col>
                      <el-col :span="6"><div>1</div></el-col>
                    </el-row>
                    <el-row :gutter="0" style="hight:80%">
                      <el-col :span="6" v-for="(num,index2) of numberList" :key="index2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </div>
              <div class="r-container">
                <div class="r-title">{{titleList[5] }}</div>
                <div class="r-item-container">
                  <div class="content">
                    <el-row :gutter="0">
                      <el-col :span="5"><div>A</div></el-col>
                      <el-col :span="2"><div>B</div></el-col>
                      <el-col :span="2"><div>X</div></el-col>
                      <el-col :span="2"><div>D</div></el-col>
                      <el-col :span="2"><div>E</div></el-col>
                      <el-col :span="2"><div>F</div></el-col>
                      <el-col :span="2"><div>G</div></el-col>
                      <el-col :span="2"><div>H</div></el-col>
                      <el-col :span="2"><div>I</div></el-col>
                    </el-row>
                    <el-row :gutter="0">
                      <el-col :span="5">
                        <div>
                          &nbsp;
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                      <el-col :span="2">
                        <div class="icon-content">
                           <svg-icon style="height:7em;" v-if="num===1" icon-class="cutRed"></svg-icon>
                           <svg-icon style="height:7em;" v-else icon-class="connectBlue"></svg-icon>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">{{$t('edge.login.close')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'seeCutEffect',
  data () {
    return {
      dialogVisible: false,
      code: 0,
      labelList: [8, 4, 2, 1],
      titleList: ['X10000', 'X1000', 'X100', 'X10', 'X1', 'REVISION'],
      cutMsg: '',
      xList: [
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
      ]
    }
  },
  methods: {
    show (val) {
      if (!val) {
        return false
      }
      this.code = val
      this.dialogVisible = true
      let isValid = false
      let resStr = ''
      for (let i = 0; i < val.length; i++) {
        let char = val[i]
        let num = Number(char)
        if (num === 0 && !isValid) {
          continue
        } else {
          isValid = true
        }
        let str = this.toBinary(num)
        let diffLength = 4 - str.length
        for (let j = 0; j < diffLength; j++) {
          str = '0' + str
        }
        resStr = resStr + str
      }
      this.setXlist(resStr)
      console.log(resStr)
    },
    setXlist (binaryStr) {
      if (binaryStr.length > 20) {
        this.$message.error(this.$t('edge.deviceinfo.longgerThan20'))
      }
      if (binaryStr.length < 20) {
        let len = 20 - binaryStr.length
        for (let i = 0; i < len; i++) {
          binaryStr = '0' + binaryStr
        }
      }
      let list = binaryStr.split('')
      let tempList = []
      let targetList = []
      for (let i = 0; i < list.length; i++) {
        let num = Number(list[i])
        tempList.push(num)
        if (tempList.length === 4) {
          targetList.push(tempList)
          tempList = []
        }
      }
      this.xList = [...targetList]
      this.setCutMsg()
    },
    setCutMsg () {
      let res = ''
      let msgList = []
      for (let i = 0; i < this.xList.length; i++) {
        let list = this.xList[i]
        let titleMsg = this.titleList[i]
        let detailMag = ''
        let tempLabelList = []
        for (let j = 0; j < list.length; j++) {
          let num = list[j]
          if (num === 1) {
            let label = this.labelList[j]
            tempLabelList.push(label)
          }
        }
        detailMag = tempLabelList.join(', ')
        if (detailMag !== '') {
          let msgStr = titleMsg + ': ' + detailMag + ' '
          msgList.push(msgStr)
        }
      }
      res = msgList.join('  ')
      this.cutMsg = res
    },
    handleClose (done) {
      done()
    },
    toBinary (num) {
      var arr = []
      // 根据输入的数创建对应大小的8421数组
      var create8421 = function () {
        var tempArr = [1]
        while (tempArr[0] - num < 0) {
          // 如果两数相减为负数
          // 在数组第一位插入第二位的2倍值
          tempArr.unshift(tempArr[0] * 2)
        }
        return tempArr
      }
      var _8421 = create8421()
      var bit = _8421.length
      // 判断数组值是否全为0
      var isAll0 = function (arr) {
        var flag = true
        for (var i = 0; i < bit; i++) {
          if (arr[i] !== 0) {
            flag = false
            continue
          }
        }
        return flag
      }
      var fn = function (num) {
        for (var i = 0; i < _8421.length; i++) {
          // 存放8421的临时值
          var temp = _8421[i]
          // 如果传入的数字与8421数组中相减为0
          if (num - temp === 0) {
            // 改位用1表示
            arr.push(1)
            var length = arr.length
            // 如果当前输入length小于bit位则用0补齐
            if (length < bit) {
              for (var c = 0; c < bit - length; c++) {
                arr.push(0)
              }
            }
            return arr
          } else {
            // 如果小于0 则用0表示
            if (num - temp < 0) {
              arr.push(0)
              if (arr.length === bit) {
                // 如果8位全是0则返回改数组
                if (isAll0(arr)) {
                  return arr
                }
              }
              // 如果大于0 则用1表示
            } else if (num - temp > 0) {
              arr.push(1)
              // 并且把8421数组从当前位索引截断
              _8421.splice(0, i + 1)
              // 把改数与当前位的差递归
              return fn(num - temp)
            }
          }
        }
      }
      return fn(num).join('').replace(/^0+/, '')
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
</style>
