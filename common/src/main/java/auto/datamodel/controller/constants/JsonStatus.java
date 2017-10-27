/**
 * 
 */
package auto.datamodel.controller.constants;

import auto.util.JsonUtils;

public class JsonStatus {
    
	// config
	public static final String CHARSET = "utf-8";
	public static final String VERSION = "0.0.1";
    //properties name
    
    public static final String STATUS = "status";
    
    public static final String ERROR_TITLE = "errorTitle";
    
    public static final String ERROR_CODE = "errCode";
    
    public static final String ERROR_MSG = "msg";
    
    //status
    
    public static final int EMPTY = 1;
    
    public static final int ERROR = 0;
    
    public static final int SUCCESS = 200;
    
    //error code
    public static final int SUCCESS_CODE = 200;
    
    public static final int UNLOGIN_CODE = 1000;
    
    public static final int UNVERIFIED_CODE = 1001;
    
    public static final int INVALID_PARAMS_CODE = 1002;
    
    public static final int DUPLICATED_CODE = 1003;
    
    public static final int NOT_EXIST_CODE = 1004;
    
    public static final int EXPIRED_CODE = 1005;
    
    public static final int BUSINESS_ERROR_CODE = 1006;
    
    public static final int INTERNAL_ERROR_CODE = 1007;
    
    public static final int UNSURPORTED_CODE = 1008;
    
    public static final int REALNAME_AUTHENTICATTION_ERROR = 1009;
    
    public static final int CONSIGNEE_ADDRESS_ERROR = 1010;
    
    public static final int DELETE_ERROR = 1011;
    
    public static final int GRANT_CODE_EXPIRED = 1012;
    
    public static final int UPLOAD_ERROR = 1013;
    
    public static final int UN_GRANT_UPLOAD = 1014;
    
    public static final int NOT_EXIT_RESOURCE = 1015;
    
    
    //data not empty error
    public static final int GRANT_CODE_EMPTY = 2000;
    
    public static final int PROVINCE_CODE_EMPTY = 2001;
    
    public static final int CITY_CODE_EMPTY = 2002;
    
    public static final int DISTRICT_CODE_EMPTY = 2003;
    
    public static final int ID_NUMBER_EMPTY = 2004;
    
    public static final int REAL_NAME_EMPTY = 2005;
    
    public static final int FULL_ADDRESS_EMPTY = 2006;
    
    public static final int CONTACT_EMPTY = 2007;
    
    public static final int DEFAULT_ADDRESS_EMPTY = 2008;
    
    public static final int GOODSID_EMPTY = 2009;
    
    public static final int COMMON_ID_EMPTY = 2010;
    
    public static final int NOT_EXIT_EVALUATION = 2011;
    
    public static final int GRANT_FAILED = 2012;
    
    public static final int CART_ITEM_EMPTY = 2013;
    
    public static final int DEVICEID_CODE_EMPTY = 2014;
    
    public static final int PICCODE_CODE_ERROR = 2015 ;
    
    public static final int PHONECODE_CODE_ERROR = 2016 ;
    
    public static final int QRCODE_CONTENT_EMPTY = 2017 ;
    
    public static final int QRCODE_EMPTY = 2018 ;
    
    //coupon
    
    public static final int COUPON_EMPTYM = 2019;
    
    public static final int USER_CODE_ERROT = 3000;
    
    
    //data not empty error msg 
    public static final String unGrantUpload = "未经授权图片上传被拒绝";
    
    public static final String grantFailed = "授权失败";
    
    public static final String grantCodeExpired = "未登陆或无权限操作";
    
    public static final String grantCodeEmptyMsg = "授权码不能为空！";
    
    public static final String provinceCodeEmptyMsg = "省份代码不能为空！";
    
    public static final String cityCodeEmptyMsg = "城市代码不能为空！";
    
    public static final String districtCodeEmptyMsg = "区县代码不能为空！";
    
    public static final String idNumberEmptyMsg = "身份证号不能为空！";
    
    public static final String realNameEmptyMsg = "真实姓名不能为空！";
    
