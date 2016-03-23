package com.jieshun.jslifesupporter.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import util.L;
import util.ListUtils;
import util.ObjectUtils;
import util.StringUtils;

import android.support.v4.util.ArrayMap;

import com.jht.jsif.comm.ServiceRequestParam;
import com.jht.jsif.comm.TXDataObject;
import com.jieshun.jslifesupporter.entity.MediaInfo;
import com.jieshun.jslifesupporter.entity.ParkInfo;
import com.jieshun.jslifesupporter.entity.ParkingOrderInfo;
import com.jieshun.jslifesupporter.entity.PrivilegeTotalInfo;
import com.jieshun.jslifesupporter.entity.SaleDetailInfo;
import com.jieshun.jslifesupporter.entity.SalePlanInfo;
import com.jieshun.jslifesupporter.entity.ServiceInfo;
import com.jieshun.jslifesupporter.entity.ServicePic;
import com.jieshun.jslifesupporter.entity.User;
import com.jieshun.jslifesupporter.entity.VehiclePayOrder;
import com.jieshun.jslifesupporter.entity.VersionInfo;
import com.jieshun.jslifesupporter.protocol.JSCouldProtocol;

public class SupporterManage {

	//
	private Boolean result;

	private User user;
	// 版本信息
	private List<VersionInfo> versionInfoList;
	//商户打折优惠方案
	private List<SalePlanInfo> salePlanInfoList;
	// 请求服务列表
	private List<ServiceInfo> serviceInfoList;
	//打折信息列表
	private List<SaleDetailInfo> saleDetailInfoList;
	//打折优惠总额
	private List<PrivilegeTotalInfo> privilegeTotalInfoList;
	//停车场信息
	private List<ParkInfo> parkInfoList;
	//车牌支付订单提交
	private List<VehiclePayOrder> vehiclePayOrderList;
	// 多媒体信息
	private List<MediaInfo> mediaInfoList;
	//停车场订单信息
	private List<ParkingOrderInfo> parkingOrderInfoList;


	
	
	public List<ParkingOrderInfo> getParkingOrderInfoList() {
		return parkingOrderInfoList;
	}


	public List<VehiclePayOrder> getVehiclePayOrderList() {
		return vehiclePayOrderList;
	}

	public List<ParkInfo> getParkInfoList() {
		return parkInfoList;
	}

	public void setParkInfoList(List<ParkInfo> parkInfoList) {
		this.parkInfoList = parkInfoList;
	}

	public List<PrivilegeTotalInfo> getPrivilegeTotalInfoList() {
		return privilegeTotalInfoList;
	}

	public void setPrivilegeTotalInfoList(
			List<PrivilegeTotalInfo> privilegeTotalInfoList) {
		this.privilegeTotalInfoList = privilegeTotalInfoList;
	}

	public List<MediaInfo> getMediaInfoList() {
		return mediaInfoList;
	}

	public void setMediaInfoList(List<MediaInfo> mediaInfoList) {
		this.mediaInfoList = mediaInfoList;
	}

	public List<SaleDetailInfo> getSaleDetailInfoList() {
		return saleDetailInfoList;
	}

	public void setSaleDetailInfoList(List<SaleDetailInfo> saleDetailInfoList) {
		this.saleDetailInfoList = saleDetailInfoList;
	}

	public List<ServiceInfo> getServiceInfoList() {
		return serviceInfoList;
	}

	public void setServiceInfoList(List<ServiceInfo> serviceInfoList) {
		this.serviceInfoList = serviceInfoList;
	}


	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public List<SalePlanInfo> getSalePlanInfoList() {
		return salePlanInfoList;
	}

	public void setSalePlanInfoList(List<SalePlanInfo> salePlanInfoList) {
		this.salePlanInfoList = salePlanInfoList;
	}

	public List<VersionInfo> getVersionInfoList() {
		return versionInfoList;
	}

