package cn.qtone.modules.customer.obj.tmp;

/**
 * 从临时表导入企业的对象
 * @author Administrator
 *
 */
public class TmpImportCustomer {

	private String taxNO;
	
	private String companyName;
	
	private int tmpImportCustomerId;
	
	private String bossName;
	
	private String linkman;
	
	private String tel;
	
	private String address;
	
	private int importState;
	
	public TmpImportCustomer(){
		super();
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

	public int getTmpImportCustomerId() {
		return tmpImportCustomerId;
	}

	public void setTmpImportCustomerId(int tmpImportCustomerId) {
		this.tmpImportCustomerId = tmpImportCustomerId;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getImportState() {
		return importState;
	}

	public void setImportState(int importState) {
		this.importState = importState;
	}
	
	
}
