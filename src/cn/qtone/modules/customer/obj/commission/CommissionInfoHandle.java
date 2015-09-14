package cn.qtone.modules.customer.obj.commission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.modules.visit.obj.Visit;
import cn.qtone.modules.customer.obj.CustomerHandle;
import cn.qtone.modules.taxcard.obj.CardImportObj;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.DateClass;
import cn.qtone.utils.StringFunction;
import cn.qtone.utils.excel.ExcelInfo;
import cn.qtone.utils.excel.ExcelUtil;
import cn.qtone.utils.excel.MyHSSFRow;
import cn.qtone.utils.excel.MyHSSFSheet;

public class CommissionInfoHandle extends BaseHandle {

	public CommissionInfoHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 *  根据上门维护生成委托代扣
	 * @param visit
	 */
	public void generateByVisit(Visit visit){
		CommissionInfoHandle commissionInfoHandle=new CommissionInfoHandle();
		CommissionInfo commissionInfo=commissionInfoHandle.getCommissionInfoByCustomerId(visit.getCustomerId());
		if(commissionInfo==null){
			commissionInfo=new CommissionInfo();					
		}else {
			if(commissionInfo.getIsSign()==2){//如果是已签订则不用生成
				return;
			}
		}
			commissionInfo.setIsSign(1);
			commissionInfo.setSource(2);
			commissionInfo.setSourceId(visit.getVisitId());
			commissionInfo.setCustomerId(visit.getCustomerId());
			CustomerHandle customerHandle=new CustomerHandle();
			commissionInfo.setCompanyName(customerHandle.getCompanyNameByCustomerId(visit.getCustomerId()));
			CommissionNumberHandle commissionNumberHandle=new CommissionNumberHandle();
			if(StringFunction.isEmpty(commissionInfo.getNumber())||commissionInfo.getIsSign()==3){
				String number=commissionNumberHandle.getMinNumber();
				commissionInfo.setNumber(number);
			}
			if(commissionInfo.getCommissionInfoId()==0){
				commissionInfoHandle.insert(commissionInfo);
			}else{
				commissionInfoHandle.edit(commissionInfo);
				if(commissionInfo.getIsSign()==1){
					//更新协议编号为已用 
					commissionNumberHandle.edit(commissionInfo.getNumber(),2,commissionInfo.getCustomerId()); 
				}
			}
	}
	/**
	 * 获取代扣状态
	 * @param customerId
	 * @param isCancle
	 * @param isInstallCard
	 * @return
	 */
	public String fretchIsSignStr(int customerId,int isCancle,int isInstallCard){
		int isSign=0;
		int isExport=0;
		String sql="SELECT isSign,isExport FROM commission_info AS i LEFT JOIN commission_number AS n ON n.number=i.number WHERE i.customerId="+customerId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				isSign= rs.getInt("i.isSign");
				isExport=rs.getInt("n.isExport");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		if(isSign==0&&isCancle==1&&isInstallCard==2){
			isSign=4;
		}
		
		String isSignStr=(String)cn.qtone.modules.customer.Const.getCommissionIsSign().get(isSign+"");
		if(isSign==2&&isExport!=2){
			isSignStr+="(未上交)";
		}
		
		if("已签订(未上交)".equals(isSignStr)){
			isSignStr="未签订";
		}
		return isSignStr;
	}
	
	/**
	 * 保存数据
	 * @param commissionInfo
	 * @return
	 */
//	public boolean save(CommissionInfo commissionInfo){
//		 if(this.isExist(commissionInfo.getCustomerId())){
//			  return this.insert(commissionInfo);
//		 }else{
//			 return  this.edit(commissionInfo);
//		 }
//	}
	/**
	 * 是否存在
	 * @param customerId
	 * @return
	 */
	public boolean isExist(int customerId){
		String sql=" SELECT COUNT(*)  FROM  commission_info  WHERE customerId="+customerId;
		sql+=" AND isSign!=3";
		return db.countRow(sql)>0;
	}
	
