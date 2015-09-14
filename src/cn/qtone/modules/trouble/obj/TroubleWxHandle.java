package cn.qtone.modules.trouble.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.modules.customer.obj.CustomerHandle;
import cn.qtone.modules.customer.obj.CustomerWx;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class TroubleWxHandle extends BaseHandle {
	public TroubleWxHandle () {
		super();
	}
	
	/**
	 * 把数据添加到trouble表中
	 * @param troubleWx
	 */
	public void addTrouble(Trouble troubleWx) {
		//ResultSet rs = null;
		try {
			//获取customerId
			String sql = "insert into trouble(companyName, address, cityId, linkman, tel, troubleTypeId, content, customerId) values( "
					+ "companyName = '"+troubleWx.getCompanyName()+"', "
					+ "address = '"+troubleWx.getAddress()+"', "
					+ "cityId = '"+troubleWx.getCityId()+"', "
					+ "linkman = '"+troubleWx.getLinkman()+"', "
					+ "tel = '"+troubleWx.getTel()+"', "
					+ "troubleTypeId = '"+troubleWx.getTroubleTypeId()+"', "
					+ "content = '"+troubleWx.getContent()+"', "
					+ "customerId = '"+troubleWx.getCustomerId()+"' )";
			db.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List getTroubleWxByCustomerId(int customerId){
		List troubleWxList = new ArrayList();
		ResultSet rs = null;
		try {
			String sql = "select * from trouble where customerId = '"+customerId+"'";
			rs = db.select(sql);
			while(rs.next()) {
				Trouble troubleWx = new Trouble();
				troubleWx.setCustomerId(rs.getInt("customerId"));
				troubleWx.setCompanyName(rs.getString("companyName"));
				troubleWx.setAddress(rs.getString("address"));
				troubleWx.setCityId(rs.getInt("cityId"));
				troubleWx.setCreatDate(rs.getString("creatDate"));
				troubleWx.setContent(rs.getString("content"));
				troubleWx.setLinkman(rs.getString("linkman"));
				troubleWx.setModifyDate(rs.getString("modifyDate"));
				troubleWx.setResult(rs.getString("result"));
				troubleWx.setTel(rs.getString("tel"));
				troubleWx.setTroubleId(rs.getInt("troubleId"));
				troubleWx.setTroubleTypeId(rs.getInt("troubleTypeId"));
				troubleWxList.add(troubleWx);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return troubleWxList;
	}
	
	public Trouble getTroubleWxByTroubleId(int troubleId) {
		Trouble troubleWx = new Trouble();
		ResultSet rs = null;
		try {
			String sql = "select * from trouble where troubleId = troubleId";
			rs=db.select(sql);
			if(rs.next()) {
				troubleWx.setCustomerId(rs.getInt("customerId"));
				troubleWx.setCompanyName(rs.getString("companyName"));
				troubleWx.setAddress(rs.getString("address"));
				troubleWx.setCityId(rs.getInt("cityId"));
				troubleWx.setCreatDate(rs.getString("creatDate"));
				troubleWx.setContent(rs.getString("content"));
				troubleWx.setLinkman(rs.getString("linkman"));
				troubleWx.setModifyDate(rs.getString("modifyDate"));
				troubleWx.setResult(rs.getString("result"));
				troubleWx.setTel(rs.getString("tel"));
				troubleWx.setTroubleId(rs.getInt("troubleId"));
				troubleWx.setTroubleTypeId(rs.getInt("troubleTypeId"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return troubleWx;
	}
}
