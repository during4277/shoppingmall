package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.swing.plaf.synth.SynthSpinnerUI;

import bean.Basket;
import bean.Product;

public class BasketDao {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	DataSource ds;
	String code;
	String o_id;
	
	public BasketDao() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
			con = ds.getConnection();
			System.out.println("커넥션풀 연결 성공");
		} catch (Exception e) {
			System.out.println("커넥션풀 연결 실패");
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			if(rs!=null){rs.close();} //�ݾ� �� �� ���� �ֱٿ� ���� �� �ͺ���(������ ��������)
			if(pstmt!=null){pstmt.close();}
			if(con!=null){con.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertBasket(Basket bk){
		String sql = "INSERT INTO s_basket VALUES(seq_s_basket.nextval,?,?,?,?)";
		System.out.println("bkId"+bk.getB_mId());
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, bk.getB_mId());
			pstmt.setNString(2, bk.getB_pCode());
			pstmt.setNString(3, bk.getB_pfilename());
			pstmt.setInt(4, bk.getB_count());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	
	public ArrayList<Product> searchProduct(String pCode) {
		ArrayList<Product> list = new ArrayList<>();
		Product pd = new Product();
		String sql = "SELECT * FROM product WHERE p_code=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, pCode);
			rs = pstmt.executeQuery();
			if(rs.next()){
				pd.setP_qty(rs.getInt("p_qty"));
				list.add(pd);
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return list;
		
	}
	public ArrayList<Basket> selectBasket(String id) {
		Basket bk = null;
		ArrayList<Basket> list = new ArrayList<>();
		String sql = "SELECT * FROM s_basket WHERE b_mId=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				bk = new Basket();
				bk.setB_no(rs.getInt("b_no"));
				bk.setB_mId(rs.getNString("b_mId"));
				bk.setB_pCode(rs.getNString("b_pCode"));
				bk.setB_pfilename(rs.getNString("b_pFILENAME"));
				bk.setB_count(rs.getInt("b_count"));			
				list.add(bk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	public void deleteBasket(Basket bk) {
		String sql = "DELETE FROM S_BASKET WHERE B_MID=? AND B_PCODE=?";
		//String sql = "DELETE FROM S_BASKET WHERE B_MID=?";
		System.out.println("bkId"+bk.getB_mId());
		System.out.println(bk.getB_pCode());
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, bk.getB_mId());
			pstmt.setNString(2, bk.getB_pCode());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	public void allDelete(String id){
		String sql ="DELETE FROM S_BASKET WHERE B_MID=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	public void updateOrder(String id) {
		String b_selectSql = "SELECT * FROM S_BASKET WHERE B_MID=?";
		//String p_selectSql = "SELECT * FROM product WHERE B_MID";		
		String insertSql = "INSERT INTO s_order VALUES(seq_s_order.nextval,?,?,?,DEFAULT)";
		//String deleteSql = "DELETE FROM s_basket where B_MID=?";
		try {
			pstmt = con.prepareStatement(b_selectSql);
			pstmt.setString(1, id);
			o_id=id;
			rs = pstmt.executeQuery();
			pstmt = con.prepareStatement(insertSql);
			while(rs.next()){
				pstmt.setNString(1, rs.getNString("B_MID"));
				code=rs.getNString("B_PCODE");
				pstmt.setNString(2, rs.getNString("B_PCODE"));
				pstmt.setNString(3, rs.getNString("B_COUNT"));
				pstmt.executeUpdate();
				removeQty();
			}
			/*rs.getNString("B_NO");
			rs.getNString("B_MID");
			rs.getNString("B_PCODE");
			rs.getNString("B_PFILENAME");
			rs.getNString("B_COUNT");*/
			//pstmt.setNString(1, rs.getNString("B_MID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void removeQty() {
		String sql="UPDATE PRODUCT SET P_QTY=(SELECT P_QTY-(SELECT O_BCOUNT FROM S_ORDER WHERE O_NO=(SELECT O_NO FROM S_ORDER WHERE O_MID=? AND O_PCODE=?))FROM PRODUCT P INNER JOIN S_ORDER S ON P.P_CODE=S.O_PCODE WHERE S.O_NO=(SELECT O_NO FROM S_ORDER WHERE O_MID=? AND O_PCODE=?))WHERE P_CODE=(SELECT O_PCODE FROM S_ORDER WHERE O_NO=(SELECT O_NO FROM S_ORDER WHERE O_MID=? AND O_PCODE=?))";
		
			try{
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, o_id);
			pstmt.setNString(2, code);
			pstmt.setNString(3, o_id);
			pstmt.setNString(4, code);
			pstmt.setNString(5, o_id);
			pstmt.setNString(6, code);
			int suc=pstmt.executeUpdate();
			if(suc!=0){
				System.out.println("재고 정리 완료");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void selectOrder(String id, String code) {
		String sql = "SELECT * FROM ";
		
	}
}
