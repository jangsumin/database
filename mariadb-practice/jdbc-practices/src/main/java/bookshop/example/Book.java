package bookshop.example;

public class Book {
	int no;
	String title;
	String author;
	int stateCode = 1;
	
	public Book(int no, String title, String author) {
		this.no = no;
		this.title = title;
		this.author = author;
	}
	
	public void rent() {
		this.stateCode = 0;
		System.out.println(this.title + "이(가) 대여 됐습니다.\n");
	}
	
	public void print() {
		System.out.println("책 제목:" + this.title + ", 작가:" + this.author + ", 대여 유무:" + (this.stateCode == 1 ? "재고있음" : "대여중"));
	}
}
