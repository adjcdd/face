/*首页*/
$(function(){
    var oneFlag=false;
	//启动前端日志
	gLog = new log4js(1,4);

    //获取session中存储的用户名
	//日志记录
	top.gLog.info("前后端数据交互,服务名[ "+ SessionData.Service.Header.ServiceName +" ]开始......");
    $.ajax({
        type : "post",
        url : commonObj.getContextPath() + "/dispatcher",
        async:false,
        data : {"serviceParam":JSON.stringify(SessionData.Service)},
        dataType : 'json',
        success : function(data){
            //session失效或者服务重启后直接操作页面
            if(null == data ||data.responseHeader.errorCode == "20009"){
                location.href = commonObj.getContextPath() + "/login";
            }
            //日志记录
    		top.gLog.info("前后端数据交互,服务名[ "+ SessionData.Service.Header.ServiceName +" ]成功......");
            $("#userName").html(data.responseBody.name);
        }
    });

    //获取导航菜单数据
    var navs=[];
    //日志记录
	top.gLog.info("前后端数据交互,服务名[ "+ TestAuthorityInfo.Service.Header.ServiceName +" ]开始......");
    $.ajax({
        type : "post",
        url : commonObj.getContextPath() + "/dispatcher",
        async:false,
        data : {"serviceParam":JSON.stringify(TestAuthorityInfo.Service)},
        dataType : 'json',
        success : function(data){
            //session失效或者服务重启后直接操作页面
            if(null == data ||data.responseHeader.errorCode == "20009"){
                location.href = commonObj.getContextPath() + "/login";
            }
            //日志记录
    		top.gLog.info("前后端数据交互,服务名[ "+ TestAuthorityInfo.Service.Header.ServiceName +" ]成功......");
            navs=eval("("+data.responseBody+")");
            if(navs.length==0){
                var firstIframe="";
                firstIframe="<iframe scrolling='no' height='100%' width='100%' marginwidth='0' marginheight='0' frameborder='0' src='resources/common/ui/noRole.html'></iframe>";
                $(".list").html(firstIframe);
            }else{
                //加载导航内容
                var navHtml="";
                for(var i=0;i<navs.length;i++){
                    navHtml+="<li><a class="+navs[i].className+"><p><img src="+navs[i].img+"></p><span>"+navs[i].name+"</span></a></li>";
                }
                $(".nav_ul").html(navHtml);
            }
        }
    });

    //加载缓存
    dataDictionaryObj.load(dataDictionaryTemplate);

    //退出点击事件
    $("#exit").click(function(){
    	//日志记录
		top.gLog.info("退出登录,服务开始......");
        $.ajax({
            type : "post",
            url : commonObj.getContextPath() + "/user/loginOut",
            dataType : 'json',
            success : function(data){
                //session失效或者服务重启后直接操作页面
                if(null == data ||data.responseHeader.errorCode == "20009"){
                    location.href = commonObj.getContextPath() + "/login";
                }
                //日志记录
        		top.gLog.info("退出登录,服务成功......");
                if(data && data.responseHeader.errorCode == "0"){
                    location.href = commonObj.getContextPath() + "/login";
                }
            },
            error : function(jqXHR, textStatus, errorThrown){
            	//日志记录
        		top.gLog.error("退出登录,服务失败......");
            }
        });
    });

    //修改密码点击事件
    $("#passwordModify").click(function(){
        $("#modifyPassword").css("display","block");
        $(".modalDialog").css("display","block");
        commonObj.autoCenter(commonObj.g("modifyPassword"));
        commonObj.dragDialog("modifyPassword");
    });

    //修改密码按钮点击事件
    $("#userPasswordModify").click(function() {
        var newPass=$.trim($("#newPassword").val());
        var conPass=$.trim($("#confirmPassword").val());
        //区域校验
        if(!validatorObj.validate("modifyPasswordArea",1)) {
            return false;
        }
        var result = commonObj.bindPageData(ModifyPassword, true, true, true, false, true, false, null);
        if(result == "20008"){
            $("#newPassword").val("");
            $("#confirmPassword").val("");
        }
    })

    //关闭修改密码弹框
    $(".passwordCancel").click(function(){
        $("#modifyPassword").css("display","none");
        $(".modalDialog").css("display","none");
        $("#userID").val("");
        $("#oldPassword").val("");
        $("#newPassword").val("");
        $("#confirmPassword").val("");
    })
    $(document.body).on("click","h4>img",function(){
        $("#buttonPage").html("");
        $("#buttonPage").css("display","none");
        $(".modalDialog").css("display","none");
    });

    //关闭所有标签页
    $(".closeAllTags").click(function(){
        var tagLen=$(".topTags>ul>li").length;
        if(tagLen!=0){
            $(".modalDialog").css("display","block");
            $("#closeTag").css("display","block");
            commonObj.autoCenter(commonObj.g("closeTag"));
            commonObj.dragDialog("closeTag");
        }else{
            return
        }
    });
    $(".cancelTag").click(function(){
        $("#closeTag").css("display","none");
        $(".modalDialog").css("display","none");
    });
    $(".closeTag").click(function(){
        $("#closeTag").css("display","none");
        $(".modalDialog").css("display","none");
        $(".topTags>ul>li").remove();
        $(".list").children("iframe").remove();
        var firstIframe="";
        firstIframe="<iframe id='noSubMenu' style='display: none' scrolling='no' height='100%' width='100%' marginwidth='0' marginheight='0' frameborder='0' src='resources/common/ui/noSubMenu.html'></iframe>";
        $(".list").html(firstIframe);
        $(".subMenu>li>a").css("color", "#474747");
        $(".closeAllTags").addClass("hiddenClass");
    });

    //导航菜单点击监听事件
    $(".nav_ul>li>a").click(function() {
        if(!oneFlag){
            $(".con_left").css("display","block");
            $(".con_right").css("width","87.4%");
            $("#openAndClose").attr("title","收缩");
            $("#openAndClose").css("left",-10);
            $("#openAndClose").attr("src","resources/common/image/arrow.png");
            oneFlag=true;
        }
        var currentClass = $(this).attr("class");
        //切换选中class
        $(this).parent().siblings("li.navCheck").removeClass("navCheck");
        $(this).parent().addClass("navCheck");
        //导航菜单加载子菜单
        var ind=$(this).parent().index();
        var subHtml="";
        if(navs[ind].sub != null && navs[ind].sub != undefined && navs[ind].sub != ""){
            for(var i=0;i<navs[ind].sub.length;i++){
                subHtml+="<li class='"+navs[ind].className+"'><a data-type="+navs[ind].sub[i].url+"><i></i>"+navs[ind].sub[i].name+"</a></li>";
            }
            $(".subMenu").html(subHtml);
        }else {
            $(".subMenu").html("");
        }
        var subMenuList=$(".subMenu>li");
        var id=subMenuList.eq(0).children("a").attr('data-type');
        var cla=subMenuList.eq(0).attr("class");
        var text=subMenuList.eq(0).children("a").text();
        var tagUl=$(".topTags>ul").children("li");
        var listIframe=$(".list iframe");
        var hasIframe=true;
        var iframeIndex=0;
        for(var j=0;j<listIframe.length;j++){
            var iframeSrc=listIframe.eq(j).attr("src");
            if(id==iframeSrc){
                iframeIndex=j;
                hasIframe=false;
            }
        }
        if(subMenuList.length==0){
            tagUl.children("a").removeClass("tagChecked");
            listIframe.css("display","none");
            $("#noSubMenu").css("display","inline");
        }
        if(hasIframe){
            if($(".topTags>ul>li").length==10){
                return;
            }else{
                if(subMenuList.length==0){
                    tagUl.children("a").removeClass("tagChecked");
                    listIframe.css("display","none");
                    $("#noSubMenu").css("display","inline");
                }else {
                    listIframe.css("display","none");
                    //主菜单切换实现显示子菜单的第一个功能
                    subMenuList.eq(0).children("a").css("color","#328FC5");
                    //右侧主体内容切换
                    var htmlTag="<li data-type='"+id+"' class='"+cla+"'><a title='"+text+"' class='tagChecked'>"+text+"<i></i></a></li>";
                    $(".topTags>ul a").removeClass("tagChecked");
                    $(".topTags>ul").append(htmlTag);
                    $(".closeAllTags").removeClass("hiddenClass");
                    var iframeHtml = "<iframe id='" + id + "' scrolling='no' height='100%' width='100%' marginwidth='0' marginheight='0' frameborder='0' src='" + id + "'></iframe>";
                    $(".list").append(iframeHtml);
                }
            }
        }else{
            listIframe.css("display","none");
            $(".subMenu>li").children("a").css("color", "#474747");
            $(".subMenu>li").eq(0).children("a").css("color", "#328FC5");
            //添加页签
            for(var i=0;i<tagUl.length;i++){
                var liId=tagUl.eq(i).attr("data-type");
                if(liId==id){
                    tagUl.children("a").removeClass("tagChecked");
                    tagUl.eq(i).children("a").addClass("tagChecked");
                }
            }
            listIframe.eq(iframeIndex).css("display","inline");
        }
    });

    //子菜单点击事件
    $(".subMenu").on("click","li>a",function(e){
        e.preventDefault();
        //添加页签
        var id = $(this).attr('data-type');
        var cla=$(this).parent().attr("class");
        var tagUl=$(".topTags>ul").children("li");
        //子菜单功能切换
        //右侧主体内容切换
        var listIframe=$(".list iframe");
        var hasIframe=true;
        var iframeIndex=0;
        for(var j=0;j<listIframe.length;j++){
            var iframeSrc=listIframe.eq(j).attr("src");
            if(id==iframeSrc){
                iframeIndex=j;
                hasIframe=false;
            }
        }
        if(hasIframe){
            if($(".topTags>ul>li").length==10){
                $(".modalDialog").css("display","block");
                $("#moreTag").css("display","block");
                commonObj.autoCenter(commonObj.g("moreTag"));
                commonObj.dragDialog("moreTag");
                $(".cancelMoreTag").click(function(){
                    $("#moreTag").css("display","none");
                    $(".modalDialog").css("display","none");
                });
            }else {
                $(this).css("color", "#328FC5");
                $(this).parent().siblings().children("a").css("color", "#474747");
                $(".list iframe").css("display","none");
                var htmlTag = "<li data-type='" + id + "' class='" + cla + "'><a title='" + $(this).text() + "' class='tagChecked'>" + $(this).html() + "<i></i></a></li>";
                $(".topTags>ul>li").css("background","transparent");
                $(".topTags>ul a").removeClass("tagChecked");
                $(".topTags>ul").append(htmlTag);
                $(".closeAllTags").removeClass("hiddenClass");
                var iframeHtml="<iframe id='"+id+"' scrolling='no' height='100%' width='100%' marginwidth='0' marginheight='0' frameborder='0' src='"+id+"'></iframe>";
                $(".list").append(iframeHtml);
            }
        }else{
            $(this).css("color", "#328FC5");
            $(this).parent().siblings().children("a").css("color", "#474747");
            $(".list iframe").css("display","none");
            for(var i=0;i<tagUl.length;i++){
                var liId=tagUl.eq(i).attr("data-type");
                if(liId==id){
                    tagUl.children("a").removeClass("tagChecked");
                    tagUl.eq(i).children("a").addClass("tagChecked");
                }
            }
            listIframe.eq(iframeIndex).css("display","inline");
        }
    })
    $.ajaxSetup ({ cache: false });

    //页签点击事件
    $(document.body).on("click",".topTags>ul>li>a",function(){
        $(this).addClass("tagChecked");
        $(this).parent().siblings("li").children("a").removeClass("tagChecked");
        var id=$(this).parent().attr("data-type");
        var cla=$(this).parent().attr("class");
        //导航菜单切换
        $(".nav_ul>li>a."+cla).parent().siblings("li.navCheck").removeClass("navCheck");
        $(".nav_ul>li>a."+cla).parent().addClass("navCheck");
        var ind=$(".nav_ul>li>a."+cla).parent().index();
        var subHtml="";
        if(navs[ind].sub != null && navs[ind].sub != undefined && navs[ind].sub != ""){
            for(var i=0;i<navs[ind].sub.length;i++){
                subHtml+="<li class='"+navs[ind].className+"'><a data-type="+navs[ind].sub[i].url+"><i></i>"+navs[ind].sub[i].name+"</a></li>";
            }
            $(".subMenu").html(subHtml);
        }else {
            $(".subMenu").html("");
        }
        //左侧子菜单切换
        var subMenuList=$(".subMenu>li");
        for(var m=0;m<subMenuList.length;m++){
            var subData=subMenuList.eq(m).children("a").attr("data-type");
            if(id==subData){
                subMenuList.eq(m).children("a").css("color","#328FC5");
            }else{
                subMenuList.eq(m).children("a").css("color","#474747");
            }
        }
        //右侧主体内容切换
        $(".list iframe").css("display","none");
        var listIframe=$(".list iframe");
        for(var j=0;j<listIframe.length;j++){
            var iframeSrc=listIframe.eq(j).attr("src");
            if(id==iframeSrc){
                listIframe.eq(j).css("display","inline");
            }
        }
    });

    //页签小叉号关闭事件
    $(document.body).on("click",".topTags>ul>li>a>i",function(e){
        if (e && e.stopPropagation) {//非IE浏览器
            e.stopPropagation();
        }else {//IE浏览器
            window.event.cancelBubble = true;
        }
        if($(".topTags>ul>li").length==1){
            $(".closeAllTags").addClass("hiddenClass");
        }
        var id=$(this).parent().parent().attr("data-type");
        var listIframe=$(".list iframe");
        if($(this).parent().hasClass("tagChecked")){
            var ulLength=$(this).parent().parent().parent().children("li").length;
            var index=$(this).parent().parent().index();
            if(ulLength==(index+1)){
                if(ulLength==1){
                    listIframe.remove();
                    var firstIframe="";
                    firstIframe="<iframe id='noSubMenu' style='display: none' scrolling='no' height='100%' width='100%' marginwidth='0' marginheight='0' frameborder='0' src='resources/common/ui/noSubMenu.html'></iframe>";
                    $(".list").html(firstIframe);
                    $(".subMenu>li>a").css("color","#474747");
                }else{
                    $(this).parent().parent().prev("li").children("a").addClass("tagChecked");
                    var prevId=$(this).parent().parent().prev("li").attr("data-type");
                    //导航菜单切换
                    var cla=$(this).parent().parent().prev("li").attr("class");
                    $(".nav_ul>li>a."+cla).parent().siblings("li.navCheck").removeClass("navCheck");
                    $(".nav_ul>li>a."+cla).parent().addClass("navCheck");
                    var ind=$(".nav_ul>li>a."+cla).parent().index();
                    var subHtml="";
                    if(navs[ind].sub != null && navs[ind].sub != undefined && navs[ind].sub != ""){
                        for(var i=0;i<navs[ind].sub.length;i++){
                            subHtml+="<li class='"+navs[ind].className+"'><a data-type="+navs[ind].sub[i].url+"><i></i>"+navs[ind].sub[i].name+"</a></li>";
                        }
                        $(".subMenu").html(subHtml);
                    }else {
                        $(".subMenu").html("");
                    }
                    //左侧子菜单切换
                    var subMenuList=$(".subMenu>li");
                    for(var m=0;m<subMenuList.length;m++){
                        var subData=subMenuList.eq(m).children("a").attr("data-type");
                        if(prevId==subData){
                            subMenuList.eq(m).children("a").css("color","#328FC5");
                        }else{
                            subMenuList.eq(m).children("a").css("color","#474747");
                        }
                    }
                }
            }else{
                $(this).parent().parent().next("li").children("a").addClass("tagChecked");
                var nextId=$(this).parent().parent().next("li").attr("data-type");
                //导航菜单切换
                var cla=$(this).parent().parent().next("li").attr("class");
                $(".nav_ul>li>a."+cla).parent().siblings("li.navCheck").removeClass("navCheck");
                $(".nav_ul>li>a."+cla).parent().addClass("navCheck");
                var ind=$(".nav_ul>li>a."+cla).parent().index();
                var subHtml="";
                if(navs[ind].sub != null && navs[ind].sub != undefined && navs[ind].sub != ""){
                    for(var i=0;i<navs[ind].sub.length;i++){
                        subHtml+="<li class='"+navs[ind].className+"'><a data-type="+navs[ind].sub[i].url+"><i></i>"+navs[ind].sub[i].name+"</a></li>";
                    }
                    $(".subMenu").html(subHtml);
                }else {
                    $(".subMenu").html("");
                }
                //左侧子菜单切换
                var subMenuList=$(".subMenu>li");
                for(var m=0;m<subMenuList.length;m++){
                    var subData=subMenuList.eq(m).children("a").attr("data-type");
                    if(nextId==subData){
                        subMenuList.eq(m).children("a").css("color","#328FC5");
                    }else{
                        subMenuList.eq(m).children("a").css("color","#474747");
                    }
                }
            }
        }
        $(this).parent().parent().remove();
        var tagId=$(".tagChecked").parent().attr("data-type");
        for(var j=0;j<listIframe.length;j++){
            var iframeSrc=listIframe.eq(j).attr("src");
            if(id==iframeSrc){
                listIframe.eq(j).remove();
            }
            if(tagId==iframeSrc){
                listIframe.eq(j).css("display","inline");
            }
        }
    });

    //二级菜单收缩事件
    $("#openAndClose").click(function(){
        var title=$(this).attr("title");
        if(title=="收缩"){
            $(".con_left").css("display","none");
            $(".con_right").css("width","100%");
            $(this).css("left",0);
            $(this).attr("title","展开");
            $(this).attr("src","resources/common/image/triangle.png");
        }else if(title=="展开"){
            $(".con_left").css("display","block");
            $(".con_right").css("width","87.4%");
            $(this).attr("title","收缩");
            $(this).css("left",-10);
            $(this).attr("src","resources/common/image/arrow.png");
        }
    })
})

