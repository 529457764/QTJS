package cn.qtone.modules.customer.obj;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.qtone.sys.base.BaseHandle;

public class CustomerSessionHandle extends BaseHandle{

	public CustomerSessionHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 清除session
	 * @param request
	 */
	public void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			session.removeAttribute("customerSession");
//			session.invalidate();
		} catch (Exception ex) {
		}
	}
	
	/**
	 * 将客户对象放入session
	 * @param customerSession 客户对象
	 * @param request
	 */
	public void saveUserInSession(CustomerSession customerSession,HttpServletRequest request){
		
		request.getSession().setAttribute("customerSession",customerSession);
	}

	/**
	 * 更新放入session的客户对象
	 * @param CustomerSession 客户session对象
	 * @return
	 */
	public void updateCustomerSession(CustomerSession customerSession){
		String sql = "SELECT a.account,a.isMaster,c.companyName,c.taxNO,c.customerId,c.isConfirm FROM customer_account AS a,customer AS c WHERE a.customerId=c.customerId and c.customerId="
				+ customerSession.getCustomerId();
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				customerSession.setAccount(rs.getString("a.account"));
				customerSession.setCompanyName(rs.getString("c.companyName"));
				customerSession.setIsMaster(rs.getInt("a.isMaster"));
				customerSession.setTaxNO(rs.getString("c.taxNO"));
				customerSession.setCustomerId(rs.getInt("c.customerId"));
				customerSession.setIsConfirm(rs.getInt("c.isConfirm"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
	}
	
	/**
	 * 根据账号密码得到放入session的客户对象
	 * @param account 账号
	 * @param pwd 密码
	 * @return
	 */
	public CustomerSession getCustomerSession(String account,String pwd){
		String sql = "SELECT a.customerAccountId,a.account,a.loginDate,a.isMaster,a.isActive,a.isChangePwd,a.site,c.companyName,c.taxNO,c.customerId,c.isConfirm,c.linkman,c.tel FROM customer_account AS a,customer AS c";
		sql+=" WHERE a.customerId=c.customerId AND isForbid=2 AND account='"
				+ db.filterStr(account)
				+ "' AND pwd=MD5('"
				+ db.filterStr(pwd)
				+ "')";
		ResultSet rs=null;
		try {
			rs=db.selectMain(sql);
			if(rs.next()){
				CustomerSession customerSession=new CustomerSession();
				customerSession.setAccount(rs.getString("a.account"));
				customerSession.setCompanyName(rs.getString("c.companyName"));
				customerSession.setIsMaster(rs.getInt("a.isMaster"));
				customerSession.setTaxNO(rs.getString("c.taxNO"));
				customerSession.setCustomerId(rs.getInt("c.customerId"));
				customerSession.setIsConfirm(rs.getInt("c.isConfirm"));
				customerSession.setCustomerAccountId(rs.getInt("a.customerAccountId"));
				customerSession.setIsActive(rs.getInt("a.isActive"));
				customerSession.setIsChangePwd(rs.getInt("a.isChangePwd"));
				customerSession.setLoginDate(rs.getString("a.loginDate"));
				customerSession.setSite(rs.getInt("a.site"));
				customerSession.setLinkman(rs.getString("c.linkman"));
				customerSession.setTel(rs.getString("c.tel"));
				return customerSession;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return null;
	}
	
	/**
	 * 根据账号得到放入session的客户对象
	 * 该方法主要用于单点登录，调用该方法前必须先验证token
	 * @param account 账号
	 * @return
	 */
	public CustomerSession getCustomerSessionByAccount(String account){
		String sql = "SELECT a.isActive,a.customerAccountId,a.account,a.isMaster,c.companyName,c.taxNO,c.customerId,c.isConfirm,a.site FROM customer_account AS a,customer AS c";
		sql+=" WHERE a.customerId=c.customerId AND isForbid=2 AND account='"
				+ db.filterStr(account)
				+ "' LIMIT 1";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				CustomerSession customerSession=new CustomerSession();
				customerSession.setAccount(rs.getString("a.account"));
				customerSession.setCompanyName(rs.getString("c.companyName"));
				customerSession.setIsMaster(rs.getInt("a.isMaster"));
				customerSession.setTaxNO(rs.getString("c.taxNO"));
				customerSession.setCustomerId(rs.getInt("c.customerId"));
				customerSession.setIsConfirm(rs.getInt("c.isConfirm"));
				customerSession.setCustomerAccountId(rs.getInt("a.customerAccountId"));
				customerSession.setIsActive(rs.getInt("a.isActive"));
				customerSession.setSite(rs.getInt("a.site"));
				return customerSession;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return null;
	}
	/**
	 * 从session得到CustomerSession对象
	 * @param request
	 * @return
	 */
	public CustomerSession getCustomerSession(HttpServletRequest request){
		CustomerSession customerSession=(CustomerSession)request.getSession().getAttribute("customerSession");
		return customerSession;
	}
	
	/**
	 * 检查用户是否有登陆
	 * @param request
	 * @return
	 */
	public boolean isLogin(HttpServletRequest request){
		if(this.getCustomerSession(request)==null){
			return false;
		}else{
			return true;
		}
	}
}
