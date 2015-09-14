package cn.qtone.modules.customer.obj;

/**
 * 代理记账公司对象
 * @author Administrator
 *
 */
public class CustomerAgent {

	private int customerAgentId;
	
	private String agentName;
	
	public CustomerAgent(){
		
	}

	public int getCustomerAgentId() {
		return customerAgentId;
	}

	public void setCustomerAgentId(int customerAgentId) {
		this.customerAgentId = customerAgentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	
}
