package cn.qtone.modules.customer.obj.commission;

public class CommissionBank { 
	private  int commissionBankId;
	private  String bankNO;
	private  String bankName;
	
	private String errorMsg;//excel导入时使用
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getCommissionBankId() {
		return commissionBankId;
	}
	public void setCommissionBankId(int commissionBankId) {
		this.commissionBankId = commissionBankId;
	}
	public String getBankNO() {
		return bankNO;
	}
	public void setBankNO(String bankNO) {
		this.bankNO = bankNO;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
