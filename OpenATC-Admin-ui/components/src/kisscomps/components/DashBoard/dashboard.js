/**
 * @Description: 这个类封装了仪表盘
 * @Author: chengcheng
 * @Date: Create in 10:26 2019/04/03
 * @Modified By:
 */
import MakeGradientColot from './MakeGradientColor'
const PIDEG = Math.PI / 180
export default class DashBoard {
  constructor (id, options) {
    this.m_id = id // id 用来获取dom节点
    this.m_options = options // 设置的选项
    this.m_canvas = null // canvas
    this.m_save_canvas = null // 用来保存状态的canvas  暂时还不理解
    this.m_context = null
    this.m_save_context = null // canvas绘制环境
    this.m_percent = 0
    this.m_target_percent = 0
    this.m_tickmask_offset = 0
    this.m_halfCanvasWidth = 0
    this.m_halfCanvasHeight = 0
    this.m_rotation_deg = 0

    this.default_option = {
      style: 'bar',
      bar_radius: 200,
      bar_bg_w: 20,
      bar_w: 18,
      bar_bg_color: '#193b43',
      bar_bg_ring_color: '#193b43',
      bar_start_color: '#42daff',
      bar_stop_color: 'yellow',
      title: '交通强度',
      title_font_color: '#FFFFFF',
      title_font_size: 40,
      title_show: true,
      title_font_family: 'HanHei SC, PingFang SC, Helvetica Neue Thin, Helvetica, STHeitiSC-Light, Arial, sans-serif',
      sides_font_color: '#FFFFFF',
      sides_font_size: 30,
      sides_show: true,
      sides_font_family: 'HanHei SC, PingFang SC, Helvetica Neue Thin, Helvetica, STHeitiSC-Light, Arial, sans-serif',
      tick_length: 12,
      large_tick_length: 22,
      tick_thickness: 1,
      tick_group_length: 9,
      ticks_groups_begin: 0,
      total_degrees: 200,
      total_tick: 101,
      tick_color: '#555962',
      tick_on_color: '#527d98',
      on_color_gradient: null,
      bg_color: 'rgba(255, 255, 255, 0)',
      gauge_scale: 1,
      animation_duration: 2000,
      show_num: true,
      show_center_num: true,
      center_font_size: 100,
      center_font_color: '#FFFFFF',
      cur_score_circle_color: '#555962',
      center_offset: {
        x: 0,
        y: -30
      },
      center_num_font_family: 'HanHei SC, PingFang SC, Helvetica Neue Thin, Helvetica, STHeitiSC-Light, Arial, sans-serif',
      num_gap: 1,
      num_begin: 0,
      num_font_size: 18,
      tickmask_offset: 0,
      num_font_family: 'HanHei SC, PingFang SC, Helvetica Neue Thin, Helvetica, STHeitiSC-Light, Arial, sans-serif',
      circle_radius: 5,
      circle_offset: 0,
      center_text_unit: '%',
      center_text_offset: {
        x: 16,
        y: 8 - 30
      }
    } // 默认选项
    this.m_default_option = {}
    Object.assign(this.m_default_option, this.default_option, options)
    // 初始化绘制环境
    this.InitCanvas()
  }

  /**
   * 初始化绘图环境
   */
  InitCanvas () {
    this.m_canvas = document.getElementById(this.m_id)
    this.m_save_canvas = document.createElement('canvas')

    let canvas = this.m_canvas

    this.m_save_canvas.width = canvas.width
    this.m_save_canvas.height = canvas.height
    this.delatLength = this.m_default_option.large_tick_length - this.m_default_option.tick_length

    this.m_save_context = this.m_save_canvas.getContext('2d')
    this.m_context = canvas.getContext('2d')
    // this.m_save_context.scale(4, 4)
    this.m_percent = this.m_default_option.percent || 0
    this.m_target_percent = this.m_percent
    this.m_tickmask_offset = this.getTickMarkOffset()
    this.m_halfCanvasHeight = this.m_save_canvas.height / 2
    this.m_halfCanvasWidth = this.m_save_canvas.width / 2
    this.m_rotation_deg = this.getRotationDeg()
  }

  getTickMarkOffset () {
    return this.m_default_option.tickmask_offset + this.m_default_option.circle_radius * 2 + this.m_default_option.circle_offset
  }
  getRotationDeg () {
    return this.m_default_option.total_degrees / (this.m_default_option.total_tick - 1) * PIDEG
  }
  /**
   * 准备舞台, 绘制背景和中间数值
   */
  prepareStage () {
    let saveCanvas = this.m_save_canvas
    let saveContext = this.m_save_context
    // 清除canvas画布
    saveContext.clearRect(0, 0, saveCanvas.width, saveCanvas.height)
    // 设置背景
    this.applyBg()
    // 设置旋转中心
    saveContext.translate(this.m_halfCanvasWidth,
      this.m_halfCanvasHeight - this.m_default_option.tick_thickness / 2)
    // 设置缩放比例
    saveContext.scale(this.m_default_option.gauge_scale,
      this.m_default_option.gauge_scale)
    // 绘制内容
    this.drawContent()
  }

