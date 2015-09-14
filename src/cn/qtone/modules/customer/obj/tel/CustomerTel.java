package cn.qtone.modules.customer.obj.tel;

public class CustomerTel {
	
	private int telType;//电话类型，对应cn.qtone.modules.telRecord.Const的blacklistType
	private int customerTelId;
	private int customerId;
	private String tel;
	private String linkman;
	private int customerTelSourceId;
	private int isInvalid;
	private String updateTime;
	
	public CustomerTel(){
		
	}

	public int getCustomerTelId() {
		return customerTelId;
	}

	public void setCustomerTelId(int customerTelId) {
		this.customerTelId = customerTelId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}


	public int getCustomerTelSourceId() {
		return customerTelSourceId;
	}

	public void setCustomerTelSourceId(int customerTelSourceId) {
		this.customerTelSourceId = customerTelSourceId;
	}

	public int getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(int isInvalid) {
		this.isInvalid = isInvalid;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getTelType() {
		return telType;
	}

	public void setTelType(int telType) {
		this.telType = telType;
	}


	
	
}
