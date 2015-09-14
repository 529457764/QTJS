package cn.qtone.modules.customer.obj;
import cn.qtone.modules.followCus.obj.SearchAssignChangeParam;
import cn.qtone.modules.followCus.obj.SearchFollowCusStateParam;

/**
 * 搜索企业参数
 * @author Administrator
 *
 */
public class SearchCustomerParam {
	
	/**
	 * 服务单位
	 */
	private int fwdw;
	/**
	 * 开票系统升级状态
	 */
	private int isUpgradeCard;
	
	/**
	 * 全文搜索得到的客户id
	 */
	private String customerIdsFullText;
	/**
	 * 两卡发行日期开始日期
	 */
	private String publishDateStart;
	
	/**
	 * 两卡发行日期结束日期
	 */
	private String publishDateEnd;
	
	/**
	 * 下来选择客户时直接使用全文搜索，因为此时不用关联搜索
	 */
	private boolean isUseFullText;
	
	private String cancleCusDateStart;
	
	private String cancleCusDateEnd;
	/**
	 * 是否前台讲解流程 1不是 2是
	 */
	private int qtjjlc;
	/**
	 * 搜索是否修改了暂不开票
	 */
	private int isModZBKP;
	
	/**
	 * 搜索修改了暂不开票的审批状态
	 */
	private SearchCustomeIsInstallCardChangeParam searchCustomeIsInstallCardChangeParam;
	
	/**
	 * 跟进人修改记录查询参数
	 */
	private SearchAssignChangeParam searchAssignChangeParam; 
	
	private String source;

	/**
	 * 购买1416订单日期
	 */
	private String buy1416DateStart;

	/**
	 * 购买1416订单日期
	 */
	private String buy1416DateEnd;
	
	/**
	 * 认定日期设置日期
	 */
	private String confirmDateSetDateStart;
	
	/**
	 * 认定日期设置日期
	 */
	private String confirmDateSetDateEnd;
	/**
	 * 是否报税软件服务企业 1不是 2是
	 */
	private int isServerDzsb;
	
	/**
	 * 是否有报税软件 0不限 1无 2有
	 */
	private int hasBSRJ;

	/**
	 * 是否有报税软件（主要是接口使用，查询数据库中的真实值） 1没有 2有(未确认) 3有(已确认) 4有(已确认无)
	 */
	private int hasBSRJDB;
	
	/**
	 * 新企业不需上门 1要跟进 2不用跟进
	 */
	private int unNeedFollow;

	/**
	 * 新企业不需上门 1要上门 2不用上门
	 */
	private int unNeedVisit;
	
	/**
	 * 新企业不需回访 1要回访 2不用回访
	 */
	private int unNeedReturnvisit;
	
	/**
	 * 是否指定服务电子申报企业 2是
	 */
	private int isAssignDzsb;
	
	/**
	 * 是否签订委托代扣协议
	 */
	private int commissionIsSign;
	
	/**
	 * 是否从暂不开票转为已装卡
	 */
	private int isZBKPtoInstallCard;
	
	/**
	 * 营改增前已装卡 1未安装 2已安装
	 */
	private int isInstallCardBeforeYGZ;
	
	/**
	 * 转型前企业是否安装金税卡 1未安装 2已安装
	 */
	private int isRelateInstallCard;
	
	private String companyName;
	
	private int taxType;
	
	private String taxNO;
	
	private String cityIds;
	
	private String tel;
	private String fax;//传真号码
	private String mobile;
	
	/**
	 * 是否激活，1未激活，2已激活
	 */
	private int isActive;
	
	private String installCardDateStart;
	
	private String installCardDateEnd;
	
	/**
	 * 今年是否进行了回访：1没有 2回访了
	 */	
	private int isReturnvisit;

	//认定日期
	private String confirmDateStart;

	//认定日期
	private String confirmDateEnd;
	
	//企业是否注销 1没有注销，2已注销
	private int isCancle;
	
//	//金税卡安装情况  :1没有安装，2装了,3注销
//	private int isInstallCard;

	//金税卡安装情况  :1没有安装，2装了,3注销
	private String isInstallCard;

	/**
	 * 是否重要客户
	 * 0不限 1不是 2是
	 */
	private int isImportance;
	
	/**
	 * 是否黑名单 2是 1不是
	 */
	private int isBlacklist;
	
	private int customerCancleType;
	
	private int cardCancleType;
	
	
	/**
	 * 黑名单类型
	 */
	private int blackType;
	
	private String importDate;//导入日期

	private String importDateStart;//导入日期
	
	private String importDateEnd;//导入日期
	
	private int importDateIsNull;//导入日期为空：2为空  1不为空
	
