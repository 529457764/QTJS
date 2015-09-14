package cn.qtone.modules.customer.obj.tel;

import java.sql.*;
import java.util.*;

import cn.qtone.modules.ca.obj.CaCustomerHandle;
import cn.qtone.modules.customer.obj.CustomerAccountHandle;
import cn.qtone.modules.order.obj.OrderHandle;
import cn.qtone.modules.telRecord.obj.TelBlacklistHandle;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.*;

public class CustomerTelHandle extends BaseHandle{

	public CustomerTelHandle(){
		super();
	}
	
	/**
	 * 通过电话黑名单删除
	 * @return
	 */
	public boolean delByBlacklist(int customerId){
		String sql="update customer_tel as ct,tel_blacklist as tb set ct.isInvalid=2 where ct.tel=tb.tel and ct.customerId=tb.customerId and ct.isInvalid=1 and tb.blacklistType not in (5)";
		if(customerId>0) sql+=" AND ct.customerId="+customerId;
		return db.execute(sql);
	}
	
	public boolean delByBlacklist(){
		return this.delByBlacklist(0);
	}
	/**
	 * 根据企业电话id删除企业相关电话资料
	 * @return
	 */
	public boolean delTelById(int telID){
		if(telID<=0) return false;
		String sql="update customer_tel set isInvalid=2 WHERE customerTelId="+telID;
		return db.execute(sql);		
	}
	public boolean update(CustomerTel customerTel){
		CustomerTel customerTelExist=this.getCustomerTel(customerTel.getCustomerId(), customerTel.getTel());
		if(customerTelExist==null){
			if(customerTel.getTel().length()>=11){//手机号码要检查前面加0的情况
				String mobile=customerTel.getTel();
				if(mobile.startsWith("0")){
					mobile=mobile.substring(1);
				}else{
					mobile="0"+mobile;
				}
				customerTelExist=this.getCustomerTel(customerTel.getCustomerId(), mobile);
			}
		}
		
		if(customerTelExist!=null){
			String sql="UPDATE customer_tel SET isInvalid="+customerTel.getIsInvalid();
			sql+=",customerId="+customerTel.getCustomerId();

			//本次的联系时间比上次的大才更新联系人
			if(!StringFunction.isEmpty(customerTel.getLinkman())){
				if(DateClass.CompareTwoDate(customerTel.getUpdateTime(),customerTelExist.getUpdateTime(),"yyyy-MM-dd hh:mm:ss")==1){
					sql+=",linkman='"+db.filterStr(customerTel.getLinkman())+"'";
				}
			}

			//本次的联系时间比上次的大才更新联系时间
			if(!StringFunction.isEmpty(customerTel.getUpdateTime())){
				if(DateClass.CompareTwoDate(customerTel.getUpdateTime(),customerTelExist.getUpdateTime(),"")==1){
					sql+=",updateTime='"+customerTel.getUpdateTime()+"'";								
				}
			}
			
			if(customerTel.getCustomerTelSourceId()>1) sql+=",customerTelSourceId="+customerTel.getCustomerTelSourceId();//电话来源是电话登记时不更新
			sql+=" WHERE customerTelId="+customerTelExist.getCustomerTelId();
			return db.execute(sql);
		}else{
			String sql="INSERT INTO customer_tel SET isInvalid="+customerTel.getIsInvalid();
			sql+=",customerId="+customerTel.getCustomerId();
			sql+=",tel='"+db.filterStr(customerTel.getTel())+"'";
			if(!StringFunction.isEmpty(customerTel.getLinkman())){
				sql+=",linkman='"+db.filterStr(customerTel.getLinkman())+"'";
			}
			if(!StringFunction.isEmpty(customerTel.getUpdateTime())){
				sql+=",updateTime='"+customerTel.getUpdateTime()+"'";								
			}
			sql+=",customerTelSourceId="+customerTel.getCustomerTelSourceId();//电话来源是电话登记时不更新
			return db.execute(sql);			
		}
	}
	
