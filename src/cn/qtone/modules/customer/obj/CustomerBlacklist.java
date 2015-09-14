package cn.qtone.modules.customer.obj;

public class CustomerBlacklist {

	private int customerBlacklistId;
	private int customerId;
	private String comment;
	private int userBoId;
	private String createDate;
	private int blackType;
	private String modDate;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCustomerBlacklistId() {
		return customerBlacklistId;
	}
	public void setCustomerBlacklistId(int customerBlacklistId) {
		this.customerBlacklistId = customerBlacklistId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getUserBoId() {
		return userBoId;
	}
	public void setUserBoId(int userBoId) {
		this.userBoId = userBoId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public int getBlackType() {
		return blackType;
	}
	public void setBlackType(int blackType) {
		this.blackType = blackType;
	}
	
}
