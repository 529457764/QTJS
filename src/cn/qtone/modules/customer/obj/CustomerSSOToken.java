package cn.qtone.modules.customer.obj;
//单点登录令牌对象
public class CustomerSSOToken {
	
	private int CustomerSSOTokenId;
	
	private String account;
	
	private String token;
	
	private String avaDate;
	
	private int useTimes;

	public CustomerSSOToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAvaDate() {
		return avaDate;
	}

	public void setAvaDate(String avaDate) {
		this.avaDate = avaDate;
	}


	public int getCustomerSSOTokenId() {
		return CustomerSSOTokenId;
	}

	public void setCustomerSSOTokenId(int customerSSOTokenId) {
		CustomerSSOTokenId = customerSSOTokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}

}
