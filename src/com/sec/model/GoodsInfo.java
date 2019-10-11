package com.sec.model;

public class GoodsInfo {
	private int goodsId ;//商品编号
	private GoodsType goodstype = new GoodsType();//商品类型
	private String goodsName;//商品名称
	private double price;//商品价格
	private float discount;//商品折扣
	private int isNew;//是否新品（0新品，1不是新品）
	private int isRecommend;//是否推荐（0推荐，1不推荐）
	private int status;//商品状态（0上架，1下架）
	private String photo;//商品图片
	private String remark;//商品描述
	private String detailed;//商品详细
	
	
	public String getDetailed() {
		return detailed;
	}
	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public GoodsType getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(GoodsType goodstype) {
		this.goodstype = goodstype;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public int getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 商品价格乘折扣
	 * @return
	 */
	public double getDiscountPrice(){
		double price = this.price * this.discount/10;
		price = Math.round(price * 100.0)/100.0;
		return price;
	}
}
