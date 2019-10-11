package com.sec.util;

import java.sql.*;


public class DBConn {
	static {
		//��������
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *������������ȡ���Ӷ���
	 * @return �������Ӷ���
	 */
	public static Connection getConn()
	{
		Connection conn = null;
		try {
			//��֯�����ַ���
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=shoppingDB","sa","123456");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * �ر�����
	 * @param rs �����
	 * @param pstmt �������
	 * @param conn ���Ӷ���
	 */
	public static void closeDB(ResultSet rs,PreparedStatement pstmt,Connection conn)
	{
		try {
			//�رս����
			if(rs != null)
			{
				rs.close();
			}
			//�ر��������
			if (pstmt != null) 
			{
				pstmt.close();
			}
			//�ر�����
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