	public void setVersionInfoList(List<VersionInfo> versionInfoList) {
		this.versionInfoList = versionInfoList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// 用户登录请求
	public ServiceRequestParam packUserLoginRequestParam(User user,
			String openFireRes) {

		// 创建参数
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("sign", user.getUserName());
		attributes.put("password", user.getUserPassword());
		attributes.put("userType", user.getUserType());
		attributes.put("opfResource", openFireRes);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				attributes, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);
		// 获取请求对象
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_SYS_LOGIN, null, datas);
		return param;
	}

	// 用户登录数据返回
	public void receiveUserLogin(List<TXDataObject> dataList) {

		if (ListUtils.isEmpty(dataList))
			return;
		this.user = new User();
		for (TXDataObject dataObj : dataList) {

			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			this.user.setNickName(ObjectUtils.nullStrToEmpty(map
					.get("nickName")));
			this.user.setUserId(ObjectUtils.nullStrToEmpty(map.get("userId")));
			this.user.setUserType(ObjectUtils.nullStrToEmpty(map
					.get("userType")));

		}

	}

	// 获取个人配置信息
	public ServiceRequestParam packGetUserConfigInfoParam(User user) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("userId", user.getUserId());
		attributes.put("userType", user.getUserType());
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				attributes, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);
		// 获取请求对象
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_GETUSERCONFIGINFO, null, datas);
		return param;
	}
	// 获取个人信息数据返回
	public void receiveGetUserConfigInfo(List<TXDataObject> dataList) {

		if (ListUtils.isEmpty(dataList))
			return;
		//		this.pushSetInfoList = new ArrayList<PushSetInfo>();
		for (TXDataObject dataObj : dataList) {
			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			if (StringUtils.isEquals(dataObj.getObjectId(), "USERBASEINFO")) {
				if (this.user == null)
					this.user = new User();
				//用户姓名
				this.user.setName(ObjectUtils.nullStrToEmpty(map.get("name")));
				//用户名（手机号）
				this.user.setUserName(ObjectUtils.nullStrToEmpty(map.get("userName")));
				this.user.setUserType(ObjectUtils.nullStrToEmpty(map.get("userType")));
				this.user.setUserPassword(ObjectUtils.nullStrToEmpty(map.get("userPassword")));
				this.user.setStatus(ObjectUtils.nullStrToEmpty(map.get("status")));
				this.user.setUserId(ObjectUtils.nullStrToEmpty(map.get("userId")));
				this.user.setNickName(ObjectUtils.nullStrToEmpty(map.get("nickName")));
			} else if (StringUtils.isEquals(dataObj.getObjectId(), "SHORTCUT")) {


			} else if (StringUtils.isEquals(dataObj.getObjectId(),
					"PUSHMSGCONFIG")) {


			}

		}

	}

	// 设置用户信息
	public ServiceRequestParam packSetUserInfoRequestParam(User user) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getUserId());
		map.put("type", user.getUserType());
		map.put("name", user.getName());
		map.put("nickName", user.getNickName());
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_SETUSERINFO, null, datas);
		return param;
	}

	//
	// 获取用户信息
	public ServiceRequestParam packGetUserInfoRequestParam(String userID) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_GETUSERINFO, null, datas);
		return param;
	}

	// 获取个人信息数据返回
	public void receiveGetUserInfo(List<TXDataObject> dataList) {

		if (ListUtils.isEmpty(dataList))
			return;
		for (int i = 0; i < dataList.size(); i++) {

			if (this.user == null)
				this.user = new User();
			TXDataObject dataObj = dataList.get(i);
			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			user.setUserType(ObjectUtils.nullStrToEmpty(map.get("userType")));
			user.setName(ObjectUtils.nullStrToEmpty(map.get("name")));
			user.setStatus(ObjectUtils.nullStrToEmpty(map.get("status")));
			user.setNickName(ObjectUtils.nullStrToEmpty(map.get("nickName")));
		}

	}

	/**
	 * 
	 * 修改用户密码
	 * 
	 * @param user
	 *            用户信息实体
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	public ServiceRequestParam packUpdateUserPasswordRequestParam(User user,
			String newPwd) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getUserId());
		map.put("userName", user.getUserName());
		map.put("userType", user.getUserType());
		map.put("password", user.getUserPassword());
		map.put("newPwd", newPwd);

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_UPDATEUSERPASS, null, datas);
		return param;
	}

	// 用户注册
	public ServiceRequestParam packUserRegisterRequestParam(User user,
			String vertificationCode) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sign", user.getUserName());
		map.put("userType", user.getUserType());
		map.put("password", user.getUserPassword());
		map.put("vertificationCode", vertificationCode);

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_REGUSERINFO, null, datas);
		return param;
	}

	// 重置用户密码
	public ServiceRequestParam packResetUserPasswordRequestParam(User user, String newPwd) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getUserId());
		map.put("userName", user.getUserName());
		map.put("userType", user.getUserType());
		map.put("password", user.getUserPassword());
		map.put("newPwd", newPwd);

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_UPDATEUSERPASS, null, datas);
		return param;
	}
	//	// 重置用户密码
	//	public ServiceRequestParam packResetUserPasswordRequestParam(User user,
	//			String vertificationCode, String newPwd) {
	//		// 创建参数----------------
	//		Map<String, Object> map = new HashMap<String, Object>();
	//		map.put("sign", user.getUserName());
	//		map.put("userType", user.getUserType());
	//		map.put("vertificationCode", vertificationCode);
	//		map.put("newPwd", newPwd);
	//		
	//		// 创建业务对象
	//		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
	//				map, null);
	//		// 加入业务对象列表
	//		List<TXDataObject> datas = new ArrayList<TXDataObject>();
	//		datas.add(dataObj);
	//		
	//		// 获取请求对象----------------
	//		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
	//				ServiceID.JSCSP_USER_RESETUSERPASS, null, datas);
	//		return param;
	//	}

	// 修改用户昵称
	public ServiceRequestParam packUpdateUserNickRequestParam(User user) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", user.getUserId());
		map.put("nickName", user.getNickName());

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_UPDATEUSERNICK, null, datas);
		return param;
	}

	// 绑定手机
	public ServiceRequestParam packUserBindCellRequestParam(User user,
			String vertificationCode) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", user.getUserId());
		map.put("sign", user.getUserName());
		map.put("userType", user.getUserType());
		map.put("vertificationCode", vertificationCode);
		//			map.put("cellPhoneNo", user.getCellphoneNo());

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_USER_BINDCELL, null, datas);
		return param;
	}

	/**
	 * 录入反馈意见
	 * 
	 * @return param
	 */
	public ServiceRequestParam addContentFeedback(String userId,
			String userType, String userName, String opinionType,
			String opinionSource, String opinionMsg) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("userType", userType);
		map.put("userName", userName);
		map.put("opinionType", opinionType);
		map.put("opinionSource", opinionSource);
		map.put("opinionMsg", opinionMsg);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_FEEDBACK_ADDCONTENT, null, datas);
		return param;
	}

	/**
	 * 获取验证码
	 * 
	 * @param userId
	 *            用户id
	 * @param telephone
	 *            手机号
	 * @param bizType
	 *            获取验证码业务类型 REGISTER:注册 RESETPWD:重置密码 BINDPHONE：绑定手机
	 *            OPENSERVICE:开通服务 UPDATEOPNENPWD:修改开启设备密码
	 * @return
	 */
	public ServiceRequestParam packGetVerifycodeRequestParam(String userId,
			String telephone, String bizType) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("sign", telephone);
		map.put("bizType", bizType);

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_SYS_GETVERIFYCODE, null, datas);
		return param;
	}

	// 打折优惠方案
	public ServiceRequestParam packGetSalePlanParam(String userId) {

		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);
		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_ORDER_SALEPLAN, null, datas);
		return param;
	}

	// 打折优惠方案返回
	public void receiveGetSalePlan(List<TXDataObject> dataList) {

		this.salePlanInfoList = new ArrayList<SalePlanInfo>();
		for (TXDataObject dataObj : dataList) {
			Map<String, Object> map = (Map<String, Object>) dataObj.getAttributes();
			SalePlanInfo salePlanInfo = new SalePlanInfo();
			salePlanInfo.setSalePlanId(ObjectUtils.nullStrToEmpty(map.get("plan_id")));
			salePlanInfo.setStrategyId(ObjectUtils.nullStrToEmpty(map.get("strategyid")));
			salePlanInfo.setPlanNmae(ObjectUtils.nullStrToEmpty(map.get("plan_name")));
			salePlanInfo.setTacticsName(ObjectUtils.nullStrToEmpty(map.get("tactics_name")));
			salePlanInfo.setTacticsWay(ObjectUtils.nullIntegerToDefault(map.get("tactics_way")));
			salePlanInfo.setAmount(ObjectUtils.nullDoubleToDefault(map.get("amount")));
			salePlanInfoList.add(salePlanInfo);
		}

	}
	/**
	 * 获取停车场信息
	 * @param userId
	 * @return
	 */
	public ServiceRequestParam packGetParkInfoParam(String userId) {

		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);
		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_INFO_QUERYPARK, null, datas);
		return param;
	}

	/**
	 * 获取停车场信息返回
	 * @param dataList
	 */
	public void receiveGetParkInfo(List<TXDataObject> dataList) {

		this.parkInfoList = new ArrayList<ParkInfo>();
		for (TXDataObject dataObj : dataList) {
			Map<String, Object> map = (Map<String, Object>) dataObj.getAttributes();
			ParkInfo parkInfo = new ParkInfo();
			parkInfo.setParkId(ObjectUtils.nullStrToEmpty(map.get("parkid")));
			parkInfo.setBusinesserId(ObjectUtils.nullStrToEmpty(map.get("businesserid")));
			parkInfo.setBusinesserName(ObjectUtils.nullStrToEmpty(map.get("businessername")));
			parkInfo.setBusinesserCode(ObjectUtils.nullStrToEmpty(map.get("businesser_code")));
			parkInfo.setParkCode(ObjectUtils.nullStrToEmpty(map.get("parkcode")));
			parkInfo.setParkName(ObjectUtils.nullStrToEmpty(map.get("parkname")));
			parkInfo.setAreaId(ObjectUtils.nullStrToEmpty(map.get("area_id")));
			parkInfoList.add(parkInfo);
		}

	}

	/**
	 * 商户打折接口
	 * @param planId 方案Id
	 * @param operateId 操作员Id
	 * @param vehicleId 卡号/车牌号
	 * @return
	 */
	public ServiceRequestParam packExecuteSaleParam(String planId,String operateId,String vehicleId) {

		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("plan_id", planId);
		map.put("operatorid", operateId);
		map.put("vehicle_no", vehicleId);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);
		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_ORDER_EXECUTE_SALE, null, datas);
		return param;
	}

	// 商户打折接口返回
	public void receiveExecuteSale(List<TXDataObject> dataList) {

		for (TXDataObject dataObj : dataList) {
			Map<String, Object> map = (Map<String, Object>) dataObj.getAttributes();
			this.result =Boolean.valueOf(ObjectUtils.nullStrToEmpty(map.get("result")));
		}

	}

	/**
	 * packVehicleOrderSubmit 车牌订单提交
	 * @param userId
	 * @param parkCode 停车场标志
	 * @param carNo 车牌号码
	 * @param orderType 订单类型
	 * @return
	 */
	public ServiceRequestParam packVehicleOrderSubmit(String userId,String parkCode,String carNo,String orderType) {

		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("parkCode", parkCode);
		map.put("carNo", carNo);
		map.put("orderType", orderType);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);
		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_ORDER_CARNOGENERATE, null, datas);
		return param;
	}

	/**
	 * 车牌支付订单返回数据
	 * @param dataList
	 */
	public void receiveVehicleOrderSubmit(List<TXDataObject> dataList) {

		this.vehiclePayOrderList = new ArrayList<VehiclePayOrder>();
		for (TXDataObject dataObj : dataList) {
			Map<String, Object> map = (Map<String, Object>) dataObj.getAttributes();
			VehiclePayOrder vehiclePayOrder = new VehiclePayOrder();
			vehiclePayOrder.setOrderType(ObjectUtils.nullStrToEmpty(map.get("orderType")));
			vehiclePayOrder.setBussinesserId(ObjectUtils.nullStrToEmpty(map.get("bussinesserId")));
			vehiclePayOrder.setBussinesserCode(ObjectUtils.nullStrToEmpty(map.get("bussinesserCode")));
			vehiclePayOrder.setServiceFee(ObjectUtils.nullStrToEmpty(map.get("serviceFee")));
			vehiclePayOrder.setDeducFee(ObjectUtils.nullStrToEmpty(map.get("deducFee")));
			vehiclePayOrder.setDiscountFee(ObjectUtils.nullStrToEmpty(map.get("discountFee")));
			vehiclePayOrder.setTotalFee(ObjectUtils.nullStrToEmpty(map.get("totalFee")));
			vehiclePayOrderList.add(vehiclePayOrder);
		}
	}

	/**
	 * 扫码支付订单查询
	 * 
	 * @param userId
	 *            用户id
	 * @param QRKey
	 *            二维码信息
	 * @param orderType
	 *            CDP 月卡缴费 SP 扫码支付 VNP 车牌支付
	 * @return
	 */
	// 已修改 4-26
	public ServiceRequestParam queryParkingOrder(String userId, String QRKey,
			String orderType) {
		// 创建参数----------------
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userId", userId);
		map.put("QRKey", QRKey);
		map.put("orderType", orderType);

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_ORDER_SCANGENERATE, null, datas);
		return param;
	}

	/**
	 * 扫码支付订单查询返回
	 * 
	 * @param map
	 */
	// 已修改 4-26
	public void receiveParkingOrder(List<TXDataObject> dataList) {
		this.parkingOrderInfoList = new ArrayList<ParkingOrderInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			ParkingOrderInfo parkingOrderInfo = new ParkingOrderInfo();
			TXDataObject dataObj = dataList.get(i);
			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			parkingOrderInfo.setOrderNo(ObjectUtils.nullStrToEmpty(map
					.get("orderNo")));

			parkingOrderInfo.setBusinesserCode(ObjectUtils.nullStrToEmpty(map
					.get("businesserCode")));

			parkingOrderInfo.setMerGID(ObjectUtils.nullStrToEmpty(map
					.get("merGID")));

			parkingOrderInfo.setGoodName(ObjectUtils.nullStrToEmpty(map
					.get("goodName")));

			parkingOrderInfo.setParkingCode(ObjectUtils.nullStrToEmpty(map
					.get("parkCode")));

			parkingOrderInfo.setParkingName(ObjectUtils.nullStrToEmpty(map
					.get("parkName")));

			parkingOrderInfo.setCardNo(ObjectUtils.nullStrToEmpty(map
					.get("cardNo")));
			parkingOrderInfo.setCarNo(ObjectUtils.nullStrToEmpty(map
					.get("carNo")));
			parkingOrderInfo.setStartTime(ObjectUtils.nullStrToEmpty(map
					.get("startTime")));
			parkingOrderInfo.setServiceTime(ObjectUtils.nullStrToEmpty(map
					.get("serviceTime")));
			parkingOrderInfo.setCreateTime(ObjectUtils.nullStrToEmpty(map
					.get("createTime")));
			parkingOrderInfo.setEndTime(ObjectUtils.nullStrToEmpty(map
					.get("endTime")));
			parkingOrderInfo.setServiceFee(ObjectUtils.nullStrToEmpty(map
					.get("serviceFee")));
			parkingOrderInfo.setDeductFee(ObjectUtils.nullStrToEmpty(map
					.get("deductFee")));
			parkingOrderInfo.setDiscountFee(ObjectUtils.nullStrToEmpty(map
					.get("discountFee")));
			parkingOrderInfo.setTransportFee(ObjectUtils.nullStrToEmpty(map
					.get("transportFee")));
			parkingOrderInfo.setOtherFee(ObjectUtils.nullStrToEmpty(map
					.get("otherFee")));
			parkingOrderInfo.setTotalFee(ObjectUtils.nullStrToEmpty(map
					.get("totalFee")));
			parkingOrderInfo.setTradeStatus(ObjectUtils.nullStrToEmpty(map
					.get("tradeStatus")));
			parkingOrderInfo.setNotifyUrl(ObjectUtils.nullStrToEmpty(map
					.get("notifyUrl")));
			parkingOrderInfo.setAttach(ObjectUtils.nullStrToEmpty(map
					.get("attach")));
			parkingOrderInfo.setRetCode(ObjectUtils.nullIntegerToDefault(map
					.get("retcode")));
			parkingOrderInfo.setGoParkDesc(ObjectUtils.nullIntegerToDefault(map
					.get("goParkDesc")));
			parkingOrderInfo.setRemark(ObjectUtils.nullStrToEmpty(map
					.get("remark")));
			parkingOrderInfoList.add(parkingOrderInfo);

		}
	}
	
	/**
	 * 新增请求服务信息
	 * 
	 * @return param
	 */
	public ServiceRequestParam addRequestService(String userId, String areaId,
			ServiceInfo serviceInfo) {

		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("areaId", areaId);
		map.put("serviceType", serviceInfo.getServiceType());
		map.put("serviceTitle", serviceInfo.getServiceTitle());
		map.put("serviceContent", serviceInfo.getServiceContent());
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		List<ServicePic> servicePicList = serviceInfo.getServicePicList();
		if (!ListUtils.isEmpty(servicePicList)) {
			for (ServicePic servicePic : servicePicList) {

				TXDataObject picObj = new TXDataObject("objID");
				picObj.setObjectId("REQSERVICEPIC");
				Map<String, Object> picMap = new WeakHashMap<String, Object>();
				picMap.put("fileSize", servicePic.getFileSize());
				picMap.put("localUrl", servicePic.getPicLocalUrl());
				picMap.put("picIndex", servicePic.getPicIndex());
				picObj.setAttributes(picMap);
				dataObj.addSubItem(picObj);
			}
		}

		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_REQSERVICE_ADDREQSERVICE, null, datas);
		return param;
	}

	/**
	 * 新增请求服务返回
	 * 
	 * @param map
	 */
	public void receivAddRequestService(List<TXDataObject> dataList) {
		this.serviceInfoList = new ArrayList<ServiceInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			ServiceInfo serviceInfo = new ServiceInfo();
			TXDataObject dataObj = dataList.get(i);
			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			serviceInfo.setServiceId(ObjectUtils.nullStrToEmpty(map
					.get("serviceId")));
			serviceInfo.setPicName(ObjectUtils.nullStrToEmpty(map
					.get("picName")));
			serviceInfoList.add(serviceInfo);
		}
	}

	/**
	 * 更新请求服务上传状态
	 * 
	 * @return param
	 */
	public ServiceRequestParam updateUploadRequestServiceStatus(String userId,
			String serviceId, String isSuccess) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("serviceId", serviceId);
		map.put("isSuccess", isSuccess);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_REQSERVICE_UPDATPICSTATUS, null, datas);
		return param;
	}



	/**
	 * 打折记录查询
	 * @param userId
	 * @param beginTime 打折时间（开始）
	 * @param endTime 打折时间（结束）
	 * @return
	 */
	public ServiceRequestParam querySaleDetailList(String userId,
			String beginTime,String endTime,int pageSize, int pageIndex) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		map.put("begintime", beginTime);
		map.put("endtime", endTime);
		map.put("pageSize", pageSize);
		map.put("pageIndex", pageIndex);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_ORDER_QUERYSALEDETAILINFO, null, datas);
		return param;
	}

	public void receiveSaleDetailList(List<TXDataObject> dataList) {

		this.saleDetailInfoList = new ArrayList<SaleDetailInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			SaleDetailInfo saleDetailInfo = new SaleDetailInfo();
			TXDataObject dataObj = dataList.get(i);

			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			// 保存成功后 返回资讯id
			saleDetailInfo.setSaleId(ObjectUtils.nullStrToEmpty(map
					.get("id")));
			saleDetailInfo.setUserId(ObjectUtils.nullStrToEmpty(map
					.get("userid")));
			saleDetailInfo.setOrderId(ObjectUtils.nullStrToEmpty(map
					.get("orderid")));
			saleDetailInfo.setPlanId(ObjectUtils.nullStrToEmpty(map
					.get("plan_id")));
			// 版本创建 2014-06-09 17:31：00
			saleDetailInfo.setBusinesserId(ObjectUtils.nullStrToEmpty(map
					.get("businesser_id")));
			saleDetailInfo.setPlanName(ObjectUtils.nullStrToEmpty(map.get("plan_name")));
			saleDetailInfo.setStoreName(ObjectUtils.nullStrToEmpty(map
					.get("storename")));
			saleDetailInfo.setVehicleNo(ObjectUtils.nullStrToEmpty(map
					.get("vehicle_no")));
			saleDetailInfo.setCreateTime(ObjectUtils.nullStrToEmpty(map
					.get("createtime")));
			saleDetailInfo.setStatus(ObjectUtils.nullIntegerToDefault(map
					.get("status")));
			saleDetailInfo.setModule(ObjectUtils.nullIntegerToDefault(map
					.get("discountmode")));
			saleDetailInfo.setAmount(ObjectUtils.nullDoubleToDefault(map
					.get("amount")));
			saleDetailInfo.setOperatorName(ObjectUtils.nullStrToEmpty(map
					.get("operatorname")));
			saleDetailInfo.setUserStatus(ObjectUtils.nullStrToEmpty(map
					.get("use_status")));
			saleDetailInfoList.add(saleDetailInfo);
		}
	}
	/**
	 * 打折优惠总额查询
	 * @param userId
	 * @param beginTime 打折时间（开始）
	 * @param endTime 打折时间（结束）
	 * @return
	 */
	public ServiceRequestParam queryPrivilegeTotalList(String userId,
			String beginTime,String endTime) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		map.put("begintime", beginTime);
		map.put("endtime", endTime);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_ORDER_QUERYPRIVILEGETOTALINFO, null, datas);
		return param;
	}
	/**
	 * 打折优惠总额查询
	 * @param userId
	 * @param beginTime 打折时间（开始）
	 * @param endTime 打折时间（结束）
	 * @param status 状态（0 未同步，1 已同步）
	 * @return
	 */
	public ServiceRequestParam queryPrivilegeTotalList(String userId,
			String beginTime,String endTime,String status) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		map.put("begintime", beginTime);
		map.put("endtime", endTime);
		map.put("status", status);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_ORDER_QUERYPRIVILEGETOTALINFO, null, datas);
		return param;
	}

	public void receivePrivilegeTotalList(List<TXDataObject> dataList) {

		this.privilegeTotalInfoList = new ArrayList<PrivilegeTotalInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			PrivilegeTotalInfo privilegeTotalInfo = new PrivilegeTotalInfo();
			TXDataObject dataObj = dataList.get(i);

			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			// 保存成功后 返回资讯id
			privilegeTotalInfo.setTotalMoney(Double.valueOf(ObjectUtils.nullStrToEmpty(map
					.get("totalmoney"))));
			L.i("tag", "totalMoney===="+ObjectUtils.nullStrToEmpty(map
					.get("totalmoney")));
			privilegeTotalInfo.setTotalHour(Double.valueOf(ObjectUtils.nullStrToEmpty(map
					.get("totalhour"))));
			privilegeTotalInfo.setTotalCount(Integer.valueOf(ObjectUtils.nullStrToEmpty(map
					.get("totalcount"))));
			privilegeTotalInfoList.add(privilegeTotalInfo);
		}
	}

	/**
	 * 获取多媒体信息
	 * 
	 * @return param
	 */
	public ServiceRequestParam queryMediaInfo(String category, String type,
			String userType) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("type", type);
		map.put("userType", userType);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_INFO_QUERYMEDIA, null, datas);
		return param;
	}

	/**
	 * 获取图片信息返回
	 * 
	 * @param map
	 */
	public void receiveMediaInfo(List<TXDataObject> dataList) {

		if (ListUtils.isEmpty(dataList))
			return;
		this.mediaInfoList = new ArrayList<MediaInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			MediaInfo meidaInfo = new MediaInfo();
			TXDataObject dataObj = dataList.get(i);

			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			meidaInfo.setCategory(ObjectUtils.nullStrToEmpty(map
					.get("category")));
			meidaInfo.setType(ObjectUtils.nullStrToEmpty(map.get("type")));
			meidaInfo.setTitle(ObjectUtils.nullStrToEmpty(map.get("title")));
			meidaInfo.setMediaMsg(ObjectUtils.nullStrToEmpty(map
					.get("mediaMsg")));
			meidaInfo.setFileSize(ObjectUtils
					.nullIntegerToDefault(map.get("fileSize")));
			meidaInfo.setParam(ObjectUtils.nullStrToEmpty(map.get("param")));
			String storeUrl = ObjectUtils.nullStrToEmpty(map.get("storeUrl"));
			meidaInfo.setStoreUrl(storeUrl);
			meidaInfo.setModified(true);
			// 缺少pictureInfo.setPicId(StringUtils.nullStrToEmpty(map.get("id")));
			meidaInfo.setActiveUrl(ObjectUtils.nullStrToEmpty(map
					.get("activeUrl")));
			meidaInfo.setName(storeUrl.substring(storeUrl.lastIndexOf("/") + 1,
					storeUrl.length()));
			meidaInfo.setBuildDate(ObjectUtils.nullStrToEmpty(map
					.get("buildDate")));
			meidaInfo.setModifyDate(ObjectUtils.nullStrToEmpty(map
					.get("modifieDate")));
			mediaInfoList.add(meidaInfo);
		}
	}

	// 获取个人配置信息
	public ServiceRequestParam packGetAPPConfigInfoParam(String userId,
			String mobileModels, String versionType) {
		// 创建参数
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("userId", userId);
		attributes.put("mobileModels", mobileModels);
		attributes.put("versionType", versionType);

		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				attributes, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_VERSION_GETAPPCONFIGINFO, null, datas);
		return param;
	}

	//
	// 获取个人信息数据返回
	public void receiveGetAPPConfigInfo(List<TXDataObject> dataList) {

		if (ListUtils.isEmpty(dataList))
			return;
		this.versionInfoList = new ArrayList<VersionInfo>();
		for (TXDataObject dataObj : dataList) {
			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			if (StringUtils.isEquals(dataObj.getObjectId(), "APPNEWVERSION")) {

				for (int i = 0; i < dataList.size(); i++) {
					VersionInfo versionInfo = new VersionInfo();

					// 保存成功后 返回资讯id
					versionInfo.setVersionNo(ObjectUtils.nullStrToEmpty(map
							.get("versionNo")));
					versionInfo.setDownloadUrl(ObjectUtils.nullStrToEmpty(map
							.get("downloadUrl")));
					versionInfo.setVerSize(ObjectUtils.nullIntegerToDefault(map
							.get("fileSize")));
					versionInfo.setVerionInfo(ObjectUtils.nullStrToEmpty(map
							.get("versionInfo")));
					// 版本创建 2014-06-09 17:31：00
					versionInfo.setUpdateTime((ObjectUtils
							.nullDateToDefault(map.get("updateTime"))));
					versionInfo.setName(ObjectUtils.nullStrToEmpty(map
							.get("name")));
					versionInfo.setMobileModels(ObjectUtils.nullStrToEmpty(map
							.get("mobileModels")));
					versionInfo.setVersionNo(ObjectUtils.nullStrToEmpty(map
							.get("versionType")));
					versionInfoList.add(versionInfo);
				}
			}

		}

	}


	/**
	 * 获取最新版本信息
	 * @param mobileModels
	 *            手机型号 0：android 1:iphone
	 * @param versionType
	 *            版本类型 0：捷生活 1：捷生活（商户端） 2：捷生活（物业端）
	 * @return
	 */
	// 已修改
	public ServiceRequestParam queryVersion(String mobileModels,
			String versionType) {
		// 创建参数----------------
		Map<String, Object> map = new HashMap<String, Object>();
		// MobleModelsEnum
		map.put("mobileModels", mobileModels);
		// VersionTypeEnum
		map.put("versionType", versionType);
		// 创建业务对象
		TXDataObject dataObj = JSCouldProtocol.dataObjectPack("objID", null,
				map, null);
		// 加入业务对象列表
		List<TXDataObject> datas = new ArrayList<TXDataObject>();
		datas.add(dataObj);

		// 获取请求对象----------------
		ServiceRequestParam param = JSCouldProtocol.dataRequestParamPack(
				ServiceID.JSCSP_VERSION_GETNEWVERSION, null, datas);
		return param;
	}

	/**
	 * 获取最新版本信息返回
	 * 
	 * @param map
	 */
	public void receiveNewVersion(List<TXDataObject> dataList) {

		this.versionInfoList = new ArrayList<VersionInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			VersionInfo versionInfo = new VersionInfo();
			TXDataObject dataObj = dataList.get(i);

			Map<String, Object> map = (Map<String, Object>) dataObj
					.getAttributes();
			// 保存成功后 返回资讯id
			versionInfo.setVersionNo(ObjectUtils.nullStrToEmpty(map
					.get("versionNo")));
			versionInfo.setDownloadUrl(ObjectUtils.nullStrToEmpty(map
					.get("downloadUrl")));
			versionInfo.setVerSize(ObjectUtils.nullIntegerToDefault(map
					.get("fileSize")));
			versionInfo.setVerionInfo(ObjectUtils.nullStrToEmpty(map
					.get("verionInfo")));
			// 版本创建 2014-06-09 17:31：00
			versionInfo.setUpdateTime((ObjectUtils.nullDateToDefault(map
					.get("updateTime"))));
			versionInfo.setName(ObjectUtils.nullStrToEmpty(map.get("name")));
			versionInfo.setMobileModels(ObjectUtils.nullStrToEmpty(map
					.get("mobileModels")));
			versionInfo.setVersionType(ObjectUtils.nullStrToEmpty(map
					.get("versionType")));
			versionInfoList.add(versionInfo);
		}
	}

	private String unknownError = "未知错误";
	private String lackParam = "缺少参数";

	public String getErrorMsg(String commandId, int retCode) {

		switch (commandId) {
		case ServiceID.JSCSP_USER_RESETUSERPASS:
			return checkUserResetPwdRetCode(retCode);
		case ServiceID.JSCSP_SYS_GETVERIFYCODE:
			return checkReceiveVertifyCodeRetCode(retCode);
		case ServiceID.JSCSP_INFO_QUERYMEDIA:
			return checkQryMediaCode(retCode);
		case ServiceID.JSCSP_SYS_LOGIN:
			return checkUserLoginCode(retCode);
		case ServiceID.JSCSP_USER_SETUSERINFO:
			return checkSetUserInfoRetCode(retCode);
		case ServiceID.JSCSP_USER_GETUSERINFO:
			return checkGetUserInfoRetCode(retCode);
		case ServiceID.JSCSP_USER_UPDATEUSERPASS:
			return checkModifyUserPwdRetCode(retCode);
		case ServiceID.JSCSP_ORDER_SALEPLAN:
			return checkDiscountPlanCode(retCode);
		case ServiceID.JSCSP_INFO_QUERYPARK:
			return checkQueryParkCode(retCode);
		case ServiceID.JSCSP_ORDER_EXECUTE_SALE:
			return checkSaleCode(retCode);
		case ServiceID.JSCSP_ORDER_QUERYSALEDETAILINFO:
			return checkSaleDetailCode(retCode);
		case ServiceID.JSCSP_ORDER_QUERYPRIVILEGETOTALINFO:
			return checkPrivilegeTotalCode(retCode);
		case ServiceID.JSCSP_VERSION_GETNEWVERSION:
			return checkGetNewVersionRetCode(retCode);
		case ServiceID.JSCSP_REQSERVICE_ADDREQSERVICE:
			return checkAddReqServiceRetCode(retCode);
		case ServiceID.JSCSP_FEEDBACK_ADDCONTENT:
			return checkAddFeedbackContentRetCode(retCode);
		}
		return "";
	}

	public String checkAddFeedbackContentRetCode(int retCode) {

		switch (retCode) {
		case 2001:
			return "用户不存在";

		case 1001:
			L.d(getClass(), lackParam + "===================");
			return "缺少参数，反馈失败";

		default:

			return unknownError;
		}
	}
	private String checkAddReqServiceRetCode(int retCode) {

		switch (retCode) {
		case 2042:
			return "小区已删除";
		case 2001:
			return "用户不存在";
		case 2043:
			return "你没有开通该小区的服务";
		case 1001:
			return "缺少参数，添加失败";
		default:
			return unknownError;
		}
	}

	public String checkGetNewVersionRetCode(int retCode) {

		switch (retCode) {
		case 1001:
			L.d(getClass(), lackParam + "===================");
			return "缺少参数，查询失败";

		default:

			return unknownError;
		}
	}

	private String checkPrivilegeTotalCode(int retCode) {
		switch (retCode) {
		case 1001:

			return "缺少参数，设置失败";
		default:

			return unknownError;
		}
	}

	private String checkSaleDetailCode(int retCode) {
		switch (retCode) {
		case 1001:

			return "缺少参数，设置失败";
		default:

			return unknownError;
		}
	}

	private String checkSaleCode(int retCode) {
		switch (retCode) {
		case 1001:

			return "缺少参数，设置失败";
		default:

			return unknownError;
		}
	}

	private String checkQueryParkCode(int retCode) {
		switch (retCode) {
		case 1001:

			return "缺少参数，设置失败";
		default:

			return unknownError;
		}
	}

	private String checkDiscountPlanCode(int retCode) {
		switch (retCode) {
		case 1001:

			return "缺少参数，设置失败";
		default:

			return unknownError;
		}
	}

	public String checkModifyUserPwdRetCode(int retCode) {

		switch (retCode) {
		case 1001:
			return "缺少参数，修改失败";

		case 2001:

			return "用户不存在";
		case 2005:

			return "旧密码有误，请重新输入";
		case 2241:
			// openfire用户不存在
			return "用户不存在";
		case 2242:
			// 获取openfire加密密钥失败
			return "修改失败";
		default:

			return unknownError;

		}

	}

	public String checkGetUserInfoRetCode(int retCode) {

		switch (retCode) {
		case 1001:
			return "缺少参数，查询失败";

		case 2004:

			return "个人信息为空";
		default:

			return unknownError;

		}

	}

	public String checkSetUserInfoRetCode(int retCode) {

		switch (retCode) {
		case 1001:

			return "缺少参数，设置失败";
		default:

			return unknownError;
		}

	}

	private String checkUserLoginCode(int retCode) {
		switch (retCode) {
		case 2001:
			return "用户不存在";

		case 2002:

			return "用户名或密码不正确";
		case 2246:

			return "登录密码输入错误超过5次，账户已被锁定，请在三分钟后再尝试登录";
		case 2247:

			return "账户已被锁定，请在三分钟后再尝试登录";
		case 1001:
			return "缺少参数，登录失败";
		default:

			return unknownError;
		}
	}

	public String checkQryMediaCode(int retCode) {

		switch (retCode) {

		case 1001:
			L.d(getClass(), lackParam + "===================");
			return "缺少参数，查询失败";

		default:

			return unknownError;
		}
	}

	public String checkUserResetPwdRetCode(int retCode) {

		switch (retCode) {
		case 1001:
			return "缺少参数，重置失败";

		case 2018:

			return "手机验证码已过期，请重新获取";
		case 2019:

			return "验证码不正确";
		case 2240:

			return "该用户不存在或用户没有绑定手机";

		case 2241:
			// openfire用户不存在
			return "用户不存在";
		case 2242:
			// 获取openfire加密密钥失败
			return "修改失败";

		default:

			return unknownError;

		}

	}
	public String checkReceiveVertifyCodeRetCode(int retCode) {

		switch (retCode) {
		case 1001:
			return "缺少参数，获取验证码失败";

		case 2006:

			return "获取验证码失败";
		case 2240:
			//
			return "该用户不存在或用户没有绑定手机";
		case 2238:

			return "不是有效的手机号码";
		default:

			return unknownError;

		}

	}


}
