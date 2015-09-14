package cn.qtone.modules.customer.obj;

import java.sql.*;

import cn.qtone.sys.base.BaseHandle;


public class CustomerPwdTokenHandle extends BaseHandle{

	public CustomerPwdTokenHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 保存修改密码的令牌对象
	 * @param PwdToken 令牌对象
	 * @return
	 */
	public boolean savePwdToken(CustomerPwdToken PwdToken){
		String sql="SELECT COUNT(*) FROM customer_pwd_token WHERE customerAccountId="+PwdToken.getCustomerAccountId();
		if(db.getValueInt(sql)>0){
			sql="UPDATE customer_pwd_token SET token='"+PwdToken.getToken()+"',avaDate='"+PwdToken.getAvaDate()+"',isUse=1 WHERE customerAccountId="+PwdToken.getCustomerAccountId();
		}else{
			sql="INSERT INTO customer_pwd_token SET customerAccountId="+PwdToken.getCustomerAccountId()+",token='"+PwdToken.getToken()+"',avaDate='"+PwdToken.getAvaDate()+"',isUse="+PwdToken.getIsUse();
		}
		return db.execute(sql);
	}
	
	/**
	 * 更新令牌的为已使用
	 * @param customerPwdTokenId 令牌id
	 * @return
	 */
	public boolean updateTokenisUse(int customerPwdTokenId){
		String sql="UPDATE customer_pwd_token SET isUse=2 WHERE customerPwdTokenId="+customerPwdTokenId;
		return db.execute(sql);
	}
	
	/**
	 * 令牌对象
	 * @param customerAccountId 账号id
	 * @param token 令牌
	 * @return 令牌对象
	 */
	public CustomerPwdToken getPwdToken(String token){
		String sql="SELECT * FROM customer_pwd_token WHERE 1=1";
		sql+=" AND token='"+db.filterStr(token)+"'";
//		sql+=" AND avaDate>=CURRENT_TIMESTAMP";
//		sql+=" AND isUse!=2 ";
		ResultSet rs=null;
		CustomerPwdToken tokenObj=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				tokenObj=new CustomerPwdToken();
				tokenObj.setCustomerAccountId(rs.getInt("customerAccountId"));
				tokenObj.setAvaDate(rs.getString("avaDate"));
				tokenObj.setCustomerPwdTokenId(rs.getInt("customerPwdTokenId"));
				tokenObj.setToken(token);
				tokenObj.setIsUse(rs.getInt("isUse"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
			return null;
		} finally{
			db.closeResultSet(rs);
		}
		return tokenObj;
	}

}
