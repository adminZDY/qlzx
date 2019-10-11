package com.sec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.sec.model.BulletinInfo;
import com.sec.model.UserInfo;
import com.sec.util.DBConn;
import com.sec.util.DateTimeUtil;
import com.sec.util.PageModel;

public class BulletinDao {
	/**
	 * 获取所有的公告信息（分页）
	 * @param pageSize 页面数据量
	 * @param pageNo 页码
	 * @param text 查询文本
	 * @param userid 用户编号
	 * @return
	 */
	public PageModel<BulletinInfo> getAllBulletionInfo(int pageSize,int pageNo ,String text ,int userid){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<BulletinInfo> pm = null;
		ArrayList<BulletinInfo> items = new ArrayList<BulletinInfo>();
		
		String s = "";
		String a = "";
		
		if(text != null && !"".equals(text)){
			s = " a.title like '%"+text+"%' and ";
			if(userid != 0){
				a = "and title like '%"+text+"%' ";
			}else{
				a = "where title like '%"+text+"%' ";
			}
		}
	
		String sql = "";
		//判断sign如果不等于空后台
		if(userid != 0){
			 sql = "select top "+pageSize+" a.*,b.userName from bulletininfo a " +
				" left join UserInfo b on a.userId = b.id " +
				" where a.userid="+userid+"  and "+s+" a.id not in(select top "+((pageNo-1)*pageSize)+" id from BulletinInfo where userid="+userid+" "+a+" order by createtime desc)order by a.createtime desc";
		}else{
			
			 sql = "select top "+pageSize+" a.*,b.userName from bulletininfo a" +
					" left join UserInfo b on a.userId = b.id" +
					" where  "+s+" a.id not in(select top "+((pageNo-1)*pageSize)+" id from BulletinInfo "+a+" order by createtime desc)order by a.createtime desc";
		}
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			//执行查询
			rs = pstmt.executeQuery();
			
			BulletinInfo item = null;
			UserInfo user = null;
			
			while(rs.next()){
				item = new BulletinInfo();
				item.setId(rs.getInt("id"));
				item.setTitle(rs.getString("title"));
				item.setContent(rs.getString("content"));
				
				user = new UserInfo();
				user.setUserId(rs.getInt("userid"));
				user.setUserName(rs.getString("username"));
				
				item.setUser(user);
				item.setCreateTime(DateTimeUtil.convertDate(rs.getString("createtime")));
				items.add(item);
			}
			
			pm = new PageModel<BulletinInfo>();
			pm.setData(items);
			pm.setPageNo(pageNo);
			pm.setPageSize(pageSize);
			pm.setTotalRecords(getTotalRecords(conn , pstmt , rs ,text ,userid));
		}catch(Exception e){
			
			//e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn );
		}
		return pm;
	}
	
	//获取总记录条数
	private int getTotalRecords(Connection conn , PreparedStatement pstmt , ResultSet rs ,String text , int userid){
		String s = "";
		if(text != null && !"".equals(text)){
			if(userid != 0){
				s = " and title like '%"+text+"%' ";		
			}else{
				s = " where title like '%"+text+"%' ";
			}
		}
		String sql = "";
		if(userid != 0){
			 sql = "select count(1) from  bulletininfo where userid = "+userid+" "+s;
		}else{
			
			 sql = "select count(1) from  bulletininfo  "+s;
		}
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
	
	/*
	 * (多)删除公告信息
	 */
	public boolean delete(Integer id){
		
		String sql = "delete from bulletininfo where id= ? ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
				pstmt.setInt(1, id);
			
			
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

	
	/*
	 * 修改公告信息
	 */
	public boolean update(String title , String content ,int id){
		String sql = "update bulletininfo set title = ? ,content = ? where  id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = DBConn.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, id);
			
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
	
	/*
	 * 查询单条公告信息
	 */
	public BulletinInfo add(int id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BulletinInfo bu = null;
		UserInfo user = null;
		
		String sql = "select a.id , a.title, a.content , c.username ,a.createtime  from  bulletininfo a inner join userinfo c on a.userid = c.id where  a.id = ?";
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs= pstmt.executeQuery();
			
			
			while(rs.next()){
				bu = new BulletinInfo();
				user = new UserInfo();
				
				user.setUserName(rs.getString(4));//发布名称
				
				bu.setId(rs.getInt(1));
				bu.setTitle(rs.getString(2));
				bu.setContent(rs.getString(3));
				bu.setUser(user);
				bu.setCreateTime(DateTimeUtil.convertDate(rs.getString(5)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return bu;
	}

	//添加公告信息
	public boolean insert(String title , String content , int userId){
		String sql = "insert into bulletininfo values(?,?,?,getdate())";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, userId);
		
			int i = pstmt.executeUpdate();
			
			if(i > 0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return false;
	}
}
