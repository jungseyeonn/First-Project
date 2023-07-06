package scheduler;

public class LoginVo {
	private String Id, Password, div;

	public LoginVo() {

	}

	public LoginVo(String Id, String Password) {
		this.Id = Id;
		this.Password = Password;
	}
	
//	public LoginVo(String Id, String div) {
//		this.Id = Id;
//		this.div = div;
//	}

	public String getId() {
		return Id;
	}
	
	public String setId() {
		return Id;
	}

	public String getPassword() {
		return Password;
	}
	
	public String setPassword() {
		return Password;
	}
}
