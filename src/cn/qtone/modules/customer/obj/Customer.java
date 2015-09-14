package cn.qtone.modules.customer.obj;

import cn.qtone.modules.customer.obj.blacklist.CustomerBlacklist;

public class Customer {

	/**
	 * 市级授权服务单位
	 * 1全通 2百望
	 */
	private int fwdw;
	
	/**
	 * 从用友获取客户开票的时间
	 */
	private String updateReceiptDate;
	/**
	 * 金税盘升级状态
	 * 1未升级
	   2已升级（确认）
	   3已升级（客户提出）
	 */
	private int isUpgradeCard;
	
	/**
	 * 专用设备发行日期
	 */
	private String publishDate;
	/**
	 * 1金税卡 2金税盘 3金税盘(白)
	 */
	private int cardType;
	/**
	 * 客户早上开始工作时间
	 */
	private String workTimeAMStart;

	/**
	 * 客户早上结束工作时间
	 */
	private String workTimeAMEnd;
	
	/**
	 * 客户下午开始工作时间
	 */	
	private String workTimePMStart;
	
	/**
	 * 客户下午结束工作时间
	 */		
	private String workTimePMEnd;
	
	/**
	 * 客户需要来前台讲解流程 1不需要 2需要
	 */
	private int qtjjlc;
	
	/**
	 * 报税软件注册码的获取日期
	 */
	private String fretchBSRJDate;
	
	
	/**
	 * 是否提交了报税软件申请表 1没提交 2提交了
	 */
//	private int isApplyBSRJ;
	
	
	/**
	 * 报税软件申请表说明
	 */
//	private String applyBSRJMsg;
	
	/**
	 * 报税软件注册码是否已处理
	 * 1未处理，2已处理
	 */
	private int isHandleBSRJ;
	
	/**
	 * 报税软件注册码处理日期
	 */
	private String handleBSRJDate;
	
	/**
	 * 是否有报税软件
	 */
	private int hasBSRJ;
	/**
	 * 新企业无需上门 1要上门 2无需上门
	 */
	private int unNeedVisit;
	/**
	 * 新企业无需跟进 1要跟进 2无需跟进
	 */
	private int unNeedFollow;
	
	/**
	 * 新企业无需回访 1要回访 2无需回访
	 */
	private int unNeedReturnvisit;
	
	/**
	 * 1不服务企业
2服务企业
3服务5.02软件企业
4指定服务企业
	 */
	private int dzsbState;
	
	private int customerId;
		
	private String taxNO;
	
	private String companyName;
	
	private String companyInfo;
	
	private String bossName;
	
	private String bossTel;
	
	private int bossTelIsblacklist;//是否为黑名单 大于等于1 是
	
	private String fax;
	
	private int cityId;
	
	private int cityIdPre;
	
	private String businessAddress;
	
	private String confirmDate;
	
	/**
	 * 企业注销状态
	 * 1没有注销，2已注销,3非正常户
	 */
	private int isCancle;
	
	private String cancleCusDate;
	
	/**
	 * 设置企业注销的设置日期
	 */
	private String cancleCusSetDate;
	
	private String linkman;
	
	private String tel;
	private int telIsBlacklist; //是否为黑名单 大于等于1 是
	private int mobileIsBlacklist; //是否为黑名单 大于等于1 是
	private String mobile;
	
	private String email;
	
	private int taxType;
	
	private String postCode;
	
	private String bank;
	
	private String bankAccount;
	
	private String homepage;
	
	/**
	 * 1年内是否有回访过
	 */
	private boolean hasReturnvisited;
	
	/**
	 * 是否安装了金税卡:1没有安装，2装了,3注销
	 */
	private int isInstallCard;
	
	/**
	 * 安装金税卡日期
	 */
	private String installCardDate;
	
	/**
	 * 正版标签编号
	 */
	private String sysLegalNO;
	
	private String cardNO;
	
	private String ICNO;
	
	//最后一次联系人
	private String lastLinkman;
	
	//最后一次联系电话
	private String lastTel;
	
	//最后一次联系电话(固话)
	private String lastFixTel;
	
	private String lastMobile;
	
	
//	最后一次联系电话是否为黑名单
	private int lastTelIsBlacklist; //大于等于1 是黑名单号码
	
	//注销金税卡日期
	private String cancleDate;
	
