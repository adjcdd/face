/********************************************
*  全局变量js
*  create by ljs at 2017-11-04
*********************************************/
//日志写入磁盘api
var gOcxLogApi = null;
//身份证读取api
//var gOcxIDCardApi = null;
//摄像头拍照
var gPhotographBase64 = "";
var gTakePhotographFlag = "0";//0-拍照   1-重拍
// 用于存放 MediaRecorder 对象和音频Track，关闭录制和关闭媒体设备需要用到
var recorder, mediaStream;
// 用于存放录制后的音频文件对象和录制结束回调
var recorderFile, stopRecordCallback;
// 用于存放是否开启了视频录制
var videoEnabled = false;