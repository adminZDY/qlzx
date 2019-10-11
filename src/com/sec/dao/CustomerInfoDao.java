package com.sec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.Flags.Flag;

import com.sec.model.CustomerDatailInfo;
import com.sec.model.CustomerInfo;
import com.sec.model.OrderInfo;
import com.sec.util.DBConn;
import com.sec.util.DateTimeUtil;
import com.sec.util.PageModel;
import com.sec.util.StringUtil;
import com.sun.org.apache.regexp.internal.recompile;

public class CustomerInfoDao  {
	/**
	 * 提示是否注册成功（客户注册）
	 * @param CustomerInfo 客户对象
	 * @return 是否注册成功（boolean值）
	 */
	public boolean addUserInfo(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("insert into CustomerInfo(email,pwd) values(?,?)");
			//System.out.println("insert into CustomerInfo(email,pwd) values('"+customer.getEmail()+"','"+customer.getPwd()+"')");
			//设置参数列表
			pstmt.setString(1,customer.getEmail().trim());
			pstmt.setString(2,customer.getPwd().trim());
			//获得结果集
			int i = pstmt.executeUpdate();
			if(i > 0)
			{
				//注册时，填写地址
				if(StringUtil.isNullOrEmpty(customer.getCustomerDatail().getName()) == false)
				{
					//System.out.println(StringUtil.isNullOrEmpty(customer.getCustomerDatail().getName()));
					//获取标识列的值
					pstmt = con.prepareStatement("select @@IDENTITY as 'id'");
					rs = pstmt.executeQuery();
					//System.out.println("详细");
					if(rs.next())
					{
						System.out.println(rs.getInt("id"));
						flag = insertDetail(rs.getInt("id"),customer,con,pstmt);
						
					}
				}
				//注册时，无填写地址
				else {	
					flag = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * 初始化详细地址，在注册时顺带填写地址【填写详细地址时使用的语句】
	 * @param id 客户编号
	 * @param customer 
	 * @param con 
	 * @param pstmt
	 * @return 插入是否成功
	 * @throws SQLException 
	 */
	public boolean insertDetail(int id,CustomerInfo customer,Connection con,PreparedStatement pstmt) throws SQLException
	{
		CustomerDatailInfo customerDatail = customer.getCustomerDatail();
		
		String telphone = customerDatail.getTelphone();
		if(telphone != null)
		{
			telphone = "'"+telphone+"'";
		}
		
		//insert into CustomerDetailInfo values(编号，'收获人','固定电话','移动电话','收获地址');
		pstmt = con.prepareStatement("insert into CustomerDetailInfo values(?,?,"+telphone+
				","+customerDatail.getMobileTelphone()+",?)");
		
		System.out.println("注册时填写地址");
		System.out.println(String.format("insert into CustomerDetailInfo values(%d,%s,%s,%s,%s)",id,customerDatail.getName().trim()
				, telphone,customerDatail.getMobileTelphone(), customerDatail.getAddress().trim()));
		pstmt.setInt(1, id);
		//名称
		pstmt.setString(2, customerDatail.getName().trim());
		//固定电话
		//电话
		//地址
		pstmt.setString(3, customerDatail.getAddress().trim());
		int i = pstmt.executeUpdate();
		if(i > 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 初始化详细地址，填写地址【填写详细地址时使用的语句】
	 * @param id 客户编号
	 * @param customer 
	 * @param con 
	 * @param pstmt
	 * @return 插入是否成功
	 * @throws SQLException 
	 */
	public boolean insertDetail(int id,CustomerDatailInfo customerDatail)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			String telphone = customerDatail.getTelphone();
			if(telphone != null)
			{
				telphone = "'"+telphone+"'";
			}
			
			con = DBConn.getConn();
			//insert into CustomerDetailInfo values(编号，'收获人','固定电话','移动电话','收获地址');
			pstmt = con.prepareStatement("insert into CustomerDetailInfo values(?,?," +
					""+telphone+","+customerDatail.getMobileTelphone()+",?)");
			System.out.println("注册后，填写地址");
			System.out.println(String.format("insert into CustomerDetailInfo values(%d,%s,%s,%s,%s)",id,customerDatail.getName()
					, customerDatail.getTelphone(),customerDatail.getMobileTelphone(), customerDatail.getAddress()));
			pstmt.setInt(1, id);
			//名称
			pstmt.setString(2, customerDatail.getName().trim());
			//地址
			pstmt.setString(3, customerDatail.getAddress().trim());
			int i = pstmt.executeUpdate();
			if(i > 0)
			{
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * 修改收货地址
	 * @param CustomerInfo 客户信息类
	 * @param CustomerDatailInfo 
	 * @return 是否修改成功（boolean值）
	 */
	public boolean updateDetail(CustomerDatailInfo customerDatail)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			String telphone = customerDatail.getTelphone();
			if(telphone != null)
			{
				telphone = "'"+telphone+"'";
			}
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update CustomerDetailInfo set name = ?,telphone = "+telphone+"," +
					"mobileTelephone = "+customerDatail.getMobileTelphone().trim()+",address = ?" +
					" where customerId = ?");
			//设置参数列表
			pstmt.setString(1, customerDatail.getName().trim());
			pstmt.setString(2, customerDatail.getAddress().trim());
			pstmt.setInt(3, customerDatail.getCustomerId());
			int i = pstmt.executeUpdate();
			if(i > 0)
			{
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * (用户登录)
	 * 登录成功，初始化客户信息包括地址
	 * @param CustomerInfo 客户对象
	 * @return 是否登录成功（boolean值）
	 */
	public boolean CustomerSelect(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo a left join CustomerDetailInfo b on a.id=b.customerId" +
					" where email = ? and pwd =?");
			//System.out.println("select * from CustomerInfo a left join CustomerDetailInfo b on a.id=b.customerId" +
			//		" where email = "+customer.getEmail()+" and pwd = "+customer.getPwd());
			//设置参数列表
			pstmt.setString(1,customer.getEmail().trim());
			pstmt.setString(2,customer.getPwd().trim());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			if(flag = rs.next())
			{
				//结果集赋值
				customer.setId(rs.getInt("id"));
				customer.setEmail(customer.getEmail());
				customer.setPwd(customer.getPwd());
				customer.setRegisterTime(rs.getDate("registerTime"));
					
				//名称不为空，则初始化地址，以防出现null异常
				if(rs.getString("name") != null)
				{
					CustomerDatailInfo customerDatail = new CustomerDatailInfo();
					customerDatail.setCustomerId(rs.getInt("customerid"));
					customerDatail.setName(rs.getString("name"));
					customerDatail.setMobileTelphone(rs.getString("mobileTelephone"));
					customerDatail.setTelphone(rs.getString("telphone"));
					customerDatail.setAddress(rs.getString("address"));
					customer.setCustomerDatail(customerDatail);
				}
			}
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
	
	/**
	 * 客户修改查询数据（前台）
	 * @param customer 初始化好id
	 * @return
	 */
	public void selectCustomer(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo a left join CustomerDetailInfo b on a.id=b.customerId" +
					" where id = ?");
			//设置参数列表
			pstmt.setInt(1,customer.getId());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			if(rs.next())
			{
				//结果集赋值
				customer.setId(rs.getInt("id"));
				customer.setEmail(rs.getString("email"));
				customer.setPwd(rs.getString("pwd"));
				customer.setRegisterTime(rs.getDate("registerTime"));
					
				//名称不为空，则初始化地址，以防出现null异常
				if(rs.getString("name") != null)
				{
					CustomerDatailInfo customerDatail = new CustomerDatailInfo();
					customerDatail.setCustomerId(rs.getInt("customerid"));
					customerDatail.setName(rs.getString("name"));
					customerDatail.setMobileTelphone(rs.getString("mobileTelephone"));
					customerDatail.setTelphone(rs.getString("telphone"));
					customerDatail.setAddress(rs.getString("address"));
					customer.setCustomerDatail(customerDatail);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
	}
	
	/**
	 * 查询用户是否已经被使用(修改用户名密码)【前台功能】
	 * @param customerInfo 
	 * @return
	 */
	@Deprecated
	public boolean IfExistsCustomerInfo(CustomerInfo customerInfo)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select  * from CustomerInfo where id != ? and email = ? ");
			//设置参数列表
			pstmt.setInt(1,customerInfo.getId());
			pstmt.setString(2, customerInfo.getEmail().trim());
			
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			flag = rs.next();
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
	
	/**
	 * [ajax]查询所有用户是否已经存在该邮箱(注册)
	 * @param customerInfo 
	 * @return
	 */
	public boolean IfExistsEmail(CustomerInfo customerInfo)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo where email = ? ");
			//设置参数列表
			pstmt.setString(1, customerInfo.getEmail().trim());
			
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			flag = rs.next();
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
	
	/**
	 * 用户信息分页查询(后台)
	 * @param pm
	 */
	public void selectPage(PageModel<CustomerInfo> pm)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//打开连接
			con = DBConn.getConn();
			//查询有填写地址的客户（分页）
			//pstmt = con.prepareStatement("select top "+pm.getPageSize()+" * from  CustomerDetailInfo a left join  CustomerInfo b on b.id=a.customerId where a.customerId not in " +
			//		"(select top  "+pm.getPageSize()*(pm.getPageNo()-1)+" customerId from  CustomerDetailInfo a left join  CustomerInfo b on b.id=a.customerId order by registerTime desc) " +
			//				"order by registerTime desc");
			
			
			pstmt = con.prepareStatement("select  top "+pm.getPageSize()+" * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid " +
			" where a.id not in (select top "+pm.getPageSize()*(pm.getPageNo()-1)+" id from customerinfo order by id,registerTime desc) " +
			" order by a.id,a.registerTime desc");
			//查询信息
//			pstmt = con.prepareStatement("select top ? * from CustomerInfo where id not in " +
//					"(select  top ? id from CustomerInfo order by registerTime desc) order by registerTime desc");
			//设置参数列表
//			pstmt.setInt(1, 3);
//			pstmt.setInt(2, 1);
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			while (rs.next()) {
				//封装数据
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setEmail(rs.getString("email"));
				customerInfo.setPwd(rs.getString("pwd"));
				customerInfo.setId(rs.getInt("id"));
				customerInfo.setRegisterTime(DateTimeUtil.convertDate(rs.getString("registerTime")));
				
				CustomerDatailInfo customerDatail = new CustomerDatailInfo();
				customerDatail.setCustomerId(rs.getInt("customerid"));
				customerDatail.setName(rs.getString("name"));
				customerDatail.setMobileTelphone(rs.getString("mobileTelephone"));
				customerDatail.setTelphone(rs.getString("telphone"));
				customerDatail.setAddress(rs.getString("address"));
				customerInfo.setCustomerDatail(customerDatail);
				pm.getData().add(customerInfo);
			}
			
			//统计查询记录数
			pstmt = con.prepareStatement("select count(*) as 'num' from CustomerInfo");
			//获得结果集
			rs = pstmt.executeQuery();
			rs.next();
			pm.setTotalRecords(rs.getInt("num"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
	}
	
	/**
	 * 用户信息分页查询(模糊查询)【后台】
	 */
	public boolean selectCasePage(PageModel<CustomerInfo> pm,String selectText)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		
		String sqlAppend = "where email like ";
		
		sqlAppend += " '%"+selectText+"%'";
		
		String sql ="select top ? * from CustomerInfo a left join CustomerDetailInfo b on a.Id = b.customerId" +
					" where a.Id not in(select  top ? customerId from CustomerInfo " +
					" ? order by registerTime desc) " +
					" ? order by registerTime desc";
//		sql = "select top "+pm.getPageSize()+" * from  CustomerDetailInfo a left join  CustomerInfo b " +
//				"on b.id=a.customerId "+sqlAppend+" and a.customerId not in " +
//				"(select top "+pm.getPageSize()*(pm.getPageNo()-1)+" customerId from  CustomerDetailInfo a left join  CustomerInfo b " +
//				"on b.id=a.customerId "+sqlAppend+" order by registerTime desc) order by registerTime desc";
		sql = "select  top "+pm.getPageSize()+" * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid "+sqlAppend+
			   " and a.id not in (select top "+pm.getPageSize()*(pm.getPageNo()-1)+" id from customerinfo " +sqlAppend+
			   "  order by registerTime desc) order by registerTime desc";

		System.out.println(sql);
		
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement(sql);
			//设置参数列表
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			while (rs.next()) {
				//封装数据
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setEmail(rs.getString("email"));
				customerInfo.setPwd(rs.getString("pwd"));
				customerInfo.setId(rs.getInt("id"));
				customerInfo.setRegisterTime(DateTimeUtil.convertDate(rs.getString("registerTime")));
				
				CustomerDatailInfo customerDatail = new CustomerDatailInfo();
				customerDatail.setCustomerId(rs.getInt("customerid"));
				customerDatail.setName(rs.getString("name"));
				customerDatail.setMobileTelphone(rs.getString("mobileTelephone"));
				customerDatail.setTelphone(rs.getString("telphone"));
				customerDatail.setAddress(rs.getString("address"));
				customerInfo.setCustomerDatail(customerDatail);
				pm.getData().add(customerInfo);
			}
			
			sql = "select  COUNT(*) as 'num' from customerinfo a " +
				  "left join CustomerDetailInfo b on a.id = b.customerid "+sqlAppend;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				pm.setTotalRecords(rs.getInt("num"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * 查询客户是否有地址(初始化地址，需提供一个初始化号用户信息的客户类【必须要有编号ID】)
	 * @param customer 客户类
	 * @return 是否填写地址
	 */
	public boolean selectAddress(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo a left join CustomerDetailInfo b " +
					"on a.Id = b.customerId where b.customerId = ?");
			//设置参数列表
			pstmt.setInt(1,customer.getId());
			
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			if(flag =rs.next()) {
				//封装数据
				CustomerDatailInfo customerDatail = customer.getCustomerDatail();
				customerDatail.setCustomerId(customer.getId());
				customerDatail.setMobileTelphone(rs.getString("mobileTelephone"));
				customerDatail.setTelphone(rs.getString("telphone"));
				customerDatail.setName(rs.getString("name"));
				customerDatail.setAddress(rs.getString("address"));
			}
			
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
	
	/**
	 * 根据编号删除单个客户编号(删除单)【后台】
	 * @param id
	 * @return
	 */
	public boolean deletecustomer(int id)
	{
		System.out.println("执行删除");
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
//			delete from CustomerDetailInfo where customerId = 1
//			delete from CustomerInfo where id = 1
			pstmt = con.prepareStatement("delete from CustomerInfo where Id = ?");
			pstmt.setInt(1, id);
			int i = pstmt.executeUpdate();
			System.out.println(i);
			if(i > 0)
			{
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * 根据客户编号删除多个客户编号(删除多)【后台】
	 * @param id
	 * @return
	 */
	public boolean deletecustomers(int[] id)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			String sql = "delete from CustomerInfo where id = ?";
			if(id.length != 0)
			{
				for (int i = 0; i < id.length; i++) {
					System.out.println("delete from CustomerInfo where id = "+id[i]);
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, id[i]);
					count += pstmt.executeUpdate();
				}
			}
			if(count == id.length && id.length != 0)
			{
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * 根据客户查询客户订单信息(订单信息)【前台】
	 * @param CustomerInfo 客户对象
	 * @return 是否登录成功（boolean值）
	 */
	public OrderInfo Order_Customer(int customerid,int orderId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderInfo order = null;
		CustomerInfo customer = null;
	
		try {
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from orderinfo c left join customerinfo a on c.customerid = a.id left join CustomerDetailInfo b on a.id = b.customerid  where id = ? and orderid = ?");
		
			//设置参数列表
			pstmt.setInt(1,customerid);
			pstmt.setInt(2,orderId);
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			if(rs.next())
			{
				order = new OrderInfo();
				
				customer = new CustomerInfo();
				//结果集赋值
				customer.setId(rs.getInt("id"));
				customer.setEmail(rs.getString("email"));
				customer.setRegisterTime(rs.getDate("registerTime"));
				
				CustomerDatailInfo customerDatail = new CustomerDatailInfo();
				customerDatail.setCustomerId(rs.getInt("customerid"));
				customerDatail.setName(rs.getString("name"));
				customerDatail.setMobileTelphone(rs.getString("mobileTelephone"));
				customerDatail.setTelphone(rs.getString("telphone"));
				customerDatail.setAddress(rs.getString("address"));
				
				customer.setCustomerDatail(customerDatail);
				
				order.setCustomer(customer);
				
				order.setOrderId(rs.getInt("orderid"));//订单编号
				order.setStatus(rs.getInt("status"));//订单状态
				order.setOrderTime(DateTimeUtil.convertDate(rs.getString("ordertime")));//订单时间
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(rs, pstmt, con);
		}
		return order;
	}
	
	/**
	 * 删除过滤，即查询到信息不允许用户继续删除操作
	 */
	public boolean delteFilter(int... args)
	{
		System.out.println("查询是否有相关信息");
//		--只有其中有一个有信息在订单中出现就不执行删除（客户信息）
//		select * from OrderInfo where customerId in (1)
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//打开连接
			con = DBConn.getConn();
			String sql = "select * from OrderInfo where ";
			for (int i = 0; i < args.length; i++) {
				if(i == 0)
				{
					sql += " customerId ="+args[i];	
				}
				else{
					sql += " or customerId ="+args[i];	
				}
			}
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			flag = rs.next();
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
	
	
	public boolean updateData(CustomerInfo customer,CustomerDatailInfo customerDatail)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			String telphone = customerDatail.getTelphone();
			if(telphone != null)
			{
				telphone = "'"+telphone+"'";
			}
			//打开连接
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update CustomerDetailInfo set name = ?,telphone = "+telphone
					+",mobiletelephone = "+customerDatail.getMobileTelphone()+",address = ? " +
					" where customerid = ?");
			System.out.println("update CustomerDetailInfo set name = "+customerDatail.getName()+",telphone = "+telphone+"," +
					" mobiletelephone = "+customerDatail.getMobileTelphone()+",address =  "+customerDatail.getAddress()+"where customerid = "+customer.getId()+"");
			//设置参数列表
			pstmt.setString(1, customerDatail.getName());
			pstmt.setString(2, customerDatail.getAddress());
			pstmt.setInt(3, customer.getId());
			int i = pstmt.executeUpdate();
			if(i > 0)
			{
				flag = true;
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DBConn.closeDB(null, pstmt, con);
		}
		return flag;
	}
	
/**
 * 修改用户密码	
 * @param pwd
 * @param id
 * @return
 */
public boolean updateUserPwd(String pwd ,int id){
		
		Connection conn  =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "update customerinfo set pwd = ? where id = ?";
		try{
			
			conn = DBConn.getConn();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setInt(2, id);
			
			int i = pstmt.executeUpdate();
			
			if(i != 0){
				return true;
			}
		}catch(Exception e){
			
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		
		return false;
	}
}



