package com.sec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.w3c.dom.css.Counter;


import com.sec.model.BulletinInfo;
import com.sec.model.CustomerDatailInfo;
import com.sec.model.CustomerInfo;
import com.sec.model.GoodsInfo;
import com.sec.model.GoodsType;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.OrderInfo;
import com.sec.model.Review;
import com.sec.model.UserInfo;
import com.sec.util.DBConn;
import com.sec.util.DateTimeUtil;
import com.sec.util.PageModel;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class OrderInfoDao {
	
	/**
	 * 分页模糊查询
	 * @param pageSize
	 * @param pageNo
	 * @param text
	 * @return
	 */
	public PageModel<OrderGoodsInfo> getAllBulletionInfo(int pageSize,int pageNo ,String text){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<OrderGoodsInfo> pm = null;
		ArrayList<OrderGoodsInfo> items = new ArrayList<OrderGoodsInfo>();
		
		String s = "";
		String a = "";
		
		if(text != null && !"".equals(text) ){
			s = " c.email like '%"+text+"%' and ";
			a = "where email like '%"+text+"%' ";
		}
		
		String sql =" select top "+pageSize+" a.orderid,a.status,a.ordertime,c.id,c.email,d.name,d.telphone,d.mobiletelephone from " +
				"orderinfo a  left join " +
				"customerinfo c on c.id = a.customerid left join " +
				"customerDetailinfo d on d.customerid = c.id where " +
				" "+s+" a.orderid not in(select top "+((pageNo-1)*pageSize)+" orderid from orderinfo "+a+"order by status,ordertime )order by status,ordertime";
	 
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			//执行查询
			rs = pstmt.executeQuery();
			
			OrderGoodsInfo item = null;
			OrderInfo order = null;
			CustomerInfo customer = null;
			CustomerDatailInfo customerDatail = null;
			while(rs.next()){
				/**
				 * 封装所有订单数据
				 */
				//订单商品信息
				item = new OrderGoodsInfo();
				//订单信息
				order = new OrderInfo();
				order.setOrderId(rs.getInt("orderid"));//订单编号
				order.setStatus(rs.getInt("status"));//订单状态
				order.setOrderTime(DateTimeUtil.convertDate(rs.getString("ordertime")));//订单时间
				//客户信息
				customer =new CustomerInfo();
				customer.setEmail(rs.getString("email"));//客户邮箱
				customer.setId(rs.getInt("id"));
				//客户详细信息
				customerDatail = new CustomerDatailInfo();
				customerDatail.setName(rs.getString("name"));//客户名称
				customerDatail.setTelphone(rs.getString("telphone"));//固定电话
				customerDatail.setMobileTelphone(rs.getString("mobiletelephone"));//移动电话
				//获取客户详细信息
				customer.setCustomerDatail(customerDatail);
				//获取客户信息
				order.setCustomer(customer);
				//获取订单信息
				item.setOrderInfo(order);
			
				items.add(item);
			}
			
			pm = new PageModel<OrderGoodsInfo>();
			pm.setData(items);
			pm.setPageNo(pageNo);
			pm.setPageSize(pageSize);
			pm.setTotalRecords(getTotalRecords(conn , pstmt , rs ,text));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn );
		}
		return pm;
	}
	
	/**
	 * 分页查询所有订单
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public PageModel<OrderGoodsInfo> getAllBulletionInfo(int pageSize,int pageNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<OrderGoodsInfo> pm = null;
		ArrayList<OrderGoodsInfo> items = new ArrayList<OrderGoodsInfo>();
		
		String sql =" select top "+pageSize+" a.orderid,a.status,a.ordertime,c.id,c.email,d.name,d.telphone,d.mobiletelephone from " +
				"orderinfo a  left join " +
				"customerinfo c on c.id = a.customerid left join " +
				"customerDetailinfo d on d.customerid = c.id where " +
				" a.orderid not in(select top "+((pageNo-1)*pageSize)+" orderid from orderinfo order by status,ordertime )order by status,ordertime";
	 
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			//执行查询
			rs = pstmt.executeQuery();
			
			OrderGoodsInfo item = null;
			OrderInfo order = null;
			CustomerInfo customer = null;
			CustomerDatailInfo customerDatail = null;
			while(rs.next()){
				/**
				 * 封装所有订单数据
				 */
				//订单商品信息
				item = new OrderGoodsInfo();
				//订单信息
				order = new OrderInfo();
				order.setOrderId(rs.getInt("orderid"));//订单编号
				order.setStatus(rs.getInt("status"));//订单状态
				order.setOrderTime(DateTimeUtil.convertDate(rs.getString("ordertime")));//订单时间
				//客户信息
				customer =new CustomerInfo();
				customer.setEmail(rs.getString("email"));//客户邮箱
				customer.setId(rs.getInt("id"));
				//客户详细信息
				customerDatail = new CustomerDatailInfo();
				customerDatail.setName(rs.getString("name"));//客户名称
				customerDatail.setTelphone(rs.getString("telphone"));//固定电话
				customerDatail.setMobileTelphone(rs.getString("mobiletelephone"));//移动电话
				//获取客户详细信息
				customer.setCustomerDatail(customerDatail);
				//获取客户信息
				order.setCustomer(customer);
				//获取订单信息
				item.setOrderInfo(order);
			
				items.add(item);
			}
			
			pm = new PageModel<OrderGoodsInfo>();
			pm.setData(items);
			pm.setPageNo(pageNo);
			pm.setPageSize(pageSize);
			pm.setTotalRecords(getTotalRecords(conn , pstmt , rs));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	
	/**
	 * 获取总记录条数(所有订单总数)
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @param text
	 * @return
	 */
	private int getTotalRecords(Connection conn , PreparedStatement pstmt , ResultSet rs){
		String sql = " select count(1) from  orderinfo a  left join " +
				"customerinfo c on c.id = a.customerid ";
		int count = 0 ;
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 获取总记录条数(模糊查询订单总数)
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @param text
	 * @return
	 */
	private int getTotalRecords(Connection conn , PreparedStatement pstmt , ResultSet rs ,String text){
		String s = "";
		if(text != null && !"".equals(text)){
			s = " where c.email like '%"+text+"%' ";
		}
		
		String sql = " select count(1) from  orderinfo a  left join " +
				"customerinfo c on c.id = a.customerid "+s;
		int count = 0 ;
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 根据商品类型查询【单条】订单商品信息
	 * @param goodsid
	 * @return
	 */
	public OrderGoodsInfo select(int goodsid){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderGoodsInfo orderGods = null;
		String sql = "select a.goodsid,a.goodsname,a.price ,a.discount ,b.typename , a.remark , a.photo from goodsinfo a left join " +
				" goodstype b on a.typeid = b.typeid where a.goodsid = ?";
		try{
			
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, goodsid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				orderGods = new OrderGoodsInfo();
				
				GoodsInfo goodsinfo = new GoodsInfo();
				
				goodsinfo.setGoodsId(rs.getInt("goodsid"));
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				goodsinfo.setPrice(rs.getDouble("price"));
				goodsinfo.setDiscount(rs.getFloat("discount"));
				
				GoodsType goodstype = new GoodsType();
				goodstype.setTypeName(rs.getString("typename"));
				goodsinfo.setGoodstype(goodstype);
				
				goodsinfo.setRemark(rs.getString("remark"));
				goodsinfo.setPhoto(rs.getString("photo"));
				
				orderGods.setGoodsInfo(goodsinfo);
			}
			
		}catch(Exception e){
			
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return orderGods;
	}

	/**
	 * 添加订单信息
	 */
	public int insert (int customerid){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into orderinfo values (?,0,getdate()) ";
		int orderid = 0;//订单编号
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, customerid);
			//添加订单编号
			int i = pstmt.executeUpdate();
			
			if(i != 0){
				//@@identity全局变量表示获取刚插入的编号
				sql = "select  @@identity as 'orderid'";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					//获取刚添加的订单编号
					orderid = rs.getInt("orderid");
				}
			}
			
		}catch (Exception e) {
				// TODO: handle exception
		}finally{
				DBConn.closeDB(rs, pstmt, conn);
		}
		return orderid;
	}
	
	//添加订单商品信息
	public boolean addOrder(OrderInfo order){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		String sql = "insert into ordergoodsinfo values (? , ? , ?)";
		try{
			conn = DBConn.getConn();
			//获取订单编号
			int orderid =insert(order.getCustomer().getId());
			order.setOrderId(orderid);
			if(orderid != 0){
				
				for(OrderGoodsInfo goodsinfo : order.getOrderDatails()){
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, orderid);
					pstmt.setInt(2, goodsinfo.getGoodsInfo().getGoodsId());
					pstmt.setInt(3, goodsinfo.getQuantity());
					i = pstmt.executeUpdate();
				}
			}
			
			if(i != 0){
				return true;
			}
		}catch(Exception e){
			
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return false;
	}
	
	/**
	 * 查询指定订单有关的信息
	 */
	public ArrayList<OrderGoodsInfo> add(int customerid , int orderid ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderGoodsInfo ordergoods = null;
		OrderInfo orderinfo = null;
		GoodsInfo goodsinfo = null;
		ArrayList<OrderGoodsInfo> orderList =new ArrayList<OrderGoodsInfo>();
		
		String sql = "select * from ordergoodsinfo a  left join " +
				"orderinfo b on b.orderid = a.orderid left join " +
				"customerinfo c on c.id = b.customerid left join " +
				"goodsinfo e on a.goodsid = e.goodsid left join " +
				"goodstype f on e.typeid = f.typeid where c.id = ? and b.orderid = ? order by b.status,ordertime";
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, customerid);
			pstmt.setInt(2, orderid);
			
			rs= pstmt.executeQuery();
			
			
			while(rs.next()){
				
				ordergoods = new OrderGoodsInfo();
				ordergoods.setQuantity(rs.getInt("quantity"));//订单商品数量
				
				orderinfo = new OrderInfo();
				orderinfo.setOrderId(rs.getInt("orderid"));//订单编号
				orderinfo.setStatus(rs.getInt("status"));//订单状态
//				System.out.println(rs.getDate("ordertime"));
//				System.out.println(DateTimeUtil.convertDate(rs.getString("ordertime")));
				orderinfo.setOrderTime(DateTimeUtil.convertDate(rs.getString("ordertime")));
				//orderinfo.setOrderTime(rs.getDate("ordertime"));
				//订单商品信息
				ordergoods.setOrderInfo(orderinfo);
				
				goodsinfo = new GoodsInfo();
				goodsinfo.setGoodsId(rs.getInt("goodsid"));//商品编号
				goodsinfo.setGoodsName(rs.getString("goodsname"));//商品名称
				goodsinfo.setPrice(rs.getDouble("price"));//商品价格
				goodsinfo.setDiscount(rs.getFloat("discount"));//商品折扣
				goodsinfo.setPhoto(rs.getString("photo"));
				//商品类型信息
				goodsinfo.getGoodstype().setTypeName(rs.getString("typename"));//商品类型名称
				
				//商品信息
				ordergoods.setGoodsInfo(goodsinfo);
				
				orderList.add(ordergoods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return orderList;
	}
	/**
	 * 删除订单信息
	 */
	public boolean delete(int... args){
		
		System.out.println(args.length);
		System.out.println("删除");
		//String sql = "exec proc_orderdelete ?";
		String sql = " delete from ordergoodsinfo where orderid = ? delete from OrderInfo where orderId = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			int count=0;
			conn = DBConn.getConn();
			//pstmt = conn.prepareStatement(sql);
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < args.length; i++) {
				
				pstmt.setInt(1, args[i]);
				pstmt.setInt(2, args[i]);
				count += pstmt.executeUpdate();
			}
			
			
			if(count == args.length){
				return true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return false;
	}
	
	/**
	 * 修改订单信息状态
	 * @param status
	 * @param id
	 * @return
	 */
	public boolean update(int status ,int id){
		String sql = "update orderinfo set status = ? where orderid = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = DBConn.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, id);
			
			int i = pstmt.executeUpdate();
			if(i > 0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return false;
	}
	
	/**
	 * 删除过滤，即查询到信息不允许用户继续删除操作
	 */
	public boolean delteFilter(int... args)
	{
		System.out.println("查询是否有相关信息");
//		--只要其他一个编号的状态为1就不允许删除订单
//		select * from OrderInfo where status = 1 and (orderId =2 or orderId =3 or orderId =1)
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			String sql = "select * from OrderInfo where status = 0 ";
			for (int i = 0; i < args.length; i++) {
				if(i == 0)
				{
					sql += "and ( orderId ="+args[i];	
				}
				else{
					sql += " or orderId ="+args[i];	
				}
				if(i==args.length-1){
					sql += ")";
				}
			}
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			flag = rs.next();
			System.out.println(flag?"执行正常":"不正常");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
		return flag;
	}
	
	
	/*
	 * 根据客户编号查询所有订单有关的信息
	 */
	public ArrayList<OrderGoodsInfo> addGoods(int customerid ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderGoodsInfo ordergoods = null;
		OrderInfo orderinfo = null;
		GoodsInfo goodsinfo = null;
		ArrayList<OrderGoodsInfo> orderList =new ArrayList<OrderGoodsInfo>();
		String sql = "select * from ordergoodsinfo a  left join " +
				"orderinfo b on b.orderid = a.orderid left join " +
				"customerinfo c on c.id = b.customerid left join " +
				"goodsinfo e on a.goodsid = e.goodsid left join  " +
				"goodstype f on e.typeid = f.typeid where c.id = ? order by b.status,a.orderid";
	
	
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, customerid);
			rs= pstmt.executeQuery();
			
			
			while(rs.next()){
				
				ordergoods = new OrderGoodsInfo();
				ordergoods.setQuantity(rs.getInt("quantity"));//订单商品数量
				
				orderinfo = new OrderInfo();
				orderinfo.setOrderId(rs.getInt("orderid"));//订单编号
				orderinfo.setStatus(rs.getInt("status"));//订单状态
				orderinfo.setOrderTime(DateTimeUtil.convertDate(rs.getString("ordertime")));//订单时间
				
				
				//订单商品信息
				ordergoods.setOrderInfo(orderinfo);
				
				goodsinfo = new GoodsInfo();
				goodsinfo.setGoodsId(rs.getInt("goodsid"));//商品编号
				goodsinfo.setGoodsName(rs.getString("goodsname"));//商品名称
				goodsinfo.setPrice(rs.getDouble("price"));//商品价格
				goodsinfo.setDiscount(rs.getFloat("discount"));//商品折扣
				goodsinfo.setPhoto(rs.getString("photo"));
			
				//商品类型信息
				goodsinfo.getGoodstype().setTypeName(rs.getString("typename"));//商品类型名称
				goodsinfo.getGoodstype().setTypeId(rs.getInt("typeid"));
				//商品信息
				ordergoods.setGoodsInfo(goodsinfo);
				
				orderList.add(ordergoods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return orderList;
	}
	
	//添加留言信息
	public boolean toAdd(int orderid ,int customerid , String[] goodsid , String content , int reviewStatus){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		String sql = "insert into Review values (? , ? , ?,getdate(),?,?)";
		
		try{
			conn = DBConn.getConn();
			//获取订单编号
				for(int j = 0 ; j < goodsid.length ; j++){
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, customerid);
					pstmt.setInt(2, Integer.parseInt(goodsid[j]));
					pstmt.setInt(3, orderid);
					pstmt.setInt(4, reviewStatus);
					pstmt.setString(5,content);
					i = pstmt.executeUpdate();
				}
			if(i != 0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return false;
	}
	
	
	public ArrayList<Review> review(int goodsid ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerInfo customer = null;
		Review review = null;
		ArrayList<Review> reviewList =new ArrayList<Review>();
		String sql = "select * from Review a left join customerinfo b on a.customerId = b.id where a.goodid= ?";
	
	
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, goodsid);
			rs= pstmt.executeQuery();
			
			
			while(rs.next()){
				review = new Review();
				review.setReviewTime(DateTimeUtil.convertDate(rs.getString("reviewTime")));
				review.setReviewContent(rs.getString("reviewContent"));
				review.setReviewStatus(rs.getInt("reviewStatus"));
				
				customer = new CustomerInfo();
				
				customer.setEmail(rs.getString("email"));
				//客户信息
				review.setCustoomer(customer);
				
				reviewList.add(review);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return reviewList;
	}
}





