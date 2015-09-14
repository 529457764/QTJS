package cn.qtone.modules.customer.obj;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;
import cn.qtone.utils.db.DBControlManager;
import cn.qtone.utils.dbcp.ConnectionManager;

public class CustomerBlacklistHandle extends BaseHandle{

	public CustomerBlacklistHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 写入黑名单
	 * @param customerBlacklist
	 * @return
	 */
	public boolean insert (CustomerBlacklist customerBlacklist){
		String sql=" INSERT INTO customer_blacklist set customerId="+customerBlacklist.getCustomerId()+",blackType="+customerBlacklist.getBlackType()
		            +", comment='"+db.filterStr(customerBlacklist.getComment())+"', userBoId="+customerBlacklist.getUserBoId()
		            +", createDate=CURRENT_TIMESTAMP , flag=2 ";
		return db.execute(sql);
	}
	
	/**
	 * 写入黑名单
	 * @param customerBlacklist
	 * @return
	 */
	public boolean update (CustomerBlacklist customerBlacklist){
		String sql=" UPDATE customer_blacklist set  comment='"+db.filterStr(customerBlacklist.getComment())+"'"
		+", userBoId="+customerBlacklist.getUserBoId()+",blackType="+customerBlacklist.getBlackType()
		+", flag=2 "
		+", modDate=CURRENT_TIMESTAMP "
		+" WHERE customerId="+customerBlacklist.getCustomerId();
		return db.execute(sql);
	}

	/**
	 * 企业是否已添加黑名单
	 * @param customerId
	 * @return
	 */
	public int isExist(int customerId){
		String sql=" SELECT COUNT(*) FROM customer_blacklist WHERE customerId="+customerId;
		return db.countRow(sql);
	}
	
	/**
	 * 取消黑名单
	 * @param customerId
	 * @return
	 */
	public boolean delete(CustomerBlacklist customerBlacklist){
		String sql=" UPDATE  customer_blacklist  SET flag=1,userBoId="+customerBlacklist.getUserBoId()
		+"  ,comment='"+db.filterStr(customerBlacklist.getComment())+"' "
		+", modDate=CURRENT_TIMESTAMP "
	    +" WHERE customerId="+customerBlacklist.getCustomerId();
		return db.execute(sql);	
	}
	/**
	 * 取得customerBlacklist对象
	 * @param customerId
	 * @return
	 */
	public CustomerBlacklist getCustomerBlacklistByCustomerId(int customerId){
		CustomerBlacklist customerBlacklist=null;
		String sql=" SELECT * FROM customer_blacklist WHERE flag=2 AND customerId="+customerId;
		ResultSet rs=null;
		try{
			rs=db.select(sql);
			if(rs.next()){
				customerBlacklist=new CustomerBlacklist();
				customerBlacklist.setComment(rs.getString("comment"));
				customerBlacklist.setCustomerBlacklistId(rs.getInt("customerBlacklistId"));
				customerBlacklist.setCustomerId(rs.getInt("customerId"));
				customerBlacklist.setUserBoId(rs.getInt("userBoId"));
				customerBlacklist.setCreateDate(rs.getString("createDate"));
				customerBlacklist.setBlackType(rs.getInt("blackType"));
				customerBlacklist.setModDate(rs.getString("modDate"));
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return customerBlacklist;
	}
	
	//根据未交费企业设置为黑名单，手工运行
	private void autoSet336Black(){
		String sql="SELECT DISTINCT fwc.customerId FROM follow_whf_customer AS fwc  LEFT JOIN order_product_rebuy AS opr ON fwc.customerId=opr.customerId AND opr.productId = '33'"; 
		sql+=" AND opr.isStop=1 LEFT JOIN follow_whf_year AS fwy ON fwy.customerId=fwc.customerId AND fwy.invalid=1 AND fwy.payYear=2013 AND fwy.isReceive!=3"; 
		sql+=" LEFT JOIN follow_whf AS fw ON fw.followWHFId=fwy.followWHFId AND fw.invalid=1 AND fw.isReceive!=3";
		sql+=" LEFT JOIN customer_blacklist AS cbl ON fwc.customerId=cbl.customerId AND cbl.flag=2"; 
		sql+=" AND cbl.blackType=2,customer AS c WHERE c.customerId=fwc.customerId AND fwc.isPayCurYear=1 AND fw.followWHFId IS NULL AND c.isInstallCard=2 AND cbl.customerBlacklistId IS NULL";
		ResultSet rs=null;
		int i=0;
		try {
			rs=db.select(sql);
			while(rs.next()){
				i++;
				int customerId=rs.getInt("fwc.customerId");
				CustomerBlacklist customerBlacklist=new CustomerBlacklist();
				customerBlacklist.setCustomerId(customerId);
				customerBlacklist.setComment("根据未交费企业，系统手工设置");
				customerBlacklist.setUserBoId(0);
				customerBlacklist.setBlackType(2);
				this.insert(customerBlacklist);
				System.out.println("customerId="+customerId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		System.out.println("finished total:"+i);
	}
	
	/**
	 * 删除重复的黑名单数据
	 */
	private void delRepeate(){
		String sql="select customerId,count(*) as num from customer_blacklist where blackType=2 group by customerId having num>1";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				int customerId=rs.getInt("customerId");
				int num=rs.getInt("num");
				sql="delete from customer_blacklist where customerId ="+customerId+" order by modDate asc limit "+(num-1);
				db.execute(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
		}
	}
	
	public static void main(String[] ary){
		
		String root="E:\\java\\qt_tax\\WebRoot";
		
		try {
			ConnectionManager.init("E:\\java\\qt_tax\\WebRoot\\WEB-INF\\conf\\DatabasePool.conf_10");
//			ConnectionManager.init("E:\\java\\qt_tax\\WebRoot\\WEB-INF\\conf\\DatabasePool.conf");
	        DBControlManager.init("E:\\java\\qt_tax\\WebRoot\\WEB-INF\\conf\\DBControl.conf");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CustomerBlacklistHandle customerBlacklistHandle=new CustomerBlacklistHandle();
		customerBlacklistHandle.delRepeate();
	}
	
}
