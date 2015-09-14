package cn.qtone.modules.customer.obj;
/**
 * 客户图片对象
 * @author Administrator
 *
 */
public class CustomerPic {

	private int customerPicId;
	
	private int customerId;
	
	private String picPath;
	
	private String createDate;
	
	//图片类型
	private int flag;
	
	private String flagName;
	
	public CustomerPic(){	
	}

	public int getCustomerPicId() {
		return customerPicId;
	}

	public void setCustomerPicId(int customerPicId) {
		this.customerPicId = customerPicId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}
	
	
}
