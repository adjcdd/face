/********************************************
*  数据页面链接
*  create by ljs at 2017-08-10
*********************************************/
//对象数据容器
var objDataContainer = null;

/**
 * 数据连接对象
 */
var dataLinkObj = {
	/**
	 * 填充模板
	 */
	"fillTemplate" : function(Template) {
		//模板克隆,深度复制
		var fillingTemplate = jQuery.extend(true, {}, Template);
		//前端输数据模板
		var Input = fillingTemplate.Service.Input;
		//前端输入类型
		var InputType = fillingTemplate.Service.Type.InputType;
		
		//前端输入类型为特殊类型时
		if(InputType != null && InputType != undefined && InputType != "") {
			//数组类型
			if(InputType.Array != null && InputType.Array != undefined && InputType.Array != "") {
				var pageDataId = InputType.Array;
				
                //特殊功能(角色管理)
                if("permissionTable" == pageDataId){
                    //组装正常模板
                    this.parsNode(Input);
                    //组装数组模板
                    var data = new Object();
                    var Fname=Input.Arrays[0][pageDataId].FieldName;
                    var key=Input.Arrays[0][pageDataId].key;
                    var boxs=$("#"+pageDataId+">tbody>tr td label").children("input[type=checkbox]");
                    data[Fname]=[];
                    for(var i=0;i<boxs.length;i++){
                        var ItemId="";
                        var data1=new Object();
                        if(boxs.eq(i).attr("checked")=="checked"){
							if(boxs.eq(i).attr("id")){
                                ItemId = boxs.eq(i).attr("id");
							}
                            data1[key] = ItemId;
                            data[Fname].push(data1);
                        }
                    }
                    fillingTemplate.Service.Input.Arrays[0] = data;
				}
				
                //特殊功能(组装用户角色)
                if("rolePojoList" == pageDataId){
                    //组装正常模板
                    this.parsNode(Input);
                    //组装数组模板
                    var data = new Object();
                    data[pageDataId] = [];
                    var rolePojoList=Input.Arrays[0];
                    var key=Input.Arrays[0][pageDataId][0].key;
                    var opts = document.getElementById("rolePojoSelectList").getElementsByTagName("li");
                    for(var i=0;i<opts.length;i++){
                        var data1 = new Object();
                        var ItemId = "";
                        ItemId = opts[i].firstChild.id;
                        data1[key] = ItemId;
                        data[pageDataId].push(data1);
					}
                    fillingTemplate.Service.Input.Arrays[0] = data;
                }
			}
		}
		//前端输入类型为正常类型时
		else {
			//解析前端输入数据模板
			this.parsNode(Input);
		}
		
		//返回已填充的克隆模板
		return fillingTemplate;
	},
	
	/**
	 * 填充页面
	 */
	"fillPage" : function(Template, ResponseData) {
		//模板克隆,深度复制
		var fillingPageTemplate = jQuery.extend(true, {}, Template);
		//后端返回类型
		var OutputType = fillingPageTemplate.Service.Type.OutputType;
		//后端返回模板
		var OutputTemplate = fillingPageTemplate.Service.Output;
		//后端返回数据
		var dataBody = ResponseData.responseBody;
		
		//后端有返回数据
		if(null != dataBody && undefined != dataBody && "" != dataBody) {
			//后端返回类型为特殊类型
			if(OutputType != null && OutputType != undefined && OutputType != "") {
				//表格类型
				if(OutputType.Table != null && OutputType.Table != undefined && OutputType.Table != "") {
					$("#"+OutputType.Table.Id+">tbody").html("");
					var str = "";
					var dataTemplate = null;
					var data = null;
					var primaryKey = OutputType.Table.DataPrimaryKey;
					var executeId = OutputType.Table.ExecuteId;
					var buttonName = OutputType.Table.ButtonName;
					var checkbox = OutputType.Table.Checkbox;
					if(ResponseData.responseBody instanceof Array) {
						dataTemplate = OutputTemplate.Objects.responseBody;
						data = ResponseData.responseBody;
					}else {
						dataTemplate = OutputTemplate.Objects.responseBody.data;
						data = ResponseData.responseBody.data;
						var pageNo = ResponseData.responseBody.pageNo;
						var pageSize = ResponseData.responseBody.pageSize;
					}
					
					if(data != null && data != undefined && data != "") {
						for(var i=0; i<data.length; i++) {
							str += "<tr>";
							//有按钮，有复选框在记录中
							if(null != buttonName && undefined != buttonName && "" != buttonName && checkbox=="true" ) {
								str += "<td style='width:5%;'><input type='checkbox' id='tableChecked' class='checkOne' value="+ data[i][primaryKey] +"></td>";
							}
							//表格既没有复选框也没有按钮null != buttonName && undefined != buttonName && "" != buttonName&&
							else if(checkbox=="false"){
                                str += "";
                                /*str += "<td style='width:5%; display:none'><input type='checkbox' id='tableChecked' class='checkOne' value="+ data[i][primaryKey] +"></td>";*/
							}
							//没按钮 有复选框
							else {
								if(executeId != null && executeId != undefined && executeId != "") {
									str += "<td style='width:5%'><input type='checkbox' id="+ data[i][executeId] +"  class='checkOne' value="+ data[i][primaryKey] +"></td>";
								}else {
									str += "<td style='width:5%'><input type='checkbox'  class='checkOne' value="+ data[i][primaryKey] +"></td>";
								}
							}
							for(var d=0; d<dataTemplate.length; d++) {
								//表格序号
								if("Serial Number" === dataTemplate[d].FieldId) {
									str += "<td align='center'>" + ((pageNo-1)*pageSize+(i+1)) + "</td>";
								}else {
									//表格隐藏字段
									if("HideField" === dataTemplate[d].FieldType) {
										str += "<td align='center' style='display:none'>" + data[i][dataTemplate[d].FieldId] + "</td>";
									}
									//数据字典字段
									else if(dataTemplate[d].FieldType.indexOf("DataDictionary") == 0) {
										var type = dataTemplate[d].FieldType.split("-")[1];
										var currentDataDictionary = parent.gDataDictionary[type];
										if(null != currentDataDictionary && undefined != currentDataDictionary && "" != currentDataDictionary) {
											if(null != data[i][dataTemplate[d].FieldId] && undefined != data[i][dataTemplate[d].FieldId] && "" !== data[i][dataTemplate[d].FieldId]) {
												var dictionary = "";
												for(var l=0; l<currentDataDictionary.length; l++) {
													if(currentDataDictionary[l].code == data[i][dataTemplate[d].FieldId]) {
														dictionary = "<td align='center'>" + currentDataDictionary[l].name + "</td>";
														break;
													}
												}
												if("" == dictionary) {
													dictionary = "<td align='center'></td>";
												}
												str += dictionary;
											}else {
												str += "<td align='center'></td>";
											}
										}
									}
									//时间类型字段
									else if(dataTemplate[d].FieldType.indexOf("Date") == 0) {
										if(null != data[i][dataTemplate[d].FieldId]
										&& undefined != data[i][dataTemplate[d].FieldId]
										&& "" != data[i][dataTemplate[d].FieldId]) {
											var datetype = dataTemplate[d].FieldType.split("-")[1];
											str += "<td align='center'>" + utilsObj.formatTime(datetype, data[i][dataTemplate[d].FieldId]) + "</td>";
										}else {
											str += "<td align='center'>" + utilsObj.transNullToBlank(null) + "</td>";
										}
									}
									//表格正常字段
									else {
                                        if(OutputType.Table.Id=="systemRoleTable"||OutputType.Table.Id=="systemUsersTable" || OutputType.Table.Id=="systemMenuTable"){
                                            	if(dataTemplate[d].FieldId=="telphone"){
                                            		var telphone = data[i][dataTemplate[d].FieldId];
                                                    if(null ===telphone || "" === telphone){
                                                        str +=  "<td align='center'></td>";
                                                    }else{
                                                        str +=  "<td align='center'>"+telphone+"</td>";
                                                    }
//												}else if(dataTemplate[d].FieldId=="createTime"||dataTemplate[d].FieldId=="updateTime"||dataTemplate[d].FieldId=="operateTime"||dataTemplate[d].FieldId=="acreateTime"){
//                                                    var fmtime = data[i][dataTemplate[d].FieldId];
//                                            		if(null ===fmtime || "" === fmtime){
//                                                        str +=  "<td align='center'></td>";
//                                                    }else{
//														str += "<td align='center'>"+utilsObj.formatTime("datetime", fmtime)+"</td>";
//                                                    }
												}else if(dataTemplate[d].FieldId=="rolePojoList"){
													var rolePojoList = data[i][dataTemplate[d].FieldId];
                                                    if(rolePojoList.length<1){
                                                        str += "<td align='center'></td>";
                                                    }else{
                                                    	var role = "";
                                                    	for(var m=0;m<rolePojoList.length;m++){
                                                    		role += rolePojoList[m]["roleName"]+",";
														}
                                                        str += "<td align='center' title='"+role.substring(0,role.length-1)+"' style='text-overflow:ellipsis;white-space:nowrap;overflow:hidden;'>"+role.substring(0,role.length-1)+"</td>";
                                                    }
                                                }else if(dataTemplate[d].FieldId=="authorityLevel"){
                                                    var authorityLevel = data[i][dataTemplate[d].FieldId];
                                                    if(authorityLevel == "0"){
                                                        str += "<td align='center'>特殊</td>";
                                                    }else{
                                                        str += "<td align='center'>一般</td>";
                                                    }
                                                }else if(dataTemplate[d].FieldId=="ctfNo"){
                                                    var ctfNo = data[i][dataTemplate[d].FieldId];
                                                    if(ctfNo!=null&&ctfNo!=undefined&&ctfNo!=""&&ctfNo.length>7){
                                                        var cardNO=ctfNo.substring(0,ctfNo.length-6)+"******";
                                                        str += "<td align='center'>"+cardNO+"</td>";
													}else{
                                                        str += "<td align='center'></td>";
													}
                                                }else{
                                                   str += "<td align='center'>" + utilsObj.transNullToBlank(data[i][dataTemplate[d].FieldId]) + "</td>";
                                            	}
                                            }
										else{
                                               str += "<td align='center'>" + utilsObj.transNullToBlank(data[i][dataTemplate[d].FieldId]) + "</td>";
                                            }
									}
								}
							}
							if(null != buttonName && undefined != buttonName && "" != buttonName) {
								str += "<td align='center'><input type='button' id='"+ data[i][primaryKey] +"' value='"+ buttonName +"' class='tableButton'></td>";
							}
							str += "</tr>";
						}
					}
					$("#"+OutputType.Table.Id+">tbody").html(str);
					
					//移除模板数据模板
					delete OutputTemplate.Objects.responseBody.data;
					//移除表格数据
					delete ResponseData.responseBody.data;
					//填充数据容器
					this.fillDataContainer(ResponseData.responseBody);
					//填充元素
					this.fillNode(OutputTemplate.Objects.responseBody);
				}
				
				//下拉框类型(相同数据的Select)
				if(OutputType.DataDictionary != null && OutputType.DataDictionary != undefined && OutputType.DataDictionary != "") {
					var selectIds = OutputType.DataDictionary.split("&");
					var key = OutputTemplate.Objects.responseBody.key;
					var value = OutputTemplate.Objects.responseBody.value;
					var arrayList = ResponseData.responseBody;
					if(arrayList != null && arrayList != undefined && arrayList != "") {
						for(var i=0; i<arrayList.length; i++) {
							for(var j=0; j<selectIds.length; j++) {
								$("#"+selectIds[j]).append("<option value='" + arrayList[i][key] + "'>"+ arrayList[i][value] + "</option>");
							}
						}
					}
				}
				
				//DIV类型(Div_ul)
				if(OutputType.Div!= null && OutputType.Div != undefined && OutputType.Div != ""){
					//已签到列表
					var str="";
					if("writeList" == OutputType.Div) {
                        //$("#writeList").    dataBody
						var str="";
						for(var i = 0; i < dataBody.length; i++){
                            str += "<li ><img src='data:image/png;base64,"+ dataBody[i].employeeSignPojo.imageBase64 +"' title='姓    名："+dataBody[i].name+"&#10;工    号："+dataBody[i].uid+"&#10;签到时间："+utilsObj.formatTime("time", dataBody[i].check_in_time)+"&#10;是否迟到："+dataBody[i].later+"'><p><label>"+dataBody[i].name+"签到成功</p></label>"
						}

					}else if("recordList"==OutputType.Div){
						var str="";
                        for(var i = 0; i < dataBody.length; i++){
                            str += "<li ><img src='data:image/png;base64,"+ dataBody[i].imageBase64 +"'>";
                        }
					}
                    $("#"+OutputType.Div).html(str);
					// var divUlIds = OutputType.Div_ul.split("&");
					// var str = "";
					// var key = OutputTemplate.Objects.responseBody.key;
					// var value = OutputTemplate.Objects.responseBody.value;
					// var arrayList = ResponseData.responseBody;
					// for (var k = 0; k < divUlIds.length; k++) {
					// 	$("#" + divUlIds[k]).html("");
					// }
					// if (arrayList != null && arrayList != undefined && arrayList != "") {
					// 	for (var i = 0; i < arrayList.length; i++) {
					// 		for (var j = 0; j < divUlIds.length; j++) {
					// 			$("#" + divUlIds[j]).append("<li data-type='" + arrayList[i][key] + "'><a>" + arrayList[i][value] + "</a></li>");
					// 		}
					// 	}
					// }
				}
				
                //特殊功能(系统用户页面下拉列表)
                if(OutputType.RoleSelects != null && OutputType.RoleSelects != undefined && OutputType.RoleSelects != "") {
                    var rolesIds = OutputType.RoleSelects.split("&");
                    for(var k =0;k<rolesIds.length;k++){
                    	var roleSelect = rolesIds[k];
                        var sel = ResponseData.responseBody[roleSelect];
                        for(var i =0;i<sel.length;i++){
                            if(sel[i]["status"] == "1"){
                                   $("#"+roleSelect).append("<li><input type='checkbox' id='"+sel[i]["id"]+"'/>"+sel[i]["roleName"]+"</li>");
                                }else{
                                   $("#"+roleSelect).append("<li><input type='checkbox' id='"+sel[i]["id"]+"' disabled/>"+sel[i]["roleName"]+"</li>");
                                }
						}
					}
                }

                //特殊功能(设置权限查询所有菜单)
                if(OutputType.permissionTable != null && OutputType.permissionTable != undefined && OutputType.permissionTable != ""){
                    var permissionTableId= OutputType.permissionTable;
                    var type=OutputType.type;
                    if(type!=""){
                    	var checkData=ResponseData.responseBody.authorityPojoList;
                    	for(var m=0;m<checkData.length;m++){
                    		var id=checkData[m].id;
                    		$("#"+id).attr("checked","checked");
						}
					}else{
                        var menus=eval("("+ResponseData.responseBody+")");
                        var trStr="";
                        for(var i=0;i<menus.length;i++){
                            var subs=menus[i].sub;
                            if(subs!=undefined){
                                trStr+="<tr><td rowSpan='"+subs.length+"'><label style='width:70px'><input type='checkbox' id='"+menus[i].id+"'/></label>"+menus[i].authorityName+"</td><td><label><input type='checkbox' class='"+menus[i].id+"' id='"+subs[0].id+"'/></label>"+subs[0].authorityName+"</td></tr>";
                                for(var j=1;j<subs.length;j++){
                                    trStr+="<tr><td><label><input type='checkbox' class='"+menus[i].id+"' id='"+subs[j].id+"'/></label>"+subs[j].authorityName+"</td></tr>";
                                }
                            }
                        }
                        $("#"+permissionTableId+">tbody").html(trStr);
					}
                }
			}
			//后端返回类型为正常类型
			else{
				//填充数据容器
                this.fillDataContainer(ResponseData);
                //填充元素
                this.fillNode(OutputTemplate);
			}
		}
	},
	
	/**
	 * 填充数据容器
	 */
	"fillDataContainer" : function(data) {
		//新构建对象数据容器
		objDataContainer = new Object();
		//解析后端返回数据
		this.parsData(data);
	},
	
	/**
	 * 解析节点
	 */
	"parsNode" : function(node) {
		//遍历模板节点对象属性
		for(key in node) {
			//此属性对应值是否需继续解析
			//while(this.isNeedParse(node, key, node[key])) {
			if(this.isNeedParse(node, key, node[key])) {
				//解析此属性对应值
				this.parsNode(node[key]);
			}
		}
	},
	
	/**
	 * 分解数据
	 */
	"parsData" : function(data) {
		//遍历数据对象属性
		for(key in data) {
			//判断此属性对应值是否可继续分解
			//while(this.isNeedResolve(key, data[key])) {
			if(this.isNeedResolve(key, data[key])) {
				//继续分解此属性对应的值
				this.parsData(data[key]);
			}
		}
	},
	
	/**
	 * 填充元素
	 */
	"fillNode" : function(node) {
		//遍历节点对象属性
		for(key in node) {
			//判断此属性对应值是否可继续解析
			//while(this.isNeedFill(key, node[key])) {
			if(this.isNeedFill(key, node[key])) {
				//继续解析此属性对应的值
				this.fillNode(node[key]);
			}
		}
	},
	
	/**
	 * 是否需解析
	 */
	"isNeedParse" : function(node, key, value) {
		//mod by ljs at 2017-09-26 start
//		if(value instanceof Array || value instanceof Object) {
//			return true;
//		}else if((typeof value=="string" && value.constructor == String) || (typeof value=="number" && value.constructor == Number)) {
//			this.setNode(node, key, value);
//		}
		//属性值属于Array,继续解析
		if(value instanceof Array) {
			//判断数组是否为空数组
			if(value.length > 0) {
				return true;
			}else {
				//对模板节点赋值为空字符串
				//this.setData(key, "");
				this.setNode(node, key, "");
			}
		}
		//属性值属于对象,继续解析
		else if(value instanceof Object) {
			return true;
		}
		//属性值属于基本类型,对模板节点进行赋值
		else if((typeof value=="string" && value.constructor == String) || (typeof value=="number" && value.constructor == Number)) {
			//对模板节点进行赋值
			this.setNode(node, key, value);
		}
		//mod by ljs at 2017-09-26 end
	},
	
	/**
	 * 是否需分解
	 */
	"isNeedResolve" : function(key, value) {
		//mod by ljs at 2017-09-26 start
//		if(value instanceof Array || value instanceof Object) {
//			return true;
//		}else if((typeof value=="string" && value.constructor == String) || (typeof value=="number" && value.constructor == Number)) {
//			this.setData(key, value);
//		}
		//属性对应的值为数组
		if(value instanceof Array) {
			//判断数组是否为空数组
			if(value.length > 0) {
				return true;
			}else {
				//将数据放入对象数据容器
				this.setData(key, "");
			}
		}
		//属性对应的值为对象
		else if(value instanceof Object) {
			return true;
		}
		//属性对应的值为基本类型
		else if((typeof value=="string" && value.constructor == String) || (typeof value=="number" && value.constructor == Number)) {
			//将数据放入对象数据容器
			this.setData(key, value);
		}
		//mod by ljs at 2017-09-26 end
	},
	
	/**
	 * 是否需填充
	 */
	"isNeedFill" : function(key, value) {
		//mod by ljs at 2017-09-26 start
//		if(value instanceof Array || value instanceof Object) {
//			return true;
//		}else if((typeof value=="string" && value.constructor == String) || (typeof value=="number" && value.constructor == Number)) {
//			this.setPage(key, value);
//		}
		//属性对应的值为数组
		if(value instanceof Array) {
			//判断数组是否为空数组
			if(value.length > 0) {
				return true;
			}else {
				//对页面元素赋值空字符串
				//this.setData(key, "");
				this.setPage(key, "");
			}
		}
		//属性对应的值为对象
		else if(value instanceof Object) {
			return true;
		}
		//属性对应的值为基本类型
		else if((typeof value=="string" && value.constructor == String) || (typeof value=="number" && value.constructor == Number)) {
			//对页面元素进行赋值
			this.setPage(key, value);
		}
		//mod by ljs at 2017-09-26 end
	},
	
	/**
	 * 设置模板节点
	 */
	"setNode" : function(node, key, value) {
		//获取页面元素对象
		var target = document.getElementById(value);
		//元素存在
		if(target) {
			//获取对象标签
			var tagName = target.tagName;
			//标签为LABEL、SPAN,用html()方法赋值
			if("LABEL" == tagName || "SPAN" == tagName) {
				node[key] = $("#"+value).html();
			}
			//标签为IMG
			else if("IMG" == tagName){
				//若图片元素有图片则为模板节点赋值(base64),否则赋空字符串
				if(null != $(target).attr("src") && undefined != $(target).attr("src") && "" != $(target).attr("src")) {
					node[key] = $(target).attr("src").split(",")[1];
                }else {
                    node[key] = "";
                }
			}
			//其他标签用val()赋值
			else {
				//元素有query-value属性时为查询框,将此属性值赋到模板节点上
				if("undefined" != typeof($(target).attr("query-value"))) {
					node[key] = $(target).attr("query-value");
				}
				//正常情况直接将页面元素的值赋到模板节点上
				else {
					node[key] = $("#"+value).val();
				}
			}
		}
	},
	
	/**
	 * 设置容器数据
	 */
	"setData" : function(key, value) {
		//对象数据容器添加数据
		objDataContainer[key] = value;
	},
	
	/**
	 * 设置页面元素
	 */
	"setPage" : function(key, value) {
		//获取页面元素对象
		var target = document.getElementById(value);
		//元素存在
		if(target) {
			//获取元素标签
			var tagName = target.tagName;
			//元素是否为数据字典类型
            var dictionary = $(target).attr("data-dictionary");
			//元素是否为日期类型
            var datetype = $(target).attr("date-format");
            //数据字典类型元素
            if(dictionary != "" && dictionary !=null){
                var dictionryValue = parent.gDataDictionary[dictionary];
                for(var k=0;k<dictionryValue.length;k++){
                    if(dictionryValue[k].code == objDataContainer[key] ){
                        if("LABEL" == tagName || "SPAN" == tagName) {
                            $("#"+value).html(dictionryValue[k].name);
                        }else{
                            $("#"+value).val(dictionryValue[k].name);
						}
					}
				}
			}
            //日期类型元素
            else if(null != datetype && undefined !== datetype && "" != datetype){
            	//标签为LABEL、SPAN,用html()方法赋值
            	if("LABEL" == tagName || "SPAN" == tagName) {
            		$("#"+value).html(utilsObj.formatTime(datetype, objDataContainer[key]));
            	}
            	//其他标签用val()赋值
            	else {
            		$("#"+value).val(utilsObj.formatTime(datetype, objDataContainer[key]));
            	}
			}
            //常规元素
            else{
            	//标签为LABEL、SPAN,用html()方法赋值
                if("LABEL" == tagName || "SPAN" == tagName) {
                	if(value == "detail_IdCardNumber"){
                        if(objDataContainer[key]!=null&&objDataContainer[key]!=undefined&&objDataContainer[key]!=""){
                            objDataContainer[key]=objDataContainer[key].substring(0,objDataContainer[key].length-6);
                            objDataContainer[key]=objDataContainer[key]+"******";
                        }
					}else if(value == "reducedResult"){
                		if(objDataContainer[key] == "0"){
                            objDataContainer[key] = "成功";
						}else{
                            objDataContainer[key] = "失败";
						}
					}
					$("#"+value).html(objDataContainer[key]);
                }
                //元素标签为IMG
                else if("IMG" == tagName){
                	//若对象数据容器有数据则为页面元素赋值(base64),否则不赋值
                	if(null != objDataContainer[key] && undefined != objDataContainer[key] && "" != objDataContainer[key]){
                        $("#"+value).attr("src", "data:image/png;base64,"+ objDataContainer[key]);
					}
                }
                //其他标签用val()赋值
                else{
                	//特殊功能(身份证号码隐藏后六位)
                	if(value=="checkNo"){
                        if(objDataContainer[key]!=null&&objDataContainer[key]!=undefined&&objDataContainer[key]!=""){
                            objDataContainer[key]=objDataContainer[key].substring(0,objDataContainer[key].length-6);
                            objDataContainer[key]=objDataContainer[key]+"******";
						}
					}
                	//正常情况直接对象数据容器的值赋到元素上
                    $("#"+value).val(objDataContainer[key]);
                }
			}
		}
	}
};