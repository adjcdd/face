/********************************************
*  数据字典
*  create by ljs at 2017-11-04
*********************************************/
/**
 * 数据字典全局变量
 */
var gDataDictionary = null;
var dataDictionaryObj = {
	/**
	 * 加载数据字典缓存
	 * @param Template {Json Object} 数据交互模板
	 */
	"load" : function(Template) {
		commonObj.bindDataAndCallback(Template, true, false, this.initDataDictionary);
	},
	
	/**
	 * 初始化数据字典全局变量
	 * @param Template {Json Object} 数据交互模板
	 */
	"initDataDictionary" : function(data) {
		gDataDictionary = data.responseBody;
	}
}

//数据字典模板
var dataDictionaryTemplate = {
    "Service" : {
        "Header" : {
            "ServiceName" : "ES-DataDictionaryPojo-All-T"
        },
        "Type" : {
            "InputType" : "",
            "OutputType" : ""
        },
        "Input" : {
        	
        },
        "Output" : {
        	"Objects" : {
                "responseHeader" : "",
                "responseBody":""
           }
        }
    }
};