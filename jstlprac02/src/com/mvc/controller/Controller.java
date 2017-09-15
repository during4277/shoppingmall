package com.mvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Forward;
import model.ProductM;
import service.MovingPage;
// '/' 루트 url은 톰캣이 사용하는 것.
//프로젝트에 web.xml이 없다면 서버의 web.xml을 영향 받음
//결론: '/'는 톰캣이 사용하므로 개발자가 사용하면 톰캣에 오류가 날 가능성이 높다.
@WebServlet({"/index", "/basketBtn", "/basketMsg","/deleteOrder", "/toOrder", "/deleteBasket", "/viewBasket", "/basket", "/conFirm", "/join", "/detail","/newItemList", "/bestItemList","/insertProduct", "/joinForm", "/logout", "/proUpForm", "/access", "/header","/menu","/main","/bottom", "/newitem", "/bestitem", "/outwear"})
public class Controller extends HttpServlet {
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
		switch(url){
		case "/index":
			//request.setAttribute("page", "main.jsp");
			fw=new Forward();
			fw.setRedirect(false);
			fw.setPath("main.jsp");
			//HttpSession ss = request.getSession();
			Object obj = request.getAttribute("page");
			//if(ss.getAttribute("page")==null || ss.getAttribute("page").toString().equals("newItem")){
			if(obj==null || obj.toString().equals("newitem")){
				request.setAttribute("page", "newitem");				
			}else{
				request.setAttribute("page", "bestitem");
			}
			//if(page.equals("newItem") || page==null){
			break;
		case "/access":
			//request.setAttribute("page", "main.jsp");
			mp = new MovingPage(request, response);
			fw = mp.execute(8);
			break;
		case "/newitem":
			mp = new MovingPage(request, response);
			fw = mp.execute(1);
			break;
		case "/bestitem":
			mp = new MovingPage(request, response);
			fw = mp.execute(2);
			break;
		case "/outwear":
			mp = new MovingPage(request, response);
			fw = mp.execute(3);
			break;
		case "/header":
			mp = new MovingPage(request, response);
			fw = mp.execute(4);
			break;
		case "/menu":
			mp = new MovingPage(request, response);
			fw = mp.execute(5);
			break;
		case "/main":
			mp = new MovingPage(request, response);
			fw = mp.execute(6);
			break;
		case "/bottom":
			mp = new MovingPage(request, response);
			fw = mp.execute(7);
			break;
		case "/logout":
			mp = new MovingPage(request, response);
			fw = mp.execute(9);
			break;
		case "/proUpForm":
			mp = new MovingPage(request, response);
			fw = mp.execute(10);
			break;
		case "/joinForm":
			//mp = new MovingPage(request, response);
			fw = new Forward();
			fw.setRedirect(false);
			fw.setPath("joinForm.jsp");
			break;
		case "/join":
			//mp = new MovingPage(request, response);
			pm = new ProductM(request, response);
			fw = pm.execute(5);
			break;
		case "/conFirm":
			//mp = new MovingPage(request, response);
			pm = new ProductM(request, response);
			fw = pm.execute(6);
			break;
		case "/insertProduct":
			pm = new ProductM(request, response);
			fw = pm.execute(1);
			break;
		case "/newItemList":
			pm = new ProductM(request, response);
			fw = pm.execute(2);
			break;
		case "/bestItemList":
			pm = new ProductM(request, response);
			fw = pm.execute(3);
			break;
		case "/detail":
			pm = new ProductM(request, response);
			fw = pm.execute(4);
			break;
		case "/basket":
			pm = new ProductM(request, response);
			fw = pm.execute(7);
			break;
		case "/viewBasket":
			pm = new ProductM(request, response);
			fw = pm.execute(8);
			break;
		case "/deleteBasket":
			pm = new ProductM(request, response);
			fw = pm.execute(9);
			break;
		case "/toOrder":
			pm = new ProductM(request, response);
			fw = pm.execute(10);
			break;
		case "/deleteOrder":
			pm = new ProductM(request, response);
			fw = pm.execute(11);
			break;
		case "/basketMsg":
			pm = new ProductM(request, response);
			fw = pm.execute(12);
			break;
		case "/basketBtn":
			pm = new ProductM(request, response);
			fw = pm.execute(13);
			break;
		default:
			break;
		}
		
		if(fw!=null){
			if(fw.isRedirect()){
				response.sendRedirect(fw.getPath());
			}else{
				RequestDispatcher dis = request.getRequestDispatcher(fw.getPath());
				dis.forward(request, response);
			}
		}
	}	

}
