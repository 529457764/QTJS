package cn.qtone.modules.customer.obj.ht;

public class HtCardInstallForm {

	 private int htCardInstallFormId; 
	 private int customerId; 
	 private String companyName; 
	 private String taxNO; 
	 private String address; 
	 private int cityId; 
	 private String linkman; 
	 private String tel; 
	 private String mobile; 
	 private String pc; 
	 private String printer; 
	 
	 private String cardNO; 
	 private String ICNO; 
	 private int pcNO; 
	 private int installType; 
	 private int installState; 
	 private String failedInfo; 
	 private String remark; 
	 private int installer; 
	 private String signer; 
 
	 private String installDate;
	 private int htNumber;
	 private String htNumberPrefix;
	 private int softVersion;	 
	 private String zysbckd;//专用设备出库单单号
	 
	 /**
	  * 是否是报税盘Ⅲ安装单
	  */
	 private int isBSP;

	public String getZysbckd() {
		return zysbckd;
	}
	public void setZysbckd(String zysbckd) {
		this.zysbckd = zysbckd;
	}
	public int getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(int softVersion) {
		this.softVersion = softVersion;
	}
	public int getHtNumber() {
		return htNumber;
	}
	public void setHtNumber(int htNumber) {
		this.htNumber = htNumber;
	}
	public int getHtCardInstallFormId() {
		return htCardInstallFormId;
	}
	public void setHtCardInstallFormId(int htCardInstallFormId) {
		this.htCardInstallFormId = htCardInstallFormId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTaxNO() {
		return taxNO;
	}
	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPc() {
		return pc;
	}
	public void setPc(String pc) {
		this.pc = pc;
	}
	public String getPrinter() {
		return printer;
	}
	public void setPrinter(String printer) {
		this.printer = printer;
	}
	 
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	public String getICNO() {
		return ICNO;
	}
	public void setICNO(String icno) {
		ICNO = icno;
	}
	public int getPcNO() {
		return pcNO;
	}
	public void setPcNO(int pcNO) {
		this.pcNO = pcNO;
	}
	public int getInstallType() {
		return installType;
	}
	public void setInstallType(int installType) {
		this.installType = installType;
	}
	public int getInstallState() {
		return installState;
	}
	public void setInstallState(int installState) {
		this.installState = installState;
	}
	public String getFailedInfo() {
		return failedInfo;
	}
	public void setFailedInfo(String failedInfo) {
		this.failedInfo = failedInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getInstaller() {
		return installer;
	}
	public void setInstaller(int installer) {
		this.installer = installer;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
	}
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public int getIsBSP() {
		return isBSP;
	}
	public void setIsBSP(int isBSP) {
		this.isBSP = isBSP;
	}
	public String getHtNumberPrefix() {
		return htNumberPrefix;
	}
	public void setHtNumberPrefix(String htNumberPrefix) {
		this.htNumberPrefix = htNumberPrefix;
	}
 
	
	
}
