package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bean.Basket;
import bean.BasketView;
import bean.Forward;
import bean.Member;
import bean.Product;
import dao.BasketDao;
import dao.MemberDao;
import dao.ProductDao;

public class ProductM {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Forward fw;
	String htmlStr;
	ProductDao pDao = null;
	public ProductM(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	//response이용한 ajax 컨트롤러 메소드
	public String executeAjax(int code) {
		switch(code){
		case 1:
			getDetail();
			break;
		case 2:
			basketDetail();
			break;
		default:
			
		}
		return htmlStr;
	}

	private void basketDetail() {
		int qty = Integer.parseInt(request.getParameter("qty"));
		//HTttp
	}
	private void getDetail() {
		String code=request.getParameter("code");//ajax의 data에서 받아온다
		HttpSession ss = request.getSession();
		pDao  = new ProductDao();
		Product p = null;
		p = pDao.getDetail(code);
		System.out.println("code:"+code);
		String filename = p.getP_fileName();
		System.out.println("getdetail.p_filename"+ filename);
		Forward fw = new Forward();
		ss.setAttribute("p_filename", filename);
		if(p!=null){//빈을 제대로 받아왔다면
			ss.setAttribute("code", code);
			htmlStr="";
			htmlStr+=
			htmlStr+=("상품명:"+p.getP_name()+"<br/>");
			htmlStr+=("가격:"+p.getP_price()+"<br/>");
			htmlStr+=("재고수량:"+p.getP_qty()+"<br/>");
			htmlStr+=("설명:"+p.getP_content()+"<br/>");
			htmlStr+=("<script> "
					+ "function cal(){ var num = $('#count').val(); if(num>"+p.getP_qty()+"){alert('값이 큽니다.')} } </script>");
			/*var num = $('#count').val(); if(val>"+p.getP_qty()+"){alert('값이 큽니다.')};*/
			/*if(num>"+p.getP_qty()+"){alert('안녕하세용')}*/
					/*+ " var val = $('#count').val()"
					+ " if(val>"+p.getP_qty()+"){"
							+ " alert('안녕하세요')}} </script>");*/
			/*request.setAttribute("p_name", p.getP_name());
			request.setAttribute("p_price", p.getP_price());
			request.setAttribute("p_qty", p.getP_qty());*/
		}
	}
	public Forward execute(int code) {
		switch(code){
		case 1:
			insertProduct();
			break;
		case 2:
			getItemList("new");
			break;
		case 3:
			getItemList("best");
			break;
		case 4:
			getDetail();
			break;
		case 5:
			join();
			break;
		case 6:
			confirm();
			break;
		case 7:
			insertBasket();
			break;
		case 8:
			viewBasket();
			break;
		case 9:
			deleteBasket();
			break;
		case 10:
			toOrder();
			break;
		case 11:
			deleteOrder();
			break;
		case 12:
			basketMsg();
			break;
		case 13:
			basketBtn();
			break;
		default:
		}
		return fw;
	}
	
	private void basketBtn() {
		request.setAttribute("btn", "basketBtn.jsp");
		fw = new Forward();
		fw.setRedirect(false);
		fw.setPath("basketBtn.jsp");
		
	}
	private void basketMsg() {
		
	}
	private void deleteOrder() {
		BasketDao bDao = new BasketDao();
		String id =request.getSession().getAttribute("id").toString();
		String code = (String)request.getAttribute("code");
		bDao.selectOrder(id, code);
		
	}
	private void toOrder() {
		HttpSession ss = request.getSession();
		String id = (String)ss.getAttribute("id");
		String code =(String)ss.getAttribute("code");
		BasketDao bd = new BasketDao();
		ArrayList<Basket> bList = new ArrayList<>();
		bList= bd.selectBasket(id);
		
		Basket bk = new Basket();
		bk.setB_mId(id);
		bk.setB_pCode(code);
		BasketDao bDao = new BasketDao();
		bDao.updateOrder(id);
		bDao.allDelete(id);
		fw = new Forward();
		fw.setRedirect(false);
		fw.setPath("./index");
	
	}
	private String makeBasket() {
		HttpSession ss=request.getSession();
		String id=(String) ss.getAttribute("id");
		fw = new Forward();
		BasketDao bd = new BasketDao();
		List<Basket> bList=bd.selectBasket(id);
		if(bList!=null){
			StringBuilder sb=new StringBuilder();
			sb.append("<table id='tb'>");
			sb.append("<tr><th>번호</th><th>상품코드</th><th>이미지</th><th>수량</th><th>삭제</th></tr>");
			for(int i=0; i<bList.size();i++){
				Basket b=bList.get(i);
				sb.append("<tr><td>"+b.getB_no()+"</td>");
				sb.append("<td>"+b.getB_pCode()+"</td>");
				sb.append("<td>"+"<img src='upload"+"/"+b.getB_pfilename()+"' width='80'>"+"</td>");
				sb.append("<td>"+b.getB_count()+"</td>");
				sb.append("<td>"+"<form action='deleteBasket'><button>삭제하기</button></form><br/><form action='selectOrder'><button>주문하기</button></form>"+"</td></tr>");
				
			}
			sb.append("</table>");
			return sb.toString();
		}
		return null;
	}
	
	private void viewBasket(){
		String basketList = makeBasket();
		request.setAttribute("basketList", basketList);
		fw.setRedirect(false);
		fw.setPath("basketForm.jsp");

	}
	private void deleteBasket() {
		fw = new Forward();
		//�α����� �ȵ� ��� �α��� �������� �̵�
		if(request.getSession().getAttribute("id")==null){
			fw.setPath("./index");
			fw.setRedirect(true);
		}else{
			HttpSession ss = request.getSession();
			String id = (String)ss.getAttribute("id");
			String code =(String)ss.getAttribute("code");
			System.out.println("id확인="+id);
			Basket bk = new Basket();
			bk.setB_mId(id);
			bk.setB_pCode(code);
			System.out.println("code확인="+code);
			System.out.println(bk);
			BasketDao bd = new BasketDao();
			bd.deleteBasket(bk);
			fw.setRedirect(false);
			fw.setPath("./index");
			bd.close();
		}
	}
	/*private void basketView() {
		fw = new Forward();
		BasketDao bDao = new BasketDao();
		HttpSession ss= request.getSession();
		String id = (String)ss.getAttribute("id");
		String code = (String)ss.getAttribute("code");//new33
		ArrayList<Basket> list = bDao.selectBasket(id);
		String result = makeBasketView(list);
		ss.setAttribute("basketView", result);
		fw.setRedirect(false);
		fw.setPath("basketView.jsp");
	}*/
	
	/*private String makeBasketView(ArrayList<Basket> list) {
		StringBuilder sb = new StringBuilder();
		HttpSession ss = request.getSession();
		String code = (String)ss.getAttribute("code");
		ProductDao pDao = new ProductDao();
		Basket pd = pDao.getDetail(code);
		System.out.println(code);
		ss.setAttribute("imageFile", pd.getB_pfilename());
		sb.append("<table border='1' cellspacing='0'>");
		sb.append("<tr><th>번호</th><th>상품이미지</th> "
				+ "<th>상품명</th><th>가격</th><th>수량</th>");
		for(int i=0; i<list.size(); i++){
			Basket pd2 = list.get(i);
			sb.append("<tr>");
			sb.append("<td>"+pd2.getP_name()+"</td>");
			sb.append("<td>"+pd2.getP_price()+"</td>");
			sb.append("<td>"+pd2.getP_qty()+"</td>");
			sb.append("</tr>");
			sb.append("<td>"+bv.getB_no()+"</td>");
			sb.append("<td>"+bv.getP_filename()+"</td>");
			sb.append("<td>"+bv.getP_name()+"</td>");
			sb.append("<td>"+bv.getP_price()+"</td>");
			sb.append("<td>"+bv.getB_count()+"</td>");
		}
		sb.append("</table>");
		return sb.toString();
	}*/
	private void insertBasket() {
		fw = new Forward();
		HttpSession ss = request.getSession();
		String id = (String)ss.getAttribute("id");
		String code =(String)ss.getAttribute("code");//
		int count = Integer.parseInt(request.getParameter("count"));
		String fileName =(String) ss.getAttribute("p_filename");
	
		System.out.println("id는"+id);
		System.out.println("code는"+code);
		Basket bk = new Basket();
		bk.setB_mId(id);
		bk.setB_pCode(code);
		bk.setB_count(count);
		bk.setB_pfilename(fileName);
		System.out.println("Basket filename"+fileName);
		BasketDao db = new BasketDao();
		ArrayList<Product> list = db.searchProduct(code);
		int pdCnt = 0;
		for(int i=0; i<list.size(); i++){
			Product pd = list.get(i);
			pdCnt =	pd.getP_qty();
		}
		System.out.println("갯수:"+pdCnt);
		System.out.println("count갯수:"+count);
		
		/*if(count>pdCnt){
			System.out.println("실행되나요1");
			request.setAttribute("cntMsg", "재고량보다 많이 입력하셨습니다.");
			fw.setRedirect(false);
			fw.setPath("basketBtn.jsp");
			return;
		}else if(count<0){
			System.out.println("실행되나요2");
			request.setAttribute("cntMsg", "재고량이 없습니다.");
			fw.setRedirect(false);
			fw.setPath("basketBtn.jsp");
			return;
		}*/
		db.insertBasket(bk);
		fw.setRedirect(false);
		fw.setPath("./index");
		
	}
	private void confirm() {
		String id = request.getParameter("id");
		System.out.println("id"+id);
		MemberDao mDao = new MemberDao();
		//System.out.println(mDao.isId(id));
		//HttpSession ss = request.getSession();
		if(mDao.isId(id)){
			request.setAttribute("txt", "아이디가 존재합니다.");
			fw=new Forward();
			fw.setRedirect(false);
			fw.setPath("joinForm.jsp");
		}else{
			request.setAttribute("txt", "아이디가 존재하지 않습니다.");
			fw=new Forward();
			fw.setRedirect(false);
			fw.setPath("joinForm.jsp");
		}
		
	}
	private void join() {
		String id = request.getParameter("id");
		String pw1 = request.getParameter("pw1");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		Member mb = new Member(id,pw1,name,nickname);
		MemberDao mDao = new MemberDao();
		mDao.insertMember(mb);
		
		fw = new Forward();
		fw.setRedirect(false);
		fw.setPath("./index");
	}
	//Job(업무): 서버의 1개이상의 메소드를 호출해야 한다.
	private void getItemList(String kind) {
		fw=new Forward();
		//로그인이 안된 경우 로그인 페이지로 이동
		if(request.getSession().getAttribute("id")==null){
			fw.setPath("./index");
			fw.setRedirect(true);
		}
		pDao = new ProductDao();
		request.setAttribute("kind", kind);
		List<Product> pList = null;//다른 종류의 list를 받는다. 
		pList = pDao.productList(kind); //종류 별로 db에서 가지고 온다.
		System.out.println("pList="+pList);
		String html = null;
		//정상적으로 list를 가지고 왔다면
		if(pList!=null){
			html=makeHtml_pList(pList);
			request.setAttribute("pList", html);
		}
		pDao.close();
		fw=new Forward();
		fw.setPath("article.jsp");
		fw.setRedirect(false);//request객체를 가지고 가기 위해 false
		
	}
	private String makeHtml_pList(List<Product> pList) {
		StringBuilder sb = new StringBuilder();
		System.out.println("list cnt="+pList.size());//check용
		sb.append("<div id='group'>");
		for(int i=0; i<pList.size(); i++){
			Product p = pList.get(i);
			System.out.println(p.getP_fileName());
			sb.append("<div id='product' onclick=detail('"+p.getP_code()+"')>");
			sb.append("<img src='upload/"+p.getP_fileName()+"' width='150px'/><br/>");
			sb.append("<span>"+p.getP_name()+"</span><span id='pTxt'>가격:"+p.getP_price()+"</span>");
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}
	private void insertProduct() {	
		pDao=new ProductDao();
		int size = 1*1024*1024;// 1MB
		//실제 저장한 폴더 주소 리턴
		String uploadPath = request.getRealPath("upload");//upload 폴더에 저장할 것
		System.out.println("서버 폴더:"+uploadPath);
		//cos에서 제공하는 클래스 
		try {
			MultipartRequest multi = new MultipartRequest(
									request,
									uploadPath, //업로드 경로
									size, //업로드 이미지 최대크기
									"UTF-8",
									new DefaultFileRenamePolicy()
									//서버에 a.txt올렸을때 다른 누군가가 똑같은 이름의 파일을 올렸을 때
									//덮어쓰기를 안하게 파일이름 마지막에 구분을 해주는 클래스
							);
			//System.out.println(multi.getParameter("p_kind"));
			//System.out.println(multi.getParameter("p_fileName"));
			String kind = multi.getParameter("p_kind");
			String pName = multi.getParameter("p_name");
			int price = Integer.valueOf(multi.getParameter("p_price"));
			int qty= Integer.parseInt(multi.getParameter("p_qty"));
			String fName = multi.getFilesystemName("p_fileName");//db에 저장 될 파일
			String fContent = multi.getParameter("p_content");//db에 저장 될 파일
			//String orgFileName = multi.getOriginalFileName("p_fileName");
			//System.out.println("서버파일명:"+sysFileName);
			//System.out.println("오리지널명:"+orgFileName);
			/*System.out.println(request.getParameter("p_kind"));
			System.out.println(request.getParameter("p_name"));*/
			HttpSession ss = request.getSession();
			String id = ss.getAttribute("id").toString();
			Product product = new Product(id, kind, pName, price, qty, fName, fContent);
			pDao = new ProductDao();
			fw = new Forward();
			if(pDao.insertProduct(product)){
				System.out.println("상품등록 성공");
			}else{
				System.out.println("상품등록 실패");
			}
			if(product.getP_kind().equals("new")){
				System.out.println("new");
				request.setAttribute("page", "newitem");
			}else{
				System.out.println("best");
				request.setAttribute("page", "bestitem");
			}
			fw=new Forward();
			fw.setPath("/index");//xxx.jsp or url(권장)
			fw.setRedirect(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
}
