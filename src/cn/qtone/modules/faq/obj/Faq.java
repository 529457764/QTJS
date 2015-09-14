package cn.qtone.modules.faq.obj;

public class Faq {
	
	private int faqId;
	
	private int faqCategoryId;
	
	private String question;
	
	private String questionTitle;
	
	private String answer;
	
	private int userBoId;
	
	private int isChecked;
	
	/**
	 * 后台用户工号
	 */
	private String number;
	
	private String askDate;
	
	private String answerDate;
	
	private int isShow;
	
	private int customerId;
	
	private int customerAccountId;
	
	/**
	 * 公司名称，用于faq的列表显示
	 */
	private String companyName;
	
	private String linkman;
	
	private String tel;
	
	private int source;

	public Faq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}

	public String getAskDate() {
		return askDate;
	}

	public void setAskDate(String askDate) {
		this.askDate = askDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getFaqId() {
		return faqId;
	}

	public void setFaqId(int faqId) {
		this.faqId = faqId;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getUserBoId() {
		return userBoId;
	}

	public void setUserBoId(int userBoId) {
		this.userBoId = userBoId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(int customerAccountId) {
		this.customerAccountId = customerAccountId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questiontTitle) {
		this.questionTitle = questiontTitle;
	}

	public int getFaqCategoryId() {
		return faqCategoryId;
	}

	public void setFaqCategoryId(int faqCategoryId) {
		this.faqCategoryId = faqCategoryId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
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

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

}
