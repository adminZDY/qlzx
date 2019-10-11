package com.sec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.sec.model.GoodsType;
import com.sec.util.DBConn;
import com.sec.util.PageModel;

public class GoodsTypeDao {
	
	
	/**
	 * ��ѯ��������
	 * @return
	 */
	public ArrayList<GoodsType> GoodsTypeList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from goodstype";
		ArrayList<GoodsType> goodstypeList = new ArrayList<GoodsType>(); 
		GoodsType type = null;
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				type = new GoodsType();
				type.setTypeId(rs.getInt(1));
				type.setTypeName(rs.getString(2));
				goodstypeList.add(type);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return goodstypeList;
	}
	
	
	/**
	 * ģ����ѯ���͡���ҳ
	 * @param pageSize ҳ��������
	 * @param pageNo ҳ��
	 * @param text ģ����ѯ�ı�
	 * @return
	 */
	public PageModel<GoodsType> getAllGoodsType(int pageSize,int pageNo,String text){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "";
		//ģ����ѯ
		sql = "select * from goodstype where typename like '%"+text+"%'";

		ArrayList<GoodsType> items = new ArrayList<GoodsType>();
		PageModel<GoodsType> pm = new PageModel<GoodsType>();
		
		try {
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			//ִ�в�ѯ
			rs = pstmt.executeQuery();
			
			GoodsType item = null;
				
			//ѭ����ȡ����
			while(rs.next()){
				item = new GoodsType();
				item.setTypeId(rs.getInt("typeId"));
				item.setTypeName(rs.getString("typeName"));
				//��ӵ�����
				items.add(item);
			}
			//���÷�ҳ
			pm.setData(items);//���õ�ǰҳ������
			pm.setTotalRecords(getCountGoods(conn,pstmt,rs,text));//�����ܼ�¼��
			pm.setPageSize(pageSize);//ÿҳ��ʾ������
			pm.setPageNo(pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	
	/**
	 * ��ȡ���е���Ʒ���͡���ҳ
	 * @param pageSize ҳ��������
	 * @param pageNo ҳ��
	 * @return
	 */
	public PageModel<GoodsType> getAllGoodsType(int pageSize,int pageNo){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		sql = "select top "+pageSize+" * from GoodsType where typeid not in(select top "+(pageNo-1) * pageSize+" typeid from GoodsType)";
		System.out.println("1"+sql);
		ArrayList<GoodsType> items = new ArrayList<GoodsType>();
		PageModel<GoodsType> pm = new PageModel<GoodsType>();
		
		try {
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			//ִ�в�ѯ
			rs = pstmt.executeQuery();
			
			GoodsType item = null;
			
			//ѭ����ȡ����
			while(rs.next()){
				item = new GoodsType();
				item.setTypeId(rs.getInt("typeId"));
				item.setTypeName(rs.getString("typeName"));
				//��ӵ�����
				items.add(item);
			}
			//���÷�ҳ
			pm.setData(items);//���õ�ǰҳ������
			pm.setTotalRecords(getCountGoods(conn,pstmt,rs));//�����ܼ�¼��
			pm.setPageSize(pageSize);//ÿҳ��ʾ������
			pm.setPageNo(pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	
	/**
	 * ��ѯ�ܵļ�¼��(���в�ѯ�ܼ�¼��)
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @return
	 */
	private int getCountGoods(Connection conn, PreparedStatement pstmt,
			ResultSet rs) throws Exception {
		int count =0;
		String sql ="";
		sql = "select count(*) as 'count' from GoodsType ";
		System.out.println(sql);
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
		System.out.println(count);
		return count;
	}
	
	/**
	 * ��ѯ�ܵļ�¼��(ģ����ѯ�ܼ�¼��)
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @return
	 */
	private int getCountGoods(Connection conn, PreparedStatement pstmt,
			ResultSet rs,String text) throws Exception {
		int count =0;
		String sql ="";
		sql = "select count(*) as 'count' from GoodsType where typeName like '%"+text+"%'";
		System.out.println(sql);
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
		System.out.println(count);
		return count;
	}
	
	/**
	 * �����Ʒ���͵ķ���
	 */
	public boolean insert(GoodsType type){
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement("insert into goodstype values(?)");
			pstmt.setString(1,type.getTypeName());
			
			int rowcount = pstmt.executeUpdate();
			if(rowcount>0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.closeDB(null, pstmt, conn);
		}
		return flag;
	}

	/**
	 * �������ͱ���޸���������
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean update(int id,String name){
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement("update GoodsType set typeName = ? where typeId=?");
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			int rowcount = 0;
			rowcount = pstmt.executeUpdate();
			if(rowcount>0){
				flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}

	/**����id�Ų�ѯ(��ת���޸�ҳ��Ҫ�õ�)
	 * @param id
	 * @return
	 */
	public GoodsType selectid(int id){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "";
		GoodsType goodsType = null;
			
		sql = "select * from goodstype where typeid = "+id;
		//System.out.println(sql);
		try {
			goodsType = new GoodsType();
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getInt("typeid")+"  "+rs.getString("typeName"));
				goodsType.setTypeId(rs.getInt("typeid"));
				goodsType.setTypeName(rs.getString("typeName"));
			}
		} catch (Exception e) {
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return goodsType;
	}
	
	/**
	 * ɾ��������Ϣ
	 */
	public boolean remove(int id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		
		try {
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement("delete from GoodsType where typeId = ?");
			pstmt.setInt(1, id);
			int rowcount = 0;
			rowcount = pstmt.executeUpdate();
			if(rowcount>0){
				flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * ���ݿͻ����ɾ������ͻ����(ɾ����)����̨��
	 * @param id
	 * @return
	 */
	public boolean deletes(int[] id)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			String sql = "delete from GoodsType where typeid = ?";
			if(id.length != 0)
			{
				for (int i = 0; i < id.length; i++) {
					System.out.println("delete from GoodsType where typeid = "+id[i]);
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
	 * ɾ�����ˣ�����ѯ����Ϣ�������û�����ɾ������
	 */
	public boolean delteFilter(int... args)
	{
//		--ֻ��������һ������Ʒ��Ϣ�Ͳ�ִ��ɾ��(��Ʒ����)
//		select * from GoodsType a inner join GoodsInfo b on a.typeId = b.typeId
//		where a.typeId in (1) or a.typeId in (2) or a.typeId in (3)
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			String sql = "select * from GoodsType a inner join GoodsInfo b on a.typeId = b.typeId where ";
			for (int i = 0; i < args.length; i++) {
				if(i == 0)
				{
					sql += " a.typeId = "+args[i];	
				}
				else{
					sql += " or a.typeId = "+args[i];	
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
	
	/**
	 * ��ѯ�û����Ƿ����
	 * @param typeId ���ͱ�š�0��ʾδ������Ʒ���ͣ���������0�����Ѵ��ڵ���Ʒ���͡�
	 * @param typeName ��������
	 * @return
	 */
	public boolean checkType(int typeId,String typeName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			String sql = "select * from GoodsType where typeId != ? and typeName = ?";
			System.out.println("select * from GoodsType where typeId != "+typeId+" and typeName = '"+typeName+"'");
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			pstmt.setString(2, typeName);
			
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
}
