package cn.qtone.modules.customer.obj;

/**
 * 导入专用设备出库单对象
 * @author Administrator
 *
 */
public class CustomerUpgradeCard {
	
	private int importChangeCardId;
	
	private String taxNO;
	
	private String companyName;
	
	private double cost;
	
	private String deviceNO;
	
	private int flag;
	
	private String msg;
	
	private String upgradeDate;
	
	private String importDate;

	public CustomerUpgradeCard(){
		
	}

	public int getImportChangeCardId() {
		return importChangeCardId;
	}

	public void setImportChangeCardId(int importChangeCardId) {
		this.importChangeCardId = importChangeCardId;
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

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getDeviceNO() {
		return deviceNO;
	}

	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public String getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(String upgradeDate) {
		this.upgradeDate = upgradeDate;
	}
	
	
}
