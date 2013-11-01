package com.greenisland.taxi.gateway.gps;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.greenisland.taxi.common.constant.GPSCommand;
import com.greenisland.taxi.domain.CallApplyInfo;
import com.greenisland.taxi.domain.CompanyInfo;
import com.greenisland.taxi.domain.TaxiInfo;
import com.greenisland.taxi.gateway.gps.resolver.MessageHandler;
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

	public synchronized void handlerResponse(String responseData) {
		Map<String, Object> mapTaxi = null;// 调用接口返回值
		mapTaxi = messageHandler.handler(responseData);
		String applyId = (String) mapTaxi.get("applyId");
		TaxiInfo respTaxi = (TaxiInfo) mapTaxi.get(GPSCommand.GPS_TAXI_RESP);
		CallApplyInfo applyInfo = callApplyInfoService.getCallApplyInfoById(applyId);
		if (applyInfo != null) {
			CompanyInfo company = companyInfoService.getCompanyByName(respTaxi.getCompanyInfo() != null ? respTaxi.getCompanyInfo().getName() : null);
			// 判断公司是否存在
			if (company != null) {
				TaxiInfo taxi = taxiInfoService.getTaxiByPlateNumber(respTaxi.getTaxiPlateNumber());
				if (taxi != null) {
					taxi.setUpdateDate(new Date());
					taxiInfoService.updateTaxiInfo(taxi);
				}else{
					respTaxi.setCompanyId(company.getId());
					respTaxi.setBreakPromiseCount(0);
					respTaxi.setCreateDate(new Date());
					String taxiId = taxiInfoService.saveTaxiInfo(respTaxi);
				}
			}
			company.setCreateDate(new Date());
			String companyId = companyInfoService.saveCompany(company);
			respTaxi.setCompanyId(companyId);
			// taxi.set
			// applyInfo.setTaxiId(taxi)
		}
	}
}
