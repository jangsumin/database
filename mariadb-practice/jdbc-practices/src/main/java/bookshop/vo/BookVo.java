package bookshop.vo;

public class BookVo {
	private Long id;
	private String title;
	private String status;
	private long authorId;
	private String authorName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "BookVo [title=" + title + ", authorId=" + authorId + "]";
	}
}
