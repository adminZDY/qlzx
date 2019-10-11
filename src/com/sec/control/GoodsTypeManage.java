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
				// 模糊查询
				returnURL = query(request, response);
			} else if ("viewGoodsType".equals(op)) {
				// 显示部分类型【前台】
				returnURL = viewBulletin(request, response);
			} else if ("toAdd".equals(op)) {
				// 跳转到添加页面
				returnURL = toAdd(request, response);
			} else if ("add".equals(op)) {
				// 添加商品类型的过滤器【数据操作】
				returnURL = add(request, response);
			} else if ("toEdit".equals(op)) {
				// 跳转到修改页面
				returnURL = toEdit(request, response);
			} else if ("update".equals(op)) {
				// 修改商品类型的过滤器【数据操作】
				returnURL = update(request, response);
			} else if ("remove".equals(op)) {
				// 删除单条数据
				returnURL = remove(request, response);
			} else if ("removeMore".equals(op)) {
				// 删除多条数据
				returnURL = removeMore(request, response);
			}
			else {
				String URL = "";
				// 跳转到error（错误）页面
				request.setAttribute("msg", "访问资源出错了！访问该资源未携带必要的参数");
				request.setAttribute("jsp", URL);
				returnURL = "/error.jsp";
			}
		}
		/**
		 * ajax验证名称是否已经存在
		 */
		if("checkType".equals(op)) {
			checkType(request, response);
		}
		else {
			//跳转
			request.getRequestDispatcher(returnURL).forward(request, response);
		}
	}

	/**
	 * 查询所有商品类型
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
		System.out.println("页码"+pageNo+"页面大小"+pageSize);
		
		//数据访问
		GoodsTypeDao dao = new GoodsTypeDao();
		PageModel<GoodsType> pm = dao.getAllGoodsType(pageSize, pageNo);
		// 当某页面没有查询的出数据 则查询第一个页面的数据
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.getAllGoodsType(pageSize, 1);
		}
		request.setAttribute("alltype", pm);

		return "/admin/goodsTypeManage.jsp";
	}
	
	/**
	 * 模糊查询
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
		
		//数据访问
		GoodsTypeDao dao = new GoodsTypeDao();
		PageModel<GoodsType> pm = dao.getAllGoodsType(pageSize, pageNo, text);
		// 当某页面没有查询的出数据 则查询第一个页面的数据
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.getAllGoodsType(pageSize, 1, text);
		}
		request.setAttribute("alltype", pm);

		return "/admin/goodsTypeManage.jsp";
	}

	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	private String toAdd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return "/admin/addGoodsType.jsp";
	}

	/**
	 * 添加方法
	 * @param request
	 * @param response
	 * @return
	 */
	private String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 添加方法
		GoodsType type = new GoodsType();
		String text = request.getParameter("typeName");
		type.setTypeName(text);

		GoodsTypeDao dao = new GoodsTypeDao();
		if (dao.insert(type)) {
			return queryAll(request, response);
		}
		// 跳转到error（错误）页面
		request.setAttribute("error", "添加失败");
		return "/error.jsp";
	}

	/**
	 * 跳转到修改的页面
	 * @param request
	 * @param response
	 * @return
	 */
	private String toEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		GoodsType goodsType = null;
		int id = (Integer.parseInt(request.getParameter("id")));
		
		GoodsTypeDao goods = new GoodsTypeDao();
		
		goodsType = goods.selectid(id);
		System.out.println("测试"+goodsType.getTypeId()+"  "+goodsType.getTypeName());
		request.setAttribute("goodsType", goodsType);
		// System.out.println("sfds");
		return "/admin/updateGoodsType.jsp";
	}

	/**
	 * 修改条数据
	 * @param request
	 * @param response
	 * @return
	 */
	private String update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException  {
			//修改
			int typeId = 0;
			try {
				typeId = Integer.parseInt(request.getParameter("typeId"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			String typeName = request.getParameter("typeName");
			System.out.println(typeId+"名字"+typeName);
			//String name = request.getParameter("updatatx");
			 GoodsTypeDao dao = new GoodsTypeDao();
			 
			if (dao.update(typeId,typeName)) {
				return queryAll(request, response);
			}
			else {
				request.setAttribute("failed", "修改类型失败!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/failed.jsp";
			}
	}

	/**
	 * 删除多条数据
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
		//查询在商品信息中是否有我选择删除例其中一个编号
		//如果有查询到则说明，该类型有商品信息，不允许删除，跳转到错误页面
		if(goodsTypeDao.delteFilter(id))
		{
			//查询到——>错误页面
			request.setAttribute("msg", "删除商品类型错误!，该类型已经有商品信息了");
			request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
			return "/admin/error.jsp";
		}
		else {
			//可以执行删除操作
			//删除成功
		    if(goodsTypeDao.deletes(id))
			{
				//添加成功，操作成功
				request.setAttribute("ok", "删除客户信息成功!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			//删除失败
			else {
				//添加失败，操作失败
				request.setAttribute("failed", "删除客户信息失败!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		}
	}

	/**
	 * 删除单挑数据
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
		//查询在商品信息中是否有我选择删除例其中一个编号
		//如果有查询到则说明，该类型有商品信息，不允许删除，跳转到错误页面
		if(goodsTypeDao.delteFilter(id))
		{
			//查询到——>错误页面
			request.setAttribute("msg", "删除商品类型错误!，你所选要删除类型已经有商品信息了");
			request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
			return "/admin/error.jsp";
		}
		else {
			//可以执行删除操作
			//删除成功
		    if(goodsTypeDao.remove(id))
			{
				//删除成功，操作成功
				request.setAttribute("ok", "删除客户信息成功!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			//删除失败
			else {
				//删除失败，操作失败
				request.setAttribute("failed", "删除客户信息失败!");
				request.setAttribute("jsp", "/qlzx/servlet/GoodsTypeManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		} 
	}

	/**
	 * 模糊查询
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
		// 当某页面没有查询的出数据 则查询第一个页面的数据
		if (pm != null && pm.getData().isEmpty() && pageNo != 1) {
			pm = dao.getAllGoodsType(pageSize, 1, text);
		}

		request.setAttribute("alltype", pm);

		return "../goodsTypeManage.jsp";

	}
	
	/**
	 * 验证该类型名称是否已经存在
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
		System.out.println("检查名称");
		System.out.println(typeId);
		System.out.println(flag);
		//查询到，不允许添加或修改
		if(flag)
		{
			System.out.println("不允许添加");
			out.print("no");
		}else{
			System.out.println("允许添加");
			out.print("ok");
		}
	}
}
