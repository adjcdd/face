/**
 * Created by zhangweihua on 2018/1/18.
 */
var templateContainer = new Object();
var dateContainer = new Object();
$(function () {
//     dateContainer.format="hh:mm:ss";
//     dateContainer.startMinDate='00:00:00';
//     dateContainer.startMaxDate=dateContainer.endTag;
//     dateContainer.endMinDate=dateContainer.startTag;
//     dateContainer.endMaxDate="23:59:59";
//     dateContainer.startInitFlag=true;
//     dateContainer.endInitFlag=true;
//     dateContainer.startTag='search_check_in_time';
//     dateContainer.endTag='search_check_out_time';
// //加载日期控件
//     commonObj.dateControl(dateContainer);
  //      commonObj.checkEndDate(dateContainer);
    //填充模板容器
    templateContainer.list = TestEmployeeDailyRuleList;
    templateContainer.add = TestEmployeeDailyRuleAdd;
    templateContainer.detail = TestEmployeeDailyRuleInfo;
    templateContainer.del = TestEmployeeDailyRuleDelete;
    templateContainer.update = TestEmployeeDailyRuleUpdate;
    //页面初始化
    initializeObj.initialize(initialize);
    //分页初始化
    paginationObj.initPagination(templateContainer.list, 10);
    //分页查询
    paginationObj.paginationQuery(templateContainer.list, true);
    //公共按钮
    commonButtonObj.initButton("EmployeeDailyRuleTable");
    commonButtonObj.triggerButton("search", null, "EmployeeDailyRuleTable", templateContainer);
    commonButtonObj.triggerButton("add", "addArea", "EmployeeDailyRuleTable", templateContainer);
    commonButtonObj.triggerButton("edit", "editArea", "EmployeeDailyRuleTable", templateContainer);
    commonButtonObj.triggerButton("del", null, "EmployeeDailyRuleTable", templateContainer);
    commonButtonObj.resetButton("reset", "toRuleManagement");
    var urlId="/exportEmployeeDailyRule";
    //导出按钮
    commonButtonObj.exportButton("export","EmployeeDailyRuleTable","toRuleManagement",urlId);

});

/**
 * 页面初始化
 */
function initialize() {
    //时分秒设置为 10:30:00

     $("#search_check_in_time").jeDate({
         //initDate:[{hh:10,mm:00,ss:00},false],
         format:"hh:mm:ss",
         // isinitVal:true,
         isTime:false,
         minDate:"00:00:00",
         maxDate:"23:59:59"
     })
     $("#search_check_out_time").jeDate({
         //isinitVal:true,
         isTime:false,
         //initDate:[{hh:19,mm:00,ss:00},false],
         format:"hh:mm:ss",
         zIndex:3000
     })
    //commonObj.bindPageData(templateContainer.list, true, true, true, true, false, false, null);
    //加载页面数据字典
  //  var array = ["sex_modify-1","employee_sex-1"];
   // commonObj.fillPageDataDictionary(array);

};
/**
 * 分页刷新页面
 *
 */
function Refresh() {
    paginationObj.bindPaginationData(templateContainer.list, true, false);
};