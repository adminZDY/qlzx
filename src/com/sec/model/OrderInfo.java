package com.sec.model;

import java.util.ArrayList;
import java.util.Date;


public class OrderInfo {
	
	private CustomerInfo Customer;//客户
	private int status;//订单状态
	private ArrayList<OrderGoodsInfo> OrderDatails;//订单
	private int orderId;//订单编号
	private Date orderTime;//订单时间
	
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
