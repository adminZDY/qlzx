package com.sec.model;

import java.util.Date;


public class BulletinInfo {

	private int Id ;//编号
	private String Title;//标题
	private String Content;//内容
	private UserInfo User;//发布者
	private Date CreateTime;//发布时间
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public UserInfo getUser() {
		return User;
	}
	public void setUser(UserInfo user) {
		User = user;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	
	
}
