/**
 * 员工菜单js
 * create by zwh at 2018-1-2
 */
var templateContainer = new Object();
$(function () {
    //填充模板容器
    templateContainer.list1 = TestMonitoringList;
    templateContainer.list2 = TestMonitoringFailList;
    // templateContainer.add = TestMonitoringDetail;
    //页面初始化
    initializeObj.initialize(initialize);
    commonButtonObj.initButton("receiverTable");
    //这个方法用来启动该页面的ReverseAjax功能
    dwr.engine.setActiveReverseAjax(true);
    //设置在页面关闭时，通知服务端销毁会话
    dwr.engine.setNotifyServerOnPageUnload(true);
    //这个函数是提供给后台推送的时候 调用的

    // $(document.body).on("click",".tableButton",function(){
    //     var basicLable = document.getElementById("employeeResultQueryPage").getElementsByTagName("label");
    //     var img = document.getElementById("imageArea").getElementsByTagName("img");
    //     for(var k=0;k<basicLable.length;k++){
    //         basicLable[k].innerHTML = "";
    //     }
    //     for(var m=0;m<img.length;m++){
    //         img[m].src = "";
    //     }
    //     var id2 = $(this).attr("id");
    //     $("#employeeResultQuery_id").html(id2);
    //     commonButtonObj.triggerDetailButton("employeeResultQueryPage", templateContainer.info);
    // });
    // $("#checkBtn").click(function(){
    //     // alert("aa");
    //     commonObj.bindDataAndCallback(templateContainer.add,true,true,null);
    // })
    //用户特征检索上传图片事件
    // $("#photoUpload").change(function(){
    //     var files = document.getElementById("photoUpload").files;
    //     if(files.length>0){
    //         var content = "";
    //         var liList="";
    //         var useBase64 = "";
    //         var allBase64 = "";
    //         var photoStr="";
    //         for(var i =0;i<files.length;i++){
    //             content += files[i].name;
    //             var imgSrc = window.URL.createObjectURL(files[i]);
    //             var image = new Image();
    //             image.src = imgSrc;
    //             image.name = files[i].name;
    //             image.onload = function() {
    //                 base64 = commonObj.getBase64Image(this);
    //                 $("#image_add").val(base64.split(",")[1]);
    //                 // //图片质量检测
    //                 // var result = commonObj.bindPageData(templateContainer.check, false, true, false,false, false, false, "0", null, null);
    //                 // if(result ==0){
    //                 //     $("#contrastivePhoto").html("");
    //                 //     var img = document.createElement("img");
    //                 //     img.setAttribute("id","scenePic");
    //                 //     img.src = base64;
    //                 //     $("#contrastivePhoto").append(img);
    //                 //     var content = "<span  class='hiddenClass'><a id='delPic' class='delPic'></a></span>";
    //                 //     $("#contrastivePhoto").append(content);
    //                 //     $(".hiddenClass>div>em").css("display", "none");
    //                 //     $(".hiddenClass>div>span").css("display", "block");
    //                 // }else if(result == 500){
    //                 //     $("#photoUpload").replaceWith("<input type='file' style='opacity:0' id='photoUpload' accept='image/png,image/jpeg,image/gif'>");
    //                 //     $("#photoUpload").parent().css("background","url(resources/common/image/shangchuan.png)");
    //                 //     $.layer.alert("服务器异常");
    //                 //     return false;
    //                 // }else if(result == 10003){
    //                 //     $.layer.alert("未检测到人脸");
    //                 //     return false;
    //                 // }
    //             }
    //         }
    //     }
    // });

});

/*
 * 后台推送消息调用函数
 */
function show(msg){
    commonObj.bindPageData(templateContainer.list1, true, true, true, true, false, false, null);
    var employeeSignFiledNameArray = msg.split(",");
    if(employeeSignFiledNameArray.length>1){
        for(var i = 0;i<employeeSignFiledNameArray.length;i++){
            //console.log(employeeSignFiledNameArray[i]+"i");
           // var uidFileName1=employeeSignFiledNameArray[i].split("uid=");
           // if(uidFileName1[1]!=""&&uidFileName1[1]!=null){
           //    // console.log("uid");
           // }
           var imageBase64FileName=employeeSignFiledNameArray[i].split("imageBase64=");
           if(imageBase64FileName[1]!=""&&imageBase64FileName[1]!=null){
               var imageBase64=imageBase64FileName[1].replace(/'/g,"").trim();
               $("#face").attr("src", "data:image/png;base64,"+imageBase64);
               $("#face2").attr("src", "data:image/png;base64,"+imageBase64);
           }
           var nameFile=employeeSignFiledNameArray[i].split("name=");
           if(nameFile[1]!=""&&nameFile[1]!=null){
               var nameFile=nameFile[1].replace("}","").replace(/'/g,"").trim();
               // var label1 = document.createElement("label");
               // var text = document.createTextNode("" + nameFile + "：已签到");
               // label1.appendChild(text);
               //  var labelId=document.getElementById("labelId");
               //  labelId.appendChild(label1);
               var str="";
               str +="<label >姓名："+nameFile+"</label>";
               $("#labelId").html(str);
           }
            var uidFile=employeeSignFiledNameArray[i].split("uid=");
            if(uidFile[1]!=""&&uidFile[1]!=null){
                var uidFile=uidFile[1].replace(/'/g,"").trim();
                var str="";
                str +="<label>&nbsp工号："+uidFile+"</label>";
                $("#labelId1").html(str);
            }
        }

    }else{
        commonObj.bindPageData(templateContainer.list2, true, true, true, true, false, false, null);
        $("#face").attr("src", "data:image/png;base64,"+ msg);
        $("#face2").attr("src", "data:image/png;base64,"+msg);
        // var label1 = document.createElement("label");
        // var text = document.createTextNode( "签到失败");
        // label1.appendChild(text);
        // var labelId=document.getElementById("labelId");
        var str="";
        str +="<label >签到失败</label>";
        $("#labelId").html(str);
        var str1="";
        $("#labelId1").html(str1);
        //labelId.appendChild(label1);
    }
}
/**
 * 页面初始化
 */
function initialize() {
    commonObj.bindPageData(templateContainer.list1, true, true, true, true, false, false, null);
    commonObj.bindPageData(templateContainer.list2, true, true, true, true, false, false, null);
    // commonObj.bindPageData(templateContainer.list, true, true, true, true, false, false, null);
    // commonObj.bindPageData(templateContainer.detail, true, true, true, true, false, false, null);
    //加载页面数据字典
    // var array = ["sexId-5", "update_sexId-5"];
    // commonObj.fillPageDataDictionary(array);
};

/**
 * 分页刷新页面
 */
function Refresh() {
    paginationObj.bindPaginationData(templateContainer, true, false);
};