  /**
   * 绘制弧度
   * @param {*} r  半径
   * @param {*} start 开始角度
   * @param {*} end 结束角度
   * @param {*} w 弧宽度
   * @param {*} colorStyle 圆环颜色 支持渐变
   */
  drawDonuts (r, start, end, w, colorStyle) {
    let saveContext = this.m_save_context
    saveContext.lineWidth = w
    saveContext.lineCap = 'round'
    saveContext.beginPath()
    saveContext.arc(0, 0, r, start, end, false)
    saveContext.strokeStyle = colorStyle// '#DC143C'
    saveContext.stroke()
    saveContext.closePath()
  }

  // 绘制中间数字
  drawContent () {
    let saveCanvas = this.m_save_canvas
    let saveContext = this.m_save_context

    if (this.m_default_option.show_center_num) {
      let fillColor = this.m_default_option.center_font_color
        ? this.m_default_option.center_font_color.split('-') : [this.m_default_option.tick_on_color]
      let centerText = Math.floor(
        this.m_percent * ((this.m_default_option.total_tick - 1) *
        this.m_default_option.num_gap +
        this.m_default_option.num_begin) / 100)

      var gaugeWidth = saveCanvas.width / 2 -
        this.m_default_option.circle_radius * 2 -
        this.m_default_option.circle_offset - this.m_default_option.large_tick_length - this.m_default_option.tickmask_offset

      saveContext.save()
      if (fillColor.length === 2) {
        var gradient = saveContext.createLinearGradient(-gaugeWidth, 0, gaugeWidth, 0)
        gradient.addColorStop('0', fillColor[0])
        gradient.addColorStop('0.7', fillColor[1])
        gradient.addColorStop('1', fillColor[1])
        saveContext.fillStyle = gradient
      } else {
        saveContext.fillStyle = fillColor[0]
      }
      saveContext.font = this.m_default_option.center_font_size + 'px ' + this.m_default_option.center_num_font_family
      saveContext.textAlign = 'center'
      saveContext.textBaseline = 'middle'
      var centerTextWidth = Math.floor(saveContext.measureText(centerText).width)
      saveContext.fillText(centerText, this.m_default_option.center_offset.x, this.m_default_option.center_offset.y)
      saveContext.font = '30px ' + this.m_default_option.num_font_family
      saveContext.textAlign = 'center'
      saveContext.textBaseline = 'middle'
      saveContext.fillText(this.m_default_option.center_text_unit, centerTextWidth / 2 +
        this.m_default_option.center_text_offset.x, this.m_default_option.center_text_offset.y)
    }
    let x, y, titleY
    let totalDegrees = this.m_default_option.total_degrees
    if (totalDegrees < 180) {
      x = this.m_default_option.bar_radius * Math.sin(totalDegrees / 2 * PIDEG)
      y = this.m_default_option.bar_radius * Math.cos(totalDegrees / 2 * PIDEG)
      y = -y
      titleY = 0
    } else if (totalDegrees === 180) {
      x = this.m_default_option.bar_radius
      y = 0
      titleY = 0
    } else {
      x = this.m_default_option.bar_radius * Math.cos((totalDegrees - 180) / 2 * PIDEG)
      y = this.m_default_option.bar_radius * Math.sin((totalDegrees - 180) / 2 * PIDEG)
      titleY = y
    }
    if (this.m_default_option.sides_show) {
      // 绘制最大值和最小值
      saveContext.font = this.m_default_option.sides_font_size + 'px ' + this.m_default_option.sides_font_family
      saveContext.fillStyle = this.m_default_option.sides_font_color
      saveContext.fillText(0, -x, y + 30)
      saveContext.fillText(this.m_default_option.total_tick, x, y + 30)
    }
    if (this.m_default_option.title_show) {
      saveContext.font = this.m_default_option.title_font_size + 'px ' + this.m_default_option.title_font_family
      saveContext.fillStyle = this.m_default_option.title_font_color
      saveContext.fillText(this.m_default_option.title, 0, titleY + 30)
    }
    saveContext.restore()
  }
  /**
   * 绘制刻度帧数， 刻度长度 和 第几个数字
   * @param {*} tickLength
   * @param {*} tickIndex
   */
  drawGaugeNum (tickLength, tickIndex) {
    let saveContext = this.m_save_context
    let text = this.m_default_option.num_begin + this.m_default_option.num_gap * tickIndex - 1
    // saveContext.measureText(text)
    saveContext.save()
    saveContext.translate(-1 * this.m_halfCanvasWidth +
      tickLength + this.m_default_option.circle_radius * 2 +
      this.m_default_option.circle_offset +
      this.m_default_option.num_font_size / 2, -this.m_default_option.tick_thickness / 2)
    saveContext.rotate(-90 * PIDEG)
    saveContext.font = this.m_default_option.num_font_size + 'px ' + this.m_default_option.num_font_family
    saveContext.textAlign = 'center'
    saveContext.textBaseline = 'middle'
    saveContext.fillText(text, 0, this.m_default_option.tickmask_offset)
    saveContext.restore()
  }
  /**
   * 应用背景
   */
  applyBg () {
    let saveCanvas = this.m_save_canvas
    let saveContext = this.m_save_context
    saveContext.save()
    saveContext.fillStyle = this.m_default_option.bg_color // 填充绘画的颜色
    saveContext.fillRect(0, 0, saveCanvas.width, saveCanvas.height) // 绘制“被填充”的矩形
    saveContext.restore() // 返回之前保存过的路径状态和属性
  }
  /**
   * 绘制提示圆点
   * @param {*} beginX
   * @param {*} beginY
   */
  drawScoreTipCircle (beginX, beginY) {
    let saveContext = this.m_save_context
    saveContext.save()
    saveContext.fillStyle = this.m_percent > 0.01
      ? this.m_default_option.cur_score_circle_color : this.m_default_option.tick_color
    saveContext.rotate(this.m_percent * this.m_default_option.total_degrees / 100 * PIDEG)
    saveContext.beginPath()
    /**
     * arc方法用来绘制圆
     *   参数1 圆心的x坐标
     *   参数2 圆心的y坐标
     *   参数3 圆的半径
     *   参数4 起始角 弧度单位
     *   参数5 结束角 弧度
     *   参数6 False = 顺时针 True = 逆时针
     */
    saveContext.arc(-beginX, -beginY, this.m_default_option.circle_radius, 0, Math.PI * 2, true)
    saveContext.closePath()
    saveContext.fill()
    saveContext.restore()
  }
  /**
   * 样式一  bar样式
   */
  drawProgressBar () {
    let dutyCycle = this.m_default_option.total_degrees
    let startAngle = (270 - dutyCycle / 2) * PIDEG
    let endAngle = startAngle + dutyCycle * PIDEG

    // bar_bg_color: '#193b43',
    let baroffset = 3
    this.drawDonuts(this.m_default_option.bar_radius,
      startAngle, endAngle,
      this.m_default_option.bar_bg_w, this.m_default_option.bar_bg_color)
    this.drawDonuts(this.m_default_option.bar_radius + this.m_default_option.bar_bg_w + baroffset,
      startAngle, endAngle,
      3, this.m_default_option.bar_bg_ring_color)

    if (this.m_percent === 0) return
    let curNum = this.m_percent
    if (curNum >= this.m_default_option.total_tick) curNum = this.m_default_option.total_tick
    let curendAngle = startAngle + dutyCycle * curNum / 100 * PIDEG

    let saveContext = this.m_save_context
    let ring = saveContext.createLinearGradient(-205, 0, 205, 0)
    ring.addColorStop('0', this.m_default_option.bar_start_color)
    ring.addColorStop('1.0', this.m_default_option.bar_stop_color)
    this.drawDonuts(this.m_default_option.bar_radius,
      startAngle, curendAngle, this.m_default_option.bar_w, ring)
  }

