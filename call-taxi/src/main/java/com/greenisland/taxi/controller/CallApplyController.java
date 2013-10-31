package com.greenisland.taxi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenisland.taxi.domain.LocationInfo;
import com.greenisland.taxi.domain.UserInfo;
import com.greenisland.taxi.gateway.gps.SyncClient;
import com.greenisland.taxi.manager.LocationInfoService;
import com.greenisland.taxi.manager.UserInfoService;

/**
 * 叫车操作
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-10-30下午4:38:58
 */
@Controller
public class CallApplyController {
	private static Log log = LogFactory.getLog(CallApplyController.class);
	@Resource
	private SyncClient syncClient;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private LocationInfoService locationInfoService;

	@RequestMapping(value = "/call_taxi", method = RequestMethod.POST)
	public void callTaxi(@RequestParam String phoneNumber, @RequestParam String callTime, @RequestParam String callType, @RequestParam String callScope, @RequestParam String callDistance,
			@RequestParam String mechineType, @RequestParam String sLoca, @RequestParam String eLoca, @RequestParam String longitude, @RequestParam String latitude) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd"));
		// 读到用户信息
		UserInfo userInfo = userInfoService.getUserInfoByPhoneNumber(phoneNumber);
		// 保存用户叫车位置
		LocationInfo location = new LocationInfo();
		location.setCreateDate(new Date());
		location.setGpsLatitude(latitude);
		location.setGpsLongitude(longitude);
		location.setGpsTime(new Date());
		location.setUserId(userInfo.getId());
		locationInfoService.saveLocationInfo(location);
		String test = "";
	}
}
