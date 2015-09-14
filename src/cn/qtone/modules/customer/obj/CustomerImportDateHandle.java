package cn.qtone.modules.customer.obj;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.*;

public class CustomerImportDateHandle extends BaseHandle{

	public CustomerImportDateHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 取得导入日期字符数组
	 * @return
	 */
	public String [] getImportDateArr(String startDate){
		String sql="SELECT DISTINCT importDate FROM customer_import_date ";
		if(!StringFunction.isEmpty(startDate)){
			sql+=" WHERE importDate>='"+startDate+"'";
		}
		return db.getQueryResult(sql,",").split(",");
	}

	/**
	 * 取得导入日期字符数组
	 * @return
	 */
	public String [] getImportDateArr(){
		return this.getImportDateArr("");
	}
	

	public boolean addImportDate(String importDate){
		String sql=" SELECT customerImportDateId FROM customer_import_date WHERE importDate='"+importDate+"'";
		if(db.getValueInt(sql)>0) {//已存在
			return true;
		}else{
			sql=" insert into  customer_import_date set   importDate='"+importDate+"'";
			return db.execute(sql);
		}
		
	}
	
}