/**
 * 公共按钮方法
 * @returns
 */
//新增
function modelAdd(model,ValidateAreaId,templateContainer){
	//页面
	$("#buttonPage").html(model);
    $("#buttonPage").css("display","block");
    $(".modalDialog").css("display","block");
    commonObj.autoCenter(commonObj.g("buttonPage"));
    commonObj.dragDialog("buttonPage");

    //确定按钮点击事件
	$("#model_add").click(function(){
        //区域校验
        if(!validatorObj.validate(ValidateAreaId, 1)) {
            return false;
        }
        var listIframe=$(".list iframe");
        var iframeBlockId="toChannel";
        for(var j=0;j<listIframe.length;j++){
            var dis=listIframe.eq(j).css("display");
            if(dis=="inline") {
                iframeBlockId=listIframe.eq(j).attr("id");
            }
        }
		var result = paginationObj.paginationRefresh(templateContainer.add, iframeBlockId, false);
        if(result != false && result != ""&& result != null){
            $("#buttonPage").html("");
        }
    });

	//取消按钮点击事件
	$(".cancel").click(function(){
        $("#buttonPage").html("");
        $("#buttonPage").css("display","none");
        $(".modalDialog").css("display","none");
    });
	//用户特征注册上传图片事件
    //$("#addPage").on("change", "#photoUpload", function(){
    $("#photoUpload").change(function(){
        var files = document.getElementById("photoUpload").files;
        if(files.length>0){
            var content = "";
            var liList="";
            var useBase64 = "";
            var allBase64 = "";
            var photoStr="";
            for(var i =0;i<files.length;i++){
                content += files[i].name;
                var imgSrc = window.URL.createObjectURL(files[i]);
                var image = new Image();
                image.src = imgSrc;
                image.name = files[i].name;
                image.onload = function() {
                    base64 = commonObj.getBase64Image(this);
                    $("#image_add").val(base64.split(",")[1]);
                    //图片质量检测
                    var result = 0;//commonObj.bindPageData(templateContainer.check, false, true, false,false, false, false, "0", null, null);

                    if(result ==0){
                        $("#contrastivePhoto").html("");
                        var img = document.createElement("img");
                        img.setAttribute("id","scenePic");
                        img.src = base64;
                        $("#contrastivePhoto").append(img);
                        var content = "<span  class='hiddenClass'><a id='delPic' class='delPic'></a></span>";
                        $("#contrastivePhoto").append(content);
                        $(".hiddenClass>div>em").css("display", "none");
                        $(".hiddenClass>div>span").css("display", "block");
                    }else if(result == 500){
                        $("#photoUpload").replaceWith("<input type='file' style='opacity:0' id='photoUpload' accept='image/png,image/jpeg,image/gif'>");
                        $("#photoUpload").parent().css("background","url(resources/common/image/shangchuan.png)");
                        $.layer.alert("服务器异常");
                        return false;
                    }else if(result == 10003){
                        $.layer.alert("未检测到人脸");
                        return false;
                    }
                }
            }
        }
    });
    $("#photoUpload1").change(function(){
        var files = document.getElementById("photoUpload").files;
        if(files.length>0){
            var content = "";
            var liList="";
            var useBase64 = "";
            var allBase64 = "";
            var photoStr="";
            for(var i =0;i<files.length;i++){
                content += files[i].name;
                var imgSrc = window.URL.createObjectURL(files[i]);
                var image = new Image();
                image.src = imgSrc;
                image.name = files[i].name;
                image.onload = function() {
                    base64 = commonObj.getBase64Image(this);
                    $("#image_add").val(base64.split(",")[1]);
                    //图片质量检测
                    //var result = commonObj.bindPageData(templateContainer.check, false, true, false,false, false, false, "0", null, null);
                    var result = 0;
                    if(result ==0){
                        $("#contrastivePhoto").html("");
                        var img = document.createElement("img");
                        img.setAttribute("id","scenePic");
                        img.src = base64;
                        $("#contrastivePhoto").append(img);
                        var content = "<span  class='hiddenClass'><a id='delPic' class='delPic'></a></span>";
                        $("#contrastivePhoto").append(content);
                        $(".hiddenClass>div>em").css("display", "none");
                        $(".hiddenClass>div>span").css("display", "block");
                    }else if(result == 500){
                        $("#photoUpload").replaceWith("<input type='file' style='opacity:0' id='photoUpload' accept='image/png,image/jpeg,image/gif'>");
                        $("#photoUpload").parent().css("background","url(resources/common/image/shangchuan.png)");
                        $.layer.alert("服务器异常");
                        return false;
                    }else if(result == 10003){
                        $.layer.alert("未检测到人脸");
                        return false;
                    }
                }
            }
        }
    });

    if(templateContainer.add.Service.Header.ServiceName == "ES-EmployeeDailyRule-Add-T"){
        $("#check_in_time_add").jeDate({
            isinitVal:true,
            initDate:[{hh:09,mm:00,ss:00},false],
            format:"hh:mm:ss",
            zIndex:3000
        });
        $("#check_out_time_add").jeDate({
            isinitVal:true,
            initDate:[{hh:18,mm:00,ss:00},false],
            format:"hh:mm:ss",
            zIndex:3000
        });
    }
};

