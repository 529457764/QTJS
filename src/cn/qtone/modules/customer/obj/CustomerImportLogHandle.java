package cn.qtone.modules.customer.obj;

import cn.qtone.sys.base.BaseHandle;



public class CustomerImportLogHandle  extends BaseHandle{
	public CustomerImportLogHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 记日志
	 * @param log
	 * @return
	 */	
	public boolean logging(CustomerImportRow log,int flag,String remark){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("INSERT INTO customer_import_log " +
				" SET taxNO='"+db.filterStr(log.getTaxNO())+"'");
		sqlBuf.append(",companyName='"+db.filterStr(log.getCompanyName())+"'");
		sqlBuf.append(",remark='"+remark+"'");	
		sqlBuf.append(",flag="+flag);				
		sqlBuf.append(",logDate=CURRENT_TIMESTAMP");	
		
		return db.execute(sqlBuf.toString());
	}
	
}
