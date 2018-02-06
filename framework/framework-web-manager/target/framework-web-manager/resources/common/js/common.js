/********************************************
*  js公共封装方法
*  create by ljs at 2017-08-13
*********************************************/

/**
 * 公共方法对象
 */
var commonObj = {
	/**
	 * 页面数据交互
	 * @param Template {Json Object} 数据交互模板
	 * @param async {Boolean} 同步异步
	 * @param isFillTemplate {Boolean} 是否填充交互模板
	 * @param isFillPage {Boolean} 是否填充页面
	 * @param isLockScreen {Boolean} 是否锁屏
	 * @param isShowResultMsg {Boolean} 是否显示返回信息
	 * @param isShowChildPage {Boolean} 返回信息后是否显示子页面
	 * @param callback {function} 回调函数
     * @param result {Return} 返回结果
	 */
	"bindPageData" : function(Template, async, isFillTemplate, isFillPage, isLockScreen, isShowResultMsg, isShowChildPage, callback) {
	    //返回结果
		var result = null;
	    //模板深度复制
		var filledTemplate = jQuery.extend(true, {}, Template);
		//是否填充模板
		if(isFillTemplate) {
			filledTemplate = dataLinkObj.fillTemplate(Template);
		}
		//是否锁屏
		if(isLockScreen) {
			top.lockScreen();
		}
		//日志记录
		top.gLog.info("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]开始......");
		//发起请求
		$.ajax({
	        type : "post",
	        url : this.getContextPath() + "/dispatcher",
            async:async,
	        data : {"serviceParam":JSON.stringify(filledTemplate.Service)},
	        dataType : 'json',
	        success : function(data){
	        	//session失效或服务重启后直接操作页面
                if(null == data || data.responseHeader.errorCode == "20009"){
                    location.href = commonObj.getContextPath() + "/login";
                }
                //日志记录
                top.gLog.info("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]成功!");
                
                //返回结果赋值
	        	result = data.responseHeader.errorCode;
	        	
	        	//一级菜单不可编辑删除、原密码新密码不可一致
	        	if(result == "20011"||result == "20013"){
                    $.layer.alert(data.responseHeader.message);
                    //result = false;
                    return result;
                }
                
	        	//修改原密码错误
                if(result == "20012"){
	        	    $("#oldPassword").val("");
                    $.layer.alert(data.responseHeader.message);
                    result = false;
                    return result;
                }
                
                //修改密码 新密码和确认密码不一致
                if(result == "20008"){
                    $("#newPassword").val("");
                    $("#confirmPassword").val("");
                    $.layer.alert(data.responseHeader.message);
                    result = false;
                    return result;
                }
                
	        	//是否填充页面
	        	if(isFillPage) {
	        		dataLinkObj.fillPage(Template, data);
	        	}
	        	
	        	//是否解屏
	        	if(isLockScreen) {
	        		top.solutionScreen();
	        	}
	        	
	        	//是否返回结果信息
	        	if(isShowResultMsg) {
                    top.showReturnBox(data.responseHeader, isShowChildPage);
	        	}
	        	
	        	//是否刷新页面
//	        	if("0" === refresh) {
//	        		
//	        	}else if("1" === refresh) {
//	        		window.location.reload();
//	        	}else if("2" === refresh) {
//	        		commonObj.bindPageData(refreshTemplate, true, false, true, false, true, true, "0", null, null);
//	        	}else if("3" === refresh) {
//	        		Refresh(refreshId);
//	        	}
	        	
	        	//回调函数
                if(null != callback 
                	&& undefined != callback 
                	&& "" != callback
                	&& "function" === typeof callback) {
              		callback(data);
              	}
	        },
	        error : function(jqXHR, textStatus, errorThrown){
	        	//日志记录
	        	top.gLog.error("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]失败!");
	        	//是否解屏
	        	if(isLockScreen) {
	        		top.solutionScreen();
	        	}
	        }
	    });
		
		//返回结果
        return result;
	},
    /***
     * 前后端数据交互并进行回调
     * @param Template {Json Object} 数据交互模板
     * @param isFillTemplate {Boolean} 是否填充交互模板
     * @param isFillPage {Boolean} 是否填充页面
     * @param callback {function} 回调函数
     * **/
    "bindDataAndCallback":function(Template, isFillTemplate, isFillPage, callback){
        //是否填充模板
        var filledTemplate = "";
        if(isFillTemplate) {
            filledTemplate = dataLinkObj.fillTemplate(Template);
        }else {
            filledTemplate = jQuery.extend(true, {}, Template);
        }
        
        //锁屏
        top.lockScreen();
        
        //日志记录
		top.gLog.info("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]开始......");
        
        //发起请求
        $.ajax({
            type : "post",
            url : this.getContextPath() + "/dispatcher",
            async:true,
            data : {"serviceParam":JSON.stringify(filledTemplate.Service)},
            dataType : 'json',
            success : function(data){
                //session失效或者服务重启后直接操作页面
                if(null == data ||data.responseHeader.errorCode == "20009"){
                    location.href = commonObj.getContextPath() + "/login";
                }
                //日志记录
                top.gLog.info("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]成功!");
                
                //是否填充页面
                if(isFillPage) {
                    dataLinkObj.fillPage(Template, data);
                }
                
                //解屏
                top.solutionScreen();
                
                //回调函数
                if(null != callback 
                	&& undefined != callback 
                	&& "" != callback
                	&& "function" === typeof callback) {
              		callback(data);
              	}
            },
            error : function(jqXHR, textStatus, errorThrown){
                //日志记录
                top.gLog.error("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]失败!");
                
                //解屏
                top.solutionScreen();
            }
        });


    },
    /**
	 * 页面填充数据字典
	 * @param pageId_typeArray {String} 页面ID、数据字典类型字符串数组
	 *                                  如：["sexId-5", "nationId-8"]
	 */
	"fillPageDataDictionary" : function(pageId_typeArray) {
		if(null != pageId_typeArray && undefined != pageId_typeArray && "" != pageId_typeArray) {
			for(var i=0; i<pageId_typeArray.length; i++) {
				var pageId = pageId_typeArray[i].split("-")[0];
				var currentDataDictionary = parent.gDataDictionary[pageId_typeArray[i].split("-")[1]];
				if(currentDataDictionary!= null){
                    for(var j=0; j<currentDataDictionary.length; j++) {
                        $("#"+pageId).append("<option value='" + currentDataDictionary[j].code + "'>"+ currentDataDictionary[j].name + "</option>");
                    }
                }
			}
		}
	},
    /**
	 * 获取项目根路径
	 * @returns {String} 项目根路径
	 */
	"getContextPath" : function() {
	    var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0,index+1);
	    return result;
	},
    //转换base64
    "getBase64Image":function (img) {
        var canvas = document.createElement("canvas");
        canvas.width = img.width;
        canvas.height = img.height;
        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, img.width, img.height);
        var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();
        var dataURL = canvas.toDataURL("image/"+ext);
        return dataURL;
    },

    /**
     * @param dateContainer {Object} 日期控件对象
     * */
    "dateControl":function (dateContainer) {
        var start = {
            onClose:true,
            format: dateContainer.format,
            //isinitVal:true,
            //initDate:[{DD:"-7"},true],
            //minDate:startMinDate, //设定最小日期为当前日期
            maxDate: dateContainer.startMaxDate, //最大日期
            okfun: function(obj){
                end.minDate = $("#"+dateContainer.startTag).val();//.split(" ")[0]+" 00:00:00"; //开始日选好后，重置结束日的最小日期
                endDates();
            }
        };
        //开始日期控件初始化时间设置
        if (dateContainer.startInitFlag === true) {
            start.isinitVal = dateContainer.startInitFlag;
            start.initDate=[{DD:dateContainer.startInitValue},true];
        }
        //开始时间控件最小日期设置
        if (dateContainer.startMinDate!="") {
            start.minDate=dateContainer.startMinDate;
        }
        var end = {
            onClose:true,
            format: dateContainer.format,
            //isinitVal:true,
            minDate: dateContainer.endMinDate, //设定最小日期为当前日期
            //maxDate: endMaxDate, //最大日期
            okfun: function(obj){
                start.maxDate =  $("#"+dateContainer.endTag).val();//.split(" ")[0]+" 00:00:00";; //将结束日的初始值设定为开始日的最大日期
                startDates();
            }
        };
        if(dateContainer.endInitFlag === true) {
            end.isinitVal=dateContainer.endInitFlag;
        }
        //结束日期最大日期设置
        if (dateContainer.endMaxDate!="") {
            end.maxDate = dateContainer.endMaxDate;
        }
        //这里是日期联动的关键
        function endDates() {
            //将结束日期的事件改成 false 即可
            if (dateContainer.endInitFlag === true) {
                end.isinitVal = false;
            }
            end.trigger = false;
            $("#"+dateContainer.endTag).jeDate(end);
        }
        //这里是日期联动的关键
        function startDates() {
            //将结束日期的事件改成 false 即可
            if (dateContainer.startInitFlag === true) {
                start.isinitVal=false;
            }
            start.trigger = true;
            $("#"+dateContainer.startTag).jeDate(start);
        }
        $("#"+dateContainer.startTag).jeDate(start);
        $("#"+dateContainer.endTag).jeDate(end);
    },
    /***
     * 弹出框拖动效果
     * **/
    "g":function(id){
        return document.getElementById(id);
    },
    
    /**
     * 对话框自动居中
     */
    "autoCenter":function(e1){
        //获取可见窗口大小
        var bodyW = document.documentElement.clientWidth;
        var bodyH = document.documentElement.clientHeight;
        //获取对话框宽、高
        var elW = e1.offsetWidth;
        var elH = e1.offsetHeight;
        e1.style.left=(bodyW - elW)/2 + 'px';
        e1.style.top=(bodyH - elH)/2 + 'px';
    },
    
    /**
     * 弹出框拖拽
     */
    "dragDialog":function(dialog){
        //禁止选中对话框内容
        if(document.attachEvent) {//ie的事件监听，拖拽div时禁止选中内容，firefox与chrome已在css中设置过-moz-user-select: none; -webkit-user-select: none;
            commonObj.g(dialog).attachEvent('onselectstart', function() {
                return false;
            });
        }
        //窗口大小改变时，对话框始终居中
        window.onresize = function(){
            commonObj.autoCenter(commonObj.g(dialog));
        };

        //声明需要用到的变量
        var mx = 0,my = 0;      //鼠标x、y轴坐标（相对于left，top）
        var dx = 0,dy = 0;      //对话框坐标（同上）
        var isDraging = false;      //不可拖动

        //鼠标按下
        var classArray=document.getElementsByClassName("movePart");
        for(var m=0;m<classArray.length;m++){
	        classArray[m].addEventListener('mousedown',function(e){
	            var e = e || window.event;
	            mx = e.pageX;      //点击时鼠标X坐标
	            my = e.pageY;      //点击时鼠标Y坐标
	            dx = commonObj.g(dialog).offsetLeft;
	            dy = commonObj.g(dialog).offsetTop;
	            isDraging = true;   //标记对话框可拖动
	        });
	        //鼠标离开
	        classArray[m].addEventListener('mouseup',function(){
	            isDraging = false;
	        });
	    }
        document.onmousemove = function(e){
            var e = e || window.event;
            var x = e.pageX;      //移动时鼠标X坐标
            var y = e.pageY;      //移动时鼠标Y坐标
            if(isDraging){        //判断对话框能否拖动
                var moveX = dx + x - mx;      //移动后对话框新的left值
                var moveY = dy + y - my;      //移动后对话框新的top值
                //设置拖动范围
                var pageW = document.documentElement.clientWidth;
                var pageH = document.documentElement.clientHeight;
                var dialogW = commonObj.g(dialog).offsetWidth;
                var dialogH = commonObj.g(dialog).offsetHeight;
                var maxX = pageW - dialogW;       //X轴可拖动最大值
                var maxY = pageH - dialogH;       //Y轴可拖动最大值
                moveX = Math.min(Math.max(0,moveX),maxX);     //X轴可拖动范围
                moveY = Math.min(Math.max(0,moveY),maxY);    //Y轴可拖动范围
                commonObj.g(dialog).style.left = moveX +'px';       //重新设置对话框的left
                commonObj.g(dialog).style.top =  moveY +'px';        //重新设置对话框的top
            };
        };
    },
    
    /***
     * 滚动条效果
     */
    "scrollBar":function(){
        $(".content").mCustomScrollbar({
            theme:"dark-3",
            scrollButtons:{enable:true}
        });
    }

}

