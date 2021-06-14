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
/**
 * @Description: Coordination Echarts表格图
 * @Author: yangdongyang
 * @Date: Create in 11:27 2019/11/26
 * @Modified By:
 */
import echarts from 'echarts'
export default class CoordinationModel {
  constructor () {
    this.rourte = {}
    this.greenwave = []
  }
  getMaxY () {
    let rourte = this.rourte
    let maxY = 0
    if (Object.keys(rourte).length === 0) return maxY
    for (let intersections of rourte.intersections) {
      for (let pattern of intersections.patternList) {
        if (pattern.cycle > maxY) {
          maxY = pattern.cycle
        }
      }
    }
    return maxY
  }
  getMaxX () {
    let rourte = this.rourte
    let maxX = 0
    if (Object.keys(rourte).length === 0) return maxX
    for (let intersections of rourte.intersections) {
      maxX = maxX + intersections.distance
    }
    return maxX
  }
  getInitBarData () {
    let rourte = this.rourte
    let data = []
    let xDistance = 0 // 表示该路口到起点的距离
    for (let intersections of rourte.intersections) {
      xDistance = xDistance + intersections.distance
      let list = [xDistance, 0]
      data.push(list)
    }
    return data
  }
  getXName (val) {
    let rourte = this.rourte
    let xDistance = 0 // 表示该路口到起点的距离
    let xName = ''
    for (let intersections of rourte.intersections) {
      xDistance = xDistance + intersections.distance
      if (xDistance === val) {
        xName = intersections.intersectionid + '(' + val + ')'
      }
    }
    return xName
  }
  getCycleNum () {
    let rourte = this.rourte
    let minCycle = 99999
    for (let intersections of rourte.intersections) {
      let cycle = intersections.patternList[0].cycle
      if (cycle < minCycle) {
        minCycle = cycle
      }
    }
    let maxValue = this.getMaxY()
    let cycleNum = Math.ceil(maxValue * 3 / minCycle)
    return cycleNum
  }
  getSeries () {
    let _vue = this
    let seriesList = []
    let dataTemplate = this.getInitBarData()
    let obj = {
      type: 'bar',
      clickable: false,
      stack: '信号灯0',
      barWidth: 6,
      silent: true,
      itemStyle: {
        color: '#009f3c'
      },
      data: dataTemplate,
      label: {
        show: true,
        position: 'bottom',
        offset: [0, 5],
        color: '#666666',
        formatter: function (val) {
          return _vue.getXName(val.data[0])
        }
      }
    }
    seriesList.push(obj)
    let allInterList = []
    let forwardCycleDta = this.getForwardIntersectionCycle()
    let backCycleDta = this.getBackIntersectionCycle()
    allInterList.push(backCycleDta)
    allInterList.push(forwardCycleDta)
    let cycleNum = this.getCycleNum() // 计算时距图上显示几个周期（以周期最小的路口计算）
    for (let s = 0; s < allInterList.length; s++) {
      for (let t = 0; t < cycleNum; t++) {
        for (let i = 0; i < 4; i++) {
          let tempObj = {}
          tempObj.type = 'bar'
          tempObj.clickable = false
          tempObj.stack = '信号灯' + s
          tempObj.barWidth = 6
          tempObj.silent = true
          tempObj.itemStyle = {}
          tempObj.data = []
          if (i % 2 !== 0) {
            tempObj.itemStyle.color = '#009f3c'
          } else {
            tempObj.itemStyle.color = '#ca0d0d'
          }
          for (let cycle of allInterList[s]) {
            let cycleList = []
            cycleList[0] = cycle.distance
            cycleList[1] = cycle.value[i]
            tempObj.data.push(cycleList)
          }
          seriesList.push(tempObj)
        }
        for (let cyc of allInterList[s]) {
          let addCycle = cyc.cycle
          let val = cyc.value
          cyc.value = this.calculationNewCycle(val, addCycle)
        }
      }
    }
    let newSeriesList = seriesList.reverse()
    // 画时距图阴影部分
    let customList = []
    let dir = this.rourte.direction
    if (dir === 'up') {
      let forwardCustomData = this.getCustomData('forward') // 获取正向阴影面积的数据
      customList.push(forwardCustomData)
    } else if (dir === 'down') {
      let backCustomData = this.getCustomData('back') // 获取反向阴影部分面积数据
      customList.push(backCustomData)
    } else if (dir === 'all') {
      let forwardCustomData = this.getCustomData('forward') // 获取正向阴影面积的数据
      let backCustomData = this.getCustomData('back') // 获取反向阴影部分面积数据
      customList.push(forwardCustomData)
      customList.push(backCustomData)
    }
    for (let customl of customList) {
      for (let custom of customl) {
        let data = []
        data.push(custom)
        let customObj = {}
        customObj.type = 'custom'
        customObj.renderItem = this.renderItem
        customObj.silent = true
        customObj.data = data // data数据格式为[n1,n2,n3,n4,n5,n6]n1到n4为阴影面的四个带你的y坐标，n5和n6分别表示x轴上的两点
        newSeriesList.push(customObj)
      }
    }
    // 画时距图虚线部分
    if (this.greenwave.length > 0) {
      let allLine = []
      let dir = this.rourte.direction
      let greenwave = this.greenwave
      let upGreenware = greenwave.filter(ele => ele.type === 'up')
      let downGreenware = greenwave.filter(ele => ele.type === 'down')
      if (dir === 'down') {
        let lineDataBack = this.getLineData('down', downGreenware)
        allLine.push(lineDataBack)
      } else if (dir === 'up') {
        let lineDataFoward = this.getLineData('up', upGreenware)
        allLine.push(lineDataFoward)
      } else if (dir === 'all') {
        let lineDataFoward = this.getLineData('down', downGreenware)
        let lineDataBack = this.getLineData('up', upGreenware)
        allLine.push(lineDataFoward)
        allLine.push(lineDataBack)
      }
      // let lineData = this.getLineData()
      for (let lineData of allLine) {
        for (let line of lineData) {
          let lineObj = {
            lineStyle: {
              normal: {
                color: '#009f3c',
                width: 1,
                type: 'dashed'
              }
            }
          }
          lineObj.name = '最优通行方案'
          lineObj.type = 'line'
          lineObj.data = line
          newSeriesList.push(lineObj)
        }
      }
      // for (let line of lineData) {
      //   let lineObj = {
      //     lineStyle: {
      //       normal: {
      //         color: '#009f3c',
      //         width: 1,
      //         type: 'dashed'
      //       }
      //     }
      //   }
      //   lineObj.name = '最优通行方案'
      //   lineObj.type = 'line'
      //   lineObj.data = line
      //   newSeriesList.push(lineObj)
      // }
    }
    return newSeriesList
  }
  getLineData (direction, greenwave) {
    let data = []
    let rourte = this.rourte
    // let greenwave = this.greenwave
    if (greenwave.length === 0) return data
    let routeList = rourte.intersections
    let firstXLine = routeList[0].distance // 虚线起点的x坐标
    let lastXLine = this.getMaxX() // 虚线终点的x坐标
    let firstYLine = greenwave[0].start // 虚线起点的y坐标
    let firstYLine2 = greenwave[0].width + firstYLine // 第二条虚线起点的y坐标
    let speed = greenwave[0].speed
    let lastYLine = 0
    if (direction === 'down') {
      lastYLine = Number(((lastXLine - firstXLine) / (speed * 1000 / 3600)).toFixed(2)) * (-1) + firstYLine // 下行虚线终点的y坐标
    } else if (direction === 'up') {
      lastYLine = Number(((lastXLine - firstXLine) / (speed * 1000 / 3600)).toFixed(2)) + firstYLine // 上行虚线终点的y坐标
    }
    let lastYLine2 = lastYLine + greenwave[0].width // 第二条虚线终点的y坐标
    let maxY = this.getMaxY() * 3
    let firstDevCycle = rourte.intersections[0].patternList[0].cycle
    let lineNum = Math.ceil(maxY / firstDevCycle)
    for (let i = 0; i < lineNum; i++) { // 根据周期画出多段虚线
      let dataList = []
      let tempList = []
      let addNum = firstDevCycle * i
      tempList[0] = firstXLine
      tempList[1] = firstYLine + addNum
      dataList.push(tempList)
      tempList = []
      tempList[0] = lastXLine
      tempList[1] = lastYLine + addNum
      dataList.push(tempList)
      data.push(dataList)
      tempList = []
      dataList = []
      tempList[0] = firstXLine
      tempList[1] = firstYLine2 + addNum
      dataList.push(tempList)
      tempList = []
      tempList[0] = lastXLine
      tempList[1] = lastYLine2 + addNum
      dataList.push(tempList)
      data.push(dataList)
    }
    return data
  }
  getCustomData (direction) {
    let rourte = this.rourte
    let customList = []
    let routeList = rourte.intersections
    if (routeList.length === 1) return customList // 如果只有一个路口则不画阴影部分
    let speed = 0
    if (direction === 'forward') {
      speed = rourte.upspeed
    }
    if (direction === 'back') {
      speed = rourte.downspeed
    }
    let dist = 0 // 计算每个路口到原点的距离
    for (let i = 0; i < routeList.length - 1; i++) {
      dist = dist + routeList[i].distance
      if (direction === 'forward') {
        let customArea = this.getCustomArea(routeList[i], routeList[i + 1], speed, dist, 'forward')
        let firstDistance = dist // 表示第一个路口距离原点的距离
        let secondDistance = dist + routeList[i + 1].distance // 表示第二个路口到原点的距离
        for (let area of customArea) {
          area.push(firstDistance)
          area.push(secondDistance)
          customList.push(area)
        }
      }
      if (direction === 'back') {
        let firstDistance = dist // 表示第一个路口距离原点的距离
        let secondDistance = dist + routeList[i + 1].distance // 表示第二个路口到原点的距离
        let customArea = this.getCustomArea(routeList[i + 1], routeList[i], speed, secondDistance, 'back')
        for (let area of customArea) {
          area.push(secondDistance)
          area.push(firstDistance)
          customList.push(area)
        }
      }
    }
    return customList
  }
  getCustomArea (intersection1, intersection2, speed, dist, direction) {
    speed = (speed * 1000 / 3600).toFixed(2)
    let maxY = this.getMaxY()
    let intersectionCycle1
    let intersectionCycle2
    let distance
    if (direction === 'forward') {
      let rings1 = this.getByRingById(intersection1.forwardphaseid, intersection1.patternList[0].rings)
      let rings2 = this.getByRingById(intersection2.forwardphaseid, intersection2.patternList[0].rings)
      intersectionCycle1 = this.getCycleData(intersection1.forwardphaseid, rings1, intersection1.patternList[0].cycle, intersection1.patternList[0].offset)
      intersectionCycle2 = this.getCycleData(intersection2.forwardphaseid, rings2, intersection2.patternList[0].cycle, intersection2.patternList[0].offset)
      distance = intersection2.distance
    }
    if (direction === 'back') {
      let rings3 = this.getByRingById(intersection1.backphaseid, intersection1.patternList[0].rings)
      let rings4 = this.getByRingById(intersection2.backphaseid, intersection2.patternList[0].rings)
      intersectionCycle1 = this.getCycleData(intersection1.backphaseid, rings3, intersection1.patternList[0].cycle, intersection1.patternList[0].offset)
      intersectionCycle2 = this.getCycleData(intersection2.backphaseid, rings4, intersection2.patternList[0].cycle, intersection2.patternList[0].offset)
      distance = intersection1.distance
    }
    let intersectionList1 = this.getGreenAreaList(intersectionCycle1, maxY, intersection1.patternList[0].cycle) // 获取第一个路口绿色部分的集合
    let intersectionList2 = this.getGreenAreaList(intersectionCycle2, maxY, intersection2.patternList[0].cycle) // 获取第二个路口绿色部分的集合
    let time = parseInt(distance / speed) // 时间 = 距离 / 速度
    let customAreaList
    if (direction === 'forward') {
      customAreaList = this.getCustomAreaList(intersectionList1, intersectionList2, time, maxY, speed, dist, 'forward')
    }
    if (direction === 'back') {
      customAreaList = this.getCustomAreaList(intersectionList1, intersectionList2, time, maxY, speed, dist, 'back')
    }
    return customAreaList
  }
  getCustomAreaList (intlist1, intlist2, time, maxY, speed, dist, direction) {
    let shadowList = []
    for (let int1 of intlist1) {
      let shadow = []
      for (let int2 of intlist2) {
        let min = int1[0] + time
        let max = int1[1] + time
        if (min === int2[0] && max === int2[1]) {
          shadow = [int1[0], int1[1], int2[1], int2[0]]
        } else if (min < int2[0] && max >= int2[1]) {
          shadow = [int2[0] - time, int2[1] - time, int2[1], int2[0]]
        } else if (min >= int2[0] && max < int2[1]) {
          shadow = [int1[0], int1[1], int1[1] + time, int1[0] + time]
        } else if (min >= int2[0] && min < int2[1]) {
          shadow = [int1[0], int2[1] - time, int2[1], int1[0] + time]
        } else if (max > int2[0] && max <= int2[1]) {
          shadow = [int2[0] - time, int1[1], int1[1] + time, int2[0]]
        }
      }
      shadow = this.checkIsOverY(shadow, maxY, speed, dist, direction) // 检验所取数值是否大于y轴最大值，即阴影部分是否溢出
      if (shadow.length !== 0) shadowList.push(shadow)
    }
    return shadowList
  }
  checkIsOverY (shadow, maxY, speed, dist, direction) {
    let maxValue = maxY * 3 // 计算y轴最大值
    if (shadow.length === 0) {
      return []
    } else if (shadow[0] >= maxValue || shadow[3] >= maxValue) {
      return []
    } else if (shadow[1] > maxValue && shadow[3] < maxValue) {
      return [shadow[0], maxValue, shadow[3] + (maxValue - shadow[0]), shadow[3]]
    } else if (shadow[2] > maxValue && shadow[3] < maxValue) { // 处理阴影面积部分溢出y轴的情况， return[n1, n2, n3, n4, n5], n1到n4为阴影面积四点的纵坐标值，n5为溢出点的横坐标值，n5对应的y轴value值为y轴最大值
      let xValue = (maxValue - shadow[1]) * speed
      if (shadow[1] > maxValue) {
        return [shadow[0], maxValue, maxValue, shadow[3]]
      } else {
        if (direction === 'forward') {
          return [shadow[0], shadow[1], maxValue, shadow[3], xValue + dist]
        } else if (direction === 'back') {
          return [shadow[0], shadow[1], maxValue, shadow[3], dist - xValue]
        }
        // return [shadow[0], shadow[1], maxValue, shadow[3], xValue + dist]
      }
    } else {
      return shadow
    }
  }
  getGreenAreaList (list, max, cycle) {
    let greenAreaList = []
    let cycleNum = Math.ceil(max * 3 / cycle)
    for (let i = 0; i < cycleNum; i++) {
      let tempList = []
      let tempList1 = []
      if (list[3] === 0) {
        let val1 = (cycle * i) + list[0]
        let val2 = (cycle * i) + list[1]
        tempList.push(val1)
        tempList.push(val2)
        greenAreaList.push(tempList)
      } else {
        if (i === 0) {
          let val1 = list[0]
          let val2 = list[1]
          let val3 = list[2]
          let val4 = list[3] + list[1]
          tempList.push(val1)
          tempList.push(val2)
          tempList1.push(val3)
          tempList1.push(val4)
          greenAreaList.push(tempList)
          greenAreaList.push(tempList1)
        } else {
          let val3 = (cycle * i) + list[2]
          let val4 = (cycle * i) + list[3] + list[1]
          tempList.push(val3)
          tempList.push(val4)
          greenAreaList.push(tempList)
        }
      }
    }
    return greenAreaList
  }
  getForwardIntersectionCycle () { // 获取正向每个路口的相位周期数组
    let rourte = this.rourte
    let list = []
    let distance = 0
    for (let intersections of rourte.intersections) {
      let ob = {}
      ob.name = intersections.intersectionid
      distance = distance + intersections.distance
      ob.distance = distance
      let cycle = intersections.patternList[0].cycle
      ob.cycle = cycle
      let forwardphaseid = intersections.forwardphaseid
      let rings = this.getByRingById(forwardphaseid, intersections.patternList[0].rings)
      let offset = intersections.patternList[0].offset
      ob.value = this.getCycleData(forwardphaseid, rings, cycle, offset)
      list.push(ob)
    }
    return list
  }
  getBackIntersectionCycle () { // 获取反向每个路口的相位周期数组
    let rourte = this.rourte
    let list = []
    let distance = 0
    for (let intersections of rourte.intersections) {
      let ob = {}
      ob.name = intersections.intersectionid
      distance = distance + intersections.distance
      ob.distance = distance
      let cycle = intersections.patternList[0].cycle
      ob.cycle = cycle
      let backphaseid = intersections.backphaseid
      let rings = this.getByRingById(backphaseid, intersections.patternList[0].rings)
      let offset = intersections.patternList[0].offset
      // ob.value = this.getCycleData(backphaseid, intersections.patternList[0].rings, cycle, offset)
      ob.value = this.getCycleData(backphaseid, rings, cycle, offset)
      list.push(ob)
    }
    return list
  }
  calculationNewCycle (list, addNum) { // 将数组里的每个值加addnum
    let ll = []
    for (let li of list) {
      li = li + addNum
      ll.push(li)
    }
    return ll
  }
  getByRingById (phaseId, ringsList) {
    for (let rings of ringsList) {
      let ring = rings.filter(item => item.id === phaseId)
      if (ring.length > 0) {
        return rings
      }
    }
    return []
  }
  getCycleData (phaseid, rings, cycle, offset) {
    let tempList = [0, 0, 0, 0]
    let num = rings.length - 1
    if (rings.length === 0) return tempList
    let val = 0
    let greenNum = 0 // 表示时距图中一个周期绿色部分的高度
    for (let i = 0; i < rings.length; i++) {
      val = val + rings[i].value
      if (rings[i].id === phaseid) {
        greenNum = rings[i].value
        // tempList[1] = greenNum
        // tempList[2] = cycle
        if (i === 0) {
          tempList[1] = greenNum
          tempList[2] = cycle
        } else if (i === num) {
          tempList[0] = cycle - greenNum
          tempList[1] = cycle
        } else {
          tempList[0] = val - greenNum
          tempList[1] = val
          tempList[2] = cycle
        }
      }
    }
    // 根据offset对tempList数组进行调整
    if ((offset % cycle) !== 0) {
      let relativeOffset = offset % cycle
      let greenVal = tempList[1]
      if ((relativeOffset + greenVal) < cycle) {
        tempList[0] = relativeOffset + greenVal - greenNum
        tempList[1] = relativeOffset + greenVal
        tempList[2] = cycle
        tempList[3] = 0
      } else if ((relativeOffset + greenVal) === cycle) {
        tempList[0] = cycle - greenNum
        tempList[1] = cycle
        tempList[2] = 0
        tempList[3] = 0
      } else {
        let greenOffset = relativeOffset + greenVal - cycle
        // tempList[0] = 0
        // tempList[1] = greenOffset
        // tempList[2] = relativeOffset
        // tempList[3] = cycle
        if (greenOffset < greenNum) {
          tempList[0] = 0
          tempList[1] = greenOffset
          tempList[2] = relativeOffset + greenVal - greenNum
          tempList[3] = cycle
        } else if (greenOffset === greenNum) {
          tempList[0] = 0
          tempList[1] = greenNum
          tempList[2] = cycle
          tempList[3] = 0
        } else {
          tempList[0] = greenOffset - greenNum
          tempList[1] = greenOffset
          tempList[2] = cycle
          tempList[3] = 0
        }
      }
    }
    return tempList
  }
  renderItem (params, api) {
    if (params.context.rendered) {
      return
    }
    params.context.rendered = true
    var points = []
    if (!Number.isNaN(api.value(6))) { // 处理阴影面积溢出y轴的情况
      points.push(api.coord([api.value(5), api.value(0)]))
      points.push(api.coord([api.value(5), api.value(1)]))
      points.push(api.coord([api.value(4), api.value(2)]))
      points.push(api.coord([api.value(6), api.value(2)]))
      points.push(api.coord([api.value(6), api.value(3)]))
    } else { // 处理正常情况
      points.push(api.coord([api.value(4), api.value(0)]))
      points.push(api.coord([api.value(4), api.value(1)]))
      points.push(api.coord([api.value(5), api.value(2)]))
      points.push(api.coord([api.value(5), api.value(3)]))
    }
    return {
      type: 'polygon',
      shape: {
        // points: points
        points: echarts.graphic.clipPointsByRect(points, {
          x: params.coordSys.x,
          y: params.coordSys.y,
          width: params.coordSys.width,
          height: params.coordSys.height
        })
      },
      style: api.style({
        fill: 'rgba(0, 159, 60, 0.2)'
        // stroke: echarts.color.lift(color)
      })
    }
  }
  RenderOption () {
    return {
      // legend: {
      //   data: ['红灯1', '绿灯1']
      // },
      tooltip: {
        // trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        },
        formatter: function (params) {
          return ''
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'value',
          name: '距离（m）',
          nameGap: 5,
          axisTick: {
            show: false,
            alignWithLabel: false
          },
          splitLine: {
            show: false
          },
          axisLabel: { // 可以设置x轴的value格式
            formatter: function (val) {
              return ''
            },
            interval: 0
            // rotate: 30
          },
          min: -50,
          max: this.getMaxX() + 50
        }
      ],
      yAxis: [
        {
          type: 'value',
          name: '时间（s）',
          axisLine: {
            show: true,
            onZero: false
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: true
          },
          min: 0,
          max: this.getMaxY() * 3
          // show: false
        }
      ],
      series: this.getSeries()
    }
  }
}
