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
      'strong': 'strong',
      'setnewpassword': 'Set New Password',
      'oldpass': 'Old Password',
      'inputpassagain': 'Please enter the password again',
      'twopassinconsistent': 'The two passwords are inconsistent',
      'inputpass': 'Please input a password',
      'enternewpass': 'Please enter a new password',
      'inputoldpass': 'Please input the old password!',
      'twopassnotdiff': 'The new password cannot be the same as the old one!',
      'originalpasserror': 'Original password error',
      'modifisucceeded': 'Modification succeeded',
      'getmd5error': 'Failed to get MD5!'
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
      'deviceInfo': 'DeviceInfo',
      'adaptiveParameter': 'AdaptiveParameter',
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
      'agentid': 'AGENTID',
      'Password expiration': 'Password expiration',
      'organization': 'Organization',
      'realname': 'Name',
      'phone': 'Telephone',
      'email': 'Email',
      'changepass': 'Change Password',
      'about': 'About',
      'exit': 'Exit',
      'options': 'options',
      'options1': 'All',
      'options2': 'Phase',
      'options3': 'Overlap',
      'options4': 'Pattern',
      'options5': 'Plan',
      'options6': 'Date',
      'options7': 'Channel',
      'options8': 'Detector',
      'options9': 'PedestrianDetector'
    },
    'overview': {
      'crossinfo': 'Cross Information',
      'crossname': 'Cross Name',
      'divicestate': 'Device Status',
      'protocoltype': 'Protocol',
      'signalID': 'Signal ID',
      'signalIP': 'Signal IP',
      'faultinfo': 'Fault Information',
      'platform': 'platform',
      'online': 'Online',
      'offline': 'Offline',
      'onlineing': 'Offline...',
      'controlmode': 'Control Mode',
      'manual': 'Manual',
      'exitmanual': 'Exit Manual',
      'controlmodel': 'Control Pattern',
      'patternname': 'Pattern Name',
      'controlnumber': 'Control Num',
      'mode': 'Mode',
      'stage': 'Stage',
      'specialcontrol': 'Special Control',
      'phaseclosecontrol': 'Phase Close Control',
      'implement': 'Execute',
      'ipaddress': 'IP',
      'deviceport': 'Port',
      'agreement': 'Protocol',
      'currentstage': 'Current Stage',
      'allstagesnum': 'Total Stages',
      'patternnum': 'Pattern Number',
      'details': 'Details',
      'autocontrol': 'Auto Control',
      'yellowflash': 'Yellow Flash Control',
      'allred': 'Red Control',
      'step': 'Step Control',
      'fixedperiod': 'Fixed_Cycle Control',
      'inductioncontrol': 'Free Control',
      'nocablecoordination': 'Nocable Control',
      'phasewalk': 'Pedestrian Crossing Control',
      'websteroptimization': 'Webster Single Point Optimization',
      'inductivePedestrianCrossControl': 'Inductive Pedestrian Crossing Control',
      'selfadaption': 'Self-Adaption',
      'custom': 'Custom',
      'controlmodevalue': 'Control Value',
      'patternstate': 'Pattern State',
      'cycle': 'Cycle',
      'phasesplit': 'Phase Split',
      'phase': 'Phase',
      'phasedifference': 'Offset',
      'getintersectionmapagain': 'Get intersection map again',
      'ring': 'Ring',
      'phaseid': 'Phase ID',
      'detectorid': 'Detector ID',
      'smallcars': 'Small Cars',
      'mediumvehicles': 'Medium Vehicles',
      'bigcars': 'Big Cars',
      'possessionoftime': 'Possession Of Time',
      'occupancy': 'Occupancy',
      'phase_countdown': 'Phase_countdown',
      'split': 'Split',
      'type': 'Type',
      'showlist': 'showlist',
      'showlist1': 'Control Mode',
      'showlist2': 'Cycle',
      'showlist3': 'Current/Remaining Time',
      'showlist4': 'Control Mode',
      'showlist5': 'Offset',
      'showlist6': 'Real-time Volume',
      'platformcontrol': 'Platform Control',
      'configurationtoolcontrol': 'Configuration Tool Control',
      'manualcontrol': 'Manual Control',
      'modelList': 'modelList',
      'modelList0': 'Auto Control',
      'modelList1': 'Yellow Flash',
      'modelList2': 'Red Control',
      'modelList3': 'Dark Control',
      'modelList4': 'Step Control',
      'modelList5': 'Fixed_Cycle Control',
      'modelList6': 'Free Control',
      'modelList9': 'Self-Adaption',
      'modelList10': 'Nocable Control',
      'modelList12': 'phasewalk',
      'modelList13': 'Pattern Recovery',
      'modelList19': 'Inductive Pedestrian Crossing Control',
      'modelList23': 'Close Phase',
      'tips': 'Tips',
      'exitmanul': 'It is necessary to restore self-control before exiting. Do you want to exit?',
      'canceled': 'Cancelled',
      'nextcycleeffic': 'Successful execution, the next cycle will take effect!',
      'transitioneffic': 'The execution is successful. It will take effect after the transition switch!',
      'controlnumerrormess': 'When the control mode is user-defined, the control mode number cannot be empty!',
      'delay': 'Delay time',
      'duration': 'Duration',
      'curModel': 'Current Model',
      'curStage': 'Current Stage',
      'responseTime': 'Response Time',
      'putTscControlError1': 'Unsupported control pattern',
      'putTscControlError2': 'Control number not exist',
      'putTscControlError3': 'Unsupported control params',
      'putTscControlError4': 'Unable to execute because device is in initialization',
      'putTscControlError5': 'Cannot be executed, beacuse the pattern with higher priority is under control',
      'putTscControlError6': 'Cannot be executed, beacuse the user with higher priority is under control',
      'nofault': 'None',
      'extendedContent': 'Extended Content',
      'JSONFormatError': 'JSON format error!',
      'showFault': 'Display fault details',
      'hideFault': 'Hide fault details',
      'close': 'close',
      'closemode': 'mode',
      'closeOption0': 'recovery',
      'closeOption1': 'close',
      'closeOption2': 'only vehicle close',
      'closeOption3': 'only pedestrian close',
      'vehicle': 'vehicle',
      'pedestrian': 'pedestrian'
    },
    'phase': {
      'desc': 'LaneDirection',
      'peddesc': 'PedestrianDirection',
      'controltype': 'ControlType',
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
      'deleteAlltip': 'Are you sure to delete all phases and patterns ?',
      'mostdata': 'There are at most 40 data !',
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
      'sb': 'South-Back',
      'eped': 'East-Pedestrian',
      'wped': 'West-Pedestrian',
      'sped': 'South-Pedestrian',
      'nped': 'North-Pedestrian',
      'etped': 'East-Top-Pedestrian',
      'ebped': 'East-Bottom-Pedestrian',
      'wtped': 'West-Top-Pedestrian',
      'wbped': 'West-Bottom-Pedestrian',
      'slped': 'South-Left-Pedestrian',
      'srped': 'South-Right-Pedestrian',
      'nlped': 'North-Left-Pedestrian',
      'nrped': 'North-Right-Pedestrian',
      'xrped': 'X-/-Pedestrian',
      'xlped': 'X-\\-Pedestrian',
      'ewped': 'East-West-Pedestrian',
      'snped': 'South-North-Pedestrian',
      'redyellow': 'redyellow',
      'vehiclethresh': 'Vehicle Queuing Threshold',
      'pedestrianthresh': 'Pedestrian waiting Threshold',
      'mainroad': 'main road',
      'bypass': 'by-pass',
      'pedestrianonly': 'pedestrian only',
      'busonly': 'bus only',
      'BRTonly': 'BRT only',
      'tramonly': 'Tram only',
      'pulsetype': 'PulseType',
      'offpulse': 'Turn off pedestrian and vehicle pulses',
      'sendvehiclepulse': 'Send vehicle pulse',
      'sendpedestrianpulse': 'Send pedestrian pulse',
      'sendpedestriansvehiclepulse': 'Send pedestrians and vehicles pulses'
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
      'property': 'Property',
      'desc': 'Description',
      'offset': 'Offset',
      'cycle': 'Cycle',
      'operation': 'Operation',
      'deletetip': 'Delete This Pattern?',
      'firstphase': 'Please create phase first !',
      'mostdata': 'There are at most 32 data !',
      'ring': 'Ring',
      'modeOption1': 'Other',
      'modeOption2': 'None',
      'modeOption3': 'Min Call',
      'modeOption4': 'Max Call',
      'modeOption5': 'Pedestrian Call',
      'modeOption6': 'Max Call And Pedestrian Call',
      'modeOption7': 'Omit Call',
      'coordphaseOption1': 'Coordination Phase',
      'coordphaseOption2': 'Key Phase',
      'coordphaseOption4': 'Fixed Phase',
      'forbiddenstage': 'Forbidden Stage',
      'screenstage': 'Screen Stage',
      'coordinatestage': 'Coordinate Stage ',
      'delaystart': 'Delay Start ',
      'advanceend': 'Advance End',
      'stageConfig': 'Stage Config',
      'parameters': 'Pparameters',
      'plan': 'Plan',
      'ringConfig': 'Ring Config',
      'stage': 'Stage',
      'phase': 'Phase',
      'agentidError': 'Failed to get agentid!',
      'splitCheckMsg': 'The green letter ratio must be greater than the phase transition time or the pedestrian crossing + pedestrian transition time!'
    },
    'plan': {
      'tipcontext': 'Please input your plan_name',
      'editcontext': 'Please edit plan name',
      'plannamerequired': 'Plan name is required!',
      'plannamerepeated': 'Plan name cannot be repeated!',
      'mostplandata': 'There are at most 16 data !!',
      'tip': 'Tips',
      'ok': 'OK',
      'cancel': 'Cancel',
      'desc': 'Description',
      'time': 'Time',
      'pattern': 'Pattern',
      'operation': 'Operation',
      'deletetip': 'Delete This Plan?',
      'tabtip': 'Delete This Tab?',
      'mostdata': 'There are at most 48 data !',
      'editname': 'Edit name',
      'controltype': 'Control Type',
      'ControlOption1': 'Yellow Clear',
      'ControlOption2': 'Red Control',
      'ControlOption3': 'Dark Control',
      'ControlOption4': 'Step Control',
      'ControlOption5': 'Fixed_Cycle Control',
      'ControlOption6': 'Free Control',
      'ControlOption9': 'Self-Adaption',
      'ControlOption10': 'Nocable Control',
      'ControlOption12': 'Pedestrian Crossing Control',
      'ControlOption19': 'Inductive Pedestrian Crossing Control'
    },
    'date': {
      'desc': 'Description',
      'month': 'Month',
      'day': 'Day',
      'date': 'Date',
      'plan': 'Plan',
      'operation': 'Operation',
      'deletetip': 'Delete This Date?',
      'mostdata': 'There are at most 40 data !'
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
      'type': 'Type',
      'typeOptionundefined': '',
      'typeOption': 'typeOption',
      'typeOption0': 'Coil',
      'typeOption1': 'Smart junction terminal',
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
      'saturationflow': 'SaturationFlow',
      'operation': 'Operation',
      'deletetip': 'Delete This Detector?',
      'deleteAlltip': 'Delete All Detector?',
      'mostdata': 'There are at most 64 data !'
    },
    'channellock': {
      'deleteAlltip': 'Delete All channellock',
      'deleteOnetip': 'Are you sure you want to delete this channel lock?',
      'starttime': 'Start Time',
      'endtime': 'End Time',
      'transitiongreenflashtime': 'Green Flashing Time',
      'transitionyellowtime': 'Yellow Time',
      'channelstats': 'Channel Status',
      'channel': 'Channel',
      'channelinfo': 'Channel Info',
      'status': 'Status'
    },
    'channelControl': {
      'recoverysuccess': 'Recovery Success!',
      'recoveryfailed': 'Recovery Failed!',
      'testsuccess': 'Test Success!',
      'testfailed': 'Test Failed!',
      'channel': 'Channel',
      'redresidualvoltage': 'Red Residual Voltage',
      'redoutputvoltage': 'Red Output Voltage',
      'redoffresidualpower': 'Red Off Residual Power',
      'redonoutputpower': 'Red On Output Power',
      'yellowresidualvoltage': 'Yellow Residual Voltage',
      'yellowoutputvoltage': 'Yellow Output Voltage',
      'yellowoffresidualpower': 'Yellow Off Residual Power',
      'yellowonoutputpower': 'Yellow On Output Power',
      'greenresidualvoltage': 'Green Residual Voltage',
      'greenoutputvoltage': 'Green Output Voltage',
      'greenoffresidualpower': 'Green Off Residual Power',
      'greenonoutputpower': 'Green On Output Power',
      'inputvoltage': 'Input Voltage'
    },
    'pedestrianDetector': {
      'desc': 'Description ',
      'callPhase': 'Call Phase',
      'noactivity': 'Noactivity(min)',
      'maxpresence': 'Maxpresence(min)',
      'erraticCounts': 'Erratic Counts(one/min）',
      'operation': 'Operation',
      'deletetip': 'Delete This Detector',
      'mostdata': 'There are at most 8 data !',
      'type': 'Detector Type',
      'typeOptionundefined': '',
      'typeOption': 'typeOption',
      'typeOption0': 'Coil',
      'typeOption1': 'Radar',
      'typeOption2': 'Video',
      'region': 'Detector Area',
      'regionOption': 'regionOption',
      'regionOptionundefined': '',
      'regionOption0': 'Default',
      'regionOption1': 'Waiting Area',
      'regionOption2': 'Cross Street Area'
    },
    'deviceinfo': {
      'deviceinfo': 'Device Info:',
      'addresscode': 'Adress Code:',
      'areaid': 'Region ID:',
      'crossid': 'Cross ID:',
      'selflearning': 'Selflearning:',
      'deviceparam': 'Device Parameters:',
      'startyellowflashtime': 'Start Yellow Flash Time:',
      'startredtime': 'Start Red Time:',
      'greenwavetransitionperiod': 'Transition Cycle:',
      'ip1': 'IP1',
      'ip': 'IP: ',
      'subnetmask': 'Subnet Mask:',
      'gateway': 'Gateway:',
      'ip2': 'IP2',
      'centercommunicationip': 'Center Communication IP',
      'port': 'Port:',
      'cascade': 'Cascade:',
      'lampcontrolpanel': 'Number Of Light Control Panels:',
      'boardsformainengine': 'Number Of Inspection Boards:',
      'ioboards': 'Number Of IO Boards:',
      'slavecascadeoffset': 'Slave Cascade Offset:',
      'deviceinfostyle': 'margin-left: 100px; top: 10px;',
      'deviceparamstyle': 'margin-left: 170px; top: 10px;',
      'deviceselflearningstyle': 'margin-left: 100px; top: 15px;',
      'faultdetect': 'Fault Detect',
      'closegreenandredon': 'Close Green And Red On: ',
      'closenoredon': 'Close No Red On: ',
      'detectgapgreenandredon': 'Detect Gap Green And Red On（0.1s）: ',
      'detectgapnoredon': 'Detect Gap No Red On（0.1s）: ',
      'detectgapgreenconflict': 'Detect Gap Green Conflict（0.1s）: ',
      'startOrderParams': 'Start Order Params ',
      'netcardInfo': 'Network Info: ',
      'stepType': 'Step Type',
      'seeCutEffect': 'See Cut Detail',
      'siteIdLimit': 'SiteId must be whithin 0 ~ 99999',
      'siteIdCutEffect': 'SiteId Cut Detail',
      'cutData': 'Cut Data',
      'cutEffect': 'Cut Detail',
      'longgerThan20': 'Data length is longger than 20 ！'
    },
    'singleoptim': {
      'deletetip': 'Sure to delete ？',
      'selfControlParams': 'Self Control Params',
      'lossTimeOfGreenBeginning': 'LossTime Of Green Beginning(second)',
      'lossTimeOfYellowEnding': 'LossTime Of Yellow Ending(second)',
      'saturationFlow': 'Saturation Flow(vehidle/hour)',
      'cycleAdjustmentFactor': 'Cycle Adjustment Factor',
      'selfLearningModeOnOrOff': 'Self Learning Mode',
      'staticWeight': 'Static Weight',
      'phaseStaticFactor': 'Phase Static Factor',
      'createPatternFirst': 'Please create pattern first',
      'gratterThanOne': 'The sum must less than one '
    },
    'controlpanel': {
      'controlmode': 'Control Mode',
      'duration': 'Duration',
      'transitiongreenflashtime': 'Green Flash Time',
      'transitionyellowtime': 'Yellow Time',
      'transitionredtime': 'All Red Time',
      'mingreen': 'Min Green',
      'channel': 'Channel',
      'channeldesc': 'Channel Description',
      'status': 'State',
      'step': 'Step',
      'allred': 'All Red',
      'yellowflash': 'Yellow Flash',
      'defaultsettings': 'Default Settings',
      'allclear': 'All Clear',
      'clear': 'Clear',
      'resetstatus': 'Reset State',
      'manualbtn': 'manualbtn',
      'manualbtn1': '1',
      'manualbtn2': '2',
      'manualbtn3': '3',
      'manualbtn4': '4',
      'manualbtn5': '5',
      'manualbtn6': '6',
      'manualbtn7': '7',
      'manualbtn8': '8',
      'channelstatus': 'channelstatus',
      'channelstatus1': 'Red',
      'channelstatus3': 'Green',
      'channelstatus4': 'Lights Out',
      'getdefaultmanualpaneltips': 'Generate default parameters successfully, please check!',
      'phaseanddetectornotnull': 'Phase and channel cannot be empty!',
      'iscleardata': 'Clear data?',
      'tips': 'Tips',
      'cancelclear': 'Emptying cancelled',
      'changered': 'Do you want to initialize all States to red?',
      'cancelinit': 'Uninitialized',
      'manualpanelundefined': '',
      'manualpanel0': 'Default',
      'manualpanel1': 'Custom',
      'manualcontrolbackground': 'manualcontrolbackgrounden'
    },
    'system': {
      'information': 'Information',
      'remotecontrol': 'Remote Control',
      'channeldetection': 'Channel Detection',
      'signalversion': 'Signal Version',
      'softwareversion': 'Software Version',
      'hardwareversion': 'Hardware Version',
      'currentsystemtime': 'System Time',
      'time': 'Time',
      'featureparameterversion': 'Parameter Version',
      'version': 'Version',
      'identificationcode': 'Identification Code',
      'equipmentidentificationcode': 'Device Identification Code',
      'remotedebugging': 'Remote Debugging',
      'openornot': 'Open',
      'automaticshutdown': 'Auto Shutdown',
      'systemupdate': 'System Update',
      'dataupdate': 'Data Update',
      'reset': 'Set',
      'sure': 'Sure',
      'oper': 'Operation',
      'selectfile': 'Select File',
      'noselectfile': 'No update file selected!',
      'updateUSB': 'Update USB',
      'recovery': 'Recovery',
      'lampcontrolpanel': 'Lamp Control Panel',
      'featureparameterversionsetting': 'Parameter Version Setting',
      'deviceidentificationcodesetting': 'Device Identification Code Setting',
      'baudrate': 'Baudrate',
      'databit': 'Data Bits',
      'stopbit': 'Stop Bit',
      'paritybit': 'Parity Bit',
      'signalidentificationcode': 'Signal Identification Code',
      'modifysystemtime': 'Modify System Time',
      'selectdate': 'Select Date',
      'anytimepoint': 'Any Time Point',
      'tips': 'Tips',
      'update': 'Update',
      'duration1': '5min',
      'duration2': '60min',
      'duration3': 'Unlimited',
      'remote1': 'Restart',
      'remote2': 'Authorize',
      'remote3': 'Custom',
      'test': 'Test',
      'realTimeChannel': 'Real time channel',
      'isReboot': 'The system file is downloaded successfully. The update takes effect after restarting. Do you want to restart the device?',
      'rebootSuccess': 'Restart successfully!',
      'rebootFaile': 'Restart failed!',
      'resetSuccess': 'Set successfully!',
      'filesuccess': 'Upgrade file transfer complete!',
      'filefailed': 'Upgrade interface call failed!',
      'updateovertime': 'Update file overtime!'
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
    'fault': {
      'refresh': 'Refresh',
      'export': 'Export',
      'faultofcurrentdetailedlightgroup': 'Detail Faults',
      'faultofcurrentdetailedvehicleinspectionversion': 'Detail Faults',
      'tab': 'tab',
      'tab0': 'Overview',
      'tab1': 'Main Control Board',
      'tab2': 'Light Control Panel',
      'tab3': 'Vehicle Inspection Board',
      'tab4': 'I/O Board',
      'maintype': 'Maintype',
      'subtype': 'Subtype',
      'occurtime': 'Occurtime',
      'recoverytime': 'Recoverytime',
      'grade': 'Grade',
      'value': 'value'
    },
    'statistics': {
      'username': 'User_name：',
      'pass': 'Password：',
      'usernameplaceholder': 'Enter one user name',
      'passplaceholder': 'Please input a password',
      'getdevicedata': 'Get device traffic data',
      'detector': 'Detector',
      'time': 'Time',
      'flow': 'Flow',
      'occupyrate': 'Occupancy',
      'occupytime': 'Possession of time',
      'userpassnotnull': 'User name or password cannot be empty!',
      'historicalisempty': 'Get historical traffic data is empty!'
    },
    'dailyrecord': {
      'data': 'Data',
      'infotype': 'Infotype',
      'subject': 'Subject',
      'object': 'Object',
      'operationresults': 'Operation Result'
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
      'phase_countdown': 'Phase_countdown:',
      'split': 'Split:',
      'countdown': 'Countdown:',
      'id': 'Id:',
      'type': 'Type:',
      'light_countdown': 'Light_countdown:',
      'sequence': 'Sequence:',
      'num': 'Num:',
      'value': 'Parameters',
      'currentvolume': 'CurrentVolume',
      'time': 'Time'
    },
    'button': {
      'OK': 'OK',
      'Cancel': 'Cancel',
      'Back': 'Back'
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
      'input': 'Please enter a word',
      'entercontent': 'Please enter the content',
      'commit': 'Commit',
      'commitandexecute': 'CommitAndExecute',
      'uploadsuccess': 'Upload Success!',
      'download': 'Download Success!',
      'downloaderror': 'Signal Check Error!',
      'downloadandrun': 'Submit and run successfully!',
      'editagentid': 'Edit AgentID',
      'querysucess': 'Query Success!',
      'setup': 'setup',
      'tipsmodaltitle': 'Tips',
      'deviceoffline': 'Device not online!'
    },
    'login': {
      'title': 'Openatc-Configer',
      'login': 'Sign in',
      'authorize': 'Authorize',
      'close': 'Close',
      'dialogtitle': 'Available authorizations',
      'username': 'User Name',
      'password': 'Password',
      'networkparam': 'Network parameters',
      'port': 'Port',
      'protocol': 'Protocol',
      'restoredefaultparameters': 'Restore default parameters',
      'enterip': 'Please enter IP',
      'enterlegalip': 'Please enter legal IP!',
      'enterpport': 'Please enter the port',
      'entercorrectport': 'Please enter the correct port!',
      'usernamenotnull': 'User name cannot be empty!',
      'passnotnull': 'Password cannot be empty!',
      'selectagreement': 'Please select protocol',
      'enterdeviceid': 'Please enter device ID',
      'loginerror': 'Login error!'
    },
    'errorTip': {
      'phaseNotZero': 'Must contain at least phase !',
      'phaseExceed': 'One ring can not exceed 16 phases at most!',
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
      'momthIsNull': 'Month cannot be empty in date！',
      'devicenotonline': 'Device not online!',
      'abnormalcommunication': 'Abnormal Communication!',
      'saveParamFailed': 'Signal save parameter failed!',
      'ringErrorTip': 'Ring configuration should be started from ring 1, and skip ring configuration is not allowed!',
      'deviceinformationnotnull': 'Address code in device information cannot be empty!',
      'noSchemeUpload': 'There is no scheme to upload!',
      'readSchemeSucc': 'Reading scheme succeeded!'
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
        'rampintersection': 'RampIntersection',
        'pedestriancrossing': 'PedestrianCrossing',
        'otherIntersections': 'OtherIntersections',
        'customIntersection': 'CustomIntersection'
      },
      'crossIntersection': {
        'eastward': 'Eastward',
        'westward': 'Westward',
        'southward': 'Southward',
        'northward': 'Northward',
        'eastwestward': 'Eastwestdirection',
        'southnorthward': 'Northsouthdirection'
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
    },
    'about': {
      'version': 'Version',
      'copyright': 'Copyright',
      'company': 'KEDACOM',
      'packedtime': 'Packed time',
      'opensourceagreement': 'Opensource Agreement'
    },
    'message': {
      '10000': 'Parameter is empty',
      '10001': 'Parameter length error',
      '1002': 'Error',
      '10002': 'Record cannot be empty',
      '20003': 'id cannot be empty and cannot be 0',
      '20004': 'No parameter record',
      '20005': 'No device id found',
      // 全局异常错误
      '0001': 'Null pointer',
      '0002': 'Wrong request method',
      '0003': 'Incorrect input parameter type',
      '0004': 'Incomplete input parameters',
      '0005': 'Input parameters do not meet constraints',
      '0006': 'system error',
      // 用户管理错误
      '3001': 'Username does not exist',
      '3002': 'Authentication failed',
      '3003': 'Duplicate username',
      '3004': 'The old and new passwords cannot be the same',
      '3005': 'password can not be blank',
      '3006': 'The original password is wrong',
      '3007': 'Login user does not exist',
      '3008': 'No access',
      '3009': 'wrong password',
      '3010': 'Super user role cannot be modified',
      '3011': 'username or password is wrong',
      '3013': 'Account is disabled',
      '3014': 'token has expired',
      '3015': 'token failure',
      '3016': 'User organization is empty',
      '3017': 'Organization does not exist',
      '3018': 'Access ip is inconsistent with user ip, login is forbidden',
      // 消息通讯错误
      '4001': '错误请求',
      '4002': '错误应答',
      '4003': '设备不在线',
      '4004': '重复的设备id',
      '4005': '信号机应答为空',
      '4006': '信号机应答操作类型为空',
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

export default en
