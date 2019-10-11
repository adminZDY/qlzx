package com.sec.model;

public class GoodsInfo {
	private int goodsId ;//��Ʒ���
	private GoodsType goodstype = new GoodsType();//��Ʒ����
	private String goodsName;//��Ʒ����
	private double price;//��Ʒ�۸�
	private float discount;//��Ʒ�ۿ�
	private int isNew;//�Ƿ���Ʒ��0��Ʒ��1������Ʒ��
	private int isRecommend;//�Ƿ��Ƽ���0�Ƽ���1���Ƽ���
	private int status;//��Ʒ״̬��0�ϼܣ�1�¼ܣ�
	private String photo;//��ƷͼƬ
	private String remark;//��Ʒ����
	private String detailed;//��Ʒ��ϸ
	
	
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
	 * ��Ʒ�۸���ۿ�
	 * @return
	 */
	public double getDiscountPrice(){
		double price = this.price * this.discount/10;
		price = Math.round(price * 100.0)/100.0;
		return price;
	}
}
