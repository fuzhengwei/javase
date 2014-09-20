package com.qq.client.view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.qq.client.MsnClient;
import com.qq.client.io.ReadWriteFile;
import com.qq.model.MessageSerialization;
import com.qq.model.User;

public class QqClientList extends JFrame implements ActionListener,MouseListener,KeyListener,TreeSelectionListener{
	
	
	
	//鍖楅儴缁勪欢
	JPanel jpNorth1;
	JMenuBar jmb1;
	JMenu jm1,jm2,jm3,jmvip;
	JMenuItem jmi1,jm2_1,jm2_2;
	
	//鍗楅儴缁勪欢
	JPanel jpSouth1;
	static JPanel jpSouth2;
	JPanel jpSouth3, jpSouth4;
	JScrollPane jspSouth1,jspSouth2,jspSouth3,jspSouth4;
	static JTextArea jtaSouth1;
	JTextArea jtaSouth2;
	JLabel jl;
	JTabbedPane jtpSouth1;
	
	JTextArea jtaG1;
	JScrollPane jspG1;
	JButton jbG1,jbG2;
	
	JTree tree;
	Dialog dialog;
	//鐢ㄦ埛淇℃伅
	private String userName;
	private String userIpv4;
	private String userPort;
	private String AllPort;
	private static String MYPORT;
	
	private MessageSerialization msn;
	private int joption;
	
