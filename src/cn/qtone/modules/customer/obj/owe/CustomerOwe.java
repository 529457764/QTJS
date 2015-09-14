package cn.qtone.modules.customer.obj.owe;

import cn.qtone.modules.customer.obj.*;
/**
 * 欠费的企业
 * @author Administrator
 *
 */
public class CustomerOwe {

	private CustomerOweVisit lastCustomerOweVisit;

	private CustomerOweFollower customerOweFollower;

	private float totalCost;
	
	private int customerId;
	
	private float totalPayCost;

	private Customer customer;
	
	//跟进人，对应user_bo表的id
	private int follower;
	
	private String linkman;
	
	private String tel;
	
	private String address;
	
	private int saleman;
	
	private String   orderDate;//最小下单日期
	
	private int oweDay; //欠费天数
	
//	private String followRemark;//跟进备注
//	
//	private String visitRemark;//回访备注
	
//	private int isNeedVisit;//需回访 1 不需要 ；2 需要
	
//	private int visitFlag;//跟进情况回访    1 业务情况属实；2 业务情况不真实
	
//	private int visitUserBoId;
	
//	private int followDate;//跟进时间
	
//	private String visitDate;//跟进情况回访时间
	
//	private int telId;//绑定的录音
//	private String telPath;//录音路径
	
//	public String getTelPath() {
//		return telPath;
//	}
//
//	public void setTelPath(String telPath) {
//		this.telPath = telPath;
//	}
//
//	public int getTelId() {
//		return telId;
//	}
//
//	public void setTelId(int telId) {
//		this.telId = telId;
//	}

//	public int getFollowDate() {
//		return followDate;
//	}
//
//	public void setFollowDate(int followDate) {
//		this.followDate = followDate;
//	}
//
// 
//
//	public int getVisitUserBoId() {
//		return visitUserBoId;
//	}
//
//	public void setVisitUserBoId(int visitUserBoId) {
//		this.visitUserBoId = visitUserBoId;
//	}

//	public String getFollowRemark() {
//		return followRemark;
//	}
//
//	public void setFollowRemark(String followRemark) {
//		this.followRemark = followRemark;
//	}
//
//	public String getVisitRemark() {
//		return visitRemark;
//	}
//
//	public void setVisitRemark(String visitRemark) {
//		this.visitRemark = visitRemark;
//	}

	 

	public CustomerOwe(){		
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public float getTotalPayCost() {
		return totalPayCost;
	}

	public void setTotalPayCost(float totalPayCost) {
		this.totalPayCost = totalPayCost;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSaleman() {
		return saleman;
	}

	public void setSaleman(int saleman) {
		this.saleman = saleman;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getOweDay() {
		return oweDay;
	}

	public void setOweDay(int oweDay) {
		this.oweDay = oweDay;
	}
//
//	public int getIsNeedVisit() {
//		return isNeedVisit;
//	}
//
//	public void setIsNeedVisit(int isNeedVisit) {
//		this.isNeedVisit = isNeedVisit;
//	}
//
//	public int getVisitFlag() {
//		return visitFlag;
//	}
//
//	public void setVisitFlag(int visitFlag) {
//		this.visitFlag = visitFlag;
//	}
//
//	public String getVisitDate() {
//		return visitDate;
//	}
//
//	public void setVisitDate(String visitDate) {
//		this.visitDate = visitDate;
//	}

	public CustomerOweVisit getLastCustomerOweVisit() {
		return lastCustomerOweVisit;
	}

	public void setLastCustomerOweVisit(CustomerOweVisit lastCustomerOweVisit) {
		this.lastCustomerOweVisit = lastCustomerOweVisit;
	}

	public CustomerOweFollower getCustomerOweFollower() {
		return customerOweFollower;
	}

	public void setCustomerOweFollower(CustomerOweFollower customerOweFollower) {
		this.customerOweFollower = customerOweFollower;
	}


	
	
}