//修改
function modelEdit(model, id,ValidateAreaId,templateContainer){
	//页面
	$("#buttonPage").html(model);
    $("#buttonPage").css("display","block");
    $(".modalDialog").css("display","block");
    commonObj.autoCenter(commonObj.g("buttonPage"));
    commonObj.dragDialog("buttonPage");
	$("#configId_modify").val(id);
    $("#basicInfor").val(id);

    //获取单条详细信息
	commonObj.bindPageData(templateContainer.detail, false, true, true, false, false, false, null);

	//确定按钮点击
	$("#model_modify").click(function() {
        //区域校验
        if(!validatorObj.validate(ValidateAreaId, 1)) {
            return false;
        }
        var listIframe=$(".list iframe");
        var iframeBlockId="toChannel";
        for(var j=0;j<listIframe.length;j++){
            var dis=listIframe.eq(j).css("display");
            if(dis=="inline") {
                iframeBlockId=listIframe.eq(j).attr("id");
            }
        }
    	paginationObj.paginationRefresh(templateContainer.update,iframeBlockId,false);
    	$("#buttonPage").html("");
    });

    //取消按钮点击事件
    $(".cancel").click(function(){
        $("#buttonPage").html("");
        $("#buttonPage").css("display","none");
        $(".modalDialog").css("display","none");
    });

    commonObj.scrollBar();

};