    public static final String fullAddressEmptyMsg = "详细地址不能为空！";
    
    public static final String contactEmptyMsg = "联系方式不能为空！";
    
    public static final String defaultAddressEmptyMsg = "默认地址选择不能为空！";
    
    public static final String deleteErrorMsg = "删除失败";
    
    public static final String goodsIdEmptyMsg = "商品ID不能为空";
    
    public static final String goodsQtyEmptyMsg = "商品数量不能为空";
    
    public static final String commonIdEmpty = "Id不能为空";
    
    public static final String uploadErrorMsg = "图片上传失败";
    
    public static final String deviceIdEmptyMsg = "设备Id不能为空";
    
    public static final String piccode_msg_error = "图形验证码错误" ;
    
    public static final String phonecode_msg_error= "手机验证码错误" ;
    
    public static final String notExitResource = "请求资源不存在";
    
    public static final String qrcodeContentEmptyMsg = "二维码内容为空";

    public static final String qrcodeEmptyMsg = "二维码生成失败";
    
    //status msg
    public static final String successMsg = "success";
    
    public static final String notExitEvaluation = "该评论不存在或已删除";
    
    public static final String  realNameAuthenticationError =  "姓名和身份证不匹配";
    
    public static final String  consigneeAddressError = "收获地址信息不全";
    
    public static final String businessErrorMsg = "服务器出现未知错误！";
    
    public static final String unlogin = "请先登录";
    
    public static final String unverify = "请先进行身份验证";
    
    public static final String duplicatedVerify = "已有认证权限，无需再次认证";
    
    public static final String invalidTelephone = "请输入正确的手机号";
    
    public static final String duplicatedTelephone = "该手机已被其他账号绑定";
    
    public static final String emptyPassword = "密码至少为5位";
    
    public static final String invalidPassword = "密码不正确";
    
    public static final String unsetPassword = "该用户尚未设置密码，请使用找回密码进行设置";
    
    public static final String invalidVerifyCode = "验证码错误";
    
    public static final String limitedVerifyCode = "验证码频率超过限制";
    
    public static final String duplicatedUsername = "该用户名已存在";
    
    public static final String notExistUsername = "该用户不存在";
    
    public static final String emptyIdCardKey = "请添加证件图片";
    
    public static final String emptyFaviconKey = "请上传头像";
    
    public static final String invalidEmail = "请输入工作或学校邮箱";
    
    public static final String duplicatedEmail = "该邮箱已被他人认证";
    
    public static final String emptyName = "请输入真实姓名";
    
    public static final String emptyIdCard = "请输入身份证号";
    
    public static final String invalidIdCard = "姓名身份证不匹配";
    
    public static final String duplicatedIdCard = "该身份证已被他人绑定";
    
    public static final String notExistIdCard = "该身份证对应账号不存在";
    
    public static final String emptyDebitCard = "请输入正确的银行卡号";
    
    public static final String emptyAlipayNumber = "请输入正确的支付宝账号";
    
    public static final String emptyAddress = "请输入详细地址";
    
    public static final String emptyPosition = "请输入搜索地址";
    
    public static final String notExistContract = "该合同不存在";
    
    public static final String notExistHouse = "该房间已删除";
    
    public static final String closedHouse = "该房间已出租";
    
    public static final String notExistLandlordHouse = "没有已发布的房间";
    
    public static final String duplicatedHouse = "当前仅允许发一套房";
    
    public static final String notExistMessage = "该消息不存在";
    
    public static final String emptyFeedback = "请输入反馈内容";
    
    public static final String emptyReport = "请输入举报内容";
    
    public static final String emptyCheckinTime = "请确认入住时间";
    
    public static final String invalidCheckinTime = "入住时间必须早于搬出时间";
    
    public static final String emptyExitTime = "请确认搬出时间";
    
    public static final String emptyTerminatedTime = "请确认终止时间";
    
    public static final String emptyPayingType = "请确认支付方式";
    
    public static final String emptyCredentials = "请上传出租资格证明照片";
    
    public static final String emptyImageKeys = "请上传图片";
    
