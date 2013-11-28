package com.greenisland.taxi.test;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.greenisland.taxi.push.DefaultPushClient;
import com.greenisland.taxi.push.auth.PushCredentials;
import com.greenisland.taxi.push.model.DeviceType;
import com.greenisland.taxi.push.model.MessageType;
import com.greenisland.taxi.push.model.PushType;
import com.greenisland.taxi.push.request.PushMessageRequest;
import com.greenisland.taxi.push.response.PushResponse;

public class PushTest {
	private final static String API_KEY = "Hh3TibjsDoMZWi2NxXGRVGF6";
	private final static String SECRIT_KEY = "moeDFfZMpbSNoRaH1hDpNfzSLHWf1BM9";

	/**
	 * 
	 */
	@Test
	public void pushNotify() {
		DefaultPushClient client = new DefaultPushClient(new PushCredentials(API_KEY, SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		request.setMessageType(MessageType.notify);
		// request.setMessageType(MessageType.message);
		/*
		 * String msg = String .format(
		 * "{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}"
		 * , "aaaa", "Hello!");
		 */
		// String msg =
		// String.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}",
		// "aaaa",
		// "aaaaa");
		request.setMessages("{\"aps\":{\"alert\":\"小月3+'"+new Date()+"'\",\"sound\":\"\",\"badge\":\"\"}}");
		// request.setMessages(msg);
		request.setChannelId("4673967466860942996");
		request.setUserId("1148595117558060919");
//		request.setChannelId("5451283487280084826");
//		request.setUserId("793063601730133299");
//		request.setTag("15000191869");
		request.setPushType(PushType.single_user);
//		request.setPushType(PushType.tag_user);
		request.setDeviceTypes(Arrays.asList(DeviceType.iso));
		request.setDeployStatus(Long.valueOf(1));
		request.setMessageKeys("msgkeys");
		PushResponse<Integer> response = client.pushMessage(request);
		System.out.println(response);
	}

	@Test
	public void pushMessageAndroid() {
		DefaultPushClient client = new DefaultPushClient(new PushCredentials(API_KEY, SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		 request.setMessageType(MessageType.message);
		 String msg = "hello 二货!"+new Date();
//		request.setMessageType(MessageType.notify);
//		String msg = String
//				.format("{'title':'hello','description':'hello world','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'taxiPlateNumber':'浙A123','driverName':'顾师傅','driverNumber':'123123123'}}",
//						"aaaa", "masui"+new Date());
//		request.setChannelId("4478218280303304392");
//		request.setUserId("745409507009129179");
		request.setTag("15000191869");
		request.setMessages(msg);
		request.setPushType(PushType.tag_user);
		request.setDeployStatus(Long.valueOf(1));
		request.setMessageKeys("aaa");
		PushResponse<Integer> response = client.pushMessage(request);
		System.out.println(response);
	}

}
