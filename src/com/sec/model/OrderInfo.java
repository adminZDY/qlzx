package com.sec.model;

import java.util.ArrayList;
import java.util.Date;


public class OrderInfo {
	
	private CustomerInfo Customer;//�ͻ�
	private int status;//����״̬
	private ArrayList<OrderGoodsInfo> OrderDatails;//����
	private int orderId;//�������
	private Date orderTime;//����ʱ��
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public CustomerInfo getCustomer() {
		return Customer;
	}
	public void setCustomer(CustomerInfo customer) {
		Customer = customer;
	}
	
	public ArrayList<OrderGoodsInfo> getOrderDatails() {
		return OrderDatails;
	}
	public void setOrderDatails(ArrayList<OrderGoodsInfo> orderDatails) {
		OrderDatails = orderDatails;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
