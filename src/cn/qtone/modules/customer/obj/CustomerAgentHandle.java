package cn.qtone.modules.customer.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;

public class CustomerAgentHandle extends BaseHandle{
	
	public CustomerAgentHandle(){
		super();
	}
	
	/**
	 * 添加代理记账公司
	 * @param customerAgent
	 * @return
	 */
	public boolean add(CustomerAgent customerAgent){
		String sql="INSERT INTO customer_agent SET agentName='"+db.filterStr(customerAgent.getAgentName())+"'";
		return db.execute(sql);
	}
	
	/**
	 * 根据代理公司名称获取代理公司对象，
	 * 因一般是添加完后马上调用，所以选主数据库
	 * @param agentName
	 * @return
	 */
	public CustomerAgent getCustomerAgent(String agentName){
		String sql="SELECT * FROM customer_agent WHERE agentName='"+db.filterStr(agentName)+"' ORDER BY customerAgentId DESC LIMIT 1";
		ResultSet rs=null;
		try {
			rs=db.selectMain(sql);
			if(rs.next()){
				CustomerAgent customerAgent=new CustomerAgent();
				customerAgent.setAgentName(rs.getString("agentName"));
				customerAgent.setCustomerAgentId(rs.getInt("customerAgentId"));
				return customerAgent;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	/**
	 * 根据id获取代理记账公司对象
	 * @param customerAgentId
	 * @return
	 */
	public CustomerAgent getCustomerAgent(int customerAgentId){
		if(customerAgentId==0) return null;
		CustomerAgent customerAgent=new CustomerAgent();
		String sql="SELECT * FROM customer_agent WHERE customerAgentId="+customerAgentId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				customerAgent.setAgentName(rs.getString("agentName"));
				customerAgent.setCustomerAgentId(rs.getInt("customerAgentId"));
				return customerAgent;
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
	 * 搜索代理商
	 * @return
	 */
	public List search(){
		List caList=new ArrayList();
		String sql="SELECT * FROM customer_agent ORDER BY customerAgentId DESC";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerAgent ca=new CustomerAgent();
				ca.setAgentName(rs.getString("agentName"));
				ca.setCustomerAgentId(rs.getInt("customerAgentId"));
				caList.add(ca);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return caList;
	}
}
