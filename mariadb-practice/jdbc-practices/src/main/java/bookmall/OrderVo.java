package bookmall;

public class OrderVo {
	private long no;
	private String number;
	private int payment;
	private String shipping;
	private String status;
	private int userNo;
	
	public long getNo() {
		return no;
	}
	
	public void setNo(long no) {
		this.no = no;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public int getPayment() {
		return payment;
	}
	
	public void setPayment(int payment) {
		this.payment = payment;
	}
	
	public String getShipping() {
		return shipping;
	}
	
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
}
