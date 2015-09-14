package cn.qtone.modules.customer.obj.blacklist;

public class SearchBlacklistParam {

	/**
	 * 是否建议取消 1否，2是
	 */
	private int isSuggestCancle;
	
	/**
	 * 是否取消黑名单 1取消，2未取消
	 */
	private int flag;
	
	private String companyName;
	
	private String taxNO;
	
	public SearchBlacklistParam(){
		
	}

	public int getIsSuggestCancle() {
		return isSuggestCancle;
	}

	public void setIsSuggestCancle(int isSuggestCancle) {
		this.isSuggestCancle = isSuggestCancle;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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
	
	
}
