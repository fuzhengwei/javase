package com.qq.model;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class User implements Serializable{
	
	private String userName;
	private String userIP;
	private String userPort;
	private Socket st;
	
	private ArrayList<User> alUser;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public String getUserPort() {
		return userPort;
	}
	public void setUserPort(String userPort) {
		this.userPort = userPort;
	}
	public Socket getSt() {
		return st;
	}
	public void setSt(Socket st) {
		this.st = st;
	}
	public ArrayList<User> getAlUser() {
		return alUser;
	}
	public void setAlUser(ArrayList<User> alUser) {
		this.alUser = alUser;
	}
	
	public String toMyString(){
		return getUserName()+"\r\n"+getUserIP()+":"+getUserPort()+"\r\n";
	}
	
}
