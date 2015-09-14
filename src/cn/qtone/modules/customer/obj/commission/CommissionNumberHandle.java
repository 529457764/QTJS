package cn.qtone.modules.customer.obj.commission;

import java.sql.ResultSet;

import cn.qtone.sys.base.BaseHandle;

public class CommissionNumberHandle extends BaseHandle {
	public CommissionNumberHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 增加
	 * @param commissionNumber
	 * @return
	 */
	public boolean insert(CommissionNumber commissionNumber){
		String sql="INSERT INTO commission_number  SET number='"+db.filterStr(commissionNumber.getNumber())+"'" 
		        +",state="+commissionNumber.getState(); 			
		return db.execute(sql);
	}
	
	
	/**
	 * 修改
	 * @param commissionNumber
	 * @return
	 */
	public boolean edit(String number,int state,int customerId){
			String sql="UPDATE  commission_number  SET  state="+state+",customerId="+customerId
	        +" WHERE  number='"+db.filterStr(number)+"'" ;
			return db.execute(sql);
	}
		
	/**
	 * 修改
	 * @param commissionNumber
	 * @return
	 */
	public boolean edit(String number,int state,int customerId,int isExport){
			String sql="UPDATE  commission_number  SET  state="+state+",customerId="+customerId+",isExport="+isExport
	        +" WHERE  number='"+db.filterStr(number)+"'" ;
			return db.execute(sql);
	}
	
	/**
	 * 是否存在
	 * @param customerId
	 * @return
	 */
	public boolean isExist(String number){
		String sql=" SELECT COUNT(*)  FROM  commission_number  WHERE  number='"+db.filterStr(number)+"'" ;
		return db.countRow(sql)>0;
	}
	
	/**
	 * 取得编号对象 
	 * @param number
	 * @return
	 */
	public CommissionNumber getCommissionNumberByNumber(String number ){
		CommissionNumber commissionNumber=null;
		String sql=" SELECT * FROM  commission_number  WHERE     number='"+db.filterStr(number)+"'" ;
		ResultSet rs=null;
	    try{
	    	rs=db.select(sql);
	    	while(rs.next()){
	    		commissionNumber=new CommissionNumber();
	    		commissionNumber.setCommissionNumberId(rs.getInt("commissionNumberId"));
	    		commissionNumber.setNumber(rs.getString("number"));
	    		commissionNumber.setState(rs.getInt("state"));
	    		commissionNumber.setCustomerId(rs.getInt("customerId"));
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}	
		return commissionNumber;
		 
	}
	
	public boolean setIsExport(String number){
		 String sql=" UPDATE  commission_number SET isExport=2  WHERE number='"+db.filterStr(number)+"'" ;
		 return db.execute(sql);
	 }
	/**
	 * 取得最少的号码
	 * @return
	 */
	public String getMinNumber(){
		String sql=" SELECT number FROM  commission_number WHERE  state<=1  ORDER BY  number ASC LIMIT 1";
		return db.getValue(sql);
	}
	
	/**
	 * 取得状态
	 * @return
	 */
	public int getState(String number){
		String sql=" SELECT state FROM  commission_number WHERE  number='"+db.filterStr(number)+"'";
		return db.getValueIntMain(sql);
	}
}
