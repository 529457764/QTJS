package cn.qtone.modules.customer.obj.owe;
/**
 * 欠费企业跟进人对象
 * @author Administrator
 *
 */
public class CustomerOweFollower {

	private int customerOweFollowerId;
	
	private int follower;
	
	private int customerId;
	
	private int isSpecial;//是否特殊情况 1不是 2是
	
   private String followRemark;//跟进备注
	
//	private String visitRemark;//回访备注
	
	private int isNeedVisit;//需回访 1 不需要 ；2 需要
	
	private int visitFlag;//跟进情况回访    1 业务情况属实；2 业务情况不真实
	
//	private int visitUserBoId;
	
	private int followDate;//跟进时间
	
//	private String visitDate;//跟进情况回访时间
	
	private String nextFollowDate;//下次跟进日期
	
	private String expireDate;//业务收款过期日期
	
//	public String getVisitDate() {
//		return visitDate;
//	}
//
//	public void setVisitDate(String visitDate) {
//		this.visitDate = visitDate;
//	}

	public CustomerOweFollower(){}

	public int getCustomerOweFollowerId() {
		return customerOweFollowerId;
	}

	public void setCustomerOweFollowerId(int customerOweFollowerId) {
		this.customerOweFollowerId = customerOweFollowerId;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFollowRemark() {
		return followRemark;
	}

	public void setFollowRemark(String followRemark) {
		this.followRemark = followRemark;
	}

//	public String getVisitRemark() {
//		return visitRemark;
//	}
//
//	public void setVisitRemark(String visitRemark) {
//		this.visitRemark = visitRemark;
//	}

	public int getIsNeedVisit() {
		return isNeedVisit;
	}

	public void setIsNeedVisit(int isNeedVisit) {
		this.isNeedVisit = isNeedVisit;
	}

	public int getVisitFlag() {
		return visitFlag;
	}

	public void setVisitFlag(int visitFlag) {
		this.visitFlag = visitFlag;
	}

//	public int getVisitUserBoId() {
//		return visitUserBoId;
//	}
//
//	public void setVisitUserBoId(int visitUserBoId) {
//		this.visitUserBoId = visitUserBoId;
//	}

	public int getFollowDate() {
		return followDate;
	}

	public void setFollowDate(int followDate) {
		this.followDate = followDate;
	}

	public String getNextFollowDate() {
		return nextFollowDate;
	}

	public void setNextFollowDate(String nextFollowDate) {
		this.nextFollowDate = nextFollowDate;
	}

	public int getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(int isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	 
	
	
}
