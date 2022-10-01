package com.functions.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;


public class ConnectionFactory {

	public static Connection getConnection(String url, String user, String pw) {
		Connection dm = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			dm = DriverManager.getConnection(url, user, pw);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dm;
	}
}
