package com.qq.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class MessageSerialization implements Serializable{

	//发送的类型（群发，单发）
	private String sendType;
	//发送的端口(单发时候，我的端口和你的端口)
	private String myPort;
	private String yoPort;
	private String longPort;
	private String ipv4;
	//发送的内容
	private StringBuffer userSendMessage;
	//发送用户名
	private String userName;
	
	public StringBuffer getUserSendMessage() {
		return userSendMessage;
	}

	public void setUserSendMessage(StringBuffer userSendMessage) {
		this.userSendMessage = userSendMessage;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getMyPort() {
		return myPort;
	}

	public void setMyPort(String myPort) {
		this.myPort = myPort;
	}

	public String getYoPort() {
		return yoPort;
	}

	public void setYoPort(String yoPort) {
		this.yoPort = yoPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLongPort() {
		return longPort;
	}

	public void setLongPort(String longPort) {
		this.longPort = longPort;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	//重写toString
	public String toMyString(){
		return getUserName()+" "+getIpv4()+":"+getLongPort()+" "+getTime()+"\r\n"+getUserSendMessage()+"\r\n";
	}
	
	//
	public String toMyyString(){
		return getIpv4()+":"+getMyPort()+"|"+getYoPort()+" "+getTime()+"\r\n"+getUserSendMessage()+"\r\n";
	}
	
	//时间函数
	public String getTime(){
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("H:m:s");
		return dateFormat.format(date);
	}


}
