package cn.qtone.modules.customer.obj;

/**
 * 代理记账公司的员工对象
 * @author Administrator
 *
 */
public class CustomerAgentStaff {

	private int customerAgentStaffId;
	
	/**
	 * 员工名称
	 */
	private String staffName;
	
	/**
	 * 员工电话
	 */
	private String tel;
	
	/**
	 * 代理记账公司id
	 */
	private int customerId;
	
	
	public CustomerAgentStaff(){
		
	}

	public int getCustomerAgentStaffId() {
		return customerAgentStaffId;
	}

	public void setCustomerAgentStaffId(int customerAgentStaffId) {
		this.customerAgentStaffId = customerAgentStaffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
}
