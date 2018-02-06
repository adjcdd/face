/**
 * Created by zhangweihua on 2018/1/18.
 */
//查询员工列表
var TestEmployeeDailyRuleList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-EmployeeDailyRule-List-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Table" : {"Id" : "EmployeeDailyRuleTable",
                "DataPrimaryKey" : "id"
            }
            }
        },
        "Input": {
            "Fields": [
                {"pageNo" : "currentPage"},
                {"pageSize" : "pageSize"},
                {"title" : "search_title"},
                {"dept":"search_dept"},
                {"check_in_time":"search_check_in_time"},
                {"check_out_time":"search_check_out_time"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody" : {"pageNo" : "currentPage", "totalCount" : "totalNum", "totalPage" : "totalPage", "pageSize" : "",
                    "data" : [
                        {"FieldId":"id", "FieldType":"Normal"},
                        {"FieldId":"title", "FieldType":"Normal"},
                        {"FieldId":"dept", "FieldType":"Normal"},
                        {"FieldId":"check_in_time", "FieldType":"Normal"},
                        {"FieldId":"check_out_time", "FieldType":"Normal"}
                    ]
                }
            }
        }
    }
}
//规则添加
var TestEmployeeDailyRuleAdd = {
    "Service": {
        "Header": {
            "ServiceName": "ES-EmployeeDailyRule-Add-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id": "id_add"},
                {"title": "title_add"},
                {"dept": "dept_add"},
                {"check_in_time": "check_in_time_add"},
                {"check_out_time": "check_out_time_add"}
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
//规则删除
var TestEmployeeDailyRuleDelete = {
    "Service": {
        "Header": {
            "ServiceName": "ES-EmployeeDailyRule-Delete-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id":"deleteId"}
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

//规则修改
var TestEmployeeDailyRuleUpdate = {
    "Service": {
        "Header": {
            "ServiceName": "ES-EmployeeDailyRule-Update-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id":"id_modify"} ,
                {"title":"title_modify"},
                {"dept":"dept_modify"},
                {"check_in_time":"check_in_time_modify"},
                {"check_out_time":"check_out_time_modify"}
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
//规则详情
var TestEmployeeDailyRuleInfo={
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-EmployeeDailyRule-Info-T"
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
            "Objects" : {
                "responseHeader" : "",
                "responseBody" : {
                    "id":"id_modify",
                    "title":"title_modify",
                    "check_in_time":"check_in_time_modify",
                    "check_out_time":"check_out_time_modify",
                    "dept":"dept_modify"
                }}
        }
    }
}