/**
 * 系统用户前后端数据交互模板
 * create by mc at 2017-10-12
 */
//用户新增
var TestUserRegister = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-Add-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"username":"username1"},
                {"name":"name"},
                {"telphone":"telphone"},
                {"email":"email"},
                {"status":"status"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
}

//用户删除
var TestDeleteUser = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-Delete-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id" : "deleteId"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
}

//用户更新
var TestUserUpdate = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-Update-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id" : "configId_modify"},
                {"username":"update_username"},
                {"name":"update_name"},
                {"status":"update_status"},
                {"telphone":"update_telphone"},
                {"email":"update_email"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
}

//初始化用户列表
var TestUserList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-List-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Table" : {"Id" : "systemUsersTable",
                            "DataPrimaryKey" : "id"}
            }
        },
        "Input": {
            "Fields":[
                {"pageNo" : "currentPage"},
                {"pageSize" : "pageSize"},
                {"name":"search_name"},
                {"username":"search_username"}
            ]
        },
        "Output" : {
            "Objects" : {"responseHeader":"",
                "responseBody":{"pageNo" : "currentPage", "totalCount" : "totalNum", "totalPage" : "totalPage", "pageSize" : "",
                    "data" : [
                                {"FieldId":"Serial Number", "FieldType":"Normal"},
                                {"FieldId":"name", "FieldType":"Normal"},
                                {"FieldId":"username", "FieldType":"Normal"},
                                {"FieldId":"telphone", "FieldType":"Normal"},
                                {"FieldId":"email", "FieldType":"Normal"},
                                {"FieldId":"status", "FieldType":"DataDictionary-13"},
                                {"FieldId":"rolePojoList", "FieldType":"Normal"}
                    ]}}
        }
        }
}

//查询单个用户详情
var TestuserDetail = {
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-User-Info-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input" : {
            "Fields" : [
                {"id" : "configId_modify"}
            ]
        },
        "Output" : {
            "Objects" : {"responseHeader" : "",
                "responseBody" : { "id":"userId_modify",
                                    "name":"update_name",
                                    "username":"update_username",
                                    "telphone":"update_telphone",
                                    "email":"update_email",
                                    "status":"update_status"}}
        }
    }
}

//角色查询
var TestRoleAll = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-RoleList-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {
                "RoleSelects" : "rolePojoReleaseList&rolePojoSelectList"
            }
        },
        "Input": {
            "Fields" : [
                {"id" : "allSelectIds"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                 "responseBody" : { "rolePojoReleaseList" : [ { "key" : "id","value" : "roleName"}],
                                      "rolePojoSelectList"  : [ { "key" : "id","value" : "roleName"}]}
            }
        }
    }
}

//密码重置
var TestUserResetPwd = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-Resertpwd-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id" : "deleteId"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
}

//用户分配角色
var TestUserAuthorizate = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-Authorizate-T"
        },
        "Type" : {
            "InputType" : {"Array":"rolePojoList"},
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id" : "allSelectIds"}
            ],
            "Arrays" :[
                {"rolePojoList":[
                    {"key":"id"}
                    ]}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
}