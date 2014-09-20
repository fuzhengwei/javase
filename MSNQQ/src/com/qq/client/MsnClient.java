package com.qq.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.qq.client.view.QqClientChat;
import com.qq.client.view.QqClientList;
import com.qq.model.MessageSerialization;

public class MsnClient implements Runnable{

	
	private static Socket st;
	private MessageSerialization msn;
    private static ObjectOutputStream oos;
    private QqClientList qqClientList;
    private static QqClientChat qqChat;
    
    
	public MsnClient(String loginUserName,String ip,int port){
		try {
			st = new Socket(ip,port);
			//发送流
			oos = new ObjectOutputStream(st.getOutputStream());
			//发送用户登录信息给服务端
			msn = new MessageSerialization();
			msn.setSendType("登录信息");
			msn.setUserName(loginUserName);
			
			sendMessage(msn);
			
			//开启客户端界面
			qqClientList = new QqClientList(loginUserName, st.getInetAddress().getAddress().toString());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//开启线程
		new Thread(this).start();
	}

	
	
	
	//发信息
	public static void sendMessage(MessageSerialization msn){
		try {
			if("单聊".equals(msn.getSendType())){
				if(qqChat == null){
					qqChat = new QqClientChat(msn.getUserName(),msn.getIpv4(),msn.getMyPort(),msn.getYoPort());
				}
			
			}
			oos.writeObject(msn);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ObjectInputStream ois;
			while(true){
				ois = new ObjectInputStream(st.getInputStream());
				if((msn = (MessageSerialization)ois.readObject())!=null){
					
					if("单聊".equals(msn.getSendType())){

						if(qqChat==null){
							qqChat = new QqClientChat(msn.getUserName(),msn.getIpv4(),msn.getYoPort(),msn.getMyPort());
							
						}
						qqChat.setMessToTextArea(msn);
					
					}else{
						qqClientList.setMessageTextArea(msn);
					}
					
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
