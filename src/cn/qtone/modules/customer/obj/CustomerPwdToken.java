package cn.qtone.modules.customer.obj;
//修改密码令牌对象
public class CustomerPwdToken {
	
	private int CustomerPwdTokenId;
	
	private int customerAccountId;
	
	private String token;
	
	private String avaDate;
	
	private int isUse;

	public CustomerPwdToken() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getCustomerAccountId() {
		return customerAccountId;
	}


	public void setCustomerAccountId(int customerAccountId) {
		this.customerAccountId = customerAccountId;
	}


	public String getAvaDate() {
		return avaDate;
	}

	public void setAvaDate(String avaDate) {
		this.avaDate = avaDate;
	}


	public int getCustomerPwdTokenId() {
		return CustomerPwdTokenId;
	}

	public void setCustomerPwdTokenId(int customerPwdTokenId) {
		CustomerPwdTokenId = customerPwdTokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

}