	/**
	 * 增加
	 * @param commissionInfo
	 * @return
	 */
	public boolean insert(CommissionInfo commissionInfo){
		String sql="INSERT INTO commission_info  SET customerId="+commissionInfo.getCustomerId() 
		        +",companyName='"+db.filterStr(commissionInfo.getCompanyName())+"'" 
		        +",bankNO='"+db.filterStr(commissionInfo.getBankNO())+"'" 
		        +",bankName='"+db.filterStr(commissionInfo.getBankName())+"'" 
		        +",bankAccount='"+db.filterStr(commissionInfo.getBankAccount())+"'" 
		        +",organizationCode='"+db.filterStr(commissionInfo.getOrganizationCode())+"'"  		       
		        +",linkMan='"+db.filterStr(commissionInfo.getLinkMan())+"'" 
		        +",number='"+db.filterStr(commissionInfo.getNumber())+"'" 
		        +",isNew="+commissionInfo.getIsNew() 
		        +",commissionType="+commissionInfo.getCommissionType() 
		        +",isYingZhuanZeng="+commissionInfo.getIsYingZhuanZeng() 
		        +",source="+commissionInfo.getSource()
		        +",sourceId="+commissionInfo.getSourceId()
		        +",isSign="+commissionInfo.getIsSign()
		        +",createDate=CURRENT_TIMESTAMP ";
		if(!StringFunction.isEmpty(commissionInfo.getStartDate())){
			sql +=",startDate='"+db.filterStr(commissionInfo.getStartDate())+"'";
		}
		if(!StringFunction.isEmpty(commissionInfo.getAgreementDate())){
			sql +=",agreementDate='"+db.filterStr(commissionInfo.getAgreementDate())+"'";
		} 			
		if(db.execute(sql)){
			 //更新协议编号为已用 
			CommissionNumberHandle commissionNumberHandle=new CommissionNumberHandle();
			commissionNumberHandle.edit(commissionInfo.getNumber(),2,commissionInfo.getCustomerId()); 
			return true;
		}
		return false;
		
		 
	}
	/**
	 * 修改
	 * @param commissionInfo
	 * @return
	 */
	public boolean edit(CommissionInfo commissionInfo){
			String sql="UPDATE  commission_info  SET  companyName='"+db.filterStr(commissionInfo.getCompanyName())+"'" 
	        +",bankNO='"+db.filterStr(commissionInfo.getBankNO())+"'" 
	        +",bankName='"+db.filterStr(commissionInfo.getBankName())+"'" 
	        +",bankAccount='"+db.filterStr(commissionInfo.getBankAccount())+"'" 
	        +",organizationCode='"+db.filterStr(commissionInfo.getOrganizationCode())+"'"  		       
	        +",linkMan='"+db.filterStr(commissionInfo.getLinkMan())+"'" 
	        +",number='"+db.filterStr(commissionInfo.getNumber())+"'" 
	        +",isNew="+commissionInfo.getIsNew() 
	        +",commissionType="+commissionInfo.getCommissionType() 
	        +",isYingZhuanZeng="+commissionInfo.getIsYingZhuanZeng() +",modDate=CURRENT_TIMESTAMP ";
			if(!StringFunction.isEmpty(commissionInfo.getStartDate())){
				sql +=",startDate='"+db.filterStr(commissionInfo.getStartDate())+"'";
			}
			if(!StringFunction.isEmpty(commissionInfo.getAgreementDate())){
				sql +=",agreementDate='"+db.filterStr(commissionInfo.getAgreementDate())+"'";
			} 
			sql +=",isSign="+commissionInfo.getIsSign();
			if(commissionInfo.getIsSign()==2){//已签订
				sql +=",signDate=CURRENT_TIMESTAMP,signUserBoId="+commissionInfo.getSignUserBoId();
			}
			if(commissionInfo.getSource()>1){
				sql+=",source="+commissionInfo.getSource();
			}
			if(commissionInfo.getSourceId()>0){
				sql+=",sourceId="+commissionInfo.getSourceId();
			}
			sql +=" WHERE  customerId="+commissionInfo.getCustomerId();
			return db.execute(sql);
	}
	
