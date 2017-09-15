package service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Forward;
import bean.Member;
import dao.MemberDao;

public class MovingPage {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Forward fw=null;
	private String jsonStr;
	private ArrayList list;
	public MovingPage() {
	}
	
	public MovingPage(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public Forward execute(int code){
		switch(code){
		case 1:
			newItem();
			break;
		case 2:
			bestItem();
			break;
		case 3:
			outwear();
			break;
		case 4:
			headerMove();
			break;
		case 5:
			menuMove();
			break;
		case 6:
			mainMove();
			break;
		case 7:
			bottomMove();
			break;
		case 8:
			login();
			break;
		case 9:
			logout();
			break;
		case 10:
			proUpFormMoving();
			break;
		default:
			break;
		}
		return fw;
	}

	private void proUpFormMoving() {
		fw=new Forward();
		fw.setRedirect(true);
		fw.setPath("proUpForm.jsp");
	}

	private void logout() {
		HttpSession ss = request.getSession();
		ss.invalidate();	
		fw = new Forward();
		fw.setRedirect(false);//redirect 시
		fw.setPath("/index");// '/'만 하면 conPath가 생략되어 오류
	}

	private void login() {
		String id=request.getParameter("id");
		String pw=request.getParameter("pw");
		HttpSession ss = request.getSession();
		Member mb = new Member();
		mb.setId(id);
		mb.setPw1(pw);
		MemberDao mDao = new MemberDao();
		int flag=0;
		int num = mDao.isMember(mb.getId(), mb.getPw1());
		if(num==-1){
			ss.setAttribute("msgAccess","Id가 존재하지 않아요");
		}else if(num==0){
			ss.setAttribute("msgAccess","Pw가 존재하지 않아요");
		}else if(num==1){
			ss.setAttribute("id", id);
		}	
		fw = new Forward();
		fw.setRedirect(false);
		fw.setPath("/index");
	}

	private void menuMove() {
		fw=new Forward();
		fw.setRedirect(false);
		fw.setPath("left.jsp");
	}

	private void mainMove() {
		fw=new Forward();
		fw.setRedirect(false);
		fw.setPath("article.jsp");
	}

	private void bottomMove() {
		fw=new Forward();
		fw.setRedirect(false);
		fw.setPath("bottom.jsp");
		
	}
	private void headerMove() {
		//request.setAttribute("html", "header.jsp");
		HttpSession ss = request.getSession();
		if(ss.getAttribute("id") ==null){
			ss.setAttribute("login", 0);
		}else{
			ss.setAttribute("login", 1);
		}
		fw=new Forward();
		fw.setRedirect(false);
		fw.setPath("header.jsp");
	}

	private void outwear() {
		//request.setAttribute("page", "outwear.jsp");
		fw = new Forward();
		fw.setRedirect(false);
		fw.setPath("outwear.jsp");
		
	}

	private void newItem() {
		//request.setAttribute("page", "newItem.jsp");
		fw = new Forward();
		fw.setRedirect(false);
		fw.setPath("newItem.jsp");
	}

	private void bestItem() {
		//request.setAttribute("page", "bestItem.jsp");
		fw = new Forward();
		fw.setRedirect(false);
		fw.setPath("bestItem.jsp");
	}
}
