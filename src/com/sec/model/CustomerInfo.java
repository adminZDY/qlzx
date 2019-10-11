package com.sec.model;

import java.io.Serializable;
import java.util.Date;

import com.sec.dao.CustomerInfoDao;

/**
 * �ͻ���Ϣ��
 * @author ֣
 *
 */
public class CustomerInfo implements Serializable {
	
	public CustomerInfo()
	{}
	
	/**
	 * �ͻ����
	 */
	private int id;
	/**
	 * �ͻ����䣨��¼������Ϊ׼��
	 */
	private String email;
	/**
	 * �ͻ�����
	 */
	private String Pwd;
	/**
	 * ע��ʱ��
	 */
	private Date registerTime;
	/**
	 * �ͻ��ջ���ַ
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


