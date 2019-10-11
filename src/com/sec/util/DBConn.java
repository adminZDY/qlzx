package com.sec.util;

import java.sql.*;


public class DBConn {
	static {
		//加载驱动
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *加载驱动，获取连接对象
	 * @return 返回连接对象
	 */
	public static Connection getConn()
	{
		Connection conn = null;
		try {
			//组织连接字符串
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=shoppingDB","sa","123456");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭连接
	 * @param rs 结果集
	 * @param pstmt 命令对象
	 * @param conn 连接对象
	 */
	public static void closeDB(ResultSet rs,PreparedStatement pstmt,Connection conn)
	{
		try {
			//关闭结果集
			if(rs != null)
			{
				rs.close();
			}
			//关闭命令对象
			if (pstmt != null) 
			{
				pstmt.close();
			}
			//关闭连接
			if (conn != null) 
			{
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
