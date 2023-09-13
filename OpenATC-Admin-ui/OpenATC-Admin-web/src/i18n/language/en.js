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
  openatc: {
    'common': {
      'add': 'Add',
      'delete': 'Delete',
      'edit': 'Edit',
      'detail': 'Detail',
      'searchplaceholder': 'Enter keyword search',
      'addfailed': 'Add Success!',
      'addsuccess': 'Add Failed!',
      'addcancel': 'Add Canceled!',
      'deletefailed': 'Delete Failed!',
      'deletesuccess': 'Delete Success!',
      'deletecancel': 'Delete Canceled!',
      'updatefailed': 'Update Failed!',
      'updatesuccess': 'Update Success!',
      'updatecancel': 'Update Canceled!',
      'devicenotonline': 'Device not online！',
      'tipsmodaltitle': 'Tips',
      'placeholder': 'Select',
      'operation': 'Operation',
      'operationsuccess': 'Operation Success !',
      'authtip': 'No access !',
      'nodata': 'No Data !'
    },
    'button': {
      'OK': 'OK',
      'Cancel': 'Cancel',
      'expandall': 'Expand All',
      'collapseall': 'Collapse All',
      'search': 'Search',
      'location': 'Location',
      'ignore': 'Ignore'
    },
    'login': {
      'username': 'user_name',
      'password': 'Password',
      'login': 'Login',
      'usernamenotnull': 'The user name cannot be empty!',
      'passnotnull': 'Password cannot be empty!',
      'passerror': 'Password error!',
      'usernamenotexist': 'User name does not exist!'
    },
    'main': {
      'homepage': 'Home',
      'gis': 'GIS',
      'devicemanager': 'Device Manager',
      'usermanager': 'User Manager',
      'greenwaveoptimize': 'Greenwave Optimize',
      'operationrecord': 'Operation Record',
      'organization': 'Organization',
      'script': 'Script',
      'dutyroute': 'Duty Route',
      'realname': 'RealName',
      'telno': 'Telno',
      'email': 'Email',
      'changepass': 'Changepass',
      'systemsettings': 'System Settings',
      'about': 'About',
      'signout': 'Sign Out',
      'language': 'Language',
      'skin': 'Skin',
      'dark': 'Dark',
      'light': 'Light'
    },
    'home': {
      'devicestatus': 'Device Status',
      'faultlist': 'Fault List',
      'online': 'Online',
      'offline': 'Offline',
      'total': 'Total',
      'faultsum': 'Fault Sum',
      'faulttype': 'Fault Type',
      'quantity': 'Quantity',
      'devicemaneger': 'Device Maneger',
      'usermanager': 'User Manager',
      'operationrecord': 'Operation Record'
    },
    'devicemanager': {
      'devicelist': 'Device List',
      'deviceid': 'Device Id',
      'devicename': 'Device Name',
      'firm': 'Firm',
      'platform': 'Platform',
      'type': 'Type',
      'IP': 'IP',
      'port': 'Port',
      'describe': 'Describe',
      'protocol': 'Protocol',
      'state': 'State',
      'fault': 'Fault',
      'online': 'Online',
      'offline': 'Offline',
      'lastupdatetime': 'Last Update Time',
      'operation': 'Operation',
      'longitude': 'Longitude',
      'latitude': 'Latitude',
      'layerLevel': '层级',
      'adddevice': 'Add Device',
      'editdevice': 'Edit Device',
      'enterIp': 'Please enter IP',
      'correctIp': 'Please fill in the legal IP!',
      'enterport': 'Please enter port!',
      'correctport': 'Please fill in the legal port!',
      'choosetype': 'Please select type',
      'chooseprotocol': 'Please select protocol',
      'chooseplatform': 'Please select platform',
      'enterId': 'Please enter device ID',
      'entername': 'Please enter device name',
      'enterrequired': 'Please fill in the required items!',
      'deletedevice': 'Do you want to delete this device?',
      'faultDetail': 'FaultDetail',
      'faultMaintype': 'Main Type',
      'faultSubtype': 'Sub Type',
      'faultOccurtime': 'Occur Time',
      'boardCardType': 'BoardCard Type',
      'faultGrade': 'Grade',
      'faultValue': 'Value',
      'crowsid': 'Crows ID',
      'updateDeviceId': 'Modify ID',
      'deviceNotNull': 'Device ID cannot be empty!',
      'oldIdNotConsistentNewId': 'New device ID cannot be consistent with old device ID!',
      'thirdplatformid': 'Third party ID',
      'timeSpaceGraph': 'Time space graph',
      'keyintersection': 'Key Intersection：'
    },
    'usermanager': {
      'username': 'Username',
      'organization': 'Organization',
      'realname': 'Realname',
      'role': 'Role',
      'telno': 'Telno',
      'email': 'Email',
      'operation': 'Operation',
      'password': 'Password',
      'confirmpassword': 'Confirm Password',
      'newpassword': 'New Password',
      'edituser': 'Edit User',
      'adduser': 'Add User',
      'deleteuser': 'Delete This User?',
      'labelwidth': '25%',
      'authorize': 'Authorize',
      'authorizetitle': 'Authorize Token',
      'authorizetime': 'Authorize Time：',
      'generate': 'Generate',
      'to': 'To',
      'starttime': 'Start date',
      'endtime': 'End date',
      'Authorization': 'Authorization time cannot be empty!',
      'enterpass': 'Please enter the confirmation password!',
      'enterpassdiffpass': 'Confirm password is different from password!',
      'correctphone': 'Please input the correct phone number!',
      'correctemail': 'Please enter the correct email address！',
      'lettersnumbersunderscores': 'The user name can only input letters, numbers or underscores!',
      'enterusername': 'Please enter the user name!',
      'enterpassword': 'Please input a password!',
      'nameandpasswordnotempty': 'User name and password cannot be empty!',
      'wrongphoneemail': 'Wrong phone number or email!',
      'selectonerole': 'Please select at least one role!',
      'pleaseenter': 'Please enter',
      'newpassdiffoldpass': 'The new password cannot be the same as the old one!',
      'nochanges': 'No changes!',
      'superrolenotmodified': 'Super role cannot be modified！',
      'editsucc': 'Edit successfully!',
      'oldpass': 'Old password',
      'confirmpass': 'Confirm new password',
      'enputpassagain': 'Please enter the password again!',
      'twopasswordsdiff': 'The two passwords are inconsistent!',
      'enteroldpass': 'Please input the old password!',
      'enternewepass': 'Please input the new password!',
      'originalpassworderror': 'Original password error！',
      'modificationsucc': 'Modification succeeded',
      'ip': 'IP'
    },
    'greenwaveoptimize': {
      'addplan': 'Click the Add button to add the greenwave Optimize plan',
      'createconfig': 'Click the Edit button to start the plan generation configuration',
      'operation': 'Operation',
      'isdelete': 'Is delete',
      'nochooseplan': 'No scheme selected!',
      'namerepeat': 'Duplicate Optimize scheme name!',
      'addfail': 'Failed to add, the name of Optimize scheme is duplicate!',
      'addroute': 'New Optimize Scheme',
      'editroute': 'Edit Optimize Scheme',
      'name': 'Name',
      'routename': 'Scheme Name',
      'isClose': 'Configuration not saved, are you sure to close?',
      'coordinateparamconfig': 'Coordinate Parameter Configuration',
      'device': 'Device',
      'phase': 'Phase',
      'distance': 'Distance',
      'pattern': 'Pattern',
      'choosephase': 'Please select phase!',
      'choosepattern': 'Please select pattern!',
      'configsuccess': 'Optimize parameter configuration succeeded!',
      'adddevice': 'Add Device',
      'deletedevice': 'Is delete device',
      'deviceid': 'Device Id',
      'devicename': 'Device Name',
      'type': 'Type',
      'IP': 'IP',
      'port': 'Port',
      'describe': 'Describe',
      'deletedevsuccess': 'Delete device successfully!',
      'distancemeter': 'Distance（m）',
      'widthmeter': 'Width（m）',
      'forward': 'Forward',
      'back': 'Back',
      'generate': 'Generate',
      'edit': 'Edit',
      'confirmdownloadpattern': 'Confirm to download this pattern?',
      'offset': 'Offset',
      'cycle': 'Cycle',
      'timespace': 'Time Space',
      'coordinationstrategy': 'Coordination Strategy',
      'coordinationdirection': 'Coordination Direction',
      'keyintersection': 'Key Intersection',
      'upspeed': 'Up Speed(km/h)',
      'downspeed': 'Down Speed(km/h)',
      'up': 'Up',
      'down': 'Down',
      'optstrategyoption1': 'Green wave optimization',
      'directionOption1': 'Up',
      'directionOption2': 'Down',
      'directionOption3': 'All',
      'duration': 'Duration：',
      'speed': 'Speed：',
      'vehicle': 'vehicle：',
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
      'notonline': ' do not online!',
      'planissuedsucc': 'Plan issued successfully',
      'time': 'Time（s）'
    },
    'record': {
      'user': 'Operator',
      'updatetime': 'Update Time',
      'originadress': 'Source',
      'deviceid': 'Device Id',
      'messagetype': 'Infotype',
      'reponsestatus': 'Response Status',
      'opera': 'Operation',
      'export': 'Export'
    },
    'organization': {
      'viewdevice': 'ViewDevice',
      'belongorganization': 'Belong Organization',
      'relatedevice': 'RelateDevice',
      'unrelateddevice': 'Unrelated Device',
      'relateddevice': 'Related Device',
      'modifyorganization': 'Modify Organization',
      'addorganization': 'Add Organization'
    },
    'dutyroute': {
      'clickaddroute': 'Click to add a new route',
      'nochooseplan': 'No chosen plan！',
      'routePreview': 'Route Preview',
      'routeState': 'Route Control',
      'dutyrouteconfig': 'Dutyroute Config',
      'node': 'Node',
      'plan': 'Plan',
      'controlpattern': 'Control Pattern',
      'lasttime': 'Last Time(second)',
      'stage': 'Stage',
      'greenpercent': 'Green Percent',
      'getpatternfailed': 'Get Pattern Failed !',
      'ring': 'Ring',
      'notonduty': 'Not Onduty',
      'onduty': 'Onduty',
      'attributes': 'Attributes',
      'executionway': 'Execution Way',
      'residentphase': 'Resident Phase',
      'timeleft': 'Time Left',
      'executenow': 'Execute Now',
      'cancelexecute': 'Cancel Execute',
      'autonomouscontrol': 'Auto Control',
      'yellowflash': 'Yellow Flash',
      'allred': 'All Red',
      'turnofflights': 'Turn Off Lights',
      'stepping': 'Stepping',
      'fixedcyclecontrol': 'Fixed Cycle Control',
      'singlePointInductionControl': 'Single Point Induction Control',
      'coordinatedInductionControl': 'Coordinated Induction Control',
      'schemeSelectionControl': 'Scheme Selection Control',
      'adaptivecontrol': 'Adaptive Control',
      'nocablecontrol': 'Nocable Control',
      'cablecontrol': 'Cable Control',
      'pedestrianCrossingControl': 'Pedestrian Crossing Control',
      'channellock': 'Channel Lock',
      'phaselock': 'Phase Lock',
      'programIntervention': 'Program Intervention',
      'channelDetection': 'Channel Detection',
      'emptyRouteName': 'Empty Route Name !',
      'namerepeat': 'Name Repeat !',
      'notAllowedToEdit': 'Not allowed to edit processing data !'
    },
    'gis': {
      'chooseLocationInfo': 'Click the map to choose a location !',
      'clickToChooseLocationInfo': 'Click to choose locationInfo on the map!'
    },
    'systemsettings': {
      'id': 'ID',
      'module': 'Module',
      'name': 'Name',
      'content': 'Content',
      'desc': 'Desc',
      'operate': 'Operate',
      'addconfig': 'Add Config',
      'updateconfig': 'Update Config',
      'deleteconfig': 'Do you want to delete this config?'
    }
  }
}

export default en
