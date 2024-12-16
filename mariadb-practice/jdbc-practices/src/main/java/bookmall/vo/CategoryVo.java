package bookmall.vo;

public class CategoryVo {
	private int no;
	private String categoryName;
	
	public CategoryVo() {
		
	}
	
	public CategoryVo(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
