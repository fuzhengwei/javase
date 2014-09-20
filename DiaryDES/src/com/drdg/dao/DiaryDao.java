package com.drdg.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.drdg.bean.DiaryBean;
import com.drdg.endecry.MD5Util;
import com.drdg.endecry.TripleDES;
import com.drdg.util.conndb.GetSelectQuery;

public class DiaryDao extends GetSelectQuery{

	private static TripleDES td = new TripleDES();
	
	public String doInsertDiary(DiaryBean model){
		
		try {
			ps = super.getPs("INSERT INTO [diary]([d_title],[d_content],[d_date],[d_key]) VALUES(?,?,?,?)");
			ps.setString(1, model.getD_title());
			ps.setString(2, model.getD_content());
			ps.setString(3, model.getD_date());
			ps.setString(4, model.getD_key());
			ps.executeUpdate();
			super.commit();
			
		} catch (SQLException e) {
			super.rollback();
			e.printStackTrace();
		}
		
		return doSelectMaxLastId();
		
	}
	
	public boolean doDeleteDiaryById(String d_id){
		
		boolean bool = false;
		
		try {
			ps = super.getPs("DELETE FROM [diary] WHERE [d_id] = ?");
			ps.setString(1, d_id);
			bool = ps.executeUpdate() > 0;
			super.commit();
		} catch (SQLException e) {
			super.rollback();
			e.printStackTrace();
		}
		return bool;
	}
	
	public boolean doUpdateDiary(DiaryBean model){
		boolean bool = false;
		try {
			ps = super.getPs("UPDATE [diary] SET [d_title] = ?,[d_content] = ?,[d_date] = ?,[d_key] = ? WHERE [d_id] = ?");
			ps.setString(1, model.getD_title());
			ps.setString(2, model.getD_content());
			ps.setString(3, model.getD_date());
			ps.setString(4, model.getD_key());
			ps.setString(5, model.getD_id());
			bool = ps.executeUpdate() > 0;
			super.commit();
		} catch (SQLException e) {
			super.rollback();
			e.printStackTrace();
		}
		return bool;
		
	}
	
	public String doSelectMaxLastId(){
		String d_id = "";
		try {
			ps = super.getPs("SELECT MAX(d_id) FROM [diary]");
			rs = ps.executeQuery();
			if(rs.next()){
				d_id = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return d_id;
	}

	public DiaryBean doSelectDiaryById(String d_id){
		DiaryBean model = new DiaryBean();
		try {
			ps = super.getPs("SELECT [d_id],[d_title],[d_content],[d_date] FROM [diary] WHERE [d_id] = ?");
			ps.setString(1, d_id);
			rs = ps.executeQuery();
			if(rs.next()){
				model.setD_id(rs.getString("d_id"));
				model.setD_title(rs.getString("d_title"));
				model.setD_date(rs.getString("d_date"));
//				model.setD_content(MD5Util.convertMD5(rs.getString("d_content")));
				//private static TripleDES td = new TripleDES();
				model.setD_content(td.getDecString(rs.getString("d_content"), "Key"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public Map<String, ArrayList<DiaryBean>> doSelectDiary(){
		
		Map<String, ArrayList<DiaryBean>> modelMap = new HashMap<String, ArrayList<DiaryBean>>();
		
		try {
			ps = super.getPs("SELECT [d_id],[d_title],[d_content],[d_date],[d_key] FROM [diary] ORDER BY [d_id] desc");
			rs = ps.executeQuery();
			while(rs.next()){
				
				DiaryBean model = new DiaryBean();
				
				model.setD_id(rs.getString("d_id"));
				model.setD_title(rs.getString("d_title"));
				model.setD_date(rs.getString("d_date"));
				model.setD_key(rs.getString("d_key"));
				
				if(null == modelMap.get(rs.getString("d_date"))){
					ArrayList<DiaryBean> modelList = new ArrayList<DiaryBean>();
					modelList.add(model);
					modelMap.put(rs.getString("d_date"), modelList);
				}else{
					ArrayList<DiaryBean> modelList = modelMap.get(rs.getString("d_date"));
					modelList.add(model);
					modelMap.put(rs.getString("d_date"), modelList);
				}
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return modelMap;
		
	}
	
	
	
	public ArrayList<String> doGetDiaryDateGroup(){
		ArrayList<String> dateList = new ArrayList<String>();
		try {
			ps = super.getPs("select d_date from [diary] group by d_date");
			rs = ps.executeQuery();
			
			while(rs.next()){
				dateList.add(rs.getString("d_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dateList;
		
	}
	
}