//系统管理员给用户分配角色
function modelAssignRoles(model, id,ValidateAreaId,templateContainer){
	//页面
    $("#buttonPage").html(model);
    $("#buttonPage").css("display","block");
    $(".modalDialog").css("display","block");
    commonObj.autoCenter(commonObj.g("buttonPage"));
    commonObj.dragDialog("buttonPage");
    $("#allSelectIds").val(id);
    $("#basicInfor").val(id);
    commonObj.bindPageData(templateContainer.allRole, false, true, true, true, false, false, null);
    //滚动条
    commonObj.scrollBar();
    RoleSelect();
    $("#assignRoles_modify").click(function() {
        //区域校验
        // var opts = document.getElementById("rolePojoSelectList").getElementsByTagName("option");
        var opts = document.getElementById("rolePojoSelectList").getElementsByTagName("li");
        if(opts.length<1){
            $.layer.alert("每个用户必须至少拥有一个角色");
            return false;
        }
        var listIframe=$(".list iframe");
        var iframeBlockId="toSystemUser";
        for(var j=0;j<listIframe.length;j++){
            var dis=listIframe.eq(j).css("display");
            if(dis=="inline") {
                iframeBlockId=listIframe.eq(j).attr("id");
            }
        }
        paginationObj.paginationRefresh(templateContainer.userAuthorizate,iframeBlockId,false);
        // commonObj.bindPageData(templateContainer.userAuthorizate, true, true, true, true, true, false, "0", null,null);
        $("#buttonPage").html("");
    });

    //取消按钮
    $(".cancel").click(function(){
        $("#buttonPage").html("");
        $("#buttonPage").css("display","none");
        $(".modalDialog").css("display","none");
    });
}