  /**
   * 绘制刻度盘的样式
   */
  drawTickType () {
    let saveContext = this.m_save_context
    let startingDeg = (180 - this.m_default_option.total_degrees) / 2 // 计算开始旋转度数
    saveContext.rotate(startingDeg * PIDEG) // 旋转到初始位置
    // 绘制提示原点
    this.drawScoreTipCircle(this.m_halfCanvasWidth - this.m_default_option.circle_radius,
      this.m_default_option.circle_radius)

    let numTicks = this.m_default_option.total_tick // 总刻度
    for (let i = 1; i <= numTicks; i++) {
      // should this tick be on or off?
      let isOn = (((i - 1) / numTicks) * 100 < this.m_percent)

      // 判断是否最大
      let isLargeTick = this.isLargeTick(i)
      // let rectScale = isLargeTick ? this.large_tick_scale : 1
      let tickLength = isLargeTick ? this.m_default_option.large_tick_length : this.m_default_option.tick_length

      // 获取刻度颜色
      var color = this.getTickColor(isOn, i)
      saveContext.fillStyle = color
      if (isLargeTick) {
        saveContext.fillRect(-1 * this.m_halfCanvasWidth + this.m_default_option.circle_radius * 2 +
        this.m_default_option.circle_offset,
        -this.m_default_option.tick_thickness / 2,
        tickLength, this.m_default_option.tick_thickness)
        if (this.m_default_option.show_num) {
          this.drawGaugeNum(tickLength, i)
        }
      } else {
        saveContext.fillRect(-1 * this.m_halfCanvasWidth +
          this.m_default_option.circle_radius * 2 + this.m_default_option.circle_offset +
          this.delatLength,
        -this.m_default_option.tick_thickness / 2,
        tickLength, this.m_default_option.tick_thickness)
      }
      // rotate for next tick to be placed
      saveContext.rotate(this.m_rotation_deg)
    }
  }

