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
  <div class="plan-table" ref="plan-table">
    <el-button style="margin-bottom:10px" type="primary" @click="onAdd">{{$t('edge.common.add')}}</el-button>
    <el-button
      style="margin-bottom:10px"
      type="primary"
      @click="editName"
    >{{$t('edge.plan.editname')}}</el-button>
    <el-table
      :data="plan"
      v-loading.body="listLoading"
      element-loading-text="Loading"
      fit
      highlight-current-row
      :max-height="tableHeight"
      id="footerBtn"
    >
      <el-table-column align="center" label="ID" width="60">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.plan.time')" min-width="190" align="center">
        <template slot-scope="scope">
          <el-col :span="10">
            <el-select style="float:right;width:80px" v-model="scope.row.hour" size="small">
              <el-option
                v-for="item in HoursOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-col>
          <el-col :span="2">
            <span>:</span>
          </el-col>
          <el-col :span="10">
            <el-select style="float:left;width:80px" v-model="scope.row.minute" size="small">
              <el-option
                v-for="item in MinuteOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-col>
        </template>
      </el-table-column>
      <!-- <el-table-column :label="$t('edge.plan.pattern')" min-width="100" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.pattern" size="small" @change="doChange(scope.row)">
            <el-option
              v-for="item in PatternOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </template>
      </el-table-column>-->
      <el-table-column :label="$t('edge.plan.controltype')" min-width="100" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.control" size="small" @change="doChange(scope.row)">
            <el-option
              v-for="item in ControlOption"
              :key="item.value"
              :label="$t('edge.plan.ControlOption' + item.value)"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.plan.pattern')" min-width="100" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.pattern" size="small" :disabled="handleControl(scope.row)">
            <el-option
              v-for="item in PatternOption"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column :label="$t('edge.plan.operation')" width="120" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleDelete(scope.$index)">{{$t('edge.common.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  props: ['plan', 'planid', 'planname'],
  computed: {
    ...mapState({
      planList: state => state.globalParam.tscParam.planList
    })
  },
  data () {
    return {
      tableHeight: 700,
      screenHeight: window.innerHeight, // 屏幕高度
      id: 1,
      listLoading: false,
      HoursOption: [
        {
          value: 0,
          label: '00'
        },
        {
          value: 1,
          label: '01'
        },
        {
          value: 2,
          label: '02'
        },
        {
          value: 3,
          label: '03'
        },
        {
          value: 4,
          label: '04'
        },
        {
          value: 5,
          label: '05'
        },
        {
          value: 6,
          label: '06'
        },
        {
          value: 7,
          label: '07'
        },
        {
          value: 8,
          label: '08'
        },
        {
          value: 9,
          label: '09'
        },
        {
          value: 10,
          label: '10'
        },
        {
          value: 11,
          label: '11'
        },
        {
          value: 12,
          label: '12'
        },
        {
          value: 13,
          label: '13'
        },
        {
          value: 14,
          label: '14'
        },
        {
          value: 15,
          label: '15'
        },
        {
          value: 16,
          label: '16'
        },
        {
          value: 17,
          label: '17'
        },
        {
          value: 18,
          label: '18'
        },
        {
          value: 19,
          label: '19'
        },
        {
          value: 20,
          label: '20'
        },
        {
          value: 21,
          label: '21'
        },
        {
          value: 22,
          label: '22'
        },
        {
          value: 23,
          label: '23'
        }
      ],
      MinuteOption: [
        {
          value: 0,
          label: '00'
        },
        {
          value: 1,
          label: '01'
        },
        {
          value: 2,
          label: '02'
        },
        {
          value: 3,
          label: '03'
        },
        {
          value: 4,
          label: '04'
        },
        {
          value: 5,
          label: '05'
        },
        {
          value: 6,
          label: '06'
        },
        {
          value: 7,
          label: '07'
        },
        {
          value: 8,
          label: '08'
        },
        {
          value: 9,
          label: '09'
        },
        {
          value: 10,
          label: '10'
        },
        {
          value: 11,
          label: '11'
        },
        {
          value: 12,
          label: '12'
        },
        {
          value: 13,
          label: '13'
        },
        {
          value: 14,
          label: '14'
        },
        {
          value: 15,
          label: '15'
        },
        {
          value: 16,
          label: '16'
        },
        {
          value: 17,
          label: '17'
        },
        {
          value: 18,
          label: '18'
        },
        {
          value: 19,
          label: '19'
        },
        {
          value: 20,
          label: '20'
        },
        {
          value: 21,
          label: '21'
        },
        {
          value: 22,
          label: '22'
        },
        {
          value: 23,
          label: '23'
        },
        {
          value: 24,
          label: '24'
        },
        {
          value: 25,
          label: '25'
        },
        {
          value: 26,
          label: '26'
        },
        {
          value: 27,
          label: '27'
        },
        {
          value: 28,
          label: '28'
        },
        {
          value: 29,
          label: '29'
        },
        {
          value: 30,
          label: '30'
        },
        {
          value: 31,
          label: '31'
        },
        {
          value: 32,
          label: '32'
        },
        {
          value: 33,
          label: '33'
        },
        {
          value: 34,
          label: '34'
        },
        {
          value: 35,
          label: '35'
        },
        {
          value: 36,
          label: '36'
        },
        {
          value: 37,
          label: '37'
        },
        {
          value: 38,
          label: '38'
        },
        {
          value: 39,
          label: '39'
        },
        {
          value: 40,
          label: '40'
        },
        {
          value: 41,
          label: '41'
        },
        {
          value: 42,
          label: '42'
        },
        {
          value: 43,
          label: '43'
        },
        {
          value: 44,
          label: '44'
        },
        {
          value: 45,
          label: '45'
        },
        {
          value: 46,
          label: '46'
        },
        {
          value: 47,
          label: '47'
        },
        {
          value: 48,
          label: '48'
        },
        {
          value: 49,
          label: '49'
        },
        {
          value: 50,
          label: '50'
        },
        {
          value: 51,
          label: '51'
        },
        {
          value: 52,
          label: '52'
        },
        {
          value: 53,
          label: '53'
        },
        {
          value: 54,
          label: '54'
        },
        {
          value: 55,
          label: '55'
        },
        {
          value: 56,
          label: '56'
        },
        {
          value: 57,
          label: '57'
        },
        {
          value: 58,
          label: '58'
        },
        {
          value: 59,
          label: '59'
        }
      ],
      // HoursOption: ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
      //   '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
      // MinuteOption: [
      //   '00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
      //   '11', '12', '13', '14', '15', '16', '17', '18', '19', '20',
      //   '21', '22', '23', '24', '25', '26', '27', '28', '29', '30',
      //   '31', '32', '33', '34', '35', '36', '37', '38', '39', '40',
      //   '41', '42', '43', '44', '45', '46', '47', '48', '49', '50',
      //   '51', '52', '53', '54', '55', '56', '57', '58', '59'],
      PatternOption: [],
      ControlOption: [
        {
          value: 1
        },
        {
          value: 2
        },
        {
          value: 3
        },
        {
          value: 5
        },
        {
          value: 6
        },
        {
          value: 9
        },
        {
          value: 10
        },
        {
          value: 12
        },
        {
          value: 19
        }
      ],
      controlStatus: true
    }
  },
  // filters: {
  //   statusFilter(status) {
  //     const statusMap = {
  //       published: 'success',
  //       draft: 'gray',
  //       deleted: 'danger'
  //     }
  //     return statusMap[status]
  //   }
  // },
  created () {
    // this.fetchData()
    this.globalParamModel = this.$store.getters.globalParamModel
    const patternList = this.globalParamModel.getParamsByType('patternList')
    for (let i = 0; i < patternList.length; i++) {
      if (patternList[i].desc !== '') {
        let obj = {}
        obj.value = patternList[i].id
        obj.label = patternList[i].desc
        this.PatternOption.push(obj)
      } else {
        let obj = {}
        obj.value = patternList[i].id
        if (this.$i18n.locale === 'en') {
          obj.label = 'Pattern' + String(patternList[i].id)
        } else if (this.$i18n.locale === 'zh') {
          obj.label = '方案' + String(patternList[i].id)
        }
        this.PatternOption.push(obj)
      }
    }
    for (let i = 0; i < this.plan.length; i++) {
      const patternValue = this.PatternOption.map(ele => ele.value)
      if (patternValue.indexOf(this.plan[i].pattern) === -1) {
        this.plan[i].pattern = ''
      }
    }
    this.increaseId()
  },
  mounted: function () {
    this.setTableMaxHeight()
  },
  watch: {
    screenHeight: function () {
      // 监听屏幕高度变化
      this.tableHeight =
        window.innerHeight -
        document.querySelector('#footerBtn').offsetTop -
        150
    }
  },
  methods: {
    setTableMaxHeight () {
      var _this = this
      _this.$nextTick(function () {
        _this.tableHeight = _this.$refs['plan-table'].offsetHeight - 50
        window.onresize = function () {
          _this.tableHeight = _this.$refs['plan-table'].offsetHeight - 50
        }
      })
    },
    // increaseId () { // 实现id在之前的基础上加1
    //   let plan = this.plan
    //   let i = plan.length - 1
    //   if (i >= 0) {
    //     this.id = plan[i].id + 1
    //   }
    // },
    increaseId () {
      // 实现id在之前的基础上寻找最小的
      let plan = this.plan
      let planIdList = plan.map(ele => ele.id)
      let i = plan.length - 1
      if (i >= 0) {
        for (let j = 1; j <= 48; j++) {
          if (!planIdList.includes(j)) {
            this.id = j
            return
          }
        }
      }
    },
    handleDelete (index) {
      this.$confirm(
        this.$t('edge.plan.deletetip'),
        this.$t('edge.common.alarm'),
        {
          confirmButtonText: this.$t('edge.common.confirm'),
          cancelButtonText: this.$t('edge.common.cancel'),
          type: 'warning'
        }
      )
        .then(() => {
          this.plan.splice(index, 1)
          this.$message({
            type: 'success',
            message: this.$t('edge.common.deletesucess')
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('edge.common.deletecancel')
          })
        })
    },
    onAdd () {
      this.increaseId()
      if (this.plan.length >= 48) {
        this.$message.error(
          // 'There are at most 48 data !'
          this.$t('edge.plan.mostdata')
        )
        return
      }
      let newPlanItem = {}
      newPlanItem.id = this.id
      newPlanItem.hour = 0
      newPlanItem.minute = 0
      newPlanItem.control = 5
      this.plan.push(newPlanItem)
      // this.id++
      // 重新排序相位数组
      this.plan.sort(this.compareProperty('id'))
    },
    compareProperty (property) {
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value1 - value2
      }
    },
    doChange (row) {
      if (row.control === 1 || row.control === 2 || row.control === 3) {
        if (row.pattern !== undefined) {
          delete row.pattern
        }
      }
    },
    handleControl (row) {
      if (
        row.control === undefined ||
        row.control === '' ||
        row.control === 1 ||
        row.control === 2 ||
        row.control === 3
      ) {
        return true
      } else {
        return false
      }
    },
    editName () {
      this.$prompt(this.$t('edge.plan.editcontext'), this.$t('edge.plan.tip'), {
        confirmButtonText: this.$t('edge.plan.ok'),
        cancelButtonText: this.$t('edge.plan.cancel'),
        inputValue: this.planname,
        inputValidator: (value) => {
          if (value === undefined || value === null || value.replace(/\s/g, '') === '') {
            // 计划名必填校验
            return this.$t('edge.plan.plannamerequired')
          }
          // 计划名不能重复校验
          let inputvalue = value.replace(/\s/g, '')
          const planList = this.globalParamModel.getParamsByType('planList')
          for (let obj of planList) {
            let curdesc = obj.desc
            if (curdesc) {
              curdesc = curdesc.replace(/\s/g, '')
            }
            if (curdesc === inputvalue && curdesc !== this.planname) {
              return this.$t('edge.plan.plannamerepeated')
            }
          }
          return true
        }
      }).then(({ value }) => {
        this.planList.forEach(plan => {
          if (plan.id === this.planid) {
            plan.desc = value
          }
        })
      }).catch(() => {
        // this.$message({
        //   type: 'info',
        //   message: 'Input canceled'
        // })
      })
    }
  }
}
</script>
