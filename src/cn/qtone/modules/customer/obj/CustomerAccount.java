package cn.qtone.modules.customer.obj;

public class CustomerAccount {
	
	private int customerAccountId;
	
	private String account;
	
	private String pwd;
	
	private int customerId;
	
	private int isMaster;
	
	private String createDate;
	
	private String name;
	
	private String tel;
	
	private String initPwd;
	
	private int isActive;
	
	private String email;

	//是否修改了密码，1没有，2修改了
	private int isChangePwd;
	
	/**
	 * 是否禁用，1是，2没有禁用
	 */
	private int isForbid;
	
	private String loginDate;
	
	/**
	 * 账号所属站点：1金税家园 2商城
	 */
	private int site;
	
	private String ip;
	
	public CustomerAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(int isMaster) {
		this.isMaster = isMaster;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(int customerAccountId) {
		this.customerAccountId = customerAccountId;
	}

	public int getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(int isForbid) {
		this.isForbid = isForbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getInitPwd() {
		return initPwd;
	}

	public void setInitPwd(String initPwd) {
		this.initPwd = initPwd;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
