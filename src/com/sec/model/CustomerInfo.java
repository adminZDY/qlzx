package com.sec.model;

import java.io.Serializable;
import java.util.Date;

import com.sec.dao.CustomerInfoDao;

/**
 * 客户信息类
 * @author 郑
 *
 */
public class CustomerInfo implements Serializable {
	
	public CustomerInfo()
	{}
	
	/**
	 * 客户编号
	 */
	private int id;
	/**
	 * 客户邮箱（登录以邮箱为准）
	 */
	private String email;
	/**
	 * 客户密码
	 */
	private String Pwd;
	/**
	 * 注册时间
	 */
	private Date registerTime;
	/**
	 * 客户收货地址
	 */
	private CustomerDatailInfo customerDatail = new CustomerDatailInfo();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return Pwd;
	}
	public void setPwd(String pwd) {
		Pwd = pwd;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public CustomerDatailInfo getCustomerDatail() {
		return customerDatail;
	}
	public void setCustomerDatail(CustomerDatailInfo customerDatail) {
		this.customerDatail = customerDatail;
	}
}


