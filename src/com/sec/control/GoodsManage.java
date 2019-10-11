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
     
	    // �ϴ��ļ��洢Ŀ¼
	    private static final String UPLOAD_DIRECTORY = "products";
	 
	    // �ϴ�����
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
			// ǰ̨��ҳ
			returnURL = queryAll(request, response);
		} else {
			// ������Ʒ���ͱ�Ų�ѯ������Ʒ��Ϣ
			if ("query".equals(op)) {
				returnURL = query(request, response);
				// ��̨��ҳ
			} else if ("viewGoods".equals(op)) {
				returnURL = viewGoods(request, response);
				// �޸���Ʒ��Ϣ
			} else if ("update".equals(op)) {
				returnURL = update(request, response);
				// ɾ��������Ʒ��Ϣ
			} else if ("remove".equals(op)) {
				returnURL = remove(request, response);
				// ɾ��������Ʒ��Ϣ
			} else if ("removeMore".equals(op)) {
				returnURL = removeMore(request, response);
				//��ת�������Ʒҳ��
			} else if ("toAdd".equals(op)) {
				returnURL = toAdd(request, response);
				//�����Ʒ
			} else if ("Add".equals(op)) {
				returnURL = Add(request, response);
				//ǰ̨ģ����ѯ
			}else if("addvague".equals(op)){
				returnURL = addvague(request,response);
			}
			/*
			 * ���ݿͻ���Ų�ѯ��Ʒ��Ϣ if("cart".equals(op)){ returnURL =
			 * cart(request,response); }
			 */
		}
		
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

	/**
	 * ǰ̨��Ʒ��Ϣ��ҳ
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
	 * ��̨��Ʒ��Ϣ��ҳ
	 */
	public String viewGoods(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageSize = 7;
		int pageNo = 1;
		PageModel<GoodsInfo> pm = null;
		//��ȡֵ
		String text = request.getParameter("text");

		// �ж��ύ�����������get�ύʱת�룬����Ϊ����
		if (text != null && "GET".equals(request.getMethod())) {
			// ����get�ύ��ת�뷽��
			text = new String(request.getParameter("text").getBytes(
					"iso-8859-1"), "utf-8");
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {}

		
		GoodsInfoDao dao = new GoodsInfoDao();

		//��ѯ������Ʒ
		if ("null".equals(text)||"".equals(text)||text == null) {
			pm = dao.goodspaging(pageSize, pageNo);
		}
		else{
			//ģ����ѯ
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
	 * ������Ʒ���Ͳ�ѯ������Ʒ��Ϣ
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
	 * �޸���Ʒ��Ϣ
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
			request.setAttribute("ok", "�޸���Ʒ��Ϣ�ɹ�!");
			return "../admin/ok.jsp";
		}
		request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=");
		request.setAttribute("msg", "�Բ���,�޸���Ʒ��Ϣʧ��!");
		return "../admin/failed.jsp";
	}

	/**
	 * ɾ����Ʒ��Ϣ������
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));// ���

		GoodsInfoDao dao = new GoodsInfoDao();
		if (!dao.delteFilter(id)) {
			if (dao.delete(id)) {
				request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=viewGoods");
				request.setAttribute("ok", "�޸���Ʒ��Ϣ�ɹ�!");
				return "../admin/ok.jsp";
			}
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("failed", "ɾ����Ʒ��Ϣʧ��!");
			return "../admin/failed.jsp";
		} else {
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("msg", "ɾ����Ʒ��Ϣ������!");
			return "../admin/error.jsp";
		}
	}

	// ɾ��������Ʒ��Ϣ
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
				request.setAttribute("ok", "�޸���Ʒ��Ϣ�ɹ�!");
				return "../admin/ok.jsp";
			}
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("failed", "ɾ����Ʒ��Ϣʧ��!");
			return "../admin/failed.jsp";
		} else {
			request.setAttribute("jsp","/qlzx/servlet/GoodsManage?op=viewGoods");
			request.setAttribute("msg", "ɾ����Ʒ��Ϣ������!");
			return "../admin/error.jsp";
		}
	}

	/**
	 * ��ת�����ҳ��
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
	 * �����Ʒ
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
		
		System.out.println("��Ʒ����" + goodsname + ",����" + goodstype + ",�۸�"
				+ goodsprice + "," + discount + "�Ƽ�" + remark);
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
			request.setAttribute("ok", "�����Ʒ��Ϣ�ɹ�!");
			return "../admin/ok.jsp";
		}

		request.setAttribute("jsp", "/qlzx/servlet/GoodsManage?op=viewGoods");
		request.setAttribute("failed", "�����Ʒ��Ϣʧ��!");
		return "../admin/failed.jsp";
	}
	
	/**
	 * ǰ̨��Ʒ��Ϣģ����ѯ
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
		
		//�ж��ύ�����������get�ύʱת�룬����Ϊ����
		if(text != null && "GET".equals(request.getMethod())){
			//����get�ύ��ת�뷽��
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