	private String getWhereCase(SearchCommissionInfoParam param){
		if(param==null) return "";
		StringBuffer bf=new StringBuffer();
		if(!StringFunction.isEmpty(param.getSignDateStart())){
			bf.append(" AND i.signDate >='").append(db.filterStr(param.getSignDateStart())).append("'");
		}
		if(!StringFunction.isEmpty(param.getSignDateEnd())){
			bf.append(" AND i.signDate < DATE_ADD('").append(db.filterStr(param.getSignDateEnd())).append("',INTERVAL 1 day)");
		}		
		if(!StringFunction.isEmpty(param.getTaxNO())){
			bf.append(" AND c.taxNO LIKE '%").append(db.filterStr(param.getTaxNO())).append("%'");
		}
		if(!StringFunction.isEmpty(param.getCompanyName())){
			bf.append(" AND i.companyName LIKE '%").append(db.filterStr(param.getCompanyName())).append("%'");
		}
		if(!StringFunction.isEmpty(param.getBankName())){
			bf.append(" AND i.bankName LIKE '%").append(db.filterStr(param.getBankName())).append("%'");
		}
		if(!StringFunction.isEmpty(param.getCreateDateStart())){
			bf.append(" AND i.createDate >='").append(db.filterStr(param.getCreateDateStart())).append("'");
		}
		if(!StringFunction.isEmpty(param.getCreateDateEnd())){
			bf.append(" AND i.createDate < DATE_ADD('").append(db.filterStr(param.getCreateDateEnd())).append("',INTERVAL 1 day)");
		}
		if( param.getIsYingZhuanZeng()>0 ){
			bf.append(" AND i.isYingZhuanZeng=").append( param.getIsYingZhuanZeng());
		}
		if(!StringFunction.isEmpty(param.getNumber())){
			bf.append(" AND i.number='").append(db.filterStr(param.getNumber())).append("'");
		}
		if( param.getNumberIsInvalid()==1 ){//非作废，只显示在用
			bf.append(" AND n.state=2 ");
		}else if( param.getNumberIsInvalid()==2 ){//作废
			bf.append(" AND n.state=3 ");
		}
		if( param.getIsBankOfChina()==1 ){//非中国银行
			bf.append(" AND i.bankName NOT LIKE '%中国银行%' ");
		}else if( param.getIsBankOfChina()==2 ){//中国银行
			bf.append(" AND i.bankName   LIKE '%中国银行%' ");
		}
		if( param.getIsExport()==2 ){//是否已上交
			bf.append(" AND n.isExport=").append(param.getIsExport());
		} else if( param.getIsExport()==1 ){//是否已上交
			bf.append(" AND ( n.isExport IS NULL OR n.isExport<2) ");
		}
		if( param.getIsSign()>0 ){//签订状态
			bf.append(" AND i.isSign=").append(param.getIsSign());
		}
		if(param.getCommissionSource()>0){//来源
			bf.append(" AND i.source=").append(param.getCommissionSource());
		}
		return bf.toString();
	}
	
	public int getTotalRows(SearchCommissionInfoParam param){
		String sql=" SELECT  COUNT(*)  FROM commission_info  AS i LEFT JOIN commission_number AS n ON n.number=i.number " 
			+" LEFT JOIN customer AS c  ON i.customerId=c.customerId  WHERE   1=1  ";//未用的不列出
		sql +=this.getWhereCase(param);
		return db.countRow(sql);
	}
	
