package cn.qtone.modules.customer.obj.tel;

public class CustomerLastTel {
	
	private String source;//电话类型，对应cn.qtone.modules.telRecord.Const的blacklistType
	private int customerId;
	private String customerTel;
	private String linkman;
	private int userBoId;
	private String creatDate;
	private String customerName;
	private String questionType;
	private int createId;
	private String bo_userName;
	public CustomerLastTel(){
		
	}
	public String getBo_userName() {
		return bo_userName;
	}

	public void setBo_userName(String bo_userName) {
		this.bo_userName = bo_userName;
	}
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}


	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getUserBoId() {
		return userBoId;
	}

	public void setUserBoId(int userBoId) {
		this.userBoId = userBoId;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}
	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createId) {
		this.createId = createId;
	}

	
	
}