/**
 * 公共按钮对象
 */
var commonButtonObj = {
    /**
     * 初始化
     * @param TableId {String} 页面表格ID
     */
    "initButton": function (TableId) {
        $(".checkAll").click(function () {
            if ($(this).prop("checked") == true) {
                $("#" + TableId + " > tbody input").prop("checked", true);
            } else {
                $("#" + TableId + " > tbody input").prop("checked", false);
            }
        });
        $(document.body).on("click",".checkOne",function(){
            if($(this).prop("checked") == true) {
                var flag=true;
                var tableInput=$("#" + TableId + " > tbody input");
                for(var i=0;i<tableInput.length;i++){
                    if(tableInput.eq(i).prop("checked")==false){
                        flag=false;
                    }
                }
                if(flag) {
                    $(".checkAll").prop("checked", true);
                }
            }else{
                $(".checkAll").prop("checked", false);
            }
        })
    },

    /**
     * 触发按钮
     * @param TriggerId {String} 触发按钮ID
     * @param ValidateAreaId {String} 校验区域ID
     * @param TableId {String} 页面表格ID
     * @param TemplateContainer {Json Object} 数据交互模板或模板容器
     */
    "triggerButton": function (TriggerId, ValidateAreaId, TableId, TemplateContainer) {
    	//按钮ID触发监听
        $("#" + TriggerId).click(function () {
            //选中记录数
            var checkedNum = $("#" + TableId + ">tbody input:checked").length;
            
            //公共增加
            if ("add" === TriggerId) {
                var addPage = commonButtonObj.getButtonPage("add");
                top.modelAdd(addPage, ValidateAreaId, TemplateContainer);
            }
            
            //公共修改
            else if ("edit" === TriggerId) {
                if (checkedNum == 0 || checkedNum > 1) {
                    $(".promptInfor").html("请选择一条要编辑的数据！");
                    $(".promptInfor").css("display", "block");
                    setTimeout(function () {
                        $(".promptInfor").css("display", "none");
                    }, 1000);
                } else {
                    var checkId = $("#" + TableId + ">tbody input:checked")[0].value;
                    //判断菜单编辑是否是一级菜单的情况,如果result是false，则是一级菜单不能编辑删除
                    if(TableId=="systemMenuTable"){
                        $("#configId_modify").val(checkId);
                        var result = commonObj.bindPageData(templateContainer.detail, false, true, true, false, false, false, null);
                        if(result != "0"){
                            return result;
                        }
                    }
                    var editPage = commonButtonObj.getButtonPage("edit");
                    top.modelEdit(editPage, checkId, ValidateAreaId, TemplateContainer);
                }
            }
            
            //公共删除
            else if ("del" === TriggerId) {
                if(checkedNum == 0 || checkedNum > 1){
                    $(".promptInfor").html("请选择一条要删除的数据！");
                    $(".promptInfor").css("display", "block");
                    setTimeout(function () {
                        $(".promptInfor").css("display", "none");
                    }, 1000);
                } else {
                    var checkId = $("#" + TableId + ">tbody input:checked")[0].value;
                    $("#deleteId").val(checkId);
                    $.layer.confirm("确定删除么", function () {
                        paginationObj.paginationRefresh(TemplateContainer.del, null, true);
                    });
		        }
		    }
            
            //公共查询
            else if("search" === TriggerId) {
                if (TableId == "systemLogTable" && ValidateAreaId == "operation") {
                    //区域校验
                    if (!validatorObj.validate(ValidateAreaId, 1)) {
                        return false;
                    }
                }
                paginationObj.paginationQuery(TemplateContainer.list, true);
            }
            
            //系统管理员重置密码
            else if ("resetPwd" === TriggerId) {
                if (checkedNum == 0 || checkedNum > 1) {
                    $(".promptInfor").html("请选择一条要重置密码的数据！");
                    $(".promptInfor").css("display", "block");
                    setTimeout(function () {
                        $(".promptInfor").css("display", "none");
                    }, 1000);
                } else {
                    var resetPwdPage = commonButtonObj.getButtonPage("resetPwd");
                    var checkId = $("#" + TableId + ">tbody input:checked")[0].value;
                    if (checkId == 1) {
                        $.layer.alert("超级管理员密码不能重置");
                        return false;
                    }
                    $.layer.confirm("确定重置密码么", function () {
                        //删除和重置公用一个页面Id
                        $("#deleteId").val(checkId);
                        paginationObj.paginationRefresh(TemplateContainer.resetPwd, null, true);
                    });
                }
            }
            
            //系统管理员给用户分配角色
            else if ("assign" === TriggerId) {
                if (checkedNum == 0 || checkedNum > 1) {
                    $(".promptInfor").html("请选择一条要分配角色的数据！");
                    $(".promptInfor").css("display", "block");
                    setTimeout(function () {
                        $(".promptInfor").css("display", "none");
                    }, 1000);
                } else {
                    var assignRolesPage = commonButtonObj.getButtonPage("assign");
                    var checkId = $("#" + TableId + ">tbody input:checked")[0].value;
                    if (checkId == 1) {
                        $.layer.alert("超级管理员角色不能修改");
                        return false;
                    }
                    top.modelAssignRoles(assignRolesPage, checkId, ValidateAreaId, TemplateContainer);
                }
            }
            
            //设置权限
            else if ("permission" === TriggerId) {
                if (checkedNum == 0 || checkedNum > 1) {
                    $(".promptInfor").html("请选择一条要分配菜单的数据！");
                    $(".promptInfor").css("display", "block");
                    setTimeout(function () {
                        $(".promptInfor").css("display", "none");
                    }, 1000);
                } else {
                    var permissionPage = commonButtonObj.getButtonPage("permission");
                    var checkId = $("#" + TableId + ">tbody input:checked")[0].value;
                    if (checkId == 1) {
                        $.layer.alert("超级管理员权限不能修改");
                        return false;
                    }
                    top.modelPermission(permissionPage, checkId, TemplateContainer);
                }
            }
        });
    },
    
    /**
     * @param pageId   要显示详情页的id
     * @param templateContainer 模板
     */
    "triggerDetailButton":function (pageId, template) {
        $("#"+pageId).css("display","block");
        $(".modalDialog").css("display","block");
        commonObj.autoCenter(commonObj.g(pageId));
        commonObj.dragDialog(pageId);

        //模板深度复制
        var filledTemplate = dataLinkObj.fillTemplate(template);
        //日志记录
		top.gLog.info("前后端数据交互,服务名[ "+ template.Service.Header.ServiceName +" ]开始......");
        //发起请求
        $.ajax({
            type : "post",
            url : commonObj.getContextPath() + "/dispatcher",
            async:false,
            data : {"serviceParam":JSON.stringify(filledTemplate.Service)},
            dataType : 'json',
            success : function(data){
                //session失效或者服务重启后直接操作页面
                if(null == data ||data.responseHeader.errorCode == "20009"){
                    location.href = commonObj.getContextPath() + "/login";
                }
                //日志记录
        		top.gLog.info("前后端数据交互,服务名[ "+ template.Service.Header.ServiceName +" ]成功......");
                dataLinkObj.fillPage(template,data);
            }
        });

        //关闭弹出详情对话框
        $(".cancel").click(function(){
            $("#"+pageId).css("display","none");
            $(".modalDialog").css("display","none");
        })
    },

    /**
     * 获取按钮页面
     * @param type 按钮类型
     */
    "getButtonPage": function (type) {
        var page = null;
        var htmlPage = "";
        if ("add" === type) {
            page = document.getElementById("addPage");
        } else if ("edit" === type) {
            page = document.getElementById("editPage");
        } else if ("del" === type) {
            page = document.getElementById("delPage");
        } else if ("resetPwd" === type) {
            page = document.getElementById("resetPwdPage");
        } else if ("permission" === type) {
            page = document.getElementById("permissionPage");
        } else if ("assign" === type) {
            page = document.getElementById("assignRolesPage");
        }
        if (null != page && undefined != page && "" != page) {
            htmlPage = page.innerHTML;
        }
        return htmlPage;
    },

    /**
     * 页面重置按钮
     * **/
    "resetButton": function (btnId, iframeId) {
        $("#" + btnId).click(function () {
            var inputs = $($("#" + iframeId).context.getElementById("operation")).find("input");
            for (var i = 0; i < inputs.length; i++) {
                $(inputs[i]).val("");
            }
            var selects = $($("#" + iframeId).context.getElementById("operation")).find("select");
            for (var i = 0; i < selects.length; i++) {
                $(selects[i]).val(0);
            }
            if (iframeId == "toInterface") {
                $(selects[1]).empty();
                $(selects[1]).append("<option value='0'>全部</option>");
                $(selects[1]).attr("disabled", "disabled");
            }
        })
    },
    
    /**
     * 页面刷新按钮
     * *@param TriggerId {String} 触发按钮ID
     * @param iframeId {String} 当前内联框架ID
     * @param TemplateContainer {Json Object} 数据交互模板或模板容器
     * **/
    "refreshButton": function (btnId, iframeId,TemplateContainer) {
        $("#" + btnId).click(function () {
            location.reload();
        });
    },
    /**
     * 表格导出按钮
     * **/
    "exportButton": function (btnId, TableId, ifId,urlId) {
        $("#" + btnId).click(function(){
            //表格导出按钮
            var formStr = "";
            formStr += "<form  id='exportForm'  action='"+ commonObj.getContextPath() +urlId+"' method='post'>";
            formStr += "<input type='text' name='enumExcel' value='OperateRecordLogExport'/>"
            var inputs = $($("#" + ifId).context.getElementById("operation")).find("input");
            for (var i = 0; i < inputs.length; i++) {
                var valType = $(inputs[i]).attr("data-type");
                //var val = $(inputs[i]).attr("query-value");
                var val = $(inputs[i]).val();
                //formStr += "<input type='text' name='queryList["+ i  + "]' value='"+valType+"="+val+"'/>";
                formStr += "<input type='text' name='"+ valType  + "' value='"+val+"'/>";
            }
           /* var tableChecked = $("#" + TableId + ">tbody input:checked");
            for(var j=0;j<tableChecked.length;j++){
                var cheVal=$("#" + TableId + ">tbody input:checked").eq(j).val();
                formStr+="<input type='text' name='checkedIdList["+j+"]' value='"+cheVal+"'/>";
            }*/
            formStr+="</form>";
            $("#ecportHiddenDiv").html(formStr);
            $("#exportForm").submit();
            /* var selects=$($("#"+iframeId).context.getElementById("operation")).find("select");
             for(var i=0;i<selects.length;i++){
             $(selects[i]).val(0);
             }*/
        })
    }

}