	/***
	 * 搜索
	 * @return
	 */
	public List search(SearchCommissionInfoParam param,int start,int limit){
		String sql=" SELECT i.*,n.state,n.number,c.taxNO  FROM commission_info  AS i LEFT JOIN commission_number AS n ON n.number=i.number " 
			+" LEFT JOIN customer AS c  ON i.customerId=c.customerId  WHERE   1=1 ";//未用的不列出
		sql +=this.getWhereCase(param);
		sql +=" ORDER BY n.number DESC ";
		if(limit>0){
			sql +=" LIMIT "+start+","+limit;
		}		
		List list=new ArrayList();
		ResultSet rs=null;
//		CommissionInfo commissionInfo=null;
	    try{
	    	rs=db.select(sql);
	    	while(rs.next()){
	    		CommissionInfo commissionInfo=new CommissionInfo();
	    		commissionInfo.setCommissionInfoId(rs.getInt("i.commissionInfoId"));
	    		commissionInfo.setCustomerId(rs.getInt("i.customerId"));
	    		commissionInfo.setCompanyName(rs.getString("i.companyName"));
	    		commissionInfo.setTaxNO(rs.getString("c.taxNO"));
	    		commissionInfo.setBankNO(rs.getString("i.bankNO"));
	    		commissionInfo.setBankName(rs.getString("i.bankName"));
	    		commissionInfo.setBankAccount(rs.getString("i.bankAccount"));
	    		commissionInfo.setOrganizationCode(rs.getString("i.organizationCode"));
	    		commissionInfo.setStartDate(rs.getString("i.startDate"));
	    		commissionInfo.setAgreementDate(rs.getString("i.agreementDate"));
	    		commissionInfo.setLinkMan(rs.getString("i.linkMan"));
	    		commissionInfo.setNumber(rs.getString("n.number"));
	    		commissionInfo.setIsNew(rs.getInt("i.isNew"));
	    		commissionInfo.setIsYingZhuanZeng(rs.getInt("i.isYingZhuanZeng"));
	    		commissionInfo.setCreateDate(rs.getString("i.createDate"));
	    		commissionInfo.setState(rs.getInt("n.state"));
	    		commissionInfo.setIsSign(rs.getInt("i.isSign"));
	    		commissionInfo.setSignDate(rs.getString("i.signDate"));
	    		commissionInfo.setSignUserBoId(rs.getInt("i.signUserBoId"));
	    		commissionInfo.setCommissionType(rs.getInt("i.commissionType"));
	    		commissionInfo.setSource(rs.getInt("i.source"));
	    		commissionInfo.setSourceId(rs.getInt("i.sourceId"));
	    		list.add(commissionInfo);
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}	
		return list;
	}
	
	private CommissionInfo getCommissionInfoByRs(ResultSet rs) throws Exception{
		CommissionInfo commissionInfo=new CommissionInfo();
		commissionInfo.setCommissionInfoId(rs.getInt("i.commissionInfoId"));
		commissionInfo.setCustomerId(rs.getInt("i.customerId"));
		commissionInfo.setCompanyName(rs.getString("i.companyName"));
//		commissionInfo.setTaxNO(rs.getString("c.taxNO"));
		commissionInfo.setBankNO(rs.getString("i.bankNO"));
		commissionInfo.setBankName(rs.getString("i.bankName"));
		commissionInfo.setBankAccount(rs.getString("i.bankAccount"));
		commissionInfo.setOrganizationCode(rs.getString("i.organizationCode"));
		commissionInfo.setStartDate(rs.getString("i.startDate"));
		commissionInfo.setAgreementDate(rs.getString("i.agreementDate"));
		commissionInfo.setLinkMan(rs.getString("i.linkMan"));
		commissionInfo.setNumber(rs.getString("n.number"));
		commissionInfo.setIsNew(rs.getInt("i.isNew"));
		commissionInfo.setIsYingZhuanZeng(rs.getInt("i.isYingZhuanZeng"));
		commissionInfo.setCreateDate(rs.getString("i.createDate"));
		commissionInfo.setState(rs.getInt("n.state"));
		commissionInfo.setIsSign(rs.getInt("i.isSign"));
		commissionInfo.setSignDate(rs.getString("i.signDate"));
		commissionInfo.setSignUserBoId(rs.getInt("i.signUserBoId"));
		commissionInfo.setCommissionType(rs.getInt("i.commissionType"));
		commissionInfo.setSource(rs.getInt("i.source"));
		commissionInfo.setSourceId(rs.getInt("i.sourceId"));
		return commissionInfo;
	}

	/**
	 * 取得委托信息对象
	 * @param customerId
	 * @return
	 */
	public CommissionInfo getCommissionInfoByVisitId(int visitId){
		String sql=" SELECT i.*,n.state,n.number  FROM  commission_info  AS i LEFT JOIN commission_number AS n ON n.number=i.number WHERE i.source=2 AND i.sourceId="+visitId;
		ResultSet rs=null;
		CommissionInfo commissionInfo=null;
		CustomerHandle customerHandle=new CustomerHandle();
	    try{
	    	rs=db.select(sql);
	    	if(rs.next()){
	    		commissionInfo=this.getCommissionInfoByRs(rs);
	    		commissionInfo.setCustomer(customerHandle.getCustomerById(commissionInfo.getCustomerId()));
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return commissionInfo;		
	}
	/**
	 * 取得委托信息对象
	 * @param customerId
	 * @return
	 */
	public CommissionInfo getCommissionInfoByCustomerId(int customerId){
		String sql=" SELECT i.*,n.state,n.number  FROM  commission_info  AS i LEFT JOIN commission_number AS n ON n.number=i.number WHERE i.customerId="+customerId;
		ResultSet rs=null;
		CommissionInfo commissionInfo=null;
		CustomerHandle customerHandle=new CustomerHandle();
	    try{
	    	rs=db.select(sql);
	    	if(rs.next()){
	    		commissionInfo=this.getCommissionInfoByRs(rs);
	    		commissionInfo.setCustomer(customerHandle.getCustomerById(commissionInfo.getCustomerId()));
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return commissionInfo;
	}
	
	/**
	 * 取得委托信息对象
	 * @param number
	 * @return
	 */
	public CommissionInfo getCommissionInfoByNumber(String number){
		String sql=" SELECT i.*,n.state,n.number  FROM commission_number AS n LEFT JOIN commission_info  AS i ON n.number=i.number WHERE i.number='"+db.filterStr(number)+"'";
		ResultSet rs=null;
		CommissionInfo commissionInfo=null;
		CustomerHandle customerHandle=new CustomerHandle();
	    try{
	    	rs=db.select(sql);
	    	if(rs.next()){
	    		commissionInfo=this.getCommissionInfoByRs(rs);
	    		commissionInfo.setCustomer(customerHandle.getCustomerById(commissionInfo.getCustomerId()));
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return commissionInfo;
	}
	
	/**
	 * 从excel的第一个表中读取导入数据
	 * @param excelPath
	 * @return list
	 */
	public ExcelInfo getBankInfoFromExcel(String excelPath){	
		cn.qtone.utils.excel.ExcelInfo excelInfo=new ExcelInfo();
		CommissionBank imxls=null;
		ArrayList list=new ArrayList();
		ExcelUtil excelUtil=new ExcelUtil(excelPath);
		MyHSSFSheet sheet=excelUtil.getSheet(0);	
		int totalRow=sheet.getLastRowNum();
		MyHSSFRow row=null;
		//检查导入列是否对应
		row=sheet.getRow(0);		
		this.checkBankInfoRowName(row,excelInfo);
		if(excelInfo.getFlag()==1) return excelInfo; 
		for(int i=1;i<=totalRow;i++){
			row=sheet.getRow(i);
			if(row==null) continue;
			imxls=getBankInfoByRow(row );
			list.add(imxls);
		}
		excelInfo.setFlag(2);
		excelInfo.setDataList(list);
		return excelInfo;
	}
	
	private CommissionBank getBankInfoByRow(MyHSSFRow row ){
		CommissionBank imxls=new CommissionBank();	
		imxls.setBankNO(this.trim(row.getStringCellValue(0))); 
		imxls.setBankName(this.trim(row.getStringCellValue(1)));		
		return imxls;
	}
	 
	/**
	 * 检查列名
	 * @param imxls
	 * @param excelInfo
	 */
	private void checkBankInfoRowName(MyHSSFRow row,ExcelInfo excelInfo){
		if(row==null){
			excelInfo.setFlag(1);
			excelInfo.setMsg(" 列名出错，请核对第1行是否包含列名.");
			return ;
		}
		if(!this.trim(row.getStringCellValue(0)).equals("银行行号")){
			excelInfo.setMsg(excelInfo.getMsg()+"  银行行号对应列出错  ");
			excelInfo.setFlag(1);
		}
		if(!this.trim(row.getStringCellValue(1)).equals("行名")){
			excelInfo.setMsg(excelInfo.getMsg()+"  行名对应列出错  ");
			excelInfo.setFlag(1);
		}
		 
	}
	/**
	 * 去空格
	 * @param value
	 * @return
	 */
	private String trim(String value){
		return StringFunction.strReplace(value," ","");
	}
	/**
	 * 设置为已签订
	 * @return
	 */
	 public boolean setIsSign(int customerId,int userBoId,int isSign){
		 String sql="UPDATE commission_info SET isSign="+isSign+",signDate=CURRENT_TIMESTAMP,signUserBoId="+userBoId;
		 if(isSign==3){//客户不签订
			 sql+=",number=null";
		 }
		 sql+=" WHERE customerId="+customerId;
		 return db.execute(sql);
	 }
	
	 public int getIsSign(int customerId){
		 String sql="SELECT isSign FROM commission_info  WHERE customerId="+customerId;
		 return db.getValueInt(sql);
	 }
	 /**
	  * 删除编号对应的委托企业记录
	  * @param number
	  * @return
	  */
	 public boolean delInfo(String number){
		    if(StringFunction.isEmpty(number)) return false;
			String sql="DELETE FROM  commission_info  WHERE number='"+db.filterStr(number)+"'";
			return db.execute(sql);
	}
	 /**
	  * 删除
	  * @param commissionInfoId
	  * @return
	  */
	 public boolean delInfo(int commissionInfoId){
			String sql="DELETE FROM  commission_info  WHERE commissionInfoId="+commissionInfoId;
			return db.execute(sql);
	}		
}
