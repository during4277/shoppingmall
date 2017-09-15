package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.Member;

public class MemberDao {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	DataSource ds;
	HttpServletRequest request; 
	HttpServletResponse response;
	//커넥션 풀과 연동하는 것을 생성자에 작성
	public MemberDao() {
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
	public MemberDao(HttpServletRequest request, HttpServletResponse response) {
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
	//아이디 존재 여부 판독
	public boolean isId(String id) {
		String sql = "SELECT * FROM s_member WHERE M_ID=?";
		System.out.println("isId:"+id);
		int result = 0;
		try {
			//con=ds.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				System.out.println("컬럼 존재");
				result =1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boolConvert(result);
	}
	private boolean boolConvert(int result) {
		return (result!=0)?true:false;
	}
	public boolean isNickName(String nickname) {
		String sql = "SELECT COUNT(*) FROM MEMBERS WHERE M_NICKNAME=?";
		int result = 0;
		try {
			//con=ds.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, nickname);
			rs= pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return boolConvert(result);
	}
	public boolean insertMember(Member mb) {
		String sql = "INSERT INTO s_member(M_ID, M_PW1, M_NAME, M_NICKNAME)"
				+ " VALUES(?,?,?,?)";
		int result =0;
		try {
			//con=ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getId());
			pstmt.setNString(2, mb.getPw1());
			pstmt.setNString(3, mb.getName());
			pstmt.setNString(4, mb.getNickname());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("members insert 실패");
			e.printStackTrace();
		}
		return boolConvert(result);
	}
	public boolean insertHistory(String id, int flag) {
		String sql = "INSERT INTO HISTORY(H_MID, H_DATE, H_FLAG)"
				+ " VALUES(?, DEFAULT, ?)";
		int result =0;
		try {
			//con=ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);
			pstmt.setInt(2, flag);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("history insert 실패");
			e.printStackTrace();
		}
		return boolConvert(result);
	}
	public int isMember(String id, String pw) {
		//회원이면 true, 회원이 아니면 false
		String sql = "SELECT * FROM S_member WHERE M_ID=?";
		int result=-1;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, id);
			//pstmt.setNString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()){//존재한다면
				if(rs.getNString("M_PW1").equals(pw)){
					result = 1;
				}else{
					result = 0;
				}
			}else{
				result = -1;
			}
		} catch (SQLException e) {
			System.out.println("select 실패");
			e.printStackTrace();
		}
		return result;
	}
	
	/*public boolean updateMember(Member mb){
		String sql = "UPDATE MEMBERS SET M_PW=?, M_NAME=?, M_NICKNAME=? WHERE M_ID=?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getPw());
			pstmt.setNString(2, mb.getName());
			pstmt.setNString(3, mb.getNickname());
			pstmt.setNString(4, mb.getId());
			int count = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println("수정 실패");
			e.printStackTrace();
		}
		
		return boolConvert(result);
	}*/
	/*public Member getMemberInfo(String id) {
		//+ HISTORY와 조인해서 HISTORY정보까지 출력
		String sql = "SELECT * FROM MEMBERS WHERE M_ID=?";
		Member mb = null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				mb = new Member();
				mb.setId(rs.getNString("M_ID"));
				mb.setPw(rs.getNString("M_PW"));
				mb.setName(rs.getNString("M_NAME"));
				mb.setNickname(rs.getNString("M_NICKNAME"));
			}
		} catch (SQLException e) {
			System.out.println("getMemberInfo 실패");
			e.printStackTrace();
		}
		return mb;
	}*/
	
	/*public ArrayList<History> historyInfo(){
		String sql = "SELECT * FROM HISTORY";
		ArrayList<History> list = new ArrayList<History>();
		try {
			//con=ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			History ht = null;
			while(rs.next()){
				//System.out.println(rs.getString("H_FLAG"));
				ht = new History();
				String flag = rs.getString("H_FLAG");
				if(flag.equals("0")){
					flag="로그아웃";
				}else if(flag.equals("1")){
					flag="로그인";
				}else if(flag.equals("2")){
					flag="회원가입";
				}else{
					flag="관리자";
				}
				ht.setHid(rs.getString("H_MID"));
				ht.setDate(rs.getString("H_DATE"));
				ht.setIp(rs.getString("H_IP"));
				ht.setFlag(flag);
				list.add(ht);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return null;
	}*/
	/*public Member getMeberInfo(String id) {
		String sql="SELECT * FROM MEMBERS WHERE M_ID=?";
		//+history와 조인해서 history정보까지 검색가능
		Member mb=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				mb=new Member();
				mb.setId(rs.getNString("M_ID"));
				mb.setName(rs.getNString("M_NAME"));
				mb.setNickname(rs.getNString("M_NICKNAME"));
				System.out.println("getMemberInfo 성공");
			}
			return mb;
		} catch (SQLException e) {
			System.out.println("getMemberInfo 실패");
			e.printStackTrace();
		}
		close();
		return null;
	}*/
	/*public Member membersearch(String id){
	      String sql="SELECT * FROM MEMBERS WHERE M_ID=?";
	      Member mb=null;
	      try {
	         //con=ds.getConnection();
	         pstmt=con.prepareStatement(sql);
	         pstmt.setNString(1, id);
	         rs=pstmt.executeQuery();
	         if(rs.next()){
	            mb=new Member();
	            mb.setId(rs.getNString("M_ID"));
	            mb.setName(rs.getNString("M_NAME"));
	            mb.setNickname(rs.getNString("M_NICKNAME"));
	            return mb;  
	         }else{
	        	 System.out.println("없는 ID");
	         }
	      } catch (SQLException e) {
	         System.out.println("Membersearch 실패");
	         e.printStackTrace();
	      }
	      return null;
	   }*/
	/*public ArrayList<Member> memberSelect() {
		ArrayList<Member> list = new ArrayList<>();
		String sql = "SELECT * FROM MEMBERS";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			Member mb = null;
			while(rs.next()){
				mb = new Member();
				mb.setId(rs.getNString("M_ID"));
				mb.setName(rs.getNString("M_NAME"));
				mb.setNickname(rs.getNString("M_NICKNAME"));
				list.add(mb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return null;
	}*/
	/*public void mDelete(String id) {
		String sql = "DELETE FROM MEMBERS WHERE M_ID=?";
		String query = "DELETE FROM HISTORY WHERE H_MID=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);
			pstmt.executeQuery();
			pstmt = con.prepareStatement(query);
			pstmt.setNString(1, id);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}*/
/*	public AllHistory allMembersearch(String fId, String flag) {
		String sql = "SELECT * FROM MEMBERS M INNER JOIN HISTORY H "
				+ "ON M.M_ID=H.H_MID WHERE M.M_ID=? AND H.H_FLAG=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, fId);
			pstmt.setNString(2, flag);
			rs = pstmt.executeQuery();
			
			AllHistory ah = null;
			if(rs.next()){
				ah = new AllHistory();
				ah.setId(rs.getNString("M_ID"));
				ah.setPw(rs.getNString("M_PW"));
				ah.setName(rs.getNString("M_NAME"));
				ah.setNickname(rs.getNString("M_NICKNAME"));
				ah.setIp(rs.getNString("H_IP"));
				ah.setDate(rs.getString("H_DATE"));
				if(flag.equals("0")){
					flag="로그아웃";
				}else if(flag.equals("1")){
					flag="로그인";
				}else if(flag.equals("2")){
					flag="회원가입";
				}else{
					flag="관리자";
				}
				ah.setFlag(flag);
			}
			return ah;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
}