	public List search(int customerId,int num){
		TelBlacklistHandle telBlacklistHandle=new TelBlacklistHandle();
		List telList=new ArrayList();
		String sql="SELECT * FROM customer_tel WHERE customerId="+customerId;
		sql+=" AND isInvalid=1";//此处加上只显示没有被删除的记录
		sql+=" ORDER BY updateTime DESC";
		if(num!=-1) sql+=" LIMIT 0,"+num;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerTel customerTel=new CustomerTel();
				customerTel.setCustomerId(rs.getInt("customerId"));
				customerTel.setCustomerTelId(rs.getInt("customerTelId"));
				customerTel.setIsInvalid(rs.getInt("isInvalid"));
				customerTel.setLinkman(rs.getString("linkman"));
				customerTel.setCustomerTelSourceId(rs.getInt("customerTelSourceId"));
				customerTel.setTel(rs.getString("tel"));
				customerTel.setUpdateTime(rs.getString("updateTime"));
				int telType=telBlacklistHandle.getBlackType(customerTel.getTel(),rs.getInt("customerId"));
				customerTel.setTelType(telType);
				telList.add(customerTel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return telList;
	}
	
	private CustomerTel getCustomerTel(int customerId,String tel){
		String sql="SELECT * FROM customer_tel WHERE customerId="+customerId;
		sql+=" AND tel='"+db.filterStr(tel)+"'";
		ResultSet rs=null;
		try {
			rs=db.selectMain(sql);
			if(rs.next()){
				CustomerTel customerTel=new CustomerTel();
				customerTel.setCustomerId(rs.getInt("customerId"));
				customerTel.setCustomerTelId(rs.getInt("customerTelId"));
				customerTel.setIsInvalid(rs.getInt("isInvalid"));
				customerTel.setLinkman(rs.getString("linkman"));
				customerTel.setCustomerTelSourceId(rs.getInt("customerTelSourceId"));
				customerTel.setTel(rs.getString("tel"));
				customerTel.setUpdateTime(rs.getString("updateTime"));
				return customerTel;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	public int update(CustomerTelSource telSource){
		String sql=telSource.getSourceSQL();
		if(StringFunction.isEmpty(sql)){
			return 0;
		}
		sql=StringFunction.strReplace(sql,"lastRunDate", telSource.getLastRunDate());
		int successNum=0;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				String tel=rs.getString("tel");
				if(StringFunction.isEmpty(tel)||"无".equals(tel)){
					continue;
				}
				
				if("13078610928".equals(tel)){//该号码经常投诉我们，不更新，以免导入航信系统
					continue;
				}
				String linkman=rs.getString("linkman");
				if(StringFunction.isEmpty(linkman)){
					continue;
				}
				int customerId=rs.getInt("customerId");
				String updateTime=rs.getString("updateTime");
				CustomerTel customerTel=new CustomerTel();
				customerTel.setCustomerId(customerId);
				customerTel.setIsInvalid(1);
				customerTel.setLinkman(linkman);
				customerTel.setCustomerTelSourceId(telSource.getCustomerTelSourceId());
				customerTel.setTel(tel);
				customerTel.setUpdateTime(updateTime);
				if(this.update(customerTel)){
					successNum++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
			return successNum;
		}finally{
			db.closeResultSet(rs);
		}
		
		//更新最后运行日期
		CustomerTelSourceHandle custoemrTelSourceHandle=new CustomerTelSourceHandle();
		custoemrTelSourceHandle.updateLastRunDate(telSource.getCustomerTelSourceId());
		
		return successNum;
	}
	/**
	 * 取得最后的联系电话
	 * @param customerId
	 * @return
	 */
	public String getLastTel(int customerId){
		String sql="SELECT tel FROM customer_tel WHERE customerId="+customerId+" AND isInvalid=1 AND tel!='' and tel IS NOT NULL  ORDER BY customerTelId  DESC LIMIT 1 ";
		return db.getValue(sql);
	}

	/**
	 * 取得最后的联系固话
	 * @param customerId
	 * @return
	 */
	public CustomerTel getLastFixLineTel(int customerId){
		String sql="SELECT * FROM customer_tel WHERE customerId="+customerId+" AND isInvalid=1";
		sql+=" AND length(tel)<11 AND tel!='' and tel IS NOT NULL";
		sql+=" ORDER BY customerTelId  DESC LIMIT 1 ";
		ResultSet rs=null;
		CustomerTel customerTel=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				customerTel=new CustomerTel();
				customerTel.setTel(rs.getString("tel"));
				customerTel.setLinkman(rs.getString("linkman"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return customerTel;
	}
	
	/**
	 * 取得最后的联系手机
	 * @param customerId
	 * @return
	 */
	public CustomerTel getLastMobile(int customerId){
		String sql="SELECT * FROM customer_tel WHERE customerId="+customerId+" AND isInvalid=1";
		sql+=" AND length(tel)>=11 AND tel!='' and tel IS NOT NULL";
		sql+=" ORDER BY customerTelId  DESC LIMIT 1 ";
		ResultSet rs=null;
		CustomerTel customerTel=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				customerTel=new CustomerTel();
				customerTel.setTel(rs.getString("tel"));
				customerTel.setLinkman(rs.getString("linkman"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return customerTel;
	}

	/**
	 * 删除重复电话，如前面有0的手机号码和没有0的手机号码重复
	 */
	private void delRepeat(){
		String sql="select customerId ,tel,TRIM(LEADING '0' FROM tel) as tel1,count(*) as num from customer_tel  where tel like'01%' and length(tel)>10 group by tel1 having num>1";
		ResultSet rs=null;
		int j=0;
		try {
			rs=db.select(sql);
			while(rs.next()){
				String tel=rs.getString("tel1");
				int customerId=rs.getInt("customerId");
				sql="select customerTelId from customer_tel where tel IN ("+tel+",0"+tel+") and customerId="+customerId+" order by updateTime desc";
				ResultSet rsDel=db.selectMain(sql);
				int i=0;
				while(rsDel.next()){
					if(i>0){
						sql="delete from customer_tel where customerTelId="+rsDel.getInt("customerTelId");
						db.execute(sql);
						j++;
						logger.debug(j+"删除"+customerId+"("+tel+")");
					}
					i++;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(null,e);
		} finally{
			db.closeResultSet(rs);
		}
		
		logger.debug("finish");
	}

	public static void main(String[] args){
		String root="E:\\java\\qt_tax_v2\\WebRoot";
		cn.qtone.sys.Init init=new cn.qtone.sys.Init();
		init.init(root, null);
		
		CustomerTelHandle customerTelHandle=new CustomerTelHandle();
		customerTelHandle.delRepeat();

//		System.out.println("开始从电话记录更新联系电话");
//		System.out.println("更新记录数："+customerTelHandle.updateFromTelRecord(startDate));
//		System.out.println("结束从电话记录更新联系电话");
	
	}
}