	private int scannerType;//扫描系统
	
	/**
	 * 联系人
	 */
	private String linkman;
	
	private int isPay336;
	
	/**
	 * 银行账号
	 */
	private String bankAccount;
	
	/**
	 * 是否个体转型
	 */
	private int isRelate;
	
	//跟进人
	private String followers;
//	//是否新企业
//	private int isNew;
	
	//是否分配
	private int isAssign;
	
	//更新日期最小值
	private String modifyDateStart;
	
	//更新日期最大值
	private String modifyDateEnd;
	
	//添加日期最小值
	private String createDateStart;
	
	//添加日期最大值
	private String createDateEnd;
	
	//搜索跟进状态参数
	private SearchFollowCusStateParam searchFollowCusStateParam;
	
	private String orderField;
	
	//分配日期开始
	private String assignDateStart;
	
	//分配日期结束
	private String assignDateEnd;
	
	/**
	 * 是否是代理记账公司 1不是 2是
	 */
	private int isAgent;
	
	/**
	 * 代理记账公司的id
	 */
	private int customerAgentId;
	
	/**
	 * 代理记账公司是否前台显示
	 */
	private int isShow;
	
	private String customerIds;
	
	private int softVersion;
	
	private int isYingZhuanZeng;
	
	private String ygzDateStart;
	
	private String ygzDateEnd;
	

	private int isByAgent;//是否使用代理记账公司记账
	
	private int isLastThreeMonth=0;//是否是最后三个月的联系2:是
	
	private int isInstallBSP;//是否安装了报税盘:1没有安装，2装了

	private int isCustomerWx;//是否绑定了微信账号：1.没有绑定   2.绑定了
	
	public int getIsInstallBSP() {
		return isInstallBSP;
	}

	public void setIsInstallBSP(int isInstallBSP) {
		this.isInstallBSP = isInstallBSP;
	}
	
	public int getIsLastThreeMonth() {
		return isLastThreeMonth;
	}

	public void setIsLastThreeMonth(int isLastThreeMonth) {
		this.isLastThreeMonth = isLastThreeMonth;
	}
	
	public int getIsByAgent() {
		return isByAgent;
	}

	public void setIsByAgent(int isByAgent) {
		this.isByAgent = isByAgent;
	}

