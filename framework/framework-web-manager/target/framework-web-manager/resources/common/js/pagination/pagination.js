/********************************************
*  js分页封装方法
*  create by ljs at 2017-08-11
*********************************************/
//当前也码
var currentPage = 0;

/**
 * 公共分页对象
 */
var paginationObj = {
	/**
	 * 初始化分页
	 * @param Template {Json Object} 数据交互模板
	 * @param pagesize {Number} 页面记录条数
	 */
	"initPagination" : function(Template, pagesize){
		//初始化页面记录条数
		if(null != pagesize && undefined != pagesize && "" != pagesize) {
			$("#pageSize").val(pagesize);
		}else {
			$("#pageSize").val(10);
		}
		// 第一页按钮click事件
        $("#first").unbind();
		$("#first").click(function() {
			if (currentPage == 0 || currentPage == 1){
				return;
			}else{
				currentPage = 1;
			}
			$("#currentPage").html(currentPage);
			paginationObj.bindPaginationData(Template, false, true);
            $(".checkAll").attr("checked",false);
		});
		
		// 上一页按钮click事件
        $("#previous").unbind();
		$("#previous").click(function() {
			if (currentPage == 0 || currentPage == 1){
				return;
			}else{
				currentPage--;
			}
			$("#currentPage").html(currentPage);
			paginationObj.bindPaginationData(Template, false, true);
            $(".checkAll").attr("checked",false);
		});
		
		// 下一页按钮click事件
        $("#next").unbind();
		$("#next").click(function() {
			var totalPage = parseInt($("#totalPage").text());
			if (currentPage == 0 || currentPage == totalPage){
				return;
			}else if (currentPage != totalPage) {
				currentPage++;
			}
			$("#currentPage").html(currentPage);
			paginationObj.bindPaginationData(Template, false, true);
            $(".checkAll").attr("checked",false);
		});
		
		// 最后一页按钮click事件
        $("#last").unbind();
		$("#last").click(function() {
			var totalPage = parseInt($("#totalPage").text());
			if (currentPage == 0 || currentPage == totalPage){
				return;
			}else{
				currentPage = totalPage;
			}
			$("#currentPage").html(currentPage);
			paginationObj.bindPaginationData(Template, false, true);
            $(".checkAll").attr("checked",false);
		});
		
		// 跳转按钮click事件
        $("#jumpPageButton").unbind();
		$("#jumpPageButton").click(function() {
			var totalPage = parseInt($("#totalPage").text());
			var jumpPage = parseInt($("#jumpNo").val());
			if (isNaN(jumpPage) || jumpPage < 1 || jumpPage > totalPage){
				$("#currentPage").html(currentPage);
				return;
			}
			currentPage = jumpPage;
			$("#currentPage").html(currentPage);
			paginationObj.bindPaginationData(Template, false, true);
            $(".checkAll").attr("checked",false);
		});
	},
	
	/**
	 * 分页查询
	 * @param Template {Json Object} 数据交互模板
	 * @param isLockScreen {Boolean} 是否锁屏
	 */
	"paginationQuery" : function(Template, isLockScreen) {
		currentPage = 1;
		$("#currentPage").html(currentPage);
		this.bindPaginationData(Template, true, isLockScreen);
	},
	
	/**
	 * 表格操作后分页刷新
	 * @param Template {Json Object} 数据交互模板
	 * @param iframeId {String} 当前内联框架ID
	 * @param isLockScreen {Boolean} 是否锁屏
	 */
	"paginationRefresh" : function(Template, iframeId, isLockScreen) {
		//访问后台的结果
		var result = "";
		//填充模板
		var filledTemplate = dataLinkObj.fillTemplate(Template);
		//锁屏
		if(isLockScreen) {
            top.lockScreen();
		}
		//日志记录
		top.gLog.info("分页刷新,服务名[ "+ JSON.stringify(filledTemplate.Service) +" ]开始......");
		//发起请求
		$.ajax({
	        type : "post",
	        url : commonObj.getContextPath() + "/dispatcher",
	        data : {"serviceParam":JSON.stringify(filledTemplate.Service)},
	        dataType : 'json',
	        success : function(data){
                //session失效或者服务重启后直接操作页面
                if(null == data ||data.responseHeader.errorCode == "20009"){
                    location.href = commonObj.getContextPath() + "/login";
                }
                //日志记录
        		top.gLog.info("分页刷新,服务名[ "+ Template.Service.Header.ServiceName +" ]成功......");
	        	//角色是否强制删除，解绑用户
	        	if(data.responseHeader.errorCode == "20005" && data.responseHeader.message == "此角色已有关联账户,是否删除"){
                    //解屏
                    if(isLockScreen) {
                        top.solutionScreen();
                    }
	        		$.layer.confirm("此角色已有关联账户,是否删除?",function () {
                        $("#deleteIgnoreRelationship").val(data.responseHeader.errorCode);
                        paginationObj.paginationRefresh(Template,null,true);
                    })
				}else if(data.responseHeader.errorCode == "20011"|| data.responseHeader.errorCode == "20004"){
                    result = false;
	        		$.layer.alert(data.responseHeader.message);
                    //解屏
                    if(isLockScreen) {
                        top.solutionScreen();
                    }
                    return result;
				}else{
					result = true;
					//填充页面
                    dataLinkObj.fillPage(Template, data);
                    //跨框架刷新
                    if(null != iframeId && undefined != iframeId && "" != iframeId) {
                        $(window.parent.document).contents().find("#"+iframeId)[0].contentWindow.Refresh();
                    }
                    //当前页面刷新
                    else {
                        // window.location.reload();
                        Refresh();
                    }
                    //解屏
                    if(isLockScreen) {
                        top.solutionScreen();
                    }
                    //返回结果
                    top.showReturnBox(data.responseHeader, false);
				}
	        },
	        error : function(jqXHR, textStatus, errorThrown){
	        	//日志记录
        		top.gLog.error("分页刷新,服务名[ "+ Template.Service.Header.ServiceName +" ]失败......");
	        	//解屏
	        	if(isLockScreen) {
                    top.solutionScreen();
	        	}
	        }
	    });
        return result;
	},
	
	/**
	 * 装订分页数据
	 * @param Template {Json Object} 数据交互模板
	 * @param initalSign {Boolean} 是否重组查询条件
	 * @param isLockScreen {Boolean} 是否锁屏
	 */
	"bindPaginationData" : function(Template, initalSign, isLockScreen) {
		if(initalSign){
			//重组查询条件
			this.reformQueryValue();
			//填充模板
			var filledTemplate = dataLinkObj.fillTemplate(Template);
			//锁屏
			if(isLockScreen) {
               top.lockScreen();
			}
			//日志记录
            top.gLog.info("分页查询,服务[ "+ Template.Service.Header.ServiceName +" ]开始......");
			$.ajax({
				type : "post",
				url : commonObj.getContextPath() + "/dispatcher",
				data : {"serviceParam":JSON.stringify(filledTemplate.Service)},
				dataType : 'json',
				success : function(data){
					//滚动条
					$(".tableContent").mCustomScrollbar({
			            theme:"dark-3",
			            scrollButtons:{enable:true}
			        });
                    //session失效或者服务重启后直接操作页面
                    if(null == data ||data.responseHeader.errorCode == "20009"){
                        location.href = commonObj.getContextPath() + "/login";
                    }
					//日志记录
                    top.gLog.info("分页查询,服务[ "+ Template.Service.Header.ServiceName +" ]成功!");
					if (currentPage == 0 && data.totalRows != 0){
						currentPage = 1;
					}
					dataLinkObj.fillPage(Template, data);
					paginationObj.bindPager();
					//解屏
					if(isLockScreen) {
                        top.solutionScreen();
					}
				},
				error : function(jqXHR, textStatus, errorThrown){
					//日志记录
                    top.gLog.error("分页查询,服务[ "+ Template.Service.Header.ServiceName +" ]失败!");
					//解屏
					if(isLockScreen) {
                        top.solutionScreen();
					}
				}
			});
		}else {
			//填充模板
			var filledTemplate = dataLinkObj.fillTemplate(Template);
			//锁屏
			if(isLockScreen) {
                top.lockScreen();
			}
			//日志记录
            top.gLog.info("分页查询,服务[ "+ Template.Service.Header.ServiceName +" ]开始......");
			$.ajax({
				type : "post",
				url : commonObj.getContextPath() + "/dispatcher",
				data : {"serviceParam":JSON.stringify(filledTemplate.Service)},
				dataType : 'json',
				success : function(data){
                    //session失效或者服务重启后直接操作页面
                    if(null == data ||data.responseHeader.errorCode == "20009"){
                        location.href = commonObj.getContextPath() + "/login";
                    }
                    //日志记录
                    top.gLog.info("分页查询,服务[ "+ Template.Service.Header.ServiceName +" ]成功......");
					if (currentPage == 0 && data.totalRows != 0){
						currentPage = 1;
					}
					dataLinkObj.fillPage(Template, data);
					paginationObj.bindPager();
					//解屏
					if(isLockScreen) {
                      top.solutionScreen();
					}
				},
				error : function(jqXHR, textStatus, errorThrown){
					//日志记录
		            top.gLog.error("分页查询,服务[ "+ Template.Service.Header.ServiceName +" ]失败......");
					//解屏
					if(isLockScreen) {
                        top.solutionScreen();
					}
				}
			});
		}
	},
	
	/**
	 * 组装查询条件
	 */
	"reformQueryValue" : function() {
		var inputs = $(document.getElementById("operation")).find("input");
        for (var i = 0; i < inputs.length; i++) {
            var value = $(inputs[i]).val();
            if("undefined" != typeof($(inputs[i]).attr("query-value"))) {
            	$(inputs[i]).attr("query-value", value);
            }else {
            	
            }
        }
        var selects = $(document.getElementById("operation")).find("select");
        for (var i = 0; i < selects.length; i++) {
            var value = $(selects[i]).val();
            if("undefined" != typeof($(inputs[i]).attr("query-value"))) {
            	$(selects[i]).attr("query-value", value);
            }else {
            	
            }
        }
	},
	
	/**
	 * 分页按钮控制
	 */
	"bindPager" : function() {
		var totalPage = parseInt($("#totalPage").text());
		document.getElementById("first").style.color = (currentPage == 1 || totalPage <= 1) ? "#777" : "";
		document.getElementById("previous").style.color = (currentPage == 1 || totalPage <= 1) ? "#777" : "";
		document.getElementById("next").style.color = (currentPage == 0 || totalPage == currentPage) ? "#777" : "";
		document.getElementById("last").style.color = (currentPage == 0 || totalPage == currentPage) ? "#777" : "";
	},
	
    /**
     * 角色强制删除分页刷新
     * @param Template {Json Object} 数据交互模板
     * @param iframeId {String} 当前内联框架ID
     * @param isLockScreen {Boolean} 是否锁屏
     */
    "paginationRoleDelRefresh" : function(Template, iframeId, isLockScreen) {
        //填充模板
        var filledTemplate = dataLinkObj.fillTemplate(Template);
        //锁屏
        if(isLockScreen) {
            top.lockScreen();
        }
        //日志记录
		top.gLog.info("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]开始......");
        //发起请求
        $.ajax({
            type : "post",
            url : commonObj.getContextPath() + "/dispatcher",
            data : {"serviceParam":JSON.stringify(filledTemplate.Service)},
            dataType : 'json',
            success : function(data){
                //session失效或者服务重启后直接操作页面
                if(null == data ||data.responseHeader.errorCode == "20009"){
                    location.href = commonObj.getContextPath() + "/login";
                }
                //日志记录
        		top.gLog.info("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]成功......");
            	if(data.errorCode == ""){

            		$.layer.confirm("此角色已有关联账户，是否删除?",function () {

                    });
				}else{
                    paginationObj.paginationRoleDelRefresh(TemplateContainer.del,null,true);
				}
                //填充页面
                dataLinkObj.fillPage(Template, data);
                //跨框架刷新
                if(null != iframeId && undefined != iframeId && "" != iframeId) {
                    $(window.parent.document).contents().find("#"+iframeId)[0].contentWindow.Refresh();
                }
                //当前页面刷新
                else {
                    //window.location.reload();
                    Refresh();
                }
                //解屏
                if(isLockScreen) {
                    top.solutionScreen();
                }
                //返回结果
                top.showReturnBox(data.responseHeader, false);
            },
            error : function(jqXHR, textStatus, errorThrown){
            	//日志记录
        		top.gLog.error("前后端数据交互,服务名[ "+ Template.Service.Header.ServiceName +" ]失败......");
                //解屏
                if(isLockScreen) {
                    top.solutionScreen();
                }
            }
        });
    }
}