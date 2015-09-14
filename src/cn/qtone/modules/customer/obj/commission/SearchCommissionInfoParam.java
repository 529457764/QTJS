package cn.qtone.modules.customer.obj.commission;

public class SearchCommissionInfoParam {
	private String taxNO;
	private String companyName; 
	private String bankName;
	private String createDateStart;
	private String createDateEnd;
	private int isYingZhuanZeng;
	private String number;
	private int numberIsInvalid;
	private int isBankOfChina;
	private int isExport;
	private int isSign;
	private int commissionSource;//来源 1手工添加 2维护

	private String signDateStart;
	private String signDateEnd;
	
	public int getIsSign() {
		return isSign;
	}
	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}
	public int getIsExport() {
		return isExport;
	}
	public void setIsExport(int isExport) {
		this.isExport = isExport;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCreateDateStart() {
		return createDateStart;
	}
	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}
	public String getCreateDateEnd() {
		return createDateEnd;
	}
	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
	public int getIsYingZhuanZeng() {
		return isYingZhuanZeng;
	}
	public int getNumberIsInvalid() {
		return numberIsInvalid;
	}
	public void setNumberIsInvalid(int numberIsInvalid) {
		this.numberIsInvalid = numberIsInvalid;
	}
	public void setIsYingZhuanZeng(int isYingZhuanZeng) {
		this.isYingZhuanZeng = isYingZhuanZeng;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	 
	public int getIsBankOfChina() {
		return isBankOfChina;
	}
	public void setIsBankOfChina(int isBankOfChina) {
		this.isBankOfChina = isBankOfChina;
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
	public int getCommissionSource() {
		return commissionSource;
	}
	public void setCommissionSource(int commissionSource) {
		this.commissionSource = commissionSource;
	}
	public String getSignDateStart() {
		return signDateStart;
	}
	public void setSignDateStart(String signDateStart) {
		this.signDateStart = signDateStart;
	}
	public String getSignDateEnd() {
		return signDateEnd;
	}
	public void setSignDateEnd(String signDateEnd) {
		this.signDateEnd = signDateEnd;
	}
	
}
