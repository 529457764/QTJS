package cn.qtone.modules.customer.obj.owe;

import java.sql.*;
import java.util.*;

import cn.qtone.sys.base.BaseHandle;

public class CustomerOweFollowerHandle extends BaseHandle{

	public CustomerOweFollowerHandle(){
		super();
		
	}
	
	/**
	 * 更新没有下次跟进记录的欠费企业，每天定时运行
	 * @return
	 */
	public boolean updateNextFollowDate(){
		String sql="UPDATE customer_owe_follower AS f";  
		sql+=" LEFT JOIN `order` AS o ON f.customerId=o.customerId";
		sql+=" set f.nextFollowDate=Date_ADD(o.orderDate,INTERVAL 30 day)";
		sql+=" WHERE f.nextFollowDate is null AND o.invalid!=1 AND o.isReceive IN(1,3,6) AND o.buyType<7 AND o.payType!=1 AND o.cost>0 AND o.orderDate>='2010-1-1' AND o.isReturn=1 AND o.payType!=4";
		return db.execute(sql);
	}

	/**
	 * 更新业务收款过期日期，每天定时运行
	 * @return
	 */
	public boolean updateExpireDate(){
		String sql="UPDATE customer_owe_follower AS f";  
		sql+=" LEFT JOIN `order` AS o ON f.customerId=o.customerId";
		sql+=" set f.expireDate=Date_ADD(o.orderDate,INTERVAL 30 day)";
		sql+=" WHERE f.expireDate is null AND o.invalid!=1 AND o.isReceive IN(1,3,6) AND o.buyType<7 AND o.payType!=1 AND o.cost>0 AND o.orderDate>='2010-1-1' AND o.isReturn=1 AND o.payType!=4";
		return db.execute(sql);
	}
	
	public List search(){
		List followerList=new ArrayList();
		String sql="SELECT * FROM customer_owe_follower";
		ResultSet rs=null;
		try {
			rs=db.selectMain(sql);
			while(rs.next()){
				CustomerOweFollower follower=new CustomerOweFollower();
				follower.setCustomerId(rs.getInt("customerId"));
				follower.setCustomerOweFollowerId(rs.getInt("customerOweFollowerId"));
				follower.setFollower(rs.getInt("follower"));
				follower.setFollowRemark(rs.getString("followRemark"));
				follower.setIsNeedVisit(rs.getInt("isNeedVisit"));
				follower.setVisitFlag(rs.getInt("visitFlag"));
//				follower.setVisitRemark(rs.getString("visitRemark"));
//				follower.setVisitUserBoId(rs.getInt("visitUserBoId"));
//				follower.setVisitDate(rs.getString("visitDate"));
				 
				followerList.add(follower);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
		}
		return followerList;
	}
	
	public boolean del(int customerOweFollowerId){
		String sql="DELETE FROM customer_owe_follower WHERE customerOweFollowerId="+customerOweFollowerId;
		return db.execute(sql);
	}
	
	
	/**
	 * 是否需要回访
	 * @param isNeedVisit
	 * @param customerId
	 * @return
	 */
	public boolean setIsNeedVisit(int isNeedVisit,int customerId){
		String sql="UPDATE customer_owe_follower SET isNeedVisit="+isNeedVisit+" WHERE customerId="+customerId;
		return db.execute(sql);
	}
	 
	/**
	 * 跟进情况
	 * @param customerd
	 * @param followRemark
	 * @return
	 */
	public boolean updateFollowRemark(int customerId,String followRemark){
		String sql="UPDATE customer_owe_follower SET followRemark='"+db.filterStr(followRemark)+"',followDate=CURRENT_TIMESTAMP  WHERE customerId="+customerId;;
		return db.execute(sql);
	}
	
/**
 * 更新跟进情况的附加信息
 * @param isSpecial 是否特殊情况 1否 2是
 * @param visitFlag 跟进情况回访 1跟进情况不真实 2跟进情况属实
 * @param nextFollowDate 下次跟进日期
 * @param customerId
 * @return
 */
	public boolean updateFollow(int isSpecial,int visitFlag,String nextFollowDate,int customerId){
		String sql="UPDATE customer_owe_follower SET isSpecial='"+isSpecial+"',visitFlag="+visitFlag+",nextFollowDate='"+nextFollowDate+"'  WHERE customerId="+customerId;
		return db.execute(sql);
	}
	

	
}
