package com.greenisland.taxi.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bstek.dorado.core.Configure;
import com.greenisland.taxi.common.constant.GPSCommand;
import com.greenisland.taxi.common.utils.TCPUtils;
import com.greenisland.taxi.domain.CallApplyInfo;
import com.greenisland.taxi.domain.TaxiInfo;
import com.greenisland.taxi.gateway.gps.SyncClient;
import com.greenisland.taxi.gateway.gps.resolver.MessageHandler;
import com.greenisland.taxi.manager.CallApplyInfoService;
import com.greenisland.taxi.manager.TaxiInfoService;

/**
 * 车辆操作
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-10-29下午5:31:21
 */
@Controller
public class TaxiController {
	private static Log log = LogFactory.getLog(TaxiController.class);
	@Resource
	private SyncClient syncClient;
	@Resource
	private MessageHandler messageHandler;
	@Resource
	private TaxiInfoService taxiInfoService;
	@Resource
	private CallApplyInfoService applyInfoService;

	/**
	 * 周边车辆查询
	 * 
	 * @param longitude
	 * @param latitude
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query_taxi", method = RequestMethod.GET)
	public void queryTaxis(@RequestParam String longitude, @RequestParam String latitude, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd"));
		// 默认查询半径
		String defaultRadius = Configure.getString("defaultRadius");
		// 持久化用户叫车位置
		Map<String, Object> mapTaxi = null;// 调用接口返回值
		try {
			String requestParam = TCPUtils.getTaxis(longitude, latitude, defaultRadius);
			syncClient.sendMessage(requestParam);
			String returnData = syncClient.getResult();
			mapTaxi = messageHandler.handler(returnData);
			List<TaxiInfo> taxis = (List<TaxiInfo>) mapTaxi.get(Integer.toString(GPSCommand.GPS_AROUND_TAXIS));
			List<TaxiInfo> reTaxis = new ArrayList<TaxiInfo>();
			TaxiInfo tempTaxi = new TaxiInfo();
			if (taxis != null) {
				for (TaxiInfo taxi : taxis) {
					if (taxi.getIsEmpty().equals("0")) {
						if (taxiInfoService.validateTaxiExist(taxi.getTaxiPlateNumber())) {
							tempTaxi = taxiInfoService.getTaxiByPlateNumber(taxi.getTaxiPlateNumber());
							taxi.setId(tempTaxi.getId());
							taxi.setCallApplyInfos(tempTaxi.getCallApplyInfos());
							taxi.setBreakPromiseCount(tempTaxi.getBreakPromiseCount());
						} else {
							taxi.setCallApplyInfos(null);
							taxi.setBreakPromiseCount(0);
						}
						reTaxis.add(taxi);
					}
				}
				map.put("state", "0");
				map.put("message", "OK");
				map.put("count", reTaxis.size());
				map.put("date", new Date());
				map.put("data", reTaxis);
			} else {
				map.put("state", "0");
				map.put("message", "OK");
				map.put("count", 0);
				map.put("date", new Date());
				map.put("data", null);
			}
		} catch (Exception e) {
			log.error("系统异常>>" + e.getMessage());
			map.put("state", "1");
			map.put("message", "ER");
			map.put("count", 0);
			map.put("date", new Date());
			map.put("data", null);
		}
		try {
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json");
			PrintWriter pw = response.getWriter();
			pw.write(objectMapper.writeValueAsString(map));
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error("系统异常>>" + e.getMessage());
		}
	}

	/**
	 * 监控抢到订单的出租车位置信息
	 * 
	 * @param applyId
	 * @param plateNumber
	 * @param response
	 */
	@RequestMapping(value = "/monitor")
	public void monitorTaxi(@RequestParam String applyId, @RequestParam String plateNumber, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd"));
		CallApplyInfo applyInfo = applyInfoService.getCallApplyInfoById(applyId);
		Map<String, Object> mapReturn = null;
		// 监控次数小于三次，可以继续监控
		if (applyInfo.getMonitorCount() < 3) {
			applyInfo.setMonitorCount(applyInfo.getMonitorCount() + 1);
			syncClient.sendMessage(TCPUtils.getMonitorMessage(applyId, plateNumber));
			String returnData = syncClient.getResult();
			mapReturn = messageHandler.handler(returnData);
			TaxiInfo taxiInfo = (TaxiInfo) mapReturn.get(Integer.toString(GPSCommand.GPS_TAXI_MONITER));
			map.put("state", "0");
			map.put("message", "OK");
			map.put("date", new Date());
			map.put("data", taxiInfo);
		} else {
			map.put("state", "1");
			map.put("message", "ER");
			map.put("date", new Date());
			map.put("data", null);
		}
		try {
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json");
			PrintWriter pw = response.getWriter();
			pw.write(objectMapper.writeValueAsString(map));
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error("系统异常>>" + e.getMessage());
		}
	}
}
