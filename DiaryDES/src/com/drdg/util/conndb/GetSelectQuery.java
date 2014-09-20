package com.drdg.util.conndb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.drdg.util.conndb.ConnSqlServer;

public class GetSelectQuery {

	protected Connection conn;
	protected PreparedStatement ps;
	protected ResultSet rs;
	
	
	/**
	 * @param sql
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement getPs(String sql) throws SQLException {
		conn = ConnSqlServer.getConn();
		return conn.prepareStatement(sql);
	}

	/**
	 * Ìá½»
	 * @throws SQLException 
	 */
	public void commit(){
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * »Ø¹ö
	 * @throws SQLException 
	 */
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
