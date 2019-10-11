package com.sec.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sec.dao.BulletinDao;
import com.sec.dao.GoodsInfoDao;
import com.sec.dao.GoodsTypeDao;
import com.sec.model.GoodsInfo;
import com.sec.model.GoodsType;
import com.sec.model.SalesGoods;
import com.sec.util.PageModel;

public class GoodsManage extends HttpServlet {
	 private static final long serialVersionUID = 1L;
     
	    // 上传文件存储目录
	    private static final String UPLOAD_DIRECTORY = "products";
	 
	    // 上传配置
	    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	 
	/**
	 * Constructor of the object.
	 */
	public GoodsManage() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String returnURL = "";
		String op = request.getParameter("op");
		
		if ("".equals(op) || op == null || "queryAll".equals(op)) {
			// 前台分页
			returnURL = queryAll(request, response);
		} else {
			// 根据商品类型编号查询单条商品信息
			if ("query".equals(op)) {
				returnURL = query(request, response);
				// 后台分页
			} else if ("viewGoods".equals(op)) {
				returnURL = viewGoods(request, response);
				// 修改商品信息
			} else if ("update".equals(op)) {
				returnURL = update(request, response);
				// 删除单条商品信息
			} else if ("remove".equals(op)) {
				returnURL = remove(request, response);
				// 删除多条商品信息
			} else if ("removeMore".equals(op)) {
				returnURL = removeMore(request, response);
				//跳转到添加商品页面
			} else if ("toAdd".equals(op)) {
				returnURL = toAdd(request, response);
				//添加商品
			} else if ("Add".equals(op)) {
				returnURL = Add(request, response);
				//前台模糊查询
			}else if("addvague".equals(op)){
				returnURL = addvague(request,response);
			}
			/*
			 * 根据客户编号查询商品信息 if("cart".equals(op)){ returnURL =
			 * cart(request,response); }
			 */
		}
		
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

	/**
	 * 前台商品信息分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageSize = 12;
		int pageNo = 1;
		int typeid = 0;

		try {
			typeid = Integer.parseInt(request.getParameter("typeid"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {

		}

		GoodsInfoDao dao = new GoodsInfoDao();
		ArrayList<SalesGoods> salesList = dao.getSalesGoods(typeid);

		PageModel<GoodsInfo> pm = dao.getAllGoodsInfo(pageSize, pageNo, typeid);
		ArrayList<GoodsInfo> list = pm.getData();
		
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.getAllGoodsInfo(pageSize, 1, typeid);
		}

		request.setAttribute("allsales", salesList);
		request.setAttribute("allgoods", pm);
		request.setAttribute("typeid", typeid);
		return "../products.jsp";
	}

	/**
	 * 后台商品信息分页
	 */
	public String viewGoods(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageSize = 7;
		int pageNo = 1;
		PageModel<GoodsInfo> pm = null;
		//获取值
		String text = request.getParameter("text");

		// 判断提交方法，如果是get提交时转码，否则将为乱码
		if (text != null && "GET".equals(request.getMethod())) {
			// 这是get提交的转码方法
			text = new String(request.getParameter("text").getBytes(
					"iso-8859-1"), "utf-8");
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {}

		
		GoodsInfoDao dao = new GoodsInfoDao();

		//查询所有商品
		if ("null".equals(text)||"".equals(text)||text == null) {
			pm = dao.goodspaging(pageSize, pageNo);
		}
		else{
			//模糊查询
			pm = dao.goodspaging(pageSize, pageNo, text);
		}
	
	
		if(pm == null || "".equals(pm)){
			text = "";
			pm = dao.goodspaging(pageSize, pageNo);
		}
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.goodspaging(pageSize, pageNo);
		}

		request.setAttribute("allgoods", pm);

		return "../admin/goodsManage.jsp?text="+text;
	}