////删除
//function modelDel(model, id, templateContainer){
//	$("#buttonPage").html(model);
//    $("#buttonPage").css("display","block");
//    $(".modalDialog").css("display","block");
//    commonObj.autoCenter(commonObj.g("buttonPage"));
//    commonObj.dragDialog("buttonPage");
//    $("#configId_delete").val(id);
//    $("#model_delete").click(function() {
//        var listIframe=$(".list iframe");
//        var iframeBlockId="toChannel";
//        for(var j=0;j<listIframe.length;j++){
//            var dis=listIframe.eq(j).css("display");
//            if(dis=="inline") {
//                iframeBlockId=listIframe.eq(j).attr("id");
//            }
//        }
//    	paginationObj.paginationRefresh(templateContainer.del,iframeBlockId,false);
//    	$("#buttonPage").html("");
//    });
//    $(".cancel").click(function(){
//        $("#buttonPage").html("");
//        $("#buttonPage").css("display","none");
//        $(".modalDialog").css("display","none");
//    });
//};

//参数配置
//function modelConfig(model, validateAreaId, id, templateContainer){
//	//列表内容填充
//	$("#buttonPage").html(model);
//    $("#buttonPage").css("display","block");
//    $(".modalDialog").css("display","block");
//    commonObj.autoCenter(commonObj.g("buttonPage"));
//    commonObj.dragDialog("buttonPage");
//	$("#configListId").val(id);
//    //获取单条详细信息
//	commonObj.bindPageData(templateContainer.configList, true, true, true, false, false, false, "0", null, null);
//    //滚动条
//    commonObj.scrollBar();
//	//列表页面确认按钮点击事件
//	$("#model_config").click(function() {
//    	//paginationObj.paginationRefresh(updateTemplate, false);
//		solutionScreen();
//		$("#buttonPage").html("");
//		//记录内按钮事件解绑
//		$(document.body).off("click", ".tableButton");
//    });
//	//列表页面取消按钮点击事件
//    $(".cancel").click(function(){
//        $("#buttonPage").html("");
//        $("#buttonPage").css("display","none");
//        $(".modalDialog").css("display","none");
//        //记录内按钮事件解绑
//        $(document.body).off("click", ".tableButton");
//    });
//    //列表页面内记录按钮内点击事件
//	$(document.body).on("click",".tableButton",function(){
//		var id2 = $(this).attr("id");
//		$("#configItemId").val(id2);
//		//加载所有配置项
//		commonObj.bindPageData(templateContainer.configItemList,false, true, true, false, false, false, "0", null, null);
//		$("#buttonPage").css("display","none");
//		$("#divPage").css("display","block");
//        commonObj.autoCenter(commonObj.g("divPage"));
//        commonObj.dragDialog("divPage");
//	});
//	//配置项页面确认按钮点击事件f
//    $("#divPage").off("click").on("click","#model_configItem",function(){
//    	//区域校验
//        if(!validatorObj.validate(validateAreaId, 1)) {
//            return false;
//        }
//    	//提交设置的配置项
//		commonObj.bindPageData(templateContainer.configItemSet, true, true, false, false, false, false, "0", null, null);
//		$("#divPage>div>div").html("");
//		$("#divPage").css("display","none");
//    	$("#buttonPage").css("display","block");
//        commonObj.autoCenter(commonObj.g("buttonPage"));
//        commonObj.dragDialog("buttonPage");
//    });
//    //配置项页面取消按钮点击事件
//    $(document.body).on("click",".configItemCancel",function(){
//    	$("#divPage>div>div").html("");
//        $("#divPage").css("display","none");
//    	$("#buttonPage").css("display","block");
//        commonObj.autoCenter(commonObj.g("buttonPage"));
//        commonObj.dragDialog("buttonPage");
//    });
//};

