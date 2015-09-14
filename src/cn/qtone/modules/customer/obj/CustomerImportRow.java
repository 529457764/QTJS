package cn.qtone.modules.customer.obj;

public class CustomerImportRow {

	public CustomerImportRow() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String taxNO;
	private int cityId;
	private String companyName;
	private String address;
	private String tel; 
	private String linkMan;
	private String confirmDate;
//	private int isYingZhuanZeng;//是否营转增
	private int isXgm;//是否小规模企业 2小规模 1一般纳税人
	private int customerAgentId;//代理记账公司id
	private Customer customerAgent;//代理记账公司
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getTaxNO() {
		return taxNO;
	}
	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
//	public int getIsYingZhuanZeng() {
//		return isYingZhuanZeng;
//	}
//	public void setIsYingZhuanZeng(int isYingZhuanZeng) {
//		this.isYingZhuanZeng = isYingZhuanZeng;
//	}
	public int getCustomerAgentId() {
		return customerAgentId;
	}
	public void setCustomerAgentId(int customerAgentId) {
		this.customerAgentId = customerAgentId;
	}
	public Customer getCustomerAgent() {
		return customerAgent;
	}
	public void setCustomerAgent(Customer customerAgent) {
		this.customerAgent = customerAgent;
	}
	public int getIsXgm() {
		return isXgm;
	}
	public void setIsXgm(int isXgm) {
		this.isXgm = isXgm;
	}
 
}
