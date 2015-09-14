package cn.qtone.modules.customer.obj.blacklist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import cn.qtone.modules.customer.obj.CustomerHandle;
import cn.qtone.modules.user.obj.User_bo;
import cn.qtone.modules.user.obj.User_boHandle;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.DateClass;
import cn.qtone.utils.ParseTool;
import cn.qtone.utils.StringFunction;

public class CustomerBlacklistHandle extends BaseHandle{

	public CustomerBlacklistHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 忽略待取消黑名单
	 * @param customerBlacklistId
	 * @return
	 */
	public boolean skipCancleBlacklist(int customerBlacklistId){
		String sql="UPDATE customer_blacklist SET isSuggestCancle=3,modDate=CURRENT_TIMESTAMP";
		sql+=" WHERE customerBlacklistId="+customerBlacklistId;
		return db.execute(sql);
	}
	
	private String sqlWhere(SearchBlacklistParam param){
		StringBuffer sqlWhere=new StringBuffer();
		if(!StringFunction.isEmpty(param.getCompanyName())){
			sqlWhere.append(" AND c.companyName like '%").append(param.getCompanyName()).append("%'");
		}
		if(!StringFunction.isEmpty(param.getTaxNO())){
			sqlWhere.append(" AND c.taxNO='").append(param.getTaxNO()).append("'");
		}
		if(param.getFlag()>0){
			sqlWhere.append(" AND b.flag=").append(param.getFlag());
		}
		if(param.getIsSuggestCancle()>0){
			sqlWhere.append(" AND b.isSuggestCancle=").append(param.getIsSuggestCancle());			
		}
		return sqlWhere.toString();
	}
	
	public int totalRow(SearchBlacklistParam param){
		String sql="SELECT COUNT(*) FROM customer_blacklist AS b,customer AS c WHERE b.customerId=c.customerId";
		sql+=this.sqlWhere(param);
		return db.countRow(sql);
	}
	