//角色分配菜单
function modelPermission(model,id,templateContainer){
    $("#buttonPage").html(model);
    $("#buttonPage").css("display","block");
    $(".modalDialog").css("display","block");
    commonObj.autoCenter(commonObj.g("buttonPage"));
    commonObj.dragDialog("buttonPage");
    $("#permissionCheckId").val(id);
    $("#configId_modify").val(id);
    commonObj.bindPageData(templateContainer.distribution, true, true, true,false, false, false, null);
    $("#permissionTable>tbody").on("change","input[type=checkbox]",function(){
        var checkClass=$(this).attr("id");
        if($(this).attr("checked")){
            $("."+checkClass).attr("checked",true);
        }else{
            $("."+checkClass).attr("checked",false);
        }
        if($(this).attr("class")){
            var mailId=$(this).attr("class");
            var num=0;
            for(var i=0;i<$("."+mailId).length;i++){
                if($("."+mailId).eq(i).attr("checked")=="checked"){
                    num++;
                }
            }
            if(num){
                $("#"+mailId).attr("checked",true);
            }else{
                $("#"+mailId).attr("checked",false);
            }
        }
    });
    $("#model_set").click(function(){

        commonObj.bindPageData(templateContainer.authorite, true, true, true,false, true, false, null);
    })
    $(".cancel").click(function(){
        $("#buttonPage").css("display","none");
        $(".modalDialog").css("display","none");
    });
    //滚动条
    commonObj.scrollBar();
}