	//装卡状态改变的日期
	private String cancleSetDate;
	
//	/**
//	 * 是否停用扫描仪
//	 * 1正常 2停用
//	 */
//	private int isStopScanner;
	
	/**
	 * 扫描系统类型
	 */
	private int scannerType;
	
	/**
	 * 转换前的扫描系统
	 */
	private int preScannerType;
	
	/**
	 * 转换扫描系统日期
	 */
	private String convertScannerTypeDate;
	
	/**
	 * 联系方式是否无效
	 * 1有效 2无效
	 */
	private int isInvalidCI;
	
	 
	/**
	 * 是否重要客户
	 * 1不是 2是
	 */
	private int isImportance;

	/**
	 * 是否黑名单企业
	 * false 不是 ; true 是
	 */
	private boolean isBlacklist;
	
	/**
	 * 黑名单对象
	 */
	private CustomerBlacklist blacklist;
	
	/**
	 * 代理该公司记账的代理记账公司员工id
	 */
	private int customerAgentStaffId;
	//代理该公司的代理记账公司id
	private int customerAgentId;
	//代理该公司的代理记账公司
	private Customer CustomerAgent;
	/**
	 * 代理该公司记账的代理记账公司记账人对象
	 */	
	private CustomerAgentStaff customerAgentStaff;
	
	private int source;  //数据来源 1工作人员录入，2每月导入，3导入银行收款时导入，4商城注册，5手工导入,6CA导入,7百望企业导入
	private String importDate; //导入时间
	
	private String name;
	private String mobileShop;
	private int birthdayYear;
	private int birthdayMonth;
	private int birthdayDay;
	private int sex;
	//开增值税发票的地址
	private String receipt_address;
	//开增值税发票的电话
	private String receipt_tel;
//	
	/**
	 * 办公地址
	 */
	private String address;
	private String uptAddressDate;//更新办公地址时间
	
	private int cardCancleType; //卡取消类型
	private int customerCancleType;//企业取消类型
	
	private int telBusyTimes;//企业电话忙音或无人接听
	
	private String remark;
	
	/**
	 * 是否用过尚洲
	 * 1否 2是
	 */
	private int hasUseSOZO;
	
	/**
	 * 个体转型前的公司id
	 */
	private String relateCompanyName;
	
	/**
	 * 个体转型前的公司名称
	 */
	private int relateCustomerId;
	
	/**
	 * 是否个体转型 1否 2是
	 */
	private int isRelate;
	
	//是否新企业
	private boolean isNew;
	
	//跟进人
	private int follower;
	
	//分配日期
	private String assignDate;
	
	private int assigner;
	
	private String createDate;
	
	private String receipt_companyName;
	
	private String receipt_taxNO;
	
	/**
	 * 是否代理记账公司 1不是 2是
	 */
	private int isAgent;
	
	/**
	 * 业务范围
	 */
	private String businessArea;
	
	/**
	 * 经营范围
	 */
	private String businessScope;
	
	/**
	 * 代理记账公司是否在前台显示
	 */
	private int isShow;
	
	//迁入日期
	private String ingoingDate;
	
	//是否迁入
	private int isIngoing;
	
	//是否变更为非一般纳税人 1否 2是
	private int isChangeToUnTax;
	
	//变更为非一般纳税人日期
	private String changeToUnTaxDate;

	private int softVersion;//开票软件版本号
	
	private int isYingZhuanZeng;//是否营转增 1 否 ；2 是
	
	private int  isInstallBSP; //报税盘 安装状态
//	private String installBSPDate;//报税盘 安装日期
//	private String cancleBSPDate;//报税盘 取消日期	
	
	private int isInstallCardPre;//之前的装卡状态
	
	private String ygzDate;//营改增日期
	
	private String commissionIsSignStr;//委托代扣状态
	
	private String confirmBSRJDate;//报税软件注册码确认时间
	
	public int getIsInstallBSP() {
		return isInstallBSP;
	}

	public void setIsInstallBSP(int isInstallBSP) {
		this.isInstallBSP = isInstallBSP;
	}

	public int getIsInstallCardPre() {
		return isInstallCardPre;
	}

	public void setIsInstallCardPre(int isInstallCardPre) {
		this.isInstallCardPre = isInstallCardPre;
	}

	public int getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(int softVersion) {
		this.softVersion = softVersion;
	}

