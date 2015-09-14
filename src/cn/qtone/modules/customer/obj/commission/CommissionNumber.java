package cn.qtone.modules.customer.obj.commission;

public class CommissionNumber {
	private  int  commissionNumberId;
	private  String  number;
	private int customerId;//对应的公司
	private int isExport;
	
	public int getIsExport() {
		return isExport;
	}
	public void setIsExport(int isExport) {
		this.isExport = isExport;
	}
	private String errorMsg;//excel导入时使用
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCommissionNumberId() {
		return commissionNumberId;
	}
	public void setCommissionNumberId(int commissionNumberId) {
		this.commissionNumberId = commissionNumberId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	private  int  state;
}
