package com.sec.model;

import java.util.ArrayList;


/**
 * ��Ʒ����
 * @author ֣
 *
 */
public class GoodsType {
	/**
	 * ���ͱ��
	 */
	private int typeId;
	/**
	 * ��������
	 */
	private String typeName;
	/**
	 * ��Ʒ�����µ���Ʒ��Ϣ
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
