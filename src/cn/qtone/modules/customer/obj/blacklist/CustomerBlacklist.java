package cn.qtone.modules.customer.obj.blacklist;

public class CustomerBlacklist {

	private int customerBlacklistId;
	private int customerId;
	private String comment;
	private int userBoId;
	private String createDate;
	private int blackType;
	private String modDate;
	
	/**
	 * 建议取消黑名单的日期
	 * 系统根据该日期查询订单模块判断是否建议取消黑名单
		该字段值默认等于添加黑名单的日期
	 */
	private String sugCancleDate;
	
	/**
	 * 是否建议取消
	 * 1否 2是 3忽略建议
	 */
	private int isSuggestCancle;
	
	private String companyName;
	
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

	public String getSugCancleDate() {
		return sugCancleDate;
	}
	public void setSugCancleDate(String sugCancleDate) {
		this.sugCancleDate = sugCancleDate;
	}
	public int getIsSuggestCancle() {
		return isSuggestCancle;
	}
	public void setIsSuggestCancle(int isSuggestCancle) {
		this.isSuggestCancle = isSuggestCancle;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
