/**
 * js初始化封装方法
 * create by ljs at 2017-08-13
 */

/**
 * 初始化对象
 */
var initializeObj = {
	/**
	 * 公共初始化
	 */
	"initialize" : function(callback) {
		if(null != callback 
		  && undefined != callback 
		  && "" != callback
		  && "function" === typeof callback) {
			callback();
		}
	}
}