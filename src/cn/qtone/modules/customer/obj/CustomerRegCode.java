package cn.qtone.modules.customer.obj;

public class CustomerRegCode {

	private int customerRegCodeId;
	private String taxNO;
	private int customerId;
	private String deductCode; //华洲抵扣联注册码
	private String deductCodeExpireDate;  //华洲抵扣联注册码到期时间
	private String huyouCode; //泸友注册码
	private String huyouCodeExpireDate; //泸友注册码到期时间
	private String softcode;  //	电子报税软件注册码
	private String softcodeMsg;  //	电子报税软件注册码查询失败信息
	private String softcodeExpireDate;  //	电子报税软件注册码到期时间
	
	public int getCustomerRegCodeId() {
		return customerRegCodeId;
	}
	public void setCustomerRegCodeId(int customerRegCodeId) {
		this.customerRegCodeId = customerRegCodeId;
	}
	public String getDeductCode() {
		return deductCode;
	}
	public void setDeductCode(String deductCode) {
		this.deductCode = deductCode;
	}
	public String getHuyouCode() {
		return huyouCode;
	}
	public void setHuyouCode(String huyouCode) {
		this.huyouCode = huyouCode;
	}
	public String getHuyouCodeExpireDate() {
		return huyouCodeExpireDate;
	}
	public void setHuyouCodeExpireDate(String huyouCodeExpireDate) {
		this.huyouCodeExpireDate = huyouCodeExpireDate;
	}
	public String getSoftcode() {
		return softcode;
	}
	public void setSoftcode(String softcode) {
		this.softcode = softcode;
	}
	public String getTaxNO() {
		return taxNO;
	}
	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}
	public String getDeductCodeExpireDate() {
		return deductCodeExpireDate;
	}
	public void setDeductCodeExpireDate(String deductCodeExpireDate) {
		this.deductCodeExpireDate = deductCodeExpireDate;
	}
	public String getSoftcodeExpireDate() {
		return softcodeExpireDate;
	}
	public void setSoftcodeExpireDate(String softcodeExpireDate) {
		this.softcodeExpireDate = softcodeExpireDate;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getSoftcodeMsg() {
		return softcodeMsg;
	}
	public void setSoftcodeMsg(String softcodeMsg) {
		this.softcodeMsg = softcodeMsg;
	}

}
