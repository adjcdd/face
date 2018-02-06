/**
 * 系统角色前后端数据交互模板
 * create by mc at 2017-10-12
 */

//角色查询列表
var TestRoleList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Role-List-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Table" : {"Id" : "systemRoleTable",
                                       "DataPrimaryKey" : "id"}
                           }
        },
        "Input": {
            "Fields" : [
                {"pageSize" : "pageSize"},
                {"pageNo" : "currentPage"},
                {"roleName" : "roleName"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": {"pageNo" : "currentPage", "totalCount" : "totalNum", "totalPage" : "totalPage", "pageSize" : "",
                    "data" : [ {"FieldId":"Serial Number", "FieldType":"Normal"},
                               {"FieldId":"roleName", "FieldType":"Normal"},
                               {"FieldId":"updateTime", "FieldType":"Date-datetime"},
                               {"FieldId":"updateUser", "FieldType":"Normal"},
                               {"FieldId":"status", "FieldType":"DataDictionary-13"}
                             ]}
            }
        }
    }
}

//角色新增
var TestRoleAdd = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Role-Add-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"roleName" : "roleName_add"},
                {"status" : "roleStatus_add"}
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
//角色修改
var TestRoleUpdate = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Role-Update-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id" :"configId_modify"},
                {"roleName" : "roleName_modify"},
                {"status" : "roleStatus_modify"}
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
//编辑单条数据
var TestRoleDetail = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Role-ForEdit-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" :""
        },
        "Input": {
            "Fields": [
                {"id" :"configId_modify"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody":{"id":"configId_modify",
                    "roleName":"roleName_modify",
                    "status":"roleStatus_modify"
                }
            }
        }
    }
}
//分配单条数据
var TestRoleDistribution = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Role-Info-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" :{"permissionTable":"permissionTable",
                "type":"edit"
            }
        },
        "Input": {
            "Fields": [
                {"id" :"configId_modify"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody":{"id":"configId_modify",
                    "roleName":"roleName_modify",
                    "status":"roleStatus_modify"
                }
            }
        }
    }
}
//角色删除
var TestRoleDelete = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Role-Delete-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id" : "deleteId",
                 "deleteIgnoreRelationship":"deleteIgnoreRelationship"}
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
//角色查询
var RoleMenuAll = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Authority-All-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {
                "permissionTable":"permissionTable",
                "type":""
            }
        },
        "Input": "",
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
}
//角色授权
var TestRoleAuthorite = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Role-Authorite-T"
        },
        "Type" : {
            "InputType" : {"Array" : "permissionTable"},
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id": "permissionCheckId"}
            ],
            "Arrays": [
                {
                    "permissionTable": {
                        "FieldName": "authorityPojoList",
                        "key": "id"
                    }
                }
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