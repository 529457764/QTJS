package cn.qtone.modules.customer.obj;

public class CustomerTaxcard {
	
	private int customerTaxcardId;
	
	private int customerId;
	
	private String installCardDate;
	
	private String cardNO;
	
	private String ICNO;
	
	private String sysLegalNO;
	
	private String createDate;
	

	
	//第几分机
	private int cardNum;
	
	public CustomerTaxcard(){		
	}

	public int getCustomerTaxcardId() {
		return customerTaxcardId;
	}

	public void setCustomerTaxcardId(int customerTaxcardId) {
		this.customerTaxcardId = customerTaxcardId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getInstallCardDate() {
		return installCardDate;
	}

	public void setInstallCardDate(String installCardDate) {
		this.installCardDate = installCardDate;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getICNO() {
		return ICNO;
	}

	public void setICNO(String icno) {
		ICNO = icno;
	}

	public String getSysLegalNO() {
		return sysLegalNO;
	}

	public void setSysLegalNO(String sysLegalNO) {
		this.sysLegalNO = sysLegalNO;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	
}
