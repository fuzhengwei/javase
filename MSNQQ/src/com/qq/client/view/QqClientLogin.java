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
	
	
	//��������
	JLabel jl1;

	//�в�����
	JTabbedPane jtp1;
	JPanel jp2,jp3,jp4;
	JLabel jp2_jl1,jp2_jl2,jp2_jl3,jp2_jl4,jp2_jl5;
	JButton jp2_jb1;
	JTextField jp2_jf1,jp2_jf2,jp2_jpf1;
	JCheckBox jp2_jcb1,jp2_jcb2;
	
	//�ϲ�����
	JPanel jp1;
	JButton jp1_jb1,jp1_jb2,jp1_jb3;
	
	
	public QqClientLogin(){
		
		//SwingResourceManager.getImage("imagefilePath")
		//this.class.getResource("imagefilePath")
		//������
		jl1 = new JLabel(new ImageIcon("img\\bgHead1.jpg"));
		this.add(jl1,"North");
		
		//�����в�
		jtp1 = new JTabbedPane();
		
		jp2 = new JPanel(new GridLayout(3,3));
		jp3 = new JPanel();
		jp4 = new JPanel();
		
		jp2.setName("��ͨ�û���¼");
		jp3.setName("��Ա�û���¼");
		jp4.setName("vip�û���¼");
		
		//�ؼ�
		jp2_jl1 = new JLabel("IPv4��",JLabel.CENTER);
		jp2_jl5 = new JLabel("�˿ڣ�",JLabel.CENTER);
		jp2_jl2 = new JLabel("�ǳƣ�",JLabel.CENTER);
		
		jp2_jf2 = new JTextField();
		jp2_jf1 = new JTextField();//ip
		jp2_jpf1 = new JTextField();//port
		jp2_jcb1 = new JCheckBox("�����½");
		jp2_jcb2 = new JCheckBox("��ס��Ϣ");
		//�ѿؼ�����˳����ӵ����
		jp2.add(jp2_jl1);
		jp2.add(jp2_jf1);
		jp2.add(jp2_jcb1);
		
		jp2.add(jp2_jl5);
		jp2.add(jp2_jpf1);
		jp2.add(jp2_jcb2);
		
		jp2.add(jp2_jl2);
		jp2.add(jp2_jf2);
//		jp2.add(jp2_jl4);
		//�������ӵ�ѡ�
		jtp1.add(jp2);
		jtp1.add(jp3);
		jtp1.add(jp4);
		
		this.add(jtp1,"Center");
		
		//�����ϲ�
		jp1_jb1 = new JButton(new ImageIcon("img\\denglu.jpg"));
		jp1_jb2 = new JButton(new ImageIcon("img\\quxiao.jpg"));
		jp1_jb3 = new JButton(new ImageIcon("img\\zhuce.jpg"));
		//����¼�
		jp1_jb1.addActionListener(this);
		jp1_jb2.addActionListener(this);
		jp1_jb3.addActionListener(this);
		
		//����button��С
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
		this.setTitle("��¼���棺ȫ�°�--���л����нӿ�");
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jp1_jb1){
			//��¼
			//���ip��ȷ����¼
			String ipv4 = jp2_jf1.getText();
			int userPort = Integer.parseInt(jp2_jpf1.getText());
			String userName = jp2_jf2.getText();
			
			//�������ж��û��� ip �˿��Ƿ�Ϸ�
			if(true){
				
				//�򿪿ͻ��ˣ��ǽ��棩
				new MsnClient(userName,ipv4,userPort);
				//���´��ڣ��رձ�����
				dispose();
				
			}
			
		}else if(e.getSource() == jp1_jb2){
			//ȡ��
			System.out.println("ȡ��");
		}else if(e.getSource() == jp1_jb3){
			//ע��
			
		}
	}

}