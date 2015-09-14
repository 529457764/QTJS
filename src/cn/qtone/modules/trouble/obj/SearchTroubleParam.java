package cn.qtone.modules.trouble.obj;

public class SearchTroubleParam {
	
	private String keyword;
	private int cityId;
	private int customerId;
	private int troubleId;
	private int userBoId;
	private int tState;
	private String taxNo;
	private int troubleTypeId;
	private int source;
	
	
	public int getTroubleId() {
		return troubleId;
	}

	public void setTroubleId(int troubleId) {
		this.troubleId = troubleId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public SearchTroubleParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public int getTState() {
		return tState;
	}

	public void setTState(int state) {
		tState = state;
	}

	public int getUserBoId() {
		return userBoId;
	}

	public void setUserBoId(int userBoId) {
		this.userBoId = userBoId;
	}

	public int getTroubleTypeId() {
		return troubleTypeId;
	}

	public void setTroubleTypeId(int troubleTypeId) {
		this.troubleTypeId = troubleTypeId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

}
