/*登录页面轮播背景图*/
var imgs=[
    {"i":0,"img":"resources/common/image/carousel3.jpg"},
    {"i":1,"img":"resources/common/image/carousel2.jpg"},
    {"i":2,"img":"resources/common/image/carousel1.jpg"}
];
/*浏览器的高和宽*/
var bodyWidth;
var bodyHeight;

/*登录页面*/
$(function () {
	//设置登录页宽高
    bodyWidth=document.body.clientWidth;
    bodyHeight=document.body.clientHeight;
    $(".content").css("width",bodyWidth+"px");
    $(".content>ul").css("width",bodyWidth*3+"px");
    
    //浏览器窗口大小改变，登录页面随之改变
    $(window).resize(function(){
        location.reload();
    });
    
    //加载验证码
    loginObj.loadVerifyCode();
    
    //查看是否有保存的用户名，如果有，就显示出来
    loginObj.loadCookieUsername();
    
    //加载验证码错误页面，实现弹出框居中并可以拖动的效果
    $("#verifyError").load("resources/common/ui/verifyError.html",function(){
        commonObj.autoCenter(commonObj.g("verifyError"));
        commonObj.dragDialog("verifyError");
    });
    
    //登录按钮点击事件
    $("#loginBtn").on("click", function () {
        loginObj.login();
    });
    
    //回车登录
    $(document.body).keydown(function(e){
        if(e.keyCode==13){
            $("#loginBtn").click();
        }
    });
    
    //图片轮播
    slider.init();
});

//登录对象
var loginObj = {
    /**
     * 加载验证码
     */
    "loadVerifyCode" : function () {
        $("#verifyCode").attr("src", commonObj.getContextPath() + "/user/getVerifyCode??rnd=" + Math.random());
    },
    
    /**
     * 加载Cookie中保存的用户名
     */
    "loadCookieUsername" : function () {
        $.ajax({
            type : "get",
            url : commonObj.getContextPath() + "/user/getCookieUsername",
            dataType : 'json',
            success : function(data){
                //session失效或者服务重启后直接操作页面
                if(null == data ||data.responseHeader.errorCode == "20009"){
                    location.href = commonObj.getContextPath() + "/login";
                }
                if(data && data.responseHeader.errorCode == "0"){
                    $("#username").val(data.responseBody);
                }
            },
            error : function(jqXHR, textStatus, errorThrown){
            }
        });
    },
    
    /**
     * 登录验证用户名和密码
     * */
    "login" : function () {
        $(".infor>li>input").blur();
        var user = {};
        if($.trim($("#username").val())==""){
            verifyErrorObj.showModal("请输入账号",1);
        }else if($.trim($("#password").val())==""){
            verifyErrorObj.showModal("请输入密码",1);
        }else if($.trim($("#verifyCodeInput").val())==""){
            verifyErrorObj.showModal("请输入验证码",1);
        }else{
            user.username = $("#username").val();
            user.password = $("#password").val();
            user.verifyCode = $("#verifyCodeInput").val();
            if($("#rememberMe").is(":checked")){
                user.rememberMe = true;
            }else{
                user.rememberMe = false;
            }
            $.ajax({
                type : "post",
                url : commonObj.getContextPath() + "/user/login",
                data : user,
                dataType : 'json',
                success : function(data){
                    //session失效或者服务重启后直接操作页面
                    if(null == data ||data.responseHeader.errorCode == "20009"){
                        location.href = commonObj.getContextPath() + "/login";
                    }
                    if(data && data.responseHeader.errorCode == "0"){
                        location.href = commonObj.getContextPath() + "/index";
                    }else{
                        verifyErrorObj.showModal(data.responseHeader.message,2);
                    }
                },
                error : function(jqXHR, textStatus, errorThrown){
                }
            });
        }
    }
}

//背景图轮播效果
var slider={
    $imgs:null,
    DURATION:2000,
    WAIT:3000,
    "init":function(){
        this.$imgs=$("#images");
        this.updateView();
        this.startAuto();
    },
    "updateView":function(){
        for(var i=0,imgsHTML="";i<imgs.length;i++){
            imgsHTML+="<li><img src='"+imgs[i].img+"' style='width:"+bodyWidth+"px'></li>";
        }
        this.$imgs.html(imgsHTML);
    },
    /**
     * 启动自动轮播
     */
    "startAuto":function(){
        //启动一次性定时器
        setTimeout(function(){slider.move()},this.WAIT);
    },
    "move":function(){
        slider.$imgs.animate({left: -bodyWidth},slider.DURATION,function(){
                imgs = imgs.concat(imgs.splice(0,1));
            slider.updateView();
            slider.$imgs.css("left",0);
            slider.startAuto();
        });
    }
}
