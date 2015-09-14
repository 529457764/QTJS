package cn.qtone.modules.customer.obj;

import java.util.*;
import java.sql.*;

import net.sf.json.JSONObject;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;
import cn.qtone.utils.WxUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CustomerWxHandle extends BaseHandle {
	public CustomerWxHandle(){
		super();
	}
	
	public CustomerWx getCustomerWxByOpenid(String openid) {
		CustomerWx customerWx = null;
		String sql = "select * from customer_wx where openid= '"+openid+"'";
		ResultSet rs = null;
		try {
			rs = db.select(sql);
			if(rs.next()) {
				customerWx = new CustomerWx();
				customerWx.setOpenid(openid);
				customerWx.setTaxNO(rs.getString("taxNO"));
				customerWx.setCompanyName(rs.getString("companyName"));
				customerWx.setCustomerId(rs.getInt("customerId"));
				customerWx.setCustomerWxId(rs.getInt("customerWxId"));
				customerWx.setNickname(rs.getString("nickname"));
				customerWx.setSex(rs.getInt("sex"));
				customerWx.setCity(rs.getString("city"));
				customerWx.setProvince(rs.getString("province"));
				customerWx.setCountry(rs.getString("country"));
				customerWx.setHeadimgurl(rs.getString("headimgurl"));
				customerWx.setPrivilege(rs.getString("privilege"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeResultSet(rs);
		}
		return customerWx;
	}
	
	
	public void insertCustomerWx(CustomerWx customerWx) {
		String sql ="insert into customer_wx(openid, nickname, sex, province, city, country, headimgurl, privilege, taxNO, customerId, companyName) values("
				+ "'"+customerWx.getOpenid()+"',"
				+ "'"+customerWx.getNickname()+"',"
				+ "'"+customerWx.getSex()+"',"
				+ "'"+customerWx.getProvince()+"',"
				+ "'"+customerWx.getCity()+"',"
				+ "'"+customerWx.getCountry()+"',"
				+ "'"+customerWx.getHeadimgurl()+"',"
				+ "'"+customerWx.getPrivilege()+"',"
				+ "'"+customerWx.getTaxNO()+"',"
				+ "'"+customerWx.getCustomerId()+"',"
				+ "'"+customerWx.getCompanyName()+"'"
				+ ")";
		try {
			db.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateCustomerWx(CustomerWx customerWx) {
		String sql = "update customer_wx set "
				+ "nickname = '"+customerWx.getNickname()+"', "
				+ "sex = '"+customerWx.getSex()+"', "
				+ "province = '"+customerWx.getProvince()+"', "
				+ "city = '"+customerWx.getCity()+"', "
				+ "country = '"+customerWx.getCountry()+"', "
				+ "headimgurl = '"+customerWx.getHeadimgurl()+"', "
				+ "privilege = '"+customerWx.getPrivilege()+"', "
				+ "taxNO = '"+customerWx.getTaxNO()+"', "
				+ "customerId = '"+customerWx.getCustomerId()+"', "
				+ "companyName = '"+customerWx.getCompanyName()+"'"
				+ "where openid = '"+customerWx.getOpenid()+"' ";
		try {
			db.executeUpdate(sql);
			//System.out.println(customerWx.getTaxNO());
			//System.out.println(customerWx.getCompanyName());
			//System.out.println(customerWx.getCustomerId());
			//System.out.println("------");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从session中获取curCustomerWx对象
	 * @param request
	 * @return
	 */
	public CustomerWx getCustomerWxSession(HttpServletRequest request) {
		CustomerWx customerWx = (CustomerWx)request.getSession().getAttribute("curCustomerWx");
		return customerWx;
	}
	
	public boolean isTaxNo(String taxNo){
//		String regEx="^(442000)[0-9X]{9,12}$";
		String regEx="^(442000)";
		java.util.regex.Pattern patten=java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m=patten.matcher(taxNo);
		return m.find();
	}
	
	public boolean exitsTaxNO(String taxNO){
		String sql = "select * from customer where taxNO = '"+taxNO+"'";
		ResultSet rs = null;
		boolean flag = false;
		try {
			rs = db.select(sql);
			if(rs.next()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.closeResultSet(rs);
		}
		return flag;
	}
	
	public boolean exitsCustomerId(int customerId) {
		String sql = "select * from customer_wx where customerId = '"+customerId+"'";
		ResultSet rs = null;
		boolean flag = false;
		try {
			rs = db.select(sql);
			if(rs.next()){
				flag = true;
			} else {
				flag = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.closeResultSet(rs);
		}
		return flag;
	}
}
