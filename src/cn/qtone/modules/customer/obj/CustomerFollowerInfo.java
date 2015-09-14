package cn.qtone.modules.customer.obj;

public class CustomerFollowerInfo {

	private int customerFollowerInfoId;
	private int followType;
	private int customerId;
	private int followerId;
	private int assignerId;
	private String assignDate;
	public int getCustomerFollowerInfoId() {
		return customerFollowerInfoId;
	}
	public void setCustomerFollowerInfoId(int customerFollowerInfoId) {
		this.customerFollowerInfoId = customerFollowerInfoId;
	}
	public int getFollowType() {
		return followType;
	}
	public void setFollowType(int followType) {
		this.followType = followType;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getFollowerId() {
		return followerId;
	}
	public void setFollowerId(int followerId) {
		this.followerId = followerId;
	}
	public int getAssignerId() {
		return assignerId;
	}
	public void setAssignerId(int assignerId) {
		this.assignerId = assignerId;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
}
