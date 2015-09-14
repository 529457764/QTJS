package cn.qtone.modules.customer.obj;
//登陆参数
public class LoginParam {
	
	private String account;
	
	private String pwd;
	
	private String identifyingCode;

	public LoginParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIdentifyingCode() {
		return identifyingCode;
	}

	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