	public int getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(int softVersion) {
		this.softVersion = softVersion;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public SearchCustomerParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTaxNO() {
		return taxNO;
	}

	public void setTaxNO(String taxNO) {
		this.taxNO = taxNO;
	}

	public int getTaxType() {
		return taxType;
	}

	public void setTaxType(int taxType) {
		this.taxType = taxType;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getIsReturnvisit() {
		return isReturnvisit;
	}

	public void setIsReturnvisit(int isReturnvisit) {
		this.isReturnvisit = isReturnvisit;
	}

	public String getConfirmDateStart() {
		return confirmDateStart;
	}

	public void setConfirmDateStart(String confirmDateStart) {
		this.confirmDateStart = confirmDateStart;
	}

	public String getConfirmDateEnd() {
		return confirmDateEnd;
	}

	public void setConfirmDateEnd(String confirmDateEnd) {
		this.confirmDateEnd = confirmDateEnd;
	}

	public String getInstallCardDateStart() {
		return installCardDateStart;
	}

	public void setInstallCardDateStart(String installCardDateStart) {
		this.installCardDateStart = installCardDateStart;
	}

	public String getInstallCardDateEnd() {
		return installCardDateEnd;
	}

	public void setInstallCardDateEnd(String installCardDateEnd) {
		this.installCardDateEnd = installCardDateEnd;
	}

	public int getIsCancle() {
		return isCancle;
	}

	public void setIsCancle(int isCancle) {
		this.isCancle = isCancle;
	}

//	public int getIsInstallCard() {
//		return isInstallCard;
//	}
//
//	public void setIsInstallCard(int isInstallCard) {
//		this.isInstallCard = isInstallCard;
//	}

	public String getIsInstallCard() {
		return isInstallCard;
	}

	public void setIsInstallCard(String isInstallCard) {
		this.isInstallCard = isInstallCard;
	}

	public int getIsImportance() {
		return isImportance;
	}

	public void setIsImportance(int isImportance) {
		this.isImportance = isImportance;
	}

	public int getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(int isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public int getBlackType() {
		return blackType;
	}

	public void setBlackType(int blackType) {
		this.blackType = blackType;
	}

	public int getCardCancleType() {
		return cardCancleType;
	}

	public void setCardCancleType(int cardCancleType) {
		this.cardCancleType = cardCancleType;
	}

	public int getCustomerCancleType() {
		return customerCancleType;
	}

	public void setCustomerCancleType(int customerCancleType) {
		this.customerCancleType = customerCancleType;
	}


	public int getScannerType() {
		return scannerType;
	}

	public void setScannerType(int scannerType) {
		this.scannerType = scannerType;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public int getIsPay336() {
		return isPay336;
	}

	public void setIsPay336(int isPay336) {
		this.isPay336 = isPay336;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getIsRelate() {
		return isRelate;
	}

	public void setIsRelate(int isRelate) {
		this.isRelate = isRelate;
	}

//	public int getIsNew() {
//		return isNew;
//	}
//
//	public void setIsNew(int isNew) {
//		this.isNew = isNew;
//	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

	public int getIsAssign() {
		return isAssign;
	}

	public void setIsAssign(int isAssign) {
		this.isAssign = isAssign;
	}

	public String getModifyDateStart() {
		return modifyDateStart;
	}

	public void setModifyDateStart(String modifyDateStart) {
		this.modifyDateStart = modifyDateStart;
	}

	public String getModifyDateEnd() {
		return modifyDateEnd;
	}

	public void setModifyDateEnd(String modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public SearchFollowCusStateParam getSearchFollowCusStateParam() {
		return searchFollowCusStateParam;
	}

	public void setSearchFollowCusStateParam(
			SearchFollowCusStateParam searchFollowCusStateParam) {
		this.searchFollowCusStateParam = searchFollowCusStateParam;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getAssignDateStart() {
		return assignDateStart;
	}

	public void setAssignDateStart(String assignDateStart) {
		this.assignDateStart = assignDateStart;
	}

	public String getAssignDateEnd() {
		return assignDateEnd;
	}

	public void setAssignDateEnd(String assignDateEnd) {
		this.assignDateEnd = assignDateEnd;
	}

	public int getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(int isAgent) {
		this.isAgent = isAgent;
	}

	public int getCustomerAgentId() {
		return customerAgentId;
	}

	public void setCustomerAgentId(int customerAgentId) {
		this.customerAgentId = customerAgentId;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}

	public int getIsRelateInstallCard() {
		return isRelateInstallCard;
	}

	public void setIsRelateInstallCard(int isRelateInstallCard) {
		this.isRelateInstallCard = isRelateInstallCard;
	}

	public int getIsYingZhuanZeng() {
		return isYingZhuanZeng;
	}

	public void setIsYingZhuanZeng(int isYingZhuanZeng) {
		this.isYingZhuanZeng = isYingZhuanZeng;
	}

	public String getYgzDateStart() {
		return ygzDateStart;
	}

	public void setYgzDateStart(String ygzDateStart) {
		this.ygzDateStart = ygzDateStart;
	}

	public String getYgzDateEnd() {
		return ygzDateEnd;
	}

	public void setYgzDateEnd(String ygzDateEnd) {
		this.ygzDateEnd = ygzDateEnd;
	}

	public int getIsInstallCardBeforeYGZ() {
		return isInstallCardBeforeYGZ;
	}

	public void setIsInstallCardBeforeYGZ(int isInstallCardBeforeYGZ) {
		this.isInstallCardBeforeYGZ = isInstallCardBeforeYGZ;
	}

	public int getIsZBKPtoInstallCard() {
		return isZBKPtoInstallCard;
	}

	public void setIsZBKPtoInstallCard(int isZBKPtoInstallCard) {
		this.isZBKPtoInstallCard = isZBKPtoInstallCard;
	}

	public int getCommissionIsSign() {
		return commissionIsSign;
	}

	public void setCommissionIsSign(int commissionIsSign) {
		this.commissionIsSign = commissionIsSign;
	}

	public int getIsAssignDzsb() {
		return isAssignDzsb;
	}

	public void setIsAssignDzsb(int isAssignDzsb) {
		this.isAssignDzsb = isAssignDzsb;
	}

	public int getUnNeedFollow() {
		return unNeedFollow;
	}

	public void setUnNeedFollow(int unNeedFollow) {
		this.unNeedFollow = unNeedFollow;
	}

	public String getImportDateStart() {
		return importDateStart;
	}

	public void setImportDateStart(String importDateStart) {
		this.importDateStart = importDateStart;
	}

	public String getImportDateEnd() {
		return importDateEnd;
	}

	public void setImportDateEnd(String importDateEnd) {
		this.importDateEnd = importDateEnd;
	}

	public int getImportDateIsNull() {
		return importDateIsNull;
	}

	public void setImportDateIsNull(int importDateIsNull) {
		this.importDateIsNull = importDateIsNull;
	}

	public int getUnNeedReturnvisit() {
		return unNeedReturnvisit;
	}

	public void setUnNeedReturnvisit(int unNeedReturnvisit) {
		this.unNeedReturnvisit = unNeedReturnvisit;
	}

	public int getHasBSRJ() {
		return hasBSRJ;
	}

	public void setHasBSRJ(int hasBSRJ) {
		this.hasBSRJ = hasBSRJ;
	}

	public int getIsServerDzsb() {
		return isServerDzsb;
	}

	public void setIsServerDzsb(int isServerDzsb) {
		this.isServerDzsb = isServerDzsb;
	}


	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getConfirmDateSetDateStart() {
		return confirmDateSetDateStart;
	}

	public void setConfirmDateSetDateStart(String confirmDateSetDateStart) {
		this.confirmDateSetDateStart = confirmDateSetDateStart;
	}

	public String getConfirmDateSetDateEnd() {
		return confirmDateSetDateEnd;
	}

	public void setConfirmDateSetDateEnd(String confirmDateSetDateEnd) {
		this.confirmDateSetDateEnd = confirmDateSetDateEnd;
	}

	public String getBuy1416DateStart() {
		return buy1416DateStart;
	}

	public void setBuy1416DateStart(String buy1416DateStart) {
		this.buy1416DateStart = buy1416DateStart;
	}

	public String getBuy1416DateEnd() {
		return buy1416DateEnd;
	}

	public void setBuy1416DateEnd(String buy1416DateEnd) {
		this.buy1416DateEnd = buy1416DateEnd;
	}

	public SearchAssignChangeParam getSearchAssignChangeParam() {
		return searchAssignChangeParam;
	}

	public int getUnNeedVisit() {
		return unNeedVisit;
	}

	public void setUnNeedVisit(int unNeedVisit) {
		this.unNeedVisit = unNeedVisit;
	}

	public void setSearchAssignChangeParam(
			SearchAssignChangeParam searchAssignChangeParam) {
		this.searchAssignChangeParam = searchAssignChangeParam;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public SearchCustomeIsInstallCardChangeParam getSearchCustomeIsInstallCardChangeParam() {
		return searchCustomeIsInstallCardChangeParam;
	}

	public void setSearchCustomeIsInstallCardChangeParam(
			SearchCustomeIsInstallCardChangeParam searchCustomeIsInstallCardChangeParam) {
		this.searchCustomeIsInstallCardChangeParam = searchCustomeIsInstallCardChangeParam;
	}

	public int getIsModZBKP() {
		return isModZBKP;
	}

	public void setIsModZBKP(int isModZBKP) {
		this.isModZBKP = isModZBKP;
	}

	public int getHasBSRJDB() {
		return hasBSRJDB;
	}

	public void setHasBSRJDB(int hasBSRJDB) {
		this.hasBSRJDB = hasBSRJDB;
	}

	public int getQtjjlc() {
		return qtjjlc;
	}

	public void setQtjjlc(int qtjjlc) {
		this.qtjjlc = qtjjlc;
	}

	public String getCancleCusDateStart() {
		return cancleCusDateStart;
	}

	public void setCancleCusDateStart(String cancleCusDateStart) {
		this.cancleCusDateStart = cancleCusDateStart;
	}

	public String getCancleCusDateEnd() {
		return cancleCusDateEnd;
	}

	public void setCancleCusDateEnd(String cancleCusDateEnd) {
		this.cancleCusDateEnd = cancleCusDateEnd;
	}

	public boolean getIsUseFullText() {
		return isUseFullText;
	}

	public void setIsUseFullText(boolean isUseFullText) {
		this.isUseFullText = isUseFullText;
	}

	public String getPublishDateStart() {
		return publishDateStart;
	}

	public void setPublishDateStart(String publishDateStart) {
		this.publishDateStart = publishDateStart;
	}

	public String getPublishDateEnd() {
		return publishDateEnd;
	}

	public void setPublishDateEnd(String publishDateEnd) {
		this.publishDateEnd = publishDateEnd;
	}

	public String getCustomerIdsFullText() {
		return customerIdsFullText;
	}

	public void setCustomerIdsFullText(String customerIdsFullText) {
		this.customerIdsFullText = customerIdsFullText;
	}

	public int getIsUpgradeCard() {
		return isUpgradeCard;
	}

	public void setIsUpgradeCard(int isUpgradeCard) {
		this.isUpgradeCard = isUpgradeCard;
	}

	public int getFwdw() {
		return fwdw;
	}

	public void setFwdw(int fwdw) {
		this.fwdw = fwdw;
	}

	public int getIsCustomerWx() {
		return isCustomerWx;
	}

	public void setIsCustomerWx(int isCustomerWx) {
		this.isCustomerWx = isCustomerWx;
	}




}
