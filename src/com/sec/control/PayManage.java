package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.sec.dao.OrderInfoDao;
import com.sec.model.Cart;
import com.sec.model.CustomerDatailInfo;
import com.sec.model.CustomerInfo;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.OrderInfo;
import com.sec.util.DateTimeUtil;

public class PayManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PayManage() {
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
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
//		HttpSession session = request.getSession();
//		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
//		CustomerInfo customer = new CustomerInfo();
//		customer.setId(1);
//		customer.setEmail("a@sina.com");
//		customer.setPwd("123456");
//		customer.setRegisterTime(DateTimeUtil.convertDate("2009-06-06 12:30:45.000"));
//		
//		CustomerDatailInfo customerDatail = new CustomerDatailInfo();
//		customerDatail.setName("����");
//		customerDatail.setTelphone("0001-32456754");
//		customerDatail.setMobileTelphone("13534563234");
//		customerDatail.setAddress("����������������·1��");
//		
//		customer.setCustomerDetail(customerDatail);
		
		//session.setAttribute("customer", customer);
		
		String op = request.getParameter("op");
		String returnURL = "";
		//ȷ�϶���
		if("add".equals(op)){
			addOrder(request, response);
		}
		else if("updatePay".equals(op)){
			updatePay(request, response);
		}
		else{
			if("confirm".equals(op)){
				returnURL = confirm(request,response);
			}
			else if("toPay".equals(op)){
				/**
				 * ��ת֧��ҳ��
				 */
				toPay(request, response);
			}
			else{
				request.setAttribute("msg", "����������ĳ���ȱ�ٱ�Ҫ�Ĳ�����");
				returnURL="../failed.jsp";
			}
			request.getRequestDispatcher(returnURL).forward(request, response);
		}
	}
	
	/**
	 * ����ȷ�϶���ҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		//���ﳵΪ�գ����ع��ﳵ
		if(cart == null || cart.getCart().isEmpty()){
			return "../cart.jsp";
		}
		//����һ����ʶ����ʾ�ͻ���ǰ�Ķ�����ǰ��ȷ������ҳ��
		session.setAttribute("toGo", "confirmOrder");
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
		  //�û�δ��¼��ת��¼ҳ��
		  if(customer == null){
		  	return "../login_register.jsp";
		  //�û���ϸ��ַΪ�գ���ת��д������ϸҳ��
		  }else if(customer.getCustomerDatail().getName() == null){
		  	//�ͻ�δ��д������Ϣ
		  	 return "../peisong.jsp";
		  }else{
			  //�û���ת��֧��ҳ��
		  	return "../confirm.jsp";
		  }
	}

	/**
	 * ��ת֧��ҳ��
	 * @return
	 */
	public void toPay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 //��ó�ʼ����AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
      //�����������
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        HttpSession session = request.getSession();
        
        //�̻������ţ��̻���վ����ϵͳ��Ψһ�����ţ�����
        String out_trade_no = request.getParameter("WIDout_trade_no");
        //���������
        String total_amount = request.getParameter("WIDtotal_amount");
        //�������ƣ�����
        String subject = "��Ʒ����";//request.getParameter("WIDsubject");
        //��Ʒ�������ɿ�
        String body = "��Ʒ����";
        
        System.out.println(out_trade_no);
        System.out.println(total_amount);
        System.out.println(subject);
        System.out.println(body);
        
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
                + "\"total_amount\":\""+ total_amount +"\"," 
                + "\"subject\":\""+ subject +"\"," 
                + "\"body\":\""+ body +"\"," 
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //����
        String result;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.charset);
            response.getWriter().write(result);//ֱ�ӽ������ı�html�����ҳ��
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            response.getWriter().write("�����쳣����");
            response.getWriter().flush();
            response.getWriter().close();
        }
	}
	
	/**
	 * ��ת֧��ҳ��
	 * @return
	 */
	public void updatePay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 //��ó�ʼ����AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
      //�����������
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        HttpSession session = request.getSession();
        
        //�̻������ţ��̻���վ����ϵͳ��Ψһ�����ţ�����
        String out_trade_no = request.getParameter("WIDout_trade_no");
        //���������
        String total_amount = request.getParameter("WIDtotal_amount");
        //�������ƣ�����
        String subject = "��Ʒ����";//request.getParameter("WIDsubject");
        //��Ʒ�������ɿ�
        String body = "��Ʒ����";
        request.getSession().setAttribute("orderId",request.getParameter("orderId"));
        
        System.out.println(out_trade_no);
        System.out.println(total_amount);
        System.out.println(subject);
        System.out.println(body);
        
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
                + "\"total_amount\":\""+ total_amount +"\"," 
                + "\"subject\":\""+ subject +"\"," 
                + "\"body\":\""+ body +"\"," 
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //����
        String result;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.charset);
            response.getWriter().write(result);//ֱ�ӽ������ı�html�����ҳ��
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            response.getWriter().write("�����쳣����");
            response.getWriter().flush();
            response.getWriter().close();
        }
	}
	
	/**
	 * �¶������ܣ��������ݿ⣩
	 * ��Ӷ�����Ϣ����ת֧��ҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		//��session���Ƴ���ʶ
		session.removeAttribute("toGo");
		//ȡ���Ự�еĵ�¼�ͻ���Ϣ
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
		//ȡ�����ﳵ�е���Ʒ����goodsList��
		Cart cart = (Cart)session.getAttribute("cart");
		ArrayList<OrderGoodsInfo> goodsList = new ArrayList<OrderGoodsInfo>();
		Iterator<OrderGoodsInfo> values = cart.getCart().values().iterator();
		while(values.hasNext()){
			goodsList.add(values.next());
		}
		//��װ������Ϣ
		OrderInfo order = new OrderInfo();
		order.setStatus(0);//����״̬Ϊδȷ��
		order.setCustomer(customer);//�����û���Ϣ
		order.setOrderDatails(goodsList);//���ö�����Ϣ
		//�������ݷ�������Ӷ��������¶���
		OrderInfoDao dao = new OrderInfoDao();
		boolean flag = dao.addOrder(order);
		
//		//�����ɹ�
		if(flag){
			//��չ��ﳵ
			cart.clearCart();
			//�洢������Ϣ
			session.setAttribute("orderInfo", order);
			//��ת��֧��ҳ��
			toPay(request, response);
		}else{
			request.setAttribute("msg", "�Բ��𣬶����쳣��");
			request.setAttribute("jsp","/qlzx/cart.jsp" );
			request.getRequestDispatcher("../fainled.jsp").forward(request, response);
		}
	}
}
