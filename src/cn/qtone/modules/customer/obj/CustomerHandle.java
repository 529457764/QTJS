package cn.qtone.modules.customer.obj;

import java.util.*;
import java.sql.*;

import net.sf.json.JSONObject;


import cn.qtone.modules.order.obj.Order;
import cn.qtone.modules.order.obj.OrderHandle;
import cn.qtone.modules.order.obj.OrderProduct;
import cn.qtone.modules.order.obj.OrderProductHandle;
import cn.qtone.modules.order.obj.SearchOrderParam;
import cn.qtone.modules.order.obj.SearchOrderProductParam;
import cn.qtone.modules.product.obj.ProductHandle;
import cn.qtone.modules.telRecord.obj.TelBlacklistHandle;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.sys.config.ModuleConfig;
import cn.qtone.utils.DateClass;
import cn.qtone.utils.StringFunction;
import cn.qtone.utils.db.DBControlManager;
import cn.qtone.utils.dbcp.ConnectionManager;
import cn.qtone.modules.ca.obj.CaCustomerHandle;
import cn.qtone.modules.city.obj.CityHandle;
import cn.qtone.modules.customer.obj.blacklist.CustomerBlacklist;
import cn.qtone.modules.customer.obj.blacklist.CustomerBlacklistHandle;
import cn.qtone.modules.customer.obj.commission.CommissionInfoHandle;
import cn.qtone.modules.customer.obj.tel.*;

public class CustomerHandle extends BaseHandle{

