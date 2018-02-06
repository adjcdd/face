/********************************************
*	文件:ocx js
*	功能:ocx控件公共调用
*	作者:柳金硕
*	时间:2017-10-27
*	版本:1.0
*********************************************/
$(document).ready(function(){
	//加载磁盘写入ocx控件
	$('body').prepend('<OBJECT id="IJnlAPI" classid="clsid:76140327-5FC8-41AA-8AE6-8FEBFABB8C77" codebase="JnlAPI.CAB#version=1,0,0,4" width="0" height="0" style="display:none" ></OBJECT>');
	gOcxLogApi = document.getElementById("IJnlAPI");
	
	//加载身份证读取ocx控件
//	$('body').prepend('<OBJECT id="IIDCardAPI" classid="clsid:82830749-6EA3-4987-8D78-C2A1FD599060" width="0" height="0" codebase="" style="display:none" ></OBJECT>');
//	gOcxIDCardApi = document.getElementById("IIDCardAPI");
	
	//加载摄像头拍照ocx控件
//	$('body').prepend('<OBJECT id="MyPhoto" CLASSID="CLSID:22FBAE15-359B-45B1-81A1-76ECA1969A71" width="0" height="0" codebase="" style="display:none" ></OBJECT>');
//	gPhotographApi = document.getElementById("MyPhoto");
});

