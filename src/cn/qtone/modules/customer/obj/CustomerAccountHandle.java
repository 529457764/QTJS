package cn.qtone.modules.customer.obj;

import java.util.*;
import java.sql.*;


import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.ParseTool;
import cn.qtone.utils.StringFunction;

public class CustomerAccountHandle extends BaseHandle{

	public CustomerAccountHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 删除账号
	 * @param customerAccountId 账号id
	 * @return
	 */
	public boolean delAccount(int customerAccountId){
		String sql="DELETE FROM customer_account WHERE customerAccountId="+customerAccountId;
		return db.executeUpdate(sql)>0?true:false;
	}
	
	/**
	 * 删除账号
	 * @param customerIds 企业id
	 * @return
	 */
	public boolean delAccount(String customerIds){
		String sql="DELETE FROM customer_account WHERE customerId IN ("+customerIds+")";
		return db.executeUpdate(sql)>0?true:false;
	}	
	
	/**
	 * 屏蔽账号
	 * @param customerAccountId 账号id
	 * @return
	 */
	public boolean forbidAccount(int customerAccountId,int isUnForbidden){
		String sql="";
		if(isUnForbidden==1){//解除屏蔽
			sql="UPDATE  customer_account SET isForbid=2 WHERE customerAccountId="+customerAccountId;
		}else{//屏蔽
			sql="UPDATE  customer_account SET isForbid=1 WHERE customerAccountId="+customerAccountId;
		}
		return db.executeUpdate(sql)>0?true:false;
	}
	
	
	/***
	 * 根据customerId 获取子账号的个数
	 * **/
	public int getChildAccountNum(int customerId ){
		String sql=" SELECT count(*) FROM customer_account WHERE isMaster=2 AND customerId="+customerId;
		 return db.countRow(sql);
	}
	
