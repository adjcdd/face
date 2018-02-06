/**
 * 系统菜单前后端数据交互模板
 * create by ljs at 2017-10-13
 */
//菜单列表
var TestAuthorityList = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Authority-List-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {"Table" : {"Id" : "systemMenuTable",
                                       "DataPrimaryKey" : "id"}
                           }
        },
        "Input": {
            "Fields": [
                {"pageNo" : "currentPage"},
                {"pageSize" : "pageSize"},
                {"authorityName" :"menuName"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody" : {"pageNo" : "currentPage", "totalCount" : "totalNum", "totalPage" : "totalPage", "pageSize" : "", 
 	               "data" : [ {"FieldId":"Serial Number", "FieldType":"Normal"}, 
 	            	          {"FieldId":"authorityName", "FieldType":"Normal"}, 
 	            	          {"FieldId":"url", "FieldType":"Normal"},
                              {"FieldId":"authorityLevel", "FieldType":"Normal"},
 	            	          {"FieldId":"pauthorityName", "FieldType":"Normal"}
 	            	        ]
                     }
            }
        }
    }
}

//菜单添加
var TestAuthorityAdd = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Authority-Add-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"authorityName": "authorityName_add"},
                {"url":"url_add"},
                {"authorityLevel":"level_add"},
                {"pid":"pid_add"}
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

//查询菜单详情
var authorityInfoTemplate = {
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-Authority-Info-T"
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
                         "responseBody" : {"id":"configId_modify",
                        	               "authorityName":"authorityName_modify",
                        	               "url":"url_modify",
                        	               "pid":"pid_modify"}}
        }
	}
}

//菜单修改
var TestAuthorityUpdate = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Authority-Update-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields": [
                {"id":"configId_modify"},
                {"authorityName":"authorityName_modify"},
                {"url":"url_modify"},
	            {"pid":"pid_modify"}
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

//菜单删除
var TestAuthorityDelete = {
    "Service": {
        "Header": {
            "ServiceName": "ES-Authority-Delete-T"
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

//获取所有父菜单
var authorityAllPidTemplate = {
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-Authority-AllPid-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : {
	                        "DataDictionary" : "pid_add&pid_modify"
	                       }
        },
		"Input" : "",
		"Output" : {
            "Objects" : {"responseHeader" : "",
                         "responseBody" : {"key" : "id",
                                           "value" : "authorityName"}}
        }
	}
}