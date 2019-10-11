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
	 * 查询所有类型
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
	 * 模糊查询类型【分页
	 * @param pageSize 页面数据量
	 * @param pageNo 页码
	 * @param text 模糊查询文本
	 * @return
	 */
	public PageModel<GoodsType> getAllGoodsType(int pageSize,int pageNo,String text){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "";
		//模糊查询
		sql = "select * from goodstype where typename like '%"+text+"%'";

		ArrayList<GoodsType> items = new ArrayList<GoodsType>();
		PageModel<GoodsType> pm = new PageModel<GoodsType>();
		
		try {
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			//执行查询
			rs = pstmt.executeQuery();
			
			GoodsType item = null;
				
			//循环获取数据
			while(rs.next()){
				item = new GoodsType();
				item.setTypeId(rs.getInt("typeId"));
				item.setTypeName(rs.getString("typeName"));
				//添加到集合
				items.add(item);
			}
			//设置分页
			pm.setData(items);//设置当前页面数据
			pm.setTotalRecords(getCountGoods(conn,pstmt,rs,text));//返回总记录数
			pm.setPageSize(pageSize);//每页显示的条数
			pm.setPageNo(pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	
	/**
	 * 获取所有的商品类型【分页
	 * @param pageSize 页面数据量
	 * @param pageNo 页码
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
			
			//执行查询
			rs = pstmt.executeQuery();
			
			GoodsType item = null;
			
			//循环获取数据
			while(rs.next()){
				item = new GoodsType();
				item.setTypeId(rs.getInt("typeId"));
				item.setTypeName(rs.getString("typeName"));
				//添加到集合
				items.add(item);
			}
			//设置分页
			pm.setData(items);//设置当前页面数据
			pm.setTotalRecords(getCountGoods(conn,pstmt,rs));//返回总记录数
			pm.setPageSize(pageSize);//每页显示的条数
			pm.setPageNo(pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	
	/**
	 * 查询总的记录数(所有查询总计录数)
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
	 * 查询总的记录数(模糊查询总计录数)
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
	 * 添加商品类型的方法
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
	 * 根据类型编号修改类型名称
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

	/**根据id号查询(跳转到修改页面要用到)
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
	 * 删除单条信息
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
	 * 根据客户编号删除多个客户编号(删除多)【后台】
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
			//打开连接
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
	 * 删除过滤，即查询到信息不允许用户继续删除操作
	 */
	public boolean delteFilter(int... args)
	{
//		--只有其中有一个有商品信息就不执行删除(商品类型)
//		select * from GoodsType a inner join GoodsInfo b on a.typeId = b.typeId
//		where a.typeId in (1) or a.typeId in (2) or a.typeId in (3)
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//打开连接
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
	 * 查询用户名是否可用
	 * @param typeId 类型编号【0表示未插入商品类型，其他大于0都是已存在的商品类型】
	 * @param typeName 类型名称
	 * @return
	 */
	public boolean checkType(int typeId,String typeName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//打开连接
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
