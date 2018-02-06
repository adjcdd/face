package com.grgbanking.framework.domains.common;

public class ErrorCode {

	//-------------------------业务异常类-------------------------
	public static final String SUCCESS = "0"; //成功

	public static final String EXCEPTION = "500"; //系统未知异常

	public static final String INIT_FAIL = "505"; // 初始化识别

	public static final String SYSTEM_BUSY = "600"; // 系统繁忙

	public static final String NOT_VALID_CLIENT = "403"; //未在平台注册的客户端

	public static final  String CALL_ALGORITHM_FAIL = "605"; // 算法调用失败

	public static final String ALG_NOT_SUPPORT = "1001"; // 平台不支持

	public static final String WRONG_PARAM = "10000"; // 参数错误

	public static final String VERIFY_FAIL = "10001"; //人脸认证拒绝

	public static final String CHECK_FACE_FAIL = "10002"; // 人脸检测失败
	public static final String NO_FACE_FOUND = "10003"; // 未检测到人脸
	public static final String REG_FACE_FAIL = "10018"; // 注册人脸失败
	public static final String TOO_MANY_FACE = "10019"; // 注册人脸达到上限
	public static final String HAS_OTHER_FACE = "10024"; // 当前用户除了默认特征外还有其他特征
	public static final String THE_LAST_FACE = "10025"; // 已经是最后一张人脸特征

	public static final String NO_USER_FOUND = "10004"; // 未找到用户

	public static final String DUPLICATE_USER = "10005"; // 用户已存在

	public static final String CHIP_IMG_EXISTS = "10006"; //芯片照已存在
	public static final String HD_IMG_EXISTS = "10007"; //高清照已存在
	public static final String FEATURE_ALREADY_EXISTS = "10023"; //用户特征已存在

	public static final String BAD_FINGERVEIN = "10008"; // 指静脉输入图像不合格
	public static final String GET_FINGERVEIN_TEMP_FAIL = "10009"; // 指静脉获取特征模板失败
	public static final String TWO_FINGER_NOT_SAME = "10010"; //两根手指不相同
	public static final String REGISTER_FINGER_FAIL = "10011"; // 指静脉注册失败
	public static final String VERIFY_FINGER_FAIL = "10012"; // 指静脉识别失败
	public static final String NO_FINGERVEIN_FOUND = "10013"; // 未找到注册指静脉模板
	public static final String FINGER_EXISTS = "100118"; // 用户指定的手指已注册指静脉或指纹


	public static final String CREATE_DEC_FAIL = "10014"; // 创建引擎失败
	public static final String VERIFY_VOICEPRINT_FAIL = "10015"; // 声纹认证失败
	public static final String CREATE_GROUP_FAIL = "10016"; // 创建组失败
	public static final String ADD_USER_TO_GROUP_FAIL = "10017"; // 指定注册用户进入组失败

	public static final String VERIFY_FINGERPRINT_FAIL = "10020";


	public static final String VOICE_INIT_FAIL = "10030"; // 语音初始化失败
	public static final String VOICE_BEGIN_FAIL = "10031"; // 语音打开会话失败
	public static final String VOICE_ERROR = "10032"; //语音音频数据错误
	public static final String VOICE_TOO_SHORT = "10033"; //音频数据太短
	public static final String VOICE_RETURN_EMPTY = "10034"; //引擎返回内容为空
	public static final String VOICE_RECOGNITION_FAIL = "10035"; //语音识别失败

	public static final String REG_HAS_DONE = "10021"; //注册已完成

	/*******************************************/
	/*************后台管理系统错误码************/
	/*******************************************/
	public static final String LOGIN_FAIL = "20001";

	public static final String EMPTY_RESULT = "20002"; // 数据不存在

	public static final String CALL_CLIENT_EXISTS = "20003"; // 客户端已存在

	public static final String DUPLICATE_DATA = "20004";  // 数据重复

	public static final String RELATION_DATA = "20005"; //数据有关联项

	public static final String ZIP_ERROR = "20006"; //上传zip包异常

	public static final String NO_CHANNEL = "20007"; //场景未绑定渠道

	public static final String NO_ADAPT = "20008"; //修改密码和确认密码不一致

	public static final String NO_EFFACTIVE = "20009"; //session无效

	public static final String USER_FORBIDEN = "20010"; //用户被禁用

	public static final String NO_EDIT_DELETE = "20011"; //一级菜单不可编辑删除

	public static final String NO_ORI_PWD = "20012"; //原密码输入错误

	public static final String NO_IDENTICAL = "20013"; //原密码和新密码不能一致

	public static final String NO_EQPWD = "20014"; //新密码确认密码不能一致


	public static final String ERROR_GETUSERINFO = "20015"; //用户平台注册用户信息失败

	public static final String NO_FACE_PLATFORMUSER = "20016"; //平台没有该注册人脸的用户

	public static final String NO_FINGERVEIN_PLATFORMUSER = "20017"; //平台没有该注册指静脉的用户

	public static final String NO_PLATFORMUSER = "20018"; //没有该平台注册用户

	public static final String FACE_PLATFORMUSER = "20019"; //用户已注册人脸

	public static final String FINGERVEIN_PLATFORMUSER = "20020"; //用户已注册指静脉

	public static final String VOICEPRINT_PLATFORMUSER = "20021"; //用户已注册声纹

	public static final String FINGERPRINT_PLATFORMUSER = "20022"; //用户已注册指纹

	public static final String NO_VOICEPRINT_PLATFORMUSER = "20023"; //平台没有该注册声纹的用户

	public static final String NO_FINGERPRINT_PLATFORMUSER = "20024"; //平台没有该注册指纹的用户


}
