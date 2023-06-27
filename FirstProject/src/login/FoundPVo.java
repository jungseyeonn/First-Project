package login;

public class FoundPVo {
	private String id;
	private String email;
	private String pwd;
	
	public FoundPVo() {
		
	}
	
	public FoundPVo(String id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public FoundPVo(String pwd) {
		this.pwd = pwd;
	}
	
	public String getid() {
		return id;
	}
	
	public String getemail() {
		return email;
	}
	
	public String setPwd() {
		return pwd;
	}
	
	public String getPwd() {
		return pwd;
	}
}