//摄像头拍照
function photograph(){
	//--拍照页面--
	this.createLogDiv=function (){
		var consoleDiv=document.createElement("div");
		consoleDiv.id="consoleDiv";
		consoleDiv.style.position="absolute";
		consoleDiv.style.border="1px solid #4581B4";
		consoleDiv.style.width=document.body.clientWidth;
		consoleDiv.style.height="240px";
		consoleDiv.style.top=document.body.scrollTop+document.body.clientHeight-240;
		consoleDiv.style.left=document.body.scrollLeft+1;
		consoleDiv.style.display="none";
		var titleDiv=document.createElement("div");
		titleDiv.style.height="20px";
		var str='<table cellpadding="0" cellspacing="0" border="0" width="100%" style="background: #39C;border-top:1px solid #4581B4; border-bottom:1px solid #4581B4; border-left:1px solid #4581B4; border-right:1px solid #4581B4; padding:0 0 10px 0; font-size:13px; color:#fff;" >';
		str+='<tr><td width="51%" style="margin:0; padding:5px 0 0 10px; background:no-repeat top left; width:100%; height:20px;"><span><b>Console</b></span></td>';
		str+='<td width="49%" style="margin:0; padding:0; background: no-repeat top right;100% height:20px;"><input type="button" id="logClear" value="清除" style="BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #2C59AA 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #2C59AA 1px solid"/>&nbsp;<input type="button" id="logClose" value="关闭" style="BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #2C59AA 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #2C59AA 1px solid"/></td></tr></table>';
		titleDiv.innerHTML=str;
		var logDiv=document.createElement("div");
		logDiv.id="logDiv";
		logDiv.style.width=document.body.clientWidth-3;
		logDiv.style.height="214px";
		logDiv.style.backgroundColor="white";
		logDiv.style.overflow="auto";
		logDiv.innerHTML="<table border='0' width='100%' cellpadding='0' cellspacing='0' style='background:#FFF; padding:0 0 10px 0; font-size:12px;'><tbody id='logTab'></tbody></table>"
		consoleDiv.appendChild(titleDiv);
		consoleDiv.appendChild(logDiv);
		document.body.appendChild(consoleDiv);
	};
	
	//--创建一行信息--
	this.createTr=function (v,color){
		var tr=document.createElement("tr");
		var td=document.createElement("td");
		td.style.color=color;
		td.style.width=document.body.clientWidth-3;
		td.innerText=v;
		tr.appendChild(td);
		return tr;
	};
	//--检测写入文件的内容是否过长--
	this.check =function (v){
		return v.length>20000?confirm("写入log4js内容过长,是否继续?"):true;
	};
	
	//--处理error方法--
	this.error=function (value){
		var str="error: ["+this.getTime()+"] "+(value.length>10000?(value.substring(0,10000)+"...内容过长"):value);
		this.saveInfo(str,1,"red");
	};
	//--处理warn方法--
	this.warn=function (value){
		var str="warn : ["+this.getTime()+"] "+(value.length>20000?(value.substring(0,10000)+"...内容过长"):value);
		this.saveInfo(str,2,"#848833");
	};
	//--处理info方法--
	this.info=function (value){
		var str="info : ["+this.getTime()+"] "+(value.length>20000?(value.substring(0,10000)+"...内容过长"):value);
		this.saveInfo(str,3,"#0000CC");
	};
	//--处理debug方法--
	this.debug=function (value){
		var str="Debug: ["+this.getTime()+"] "+(value.length>20000?(value.substring(0,10000)+"...内容过长"):value);
		this.saveInfo(str,4,"black");
	};
	
	//--得到当前时间--
	this.getTime=function (){
		var date = new Date();
		var now = ""; 
		now+=date.getFullYear()+"-"; 
		now+=(date.getMonth()+1)+"-";
		now+=date.getDate()+" "; 
		now+=date.getHours()+":"; 
		now+=date.getMinutes()+":"; 
		now+=date.getSeconds(); 
		return now;
	};
	
	//--得到当前日期--
	this.getDay = function (){
		var date = new Date();
		var day = ""; 
		day += date.getFullYear()+"-"; 
		day += (date.getMonth()+1)+"-";
		day += date.getDate();
		return day;
	};
	
	//--记录日志--
	this.saveInfo=function (str,lev,color){
		if(this.openFlag=="0"){
			return false;
		}else if(this.openFlag=="1"){
			this.saveAsTxt(str,lev);
		}else if(this.openFlag=="2"){
			this.saveAsLogDiv(str,color)
		}else if(this.openFlag=="3"){
			this.saveAsTxt(str,lev);
			this.saveAsLogDiv(str,color)
		}
	};
	
	//--显示在控制台--
	this.saveAsLogDiv=function (str,color){
		document.getElementById("logTab").appendChild(this.createTr(str,color));
		document.getElementById("logDiv").scrollTop = document.getElementById("logDiv").scrollHeight;
	};
	
	//--将信息写入txt--
	this.saveAsTxt=function (str,lev){
		if(this.grade<lev){
			return false;
		}
		try{
			gOcxLogApi.WriteJnl("f:\\"+ this.getDay() +".txt", "INFO", str);
//			var fso = new  ActiveXObject("Scripting.FileSystemObject");  
//			var url="c:\\log4js.log";
//			if(fso.FileExists(url)){
//				var f = fso.OpenTextFile(url,8);
//				f.WriteLine(str); 
//				f.Close();  
//				f=null;
//			}else{
//				var f = fso.CreateTextFile(url);
//				f.WriteLine(str); 
//				f.Close();  
//				f=null;
//			}
		}catch(e){
			
		}
	};
	//--快捷键设置--
//	this.log4jsKey=function (e){
//		e=window.event||e;
//		if(e.keyCode==112){		//F1
//			document.body.onhelp=function removeOnHelp(){return false};
//			event.keyCode=0;
//			event.returnValue=false;
//			new log4js("","").setLogDivShow();
//			document.getElementById("logDiv").scrollTop = document.getElementById("logDiv").scrollHeight;
//			return false;
//		}
//	};
	
	this.log4jsMove=function (){
		var consoleDiv=document.getElementById("consoleDiv");
		consoleDiv.style.top = document.body.scrollTop+document.body.clientHeight-240;
		consoleDiv.style.left = document.body.scrollLeft+1;
	};
	
	//--控制显示控制台--
	this.setLogDivShow=function (){
		document.getElementById("consoleDiv").style.display=document.getElementById("consoleDiv").style.display=="none"?"":"none";
	};
	
	//--清除控制台信息--
	this.clearLogDivInfo=function (){
		while(document.getElementById("logTab").hasChildNodes()){
			document.getElementById("logTab").removeChild(document.getElementById("logTab").firstChild);
		}
	};
	
	//--设置清除,关闭按钮--
	this.setLogDivBtn=function (){
		document.getElementById("logClear").onclick=function clear(){new log4js("","").clearLogDivInfo()};
		document.getElementById("logClose").onclick=function close(){new log4js("","").setLogDivShow()};
	}
	
	//--初始化函数--
	this.init=function (){
		if(!document.getElementById("logDiv")){
			this.createLogDiv();
			this.setLogDivBtn();
//			this.saveAsTxt();
			//document.body.attachEvent("onkeydown",this.log4jsKey);
			//window.attachEvent("onscroll",this.log4jsMove);
		}
	};
	
	//日志初始化
	this.init();
}