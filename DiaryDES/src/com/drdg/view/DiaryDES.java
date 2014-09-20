package com.drdg.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.TableView.TableRow;
import javax.swing.tree.DefaultMutableTreeNode;

import com.drdg.bean.DiaryBean;
import com.drdg.service.DiaryService;
import com.drdg.util.date.DateUtil;

public class DiaryDES extends JFrame implements TreeSelectionListener,
		MouseListener {

	// ������Ϣ
	private Map<String, ArrayList<DiaryBean>> modelMap;

	private ArrayList<String> dateList;

	private int nodeSum = 0;

	private String DRDN = "\r\n";

	private DiaryBean diaryBean;
	// ����һά������Ϊ�б���
	Object[] columnTitle = { "���", "ID", "����", "����" };

	/**
	 * ���캯��
	 */
	public DiaryDES() {
		// �������Ϣ
		initDiaryTree();
		// ��ʼ������
		initView();
		// ��ʼ�����ṹ
		initSPTreeView();

		// ��ʼ��ɾ��������
		initUpdateView();

		// ��ʼ���޸Ľ�����
		initDeleteView();

	}

	public static void main(String[] args) {

		new DiaryDES();

	}

	/**
	 * ����¼�
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// �����ռ�
		if (ip_jb_save == e.getSource()) {
			String d_title = ip_jtf.getText();
			String d_content = ip_jta.getText();
			String d_id = DiaryService.doInsertDiary(d_title, d_content);
			addDiaryTreeNode(d_id, d_title);
			addUpDiaryTableRow(d_id, d_title);
			addDiaryTableRow(d_id, d_title);
			ip_jtf.setText(null);
			ip_jta.setText(null);
		} else if (dp_jtable == e.getSource()) {

		} else if (dp_jb_delete == e.getSource()) {
			tableModel = dp_jtable.getModel();
			Object value = tableModel.getValueAt(dp_jtable.getSelectedRow(), 1);
			if (DiaryService.doDeleteDiaryById(value.toString())) {
				DefaultTableModel model = (DefaultTableModel) (dp_jtable
						.getModel());
				model.removeRow(dp_jtable.getSelectedRow());
				dp_jtable.revalidate();
				
			} else {
				System.out.println("ɾ��ʧ��");
			}
		} else if (up_jtable == e.getSource()) {
			tableModel_up = up_jtable.getModel();
			diaryBean = DiaryService.doSelectDiaryById((String) tableModel_up
					.getValueAt(up_jtable.getSelectedRow(), 1));

			up_jta.setText(diaryBean.getD_content());
			up_jtf.setText(diaryBean.getD_title());

		} else if (up_jb_save == e.getSource()) {

			DiaryService.doUpdateDiary(diaryBean.getD_id(), up_jtf.getText(),
					up_jta.getText());
			up_jta.setText(null);
			up_jtf.setText(null);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * ������¼�
	 */
	public void valueChanged(TreeSelectionEvent e) {
		try {
			if (sp_jtree == e.getSource()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) sp_jtree
						.getLastSelectedPathComponent();
				if (node.isLeaf()) {
					DiaryBean model = DiaryService.doSelectDiaryById(node
							.toString().substring(
									node.toString().lastIndexOf(":") + 1));
					sp_jta.setText(null);
					sp_jta.append(model.getD_title());
					sp_jta.append(DRDN);
					sp_jta.append(model.getD_date());
					sp_jta.append(DRDN);
					sp_jta.append(model.getD_content());
				}
			}
		} catch (Exception exception) {

		}

	}

	private void addUpDiaryTableRow(String d_id, String d_title){
		DefaultTableModel model = (DefaultTableModel) (up_jtable.getModel());
		Object[] diaryTable = { model.getRowCount() + 1, d_id, d_title,
				DateUtil.getDate() };
		model.addRow(diaryTable);
	}
	
	private void addDiaryTableRow(String d_id, String d_title) {
		DefaultTableModel model = (DefaultTableModel) (dp_jtable.getModel());
		Object[] diaryTable = { model.getRowCount() + 1, d_id, d_title,
				DateUtil.getDate() };
		model.addRow(diaryTable);
	}

	private void addDiaryTreeNode(String d_id, String d_title) {
		for (DefaultMutableTreeNode dn : dateNode) {
			if (dn.toString().equals(DateUtil.getDate())) {
				dn.add(new DefaultMutableTreeNode(d_title + ":" + d_id));
				sp_jtree.updateUI();
			}
		}
	}

	/**
	 * ������ṹ��Ϣ
	 */
	private void initDiaryTree() {

		modelMap = DiaryService.doSelectDiary();

		dateList = DiaryService.doGetDiaryDateGroup();

	}

	/**
	 * ��ʼ����ѯ������
	 */
	private void initSPTreeView() {

		dateNode = new DefaultMutableTreeNode[dateList.size()];

		int i = 0;
		nodeSum = 0;
		for (String date : dateList) {

			dateNode[i] = new DefaultMutableTreeNode(date);

			titleNode = new DefaultMutableTreeNode[modelMap.get(date).size()];
			int title_i = 0;
			for (DiaryBean bean : modelMap.get(date)) {
				titleNode[title_i] = new DefaultMutableTreeNode(
						bean.getD_title() + ":" + bean.getD_id());

				dateNode[i].add(titleNode[title_i]);

				title_i++;
				// ��¼�ڵ�����
				nodeSum++;
			}

			sp_treeNode.add(dateNode[i]);

			i++;

		}

		sp_jsp_jtree = new JScrollPane(sp_jtree);
		sp_jsp_jtree.setSize(190, 450);
		sp_jsp_jtree.setLocation(600, 0);

		selectPanel.add(sp_jsp_jtree);

	}

	/**
	 * ��ʼ��ɾ��������
	 */
	private void initUpdateView() {
		Collection<ArrayList<DiaryBean>> collectionModelList = modelMap
				.values();

		Object[][] diaryTable = new Object[nodeSum][4];

		int i = 0;
		for (ArrayList<DiaryBean> modelList : collectionModelList) {

			for (DiaryBean model : modelList) {
				diaryTable[i][0] = i + 1;
				diaryTable[i][1] = model.getD_id();
				diaryTable[i][2] = model.getD_title();
				diaryTable[i][3] = model.getD_date();
				i++;
			}

		}

		defaultTableModel = new DefaultTableModel(diaryTable, columnTitle);
		dp_jtable = new JTable(defaultTableModel);
		dp_jtable.addMouseListener(this);
		dp_jsp = new JScrollPane(dp_jtable);
		dp_jsp.setSize(790, 350);
		dp_jsp.setLocation(0, 0);

		deletePanel.add(dp_jsp);

	}

	/**
	 * ��ʼ���޸Ľ���
	 */
	private void initDeleteView() {
		Collection<ArrayList<DiaryBean>> collectionModelList = modelMap
				.values();

		Object[][] diaryTable = new Object[nodeSum][4];

		int i = 0;
		for (ArrayList<DiaryBean> modelList : collectionModelList) {

			for (DiaryBean model : modelList) {
				diaryTable[i][0] = i + 1;
				diaryTable[i][1] = model.getD_id();
				diaryTable[i][2] = model.getD_title();
				diaryTable[i][3] = model.getD_date();
				i++;
			}

		}

		defaultTableModel_up = new DefaultTableModel(diaryTable, columnTitle);
		up_jtable = new JTable(defaultTableModel_up);
		up_jtable.addMouseListener(this);
		up_jsp = new JScrollPane(up_jtable);
		up_jsp.setSize(790, 100);
		up_jsp.setLocation(0, 0);
		updatePanel.add(up_jsp);
	}

	/**
	 * ��ʼ������
	 */
	private void initView() {

		jTablePane = new JTabbedPane();

		// �鿴�ռ�
		selectPanel = new JPanel(null);
		selectPanel.setName("�鿴�ռ�");
		sp_treeNode = new DefaultMutableTreeNode("�ռǱ�");
		sp_jtree = new JTree(sp_treeNode);
		sp_jtree.addTreeSelectionListener(this);
		sp_jta = new JTextArea();
		sp_jta.setLineWrap(true);
		sp_jta.setEditable(false); // ��������
		sp_jsp = new JScrollPane(sp_jta);
		sp_jsp.setSize(600, 450);
		sp_jsp.setLocation(0, 0);
		selectPanel.add(sp_jsp);
		jTablePane.add(selectPanel);

		// �����ռ�
		insertPanel = new JPanel(null);
		insertPanel.setName("�����ռ�");
		ip_jtf = new JTextField();
		ip_jtf.setSize(800, 40);
		ip_jtf.setLocation(0, 0);
		ip_jtf.setFont(new Font("����_GB2312", 1, 20));
		insertPanel.add(ip_jtf);

		ip_jta = new JTextArea();
		ip_jta.setLineWrap(true);
		ip_jsp = new JScrollPane(ip_jta);
		ip_jsp.setSize(790, 300);
		ip_jsp.setLocation(0, 45);
		insertPanel.add(ip_jsp);
		ip_jb_save = new JButton("�����ռ�");
		ip_jb_save.addMouseListener(this);
		ip_jb_save.setSize(100, 30);
		ip_jb_save.setLocation(680, 355);
		ip_jb_save.setBackground(Color.yellow);
		ip_jb_save.setForeground(Color.red);
		insertPanel.add(ip_jb_save);
		jTablePane.add(insertPanel);

		// ɾ���ռ�
		deletePanel = new JPanel(null);
		deletePanel.setName("ɾ���ռ�");
		dp_jb_delete = new JButton("ɾ���ռ�");
		dp_jb_delete.addMouseListener(this);
		dp_jb_delete.setSize(100, 30);
		dp_jb_delete.setLocation(680, 355);
		dp_jb_delete.setBackground(Color.BLACK);
		dp_jb_delete.setForeground(Color.white);
		deletePanel.add(dp_jb_delete);
		jTablePane.add(deletePanel);

		// �޸��ռ�
		updatePanel = new JPanel(null);
		updatePanel.setName("�޸��ռ�");
		up_jta = new JTextArea();
		up_jta.setLineWrap(true);
		up_jsp_jta = new JScrollPane(up_jta);
		up_jsp_jta.setSize(790, 245);
		up_jsp_jta.setLocation(0, 105);
		updatePanel.add(up_jsp_jta);
		up_jb_save = new JButton("�޸��ռ�");
		up_jb_save.addMouseListener(this);
		up_jb_save.setSize(100, 30);
		up_jb_save.setLocation(680, 355);
		up_jb_save.setBackground(Color.green);
		up_jb_save.setForeground(Color.blue);
		updatePanel.add(up_jb_save);
		up_jtf = new JTextField();
		up_jtf.setFont(new Font("����_GB2312", 1, 20));
		up_jtf.setSize(350, 35);
		up_jtf.setLocation(0, 355);
		updatePanel.add(up_jtf);
		jTablePane.add(updatePanel);

		this.add(jTablePane);

		// ���ô���
		this.setLocation(300, 150);
		this.setSize(800, 450);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("DiaryDES");
	}

	private JLabel sp_jltitle, sp_jldate, ip_jl_key;

	private JTabbedPane jTablePane;

	private JPanel selectPanel, insertPanel, deletePanel, updatePanel;

	private JTree sp_jtree;

	private DefaultMutableTreeNode sp_treeNode;

	private DefaultMutableTreeNode[] dateNode, titleNode;

	private JScrollPane sp_jsp, sp_jsp_jtree, ip_jsp, dp_jsp, up_jsp,
			up_jsp_jta;

	private JTextArea sp_jta, ip_jta, up_jta;

	private JTextField ip_jtf, ip_jtf_key, up_jtf;

	private JButton ip_jb_save, ip_jb_clear, dp_jb_delete, up_jb_save;

	private JTable dp_jtable, up_jtable;

	private DefaultTableModel defaultTableModel, defaultTableModel_up;

	private TableModel tableModel, tableModel_up;
}
