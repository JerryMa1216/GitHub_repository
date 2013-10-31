package com.greenisland.taxi.gateway.gps;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-10-26下午2:09:57
 */
@Component("syncClient")
public class SyncClient {
	@Resource
	private TCPClient tcpClient;
	private String result;

	public synchronized String getResult() {
		while (result == null) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String returnData = result;
		result = null;
		notify();
		System.out.println("获取成功！");
		return returnData;
	}

	public synchronized void setResult(String data) {
		while (result != null) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.result = data;
		notify();
		System.out.println("设置完毕！");
	}

	public synchronized void sendMessage(String message) {
		System.out.println("请求开始时间 ：" + System.currentTimeMillis() / 1000);
		tcpClient.sendMessage(message);
		System.out.println("请求结束时间 ：" + System.currentTimeMillis() / 1000);
	}
}
