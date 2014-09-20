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
		
		//ʵ��������
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
				
				//Ⱥ��-1
				if("Ⱥ��".equals(msn.getSendType())){
					sendMessageToAll(alUser,msn);
				}
				//����-2
				if("����".equals(msn.getSendType())){
					sendMessageToOne(hm,msn);
				}
				//��¼��Ϣ-3
				if("��¼��Ϣ".equals(msn.getSendType())){
					loadUserMessage(msn,st);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			QqServerView.setLoginMessage("�����ˣ�"+st.getPort()+"\r\n");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�����������Ϊһ������
	public void getObjectOutPutStream(User user,MessageSerialization msn) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(user.getSt().getOutputStream());
		if(user.getUserPort()!=msn.getLongPort()){
			oos.writeObject(msn);
			oos.flush();
		}
	}
	
	
	//Ⱥ��
	public void sendMessageToAll(ArrayList<User> alUser,MessageSerialization msn) throws IOException{
		for(User user : alUser){
			getObjectOutPutStream(user,msn);
		}
	}
	//����
	public void sendMessageToOne(HashMap<Integer, User> hm,MessageSerialization msn) throws IOException{
		getObjectOutPutStream(hm.get(new Integer(msn.getYoPort())),msn);
	}
	//��¼��Ϣ����
	public void loadUserMessage(MessageSerialization msn,Socket st) throws IOException{
		String userName = msn.getUserName();
		user = new User();
		user.setSt(st);
		user.setUserIP(st.getInetAddress().getHostAddress());
		user.setUserPort(String.valueOf(st.getPort()));
		user.setUserName(userName);
		//����portѸ���ҵ���ǰҪ������Ϣ����
		hm.put(st.getPort(), user);
		//����Ϣװ��ArrayList���ϣ��û�Ⱥ��
		alUser.add(user);
		//��¼��Ϣ��д������������
		QqServerView.setLoginMessage("�����ˣ�"+user.toMyString());
		
		try {
			msn = new MessageSerialization();
			msn.setSendType("loginMessage");
			msn.setUserName(userName);
			msn.setIpv4(st.getInetAddress().getHostAddress());
			msn.setLongPort(String.valueOf(st.getPort()));
			msn.setUserSendMessage(new StringBuffer("������ӭ�����������"));
			oos.writeObject(msn);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�����˵�¼������������Ϣ���������û�������ȫ����Ϣ��������
		StringBuffer sbAlUser = new StringBuffer();
		for(User user : alUser){
			sbAlUser.append("<"+user.getUserName()+":"+user.getUserIP()+":"+user.getUserPort()+">");
		}
		msn = new MessageSerialization();
		msn.setSendType("�����б�");
		msn.setUserSendMessage(sbAlUser);
		sendUserMessage(msn);
	}
	
	//��������֪ͨ
	
	
	//�����б���--���ͺ��ѵ������û�
	public void sendUserMessage(MessageSerialization msn) throws IOException{
		for(User user : alUser){
			getObjectOutPutStream(user,msn);
		}
	}
	
	
	
}

