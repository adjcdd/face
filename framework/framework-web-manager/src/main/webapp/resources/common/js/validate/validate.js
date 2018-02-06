/********************************************
*	文件:公共校验器
*	功能:对前端页面进行校验
*	作者:柳金硕
*	时间:2017-09-12
*	版本:1.0
*********************************************/
validatorObj = {
	//公共校验方法
	validate : function(id, mode){
		//判断校验区域
		if(null === id || undefined === id || "" === id) {
			return true;
		}
		//校验容器填充
		var inputCollection = document.getElementById(id).getElementsByTagName("input");
		var selectCollection = document.getElementById(id).getElementsByTagName("select");
		var textareaCollection = document.getElementById(id).getElementsByTagName("textarea");
		//校验初始化
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		//开始校验
		if(null != inputCollection && undefined != inputCollection && "" != inputCollection) {
			this.collectionValidate(inputCollection);
		}
		if(null != selectCollection && undefined != selectCollection && "" != selectCollection) {
			this.collectionValidate(selectCollection);
		}
		if(null != textareaCollection && undefined != textareaCollection && "" != textareaCollection) {
			this.collectionValidate(textareaCollection);
		}
		//校验结果
		if(this.ErrorMessage.length > 1){
			mode = mode || 1;
			var errCount = this.ErrorItem.length;
			switch(mode){
				case 1 :
					//alert(this.ErrorMessage[1]);
                    this.ErrorItem[1].focus();
					$.layer.alert(this.ErrorMessage[1]);
                    //mod by mc at 2017-09-27 start
					// alert(this.ErrorMessage[1]);
//					this.ErrorItem[1].focus();
					//mod by mc at 2017-09-27 end
					break;
				case 2 :
					for(var i=1;i<errCount;i++)
						this.ErrorItem[i].style.color = "red";
				case 3 :
					for(var i=1;i<errCount;i++){
					try{
						var span = document.createElement("SPAN");
						span.id = "__ErrorMessagePanel";
						span.style.color = "red";
						this.ErrorItem[i].parentNode.appendChild(span);
						span.innerHTML = this.ErrorMessage[i].replace(/\d+:/,"*");
						}
						catch(e){$.layer.alert(e.description);}
					}
					this.ErrorItem[1].focus();
					break;
				default :
                    $.layer.alert(this.ErrorMessage.join("\n"));
					break;
			}
			return false;
		}
		return true;
	},
	
	//容器校验
	collectionValidate : function(collection) {
		this.ErrorItem[0] = collection;
		var count = collection.length;
		for(var i=0;i<count;i++){
			with(collection[i]){
				var _dataType = getAttribute("dataType");
				if(typeof(_dataType) == "object" || typeof(this[_dataType]) == "undefined")  continue;
				this.ClearState(collection[i]);
				if(getAttribute("require") == "false" && value == "") continue;
				switch(_dataType){
					case "Date" :
					case "Repeat" :
					case "Range" :
					case "Compare" :
					case "Custom" :
					case "Group" : 
					case "Limit" :
					case "LimitB" :
					case "SafeString" :
					case "CheckNumber" :
					case "CheckEmail":
					case "CheckPhone":
					case "CheckAccount":
					case "CheckUnSpecial":
					case "CheckChinese":
					case "CheckLetterAndUnderline":
					case "CheckPassword":
					case "CheckIdCardNumber":
						if(!eval(this[_dataType]))	{
							this.AddError(i, getAttribute("msg"));
						}
						break;
//                    case "NoChinese":
//                        if(!this[_dataType].test(value.trim())){
//                            this.AddError(i, getAttribute("msg"));
//                        }
//                        break;
					default :
						//不可全为空格
						if("" != value && "" === value.trim()) {
							this.AddError(i,getAttribute("msg"));

							break;
						}
						//正则校验
						if(!this[_dataType].test(value.trim())){
							this.AddError(i, getAttribute("msg"));
						}
						break;
				}
			}
		}
	},
	
	ErrorItem : [document.forms[0]],
	ErrorMessage : [0],
	
	//REGEX
	Require : /.+/,
	Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    Phone : /^1[34578]\d{9}$/,
	// Phone : /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/,
	Mobile : /^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/,
	Url : /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
	IdCard : /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
	IdCardNumber : /(^\d{15}$)|(^\d{17}([0-9]|X)$)/,
	Currency : /^\d+(\.\d+)?$/,
	Number : /^[1-9]\d*$/,
	Zip : /^[1-9]\d{5}$/,
	QQ : /^[1-9]\d{4,8}$/,
	Integer : /^[-\+]?\d+$/,
	Double : /^[-\+]?\d+(\.\d+)?$/,
	English : /^[A-Za-z]+$/,
	Chinese :  /^[\u0391-\uFFE5]+$/,
    NoChinese : /^([A-Za-z0-9]|[\s_\-\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]){1,20}$/,
	UnSafe : /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
	IP : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
    ChineseAndLetter :/^[\u4e00-\u9fa5a-zA-Z]+$/,
	NumberAndChinese :/^[\u4e00-\u9fa50-9]+$/,
	One2Hundred :/^(?:0|[1-9][0-9]?|100)$/,
	UnNumberLine :/^[A-Za-z0-9_-]+$/,
	Account :/^\w+$/,
	UnSpecial :/^[\u4e00-\u9fa5a-zA-Z0-9]+$/,
	LetterAndUnderline :/^[A-Za-z_]+$/,
    // Password : /^\w+$/,
	Password : /^[\w]{6,20}$/,

	IsSafe : function(str){return !this.UnSafe.test(str);},
	SafeString : "this.IsSafe(value)",
	Limit : "this.limit(value.length,getAttribute('min'),  getAttribute('max'))",
	LimitB : "this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))",
	Date : "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
	Repeat : "value == document.getElementsByName(getAttribute('to'))[0].value",
	Range : "getAttribute('min') < value && value < getAttribute('max')",
	Compare : "this.compare(value,getAttribute('operator'),getAttribute('to'))",
	Custom : "this.Exec(value, getAttribute('regexp'))",
	Group : "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
	CheckNumber : "this.IsNumber(value)",
	CheckEmail : "this.IsEmail(value)",
	CheckPhone : "this.IsPhone(value)",
	CheckAccount : "this.IsAccount(value)",
	CheckUnSpecial : "this.IsUnSpecial(value)",
	CheckChinese : "this.IsChinese(value)",
    CheckLetterAndUnderline : "this.IsLetterAndUnderline(value)",
    CheckPassword : "this.IsPassword(value)",
    CheckIdCardNumber : "this.IsIdCardNumber(value)",

    IsIdCardNumber : function (str) {
        return this.IdCardNumber.test(str);
    },
    IsPassword : function (str) {
        return this.Password.test(str);
    },
    IsLetterAndUnderline :function (str) {
        return this.LetterAndUnderline.test(str);
    },
    IsChinese :function (str) {
        return this.Chinese.test(str);
    },
	IsUnSpecial :function (str) {
        return this.UnSpecial.test(str);
    },
	IsAccount :function (str) {
        return this.Account.test(str);
    },
	IsPhone : function (str) {
        return this.Phone.test(str);
    },
	IsEmail : function(str){
		return this.Email.test(str);
	},
	IsNumber : function (str) {
		return this.Number.test(str);
    },
	limit : function(len,min, max) {
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
	},
	
	LenB : function(str) {
		return str.replace(/[^\x00-\xff]/g,"**").length;
	},
	
	ClearState : function(elem) {
		with(elem){
			if(style.color == "red")
				style.color = "";
			var lastNode = parentNode.childNodes[parentNode.childNodes.length-1];
			if(lastNode.id == "__ErrorMessagePanel")
				parentNode.removeChild(lastNode);
		}
	},
	
	AddError : function(index, str) {
		//this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
		this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0][index];
		this.ErrorMessage[this.ErrorMessage.length] = str;
	},
	
	Exec : function(op, reg) {
		return new RegExp(reg,"g").test(op);
	},
	
	compare : function(op1,operator,op2) {
		switch (operator) {
			case "NotEqual":
				return (op1 != op2);
			case "GreaterThan":
				return (op1 > op2);
			case "GreaterThanEqual":
				return (op1 >= op2);
			case "LessThan":
				return (op1 < op2);
			case "LessThanEqual":
				return (op1 <= op2);
			default:
				return (op1 == op2);            
		}
	},
	
	MustChecked : function(name, min, max) {
		var groups = document.getElementsByName(name);
		var hasChecked = 0;
		min = min || 1;
		max = max || groups.length;
		for(var i=groups.length-1;i>=0;i--)
			if(groups[i].checked) hasChecked++;
		return min <= hasChecked && hasChecked <= max;
	},
	
	IsDate : function(op, formatString) {
		formatString = formatString || "ymd";
		var m, year, month, day;
		switch(formatString){
			case "ymd" :
				m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
				if(m == null ) return false;
				day = m[6];
				month = m[5]--;
				year =  (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
				break;
			case "dmy" :
				m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
				if(m == null ) return false;
				day = m[1];
				month = m[3]--;
				year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
				break;
			default :
				break;
		}
		if(!parseInt(month)) return false;
		month = month==12 ?0:month;
		var date = new Date(year, month, day);
		return (typeof(date) == "object" && year == date.getFullYear() && month == date.getMonth() && day == date.getDate());
		function GetFullYear(y){return ((y<30 ? "20" : "19") + y)|0;}
	}
}