package cn.qtone.modules.customer.obj.owe;
/**
 * 欠费企业回访记录对象
 * @author Administrator
 *
 */
public class CustomerOweVisit {
	
	private int visitUserBoId;
	
	private int customerId;
	
	private String visitRemark;//回访备注
	
	private String visitDate;//跟进情况回访时间
	
	private int telId;//绑定的录音

	private String telPath;//录音路径
	
	private String linkMan;//联系人
	private String telNo;//联系电话
	private int customerOweVisitId;
	public CustomerOweVisit(){
		
	}

	public int getCustomerOweVisitId() {
		return customerOweVisitId;
	}

	public void setCustomerOweVisitId(int customerOweVisitId) {
		this.customerOweVisitId = customerOweVisitId;
	}
	public int getVisitUserBoId() {
		return visitUserBoId;
	}

	public void setVisitUserBoId(int visitUserBoId) {
		this.visitUserBoId = visitUserBoId;
	}
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getVisitRemark() {
		return visitRemark;
	}

	public void setVisitRemark(String visitRemark) {
		this.visitRemark = visitRemark;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public int getTelId() {
		return telId;
	}

	public void setTelId(int telId) {
		this.telId = telId;
	}

	public String getTelPath() {
		return telPath;
	}

	public void setTelPath(String telPath) {
		this.telPath = telPath;
	}
	
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

}
