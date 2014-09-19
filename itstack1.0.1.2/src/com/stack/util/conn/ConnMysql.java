package com.stack.util.conn;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnMysql {

	private static String resource = "/com/stack/util/conn/database.properties";

	private static Connection conn;
	private static String OJDO = "";
	private static String URL = "";
	private static String USER = "";
	private static String PASSWORD = "";

	static {
		try {
			Properties p = new Properties();
			InputStream in = ConnMysql.class.getResourceAsStream(resource);
			p.load(in);
			in.close();

			OJDO = p.getProperty("OJDO");
			URL = p.getProperty("URL");
			USER = p.getProperty("USER");
			PASSWORD = p.getProperty("PASSWORD");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConn() {
		try {
			Class.forName(OJDO);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void CloseConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
