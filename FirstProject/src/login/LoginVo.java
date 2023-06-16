package login;

public class LoginVo {
	private String Id;
	private String Password;

	public LoginVo() {

	}

	public LoginVo(String Id, String Password) {
		this.Id = Id;
		this.Password = Password;
	}

	public String getId() {
		return Id;
	}

	public String getPassword() {
		return Password;
	}
}
