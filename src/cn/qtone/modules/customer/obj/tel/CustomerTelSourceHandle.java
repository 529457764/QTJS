package cn.qtone.modules.customer.obj.tel;

import java.sql.*;
import java.util.*;

import cn.qtone.sys.base.BaseHandle;

public class CustomerTelSourceHandle extends BaseHandle{

	public CustomerTelSourceHandle(){
		super();
	}
	
	/**
	 * 跟新最后收集联系电话的日期
	 * @param customerTelSourceId
	 * @return
	 */
	public boolean updateLastRunDate(int customerTelSourceId){
		String sql="UPDATE customer_tel_source SET lastRunDate=CURRENT_DATE WHERE customerTelSourceId="+customerTelSourceId;
		return db.execute(sql);
	}
	
	/**
	 * 将所有city放入map
	 * @return
	 */
	public Hashtable creatTelSourceMap(){
		Hashtable hm=new Hashtable();
		String sql="SELECT customerTelSourceId,name FROM customer_tel_source";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				hm.put(rs.getString("customerTelSourceId"),rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return hm;
	}
	
	public List search(){
		List telSourceList=new ArrayList();
		String sql="SELECT * FROM customer_tel_source where isStop=1";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerTelSource telSource=new CustomerTelSource();
				telSource.setCustomerTelSourceId(rs.getInt("customerTelSourceId"));
				telSource.setLastRunDate(rs.getString("lastRunDate"));
				telSource.setName(rs.getString("name"));
				telSource.setSourceSQL(rs.getString("sourceSQL"));
				telSourceList.add(telSource);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return telSourceList;
	}
}
