package cn.qtone.modules.customer.obj;

/**
 * 修改企业装卡状态审核表，审核通过才真正更新企业的装卡状态
 * @author Administrator
 *
 */
public class CustomeIsInstallCardChange {

	private int customeIsInstallCardChangeId;

	private int customerId;
	
	private Customer customer;
	
	private int isInstallCardFrom;
	
	private int isInstallCardTo;
	
	private int modifier;
	
	private String modifyDate;
	
	private int isCheck;
	
	private String checkDate;
	
	private int checker;
	
	public CustomeIsInstallCardChange(){		
	}

	public int getCustomeIsInstallCardChangeId() {
		return customeIsInstallCardChangeId;
	}

	public void setCustomeIsInstallCardChangeId(int customeIsInstallCardChangeId) {
		this.customeIsInstallCardChangeId = customeIsInstallCardChangeId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getIsInstallCardFrom() {
		return isInstallCardFrom;
	}

	public void setIsInstallCardFrom(int isInstallCardFrom) {
		this.isInstallCardFrom = isInstallCardFrom;
	}

	public int getIsInstallCardTo() {
		return isInstallCardTo;
	}

	public void setIsInstallCardTo(int isInstallCardTo) {
		this.isInstallCardTo = isInstallCardTo;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public int getChecker() {
		return checker;
	}

	public void setChecker(int checker) {
		this.checker = checker;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

}