/**
 *角色管理功能(分配角色左右选择框)
 */
function  RoleSelect(){
    $(".removeBtn").on("click","#left",function(){
        var selOpts = document.getElementById("rolePojoSelectList").getElementsByTagName("input");
        var rSelC = "";
        for(var i=0;i<selOpts.length;i++){
            if(selOpts[i].checked){
                var input = selOpts[i].parentNode.innerText;
                var id = selOpts[i].id;
                rSelC = "<li><input type='checkbox' id='"+id+"'/>"+input+"</li>";
                selOpts[i].parentNode.parentNode.removeChild(selOpts[i].parentNode);
                $("#rolePojoReleaseList").append(rSelC);
                --i;
            }
        }
    });
    $(".removeBtn").on("click","#right",function(){
        var unSelOpts = document.getElementById("rolePojoReleaseList").getElementsByTagName("input");
        var rSelC = "";
        for(var i=0;i<unSelOpts.length;i++){
            if(unSelOpts[i].checked){
                var text = unSelOpts[i].parentNode.innerText;
                var id = unSelOpts[i].id;
                rSelC = "<li><input type='checkbox' id='"+id+"'/>"+text+"</li>";
                $("#rolePojoSelectList").append(rSelC);
                unSelOpts[i].parentNode.parentNode.removeChild(unSelOpts[i].parentNode);
                --i;
            }
        }
    });
}

