package cn.qtone.modules.customer.obj.commission;

import cn.qtone.modules.customer.obj.Customer;

public class CommissionInfo {
	  private  int commissionInfoId; 
	  private  int  customerId; 
	  private  String  companyName; 
	  private String taxNO;
	  private  String  bankNO; 
	  private  String  bankName; 
	  private  String  bankAccount; 
	  private  String  organizationCode; 
	  private  String  startDate; 
	  private  String  agreementDate; 
	  private  String  linkMan; 
	  private  String  number; 
	  private  int  isNew; 
	  private  int  isYingZhuanZeng;
	  
	  private String createDate;
	  
	  private int state;//编号状态
	  
	  private int isSign;//签订状态 1签订中 2已签订 3客户不签订
	  private String signDate;
	  private int signUserBoId;
	  private int commissionType;
	  private int sourceId;//来源，1手工添加 2上门
	  private int source=1;//来源id
	  

	public int getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(int commissionType) {
		this.commissionType = commissionType;
	}
	private Customer customer;
	  
	  public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getCommissionInfoId() {
		return commissionInfoId;
	}
	public void setCommissionInfoId(int commissionInfoId) {
		this.commissionInfoId = commissionInfoId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBankNO() {
		return bankNO;
	}
	public void setBankNO(String bankNO) {
		this.bankNO = bankNO;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getAgreementDate() {
		return agreementDate;
	}
	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public int getIsYingZhuanZeng() {
		return isYingZhuanZeng;
	}
	public void setIsYingZhuanZeng(int isYingZhuanZeng) {
		this.isYingZhuanZeng = isYingZhuanZeng;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public int getSignUserBoId() {
		return signUserBoId;
	}
	public void setSignUserBoId(int signUserBoId) {
		this.signUserBoId = signUserBoId;
	}
	public int getIsSign() {
		return isSign;
	}
	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getTaxNO() {
		return taxNO;
	}
	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	} 
	
}