	public CustomerHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 更新服务单位
	 * @param fwdw 1全通 2百望
	 * @param customerId
	 * @return
	 */
	public boolean updateFwdw(int fwdw,int customerId){
		String sql="UPDATE customer SET fwdw="+fwdw+" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	/**
	 * 生成税号
	 * @return
	 */
	public String generateTaxNO(){
		String prefixTaxNO=ModuleConfig.getConfigValue("customer","prefixTaxNO").toString();
		Double ran = new Double(98 * Math.random() + 1);
		long curTime=DateClass.getTimeInMillis("");
//		taxNO=StringFunction.strReplace(taxNO,"-","");
		String taxNO=prefixTaxNO+curTime+""+ran.intValue();
		return taxNO;
	}
	
	public boolean updateTaxType(int customerId,int taxType){
		String sql="UPDATE customer SET taxType="+taxType+" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	/**
	 * 更新是否已处理报税软件
	 * @param isHandleBSRJ 1未处理，2已处理
	 * @param customerId
	 * @return
	 */
	public boolean updateIsHandleBSRJ(int isHandleBSRJ,int customerId){
		String sql="UPDATE customer SET isHandleBSRJ="+isHandleBSRJ+",handleBSRJDate=CURRENT_DATE WHERE customerId="+customerId;
		return db.execute(sql);
	}

	/**
	 * 更新报税软件是否交申请表
	 * @param isHandleBSRJ 1未处理，2已处理
	 * @param customerId
	 * @return
	 */
//	public boolean updateApplyBSRJ(int isApplyBSRJ,String applyBSRJMsg,int customerId){
//		String sql="UPDATE customer SET isApplyBSRJ="+isApplyBSRJ+",applyBSRJMsg='"+db.filterStr(applyBSRJMsg)+"' WHERE customerId="+customerId;
//		return db.execute(sql);
//	}
	
	//更新电子申报服务状态
	public void updateDasbState(){
//		String sql="SELECT customerId from customer where dzsbState!=4";
		String sql="SELECT customerId from customer where customerAgentId<=0 and dzsbState in (2,3,5,8) order by customerId asc";
//		String sql="SELECT customerId from customer where  dzsbState in (3) order by customerId asc";
		ResultSet rs=null;
		int i=0;
		try {
			rs=db.select(sql);
			while(rs.next()){
				i++;
				if(this.updateDzsb(rs.getInt("customerId"))){
//					System.out.println(i+"="+rs.getInt("customerId"));
				}else{
//					System.out.println(i+"false="+rs.getInt("customerId"));					
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
	}

	/**
	 * 更新企业的电子申报状态
	 * @param customerId
	 * @return
	 */
	public boolean updateDzsb(int customerId){
		Customer customer=this.getCustomerById(customerId);
		return this.updateDzsb(customer);
	}
	

	/**
	 * 更新企业的电子申报状态
	 * @param customerId
	 * @return
	 */
	public boolean updateDzsb(Customer customer){
		int dzsbState=this.generateDzsbState(customer);
		String sql="UPDATE customer SET dzsbState="+dzsbState+" WHERE customerId="+customer.getCustomerId();
		return db.execute(sql);
	}
	
	/**
	 * 获取企业的电子申报服务状态
	 * @param customer
	 * @param orderProductHandle
	 * @return
	 * 1不服务企业
2服务企业
3服务5.02软件企业
4指定服务企业	 * 
	 */
	public int generateDzsbState(Customer customer){
		OrderProductHandle orderProductHandle=new OrderProductHandle();
		return this.generateDzsbState(customer, orderProductHandle);
	}
	
	/**
	 * 判断客户主机的保修期是否在有效期内
	 * @param customerId
	 * @return true 有效期内为true
	 */
	private boolean isPcInWarranty(int customerId){
		ProductHandle productHandle=new ProductHandle();
		String pcIds=productHandle.getProductIds("3");
		return this.isInWarranty(customerId, pcIds);
	}
	
	/**
	 * 判断客户主机的保修期是否在有效期内
	 * @param customerId
	 * @return true 有效期内为true
	 */
	private boolean isInWarranty(int customerId,String productIds){
		OrderProductHandle orderProductHandle=new OrderProductHandle();
		OrderProduct orderProductPC=orderProductHandle.getByMaxWarrantyDate(customerId, productIds);
		boolean isPcInWarranty=false;
		if(orderProductPC!=null){
			if(!StringFunction.isEmpty(orderProductPC.getWarrantyDate())){
				if(DateClass.CompareTwoDate(DateClass.GetSysDate(), orderProductPC.getWarrantyDate(),"")!=1){
					isPcInWarranty=true;
				}
			}
		}
		return isPcInWarranty;
	}	
	
	/**
	 * 判断客户是否购买过e税宝
	 * @param customerId
	 * @return true 购买过为true
	 */
	private boolean isBuyEtax(int customerId){
		OrderProductHandle orderProductHandle=new OrderProductHandle();
		return orderProductHandle.isBuy(cn.qtone.modules.Const.etaxId, customerId, "",0);
	}		
	/**
	 * 获取企业的电子申报服务状态
	 * @param customer
	 * @param orderProductHandle
	 * @return
				dzsbState.put("1","不服务企业");
				dzsbState.put("2","服务企业（提供电话咨询、大厅送修）");
				dzsbState.put("3","服务5.02软件企业");
				dzsbState.put("4","指定服务企业（提供电话咨询、大厅送修）");
				dzsbState.put("5","服务企业（提供电话咨询、大厅送修、远程、上门）");
				dzsbState.put("6","指定服务企业（提供电话咨询、大厅送修、远珵、上门）");
				dzsbState.put("7","指定服务企业（提供电话咨询、大厅送修、远珵）");
				dzsbState.put("8","服务企业（提供电话咨询、大厅送修、远珵）");
 * 
	 */
	private int generateDzsbState(Customer customer,OrderProductHandle orderProductHandle){
		
		CustomerBlacklist blacklist=customer.getBlacklist();
		if(blacklist!=null&&blacklist.getBlackType()==3){//未交160黑名单
			return 1;
		}
		
		//是否购买e税宝
		boolean isBuyEtax=this.isBuyEtax(customer.getCustomerId());
		
		boolean isPcInWarranty=this.isPcInWarranty(customer.getCustomerId());
		if(customer.getRelateCustomerId()>0&&!isPcInWarranty){
			isPcInWarranty=this.isPcInWarranty(customer.getRelateCustomerId());
		}
		
		if(customer.getDzsbState()==4||customer.getDzsbState()==6||customer.getDzsbState()==7){
			if(isPcInWarranty){//电脑主机在保修期内
				return 6;
			}
			if(isBuyEtax){
				return 7;
			}
			return 4;
		}
		
//		OrderProduct orderProductDZSB=orderProductHandle.getByMaxWarrantyDate(customer.getCustomerId(), cn.qtone.modules.Const.dzsbId);
//		if(orderProductDZSB!=null&&!StringFunction.isEmpty(orderProductDZSB.getWarrantyDate())){
//			if(DateClass.CompareTwoDate("2013-10-31", orderProductDZSB.getWarrantyDate(), "")==0){
//				return 3;
//			}
//		}
		
		boolean isDzsbInWarranty=this.isInWarranty(customer.getCustomerId(), cn.qtone.modules.Const.dzsbId);
		
		if(!isDzsbInWarranty){//电子申报过期
			return 1;
		}
		
		if(isPcInWarranty){//电脑主机在保修期内
			return 5;
		}else if(isBuyEtax){//购买了e税宝
			return 8;
		}else{
			return 2;
		}
		

//		
//		SearchOrderProductParam searchOrderProductParam=new SearchOrderProductParam();
//		searchOrderProductParam.setProductIds(cn.qtone.modules.Const.dzsbId);
//		searchOrderProductParam.setIsReturn(1);
//		searchOrderProductParam.setCustomerId(customer.getCustomerId());
//		List orderProductList=orderProductHandle.search(searchOrderProductParam, 0, 1);
//		OrderProduct orderProduct=null;
//		if(orderProductList.size()>=1){
//			orderProduct=(OrderProduct)orderProductList.get(0);
//			Order order=orderProduct.getOrder();
//			//电子申报订单下单日期8月1日后企业
//			if(DateClass.CompareTwoDate(order.getOrderDate(), "2013-8-1", "")!=-1){
//				if(isPcInWarranty){//电脑主机在保修期内
//					return 5;
//				}
//				return 2;
//			}
//			//电子申报订单下单日期6月1日前企业
//			if(DateClass.CompareTwoDate(order.getOrderDate(), "2013-6-1", "")==-1){
//				return 1;
//			}
//			
//			//下单日期6月1日到7月31日
//			if(DateClass.CompareTwoDate(order.getOrderDate(), "2013-6-1", "")!=-1){
//				return 3;
//			}			
//		}
//		
//		return 1;
		
		
	}
	
	/***
	 * 修改确认日期
	 * @param CustomerId
	 * @param confirmDate
	 * @return
	 */
	public boolean updateConfirmDate(int customerId,String confirmDate,String importDate,int source){
		if(StringFunction.isEmpty(confirmDate)||customerId<=0) return false;
		String sql="UPDATE customer  SET confirmDate='"+confirmDate+"',confirmDateSetDate=CURRENT_DATE,source="+source;
		if(!StringFunction.isEmpty(importDate))
		sql+=",importDate='"+importDate+"'";
		sql+=" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	/**
	 * 更新企业的无需跟进状态
	 * @param unNeedFollow 1要跟进 2无需跟进
	 * @param customerId
	 * @return
	 */
	public boolean updateUnNeedFollow(int unNeedFollow,String customerIds){
		String sql="UPDATE customer SET unNeedFollow="+unNeedFollow+" WHERE customerId IN ("+customerIds+")";
		return db.execute(sql);
	}

	/**
	 * 更新企业的无需上门状态
	 * @param unNeedFollow 1要上门 2无需上门
	 * @param customerId
	 * @return
	 */
	public boolean updateUnNeedVisit(int unNeedVisit,String customerIds){
		String sql="UPDATE customer SET unNeedVisit="+unNeedVisit+" WHERE customerId IN ("+customerIds+")";
		return db.execute(sql);
	}
	
	/**
	 * 更新企业的需预约客户上来前台讲解流程状态
	 * @param qtjjlc 1不需要 2需要
	 * @param customerId
	 * @return
	 */
	public boolean updateQtjjlc(int qtjjlc,String customerIds){
		String sql="UPDATE customer SET qtjjlc="+qtjjlc+" WHERE customerId IN ("+customerIds+")";
		return db.execute(sql);
	}
	
	/**
	 * 更新企业的无需回访状态
	 * @param unNeedFollow 1要回访 2无需回访
	 * @param customerId
	 * @return
	 */
	public boolean updateUnNeedReturnvisit(int unNeedReturnvisit,String customerIds){
		String sql="UPDATE customer SET unNeedReturnvisit="+unNeedReturnvisit+" WHERE customerId IN ("+customerIds+")";
		return db.execute(sql);
	}
	/**
	 * 根据企业id删除企业资料
	 * @param customerIds 企业id，多个用逗号隔开
	 * @return
	 */
	public boolean delCustomerByIds(String customerIds){
		if(StringFunction.isEmpty(customerIds)) return false;
		String sql="DELETE FROM customer WHERE customerId in ("+customerIds+")";
		if(db.executeUpdate(sql)>0){
			
			//删除订单
			OrderHandle orderHandle=new OrderHandle();
			orderHandle.delByCustomerIds(customerIds);
			
			//删除账号
			CustomerAccountHandle cusomterAccountHandle=new CustomerAccountHandle();
			cusomterAccountHandle.delAccount(customerIds);
			
			//删除CA申请表记录
			CaCustomerHandle caCustomerHandle=new CaCustomerHandle();
			caCustomerHandle.del(customerIds);
			return true;
		}
		
		return false;
	}

	/**
	 * 根据id获取企业对象
	 * @param customerId 企业id
	 * @return
	 */
	public Customer getCustomerById(int customerId){
		return this.getCustomerById(customerId, true);
	}
	
	/**
	 * 根据id获取企业对象
	 * @param customerId 企业id
	 * @param isReadMainDB 是否指定读主数据库，true是 false否
	 * @return
	 */
	public Customer getCustomerById(int customerId,boolean isReadMainDB){
		if(customerId<=0) return null;
		String sql="SELECT c.*,b.* FROM customer AS c LEFT JOIN customer_blacklist AS b ON  c.customerId=b.customerId AND b.flag=2  WHERE  c.customerId="+customerId;
		ResultSet rs=null;
		Customer customer=null;
		try {
			CustomerTelHandle telHandle=new CustomerTelHandle();
			if(isReadMainDB){
				rs=db.selectMain(sql);
			}else{
				rs=db.select(sql);
			}
			if(rs.next()){
				TelBlacklistHandle telBlacklistHandle=new TelBlacklistHandle();
				customer=new Customer();
				customer.setFwdw(rs.getInt("c.fwdw"));
				customer.setUpdateReceiptDate(rs.getString("c.updateReceiptDate"));
				customer.setIsUpgradeCard(rs.getInt("c.isUpgradeCard"));
				customer.setQtjjlc(rs.getInt("c.qtjjlc"));
				customer.setCreateDate(rs.getString("c.createDate"));
				customer.setImportDate(rs.getString("c.importDate"));
				customer.setUnNeedVisit(rs.getInt("c.unNeedVisit"));
				customer.setUnNeedFollow(rs.getInt("c.unNeedFollow"));
				customer.setUnNeedReturnvisit(rs.getInt("c.unNeedReturnvisit"));
				customer.setIsNew(this.isNew(rs.getString("c.confirmDate"),rs.getInt("c.taxType")));
				customer.setDzsbState(rs.getInt("c.dzsbState"));
				customer.setBankAccount(rs.getString("c.bankAccount"));
				customer.setBank(rs.getString("c.bank"));
				customer.setBossName(rs.getString("c.bossName"));
				customer.setBossTel(rs.getString("c.bossTel"));
				customer.setBossTelIsblacklist(telBlacklistHandle.isBlacklistTel(rs.getString("c.bossTel"),rs.getInt("c.customerId"))?1:0);//1表示为黑名称
				customer.setBusinessAddress(rs.getString("c.businessAddress"));
				customer.setCityId(rs.getInt("c.cityId"));
				customer.setCompanyInfo(rs.getString("c.companyInfo"));
				customer.setCompanyName(rs.getString("c.companyName"));
				customer.setConfirmDate(rs.getString("c.confirmDate"));
				customer.setCustomerId(rs.getInt("c.customerId"));
				customer.setEmail(rs.getString("c.email"));
				customer.setFax(rs.getString("c.fax"));
				customer.setHomepage(rs.getString("c.homepage"));
				customer.setIsCancle(rs.getInt("c.isCancle"));
				customer.setCancleCusDate(rs.getString("c.cancleCusDate"));
//				customer.setIsNew(rs.getInt("c.isNew"));
				customer.setLinkman(rs.getString("c.linkman"));
				customer.setMobile(rs.getString("c.mobile"));
				customer.setMobileIsBlacklist(telBlacklistHandle.isBlacklistTel(rs.getString("c.mobile"),rs.getInt("c.customerId"))?1:0);
				customer.setPostCode(rs.getString("c.postCode"));
				customer.setTaxNO(rs.getString("c.taxNO"));
				customer.setTaxType(rs.getInt("c.taxType"));
				customer.setTel(rs.getString("c.tel"));
				customer.setTelIsBlacklist(telBlacklistHandle.isBlacklistTel(rs.getString("c.tel"),rs.getInt("c.customerId"))?1:0);
				customer.setIsInstallCard(rs.getInt("c.isInstallCard"));
				customer.setInstallCardDate(rs.getString("c.installCardDate"));
				customer.setSysLegalNO(rs.getString("c.sysLegalNO"));
				customer.setCardNO(rs.getString("c.cardNO"));
				customer.setICNO(rs.getString("c.ICNO"));
				customer.setIsRelate(rs.getInt("c.isRelate"));
				customer.setRelateCustomerId(rs.getInt("c.relateCustomerId"));
				customer.setRelateCompanyName(rs.getString("c.relateCompanyName"));
				customer.setHasBSRJ(rs.getInt("c.hasBSRJ"));
				customer.setFretchBSRJDate(rs.getString("c.fretchBSRJDate"));
				customer.setIsInstallCardPre(rs.getInt("c.isInstallCardPre"));
				customer.setCancleCusSetDate(rs.getString("c.cancleCusSetDate"));
				customer.setConfirmBSRJDate(rs.getString("c.confirmBSRJDate"));
				customer.setWorkTimeAMEnd(rs.getString("c.workTimeAMEnd"));
				customer.setWorkTimeAMStart(rs.getString("c.workTimeAMStart"));
				customer.setWorkTimePMEnd(rs.getString("c.workTimePMEnd"));
				customer.setWorkTimePMStart(rs.getString("c.workTimePMStart"));
				customer.setCardType(rs.getInt("c.cardType"));
//				customer.setIsApplyBSRJ(rs.getInt("c.isApplyBSRJ"));
//				customer.setApplyBSRJMsg(rs.getString("c.applyBSRJMsg"));
				List telList=telHandle.search(customer.getCustomerId(), 20);
				if(telList.size()>0){
					for(int j=0;j<telList.size();j++){
						
						//最后联系固话和最后联系手机都有值的时候可以退出循环
						if(!StringFunction.isEmpty(customer.getLastFixTel())&&!StringFunction.isEmpty(customer.getLastMobile())){
							break;
						}
						
						CustomerTel customerTel=(CustomerTel)telList.get(j);
						if(j==0) {//不区分手机和固话的最后联系电话
							customer.setLastLinkman(customerTel.getLinkman());
							customer.setLastTel(customerTel.getTel());
						}
						if(customerTel.getTel().length()<10&&StringFunction.isEmpty(customer.getLastFixTel())) {
							customer.setLastFixTel(customerTel.getTel());//最后联系固话
							continue;
						}
						if(customerTel.getTel().length()>10&&StringFunction.isEmpty(customer.getLastMobile())) {
							customer.setLastMobile(customerTel.getTel());//最后联系手机
						}
					}
				}
				customer.setLastTelIsBlacklist(telBlacklistHandle.isBlacklistTel(customer.getLastTel(),rs.getInt("c.customerId"))?1:0);
				customer.setCancleDate(rs.getString("c.cancleDate"));
				customer.setCancleSetDate(rs.getString("c.cancleSetDate"));
//				customer.setIsStopScanner(rs.getInt("c.isStopScanner"));
				customer.setIsInvalidCI(rs.getInt("c.isInvalidCI"));
				customer.setAddress(rs.getString("c.address"));
				customer.setReceipt_address(rs.getString("receipt_address"));
				customer.setReceipt_tel(rs.getString("receipt_tel"));
				customer.setSource(rs.getInt("c.source"));				
				customer.setCustomerCancleType(rs.getInt("c.customerCancleType"));
				customer.setCardCancleType(rs.getInt("c.cardCancleType"));				
				customer.setName(rs.getString("c.name"));
				customer.setSex(rs.getInt("c.sex"));
				customer.setMobileShop(rs.getString("c.mobileShop"));
//				customer.setIndustry(rs.getInt("c.industry"));   //暂时不用
//				customer.setEducation(rs.getInt("c.education"));
				customer.setBirthdayDay(rs.getInt("c.birthdayDay"));
				customer.setBirthdayMonth(rs.getInt("c.birthdayMonth"));
				customer.setBirthdayYear(rs.getInt("c.birthdayYear"));
				customer.setScannerType(rs.getInt("c.scannerType"));
				customer.setPreScannerType(rs.getInt("c.preScannerType"));
				customer.setConvertScannerTypeDate(rs.getString("c.convertScannerTypeDate"));
				customer.setRemark(rs.getString("c.remark"));
				customer.setHasUseSOZO(rs.getInt("c.hasUseSOZO"));
//				customer.setIsNew(rs.getInt("c.isNew"));
				customer.setFollower(rs.getInt("c.follower"));
				customer.setAssignDate(rs.getString("c.assignDate"));
				customer.setAssigner(rs.getInt("c.assigner"));
				customer.setReceipt_companyName(rs.getString("c.receipt_companyName"));
				customer.setReceipt_taxNO(rs.getString("c.receipt_taxNO"));
				
				if(rs.getInt("b.customerId")>0){//是否黑名单企业
					customer.setIsBlacklist(true);
					CustomerBlacklist customerBlacklist=new CustomerBlacklist();
					customerBlacklist.setComment(rs.getString("b.comment"));
					customerBlacklist.setCustomerBlacklistId(rs.getInt("b.customerBlacklistId"));
					customerBlacklist.setCustomerId(rs.getInt("b.customerId"));
					customerBlacklist.setUserBoId(rs.getInt("b.userBoId"));
					customerBlacklist.setCreateDate(rs.getString("b.createDate"));
					customerBlacklist.setBlackType(rs.getInt("b.blackType"));
					customerBlacklist.setModDate(rs.getString("b.modDate"));	
					customer.setBlacklist(customerBlacklist);					
				}
				//读取代理记账公司员工对象
				customer.setCustomerAgentStaffId(rs.getInt("c.customerAgentStaffId"));
//				if(customer.getCustomerAgentStaffId()>0){
//					CustomerAgentStaffHandle cash=new CustomerAgentStaffHandle();
//					customer.setCustomerAgentStaff(cash.getCustomerAgentStaff(customer.getCustomerAgentStaffId()));
//				}
				customer.setIsAgent(rs.getInt("c.isAgent"));
				customer.setCustomerAgentId(rs.getInt("c.customerAgentId"));
				if(customer.getCustomerAgentId()>0){
//					customer.setCustomerAgent(this.getCustomerById(customer.getCustomerAgentId()));
					Customer customerAgent=new Customer();
					customerAgent.setCustomerId(customer.getCustomerAgentId());
					customerAgent.setCompanyName(this.getCompanyNameByCustomerId(customer.getCustomerAgentId()));
					customer.setCustomerAgent(customerAgent);
				}
				customer.setBusinessArea(rs.getString("c.businessArea"));
				customer.setBusinessScope(rs.getString("c.businessScope"));
				customer.setIsShow(rs.getInt("c.isShow"));
				customer.setIsIngoing(rs.getInt("c.isIngoing"));
				customer.setIngoingDate(rs.getString("c.ingoingDate"));
				customer.setCancleCusSetDate(rs.getString("c.cancleCusSetDate"));
				customer.setIsYingZhuanZeng(rs.getInt("c.isYingZhuanZeng"));
				customer.setIsInstallBSP(rs.getInt("c.isInstallBSP"));
				customer.setIsInstallCardPre(rs.getInt("c.isInstallCardPre"));
				customer.setSoftVersion(rs.getInt("c.softVersion"));
				customer.setYgzDate(rs.getString("c.ygzDate"));
				//委托代扣状态
				CommissionInfoHandle commissionInfoHandle=new CommissionInfoHandle();
				String commissionIsSignStr=commissionInfoHandle.fretchIsSignStr(customer.getCustomerId(), customer.getIsCancle(), customer.getIsInstallCard());
				customer.setCommissionIsSignStr(commissionIsSignStr);
			
				
				return customer;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	/**
	 * 添加客户
	 * @param customer
	 * @return
	 */
	public boolean add(Customer customer){
		if(customer==null) return false;
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("INSERT INTO customer SET companyName='"+db.filterStr(customer.getCompanyName()).trim()+"'");
//		sqlBuf.append(",bankAccount='"+db.filterStr(customer.getBankAccount())+"'");
//		sqlBuf.append(",bank='"+db.filterStr(customer.getBank())+"'");
		sqlBuf.append(",bossName='"+db.filterStr(customer.getBossName())+"'");
		sqlBuf.append(",bossTel='"+db.filterStr(customer.getBossTel())+"'");
		sqlBuf.append(",businessAddress='"+db.filterStr(customer.getBusinessAddress())+"'");
		sqlBuf.append(",companyInfo='"+db.filterStr(customer.getCompanyInfo())+"'");
//		if(!StringFunction.isEmpty(customer.getScannerType())){
			sqlBuf.append(",scannerType=").append(customer.getScannerType());
//		}		
		if(StringFunction.isEmpty(customer.getConfirmDate())){
			sqlBuf.append(",confirmDate=null");
		}else{
			sqlBuf.append(",confirmDate='"+db.filterStr(customer.getConfirmDate())+"'");
		}
		sqlBuf.append(",email='"+db.filterStr(customer.getEmail())+"'");
		sqlBuf.append(",fax='"+db.filterStr(customer.getFax())+"'");
		sqlBuf.append(",homepage='"+db.filterStr(customer.getHomepage())+"'");
		sqlBuf.append(",linkman='"+db.filterStr(customer.getLinkman())+"'");
		sqlBuf.append(",mobile='"+db.filterStr(customer.getMobile())+"'");
		sqlBuf.append(",postCode='"+db.filterStr(customer.getPostCode())+"'");
		sqlBuf.append(",taxNO='"+db.filterStr(customer.getTaxNO()).trim()+"'");
		sqlBuf.append(",tel='"+db.filterStr(customer.getTel())+"'");
		sqlBuf.append(",cityId="+customer.getCityId());
		if(customer.getIsCancle()>0){
			sqlBuf.append(",isCancle="+customer.getIsCancle());
		}
		if(!StringFunction.isEmpty(customer.getCancleCusDate())){
			sqlBuf.append(",cancleCusDate='").append(customer.getCancleCusDate()).append("'");
		}
		if(!StringFunction.isEmpty(customer.getCancleCusSetDate())){
			sqlBuf.append(",cancleCusSetDate='"+db.filterStr(customer.getCancleCusSetDate())+"'");						
		}
		
//		sqlBuf.append(",isNew="+customer.getIsNew());
		sqlBuf.append(",taxType="+customer.getTaxType());
		if(customer.getIsInstallCard()>0){
			sqlBuf.append(",isInstallCard="+customer.getIsInstallCard());
		}
		sqlBuf.append(",cardNO='"+db.filterStr(customer.getCardNO())+"'");
		sqlBuf.append(",ICNO='"+db.filterStr(customer.getICNO())+"'");
		sqlBuf.append(",lastTel='"+db.filterStr(customer.getTel())+"'");
		sqlBuf.append(",lastLinkman='"+db.filterStr(customer.getLinkman())+"'");
		
		if(StringFunction.isEmpty(customer.getInstallCardDate())) 
			sqlBuf.append(",installCardDate=null");
		else
			sqlBuf.append(",installCardDate='"+db.filterStr(customer.getInstallCardDate())+"'");			

		if(StringFunction.isEmpty(customer.getCancleDate())) 
			sqlBuf.append(",cancleDate=null");
		else
			sqlBuf.append(",cancleDate='"+db.filterStr(customer.getCancleDate())+"'");			
		if(!StringFunction.isEmpty(customer.getCancleSetDate())){
			sqlBuf.append(",cancleSetDate='"+db.filterStr(customer.getCancleSetDate())+"'");						
		}
		sqlBuf.append(",sysLegalNO='"+db.filterStr(customer.getSysLegalNO())+"'");
		if(customer.getIsInvalidCI()>0){
			sqlBuf.append(",isInvalidCI="+customer.getIsInvalidCI());
		}
		 
//		if(customer.getIsStopScanner()>0){
//			sqlBuf.append(",isStopScanner="+customer.getIsStopScanner());
//		}
		if(customer.getSource()>0){
			sqlBuf.append(",source="+customer.getSource());
		}
		sqlBuf.append(",customerAgentStaffId=").append(customer.getCustomerAgentStaffId());
		sqlBuf.append(",customerAgentId=").append(customer.getCustomerAgentId());
		sqlBuf.append(",address='").append(db.filterStr(customer.getAddress())).append("'");
		if(!StringFunction.isEmpty(customer.getAddress())){
			sqlBuf.append(",uptAddressDate=CURRENT_DATE");
		}
		if(!StringFunction.isEmpty(customer.getRemark())){
			sqlBuf.append(",remark='").append(db.filterStr(customer.getRemark())).append("'");
		}
		sqlBuf.append(",relateCustomerId=").append(customer.getRelateCustomerId());
		sqlBuf.append(",relateCompanyName='").append(db.filterStr(customer.getRelateCompanyName())).append("'");
		if(customer.getIsRelate()>0) sqlBuf.append(",isRelate=").append(customer.getIsRelate());
		sqlBuf.append(",modifyDate=CURRENT_TIMESTAMP");
		sqlBuf.append(",createDate=CURRENT_TIMESTAMP");
		if(!StringFunction.isEmpty(customer.getImportDate())){
			sqlBuf.append(",importDate='").append(customer.getImportDate()).append("'");
		}
		if(customer.getIsAgent()>0) sqlBuf.append(",isAgent=").append(customer.getIsAgent());
		sqlBuf.append(",businessArea='").append(customer.getBusinessArea()).append("'");
		sqlBuf.append(",businessScope='").append(db.filterStr(customer.getBusinessScope())).append("'");
		sqlBuf.append(",isShow=").append(customer.getIsShow());
		sqlBuf.append(",isIngoing=").append(customer.getIsIngoing());
		if(StringFunction.isEmpty(customer.getIngoingDate())){
			sqlBuf.append(",ingoingDate=NULL");
		}else{
			sqlBuf.append(",ingoingDate='").append(db.filterStr(customer.getIngoingDate())).append("'");
		}
		sqlBuf.append(",softVersion=").append(customer.getSoftVersion());
		if(customer.getIsYingZhuanZeng()==0) customer.setIsYingZhuanZeng(1);
		sqlBuf.append(",isYingZhuanZeng=").append(customer.getIsYingZhuanZeng());
		if(!StringFunction.isEmpty(customer.getYgzDate())){
			sqlBuf.append(",ygzDate='").append(db.filterStr(customer.getYgzDate())).append("'");
		}
		sqlBuf.append(",isInstallBSP=").append(customer.getIsInstallBSP());
		
		return db.executeUpdate(sqlBuf.toString())>0?true:false;
	}
	
	/**
	 * 更新企业信息，不更新装卡信息
	 * @param customer
	 * @return
	 */
	public boolean edit(Customer customer){
		return this.edit(customer, false);
	}
	
	/**
	 * 更新企业信息，装卡信息也会更新
	 * @param customer
	 * @return
	 */
	public boolean editAll(Customer customer){
		return this.edit(customer, true);
	}
	
	
	/**
	 * 修改客户
	 * @param customer
	 * @param boolean isUpdateCard 是否要更新装卡信息 true要
	 * @return
	 */
	private boolean edit(Customer customer,boolean isUpdateCard){
		if(customer==null) return false;
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("UPDATE customer SET companyName='"+db.filterStr(customer.getCompanyName()).trim()+"'");
		if(customer.getFwdw()>0) sqlBuf.append(",fwdw=").append(customer.getFwdw());
//		sqlBuf.append(",bankAccount='"+db.filterStr(customer.getBankAccount())+"'");
//		sqlBuf.append(",bank='"+db.filterStr(customer.getBank())+"'");
		sqlBuf.append(",bossName='"+db.filterStr(customer.getBossName())+"'");
		sqlBuf.append(",bossTel='"+db.filterStr(customer.getBossTel())+"'");
		sqlBuf.append(",businessAddress='"+db.filterStr(customer.getBusinessAddress())+"'");
		sqlBuf.append(",companyInfo='"+db.filterStr(customer.getCompanyInfo())+"'");
		if(StringFunction.isEmpty(customer.getConfirmDate())) {
			sqlBuf.append(",confirmDate=NULL");
		}else{
			sqlBuf.append(",confirmDate='"+db.filterStr(customer.getConfirmDate())+"'");			
		}
		sqlBuf.append(",email='"+db.filterStr(customer.getEmail())+"'");
		sqlBuf.append(",fax='"+db.filterStr(customer.getFax())+"'");
		sqlBuf.append(",homepage='"+db.filterStr(customer.getHomepage())+"'");
		sqlBuf.append(",linkman='"+db.filterStr(customer.getLinkman())+"'");
		sqlBuf.append(",mobile='"+db.filterStr(customer.getMobile())+"'");
		sqlBuf.append(",postCode='"+db.filterStr(customer.getPostCode())+"'");
		sqlBuf.append(",taxNO='"+db.filterStr(customer.getTaxNO()).trim()+"'");
		sqlBuf.append(",tel='"+db.filterStr(customer.getTel())+"'");
		sqlBuf.append(",cityId="+customer.getCityId());
		if(customer.getCityIdPre()>0) sqlBuf.append(",cityIdPre="+customer.getCityIdPre());
		sqlBuf.append(",isCancle="+customer.getIsCancle());
		if(customer.getIsCancle()>0){
			if(customer.getIsCancle()!=1){//注销、待注销企业
				sqlBuf.append(",customerCancleType="+customer.getCustomerCancleType());
			}else{
				sqlBuf.append(",customerCancleType=0");
			}
		}
		if(StringFunction.isEmpty(customer.getCancleCusDate())){
			sqlBuf.append(",cancleCusDate=null");
		}else{
			sqlBuf.append(",cancleCusDate='").append(customer.getCancleCusDate()).append("'");	
		}
		if(!StringFunction.isEmpty(customer.getCancleCusSetDate())){
			sqlBuf.append(",cancleCusSetDate='"+db.filterStr(customer.getCancleCusSetDate())+"'");						
		}
		
//		sqlBuf.append(",isNew="+customer.getIsNew());
		sqlBuf.append(",taxType="+customer.getTaxType());
		if(customer.getIsUpgradeCard()>0) sqlBuf.append(",isUpgradeCard="+customer.getIsUpgradeCard());
		if(customer.getSource()>0) sqlBuf.append(",source="+customer.getSource());
		
		
		if(isUpdateCard&&customer.getIsInstallCard()>1){//要更新装卡信息
			
			if(customer.getIsInstallCard()>0){
				sqlBuf.append(",isInstallCard="+customer.getIsInstallCard());
				if(customer.getIsInstallCard()==3||customer.getIsInstallCard()==4){//取消卡
					sqlBuf.append(",cardCancleType="+customer.getCardCancleType());
				}else{
					sqlBuf.append(",cardCancleType=0");
				}
			}
			if(StringFunction.isEmpty(customer.getInstallCardDate())) {
				sqlBuf.append(",installCardDate=null");			
			}else{
				sqlBuf.append(",installCardDate='"+db.filterStr(customer.getInstallCardDate())+"'");
			}
			
			sqlBuf.append(",softVersion=").append(customer.getSoftVersion());
			sqlBuf.append(",isInstallBSP=").append(customer.getIsInstallBSP());
			sqlBuf.append(",cardType="+customer.getCardType());
			sqlBuf.append(",sysLegalNO='"+db.filterStr(customer.getSysLegalNO())+"'");
			sqlBuf.append(",cardNO='"+db.filterStr(customer.getCardNO())+"'");
			sqlBuf.append(",ICNO='"+db.filterStr(customer.getICNO())+"'");
			if(customer.getIsInstallCardPre()>0) sqlBuf.append(",isInstallCardPre=").append(customer.getIsInstallCardPre());
			sqlBuf.append(",isYingZhuanZeng=").append(customer.getIsYingZhuanZeng());
			if(StringFunction.isEmpty(customer.getCancleDate())) 
				sqlBuf.append(",cancleDate=null");
			else
				sqlBuf.append(",cancleDate='"+db.filterStr(customer.getCancleDate())+"'");
			if(!StringFunction.isEmpty(customer.getCancleSetDate())){
				sqlBuf.append(",cancleSetDate='"+db.filterStr(customer.getCancleSetDate())+"'");						
			}
			if(!StringFunction.isEmpty(customer.getCancleSetDate())){
				sqlBuf.append(",cancleSetDate='"+db.filterStr(customer.getCancleSetDate())+"'");
			}
			if(StringFunction.isEmpty(customer.getYgzDate())){
				sqlBuf.append(",ygzDate=null");
			}else{
				sqlBuf.append(",ygzDate='").append(customer.getYgzDate()).append("'");
			}
		}
		
		
		if(!StringFunction.isEmpty(customer.getImportDate())) sqlBuf.append(",importDate='"+db.filterStr(customer.getImportDate())+"'");

//		if(!StringFunction.isEmpty(customer.getLastLinkman())){
//			sqlBuf.append(",lastLinkman='"+db.filterStr(customer.getLastLinkman())+"'");
//		}
//		if(!StringFunction.isEmpty(customer.getLastTel())){
//			sqlBuf.append(",lastTel='"+db.filterStr(customer.getLastTel())+"'");
//		}
		if(customer.getIsInvalidCI()>0){
			sqlBuf.append(",isInvalidCI="+customer.getIsInvalidCI());
		}
		if(customer.getTelBusyTimes()==2){
			sqlBuf.append(",telBusyTimes=telBusyTimes+1");
		}else if(customer.getTelBusyTimes()==1){
			sqlBuf.append(",telBusyTimes=0");
		}
//		if(customer.getHasUseSOZO()>0){
//			sqlBuf.append(",hasUseSOZO=").append(customer.getHasUseSOZO());
//		}
//		if(customer.getIsStopScanner()>0){
//			sqlBuf.append(",isStopScanner="+customer.getIsStopScanner());
//		}
		sqlBuf.append(",customerAgentStaffId=").append(customer.getCustomerAgentStaffId());
		sqlBuf.append(",customerAgentId=").append(customer.getCustomerAgentId());
		sqlBuf.append(",modifyDate=CURRENT_TIMESTAMP");
		sqlBuf.append(",address='").append(db.filterStr(customer.getAddress())).append("'");
		if(!StringFunction.isEmpty(customer.getUptAddressDate())){
			sqlBuf.append(",uptAddressDate='"+customer.getUptAddressDate()+"'");
		}		
		if(StringFunction.isEmpty(customer.getAddress())){
			sqlBuf.append(",uptAddressDate=null");
		}
//		if(customer.getScannerType()!=0){
//			sqlBuf.append(" ,preScannerType=IF(scannerType<>"+customer.getScannerType()+",scannerType,preScannerType)");
//			sqlBuf.append(",convertScannerTypeDate=IF(scannerType<>"+customer.getScannerType()+",CURRENT_DATE,convertScannerTypeDate)");
//			sqlBuf.append(",scannerType=").append(customer.getScannerType());
//		}
		sqlBuf.append(",scannerType=").append(customer.getScannerType());
		sqlBuf.append(",preScannerType=").append(customer.getPreScannerType());
		if(!StringFunction.isEmpty(customer.getConvertScannerTypeDate())){
			sqlBuf.append(",convertScannerTypeDate='").append(customer.getConvertScannerTypeDate()).append("'");
		}else{
			sqlBuf.append(",convertScannerTypeDate=NULL");
		}
		
		sqlBuf.append(",remark='").append(db.filterStr(customer.getRemark())).append("'");


//		if(!StringFunction.isEmpty(customer.getReceipt_tel())){
//			sqlBuf.append(",receipt_tel='").append(db.filterStr(customer.getReceipt_tel())).append("'");			
//		}
//		
//		if(!StringFunction.isEmpty(customer.getReceipt_address())){
//			sqlBuf.append(",receipt_address='").append(db.filterStr(customer.getReceipt_address())).append("'");						
//		}
		sqlBuf.append(",relateCustomerId=").append(customer.getRelateCustomerId());
		sqlBuf.append(",relateCompanyName='").append(db.filterStr(customer.getRelateCompanyName())).append("'");
		sqlBuf.append(",isRelate=").append(customer.getIsRelate());
		sqlBuf.append(",isAgent=").append(customer.getIsAgent());
		sqlBuf.append(",businessArea='").append(customer.getBusinessArea()).append("'");
		sqlBuf.append(",businessScope='").append(db.filterStr(customer.getBusinessScope())).append("'");
		sqlBuf.append(",isShow=").append(customer.getIsShow());
		sqlBuf.append(",isIngoing=").append(customer.getIsIngoing());
		if(StringFunction.isEmpty(customer.getIngoingDate())){
			sqlBuf.append(",ingoingDate=NULL");
		}else{
			sqlBuf.append(",ingoingDate='").append(db.filterStr(customer.getIngoingDate())).append("'");
		}
		if(customer.getIsChangeToUnTax()==2){
			sqlBuf.append(",isChangeToUnTax=").append(customer.getIsChangeToUnTax());
			sqlBuf.append(",changeToUnTaxDate=CURRENT_DATE");
		}
		

		sqlBuf.append(" WHERE customerId="+customer.getCustomerId());
		if(db.execute(sqlBuf.toString())){
			cn.qtone.modules.order.obj.OrderProductRebuyHandle rebuyHandle=new cn.qtone.modules.order.obj.OrderProductRebuyHandle();
			if(customer.getIsInstallCard()==3){//停用金税卡
				rebuyHandle.stop(customer.getCustomerId(), "33");
			}else{
				rebuyHandle.unStop(customer.getCustomerId(), "33");				
			}
//			if(customer.getIsStopScanner()==2){//停用扫描仪
//				rebuyHandle.stop(customer.getCustomerId(), "32,84,85");				
//			}else{
//				rebuyHandle.unStop(customer.getCustomerId(), "32,84,85");					
//			}
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 客户修改企业资料
	 * @param customer
	 * @return
	 */
	public boolean editBySelf(Customer customer){
		if(customer==null) return false;
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("UPDATE customer SET bossName='"+db.filterStr(customer.getBossName())+"'");
//		sqlBuf.append(",bankAccount='"+db.filterStr(customer.getBankAccount())+"'");
//		sqlBuf.append(",bank='"+db.filterStr(customer.getBank())+"'");
		sqlBuf.append(",bossTel='"+db.filterStr(customer.getBossTel())+"'");
		sqlBuf.append(",businessAddress='"+db.filterStr(customer.getBusinessAddress())+"'");
		sqlBuf.append(",companyInfo='"+db.filterStr(customer.getCompanyInfo())+"'");
//		sqlBuf.append(",confirmDate='"+db.filterStr(customer.getConfirmDate())+"'");
		sqlBuf.append(",email='"+db.filterStr(customer.getEmail())+"'");
		sqlBuf.append(",fax='"+db.filterStr(customer.getFax())+"'");
		sqlBuf.append(",homepage='"+db.filterStr(customer.getHomepage())+"'");
		sqlBuf.append(",linkman='"+db.filterStr(customer.getLinkman())+"'");
		sqlBuf.append(",mobile='"+db.filterStr(customer.getMobile())+"'");
		sqlBuf.append(",postCode='"+db.filterStr(customer.getPostCode())+"'");
//		sqlBuf.append(",taxNO='"+db.filterStr(customer.getTaxNO())+"'");
		sqlBuf.append(",tel='"+db.filterStr(customer.getTel())+"'");
		sqlBuf.append(",cityId="+customer.getCityId());
//		sqlBuf.append(",customerBaseId="+customer.getCustomerBaseId());
//		sqlBuf.append(",isCancle="+customer.getIsCancle());
//		sqlBuf.append(",isNew="+customer.getIsNew());
//		sqlBuf.append(",taxType="+customer.getTaxType());
		sqlBuf.append(",isConfirm=2");
		sqlBuf.append(",modifyDate=CURRENT_TIMESTAMP");		
		sqlBuf.append(" WHERE customerId="+customer.getCustomerId());
		return db.execute(sqlBuf.toString());
	}
	
	
	
	/**
	 * 商城客户修改资料
	 * @param customer
	 * @return
	 */
	public boolean shopEditBySelf(Customer customer){
		if(customer==null) return false;
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("UPDATE customer SET name='"+db.filterStr(customer.getName())+"'");
		sqlBuf.append(",sex="+customer.getSex());
		sqlBuf.append(" , mobileShop='"+db.filterStr(customer.getMobileShop())+"'");
		sqlBuf.append(" , address='"+db.filterStr(customer.getAddress())+"'");
//		sqlBuf.append(",industry="+customer.getIndustry());
//		sqlBuf.append(",education="+customer.getEducation()); //暂时不用
		sqlBuf.append(",birthdayDay="+customer.getBirthdayDay());
		sqlBuf.append(",birthdayMonth="+customer.getBirthdayMonth());
		sqlBuf.append(",birthdayYear="+customer.getBirthdayYear());
		sqlBuf.append(" WHERE customerId="+customer.getCustomerId());
		return db.execute(sqlBuf.toString());
	}
		
	/**
	 * 更新企业的金税卡信息
	 * @param customerId
	 * @param installCardDate 金税卡安装日期
	 * @param sysLegalNO 正版标签编号
	 * @return
	 */
	public boolean installCard(Customer customer){
//	public boolean installCard1(int customerId,String installCardDate,String sysLegalNO,String cardNO,String ICNO,String cancleDate,int isInstallCard){
		String sql="UPDATE customer SET isInstallCard="+customer.getIsInstallCard();
		if(StringFunction.isEmpty(customer.getInstallCardDate())){
			sql+=",installCardDate=null";
		}else{
			sql+=",installCardDate='"+db.filterStr(customer.getInstallCardDate())+"'";
		}
		
		if(!StringFunction.isEmpty(customer.getCancleSetDate())){
			sql+=",cancleSetDate='"+db.filterStr(customer.getCancleSetDate())+"'";
		}
		
		
		if(StringFunction.isEmpty(customer.getCancleDate())){
			sql+=",cancleDate=null";
		}else{
			sql+=",cancleDate='"+db.filterStr(customer.getCancleDate())+"'";
		}
		if(!StringFunction.isEmpty(customer.getSysLegalNO())){
			sql+=",sysLegalNO='"+db.filterStr(customer.getSysLegalNO())+"'";			
		}
		if(StringFunction.isEmpty(customer.getCardNO())){
			sql+=",cardNO=''";
		}else{
			sql+=",cardNO='"+db.filterStr(customer.getCardNO())+"'";			
		}
		if(StringFunction.isEmpty(customer.getICNO())){
			sql+=",ICNO=''";
		}else{
			sql+=",ICNO='"+db.filterStr(customer.getICNO())+"'";			
		}
		sql+=",isYingZhuanZeng="+customer.getIsYingZhuanZeng();
		sql+=",isInstallBSP="+customer.getIsInstallBSP();
		if(!StringFunction.isEmpty(customer.getYgzDate())){
			sql+=",ygzDate='"+customer.getYgzDate()+"'";
		}else{
			sql+=",ygzDate=null";			
		}
		sql+=",softVersion="+customer.getSoftVersion();
		sql+=",isInstallCardPre="+customer.getIsInstallCardPre();
		sql+=",cardType="+customer.getCardType();
		if(customer.getIsUpgradeCard()>1){
			sql+=",isUpgradeCard="+customer.getIsUpgradeCard();
		}
		
		sql+=" WHERE customerId = "+customer.getCustomerId();
		if(db.execute(sql)){
			cn.qtone.modules.order.obj.OrderProductRebuyHandle rebuyHandle=new cn.qtone.modules.order.obj.OrderProductRebuyHandle();
			if(customer.getIsInstallCard()==3){//停用金税卡
				rebuyHandle.stop(customer.getCustomerId(), "33");
			}else{
				rebuyHandle.unStop(customer.getCustomerId(), "33");				
			}
//			if(isInstallCard==2){//安装金税卡
//				sql=" UPDATE  customer SET softVersion=IF(softVersion='' OR softVersion IS NULL ,6.15,softVersion ) WHERE customerId = "+customerId;
//			}
			
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 得到某一公司名称的企业数
	 * @param companyName
	 * @return 
	 */
	public int countCompanyName(String companyName){
		String sql="SELECT COUNT(*) FROM customer WHERE companyName='"+db.filterStr(companyName)+"'";
		return db.countRow(sql);
	}

	/**
	 * 得到某一税号的企业数
	 * @param companyName
	 * @return 
	 */
	public int countTaxNO(String taxNO){
		String sql="SELECT COUNT(*) FROM customer WHERE taxNO='"+db.filterStr(taxNO)+"'";
		return db.countRow(sql);
	}
	
	
	/**
	 * 搜索企业
	 * @param param 搜索参数
	 * @param startPos 返回的开始位置
	 * @param num 返回的条数 -1为不限
	 * @return
	 */
	public List search(SearchCustomerParam param,int startPos,int num,boolean isMainDB){
		return this.search(param, startPos, num, 1,isMainDB,false);
	}

	/**
	 * 搜索企业
	 * @param param 搜索参数
	 * @param startPos 返回的开始位置
	 * @param num 返回的条数 -1为不限
	 * @return
	 */
	public List search(SearchCustomerParam param,int startPos,int num){
		return this.search(param, startPos, num, 1,false,false);
	}

	/**
	 * 搜索企业(只读取基本信息)
	 * @param param 搜索参数
	 * @param startPos 返回的开始位置
	 * @param num 返回的条数 -1为不限
	 * @return
	 */
	public List searchReadBaseInfo(SearchCustomerParam param,int startPos,int num){
		return this.search(param, startPos, num, 1,false,true);
	}
	
	/**
	 * 是否新企业，认定日期大于等于1个月前1号的为新企业
	 * @param confirmDate
	 * @return
	 */
	private boolean isNew(String confirmDate,int taxType){
		if(StringFunction.isEmpty(confirmDate)){
			return false;
		}
		if(taxType!=1) return false;
		String endDate=DateClass.getMonthBeginDate("");
		String startDate=DateClass.AddDateMonth(endDate, -1);
		if(DateClass.CompareTwoDate(confirmDate, startDate, "")!=-1){
			return true;
		}
		return false;
	}
	
	/**
	 * 搜索企业
	 * @param param 搜索参数
	 * @param startPos 返回的开始位置
	 * @param num 返回的条数 -1为不限
	 * @param isSuggest 是否下拉框选择 2是
	 * @return
	 */
	public List search(SearchCustomerParam param,int startPos,int num,int isSuggest,boolean isMainDB,boolean onlyBaseInfo){
		StringBuffer sqlFrom=new StringBuffer();
		if(param.getIsModZBKP()==2||!StringFunction.isEmpty(param.getBuy1416DateStart())||!StringFunction.isEmpty(param.getBuy1416DateEnd())){
			sqlFrom.append("SELECT DISTINCT c.* FROM customer AS c");
		}else{
			sqlFrom.append("SELECT c.* FROM customer AS c");
		}
		sqlFrom.append(this.searchSQLFrom(param));
		StringBuffer sqlWhere=new StringBuffer();
		sqlWhere.append("  WHERE 1=1");
		sqlWhere.append(this.searchSQL(param));
//		String sqlOrder=" ORDER BY c.customerId DESC";
		String sqlOrder="";
//		if(param.getIsUseFullText()){//下拉选择时没有必要排序
//			sqlOrder=" ORDER BY c.taxNO DESC";
//		}
		if(!StringFunction.isEmpty(param.getOrderField())){
			sqlOrder=" ORDER BY "+param.getOrderField()+" DESC";
		}
		if(param.getIsPay336()>0){//当要搜索今年是否交336时，加了排序会很慢
			sqlOrder=" ";
		}
		String sqlLimit="";
		if(num!=-1) sqlLimit=" LIMIT "+startPos+","+num;
		
		List list=new ArrayList();
		
		CustomerAgentStaffHandle customerAgentStaffHandle=new CustomerAgentStaffHandle();
		CommissionInfoHandle commissionInfoHandle=new CommissionInfoHandle();
		String sql=sqlFrom.append(sqlWhere).append(sqlOrder).append(sqlLimit).toString();
		ResultSet rs=null;
		try {
			CustomerTelHandle telHandle=new CustomerTelHandle();
			CustomerBlacklistHandle customerBlacklistHandle=new CustomerBlacklistHandle();
			if(isMainDB){
				rs=db.selectMain(sql);				
			}else{
				rs=db.select(sql);
			}
			while (rs.next()){
				Customer customer=new Customer();
				customer.setFwdw(rs.getInt("c.fwdw"));
				customer.setCustomerId(rs.getInt("c.customerId"));
				
				CustomerWxHandle customerWxHandle = new CustomerWxHandle();
				customer.setCompanyName(rs.getString("c.companyName"));
				customer.setTaxType(rs.getInt("c.taxType"));
				customer.setTaxNO(rs.getString("c.taxNO"));
				
				if(onlyBaseInfo){//只读取基本信息
					list.add(customer);
					continue;
				}

				customer.setFretchBSRJDate(rs.getString("c.fretchBSRJDate"));
				customer.setIsInstallCardPre(rs.getInt("c.isInstallCardPre"));
				customer.setCancleCusSetDate(rs.getString("c.cancleCusSetDate"));
				customer.setConfirmBSRJDate(rs.getString("c.confirmBSRJDate"));
				customer.setWorkTimeAMEnd(rs.getString("c.workTimeAMEnd"));
				customer.setWorkTimeAMStart(rs.getString("c.workTimeAMStart"));
				customer.setWorkTimePMEnd(rs.getString("c.workTimePMEnd"));
				customer.setWorkTimePMStart(rs.getString("c.workTimePMStart"));
				customer.setQtjjlc(rs.getInt("c.qtjjlc"));
				customer.setIsAgent(rs.getInt("c.isAgent"));
				customer.setHasBSRJ(rs.getInt("c.hasBSRJ"));
				customer.setFretchBSRJDate(rs.getString("c.fretchBSRJDate"));
				customer.setUnNeedVisit(rs.getInt("c.unNeedVisit")); 
				customer.setUnNeedFollow(rs.getInt("c.unNeedFollow")); 
				customer.setUnNeedReturnvisit(rs.getInt("c.unNeedReturnvisit"));
				customer.setFollower(rs.getInt("c.follower"));
				customer.setDzsbState(rs.getInt("c.dzsbState"));
				customer.setLinkman(rs.getString("c.linkman"));
				customer.setTel(rs.getString("c.tel"));
				customer.setMobile(rs.getString("c.mobile"));
				List telList=telHandle.search(customer.getCustomerId(), 20);
				if(telList.size()>0){
					for(int j=0;j<telList.size();j++){
						
						//最后联系固话和最后联系手机都有值的时候可以退出循环
						if(!StringFunction.isEmpty(customer.getLastFixTel())&&!StringFunction.isEmpty(customer.getLastMobile())){
							break;
						}
						
						CustomerTel customerTel=(CustomerTel)telList.get(j);
						if(param.getIsLastThreeMonth()==2)//限定三个月
						{
							int isLastThreeMonth;
							if(!StringFunction.isEmpty(customerTel.getUpdateTime()))
							{
								isLastThreeMonth=DateClass.CompareTwoDate(DateClass.AddDateMonth(customerTel.getUpdateTime(),3), DateClass.GetSysDate(), "");
							}
							else
								isLastThreeMonth=-1;
							if(isLastThreeMonth>=0&&StringFunction.isEmpty(customer.getLastTel())) {//不区分手机和固话的最后联系电话
								customer.setLastLinkman(customerTel.getLinkman());
								customer.setLastTel(customerTel.getTel());
							}
							if(customerTel.getTel().length()<10&&StringFunction.isEmpty(customer.getLastFixTel())&&isLastThreeMonth>=0) {
								customer.setLastFixTel(customerTel.getTel());//最后联系固话
								continue;
							}
							if(customerTel.getTel().length()>10&&(customerTel.getTel().startsWith("1")||customerTel.getTel().startsWith("01"))&&StringFunction.isEmpty(customer.getLastMobile())&&isLastThreeMonth>=0) {
								customer.setLastMobile(customerTel.getTel());//最后联系手机
							}
						}
						else//不限定三个月
						{
						if(j==0) {//不区分手机和固话的最后联系电话
							customer.setLastLinkman(customerTel.getLinkman());
							customer.setLastTel(customerTel.getTel());
						}
						if(customerTel.getTel().length()<10&&StringFunction.isEmpty(customer.getLastFixTel())) {
							customer.setLastFixTel(customerTel.getTel());//最后联系固话
							continue;
						}
						if(customerTel.getTel().length()>10&&(customerTel.getTel().startsWith("1")||customerTel.getTel().startsWith("01"))&&StringFunction.isEmpty(customer.getLastMobile())) {
							customer.setLastMobile(customerTel.getTel());//最后联系手机
						}
						}
					}
				}
//				customer.setLastLinkman(rs.getString("c.lastLinkman"));
//				customer.setLastTel(rs.getString("c.lastTel"));	
				customer.setFax(rs.getString("c.fax"));	
				customer.setCityId(rs.getInt("c.cityId"));
				customer.setBusinessAddress(rs.getString("c.businessAddress"));
				customer.setAddress(rs.getString("c.address"));
				customer.setIsInstallCard(rs.getInt("c.isInstallCard"));
//				customer.setIsStopScanner(rs.getInt("c.isStopScanner"));
				customer.setIsInvalidCI(rs.getInt("c.isInvalidCI"));
				customer.setCancleDate(rs.getString("c.cancleDate"));
				customer.setInstallCardDate(rs.getString("c.installCardDate"));
				customer.setIsImportance(rs.getInt("c.isImportance"));
				customer.setConfirmDate(rs.getString("c.confirmDate"));
				customer.setScannerType(rs.getInt("c.scannerType"));
				customer.setIsCancle(rs.getInt("c.isCancle"));
				customer.setCancleCusDate(rs.getString("c.cancleCusDate"));
				customer.setCustomerCancleType(rs.getInt("c.customerCancleType"));
				customer.setCardCancleType(rs.getInt("c.cardCancleType"));	
				customer.setBankAccount(rs.getString("c.bankAccount"));
				customer.setBank(rs.getString("c.bank"));
				customer.setFollower(rs.getInt("c.follower"));
				customer.setAssignDate(rs.getString("c.assignDate"));
				customer.setImportDate(rs.getString("c.importDate"));
				customer.setCompanyInfo(rs.getString("c.companyInfo"));
				customer.setBusinessScope(rs.getString("c.businessScope"));
				customer.setBossName(rs.getString("c.bossName"));
				customer.setCreateDate(rs.getString("c.createDate"));
				customer.setSoftVersion(rs.getInt("c.softVersion"));
				
				CustomerBlacklist customerBlacklist=customerBlacklistHandle.getCustomerBlacklistByCustomerId(customer.getCustomerId());
				if(customerBlacklist!=null){//是否黑名单企业
					customer.setIsBlacklist(true);
					customer.setBlacklist(customerBlacklist);
				}
				
				customer.setCustomerAgentId(rs.getInt("c.customerAgentId"));
				customer.setCustomerAgentStaffId(rs.getInt("c.customerAgentStaffId"));
				if(isSuggest==2){//下拉框须提取的内容
					//委托代扣状态
					String commissionIsSignStr=commissionInfoHandle.fretchIsSignStr(customer.getCustomerId(), customer.getIsCancle(), customer.getIsInstallCard());
					customer.setCommissionIsSignStr(commissionIsSignStr);
				}else{//下拉框不须提前该内容
					if(customer.getCustomerAgentStaffId()>0){//读取代理该公司的代理记账人员对象
						CustomerAgentStaff customerAgentStaff=customerAgentStaffHandle.getCustomerAgentStaff(customer.getCustomerAgentStaffId());
						customer.setCustomerAgentStaff(customerAgentStaff);
					}
					else if( customer.getCustomerAgentId()==0){//读取代理该公司的代理记账人员对象
						CustomerAgentStaff customerAgentStaff=new CustomerAgentStaff();
						customerAgentStaff.setStaffName("无");
						customer.setCustomerAgentStaff(customerAgentStaff);
					}
					else if( customer.getCustomerAgentId()==-1){//读取代理该公司的代理记账人员对象
						CustomerAgentStaff customerAgentStaff=new CustomerAgentStaff();
						customerAgentStaff.setStaffName("无(有联系)");
						customer.setCustomerAgentStaff(customerAgentStaff);
					}	
				}
				customer.setIsRelate(rs.getInt("c.isRelate"));
				customer.setRelateCustomerId(rs.getInt("c.relateCustomerId"));
				customer.setIsYingZhuanZeng(rs.getInt("c.isYingZhuanZeng"));
				customer.setIsInstallBSP(rs.getInt("c.isInstallBSP"));
				customer.setIsInstallCardPre(rs.getInt("c.isInstallCardPre"));
				
				
                customer.setReceipt_address(rs.getString("c.receipt_address"));				
				customer.setReceipt_tel(rs.getString("c.receipt_tel"));
				customer.setReceipt_companyName(rs.getString("c.receipt_companyName"));
				customer.setReceipt_taxNO(rs.getString("c.receipt_taxNO")); 
				customer.setRemark(rs.getString("c.remark"));
				customer.setRelateCompanyName(rs.getString("c.relateCompanyName"));
				customer.setIsNew(this.isNew(rs.getString("c.confirmDate"),rs.getInt("c.taxType")));
				

				list.add(customer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return list;
	}
	
	/**
	 * 得到搜索企业的总记录数
	 * @param param
	 * @return
	 */
	public int totalRow(SearchCustomerParam param){
		String sql="SELECT";
		if(param.getIsModZBKP()==2||!StringFunction.isEmpty(param.getBuy1416DateStart())||!StringFunction.isEmpty(param.getBuy1416DateEnd())){
			sql+=" count(DISTINCT c.customerId) FROM customer AS c";
		}else{
			sql+=" count(*) FROM customer AS c";
		}
		sql+=this.searchSQLFrom(param);
		sql+="  WHERE 1=1";
		sql+=this.searchSQL(param);
		return db.countRow(sql);
	}
	
	/**
	 * 根据参数生成搜索sql条件
	 * @param param 参数对象
	 * @return
	 */
	private String searchSQL(SearchCustomerParam param){
		if(param==null) return "";
		StringBuffer sqlWhere=new StringBuffer();

		if(param.getFwdw()>0){
			sqlWhere.append(" AND c.fwdw=").append(param.getFwdw());
		}
		if(param.getIsUpgradeCard()>0){
			sqlWhere.append(" AND c.isUpgradeCard="+param.getIsUpgradeCard());
		}
		if(!StringFunction.isEmpty(param.getPublishDateEnd())){
			sqlWhere.append(" AND c.publishDate< DATE_ADD('"+param.getPublishDateEnd()+"',INTERVAL 1 day)");
		}	
		if(!StringFunction.isEmpty(param.getPublishDateStart())){
			sqlWhere.append(" AND c.publishDate>='"+param.getPublishDateStart()+"'");
		}
		if(!StringFunction.isEmpty(param.getCancleCusDateEnd())){
			sqlWhere.append(" AND c.cancleCusDate< DATE_ADD('"+param.getCancleCusDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getCancleCusDateStart())){
			sqlWhere.append(" AND c.cancleCusDate>='"+param.getCancleCusDateStart()+"'");
		}
		
		if(param.getQtjjlc()>0){
			sqlWhere.append(" AND c.qtjjlc=").append(param.getQtjjlc());
		}
		if(param.getIsModZBKP()==2){//搜索暂不开票修改审核情况
			if(param.getSearchCustomeIsInstallCardChangeParam().getIsCheck()>0){
				sqlWhere.append(" AND cic.isCheck=").append(param.getSearchCustomeIsInstallCardChangeParam().getIsCheck());
			}else{
				sqlWhere.append(" AND cic.customerId IS NOT NULL");
			}
		}else if(param.getIsModZBKP()==1){
			sqlWhere.append(" AND cic.customerId IS NULL");			
		}
		
		if(param.getSearchAssignChangeParam()!=null){
			if(param.getSearchAssignChangeParam().getCheckState()>0){
				sqlWhere.append(" AND cac.checkState=").append(param.getSearchAssignChangeParam().getCheckState());
			}
		}
		
		if(!StringFunction.isEmpty(param.getBuy1416DateEnd())){
			sqlWhere.append(" AND o.orderDate< DATE_ADD('"+param.getBuy1416DateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getBuy1416DateStart())){
			sqlWhere.append(" AND o.orderDate>='"+param.getBuy1416DateStart()+"'");
		}
		if(!StringFunction.isEmpty(param.getBuy1416DateEnd())||!StringFunction.isEmpty(param.getBuy1416DateStart())){
			sqlWhere.append(" AND o.cost=1416 and c.isInstallCard!=2");
		}
		
		if(!StringFunction.isEmpty(param.getSource())){
			sqlWhere.append(" AND c.source IN (").append(param.getSource()).append(")");
		}
		
		if(!StringFunction.isEmpty(param.getConfirmDateSetDateEnd())){
			sqlWhere.append(" AND c.confirmDateSetDate< DATE_ADD('"+param.getConfirmDateSetDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getConfirmDateSetDateStart())){
			sqlWhere.append(" AND c.confirmDateSetDate>='"+param.getConfirmDateSetDateStart()+"'");
		}
		
		if(param.getIsServerDzsb()==1){
			sqlWhere.append(" AND c.dzsbState=1");
		}else if(param.getIsServerDzsb()==2){
			sqlWhere.append(" AND c.dzsbState!=1");			
		}
		if(param.getHasBSRJ()>0){
			sqlWhere.append(" AND c.hasBSRJ=").append(param.getHasBSRJ());			
		}
		if(param.getUnNeedFollow()>0){
			sqlWhere.append(" AND c.unNeedFollow=").append(param.getUnNeedFollow());
		}
		if(param.getUnNeedVisit()>0){
			sqlWhere.append(" AND c.unNeedVisit=").append(param.getUnNeedVisit());
		}		
		if(param.getUnNeedReturnvisit()>0){
			sqlWhere.append(" AND c.unNeedReturnvisit=").append(param.getUnNeedReturnvisit());
		}		
		if(param.getIsAssignDzsb()==2){
			sqlWhere.append(" AND c.dzsbState in (4,6)");
		}else if(param.getIsAssignDzsb()==1){
			sqlWhere.append(" AND c.dzsbState in (2,3,5)");			
		}

//		if(param.getIsNew()>0){
//			sqlWhere.append(" AND c.isNew=").append(param.getIsNew());
//		}
		if(param.getCommissionIsSign()>0){
			if(param.getCommissionIsSign()==4){
				sqlWhere.append(" AND ci.commissionInfoId IS NULL");
			}else{
				sqlWhere.append(" AND ci.isSign=").append(param.getCommissionIsSign());				
			}
		}
		if(param.getCustomerAgentId()!=0)
			sqlWhere.append("  AND c.customerAgentId="+param.getCustomerAgentId());	
		//是否使用代理公司记账
		if(param.getIsByAgent()==1){//否
			sqlWhere.append("  AND ( c.customerAgentId<=0 OR  c.customerAgentId IS NULL ) ");
		}else if(param.getIsByAgent()==2){//是
			sqlWhere.append("  AND c.customerAgentId>0  ");			
		}
		if(param.getIsZBKPtoInstallCard()==1){//不是从暂不开票转为已装卡
			sqlWhere.append(" AND c.isInstallCard=2 AND c.isInstallCardPre NOT IN (5,6)");
		}else if(param.getIsZBKPtoInstallCard()==2){//从暂不开票转为已装卡
			sqlWhere.append(" AND c.isInstallCard=2 AND c.isInstallCardPre IN (5,6) AND c.cancleSetDate>='2012-1-1'");			
		}
		
		//个体转型前企业是否装卡
		if(param.getIsRelateInstallCard()==1){//未装卡
			sqlWhere.append("  AND c.relateCustomerId>0 AND customer_relate.customerId IS NULL");
		}else if(param.getIsRelateInstallCard()==2){//已装卡
			sqlWhere.append("  AND c.relateCustomerId>0 AND customer_relate.customerId IS NOT NULL");			
		}
		
		if(param.getSearchFollowCusStateParam()!=null){//搜索企业的跟进状态

			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getReviewDateEnd())){
				sqlWhere.append(" AND fs.reviewDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getReviewDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getReviewDateStart())){
				sqlWhere.append(" AND fs.reviewDate>='"+param.getSearchFollowCusStateParam().getReviewDateStart()+"'");
			}
			
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getModPcDateEnd())){
				sqlWhere.append(" AND fs.modPcDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getModPcDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getModPcDateStart())){
				sqlWhere.append(" AND fs.modPcDate>='"+param.getSearchFollowCusStateParam().getModPcDateStart()+"'");
			}	
			
			if(param.getSearchFollowCusStateParam().getWhfIsOk()>0){
				sqlWhere.append(" AND fs.whfIsOk=").append(param.getSearchFollowCusStateParam().getWhfIsOk());				
			}
			
			if(param.getSearchFollowCusStateParam().getIsPayAll()==1){//未交齐费用
				sqlWhere.append(" AND (fs.whfIsOk!=2 OR fs.isPay1416!=2)");
			}else if(param.getSearchFollowCusStateParam().getIsPayAll()==2){//交齐费用
				sqlWhere.append(" AND fs.whfIsOk=2 AND fs.isPay1416=2");
			}
			
			if(param.getSearchFollowCusStateParam().getIsMissFullJCD()==2){//欠完整检测单
				sqlWhere.append(" AND fs.isNeedFullJCD=2 and fs.isFullJCD!=2");
			}else if(param.getSearchFollowCusStateParam().getIsMissFullJCD()==1){//不欠完整检测单
				sqlWhere.append(" AND fs.isNeedFullJCD=2 and fs.isFullJCD=2");				
			}
			
			//正版操作系统序号来自订单
			if(param.getSearchFollowCusStateParam().getIsSysLegalFromOrder()==2){
				sqlWhere.append(" AND fs.isSysLegalFromOrder=2");				
			}
			//正版操作系统序号不是来自订单
			else if(param.getSearchFollowCusStateParam().getIsSysLegalFromOrder()==1){
				sqlWhere.append(" AND fs.isSysLegalFromOrder!=2");
			}
			
			//下次跟进人搜索
			if(param.getSearchFollowCusStateParam().getNextFollowerType()>0){
				sqlWhere.append(" AND fs.nextFollowerType=").append(param.getSearchFollowCusStateParam().getNextFollowerType());
			}
			
			if(param.getSearchFollowCusStateParam().getIsModDateSet()==1){//未修改过预设日期
				sqlWhere.append(" AND fs.isModFirstFollowDateSet=1 AND fs.isModFirstVisitDateSet=1 AND isModFirstReturnVisitDateSet=1");
			}else if(param.getSearchFollowCusStateParam().getIsModDateSet()==2){//修改过预设日期
				sqlWhere.append(" AND (fs.isModFirstFollowDateSet=2 OR fs.isModFirstVisitDateSet=2 OR isModFirstReturnVisitDateSet=2)");
			}
			
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getModDateSetDateEnd())){
				sqlWhere.append(" AND (fs.modFirstFollowDateSetDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getModDateSetDateEnd()+"',INTERVAL 1 day)");
				sqlWhere.append(" OR fs.modFirstVisitDateSetDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getModDateSetDateEnd()+"',INTERVAL 1 day)");
				sqlWhere.append(" OR fs.modFirstReturnVisitDateSetDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getModDateSetDateEnd()+"',INTERVAL 1 day))");
			}	
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getModDateSetDateStart())){
				sqlWhere.append(" AND (fs.modFirstFollowDateSetDate>='"+param.getSearchFollowCusStateParam().getModDateSetDateStart()+"'");
				sqlWhere.append(" OR fs.modFirstVisitDateSetDate>='"+param.getSearchFollowCusStateParam().getModDateSetDateStart()+"'");
				sqlWhere.append(" OR fs.modFirstReturnVisitDateSetDate>='"+param.getSearchFollowCusStateParam().getModDateSetDateStart()+"')");
			}
			
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstFollowDateSetEnd())){
				sqlWhere.append(" AND fs.firstFollowDateSet< DATE_ADD('"+param.getSearchFollowCusStateParam().getFirstFollowDateSetEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstFollowDateSetStart())){
				sqlWhere.append(" AND fs.firstFollowDateSet>='"+param.getSearchFollowCusStateParam().getFirstFollowDateSetStart()+"'");
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstVisitDateSetEnd())){
				sqlWhere.append(" AND fs.firstVisitDateSet< DATE_ADD('"+param.getSearchFollowCusStateParam().getFirstVisitDateSetEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstVisitDateSetStart())){
				sqlWhere.append(" AND fs.firstVisitDateSet>='"+param.getSearchFollowCusStateParam().getFirstVisitDateSetStart()+"'");
			}	
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstReturnVisitDateSetEnd())){
				sqlWhere.append(" AND fs.firstReturnVisitDateSet< DATE_ADD('"+param.getSearchFollowCusStateParam().getFirstReturnVisitDateSetEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstReturnVisitDateSetStart())){
				sqlWhere.append(" AND fs.firstReturnVisitDateSet>='"+param.getSearchFollowCusStateParam().getFirstReturnVisitDateSetStart()+"'");
			}			
			if(param.getSearchFollowCusStateParam().getIsSendCommission()==1){//未委托代扣
				sqlWhere.append(" AND (fs.isSendCommission<2 OR fs.isSendCommission IS NULL) ");				
			}else if(param.getSearchFollowCusStateParam().getIsSendCommission()==2){//已委托代扣
				sqlWhere.append(" AND fs.isSendCommission=2 ");								
			}			
			if(param.getSearchFollowCusStateParam().getIsInformInstallCard()==1){//未通知装卡
				sqlWhere.append(" AND fs.informDate IS NULL");				
			}else if(param.getSearchFollowCusStateParam().getIsInformInstallCard()==2){//已通知装卡
				sqlWhere.append(" AND fs.informType>0 AND fs.informDate IS NOT NULL");								
			}
			if(param.getSearchFollowCusStateParam().getIsReview()==1){//跟进人未复核
				sqlWhere.append(" AND fs.isReview<2");				
			}else if(param.getSearchFollowCusStateParam().getIsReview()>1){//跟进人有复核
				sqlWhere.append(" AND fs.isReview=").append(param.getSearchFollowCusStateParam().getIsReview());								
			}	
			if(param.getSearchFollowCusStateParam().getPwFollow()==1){//跟进人未复核要跟进批文
				sqlWhere.append(" AND fs.pwFollow!=2");				
			}else if(param.getSearchFollowCusStateParam().getPwFollow()==2){//跟进人已复核跟进批文
				sqlWhere.append(" AND fs.pwFollow=2");								
			}			
			if(param.getSearchFollowCusStateParam().getIsInformPW()==1){//未通知客户跟进批文
				sqlWhere.append(" AND fs.informPWDate IS NULL");				
			}else if(param.getSearchFollowCusStateParam().getIsInformPW()==2){//已通知客户跟进批文
				sqlWhere.append(" AND fs.informPWDate IS NOT NULL");								
			}
			if(param.getSearchFollowCusStateParam().getIsHasPW()==1){//未有批文
				sqlWhere.append(" AND fs.checkDate IS NULL");				
			}else if(param.getSearchFollowCusStateParam().getIsHasPW()==2){//有批文
				sqlWhere.append(" AND fs.checkDate IS NOT NULL");								
			}				
			if(param.getSearchFollowCusStateParam().getIsReserveInstallCard()==1){//未预约装卡
				sqlWhere.append(" AND fs.reserveDate IS NULL");				
			}else if(param.getSearchFollowCusStateParam().getIsReserveInstallCard()==2){//已预约装卡
				sqlWhere.append(" AND fs.reserveDate IS NOT NULL");								
			}			
			if(param.getSearchFollowCusStateParam().getIsReceive()>0){
				sqlWhere.append(" AND fs.isReceive=").append(param.getSearchFollowCusStateParam().getIsReceive());
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getInformTypes())&&!"0".equals(param.getSearchFollowCusStateParam().getInformTypes())){
				sqlWhere.append(" AND fs.informType IN (").append(param.getSearchFollowCusStateParam().getInformTypes()).append(")");
			}
			if(param.getSearchFollowCusStateParam().getIsFullJCD()==2){
				sqlWhere.append(" AND fs.isSendJCD=2");
				sqlWhere.append(" AND fs.isFullJCD=").append(param.getSearchFollowCusStateParam().getIsFullJCD());
			}else if(param.getSearchFollowCusStateParam().getIsFullJCD()==1){
				sqlWhere.append(" AND fs.isFullJCD=").append(param.getSearchFollowCusStateParam().getIsFullJCD());				
			}
			
			if(param.getSearchFollowCusStateParam().getIsSendAll()==2){//已提交全部审批资料
//				sqlWhere.append(" AND fs.isSendJCD=2");
//				sqlWhere.append(" AND fs.isFullJCD=2");
//				sqlWhere.append(" AND fs.isSendDZSBHT=2");
//				sqlWhere.append(" AND fs.isSendWTS=2");
//				sqlWhere.append(" AND fs.isSendWHHT=2");			
//				sqlWhere.append(" AND fs.checkDate IS NOT NULL");
				sqlWhere.append(" AND fs.isTakePW=2");			
//				sqlWhere.append(" AND (fs.isPay336=2 OR fs.isFax336=2) ");						
				sqlWhere.append(" AND fs.isPay1416=2");											
				sqlWhere.append(" AND fs.whfIsOk=2");											
//				if(param.getSearchFollowCusStateParam().getIsSendAll5DaysAgo()==2){
//					String fiveDaysAgo=DateClass.subWorkDay(DateClass.GetSysDate(), 4);
//					sqlWhere.append(" AND fs.sendDate<='").append(fiveDaysAgo).append("'");
//				}else if(param.getSearchFollowCusStateParam().getIsSendAll5DaysAgo()==1){
//					String fiveDaysAgo=DateClass.subWorkDay(DateClass.GetSysDate(), 4);
//					sqlWhere.append(" AND fs.sendDate>'").append(fiveDaysAgo).append("'");
//				}
			}else if(param.getSearchFollowCusStateParam().getIsSendAll()==1){//未全部提交审批资料
				sqlWhere.append(" AND (fs.isSendJCD!=2");
				sqlWhere.append(" OR fs.isSendDZSBHT!=2");
				sqlWhere.append(" OR fs.isSendWTS!=2");
				sqlWhere.append(" OR fs.isSendWHHT!=2)");
			} 
			
			if(param.getSearchFollowCusStateParam().getInstallCardState()==11){//非暂不开票及申报
				sqlWhere.append(" AND fs.isInstallCard!=6");
			}else if(param.getSearchFollowCusStateParam().getInstallCardState()==1){//未能发卡
				sqlWhere.append(" AND fs.isInstallCard in (1,3,4,5,6)");
			}else if(param.getSearchFollowCusStateParam().getInstallCardState()==12){//未装卡
				sqlWhere.append(" AND fs.isInstallCard!=2");
			}else if(param.getSearchFollowCusStateParam().getInstallCardState()>0){
				sqlWhere.append(" AND fs.isInstallCard=").append(param.getSearchFollowCusStateParam().getInstallCardState());						
			}
			
			if(param.getSearchFollowCusStateParam().getIsFirstFollow()==1){
				sqlWhere.append(" AND fs.firstFollowDate IS NULL");
			}else if(param.getSearchFollowCusStateParam().getIsFirstFollow()==2){
				sqlWhere.append(" AND fs.firstFollowDate IS NOT NULL");
			}
			if(param.getSearchFollowCusStateParam().getFirstFollowIsValid()==2){//有有效的首次跟进
				sqlWhere.append(" AND fs.firstFollowIsValid=2");
			}else if(param.getSearchFollowCusStateParam().getFirstFollowIsValid()==1){//无有效的首次跟进
				sqlWhere.append(" AND fs.firstFollowIsValid!=2");				
			}

			if(param.getSearchFollowCusStateParam().getIsFirstVisit()==1){
				sqlWhere.append(" AND fs.firstVisitDate IS NULL");
			}else if(param.getSearchFollowCusStateParam().getIsFirstVisit()==2){
				sqlWhere.append(" AND fs.firstVisitDate IS NOT NULL");
			}
			if(param.getSearchFollowCusStateParam().getFirstVisitIsValid()==2){//有有效的首次上门
				sqlWhere.append(" AND fs.firstVisitIsValid=2");
			}else if(param.getSearchFollowCusStateParam().getFirstVisitIsValid()==1){//无有效的首次上门
				sqlWhere.append(" AND fs.firstVisitIsValid!=2");				
			}

			if(param.getSearchFollowCusStateParam().getIsFirstReturnVisit()==1){
				sqlWhere.append(" AND fs.firstReturnVisitDate IS NULL");
			}else if(param.getSearchFollowCusStateParam().getIsFirstReturnVisit()==2){
				sqlWhere.append(" AND fs.firstReturnVisitDate IS NOT NULL");
			}
			if(param.getSearchFollowCusStateParam().getFirstReturnVisitIsValid()==2){//有有效的首次回访
				sqlWhere.append(" AND fs.firstReturnVisitIsValid=2");
			}else if(param.getSearchFollowCusStateParam().getFirstReturnVisitIsValid()==1){//无有效的首次回访
				sqlWhere.append(" AND fs.firstReturnVisitIsValid!=2");				
			}

			if(param.getSearchFollowCusStateParam().getIsDirect()>0){
				sqlWhere.append(" AND fs.isDirect=").append(param.getSearchFollowCusStateParam().getIsDirect());
			}
			if(param.getSearchFollowCusStateParam().getIsPay160()>0){
				sqlWhere.append(" AND fs.isPay160=").append(param.getSearchFollowCusStateParam().getIsPay160());
			}
			if(param.getSearchFollowCusStateParam().getIsPay336()>0){
				sqlWhere.append(" AND fs.isPay336=").append(param.getSearchFollowCusStateParam().getIsPay336());
			}
			if(param.getSearchFollowCusStateParam().getIsPay1416()>0){
				sqlWhere.append(" AND fs.isPay1416=").append(param.getSearchFollowCusStateParam().getIsPay1416());
			}
			if(param.getSearchFollowCusStateParam().getIsFax336()==2){
				sqlWhere.append(" AND fs.isFax336=").append(param.getSearchFollowCusStateParam().getIsFax336());
				if(param.getSearchFollowCusStateParam().getIsFax10DaysAgo()==1){
					String tenDaysAgo=DateClass.subWorkDay(DateClass.GetSysDate(), 5);
					sqlWhere.append(" AND fs.fax336Date<='").append(tenDaysAgo).append("'");
				}
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getNextFollowDateEnd())){
				sqlWhere.append(" AND fs.nextFollowDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getNextFollowDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getNextFollowDateStart())){
				sqlWhere.append(" AND fs.nextFollowDate>='"+param.getSearchFollowCusStateParam().getNextFollowDateStart()+"'");
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstFollowDateEnd())){
				sqlWhere.append(" AND fs.firstFollowDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getFirstFollowDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstFollowDateStart())){
				sqlWhere.append(" AND fs.firstFollowDate>='"+param.getSearchFollowCusStateParam().getFirstFollowDateStart()+"'");
			}	
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstVisitDateEnd())){
				sqlWhere.append(" AND fs.firstVisitDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getFirstVisitDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getFirstVisitDateStart())){
				sqlWhere.append(" AND fs.firstVisitDate>='"+param.getSearchFollowCusStateParam().getFirstVisitDateStart()+"'");
			}			
//			if(param.getSearchFollowCusStateParam().getIsFax10DaysAgo()==1){
//				sqlWhere.append(" AND fs.isFax336=2");
//			}else{
//				if(param.getSearchFollowCusStateParam().getIsFax336()==1){//未传真
//					sqlWhere.append(" AND fs.fax336Date IS null");
//				}else if(param.getSearchFollowCusStateParam().getIsFax336()==2){//已传真
//					sqlWhere.append(" AND fs.fax336Date IS NOT null");					
//				}
//			}
			if(param.getSearchFollowCusStateParam().getIsFinish()>0){
				sqlWhere.append(" AND fs.isFinish=").append(param.getSearchFollowCusStateParam().getIsFinish());
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getInstallTypes())&&!"0".equals(param.getSearchFollowCusStateParam().getInstallTypes())){
				sqlWhere.append(" AND fs.installType IN (").append(param.getSearchFollowCusStateParam().getInstallTypes()).append(")");
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getCheckDateEnd())){
				sqlWhere.append(" AND fs.checkDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getCheckDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getCheckDateStart())){
				sqlWhere.append(" AND fs.checkDate>='"+param.getSearchFollowCusStateParam().getCheckDateStart()+"'");
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getCheckDateSetDateEnd())){
				sqlWhere.append(" AND fs.checkDateSetDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getCheckDateSetDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getCheckDateSetDateStart())){
				sqlWhere.append(" AND fs.checkDateSetDate>='"+param.getSearchFollowCusStateParam().getCheckDateSetDateStart()+"'");
			}			
			if(param.getSearchFollowCusStateParam().getIsTakePW()>0){
				if(param.getSearchFollowCusStateParam().getIsTakePW()==1){//未取批文
					sqlWhere.append(" AND fs.isTakePW<=1");
				}else{
					sqlWhere.append(" AND fs.isTakePW=").append(param.getSearchFollowCusStateParam().getIsTakePW());
				}
				
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getTakeDateEnd())){
				sqlWhere.append(" AND fs.takeDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getTakeDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getTakeDateStart())){
				sqlWhere.append(" AND fs.takeDate>='"+param.getSearchFollowCusStateParam().getTakeDateStart()+"'");
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getReserveDateEnd())){
				sqlWhere.append(" AND fs.reserveDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getReserveDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getReserveDateStart())){
				sqlWhere.append(" AND fs.reserveDate>='"+param.getSearchFollowCusStateParam().getReserveDateStart()+"'");
			}
			if(param.getSearchFollowCusStateParam().getIsReserve()==1){//未预约领卡
				sqlWhere.append(" AND fs.takeDate IS NULL");				
			}else if(param.getSearchFollowCusStateParam().getIsReserve()==2){//已预约领卡
				sqlWhere.append(" AND fs.takeDate IS NOT NULL");				
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getDirectDateEnd())){
				sqlWhere.append(" AND fs.directDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getDirectDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getDirectDateStart())){
				sqlWhere.append(" AND fs.directDate>='"+param.getSearchFollowCusStateParam().getDirectDateStart()+"'");
			}
			if(param.getSearchFollowCusStateParam().getIsStamp()>0){
				sqlWhere.append(" AND fs.isStamp=").append(param.getSearchFollowCusStateParam().getIsStamp());
			}
			if(param.getSearchFollowCusStateParam().getIsReserveVisit()>0){
				sqlWhere.append(" AND fs.isReserveVisit=").append(param.getSearchFollowCusStateParam().getIsReserveVisit());
			}
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getCanInstallCardDateEnd())){
				sqlWhere.append(" AND fs.canInstallCardDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getCanInstallCardDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getCanInstallCardDateStart())){
				sqlWhere.append(" AND fs.canInstallCardDate>='"+param.getSearchFollowCusStateParam().getCanInstallCardDateStart()+"'");
			}
			if(param.getSearchFollowCusStateParam().getIsSendKPZL()==2){
				sqlWhere.append(" AND fs.isSendKPZL=").append(param.getSearchFollowCusStateParam().getIsSendKPZL());
			}else if(param.getSearchFollowCusStateParam().getIsSendKPZL()==1){
				sqlWhere.append("  AND ( fs.isSendKPZL IS NULL OR  fs.isSendKPZL<=").append(param.getSearchFollowCusStateParam().getIsSendKPZL()).append(")");
			}
			
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getSendDateEnd())){
				sqlWhere.append(" AND fs.sendDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getSendDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getSendDateStart())){
				sqlWhere.append(" AND fs.sendDate>='"+param.getSearchFollowCusStateParam().getSendDateStart()+"'");
			}
			
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getModIsTakePWDateSetDateEnd())){
				sqlWhere.append(" AND fs.modIsTakePWDate< DATE_ADD('"+param.getSearchFollowCusStateParam().getModIsTakePWDateSetDateEnd()+"',INTERVAL 1 day)");
			}		
			if(!StringFunction.isEmpty(param.getSearchFollowCusStateParam().getModIsTakePWDateSetDateStart())){
				sqlWhere.append(" AND fs.modIsTakePWDate>='"+param.getSearchFollowCusStateParam().getModIsTakePWDateSetDateStart()+"'");
			}			
			
		}
		if(param.getSoftVersion()!=0){
			sqlWhere.append(" AND c.softVersion ="+param.getSoftVersion());
		}
		if(param.getIsYingZhuanZeng()>0){
			sqlWhere.append(" AND c.isYingZhuanZeng = "+param.getIsYingZhuanZeng());
		}
		if(!StringFunction.isEmpty(param.getYgzDateEnd())){
			sqlWhere.append(" AND c.ygzDate< DATE_ADD('"+param.getYgzDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getYgzDateStart())){
			sqlWhere.append(" AND c.ygzDate>='"+param.getYgzDateStart()+"'");
		}
		if(param.getIsInstallCardBeforeYGZ()==1){
			if(param.getIsYingZhuanZeng()!=2){
				sqlWhere.append(" AND c.isYingZhuanZeng = 2");
			}
			sqlWhere.append(" AND (c.ygzDate<=c.installCardDate)");
		}else if(param.getIsInstallCardBeforeYGZ()==2){
			if(param.getIsYingZhuanZeng()!=2){
				sqlWhere.append(" AND c.isYingZhuanZeng = 2");
			}			
			sqlWhere.append(" AND (c.ygzDate>c.installCardDate)");
		}
		if(!StringFunction.isEmpty(param.getCustomerIds())){
			sqlWhere.append(" AND c.customerId IN (").append(db.filterStr(param.getCustomerIds())).append(")");
		}
		if(param.getIsShow()>0){
			sqlWhere.append(" AND c.isShow=").append(param.getIsShow());
		}
//		if(!StringFunction.isEmpty(param.getCustomerAgentName())){
//			sqlWhere.append(" AND customer_agent.companyName LIKE '%").append(param.getCustomerAgentName()).append("%'");
//		}
		if(param.getCustomerAgentId()>0){
			sqlWhere.append(" AND c.customerAgentId=").append(param.getCustomerAgentId());
		}
		if(param.getIsAgent()==2){
			sqlWhere.append(" AND c.isAgent=2");
		}else if(param.getIsAgent()==1){
			sqlWhere.append(" AND c.isAgent!=2");			
		}
		
		if(param.getIsAssign()==1){
			sqlWhere.append(" AND c.follower<=0");
		}else if(param.getIsAssign()==2){
			sqlWhere.append(" AND c.follower>0");
		}
		if(!StringFunction.isEmpty(param.getAssignDateEnd())){
			sqlWhere.append(" AND c.assignDate< DATE_ADD('"+param.getAssignDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getAssignDateStart())){
			sqlWhere.append(" AND c.assignDate>='"+param.getAssignDateStart()+"'");
		}
		if(!StringFunction.isEmpty(param.getFollowers())){
			sqlWhere.append(" AND c.follower IN (").append(param.getFollowers()).append(")");
		}
		
		if(!StringFunction.isEmpty(param.getBankAccount())){
			sqlWhere.append(" AND c.bankAccount='").append(db.filterStr(param.getBankAccount())).append("'");
		}
		if(param.getIsPay336()==1){
			sqlWhere.append(" AND (rebuy.warrantyDate<'").append(cn.qtone.utils.DateClass.getYear("")+"-12-31").append("'");
			sqlWhere.append(" OR rebuy.lastPayDate IS Null)");
		}else if(param.getIsPay336()==2){
			sqlWhere.append(" AND rebuy.warrantyDate>='").append(cn.qtone.utils.DateClass.getYear("")+"-12-31").append("'");			
		}
		if(!StringFunction.isEmpty(param.getLinkman())){
			sqlWhere.append(" AND c.linkman='").append(db.filterStr(param.getLinkman())).append("'");
		}
		if(param.getIsImportance()>0){
			if(param.getIsImportance()==1){
				sqlWhere.append(" AND c.isImportance!=2");
			}else{
				sqlWhere.append(" AND c.isImportance=2");				
			}
		}
		if(!StringFunction.isEmpty(param.getCityIds())){
			sqlWhere.append(" AND c.cityId IN ("+param.getCityIds()+")");
		}
		if(!StringFunction.isEmpty(param.getCompanyName())){
			if(cn.qtone.modules.Const.isFullText&&param.getIsUseFullText()){
				sqlWhere.append(" AND MATCH(companyName) AGAINST ('"+db.filterStr(param.getCompanyName())+"'  IN BOOLEAN MODE)");
			}else{
				if(StringFunction.isEmpty(param.getCustomerIdsFullText())){
					sqlWhere.append(" AND c.companyName like '%"+db.filterStr(param.getCompanyName())+"%'");
				}
			}
		}
		
		if(!StringFunction.isEmpty(param.getCustomerIdsFullText())){
			sqlWhere.append(" AND c.customerId IN ("+param.getCustomerIdsFullText()+")");
		}
		if(!StringFunction.isEmpty(param.getTaxNO())){
			if(cn.qtone.modules.Const.isFullText){
				sqlWhere.append(" AND MATCH(taxNO) AGAINST ('"+db.filterStr(param.getTaxNO())+"'  IN BOOLEAN MODE)");				
			}else{
				sqlWhere.append(" AND c.taxNO like '%"+db.filterStr(param.getTaxNO())+"%'");
			}
		}
		if(param.getTaxType()>0){
			sqlWhere.append(" AND c.taxType="+param.getTaxType());
		}
		if(!StringFunction.isEmpty(param.getTel())){
			sqlWhere.append(" AND (c.tel='"+param.getTel()+"' OR ct.tel='"+param.getTel()+"')");
		}
		if(!StringFunction.isEmpty(param.getMobile())){
			sqlWhere.append(" AND c.mobile='"+param.getMobile()+"'");
		}		
		if(param.getIsActive()==2){
			sqlWhere.append(" AND ca.isActive=").append(2);
		}else if(param.getIsActive()==1){
			sqlWhere.append(" AND ca.isActive!=").append(2);			
		}
		if(param.getIsReturnvisit()==1){//今年未回访
			sqlWhere.append(" AND r.returnvisitId is null");
		}else if(param.getIsReturnvisit()==2){//今年已回访
			sqlWhere.append(" AND r.returnvisitId>0");			
		}
		if(!StringFunction.isEmpty(param.getConfirmDateEnd())){
			sqlWhere.append(" AND c.confirmDate< DATE_ADD('"+param.getConfirmDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getConfirmDateStart())){
			sqlWhere.append(" AND c.confirmDate>='"+param.getConfirmDateStart()+"'");
		}
		if(!StringFunction.isEmpty(param.getModifyDateEnd())){
			sqlWhere.append(" AND c.modifyDate< DATE_ADD('"+param.getModifyDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getModifyDateStart())){
			sqlWhere.append(" AND c.modifyDate>='"+param.getModifyDateStart()+"'");
		}		
		if(!StringFunction.isEmpty(param.getCreateDateEnd())){
			sqlWhere.append(" AND c.createDate< DATE_ADD('"+param.getCreateDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getCreateDateStart())){
			sqlWhere.append(" AND c.createDate>='"+param.getCreateDateStart()+"'");
		}			
		
		if(!StringFunction.isEmpty(param.getInstallCardDateEnd())){
			sqlWhere.append(" AND c.installCardDate< DATE_ADD('"+param.getInstallCardDateEnd()+"',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getInstallCardDateStart())){
			sqlWhere.append(" AND c.installCardDate>='"+param.getInstallCardDateStart()+"'");
		}		
		if(param.getIsCancle()>1){//企业是否注销
			sqlWhere.append(" AND c.isCancle="+param.getIsCancle());
			if(param.getIsCancle()>1&&param.getCustomerCancleType()>0){//注销类别查询
				sqlWhere.append(" AND c.CustomerCancleType ="+param.getCustomerCancleType());
			}
		}else if(param.getIsCancle()==1){  //正常 
			sqlWhere.append(" AND c.isCancle=1 ");
		}
		//金税卡安装情况
		if(!StringFunction.isEmpty(param.getIsInstallCard())&&!"0".equals(param.getIsInstallCard())){ 
			sqlWhere.append(" AND c.isInstallCard IN (").append(param.getIsInstallCard()).append(")");
			
			if("3".equals(param.getIsInstallCard())&&param.getCardCancleType()>0){//注销类别查询
				sqlWhere.append(" AND c.cardCancleType ="+param.getCardCancleType());
			}
			
		}
//		
//		else if(param.getIsInstallCard()>1){//2装了,3注销
//			sqlWhere.append(" AND c.isInstallCard ="+param.getIsInstallCard());
//			if(param.getIsInstallCard()==3&&param.getCardCancleType()>0){//注销类别查询
//				sqlWhere.append(" AND c.cardCancleType ="+param.getCardCancleType());
//			}
//		}
		//黑名单类型
		if(param.getBlackType()>0){
			param.setIsBlacklist(2);
			sqlWhere.append(" AND cbl.blackType=").append(param.getBlackType());
			
		}
		if(param.getIsBlacklist()==1){//不是黑名单
			sqlWhere.append(" AND cbl.customerBlacklistId IS NULL");
		}else if(param.getIsBlacklist()==2){//是黑名单
			sqlWhere.append(" AND cbl.customerBlacklistId IS NOT NULL");			
		}
		if(!StringFunction.isEmpty(param.getImportDate())){
			sqlWhere.append(" AND c.importDate='"+param.getImportDate()+"'");
		}
		if(param.getImportDateIsNull()==1){
			sqlWhere.append(" AND c.importDate IS NOT NULL");
		}else if(param.getImportDateIsNull()==2){
			sqlWhere.append(" AND c.importDate IS NULL");
		}
		if(!StringFunction.isEmpty(param.getImportDateEnd())){
			sqlWhere.append(" AND (c.importDate< DATE_ADD('"+param.getImportDateEnd()+"',INTERVAL 1 day) OR (c.createDate< DATE_ADD('"+param.getImportDateEnd()+"',INTERVAL 1 day)) AND c.importDate IS NULL)");
		}		
		if(!StringFunction.isEmpty(param.getImportDateStart())){
			sqlWhere.append(" AND (c.importDate>='"+param.getImportDateStart()+"' OR (c.createDate>='"+param.getImportDateStart()+"'  AND c.importDate IS NULL))");
		}
		if(param.getScannerType()!=0){
			sqlWhere.append(" AND c.scannerType=").append(param.getScannerType());
		}	 
		if(!StringFunction.isEmpty(param.getFax())){
			sqlWhere.append(" AND c.fax like '%"+db.filterStr(param.getFax())+"%'");
		}
		if(param.getIsRelate()>0){
			sqlWhere.append(" AND c.isRelate=").append(param.getIsRelate());
		}
		//加上是否安装报税盘
		if(param.getIsInstallBSP()>0){
			sqlWhere.append(" AND c.isInstallBSP=").append(param.getIsInstallBSP());
		}
		
		//是否绑定微信账号
		if(param.getIsCustomerWx() == 1){
			sqlWhere.append(" AND cx.customerId IS NULL");
		} else if(param.getIsCustomerWx() == 2) {
			sqlWhere.append(" AND cx.customerId IS NOT NULL");
		}
		
		return sqlWhere.toString();
	}	

	/**
	 * 根据参数生成搜索sql条件
	 * @param param 参数对象
	 * @return
	 */
	private String searchSQLFrom(SearchCustomerParam param){
		if(param==null) return "";
		StringBuffer sqlFrom=new StringBuffer();
//		if(!StringFunction.isEmpty(param.getCustomerAgentName())){
//			sqlFrom.append(" LEFT JOIN customer AS customer_agent ON c.customerAgentId=customer_agent.customerId");
//			sqlFrom.append(" AND customer_agent.isAgent=2");
//		}
		if(param.getSearchAssignChangeParam()!=null){
			if(param.getSearchAssignChangeParam().getCheckState()>0){
				sqlFrom.append(" INNER JOIN customer_assign_change AS cac ON c.customerId=cac.customerId");
			}
		}
		if(param.getCommissionIsSign()>0){
			sqlFrom.append(" LEFT JOIN commission_info AS ci ON c.customerId=ci.customerId");
		}
		if(param.getIsRelateInstallCard()>0){
			sqlFrom.append(" LEFT JOIN customer AS customer_relate ON c.relateCustomerId=customer_relate.customerId");
			sqlFrom.append(" AND customer_relate.isInstallCard IN (2,3,4)");
		}
		
		if(param.getSearchFollowCusStateParam()!=null){
//		if(param.getInstallCardState()>0){
			sqlFrom.append(" LEFT JOIN follow_cus_state AS fs ON c.customerId=fs.customerId");
//		}
		}
		
		if(param.getBlackType()>0||param.getIsBlacklist()>0){
			sqlFrom.append(" LEFT JOIN customer_blacklist AS cbl ON c.customerId=cbl.customerId AND cbl.flag=2 ");
		}
		
		if(param.getIsPay336()>0){
//			sqlFrom.append(" LEFT JOIN order_product_rebuy AS rebuy ON c.customerId=rebuy.customerId AND rebuy.productId=33 AND rebuy.isStop=1");
			sqlFrom.append(" LEFT JOIN order_product_rebuy AS rebuy ON c.customerId=rebuy.customerId AND rebuy.productId=33");
		}
		
		if(param.getIsActive()>0){
			sqlFrom.append(" LEFT JOIN customer_account AS ca ON c.customerId=ca.customerId AND ca.isMaster=1");
		}
		if(param.getIsReturnvisit()>0){
			String fromDate=cn.qtone.utils.DateClass.getYear("")+"-1-1";
			sqlFrom.append(" LEFT JOIN returnvisit AS r ON c.customerId=r.customerId");
			sqlFrom.append(" AND r.visitDate>='"+fromDate+"'");
		}
		if(!StringFunction.isEmpty(param.getBuy1416DateStart())||!StringFunction.isEmpty(param.getBuy1416DateEnd())){
			sqlFrom.append(" INNER JOIN `order` AS o ON o.customerId=c.customerId AND o.isReturn!=2 AND o.invalid!=1 AND o.buyType=3");
		}
		
		if(param.getIsModZBKP()>0){
			sqlFrom.append(" LEFT JOIN customer_isInstallCard_change AS cic ON c.customerId=cic.customerId");
		}
		if(!StringFunction.isEmpty(param.getTel())){
			sqlFrom.append(" LEFT JOIN customer_tel AS ct ON ct.customerId=c.customerId");
		}
		
		//绑定微信账号
		if(param.getIsCustomerWx() > 0) {
			sqlFrom.append(" LEFT JOIN customer_wx AS cx ON cx.customerId=c.customerId");
		}
		
		return sqlFrom.toString();
	}	

	/**
	 * 根据税号得到企业的id
	 * @param taxNo
	 * @return
	 */
	public int getCustomerIdByTaxNO(String taxNO){
		if(StringFunction.isEmpty(taxNO)){
			return 0;
		}
		String sql="SELECT customerId FROM customer WHERE taxNO='"+db.filterStr(taxNO)+"'";
		return db.countRowMain(sql);
	}

	/**
	 * 根据税号得到企业的基本信息
	 * @param taxNo
	 * @return
	 */
	public Customer getCustomerBaseInfoByTaxNO(String taxNO){
		if(StringFunction.isEmpty(taxNO)){
			return null;
		}
		String sql="SELECT customerId,companyName,linkman,tel FROM customer WHERE taxNO='"+db.filterStr(taxNO)+"'";
		ResultSet rs=null;
		Customer customer=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				customer=new Customer();
				customer.setCustomerId(rs.getInt("customerId"));
				customer.setCompanyName(rs.getString("companyName"));
				customer.setLinkman(rs.getString("linkman"));
				customer.setTel(rs.getString("tel"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return customer;
	}
	
	/**
	 * 根据税号得到企业的名称
	 * @param taxNo
	 * @return
	 */
	public String getCompanyNameByTaxNO(String taxNO){
		if(StringFunction.isEmpty(taxNO)){
			return "";
		}
		String sql="SELECT companyName FROM customer WHERE taxNO='"+db.filterStr(taxNO)+"'";
		return db.getValue(sql);
	}
	
	/**
	 * 根据公司编号得到企业的名称
	 * @param taxNo
	 * @return
	 */
	public String getCompanyNameByCustomerId(int  customerId){
		if(customerId<=0){
			return "";
		}
		String sql="SELECT companyName FROM customer WHERE customerId="+customerId;
		return db.getValue(sql);
	}
	/**
	 * 根据公司编号得到企业的税号
	 * @param taxNo
	 * @return
	 */
	public String getTaxNoByCustomerId(int  customerId){
		if(customerId<=0){
			return "";
		}
		String sql="SELECT taxNo FROM customer WHERE customerId="+customerId;
		return db.getValue(sql);
	}
	/**
	 * 根据email取得customerId
	 * @param email
	 * @return
	 */
	public int getCustomerIdByEmail(String email,int source,int taxType){
		String sql="SELECT customerId FROM customer WHERE email='"+db.filterStr(email)+"'" ;
		if(source>0){
			sql+=" AND source="+source;
		}
		if(taxType>0){
			sql+=" AND taxType="+taxType;
		}
		sql+=" ORDER BY customerId DESC LIMIT 1";
		return db.getValueIntMain(sql);
	}
	
	
	
	/**
	 * 根据公司名称得到企业的编号
	 * @param taxNo
	 * @return
	 */
	public int getCustomerIdByCompanyName(String  companyName){
		if(StringFunction.isEmpty(companyName)){
			return 0;
		}
		String sql="SELECT customerId FROM customer WHERE companyName=BINARY '"+companyName+"'";
		return db.getValueIntMain(sql);
	}

	/**
	 * 计算某一银行账号的企业数
	 * @param bankAccount
	 * @return
	 */
	public int countBankAccount(String bankAccount){
		if(StringFunction.isEmpty(bankAccount)){
			return 0;
		}
		String sql="SELECT count(*) FROM customer WHERE bankAccount='"+bankAccount+"'";
		return db.countRow(sql);
	}
	/**
	 * 根据公司银行账号得到企业的编号
	 * @param taxNo
	 * @return
	 */
	public int getCustomerIdByBankAccount(String bankAccount){
		if(StringFunction.isEmpty(bankAccount)){
			return 0;
		}
		String sql="SELECT customerId FROM customer WHERE bankAccount='"+bankAccount+"'";
		return db.getValueInt(sql);
	}
	
	/**
	 * 根据电话号码得到企业的编号
	 * @param customerId
	 * @return
	 */
	public int getCustomerIdByTel(String tel){
		if(StringFunction.isEmpty(tel)){
			return 0;
		}
		if(tel.startsWith("9")){
			tel=tel.substring(1);
		}
		int customerId=0;
		String sql="select  customerId from tel_record where  customerTel ='"+db.filterStr(tel)+"' ORDER BY creatDate DESC  LIMIT 1 ";
		customerId=db.getValueInt(sql);
		if(customerId==0){
			sql=" SELECT customerId FROM customer WHERE lastTel='"+db.filterStr(tel)+"' or tel='"+db.filterStr(tel)+"' or bossTel='"+db.filterStr(tel)+"' ORDER BY modifyDate DESC LIMIT 1 ";
			customerId=db.getValueInt(sql);
		}
		return customerId;
	}
	
	/**
	 * 更新扫描仪注册码到期即注册字段
	 * @param isRegSC 1否 2是
	 * @return
	 */
//	public boolean updateIsRegSC(int isRegSC,int customerId){
//		String sql="UPDATE customer SET isRegSC="+isRegSC+" WHERE customerId="+customerId;
//		return db.execute(sql);
//	}
	
	/**
	 * 判断税号是否正确
	 * @param taxNo 税号
	 * @return true正确 false错误
	 */
	public boolean isTaxNo(String taxNo){
//		String regEx="^(442000)[0-9X]{9,12}$";
		String regEx="^(442000)";
		java.util.regex.Pattern patten=java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m=patten.matcher(taxNo);
		return m.find();
	}
	
	/**
	 * 更新是否重要客户
	 * @param isImportance 1不是 2是
	 * @param customerId 客户id
	 * @return
	 */
	public boolean updateIsImportance(int isImportance,int customerId){
		String sql="UPDATE customer SET isImportance="+isImportance+" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	
	
	
	
	
	/**
	 * 更新
	 * @param customerId
	 * @param importExcelRow
	 * @return
	 */
	public boolean updateTaxNO(int customerId,CustomerImportRow importExcelRow,String importDate){
		String sql=" update customer set taxNO='"+db.filterStr(importExcelRow.getTaxNO())+"' " +
				" ,taxType=1,source=2,importDate='"+importDate+"'" +
				",confirmDate='"+importExcelRow.getConfirmDate()+"' where customerId="+customerId;
		return db.execute(sql);
	}
	
	
	
	public int insertImportCustomer(CustomerImportRow importCompany,String importDate){
		String sql=" insert into customer set taxNO='"+db.filterStr(importCompany.getTaxNO())+"',companyName='"+importCompany.getCompanyName()+"'" +
				" ,cityId="+importCompany.getCityId()+",isNew=1, taxType="+importCompany.getIsXgm()+",source=2,isConfirm=1" +
				",businessAddress='"+importCompany.getAddress()+"',linkman='"+importCompany.getLinkMan()+"'" +
				",confirmDate='"+importCompany.getConfirmDate()+"',createDate=CURRENT_TIMESTAMP" +
				",importDate='"+importDate+"'";
	
			String tel=importCompany.getTel();
			if(!StringFunction.isEmpty(tel)){
				tel=StringFunction.strReplace(tel, "，", ",");
				String[] telAry=tel.split(",");
				for(int j=0;j<telAry.length;j++){
					String tmpTel=telAry[j];
					if(tmpTel.length()>=11&&sql.indexOf(",mobile=")==-1){
						sql+=",mobile='"+db.filterStr(tmpTel)+"'";
					}else if(sql.indexOf(",tel=")==-1){
						sql+=",tel='"+db.filterStr(tmpTel)+"'";
					}
				}
				
				//根据电话查找是否有对应的代理记账公司
				SearchCustomerParam paramAgent=new SearchCustomerParam();
				paramAgent.setIsAgent(2);
				paramAgent.setMobile(tel);
				List customerAgentList=this.search(paramAgent, 0, 1);
				Customer customerAgent=null;
				if(customerAgentList!=null&&customerAgentList.size()>=1){
					customerAgent=(Customer)customerAgentList.get(0);

				}else{
					paramAgent.setTel(tel);
					paramAgent.setMobile("");
					customerAgentList=this.search(paramAgent, 0, 1);
					if(customerAgentList!=null&&customerAgentList.size()>=1){
						customerAgent=(Customer)customerAgentList.get(0);
					}
				}
				
				if(customerAgent!=null){
					importCompany.setCustomerAgent(customerAgent);
					importCompany.setCustomerAgentId(customerAgent.getCustomerId());
					
					//新企业跟进人：如果代理记账公司有跟进人则为代理记账公司跟进人
					//否则根据city表查找跟进人
					int follower=0;
					if(customerAgent.getFollower()>0){
						follower=customerAgent.getFollower();
					}else if(importCompany.getCityId()>0){
						CityHandle cityHandle=new CityHandle();
						follower=cityHandle.getUserBoIdByCityId(importCompany.getCityId());
					}
					
					sql+=",follower="+follower;
					sql+=",assignDate=CURRENT_DATE";
					sql+=",assigner=1";
					sql+=",customerAgentId="+customerAgent.getCustomerId();
					sql+=",unNeedFollow="+customerAgent.getUnNeedFollow();
					sql+=",qtjjlc="+customerAgent.getQtjjlc();
//					sql+=",unNeedVisit="+customerAgent.getUnNeedVisit();
//					sql+=",unNeedReturnvisit="+customerAgent.getUnNeedReturnvisit();
					
				}
			}
			

			
		if( db.execute(sql)){
			return db.getValueIntMain(" SELECT customerId  FROM customer WHERE taxNO='"+db.filterStr(importCompany.getTaxNO())+"' ORDER BY customerId DESC LIMIT 1 ");
		}else{
			return 0;
		}
	}
	
 /***
  *  更新软件版本号以及是否营转增
  * @param isYingZhuanZeng
  * @param customerId
  * @return
  */
	public boolean updateYGZ(int isYingZhuanZeng,int customerId){
		String sql=" UPDATE customer SET isYingZhuanZeng="+isYingZhuanZeng+",ygzDate=CURRENT_DATE WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	/**
	 * 更新企业目前使用哪个扫描系统
	 * @param customerId
	 * @param scannerType 使用的扫描系统
	 * @return
	 */
	public boolean updateScannerType(int customerId,int scannerType){
		String sql="UPDATE customer SET preScannerType=IF(scannerType<>"+scannerType+",scannerType,preScannerType)";
		sql+=",convertScannerTypeDate=IF(scannerType<>"+scannerType+",CURRENT_DATE,convertScannerTypeDate)";
		sql+=",scannerType="+scannerType;
		sql+=" WHERE customerId="+customerId;
		if(db.execute(sql)){
			//更新使用的扫描仪系统
			cn.qtone.modules.order.obj.OrderProductRebuyHandle rebuyHandle=new cn.qtone.modules.order.obj.OrderProductRebuyHandle();
			rebuyHandle.updateScanner(customerId, scannerType);
			return true;
		}
		return false;
	}

	
	/**
	 * 根据需上门安装尚洲软件的企业.xls初始化企业使用的扫描系统
	 */
	public void initScannerType(){
		String sql="SELECT customerId FROM order_product_rebuy WHERE productId IN (32,84,1671) AND isStop!=2 GROUP BY customerId";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				int customerId=rs.getInt("customerId");
				Customer customer=this.getCustomerById(customerId);
				if(customer==null) continue;
				String taxNO=customer.getTaxNO();
				ResultSet rsST=db.select("SELECT scannerType FROM temp_sannertype WHERE taxNO='"+taxNO+"'");
				
				boolean isSZ=false;
				if(rsST.next()){
					String scannerTypeXLS=rsST.getString("scannerType");
					isSZ="已置换尚洲".equals(scannerTypeXLS)?true:false;
				}else{//记录在excel不存在记录
					db.execute("INSERT INTO temp_scannertype_no SET taxNO='"+taxNO+"',companyName='"+db.filterStr(customer.getCompanyName())+"'");
				}
				
				String sql1="";
				if(isSZ){//使用尚洲
					sql1="UPDATE customer SET scannerType=84";
					sql1+=" WHERE customerId="+customerId;
				}else{//使用沪友
					sql1="UPDATE customer SET scannerType=32";
					sql1+=" WHERE customerId="+customerId;
				}
				db.execute(sql1);
				System.out.println(sql1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		
		
	}
	
	
	/***
	 * 将已到注销日期的待注销企业设置为注销
	 * @return
	 */
	public boolean updateIsCancle(){
		String sql="UPDATE customer SET isCancle=2 WHERE isCancle=5 AND cancleCusDate<=CURRENT_DATE";
		db.execute(sql);
		sql="UPDATE customer SET isInstallCard=3 WHERE isInstallCard=4 AND customerCancleType<=CURRENT_DATE";
		return db.execute(sql);
	}
	

	/**
	 * 将企业设为待注销
	 * @param cancleCusDate
	 * @param customerCancleType
	 * @param customerId
	 * @return
	 */
	public boolean cancleCus(int isCancle,int customerCancleType,String cancleCusDate,int customerId){
		if(customerId<=0) return false;
		String sql="UPDATE customer SET isCancle="+isCancle+",customerCancleType="+customerCancleType;
		sql+=",cancleCusDate='"+cancleCusDate+"'";
		sql+=",cancleCusSetDate=CURRENT_DATE";
		sql+=" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	
	/**
	 * 更新企业性质
	 * @param taxType
	 * @param customerId
	 * @return
	 */	
	public boolean updateTaxType(int taxType,int customerId,String confirmDate){
		String sql="update customer set taxType="+taxType;
		if(!StringFunction.isEmpty(confirmDate)){
			sql+=",confirmDate='"+confirmDate+"'";
			sql+=",confirmDateSetDate=CURRENT_DATE";
		}		
		sql+=" where customerId="+customerId;

		return db.execute(sql);
	}
	
	/**
	 * 更新是否新企业字段
	 * @param isNew 1旧企业 2新企业 3未设定
	 * @param customerId
	 * @return
	 */	
	public boolean updateIsNew(int isNew,int customerId){
		String sql="update customer set isNew="+isNew+" where customerId="+customerId;
		return db.execute(sql);
	}	
	
	/**
	 * 得出已分配的企业数
	 * @param customerIds
	 * @return
	 */
	public int hasAssignNum(String customerIds){
		String sql="SELECT count(*) FROM customer WHERE customerId IN ("+customerIds+") AND follower>0";
		return db.countRow(sql);
	}
	
	/**
	 * 分配客户
	 * @param follower 分配给的工作人员
	 * @param customerIds 要分配的客户id，多个用逗号隔开
	 * @param assigner为-1表示系统根据新企业跟进记录自动分配的
	 * @return
	 */
	public boolean assign(int follower,String customerIds,int assigner){

		String sql="UPDATE customer SET assignDate=CURRENT_DATE";
		sql+=" WHERE customerId IN ("+customerIds+") AND (assignDate IS NULL OR follower!="+follower+")";
		

		if(db.execute(sql)){
			sql="UPDATE customer SET follower="+follower;
			sql+=",assigner="+assigner;
			sql+=" WHERE customerId IN ("+customerIds+")";
			db.execute(sql);
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 跟进企业编号取得跟进人
	 * @param customerId
	 * @return
	 */
	public int getFollowerByCustomerId(int customerId){
		 String sql=" SELECT follower FROM  customer WHERE customerId="+customerId;
		 return db.getValueInt(sql);
	}
	
	/**
	  * 根据 企业的编号得到 cityId
	  * @param customerId
	  * @return
	  */
	 public int getCityIdByCustomerId(int customerId){
	  if(customerId<=0){
	   return 0;
	  }
	  String  sql=" SELECT cityId FROM customer WHERE customerId= "+customerId;
	  return db.getValueInt(sql);
	 }
	 /**
	  * 根据企业的编号得到企业的地址
	  * @param customerId
	  * @return
	  */
	 public String getAddressByCustomerId(int customerId){
		  if(customerId<=0){
		   return "";
		  }
		  String  sql=" SELECT address FROM customer WHERE customerId= "+customerId;
		  String address= db.getValue(sql);
		  if(StringFunction.isEmpty(address)){
			  address= db.getValue(" SELECT businessAddress FROM customer WHERE customerId= "+customerId);
		  }
		  if(StringFunction.isEmpty(address)){
			  address= db.getValue(" SELECT receipt_address FROM customer WHERE customerId= "+customerId);
		  }
		  return address;
	 }
	
	
	 /**
	  * 根据传真号码得到企业的编号
	  * @param customerId
	  * @return
	  */
	 public int getCustomerIdByFax(String fax){
	  if(StringFunction.isEmpty(fax)){
	   return 0;
	  }
	  String  sql=" SELECT customerId FROM customer WHERE fax='"+db.filterStr(fax)+"' ORDER BY modifyDate DESC LIMIT 1 ";
	  return db.getValueInt(sql);
	 }
	
		/**
		 * 从用友中更新企业信息，目前主要是更新开增值税发票的信息
		 * @param customerU8
		 * @param customerId
		 * @return
		 */
		public boolean updateCustomerFromU8(cn.qtone.modules.u8Syn.obj.u8.Customer customerU8,int customerId){
			if(customerU8==null||StringFunction.isEmpty(customerU8.getCCusAccount())){
				return false;
			}
			StringBuffer sqlBuf=new StringBuffer("UPDATE customer SET bankAccount='").append(db.filterStr(customerU8.getCCusAccount())).append("'");
			sqlBuf.append(",bank='").append(db.filterStr(StringFunction.isNull(customerU8.getCCusBank()))).append("'");
			sqlBuf.append(",receipt_address='").append(db.filterStr(StringFunction.isNull(customerU8.getCCusAddress()))).append("'");
			sqlBuf.append(",receipt_tel='").append(db.filterStr(StringFunction.isNull(customerU8.getCCusPhone()))).append("'");
			sqlBuf.append(",receipt_companyName='").append(db.filterStr(StringFunction.isNull(customerU8.getCCusName()))).append("'");
			sqlBuf.append(",receipt_taxNO='").append(db.filterStr(StringFunction.isNull(customerU8.getCCusRegCode()))).append("'");
			sqlBuf.append(",updateReceiptDate=CURRENT_TIMESTAMP");
			sqlBuf.append(" WHERE customerId=").append(customerId);
//			System.out.println(sqlBuf.toString());
			return db.execute(sqlBuf.toString());
		}	
	
		
		/**
		 * 更新企业信息
		 * 目前主要是从订购单中更新开增值税发票的信息
		 * @param customerU8
		 * @param customerId
		 * @return
		 */
		public boolean updateCustomerFromOtherModules(Customer customer ){
			if(customer==null ){
				return false;
			}
			StringBuffer sqlBuf=new StringBuffer("UPDATE customer SET bankAccount='").append(db.filterStr(customer.getBankAccount())).append("'");
			sqlBuf.append(",bank='").append(db.filterStr(StringFunction.isNull(customer.getBank()))).append("'");
			sqlBuf.append(",receipt_address='").append(db.filterStr(StringFunction.isNull(customer.getReceipt_address()))).append("'");
			sqlBuf.append(",receipt_tel='").append(db.filterStr(StringFunction.isNull(customer.getReceipt_tel()))).append("'");
			sqlBuf.append(",receipt_companyName='").append(db.filterStr(StringFunction.isNull(customer.getReceipt_companyName()))).append("'");
			sqlBuf.append(",receipt_taxNO='").append(db.filterStr(StringFunction.isNull(customer.getReceipt_taxNO()))).append("'");
			sqlBuf.append(" WHERE customerId=").append(customer.getCustomerId()); 
			return db.execute(sqlBuf.toString());
		}	
	
		/**
		 * 检查开票资料是否完整  返回 ture 完整 ; false 不完整
		 * @param customerId
		 * @return 
		 **/
		public boolean checkReceiptInfo(int customerId){
			String sql=" SELECT COUNT(*) FROM customer WHERE customerId="+customerId+"   AND  ( bankAccount='' OR bank='' " +
					" OR  receipt_address=''  OR receipt_tel=''  OR receipt_companyName=''  OR receipt_taxNO=''  OR  bankAccount IS NULL OR bank  IS NULL " +
					" OR  receipt_address  IS NULL  OR receipt_tel  IS NULL  OR receipt_companyName  IS NULL  OR receipt_taxNO  IS NULL ) ";
			return db.countRow(sql)<1;
		}
		
	
	/**
	 * 设置代理记账
	 * @param agentedList 被代理记账的公司
	 * @return 成功设置的记录数
	 */
	public int addAgented(List agentedList){
		if(agentedList==null) return 0;
		int successNUM=0;
		for(int i=0;i<agentedList.size();i++){
			Customer customer=(Customer)agentedList.get(i);
			if(this.addAgented(customer.getCustomerAgentId(), customer.getCustomerAgentStaffId(), customer.getCustomerId())){
				successNUM++;
			}
		}
		return successNUM;
	}
	
	/**
	 * 清除代理记账记录
	 * @param customerAgentId 代理公司id
	 * @param customerAgentedIds 被代理公司id，多个用逗号隔开
	 * @return
	 */
	public boolean delAgented(int customerAgentId,String customerAgentedIds){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("UPDATE customer SET customerAgentId=-2,customerAgentStaffId=0");
		sqlBuf.append(" WHERE customerAgentId=").append(customerAgentId);
		if(!StringFunction.isEmpty(customerAgentedIds)){
			sqlBuf.append(" AND customerId IN (").append(customerAgentedIds).append(")");
		}
		return db.execute(sqlBuf.toString());
	}
	
	/**
	 * 设置代理记账
	 * @param customerAgentId 代理记账的公司id
	 * @param customerAgentStaffId 代理记账公司的记账人id
	 * @param customerAgentedId 被代理记账的公司id
	 * @return
	 */
	public boolean addAgented(int customerAgentId,int customerAgentStaffId,int customerAgentedId){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("UPDATE customer SET customerAgentId=").append(customerAgentId);
		sqlBuf.append(",customerAgentStaffId=").append(customerAgentStaffId);
		sqlBuf.append(" WHERE customerId=").append(customerAgentedId);
		return db.execute(sqlBuf.toString());
	}
	
	/**
	 * 更新客户的被点击量
	 * @param customerId
	 * @return
	 */
	public boolean updateClickNum(int customerId){
		String sql="UPDATE customer SET clickNum=clickNum+1 WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	private void updatePreScannerType(){
		String sql="select distinct c.customerId from customer as c left join order_product as op on c.customerId=op.customerId"; 
		sql+=" and op.productId in (32,84,1671)"; 
		sql+=" left join `order` as o on op.orderNumber=o.orderNumber and o.isReturn!=2";
		sql+=" where c.scannerType<=0 and c.preScannerType<=0 and op.warrantyDate is not null and o.orderNumber is not null and op.orderNumber is not null";
		ResultSet rs=null;
		int i=0;
		try {
			rs=db.select(sql);
			while(rs.next()){
				int customerId=rs.getInt("c.customerId");
				String sqlMax="SELECT productId FROM order_product AS op LEFT JOIN `order` AS o ON op.orderNumber=o.orderNumber and o.isReturn!=2";
				sqlMax+=" where op.customerId="+customerId+" AND op.productId in (32,84,1671) and o.orderNumber is not null and op.warrantyDate is not null order by op.warrantyDate DESC limit 1";
				int productId=db.countRow(sqlMax);
				String sqlUpdate="update customer set preScannerType="+productId+" WHERE customerId="+customerId;
				i++;
				db.execute(sqlUpdate);
				System.out.println("更新第"+i+"条:"+customerId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
	}
	/**
	 * 更新企业装卡的卡号信息
	 * @param customerId
	 * @param cardNO
	 * @param ICNO
	 * @return  2012-10-08 邵雪芬确认需要按维护单录入的卡号信息为准，更新企业资料。
	 */
	public boolean updateCardInfo(int customerId,String cardNO,String ICNO){
		if(StringFunction.isEmpty(cardNO)||StringFunction.isEmpty(ICNO)){
			return false;
		}
		String sql=" UPDATE  customer  SET   cardNO='"+db.filterStr(cardNO)+"'"  //IF (( cardNO  IS NULL  OR  cardNO='' ),'"+db.filterStr(cardNO)+"',cardNO) "
                   +" ,ICNO='"+db.filterStr(ICNO)+"'" //IF ( ( ICNO IS NULL  OR ICNO='')  ,'"+db.filterStr(ICNO)+"',ICNO) "
                   +"  WHERE customerId="+customerId;
		return db.execute(sql);
	}
	 
	
	private void updateTmpHY(){
		String sql="UPDATE tmp_hy as ty,customer as c set ty.customerId=c.customerId WHERE ty.taxNO=c.taxNO";
		db.execute(sql);
		sql="select * from tmp_hy";
		ResultSet rs=null;
		int i=0;
		try {
			rs=db.selectMain(sql);
			while(rs.next()){
				i++;
				System.out.println(i);
				int customerId=rs.getInt("customerId");
				String linkman="",tel="";
				sql="select * from customer_tel where customerId="+customerId+" ORDER BY updateTime DESC limit 2";
				ResultSet rsTmp=db.selectMain(sql);
				while(rsTmp.next()){
					if(StringFunction.isEmpty(linkman)){
						linkman=rsTmp.getString("linkman");
					}else{
						linkman=linkman+","+rsTmp.getString("linkman");
					}
					if(StringFunction.isEmpty(tel)){
						tel=rsTmp.getString("tel");
					}else{
						tel=tel+","+rsTmp.getString("tel");
					}
					String sqlUpdate="update tmp_hy set linkman='"+db.filterStr(linkman)+"',tel='"+db.filterStr(tel)+"' where customerId="+customerId;
					db.execute(sqlUpdate);
				}
				db.closeResultSet(rsTmp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		sql="update tmp_hy set tel=concat('a',tel),taxNO=concat('a',taxNO)";
		db.execute(sql);
		System.out.print("end");
	}
	
	/**
	 * 设置企业的转换状态
	 * @param isRelate
	 * @param customerId
	 * @return
	 */
	public boolean setCustomerRelate(int isRelate,int customerId){
		String sql="update customer set isRelate="+isRelate+" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	/**
	 * 取得转型后的企业ID
	 */
	public int  getCustomerIdByRelateCustomerId(int relateCustomerId){
		String sql=" SELECT customerId FROM customer WHERE relateCustomerId="+relateCustomerId;
		return db.getValueInt(sql);
	}
	
	/**
	 * 根据删除了电子申报软件来更新电子申报状态
	 * @return
	 */
	public boolean updateDzsbStateByDelOrder(){
		String sql="update customer as c left join `order` as o on c.customerId=o.customerId and o.buyType=4 and o.isReturn!=2 and o.invalid=2 SET c.dzsbState=1 where o.orderNumber is null and c.dzsbState in (2,3,5,8)";
		return db.execute(sql);
	}
	
	/**
	 * 更新主机已过期但电子申报状态为5或6，按规则重新设置
	 */
	public void updateDzsbStateByPcWD(){
		ProductHandle productHandle=new ProductHandle();
		String pcIds=productHandle.getProductIds("3");
		String sql="select c.customerId  from customer as c left join order_product as op on c.customerId=op.customerId";
		sql+=" AND op.productId in ("+pcIds+")"; 
		sql+=" and op.amount>=1 and op.warrantyDate>=CURRENT_DATE";  
		sql+=" LEFT JOIN `order` AS o ON o.orderNumber=op.orderNumber AND o.isReturn!=2 AND o.invalid!=1";
		sql+=" where c.dzsbState in (5,6) AND o.orderId is null";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				int customerId=rs.getInt("c.customerId");
				this.updateDzsb(customerId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
		}
	}
	
	/**
	 * 更新是否有报税软件字段,如果已经是有注册码则不用再更新
	 * @param customerId
	 * @param hasBSRJ
	 * @return
	 */
	public boolean updateHasBSRJ(int customerId,int hasBSRJ,String fretchBSRJDate){
		String sql="UPDATE customer SET hasBSRJ="+hasBSRJ;
		if(hasBSRJ==1){//没有注册码则清空注册码生成日期
			sql+=",fretchBSRJDate=NULL";
		}else{
			sql+=",fretchBSRJDate=IFNULL(fretchBSRJDate,'"+fretchBSRJDate+"')";
		}
		sql+=" WHERE customerId="+customerId+" AND hasBSRJ=1";
		return db.execute(sql);
	}

	/**
	 * 确认是否有报税软件字段
	 * @param customerId
	 * @return
	 */
	public boolean confirmHasBSRJ(int customerId){
		String sql="UPDATE customer SET hasBSRJ=3,confirmBSRJDate= IF(confirmBSRJDate is null OR hasBSRJ!=3,now(),confirmBSRJDate)";
		sql+=" WHERE customerId="+customerId+" AND hasBSRJ!=3";
		return db.execute(sql);
	}
	/**
	 * 确认是否有报税软件字段(无)
	 * @param customerId
	 * @return
	 */
	public boolean confirmHasNoBSRJ(int customerId){
		String sql="UPDATE customer SET hasBSRJ=4,confirmBSRJDate= IF(confirmBSRJDate is null OR hasBSRJ!=4,now(),confirmBSRJDate)";
		sql+=" WHERE customerId="+customerId+" and hasBSRJ!=4";
		return db.execute(sql);
	}
	
	/**
	 * //根据税号更新状态
	 * @param taxno税号
	 * @param status状态  3有(已确认) 4有(已确认无)
	 * @return 0:成功 1:税号不存在 2:状态错误,3:数据库更新不成功	 */
	public int updataBSRJ(String taxno,int status){
		int taxnum;
		boolean updateflag;
		taxnum=db.countRow("select count(1) from customer where taxNO='"+taxno+"'");
		if (taxnum==0)
		{
			return 1;
		}
		else if((status!=3)&&(status!=4))//状态错误
		{
			return 2;
		}
		else
		{
		String sql="UPDATE customer SET confirmBSRJDate= IF(confirmBSRJDate is null OR hasBSRJ!="+status+",now(),confirmBSRJDate),hasBSRJ="+status;
		sql+=" WHERE taxNo='"+taxno+"' AND hasBSRJ!="+status;
		updateflag=db.execute(sql);
		if(updateflag) 
		{
			return 0;
		} 
		else
		{
			return 3;
		} 	
		}	
	}

	public boolean updateWorkTime(int customerId,String workTimeAMStart,String workTimeAMEnd,String workTimePMStart,String workTimePMEnd){

		String sql="UPDATE customer set customerId="+customerId;
		if(!StringFunction.isEmpty(workTimeAMStart)){
			sql+=",workTimeAMStart='"+workTimeAMStart+"'";
		}
		if(!StringFunction.isEmpty(workTimeAMEnd)){
			sql+=",workTimeAMEnd='"+workTimeAMEnd+"'";
		}
		if(!StringFunction.isEmpty(workTimePMStart)){
			sql+=",workTimePMStart='"+workTimePMStart+"'";
		}
		if(!StringFunction.isEmpty(workTimePMEnd)){
			sql+=",workTimePMEnd='"+workTimePMEnd+"'";
		}
		sql+=" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	public boolean updateTel(int customerId,String tel){
		String sql="UPDATE customer SET tel='"+db.filterStr(tel)+"' WHERE customerId="+customerId;
		return db.execute(sql);
	}

	public boolean updateMobile(int customerId,String mobile){
		String sql="UPDATE customer SET mobile='"+db.filterStr(mobile)+"' WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	/**
	 * 根据公司名称使用全文搜索得到1000个customer表的id供各模块搜索
	 * @param companyName
	 * @return customer表的customerId，逗号隔开
	 */
	public String fullTextSearchByCompanyName(String companyName){
		if(StringFunction.isEmpty(companyName)) return null;
		StringBuffer sqlBuf=new StringBuffer("SELECT customerId FROM customer");
		sqlBuf.append(" WHERE MATCH(companyName) AGAINST ('"+db.filterStr(companyName)+"'  IN BOOLEAN MODE)");
		sqlBuf.append(" LIMIT 0,500");
		String customerIds=db.getQueryResult(sqlBuf.toString(),",");
		if(StringFunction.isEmpty(customerIds)) customerIds="0";//如果没有数据则返回0，这样关联其他表搜索才会快
		return customerIds;
	}
	/**
	 * 更新升级状态
	 * @param customerId
	 * @param isUpgradeCard
	 * @return
	 */
	public boolean setIsUpgradeCardByCustomerId(int customerId,int isUpgradeCard){
		String sql=" update customer set isUpgradeCard="+isUpgradeCard+" where customerId="+customerId;
		return db.execute(sql);
	}
	/**
	 * 更新发行日期字段
	 * @param customerId
	 * @param publishDate
	 * @return
	 */
	public boolean updatePublishDate(int customerId,String publishDate){
		String sql="UPDATE customer SET publishDate=";
		if(StringFunction.isEmpty(publishDate)){
			sql+="Null";
		}else{
			sql+="'"+publishDate+"'";
		}
		sql+=" WHERE customerId="+customerId;
		return db.execute(sql);
		
	}
	
	/**
	 * 已装卡但专用设备退单的企业
	 * @param orderDate 退单的下单日期
	 * @return
	 */
	public List fretchInstallCardReturn(String orderDate){
		List list=new ArrayList();
		String sql="select c.customerId,c.companyName from customer as c left join `order` as o";
		sql+=" on c.customerId=o.customerId and o.buyType=3 and o.isReturn=2 and o.cost>0";
		sql+=" and o.isDeliver=2 and o.orderDate>='"+orderDate+"'";
		sql+=" left join customer_taxcard as t on c.customerId=t.customerId";
		sql+=" where c.isInstallCard=2 and o.orderId IS NOT NULL and t.customerId is null";
		sql+=" order by c.customerId";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				String companyName=rs.getString("c.companyName");
				int customerId=rs.getInt("c.customerId");
				Customer customer=new Customer();
				customer.setCompanyName(companyName);
				customer.setCustomerId(customerId);
				list.add(customer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		return list;
	}
	
	public boolean exitsTaxNO(String taxNO) {
		String sql = "select taxNO from customer where taxNO = '"+taxNO+"'";
		ResultSet rs = null;
		boolean flag = false;
		try {
			rs = db.select(sql);
			if(rs.next()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.closeResultSet(rs);
		}
		return flag;
	}
	
	public static void main(String[] args){
		try {
			ConnectionManager.init("E:\\java\\qt_tax_v2\\WebRoot\\WEB-INF\\conf\\DatabasePool.conf");
	        DBControlManager.init("E:\\java\\qt_tax_v2\\WebRoot\\WEB-INF\\conf\\DBControl.conf");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CustomerHandle customerHandle=new CustomerHandle();
		customerHandle.updateDasbState();
//		customerHandle.updateDzsbStateByPcWD();
//		Customer customer=customerHandle.getCustomerById(42723);
//		System.out.println(customerHandle.updateDzsb(37709));

//		Customer customer=customerHandle.getCustomerById(27998);
//		JSONObject jsonObject=JSONObject.fromObject(customer);
//		
//		Customer customer2=(Customer)JSONObject.toBean(jsonObject, Customer.class);
//		System.out.println(customer2.getCompanyName());
		
//		System.out.println("finish");
	}
}