	public static boolean bpNew = false;
	
	
	public QqClientList(String userName,String userIpv4){
		
		
		this.userIpv4 = userIpv4;
		
		
		//北部控制
		jmb1 = new JMenuBar();
		
		jm1 = new JMenu("界面设置");
		jmi1 = new JMenuItem("字体设置");
		jm1.add(jmi1);
		
		jm2 = new JMenu("文件发送"); 
		jm2_1 = new JMenuItem("普通文件发送");
		jm2_2 = new JMenuItem("加密文件发送");
		jm2_1.addActionListener(this);
		jm2.add(jm2_1);
		jm2.add(jm2_2);
		
		jm3 = new JMenu("特色服务");
		
		jmvip = new JMenu("vip特权");
		
		jmb1.add(jm1);
		jmb1.add(jm2);
		jmb1.add(jm3);
		jmb1.add(jmvip);
		
		this.add(jmb1,"North");
		
		
		//南部控制
		jtaSouth1 = new JTextArea();
		jtaSouth1.setEditable(false);
		jtaSouth1.setOpaque(false);
		jtaSouth1.setBorder(BorderFactory.createLineBorder(Color.red));
		jtaSouth1.setLineWrap(true);
		jtaSouth1.setForeground(Color.white);
		jtaSouth1.setFont(new Font("楷体_GB2312", 1, 20));
		
		jspSouth1 = new JScrollPane(jtaSouth1);
		jspSouth1.setOpaque(false);
		jspSouth1.getViewport().setOpaque(false);
		jspSouth1.setPreferredSize(new Dimension(400,150));
		
		jpSouth1 = new JPanel(new BorderLayout());
		jpSouth1.setOpaque(false);
		jpSouth1.setPreferredSize(new Dimension(400,100));
		/*输入区域*/
		jtaG1 = new JTextArea();
		jtaG1.setOpaque(false);
		jtaG1.setBorder(BorderFactory.createLineBorder(Color.blue));
		jtaG1.addKeyListener(this);
		jtaG1.setForeground(Color.red);
		jtaG1.setLineWrap(true);
		jtaG1.setFont(new Font("楷体_GB2312", 1, 20));
		jspG1 = new JScrollPane(jtaG1);
		jspG1.setOpaque(false);
		jspG1.getViewport().setOpaque(false);
		jspG1.setPreferredSize(new Dimension(400,100));
		
		jpSouth1.add(jspG1,"West");
		
		//发送按钮的背景设置
		jbG1 = new JButton(new ImageIcon("imgs//BtList_.png"));
		//添加事件
		jbG1.addMouseListener(this);
		jbG1.addActionListener(this);
		
		jbG1.setOpaque(false);
		jbG1.setContentAreaFilled(false);


		jpSouth1.add(jbG1,"Center");
		
		
		jpSouth2 = new JPanel(new GridLayout(20,1));
		jspSouth2 = new JScrollPane(jpSouth2);
		jspSouth2.setPreferredSize(new Dimension(240,300));
		
		jtpSouth1 = new JTabbedPane();
		jspSouth2.setName("好友列表");
		jtpSouth1.add(jspSouth2);
		
		jpSouth3 = new JPanel(new GridLayout(20,2));
		jspSouth3 = new JScrollPane(jpSouth3);
		jspSouth3.setName("群组列表");
		
		jtpSouth1.add(jspSouth3);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("表情地带");
		DefaultMutableTreeNode t1 = new DefaultMutableTreeNode("普通表情");
		DefaultMutableTreeNode t2 = new DefaultMutableTreeNode("文艺表情");
		DefaultMutableTreeNode t3 = new DefaultMutableTreeNode("二的表情");
		//鏅�琛ㄦ儏
		String[] strBiaoQing = {":-)",
								":-D",
								":-P",
								":-(",
								";-)",
								":-O",
								"o_O",
								"@_@",
								};
		for(int i = 0; i < strBiaoQing.length; i ++){
			t1.add(new DefaultMutableTreeNode(strBiaoQing[i]));
		}
		root.add(t1);
		
		String[] strWyBq = {

				"o(-\"-)o",
				"^*(- -)*^",
				"($ _ $)",
				
				
		};
		for(int i = 0; i < strWyBq.length; i ++){
			t2.add(new DefaultMutableTreeNode(strWyBq[i]));
		}
		//鏂囪壓琛ㄦ儏
		root.add(t2);
		
		String[] eDBq = {

				"(^銆俕)y-~~",
				
		};
		
		for(int i = 0; i < eDBq.length; i ++){
			t3.add(new DefaultMutableTreeNode(eDBq[i]));
		}
		
		//2鐨勮〃鎯�
		root.add(t3);

		tree = new JTree(root);
		tree.addTreeSelectionListener(this);
		
		jpSouth4 = new JPanel(new GridLayout(20,2));
		jspSouth4 = new JScrollPane(tree);
		jspSouth4.setName("表情列表");
		
		jtpSouth1.add(jspSouth4);
		
		this.add(jtpSouth1,"East");
		this.add(jpSouth1,"South");
		this.add(jspSouth1,"West");
		
		
		
		//涓荤獥浣撴帶鍒�
		this.setSize(650, 550);
		this.setLocation(200, 100);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(userName);
		
		
		
		//鏃犺儗鏅壊璁剧疆
		JPanel imagePanel;
		ImageIcon background;
		background = new ImageIcon("imgs//listBg2.jpg");//鑳屾櫙鍥剧墖
		JLabel label = new JLabel(background);//鎶婅儗鏅浘鐗囨樉绀哄湪涓�釜鏍囩閲岄潰
		//鎶婃爣绛剧殑澶у皬浣嶇疆璁剧疆涓哄浘鐗囧垰濂藉～鍏呮暣涓潰鏉�
		label.setBounds(0,0,background.getIconWidth(),background.getIconHeight());
		//鎶婂唴瀹圭獥鏍艰浆鍖栦负JPanel锛屽惁鍒欎笉鑳界敤鏂规硶setOpaque()鏉ヤ娇鍐呭绐楁牸閫忔槑
		imagePanel = (JPanel)this.getContentPane();
		imagePanel.setOpaque(false);
		//鎶婅儗鏅浘鐗囨坊鍔犲埌鍒嗗眰绐楁牸鐨勬渶搴曞眰浣滀负鑳屾櫙
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		this.setSize(background.getIconWidth(),background.getIconHeight());

	}
	
	
	//鎶奞qClient閲岄潰鐨勪俊鎭啓鍒扮晫闈笂
	static Set<String> stIpAndPort;
	
