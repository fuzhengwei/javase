package com.qq.client.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DialogIsNull extends JFrame{

	JLabel jlImg;
	
	
	//构造函数
	public DialogIsNull(int x,int y){
		
		jlImg = new JLabel(new ImageIcon("imgs//isNull.png"));
		this.add(jlImg);
		this.setUndecorated(true);
		this.setLocation(x, y);
		this.setSize(250, 20);
		this.setVisible(true);
		closeDialog();
	}
	
	
	
	//自动关闭的函数
	public void closeDialog(){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
					try {
						Thread.sleep(1500);
						dispose();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}).start();
		
	}
	
}
