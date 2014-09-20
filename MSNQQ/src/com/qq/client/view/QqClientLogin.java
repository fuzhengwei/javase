package com.qq.client.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.qq.client.MsnClient;


public class QqClientLogin extends JFrame implements ActionListener{
	
	
	//北部布局
	JLabel jl1;

	//中部布局
	JTabbedPane jtp1;
	JPanel jp2,jp3,jp4;
	JLabel jp2_jl1,jp2_jl2,jp2_jl3,jp2_jl4,jp2_jl5;
	JButton jp2_jb1;
	JTextField jp2_jf1,jp2_jf2,jp2_jpf1;
	JCheckBox jp2_jcb1,jp2_jcb2;
	
	//南部布局
	JPanel jp1;
	JButton jp1_jb1,jp1_jb2,jp1_jb3;
	
	
	public QqClientLogin(){
		
		//SwingResourceManager.getImage("imagefilePath")
		//this.class.getResource("imagefilePath")
		//处理北部
		jl1 = new JLabel(new ImageIcon("img\\bgHead1.jpg"));
		this.add(jl1,"North");
		
		//处理中部
		jtp1 = new JTabbedPane();
		
		jp2 = new JPanel(new GridLayout(3,3));
		jp3 = new JPanel();
		jp4 = new JPanel();
		
		jp2.setName("普通用户登录");
		jp3.setName("会员用户登录");
		jp4.setName("vip用户登录");
		
		//控件
		jp2_jl1 = new JLabel("IPv4：",JLabel.CENTER);
		jp2_jl5 = new JLabel("端口：",JLabel.CENTER);
		jp2_jl2 = new JLabel("昵称：",JLabel.CENTER);
		
		jp2_jf2 = new JTextField();
		jp2_jf1 = new JTextField();//ip
		jp2_jpf1 = new JTextField();//port
		jp2_jcb1 = new JCheckBox("隐身登陆");
		jp2_jcb2 = new JCheckBox("记住信息");
		//把控件按照顺序添加到面板
		jp2.add(jp2_jl1);
		jp2.add(jp2_jf1);
		jp2.add(jp2_jcb1);
		
		jp2.add(jp2_jl5);
		jp2.add(jp2_jpf1);
		jp2.add(jp2_jcb2);
		
		jp2.add(jp2_jl2);
		jp2.add(jp2_jf2);
//		jp2.add(jp2_jl4);
		//把面板添加到选项卡
		jtp1.add(jp2);
		jtp1.add(jp3);
		jtp1.add(jp4);
		
		this.add(jtp1,"Center");
		
		//处理南部
		jp1_jb1 = new JButton(new ImageIcon("img\\denglu.jpg"));
		jp1_jb2 = new JButton(new ImageIcon("img\\quxiao.jpg"));
		jp1_jb3 = new JButton(new ImageIcon("img\\zhuce.jpg"));
		//添加事件
		jp1_jb1.addActionListener(this);
		jp1_jb2.addActionListener(this);
		jp1_jb3.addActionListener(this);
		
		//设置button大小
		jp1_jb1.setPreferredSize(new Dimension(100,30));
		jp1_jb2.setPreferredSize(new Dimension(100,30));
		jp1_jb3.setPreferredSize(new Dimension(100,30));
		
		
		jp1 = new JPanel();
		
		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
		jp1.add(jp1_jb3);
		this.add(jp1,"South");
		
		
		this.setLocation(300, 200);
		this.setSize(350, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("登录界面：全新版--序列化串行接口");
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jp1_jb1){
			//登录
			//如果ip正确，登录
			String ipv4 = jp2_jf1.getText();
			int userPort = Integer.parseInt(jp2_jpf1.getText());
			String userName = jp2_jf2.getText();
			
			//将来会判断用户名 ip 端口是否合法
			if(true){
				
				//打开客户端（非界面）
				new MsnClient(userName,ipv4,userPort);
				//打开新窗口，关闭本窗口
				dispose();
				
			}
			
		}else if(e.getSource() == jp1_jb2){
			//取消
			System.out.println("取消");
		}else if(e.getSource() == jp1_jb3){
			//注册
			
		}
	}

}