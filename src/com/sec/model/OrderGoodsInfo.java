package com.sec.model;

import java.io.Serializable;

/**
 * 订单商品信息（订单详情信息）类
 * @author 郑
 *
 */
public class OrderGoodsInfo implements Serializable {
	/**
	 * 所属订单编号
	 */
	private OrderInfo orderInfo;
	
	/**
	 * 商品信息
	 */
	private GoodsInfo goodsInfo;
	
	/**
	 * 商品订购数量
	 */
	private int quantity;

	/**
	 * 获取商品小计
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
