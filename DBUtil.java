package com.wipro.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	public static Connection getDBConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:ORCL" ;
		String user = "B44641345072" ;
		String pswd ="B44641345072";
		Connection conn = DriverManager.getConnection(url,user,pswd);
		return conn;
	}	
}
