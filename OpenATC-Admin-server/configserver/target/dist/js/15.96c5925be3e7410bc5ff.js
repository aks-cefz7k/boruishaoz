webpackJsonp([15],{Hqjd:function(n,e,t){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=t("BO1k"),l=t.n(a),o=t("woOf"),i=t.n(o),s=t("Dd8w"),r=t.n(s),c=t("FQkR"),d=t("NYxO"),h=[{value:1,label:"edge.channel.other"},{value:2,label:"edge.channel.phaseVehicle"},{value:3,label:"edge.channel.phasePedestrian"},{value:4,label:"edge.channel.overlap"}],u={name:"channel",components:{},data:function(){return{tableHeight:760,screenHeight:window.innerHeight,listLoading:!1,id:1,typeOptions:[{value:2,label:"机动车相位"},{value:3,label:"行人相位"},{value:4,label:"跟随相位"},{value:5,label:"跟随行人相位"},{value:0,label:"不启用"}],etypeOptions:[{value:2,label:"phaseVehicle"},{value:3,label:"phasePedestrian"},{value:4,label:"overlap"}],signLocationList:["东","南","西","北","东北","东南","西南","西北"]}},filters:{statusFilter:function(n){return{published:"success",draft:"gray",deleted:"danger"}[n]}},computed:r()({TypeOption:function(){var n=this,e=[];return h.forEach(function(t){var a=i()({},t);a.label=n.$t(a.label),e.push(a)}),e}},Object(d.c)({channelList:function(n){return n.globalParam.tscParam.channelList}})),created:function(){this.globalParamModel=this.$store.getters.globalParamModel,this.init()},mounted:function(){var n=this;n.$nextTick(function(){n.tableHeight=window.innerHeight-document.querySelector("#footerBtn").offsetTop-50,window.onresize=function(){n.screenHeight=window.innerHeight}})},watch:{screenHeight:function(){this.tableHeight=window.innerHeight-document.querySelector("#footerBtn").offsetTop-50},channelList:function(){this.init()}},methods:{init:function(){this.initData(),this.increaseId()},initData:function(){var n=this.getControSource(),e=this.getOverLap();"en"===this.$i18n.locale&&(this.typeOptions=this.etypeOptions),this.typeOptions[0].children=n,this.typeOptions[1].children=n,this.typeOptions[2].children=e;var t=this.globalParamModel.getParamsByType("channelList"),a=!0,o=!1,i=void 0;try{for(var s,r=l()(t);!(a=(s=r.next()).done);a=!0){var c=s.value,d=[];d.push(c.controltype),d.push(c.controlsource),c.typeAndSouce=d}}catch(n){o=!0,i=n}finally{try{!a&&r.return&&r.return()}finally{if(o)throw i}}},handleChange:function(n,e){var t=this.globalParamModel.getParamsByType("channelList");t[e].controltype=n[0],t[e].controlsource=n[1]},getControSource:function(){for(var n=[],e=this.globalParamModel.getParamsByType("phaseList"),t=0;t<e.length;t++){var a,l={},o=e[t].id;a=e[t].direction.length>0&&void 0!==e[t].direction?o+"-"+Object(c.a)(e[t].direction,this.$i18n.locale):o,l.value=o,l.label=a,n.push(l)}return n},getOverLap:function(){for(var n=[],e=this.globalParamModel.getParamsByType("overlaplList"),t=0;t<e.length;t++){var a,l={},o=e[t].id;a=""!==e[t].desc&&void 0!==e[t].desc?o+"-"+e[t].desc:o,l.value=o,l.label=a,n.push(l)}return n},increaseId:function(){var n=this.globalParamModel.getParamsByType("channelList"),e=n.map(function(n){return n.id});if(n.length-1>=0)for(var t=1;t<=40;t++)if(!e.includes(t))return void(this.id=t)},handleEdit:function(n,e){},handleDelete:function(n){var e=this;this.$confirm(this.$t("edge.channel.deletetip"),this.$t("edge.common.alarm"),{confirmButtonText:this.$t("edge.common.confirm"),cancelButtonText:this.$t("edge.common.cancel"),type:"warning"}).then(function(){e.globalParamModel.deleteParamsByType("channelList",n,1),e.$message({type:"success",message:e.$t("edge.common.deletesucess")})}).catch(function(){e.$message({type:"info",message:e.$t("edge.common.deletecancel")})})},deleteAllData:function(){var n=this;this.$confirm(this.$t("edge.channel.deleteAlltip"),this.$t("edge.common.alarm"),{confirmButtonText:this.$t("edge.common.confirm"),cancelButtonText:this.$t("edge.common.cancel"),type:"warning"}).then(function(){var e=n.globalParamModel.getParamsByType("channelList");n.globalParamModel.deleteParamsByType("channelList",0,e.length),n.id=1,n.$message({type:"success",message:n.$t("edge.common.deletesucess")})}).catch(function(){n.$message({type:"info",message:n.$t("edge.common.deletecancel")})})},handleClone:function(n,e){var t={};(this.increaseId(),0!==this.globalParamModel.getParamLength("phaseList"))?this.globalParamModel.getParamLength("channelList")>=40?this.$message.error(this.$t("edge.channel.mostdata")):(i()(t,e),t.id=this.id,this.globalParamModel.addParamsByType("channelList",t),this.globalParamModel.getParamsByType("channelList").sort(this.compareProperty("id"))):this.$message.error(this.$t("edge.pattern.firstphase"))},onAdd:function(){if(0!==this.globalParamModel.getParamLength("phaseList"))if(this.globalParamModel.getParamLength("channelList")>=40)this.$message.error(this.$t("edge.channel.mostdata"));else{this.increaseId();var n={id:this.id,desc:"",controlsource:1,controltype:2,typeAndSouce:[2,1],voltthresh:50,pacthresh:30,peakhthresh:400,peaklthresh:130};this.globalParamModel.addParamsByType("channelList",n),this.globalParamModel.getParamsByType("channelList").sort(this.compareProperty("id"))}else this.$message.error(this.$t("edge.pattern.firstphase"))},compareProperty:function(n){return function(e,t){return e[n]-t[n]}}}},p={render:function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("div",{staticClass:"app-container"},[t("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{type:"primary"},on:{click:n.onAdd}},[n._v(n._s(n.$t("edge.common.add")))]),n._v(" "),t("el-button",{staticStyle:{"margin-bottom":"10px"},attrs:{type:"primary"},on:{click:n.deleteAllData}},[n._v(n._s(n.$t("edge.common.deleteall")))]),n._v(" "),t("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:n.listLoading,expression:"listLoading",modifiers:{body:!0}}],attrs:{data:n.channelList,"element-loading-text":"Loading",fit:"","highlight-current-row":"","max-height":n.tableHeight,id:"footerBtn"}},[t("el-table-column",{attrs:{align:"center",label:"No","min-width":"40"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("span",[n._v(n._s(e.$index+1))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:"ID","min-width":"40"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("span",[n._v(n._s(e.row.id))])]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.channel.desc"),prop:"desc","min-width":"100"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-select",{attrs:{placeholder:n.$t("edge.common.select"),size:"small",clearable:""},model:{value:e.row.desc,callback:function(t){n.$set(e.row,"desc",t)},expression:"scope.row.desc"}},n._l(n.signLocationList,function(n){return t("el-option",{key:n,attrs:{label:n,value:n}})}),1)]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.channel.controlType"),"min-width":"200",prop:"typeAndSouce"},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-cascader",{staticClass:"config",attrs:{size:"medium","expand-trigger":"hover",options:n.typeOptions,clearable:""},on:{change:function(t){return n.handleChange(e.row.typeAndSouce,e.$index)}},model:{value:e.row.typeAndSouce,callback:function(t){n.$set(e.row,"typeAndSouce",t)},expression:"scope.row.typeAndSouce"}})]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.phase.lane")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input",{attrs:{size:"small",oninput:"this.value=this.value.replace(/[^\\d\\,]/g,'')"},model:{value:e.row.lane,callback:function(t){n.$set(e.row,"lane",t)},expression:"scope.row.lane"}})]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.channel.voltthresh")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:5,max:200,step:1},model:{value:e.row.voltthresh,callback:function(t){n.$set(e.row,"voltthresh",n._n(t))},expression:"scope.row.voltthresh"}})]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.channel.pacthresh")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:1,max:2e3,step:1},model:{value:e.row.pacthresh,callback:function(t){n.$set(e.row,"pacthresh",n._n(t))},expression:"scope.row.pacthresh"}})]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.channel.peakhthresh")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:100,max:1e3,step:1},model:{value:e.row.peakhthresh,callback:function(t){n.$set(e.row,"peakhthresh",n._n(t))},expression:"scope.row.peakhthresh"}})]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.channel.peaklthresh")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-input-number",{staticStyle:{width:"100px"},attrs:{size:"small","controls-position":"right",min:100,max:1e3,step:1},model:{value:e.row.peaklthresh,callback:function(t){n.$set(e.row,"peaklthresh",n._n(t))},expression:"scope.row.peaklthresh"}})]}}])}),n._v(" "),t("el-table-column",{attrs:{align:"center",label:n.$t("edge.channel.operation")},scopedSlots:n._u([{key:"default",fn:function(e){return[t("el-button",{attrs:{type:"text"},on:{click:function(t){return n.handleClone(e.$index,e.row)}}},[n._v(n._s(n.$t("edge.common.clone")))]),n._v(" "),t("el-button",{attrs:{type:"text"},on:{click:function(t){return n.handleDelete(e.$index)}}},[n._v(n._s(n.$t("edge.common.delete")))])]}}])})],1)],1)},staticRenderFns:[]};var m=t("VU/8")(u,p,!1,function(n){t("zWRb")},"data-v-86dcd39e",null);e.default=m.exports},"g+y/":function(n,e,t){(n.exports=t("FZ+f")(!1)).push([n.i,"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n/* * {\n   margin: 0;\n   padding: 0} */\nbody[data-v-86dcd39e] {\n     overflow: auto;\n     font-weight: 400;\n     -webkit-font-smoothing: antialiased;\n}\n /* .el-cascader {\n   display: inline-block;\n   position: relative;\n   font-size: 14px;\n   line-height: 40px;\n   width: 100%;\n } */\n.config[data-v-86dcd39e] {\n   width: 100%;\n}\n",""])},zWRb:function(n,e,t){var a=t("g+y/");"string"==typeof a&&(a=[[n.i,a,""]]),a.locals&&(n.exports=a.locals);t("rjj0")("e93fe5e0",a,!0)}});