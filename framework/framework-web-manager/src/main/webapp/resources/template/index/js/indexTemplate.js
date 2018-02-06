/**
 * 首页导航菜单前后端数据交互模板
 * create by wmm at 2017-10-12
 */
//初始化菜单
var TestAuthorityInfo = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-AuthorityInit-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": "",
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
};
//修改密码
var ModifyPassword = {
    "Service": {
        "Header": {
            "ServiceName": "ES-User-Updatepwd-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input": {
            "Fields":[
                {"oriPassword" : "oldPassword"},
                {"password" : "newPassword"},
                {"rePassword":"confirmPassword"}
            ]
        },
        "Output": {
            "Objects": {
                "responseHeader": "",
                "responseBody": ""
            }
        }
    }
};
//获取session数据
var SessionData={
    "Service": {
        "Header": {
            "ServiceName": "ES-User-Session-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
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