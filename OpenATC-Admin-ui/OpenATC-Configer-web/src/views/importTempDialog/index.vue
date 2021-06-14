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
  <div class="temp-import">
    <el-dialog
        title="模板数据编辑"
        :visible.sync="visible"
        width="1070px"
        :modal-append-to-body="false"
        :close-on-click-modal="false"
        @close='handleClose'>
         <el-row>
          <el-col :span="13">
               <el-row>
                   <el-col :span="4">
                      <div class="sort-name">模板</div>
                   </el-col>
                   <el-col :span="20">
                      <el-form
                        ref="template"
                        :model="templateInfo"
                        label-width="80px">
                            <el-form-item
                                label="路口形状"
                                prop="shape">
                                <el-select size="small" v-model="shape" placeholder="" style="width: 367px">
                                    <el-option v-for="shape in shapeList" :key="shape" :label="CrossShape.get(shape)" :value="shape"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item
                                label="名称"
                                prop="intersection">
                                <el-select size="small" v-model="intersection" placeholder="" style="width: 367px">
                                    <el-option v-for="temp in tempList" :key="temp" :label="TempMap.get(temp)" :value="temp"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item
                                label="相位数量"
                                prop="count">
                                <el-select size="small" v-model="phaseCount" placeholder="" style="width: 367px" :disabled="shape !== '999'">
                                  <el-option v-for="option in phaseOptions" :key="option" :label="option" :value="option"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-form>
                   </el-col>
               </el-row>
               <el-row>
                   <el-col :span="4">
                      <div class="sort-name">相位</div>
                   </el-col>
                   <el-col :span="20">
                      <el-form
                        ref="phase"
                        :inline="true"
                        label-width="80px"
                        :model="templateInfo">
                            <el-form-item
                            label="绿闪"
                            prop="flashgreen">
                            <el-select size="small" v-model="templateInfo.phase.flashgreen" placeholder="">
                              <el-option v-for="option in greenOptions" :key="option" :label="option + 's'" :value="option"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item
                            label="黄灯"
                            prop="yellow" class="ipLabel">
                            <el-select size="small" v-model="templateInfo.phase.yellow" placeholder="">
                              <el-option v-for="option in yellowOptions" :key="option" :label="option + 's'" :value="option"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item
                            label="红灯清空"
                            prop="redclear" class="portLabel">
                            <el-select size="small" v-model="templateInfo.phase.redclear" placeholder="">
                             <el-option v-for="option in redOptions" :key="option" :label="option + 's'" :value="option"></el-option>
                            </el-select>
                        </el-form-item>
                        </el-form>
                   </el-col>
               </el-row>
               <el-row>
                   <el-col :span="4">
                      <div class="sort-name">方案</div>
                   </el-col>
                   <el-col :span="20">
                      <el-form
                        ref="pattern"
                        :inline="true"
                        label-width="80px"
                        :model="templateInfo">
                          <el-form-item
                            label="数量"
                            prop="count">
                            <el-select size="small" v-model="templateInfo.pattern.count" placeholder="">
                              <el-option v-for="option in defaultOptions" :key="option" :label="option" :value="option"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item
                            label="绿信比"
                            prop="split">
                            <el-input-number size="small" v-model="templateInfo.pattern.split" :min="0" :max="255"></el-input-number>
                        </el-form-item>
                        </el-form>
                   </el-col>
               </el-row>
               <el-row>
                   <el-col :span="4">
                      <div class="sort-name">计划</div>
                   </el-col>
                   <el-col :span="20">
                      <el-form
                        ref="plan"
                        :inline="true"
                        label-width="80px"
                        :model="templateInfo">
                          <el-form-item
                            label="数量"
                            prop="count">
                            <el-select size="small" v-model="templateInfo.plan.count" placeholder="">
                              <el-option v-for="option in defaultOptions" :key="option" :label="option" :value="option"></el-option>
                            </el-select>
                            </el-form-item>
                        </el-form>
                   </el-col>
               </el-row>
               <el-row>
                   <el-col :span="4">
                      <div class="sort-name">日期</div>
                   </el-col>
                   <el-col :span="20">
                      <el-form
                        ref="date"
                        :inline="true"
                        label-width="80px"
                        :model="templateInfo">
                         <el-form-item
                            label="数量"
                            prop="count">
                            <el-select size="small" v-model="templateInfo.date.count" placeholder="">
                              <el-option v-for="option in defaultOptions" :key="option" :label="option" :value="option"></el-option>
                            </el-select>
                          </el-form-item>
                          <el-form-item
                              label="全年"
                              prop="allyear">
                              <el-switch size="small" v-model="templateInfo.date.allyear"></el-switch>
                          </el-form-item>
                        </el-form>
                   </el-col>
               </el-row>
          </el-col>
          <el-col :span="11">
            <!-- <div class="crossImg" ref="crossImg" :class="crossTypeImgClass"></div> -->
            <CrossDiagram :intersection="intersection" :phaseList="phaseList" />
          </el-col>
        </el-row>
        <div
        slot="footer"
        class="footer">
            <el-button @click="handleClose()">取消</el-button>
            <el-button type="primary" @click="submitDeviceInfo()">确定</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import { getSupportedTemplate, createTemplate } from '@/api/template'
