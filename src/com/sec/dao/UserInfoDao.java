package com.sec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sec.model.UserInfo;
import com.sec.util.DBConn;


public class UserInfoDao {
	
	/**
	 * 查询是否存在该用户
	 * @param user 后台管理员对象
	 * @return 是否存在该用户（boolean值）
	 */
	public boolean selectUserInfo(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where userName = ? and userPwd = ?");
			//设置参数列表
			pstmt.setString(1,user.getUserName());
			pstmt.setString(2,user.getUserPwd());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			if(flag = rs.next())
			{
				//结果集赋值
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
	 * 查询是否存在该用户(根据ID查询)
	 * @param user 后台管理员对象
	 * @return 是否存在该用户（boolean值）
	 */
	public boolean UserSelect(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where Id = ?");
			//设置参数列表
			pstmt.setInt(1,user.getUserId());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			flag = rs.next();
			//结果集赋值
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
	 * 修改用户密码
	 * @param user 后台管理员对象
	 * @return 是否修改成功（boolean值）
	 */
	public boolean updatePwd(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update userInfo set userPwd = ? where id = ?");
			//设置参数列表
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
	 * 修改用户名称
	 * @param user 后台管理员对象
	 * @return 是否修改成功（boolean值）
	 */
	public boolean updateName(UserInfo user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update userInfo set userName = ? where id = ?");
			//设置参数列表
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
	 * 查询用户名称是否被使用
	 * @param user 之前用户
	 * @param userName 用户新名称
	 * @return 名称是否被使用
	 */
	public boolean UserNameExists(UserInfo user,String userName)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where Id != ? and userName = ?");
			//设置参数列表
			pstmt.setInt(1,user.getUserId());
			pstmt.setString(2, userName);
			
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
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
