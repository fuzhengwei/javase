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
	//��������
	private JPanel jpLong;
	
	//�в�����
	private static JTextArea jta;
	private JScrollPane jsp;
	
	//�ϲ�����
	private JTextArea jtf;
	private JScrollPane jtp;
	private MessageSerialization msn;
	
	
	public QqClientChat(String userName ,String userIpV4,String MyPort,String YouPort){
		
		this.userName = userName;
		this.userIpV4 = userIpV4;
		this.MyPort = MyPort;
		this.YouPort = YouPort;
		
		
		this.setLayout(null);
		//��������
		jpLong = new JPanel();
		jpLong.setBackground(Color.cyan);
		jpLong.setOpaque(false);
		this.add(jpLong);
		
		//�в�����
		jta = new JTextArea();
		jta.setForeground(Color.white);
		jta.setFont(new Font("����_GB2312", 4, 14));
		jta.setOpaque(false);
		jta.setLineWrap(true);
		jsp = new JScrollPane(jta);
		jsp.setBounds(55, 86, 235, 185);
		jsp.setBorder(BorderFactory.createLineBorder(Color.black));
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		this.add(jsp);
		
		//�ϲ�����
		jtf = new JTextArea();
		jtf.addKeyListener(this);
		jtf.setForeground(Color.green);
		jtf.setFont(new Font("����_GB2312", 4, 14));
		jtf.setBorder(null);
		jtf.setOpaque(false);
		jtf.setLineWrap(true);
		jtp = new JScrollPane(jtf);
		jtp.setBounds(57, 292, 235, 65);
		jtp.setOpaque(false);
		jtp.setBorder(BorderFactory.createLineBorder(Color.black));
		jtp.getViewport().setOpaque(false);
		this.add(jtp);
		
		
		//��������
		
		this.setVisible(true);
		this.setSize(350, 400);
		this.setLocation(400, 200);
		this.setResizable(false);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("��ӭ�������������� ^.^С��Ů������");
		
		//�ޱ���ɫ����
		JPanel imagePanel;
		ImageIcon background;
		background = new ImageIcon("imgs//chatbg1.png");//����ͼƬ
		JLabel label = new JLabel(background);//�ѱ���ͼƬ��ʾ��һ����ǩ����
		//�ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������
		label.setBounds(0,0,background.getIconWidth(),background.getIconHeight());
		//�����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��
		imagePanel = (JPanel)this.getContentPane();
		imagePanel.setOpaque(false);
		//�ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����
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
			msn.setSendType("����");
			msn.setUserName(userName);
			msn.setIpv4(userIpV4);
			msn.setMyPort(MyPort);
			msn.setYoPort(YouPort);
			msn.setUserSendMessage(new StringBuffer(msn.getUserName()+jtf.getText()));
			
			//ҳ����Ϣ��д��client
			jta.append(msn.toMyyString()+"\r\n");
			jtf.setText("");
			//ִ�з���
			MsnClient.sendMessage(msn);
			
			//�ù���Զ��¹�
			jta.setCaretPosition(jta.getText().length());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void setMessToTextArea(MessageSerialization msn){
		jta.append(msn.toMyyString()+"\r\n");
		
		//�ù���Զ��¹�
		jta.setCaretPosition(jta.getText().length());
	}
	
	
}