/**
 * 公共锁屏
 */
function lockScreen() {
	$(".screenLock").css("display","block");
}
/**
 * 公共解屏
 * @returns
 */
function solutionScreen() {
	$(".modal").css("display","none");
}

/**
 * 显示操作返回结果
 * @param resultData {Json Object} 数据交互模板
 * @param isShowChildPage {Boolean} 返回信息后是否显示子页面
 * @returns
 */
function showReturnBox(resultData, isShowChildPage) {
	//问题码
    if(resultData.errorCode!=0){
        $(".infoBox>img").attr("src","resources/common/image/gantanhao.png");
    }

    //成功码
    else{
        $(".infoBox>img").attr("src","resources/common/image/success.png");
    }

    //返回页面
	$("#resultMsg").html(resultData.message);
	$("#buttonPage").css("display","none");
    $("#modifyPassword").css("display","none");
	$(".modalDialog").css("display","block");
    $("#returnBox").css("display","block");
    commonObj.autoCenter(commonObj.g("returnBox"));
    commonObj.dragDialog("returnBox");

    //点击关闭按钮
    $(".cancelReturnBox").off("click").on("click",function(){
	    if("原密码输入错误"==resultData.message){
            $("#modifyPassword").css("display","block");
            $("#resultMsg").html("");
            $("#returnBox").css("display","none");
            $("#oldPassword").val("");
        }else if("新密码与原密码不能相同"==resultData.message){
            $("#modifyPassword").css("display","block");
            $("#resultMsg").html("");
            $("#returnBox").css("display","none");
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#confirmPassword").val("");
        }else if("修改用户密码成功"==resultData.message){
            $("#resultMsg").html("");
            $("#returnBox").css("display","none");
            $(".modalDialog").css("display","none");
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#confirmPassword").val("");
        }else{
            $("#resultMsg").html("");
            $(".modalDialog").css("display","none");
            $("#returnBox").css("display","none");
            if(isShowChildPage){
                $("#buttonPage").css("display","block");
            }else {
                $("#buttonPage").html("");
            }
        }
    });
}