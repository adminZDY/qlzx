package com.sec.model;

import java.util.HashMap;
import java.util.Iterator;

public class Cart {
/**
 *购物车 
 */
private HashMap<Integer, OrderGoodsInfo> cart;
	/**
	 * 保存所有GoodsInfo对象的容器对象
	 */
	public Cart(){
		cart = new HashMap<Integer, OrderGoodsInfo>();
	}
	/**
	 * 返回包含所有已经订购的商品信息的容器对象
	 * @param goods
	 */
	public HashMap<Integer , OrderGoodsInfo> getCart(){
		return cart;
	}
	
	
	/**
	 * 添加一种商品到购物车中，如果这种商品在购物车中已经存在那就修改已有的商品的数量
	 * 反之，构造一个新的Item对象添加到items对象中。
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
				//将商品id作为key值
				cart.put(Integer.valueOf(goods.getGoodsId()), orderGods);
			}
		}
	
	/**
	 * 从购物车中，删除商品
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
	 * 清空购物车
	 * @param id
	 * @param quantity
	 */
	public void clearCart(){
		if(this.cart != null){
			this.cart.clear();
		}
	}
	
	/**
	 * 更改购物车中的商品数量
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
	 * 总金额
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
