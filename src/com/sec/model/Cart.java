package com.sec.model;

import java.util.HashMap;
import java.util.Iterator;

public class Cart {
/**
 *���ﳵ 
 */
private HashMap<Integer, OrderGoodsInfo> cart;
	/**
	 * ��������GoodsInfo�������������
	 */
	public Cart(){
		cart = new HashMap<Integer, OrderGoodsInfo>();
	}
	/**
	 * ���ذ��������Ѿ���������Ʒ��Ϣ����������
	 * @param goods
	 */
	public HashMap<Integer , OrderGoodsInfo> getCart(){
		return cart;
	}
	
	
	/**
	 * ���һ����Ʒ�����ﳵ�У����������Ʒ�ڹ��ﳵ���Ѿ������Ǿ��޸����е���Ʒ������
	 * ��֮������һ���µ�Item������ӵ�items�����С�
	 * @param id
	 * @return
	 */
	public void addGoods(GoodsInfo goods,int quantity){
		OrderGoodsInfo orderGods = null;
		
			if(cart.containsKey(goods.getGoodsId())){
				orderGods =cart.get(Integer.valueOf(goods.getGoodsId()));
				orderGods.setQuantity(orderGods.getQuantity() + quantity);
			}else{
				orderGods = new OrderGoodsInfo();
				orderGods.setGoodsInfo(goods);
				orderGods.setQuantity(quantity);
				//����Ʒid��Ϊkeyֵ
				cart.put(Integer.valueOf(goods.getGoodsId()), orderGods);
			}
		}
	
	/**
	 * �ӹ��ﳵ�У�ɾ����Ʒ
	 * @param id
	 * @return
	 */
	public boolean removeGoods(int id){
		if(cart.containsKey(Integer.valueOf(id))){
			cart.remove(Integer.valueOf(id));
			return true;
		}
		return false;	
	}
	
	/**
	 * ��չ��ﳵ
	 * @param id
	 * @param quantity
	 */
	public void clearCart(){
		if(this.cart != null){
			this.cart.clear();
		}
	}
	
	/**
	 * ���Ĺ��ﳵ�е���Ʒ����
	 * @param id
	 * @param quantity
	 */
	public void updateQuantity(int id , int quantity){
		if(cart.containsKey(id)){
			OrderGoodsInfo orderGoods = cart.get(id);
			orderGoods.setQuantity(quantity);
		}
	}
	
	/**
	 * �ܽ��
	 * @return
	 */
	public double getTotalPrice(){
		double sum = 0.0;
		Iterator<Integer> it = cart.keySet().iterator();
		OrderGoodsInfo orderGoods = null;
		while(it.hasNext()){
			orderGoods = cart.get(it.next());
			sum = sum + orderGoods.getSumPrice();
		}
		return Math.round(sum*100.0)/100.0;
	}
}
