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
const zh = {
  edge: {
    'auth': {
      'current': '当前密码',
      'new': '新密码',
      'contain': '密码需要包含',
      'contain1': ' 8个或者更多的字符。',
      'contain2': '包含数字,字母以及特殊',
      'contain3': '字符。',
      'confirm': '确认新密码',
      'strength': '强度',
      'none': '无',
      'weak': '弱',
      'normal': '普通',
      'strong': '强',
      'setnewpassword': '设置新密码',
      'oldpass': '旧密码',
      'inputpassagain': '请再次输入密码',
      'twopassinconsistent': '两次输入密码不一致 !',
      'enterpass': '请输入密码',
      'enternewpass': '请输入新密码',
      'inputoldpass': '请输入旧密码！',
      'twopassnotdiff': '新密码不能与旧密码一致！',
      'originalpasserror': '原密码错误!',
      'modifisucceeded': '修改成功',
      'getmd5error': '获取MD5失败！'
    },
    'route': {
      'dashboard': '首页',
      'overview': '总览',
      'edge': '参数',
      'phase': '相位',
      'overlap': '跟随相位',
      'pattern': '方案',
      'plan': '计划',
      'date': '日期',
      'channel': '通道',
      'channelLock': '通道锁定',
      'detector': '检测器',
      'pedestrianDetector': '行人检测器',
      'system': '系统',
      'history': '故障',
      'optimize': '优化',
      'control': '控制',
      'deviceInfo': '设备信息',
      'adaptiveParameter': '自适应参数',
      'user': '用户',
      'dailyRecord': '日志',
      'controlPanel': '手动面板',
      'statistics': '统计'
    },
    'main': {
      'main': '目录',
      'home': '首页',
      'logout': '登出',
      'upload': '上载',
      'download': '下载',
      'import': '从文件导入',
      'export': '从文件导出',
      'importtemplate': '模板操作',
      'copy': '复制参数',
      'copyDevice': '设备',
      'copyPattern': '的方案已被复制',
      'tip': '提示',
      'read': '读取',
      'language': '语言',
      'readDevice': '是否载入设备',
      'readPattern': '的方案信息，到当前设备配置中 ?',
      'title': '欢迎进入配置管理中心',
      'changelanguage': '切换语言',
      'agentid': '设备ID',
      'passwordexpiration': '密码到期',
      'organization': '组织机构',
      'realname': '真实姓名',
      'phone': '联系电话',
      'email': '电子邮箱',
      'changepass': '修改密码',
      'about': '关于',
      'exit': '退出',
      'help': '帮助',
      'options': 'options',
      'options1': '全部',
      'options2': '相位',
      'options3': '跟随相位',
      'options4': '方案',
      'options5': '计划',
      'options6': '日期',
      'options7': '通道',
      'options8': '检测器',
      'options9': '行人检测器',
      'graphics': '图形界面',
      'text': '文字界面'
    },
    'overview': {
      'crossinfo': '路口信息',
      'crossname': '路口名称',
      'divicestate': '设备状态',
      'protocoltype': '协议类型',
      'signalID': '路口ID',
      'signalIP': '信号机IP',
      'faultinfo': '故障信息',
      'platform': '平台类型',
      'online': '在线',
      'offline': '离线',
      'connected': '已连接',
      'onlineing': '联机中...',
      'controlmode': '控制方式',
      'manual': '手动控制',
      'exitmanual': '退出手动',
      'controlmodel': '控制模式',
      'patternname': '方案名称',
      'controlnumber': '方案编号',
      'mode': '控制方式',
      'stage': '阶段(驻留)',
      'specialcontrol': '特殊控制',
      'phaseclosecontrol': '相位关断控制',
      'implement': '执行',
      'ipaddress': 'IP地址',
      'deviceport': '设备端口',
      'agreement': '协议',
      'currentstage': '当前阶段',
      'allstagesnum': '总阶段数',
      'patternnum': '方案编号',
      'details': '详情',
      'autocontrol': '多时段',
      'yellowflash': '黄闪',
      'allred': '全红',
      'step': '步进',
      'fixedperiod': '定周期',
      'inductioncontrol': '感应控制',
      'nocablecoordination': '无电缆协调',
      'phasewalk': '行人过街',
      'websteroptimization': 'Webster单点优化',
      'inductivePedestrianCrossControl': '感应式行人过街控制',
      'selfadaption': '自适应控制',
      'custom': '自定义',
      'controlmodevalue': '控制方式数值',
      'patternstate': '方案状态',
      'cycle': '周期',
      'phasesplit': '绿信比',
      'phase': '相位',
      'patternoffset': '相位差',
      'coordinationtime': '协调时间',
      'getintersectionmapagain': '重新获取路口图',
      'ring': '环',
      'phaseid': '相位id',
      'detectorid': '检测器ID',
      'smallcars': '小型车数量',
      'mediumvehicles': '中型车数量',
      'bigcars': '大型车数量',
      'possessionoftime': '占有时间',
      'occupancy': '占有率',
      'phase_countdown': '相位倒计时',
      'split': '绿信比',
      'type': '类型',
      'showlist': 'showlist',
      'showlist1': '控制模式',
      'showlist2': '周期',
      'showlist3': '当前/剩余时间',
      'showlist4': '控制方式',
      'showlist5': '相位差',
      'showlist6': '实时流量',
      'platformcontrol': '平台控制',
      'configurationtoolcontrol': '配置工具控制',
      'manualcontrol': '手动面板控制',
      'modelList': 'modelList',
      'modelList0': '多时段',
      'modelList1': '黄闪',
      'modelList2': '全红',
      'modelList3': '关灯',
      'modelList4': '步进',
      'modelList5': '定周期',
      'modelList6': '感应控制',
      'modelList7': '协调感应控制',
      'modelList8': '方案选择控制',
      'modelList9': '自适应控制',
      'modelList10': '无电缆协调',
      'modelList11': '上位机协调控制',
      'modelList12': '行人过街',
      'modelList13': '方案恢复过渡',
      'modelList15': '通道检测',
      'modelList16': '方向锁定',
      'modelList19': '感应式行人过街',
      'modelList20': '方案干预',
      'modelList100': '方案干预',
      'modelList22': '优先控制',
      'modelList23': '相位关断',
      'tips': '提示',
      'exitmanul': '退出前需要先恢复自主控制, 是否退出?',
      'canceled': '已取消',
      'nextcycleeffic': '执行成功，下周期生效！',
      'transitioneffic': '执行成功，过渡切换后生效！',
      'controlnumerrormess': '控制方式为自定义时，控制方式编号不能为空！',
      'delay': '延迟时间',
      'duration': '持续时间',
      'curModel': '控制方式',
      'curStage': '当前阶段',
      'responseTime': '请求耗时',
      'putTscControlError1': '不支持的控制方式',
      'putTscControlError2': '不存在的方案编号',
      'putTscControlError3': '不支持的控制参数',
      'putTscControlError4': '设备初始化中，无法执行',
      'putTscControlError5': '优先级更高的方案控制中，无法执行',
      'putTscControlError6': '优先级更高的用户控制中，无法执行',
      'putTscControlError7': '方案编号对应的方案不存在',
      'putTscControlError8': '配置包含绿冲突',
      'nofault': '无',
      'extendedContent': '扩展内容',
      'JSONFormatError': 'JSON格式错误!',
      'showFault': '显示故障详情',
      'hideFault': '隐藏故障详情',
      'close': '关闭',
      'closemode': '方式',
      'closeOption0': '恢复',
      'closeOption1': '关断',
      'closeOption2': '仅机动车关断',
      'closeOption3': '仅行人关断',
      'vehicle': '机动车',
      'pedestrian': '行人',
      'confirmed': '已确认',
      'ignored': '已忽略',
      'untreated': '未处理',
      'item': '条'
    },
    'phase': {
      'desc': '车道方向',
      'peddesc': '行人方向',
      'controltype': '控制类型',
      'mingreen': '最小绿',
      'max1': '最大绿1',
      'max2': '最大绿2',
      'passage': '延长绿',
      'phasewalk': '行人过街',
      'pedclear': '行人清空',
      'yellow': '黄灯',
      'redclear': '红灯清空',
      'greenclear': '绿闪',
      'ring': '环',
      'greenpulse': '绿脉冲倒计时',
      'redpulse': '红脉冲倒计时',
      'concurrent': '并发相位',
      'lane': '链路',
      'operation': '操作',
      'deletetip': '确认删除此相位？',
      'deleteAlltip': '确认删除所有相位、方案？',
      'mostdata': '最多只能创建40条数据！',
      'es': '东直行',
      'el': '东左转',
      'er': '东右转',
      'eb': '东掉头',
      'ws': '西直行',
      'wl': '西左转',
      'wr': '西右转',
      'wb': '西掉头',
      'ns': '北直行',
      'nl': '北左转',
      'nr': '北右转',
      'nb': '北掉头',
      'ss': '南直行',
      'sl': '南左转',
      'sr': '南右转',
      'sb': '南掉头',
      'eped': '东人行横道',
      'wped': '西人行横道',
      'sped': '南人行横道',
      'nped': '北人行横道',
      'etped': '东人行横道-上',
      'ebped': '东人行横道-下',
      'wtped': '西人行横道-上',
      'wbped': '西人行横道-下',
      'slped': '南人行横道-左',
      'srped': '南人行横道-右',
      'nlped': '北人行横道-左',
      'nrped': '北人行横道-右',
      'xrped': 'X人行横道-/',
      'xlped': 'X人行横道-\\',
      'ewped': '东西路段人行横道',
      'snped': '南北路段人行横道',
      'redyellow': '红黄',
      'vehiclethresh': '车辆排队阈值',
      'pedestrianthresh': '行人等待阈值',
      'mainroad': '机动车主路',
      'bypass': '机动车支路',
      'pedestrianonly': '行人',
      'busonly': '公交专用',
      'BRTonly': 'BRT专用',
      'tramonly': '有轨电车专用',
      'nonvehiclesonly': '非机动车专用',
      'virtualphase': '虚相位',
      'pulsetype': '脉冲屏蔽',
      'offpulse': '关闭行人及机动车脉冲',
      'sendvehiclepulse': '发送机动车脉冲',
      'sendpedestrianpulse': '发送行人脉冲',
      'sendpedestriansvehiclepulse': '发送行人及机动车脉冲'
    },
    'overlap': {
      'desc': '描述',
      'includedPhases': '母相位',
      'lane': '车道',
      'operation': '操作',
      'deletetip': '确认删除此跟随相位？',
      'mostdata': '最多只能创建16条数据!'
    },
    'pattern': {
      'property': '属性',
      'desc': '描述',
      'offset': '相位差',
      'cycle': '周期',
      'operation': '操作',
      'deletetip': '确认删除此方案？',
      'firstphase': '请先创建相位!',
      'mostdata': '最多只能创建32条数据!',
      'ring': '环',
      'modeOption1': '其他',
      'modeOption2': '无',
      'modeOption3': '最小绿请求',
      'modeOption4': '最大绿请求',
      'modeOption5': '行人请求',
      'modeOption6': '最大绿和行人请求',
      'modeOption7': '忽略相位',
      'coordphaseOption1': '协调相位',
      'coordphaseOption2': '关键相位',
      'coordphaseOption4': '固定相位',
      'forbiddenstage': '相位阶段禁止',
      'screenstage': '相位阶段屏蔽',
      'coordinatestage': '协调相位阶段 ',
      'delaystart': '晚启动时间 ',
      'advanceend': '早结束时间',
      'stageConfig': '阶段配置',
      'parameters': '扩展参数配置',
      'plan': '方案',
      'ringConfig': '环配置',
      'stage': '阶段',
      'phase': '相位',
      'agentidError': '获取设备ID失败!',
      'splitCheckMsg': '绿信比需大于相位过渡时间或绿信比需大于行人过街+行人过渡时间!',
      'pattern': '方案'
    },
    'plan': {
      'tipcontext': '请输入计划名！',
      'editcontext': '请编辑计划名！',
      'plannamerequired': '必须填写计划名！',
      'plannamerepeated': '计划名不能重复！',
      'mostplandata': '最多只能创建16条数据!',
      'tip': '提示',
      'ok': '确定',
      'cancel': '取消',
      'desc': '描述',
      'time': '时间',
      'pattern': '方案',
      'operation': '操作',
      'deletetip': '确认删除此计划？',
      'tabtip': '确认删除此标签？',
      'mostdata': '最多只能创建48条数据!',
      'editname': '编辑计划名',
      'controltype': '控制方式',
      'ControlOption1': '黄闪',
      'ControlOption2': '全红',
      'ControlOption3': '关灯',
      'ControlOption4': '步进',
      'ControlOption5': '定周期控制',
      'ControlOption6': '单点感应控制',
      'ControlOption9': '自适应控制',
      'ControlOption10': '无电缆控制',
      'ControlOption12': '行人过街控制',
      'ControlOption19': '感应式行人过街控制',
      'plan': '计划'
    },
    'date': {
      'desc': '描述',
      'month': '月份',
      'day': '周',
      'date': '日期',
      'plan': '计划',
      'operation': '操作',
      'deletetip': '确认删除此日期？',
      'mostdata': '最多只能创建40条数据！'
    },
    'channel': {
      'desc': '信号灯位置',
      'controlsource': '控制源',
      'controlType': '控制类型/源',
      'voltthresh': '灯电压检测阈值',
      'pacthresh': '灯功率检测阈值',
      'peakhthresh': '高功率故障阈值',
      'peaklthresh': '低功率故障阈值',
      'operation': '操作',
      'other': '其他',
      'phaseVehicle': '机动车相位',
      'phasePedestrian': '行人相位',
      'overlap': '跟随相位',
      'deletetip': '确认删除此通道？',
      'deleteAlltip': '确认删除所有通道？',
      'mostdata': '最多只能创建40条数据！',
      'firstoverlapl': '请先创建跟随相位!'
    },
    'detector': {
      'desc': '描述',
      'type': '类型',
      'typeOptionundefined': '',
      'typeOption': 'typeOption',
      'typeOption0': '线圈',
      'typeOption1': '智慧路口终端',
      'callPhase': '请求相位',
      'noactivity': '无响应时间',
      'maxpresence': '最大持续时间',
      'erraticCounts': '最大车辆数',
      'failtime': '失败时间',
      'enable': '使能',
      'mode': '模式',
      'presenttime': '存在时间(秒)',
      'sense': '灵敏度',
      'sampletime': '采样时间(微秒)',
      'inthresh': '进入阈值',
      'outthresh': '离开阈值',
      'infilter': '进入滤波',
      'outfilter': '离开滤波 ',
      'saturationflow': '饱和流量',
      'operation': '操作',
      'deletetip': '确认删除此检测器？',
      'deleteAlltip': '确认删除所有检测器？',
      'mostdata': '最多只能创建64条数据！',
      'mostclonedata': '最多只能克隆72条数据！'
    },
    'channellock': {
      'deleteAlltip': '确认删除所有通道锁定？',
      'deleteOnetip': '确认删除此通道锁定？',
      'starttime': '开始时间',
      'endtime': '结束时间',
      'transitiongreenflashtime': '过渡绿闪时间',
      'transitionyellowtime': '过渡黄灯时间',
      'channelstats': '通道状态',
      'lockinperiod': '锁定时段',
      'channel': '通道',
      'channelinfo': '通道信息',
      'status': '状态',
      'status1': '默认',
      'status2': '红灯',
      'status3': '黄灯',
      'status4': '绿灯',
      'status5': '绿闪',
      'status6': '灭灯',
      'status7': '红闪',
      'maxlength': '最多添加24个通道锁定记录!'
    },
    'channelControl': {
      'recoverysuccess': '恢复成功！',
      'recoveryfailed': '恢复失败！',
      'testsuccess': '检测成功!',
      'testfailed': '检测失败!',
      'channel': '通道',
      'redresidualvoltage': '红灯残留电压',
      'redoutputvoltage': '红灯输出电压',
      'redoffresidualpower': '红灯关闭的残留功率',
      'redonoutputpower': '红灯亮灯的输出功率',
      'yellowresidualvoltage': '黄灯残留电压',
      'yellowoutputvoltage': '黄灯输出电压',
      'yellowoffresidualpower': '黄灯关闭的残留功率',
      'yellowonoutputpower': '黄灯亮灯的输出功率',
      'greenresidualvoltage': '绿灯残留电压',
      'greenoutputvoltage': '绿灯输出电压',
      'greenoffresidualpower': '绿灯关闭的残留功率',
      'greenonoutputpower': '绿灯亮灯的输出功率',
      'inputvoltage': '通道输入电压',
      'implement': '执行',
      'implementsuccess': '执行成功！',
      'noClickedLamp': '未选择通道灯端子！'
    },
    'pedestrianDetector': {
      'desc': '描述',
      'callPhase': '请求源',
      'noactivity': '无响应时间(分钟)',
      'maxpresence': '最大持续时间(分钟)',
      'erraticCounts': '最大请求数（个/分钟）',
      'operation': '操作',
      'deletetip': '确认删除此行人检测器？',
      'mostdata': '最多只能创建8条数据！',
      'type': '检测器类型',
      'typeOptionundefined': '',
      'typeOption': 'typeOption',
      'typeOption0': '线圈',
      'typeOption1': '雷达',
      'typeOption2': '视频',
      'region': '检测器区域',
      'regionOptionundefined': '',
      'regionOption': 'regionOption',
      'regionOption0': '默认',
      'regionOption1': '等待区域',
      'regionOption2': '过街区域'
    },
    'deviceinfo': {
      'deviceinfo': '设备信息: ',
      'addresscode': '地址码: ',
      'areaid': '区域ID: ',
      'devid': '设备ID: ',
      'devname': '设备名称',
      'selflearning': '自学习:',
      'deviceparam': '设备参数: ',
      'startyellowflashtime': '启动黄闪时间(秒): ',
      'startredtime': '启动全红时间(秒): ',
      'greenwavetransitionperiod': '绿波过渡周期(秒): ',
      'ip1': 'IP1',
      'ip': 'IP: ',
      'subnetmask': '子网掩码: ',
      'gateway': '网关: ',
      'ip2': 'IP2',
      'centercommunicationip': '中心通讯IP',
      'port': '端口: ',
      'cascade': '级联',
      'lampcontrolpanel': '主机灯控板数量: ',
      'boardsformainengine': '主机车检板数量: ',
      'ioboards': '主机IO板数量: ',
      'slavecascadeoffset': '从机级联偏移量: ',
      'deviceinfostyle': 'margin-left: 50px; top: 10px;',
      'deviceparamstyle': 'margin-left: 50px; top: 10px;',
      'deviceselflearningstyle': 'margin-left: 50px; top: 15px;',
      'faultdetect': '故障检测参数',
      'closegreenandredon': '红绿同亮检测: ',
      'closenoredon': '无红灯亮起检测: ',
      'detectgapgreenandredon': '红绿同亮检测时间间隔(0.1秒):',
      'detectgapnoredon': '无红灯亮起检测时间间隔(0.1秒):',
      'detectgapgreenconflict': '绿灯冲突检测时间间隔(0.1秒): ',
      'startOrderParams': '启动时序参数 ',
      'netcardInfo': '网络信息: ',
      'stepType': '步进类型',
      'step0': '阶段',
      'step1': '色步',
      'seeCutEffect': '查看剪断效果',
      'siteIdLimit': '地址码规定范围是 0 ~ 99999',
      'siteIdCutEffect': '地址码剪断效果',
      'cutData': '剪断数据：',
      'cutEffect': '剪断效果：',
      'longgerThan20': '二进制数据超过20位！',
      'timezonehour': '时区 / 小时',
      'timezoneminute': '时区 / 分钟'
    },
    'singleoptim': {
      'deletetip': '确认删除？',
      'selfControlParams': '自适应控制参数',
      'lossTimeOfGreenBeginning': '绿初损失时间（秒）',
      'lossTimeOfYellowEnding': '黄末损失时间（秒）',
      'saturationFlow': '饱和流量（辆/小时）',
      'cycleAdjustmentFactor': '周期调整因子',
      'selfLearningModeOnOrOff': '自学习模式',
      'staticWeight': '静态权重',
      'phaseStaticFactor': '相位静态因子',
      'createPatternFirst': '请先创建方案',
      'gratterThanHundred': '数值之和不能大于100'
    },
    'controlpanel': {
      'controlmode': '控制模式',
      'duration': '绿灯持续时间',
      'transitiongreenflashtime': '过渡绿闪时间:',
      'transitionyellowtime': '过渡黄灯时间:',
      'transitionredtime': '过渡全红时间',
      'mingreen': '最小绿时间',
      'channel': '通道',
      'channeldesc': '通道描述',
      'status': '状态',
      'step': '步进',
      'allred': '全红',
      'yellowflash': '黄闪',
      'defaultsettings': '生成默认设置',
      'allclear': '全部清除',
      'clear': '清空',
      'execute': '锁定当前方向',
      'resetstatus': '状态重置',
      'manualbtn': 'manualbtn',
      'manualbtn1': '东西直行',
      'manualbtn2': '北向通行',
      'manualbtn3': '东西左转',
      'manualbtn4': '西向通行',
      'manualbtn5': '东向通行',
      'manualbtn6': '南北直行',
      'manualbtn7': '南向通行',
      'manualbtn8': '南北左转',
      'channelstatus': 'channelstatus',
      'channelstatus1': '红灯',
      'channelstatus3': '绿灯',
      'channelstatus4': '灭灯',
      'getdefaultmanualpaneltips': '生成默认参数成功，请查看！',
      'phaseanddetectornotnull': '相位和通道不能为空！',
      'iscleardata': '是否清空数据?',
      'tips': '提示',
      'cancelclear': '已取消清空',
      'changered': '是否将状态全部初始化为红灯?',
      'cancelinit': '已取消初始化',
      'manualpanelundefined': '',
      'manualpanel0': '默认',
      'manualpanel1': '自定义',
      'manualcontrolbackground': 'manualcontrolbackground',
      'lockconfigempty': '通道锁定信息不能为空！'
    },
    'system': {
      'information': '信息',
      'remotecontrol': '远程控制',
      'channeldetection': '通道检测',
      'signalversion': '信号机版本',
      'softwareversion': '软件版本',
      'hardwareversion': '硬件版本',
      'currentsystemtime': '当前系统时间',
      'time': '时间',
      'featureparameterversion': '特征参数版本',
      'version': '版本号',
      'identificationcode': '识别码',
      'equipmentidentificationcode': '设备识别码',
      'remotedebugging': '远程调试',
      'openornot': '是否开启',
      'automaticshutdown': '自动关闭',
      'systemupdate': '系统更新',
      'dataupdate': '数据更新',
      'reset': '设置',
      'sure': '确定',
      'oper': '操作',
      'selectfile': '选择文件',
      'noselectfile': '未选择更新文件！',
      'updateUSB': '更新U盘数据',
      'recovery': '恢复',
      'lampcontrolpanel': '灯控板',
      'featureparameterversionsetting': '特征参数版本设置',
      'deviceidentificationcodesetting': '设备识别码设置',
      'baudrate': '波特率',
      'databit': '数据位',
      'stopbit': '停止位',
      'paritybit': '奇偶校验位',
      'signalidentificationcode': '信号机识别码',
      'modifysystemtime': '修改系统时间',
      'selectdate': '选择日期',
      'anytimepoint': '任意时间点',
      'tips': '提示',
      'update': '更新',
      'duration1': '5分钟',
      'duration2': '60分钟',
      'duration3': '无限制',
      'remote1': '重启',
      'remote2': '授权',
      'remote3': '自定义',
      'test': '检测',
      'realTimeChannel': '实时通道',
      'isReboot': '系统文件下载成功,更新重启后生效,是否重启设备？',
      'rebootSuccess': '重启成功！',
      'rebootFaile': '重启失败！',
      'resetSuccess': '设置成功！',
      'filesuccess': '升级文件传输完成!',
      'filefailed': '升级接口调用失败!',
      'updateovertime': '更新超时！'
    },
    'optimize': {
      'strategy': '优化策略',
      'learn': '自学习',
      'saturation': '路口统计参数(采样周期为5分钟):',
      'phase': '相位',
      'flow': '饱和流量(辆)',
      'queue': '饱和排队长度(辆)',
      'speed': '最大速度(千米/小时))',
      'Occupancy': '饱和占有率',
      'mathematical': '数学模型'
    },
    'history': {
      'description': '描述',
      'user': '用户',
      'updatetime': '更新时间',
      'operation': '操作',
      'deletetip': '确认删除此历史记录？'
    },
    'fault': {
      'refresh': '刷新',
      'uploadfault': '上载故障',
      'export': '导出',
      'faultofcurrentdetailedlightgroup': '当前详细灯组故障',
      'faultofcurrentdetailedvehicleinspectionversion': '当前详细车检版故障',
      'tab': 'tab',
      'tab0': '总览',
      'tab1': '主控板',
      'tab2': '灯控板',
      'tab3': '车检板',
      'tab4': 'I/O板',
      'tab5': '故障板',
      'tab6': '特征参数',
      'maintype': '主类型',
      'subtype': '子类型',
      'occurtime': '发生时间',
      'recoverytime': '恢复时间',
      'grade': '等级',
      'value': '值'
    },
    'statistics': {
      'username': '用户名：',
      'pass': '密码：',
      'usernameplaceholder': '请输入用户名',
      'passplaceholder': '请输入密码',
      'getdevicedata': '上载流量',
      'detector': '检测器',
      'time': '时间',
      'flow': '流量',
      'occupyrate': '占有率',
      'occupytime': '占有时间',
      'userpassnotnull': '用户名或密码不能为空!',
      'historicalisempty': '获取历史流量数据为空！'
    },
    'dailyrecord': {
      'data': '日期',
      'infotype': '事件类型',
      'subject': '主体标时',
      'object': '客体标时',
      'operationresults': '操作结果',
      'uploaddailyrecord': '上载日志',
      'success': '成功',
      'fault': '失败',
      'platform': '平台',
      'configurationsoftware': '配置软件',
      'getmanualpanel': '获取手动面板',
      'signal': '信号机',
      'localpanelcontrol': '本地面板控制',
      'systemmanualcontrol': '系统手动控制',
      'systemdownloadcharacteristic': '系统下载特征参数',
      'systemdownload': '系统下载设备参数',
      'systemuploadparameters': '系统上载参数',
      'systemrestartsignal': '系统重启信号机',
      'systemdownloadplan': '系统下载方案',
      'systemuploadloadplan': '系统上载方案',
      'systemdownloadschedulingplan': '系统下载调度计划',
      'systemuploadloadschedulingplan': '系统上载调度计划',
      'systemdownloaddate': '系统下载日期',
      'systemuploadloaddate': '系统上载日期',
      'systemchanneldetection': '系统通道检测',
      'systemsolutionintervention': '系统方案干预',
      'systemsetuptime': '系统设置时间'
    },
    'control': {
      'mode': '模式:',
      'control': '控制:',
      'cycle': '周期:',
      'offset': '相位差:',
      'curTime': '当前时间:',
      'syncTime': '剩余时间:',
      'score': '分数:',
      'level': '等级:',
      'patternid': '方案编号:',
      'name': '名称:',
      'current_phase': '当前相位:',
      'phase': '相位',
      'ring': '环',
      'control_style': '控制方式',
      'mode_style': '控制模式',
      'pattern': '控制编号',
      'phase_countdown': '相位倒计时:',
      'split': '绿信比:',
      'countdown': '倒计时:',
      'id': '相位id:',
      'type': '相位类型:',
      'light_countdown': '通道倒计时:',
      'sequence': '相序:',
      'num': '序号：',
      'value': '控制参数',
      'currentvolume': '实时流量',
      'time': '时间'
    },
    'button': {
      'OK': '确定',
      'Cancel': '取消',
      'Back': '返回',
      'ignore': '忽略',
      'confirm': '确认'
    },
    'common': {
      'confirm': '确认',
      'query': '查询',
      'cancel': '取消',
      'add': '添加',
      'delete': '删除',
      'clone': '克隆',
      'deleteall': '全部删除',
      'update': '修改',
      'search': '搜索',
      'alarm': '警告',
      'none': '无',
      'weak': '弱',
      'normal': '普通',
      'strong': '强',
      'name': '名 称',
      'path': '路 径',
      'description': '描 述',
      'operations': '操 作',
      'addfailed': '添加失败！',
      'addsucess': '添加成功！',
      'addcancel': '添加取消！',
      'deletefailed': '删除失败！',
      'deletesucess': '删除成功！',
      'deletecancel': '删除取消！',
      'updatefailed': '更新失败！',
      'updatesucess': '更新成功！',
      'updatecancel': '更新取消！',
      'relatefailed': '关联失败！',
      'relatesucess': '关联成功！',
      'relatecancel': '关联取消！',
      'upload': '上传',
      'select': '请选择',
      'input': '请输入',
      'entercontent': '请输入内容',
      'commit': '提交',
      'commitandexecute': '提交并执行',
      'uploadsuccess': '参数上传成功!',
      'download': '参数下发成功!',
      'downloaderror': '信号机校验失败！',
      'downloadandrun': '提交并运行成功！',
      'editagentid': '编辑设备ID',
      'querysucess': '查询成功！',
      'setup': '设置',
      'tipsmodaltitle': '提示',
      'deviceoffline': '设备不在线！',
      'operationsuccess': '操作成功'
    },
    'login': {
      'title': '信号机设备配置',
      'login': '登录',
      'authorize': '授权',
      'close': '关闭',
      'dialogtitle': '应用授权',
      'username': '用户名',
      'password': '密码',
      'networkparam': '通讯参数',
      'port': '端口',
      'protocol': '协议',
      'restoredefaultparameters': '恢复默认参数',
      'enterip': '请输入IP',
      'enterlegalip': '请输入合法的IP！',
      'enterpport': '请输入端口',
      'entercorrectport': '请输入正确的端口！',
      'usernamenotnull': '用户名不能为空！',
      'passnotnull': '密码不能为空！',
      'selectagreement': '请选择协议',
      'enterdeviceid': '请输入设备ID',
      'loginerror': '登陆错误!'
    },
    'errorTip': {
      'phaseNotZero': '必须含有至少一个相位!',
      'phaseExceed': '一个环最大不能超过16相位!',
      'planNotZero': '必须含有至少一个计划!',
      'patternNotZero': '必须含有至少一个方案!',
      'dataNotZero': '必须含有至少一个日期!',
      'dateIsAll': '日期必须是全年!',
      'planDate': '计划中的时间是不合理的!',
      'patternRing': '相同方案的环时间必须相等!',
      'dataPlan': '日期里的计划不能为空!',
      'planPattern': '计划里的方案不能为空!',
      'overlapRules': '跟随相位里的母相位不能为空！',
      'manualpanel': '手动面板数据不能为空',
      'momthIsNull': '日期里月份不能为空！',
      'concurrentRules': '相位参数中配置了多环时，并发相位不能为空！',
      'devicenotonline': '设备不在线！',
      'abnormalcommunication': '通讯异常！',
      'saveParamFailed': '信号机保存参数失败!',
      'ringErrorTip': '环配置应从环1起配，不允出现跳环配置!',
      'deviceinformationnotnull': '设备信息中的地址码不能为空！',
      'noSchemeUpload': '暂没有可上载方案！',
      'readSchemeSucc': '读取方案成功！',
      'singleDownloadNotZero': '下载数据不能为空!'
    },
    'importTemp': {
      'editTemplateData': '模板数据编辑',
      'template': '模板',
      'crossShape': '路口形状',
      'name': '名称',
      'phaseCount': '相位数量',
      'phase': '相位',
      'greenClear': '绿闪',
      'yellow': '黄灯',
      'redClear': '红灯清空',
      'pattern': '方案',
      'count': '数量',
      'split': '绿信比',
      'plan': '计划',
      'date': '日期',
      'annual': '全年',
      'crossShapes': {
        'crossroads': '十字路口',
        'tjunction': '丁字路口',
        'roundabout': '环形路口',
        'rampintersection': '匝道路口',
        'pedestriancrossing': '行人过街路口',
        'otherIntersections': '其他形状路口',
        'customIntersection': '自定义路口'
      },
      'crossIntersection': {
        'eastward': '东向',
        'westward': '西向',
        'southward': '南向',
        'northward': '北向',
        'eastwestward': '东西向',
        'southnorthward': '南北向'
      },
      'crossPhase': {
        'onePhase': '一相位',
        'twoPhase': '二相位',
        'threePhase': '三相位',
        'fourPhase': '四相位',
        'fivePhase': '五相位',
        'sixPhase': '六相位',
        'sevenPhase': '七相位',
        'eightPhase': '八相位',
        'ninePhase': '九相位',
        'tenPhase': '十相位',
        'elevenPhase': '十一相位',
        'twelvePhase': '十二相位',
        'thirteenPhase': '十三相位',
        'fourteenPhase': '十四相位',
        'fifteenPhase': '十五相位',
        'sixteenPhase': '十六相位'
      }
    },
    'about': {
      'version': '版本',
      'copyright': '版权所有',
      'company': '苏州科达科技股份有限公司',
      'packedtime': '打包时间',
      'opensourceagreement': '开源协议'
    },
    'message': {
      '10000': '参数为空',
      '10001': '参数长度错误',
      '1002': '错误',
      '10002': '记录不能为空',
      '20003': 'id不能为空且不能为0',
      '20004': '无参数记录',
      '20005': '没有发现设备id',
      '20006': '数据流关闭失败!',
      '20007': 'Json类型转换失败!',
      '20008': '文件读取失败!',
      '20009': '文件不存在!',
      '20010': 'I/O关闭错误!',
      // 全局异常错误
      '0001': '空指针',
      '0002': '错误请求方式',
      '0003': '输入参数类型不正确',
      '0004': '输入参数不全',
      '0005': '输入参数不满足约束',
      '0006': '系统错误',
      // 用户管理错误
      '3001': '用户名不存在',
      '3002': '认证失败',
      '3003': '用户名重复',
      '3004': '新旧密码不能相同',
      '3005': '密码不能为空',
      '3006': '原密码错误',
      '3007': '登录用户不存在',
      '3008': '没有权限访问',
      '3009': '密码错误',
      '3010': '超级用户角色不能被修改',
      '3011': '用户名密码错误',
      '3013': '账号已停用',
      '3014': 'token已过期',
      '3015': 'token失效',
      '3016': '用户组织机构为空',
      '3017': '组织机构不存在',
      '3018': '访问ip与用户ip不一致，禁止登录',
      // 消息通讯错误
      '4001': '错误请求',
      '4002': '错误应答',
      '4003': '设备不在线',
      '4004': '重复的设备id',
      '4005': '信号机应答为空',
      '4006': '信号机应答操作类型为空',
      // 错误子类型
      // error-request
      '4100': 'agentid为空',
      '4101': '操作类型为空!',
      '4102': '消息类型为空!',
      '4103': '消息数据为空!',
      '4104': 'JSON格式错误!',
      '4105': 'agentid不存在!',
      '4106': '未知的操作类型!',
      '4107': '未知的消息类型!',
      '4108': '协议为空!',
      '4109': '接收数据超时或收到错误数据!',
      // error-response
      '4200': '应答为空!',
      '4201': '应答消息转换错误!',
      '4203': '未知的请求指示!',
      '4204': 'Comm数据发送错误!',
      '4205': 'Udp接收消息类型错误',
      '4206': '从接收信息中没找到UdpCommunication',
      '4207': '下载异常导致应答错误',
      // device not online
      '4301': '设备不在线',
      // 协调路线错误
      '5001': '重复的协调路线名称',
      // 勤务路线错误
      '6001': '重复的勤务路线名称',
      '6002': '勤务路线正在执行中',
      '6003': '特勤路线无法取消! 方案已经改变.',
      // 流量错误
      '7001': 'ftp客户端连接失败',
      // 组织机构错误
      '11001': '名称重复!',
      // 路口管理错误
      '8001': '通过路口ID未找到路口!',
      '8002': '路口位于协调路线上',
      '8003': '路口位于特勤路线上',
      '8004': '路口ID重复',
      // 错误类型子类型，返回码第二层
      '8101': '路口不允许删除!',
      // 瓶颈控制错误
      '9001': '瓶颈控制执行失败!',
      '9002': '瓶颈控制恢复失败!',
      '9003': '正在执行瓶颈控制，无法删除!',
      '9004': '瓶颈控制方案名称已存在!',
      '9005': '瓶颈控制方案为空！',
      '9006': '区域控制未执行!'
    },
    'faultrecord': {
      'faultDetail': '故障信息',
      'faultMaintype': '故障主类型',
      'faultSubtype': '故障子类型',
      'faultOccurtime': '发生时间',
      'boardCardType': '板卡类型',
      'faultGrade': '故障等级',
      'faultValue': '故障值',
      'asc': '信号机',
      'patterncalc': '方案检测服务',
      'monitor': '监控服务',
      'statusfault': '设备故障',
      'untreated': '未处理',
      'ignored': '已忽略',
      'confirmed': '已确认',
      'powerup': '功率异常增加',
      'powerdown': '功率异常减少',
      'powerno': '功率无输出',
      'powerfault': '关闭状态有功率输出',
      'general': '一般故障',
      'degradation': '降级故障',
      'serious': '严重故障',
      'eportingmodule': '上报模块',
      'roadname': '路口名称',
      'realtimealert': '实时告警',
      'neglect': '忽略',
      'confirm': '确认',
      'faultlist': '告警中心',
      'eventtype': '事件类型',
      'detaileddescription': '详细描述',
      'maincontrolboard': '主控板',
      'lightcontrolversion': '灯控版',
      'carinspectionboard': '车检板',
      'ioboard': 'I/O板',
      'faultboard': '故障板',
      'characteristicparams': '特征参数',
      'maincontrolboardfault': '主控板故障',
      'lightcontrolversionfault': '灯控版故障',
      'carinspectionboardfault': '车检板故障',
      'ioboardfault': 'I/O板故障',
      'fixdetail': '故障描述',
      'deviceid': '设备ID',
      'faultid': '故障ID',
      'boardcardtype': '板卡类型',
      'faultbegintime': '故障发生时间',
      'faultendtime': '故障恢复时间',
      'faulttype': '故障主类型',
      'faultchild': '故障子类型',
      'faultgrade': '故障等级',
      'faultvaluedetail': '故障详细值',
      'operator': '确认者',
      'operationTime': '确认时间',
      'enumerate': '故障状态',
      'operation': '操作',
      'realtimefault': '实时故障',
      'historicalfault': '历史故障',
      'boardtype': '板卡类型',
      'mainfaulttype': '故障主类型',
      'confirmresults': '确认结果',
      'intersectionname': '路口名称',
      'faulttime': '故障发生时间',
      'isdelfaultrecord': '是否删除此条故障记录？',
      'channel': '通道',
      'detector': '检测器',
      'port': '端口'
    }
  }
}

export default zh
