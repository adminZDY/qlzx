package com.sec.model;

import java.io.Serializable;

import com.sec.dao.CustomerInfoDao;

/**
 * 客户地址信息类
 * @author 郑
 *
 */
public class CustomerDatailInfo implements Serializable {
	/**
	 * 客户编号
	 */
	private int customerId;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 电话
	 */
	private String telphone;
	/**
	 * 移动电话
	 */
	private String mobileTelphone;
	/**
	 * 地址
	 */
	private String address;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getMobileTelphone() {
		return mobileTelphone;
	}
	public void setMobileTelphone(String mobileTelphone) {
		this.mobileTelphone = mobileTelphone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
	
		this.address = address;
	}
}