  /**
   * @param {*} currentNum
   * 判断当前最大刻度
   */
  isLargeTick (currentNum) {
    return (currentNum + this.m_default_option.ticks_groups_begin - 1) % (this.m_default_option.tick_group_length + 1) === 0
  }

  gradientColorArray = function () {
    let colorArray = []
    if (!this.m_default_option.on_color_gradient || !this.m_target_percent) {
      return
    }
    for (let i = 0; i < this.m_default_option.on_color_gradient.length - 1; i++) {
      let next = this.m_default_option.on_color_gradient[i + 1]
      let cur = this.m_default_option.on_color_gradient[i]
      let colorStep = (next.percent - cur.percent) / 100 * this.total_tick
      colorArray = colorArray.concat(MakeGradientColot(cur.color, next.color, colorStep))
    }
    return colorArray
  }

  /**
   * 获取刻度颜色
   * @param {*} isOn
   * @param {*} index
   */
  getTickColor (isOn, index) {
    let indexTemp = index < 1 ? 1 : index
    if (isOn) {
      if (this.colorArray && this.colorArray.length > 0) {
        return this.colorArray[indexTemp - 1]
      } else {
        return this.m_default_option.tick_on_color
      }
    } else {
      return this.m_default_option.tick_color
    }
  }
  /**
   * 进行渲染绘制
   * @param percent
   */
  render (percent) {
    if (percent !== undefined) {
      this.m_percent = percent
    }

    // let saveCanvas = this.m_save_canvas
    let saveContext = this.m_save_context
    saveContext.save() // 用来保存Canvas的状态. save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作

    // 绘制基本舞台  包括当前分值
    this.prepareStage()
    switch (this.m_default_option.style) {
      case 'bar': this.drawProgressBar(); break
      case 'tick': this.drawTickType(); break
      default: break
    }
    this.m_context.clearRect(0, 0, this.m_canvas.width, this.m_canvas.height)
    this.m_context.drawImage(this.m_save_canvas, 0, 0)
    saveContext.restore()
  }

  /**
   * 更新百分比以及实时显示信息
   */
  updatedPercent (percent, options = {}) {
    if (percent - 0.1 < 0) {
      return
    }
    let that = this
    this.m_target_percent = percent
    options = options || {}
    let duration = ('animation_duration' in options) ? options.animation_duration : that.m_default_option.animation_duration
    if (duration) {
      let lastUpdate = new Date()
      let start = this.m_percent
      let end = this.m_target_percent
      let changePerms = (end - start) / duration
      var increasing = changePerms > 0 ? 1 : 0
      this.colorArray = this.gradientColorArray()
      let update = () => {
        let now = new Date()
        let elapsed = now - lastUpdate
        that.m_percent += elapsed * changePerms
        lastUpdate = now
        // check if we've made it to our stopping point
        if ((increasing && that.m_percent < that.m_target_percent) ||
        (!increasing && that.m_percent > that.m_target_percent)) {
          that.render()
          that.requestAnimFrame(update)
        } else {
          that.m_percent = that.m_target_percent
          that.render()
        }
      }
      that.requestAnimFrame(update)
    } else {
      that.m_percent = percent
      that.render()
    }
  }
  requestAnimFrame (f) {
    let anim = window.requestAnimationFrame ||
    window.webkitRequestAnimationFrame ||
    window.mozRequestAnimationFrame ||
    window.oRequestAnimationFrame ||
    window.msRequestAnimationFrame ||
    function (callback, element) {
      window.setTimeout(function () {
        callback(new Date())
      }, 1000 / 60)
    }
    anim(f)
  }

  mergeOption () {}
}
