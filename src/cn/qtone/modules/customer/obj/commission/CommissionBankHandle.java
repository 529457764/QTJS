package cn.qtone.modules.customer.obj.commission;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.sys.base.BaseHandle;

public class CommissionBankHandle extends BaseHandle {
	
	public CommissionBankHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 取得银行号
	 * @param bankName
	 * @return
	 */
	public String getBankNOByBankName(String bankName){
		String sql="SELECT bankNO FROM commission_bank WHERE  bankName='"+db.filterStr(bankName)+"'"; 
		return db.getValue(sql);
	}
	
	/**
	 * 增加
	 * @param CommissionBank
	 * @return
	 */
	public boolean insert(CommissionBank commissionBank){
		String sql="INSERT INTO commission_bank  SET bankNO='"+db.filterStr(commissionBank.getBankNO())+"'" 
		        +",bankName='"+db.filterStr(commissionBank.getBankName())+"'"; 			
		return db.execute(sql);
	}
	/**
	 * 修改
	 * @param CommissionBank
	 * @return
	 */
	public boolean edit(CommissionBank commissionBank){
			String sql="UPDATE  commission_bank  SET bankNO='"+db.filterStr(commissionBank.getBankNO())+"'" 
		        +",bankName='"+db.filterStr(commissionBank.getBankName())+"'"
	        +" WHERE  commissionBankId="+ commissionBank.getCommissionBankId()  ;
			return db.execute(sql);
	}
	
	/**
	 * 是否存在
	 * @param customerId
	 * @return
	 */
	public boolean isExist(CommissionBank commissionBank){
		String sql=" SELECT COUNT(*)  FROM  commission_bank  WHERE  bankNO='"+db.filterStr(commissionBank.getBankNO())+"' OR bankName='"+db.filterStr(commissionBank.getBankName())+"'" ;
		return db.countRow(sql)>0;
	}
	/**
	 * 搜索
	 * @return
	 */
	public List search(String [] bankName,int limit ){
		    List list=new ArrayList();
			String sql="SELECT *  FROM   commission_bank   WHERE 1=1   ";
			for(int i=0;i<bankName.length;i++){
				sql +="  AND  bankName LIKE '%"+db.filterStr(bankName[i])+"%'";
			}
			if(limit>0){
				sql +="  LIMIT "+limit;
			}
			ResultSet rs=null;
			CommissionBank commissionBank=null;
		    try{
		    	rs=db.select(sql);
		    	while(rs.next()){
		    		commissionBank=new CommissionBank();
		    		commissionBank.setBankName(rs.getString("bankName"));
		    		commissionBank.setCommissionBankId(rs.getInt("commissionBankId"));
		    		commissionBank.setBankNO(rs.getString("bankNO"));
		    		list.add(commissionBank);
		    	}
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			} finally{
				db.closeResultSet(rs);
			}	
			return list;
			
	}
	
	
}