	public void setMessageTextArea(MessageSerialization msn){
			//鎺ユ敹鍒颁笢瑗匡紝浼氭湁澹伴煶鎻愮ず
			qqMusic();	
			//个人登录信息，显示
			if("loginMessage".equals(msn.getSendType())){
				
				userName = msn.getUserName();
				userIpv4 = msn.getIpv4();
				userPort = msn.getLongPort();
				
				MYPORT = userPort;
				
				jtaSouth1.append(userName+" "+userIpv4+":"+userPort+"\r\n"+msn.getUserSendMessage());
				jtaSouth1.append("\r\n= = = = = = = = = = = =\r\n");
			}else if("群发".equals(msn.getSendType())){
				jtaSouth1.append(msn.toMyString());
			}else if("好友列表".equals(msn.getSendType())){
				String regex = "<(.*?):(.*?):(.*?)>";
				Matcher mc = Pattern.compile(regex).matcher(msn.getUserSendMessage().toString());
				jpSouth2.removeAll();
				while(mc.find()){
					if(!userName.equals(mc.group(1))){
						jl = new JLabel(mc.group(1)+" "+mc.group(2)+" "+mc.group(3));
						jl.addMouseListener(this);
						jpSouth2.add(jl);
						jpSouth2.updateUI();
						jpSouth2.invalidate(); 
						jpSouth2.validate();
						jpSouth2.repaint();
						
					}
				}
			}
		//鍏夋爣鑷姩涓嬫粴
		jtaSouth1.setCaretPosition(jtaSouth1.getText().length());
	}
	
	//鑾峰緱鏃堕棿鍑芥暟
	public String getTime(){
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("H:m:s");
		return dateFormat.format(date);
	}
	
	public void SetBpNew(boolean bpNew){
		this.bpNew = bpNew;
	}
	
	public static boolean getBpNew(){
		return bpNew;
	}
	
	//鍒ゆ柇淇℃伅鏄笉鏄负绌�
	public boolean isNullTextArea(){
		
		return false;
	}
	//鍙戦�淇℃伅
	@SuppressWarnings("static-access")
	public void SendMess(){
		
		//缁勫悎淇℃伅瑁呰繘StringBuffer
		String strRN = "\r\n";
		
		StringBuffer sbMessage = new StringBuffer();
		sbMessage.append(jtaG1.getText());
		
		//鍙戦�鍒拌嚜宸辩殑鐣岄潰
		
		//鍙戦�鍒板鎴风锛岃瀹㈡埛绔彂閫佺粰鏈嶅姟鍣�
		msn = new MessageSerialization();
		msn.setUserName(userName);
		msn.setIpv4(userIpv4);
		msn.setLongPort(userPort);
		msn.setSendType("群发");
		msn.setUserSendMessage(sbMessage);
		
		//发送信息
		MsnClient.sendMessage(msn);
	
		//娓呯┖
		sbMessage = null;
		
		jtaG1.setText("");
		//鍏夋爣鑷姩涓嬫粴
		jtaSouth1.setCaretPosition(jtaSouth1.getText().length());
		
	}
	
