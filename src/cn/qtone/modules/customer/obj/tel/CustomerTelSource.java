package cn.qtone.modules.customer.obj.tel;

public class CustomerTelSource {
	
	private int customerTelSourceId;
	
	private String name;
	
	private String sourceSQL;
	
	private String lastRunDate;

	public CustomerTelSource(){
		
	}

	public int getCustomerTelSourceId() {
		return customerTelSourceId;
	}

	public void setCustomerTelSourceId(int customerTelSourceId) {
		this.customerTelSourceId = customerTelSourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceSQL() {
		return sourceSQL;
	}

	public void setSourceSQL(String sourceSQL) {
		this.sourceSQL = sourceSQL;
	}

	public String getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(String lastRunDate) {
		this.lastRunDate = lastRunDate;
	}
	
	
}