    public static final String emptyHouseInfo = "房子信息不全";
    
    public static final String emptyRoomInfo = "房间信息不全";
    
    public static final String emptyDistrict = "请确认房源地区";
    
    public static final String emptyRoomNumber = "请确认户型";
    
    public static final String emptyArea = "请确认面积";
    
    public static final String emptyRoomArea = "请确认房间面积";
    
    public static final String emptyRoomOrientation = "请确认房间朝向";
    
    public static final String emptyPrice = "请输入每月租金";
    
    public static final String emptyRoomPrice = "请输入房间每月租金";
    
    public static final String emptyFloor = "请确认楼层";
    
    public static final String emptyDescription = "请添加房子详情描述";
    
    public static final String emptyContact = "请添加至少一种联系方式";
    
    public static final String emptyRentType = "请确认出租方式";
    
    public static final String emptyCoupon = "请确认红包使用金额";
    
    public static final String emptyBalance = "请确认余额使用金额";
    
    public static final String emptyCost = "请确认支付金额";
    
    public static final String emptyAmount = "请输入正确的金额";
    
    public static final String emptyPaymentType = "请确认支付方式";
    
    public static final String emptyOrders = "请选择待付账单";
    
    public static final String notExistPayment = "该账单不存在";
    
    public static final String withdrawalOrder = "存在未支付的账单，完成支付后可立即提现";
    
    public static final String emptyWithdrawalType = "请选择提现类型";
    
    public static final String emptyWithdrawalNumber = "请输入提现账号";
    
    public static final String contractTenant = "租客信息缺失";
    
    public static final String contractLandlord = "房东信息缺失";
    
    public static final String confirmedCredentials = "房产证明已通过审核，无需修改";
    
    public static final String busy = "网络不给力，稍后再试试吧";
    
    public static final String expired = "数据已过时，请刷新后重试";
    
    public static final String invalidApi = "接口调用错误";
    
    public static final String unkownError = "出现未知错误，稍后再试试~更多问题可在“建议”中进行反馈";
    
    public static final String duplicatedActivity = "该活动仅限参与一次";
    
    public static final String expiredActivity = "该活动已过有效期";
    
    public static final String incompletePrice = "请完成所有房间价格填写";
    
    public static final String notExistTelephone = "暂无电话号码";
    
    public static final String limitedTelephone = "该手机号被运营商屏蔽";
    
    public static final String ownHouse = "该房子为自己发布的房子";
    
    public static final String limitedContact = "你今天联系的房东太多了\n明天再继续尝试吧";
    
    public static final String cheatContact = "你联系的房东过多，认证权限暂停\n请联系客服补充资料\n审核通过后将获得永久认证权限";
    
    public static final String limitedCotenant = "请先标记分租该房";
    
    public static final String invalidOauth = "第三方授权错误";
    
    public static final String emptyOauth = "第三方账号不存在";
    
    public static final String limitedSubscribeTitle = "订阅条件已达上限";
    
    public static final String limitedSubscribeContent = "最多同时订阅5个订阅条件\n可在“个人”——“我的订阅”中编辑订阅条件";
    
    public static final String duplicatedSubscribeTitle = "该地址已存在订阅";
    
    public static final String duplicatedSubscribeContent = "已在原地址更新订阅条件";
    
    public static final String notExistOauth = "该账户没有对应的第三方登录号";
    
    public static final String duplicatedWechat = "该微信已绑定其他账号";
    
    public static final String unrentable = "该房间信息不完整，请先填写必要信息";
    
    //coupon 
    public static final String couponEmptyMsg = "还没有任何经销商发布代金券";
    
    //default response
    
    public static final String ERROR_STATUS = JsonUtils.toJson(
            STATUS, ERROR, ERROR_CODE, INTERNAL_ERROR_CODE, ERROR_MSG, unkownError);
    
    public static final String EMPTY_STATUS = JsonUtils.toJson(STATUS, EMPTY);
    
    public static final String SUCCESS_STATUS = JsonUtils.toJson(STATUS, SUCCESS_CODE);
}
