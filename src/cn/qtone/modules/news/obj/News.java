package cn.qtone.modules.news.obj;

import cn.qtone.modules.customer.obj.Customer;

public class News {
	
	private int newsId;
	
	private int newsCategoryId;
	
	private String subject;
	
	private String content;
	
	private int userBoId;
	
	private int visitNum;
	
	private String photo;
	
	private int orderId;
	
	private String creatDate;
	//四级分类信息
	private ClassNum vclass;

	private String origin;
	
	//信息的录入人，主要用于信息列表的显示
	private String userBoName;
	
	//信息的分类名称，主要用于信息列表的显示
	private String categoryName;
	
	//从im访问的次数
	private int visitNumIM;
	
	private String subtitle; //副标题
	
	 private String startDate ; //生效日期
	  private String expiryDate ; //失效日期

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public News() {
		super();
		// TODO Auto-generated constructor stub
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

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getUserBoId() {
		return userBoId;
	}

	public void setUserBoId(int userBoId) {
		this.userBoId = userBoId;
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	public int getNewsCategoryId() {
		return newsCategoryId;
	}

	public void setNewsCategoryId(int newsCategoryId) {
		this.newsCategoryId = newsCategoryId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUserBoName() {
		return userBoName;
	}

	public void setUserBoName(String userBoName) {
		this.userBoName = userBoName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getVisitNumIM() {
		return visitNumIM;
	}

	public void setVisitNumIM(int visitNumIM) {
		this.visitNumIM = visitNumIM;
	}
	public ClassNum getVclass(){
		return vclass;
	}

	public void setVclass(ClassNum vclass) {
		this.vclass = vclass;
	}
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
