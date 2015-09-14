package cn.qtone.modules.customer.obj.owe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.DateClass;
import cn.qtone.utils.StringFunction;
import cn.qtone.modules.customer.obj.*;
import cn.qtone.modules.tel.obj.TelHandle;

public class CustomerOweHandle extends BaseHandle{

	public CustomerOweHandle(){
		
		super();
	}
	
	/**
	 * 判断企业是否欠款
	 * @param customerId
	 * @return
	 */
	public boolean isOwe(int customerId){
		String sql="SELECT count(*) from `order` WHERE customerId="+customerId;
		sql+=" AND isReceive IN(1,3,6) AND payType!=1 AND buyType NOT IN (12,9,8,7) AND cost>0 AND orderDate>='2010-1-1' AND isReturn=1";
		sql+=" AND invalid!=1";
		return db.countRowMain(sql)>0?true:false;
	}
	
	/**
	 * 更新欠费企业的跟进人
	 * @param customerId 欠费企业id
	 * @param follower 跟进人
	 * @return
	 */
	public boolean update(int customerId,int follower){
		String sql="SELECT customerOweFollowerId FROM customer_owe_follower WHERE customerId="+customerId;
		//sql+=" AND follower="+follower;
		int customerOweFollowerId=db.countRowMain(sql);

		if(customerOweFollowerId==0){
			sql="INSERT INTO customer_owe_follower SET customerId="+customerId+",follower="+follower;
		}else{
			sql="UPDATE customer_owe_follower SET follower="+follower+" WHERE customerOweFollowerId="+customerOweFollowerId;
		}
		return db.execute(sql);
	}
	
	private String sqlWhere(SearchOweParam param){
		StringBuffer sqlBuf=new StringBuffer();
		
		sqlBuf.append(" WHERE o.invalid!=1 AND o.isReceive IN(1,3,6) AND o.buyType NOT IN (12,9,8,7) AND o.payType!=1 AND o.cost>0 AND o.orderDate>='2010-1-1' AND o.isReturn=1");

		if(param.getIsPayByCheck()==1){
			sqlBuf.append(" AND o.payType!=4");
		}else if(param.getIsPayByCheck()==2){
			sqlBuf.append(" AND o.payType=4");			
		}
		if(!StringFunction.isEmpty(param.getBuyTypes())){
			sqlBuf.append(" AND o.buyType IN (").append(param.getBuyTypes()).append(")");
		}
		if(param.getHasFollower()==2){
			sqlBuf.append(" AND f.follower>0");
		}else if(param.getHasFollower()==1){
			sqlBuf.append(" AND f.follower<=0");			
		}
		if(!StringFunction.isEmpty(param.getCompanyName())){
			sqlBuf.append(" AND c.companyName LIKE BINARY '%"+param.getCompanyName()+"%'");				
		}
		if(!StringFunction.isEmpty(param.getTaxNO())){
			sqlBuf.append(" AND c.taxNO like '%"+param.getTaxNO()+"%'");
		}
		if(!StringFunction.isEmpty(param.getCityIds())){
			sqlBuf.append(" AND c.cityId in ("+param.getCityIds()+")");
		}		
		if(param.getFollower()>0){
			sqlBuf.append(" AND f.follower=").append(param.getFollower());
		}
		if(!StringFunction.isEmpty(param.getOrderDateEnd())){
			sqlBuf.append(" AND o.orderDate< DATE_ADD('"+param.getOrderDateEnd()+"',INTERVAL 1 day)");
		}
		if(!StringFunction.isEmpty(param.getOrderDateStart())){
			sqlBuf.append(" AND o.orderDate>= '"+param.getOrderDateStart()+"'");
		}
		if(!StringFunction.isEmpty(param.getNextFollowDateEnd())){
			sqlBuf.append(" AND f.nextFollowDate< DATE_ADD('"+param.getNextFollowDateEnd()+"',INTERVAL 1 day)");
		}
		if(!StringFunction.isEmpty(param.getNextFollowDateStart())){
			sqlBuf.append(" AND f.nextFollowDate>= '"+param.getNextFollowDateStart()+"'");
		}
		if(param.getIsSpecial()>0){
			sqlBuf.append(" AND f.isSpecial=").append(param.getIsSpecial());
		}
		if(param.getCustomerId()>0){
			sqlBuf.append(" AND c.customerId=").append(param.getCustomerId());
		} 		
		if(param.getIsFollowRemark()==1){
			sqlBuf.append(" AND ( f.followRemark IS NULL OR f.followRemark ='' ) ");
		}else  if(param.getIsFollowRemark()==2){
			sqlBuf.append(" AND f.followRemark !='' " );
		}
		 
		if(param.getVisitFlag()==1 ){
			sqlBuf.append(" AND f.visitFlag =1 ") ;
		}else if(param.getVisitFlag()==2 ){
			sqlBuf.append(" AND f.visitFlag =").append(param.getVisitFlag()) ;
		}
		
		if(param.getIsNeedVisit()==1 ){
			sqlBuf.append(" AND f.isNeedVisit <=1 ") ;
		}else if(param.getIsNeedVisit()==2 ){
			sqlBuf.append(" AND f.isNeedVisit =").append(param.getIsNeedVisit()) ;
		}
		if(param.getIsVisit()==1){
			sqlBuf.append(" AND ( f.visitFlag <=0  OR f.visitFlag  IS NULL )");
		}else if(param.getIsVisit()==2){
			sqlBuf.append(" AND   f.visitFlag >=1   ");
		}
		
		return sqlBuf.toString();
	}
	
