/**
 * 员工前后端数据交互模板
 * create by ljs at 2017-12-13
 */
//查询员工列表
var TestEmployeeList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Employee-List-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Table" : {"Id" : "employeeTable",
                "DataPrimaryKey" : "uid",
                "ButtonName" : "详情",
                "Checkbox" : "true"
            }
            }
        },
        "Input": {
            "Fields": [
                {"pageNo" : "currentPage"},
                {"pageSize" : "pageSize"},
                {"name" : "search_name"},
                {"ctfNo":"search_ctfNo"},
                {"uid":"search_uid"},
                {"dept":"search_dept"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody" : {"pageNo" : "currentPage", "totalCount" : "totalNum", "totalPage" : "totalPage", "pageSize" : "",
                    "data" : [
                        {"FieldId":"uid", "FieldType":"Normal"},
                        {"FieldId":"name", "FieldType":"Normal"},
                        {"FieldId":"sex", "FieldType":"DataDictionary-1"},
                        {"FieldId":"createTime", "FieldType":"Date-datetime"},
                        {"FieldId":"telphone", "FieldType":"Normal"},
                        {"FieldId":"dept", "FieldType":"Normal"},
                        {"FieldId":"ctfNo", "FieldType":"Normal"},
                        {"FieldId":"email", "FieldType":"Normal"}
                    ]
                }
            }
        }
    }
}
//员工添加
var TestEmployeeAdd = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Employee-Add-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"uid": "uid_add"},
                {"dept": "dept_add"},
                {"name": "name_add"},
                {"sex": "sex_add"},
                {"telphone": "telphone_add"},
                {"ctfNo":"ctfNo_add"},
                {"email":"email_add"},
                {"image":"image_add"}
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

//查询详情
var TestEmployeeInfo = {
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-Employee-Info-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input" : {
            "Fields" : [
                {"uid" : "employeeResultQuery_id"}
            ]
        },
        "Output" : {
            "Objects" : {"responseHeader" : {"errorCode" : "", "message":""},
                "responseBody" : {
                    "uid":"employee_uid",
                    "dept":"employee_dept",
                    "name":"employee_name",
                    "sex":"employee_sex",
                    "createTime":"employee_createTime",
                    "telphone":"employee_telphone",
                    "ctfNo":"employee_ctfNo",
                    "email":"employee_email",
                    "imageBase64":"face"
                   }}
        }
    }
}
//员工删除
var TestEmployeeDelete = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Employee-Delete-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"uid":"deleteId"}
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

//员工修改
var TestEmployeeUpdate = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Employee-Update-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"uid":"uid_modify"} ,
                {"dept":"dept_modify"},
                {"name":"name_modify"},
                {"sex":"sex_modify"},
                {"telphone":"telphone_modify"},
                {"ctfNo":"ctfNo_modify"},
                {"email":"email_modify"},
                /*{"image":"image_modify"}*/
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

var TestEmployeeDetail={
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-Employee-Info-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input" : {
            "Fields" : [
                {"uid" : "configId_modify"}
            ]
        },
        "Output" : {
            "Objects" : {
                "responseHeader" : "",
                "responseBody" : {
                    "uid":"uid_modify",
                    "dept":"dept_modify",
                    "name":"name_modify",
                    "sex":"sex_modify",
                    "telphone":"telphone_modify",
                    "ctfNo":"ctfNo_modify",
                    "email":"email_modify"
                }}
        }
    }
}