/********************************************
*	文件:语音js
*	功能:系统各场景语音
*	作者:柳金硕
*	时间:2017-11-04
*	版本:1.0
*********************************************/
$(document).ready(function(){
	//请您把二代身份证放在读卡器上
	$('body').prepend('<audio id="pleaseInIdCardVoice"><source src="resources/common/voice/idcardStart.mp3" type="audio/mp3"></audio>');
	//二代身份证读取成功
	$('body').prepend('<audio id="idCardReadSuccessVoice"><source src="resources/common/voice/idCardReadSuccess.mp3" type="audio/mp3"></audio>');
})
//系统语音对象
var gVoiceObj = {
	/**
	 * 语音播放
	 * @param voiceTypeId {String} 语音类型ID
	 * @returns
	 */
	"play" : function(voiceTypeId) {
		gVoiceApi = document.getElementById(voiceTypeId);
		gVoiceApi.play();
	}
}