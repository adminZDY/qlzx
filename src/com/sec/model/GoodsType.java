package com.sec.model;

import java.util.ArrayList;


/**
 * 商品类型
 * @author 郑
 *
 */
public class GoodsType {
	/**
	 * 类型编号
	 */
	private int typeId;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 商品类型下的商品信息
	 */
	private ArrayList<GoodsInfo> goodsInfo = new ArrayList<GoodsInfo>();
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
