package bean;

public class Product {
	private String p_code;//pk
	private String p_id; //상품을 등록한 사람의 id
	private String p_kind; //new(인기상품), best(신상품)
	private String p_name;
	private int p_price;
	private int p_qty;
	private String p_fileName;
	private String p_content;
	
	public Product() {	
	}
	public Product(String id, String kind, String pName, int price, int qty, String fName, String content) {
		this.p_id = id;
		this.p_kind = kind;
		this.p_name = pName;
		this.p_price = price;
		this.p_qty = qty;
		this.p_fileName = fName;
		this.p_content =content;
	}
	
	public Product(String id, String kind, String pName, int price, int qty, String fName) {
		this.p_id = id;
		this.p_kind = kind;
		this.p_name = pName;
		this.p_price = price;
		this.p_qty = qty;
		this.p_fileName = fName;
	}
	public String getP_code() {
		return p_code;
	}
	public void setP_code(String p_code) {
		this.p_code = p_code;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_kind() {
		return p_kind;
	}
	public void setP_kind(String p_kind) {
		this.p_kind = p_kind;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public int getP_price() {
		return p_price;
	}
	public void setP_price(int p_price) {
		this.p_price = p_price;
	}
	public int getP_qty() {
		return p_qty;
	}
	public void setP_qty(int p_qty) {
		this.p_qty = p_qty;
	}
	public String getP_fileName() {
		return p_fileName;
	}
	public void setP_fileName(String p_fileName) {
		this.p_fileName = p_fileName;
	}
	public String getP_content() {
		return p_content;
	}
	public void setP_content(String p_content) {
		this.p_content = p_content;
	}
	
}
