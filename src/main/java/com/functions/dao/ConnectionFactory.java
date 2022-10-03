package com.functions.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

	public static Connection getConnection(String url, String user, String pw) {
		Connection dm = null;
		try {
			if(url.contains("mysql")){
				Class.forName("com.mysql.cj.jdbc.Driver");
				dm = DriverManager.getConnection(url, user, pw);
			}else if(url.contains("sqlserver")){
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dm = DriverManager.getConnection(url, user, pw);
			}else if(url.contains("oracle")){
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				dm = DriverManager.getConnection(url);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dm;
	}
}
