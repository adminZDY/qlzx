package com.sec.model;

/**
 * �û���Ϣ��
 * @author ֣
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
	 * ��̨����Ա���
	 */
	private int userId;
	/**
	 * ��̨����Ա�û���
	 */
	private String userName;
	/**
	 * ����
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
