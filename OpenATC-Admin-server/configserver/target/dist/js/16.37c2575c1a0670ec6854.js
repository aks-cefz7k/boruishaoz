webpackJsonp([16],{"Gp+g":function(n,e,t){var o=t("V6rY");"string"==typeof o&&(o=[[n.i,o,""]]),o.locals&&(n.exports=o.locals);t("rjj0")("41decc61",o,!0)},V6Zp:function(n,e,t){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=t("woOf"),i=t.n(o),l=t("BO1k"),a=t.n(l),r=t("Dd8w"),s=t.n(r),c=t("FQkR"),d=t("NYxO"),u={name:"detector",components:{},data:function(){return{tableHeight:760,screenHeight:window.innerHeight,listLoading:!1,CallPhaseOption:[],id:1,modeOption:[{value:0,label:0},{value:1,label:1}],typeOption:[{value:0,label:"线圈"},{value:1,label:"智慧路口终端"}]}},directives:{clickoutside:{bind:function(n,e,t){function o(t){if(n.contains(t.target))return!1;e.expression&&e.value(t)}n.__vueClickOutside__=o,document.addEventListener("click",o)},update:function(){},unbind:function(n,e){document.removeEventListener("click",n.__vueClickOutside__),delete n.__vueClickOutside__}}},filters:{statusFilter:function(n){return{published:"success",draft:"gray",deleted:"danger"}[n]}},computed:s()({},Object(d.c)({detectorList:function(n){return n.globalParam.tscParam.detectorList}})),created:function(){this.globalParamModel=this.$store.getters.globalParamModel,this.init()},mounted:function(){var n=this;n.$nextTick(function(){n.tableHeight=window.innerHeight-document.querySelector("#footerBtn").offsetTop-50,window.onresize=function(){n.screenHeight=window.innerHeight}})},watch:{screenHeight:function(){this.tableHeight=window.innerHeight-document.querySelector("#footerBtn").offsetTop-50},detectorList:function(){this.init()}},methods:{init:function(){this.initData(),this.increaseId()},initData:function(){var n=this.globalParamModel.getParamsByType("phaseList");this.CallPhaseOption=[];for(var e=0;e<n.length;e++){var t,o={},i=n[e].id;t=n[e].direction.length>0&&void 0!==n[e].direction?i+"-"+Object(c.a)(n[e].direction,this.$i18n.locale):i,o.value=i,o.label=t,this.CallPhaseOption.push(o)}var l=this.CallPhaseOption.map(function(n){return n.value}),r=this.globalParamModel.getParamsByType("detectorList"),s=!0,d=!1,u=void 0;try{for(var m,p=a()(r);!(s=(m=p.next()).done);s=!0){var h=m.value;h.callphase=l.includes(h.callphase)?h.callphase:""}}catch(n){d=!0,u=n}finally{try{!s&&p.return&&p.return()}finally{if(d)throw u}}},increaseId:function(){var n=this.globalParamModel.getParamsByType("detectorList"),e=n.map(function(n){return n.id});if(n.length-1>=0)for(var t=1;t<=72;t++)if(!e.includes(t))return void(this.id=t)},handleEdit:function(n,e){},handleDelete:function(n){var e=this;this.$confirm(this.$t("edge.detector.deletetip"),this.$t("edge.common.alarm"),{confirmButtonText:this.$t("edge.common.confirm"),cancelButtonText:this.$t("edge.common.cancel"),type:"warning"}).then(function(){e.globalParamModel.deleteParamsByType("detectorList",n,1),e.$message({type:"success",message:e.$t("edge.common.deletesucess")})}).catch(function(){e.$message({type:"info",message:e.$t("edge.common.deletecancel")})})},deleteAllData:function(){var n=this;this.$confirm(this.$t("edge.detector.deleteAlltip"),this.$t("edge.common.alarm"),{confirmButtonText:this.$t("edge.common.confirm"),cancelButtonText:this.$t("edge.common.cancel"),type:"warning"}).then(function(){var e=n.globalParamModel.getParamsByType("detectorList");n.globalParamModel.deleteParamsByType("detectorList",0,e.length),n.id=1,n.$message({type:"success",message:n.$t("edge.common.deletesucess")})}).catch(function(){n.$message({type:"info",message:n.$t("edge.common.deletecancel")})})},handleClone:function(n,e){var t={};(this.increaseId(),this.globalParamModel.getParamLength("detectorList")>=72)?this.$message.error(this.$t("edge.detector.mostdata")):(i()(t,e),t.id=this.id,this.globalParamModel.addParamsByType("detectorList",t),this.globalParamModel.getParamsByType("detectorList").sort(this.compareProperty("id")))},onAdd:function(){if(this.increaseId(),this.globalParamModel.getParamLength("detectorList")>=72)this.$message.error(this.$t("edge.detector.mostdata"));else{var n={id:this.id,type:0,callphase:"",noactivity:0,maxpresence:0,erraticcounts:0,failtime:0,enable:1,mode:0,presenttime:240,sense:3,sampletime:100,inthresh:50,outthresh:20,infilter:3,outfilter:3,saturationflow:0};this.globalParamModel.addParamsByType("detectorList",n),this.globalParamModel.getParamsByType("detectorList").sort(this.compareProperty("id"))}},compareProperty:function(n){return function(e,t){return e[n]-t[n]}},cancelTable:function(n){this.$refs.singleTable.setCurrentRow()},getPhaseById:function(n){var e=this.globalParamModel.getParamsByType("phaseList");if(void 0===n||""===n)return"";var t=e.filter(function(e){return e.id===n})[0].direction;if(0===t.length)return n;var o=Object(c.a)(t,this.$i18n.locale);return n+"-"+o}}},m={render:function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("div",{staticClass:"app-container"},[t("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{type:"primary"},on:{click:n.onAdd}},[n._v(n._s(n.$t("edge.common.add")))]),n._v(" "),t("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{type:"primary"},on:{click:n.deleteAllData}},[n._v(n._s(n.$t("edge.common.deleteall")))]),n._v(" "),t("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:n.listLoading,expression:"listLoading",modifiers:{body:!0}},{name:"clickoutside",rawName:"v-clickoutside",value:n.cancelTable,expression:"cancelTable"}],ref:"singleTable",staticClass:"tb-edit",attrs:{data:n.detectorList,"element-loading-text":"Loading",fit:"","highlight-current-row":"","max-height":n.tableHeight,id:"footerBtn"}},[t("el-table-column",{attrs:{align:"center",label:"No","min-width":"40"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("span",[n._v(n._s(e.$index+1))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:"ID","min-width":"40"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("span",[n._v(n._s(e.row.id))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.desc")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input",{attrs:{size:"small"},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.desc,callback:function(t){n.$set(e.row,"desc",t)},expression:"scope.row.desc"}}),n._v(" "),t("span",[n._v(n._s(e.row.desc))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.type"),"min-width":"200"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-select",{attrs:{placeholder:n.$t("edge.common.select"),size:"small",clearable:""},model:{value:e.row.type,callback:function(t){n.$set(e.row,"type",t)},expression:"scope.row.type"}},n._l(n.typeOption,function(e){return t("el-option",{key:e.value,attrs:{label:n.$t("edge.detector.typeOption"+e.value),value:e.value}})}),1),n._v(" "),t("span",[n._v(n._s(n.$t("edge.detector.typeOption"+e.row.type)))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.callPhase"),"min-width":"200"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-select",{attrs:{placeholder:n.$t("edge.common.select"),size:"small",clearable:""},model:{value:e.row.callphase,callback:function(t){n.$set(e.row,"callphase",t)},expression:"scope.row.callphase"}},n._l(n.CallPhaseOption,function(n){return t("el-option",{key:n.value,attrs:{label:n.label,value:n.value}})}),1),n._v(" "),t("span",[n._v(n._s(n.getPhaseById(e.row.callphase)))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.noactivity"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:255,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.noactivity,callback:function(t){n.$set(e.row,"noactivity",t)},expression:"scope.row.noactivity"}}),n._v(" "),t("span",[n._v(n._s(e.row.noactivity))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.maxpresence"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:255,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.maxpresence,callback:function(t){n.$set(e.row,"maxpresence",t)},expression:"scope.row.maxpresence"}}),n._v(" "),t("span",[n._v(n._s(e.row.maxpresence))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.erraticCounts"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:255,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.erraticcounts,callback:function(t){n.$set(e.row,"erraticcounts",t)},expression:"scope.row.erraticcounts"}}),n._v(" "),t("span",[n._v(n._s(e.row.erraticcounts))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.failtime"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:255,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.failtime,callback:function(t){n.$set(e.row,"failtime",t)},expression:"scope.row.failtime"}}),n._v(" "),t("span",[n._v(n._s(e.row.failtime))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.enable"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:255,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.enable,callback:function(t){n.$set(e.row,"enable",t)},expression:"scope.row.enable"}}),n._v(" "),t("span",[n._v(n._s(e.row.enable))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.mode")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-select",{attrs:{size:"small"},model:{value:e.row.mode,callback:function(t){n.$set(e.row,"mode",t)},expression:"scope.row.mode"}},n._l(n.modeOption,function(n){return t("el-option",{key:n.value,attrs:{label:n.label,value:n.value}})}),1),n._v(" "),t("span",[n._v(n._s(e.row.mode))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.presenttime"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:30,max:3600,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.presenttime,callback:function(t){n.$set(e.row,"presenttime",t)},expression:"scope.row.presenttime"}}),n._v(" "),t("span",[n._v(n._s(e.row.presenttime))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.sense"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:9,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.sense,callback:function(t){n.$set(e.row,"sense",t)},expression:"scope.row.sense"}}),n._v(" "),t("span",[n._v(n._s(e.row.sense))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.sampletime"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:100,max:2e3,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.sampletime,callback:function(t){n.$set(e.row,"sampletime",t)},expression:"scope.row.sampletime"}}),n._v(" "),t("span",[n._v(n._s(e.row.sampletime))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.inthresh"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:20,max:1e3,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.inthresh,callback:function(t){n.$set(e.row,"inthresh",t)},expression:"scope.row.inthresh"}}),n._v(" "),t("span",[n._v(n._s(e.row.inthresh))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.outthresh"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:10,max:500,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.outthresh,callback:function(t){n.$set(e.row,"outthresh",t)},expression:"scope.row.outthresh"}}),n._v(" "),t("span",[n._v(n._s(e.row.outthresh))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.infilter"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:10,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.infilter,callback:function(t){n.$set(e.row,"infilter",t)},expression:"scope.row.infilter"}}),n._v(" "),t("span",[n._v(n._s(e.row.infilter))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.outfilter"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:10,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.outfilter,callback:function(t){n.$set(e.row,"outfilter",t)},expression:"scope.row.outfilter"}}),n._v(" "),t("span",[n._v(n._s(e.row.outfilter))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.saturationflow"),"min-width":"150"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:0,max:10,step:1},on:{change:function(t){return n.handleEdit(e.$index,e.row)}},model:{value:e.row.saturationflow,callback:function(t){n.$set(e.row,"saturationflow",t)},expression:"scope.row.saturationflow"}}),n._v(" "),t("span",[n._v(n._s(e.row.saturationflow))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.detector.operation")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-button",{attrs:{type:"text"},on:{click:function(t){return n.handleClone(e.$index,e.row)}}},[n._v(n._s(n.$t("edge.common.clone")))]),n._v(" "),t("el-button",{attrs:{type:"text"},on:{click:function(t){return n.handleDelete(e.$index)}}},[n._v(n._s(n.$t("edge.common.delete")))])]}}])})],1)],1)},staticRenderFns:[]};var p=t("VU/8")(u,m,!1,function(n){t("Gp+g")},null,null);e.default=p.exports},V6rY:function(n,e,t){(n.exports=t("FZ+f")(!1)).push([n.i,"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n  /* * {\n    margin: 0;\n    padding: 0} */\nbody {\n      overflow: auto;\n      font-weight: 400;\n      -webkit-font-smoothing: antialiased;\n}\n.tb-edit .el-input {\n    display: none\n}\n.tb-edit .current-row .el-input {\n    display: block\n}\n.tb-edit .current-row .el-input+span {\n    display: none\n}\n.tb-edit .el-select {\n    display: none\n}\n.tb-edit .current-row .el-select {\n    display: block\n}\n.tb-edit .current-row .el-select+span {\n    display: none\n}\n.tb-edit .el-input-number {\n    display: none\n}\n.tb-edit .current-row .el-input-number {\n  display: block;\n  margin-left: 30%;\n}\n.tb-edit .current-row .el-input-number+span {\n  display: none\n}\n",""])}});