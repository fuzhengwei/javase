package com.qq.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.qq.client.view.QqClientLogin;
import com.qq.model.MessageSerialization;
import com.qq.model.User;
import com.qq.server.view.QqServerView;

public class MsnServer implements Runnable{
	
	private MessageSerialization msn;
	private User user;
	
	private HashMap<Integer, User> hm;
	private ArrayList<User> alUser;
	private ServerSocket sst;
	private Socket st;
	private ObjectOutputStream oos;
	
	
	public MsnServer(){
		
		//实例化集合
		hm = new HashMap<Integer, User>();
		alUser = new ArrayList<User>();
		
		try {
			sst = new ServerSocket(6789);
			
			while(true){
				
				st = sst.accept();
				oos = new ObjectOutputStream(st.getOutputStream());
				new Thread(this).start();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try {
			ObjectInputStream ois = new ObjectInputStream(st.getInputStream());
			while((msn = (MessageSerialization)ois.readObject())!=null){
				
				//群发-1
				if("群发".equals(msn.getSendType())){
					sendMessageToAll(alUser,msn);
				}
				//单发-2
				if("单聊".equals(msn.getSendType())){
					sendMessageToOne(hm,msn);
				}
				//登录信息-3
				if("登录信息".equals(msn.getSendType())){
					loadUserMessage(msn,st);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			QqServerView.setLoginMessage("下线人："+st.getPort()+"\r\n");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//单提出流，作为一个方法
	public void getObjectOutPutStream(User user,MessageSerialization msn) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(user.getSt().getOutputStream());
		if(user.getUserPort()!=msn.getLongPort()){
			oos.writeObject(msn);
			oos.flush();
		}
	}
	
	
	//群发
	public void sendMessageToAll(ArrayList<User> alUser,MessageSerialization msn) throws IOException{
		for(User user : alUser){
			getObjectOutPutStream(user,msn);
		}
	}
	//单发
	public void sendMessageToOne(HashMap<Integer, User> hm,MessageSerialization msn) throws IOException{
		getObjectOutPutStream(hm.get(new Integer(msn.getYoPort())),msn);
	}
	//登录信息函数
	public void loadUserMessage(MessageSerialization msn,Socket st) throws IOException{
		String userName = msn.getUserName();
		user = new User();
		user.setSt(st);
		user.setUserIP(st.getInetAddress().getHostAddress());
		user.setUserPort(String.valueOf(st.getPort()));
		user.setUserName(userName);
		//根据port迅速找到当前要发送信息的人
		hm.put(st.getPort(), user);
		//把信息装到ArrayList集合，用户群发
		alUser.add(user);
		//登录信息，写到服务器界面
		QqServerView.setLoginMessage("上线人："+user.toMyString());
		
		try {
			msn = new MessageSerialization();
			msn.setSendType("loginMessage");
			msn.setUserName(userName);
			msn.setIpv4(st.getInetAddress().getHostAddress());
			msn.setLongPort(String.valueOf(st.getPort()));
			msn.setUserSendMessage(new StringBuffer("宝贝欢迎你加入聊天室"));
			oos.writeObject(msn);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//有新人登录，发送新人信息，到其他用户，发送全部信息，到新人
		StringBuffer sbAlUser = new StringBuffer();
		for(User user : alUser){
			sbAlUser.append("<"+user.getUserName()+":"+user.getUserIP()+":"+user.getUserPort()+">");
		}
		msn = new MessageSerialization();
		msn.setSendType("好友列表");
		msn.setUserSendMessage(sbAlUser);
		sendUserMessage(msn);
	}
	
	//好友下线通知
	
	
	//好友列表函数--发送好友到各个用户
	public void sendUserMessage(MessageSerialization msn) throws IOException{
		for(User user : alUser){
			getObjectOutPutStream(user,msn);
		}
	}
	
	
	
}

