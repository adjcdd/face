/**
 * Created by zhangweihua on 2018/1/26.
 */
var templateContainer = new Object();
var dateContainer = new Object();
$(function () {
    //加载日期控件
    dateContainer.format='YYYY-MM-DD';
    dateContainer.startMinDate='2017-12-01';
    dateContainer.startMaxDate=$.nowDate(-30)
    dateContainer.endMinDate='2017-12-01';
    dateContainer.endMaxDate=$.nowDate(-30);
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

    $("#search_start_time").jeDate({
        isinitVal:true,
        initDate:[{MM:"-1"},true],   //初始化日期减1个月
        festival: true,
        format: 'YYYY年MM月DD日'
    });

    $("#search_end_time").jeDate({
        isinitVal:true,
        initDate:[{MM:"-1"},true],   //初始化日期减1个月
        festival: true,
        format: 'YYYY年MM月DD日'
    });
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