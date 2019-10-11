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
			//分页查询
			returnURL = queryALL(request,response);
		}else{	
			//根据公告编号id获取查询公告信息，便跳转到修改界面
			if("query".equals(op)){
				returnURL =query(request,response);
			//公告信息查询
			}else if("viewBulletin".equals(op)){
				returnURL =viewBulletin(request,response);
			//跳转到添加公告信息界面
			}else if ("toAdd".equals(op)){
				returnURL = toAdd(request,response);
			//进行数据库的添加公告信息
			}else if("add".equals(op)){
				returnURL = add(request,response);
			//数据库修改公告信息，根据公告id进行修改
			}else if("update".equals(op)){
				returnURL = update(request,response);
			//删除单条公告信息
			}else if("remove".equals(op)){
				returnURL = remove(request,response);
			//删除多条公告信息
			}else if("removeMore".equals(op)){
				returnURL = removeMore(request,response);			
			}else{
				request.setAttribute("msg", "访问资源出错！访问该资源位携带必要的参数！");
				returnURL = "../abmin/error.jsp";
			}
		}
		request.getRequestDispatcher(returnURL).forward(request, response);
	}
	

	//分页查询所有
	public String queryALL(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			int userid = 0;
			int pageSize = 7;
			int pageNo = 1;
			String sign = null;
			
			//前台公告信息标志
			sign = request.getParameter("sign");
			String text =request.getParameter("text");
			
			
			//判断提交方法，如果是get提交时转码，否则将为乱码
			if(text != null && "GET".equals(request.getMethod())){
				//这是get提交的转码方法
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
	
	//跳转到修改公告界面
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String id =request.getParameter("id");
		//判断是否获取id是否不为空
		if(id != null){
			BulletinInfo buin = new BulletinInfo();
			BulletinDao dao = new BulletinDao();
			//查询单个公告的详细信息
			buin = dao.add(Integer.parseInt(id));
			//将查询到的公告信息存储到request对象
			request.setAttribute("buinfo", buin);
			return "../admin/updateBulletin.jsp";
		}
		
		return queryALL(request,response);
	}
	
	//公告信息查询
	public String viewBulletin (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String id =request.getParameter("id");
	
		BulletinInfo buin = new BulletinInfo();
		BulletinDao dao = new BulletinDao();
		//查询单个公告的详细信息
		buin = dao.add(Integer.parseInt(id));
		//将查询到的公告信息存储到request对象
		request.setAttribute("buinfo", buin);
		
		return "../bulletin.jsp";
	}
	
	//跳转到添加公告信息界面
	public String toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

			return "../admin/addBulletin.jsp";
	}
	//添加公告信息
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		UserInfo info = (UserInfo)session.getAttribute("user"); 
		
		BulletinDao dao = new BulletinDao();
		
		if(dao.insert(title, content, info.getUserId())){
			//return queryALL(request,response);
			request.setAttribute("ok", "添加公告信息成功!");
			request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
			return "../admin/ok.jsp";
		}
		
		request.setAttribute("failed", "添加公告信息失败!");
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		return "../admin/failed.jsp";
	}
	
	/*
	 * 数据库修改公告信息
	 */
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("id"));//编号
		String title = request.getParameter("title");//标题
		String content = request.getParameter("content");//内容
	
		
		BulletinDao dao = new BulletinDao();
		if(dao.update(title, content, id)){
			
			//return queryALL(request,response);
			request.setAttribute("ok", "修改公告信息成功!");
			request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
			return "../admin/ok.jsp";
		}
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		request.setAttribute("failed","修改公告信息失败!");
		return "../admin/failed.jsp";		
		
	}
	
	//删除公告信息（单）
	public String remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		int id =Integer.parseInt(request.getParameter("id"));//编号
		
		BulletinDao dao = new BulletinDao();
		if(dao.delete(id)){
			//return queryALL(request,response);
			request.setAttribute("ok", "删除公告信息成功!");
			request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
			return "../admin/ok.jsp";
		}
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		request.setAttribute("failed", "删除公告信息失败!");
		return "../admin/failed.jsp";
	}

	
	
	//删除多条公告信息
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
				request.setAttribute("ok", "删除公告信息成功!");
				request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
				return "../admin/ok.jsp";
			}else{
				request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
				request.setAttribute("failed", "删除公告信息失败!");
				return "../admin/failed.jsp";
			}
		}
		request.setAttribute("jsp", "/qlzx/servlet/BulletinManage");
		request.setAttribute("failed", "删除商品类型失败!");
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
