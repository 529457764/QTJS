package cn.qtone.modules.customer.obj.owe;

public class SearchOweParam {
	
	private int isSpecial;
	
	private String nextFollowDateStart;
	
	private String nextFollowDateEnd;
	
	private String taxNO;
	
	private String companyName;
	
	private String orderDateStart;
	
	private String orderDateEnd;
	
	private String cityIds;
	
	private int follower;
	
	/**
	 * 是否有跟进人 2 有 1 没有
	 */
	private int hasFollower;
	
	/**
	 * 购买类型
	 * "1", "320";
	 * "2", "370";
	 * "3", "1416";
	 * "4", "培训";
	 * "5", "维修";
	 * "6", "设备";
	 * "7", "团购";
	 */
	private String buyTypes;
	
	
	private int customerId;
	
	
	private int isFollowRemark; //业务跟进备注
	
	private int visitFlag; //跟进情况回访
	private int isNeedVisit;//需回访
	private int isVisit;//回访状态
	
	private int isPayByCheck;//是否支票支付 1不是 2是
	
	private String groupBy;//分组SQL
	
	
	public int getIsFollowRemark() {
		return isFollowRemark;
	}

	public void setIsFollowRemark(int isFollowRemark) {
		this.isFollowRemark = isFollowRemark;
	}

	public int getVisitFlag() {
		return visitFlag;
	}

	public void setVisitFlag(int visitFlag) {
		this.visitFlag = visitFlag;
	}

	public int getIsNeedVisit() {
		return isNeedVisit;
	}

	public void setIsNeedVisit(int isNeedVisit) {
		this.isNeedVisit = isNeedVisit;
	}

	public int getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(int isVisit) {
		this.isVisit = isVisit;
	}

	public SearchOweParam(){
		
	}

	public String getTaxNO() {
		return taxNO;
	}

	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrderDateStart() {
		return orderDateStart;
	}

	public void setOrderDateStart(String orderDateStart) {
		this.orderDateStart = orderDateStart;
	}

	public String getOrderDateEnd() {
		return orderDateEnd;
	}

	public void setOrderDateEnd(String orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public int getHasFollower() {
		return hasFollower;
	}

	public void setHasFollower(int hasFollower) {
		this.hasFollower = hasFollower;
	}

	public String getBuyTypes() {
		return buyTypes;
	}

	public void setBuyTypes(String buyTypes) {
		this.buyTypes = buyTypes;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getIsPayByCheck() {
		return isPayByCheck;
	}

	public void setIsPayByCheck(int isPayByCheck) {
		this.isPayByCheck = isPayByCheck;
	}

	public int getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(int isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getNextFollowDateStart() {
		return nextFollowDateStart;
	}

	public void setNextFollowDateStart(String nextFollowDateStart) {
		this.nextFollowDateStart = nextFollowDateStart;
	}

	public String getNextFollowDateEnd() {
		return nextFollowDateEnd;
	}

	public void setNextFollowDateEnd(String nextFollowDateEnd) {
		this.nextFollowDateEnd = nextFollowDateEnd;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

}
