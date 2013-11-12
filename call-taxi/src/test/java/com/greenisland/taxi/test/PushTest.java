package com.greenisland.taxi.test;

import java.util.Arrays;

import org.junit.Test;

import com.greenisland.taxi.push.DefaultPushClient;
import com.greenisland.taxi.push.auth.PushCredentials;
import com.greenisland.taxi.push.model.DeviceType;
import com.greenisland.taxi.push.model.MessageType;
import com.greenisland.taxi.push.model.PushType;
import com.greenisland.taxi.push.request.PushMessageRequest;
import com.greenisland.taxi.push.response.PushResponse;

public class PushTest {
	private final static String API_KEY = "zwWjcE7gU2byuN9qs5ulb7pD";
	private final static String SECRIT_KEY = "2rirQnt5zdQFbFKGgiFI7KURROlSBFsF";

//	@Test
//	public void pushNotify() {
//		DefaultPushClient client = new DefaultPushClient(new PushCredentials(API_KEY, SECRIT_KEY));
//		PushMessageRequest request = new PushMessageRequest();
//		request.setMessageType(MessageType.notify);
//		// request.setMessageType(MessageType.message);
//		/*
//		 * String msg = String .format(
//		 * "{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}"
//		 * , "aaaa", "Hello!");
//		 */
//		// String msg =
//		// String.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}",
//		// "aaaa",
//		// "aaaaa");
//		request.setMessages("{\"aps\":{\"alert\":\"Eric\",\"sound\":\"\",\"badge\":\"\"}}");
//		// request.setMessages(msg);
//		request.setPushType(PushType.all_user);
//		request.setDeviceTypes(Arrays.asList(DeviceType.iso));
//		request.setDeployStatus(Long.valueOf(1));
//		request.setMessageKeys("msgkeys");
//		PushResponse<Integer> response = client.pushMessage(request);
//		System.out.println(response);
//	}

	@Test
	public void pushMessageAndroid() {
		DefaultPushClient client = new DefaultPushClient(new PushCredentials(API_KEY, SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		// request.setMessageType(MessageType.message);
		// String msg = "hello 二货!";
		request.setMessageType(MessageType.notify);
		String msg = String
				.format("{'title':'hello','description':'hello world','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'taxiPlateNumber':'浙A123','driverName':'顾师傅','driverNumber':'123123123'}}",
						"aaaa", "masui");
		request.setMessages(msg);
		request.setPushType(PushType.all_user);
		request.setMessageKeys("aaa");
		PushResponse<Integer> response = client.pushMessage(request);
		System.out.println(response);
	}

}
