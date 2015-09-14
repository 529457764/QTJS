package cn.qtone.modules.trouble.obj;

public class Trouble {
	
	private int customerId;
	
	private int troubleId;
	
	private int cityId;
	
	private String linkman;
	
	private String tel;
	
	private String visitDate;
	
	private int troubleTypeId;
	
	private String content;
	
	private String address;
	
	private String companyName;
	
	private String result;
	
	private int custometAccountId;
	
	private int userBoId;
	
	private String modifyDate;
	
	
	/**
	 * 1未处理,2处理中，3已完成
	 */
	private int tState;
	
	private String creatDate;
	
	private int source;
	
	public Trouble() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public int gettState() {
		return tState;
	}

	public void settState(int state) {
		tState = state;
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

	public int getTroubleId() {
		return troubleId;
	}

	public void setTroubleId(int troubleId) {
		this.troubleId = troubleId;
	}

	public int getTroubleTypeId() {
		return troubleTypeId;
	}

	public void setTroubleTypeId(int troubleTypeId) {
		this.troubleTypeId = troubleTypeId;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getCustometAccountId() {
		return custometAccountId;
	}

	public void setCustometAccountId(int custometAccountId) {
		this.custometAccountId = custometAccountId;
	}

	public int getUserBoId() {
		return userBoId;
	}

	public void setUserBoId(int userBoId) {
		this.userBoId = userBoId;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

}
