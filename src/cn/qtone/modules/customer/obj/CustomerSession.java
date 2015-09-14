package cn.qtone.modules.customer.obj;

//放入session的客户对象

public class CustomerSession {
	
	private String taxNO;
	
	private String account;
	
	//1主账号，2子帐号
	private int isMaster;
	
	private String companyName;
	
	private String ip;
	
	private int customerId;
	
	private int customerAccountId;
	
	//是否确认了基本资料，1没有，2有
	private int isConfirm;
	
	//是否激活，1没激活，2激活
	private int isActive;
	
	//是否修改了密码，1没有，2修改了
	private int isChangePwd;
	
	private String loginDate;
	
	/**
	 * 账号所属站点：1金税家园 2商城
	 */
	private int site;
	
	private String linkman;
	
	private String tel;	


	public CustomerSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(int isMaster) {
		this.isMaster = isMaster;
	}

	public String getTaxNO() {
		return taxNO;
	}

	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(int isConfirm) {
		this.isConfirm = isConfirm;
	}

	public int getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(int customerAccountId) {
		this.customerAccountId = customerAccountId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getIsChangePwd() {
		return isChangePwd;
	}

	public void setIsChangePwd(int isChangePwd) {
		this.isChangePwd = isChangePwd;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public int getSite() {
		return site;
	}

	public void setSite(int site) {
		this.site = site;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
