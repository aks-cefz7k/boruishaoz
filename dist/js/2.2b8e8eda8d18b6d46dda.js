webpackJsonp([2],{"77RQ":function(t,e,n){var a=n("qT8u");"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);n("rjj0")("6bfc55c0",a,!0)},ARoL:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n("BO1k"),r=n.n(a),o=n("fZjL"),i=n.n(o),l=n("ifoU"),s=n.n(l),c=n("sGqM"),d=n("TPc2"),p=n("TIfe"),v={name:"overview",data:function(){return{controlData:{},form:{control:"",pattern:"",mode:""},spanArr:[],list:[{iconClass:"model",name:"控制模式",value:"--"},{iconClass:"cycle",name:"周期",value:"--"},{iconClass:"time",name:"当前时间",value:"--"},{iconClass:"maincontrol",name:"控制方式",value:"--"},{iconClass:"phasediff",name:"相位差",value:"--"},{iconClass:"time",name:"剩余时间",value:"--"}],tableData:[],phaseControlTimer:null,ParamsMap:new s.a([["控制模式","mode"],["周期","cycle"],["控制方式","control"],["相位差","offset"],["当前时间","curTime"],["剩余时间","syncTime"]]),ParamsMode:new s.a([[0,"默认模式"],[1,"自主模式"],[2,"中心模式"],[3,"边缘模式"],[4,"遥控器模式"],[5,"手动面板模式"],[6,"勤务模式"],[7,"优化模式"]]),ParamsControl:new s.a([[0,"自主控制"],[1,"黄闪"],[2,"全红"],[3,"关灯"],[4,"步进"],[5,"定周期控制"],[6,"单点感应控制"],[7,"协调感应控制"],[8,"方案选择控制"],[9,"自适应控制"],[10,"优化控制"]]),phaseType:new s.a([[1,"红"],[2,"黄"],[3,"绿"]]),ip:"--",port:"--",protocol:"--",devStatus:1,mode:"2",loading:{},agentId:"0"}},watch:{$route:{handler:function(t,e){void 0!==t.query&&(this.agentId=this.$route.query.agentid,Object(p.h)(this.agentId),this.ip=this.$route.query.IP,this.port=this.$route.query.port,this.protocol=this.$route.query.protocol)},deep:!0}},created:function(){void 0!==this.$route.query&&(this.agentId=this.$route.query.agentid,Object(p.h)(this.agentId),this.ip=this.$route.query.IP,this.port=this.$route.query.port,this.protocol=this.$route.query.protocol),this.registerMessage()},mounted:function(){},methods:{registerMessage:function(){var t=this;Object(d.f)(this.agentId).then(function(e){if(!e.data.success)return t.devStatus=2,"4003"===e.data.code?void t.$message.error(t.$t("edge.errorTip.devicenotonline")):void t.$message.error("通讯异常！");t.devStatus=3,t.clearPatternInterval(),t.phaseControlTimer=setInterval(function(){t.initData()},1e3)})},clearPatternInterval:function(){null!==this.phaseControlTimer&&(clearInterval(this.phaseControlTimer),this.phaseControlTimer=null)},initData:function(){var t=this;Object(c.b)(this.agentId).then(function(e){if(!e.data.success)return"4003"===e.data.code?(t.devStatus=2,t.clearPatternInterval(),void t.$message.error(t.$t("edge.errorTip.devicenotonline"))):void t.$message.error(e.data.message);t.controlData=t.handleGetData(e.data.data.data),t.handleList(t.controlData),t.handleTableData(t.controlData)}).catch(function(e){t.$message.error(e),console.log(e)})},handleObjectSpan:function(){var t=this,e=0;this.spanArr=[],this.tableData.forEach(function(n,a){n.index=a,0===a?t.spanArr.push(1):n.ring===t.tableData[a-1].ring?(t.spanArr[e]+=1,t.spanArr.push(0)):(t.spanArr.push(1),e=a)})},objectSpanMethod:function(t){t.row,t.column;var e=t.rowIndex;if(0===t.columnIndex){var n=this.spanArr[e];return{rowspan:n,colspan:n>0?1:0}}},onSubmit:function(){var t=this;this.lockScreen();var e={};e.control=Number(this.form.control),e.pattern=Number(this.form.pattern),Object(c.e)(e).then(function(e){t.unlockScreen(),e.data.success?t.$alert(t.$t("edge.common.download"),{type:"success"}):t.$message.error(e.data.message)}).catch(function(e){t.unlockScreen(),t.$message.error(e),console.log(e)})},onGet:function(){var t=this;this.lockScreen(),Object(c.d)(this.agentId).then(function(e){if(t.unlockScreen(),e.data.success){t.$message.success(t.$t("edge.common.querysucess"));var n=e.data.data.data;t.form.control=String(n.control),t.form.pattern=String(n.pattern),t.form.mode=t.ParamsMode.get(n.mode)}else t.$message.error(e.data.message)}).catch(function(e){t.unlockScreen(),t.$message.error(e),console.log(e)})},handleGetData:function(t){var e=this;return""===t.name&&(t.name="方案"+t.patternid),i()(t).forEach(function(n){if("mode"===n&&(t[n]>0&&t[n]<6?t[n]=e.ParamsMode.get(t[n]):t[n]=e.ParamsMode.get(0)),"control"===n&&(t[n]=e.ParamsControl.get(t[n])),"phase"===n){var a=!0,o=!1,i=void 0;try{for(var l,s=r()(t[n]);!(a=(l=s.next()).done);a=!0){var c=l.value;c.type=e.phaseType.get(c.type)}}catch(t){o=!0,i=t}finally{try{!a&&s.return&&s.return()}finally{if(o)throw i}}}}),t},handleList:function(t){var e=!0,n=!1,a=void 0;try{for(var o,i=r()(this.list);!(e=(o=i.next()).done);e=!0){var l=o.value,s=this.ParamsMap.get(l.name);l.value=t[s]}}catch(t){n=!0,a=t}finally{try{!e&&i.return&&i.return()}finally{if(n)throw a}}},handleTableData:function(t){this.tableData=[];var e=t.phase,n=t.rings,a=!0,o=!1,i=void 0;try{for(var l,s=r()(n);!(a=(l=s.next()).done);a=!0){var c=l.value,d=c.sequence,p=!0,v=!1,h=void 0;try{for(var f,u=r()(d);!(p=(f=u.next()).done);p=!0){var g=f.value,m={};m.ring=c.num,m.phase=g;var x=!0,b=!1,_=void 0;try{for(var y,w=r()(e);!(x=(y=w.next()).done);x=!0){var C=y.value;g===C.id&&(m.phase_countdown=C.phase_countdown,m.split=C.split,m.countdown=C.countdown,m.id=C.id,m.type=C.type,m.light_countdown=C.light_countdown)}}catch(t){b=!0,_=t}finally{try{!x&&w.return&&w.return()}finally{if(b)throw _}}this.tableData.push(m)}}catch(t){v=!0,h=t}finally{try{!p&&u.return&&u.return()}finally{if(v)throw h}}}}catch(t){o=!0,i=t}finally{try{!a&&s.return&&s.return()}finally{if(o)throw i}}this.handleObjectSpan()},lockScreen:function(){this.loading=this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.5)"})},unlockScreen:function(){this.loading.close()},addClass:function(t){var e=t.row;t.column,t.rowIndex;if(2===t.columnIndex){if("绿"===e.type)return"green";if("红"===e.type)return"red";if("黄"===e.type)return"yellow"}}},destroyed:function(){this.clearPatternInterval()}},h={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"container-main"},[n("div",{staticClass:"container-left"},[n("div",{staticClass:"container-left-top"},[n("div",{staticClass:"agent-div"},[n("div",{staticStyle:{height:"210px"}},[n("div",{staticClass:"agent-icon"},[n("div",{directives:[{name:"show",rawName:"v-show",value:3===t.devStatus,expression:"devStatus===3"}]},[n("div",{staticClass:"yuanxing",staticStyle:{background:"rgba(64, 158, 255, 0.6)"}}),n("div",{staticClass:"iconfont icon-ziyuan",staticStyle:{color:"#409eff"}}),n("div",{staticClass:"dev-status",staticStyle:{color:"#409eff",left:"40px"}},[t._v("在线")])]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:2===t.devStatus,expression:"devStatus===2"}]},[n("div",{staticClass:"yuanxing",staticStyle:{background:"rgba(179, 179, 179, 0.6)"}}),n("div",{staticClass:"iconfont icon-ziyuan",staticStyle:{color:"#b3b3b3"}}),n("div",{staticClass:"dev-status",staticStyle:{color:"#b3b3b3",left:"40px"}},[t._v("离线")])]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:1===t.devStatus,expression:"devStatus===1"}]},[n("div",{staticClass:"yuanxing",staticStyle:{background:"rgba(230, 162, 60, 0.6)"}}),n("div",{staticClass:"iconfont icon-ziyuan",staticStyle:{color:"#e6a23c"}}),n("div",{staticClass:"dev-status",staticStyle:{color:"#e6a23c",left:"30px"}},[t._v("联机中")])])]),t._v(" "),n("div",{staticClass:"agent-num"},[n("div",{staticClass:"agent-id"},[t._v("IP地址")]),t._v(" "),n("div",{staticClass:"agent-number"},[t._v(t._s(t.ip))]),t._v(" "),n("div",{staticClass:"agent-port"},[t._v("设备端口")]),t._v(" "),n("div",{staticClass:"port-number"},[t._v(t._s(t.port))]),t._v(" "),n("div",{staticClass:"agent-port"},[t._v("协议")]),t._v(" "),n("div",{staticClass:"port-number"},[t._v(t._s(t.protocol))])])]),t._v(" "),n("div",{staticStyle:{height:"210px"}},[n("div",{staticStyle:{float:"left",height:"50%",width:"50%","text-align":"center"}},[n("div",{staticClass:"curr-grade"},[t._v(t._s(t.controlData.current_stage))]),t._v(" "),n("div",{staticClass:"curr-num"},[t._v("当前阶段")])]),t._v(" "),n("div",{staticStyle:{float:"right",height:"50%",width:"50%","text-align":"center"}},[n("div",{staticClass:"curr-grade"},[t._v(t._s(t.controlData.total_stages))]),t._v(" "),n("div",{staticClass:"curr-num"},[t._v("总阶段数")])]),t._v(" "),n("div",{staticStyle:{float:"left",height:"50%",width:"50%","text-align":"center"}},[n("div",{staticClass:"curr-grade"},[t._v(t._s(t.controlData.patternid))]),t._v(" "),n("div",{staticClass:"curr-num"},[t._v("方案编号")])]),t._v(" "),n("div",{staticStyle:{float:"right",height:"50%",width:"50%","text-align":"center"}},[n("div",{staticClass:"curr-grade"},[t._v(t._s(t.controlData.name))]),t._v(" "),n("div",{staticClass:"curr-num"},[t._v("方案名称")])])])]),t._v(" "),t._l(t.list,function(e,a){return n("div",{key:a,staticClass:"other-div"},[n("div",{staticStyle:{height:"70px"}},[n("div",{staticClass:"model-tupian"},[n("svg-icon",{attrs:{"icon-class":e.iconClass,className:"model-icon"}})],1),n("div",{staticClass:"model-name"},[t._v(t._s(e.name))])]),t._v(" "),n("div",{staticStyle:{height:"140px"}},[n("div",{staticClass:"control-center"},[t._v(t._s(e.value))])])])})],2),t._v(" "),n("div",{staticClass:"container-left-bottom"},[n("div",{staticClass:"current-phase"},[n("div",{staticClass:"color-block"}),t._v(" "),n("div",{staticClass:"curr-phase"},[n("span",[t._v(t._s(t.$t("edge.control.current_phase")))]),t._v(" "),t._l(t.controlData.current_phase,function(e,a){return n("span",{key:a},[n("span",0===a?[t._v(t._s(e))]:[t._v(t._s(", "+e))])])})],2),t._v(" "),t._l(t.controlData.rings,function(e,a){return n("div",{key:a,staticClass:"curr-phase"},[n("div",{staticClass:"curr-ring"},[n("span",[t._v(t._s(t.$t("edge.control.ring"))+": ")]),n("span",[t._v(t._s(e.num))])]),t._v(" "),n("div",{staticClass:"phase-order"},[n("span",[t._v(t._s(t.$t("edge.control.sequence"))+" ")]),n("span",[t._v(t._s(e.sequence))])])])})],2),t._v(" "),n("div",{staticClass:"current-phasetable"},[n("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData,"span-method":t.objectSpanMethod,"cell-class-name":t.addClass,size:"mini"}},[n("el-table-column",{attrs:{align:"center",prop:"ring",label:"环"}}),t._v(" "),n("el-table-column",{attrs:{align:"center",prop:"id",label:"相位id"}}),t._v(" "),n("el-table-column",{attrs:{align:"center",prop:"phase_countdown",label:"相位倒计时"}}),t._v(" "),n("el-table-column",{attrs:{align:"center",prop:"split",label:"绿信比"}}),t._v(" "),n("el-table-column",{attrs:{align:"center",prop:"type",label:"相位类型"}})],1)],1)])]),t._v(" "),n("div",{staticClass:"container-right"},[n("div",{staticClass:"control-right"},[n("el-form",{ref:"form",attrs:{model:t.form,"label-width":"80px"}},[""!=t.form.mode?n("el-form-item",{attrs:{label:t.$t("edge.control.mode_style")}},[n("div",[t._v(t._s(t.form.mode))])]):t._e(),t._v(" "),n("el-form-item",{attrs:{label:t.$t("edge.control.control_style")}},[n("el-select",{attrs:{placeholder:t.$t("edge.common.select")},model:{value:t.form.control,callback:function(e){t.$set(t.form,"control",e)},expression:"form.control"}},[n("el-option",{attrs:{label:"默认",value:"0"}}),t._v(" "),n("el-option",{attrs:{label:"黄闪",value:"1"}}),t._v(" "),n("el-option",{attrs:{label:"全红",value:"2"}}),t._v(" "),n("el-option",{attrs:{label:"关灯",value:"3"}}),t._v(" "),n("el-option",{attrs:{label:"步进",value:"4"}}),t._v(" "),n("el-option",{attrs:{label:"定周期控制",value:"5"}}),t._v(" "),n("el-option",{attrs:{label:"单点感应控制",value:"6"}}),t._v(" "),n("el-option",{attrs:{label:"协调感应控制",value:"7"}}),t._v(" "),n("el-option",{attrs:{label:"方案选择控制",value:"8"}}),t._v(" "),n("el-option",{attrs:{label:"自适应控制",value:"9"}}),t._v(" "),n("el-option",{attrs:{label:"优化控制",value:"10"}})],1)],1),t._v(" "),n("el-form-item",{attrs:{label:t.$t("edge.control.pattern")}},[n("el-input",{staticStyle:{width:"70%"},model:{value:t.form.pattern,callback:function(e){t.$set(t.form,"pattern",e)},expression:"form.pattern"}})],1),t._v(" "),n("el-form-item",[n("el-button",{attrs:{type:"primary",size:"small"},on:{click:t.onSubmit}},[t._v(t._s(t.$t("edge.common.setup")))]),t._v(" "),n("el-button",{attrs:{type:"primary",size:"small"},on:{click:t.onGet}},[t._v(t._s(t.$t("edge.common.query")))])],1)],1)],1)])])},staticRenderFns:[]};var f=n("VU/8")(v,h,!1,function(t){n("77RQ"),n("Cnlm")},"data-v-c38e99ee",null);e.default=f.exports},Cnlm:function(t,e,n){var a=n("MuY/");"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);n("rjj0")("78c2f050",a,!0)},"MuY/":function(t,e,n){(t.exports=n("FZ+f")(!1)).push([t.i,"\n.container-right .el-form-item__label {\n  text-align: right;\n  float: left;\n  font-size: 14px;\n  line-height: 40px;\n  padding: 0 12px 0 0;\n  -webkit-box-sizing: border-box;\n  box-sizing: border-box;\n  font-weight: normal;\n  font-stretch: normal;\n  letter-spacing: 0px;\n  color: #999999;\n}\n.container-right .el-select {\n  width: 70%;\n}\n.current-phasetable .yellow {\n  color: #ffd000;\n}\n.current-phasetable .red {\n  color: #ff1b1b;\n}\n.current-phasetable .green {\n  color: #20b84e;\n}\n",""])},qT8u:function(t,e,n){(t.exports=n("FZ+f")(!1)).push([t.i,'\n.container-main[data-v-c38e99ee] {\n  width: 100%;\n  height: 880px;\n  background: #f8f8f8;\n}\n.container-left[data-v-c38e99ee] {\n  float: left;\n  margin-top: 20px;\n  margin-left: 20px;\n  width: 76.8%;\n  height: 840px;\n  background-color: #ffffff;\n  border: solid 1px #e6e6e6;\n}\n.container-right[data-v-c38e99ee] {\n  float: left;\n  margin-top: 20px;\n  margin-left: 20px;\n  width: 20%;\n  height: 840px;\n  background-color: #ffffff;\n  border: solid 1px #e6e6e6;\n}\n.container-left-top[data-v-c38e99ee] {\n  width: 100%;\n  height: 420px;\n}\n.container-left-bottom[data-v-c38e99ee] {\n  width: 100%;\n  height: 400px;\n  background-color: #ffffff;\n}\n.current-phase[data-v-c38e99ee] {\n  width: 96%;\n  height: 10px;\n  margin-top: 1%;\n  margin-left: 2%;\n  font-size: 16px;\n  font-weight: normal;\n  font-stretch: normal;\n  line-height: 10px;\n  letter-spacing: 0px;\n  color: #333333;\n}\n.current-phasetable[data-v-c38e99ee] {\n  width: 96%;\n  height: 380px;\n  margin-top: 2%;\n  margin-left: 2%;\n}\n.color-block[data-v-c38e99ee] {\n  float: left;\n  width: 14px;\n  height: 14px;\n  background-color: #459ffc;\n}\n.curr-phase[data-v-c38e99ee] {\n  float: left;\n  margin-left: 5px;\n  margin-top: 1px;\n}\n.curr-ring[data-v-c38e99ee] {\n  float: left;\n  margin-left: 20px;\n  margin-top: 1px;\n}\n.phase-order[data-v-c38e99ee] {\n  float: left;\n  margin-left: 20px;\n  margin-top: 1px;\n}\n.curr-order[data-v-c38e99ee] {\n  float: left;\n  margin-left: 20px;\n  margin-top: 1px;\n}\n.control-right[data-v-c38e99ee] {\n  margin-top: 40px;\n  margin-left: 20px;\n}\n.agent-div[data-v-c38e99ee] {\n  float: left;\n  width: 28%;\n  height: 420px;\n  border-bottom: solid 0.5px #e6e6e6;\n}\n.other-div[data-v-c38e99ee] {\n  float: left;\n  width: 24%;\n  height: 210px;\n  border-left: solid 1px #e6e6e6;\n  border-bottom: solid 1px #e6e6e6;\n}\n.agent-icon[data-v-c38e99ee] {\n  position: relative;\n  float: left;\n  width: 50%;\n  height: 210px;\n}\n.agent-num[data-v-c38e99ee] {\n  float: left;\n  width: 50%;\n  height: 210px;\n}\n.lianji-success[data-v-c38e99ee] {\n  position: relative;\n  float: right;\n  right: 30px;\n  top: 25px;\n  text-align: center;\n  width: 81px;\n  height: 34px;\n  background-color: #409eff;\n  border-radius: 4px;\n  font-size: 14px;\n  padding: 10px 0;\n  color: #ffffff;\n}\n.lianji-fail[data-v-c38e99ee] {\n  position: relative;\n  float: right;\n  right: 30px;\n  top: 25px;\n  text-align: center;\n  width: 81px;\n  height: 34px;\n  background-color: #909399;\n  border-radius: 4px;\n  font-size: 14px;\n  padding: 10px 0;\n  color: #ffffff;\n}\n.lianji-wait[data-v-c38e99ee] {\n  position: relative;\n  float: right;\n  right: 30px;\n  top: 25px;\n  text-align: center;\n  width: 110px;\n  height: 34px;\n  background-color: #e6a23c;\n  border-radius: 4px;\n  font-size: 14px;\n  padding: 10px 0;\n  color: #ffffff;\n}\n.agent-id[data-v-c38e99ee] {\n  margin-top: 20px;\n  margin-right: 30px;\n  text-align: right;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  letter-spacing: 0px;\n  color: #999999;\n}\n.agent-number[data-v-c38e99ee] {\n  margin-top: 10px;\n  margin-right: 30px;\n  text-align: right;\n  font-size: 16px;\n  font-weight: normal;\n  font-stretch: normal;\n  letter-spacing: 0px;\n  color: #333333;\n}\n.agent-port[data-v-c38e99ee] {\n  margin-top: 20px;\n  margin-right: 30px;\n  text-align: right;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  letter-spacing: 0px;\n  color: #999999;\n}\n.port-number[data-v-c38e99ee] {\n  margin-top: 10px;\n  margin-right: 30px;\n  text-align: right;\n  font-size: 16px;\n  font-weight: normal;\n  font-stretch: normal;\n  letter-spacing: 0px;\n  color: #333333;\n}\n.model-name[data-v-c38e99ee] {\n  float: left;\n  margin-top: 32px;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  letter-spacing: 0px;\n  color: #666666;\n}\n.model-tupian[data-v-c38e99ee] {\n  float: left;\n  margin-top: 20px;\n  margin-left: 20px;\n}\n.model-icon[data-v-c38e99ee] {\n  width: 40px;\n  height: 40px;\n}\n.control-center[data-v-c38e99ee] {\n  text-align: center;\n  margin-top: 40px;\n  font-size: 30px;\n  font-weight: normal;\n  font-stretch: normal;\n  line-height: 20px;\n  letter-spacing: 0px;\n  color: #333333;\n}\n.curr-grade[data-v-c38e99ee] {\n  margin-top: 30px;\n  font-size: 24px;\n  font-weight: normal;\n  font-stretch: normal;\n  line-height: 14px;\n  letter-spacing: 0px;\n  color: #666666;\n}\n.curr-num[data-v-c38e99ee] {\n  margin-top: 20px;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  line-height: 14px;\n  letter-spacing: 0px;\n  color: #999999;\n}\n.icon-ziyuan[data-v-c38e99ee]:before {\n  content: "\\E670";\n  position: absolute;\n  z-index: 2;\n  left: 45px;\n  top: 72px;\n}\n.yuanxing[data-v-c38e99ee] {\n  position: absolute;\n  left: 20px;\n  top: 50px;\n  z-index: 1;\n  width: 90px;\n  height: 90px;\n  opacity: 0.2;\n  border-radius: 50%;\n}\n.dev-status[data-v-c38e99ee] {\n  position: absolute;\n  top: 150px;\n  height: 21px;\n  font-size: 22px;\n  font-weight: normal;\n  font-stretch: normal;\n  line-height: 14px;\n  letter-spacing: 0px;\n}\n',""])}});