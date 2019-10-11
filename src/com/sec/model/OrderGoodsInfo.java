package com.sec.model;

import java.io.Serializable;

/**
 * ������Ʒ��Ϣ������������Ϣ����
 * @author ֣
 *
 */
public class OrderGoodsInfo implements Serializable {
	/**
	 * �����������
	 */
	private OrderInfo orderInfo;
	
	/**
	 * ��Ʒ��Ϣ
	 */
	private GoodsInfo goodsInfo;
	
	/**
	 * ��Ʒ��������
	 */
	private int quantity;

	/**
	 * ��ȡ��ƷС��
	 * @return
	 */
	public double getSumPrice()
	{
		return this.quantity * this.goodsInfo.getDiscountPrice();
	}

	
	
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
