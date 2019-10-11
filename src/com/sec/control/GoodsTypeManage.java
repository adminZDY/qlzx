package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sec.dao.GoodsTypeDao;
import com.sec.model.*;
import com.sec.util.*;

public class GoodsTypeManage extends HttpServlet {

	/**
	 * 
	 */
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		System.out.println(op);
		String returnURL = "";
		if (op == null || "".equals(op) || "queryAll".equals(op)) {
			returnURL = queryAll(request, response);
		} else {
			
			if ("query".equals(op)) {
				// ģ����ѯ
				returnURL = query(request, response);
			} else if ("viewGoodsType".equals(op)) {
				// ��ʾ�������͡�ǰ̨��
				returnURL = viewBulletin(request, response);
			} else if ("toAdd".equals(op)) {
				// ��ת�����ҳ��
				returnURL = toAdd(request, response);
			} else if ("add".equals(op)) {
				// �����Ʒ���͵Ĺ����������ݲ�����
				returnURL = add(request, response);
			} else if ("toEdit".equals(op)) {
				// ��ת���޸�ҳ��
				returnURL = toEdit(request, response);
			} else if ("update".equals(op)) {
				// �޸���Ʒ���͵Ĺ����������ݲ�����
				returnURL = update(request, response);
			} else if ("remove".equals(op)) {
				// ɾ����������
				returnURL = remove(request, response);
			} else if ("removeMore".equals(op)) {
				// ɾ����������
				returnURL = removeMore(request, response);
			}
			else {
				String URL = "";
				// ��ת��error������ҳ��
				request.setAttribute("msg", "������Դ�����ˣ����ʸ���ԴδЯ����Ҫ�Ĳ���");
				request.setAttribute("jsp", URL);
				returnURL = "/error.jsp";
			}
		}
		/**
		 * ajax��֤�����Ƿ��Ѿ�����
		 */
		if("checkType".equals(op)) {
			checkType(request, response);
		}
		else {
			//��ת
			request.getRequestDispatcher(returnURL).forward(request, response);
		}
	}

	/**
	 * ��ѯ������Ʒ����
	 * @param request
	 * @param response
	 * @return
	 */
	private String queryAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageSize = 7;
		int pageNo = 1;
		
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {}
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {}
		System.out.println("ҳ��"+pageNo+"ҳ���С"+pageSize);
		
		//���ݷ���
		GoodsTypeDao dao = new GoodsTypeDao();
		PageModel<GoodsType> pm = dao.getAllGoodsType(pageSize, pageNo);
		// ��ĳҳ��û�в�ѯ�ĳ����� ���ѯ��һ��ҳ�������
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.getAllGoodsType(pageSize, 1);
		}
		request.setAttribute("alltype", pm);

		return "/admin/goodsTypeManage.jsp";
	}
	
	/**
	 * ģ����ѯ
	 * @param request
	 * @param response
	 * @return
	 */
	private String query(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageSize = 7;
		int pageNo = 1;
		String text = request.getParameter("text");
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {}
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {}
		
		//���ݷ���
		GoodsTypeDao dao = new GoodsTypeDao();
		PageModel<GoodsType> pm = dao.getAllGoodsType(pageSize, pageNo, text);
		// ��ĳҳ��û�в�ѯ�ĳ����� ���ѯ��һ��ҳ�������
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.getAllGoodsType(pageSize, 1, text);
		}
		request.setAttribute("alltype", pm);

		return "/admin/goodsTypeManage.jsp";
	}

	/**
	 * ��ת�����ҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	private String toAdd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return "/admin/addGoodsType.jsp";
	}

	/**
	 * ��ӷ���
	 * @param request
	 * @param response
	 * @return
	 */
	private String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ӷ���
		GoodsType type = new GoodsType();
		String text = request.getParameter("typeName");
		type.setTypeName(text);

		GoodsTypeDao dao = new GoodsTypeDao();
		if (dao.insert(type)) {
			return queryAll(request, response);
		}
		// ��ת��error������ҳ��
		request.setAttribute("error", "���ʧ��");
		return "/error.jsp";
	}

	/**
	 * ��ת���޸ĵ�ҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	private String toEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		GoodsType goodsType = null;
		int id = (Integer.parseInt(request.getParameter("id")));
		
		GoodsTypeDao goods = new GoodsTypeDao();
		
		goodsType = goods.selectid(id);
		System.out.println("����"+goodsType.getTypeId()+"  "+goodsType.getTypeName());
		request.setAttribute("goodsType", goodsType);
		// System.out.println("sfds");
		return "/admin/updateGoodsType.jsp";
	}

	/**
	 * �޸�������
	 * @param request
	 * @param response
	 * @return
	 */
	private String update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException  {
			//�޸�
			int typeId = 0;
			try {
				typeId = Integer.parseInt(request.getParameter("typeId"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			String typeName = request.getParameter("typeName");
			System.out.println(typeId+"����"+typeName);
			//String name = request.getParameter("updatatx");
			 GoodsTypeDao dao = new GoodsTypeDao();
			 
			if (dao.update(typeId,typeName)) {
				return queryAll(request, response);
			}
			else {
				request.setAttribute("failed", "�޸�����ʧ��!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/failed.jsp";
			}
	}

	/**
	 * ɾ����������
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String removeMore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String []idString = request.getParameterValues("chkItems");
		int [] id = new int[idString.length];  
		for (int i = 0; i < idString.length; i++) {
			id[i] = Integer.parseInt(idString[i]);
		}
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		//��ѯ����Ʒ��Ϣ���Ƿ�����ѡ��ɾ��������һ�����
		//����в�ѯ����˵��������������Ʒ��Ϣ��������ɾ������ת������ҳ��
		if(goodsTypeDao.delteFilter(id))
		{
			//��ѯ������>����ҳ��
			request.setAttribute("msg", "ɾ����Ʒ���ʹ���!���������Ѿ�����Ʒ��Ϣ��");
			request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
			return "/admin/error.jsp";
		}
		else {
			//����ִ��ɾ������
			//ɾ���ɹ�
		    if(goodsTypeDao.deletes(id))
			{
				//��ӳɹ��������ɹ�
				request.setAttribute("ok", "ɾ���ͻ���Ϣ�ɹ�!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			//ɾ��ʧ��
			else {
				//���ʧ�ܣ�����ʧ��
				request.setAttribute("failed", "ɾ���ͻ���Ϣʧ��!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		}
	}

	/**
	 * ɾ����������
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		try {
			id = (Integer.parseInt(request.getParameter("id")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		//��ѯ����Ʒ��Ϣ���Ƿ�����ѡ��ɾ��������һ�����
		//����в�ѯ����˵��������������Ʒ��Ϣ��������ɾ������ת������ҳ��
		if(goodsTypeDao.delteFilter(id))
		{
			//��ѯ������>����ҳ��
			request.setAttribute("msg", "ɾ����Ʒ���ʹ���!������ѡҪɾ�������Ѿ�����Ʒ��Ϣ��");
			request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
			return "/admin/error.jsp";
		}
		else {
			//����ִ��ɾ������
			//ɾ���ɹ�
		    if(goodsTypeDao.remove(id))
			{
				//ɾ���ɹ��������ɹ�
				request.setAttribute("ok", "ɾ���ͻ���Ϣ�ɹ�!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			//ɾ��ʧ��
			else {
				//ɾ��ʧ�ܣ�����ʧ��
				request.setAttribute("failed", "ɾ���ͻ���Ϣʧ��!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		} 
	}

	/**
	 * ģ����ѯ
	 */
	private String viewBulletin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String text = "";
		int pageSize = 5;
		int pageNo = 1;
		try {

			text = request.getParameter("text");
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			pageNo = Integer.parseInt(request.getParameter("pageNo"));

		} catch (Exception e) {
		}

		GoodsTypeDao dao = new GoodsTypeDao();
		PageModel<GoodsType> pm = dao.getAllGoodsType(pageSize, pageNo, text);
		// ��ĳҳ��û�в�ѯ�ĳ����� ���ѯ��һ��ҳ�������
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.getAllGoodsType(pageSize, 1, text);
		}

		request.setAttribute("alltype", pm);

		return "../goodsTypeManage.jsp";

	}
	
	/**
	 * ��֤�����������Ƿ��Ѿ�����
	 * @return
	 */
	private void checkType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		String typeName = request.getParameter("checkType");
		PrintWriter out = response.getWriter();
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		int typeId = 0;
		try {
			typeId = Integer.parseInt(request.getParameter("typeId"));
		} catch (Exception e) {}
		flag = goodsTypeDao.checkType(typeId, typeName);
		System.out.println("�������");
		System.out.println(typeId);
		System.out.println(flag);
		//��ѯ������������ӻ��޸�
		if(flag)
		{
			System.out.println("���������");
			out.print("no");
		}else{
			System.out.println("�������");
			out.print("ok");
		}
	}
}