	/**
	 * 根据商品类型查询单条商品信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int goodsid = 0;
		try {
			goodsid = Integer.parseInt(request.getParameter("goodsid"));
		} catch (Exception e) {

		}
		GoodsInfoDao dao = new GoodsInfoDao();
		GoodsInfo goodsinfo = dao.select(goodsid);
		request.setAttribute("goodsinfo", goodsinfo);

		if (request.getParameter("toup") != null) {
			return "../admin/updateGoods.jsp";
		}
		return "../product.jsp";
	}

	/**
	 * 修改商品信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String goodsid = request.getParameter("goodsid");
		String goodsname = request.getParameter("goodsname");
		String goodstype = request.getParameter("goodstype");
		String goodsprice = request.getParameter("goodsprice");
		String discount = request.getParameter("discount");
		String[] stastus = request.getParameterValues("stastus");
		String[] isrecommend = request.getParameterValues("isrecommend");
		String[] isnew = request.getParameterValues("isnew");
		String photo = request.getParameter("photo");
		String remark = request.getParameter("remark");
		String content = request.getParameter("content");
		
		
		int num = 0;
		int num1 = 0;
		int num2 = 0;

		if (stastus == null) {
			num = 1;
		}
		if (isrecommend == null) {
			num1 = 1;
		}
		if (isnew == null) {
			num2 = 1;
		}

		String[] str = photo.split("\\\\");
		String s = str[str.length - 1];

		GoodsInfo goods = new GoodsInfo();
		goods.setGoodsId(Integer.parseInt(goodsid));
		goods.setGoodsName(goodsname);
		goods.getGoodstype().setTypeId(Integer.parseInt(goodstype));
		goods.setDiscount(Float.parseFloat(discount));
		goods.setPrice(Double.parseDouble(goodsprice));
		goods.setStatus(num);
		goods.setIsRecommend(num1);
		goods.setIsNew(num2);
		goods.setPhoto(s);
		goods.setRemark(remark);
		goods.setDetailed(content);
		GoodsInfoDao dao = new GoodsInfoDao();
		if (dao.update(goods)) {

			//return viewGoods(request, response);
			request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("ok", "修改商品信息成功!");
			return "../admin/ok.jsp";
		}
		request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=");
		request.setAttribute("msg", "对不起,修改商品信息失败!");
		return "../admin/failed.jsp";
	}

	/**
	 * 删除商品信息（单）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));// 编号

		GoodsInfoDao dao = new GoodsInfoDao();
		if (!dao.delteFilter(id)) {
			if (dao.delete(id)) {
				request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=viewGoods");
				request.setAttribute("ok", "修改商品信息成功!");
				return "../admin/ok.jsp";
			}
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("failed", "删除商品信息失败!");
			return "../admin/failed.jsp";
		} else {
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("msg", "删除商品信息出错了!");
			return "../admin/error.jsp";
		}
	}

	// 删除多条商品信息
	public String removeMore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] chk = request.getParameterValues("chkItems");
		int[] id = new int[chk.length];
		GoodsInfoDao dao = new GoodsInfoDao();

		for (int i = 0; i < chk.length; i++) {
			id[i] = Integer.parseInt(chk[i]);
		}

		if (!dao.delteFilter(id)) {
			if (dao.deletes(id)) {
				request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=viewGoods");
				request.setAttribute("ok", "修改商品信息成功!");
				return "../admin/ok.jsp";
			}
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("failed", "删除商品信息失败!");
			return "../admin/failed.jsp";
		} else {
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("msg", "删除商品信息出错了!");
			return "../admin/error.jsp";
		}
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		GoodsTypeDao typedao = new GoodsTypeDao();
		ArrayList<GoodsType> goodstype = typedao.GoodsTypeList();

		if (goodstype != null) {

			request.setAttribute("goodstype", goodstype);
			return "../admin/addGoods.jsp";
		}

		return queryAll(request, response);
	}

	/**
	 * 添加商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String Add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String goodsname = request.getParameter("goodsname");
		String goodstype = request.getParameter("goodstype");
		String goodsprice = request.getParameter("goodsprice");
		String discount = request.getParameter("discount");
		String[] stastus = request.getParameterValues("stastus");
		String[] isrecommend = request.getParameterValues("isrecommend");
		String[] isnew = request.getParameterValues("isnew");
		String photo = request.getParameter("photo");
		String remark = request.getParameter("remark");
		String content = request.getParameter("content");
		
		System.out.println("商品名称" + goodsname + ",类型" + goodstype + ",价格"
				+ goodsprice + "," + discount + "推荐" + remark);
		int num = 0;
		int num1 = 0;
		int num2 = 0;

		if (stastus == null) {
			num = 1;
		}
		if (isrecommend == null) {
			num1 = 1;
		}
		if (isnew == null) {
			num2 = 1;
		}

		String[] str = photo.split("\\\\");
		String s = str[str.length - 1];

		GoodsInfo goods = new GoodsInfo();
		goods.setGoodsName(goodsname);
		goods.getGoodstype().setTypeId(Integer.parseInt(goodstype));
		goods.setDiscount(Float.parseFloat(discount));
		goods.setPrice(Double.parseDouble(goodsprice));
		goods.setStatus(num);
		goods.setIsRecommend(num1);
		goods.setIsNew(num2);
		goods.setPhoto(s);
		goods.setRemark(remark);
		goods.setDetailed(content);
		
		GoodsInfoDao dao = new GoodsInfoDao();
		if (dao.insert(goods)) {
			// return viewGoods(request,response);
			request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("ok", "添加商品信息成功!");
			return "../admin/ok.jsp";
		}

		request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=viewGoods");
		request.setAttribute("failed", "添加商品信息失败!");
		return "../admin/failed.jsp";
	}
	
	/**
	 * 前台商品信息模糊查询
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addvague (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		int pageSize = 9 ;
		int pageNo = 1;
		String text =request.getParameter("txt");
		
		//判断提交方法，如果是get提交时转码，否则将为乱码
		if(text != null && "GET".equals(request.getMethod())){
			//这是get提交的转码方法
			text = new String(request.getParameter("txt").getBytes("iso-8859-1"),"utf-8");
		}
		if("null".equals(text) || text == null || "".equals(text)){
			text = "";
		}
		
		
		try{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			
		}catch(Exception e){
			
		}
		try{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		GoodsInfoDao dao = new GoodsInfoDao();
		
		PageModel<GoodsInfo> pm = dao.goodspaging(pageSize, pageNo , text);
		
		if(pm == null || "".equals(pm)){
			text = "";
			pm = dao.goodspaging(pageSize, pageNo);
		}
		if(pm != null && pm.getData().isEmpty() && pageNo != 1){
			pm= dao.goodspaging(pageSize, pageNo , text);
		}
		
		request.setAttribute("allgoods", pm);

		return "../goodsSelect.jsp?txt="+text;
	}
}