/**
 * 验证码错误弹出
 */
var verifyErrorObj = {
	/**
     * 显示错误信息模式对话框
     * @param msg 提示信息
     * @param num 提示信息图片样式 1：警告图标，2：错误图标
     */
    "showModal" : function (msg,num) {
        $("#verifyCode_msg").html(msg);
        this.secondCountDown();
        if(num==1){
            $("#dialogIcon").attr("src","resources/common/image/gantanhao.png");
        }else if(num==2){
            $("#dialogIcon").attr("src","resources/common/image/chahao.png");
        }
        $(".modalDialog").css("display", "block");
        $("#verifyError").css("display","block");
        commonObj.autoCenter(commonObj.g("verifyError"));
    },
    /**
     * 计算时间差
     */
    "getDateDiff": function (startTime, endTime, diffType) {
        //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
        startTime = startTime.replace(/\-/g, "/");
        endTime = endTime.replace(/\-/g, "/");
        //将计算间隔类性字符转换为小写
        diffType = diffType.toLowerCase();
        var sTime =new Date(startTime); //开始时间
        var eTime =new Date(endTime); //结束时间
        //作为除数的数字
        var timeType =1;
        switch (diffType) {
            case"second":
                timeType =1000;
                break;
            case"minute":
                timeType =1000*60;
                break;
            case"hour":
                timeType =1000*3600;
                break;
            case"day":
                timeType =1000*3600*24;
                break;
            default:
                break;
        }
        return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(timeType));
    },
	/**
	 * 时间计数器
	 */
    "secondCountDown" : function () {
        $(document.body).on("click",".verifyCodeSure",function(){
            $(".modalDialog").css("display", "none");
            $("#verifyError").css("display","none");
        });
    }
}