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
  openatc: {
    'common': {
      'add': '新增',
      'delete': '删除',
      'edit': '编辑',
      'detail': '详情',
      'searchdeviceid': '输入设备ID搜索',
      'searchplaceholder': '输入关键字搜索',
      'addfailed': '新增失败!',
      'addsuccess': '新增成功!',
      'addcancel': '新增取消!',
      'deletefailed': '删除失败!',
      'deletesuccess': '删除成功!',
      'deletecancel': '删除取消!',
      'updatefailed': '更新失败!',
      'updatesuccess': '更新成功!',
      'updatecancel': '更新取消!',
      'devicenotonline': '设备不在线！',
      'tipsmodaltitle': '提示',
      'placeholder': '请选择',
      'operation': '操作',
      'operationsuccess': '操作成功',
      'authtip': '没有权限访问 !',
      'nodata': '没有数据 !',
      'canceloperate': '操作已取消！',
      'submitsuccess': '提交成功！'
    },
    'button': {
      'OK': '确定',
      'Cancel': '取消',
      'expandall': '全部展开',
      'collapseall': '全部折叠',
      'search': '搜索',
      'location': '位置',
      'ignore': '忽略',
      'modify': '修改',
      'submit': '提交'
    },
    'login': {
      'username': '用户名',
      'password': '密码',
      'login': '登录',
      'usernamenotnull': '用户名不能为空!',
      'passnotnull': '密码不能为空！',
      'passerror': '密码错误！',
      'usernamenotexist': '用户名不存在！'
    },
    'main': {
      'homepage': '主页',
      'gis': '地图',
      'devicemanager': '设备管理',
      'usermanager': '用户管理',
      'greenwaveoptimize': '绿波协调',
      'operationrecord': '操作记录',
      'organization': '组织机构',
      'bottleneckcontrol': '瓶颈控制',
      'script': '脚本',
      'faultrecord': '故障记录',
      'dutyroute': '勤务路线',
      'realname': '真实姓名',
      'telno': '联系电话',
      'email': '电子邮箱',
      'changepass': '修改密码',
      'systemsettings': '系统设置',
      'about': '关于',
      'signout': '退出',
      'language': '语言',
      'skin': '皮肤',
      'dark': '暗色',
      'light': '亮色',
      'other': '其他',
      'help': '帮助'
    },
    'home': {
      'devicestatus': '设备状态',
      'faultlist': '故障列表',
      'online': '在线',
      'offline': '离线',
      'total': '总计',
      'faultsum': '故障总数',
      'faulttype': '故障类型',
      'quantity': '数量',
      'devicemaneger': '设备管理',
      'usermanager': '用户管理',
      'operationrecord': '操作记录',
      'faultOrOffLine': '故障 / 在线'
    },
    'faultrecord': {
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
      'operation': '操作'
    },
    'devicemanager': {
      'devicelist': '设备列表',
      'deviceid': '设备ID',
      'devicename': '设备名称',
      'firm': '厂商',
      'platform': '平台',
      'type': '类型',
      'IP': 'IP',
      'port': '端口',
      'describe': '设备描述',
      'protocol': '协议',
      'state': '状态',
      'fault': '故障',
      'online': '在线',
      'offline': '离线',
      'lastupdatetime': '最后更新时间',
      'operation': '操作',
      'longitude': '经度',
      'latitude': '纬度',
      'layerLevel': '层级',
      'adddevice': '新增设备',
      'editdevice': '编辑设备',
      'enterIp': '请输入IP',
      'correctIp': '请填写合法的IP!',
      'enterport': '请输入端口!',
      'correctport': '请填写合法的端口!',
      'choosetype': '请选择类型',
      'chooseprotocol': '请选择协议',
      'chooseplatform': '请选择平台',
      'enterId': '请输入设备ID',
      'entername': '请输入设备名称',
      'enterrequired': '请填写必填项!',
      'deletedevice': '是否删除此设备?',
      'faultDetail': '故障信息',
      'faultMaintype': '故障主类型',
      'faultSubtype': '故障子类型',
      'faultOccurtime': '发生时间',
      'boardCardType': '板卡类型',
      'faultGrade': '故障等级',
      'faultValue': '故障值',
      'crowsid': '路口ID',
      'updateDeviceId': '修改设备ID',
      'deviceNotNull': '设备ID不能为空！',
      'oldIdNotConsistentNewId': '新设备id不能与老设备id一致!',
      'thirdplatformid': '第三方ID',
      'timeSpaceGraph': '实时时距图',
      'keyintersection': '关键路口：',
      'sockettype': '通讯类型'
    },
    'usermanager': {
      'username': '用户名称',
      'organization': '组织机构',
      'realname': '真实姓名',
      'role': '角色',
      'telno': '电话',
      'email': '邮箱',
      'operation': '操作',
      'password': '密码',
      'confirmpassword': '确认密码',
      'newpassword': '新密码',
      'edituser': '编辑用户',
      'adduser': '添加用户',
      'deleteuser': '是否删除此用户?',
      'labelwidth': '15%',
      'authorize': '授权',
      'authorizetitle': '授权token',
      'authorizetime': '授权时间：',
      'generate': '生成',
      'to': '至',
      'starttime': '开始时间',
      'endtime': '结束时间',
      'Authorization': '授权时间不能为空！',
      'enterpass': '请输入确认密码！',
      'enterpassdiffpass': '确认密码与密码不同！',
      'correctphone': '请输入正确的电话号码！',
      'correctemail': '请输入正确的电子邮箱！',
      'lettersnumbersunderscores': '用户名只能输入字母、数字或下划线！',
      'enterusername': '请输入用户名！',
      'enterpassword': '请输入密码!',
      'nameandpasswordnotempty': '用户名密码不能为空！',
      'wrongphoneemail': '错误的电话号码或者电子邮箱！',
      'selectonerole': '请至少选择一个角色!',
      'pleaseenter': '请输入',
      'newpassdiffoldpass': '新密码不能与旧密码一致！',
      'nochanges': '没有任何修改！',
      'superrolenotmodified': '超级管理员角色不能被修改！',
      'editsucc': '编辑成功！',
      'oldpass': '旧密码',
      'confirmpass': '确认新密码',
      'enputpassagain': '请再次输入密码!',
      'twopasswordsdiff': '两次输入密码不一致!',
      'enteroldpass': '请输入旧密码！',
      'enternewepass': '请输入新密码',
      'originalpassworderror': '原密码错误！',
      'modificationsucc': '修改成功',
      'ip': 'IP'
    },
    'greenwaveoptimize': {
      'addplan': '点击添加按钮添加绿波协调计划',
      'createconfig': '点击编辑按钮开始计划生成配置',
      'operation': '操作',
      'isdelete': '是否删除',
      'nochooseplan': '未选中任何方案！',
      'namerepeat': '协调方案名称重复！',
      'addfail': '新增失败，协调方案名称重复！',
      'addroute': '新增协调方案',
      'editroute': '编辑协调方案',
      'name': '名称',
      'routename': '方案名称',
      'isClose': '配置未保存，是否确认关闭?',
      'coordinateparamconfig': '协调参数配置',
      'device': '设备',
      'phase': '相位',
      'distance': '距离',
      'pattern': '方案',
      'choosephase': '请选择相位！',
      'choosepattern': '请选择方案！',
      'configsuccess': '协调参数配置成功！',
      'adddevice': '新增设备',
      'deletedevice': '是否删除设备',
      'deviceid': '设备ID',
      'devicename': '设备名称',
      'type': '类型',
      'IP': 'IP',
      'port': '端口',
      'describe': '设备描述',
      'deletedevsuccess': '删除设备成功！',
      'distancemeter': '距离（m）',
      'widthmeter': '宽度（m）',
      'forward': '上行',
      'back': '下行',
      'generate': '生成',
      'edit': '编辑',
      'confirmdownloadpattern': '确认下载此方案?',
      'offset': '相位差',
      'cycle': '周期',
      'timespace': '时距图',
      'coordinationstrategy': '协调策略',
      'coordinationdirection': '协调方向',
      'keyintersection': '关键路口',
      'upspeed': '上行速度(km/h)',
      'downspeed': '下行速度(km/h)',
      'up': '上行',
      'down': '下行',
      'optstrategyoption1': '绿波优化',
      'directionOption1': '上行',
      'directionOption2': '下行',
      'directionOption3': '双向',
      'duration': '持续时间：',
      'speed': '速度：',
      'vehicle': '车辆：',
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
      'notonline': '不在线！',
      'planissuedsucc': '方案下发成功!',
      'time': '时间（s）'
    },
    'record': {
      'user': '用户',
      'updatetime': '更新时间',
      'originadress': '源地址',
      'deviceid': '设备ID',
      'messagetype': '消息类型',
      'reponsestatus': '返回状态',
      'opera': '操作',
      'export': '导出'
    },
    'organization': {
      'viewdevice': '查看设备',
      'belongorganization': '所属组织',
      'relatedevice': '关联设备',
      'unrelateddevice': '未关联设备',
      'relateddevice': '已关联设备',
      'modifyorganization': '修改组织',
      'addorganization': '添加组织'
    },
    'dutyroute': {
      'clickaddroute': '点击添加按钮添加路线',
      'nochooseplan': '未选中任何路线！',
      'routePreview': '路线预览',
      'routeState': '路线控制',
      'dutyrouteconfig': '勤务路线配置',
      'node': '节点',
      'plan': '预案',
      'controlpattern': '控制方式',
      'lasttime': '持续时间(秒)',
      'stage': '阶段',
      'greenpercent': '绿信比',
      'getpatternfailed': '获取方案失败!',
      'ring': '环',
      'notonduty': '待执勤',
      'onduty': '执勤中',
      'attributes': '属性',
      'executionway': '执行方式',
      'residentphase': '驻留阶段',
      'timeleft': '剩余时间',
      'executenow': '立即执行',
      'cancelexecute': '取消执行',
      'autonomouscontrol': '恢复自主控制',
      'yellowflash': '黄闪',
      'allred': '全红',
      'turnofflights': '关灯',
      'stepping': '步进',
      'fixedcyclecontrol': '定周期控制',
      'singlePointInductionControl': '单点感应控制',
      'coordinatedInductionControl': '协调感应控制',
      'schemeSelectionControl': '方案选择控制',
      'adaptivecontrol': '自适应控制',
      'nocablecontrol': '无缆线控制',
      'cablecontrol': '有缆线控制',
      'pedestrianCrossingControl': '行人过街控制',
      'channellock': '通道锁定',
      'phaselock': '相位锁定',
      'programIntervention': '方案干预',
      'channelDetection': '通道检测',
      'emptyRouteName': '名称为空!',
      'namerepeat': '名称重复!',
      'notAllowedToEdit': '执勤中的路线，不允许编辑!'
    },
    'gis': {
      'chooseLocationInfo': '请点击地图选择位置 !',
      'clickToChooseLocationInfo': '点击图标去地图上选择位置 !',
      'crossRoad': '路口',
      'gisNormal': '地图',
      'gisStatellite': '影像',
      'deviceState': '设备状态',
      'dutyRoute': '特勤路线',
      'coordinateRoute': '协调路线',
      'routeLength': '路线总长度'
    },
    'systemsettings': {
      'id': 'ID',
      'module': '模块',
      'name': '名称',
      'content': '内容',
      'desc': '描述',
      'operate': '操作',
      'addconfig': '配置新增',
      'updateconfig': '配置编辑',
      'deleteconfig': '是否删除此配置？'
    },
    'bottleneckcontrol': {
      'detector': '检测器',
      'open': '开启',
      'off': '取消',
      'isoffcontrol': '是否取消控制',
      'isopencontrol': '是否开启控制',
      'opensuccess': '开启瓶颈控制成功！',
      'offsuccess': '取消瓶颈控制成功！',
      'switchsavereminder': '切换瓶颈控制列表，不会保存当前配置，确认切换么?',
      'bottlenecklist': '瓶颈控制列表',
      'detectorid': '方案ID',
      'desc': '描述',
      'type': '类型',
      'status': '状态',
      'port': '端口',
      'name': '名称',
      'deviceid': '设备ID',
      'phaseid': '相位ID',
      'controllevel': '控制等级',
      'controltype': '控制类型',
      'controltime': '相位时长（秒）',
      'choosedev': '选择设备',
      'phase': '相位',
      'one-level': '一级',
      'two-level': '二级',
      'three-level': '三级',
      'queue': '排队',
      'overflow': '溢出',
      'triggering': '触发中',
      'nottriggered': '未触发',
      'greenextension': '绿灯延长',
      'greenreduction': '绿灯缩减',
      'bottleneckinfo': '瓶颈控制信息',
      'isdeletedetector': '确认删除检测器'
    },
    'about': {
      'version': '版本',
      'copyright': '版权所有',
      'company': '苏州科达科技股份有限公司',
      'packedtime': '打包时间',
      'opensourceagreement': '开源协议'
    },
    'message': {
      // 协调路线错误
      '5001': '重复的协调路线名称',
      // 勤务路线错误
      '6001': '重复的勤务路线名称',
      '6002': '勤务路线正在执行中',
      // 流量错误
      '7001': 'ftp客户端连接失败'
    }
  }
}

export default zh
