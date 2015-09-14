package cn.qtone.modules.customer.obj.owe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.modules.tel.obj.TelHandle;
import cn.qtone.sys.base.BaseHandle;

public class CustomerOweVisitHandle extends BaseHandle{

		private TelHandle telHandle;
		public CustomerOweVisitHandle(){
			super();
			telHandle=new TelHandle();
		}
		
		public CustomerOweVisit getLastVisit(int customerId){
			String sql="SELECT * FROM customer_owe_visit WHERE customerId="+customerId;
			sql+=" ORDER BY visitDate DESC limit 1";
			ResultSet rs=null;
			try {
				rs=db.select(sql);
				if(rs.next()){
					CustomerOweVisit customerOweVisit=this.getCustomerOweVisitByRs(rs);
					return customerOweVisit;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			}finally{
				db.closeResultSet(rs);
			}
			return null;
		}
		
		private CustomerOweVisit getCustomerOweVisitByRs(ResultSet rs) throws SQLException{
			
			CustomerOweVisit customerOweVisit=new CustomerOweVisit();
			customerOweVisit.setCustomerId(rs.getInt("customerId"));
			customerOweVisit.setTelId(rs.getInt("telId"));
//			customerOweVisit.setTelPath(rs.getString("telPath"));
			customerOweVisit.setVisitDate(rs.getString("visitDate"));
			customerOweVisit.setVisitRemark(rs.getString("visitRemark"));
			customerOweVisit.setVisitUserBoId(rs.getInt("visitUserBoId"));
			customerOweVisit.setLinkMan(rs.getString("linkMan"));
			customerOweVisit.setTelNo(rs.getString("telNo"));
			customerOweVisit.setCustomerOweVisitId(rs.getInt("customerOweVisitId"));
			if(customerOweVisit.getTelId()>0){
				customerOweVisit.setTelPath(telHandle.getPathById(customerOweVisit.getTelId()));
			}
			
			return customerOweVisit;
		}
		
		/**
		 * 添加跟进情况回访 
		 * @return
		 */
		public boolean addVisitRemark(CustomerOweVisit customerOweVisit){
			String sql="INSERT INTO customer_owe_visit SET customerId="+customerOweVisit.getCustomerId()+",visitRemark='"+db.filterStr(customerOweVisit.getVisitRemark())+"'" 
			+",linkMan='"+db.filterStr(customerOweVisit.getLinkMan())+"',telNo='"+db.filterStr(customerOweVisit.getTelNo())+"'"      
			+",telId="+customerOweVisit.getTelId()+",visitUserBoId="+customerOweVisit.getVisitUserBoId()+",visitDate=CURRENT_TIMESTAMP";
//			System.out.println(sql);
			return db.execute(sql);
		}
		/**
		 * 修改跟进情况回访 
		 * @return
		 */
		public boolean editVisitRemark(CustomerOweVisit customerOweVisit){
			String sql="UPDATE customer_owe_visit SET customerId="+customerOweVisit.getCustomerId()+",visitRemark='"+db.filterStr(customerOweVisit.getVisitRemark())+"'" 
			+",linkMan='"+db.filterStr(customerOweVisit.getLinkMan())+"',telNo='"+db.filterStr(customerOweVisit.getTelNo())+"'"      
			+",telId="+customerOweVisit.getTelId()+",visitUserBoId="+customerOweVisit.getVisitUserBoId()+",visitDate=CURRENT_TIMESTAMP"+
			" where customerOweVisitId="+customerOweVisit.getCustomerOweVisitId();
//			System.out.println(sql);
			return db.execute(sql);
		}
		
		public List search(int customerId){
			List customerOweVisitList=new ArrayList();
			String sql="SELECT * FROM customer_owe_visit WHERE customerId="+customerId;
			sql+=" ORDER BY customerOweVisitId DESC LIMIT 50";
			ResultSet rs=null;
			try {
				rs=db.select(sql);
				while(rs.next()){
					CustomerOweVisit customerOweVisit=this.getCustomerOweVisitByRs(rs);
					customerOweVisitList.add(customerOweVisit);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			} finally{
				db.closeResultSet(rs);
			}
			return customerOweVisitList;
		}
		
		public CustomerOweVisit getCustomerOweVisitById(int customerOweVisitId){
			String sql="SELECT * FROM customer_owe_visit WHERE customerOweVisitId="+customerOweVisitId;
			ResultSet rs=null;
			try {
				rs=db.select(sql);
				if(rs.next()){
					CustomerOweVisit customerOweVisit=this.getCustomerOweVisitByRs(rs);
					return customerOweVisit;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			}finally{
				db.closeResultSet(rs);
			}
			return null;
		}
		
}
