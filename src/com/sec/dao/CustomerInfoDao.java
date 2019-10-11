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
	 * ��ʾ�Ƿ�ע��ɹ����ͻ�ע�ᣩ
	 * @param CustomerInfo �ͻ�����
	 * @return �Ƿ�ע��ɹ���booleanֵ��
	 */
	public boolean addUserInfo(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("insert into CustomerInfo(email,pwd) values(?,?)");
			//System.out.println("insert into CustomerInfo(email,pwd) values('"+customer.getEmail()+"','"+customer.getPwd()+"')");
			//���ò����б�
			pstmt.setString(1,customer.getEmail().trim());
			pstmt.setString(2,customer.getPwd().trim());
			//��ý����
			int i = pstmt.executeUpdate();
			if(i > 0)
			{
				//ע��ʱ����д��ַ
				if(StringUtil.isNullOrEmpty(customer.getCustomerDatail().getName()) == false)
				{
					//System.out.println(StringUtil.isNullOrEmpty(customer.getCustomerDatail().getName()));
					//��ȡ��ʶ�е�ֵ
					pstmt = con.prepareStatement("select @@IDENTITY as 'id'");
					rs = pstmt.executeQuery();
					//System.out.println("��ϸ");
					if(rs.next())
					{
						System.out.println(rs.getInt("id"));
						flag = insertDetail(rs.getInt("id"),customer,con,pstmt);
						
					}
				}
				//ע��ʱ������д��ַ
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
	 * ��ʼ����ϸ��ַ����ע��ʱ˳����д��ַ����д��ϸ��ַʱʹ�õ���䡿
	 * @param id �ͻ����
	 * @param customer 
	 * @param con 
	 * @param pstmt
	 * @return �����Ƿ�ɹ�
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
		
		//insert into CustomerDetailInfo values(��ţ�'�ջ���','�̶��绰','�ƶ��绰','�ջ��ַ');
		pstmt = con.prepareStatement("insert into CustomerDetailInfo values(?,?,"+telphone+
				","+customerDatail.getMobileTelphone()+",?)");
		
		System.out.println("ע��ʱ��д��ַ");
		System.out.println(String.format("insert into CustomerDetailInfo values(%d,%s,%s,%s,%s)",id,customerDatail.getName().trim()
				, telphone,customerDatail.getMobileTelphone(), customerDatail.getAddress().trim()));
		pstmt.setInt(1, id);
		//����
		pstmt.setString(2, customerDatail.getName().trim());
		//�̶��绰
		//�绰
		//��ַ
		pstmt.setString(3, customerDatail.getAddress().trim());
		int i = pstmt.executeUpdate();
		if(i > 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * ��ʼ����ϸ��ַ����д��ַ����д��ϸ��ַʱʹ�õ���䡿
	 * @param id �ͻ����
	 * @param customer 
	 * @param con 
	 * @param pstmt
	 * @return �����Ƿ�ɹ�
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
			//insert into CustomerDetailInfo values(��ţ�'�ջ���','�̶��绰','�ƶ��绰','�ջ��ַ');
			pstmt = con.prepareStatement("insert into CustomerDetailInfo values(?,?," +
					""+telphone+","+customerDatail.getMobileTelphone()+",?)");
			System.out.println("ע�����д��ַ");
			System.out.println(String.format("insert into CustomerDetailInfo values(%d,%s,%s,%s,%s)",id,customerDatail.getName()
					, customerDatail.getTelphone(),customerDatail.getMobileTelphone(), customerDatail.getAddress()));
			pstmt.setInt(1, id);
			//����
			pstmt.setString(2, customerDatail.getName().trim());
			//��ַ
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
	 * �޸��ջ���ַ
	 * @param CustomerInfo �ͻ���Ϣ��
	 * @param CustomerDatailInfo 
	 * @return �Ƿ��޸ĳɹ���booleanֵ��
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
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update CustomerDetailInfo set name = ?,telphone = "+telphone+"," +
					"mobileTelephone = "+customerDatail.getMobileTelphone().trim()+",address = ?" +
					" where customerId = ?");
			//���ò����б�
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
	 * (�û���¼)
	 * ��¼�ɹ�����ʼ���ͻ���Ϣ������ַ
	 * @param CustomerInfo �ͻ�����
	 * @return �Ƿ��¼�ɹ���booleanֵ��
	 */
	public boolean CustomerSelect(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo a left join CustomerDetailInfo b on a.id=b.customerId" +
					" where email = ? and pwd =?");
			//System.out.println("select * from CustomerInfo a left join CustomerDetailInfo b on a.id=b.customerId" +
			//		" where email = "+customer.getEmail()+" and pwd = "+customer.getPwd());
			//���ò����б�
			pstmt.setString(1,customer.getEmail().trim());
			pstmt.setString(2,customer.getPwd().trim());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			if(flag = rs.next())
			{
				//�������ֵ
				customer.setId(rs.getInt("id"));
				customer.setEmail(customer.getEmail());
				customer.setPwd(customer.getPwd());
				customer.setRegisterTime(rs.getDate("registerTime"));
					
				//���Ʋ�Ϊ�գ����ʼ����ַ���Է�����null�쳣
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
	 * �ͻ��޸Ĳ�ѯ���ݣ�ǰ̨��
	 * @param customer ��ʼ����id
	 * @return
	 */
	public void selectCustomer(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo a left join CustomerDetailInfo b on a.id=b.customerId" +
					" where id = ?");
			//���ò����б�
			pstmt.setInt(1,customer.getId());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			if(rs.next())
			{
				//�������ֵ
				customer.setId(rs.getInt("id"));
				customer.setEmail(rs.getString("email"));
				customer.setPwd(rs.getString("pwd"));
				customer.setRegisterTime(rs.getDate("registerTime"));
					
				//���Ʋ�Ϊ�գ����ʼ����ַ���Է�����null�쳣
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
	 * ��ѯ�û��Ƿ��Ѿ���ʹ��(�޸��û�������)��ǰ̨���ܡ�
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
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select  * from CustomerInfo where id != ? and email = ? ");
			//���ò����б�
			pstmt.setInt(1,customerInfo.getId());
			pstmt.setString(2, customerInfo.getEmail().trim());
			
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
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
	 * [ajax]��ѯ�����û��Ƿ��Ѿ����ڸ�����(ע��)
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
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo where email = ? ");
			//���ò����б�
			pstmt.setString(1, customerInfo.getEmail().trim());
			
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
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
	 * �û���Ϣ��ҳ��ѯ(��̨)
	 * @param pm
	 */
	public void selectPage(PageModel<CustomerInfo> pm)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//������
			con = DBConn.getConn();
			//��ѯ����д��ַ�Ŀͻ�����ҳ��
			//pstmt = con.prepareStatement("select top "+pm.getPageSize()+" * from  CustomerDetailInfo a left join  CustomerInfo b on b.id=a.customerId where a.customerId not in " +
			//		"(select top  "+pm.getPageSize()*(pm.getPageNo()-1)+" customerId from  CustomerDetailInfo a left join  CustomerInfo b on b.id=a.customerId order by registerTime desc) " +
			//				"order by registerTime desc");
			
			
			pstmt = con.prepareStatement("select  top "+pm.getPageSize()+" * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid " +
			" where a.id not in (select top "+pm.getPageSize()*(pm.getPageNo()-1)+" id from customerinfo order by id,registerTime desc) " +
			" order by a.id,a.registerTime desc");
			//��ѯ��Ϣ
//			pstmt = con.prepareStatement("select top ? * from CustomerInfo where id not in " +
//					"(select  top ? id from CustomerInfo order by registerTime desc) order by registerTime desc");
			//���ò����б�
//			pstmt.setInt(1, 3);
//			pstmt.setInt(2, 1);
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			while (rs.next()) {
				//��װ����
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
			
			//ͳ�Ʋ�ѯ��¼��
			pstmt = con.prepareStatement("select count(*) as 'num' from CustomerInfo");
			//��ý����
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
	 * �û���Ϣ��ҳ��ѯ(ģ����ѯ)����̨��
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
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement(sql);
			//���ò����б�
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			while (rs.next()) {
				//��װ����
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
	 * ��ѯ�ͻ��Ƿ��е�ַ(��ʼ����ַ�����ṩһ����ʼ�����û���Ϣ�Ŀͻ��ࡾ����Ҫ�б��ID��)
	 * @param customer �ͻ���
	 * @return �Ƿ���д��ַ
	 */
	public boolean selectAddress(CustomerInfo customer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from CustomerInfo a left join CustomerDetailInfo b " +
					"on a.Id = b.customerId where b.customerId = ?");
			//���ò����б�
			pstmt.setInt(1,customer.getId());
			
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			if(flag =rs.next()) {
				//��װ����
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
	 * ���ݱ��ɾ�������ͻ����(ɾ����)����̨��
	 * @param id
	 * @return
	 */
	public boolean deletecustomer(int id)
	{
		System.out.println("ִ��ɾ��");
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			//������
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
	 * ���ݿͻ����ɾ������ͻ����(ɾ����)����̨��
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
			//������
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
	 * ���ݿͻ���ѯ�ͻ�������Ϣ(������Ϣ)��ǰ̨��
	 * @param CustomerInfo �ͻ�����
	 * @return �Ƿ��¼�ɹ���booleanֵ��
	 */
	public OrderInfo Order_Customer(int customerid,int orderId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderInfo order = null;
		CustomerInfo customer = null;
	
		try {
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("select * from orderinfo c left join customerinfo a on c.customerid = a.id left join CustomerDetailInfo b on a.id = b.customerid  where id = ? and orderid = ?");
		
			//���ò����б�
			pstmt.setInt(1,customerid);
			pstmt.setInt(2,orderId);
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			if(rs.next())
			{
				order = new OrderInfo();
				
				customer = new CustomerInfo();
				//�������ֵ
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
				
				order.setOrderId(rs.getInt("orderid"));//�������
				order.setStatus(rs.getInt("status"));//����״̬
				order.setOrderTime(DateTimeUtil.convertDate(rs.getString("ordertime")));//����ʱ��
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
	 * ɾ�����ˣ�����ѯ����Ϣ�������û�����ɾ������
	 */
	public boolean delteFilter(int... args)
	{
		System.out.println("��ѯ�Ƿ��������Ϣ");
//		--ֻ��������һ������Ϣ�ڶ����г��־Ͳ�ִ��ɾ�����ͻ���Ϣ��
//		select * from OrderInfo where customerId in (1)
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//������
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
			//������
			con = DBConn.getConn();
			pstmt = con.prepareStatement("update CustomerDetailInfo set name = ?,telphone = "+telphone
					+",mobiletelephone = "+customerDatail.getMobileTelphone()+",address = ? " +
					" where customerid = ?");
			System.out.println("update CustomerDetailInfo set name = "+customerDatail.getName()+",telphone = "+telphone+"," +
					" mobiletelephone = "+customerDatail.getMobileTelphone()+",address =  "+customerDatail.getAddress()+"where customerid = "+customer.getId()+"");
			//���ò����б�
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
 * �޸��û�����	
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



