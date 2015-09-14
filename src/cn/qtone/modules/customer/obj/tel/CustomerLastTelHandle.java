package cn.qtone.modules.customer.obj.tel;

import java.sql.*;
import java.util.*;

import cn.qtone.modules.followCus.obj.FollowCus;
import cn.qtone.modules.followCus.obj.FollowCusHandle;
import cn.qtone.modules.telRecord.obj.SearchTelRecordParam;
import cn.qtone.modules.telRecord.obj.TelRecord;
import cn.qtone.modules.telRecord.obj.TelRecordHandle;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class CustomerLastTelHandle extends BaseHandle{

	public CustomerLastTelHandle(){
		super();
		
	}
	//清空数据表，用于定时清空（放在电话绑定的定时中），防止产生过多的数据，影响速度
	public boolean del(){
		String sql="delete from customer_lastTel";
		return db.execute(sql);	
	}
	/**
	 * 增加数据
	 * @param customerTelSourceId
	 * @return
	 */
	public boolean add(CustomerLastTel customerLastTel){
		String sql="INSERT INTO customer_lastTel SET userBoId="+customerLastTel.getUserBoId();
		sql+=",customerId="+customerLastTel.getCustomerId();
		sql+=",customerTel='"+db.filterStr(customerLastTel.getCustomerTel())+"'";
		if(!StringFunction.isEmpty(customerLastTel.getCreatDate())) sql+=",creatDate='"+db.filterStr(customerLastTel.getCreatDate())+"'";
		if(!StringFunction.isEmpty(customerLastTel.getLinkman())){
			sql+=",linkman='"+db.filterStr(customerLastTel.getLinkman())+"'";
		}
		if(!StringFunction.isEmpty(customerLastTel.getCustomerName())){
			sql+=",customerName='"+customerLastTel.getCustomerName()+"'";								
		}
		if(!StringFunction.isEmpty(customerLastTel.getQuestionType())){
			sql+=",questionType='"+customerLastTel.getQuestionType()+"'";								
		}
		sql+=",createId="+customerLastTel.getCreateId();
		sql+=",source='"+db.filterStr(customerLastTel.getSource())+"'";//电话来源
		return db.execute(sql);	
	}
	
	/**
	 * 将六条数据放入对像中
	 * @return
	 */
	public void creatCustomerLastTel(int customerId,int createId){
		//创建之前先删除当前用户和当前客户的记录
		String sql="DELETE FROM customer_lastTel WHERE customerId ="+customerId+" and createId="+createId;
		 db.execute(sql);
		//增加电话登记
		SearchTelRecordParam param=new SearchTelRecordParam();
		param.setCustomerId(customerId);
		TelRecordHandle telHandle=new TelRecordHandle();
		List telList=telHandle.searchTel(param,0,3,false);
		for(int i=0;i<telList.size();i++){
			TelRecord telRecord=(TelRecord)telList.get(i);//记录待处理的客户
			CustomerLastTel lastTel=new CustomerLastTel();
			lastTel.setCustomerId(telRecord.getCustomerId());
			lastTel.setCreatDate(telRecord.getCreatDate());
			lastTel.setCustomerTel(telRecord.getCustomerTel());
			lastTel.setLinkman(telRecord.getLinkman());
			lastTel.setCustomerName(telRecord.getCompanyName());
			lastTel.setUserBoId(telRecord.getUserBoId());
			lastTel.setCreateId(createId);
			lastTel.setQuestionType(telHandle.getQuestionNameById(telRecord.getTelRecordId()));
			lastTel.setSource("电话登记");
			this.add(lastTel);
		}
		//增加新企业
		FollowCusHandle followCusHandle=new FollowCusHandle();
		List followList=followCusHandle.LastThreeTel(customerId);
		for(int j=0;j<followList.size();j++){
			FollowCus followCus=(FollowCus)followList.get(j);
			CustomerLastTel followTel=new CustomerLastTel();
			followTel.setCustomerId(followCus.getCustomerId());			
			if(followCus.getTelBinded()!=null){
				followTel.setCreatDate(followCus.getTelBinded().getStartTime());
				followTel.setCustomerTel(followCus.getTelBinded().getCompanyTel());
				followTel.setUserBoId(followCus.getTelBinded().getUserBoId());
			}

			followTel.setLinkman(followCus.getLinkman());
			followTel.setCustomerName(followCus.getCustomer().getCompanyName());
			followTel.setCreateId(createId);
			followTel.setQuestionType(followCus.getDetail());
			followTel.setSource("新企业跟进");
			this.add(followTel);
		}
	}
	
	
	//取最后三条
	public List search(int customerId,int createId){
		List lastTelList=new ArrayList();
		String sql="SELECT a.*,b.name FROM customer_lastTel as a left join user_bo b on a.userBoId=b.userBoId where a.customerId="+customerId+" and a.createId="+createId+" order by creatDate desc LIMIT 0,3";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomerLastTel lastTel=new CustomerLastTel();
				lastTel.setCustomerId(rs.getInt("a.customerId"));
				lastTel.setCreatDate(rs.getString("a.creatDate"));
				lastTel.setCustomerTel(rs.getString("a.customerTel"));
				lastTel.setLinkman(rs.getString("a.linkman"));
				lastTel.setCustomerName(rs.getString("a.customerName"));
				lastTel.setUserBoId(rs.getInt("a.userBoId"));
				//System.out.println(rs.getString("a.tState"));
				lastTel.setQuestionType(rs.getString("a.questionType"));
				lastTel.setSource(rs.getString("a.source"));
				lastTel.setBo_userName(rs.getString("b.name"));
				lastTelList.add(lastTel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return lastTelList;
	}
}
