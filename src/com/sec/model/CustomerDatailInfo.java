package com.sec.model;

import java.io.Serializable;

import com.sec.dao.CustomerInfoDao;

/**
 * �ͻ���ַ��Ϣ��
 * @author ֣
 *
 */
public class CustomerDatailInfo implements Serializable {
	/**
	 * �ͻ����
	 */
	private int customerId;
	/**
	 * �ͻ�����
	 */
	private String name;
	/**
	 * �绰
	 */
	private String telphone;
	/**
	 * �ƶ��绰
	 */
	private String mobileTelphone;
	/**
	 * ��ַ
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