import CrossDiagram from './crossDirection/crossDiagram'
export default {
  name: 'importTemp',
  components: {
    CrossDiagram
  },
  props: {
    imortVisible: {
      type: Boolean
    }
  },
  watch: {
    shape: {
      handler: function (val) {
        // 根据路口形状的选择，过滤模板名称的可选项
        let fliterList = this.cate[val]
        this.tempList = fliterList.map(ele => ele.temp)
        this.tempData = fliterList.map(ele => ({
          temp: ele.temp,
          phaseList: ele.phaseList
        }))
        // 默认显示第一项
        this.intersection = this.tempData[0].temp
        this.phaseList = this.tempData[0].phaseList
      }
    },
    intersection: {
      handler: function (val) {
        let crossintersection = val.split('-')[1]
        this.getCrossTypeImgClass(crossintersection)
        // 同步可提交的表单数据 templateInfo
        this.templateInfo.template.type = val
        // 更新相位数量
        this.phaseCount = Number(this.deleteZero(val.split('-')[2])) || 4
        this.tempData.forEach(data => {
          if (data.temp === val) {
            this.phaseList = data.phaseList
          }
        })
      }
    },
    phaseCount: {
      handler: function (val) {
        // 同步可提交的表单数据 templateInfo
        this.templateInfo.phase.count = val
      }
    }
  },
  data () {
    return {
      visible: this.imortVisible,
      list: [], // 模板列表
      cate: {}, // 经过分类的模板列表
      CrossShape: new Map([['100', '丁字路口'], ['101', '十字路口'], ['102', '环形路口'], ['110', '其他形状路口'], ['999', '自定义路口']]),
      CrossIntersection: new Map([['000', ''], ['001', '东向'], ['002', '南向'], ['003', '西向'], ['004', '北向']]),
      CrossPhase: new Map([['00', ''], ['01', '一相位'], ['02', '二相位'], ['03', '三相位'],
        ['04', '四相位'], ['05', '五相位'], ['06', '六相位'], ['07', '七相位'], ['08', '八相位'], ['09', '九相位'],
        ['10', '十相位'], ['11', '十一相位'], ['12', '十二相位'], ['13', '十三相位'], ['14', '十四相位'],
        ['15', '十五相位'], ['16', '十六相位']]),
      shapeList: [], // 路口形状可选项
      tempList: [], // 模板名称可选项
      TempMap: new Map(), // 模板字符串与模板名称映射
      shape: '', // 路口形状
      intersection: '', // 模板名称
      phaseList: [], // 当前模版相位
      phaseCount: 4, // 相位数量
      phaseOptions: [], // 相位数量可选项
      defaultOptions: [], // 通用数量可选项
      greenOptions: [],
      yellowOptions: [],
      redOptions: [],
      crossTypeImgClass: '', // 示意图类名
      templateInfo: {
        template: {
          type: ''
        },
        phase: {
          count: 4,
          flashgreen: 10,
          yellow: 3,
          redclear: 2
        },
        pattern: {
          count: 1,
          split: 30
        },
        plan: {
          count: 1
        },
        date: {
          count: 1,
          allyear: true
        }
      }
    }
  },
  methods: {
    getCrossTypeImgClass (intersection) {
      if (this.shape === '101') {
        this.crossTypeImgClass = 'Crossroads'
      }
      if (this.shape === '100') {
        switch (intersection) {
          case '001': this.crossTypeImgClass = 'TypeT-east'
            break
          case '002': this.crossTypeImgClass = 'TypeT-south'
            break
          case '003': this.crossTypeImgClass = 'TypeT-west'
            break
          case '004': this.crossTypeImgClass = 'TypeT-north'
            break
        }
      }
      if (this.shape === '999') {
        this.crossTypeImgClass = 'Customroads'
      }
    },
    submitDeviceInfo () {
      createTemplate(this.templateInfo).then(res => {
        if (!res.data.success) {
          console.log(res.data.message)
          return
        }
        this.$message({
          type: 'success',
          message: '导入模版成功！'
        })
        this.$emit('closeImportTemp')
        // this.globalParamModel.reset()
        const tscParam = JSON.parse(JSON.stringify(this.globalParamModel.getGlobalParams()))
        this.globalParamModel.reset()
        setTimeout(() => {
          let temp = {
            ...tscParam,
            ...JSON.parse(JSON.stringify(res.data.data))
          }
          this.globalParamModel.setGlobalParams(temp)
        }, 50)
      })
    },
    handleClose () {
      this.$emit('closeImportTemp')
    },
    getShapeList () {
      // 获取可选路口形状
      this.shapeList = [...new Set(this.list.map(ele => ele.crossShape))]
      // 默认显示第一项
      this.shape = this.shapeList[0]
    },
    getTemplateList () {
      // 获取可选模板名称
      this.tempList = [...new Set(this.list.map(ele => ele.temp))]
    },
    handleTempSort (data) {
    //   根据支持的模板列表，生成对应可选项
      this.list = []
      this.cate = {}
      data.forEach((ele, index) => {
        let arr = ele.type.split('-')
        let crossShape = arr[0]
        let crossintersection = arr[1]
        let phasecount = Number(this.deleteZero(arr[2]))
        let crossShapeName = this.CrossShape.get(arr[0])
        let phasecountstr = this.CrossPhase.get(arr[2])
        let crosstempstr = this.CrossShape.get(arr[0]) + this.CrossIntersection.get(arr[1]) + this.CrossPhase.get(arr[2])
        if (!this.cate[arr[0]]) {
          this.cate[arr[0]] = []
        }
        this.cate[arr[0]].push({
          id: index,
          temp: ele.type,
          phaseList: ele.phaseList,
          crosstempstr,
          crossShape,
          phasecount,
          crossintersection,
          crossShapeName,
          phasecountstr
        })
        this.list[index] = {
          id: index,
          temp: ele.type,
          phaseList: ele.phaseList,
          crosstempstr,
          crossShape,
          phasecount,
          crossintersection,
          crossShapeName,
          phasecountstr
        }
        this.TempMap.set(ele.type, crosstempstr)
      })
      this.getShapeList()
      this.getTemplateList()
    },
    createCountOptions () {
      // 根据api定义生成多个数量枚举值选项
      for (let i = 1; i <= 8; i++) {
        if (i >= 2) {
          this.phaseOptions.push(i)
        }
        this.defaultOptions.push(i)
      }
    },
    deleteZero (str) {
      // 去掉字符串首位零
      return str.replace(/\b(0+)/gi, '')
    },
    createClearOptions () {
      // 生成清空模块的绿闪、黄灯、红灯清空枚举值选项
      for (let i = 0; i <= 20; i++) {
        if (i <= 5) {
          if (i >= 1) {
            this.yellowOptions.push(i)
          }
          this.redOptions.push(i)
        }
        if (i % 5 === 0) {
          this.greenOptions.push(i)
        }
      }
    },
    getTemplate () {
      this.createCountOptions()
      this.createClearOptions()
      getSupportedTemplate().then(res => {
        if (!res.data.success) {
          console.log(res.data.message)
          return
        }
        this.handleTempSort(res.data.data)
      })
    }
  },
  mounted () {
    this.globalParamModel = this.$store.getters.globalParamModel
    this.getTemplate()
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.temp-import .el-form-item__label {
    color: #999;
    font-weight: 400;
}
.temp-import .el-dialog__footer {
  padding: 10px 72px 38px 0;
}
.temp-import .el-dialog__body {
  line-height: 0px;
}
</style>
<style lang="scss" rel="stylesheet/scss" scoped>
.temp-import {
  .el-select {
    width: 75px;
  }
  .footer {
    button {
      width: 56px;
      height: 32px;
      padding: 0;
      text-align: center;
      line-height: 30px;
      font-family: MicrosoftYaHei;
      font-size: 12px;
      font-weight: normal;
      font-stretch: normal;
    }
  }
}
.sort-name {
    font-family: MicrosoftYaHei;
    font-size: 15px;
    font-weight: normal;
    font-stretch: normal;
    line-height: 40px;
    letter-spacing: 0px;
    color: #303133;
    text-align: right;
    padding-right: 30px;
}
.crossImg {
  width: 225px;
  height: 225px;
  background-repeat: no-repeat;
}
.Crossroads {
  background-image: url('../../assets/intersection_type/Crossroads.png')
}
.TypeT-east {
  background-image: url('../../assets/intersection_type/TypeT-east.png')
}
.TypeT-south {
  background-image: url('../../assets/intersection_type/TypeT-south.png')
}
.TypeT-west {
  background-image: url('../../assets/intersection_type/TypeT-west.png')
}
.TypeT-north {
  background-image: url('../../assets/intersection_type/TypeT-north.png')
}
.Customroads {
  background-image: url('../../assets/intersection_type/Customroads.png')
}
</style>
