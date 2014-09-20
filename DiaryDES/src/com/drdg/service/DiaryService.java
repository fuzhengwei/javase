package com.drdg.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Map;

import com.drdg.bean.DiaryBean;
import com.drdg.dao.DiaryDao;
import com.drdg.endecry.MD5Util;
import com.drdg.endecry.TripleDES;
import com.drdg.util.date.DateUtil;

public class DiaryService {

	static private DiaryDao diaryDao = new DiaryDao();
	private static TripleDES td = new TripleDES();
    private static Key k = td.getKey("Key");
	/**
	 * 
	 * @return
	 */
	static public Map<String, ArrayList<DiaryBean>> doSelectDiary(){
		return diaryDao.doSelectDiary();
	}
	
	/**
	 * 
	 * @return
	 */
	static public ArrayList<String> doGetDiaryDateGroup(){
		return diaryDao.doGetDiaryDateGroup();
	}
	
	/**
	 * 
	 * @param d_id
	 * @return
	 */
	static public DiaryBean doSelectDiaryById(String d_id){
		return diaryDao.doSelectDiaryById(d_id);
	}
	
	/**
	 * 
	 * @param d_title
	 * @param d_content
	 * @return
	 */
	static public String doInsertDiary(String d_title,String d_content){
		DiaryBean model = new DiaryBean();
		model.setD_title(d_title);
		model.setD_content(MD5Util.convertMD5(d_content));
//		model.setD_content(new String(ThreeDES.encryptMode(ThreeDES.keyBytes, d_content.getBytes())));
		model.setD_content(td.getEncString(d_content, "Key"));
		model.setD_date(DateUtil.getDate());
		return diaryDao.doInsertDiary(model);
	}
	
	/**
	 * 
	 * @param d_id
	 * @return
	 */
	static public boolean doDeleteDiaryById(String d_id){
		return diaryDao.doDeleteDiaryById(d_id);
	}
	
	/**
	 * 
	 * @param d_id
	 * @param d_title
	 * @param d_content
	 * @return
	 */
	static public boolean doUpdateDiary(String d_id,String d_title,String d_content){
		DiaryBean model = new DiaryBean();
		model.setD_id(d_id);
		model.setD_title(d_title);
		model.setD_content(MD5Util.convertMD5(d_content));
//		model.setD_content(new String(ThreeDES.encryptMode(ThreeDES.keyBytes, d_content.getBytes())));
		model.setD_content(td.getEncString(d_content, "Key"));
		model.setD_date(DateUtil.getDate());
		return diaryDao.doUpdateDiary(model);
	}
}