	public int totalRow(SearchOweParam param){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("SELECT COUNT(DISTINCT c.customerId) ");
		sqlBuf.append(" FROM `order` AS o ");
//		if(!StringFunction.isEmpty(param.getCityIds())||!StringFunction.isEmpty(param.getCompanyName())||!StringFunction.isEmpty(param.getTaxNO())){
			sqlBuf.append(" LEFT JOIN customer AS c ON o.customerId=c.customerId");
//		}
		sqlBuf.append(" LEFT JOIN customer_owe_follower AS f ON f.customerId=o.customerId");
		sqlBuf.append(this.sqlWhere(param));
		return db.countRow(sqlBuf.toString());
	}
	
	/**
	 * 搜索欠费企业
	 * @return
	 */
	public List search(SearchOweParam param){
		List customerOweList=new ArrayList();
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("SELECT c.companyName,c.taxNo,c.businessAddress,c.tel,c.linkman,o.saleman,o.linkman,o.tel,o.address,o.customerId,SUM(o.cost) AS cost,f.*,MIN(o.orderDate) AS  orderDate ");
		sqlBuf.append(" FROM `order` AS o ");
//		if(!StringFunction.isEmpty(param.getCityIds())||!StringFunction.isEmpty(param.getCompanyName())||!StringFunction.isEmpty(param.getTaxNO())){
			sqlBuf.append(" LEFT JOIN customer AS c ON o.customerId=c.customerId");
//		}
		sqlBuf.append(" LEFT JOIN customer_owe_follower AS f ON f.customerId=o.customerId");
		sqlBuf.append(this.sqlWhere(param));
		if(StringFunction.isEmpty(param.getGroupBy())){
			sqlBuf.append(" GROUP BY o.customerId ");
		}else{
			sqlBuf.append(param.getGroupBy());
		}
		sqlBuf.append(" ORDER BY  o.orderDate ASC ");
		ResultSet rs=null;
		CustomerOweVisitHandle customerOweVisitHandle=new CustomerOweVisitHandle();
		try {
			rs=db.select(sqlBuf.toString());
			while(rs.next()){
				CustomerOwe customerOwe=new CustomerOwe();
				Customer customer=new Customer();
				customer.setCompanyName(rs.getString("c.companyName"));
				customer.setTaxNO(rs.getString("c.taxNO"));
				customer.setCustomerId(rs.getInt("o.customerId"));
				customer.setTel(rs.getString("c.tel"));
				customer.setLinkman(rs.getString("c.linkman"));
				customer.setBusinessAddress(rs.getString("c.businessAddress"));
				customerOwe.setCustomer(customer);
				
				customerOwe.setCustomerId(rs.getInt("o.customerId"));
				customerOwe.setTotalCost(rs.getFloat("cost"));
				customerOwe.setTotalPayCost(this.getPayCost(customer.getCustomerId(), param));
				customerOwe.setFollower(rs.getInt("f.follower"));
				customerOwe.setAddress(rs.getString("o.address"));
				customerOwe.setLinkman(rs.getString("o.linkman"));
				customerOwe.setTel(rs.getString("o.tel"));
				customerOwe.setSaleman(rs.getInt("o.saleman"));
				customerOwe.setOrderDate(rs.getString("orderDate")); 
				
				CustomerOweFollower customerOweFollower=new CustomerOweFollower();
				customerOweFollower.setIsNeedVisit(rs.getInt("f.isNeedVisit"));
				customerOweFollower.setVisitFlag(rs.getInt("f.visitFlag"));
				customerOweFollower.setFollowRemark(rs.getString("f.followRemark"));
				customerOweFollower.setNextFollowDate(rs.getString("f.nextFollowDate"));
				customerOweFollower.setIsSpecial(rs.getInt("f.isSpecial"));
				customerOweFollower.setCustomerId(customerOwe.getCustomerId());
				customerOwe.setCustomerOweFollower(customerOweFollower);
//				customerOwe.setVisitRemark(rs.getString("f.visitRemark"));
				
				CustomerOweVisit lastCustomerOweVisit=customerOweVisitHandle.getLastVisit(customerOwe.getCustomerId());
				
				//列表中不显示小于于欠费日期的“跟进情况”及“跟进情况回访”
				if(lastCustomerOweVisit!=null&&DateClass.CompareTwoDate(lastCustomerOweVisit.getVisitDate(), customerOwe.getOrderDate(), "")==1){
					customerOwe.setLastCustomerOweVisit(lastCustomerOweVisit);					
				}
				
				if(customerOwe.getTotalCost()==customerOwe.getTotalPayCost()){
					continue;
				}
				if(StringFunction.isEmpty(customerOwe.getOrderDate())){
					customerOwe.setOweDay(0);
				}else{
					customerOwe.setOweDay(DateClass.getTowDateDays(customerOwe.getOrderDate(), DateClass.GetSysDate()));
				}
//				if(customerOwe.getTelId()>0){
//					customerOwe.setTelPath(telHandle.getPathById(customerOwe.getTelId()));
//				}
				customerOweList.add(customerOwe);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return customerOweList;
	}
	
	/**
	 * 计算企业已付金额
	 * @param customerId
	 * @param param
	 * @return
	 */
	private float getPayCost(int customerId,SearchOweParam param){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("SELECT SUM(op.payCost) AS payCost FROM order_pay AS op,`order` AS o WHERE op.orderNumber=o.orderNumber AND o.customerId=").append(customerId);
		sqlBuf.append(" AND o.isReceive IN(1,3,6) AND o.buyType NOT IN (12,9,8,7) AND o.payType!=1 AND o.cost>0 AND o.orderDate>='2010-1-1' AND o.isReturn=1");	
		
		sqlBuf.append(" AND op.isReceive=2");
		if(!StringFunction.isEmpty(param.getOrderDateEnd())){
			sqlBuf.append(" AND o.orderDate< DATE_ADD('"+param.getOrderDateEnd()+"',INTERVAL 1 day)");
		}
		if(!StringFunction.isEmpty(param.getOrderDateStart())){
			sqlBuf.append(" AND o.orderDate>= '"+param.getOrderDateStart()+"'");
		}
		if(!StringFunction.isEmpty(param.getBuyTypes())){
			sqlBuf.append(" AND o.buyType IN (").append(param.getBuyTypes()).append(")");
		}
		return db.countRow(sqlBuf.toString());
	}
	
	
	
	
}
