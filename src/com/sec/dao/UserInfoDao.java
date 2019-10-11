package com.sec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sec.model.UserInfo;
import com.sec.util.DBConn;


public class UserInfoDao {
	
	/**
	 * ��ѯ�Ƿ���ڸ��û�
	 * @param user ��̨����Ա����
	 * @return �Ƿ���ڸ��û���booleanֵ��
	 */
	public boolean selectUserInfo(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where userName = ? and userPwd = ?");
			//���ò����б�
			pstmt.setString(1,user.getUserName());
			pstmt.setString(2,user.getUserPwd());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			if(flag = rs.next())
			{
				//�������ֵ
				user.setUserId(rs.getInt("id"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * ��ѯ�Ƿ���ڸ��û�(����ID��ѯ)
	 * @param user ��̨����Ա����
	 * @return �Ƿ���ڸ��û���booleanֵ��
	 */
	public boolean UserSelect(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where Id = ?");
			//���ò����б�
			pstmt.setInt(1,user.getUserId());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			flag = rs.next();
			//�������ֵ
			user.setUserName(rs.getString("userName"));
			user.setUserPwd(rs.getString("userPwd"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * �޸��û�����
	 * @param user ��̨����Ա����
	 * @return �Ƿ��޸ĳɹ���booleanֵ��
	 */
	public boolean updatePwd(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update userInfo set userPwd = ? where id = ?");
			//���ò����б�
			pstmt.setString(1, user.getUserPwd());
			pstmt.setInt(2,user.getUserId());
			int i = pstmt.executeUpdate();
			if(i > 0)
			{
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * �޸��û�����
	 * @param user ��̨����Ա����
	 * @return �Ƿ��޸ĳɹ���booleanֵ��
	 */
	public boolean updateName(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update userInfo set userName = ? where id = ?");
			//���ò����б�
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2,user.getUserId());
			int i = pstmt.executeUpdate();
			if(i > 0)
			{
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * ��ѯ�û������Ƿ�ʹ��
	 * @param user ֮ǰ�û�
	 * @param userName �û�������
	 * @return �����Ƿ�ʹ��
	 */
	public boolean UserNameExists(UserInfo user,String userName)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where Id != ? and userName = ?");
			//���ò����б�
			pstmt.setInt(1,user.getUserId());
			pstmt.setString(2, userName);
			
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			flag = rs.next();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
		return flag;
	}
}
