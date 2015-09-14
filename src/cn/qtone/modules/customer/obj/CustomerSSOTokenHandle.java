package cn.qtone.modules.customer.obj;

import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.sys.config.ModuleConfig;
import cn.qtone.utils.ParseTool;

public class CustomerSSOTokenHandle extends BaseHandle{

	public CustomerSSOTokenHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 保存当点登录的令牌对象
	 * @param ssoToken 令牌对象
	 * @return
	 */
	public boolean saveSSOToken(CustomerSSOToken ssoToken){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("INSERT INTO customer_sso_token set account='"+db.filterStr(ssoToken.getAccount())+"'");
		sqlBuf.append(",token='"+ssoToken.getToken()+"'");
		sqlBuf.append(",avaDate='"+ssoToken.getAvaDate()+"'");
		sqlBuf.append(",useTimes="+ssoToken.getUseTimes());
		return this.db.execute(sqlBuf.toString());
	}
	
	/**
	 * 更新令牌的使用次数
	 * @param customerSSOTokenId 令牌id
	 * @return
	 */
	public boolean updateTokenUseTimes(int customerSSOTokenId){
		String sql="UPDATE customer_sso_token SET useTimes=useTimes+1 WHERE customerSSOTokenId="+customerSSOTokenId;
		return db.execute(sql);
	}
	
	/**
	 * 得到有效的令牌对象
	 * @param account 账号
	 * @param token 令牌
	 * @return 令牌对象
	 */
	public CustomerSSOToken getSSOToken(String account,String token){
		int maxUseTimes=ParseTool.parseInt(ModuleConfig.getConfigValue("customer","tokenUseTimes").toString(),10);
		String sql="SELECT customerSSOTokenId,token,account,avaDate,useTimes FROM customer_sso_token WHERE account='"+db.filterStr(account)+"'";
		sql+=" AND token='"+db.filterStr(token)+"'";
		sql+=" AND avaDate>=CURRENT_TIMESTAMP";
		sql+=" AND useTimes<"+maxUseTimes+" limit 1";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				CustomerSSOToken tokenObj=new CustomerSSOToken();
				tokenObj.setAccount(account);
				tokenObj.setAvaDate(rs.getString("avaDate"));
				tokenObj.setCustomerSSOTokenId(rs.getInt("customerSSOTokenId"));
				tokenObj.setToken(token);
				tokenObj.setUseTimes(rs.getInt("useTimes"));
				return tokenObj;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
			return null;
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}

}
