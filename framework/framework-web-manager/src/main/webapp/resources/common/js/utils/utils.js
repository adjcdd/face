/**
 * 公共工具
 * create by ljs at 2017-08-13
 */

/**
 * 工具对象
 */
var utilsObj = {
	/**
	 * 界面显示时将null用""替代
	 * @param value {Object} 后端返回值
	 * @returns value {String} 空字符串
	 */
	"transNullToBlank" : function(value){
		if (value === undefined || value === null || value === ""){
			return "&nbsp;";
		}
		return value;
	},

    /**
	 * 格式化时间
	 * @param type {String} 格式化类型
	 * @param value {String} 时间串
	 * @returns fmt {String} 格式化时间
     */
    "formatTime" : function (type, value) {
    	var fmt = "";
    	//value有值
    	if(null != value && undefined != value && "" != value) {
    		var date = new Date(value);
    		Y = date.getFullYear() + '-';
    		M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    		D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
    		h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
    		m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
    		s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
    		//格式化精确到天
    		if("date" === type) {
    			fmt = Y+M+D;
    		}
    		//格式化精确到秒
    		else if("datetime" === type) {
    			fmt = Y+M+D+h+m+s;
    		}
    		else if("time"==type){
    			fmt=h+m+s;
			}
    	}
    	//value为空
    	else {
    		
    	}
        return fmt;
    },
    
    /**
	 * 浏览器是否为IE
	 */
	"isIE" : function() {
		if (!!window.ActiveXObject || "ActiveXObject" in window) {
			return true;
		}else {
			return false;
		}
	}
}