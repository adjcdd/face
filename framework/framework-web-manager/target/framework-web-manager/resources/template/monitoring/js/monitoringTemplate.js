/**
 * 实时监控前后端数据交互模板
 * create by zwh at 2018-1-2
 */
/*var TestMonitoringDetail={
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-Monitoring-Info-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input" : {
            "Fields" : [
                {"image":"image_add"}
            ]
        },
        "Output" : {
            "Objects" : {
                "responseHeader" : "",
                "responseBody" : {
                    "name":"employee_name",
                    "imageBase64":"face2"
                }}
        }
    }
}*/
//"ServiceName": "ES-Monitoring-List-T"ES-Monitoring-DailyList-T
//查询员工签到成功列表
var TestMonitoringList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Monitoring-DailyList-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Div" : "writeList"}
        },
        "Input": {},
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody" : ""
            }
        }
    }
}
//查询员工签到失败列表
var TestMonitoringFailList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Monitoring-FailList-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Div" : "recordList"}
        },
        "Input": {},
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody" : ""
            }
        }
    }
}