	/**
	 * 待取消黑名单
	 * @param param
	 * @param startPos
	 * @param num
	 * @return
	 */
	public List search(SearchBlacklistParam param,int startPos,int num){
		String sql="SELECT b.*,c.companyName FROM customer_blacklist AS b,customer as c WHERE b.customerId=c.customerId";
		sql+=this.sqlWhere(param);
		sql+=" ORDER BY b.sugCancleDate,customerBlacklistId DESC";
		if(num!=-1) sql+=" LIMIT "+startPos+","+num;
		
		List list=new ArrayList();
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerBlacklist customerBlacklist=new CustomerBlacklist();
				customerBlacklist.setBlackType(rs.getInt("b.blackType"));
				customerBlacklist.setComment(rs.getString("b.comment"));
				customerBlacklist.setCreateDate(rs.getString("b.createDate"));
				customerBlacklist.setCustomerBlacklistId(rs.getInt("b.customerBlacklistId"));
				customerBlacklist.setCustomerId(rs.getInt("b.customerId"));
				customerBlacklist.setIsSuggestCancle(rs.getInt("b.isSuggestCancle"));
				customerBlacklist.setModDate(rs.getString("b.modDate"));
				customerBlacklist.setSugCancleDate(rs.getString("b.sugCancleDate"));
				customerBlacklist.setUserBoId(rs.getInt("userBoId"));
				customerBlacklist.setCompanyName(rs.getString("c.companyName"));
				list.add(customerBlacklist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		
		return list;
	}
	/**
	 * 写入黑名单
	 * @param customerBlacklist
	 * @return
	 */
	public boolean insert (CustomerBlacklist customerBlacklist){
		String sql=" INSERT INTO customer_blacklist set customerId="+customerBlacklist.getCustomerId()+",blackType="+customerBlacklist.getBlackType()
		            +", comment='"+db.filterStr(customerBlacklist.getComment())+"', userBoId="+customerBlacklist.getUserBoId()
		            +", createDate=CURRENT_TIMESTAMP ,modDate=CURRENT_TIMESTAMP, flag=2,sugCancleDate=CURRENT_DATE";
		return db.execute(sql);
	}
	
	/**
	 * 写入黑名单
	 * @param customerBlacklist
	 * @return
	 */
	public boolean update (CustomerBlacklist customerBlacklist){
		String sql=" UPDATE customer_blacklist set  comment='"+db.filterStr(customerBlacklist.getComment())+"'"
		+", userBoId="+customerBlacklist.getUserBoId()+",blackType="+customerBlacklist.getBlackType()
		+", flag=2 "
		+", modDate=CURRENT_TIMESTAMP "
		+" WHERE customerId="+customerBlacklist.getCustomerId();
		return db.execute(sql);
	}

	/**
	 * 企业是否已添加黑名单
	 * @param customerId
	 * @return
	 */
	public int isExist(int customerId){
		String sql=" SELECT COUNT(*) FROM customer_blacklist WHERE customerId="+customerId;
		return db.countRow(sql);
	}
	
	/**
	 * 取消黑名单
	 * @param customerId
	 * @return
	 */
	public boolean delete(CustomerBlacklist customerBlacklist){
		String sql=" UPDATE  customer_blacklist  SET flag=1,userBoId="+customerBlacklist.getUserBoId()
		+"  ,comment='"+db.filterStr(customerBlacklist.getComment())+"' "
		+", modDate=CURRENT_TIMESTAMP "
	    +" WHERE customerId="+customerBlacklist.getCustomerId();
		if(db.execute(sql)){
			//更新电子申报状态
			CustomerHandle customerHandle=new CustomerHandle();
			customerHandle.updateDzsb(customerBlacklist.getCustomerId());
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 取得customerBlacklist对象
	 * @param customerId
	 * @return
	 */
	public CustomerBlacklist getCustomerBlacklistByCustomerId(int customerId){
		CustomerBlacklist customerBlacklist=null;
		String sql=" SELECT * FROM customer_blacklist WHERE flag=2 AND customerId="+customerId;
		ResultSet rs=null;
		try{
			rs=db.select(sql);
			if(rs.next()){
				customerBlacklist=new CustomerBlacklist();
				customerBlacklist.setComment(rs.getString("comment"));
				customerBlacklist.setCustomerBlacklistId(rs.getInt("customerBlacklistId"));
				customerBlacklist.setCustomerId(rs.getInt("customerId"));
				customerBlacklist.setUserBoId(rs.getInt("userBoId"));
				customerBlacklist.setCreateDate(rs.getString("createDate"));
				customerBlacklist.setBlackType(rs.getInt("blackType"));
				customerBlacklist.setModDate(rs.getString("modDate"));
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return customerBlacklist;
	}
	
	/**
	 * 根据购买记录将私自拆卡的黑名单设置为建议取消黑名单
	 * 只有在createDate之后购买了主机才建议取消黑名单
	 * @return 建议取消的记录数
	 */
	public int suggestCancle1(){
		String sql="update customer_blacklist as b,order_product as op,`order` as o,product as p";
		sql+=" set b.isSuggestCancle=2,b.sugCancleDate=current_date";
		sql+=" where b.isSuggestCancle NOT IN (2) and b.blackType=1 and b.flag=2 and b.customerId=op.customerId and o.orderNumber=op.orderNumber and op.productId=p.productId";
		sql+=" and p.productTypeId=3 and op.orderDate>=DATE_FORMAT(b.modDate,'%Y-%m-%d') and o.isReturn!=2 and o.invalid=2";
		return db.executeUpdate(sql);
	}
	
	/**
	 * 根据购买记录将未缴160电子申报的黑名单设置为建议取消黑名单
	 * 只有在createDate之后购买了电子申报才建议取消黑名单
	 * @return 建议取消的记录数
	 */
	public int suggestCancle3(){
		String sql="update customer_blacklist as b,order_product as op,`order` as o";
		sql+=" set b.isSuggestCancle=2,b.sugCancleDate=current_date";
		sql+=" where b.isSuggestCancle NOT IN (2) and b.blackType=3 and b.flag=2 and b.customerId=op.customerId and o.orderNumber=op.orderNumber";
		sql+=" and op.productId="+cn.qtone.modules.Const.dzsbId+" and o.orderDate>=DATE_FORMAT(b.modDate,'%Y-%m-%d') and o.isReturn!=2 and o.invalid=2";
		sql+=" AND o.orderDate>='2013-8-1'";
		return db.executeUpdate(sql);
	}
	
	/**
	 * 根据维护费跟进记录将未缴336的黑名单设置为建议取消黑名单
	 * 只有在createDate之后传真了汇款单
	 * @return 建议取消的记录数
	 */
	public int suggestCancle4(){
		String sql="update customer_blacklist as b,`follow_whf` as fw";
		sql+=" set b.isSuggestCancle=2,b.sugCancleDate=current_date";
		sql+=" where b.isSuggestCancle NOT IN (2) and b.blackType=2 AND fw.invalid!=2 and b.flag=2 and b.customerId=fw.customerId";
		sql+=" and (fw.sendHKDDate>=DATE_FORMAT(b.modDate,'%Y-%m-%d') OR b.modDate is NULL)";
		return db.executeUpdate(sql);
	}
	
	/**
	 * 根据购买记录将未缴336的黑名单设置为建议取消黑名单
	 * 只有在createDate之后购买了336才建议取消黑名单
	 * @return 建议取消的记录数
	 */
	public int suggestCancle2(){
		int cancleNUM=0;
		String sql="update customer_blacklist as b,`order` as o";
		sql+=" set b.isSuggestCancle=2,b.sugCancleDate=current_date";
		sql+=" where b.isSuggestCancle NOT IN (2) and b.blackType=2 and b.flag=2 and b.customerId=o.customerId";
		sql+=" and o.buyType=2 and (o.orderDate>=DATE_FORMAT(b.modDate,'%Y-%m-%d') or b.modDate is NULL) and o.isReturn!=2 and o.invalid=2";
		cancleNUM=db.executeUpdate(sql);
		
		//有当年12月31日保修期或次年后12月31日保修期的需提示取消黑名单
		int curYear=DateClass.getYear("");
		sql="update customer_blacklist as b,`order` as o ,order_product AS op";
		sql+=" set b.isSuggestCancle=2,b.sugCancleDate=current_date";
		sql+=" where b.isSuggestCancle NOT IN (2) and b.blackType=2 and b.flag=2 and b.customerId=o.customerId";
		sql+=" AND o.orderNumber=op.orderNumber AND op.productId="+cn.qtone.modules.Const.whfId;
		sql+=" and o.buyType=2 and (op.warrantyDate='"+curYear+"-12-31' or op.warrantyDate='"+curYear+1+"-12-31') and o.isReturn!=2 and o.invalid=2";
		cancleNUM+=db.executeUpdate(sql);
		return cancleNUM;
	}
	
	/**
	 * 336黑名单企业，若设黑名单后再设置企业注销--国税确认或国税报表确认，即系统将自动取消黑名单变白名单处理
	 */
	public void cancle1(){
		String sql="select c.customerId from customer_blacklist as b,customer as c";
		sql+=" where b.customerId=c.customerId and  b.blackType=2 and c.isCancle=2";
		sql+=" and c.customerCancleType in (2,3) and";
//		sql+=" (c.cancleCusSetDate>=DATE_FORMAT(b.createDate,'%Y-%m-%d') OR c.cancleCusSetDate IS NULL) and b.flag!=1 limit 1000";
		//邵雪芬2014-9-4提出不用限制设置注销的日期
		sql+=" b.flag!=1 limit 1000";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerBlacklist customerBlacklist=new CustomerBlacklist();
				customerBlacklist.setCustomerId(rs.getInt("c.customerId"));
				customerBlacklist.setComment("336黑名单企业，若设黑名单后再设置企业注销--国税确认或国税报表确认，即系统将自动取消黑名单变白名单处理");
				customerBlacklist.setUserBoId(207);
				this.delete(customerBlacklist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
	}
	
	/**
	 * 336黑名单企业，若设黑名单后再设置两卡注销--国税确认，即系统将自动取消黑名单变白名单处理
	 */
	public void cancle2(){
		String sql="select c.customerId from customer_blacklist as b,customer as c";
		sql+=" where b.customerId=c.customerId and  b.blackType=2 and c.isInstallCard=3";
		sql+=" and c.cardCancleType in (2) and";
		sql+=" (c.cancleSetDate>=DATE_FORMAT(b.createDate,'%Y-%m-%d') OR c.cancleSetDate IS NULL) and b.flag!=1 limit 1000";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerBlacklist customerBlacklist=new CustomerBlacklist();
				customerBlacklist.setCustomerId(rs.getInt("c.customerId"));
				customerBlacklist.setComment("336黑名单企业，若设黑名单后再设置两卡注销--国税确认，即系统将自动取消黑名单变白名单处理");
				customerBlacklist.setUserBoId(207);
				this.delete(customerBlacklist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
	}
	
	public void updateFromTmp(){
		String sql="SELECT c.customerId,t.taxNO FROM tmp_cancle_customer AS t,customer AS c WHERE t.taxNO=c.taxNO AND t.msg='需设置为黑名单'";
		ResultSet rs=null;
		int i=0;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerBlacklist customerBlacklist=new CustomerBlacklist();
				customerBlacklist.setCustomerId(rs.getInt("c.customerId"));
				customerBlacklist.setComment("");
				customerBlacklist.setBlackType(2);
				customerBlacklist.setUserBoId(1);
				if(this.isExist(customerBlacklist.getCustomerId())>=1){
					this.update(customerBlacklist);
				}else{
					this.insert(customerBlacklist);
				}
				i++;
				db.execute("update tmp_cancle_customer set isOK=2 where taxNO='"+rs.getString("t.taxNO")+"'");
				System.out.println("更新了"+i+"条");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
	}
	
	/**
	 * 自动设置未交336维护费的企业为黑名单
	 * @param year 未交的年份
	 * @return
	 */
	public int autoSet336Black(int year){
//		String sql="insert into customer_blacklist (customerId,blackType,createDate,flag)";
//		sql+=" SELECT DISTINCT fwc.customerId,2,current_date,2 FROM follow_whf_customer AS fwc ";
//		sql+=" LEFT JOIN order_product_rebuy AS opr ON fwc.customerId=opr.customerId AND opr.productId = '"+cn.qtone.modules.Const.whfId+"' AND opr.isStop=1"; 
//		sql+=" LEFT JOIN follow_whf_year AS fwy ON fwy.customerId=fwc.customerId AND fwy.invalid=1 AND fwy.payYear="+year;
//		sql+=" LEFT JOIN follow_whf AS fw ON fw.followWHFId=fwy.followWHFId AND fw.invalid=1 AND fw.isReceive!=3"; 
//		sql+=" LEFT JOIN customer_blacklist AS cbl ON fwc.customerId=cbl.customerId AND cbl.flag=2 AND cbl.blackType=2";
//		sql+=" ,customer AS c ";
//		sql+=" WHERE c.customerId=fwc.customerId AND fwc.isAgentPay!=2 AND fw.followWHFId IS NULL AND c.isInstallCard=2";
//		sql+=" AND c.installCardDate<'"+year+1+"-1-1' AND cbl.customerBlacklistId IS NULL";
		
		String sql="insert into customer_blacklist (customerId,blackType,createDate,flag,modDate,comment,userBoId)";
		sql+=" SELECT DISTINCT fwc.customerId,2,current_date,2,current_date,'未交维护费，系统自动设为黑名单',207 FROM follow_whf_customer AS fwc ";
		sql+=" LEFT JOIN order_product AS op ON fwc.customerId=op.customerId AND op.productId = "+cn.qtone.modules.Const.whfId+" and op.warrantyDate='"+year+"-12-31' AND op.amount>0";
		sql+=" LEFT JOIN `order` AS o ON o.orderNumber=op.orderNumber AND o.isReturn!=2 and o.invalid!=1 and o.isReceive=2";
		sql+=" LEFT JOIN order_return AS r on r.orderNumberReturn=op.orderNumber ";
		sql+=" LEFT JOIN follow_whf_year AS fwy ON fwy.customerId=fwc.customerId AND fwy.invalid=1 AND fwy.payYear="+year;
		sql+=" LEFT JOIN follow_whf AS fw ON fw.followWHFId=fwy.followWHFId AND fw.invalid=1 AND fw.isReceive!=3 ";
		sql+=" LEFT JOIN customer_blacklist AS cbl ON fwc.customerId=cbl.customerId AND cbl.blackType=2,customer AS c ";
		sql+=" WHERE c.customerId=fwc.customerId AND fwc.isAgentPay!=2 AND o.orderId IS NULL and r.orderNumberReturn is null  AND fw.followWHFId IS NULL";
		//已装卡，待注销，注销（客户提出）的才要设黑名单
		sql+=" AND (c.isInstallCard in (2,4) or (c.isInstallCard=3 and c.cardCancleType=1))";
		//已注销且国税确认或国税报表确认的不用设为黑名单
		sql+=" AND NOT (c.isCancle=2 AND  c.customerCancleType in (2,3))";
		sql+=" AND (c.installCardDate<'"+year+"-12-1' OR c.installCardDate IS NULL) AND cbl.customerBlacklistId IS NULL ";
		db.executeUpdate(sql);
		
		//已取消的黑名单，设为未取消
		sql=" UPDATE follow_whf_customer AS fwc ";
		sql+=" LEFT JOIN order_product AS op ON fwc.customerId=op.customerId AND op.productId = "+cn.qtone.modules.Const.whfId+" and op.warrantyDate>='"+year+"-12-31' AND op.amount>0";
		sql+=" LEFT JOIN `order` AS o ON o.orderNumber=op.orderNumber AND o.isReturn!=2 and o.invalid!=1 and o.isReceive=2";
		sql+=" LEFT JOIN order_return AS r on r.orderNumberReturn=op.orderNumber ";
		sql+=" LEFT JOIN follow_whf_year AS fwy ON fwy.customerId=fwc.customerId AND fwy.invalid=1 AND fwy.payYear="+year;
		sql+=" LEFT JOIN follow_whf AS fw ON fw.followWHFId=fwy.followWHFId AND fw.invalid=1 AND fw.isReceive!=3 ";
		sql+=" LEFT JOIN customer_blacklist AS cbl ON fwc.customerId=cbl.customerId AND cbl.flag=1 AND cbl.blackType=2,customer AS c ";
		sql+=" SET cbl.userBoId=207,cbl.isSuggestCancle=1,cbl.flag=2,cbl.`comment`='未交维护费，系统自动从取消黑名单状态恢复为黑名单',modDate=current_date";
		sql+=" WHERE c.customerId=fwc.customerId AND fwc.isAgentPay!=2 AND o.orderId IS NULL and r.orderNumberReturn is null  AND fw.followWHFId IS NULL";
		//已装卡，待注销，注销（客户提出）的才要设黑名单
		sql+=" AND (c.isInstallCard in (2,4) or (c.isInstallCard=3 and c.cardCancleType=1))";
		//已注销且国税确认或国税报表确认的不用设为黑名单
		sql+=" AND NOT (c.isCancle=2 AND  c.customerCancleType in (2,3))";		
		sql+=" AND (c.installCardDate<'"+year+"-12-1' OR c.installCardDate IS NULL)";
		return db.executeUpdate(sql);		
	}
	
	public static void main(String[] args){
		String root="E:\\java\\qt_tax_v2\\WebRoot";
		cn.qtone.sys.Init init=new cn.qtone.sys.Init();
		init.init(root, null);
		
		CustomerBlacklistHandle handle=new CustomerBlacklistHandle();
//		System.out.println(handle.suggestCancle2());
		System.out.println(handle.autoSet336Black(2014));
	}	
}
