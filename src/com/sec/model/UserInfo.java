package com.sec.model;

/**
 * 用户信息类
 * @author 郑
 *
 */
public class UserInfo {
	
	public UserInfo()
	{
		
	}
	
	public UserInfo(String userName,String userPwd)
	{
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
	public UserInfo(int userId)
	{
		this.userId = userId;
	}
	
	/**
	 * 后台管理员编号
	 */
	private int userId;
	/**
	 * 后台管理员用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String userPwd;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
}
