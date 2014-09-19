package com.stack.util.rsq;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.stack.bean.PageBean;
import com.stack.util.base.HandleBase;


public class ReflectQuery2TDao  extends HandleBase {

	// class
	private Class<?> loaderClass = null;
	// properties field
	private Field[] loaderField = null;
	// bean url
	private String beanUrl = "";
	// table name
	private String[] tableName = new String[2];
	// page bean
	private PageBean pageBean = new PageBean();
	
	private ArrayList<String> alID = new ArrayList<String>();
	
	private static StringBuilder strSelect = new StringBuilder("SELECT ");
	private StringBuilder strSelectModel  = new StringBuilder(" WHERE ");
	private StringBuilder strSelectPage = new StringBuilder(" LIMIT ?,?");
	private boolean isOne = false, isAddComma = false;
	
	private ReflectQuery2TDao(){}
	
	public ReflectQuery2TDao(String beanUrl){
		this.beanUrl = beanUrl;
		
		try {
			// The class loading
			loaderClass = Class.forName(beanUrl);
			// Get the properties of the class
			loaderField = loaderClass.getDeclaredFields();
			// get table name by beanUrl
			Matcher m = Pattern.compile("([A-Z]{1,}[a-z]{1,})").matcher((beanUrl.substring(beanUrl.lastIndexOf(".") + 1)
					.replace("Bean", "")));
			
			int i = 0;
			while(m.find()){
				tableName[i] = m.group(1).toLowerCase();
				i++;
			}
			
			// assemble sql string
			setSelectSql();
			setSelectModelSql();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<?> doSelectModelList(PageBean pageBean) {
		ArrayList<Object> modelList = new ArrayList<Object>();
		try {
			ps = getPs(strSelect.toString()+strSelectPage.toString());
			ps.setInt(1, pageBean.getFirstResult());
			ps.setInt(2, pageBean.getMaxResult());
			rs = ps.executeQuery();
			while (rs.next()) {
				Object obj = Class.forName(beanUrl).newInstance();
				for (Field f : loaderField) {
					Method m_set = loaderClass.getDeclaredMethod("set"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1), f.getType().getName()
							.getClass());
					m_set.invoke(
							obj,
							rs.getString(f.getName().substring(0, 1)
									+ f.getName().substring(1)));
				}
				modelList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return modelList;
	}
	
	/**
	 * @param modelId
	 * @return
	 */
	public Object doSelectModelById(String modelId) {
		Object obj = null;
		try {
			obj = Class.forName(beanUrl).newInstance();
			ps = getPs(strSelect.toString()+strSelectModel.toString());
			ps.setString(1, modelId);
			rs = ps.executeQuery();
			if(rs.next()){
				for (Field f : loaderField) {
					Method m_set = loaderClass.getDeclaredMethod("set"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1), f.getType().getName()
							.getClass());
					m_set.invoke(
							obj,
							rs.getString(f.getName().substring(0, 1)
									+ f.getName().substring(1)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	private void setSelectSql(){
		
		isOne = false;
		for (Field f : loaderField) {
			if (isOne) {
				strSelect.append(",").append(f.getName());
			}else{
				strSelect.append(f.getName());
				isOne = true;
			}
			
			if(f.getName().matches("[a-zA-Z_]+id$")){
				alID.add(f.getName());
			}
		}
		
		/**
		 * 检测主键id
		 */
		
		strSelect.append(" FROM ")
		.append(tableName[0])
		.append(" Inner Join ")
		.append(tableName[1])
		.append(" ON ").append(alID.get(0)).append(" = ").append("fk_").append(alID.get(0)).trimToSize();
		
	}
	
	private void setSelectModelSql(){
		strSelectModel.append(loaderField[0].getName()).append(" = ?").trimToSize();
	}
	
}
