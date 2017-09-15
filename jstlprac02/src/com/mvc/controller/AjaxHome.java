package com.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Forward;
import model.ProductM;
import service.MovingPage;

@WebServlet({"/ajaxDetail"})
public class AjaxHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doStart(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doStart(request, response);
	}
	protected void doStart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Forward fw = null;
		String conPath = request.getContextPath();
		int len=conPath.length();
		String uri=request.getRequestURI();
		String url=uri.substring(len);
		MovingPage mp = null;
		String jsonStr=null;
		ProductM pm = null;
		String htmlStr = null;
		switch(url){
		case "/ajaxDetail":
			//request.setAttribute("page", "main.jsp");
			pm = new ProductM(request, response);
			htmlStr = pm.executeAjax(1);//ajax용 컨트롤러(제어 메소드)
			request.setAttribute("htmlStr", htmlStr);
			fw=new Forward();
			fw.setRedirect(false);
			fw.setPath("detailForm.jsp");
			RequestDispatcher dis = request.getRequestDispatcher(fw.getPath());
			dis.forward(request, response);
			break;
		/*case "/detail":
			//request.setAttribute("page", "main.jsp");
			pm = new ProductM(request, response);
			htmlStr = pm.executeAjax(2);//ajax용 컨트롤러(제어 메소드)
			break;*/
		default:
			break;
		}
		if(htmlStr!=null){
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(htmlStr);
		}
		/*if(fw!=null){
			if(fw.isRedirect()){
				response.sendRedirect(fw.getPath());
			}else{
				RequestDispatcher dis = request.getRequestDispatcher(fw.getPath());
				dis.forward(request, response);
			}
		}*/
	}
}
