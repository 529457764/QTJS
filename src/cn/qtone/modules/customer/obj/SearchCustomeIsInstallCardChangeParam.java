package cn.qtone.modules.customer.obj;

/**
 * 搜索更改装卡状态记录参数
 * @author Administrator
 *
 */

public class SearchCustomeIsInstallCardChangeParam {
	
	private String companyName;
	
	private String taxNO;
	
	private int isCheck;
	
	public SearchCustomeIsInstallCardChangeParam(){
		
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public String getTaxNO() {
		return taxNO;
	}

	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}

}
