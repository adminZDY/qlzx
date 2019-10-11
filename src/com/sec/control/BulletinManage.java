package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sec.dao.BulletinDao;
import com.sec.model.BulletinInfo;
import com.sec.model.UserInfo;
import com.sec.util.PageModel;

public class BulletinManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BulletinManage() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		
		String returnURL = "";
		
		if(op == null || "".equals(op)){
			//��ҳ��ѯ
			returnURL = queryALL(request,response);
		}else{	
			//���ݹ�����id��ȡ��ѯ������Ϣ������ת���޸Ľ���
			if("query".equals(op)){
				returnURL =query(request,response);
			//������Ϣ��ѯ
			}else if("viewBulletin".equals(op)){
				returnURL =viewBulletin(request,response);
			//��ת����ӹ�����Ϣ����
			}else if ("toAdd".equals(op)){
				returnURL = toAdd(request,response);
			//�������ݿ����ӹ�����Ϣ
			}else if("add".equals(op)){
				returnURL = add(request,response);
			//���ݿ��޸Ĺ�����Ϣ�����ݹ���id�����޸�
			}else if("update".equals(op)){
				returnURL = update(request,response);
			//ɾ������������Ϣ
			}else if("remove".equals(op)){
				returnURL = remove(request,response);
			//ɾ������������Ϣ
			}else if("removeMore".equals(op)){
				returnURL = removeMore(request,response);			
			}else{
				request.setAttribute("msg", "������Դ�������ʸ���ԴλЯ����Ҫ�Ĳ�����");
				returnURL = "../abmin/error.jsp";
			}
		}
		request.getRequestDispatcher(returnURL).forward(request, response);
	}
	

	//��ҳ��ѯ����
	public String queryALL(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			int userid = 0;
			int pageSize = 7;
			int pageNo = 1;
			String sign = null;
			
			//ǰ̨������Ϣ��־
			sign = request.getParameter("sign");
			String text =request.getParameter("text");
			
			
			//�ж��ύ�����������get�ύʱת�룬����Ϊ����
			if(text != null && "GET".equals(request.getMethod())){
				//����get�ύ��ת�뷽��
				text = new String(request.getParameter("text").getBytes("iso-8859-1"),"utf-8");
			}
			if("null".equals(text)){
				text = "";
			}
			if(sign == null)
			{
				try{
					userid = ((UserInfo)request.getSession().getAttribute("user")).getUserId();
				}catch (Exception e) {
				// TODO: handle exception
				}
			}
			try{
				
				pageSize =Integer.parseInt(request.getParameter("pageSize"));
				pageNo =Integer.parseInt(request.getParameter("pageNo"));
				
			}catch(NumberFormatException e){}
		
			BulletinDao dao = new BulletinDao();
			PageModel<BulletinInfo> pm = dao.getAllBulletionInfo(pageSize, pageNo , text  ,userid);
			
			if(pm == null || "".equals(pm)){
				text = "";
				pm=dao.getAllBulletionInfo(pageSize, 1 , text , userid);
			}
			if(pm != null && pm.getData().isEmpty() && pageNo != 1){
				pm=dao.getAllBulletionInfo(pageSize, 1 , text , userid);
			}
			request.setAttribute("allBullein", pm);
	
			if(sign != null){
				return "../showBulletinList.jsp";
			}
			return "../admin/bulletinManage.jsp?text="+text;
	}
	
	//��ת���޸Ĺ������
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String id =request.getParameter("id");
		//�ж��Ƿ��ȡid�Ƿ�Ϊ��
		if(id != null){
			BulletinInfo buin = new BulletinInfo();
			BulletinDao dao = new BulletinDao();
			//��ѯ�����������ϸ��Ϣ
			buin = dao.add(Integer.parseInt(id));
			//����ѯ���Ĺ�����Ϣ�洢��request����
			request.setAttribute("buinfo", buin);
			return "../admin/updateBulletin.jsp";
		}
		
		return queryALL(request,response);
	}
	
	//������Ϣ��ѯ
	public String viewBulletin (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String id =request.getParameter("id");
	
		BulletinInfo buin = new BulletinInfo();
		BulletinDao dao = new BulletinDao();
		//��ѯ�����������ϸ��Ϣ
		buin = dao.add(Integer.parseInt(id));
		//����ѯ���Ĺ�����Ϣ�洢��request����
		request.setAttribute("buinfo", buin);
		
		return "../bulletin.jsp";
	}
	
	//��ת����ӹ�����Ϣ����
	public String toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

			return "../admin/addBulletin.jsp";
	}
	//��ӹ�����Ϣ
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		UserInfo info = (UserInfo)session.getAttribute("user"); 
		
		BulletinDao dao = new BulletinDao();
		
		if(dao.insert(title, content, info.getUserId())){
			//return queryALL(request,response);
			request.setAttribute("ok", "��ӹ�����Ϣ�ɹ�!");
			request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
			return "../admin/ok.jsp";
		}
		
		request.setAttribute("failed", "��ӹ�����Ϣʧ��!");
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		return "../admin/failed.jsp";
	}
	
	/*
	 * ���ݿ��޸Ĺ�����Ϣ
	 */
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("id"));//���
		String title = request.getParameter("title");//����
		String content = request.getParameter("content");//����
	
		
		BulletinDao dao = new BulletinDao();
		if(dao.update(title, content, id)){
			
			//return queryALL(request,response);
			request.setAttribute("ok", "�޸Ĺ�����Ϣ�ɹ�!");
			request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
			return "../admin/ok.jsp";
		}
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		request.setAttribute("failed","�޸Ĺ�����Ϣʧ��!");
		return "../admin/failed.jsp";		
		
	}
	
	//ɾ��������Ϣ������
	public String remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		int id =Integer.parseInt(request.getParameter("id"));//���
		
		BulletinDao dao = new BulletinDao();
		if(dao.delete(id)){
			//return queryALL(request,response);
			request.setAttribute("ok", "ɾ��������Ϣ�ɹ�!");
			request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
			return "../admin/ok.jsp";
		}
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		request.setAttribute("failed", "ɾ��������Ϣʧ��!");
		return "../admin/failed.jsp";
	}

	
	
	//ɾ������������Ϣ
	public String removeMore (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		int sum = 0;
		String[] chk = request.getParameterValues("chkItems");
		
		BulletinDao dao = null;
		
		if(chk != null){
			for(int i = 0 ; i < chk.length ; i++){
				dao = new BulletinDao();
				if(dao.delete(Integer.parseInt(chk[i]))){
					sum++;
				}
			}
			if(sum == chk.length){
				//return queryALL(request,response);
				request.setAttribute("ok", "ɾ��������Ϣ�ɹ�!");
				request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
				return "../admin/ok.jsp";
			}else{
				request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
				request.setAttribute("failed", "ɾ��������Ϣʧ��!");
				return "../admin/failed.jsp";
			}
		}
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		request.setAttribute("failed", "ɾ����Ʒ����ʧ��!");
		return "../admin/failed.jsp";
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
