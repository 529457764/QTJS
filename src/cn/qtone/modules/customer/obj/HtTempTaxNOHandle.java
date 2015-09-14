package cn.qtone.modules.customer.obj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.modules.customer.obj.tel.CustomerTel;
import cn.qtone.modules.customer.obj.tel.CustomerTelHandle;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class HtTempTaxNOHandle extends BaseHandle {

	public HtTempTaxNOHandle(){
		super();
	}
	
	public boolean  truncateTable(){
		String sql=" TRUNCATE TABLE ht_temp_taxno ";
		return db.execute(sql);		
	}
	
	
	public boolean insert(String taxNO){
		String sql=" INSERT INTO  ht_temp_taxno SET taxNO='"+db.filterStr(taxNO)+"'";
		return db.execute(sql);		
	}
	
	/**
	 * 搜索企业 航天导出
	 * @return
	 */
	public List searchHt( ){
		
		List list=new ArrayList();
		
		String sql="SELECT c.* FROM customer AS c  LEFT JOIN ht_temp_taxno AS ht ON c.taxNO=ht.taxNO WHERE ht.taxNO IS NOT NULL ";
		ResultSet rs=null;
		try {
			CustomerTelHandle telHandle=new CustomerTelHandle();
			rs=db.select(sql);
			while (rs.next()){
				Customer customer=new Customer();
				customer.setCompanyName(rs.getString("c.companyName"));
				customer.setCustomerId(rs.getInt("c.customerId"));
				customer.setTaxType(rs.getInt("c.taxType"));
				customer.setTaxNO(rs.getString("c.taxNO"));
				customer.setLinkman(rs.getString("c.linkman"));
				customer.setTel(rs.getString("c.tel"));
				customer.setMobile(rs.getString("c.mobile"));
				customer.setReceipt_address(rs.getString("c.receipt_address"));
				customer.setFax(rs.getString("c.fax"));	
				customer.setCityId(rs.getInt("c.cityId"));
				customer.setBusinessAddress(rs.getString("c.businessAddress"));
				customer.setAddress(rs.getString("c.address")); 
				
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
}
