/**
 * 系统用户js
 * create by mc at 2017-10-12
 */
var templateContainer = new Object();
$(function () {
	//填充模板容器
    templateContainer.list = TestUserList;
    templateContainer.add = TestUserRegister;
    templateContainer.detail = TestuserDetail;
    templateContainer.allRole = TestRoleAll;
    templateContainer.update = TestUserUpdate;
    templateContainer.del = TestDeleteUser;
    templateContainer.resetPwd = TestUserResetPwd;
    templateContainer.userAuthorizate = TestUserAuthorizate;
    //页面初始化
    initializeObj.initialize(initialize);
    //分页初始化
    paginationObj.initPagination(templateContainer.list, 10);
    //分页查询
    paginationObj.paginationQuery(templateContainer.list, true);
    //公共按钮
    commonButtonObj.initButton("systemUsersTable");
    commonButtonObj.triggerButton("search", null, "systemUsersTable", templateContainer);
    commonButtonObj.triggerButton("add", "addArea", "systemUsersTable", templateContainer);
    commonButtonObj.triggerButton("edit", "editArea", "systemUsersTable", templateContainer);
    commonButtonObj.triggerButton("del", "null", "systemUsersTable", templateContainer);
    commonButtonObj.triggerButton("assign", "assignRolesArea", "systemUsersTable", templateContainer);
    commonButtonObj.triggerButton("resetPwd", "null", "systemUsersTable", templateContainer);
    commonButtonObj.resetButton("reset","toSystemUser");
    commonButtonObj.refreshButton("refresh","toSystemUser", templateContainer);
});

/**
 * 分页刷新页面
 */
function Refresh() {
    paginationObj.bindPaginationData(templateContainer.list, true, false);
};
/**
 * 页面初始化
 */
function initialize(){
	//加载页面数据字典
	var array = ["status-13", "update_status-13"];
	commonObj.fillPageDataDictionary(array);
}