package bookmall;

public class OrderBookVo {
	private long orderNo;
	private int bookNo;
	private int price;
	private int quantity;
	private String bookTitle;
	
	public long getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	
	public int getBookNo() {
		return bookNo;
	}
	
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
}