	//鍙戦�娑堟伅鏃跺�鐨勫０闊�
	public void qqMusic(){
		File file = new File("music//gggg.wav");
		try {
			AudioClip clip = Applet.newAudioClip(file.toURL());
			clip.play();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//鍙戦�鏂囦欢绐楀彛Dialog
	@SuppressWarnings("static-access")
	public void sendFileDialog(){
		FileDialog fileDlg = new FileDialog(this, "閫夋嫨鍙戦�鏂囦欢", FileDialog.LOAD);
 		//璁剧疆鏂囦欢瀵硅瘽妗嗭紝瑕佷氦浜掑畬鎴愶紝鎵嶈兘缁х画杩愯
		String fileTxt = "";
		fileDlg.setModal(true);
 		fileDlg.setVisible(true);
 		File file = new File(fileDlg.getDirectory() + fileDlg.getFile());
 		fileTxt = ReadWriteFile.doReadFile(file);
 		
 		//鍙戦�鍒板鎴风锛岃瀹㈡埛绔彂閫佺粰鏈嶅姟鍣�
	}
	
	//鎺ユ敹鏂囦欢绐楀彛Dialog
	public void saveFileDialog(String str){
		FileDialog fileDlg = new FileDialog(this, "閫夋嫨淇濆瓨鏂囦欢", FileDialog.SAVE);
		fileDlg.setModal(true);
		fileDlg.setVisible(true);
		File file = new File(fileDlg.getDirectory() + fileDlg.getFile());
		ReadWriteFile.doWriteFile(file,str);
	}
	
	/**
	 * 浜嬩欢閮ㄥ垎
	 * */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jm2_1){
			sendFileDialog();
		}
		
	}
	
	//榧犳爣鐐瑰嚮浜嬩欢
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbG1){
			jbG1.setIcon(new ImageIcon(("imgs//BtList.png")));  
		}else{
			((JLabel)e.getSource()).setForeground(Color.red);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbG1){
			jbG1.setIcon(new ImageIcon(("imgs//BtList_.png")));   
		}else{
			((JLabel)e.getSource()).setForeground(Color.black);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbG1){
			String str = jtaG1.getText();
			if(str != "" && str.length() != 0 && str!=null && !str.equals("")){
				
				//娑堟伅鍙戦�
				SendMess();
				
			}else{
				new DialogIsNull(this.getX()+150,this.getY()+525);
			}
			
			
			
		}else if(e.getClickCount()==2){
			String strFriendIpAndPort = ((JLabel)e.getSource()).getText();
			
			String regex = "(.*?) (.*?) ([0-9]{4,})";
			Matcher mc = Pattern.compile(regex).matcher(strFriendIpAndPort);
			
			if(mc.find()){
				msn = new MessageSerialization();
				msn.setSendType("单聊");
				msn.setIpv4(mc.group(2));
				msn.setUserName(mc.group(1));
				msn.setMyPort(MYPORT);
				msn.setYoPort(mc.group(3));
				msn.setUserSendMessage(new StringBuffer(mc.group(1)+"正在向你单聊\r\n"));
				MsnClient.sendMessage(msn);
			}
			
			//鑾峰彇淇℃伅鍚庯紝瀹炰緥鍖朡qClient骞舵妸ip鍜岀鍙ｅ彂閫佽繃鍘�
			SetBpNew(true);
		}
		
	}

	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	//閿洏浜嬩欢
	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		if(arg0.getModifiers() == InputEvent.CTRL_MASK && arg0.getKeyCode() == KeyEvent.VK_ENTER){
			
			String str = jtaG1.getText();
			if(str != "" && str.length() != 0 && str!=null && !str.equals("")){
				//澹伴煶鎻愮ず
//				qqMusic();
				//娑堟伅鍙戦�
				SendMess();
				
			}else{
				new DialogIsNull(this.getX()+150,this.getY()+525);
			}
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//缁欐爲娣诲姞浜嬩欢
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		String strBQ = "";
		if(e.getSource() == tree){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			try{
				strBQ = node.toString();
				if(!strBQ.equals("鏅�琛ㄦ儏") && !strBQ.equals("鏂囪壓琛ㄦ儏") && !strBQ.equals("浜岀殑琛ㄦ儏") && !strBQ.equals("琛ㄦ儏鍦板甫")){
					jtaG1.append(strBQ);
				}
				
		
			}catch(NullPointerException ex){
				
			}
		}
	}
	
}
