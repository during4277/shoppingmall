package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.Basket;
import bean.Product;

public class ProductDao {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	DataSource ds;
	HttpServletRequest request; 
	HttpServletResponse response;
	//커넥션 풀과 연동하는 것을 생성자에 작성
	public ProductDao() {
		try{
		Context init = new InitialContext();
		ds=(DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		//한번에 연결 그렇지 않으면 메소드 마다 연결을 해주어야 한다.
		//커넥션을 한번 생성한다. 메소드 마다 연결을 하는 것은 커넥션 객체를 계속 만들기 때문에 비효율적
		//close()도 매번 해주어야 한다.
		con=ds.getConnection(); 
		System.out.println("커넥션 풀 연결 성공");
		}catch(Exception e){
			System.out.println("커넥션 풀 연결 실패");
			
		}
		
	}
	public ProductDao(HttpServletRequest request, HttpServletResponse response) {
		this.request= request;
		this.response = response;
	}
	public void close(){//커넥션풀에 커넥션 반납
		try {
			if(rs!=null){rs.close();} //�ݾ� �� �� ���� �ֱٿ� ���� �� �ͺ���(������ ��������)
			if(pstmt!=null){pstmt.close();}
			if(con!=null){con.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean insertProduct(Product product) {
		
		try {
			String sql = "INSERT INTO PRODUCT(P_CODE, P_ID, P_NAME, P_PRICE, P_QTY, P_FILENAME, P_CONTENT) "
					+ "VALUES(?||P_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			//?||P_SEQ.NEXTVAL은 ? 앞에 1,2,3,4를 붙여서 저장한다. ||은 연결한다는 의미
			pstmt= con.prepareStatement(sql);
			pstmt.setNString(1, product.getP_kind());
			pstmt.setNString(2, product.getP_id());
			pstmt.setNString(3, product.getP_name());
			pstmt.setInt(4, product.getP_price());
			pstmt.setInt(5, product.getP_qty());
			pstmt.setNString(6, product.getP_fileName());
			pstmt.setNString(7, product.getP_content());
			int result = pstmt.executeUpdate();
			if(result != 0){
				return true;//상품등록 성공
			}
		} catch (SQLException e) {
			System.out.println("상품등록 에러");
			e.printStackTrace();
		}
		
		return false;//사품등록 실패
	}
	public List<Product> productList(String kind) {
		String sql = "SELECT * FROM PRODUCT WHERE"
				+ " P_CODE LIKE '%'||?||'%'";//%?%으로도 실행해보기
		try {
			pstmt = con.prepareStatement(sql);
			//가독성을 위해 작성함 한 줄로 가능
			if(kind.equals("new")){				
				pstmt.setNString(1, "new");
			}else{				
				pstmt.setNString(1, "best");
			}
			//pstmt.setNString(1, kind);
			rs = pstmt.executeQuery();
			List<Product> pList = new ArrayList<>();
			Product product = null;
			while(rs.next()){
				product = new Product();
				product.setP_code(rs.getNString("P_CODE"));
				product.setP_fileName(rs.getNString("P_FILENAME"));
				product.setP_id(rs.getNString("P_ID"));
				product.setP_name(rs.getNString("P_NAME"));
				product.setP_price(rs.getInt("P_PRICE"));
				product.setP_qty(rs.getInt("P_QTY"));
				pList.add(product);
			}
			return pList;//0개~N개 List 반환
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;//검색 실패
		//return pList; 0개 검색도 예외처리를 하고 싶다면
	}
	public Product getDetail(String p_code) {
		String sql = "SELECT * FROM PRODUCT WHERE P_CODE=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, p_code);
			rs=pstmt.executeQuery();
			Product p = null;
			if(rs.next()){
				p=new Product();
				p.setP_code(rs.getNString("p_code"));
				p.setP_name(rs.getNString("p_NAME"));
				p.setP_price(rs.getInt("p_PRICE"));
				p.setP_qty(rs.getInt("p_qty"));
				p.setP_id(rs.getNString("p_ID"));
				p.setP_fileName(rs.getString("p_filename")); //그림불러왕
				p.setP_content(rs.getNString("p_content"));
			}
			return p;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
