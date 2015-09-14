package cn.qtone.modules.customer.obj;

import java.sql.ResultSet;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class CustomerRegCodeHandle  extends BaseHandle{

	public CustomerRegCodeHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public boolean initData(String taxNO){
		String sql="SELECT COUNT(*) FROM customer_reg_code WHERE taxNO='"+taxNO+"'";
		if(db.countRow(sql)<1){
			sql="INSERT INTO  customer_reg_code SET taxNO='"+taxNO+"'";
			return db.execute(sql);	
		}else{
			return true;
		}		
	}
	
	
	
	public CustomerRegCode getCustomerRegCodeByTaxNO(String taxNO){
		CustomerRegCode customerRegCode=null;
		String sql="SELECT * FROM customer_reg_code WHERE taxNO='"+taxNO+"'";
		ResultSet rs=null;
		try{
			rs=db.select(sql);
			if(rs.next()){
				customerRegCode=new CustomerRegCode();
				customerRegCode.setCustomerRegCodeId(rs.getInt("customerRegCodeId"));
				customerRegCode.setDeductCode(rs.getString("deductCode"));
				customerRegCode.setDeductCodeExpireDate(rs.getString("deductCodeExpireDate"));
				customerRegCode.setHuyouCode(rs.getString("huyouCode"));
				customerRegCode.setHuyouCodeExpireDate(rs.getString("huyouCodeExpireDate"));
				customerRegCode.setSoftcode(rs.getString("softcode"));
				customerRegCode.setSoftcodeExpireDate(rs.getString("softcodeExpireDate"));
				customerRegCode.setTaxNO(rs.getString("taxNO"));
			}
		}catch(Exception e){
			
		}finally{
			db.closeResultSet(rs);
		}
		return customerRegCode;
	}
//	更新华洲抵扣联注册码
	public boolean updateDeductCode(String taxNO,String deductCode,String expireDate){
		this.initData(taxNO);
		String sql=" UPDATE customer_reg_code SET deductCode='"+db.filterStr(deductCode)+"'";
		if(!StringFunction.isEmpty(expireDate)){
				sql+=",deductCodeExpireDate='"+expireDate+"'";
		}
		sql+= " WHERE taxNO='"+taxNO+"'";
		return db.execute(sql);
	}	
//	更新泸友注册码
	public boolean updateHuyouCode(String taxNO,String huyouCode,String expireDate){
		this.initData(taxNO);
		String sql=" UPDATE customer_reg_code SET huyouCode='"+db.filterStr(huyouCode)+"'";
		if(!StringFunction.isEmpty(expireDate)){
				sql+=",huyouCodeExpireDate='"+expireDate+"'";
		}
		sql+= " WHERE taxNO='"+taxNO+"'";
		return db.execute(sql);
	}	
	 //	更新电子报税软件注册码
	public boolean updateSoftcode(String taxNO,String softcode,String expireDate){
		this.initData(taxNO);
		String sql=" UPDATE customer_reg_code SET softcode='"+db.filterStr(softcode)+"'";
		if(!StringFunction.isEmpty(expireDate)){
				sql+=",softcodeExpireDate='"+expireDate+"'";
		}
		sql+= " WHERE taxNO='"+taxNO+"'";
		return db.execute(sql);
	}	
}
