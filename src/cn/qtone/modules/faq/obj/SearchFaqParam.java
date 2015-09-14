package cn.qtone.modules.faq.obj;

public class SearchFaqParam {
	
	private String keyword;
	
	private int customerId;
	
	private int faqCategoryId;
	
	private int faqId;
	
	private int isAnswer; //是否已经回复 2 没有回复，1已经回复
	
	private int cityId;//企业所属镇区id
	
	private int userBoId;//回复者id即后台用户id
	
	//前台是否显示
	private int isShow;
	
	//是否审核通过
	private int isChecked;
	
	//来源：1.金税家园，2.微信
	private int source;

	public int getIsAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(int isAnswer) {
		this.isAnswer = isAnswer;
	}

	public int getFaqId() {
		return faqId;
	}

	public void setFaqId(int faqId) {
		this.faqId = faqId;
	}

	public SearchFaqParam() {
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

	public int getFaqCategoryId() {
		return faqCategoryId;
	}

	public void setFaqCategoryId(int faqCategoryId) {
		this.faqCategoryId = faqCategoryId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getUserBoId() {
		return userBoId;
	}

	public void setUserBoId(int userBoId) {
		this.userBoId = userBoId;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

}
