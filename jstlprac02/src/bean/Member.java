package bean;

public class Member {
	private String id;
	private String pw1;
	private String pw2;
	private String name;
	private String nickname;

	public Member() {
		// TODO Auto-generated constructor stub
	}
	
	public Member(String id, String pw1, String name, String nickname) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.pw1=pw1;
		this.name=name;
		this.nickname=nickname;
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPw1() {
		return pw1;
	}

	public void setPw1(String pw1) {
		this.pw1 = pw1;
	}

	public String getPw2() {
		return pw2;
	}

	public void setPw2(String pw2) {
		this.pw2 = pw2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
