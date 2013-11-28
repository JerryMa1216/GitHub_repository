package com.greenisland.taxi.gateway.gps;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.bstek.dorado.core.Configure;
import com.greenisland.taxi.common.constant.GPSCommand;
import com.greenisland.taxi.common.utils.TCPUtils;

/**
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-10-22上午1:32:36
 */
@Component("tcpClient")
public class TCPClient extends Thread implements InitializingBean {
	@Resource
	private SyncClient client;
	@Resource
	private SyncResponse synResponse;
	private Socket socket = null;
	public boolean isRunning = false;
	private static OutputStream out = null;
	private static InputStream in = null;
	private String resultValue;
	private String username;
	private String password;

	protected static Log log = LogFactory.getLog(TCPClient.class);

	/**
	 * 初始化客户端socket连接
	 */
	public void initServer() {
		try {
			log.info("Client start ...");
			isRunning = true;
			String host = Configure.getString("host");
			int port = Integer.parseInt(Configure.getString("port"));
			username = Configure.getString("username");
			password = Configure.getString("password");
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			log.error("Create tcp client connect fail, " + e.getMessage());
			return;
		}
	}

	public void sendMessage(String datas) {
		try {
			if (datas != null) {
				out.write(datas.getBytes("GBK"));
			}
		} catch (Exception e) {
			log.error("Send datas fail, " + e.getMessage());
			return;
		}
	}

	@Override
	public void run() {
		super.run();
		isRunning = !isServerClose(socket);
		int rLen = 0;
		byte[] data = new byte[100 * 1024];
		while (isRunning) {
			try {
				rLen = in.read(data);
				if (rLen > 0) {
					resultValue = new String(data, 0, rLen, "GBK");
					System.out.println(resultValue);
					String msg1 = resultValue.substring(2);
					String msg2 = msg1.substring(0, msg1.indexOf(">"));
					// 消息id
					String msgId = msg2.substring(0, 4);
					if (msgId
							.equals(Integer.toString(GPSCommand.GPS_TAXI_RESP))) {
						System.out.println("=====================");
						System.out.println("司机抢达响应结果为： " + resultValue);
						System.out.println("=====================");
						synResponse.handlerResponse(resultValue);
					} else if (msgId.equals(Integer
							.toString(GPSCommand.GPS_HEARTBEAT))) {
						log.info("心跳包链路响应：" + resultValue);
					} else {
						synchronized (client) {
							client.setResult(resultValue);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("socket exception.");
				return;
			}
		}
		// 断开连接
		while (isRunning) {
			try {
				log.error("connection is disconnected!");
				log.info("reconnection");
				initServer();
				sendMessage(TCPUtils.getLoginMsg(username, password));
				isRunning = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断socket是否断开
	 * 
	 * @param socket
	 * @return true:断开 false：未断开
	 */
	public Boolean isServerClose(Socket socket) {
		try {
			socket.sendUrgentData(0);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
			return false;
		} catch (Exception se) {
			return true;
		}
	}

	public void afterPropertiesSet() throws Exception {
		this.initServer();
		this.start();
		sendMessage(TCPUtils.getLoginMsg(username, password));
		String returnData = client.getResult();
		log.info("登陆成功,返回信息：[" + returnData + "]");
		sendMessage("<<0003,8961977906,297e6c174299bb0a014299bdbc350003-2,1,余杭区临平街道思惠家园西大街府前路西北,浦东新区祖冲之路2305,120.280539,30.415682,Wed Nov 27 13:26:58 CST 2013,6,eric,13916498516,null>>");
		String r = client.getResult();
		System.out.println("打车请求响应结果： "+r);
	}

}
