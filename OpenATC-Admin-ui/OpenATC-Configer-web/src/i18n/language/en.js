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
const en = {
  edge: {
    'auth': {
      'current': 'Current Password',
      'new': 'New Password',
      'contain': 'Passsword should contain',
      'contain1': ' 8 or more characters.',
      'contain2': 'numbers,letters and special',
      'contain3': 'characters.',
      'confirm': 'Confirm New Password',
      'strength': 'Strength',
      'none': 'none',
      'weak': 'weak',
      'normal': 'normal',
      'strong': 'strong'
    },
    'route': {
      'dashboard': 'Dashboard',
      'overview': 'Overview',
      'edge': 'Tsc',
      'phase': 'Phase',
      'overlap': 'Overlap',
      'pattern': 'Pattern',
      'plan': 'Plan',
      'date': 'Date',
      'channel': 'Channel',
      'channelLock': 'ChannelLock',
      'detector': 'Detector',
      'pedestrianDetector': 'PedDetector',
      'system': 'System',
      'history': 'Fault',
      'optimize': 'Optimize',
      'control': 'Control',
      'deviceInfo': 'deviceInfo',
      'user': 'User',
      'dailyRecord': 'DailyRecord',
      'controlPanel': 'ControlPanel',
      'statistics': 'Statistics'
    },
    'main': {
      'main': 'Main',
      'home': 'Home',
      'logout': 'LogOut',
      'upload': 'Upload',
      'download': 'Download',
      'import': 'Import',
      'export': 'Export',
      'importtemplate': 'Import Template',
      'copy': 'Copy',
      'copyDevice': 'Device',
      'copyPattern': 'pattern already copied',
      'tip': 'Tips',
      'read': 'Read',
      'readDevice': 'Whether to load device',
      'readPattern': 'pattern into the current device configuration ?',
      'title': 'Welcome to KISS Edge Manage Center',
      'changelanguage': 'Switch Language',
      'agentid': 'AGENTID'
    },
    'phase': {
      'desc': 'Description',
      'mingreen': 'MinGreen',
      'max1': 'Max1',
      'max2': 'Max2',
      'passage': 'passage(min)',
      'phasewalk': 'phasewalk',
      'pedclear': 'PedClear',
      'yellow': 'Yellow(min)',
      'redclear': 'RedClear(min)',
      'greenclear': 'GreenClear',
      'ring': 'Ring',
      'greenpulse': 'Greenpulse',
      'redpulse': 'Redpulse',
      'concurrent': 'Concurrent',
      'lane': 'Connection',
      'operation': 'Operation',
      'add': 'Add Phase',
      'deletetip': 'Delete This Phase ?',
      'deleteAlltip': 'Delete All Phase ?',
      'mostdata': 'There are at most 16 data !',
      'es': 'East-Straight',
      'el': 'East-Left',
      'er': 'East-Right',
      'eb': 'East-Back',
      'ws': 'West-Straight',
      'wl': 'West-Left',
      'wr': 'West-Right',
      'wb': 'West-Back',
      'ns': 'North-Straight',
      'nl': 'North-Left',
      'nr': 'North-Right',
      'nb': 'North-Back',
      'ss': 'South-Straight',
      'sl': 'South-Left',
      'sr': 'South-Right',
      'sb': 'South-Back'
    },
    'overlap': {
      'desc': 'Description',
      'includedPhases': 'IncludedPhases',
      'lane': 'Lane',
      'operation': 'Operation',
      'add': 'Add overlap',
      'deletetip': 'Delete This Overlap?',
      'mostdata': 'There are at most 16 data !'
    },
    'pattern': {
      'desc': 'Description',
      'offset': 'Offset',
      'cycle': 'Cycle',
      'operation': 'Operation',
      'deletetip': 'Delete This Pattern?',
      'firstphase': 'Please create phase first !',
      'mostdata': 'There are at most 108 data !'
    },
    'plan': {
      'tipcontext': 'Please input your plan_name',
      'tip': 'Tips',
      'ok': 'OK',
      'cancel': 'Cancel',
      'desc': 'Description',
      'time': 'Time',
      'pattern': 'Pattern',
      'operation': 'Operation',
      'deletetip': 'Delete This Plan?',
      'tabtip': 'Delete This Tab?',
      'mostdata': 'There are at most 48 data !'
    },
    'date': {
      'desc': 'Description',
      'month': 'Month',
      'day': 'Day',
      'date': 'Date',
      'plan': 'Plan',
      'operation': 'Operation',
      'deletetip': 'Delete This Date?',
      'mostdata': 'There are at most 255 data !'
    },
    'channel': {
      'desc': 'Description',
      'controlsource': 'ControlSource',
      'controlType': 'ControlType',
      'voltthresh': 'Voltthresh',
      'pacthresh': 'Pacthresh',
      'peakhthresh': 'Peakhthresh',
      'peaklthresh': 'Peaklthresh',
      'operation': 'Operation',
      'other': 'other',
      'phaseVehicle': 'phaseVehicle',
      'phasePedestrian': 'phasePedestrian',
      'overlap': 'overlap',
      'deletetip': 'Delete This Channel ?',
      'deleteAlltip': 'Delete All Channel ?',
      'mostdata': 'There are at most 40 data !',
      'firstoverlapl': 'Please create overlap first !'
    },
    'detector': {
      'desc': 'Description',
      'callPhase': 'CallPhase',
      'noactivity': 'NoActivity(min)',
      'maxpresence': 'MaxPresence(min)',
      'erraticCounts': 'ErraticCounts(Car/minute)',
      'failtime': 'FailTime(second)',
      'enable': 'Enable',
      'mode': 'Mode',
      'presenttime': 'Presenttime(second)',
      'sense': 'Sense',
      'sampletime': 'Sampletime(μs)',
      'inthresh': 'Inthresh',
      'outthresh': 'Outthresh',
      'infilter': 'Infilter',
      'outfilter': 'Outfilter ',
      'operation': 'Operation',
      'deletetip': 'Delete This Detector?',
      'deleteAlltip': 'Delete All Detector?',
      'mostdata': 'There are at most 72 data !'
    },
    'channellock': {
      'deleteAlltip': 'Delete All channellock'
    },
    'channelControl': {
      'recoverysuccess': 'Recovery Success!',
      'recoveryfailed': 'Recovery Failed!'
    },
    'optimize': {
      'strategy': 'Optimization strategy',
      'learn': 'Self learning',
      'saturation': 'Statistical parameters of intersection(The sampling period is 5 minutes):',
      'phase': 'Phase',
      'flow': 'Saturated Flow(veh.)',
      'queue': 'Saturated Queue length(veh.)',
      'speed': 'Max Speed(km/h)',
      'Occupancy': 'Saturated Occupancy rate',
      'mathematical': 'mathematical model'
    },
    'history': {
      'description': 'Description',
      'user': 'User',
      'updatetime': 'Update Time',
      'operation': 'Operation',
      'deletetip': 'Delete This History?'
    },
    'control': {
      'mode': 'Mode:',
      'control': 'Control:',
      'cycle': 'Cycle:',
      'offset': 'Offset:',
      'curTime': 'CurTime:',
      'syncTime': 'SyncTime:',
      'score': 'Score:',
      'level': 'Level:',
      'patternid': 'Pattern Id:',
      'name': 'Name:',
      'current_phase': 'Current_phase:',
      'phase': 'Phase',
      'ring': 'Ring',
      'control_style': 'Control',
      'mode_style': 'Mode',
      'pattern': 'Pattern',
      'phase_countdown': 'phase_countdown:',
      'split': 'split:',
      'countdown': 'countdown:',
      'id': 'id:',
      'type': 'type:',
      'light_countdown': 'light_countdown:',
      'sequence': 'sequence:',
      'num': 'num:',
      'currentvolume': 'CurrentVolume',
      'time': 'Time'
    },
    'button': {
      'OK': 'OK',
      'Cancel': 'Cancel'
    },
    'common': {
      'confirm': 'Confirm',
      'query': 'Query',
      'cancel': 'Cancel',
      'add': 'Add',
      'delete': 'Delete',
      'clone': 'Clone',
      'deleteall': 'DeleteAll',
      'update': 'Update',
      'search': 'Search',
      'alarm': 'Alarm',
      'none': 'none',
      'weak': 'weak',
      'normal': 'normal',
      'strong': 'strong',
      'name': 'Name',
      'path': 'Path',
      'description': 'Description',
      'operations': 'Operations',
      'addfailed': 'Add Success!',
      'addsucess': 'Add Failed!',
      'addcancel': 'Add Canceled!',
      'deletefailed': 'Delete Failed!',
      'deletesucess': 'Delete Success!',
      'deletecancel': 'Delete Canceled!',
      'updatefailed': 'Update Failed!',
      'updatesucess': 'Update Success!',
      'updatecancel': 'Update Canceled!',
      'relatefailed': 'Relate Failed!',
      'relatesucess': 'Relate Success!',
      'relatecancel': 'Relate Canceled!',
      'upload': 'Upload',
      'select': 'Select',
      'commit': 'Commit',
      'commitandexecute': 'CommitAndExecute',
      'uploadsuccess': 'Upload Success!',
      'download': 'Download Success!',
      'downloadandrun': 'Submit and run successfully!',
      'editagentid': 'Edit AgentID',
      'querysucess': 'Query Success!',
      'setup': 'setup',
      'tipsmodaltitle': 'Tips'
    },
    'login': {
      'title': 'KISS Edge Manage Center',
      'login': 'Sign in',
      'authorize': 'Authorize',
      'close': 'Close',
      'dialogtitle': 'Available authorizations'
    },
    'errorTip': {
      'phaseNotZero': 'Must contain at least phase !',
      'planNotZero': 'Must contain at least plan !',
      'patternNotZero': 'Must contain at least pattern',
      'dataNotZero': 'Must contain at least data!',
      'dateIsAll': 'This date must be full year !',
      'planDate': 'The time in the plan is unreasonable !',
      'patternRing': 'The ring time of the same pattern must be equal !',
      'dataPlan': 'The plan in the date can not be empty !',
      'planPattern': 'The pattern in the plan can not be empty !',
      'overlapRules': 'The parent phase in the overlap cannot be empty！',
      'manualpanel': 'The manualpanel cannot be empty！',
      'momthIsNull': 'Month cannot be empty in date！'
    },
    'importTemp': {
      'editTemplateData': 'Edit Template Data',
      'template': 'Template',
      'crossShape': 'Cross Shape',
      'name': 'Name',
      'phaseCount': 'Phase Count',
      'phase': 'Phase',
      'greenClear': 'GreenClear',
      'yellow': 'Yellow',
      'redClear': 'RedClear',
      'pattern': 'Pattern',
      'count': 'Count',
      'split': 'Split',
      'plan': 'Plan',
      'date': 'Date',
      'annual': 'Annual',
      'crossShapes': {
        'crossroads': 'Crossroads',
        'tjunction': 'T-junction',
        'roundabout': 'Roundabout',
        'otherIntersections': 'OtherIntersections',
        'customIntersection': 'CustomIntersection'
      },
      'crossIntersection': {
        'eastward': 'Eastward',
        'westward': 'Westward',
        'southward': 'Southward',
        'northward': 'Northward'
      },
      'crossPhase': {
        'onePhase': 'OnePhase',
        'twoPhase': 'TwoPhase',
        'threePhase': 'ThreePhase',
        'fourPhase': 'FourPhase',
        'fivePhase': 'FivePhase',
        'sixPhase': 'SixPhase',
        'sevenPhase': 'SevenPhase',
        'eightPhase': 'EightPhase',
        'ninePhase': 'NinePhase',
        'tenPhase': 'TenPhase',
        'elevenPhase': 'ElevenPhase',
        'twelvePhase': 'TwelvePhase',
        'thirteenPhase': 'ThirteenPhase',
        'fourteenPhase': 'FourteenPhase',
        'fifteenPhase': 'FifteenPhase',
        'sixteenPhase': 'SixteenPhase'
      }
    }
  }
}

export default en
