package com.greenisland.taxi.gateway.push;

import java.io.InputStream;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bstek.dorado.core.Configure;
import com.greenisland.taxi.push.DefaultPushClient;
import com.greenisland.taxi.push.auth.PushCredentials;
import com.greenisland.taxi.push.model.DeviceType;
import com.greenisland.taxi.push.model.Empty;
import com.greenisland.taxi.push.model.IosCert;
import com.greenisland.taxi.push.model.MessageType;
import com.greenisland.taxi.push.model.PushType;
import com.greenisland.taxi.push.request.IosCertRequest;
import com.greenisland.taxi.push.request.PushMessageRequest;
import com.greenisland.taxi.push.response.PushResponse;

@Component
public class PushClient {
	private final static String API_KEY = Configure.getString("apiKey");
	private final static String SECRIT_KEY = Configure.getString("secretKey");

	public void initIosCert() {
		DefaultPushClient client = new DefaultPushClient(new PushCredentials(API_KEY, SECRIT_KEY));
		InputStream devIn = ClassLoader.getSystemResourceAsStream("159Push.pem");
		// InputStream relIn =
		// ClassLoader.getSystemResourceAsStream("prod.pem");
		IosCert iosCert = new IosCert();
		iosCert.setName("Qingyu-Hu.TaxiCaller");
		iosCert.setDescription("description");
		// iosCert.setReleaseCert(relIn);
		iosCert.setDevCert(devIn);
		IosCertRequest request = new IosCertRequest(iosCert);
		PushResponse<Empty> response = client.initIosCert(request);
		System.out.println(response);
	}

	public void pushIOSNotify() {
		DefaultPushClient client = new DefaultPushClient(new PushCredentials(API_KEY, SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		request.setMessageType(MessageType.notify);
		String msg = String
				.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}",
						"aaaa", "Hello!");
		request.setMessages(msg);
		request.setPushType(PushType.all_user);
		request.setDeviceTypes(Arrays.asList(DeviceType.iso));
		request.setMessageKeys("msgkeys");
		PushResponse<Integer> response = client.pushMessage(request);
		System.out.println(response);
	}
	
	public void pushAndroidMessage() {
		DefaultPushClient client = new DefaultPushClient(new PushCredentials(API_KEY, SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
//		request.setMessageType(MessageType.message);
//		 String msg = "hello 二货!";
		request.setMessageType(MessageType.notify);
		String msg = String
				.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}",
						"aaaa", "masui");
		request.setMessages(msg);
		request.setPushType(PushType.all_user);
		request.setMessageKeys("aaa");
		PushResponse<Integer> response = client.pushMessage(request);
		System.out.println(response);
	}

}
