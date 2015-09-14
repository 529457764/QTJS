package cn.qtone.modules.customer.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.*;

public class CustomerTaxcardHandle extends BaseHandle{
	public CustomerTaxcardHandle(){
		super();
	}
	
	/**
	 * 返回分卡的数量
	 * @param customerId
	 * @return
	 */
	public int getCardNUM(int customerId){
		String sql="SELECT COUNT(*) FROM customer_taxcard WHERE customerId="+customerId;
		return db.countRow(sql);
	}
	
	/**
	 * 根据客户id读取分卡信息
	 * @param customerId
	 * @return
	 */
	public List getTaxcardByCustomerId(int customerId){
		List taxcardList=new ArrayList();
		String sql="SELECT * FROM customer_taxcard WHERE customerId="+customerId;
		ResultSet rs=null;
		try {
			rs=this.db.select(sql);
			while(rs.next()){
				CustomerTaxcard taxcard=new CustomerTaxcard();
				taxcard.setCardNO(rs.getString("cardNO"));
				taxcard.setCreateDate(rs.getString("createDate"));
				taxcard.setCustomerId(rs.getInt("customerId"));
				taxcard.setCustomerTaxcardId(rs.getInt("customerTaxcardId"));
				taxcard.setICNO(rs.getString("ICNO"));
				taxcard.setInstallCardDate(rs.getString("installCardDate"));
				taxcard.setSysLegalNO(rs.getString("sysLegalNO"));
				taxcard.setCardNum(rs.getInt("cardNum"));
				taxcardList.add(taxcard);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		return taxcardList;
	}
	
	/**
	 * 增加金税卡
	 * @param card
	 * @return
	 */
	public boolean add(CustomerTaxcard card){
		CustomerTaxcard cardOld=this.getCustomerTaxcardId(card.getCustomerId(), card.getCardNum());
		
		if(cardOld!=null){
			card.setCustomerTaxcardId(cardOld.getCustomerTaxcardId());
			//原来已有安装日期不更新安装日期了
			return this.update(card);
		}
		
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("INSERT INTO customer_taxcard SET customerId=").append(card.getCustomerId());
		if(!StringFunction.isEmpty(card.getCardNO())){
			sqlBuf.append(",cardNO='").append(db.filterStr(card.getCardNO())).append("'");
		}
		if(!StringFunction.isEmpty(card.getICNO())){
			sqlBuf.append(",ICNO='").append(db.filterStr(card.getICNO())).append("'");
		}
		if(!StringFunction.isEmpty(card.getSysLegalNO())){
			sqlBuf.append(",sysLegalNO='").append(db.filterStr(card.getSysLegalNO())).append("'");
		}
		sqlBuf.append(",installCardDate='").append(card.getInstallCardDate()).append("'");
		sqlBuf.append(",cardNum=").append(card.getCardNum());
		sqlBuf.append(",createDate=CURRENT_TIMESTAMP");
		return db.execute(sqlBuf.toString());
	}
	
	/**
	 * 修改金税分卡
	 * @param card
	 * @return
	 */
	public boolean update(CustomerTaxcard card){
		
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("UPDATE customer_taxcard SET customerId=").append(card.getCustomerId());
		if(!StringFunction.isEmpty(card.getCardNO())){
			sqlBuf.append(",cardNO='").append(db.filterStr(card.getCardNO())).append("'");
		}
		if(!StringFunction.isEmpty(card.getICNO())){
			sqlBuf.append(",ICNO='").append(db.filterStr(card.getICNO())).append("'");
		}
		if(!StringFunction.isEmpty(card.getSysLegalNO())){
			sqlBuf.append(",sysLegalNO='").append(db.filterStr(card.getSysLegalNO())).append("'");
		}
//		if(!StringFunction.isEmpty(card.getInstallCardDate())) sqlBuf.append(",installCardDate='").append(card.getInstallCardDate()).append("'");
		sqlBuf.append(",cardNum=").append(card.getCardNum());
		sqlBuf.append(",createDate=CURRENT_TIMESTAMP");
		sqlBuf.append(" WHERE customerTaxcardId=").append(card.getCustomerTaxcardId());
		return db.execute(sqlBuf.toString());
	}
	/**
	 * 得到可以第几分机的id
	 * @param customerId
	 * @param cardNum 第几分机
	 * @return
	 */
	public CustomerTaxcard getCustomerTaxcardId(int customerId,int cardNum){
		String sql="SELECT * FROM customer_taxcard WHERE customerId="+customerId+" AND cardNum="+cardNum;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				CustomerTaxcard taxcard=new CustomerTaxcard();
				taxcard.setCardNO(rs.getString("cardNO"));
				taxcard.setCreateDate(rs.getString("createDate"));
				taxcard.setCustomerId(rs.getInt("customerId"));
				taxcard.setCustomerTaxcardId(rs.getInt("customerTaxcardId"));
				taxcard.setICNO(rs.getString("ICNO"));
				taxcard.setInstallCardDate(rs.getString("installCardDate"));
				taxcard.setSysLegalNO(rs.getString("sysLegalNO"));
				taxcard.setCardNum(rs.getInt("cardNum"));	
				return taxcard;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	/**
	 * 生成分卡对象
	 * @param customerId
	 * @param installCardDate
	 * @param sysLegalNO
	 * @param cardNO
	 * @param ICNO
	 * @param cardNum
	 * @return
	 */
	public CustomerTaxcard generateCard(int customerId,String installCardDate,String sysLegalNO,String cardNO,String ICNO,int cardNum){
		CustomerTaxcard taxcard=new CustomerTaxcard();
		taxcard.setCustomerId(customerId);
		taxcard.setInstallCardDate(installCardDate);
		taxcard.setCardNO(cardNO);
		taxcard.setCardNum(cardNum);
		taxcard.setICNO(ICNO);
		taxcard.setSysLegalNO(sysLegalNO);
		return taxcard;
	}
}
