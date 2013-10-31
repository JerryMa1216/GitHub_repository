package com.greenisland.taxi.gateway.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.greenisland.taxi.gateway.gps.SyncClient;

/**
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-10-27上午11:13:35
 */
public class ActivityConnect extends QuartzJobBean {
	private static Log log = LogFactory.getLog(ActivityConnect.class);
	private SyncClient syncClient;

	public void setSyncClient(SyncClient syncClient) {
		this.syncClient = syncClient;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("start connecting detection !");
		try {
			syncClient.sendMessage("<<0099,0000000009,0>>");
			String returnData = syncClient.getResult();
			System.out.println(returnData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