	/***
	 * 根据customerId查找  是否存在企业有税号，但没有主账号的记录 
	 * **/
	public boolean getIsNoAccount(int customerId ){
		if(customerId==0) return false;
		String sql=" SELECT count(*) FROM   customer AS c LEFT JOIN  customer_account AS a   " +
				" ON c.customerId=a.customerId AND a.isMaster=1 WHERE c.taxNO !='' AND a.account IS NULL AND c.customerId="+customerId;
		 if(db.countRow(sql)>0){
			 return true;
		 }else{
			 return false;
		 }
	}
	
	
	/***
	 * 添加子账号
	 * **/
	public boolean addChildAccount(CustomerAccount account){
		String sql=" INSERT INTO customer_account(account,pwd,customerId,isMaster,createDate,initPwd,name,tel,isChangePwd)" +
				" VALUES ('"+db.filterStr(account.getAccount())+"',MD5('"+db.filterStr(account.getPwd())+"'),"+account.getCustomerId()
				+",2,CURRENT_TIMESTAMP,'"+db.filterStr(account.getPwd())+"','"+db.filterStr(account.getName())+"','"+db.filterStr(account.getTel())+"',2)";
		 return db.execute(sql);
	}
	
	
	/***
	 * 根据账号编号取得账号信息
	 * ***/
	public CustomerAccount getAccountById(int accountId){
		CustomerAccount account=new CustomerAccount();
		String sql="SELECT * FROM customer_account WHERE customerAccountId="+accountId;
		ResultSet rs=null;
		try{
			rs=db.select(sql);
			if(rs.next()){
				account.setCustomerAccountId(ParseTool.parseInt(rs.getString("customerAccountId"),0));
				account.setAccount(rs.getString("account"));
				account.setCreateDate(rs.getString("createDate"));
				account.setCustomerAccountId(ParseTool.parseInt(rs.getString("customerAccountId"),0));
				account.setCustomerId(ParseTool.parseInt(rs.getString("customerId"),0));
				account.setIsMaster(ParseTool.parseInt(rs.getString("isMaster"),0));
				account.setName(rs.getString("name"));
				account.setTel(rs.getString("tel"));
				account.setEmail(rs.getString("email"));
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		
		return account;
	}
	
	/***
	 * 更新邮件
	 * @param email
	 * @param customerAccountId
	 * @return
	 */
	public boolean updateEmail(String email,int customerAccountId){
		if(!StringFunction.isEmpty(email)){
			String sql=" UPDATE customer_account SET email='"+db.filterStr(email)+"' WHERE customerAccountId="+customerAccountId;
			return db.execute(sql);
		}else{
			return false;
		}
	}
	
	
	/*****
	 * 更新账号
	 * **/
	public boolean updateAccount(CustomerAccount account){
		String sql=" UPDATE customer_account SET name='"+db.filterStr(account.getName())+"',tel='"
		            +db.filterStr(account.getTel())+"'";
		if(!StringFunction.isEmpty(account.getPwd())) {
			sql +=" ,pwd=MD5('"+db.filterStr(account.getPwd())+"')";
		} 
		sql+=",isChangePwd=2";
		sql +=" WHERE customerAccountId="+account.getCustomerAccountId();
		return db.execute(sql);
	}
	/**
	 * 得到某个客户的所有账号
	 * @param customerId 客户id
	 * @return
	 */
	public List accountList(int customerId){
		ArrayList list=new ArrayList();
		String sql="SELECT * FROM customer_account WHERE customerId="+customerId+" ORDER BY isMaster DESC";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerAccount account=new CustomerAccount();
				account.setAccount(rs.getString("account"));
				account.setCustomerAccountId(rs.getInt("customerAccountId"));
				account.setCustomerId(rs.getInt("customerId"));
				account.setIsMaster(rs.getInt("isMaster"));
				account.setCreateDate(rs.getString("createDate"));
				account.setPwd(rs.getString("pwd"));
				account.setName(rs.getString("name"));
				account.setTel(rs.getString("tel"));
				account.setIsForbid(rs.getInt("isForbid"));
				account.setInitPwd(rs.getString("initPwd"));
				account.setIsActive(rs.getInt("isActive"));
				account.setIsChangePwd(rs.getInt("isChangePwd"));
				account.setLoginDate(rs.getString("loginDate"));
				account.setEmail(rs.getString("email"));
				list.add(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return list;
	}
	
	/**
	 * 判断用户名是否存在
	 * @param account 用户名
	 * @return true 存在 false存在
	 */
	public boolean isExist(String account){
		String sql="SELECT COUNT(*) From customer_account WHERE account='"+db.filterStr(account)+"' AND isForbid=2";
		if(db.countRow(sql)<=0){
			return false;
		}
		return true;		
	}
	
	/**
	 * 更新密码
	 * @param newPwd 新密码
	 * @param customerAccountId 客户账号id
	 * @return
	 */
	public boolean updatePwd(String newPwd,int customerAccountId){
		String sql="UPDATE customer_account SET pwd=MD5('"+db.filterStr(newPwd)+"'),isChangePwd=2 WHERE customerAccountId="+customerAccountId;
		return db.execute(sql);
	}
	
	/**
	 * 检查密码是否正确
	 * @param customerId 客户id
	 * @param pwd 密码
	 * @return true 正确
	 */
	public boolean checkPwd(int customerId,String pwd){
		String sql="SELECT count(*) FROM customer_account WHERE customerId="+customerId+" AND pwd=MD5('"+db.filterStr(pwd)+"')";
		return db.countRow(sql)>0?true:false;
	}
	
	/**
	 * 判断是否符合规范的账号
	 * @param account
	 * @return
	 */
	public boolean isValidAccount(String account){
		String regEx="^[a-z0-9_]{5,15}$";
		java.util.regex.Pattern patten=java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m=patten.matcher(account);
		return m.find();
	}
	
	/**
	 * 将账户设为激活
	 * @param account
	 * @return
	 */
	public boolean active(int customerAccountId){
		String sql="UPDATE customer_account SET isActive=2 WHERE customerAccountId="+customerAccountId;
		return db.execute(sql);
	}
	
	/**
	 * 添加账号，根据企业资料生成账号
	 * @param customer
	 * @return
	 */
	public boolean addAccount(Customer customer){
		int customerId=customer.getCustomerId();
		if(customerId==0) {//id等于0，须根据税号读取id，一般添加企业时出现这种情况
			CustomerHandle customerHandle=new CustomerHandle();
			customerId=customerHandle.getCustomerIdByTaxNO(customer.getTaxNO());
		}
		CustomerAccount customerAccount=new CustomerAccount();
		customerAccount.setAccount(customer.getTaxNO());
		customerAccount.setCustomerId(customerId);
		customerAccount.setInitPwd("000000");
		customerAccount.setIsMaster(1);
		customerAccount.setName(customer.getLinkman());
		customerAccount.setPwd("000000");
		customerAccount.setTel(customer.getTel());
		
		return this.addAccount(customerAccount);
		
	}
	

	
	
	/**
	 * 添加客户账号
	 * @param account 账号对象
	 * @return
	 */
	public boolean addAccount(CustomerAccount account){
		if(account==null) return false;
		
		String sql="INSERT INTO customer_account SET customerId="+account.getCustomerId();
		sql+=",account='"+db.filterStr(account.getAccount()).trim()+"'";
		sql+=",pwd=MD5('"+db.filterStr(account.getPwd())+"')";
		sql+=",isMaster="+account.getIsMaster();
		sql+=",initPwd='"+db.filterStr(account.getInitPwd())+"'";
		sql+=",name='"+db.filterStr(account.getName())+"'";
		sql+=",tel='"+db.filterStr(account.getTel())+"'";
		sql+=",email='"+db.filterStr(account.getEmail())+"'";
		sql+=",createDate=CURRENT_TIMESTAMP";
		sql+=",ip='"+db.filterStr(account.getIp())+"'";
		if(account.getIsActive()>0){
			sql+=",isActive="+account.getIsActive();
		}
		if(account.getSite()>0){
			sql+=",site="+account.getSite();
		}		
		return db.execute(sql);
	}
	
	/**
	 * 更新最后登录信息
	 * @param accountId
	 * @return
	 */
	public boolean updateLoginMsg(int accountId,String ip){
		String sql="UPDATE customer_account SET loginDate=CURRENT_TIMESTAMP,ip='"+ip+"' WHERE customerAccountId="+accountId;
		return db.execute(sql);
	}
	
	/***
	 *	根据customer表生成账号 导入新企业的时候用
	 * @return
	 */
	public int  insertAccountData(){	
		String sql="insert into customer_account(account,pwd,customerId,isMaster,createDate,isForbid,initPwd) "
		+" select  c.taxNO,md5('000000'),c.customerId,1,CURRENT_TIMESTAMP,2,'000000'  from customer as c left join customer_account as a "
		+"on c.taxNO=a.account  where a.account is null and c.taxNO !='' ";  //and c.taxNO like '442000%'
		return db.executeUpdate(sql);		
	}
	
	/**
	 * 自动新建im账号使用
	 * 取得税号的字符串列表  
	 * @param start
	 * @param limit
	 * @return
	 */
	public String getAccountStrList(int start ,int limit,String sign){
		String sql=" SELECT account FROM customer_account WHERE LENGTH(account)>12 LIMIT "+start+","+limit;
		return db.getQueryResult(sql,sign);
	}
	/***
	 * 取得 customer_account 的总条数
	 * @return
	 */
	public int countRows(){
		return db.countRow("SELECT COUNT(*) FROM customer_account WHERE 1=1 ");
	}
	
	/***
	 * 取得账号的初始密码
	 * @param taxNO
	 * @return
	 */
	public String getInitPwdByTaxNO(String taxNO){
		return db.getValue("SELECT initPwd FROM customer_account WHERE account='"+taxNO+"'");
	}
	
	
	/**
	 * 根据邮箱取得用户账号对象
	 * @param email
	 * @return
	 */
	public CustomerAccount getAccountByEmail(String email){
		CustomerAccount account=null;
		String sql="SELECT * FROM customer_account WHERE email='"+db.filterStr(email)+"'";
		ResultSet rs=null;
		try{
			rs=db.select(sql);
			if(rs.next()){
				account=new CustomerAccount();
				account.setCustomerAccountId(ParseTool.parseInt(rs.getString("customerAccountId"),0));
				account.setAccount(rs.getString("account"));
				account.setCreateDate(rs.getString("createDate"));
				account.setCustomerId(ParseTool.parseInt(rs.getString("customerId"),0));
				account.setIsMaster(ParseTool.parseInt(rs.getString("isMaster"),0));
				account.setName(rs.getString("name"));
				account.setEmail(rs.getString("email"));
				account.setTel(rs.getString("tel"));
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		
		return account;		
	}
	
	
	public int isExistAccount(String account){
		String sql="SELECT COUNT(*) FROM customer_account WHERE account='"+db.filterStr(account)+"'";
		return db.countRow(sql);
	}
	
	public int isExistEmail(String email){
		String sql="SELECT COUNT(*) FROM customer_account WHERE email='"+db.filterStr(email)+"'";
		return db.countRow(sql);
	}

	
	public static void main(String[] args){
		CustomerAccountHandle handle=new CustomerAccountHandle();
		System.out.println(handle.isValidAccount("312a34234_"));
	}	
}
