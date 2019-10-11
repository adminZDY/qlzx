package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.Card;

import com.sec.dao.BulletinDao;
import com.sec.dao.CustomerInfoDao;
import com.sec.dao.OrderInfoDao;
import com.sec.model.BulletinInfo;
import com.sec.model.CustomerInfo;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.OrderInfo;
import com.sec.util.PageModel;

public class OrderManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public OrderManage() {
		super();
	}

	/**
	 * ����ҵ���services
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		String returnURL = "";
		System.out.println(op);
		if(op == null || "".equals(op) || "queryAll".equals(op)){
			//��ҳ��ѯ
			returnURL = queryALL(request,response);
		}else{
			//���ݹ�����id��ȡ��ѯ������Ϣ������ת���޸Ľ���
			if("query".equals(op)){
				returnURL =query(request,response);
			//������Ϣ��ѯ
			}else if("update".equals(op)){
				returnURL = update(request,response);
			//ɾ������������Ϣ
			}else if("remove".equals(op)){
				returnURL = remove(request,response);
			//ɾ������������Ϣ
			}else if("removeMore".equals(op)){
				returnURL = removeMore(request,response);			
			}else if("myManage".equals(op)){
				returnURL = queryAll(request,response);
			}else if("statusChange".equals(op)){
				//ǰ̨�޸�״̬
				returnURL = statusChange(request,response);
			}
			else{
				request.setAttribute("msg", "������Դ�������ʸ���ԴλЯ����Ҫ�Ĳ�����");
				returnURL = "../abmin/error.jsp";
			}
		}
		
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

	/**
	 * ��ҳ��ѯ����
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryALL(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
			int pageSize = 7;
			int pageNo = 1;
			String sign = null;
				
			sign = request.getParameter("sign");
			String text =request.getParameter("text");
				
			//�ж��ύ�����������get�ύʱת�룬����Ϊ����
			if(text != null && "GET".equals(request.getMethod())){
				//����get�ύ��ת�뷽��
				text = new String(request.getParameter("text").getBytes("iso-8859-1"),"utf-8");
			}
			if("null".equals(text)||"".equals(text)){
				text = "";
			}
			
			try{
				pageSize =Integer.parseInt(request.getParameter("pageSize"));
				pageNo =Integer.parseInt(request.getParameter("pageNo"));
					
			}catch(NumberFormatException e){}
			
			OrderInfoDao dao = new OrderInfoDao();
			PageModel<OrderGoodsInfo> pm = dao.getAllBulletionInfo(pageSize, pageNo , text );
			
			if(pm == null || "".equals(pm)){
				text = "";
				pm=dao.getAllBulletionInfo(pageSize, 1 , text);
			}
			if(pm != null && pm.getData().isEmpty() && pageNo != 1){
				pm=dao.getAllBulletionInfo(pageSize, 1 , text);
			}
			
			request.setAttribute("allBullein", pm);
		
			if(sign != null){
				return "../showBulletinList.jsp";
			}
			return "../admin/orderManage.jsp?text="+text;
	}	
	
	/**
	 * ������ϸ��Ϣ��������
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String customerid = request.getParameter("customerid");
		String orderid = request.getParameter("orderid");
		// �ж��Ƿ��ȡid�Ƿ�Ϊ��
		if (customerid != null && orderid != null) {

			CustomerInfoDao customrDao = new CustomerInfoDao();
			//�����ͻ���Ϣ
			OrderInfo orderinfo = customrDao.Order_Customer(
					Integer.parseInt(customerid), Integer.parseInt(orderid));

			OrderInfoDao dao = new OrderInfoDao();
			ArrayList<OrderGoodsInfo> orderList = new ArrayList<OrderGoodsInfo>();
			// ��ѯ����������Ʒ����ϸ��Ϣ
			orderList = dao.add(Integer.parseInt(customerid),
					Integer.parseInt(orderid));

			// ����ѯ���Ĺ�����Ϣ�洢��request����
			request.setAttribute("orderinfo", orderinfo);
			request.setAttribute("orderList", orderList);

			return "../admin/showOrderDetail.jsp";
		}

		return queryALL(request, response);
	}
	
	/**
	 * ���ݿ��޸Ķ���״̬
	 */
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("id"));//���
		String status = request.getParameter("Status");//����

		OrderInfoDao dao = new OrderInfoDao();
		if(dao.update(Integer.parseInt(status), id)){
			
			return queryALL(request,response);
		}
		request.setAttribute("failed","�޸Ķ�����Ϣʧ��!");
		return "../admin/failed.jsp";		
		
	}

	/**
	 * ǰ̨������
	 * �޸Ķ���״̬[0��ʾδ����][1�û�����ɹ�][2�̼��ѷ���][3�û�ȷ���ջ�][4�û����ۡ��������׳ɹ�]
	 */
	public String statusChange(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("orderId"));//���
		String status = request.getParameter("status");//״̬

		OrderInfoDao dao = new OrderInfoDao();
		if(dao.update(Integer.parseInt(status), id)){
			
		
			return queryAll(request,response);
		}
		request.setAttribute("failed","�޸Ķ�����Ϣʧ��!");
		return "../admin/failed.jsp";		
		
	}
	
	
	/**
	 * ɾ��������Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));// ���

		OrderInfoDao orderInfoDao = new OrderInfoDao();
		System.out.println("ɾ��");
		// ��ѯ����Ʒ��Ϣ���Ƿ�����ѡ��ɾ��������һ�����
		// ����в�ѯ����˵��������������Ʒ��Ϣ��������ɾ������ת������ҳ��
		if (orderInfoDao.delteFilter(id)) {
			// ��ѯ������>����ҳ��
			request.setAttribute("msg", "ɾ����������!������ѡҪɾ������δȷ��");
			request.setAttribute("jsp",
					"/qlzx/servlet/OrderManage?op=queryAll");
			return "/admin/error.jsp";
		} else {
			// ����ִ��ɾ������
			// ɾ���ɹ�
			if (orderInfoDao.delete(id)) {
				// ɾ���ɹ��������ɹ�
				request.setAttribute("ok", "ɾ��������Ϣ�ɹ�!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			// ɾ��ʧ��
			else {
				// ɾ��ʧ�ܣ�����ʧ��
				request.setAttribute("failed", "ɾ��������Ϣʧ��!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		}
	}

	/**
	 *  ɾ������������Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String removeMore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] chk = request.getParameterValues("chkItems");
		int[] orderId = new int[chk.length];
		OrderInfoDao orderInfoDao = new OrderInfoDao();

		if (chk != null) {
			for (int i = 0; i < chk.length; i++) {
				orderId[i] = Integer.parseInt(chk[i]);
			}
		}
//		request.setAttribute("failed", "ɾ����Ʒ����ʧ��!");
//		return "../admin/failed.jsp";
		// ��ѯ����Ʒ��Ϣ���Ƿ�����ѡ��ɾ��������һ�����
		// ����в�ѯ����˵��������������Ʒ��Ϣ��������ɾ������ת������ҳ��
		if (orderInfoDao.delteFilter()) {
			// ��ѯ������>����ҳ��
			request.setAttribute("msg", "ɾ����������!������ѡҪɾ������δȷ��");
			request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
			return "/admin/error.jsp";
		} else {
			// ����ִ��ɾ������
			// ɾ���ɹ�
			if (orderInfoDao.delteFilter(orderId)) {
				// ɾ���ɹ��������ɹ�
				request.setAttribute("ok", "ɾ��������Ϣ�ɹ�!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			// ɾ��ʧ��
			else {
				// ɾ��ʧ�ܣ�����ʧ��
				request.setAttribute("failed", "ɾ��������Ϣʧ��!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		}
	}
	
	/**
	 * �ҵ���Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String customerid =request.getParameter("customerid");
	
		//�ж��Ƿ��ȡid�Ƿ�Ϊ��
		if(customerid != null){
			
			OrderInfoDao dao = new OrderInfoDao();
			ArrayList<OrderGoodsInfo> orderList = new ArrayList<OrderGoodsInfo>();
			orderList = dao.addGoods(Integer.parseInt(customerid));
			
			//����ѯ������Ϣ�洢��request����
			request.setAttribute("orderList", orderList);
			
			return "../myMessage.jsp";
		}
		
		return "../login_register.jsp";
	}
}
