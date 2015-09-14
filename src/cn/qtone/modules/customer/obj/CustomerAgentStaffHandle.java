package cn.qtone.modules.customer.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.*;

public class CustomerAgentStaffHandle extends BaseHandle{

	public CustomerAgentStaffHandle(){
		super();
	}
	
	/**
	 * 根据员工id得到代理记账公司员工对象
	 * @param customerAgentStaffId
	 * @return
	 */
	public CustomerAgentStaff getCustomerAgentStaff(int customerAgentStaffId){
		if(customerAgentStaffId==0) return null;
		CustomerAgentStaff customerAgentStaff=new CustomerAgentStaff();
		String sql="SELECT * FROM customer_agent_staff WHERE customerAgentStaffId="+customerAgentStaffId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				customerAgentStaff.setCustomerId(rs.getInt("customerId"));
				customerAgentStaff.setCustomerAgentStaffId(rs.getInt("customerAgentStaffId"));
				customerAgentStaff.setStaffName(rs.getString("staffName"));
				customerAgentStaff.setTel(rs.getString("tel"));
				return customerAgentStaff;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	/**
	 * 搜索代理记账人员
	 * @param customerId 代理公司id
	 * @return
	 */
	public List search(int customerId){
		List agentStaffList=new ArrayList();
		String sql="SELECT * FROM customer_agent_staff WHERE customerId="+customerId+" ORDER BY customerAgentStaffId DESC LIMIT 100";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerAgentStaff agentStaff=new CustomerAgentStaff();
				agentStaff.setCustomerId(rs.getInt("customerId"));
				agentStaff.setCustomerAgentStaffId(rs.getInt("customerAgentStaffId"));
				agentStaff.setStaffName(rs.getString("staffName"));
				agentStaff.setTel(rs.getString("tel"));
				agentStaffList.add(agentStaff);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return agentStaffList;
	}
	
	/**
	 * 添加记账人员
	 * @param staff
	 * @return
	 */
	public boolean add(CustomerAgentStaff staff){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("INSERT INTO customer_agent_staff SET customerId=").append(staff.getCustomerId());
		sqlBuf.append(",staffName='").append(db.filterStr(staff.getStaffName())).append("'");
		sqlBuf.append(",tel='").append(db.filterStr(staff.getTel())).append("'");
		sqlBuf.append(",createDate=CURRENT_TIMESTAMP");
		return db.execute(sqlBuf.toString());
	}
	
	/**
	 * 修改记账人员
	 * @param staff
	 * @return
	 */
	public boolean edit(CustomerAgentStaff staff){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("UPDATE customer_agent_staff SET customerId=").append(staff.getCustomerId());
		sqlBuf.append(",staffName='").append(db.filterStr(staff.getStaffName())).append("'");
		sqlBuf.append(",tel='").append(db.filterStr(staff.getTel())).append("'");
		sqlBuf.append(" WHERE customerAgentStaffId=").append(staff.getCustomerAgentStaffId());
		return db.execute(sqlBuf.toString());
	}	
	
	public int update(List agentStaffList){
		int successNUM=0;
		for(int i=0;i<agentStaffList.size();i++){
			CustomerAgentStaff staff=(CustomerAgentStaff)agentStaffList.get(i);
			if(staff.getCustomerAgentStaffId()<=0){
				if(this.add(staff)){
					successNUM++;
				}
			}else{
				if(this.edit(staff)){
					successNUM++;
				}			
			}
		}
		return successNUM;
	}
	
	/**
	 * 获取记账人员对象
	 * 因为一般是刚写入完就要读取，所以使用主数据库
	 * @param customerAgentId
	 * @param staffName
	 * @return
	 */
	public CustomerAgentStaff getCustomerAgentStaff(int customerId,String staffName){
		if(customerId==0) return null;
		if(StringFunction.isEmpty(staffName)){
			return null;
		}
		
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("SELECT * FROM customer_agent_staff WHERE customerId=").append(customerId);
		sqlBuf.append(" AND staffName='").append(db.filterStr(staffName)).append("'");	
		sqlBuf.append(" ORDER BY customerAgentStaffId DESC LIMIT 1");
		ResultSet rs=null;
		try {
			rs=db.selectMain(sqlBuf.toString());
			if(rs.next()){
				CustomerAgentStaff customerAgentStaff=new CustomerAgentStaff();
				customerAgentStaff.setCustomerId(customerId);
				customerAgentStaff.setCustomerAgentStaffId(rs.getInt("customerAgentStaffId"));
				customerAgentStaff.setStaffName(rs.getString("staffName"));
				customerAgentStaff.setTel(rs.getString("tel"));
				return customerAgentStaff;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	public boolean delByCustomerId(int customerId){
		String sql="DELETE FROM customer_agent_staff WHERE customerId="+customerId;
		return db.execute(sql);
	}
	
	public boolean delByIds(String customerAgentStaffIds){
		String sql="DELETE FROM customer_agent_staff WHERE customerAgentStaffId IN ("+customerAgentStaffIds+")";
		return db.execute(sql);		
	}
}
