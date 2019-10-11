package com.sec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sec.model.BulletinInfo;
import com.sec.model.GoodsInfo;
import com.sec.model.GoodsType;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.SalesGoods;
import com.sec.util.DBConn;
import com.sec.util.PageModel;

public class GoodsInfoDao {
	//������Ʒ���Ͳ�ѯ������Ʒ��Ϣ����ҳ
	public PageModel<GoodsInfo> getAllGoodsInfo(int pageSize,int pageNo, int typeid){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<GoodsInfo> pm = null;
		ArrayList<GoodsInfo> list = new ArrayList<GoodsInfo>();
		
		String sql = " select top "+pageSize+" a.goodsid, a.goodsname , a.price , a.discount , a.photo  from goodsinfo a " +
				"where  a.goodsid not in(select top "+((pageNo-1)*pageSize)+" goodsid from goodsinfo where typeid = ?) and  a.typeid = ? and  a.status = 0";
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, typeid);
			pstmt.setInt(2, typeid);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				GoodsInfo goodsinfo = new GoodsInfo();
				goodsinfo.setGoodsId(rs.getInt(1));
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				goodsinfo.setPrice(rs.getDouble("price"));
				goodsinfo.setDiscount(rs.getFloat("discount"));
				//goodsinfo.setIsNew(rs.getInt("isnew"));
				//goodsinfo.setIsRecommend(rs.getInt("isrecommend"));
				//goodsinfo.setStatus(rs.getInt("status"));
				goodsinfo.setPhoto(rs.getString("photo"));
				//goodsinfo.setRemark(rs.getString("remark"));
				
