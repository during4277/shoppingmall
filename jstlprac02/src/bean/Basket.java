package bean;

public class Basket {
	private int b_no;
	private String b_mId;
	private String b_pCode;
	private String b_pfilename;
	private int b_count;
	private String content;
	private String b_pName;
	//private int b_flag;

	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public String getB_mId() {
		return b_mId;
	}
	public void setB_mId(String b_mId) {
		this.b_mId = b_mId;
	}
	public String getB_pCode() {
		return b_pCode;
	}
	public void setB_pCode(String b_pCode) {
		this.b_pCode = b_pCode;
	}
	
	public String getB_pfilename() {
		return b_pfilename;
	}
	public void setB_pfilename(String b_pfilename) {
		this.b_pfilename = b_pfilename;
	}
	
	public int getB_count() {
		return b_count;
	}
	public void setB_count(int b_count) {
		this.b_count = b_count;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getB_pName() {
		return b_pName;
	}
	public void setB_pName(String b_pName) {
		this.b_pName = b_pName;
	}
	
	/*public int getB_flag() {
		return b_flag;
	}
	public void setB_flag(int b_flag) {
		this.b_flag = b_flag;
	}*/
	
}
