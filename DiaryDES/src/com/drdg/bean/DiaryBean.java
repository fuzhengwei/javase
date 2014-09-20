package com.drdg.bean;

/**
 * SELECT TOP 1000 [d_id] ,[d_title] ,[d_content] ,[d_date] ,[d_key] FROM
 * [DiaryDES].[dbo].[diary]
 * 
 * @author user
 * 
 */
public class DiaryBean {

	private String d_id;
	private String d_title;
	private String d_content;
	private String d_date;
	private String d_key;

	public String getD_id() {
		return d_id;
	}

	public void setD_id(String d_id) {
		this.d_id = d_id;
	}

	public String getD_title() {
		return d_title;
	}

	public void setD_title(String d_title) {
		this.d_title = d_title;
	}

	public String getD_content() {
		return d_content;
	}

	public void setD_content(String d_content) {
		this.d_content = d_content;
	}

	public String getD_date() {
		return d_date;
	}

	public void setD_date(String d_date) {
		this.d_date = d_date;
	}

	public String getD_key() {
		return d_key;
	}

	public void setD_key(String d_key) {
		this.d_key = d_key;
	}


}