				list.add(goodsinfo);
			}
			
		pm = new PageModel<GoodsInfo>();
			pm.setData(list);
			pm.setPageNo(pageNo);//
			//��ҳ��
			pm.setTotalRecords(getTotalRecords(conn, pstmt, rs , typeid));
			pm.setPageSize(pageSize);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	//������Ʒ���Ͳ�ѯ������Ʒ������
	private int getTotalRecords(Connection conn , PreparedStatement pstmt , ResultSet rs , int typeid ){
		
		String sql = "select COUNT(*) as 'goodsid' from goodsinfo where typeid = ? and  status = 0";
		int count = 0 ;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, typeid);
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
	 * ��̨��ҳģ����ѯ
	 * @param pageSize
	 * @param pageNo
	 * @param text
	 * @return
	 */
	public PageModel<GoodsInfo> goodspaging(int pageSize,int pageNo,String text){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<GoodsInfo> pm = null;
		ArrayList<GoodsInfo> list = new ArrayList<GoodsInfo>();
		String s = "";
		String a = "";
		
		if(text != null){
			s = " a.goodsname like '%"+text+"%' and ";
			a = "where goodsname like '%"+text+"%' ";
		} 
//		String sql = " select top "+pageSize+" * from goodsinfo a left join goodstype b on a.typeid = b.typeid " +
//				"where "+s+" a.goodsid not in(select top "+((pageNo-1)*pageSize)+" goodsid from goodsinfo "+a+" order by goodsId,typeid ) order by a.goodsId,a.typeid ";
		String sql = " select top "+pageSize+" * from goodsinfo a left join goodstype b on a.typeid = b.typeid " +
				"where "+s+" a.goodsid not in(select top "+((pageNo-1)*pageSize)+" goodsid from goodsinfo "+a+" order by typeid ,goodsId) order by a.typeid,a.goodsId ";
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				GoodsInfo goodsinfo = new GoodsInfo();
				goodsinfo.setGoodsId(rs.getInt(1));
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				goodsinfo.getGoodstype().setTypeName(rs.getString("typeName"));
				goodsinfo.setPrice(rs.getDouble("price"));
				goodsinfo.setDiscount(rs.getFloat("discount"));
				goodsinfo.setIsNew(rs.getInt("isnew"));
				goodsinfo.setIsRecommend(rs.getInt("isrecommend"));
				goodsinfo.setStatus(rs.getInt("status"));
				goodsinfo.setPhoto(rs.getString("photo"));
				//goodsinfo.setRemark(rs.getString("remark"));
				
				list.add(goodsinfo);
			}
			
		pm = new PageModel<GoodsInfo>();
			pm.setData(list);
			pm.setPageNo(pageNo);//
			//��ҳ��
			pm.setTotalRecords(getTotalRecords1(conn, pstmt, rs,text));
			pm.setPageSize(pageSize);
		}catch(Exception e){
		//	e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	
	/**
	 * ��̨��ҳ��ѯ
	 * @param pageSize
	 * @param pageNo
	 * @param text
	 * @return
	 */
	public PageModel<GoodsInfo> goodspaging(int pageSize,int pageNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<GoodsInfo> pm = null;
		ArrayList<GoodsInfo> list = new ArrayList<GoodsInfo>();
		
//		String sql = " select top "+pageSize+" * from goodsinfo a left join goodstype b on a.typeid = b.typeid " +
//				"where a.goodsid not in(select top "+((pageNo-1)*pageSize)+" goodsid from goodsinfo order by goodsId,typeid ) order by a.goodsId,a.typeid ";

		String sql = " select top "+pageSize+" * from goodsinfo a left join goodstype b on a.typeid = b.typeid " +
				"where a.goodsid not in(select top "+((pageNo-1)*pageSize)+" goodsid from goodsinfo  order by typeid ,goodsId) order by a.typeid,a.goodsId ";
		
		//System.out.println(sql);
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				GoodsInfo goodsinfo = new GoodsInfo();
				goodsinfo.setGoodsId(rs.getInt(1));
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				goodsinfo.getGoodstype().setTypeName(rs.getString("typeName"));
				goodsinfo.setPrice(rs.getDouble("price"));
				goodsinfo.setDiscount(rs.getFloat("discount"));
				goodsinfo.setIsNew(rs.getInt("isnew"));
				goodsinfo.setIsRecommend(rs.getInt("isrecommend"));
				goodsinfo.setStatus(rs.getInt("status"));
				goodsinfo.setPhoto(rs.getString("photo"));
				//goodsinfo.setRemark(rs.getString("remark"));
				
				list.add(goodsinfo);
			}
			pm = new PageModel<GoodsInfo>();
			pm.setData(list);
			pm.setPageSize(pageSize);
			pm.setPageNo(pageNo);//
			//��ҳ��
			pm.setTotalRecords(getTotalRecords1(conn, pstmt, rs));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return pm;
	}
	
	/**
	 * ������Ʒ���Ͳ�ѯ������Ʒ������[��̨ģ��ͳ��]
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @param text
	 * @return
	 */
	private int getTotalRecords1(Connection conn , PreparedStatement pstmt , ResultSet rs,String text ){
			
			String s = "";
			if(text != null){
				s = " where goodsname like '%"+text+"%' ";
			}
			String sql = "select COUNT(*) as 'goodsid' from goodsinfo  "+s;
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
	 * ������Ʒ���Ͳ�ѯ������Ʒ������[��̨��ѯ����ͳ��]
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @param text
	 * @return
	 */
	private int getTotalRecords1(Connection conn , PreparedStatement pstmt , ResultSet rs){
			String sql = "select COUNT(*) as 'goodsid' from goodsinfo  ";
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
	 * ������Ʒ���Ͳ�ѯ��Ʒ���۵�������������
	 * @param typeId
	 * @return
	 */
	public ArrayList<SalesGoods> getSalesGoods(int typeId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<SalesGoods> goodsList = new ArrayList<SalesGoods>();
		String sql = "select top 10 sum(a.quantity) as quantity,b.goodsid,b.goodsname from ordergoodsinfo a left join " +  
				"goodsinfo b on a.goodsid = b.goodsid inner join " + 
				"orderinfo c on c.orderid = a.orderid where " + 
				"b.typeid = ? and b.status = 0 and c.status=1 group by " + 
				"b.goodsid , b.goodsname ORDER  BY sum(a.quantity) DESC";
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			
			rs = pstmt.executeQuery();
			SalesGoods goods = null;
			while(rs.next()){
				goods = new SalesGoods();
				goods.setQuantity(rs.getInt("quantity"));
				goods.setGoodsId(rs.getInt("goodsId"));
				goods.setGoodsName(rs.getString("goodsName"));
				goodsList.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return goodsList;
	}
	/**
	 * ��ѯ������Ʒ��Ϣ
	 * @param goodsid
	 * @return
	 */
	public GoodsInfo select(int goodsid){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GoodsInfo goodsinfo = null;
		String sql = "select a.goodsid,a.goodsname,a.price ,a.isnew,a.isrecommend,a.status,a.discount ,b.typename , a.remark , a.photo , a.detailed from goodsinfo a left join " +
				" goodstype b on a.typeid = b.typeid where a.goodsid = ?";
		
		try{
			
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, goodsid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				goodsinfo = new GoodsInfo();
				
				goodsinfo.setGoodsId(rs.getInt("goodsid"));
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				goodsinfo.getGoodstype().setTypeName(rs.getString("typeName"));
				goodsinfo.setPrice(rs.getDouble("price"));
				goodsinfo.setDiscount(rs.getFloat("discount"));
				goodsinfo.setIsNew(rs.getInt("isnew"));
				goodsinfo.setIsRecommend(rs.getInt("isrecommend"));
				goodsinfo.setStatus(rs.getInt("status"));
				goodsinfo.setPhoto(rs.getString("photo"));
				goodsinfo.setRemark(rs.getString("remark"));
				goodsinfo.setDetailed(rs.getString("detailed"));
			}
			
		}catch(Exception e){
			
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return goodsinfo;
	}
	/**
	 * ɾ����Ʒ��Ϣ[����]
	 */
	public boolean delete(int id){
		
		//String sql = "exec proc_goodsdelete ?";
		String sql = "delete from GoodsInfo where goodsId = ?";
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
	
	/**ɾ����Ʒ��Ϣ[���]
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletes(int[] id){
		String sql = "delete from GoodsInfo where goodsId = ?";
		for (int i = 1; i < id.length; i++) {
			sql+=" or goodsId = ? ";
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id[0]);
			for (int i = 1; i < id.length; i++) {
				pstmt.setInt(i+1, id[i]);
			}
			
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
	 * �޸���Ʒ��Ϣ
	 * @param goods
	 * @return
	 */
	public boolean update(GoodsInfo goods){
		
		String sql = " update goodsinfo set typeid = ? , goodsname = ?  , price = ? ,discount = ? ,isnew = ? , isrecommend =? , status = ? , photo = ? , remark = ? , detailed = ? where goodsid = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = DBConn.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, goods.getGoodstype().getTypeId());
			pstmt.setString(2, goods.getGoodsName());
			pstmt.setDouble(3, goods.getPrice());
			pstmt.setFloat(4, goods.getDiscount());
			pstmt.setInt(5, goods.getIsNew());
			pstmt.setInt(6, goods.getIsRecommend());
			pstmt.setInt(7, goods.getStatus());
			pstmt.setString(8, goods.getPhoto());
			pstmt.setString(9, goods.getRemark());
			pstmt.setString(10, goods.getDetailed());
			pstmt.setInt(11, goods.getGoodsId());
		
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
	 * �����Ʒ��Ϣ
	 * @param goods
	 * @return
	 */
	public boolean insert(GoodsInfo goods){
		String sql = "insert into goodsinfo values(?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, goods.getGoodstype().getTypeId());
			pstmt.setString(2, goods.getGoodsName());
			pstmt.setDouble(3, goods.getPrice());
			pstmt.setFloat(4, goods.getDiscount());
			pstmt.setInt(5, goods.getIsNew());
			pstmt.setInt(6, goods.getIsRecommend());
			pstmt.setInt(7, goods.getStatus());
			pstmt.setString(8, goods.getPhoto());
			pstmt.setString(9, goods.getRemark());
			pstmt.setString(10, goods.getDetailed());
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
	/**
	 * ���ݿͻ���Ų�ѯ��Ʒ��Ϣ
	 */
	/*public ArrayList<GoodsInfo> shopping(int customerid){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GoodsInfo goodsinfo = null;
		GoodsType type = null;
		
		ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();;
		String sql = "select b.photo , b.goodsname , c.typename ,b.price , b.discount ,a.quantity  from ordergoodsinfo a left join " +
				"goodsinfo b on a.goodsid = b.goodsid inner join " +
				"goodstype c on b.typeid = c.typeid inner join " +
				"orderinfo d on d.orderid = a.orderid inner join " +
				"customerinfo e on d.customerid = e.id where " +
				"b.status = 0 and d.status = 0 and d.customerid = ?";
		
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, customerid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				goodsinfo = new GoodsInfo();
				
				goodsinfo.setPhoto(rs.getString("photo"));
				goodsinfo.setGoodsName(rs.getString("goodsName"));
				
				type = new GoodsType();
				type.setTypename(rs.getString("typeName"));
				
				goodsinfo.setGoodstype(type);
				goodsinfo.setPrice(rs.getDouble("price"));
				goodsinfo.setDiscount(rs.getFloat("discount"));
				
				goodsList.add(goodsinfo);
			}
		}catch (Exception e) {
			
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		return goodsList;
	}*/
	
	/**
	 * �Ƽ���Ʒ
	 */
	public ArrayList<GoodsInfo> isrecommend(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
		GoodsInfo goodsinfo = null;
		String sql = "select * from goodsinfo where isrecommend = 0 and status = 0";
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				goodsinfo = new GoodsInfo();
				
				//��Ʒ���
				goodsinfo.setGoodsId(rs.getInt("goodsid"));
				
				//��Ʒ����
				GoodsType goodstype = new GoodsType();
				goodstype.setTypeId(rs.getInt("typeid"));
				goodsinfo.setGoodstype(goodstype);
				
				//��Ʒ����
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				//��Ʒ�۸�
				goodsinfo.setPrice(rs.getDouble("price"));
				//��Ʒ�ۿ�
				goodsinfo.setDiscount(rs.getFloat("discount"));
				//��ƷͼƬ
				goodsinfo.setPhoto(rs.getString("photo"));
				//��Ʒ��ע
				goodsinfo.setRemark(rs.getString("remark"));
				
				goodsList.add(goodsinfo);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		
		return goodsList;
	}
	
	/**
	 * ��Ʒ�ϼ�
	 */
	public ArrayList<GoodsInfo> isnew(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
		GoodsInfo goodsinfo = null;
		String sql = "select * from goodsinfo where isnew = 0 and status = 0";
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				goodsinfo = new GoodsInfo();
				
				//��Ʒ���
				goodsinfo.setGoodsId(rs.getInt("goodsid"));
				
				//��Ʒ����
				GoodsType goodstype = new GoodsType();
				goodstype.setTypeId(rs.getInt("typeid"));
				goodsinfo.setGoodstype(goodstype);
				
				//��Ʒ����
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				//��Ʒ�۸�
				goodsinfo.setPrice(rs.getDouble("price"));
				//��Ʒ�ۿ�
				goodsinfo.setDiscount(rs.getFloat("discount"));
				//��ƷͼƬ
				goodsinfo.setPhoto(rs.getString("photo"));
				//��Ʒ��ע
				goodsinfo.setRemark(rs.getString("remark"));
				
				goodsList.add(goodsinfo);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		
		return goodsList;
	}
	
	/**
	 * �ؼ���Ʒ
	 */
	public ArrayList<GoodsInfo> discount(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
		GoodsInfo goodsinfo = null;
		String sql = "select * from goodsinfo where discount != 10.0 and status = 0 order by discount asc";
		try{
			conn = DBConn.getConn();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				goodsinfo = new GoodsInfo();
				
				//��Ʒ���
				goodsinfo.setGoodsId(rs.getInt("goodsid"));
				
				//��Ʒ����
				GoodsType goodstype = new GoodsType();
				goodstype.setTypeId(rs.getInt("typeid"));
				goodsinfo.setGoodstype(goodstype);
				
				//��Ʒ����
				goodsinfo.setGoodsName(rs.getString("goodsname"));
				//��Ʒ�۸�
				goodsinfo.setPrice(rs.getDouble("price"));
				//��Ʒ�ۿ�
				goodsinfo.setDiscount(rs.getFloat("discount"));
				//��ƷͼƬ
				goodsinfo.setPhoto(rs.getString("photo"));
				//��Ʒ��ע
				goodsinfo.setRemark(rs.getString("remark"));
				
				goodsList.add(goodsinfo);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBConn.closeDB(rs, pstmt, conn);
		}
		
		return goodsList;
	}
	
	/**
	 * ɾ��������Ʒ��Ϣ
	 * ɾ�����ˣ�����ѯ����Ϣ�������û�����ɾ������
	 */
	public boolean delteFilter(int... args)
	{
//		--ֻ��������һ������Ϣ�ڶ����г��־Ͳ�ִ��ɾ������Ʒ��Ϣ��
//		select * from OrderGoodsInfo where goodsId in (1) or goodsId in (2)
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		try {
			//������
			con = DBConn.getConn();
			String sql = "select * from OrderGoodsInfo where ";
			for (int i = 0; i < args.length; i++) {
				if(i == 0)
				{
					sql += " goodsId ="+args[i];	
				}
				else{
					sql += " or goodsId ="+args[i];	
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
}
