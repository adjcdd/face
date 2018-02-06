/**
 * 系统角色js
 * create by mc at 2017-10-12
 */
var templateContainer = new Object();
$(function () {
	//填充模板容器
    templateContainer.list = TestRoleList;
    templateContainer.add = TestRoleAdd;
    templateContainer.del = TestRoleDelete;
    templateContainer.update = TestRoleUpdate;
    templateContainer.detail =TestRoleDetail;
    templateContainer.authorite =TestRoleAuthorite;
    templateContainer.distribution=TestRoleDistribution;
    //页面初始化
    initializeObj.initialize(initialize);
    //分页初始化
    paginationObj.initPagination(templateContainer.list, 10);
    //分页查询
    paginationObj.paginationQuery(templateContainer.list, true);
    //公共按钮
    commonButtonObj.initButton("systemRoleTable");
    commonButtonObj.triggerButton("search", null, "systemRoleTable", templateContainer);
    commonButtonObj.triggerButton("add", "addArea", "systemRoleTable", templateContainer);
    commonButtonObj.triggerButton("edit", "editArea", "systemRoleTable", templateContainer);
    commonButtonObj.triggerButton("del",null, "systemRoleTable",templateContainer);
    commonButtonObj.triggerButton("permission",null, "systemRoleTable",templateContainer);
    commonButtonObj.resetButton("reset","toSystemRole");
    commonButtonObj.refreshButton("refresh","toSystemRole", templateContainer);
});

/**
 * 分页刷新页面
 */
function Refresh() {
    //本页面刷新
    paginationObj.bindPaginationData(templateContainer.list, true, false);
};
/**
 * 页面初始化
 */
function initialize(){
	//加载页面数据字典
	var array = ["roleStatus_add-13", "roleStatus_modify-13"];
	commonObj.fillPageDataDictionary(array);
	
    commonObj.bindPageData(RoleMenuAll, true, true, true, true, false, false, null);
}