	public int getTelBusyTimes() {
		return telBusyTimes;
	}

	public void setTelBusyTimes(int telBusyTimes) {
		this.telBusyTimes = telBusyTimes;
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

	public String getUptAddressDate() {
		return uptAddressDate;
	}

	public void setUptAddressDate(String uptAddressDate) {
		this.uptAddressDate = uptAddressDate;
	}

	public boolean getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(boolean isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getBossTel() {
		return bossTel;
	}

	public void setBossTel(String bossTel) {
		this.bossTel = bossTel;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getIsCancle() {
		return isCancle;
	}

	public void setIsCancle(int isCancle) {
		this.isCancle = isCancle;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public boolean isHasReturnvisited() {
		return hasReturnvisited;
	}

	public void setHasReturnvisited(boolean hasReturnvisited) {
		this.hasReturnvisited = hasReturnvisited;
	}

	public int getIsInstallCard() {
		return isInstallCard;
	}

	public void setIsInstallCard(int isInstallCard) {
		this.isInstallCard = isInstallCard;
	}

	public String getInstallCardDate() {
		return installCardDate;
	}

	public void setInstallCardDate(String installCardDate) {
		this.installCardDate = installCardDate;
	}

	public String getSysLegalNO() {
		return sysLegalNO;
	}

	public void setSysLegalNO(String sysLegalNO) {
		this.sysLegalNO = sysLegalNO;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getICNO() {
		return ICNO;
	}

	public void setICNO(String icno) {
		ICNO = icno;
	}

	public String getLastLinkman() {
		return lastLinkman;
	}

	public void setLastLinkman(String lastLinkman) {
		this.lastLinkman = lastLinkman;
	}

	public String getLastTel() {
		return lastTel;
	}

	public void setLastTel(String lastTel) {
		this.lastTel = lastTel;
	}

	public String getCancleDate() {
		return cancleDate;
	}

	public void setCancleDate(String cancleDate) {
		this.cancleDate = cancleDate;
	}

//	public int getIsStopScanner() {
//		return isStopScanner;
//	}
//
//	public void setIsStopScanner(int isStopScanner) {
//		this.isStopScanner = isStopScanner;
//	}

	public int getIsInvalidCI() {
		return isInvalidCI;
	}

	public void setIsInvalidCI(int isInvalidCI) {
		this.isInvalidCI = isInvalidCI;
	}

 

	public int getIsImportance() {
		return isImportance;
	}

	public void setIsImportance(int isImportance) {
		this.isImportance = isImportance;
	}

	public int getCustomerAgentStaffId() {
		return customerAgentStaffId;
	}

	public void setCustomerAgentStaffId(int customerAgentStaffId) {
		this.customerAgentStaffId = customerAgentStaffId;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public CustomerBlacklist getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(CustomerBlacklist blacklist) {
		this.blacklist = blacklist;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCancleCusDate() {
		return cancleCusDate;
	}

	public void setCancleCusDate(String cancleCusDate) {
		this.cancleCusDate = cancleCusDate;
	}



//	public int getEducation() {
//		return education;
//	}
//
//	public void setEducation(int education) {
//		this.education = education;
//	}
//
//	public int getIndustry() {
//		return industry;
//	}
//
//	public void setIndustry(int industry) {
//		this.industry = industry;
//	}

	public String getMobileShop() {
		return mobileShop;
	}

	public void setMobileShop(String mobileShop) {
		this.mobileShop = mobileShop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getBirthdayDay() {
		return birthdayDay;
	}

	public void setBirthdayDay(int birthdayDay) {
		this.birthdayDay = birthdayDay;
	}

	public int getBirthdayMonth() {
		return birthdayMonth;
	}

	public void setBirthdayMonth(int birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}

	public int getBirthdayYear() {
		return birthdayYear;
	}

	public void setBirthdayYear(int birthdayYear) {
		this.birthdayYear = birthdayYear;
	}

	public String getReceipt_address() {
		return receipt_address;
	}

	public void setReceipt_address(String receipt_address) {
		this.receipt_address = receipt_address;
	}

	public String getReceipt_tel() {
		return receipt_tel;
	}

	public void setReceipt_tel(String receipt_tel) {
		this.receipt_tel = receipt_tel;
	}

	public int getLastTelIsBlacklist() {
		return lastTelIsBlacklist;
	}

	public void setLastTelIsBlacklist(int lastTelIsBlacklist) {
		this.lastTelIsBlacklist = lastTelIsBlacklist;
	}

	public void setBlacklist(boolean isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public int getTelIsBlacklist() {
		return telIsBlacklist;
	}

	public void setTelIsBlacklist(int telIsBlacklist) {
		this.telIsBlacklist = telIsBlacklist;
	}

	public int getBossTelIsblacklist() {
		return bossTelIsblacklist;
	}

	public void setBossTelIsblacklist(int bossTelIsblacklist) {
		this.bossTelIsblacklist = bossTelIsblacklist;
	}



	public int getScannerType() {
		return scannerType;
	}

	public void setScannerType(int scannerType) {
		this.scannerType = scannerType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public int getHasUseSOZO() {
		return hasUseSOZO;
	}

	public void setHasUseSOZO(int hasUseSOZO) {
		this.hasUseSOZO = hasUseSOZO;
	}

	public String getRelateCompanyName() {
		return relateCompanyName;
	}

	public void setRelateCompanyName(String relateCompanyName) {
		this.relateCompanyName = relateCompanyName;
	}

	public int getRelateCustomerId() {
		return relateCustomerId;
	}

	public void setRelateCustomerId(int relateCustomerId) {
		this.relateCustomerId = relateCustomerId;
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

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getReceipt_companyName() {
		return receipt_companyName;
	}

	public void setReceipt_companyName(String receipt_companyName) {
		this.receipt_companyName = receipt_companyName;
	}

	public String getReceipt_taxNO() {
		return receipt_taxNO;
	}

	public void setReceipt_taxNO(String receipt_taxNO) {
		this.receipt_taxNO = receipt_taxNO;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
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

	public CustomerAgentStaff getCustomerAgentStaff() {
		return customerAgentStaff;
	}

	public void setCustomerAgentStaff(CustomerAgentStaff customerAgentStaff) {
		this.customerAgentStaff = customerAgentStaff;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getIngoingDate() {
		return ingoingDate;
	}

	public void setIngoingDate(String ingoingDate) {
		this.ingoingDate = ingoingDate;
	}

	public int getIsIngoing() {
		return isIngoing;
	}

	public void setIsIngoing(int isIngoing) {
		this.isIngoing = isIngoing;
	}

	public String getCancleCusSetDate() {
		return cancleCusSetDate;
	}

	public void setCancleCusSetDate(String cancleCusSetDate) {
		this.cancleCusSetDate = cancleCusSetDate;
	}

	public int getCityIdPre() {
		return cityIdPre;
	}

	public void setCityIdPre(int cityIdPre) {
		this.cityIdPre = cityIdPre;
	}

	public String getCancleSetDate() {
		return cancleSetDate;
	}

	public void setCancleSetDate(String cancleSetDate) {
		this.cancleSetDate = cancleSetDate;
	}

	public int getAssigner() {
		return assigner;
	}

	public void setAssigner(int assigner) {
		this.assigner = assigner;
	}

	public Customer getCustomerAgent() {
		return CustomerAgent;
	}

	public void setCustomerAgent(Customer customerAgent) {
		CustomerAgent = customerAgent;
	}

	public int getIsChangeToUnTax() {
		return isChangeToUnTax;
	}

	public void setIsChangeToUnTax(int isChangeToUnTax) {
		this.isChangeToUnTax = isChangeToUnTax;
	}

	public String getChangeToUnTaxDate() {
		return changeToUnTaxDate;
	}

	public void setChangeToUnTaxDate(String changeToUnTaxDate) {
		this.changeToUnTaxDate = changeToUnTaxDate;
	}

	public int getPreScannerType() {
		return preScannerType;
	}

	public void setPreScannerType(int preScannerType) {
		this.preScannerType = preScannerType;
	}

	public String getConvertScannerTypeDate() {
		return convertScannerTypeDate;
	}

	public void setConvertScannerTypeDate(String convertScannerTypeDate) {
		this.convertScannerTypeDate = convertScannerTypeDate;
	}

	public int getIsYingZhuanZeng() {
		return isYingZhuanZeng;
	}

	public void setIsYingZhuanZeng(int isYingZhuanZeng) {
		this.isYingZhuanZeng = isYingZhuanZeng;
	}

	public String getYgzDate() {
		return ygzDate;
	}

	public void setYgzDate(String ygzDate) {
		this.ygzDate = ygzDate;
	}

	public String getCommissionIsSignStr() {
		return commissionIsSignStr;
	}

	public void setCommissionIsSignStr(String commissionIsSignStr) {
		this.commissionIsSignStr = commissionIsSignStr;
	}

	public int getDzsbState() {
		return dzsbState;
	}

	public void setDzsbState(int dzsbState) {
		this.dzsbState = dzsbState;
	}

	public int getUnNeedFollow() {
		return unNeedFollow;
	}

	public void setUnNeedFollow(int unNeedFollow) {
		this.unNeedFollow = unNeedFollow;
	}

	public String getLastFixTel() {
		return lastFixTel;
	}

	public void setLastFixTel(String lastFixTel) {
		this.lastFixTel = lastFixTel;
	}

	public String getLastMobile() {
		return lastMobile;
	}

	public void setLastMobile(String lastMobile) {
		this.lastMobile = lastMobile;
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

	public int getIsHandleBSRJ() {
		return isHandleBSRJ;
	}

	public void setIsHandleBSRJ(int isHandleBSRJ) {
		this.isHandleBSRJ = isHandleBSRJ;
	}

	public String getHandleBSRJDate() {
		return handleBSRJDate;
	}

	public void setHandleBSRJDate(String handleBSRJDate) {
		this.handleBSRJDate = handleBSRJDate;
	}

	public String getFretchBSRJDate() {
		return fretchBSRJDate;
	}

	public void setFretchBSRJDate(String fretchBSRJDate) {
		this.fretchBSRJDate = fretchBSRJDate;
	}

	public int getUnNeedVisit() {
		return unNeedVisit;
	}

	public void setUnNeedVisit(int unNeedVisit) {
		this.unNeedVisit = unNeedVisit;
	}

	public String getConfirmBSRJDate() {
		return confirmBSRJDate;
	}

	public void setConfirmBSRJDate(String confirmBSRJDate) {
		this.confirmBSRJDate = confirmBSRJDate;
	}

	public int getMobileIsBlacklist() {
		return mobileIsBlacklist;
	}

	public void setMobileIsBlacklist(int mobileIsBlacklist) {
		this.mobileIsBlacklist = mobileIsBlacklist;
	}

	public int getQtjjlc() {
		return qtjjlc;
	}

	public void setQtjjlc(int qtjjlc) {
		this.qtjjlc = qtjjlc;
	}

	public String getWorkTimeAMStart() {
		return workTimeAMStart;
	}

	public void setWorkTimeAMStart(String workTimeAMStart) {
		this.workTimeAMStart = workTimeAMStart;
	}

	public String getWorkTimeAMEnd() {
		return workTimeAMEnd;
	}

	public void setWorkTimeAMEnd(String workTimeAMEnd) {
		this.workTimeAMEnd = workTimeAMEnd;
	}

	public String getWorkTimePMStart() {
		return workTimePMStart;
	}

	public void setWorkTimePMStart(String workTimePMStart) {
		this.workTimePMStart = workTimePMStart;
	}

	public String getWorkTimePMEnd() {
		return workTimePMEnd;
	}

	public void setWorkTimePMEnd(String workTimePMEnd) {
		this.workTimePMEnd = workTimePMEnd;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getIsUpgradeCard() {
		return isUpgradeCard;
	}

	public void setIsUpgradeCard(int isUpgradeCard) {
		this.isUpgradeCard = isUpgradeCard;
	}

	public String getUpdateReceiptDate() {
		return updateReceiptDate;
	}

	public void setUpdateReceiptDate(String updateReceiptDate) {
		this.updateReceiptDate = updateReceiptDate;
	}

	public int getFwdw() {
		return fwdw;
	}

	public void setFwdw(int fwdw) {
		this.fwdw = fwdw;
	}



//	public int getIsApplyBSRJ() {
//		return isApplyBSRJ;
//	}
//
//	public void setIsApplyBSRJ(int isApplyBSRJ) {
//		this.isApplyBSRJ = isApplyBSRJ;
//	}
//
//	public String getApplyBSRJMsg() {
//		return applyBSRJMsg;
//	}
//
//	public void setApplyBSRJMsg(String applyBSRJMsg) {
//		this.applyBSRJMsg = applyBSRJMsg;
//	}






}
