package com.stack.util.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.stack.util.conn.ConnMysql;

public class HandleBase {

	protected Connection conn;
	protected PreparedStatement ps;
	protected ResultSet rs;

	/**
	 * preparedstatement ps
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement getPs(String sql) {
		conn = ConnMysql.getConn();
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * commit
	 */
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * rollback
	 */
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * close
	 */
	public void close(Connection conn){
		ConnMysql.CloseConn(conn);
	}
}
