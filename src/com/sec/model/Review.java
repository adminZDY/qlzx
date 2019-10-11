package com.sec.model;


import java.util.Date;
import com.sec.util.DateTimeUtil;

public class Review {
	private CustomerInfo custoomer;//用户
	private GoodsInfo goodsinfo;//商品
	private OrderInfo orderInfo;//订单
	private Date reviewTime;//时间
	private int reviewStatus;//评论
	private String reviewContent;//内容
	
	public CustomerInfo getCustoomer() {
		return custoomer;
	}
	public void setCustoomer(CustomerInfo custoomer) {
		this.custoomer = custoomer;
	}
	public GoodsInfo getGoodsinfo() {
		return goodsinfo;
	}
	public void setGoodsinfo(GoodsInfo goodsinfo) {
		this.goodsinfo = goodsinfo;
	}
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public Date getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	public int getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
}
