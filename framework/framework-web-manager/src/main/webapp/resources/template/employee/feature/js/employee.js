/**
 * 员工菜单js
 * create by ljs at 2017-10-13
 */
var templateContainer = new Object();
$(function () {
    //填充模板容器
    templateContainer.list = TestEmployeeList;
    templateContainer.add = TestEmployeeAdd;
    templateContainer.info = TestEmployeeInfo;
    templateContainer.del = TestEmployeeDelete;
    templateContainer.update = TestEmployeeUpdate;
    templateContainer.detail = TestEmployeeDetail;
    //页面初始化
    initializeObj.initialize(initialize);
    //分页初始化
    paginationObj.initPagination(templateContainer.list, 10);
    //分页查询
    paginationObj.paginationQuery(templateContainer.list, true);
    //公共按钮
    commonButtonObj.initButton("employeeTable");
    commonButtonObj.triggerButton("search", null, "employeeTable", templateContainer);
    commonButtonObj.triggerButton("add", "addArea", "employeeTable", templateContainer);
    commonButtonObj.triggerButton("edit", "editArea", "employeeTable", templateContainer);
    commonButtonObj.triggerButton("del", null, "employeeTable", templateContainer);
    commonButtonObj.resetButton("reset", "toEmployee");
    commonButtonObj.refreshButton("refresh","toEmployee", templateContainer);
    $(document.body).on("click",".tableButton",function(){
        var basicLable = document.getElementById("employeeResultQueryPage").getElementsByTagName("label");
        var img = document.getElementById("imageArea").getElementsByTagName("img");
        var resultLable = document.getElementById("basicInformationArea").getElementsByTagName("label");
        for(var k=0;k<basicLable.length;k++){
            basicLable[k].innerHTML = "";
        }
        for(var m=0;m<img.length;m++){
            img[m].src = "";
        }
        for(var n=0;n<resultLable.length;n++){
            resultLable[n].innerHTML = "";
        }
        var id2 = $(this).attr("id");
        $("#employeeResultQuery_id").html(id2);
        commonButtonObj.triggerDetailButton("employeeResultQueryPage", templateContainer.info);
    });

 /*   dwr.engine.setActiveReverseAjax(true);
    $("#model_modify").click(function(){
        MessagePush.send($("#telphone_modify").val());
    });*/

});

/**
 * 页面初始化
 */
function initialize() {
    // commonObj.bindPageData(templateContainer.list, true, true, true, true, false, false, null);

    //加载页面数据字典
    // var array = ["sexId-5", "update_sexId-5"];
    // commonObj.fillPageDataDictionary(array);
    var array = ["sex_modify-1","employee_sex-1"];
    commonObj.fillPageDataDictionary(array);

};

/**
 * 分页刷新页面
 */
function Refresh() {
    paginationObj.bindPaginationData(templateContainer.list, true, false);
};
