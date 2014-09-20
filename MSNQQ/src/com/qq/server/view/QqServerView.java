/*
 * 服务器界面
 * */
package com.qq.server.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.qq.server.*;

public class QqServerView extends JFrame implements ActionListener{

	
	//北部组件north
	JMenuBar jmbNorth;
	JMenu jmNorth1,jmNorth2,jmNorth3;
	JMenuItem jmiNorth1,jmiNorth2,jmiNorth3,jmiNorth4,jmiNorth5;
	
	JLabel jlNorth1;
	
	//西部组件west
	static JTextArea jtaWest1;
	JScrollPane jspWest1;
	
	//东部组件east
	JPanel jpEast1;
	JLabel jlEast1;
	JTextField jtfEast1;
	JButton jbEast1,jbEast2;
	
	//事件处理
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbEast1){
			
			//开启服务器
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int port = Integer.parseInt(jtfEast1.getText());
					new MsnServer();
				}
			}).start();
			
			jbEast1.setEnabled(false);
			
		}else if(e.getSource() == jbEast2){
			jbEast1.setEnabled(true);
			jtaWest1.append("关闭服务器\r\n");
			System.exit(0);
		}
	}
	
	//显示登录的信息
	public static void setLoginMessage(String strUserMess){
		jtaWest1.append(strUserMess);
	}
	
	
	public QqServerView(){
		
		//北部处理
		jmbNorth = new JMenuBar();
		jmNorth1 = new JMenu("设置");
		jmiNorth1 = new JMenuItem("内容提取");
		jmiNorth2 = new JMenuItem("文字过滤");
		
		jmNorth1.add(jmiNorth1);
		jmNorth1.add(jmiNorth2);
		jmbNorth.add(jmNorth1);
		
		jmNorth2 = new JMenu("控制");
		jmiNorth3 = new JMenuItem("T除某人");
		jmiNorth4 = new JMenuItem("发送信息控制");
		jmiNorth5 = new JMenuItem("接收信息控制");
		jmNorth2.add(jmiNorth3);
		jmNorth2.add(jmiNorth4);
		jmNorth2.add(jmiNorth5);
		
		jmNorth3 = new JMenu("作者");
		
		jmbNorth.add(jmNorth2);
		jmbNorth.add(jmNorth3);
		
		this.add(jmbNorth,"North");
		
		//西部处理
		jtaWest1 = new JTextArea();
		
		jtaWest1.setEditable(false);
		jtaWest1.setCaretPosition(jtaWest1.getText().length());

		jspWest1 = new JScrollPane(jtaWest1);
		jspWest1.setPreferredSize(new Dimension(200,300));
		
		this.add(jspWest1,"West");
		
		//东部处理
		jpEast1 = new JPanel(new GridLayout(4,1));
		jlEast1 = new JLabel("端口号",JLabel.CENTER);
		jlEast1.setFont(new Font("楷体_GB2312", 4, 30));
		jtfEast1 = new JTextField();
		jtfEast1.setFont(new Font("楷体_GB2312", 4, 48));
		jbEast1 = new JButton("开启服务器");
		jbEast1.addActionListener(this);
		jbEast2 = new JButton("关闭服务器");
		jbEast2.addActionListener(this);
		
		jpEast1.add(jlEast1);
		jpEast1.add(jtfEast1);
		jpEast1.add(jbEast1);
		jpEast1.add(jbEast2);
	
		this.add(jpEast1,"East");
		
		//主窗口设置
		this.setLocation(300, 150);
		this.setSize(305,350);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("忙啥呢--全新改版，序列化，串行接口");
	}

}
