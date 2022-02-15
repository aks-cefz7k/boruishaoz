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
      'import': '导入文件',
      'export': '导出文件',
      'importtemplate': '从模板导入',
      'copy': '复制参数',
      'copyDevice': '设备',
      'copyPattern': '的方案已被复制',
      'tip': '提示',
      'read': '读取',
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
      'exit': '退出',
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
      'options10': '设备信息'
    },
    'overview': {
      'crossinfo': '路口信息',
      'crossname': '路口名称',
      'divicestate': '设备状态',
      'protocoltype': '协议类型',
      'signalID': '信号机ID',
      'signalIP': '信号机IP',
      'online': '在线',
      'offline': '离线',
      'onlineing': '联机中...',
      'controlmode': '控制方式',
      'manual': '手动',
      'exitmanual': '退出手动',
      'controlmodel': '控制模式',
      'patternname': '方案名称',
      'controlnumber': '控制编号',
      'mode': '方式',
      'stage': '阶段（驻留）',
      'implement': '执行',
      'ipaddress': 'IP地址',
      'deviceport': '设备端口',
      'agreement': '协议',
      'currentstage': '当前阶段',
      'allstagesnum': '总阶段数',
      'patternnum': '方案编号',
      'details': '详情',
      'autocontrol': '自主控制',
      'yellowflash': '黄闪',
      'allred': '全红',
      'step': '步进',
      'fixedperiod': '定周期',
      'inductioncontrol': '感应控制',
      'nocablecoordination': '无电缆协调',
      'phasewalk': '行人过街',
      'websteroptimization': 'Webster单点优化',
      'selfadaption': '自适应控制',
      'custom': '自定义',
      'controlmodevalue': '控制方式数值',
      'patternstate': '方案状态',
      'cycle': '周期',
      'phasesplit': '绿信比',
      'phase': '相位',
      'phasedifference': '相位差',
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
      'modelList0': '自主控制',
      'modelList1': '黄闪',
      'modelList2': '全红',
      'modelList3': '关灯',
      'modelList4': '步进',
      'modelList5': '定周期',
      'modelList6': '感应控制',
      'modelList9': '自适应控制',
      'modelList10': '无电缆协调',
      'modelList12': '行人过街',
      'tips': '提示',
      'exitmanul': '退出前需要先恢复自主控制, 是否退出?',
      'canceled': '已取消',
      'nextcycleeffic': '执行成功，下周期生效！',
      'transitioneffic': '执行成功，过渡切换后生效！',
      'controlnumerrormess': '控制方式为自定义时，控制方式编号不能为空！'
    },
    'phase': {
      'desc': '描述',
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
      'mostdata': '最多只能创建16条数据！',
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
      'redyellow': '红黄'
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
      'desc': '描述',
      'offset': '相位差',
      'cycle': '周期',
      'operation': '操作',
      'deletetip': '确认删除此方案？',
      'firstphase': '请先创建相位!',
      'mostdata': '最多只能创建108条数据!',
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
      'coordphaseOption4': '固定相位'
    },
    'plan': {
      'tipcontext': '请输入计划名！',
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
      'controltype': '控制方式',
      'ControlOption1': '黄闪',
      'ControlOption2': '全红',
      'ControlOption3': '关灯',
      'ControlOption4': '步进',
      'ControlOption5': '定周期控制',
      'ControlOption6': '单点感应控制',
      'ControlOption10': '无电缆控制',
      'ControlOption12': '行人过街控制'
    },
    'date': {
      'desc': '描述',
      'month': '月份',
      'day': '周',
      'date': '日期',
      'plan': '计划',
      'operation': '操作',
      'deletetip': '确认删除此日期？',
      'mostdata': '最多只能创建255条数据！'
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
      'mostdata': '最多只能创建72条数据！'
    },
    'channellock': {
      'deleteAlltip': '确认删除所有通道锁定？',
      'starttime': '开始时间',
      'endtime': '结束时间',
      'transitiongreenflashtime': '过渡绿闪时间',
      'transitionyellowtime': '过渡黄灯时间',
      'channelstats': '通道状态',
      'channel': '通道',
      'channelinfo': '通道信息',
      'status': '状态'
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
      'inputvoltage': '通道输入电压'
    },
    'pedestrianDetector': {
      'desc': '描述',
      'callPhase': '请求源',
      'noactivity': '无响应时间(分钟)',
      'maxpresence': '最大持续时间(分钟)',
      'erraticCounts': '最大请求数（个/分钟）',
      'operation': '操作',
      'deletetip': '确认删除此行人检测器？',
      'mostdata': '最多只能创建8条数据！'
    },
    'deviceinfo': {
      'deviceinfo': '设备信息: ',
      'addresscode': '地址码: ',
      'areaid': '区域ID: ',
      'crossid': '路口ID: ',
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
      'deviceselflearningstyle': 'margin-left: 50px; top: 15px;'
    },
    'controlpanel': {
      'controlmode': '控制模式',
      'duration': '持续时间',
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
      'clear': '清空',
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
      'manualcontrolbackground': 'manualcontrolbackground'
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
      'test': '检测'
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
      'export': '导出',
      'faultofcurrentdetailedlightgroup': '当前详细灯组故障',
      'faultofcurrentdetailedvehicleinspectionversion': '当前详细车检版故障',
      'tab': 'tab',
      'tab0': '总览',
      'tab1': '主控板',
      'tab2': '灯控板',
      'tab3': '车检板',
      'tab4': 'I/O板',
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
      'getdevicedata': '获取设备流量数据',
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
      'operationresults': '操作结果'
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
      'Cancel': '取消'
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
      'entercontent': '请输入内容',
      'commit': '提交',
      'commitandexecute': '提交并执行',
      'uploadsuccess': '参数上传成功!',
      'download': '参数下发成功!',
      'downloadandrun': '提交并运行成功！',
      'editagentid': '编辑设备ID',
      'querysucess': '查询成功！',
      'setup': '设置',
      'tipsmodaltitle': '提示',
      'deviceoffline': '设备不在线！'
    },
    'login': {
      'title': '信号机设备配置',
      'login': '登录',
      'authorize': '授权',
      'close': '关闭',
      'dialogtitle': '应用授权',
      'username': '用户名',
      'password': '密码',
      'locallogin': '本地登陆',
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
      'readSchemeSucc': '读取方案成功！'
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
        'otherIntersections': '其他形状路口',
        'customIntersection': '自定义路口'
      },
      'crossIntersection': {
        'eastward': '东向',
        'westward': '西向',
        'southward': '南向',
        'northward': '北向'
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
    }
  }
}

export default zh
