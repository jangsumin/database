package example;

public class MemberVo {
	private Long id;
	private String deptName;
	
	public MemberVo() {
	}
	 
	public MemberVo(long id, String deptName) {
		this.id = id;
		this.deptName = deptName;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "MemberVo [id=" + id + ", deptName=" + deptName + "]";
	}
}
