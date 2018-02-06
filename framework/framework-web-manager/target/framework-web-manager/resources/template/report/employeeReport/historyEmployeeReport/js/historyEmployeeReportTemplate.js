/**
 * Created by zhangweihua on 2018/1/18.
 */
//查询员工列表
var TestMonitoringEmployeeList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-MonitoringEmployee-List-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Table" : {"Id" : "MonitoringEmployeeDailyTable",
                "DataPrimaryKey" : "id",
                "Checkbox":"false"
            }
            }
        },
        "Input": {
            "Fields": [
                {"pageNo" : "currentPage"},
                {"pageSize" : "pageSize"},
                {"name" : "search_name"},
                {"ctfNo":"search_ctfNo"},
                {"start_time":"search_start_time"},
                {"end_time":"search_end_time"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody" : {"pageNo" : "currentPage", "totalCount" : "totalNum", "totalPage" : "totalPage", "pageSize" : "",
                    "data" : [
                        {"FieldId":"name", "FieldType":"Normal"},
                        {"FieldId":"dept", "FieldType":"Normal"},
                        {"FieldId":"start_time", "FieldType":"Date-date"},
                        {"FieldId":"end_time", "FieldType":"Date-date"},
                        {"FieldId":"daily_time", "FieldType":"Date-date"},
                        {"FieldId":"daily_result", "FieldType":"Normal"},
                        {"FieldId":"later_times", "FieldType":"Normal"},
                        {"FieldId":"early_times", "FieldType":"Normal"},
                        {"FieldId":"normal_times", "FieldType":"Normal"},
                        {"FieldId":"attendance_days", "FieldType":"Normal"},
                        {"FieldId":"attendanced_days", "FieldType":"Normal"},
                        {"FieldId":"total_latertime", "FieldType":"Normal"},
                        {"FieldId":"total_earlytime", "FieldType":"Normal"},
                        {"FieldId":"no_check_in", "FieldType":"Normal"},
                        {"FieldId":"no_check_out", "FieldType":"Normal"}
                    ]
                }
            }
        }
    }
}

