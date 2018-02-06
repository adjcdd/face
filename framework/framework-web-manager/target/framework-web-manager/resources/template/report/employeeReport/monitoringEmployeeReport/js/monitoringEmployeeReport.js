/**
 * Created by zhangweihua on 2018/1/26.
 */
var templateContainer = new Object();
var dateContainer = new Object();
$(function () {
    //加载日期控件
    dateContainer.format='YYYY-MM-DD';
    dateContainer.startMinDate=$.nowDate(-30);
    dateContainer.startMaxDate=$.nowDate(0)
    dateContainer.endMinDate=$.nowDate(-30);
    dateContainer.endMaxDate=$.nowDate(0);
    dateContainer.startInitFlag=true;
    dateContainer.startInitValue='-30';
    dateContainer.endInitFlag=true;
    dateContainer.endInitValue='+0';
    dateContainer.startTag='search_start_time';
    dateContainer.endTag='search_end_time';

    commonObj.dateControl(dateContainer);
    //填充模板容器
    templateContainer.list = TestMonitoringEmployeeList;
    //页面初始化
    initializeObj.initialize(initialize);
    //分页初始化
    paginationObj.initPagination(templateContainer.list, 10);
    //分页查询
    paginationObj.paginationQuery(templateContainer.list, true);
    //公共按钮
    commonButtonObj.initButton("MonitoringEmployeeDailyTable");
    commonButtonObj.triggerButton("search", null, "MonitoringEmployeeDailyTable", templateContainer);
    var urlId="/exportMonitoringEmployee";
    //导出按钮
    commonButtonObj.exportButton("export","MonitoringEmployeeDailyTable","toMonitoringEmployeeReport",urlId);
});

/**
 * 页面初始化
 */
function initialize() {
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