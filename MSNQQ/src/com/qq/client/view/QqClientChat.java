package com.qq.client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.qq.client.MsnClient;
import com.qq.model.MessageSerialization;
import com.sun.awt.*;

public class QqClientChat extends JFrame implements KeyListener{
	
	private String userIpV4;
	private String MyPort;
	private String YouPort;
	private String userName;
	//北部布局
	private JPanel jpLong;
	
	//中部布局
	private static JTextArea jta;
	private JScrollPane jsp;
	
	//南部布局
	private JTextArea jtf;
	private JScrollPane jtp;
	private MessageSerialization msn;
	
	
	public QqClientChat(String userName ,String userIpV4,String MyPort,String YouPort){
		
		this.userName = userName;
		this.userIpV4 = userIpV4;
		this.MyPort = MyPort;
		this.YouPort = YouPort;
		
		
		this.setLayout(null);
		//北部布局
		jpLong = new JPanel();
		jpLong.setBackground(Color.cyan);
		jpLong.setOpaque(false);
		this.add(jpLong);
		
		//中部布局
		jta = new JTextArea();
		jta.setForeground(Color.white);
		jta.setFont(new Font("楷体_GB2312", 4, 14));
		jta.setOpaque(false);
		jta.setLineWrap(true);
		jsp = new JScrollPane(jta);
		jsp.setBounds(55, 86, 235, 185);
		jsp.setBorder(BorderFactory.createLineBorder(Color.black));
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		this.add(jsp);
		
		//南部布局
		jtf = new JTextArea();
		jtf.addKeyListener(this);
		jtf.setForeground(Color.green);
		jtf.setFont(new Font("楷体_GB2312", 4, 14));
		jtf.setBorder(null);
		jtf.setOpaque(false);
		jtf.setLineWrap(true);
		jtp = new JScrollPane(jtf);
		jtp.setBounds(57, 292, 235, 65);
		jtp.setOpaque(false);
		jtp.setBorder(BorderFactory.createLineBorder(Color.black));
		jtp.getViewport().setOpaque(false);
		this.add(jtp);
		
		
		//主面板控制
		
		this.setVisible(true);
		this.setSize(350, 400);
		this.setLocation(400, 200);
		this.setResizable(false);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("欢迎来到三流网聊天 ^.^小美女给命名");
		
		//无背景色设置
		JPanel imagePanel;
		ImageIcon background;
		background = new ImageIcon("imgs//chatbg1.png");//背景图片
		JLabel label = new JLabel(background);//把背景图片显示在一个标签里面
		//把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0,0,background.getIconWidth(),background.getIconHeight());
		//把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel = (JPanel)this.getContentPane();
		imagePanel.setOpaque(false);
		//把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		this.setSize(background.getIconWidth(),background.getIconHeight());
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getModifiers() == InputEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER){
			
			msn = new MessageSerialization();
			msn.setSendType("单聊");
			msn.setUserName(userName);
			msn.setIpv4(userIpV4);
			msn.setMyPort(MyPort);
			msn.setYoPort(YouPort);
			msn.setUserSendMessage(new StringBuffer(msn.getUserName()+jtf.getText()));
			
			//页面信息，写到client
			jta.append(msn.toMyyString()+"\r\n");
			jtf.setText("");
			//执行发送
			MsnClient.sendMessage(msn);
			
			//让光标自动下滚
			jta.setCaretPosition(jta.getText().length());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void setMessToTextArea(MessageSerialization msn){
		jta.append(msn.toMyyString()+"\r\n");
		
		//让光标自动下滚
		jta.setCaretPosition(jta.getText().length());
	}
	
	
}
