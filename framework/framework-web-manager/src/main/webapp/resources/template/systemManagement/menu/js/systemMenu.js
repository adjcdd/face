/**
 * 系统菜单js
 * create by ljs at 2017-10-13
 */
var templateContainer = new Object();
$(function () {
	//填充模板容器
    templateContainer.list = TestAuthorityList;
    templateContainer.add = TestAuthorityAdd;
    templateContainer.del = TestAuthorityDelete;
    templateContainer.detail = authorityInfoTemplate;
    templateContainer.update = TestAuthorityUpdate;
    templateContainer.allPid = authorityAllPidTemplate;
    //页面初始化
    initializeObj.initialize(initialize);
    //分页初始化
    paginationObj.initPagination(templateContainer.list, 10);
    //分页查询
    paginationObj.paginationQuery(templateContainer.list, true);
    //公共按钮
    commonButtonObj.initButton("systemMenuTable");
    commonButtonObj.triggerButton("search", null, "systemMenuTable", templateContainer);
    commonButtonObj.triggerButton("add", "addArea", "systemMenuTable", templateContainer);
    commonButtonObj.triggerButton("edit", "editArea", "systemMenuTable", templateContainer);
    commonButtonObj.triggerButton("del", null, "systemMenuTable", templateContainer);
    commonButtonObj.resetButton("reset", "toSystemMenu");
    commonButtonObj.refreshButton("refresh","toSystemMenu", templateContainer);
});

/**
 * 页面初始化
 */
function initialize() {
	commonObj.bindPageData(templateContainer.allPid, true, true, true, true, false, false, null);
};

/**
 * 分页刷新页面
 */
function Refresh() {
    paginationObj.bindPaginationData(templateContainer.list, true, false);
};