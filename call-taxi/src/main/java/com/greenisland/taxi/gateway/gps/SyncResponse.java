package com.greenisland.taxi.gateway.gps;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.greenisland.taxi.common.constant.GPSCommand;
import com.greenisland.taxi.common.constant.MechineType;
import com.greenisland.taxi.common.constant.ResponseState;
import com.greenisland.taxi.domain.CallApplyInfo;
import com.greenisland.taxi.domain.CompanyInfo;
import com.greenisland.taxi.domain.TaxiInfo;
import com.greenisland.taxi.gateway.gps.resolver.MessageHandler;
import com.greenisland.taxi.gateway.push.PushClient;
import com.greenisland.taxi.manager.CallApplyInfoService;
import com.greenisland.taxi.manager.CompanyInfoService;
import com.greenisland.taxi.manager.TaxiInfoService;

@Component
public class SyncResponse {
	@Resource
	private MessageHandler messageHandler;
	@Resource
	private CallApplyInfoService callApplyInfoService;
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private TaxiInfoService taxiInfoService;
	@Resource
	private PushClient pushClient;

	public synchronized void handlerResponse(String responseData) {
		Map<String, Object> mapTaxi = null;// 调用接口返回值
		mapTaxi = messageHandler.handler(responseData);
		String applyId = (String) mapTaxi.get("applyId");
		String mechineType = applyId.split(",")[1];
		TaxiInfo respTaxi = (TaxiInfo) mapTaxi.get(GPSCommand.GPS_TAXI_RESP);
		CallApplyInfo applyInfo = callApplyInfoService.getApplyInfoValidated(applyId.split(",")[0]);
		CompanyInfo respCompany = respTaxi.getCompanyInfo();
		if (applyInfo != null) {
			CompanyInfo company = companyInfoService.getCompanyByName(respCompany != null ? respCompany.getName() : null);
			String taxiId = null;
			String companyId = null;
			TaxiInfo taxi = new TaxiInfo();
			// 判断公司是否存在
			if (company != null) {
				taxi = taxiInfoService.getTaxiByPlateNumber(respTaxi.getTaxiPlateNumber());
				if (taxi == null) {
					respTaxi.setCompanyId(company.getId());
					respTaxi.setBreakPromiseCount(0);
					respTaxi.setCreateDate(new Date());
					taxiId = taxiInfoService.saveTaxiInfo(respTaxi);
				}
			} else {
				respCompany.setId(UUID.randomUUID().toString());
				respCompany.setCreateDate(new Date());
				companyId = companyInfoService.saveCompany(respCompany);
				taxi = taxiInfoService.getTaxiByPlateNumber(respTaxi.getTaxiPlateNumber());
				if (taxi == null) {
					respTaxi.setCompanyId(companyId);
					respTaxi.setBreakPromiseCount(0);
					respTaxi.setCreateDate(new Date());
					taxiId = taxiInfoService.saveTaxiInfo(respTaxi);
				}
			}
			// 更新订单信息
			applyInfo.setTaxiId(taxiId);
			applyInfo.setResponseState(ResponseState.RESPONSED);
			applyInfo.setUpdateDate(new Date());
			callApplyInfoService.updateApplyInfo(applyInfo);
			// 调用推送
			if(mechineType.equals(MechineType.ANDROID)){
				pushClient.pushAndroidMessage();
			}else{
				pushClient.pushIOSNotify();
			}
			
		}
	}
}
