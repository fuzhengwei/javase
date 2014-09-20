package com.drdg.util.conndb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnSqlServer {
	
	private static Connection conn;
	private static String OJDO = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String URL = "jdbc:sqlserver://localhost:1433; DatabaseName=DiaryDES";
	private static String USER = "sa";
	private static String PASSWORD = "sa";
	
	static{
		try {
			Class.forName(OJDO);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn.setAutoCommit(false);
			System.out.println("连接成功... ...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		return conn;
	}
	
	public static void CloseConn(){
		if(conn != null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
