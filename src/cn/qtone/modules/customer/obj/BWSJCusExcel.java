package cn.qtone.modules.customer.obj;

/**
 * 导入注销企业对象
 * @author Administrator
 *
 */
public class BWSJCusExcel {

	private String taxNO;
	
	
	private String errorMsg;
	
	private int rowNum;
	
	public BWSJCusExcel(){}

	public String getTaxNO() {
		return taxNO;
	}

	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}


	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	
}
