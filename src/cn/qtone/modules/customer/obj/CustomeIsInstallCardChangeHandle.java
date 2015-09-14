package cn.qtone.modules.customer.obj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class CustomeIsInstallCardChangeHandle extends BaseHandle{
	public CustomeIsInstallCardChangeHandle(){
		super();
	}
	
	public boolean check(int customeIsInstallCardChangeId,int isCheck,int checker){
		String sql="UPDATE customer_isInstallCard_change SET isCheck="+isCheck;
		sql+=",checker="+checker+",checkDate=CURRENT_TIMESTAMP";
		sql+=" WHERE customeIsInstallCardChangeId="+customeIsInstallCardChangeId;
		return db.execute(sql);
	}
	
	public boolean insert(CustomeIsInstallCardChange customeIsInstallCardChange){
		StringBuffer sql=new StringBuffer();
		sql.append("INSERT INTO customer_isInstallCard_change SET customerId=").append(customeIsInstallCardChange.getCustomerId());
		sql.append(",isInstallCardFrom=").append(customeIsInstallCardChange.getIsInstallCardFrom());
		sql.append(",isInstallCardTo=").append(customeIsInstallCardChange.getIsInstallCardTo());
		sql.append(",modifier=").append(customeIsInstallCardChange.getModifier());
		sql.append(",modifyDate=CURRENT_TIMESTAMP");
		sql.append(",isCheck=").append(customeIsInstallCardChange.getIsCheck());
		return db.execute(sql.toString());
	}
	
	public int totalRow(SearchCustomeIsInstallCardChangeParam searchParam){
		String sql="SELECT COUNT(*) FROM customer_isInstallCard_change AS cic,customer AS c WHERE cic.customerId=c.customerId";
		sql+=this.sqlWhere(searchParam);
		return db.countRow(sql);
	}
	
	/**
	 * 是否有未审核的记录
	 * @param customerId
	 * @return true 有 false 没有
	 */
	public boolean hasUnCheck(int customerId){
		String sql="SELECT COUNT(*) FROM customer_isInstallCard_change WHERE isCheck=1 and customerId="+customerId;
		return db.countRow(sql)>0?true:false;
	}
	
	public List search(SearchCustomeIsInstallCardChangeParam searchParam,int startPos,int num){
		List list=new ArrayList();		
		String sql="SELECT cic.*,c.companyName FROM customer_isInstallCard_change AS cic,customer AS c WHERE cic.customerId=c.customerId";
		sql+=this.sqlWhere(searchParam);	
		sql+=" ORDER BY cic.modifyDate DESC";
		if(num!=-1) sql+=" LIMIT "+startPos+","+num;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				CustomeIsInstallCardChange customeIsInstallCardChange=this.getByRs(rs);
				
				list.add(customeIsInstallCardChange);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return list;
		
	}
		
	private CustomeIsInstallCardChange getByRs(ResultSet rs){
		CustomeIsInstallCardChange customeIsInstallCardChange;
		try {
			customeIsInstallCardChange = new CustomeIsInstallCardChange();
			customeIsInstallCardChange.setCheckDate(rs.getString("cic.checkDate"));
			customeIsInstallCardChange.setChecker(rs.getInt("cic.checker"));
			customeIsInstallCardChange.setCustomeIsInstallCardChangeId(rs.getInt("customeIsInstallCardChangeId"));
			customeIsInstallCardChange.setCustomerId(rs.getInt("cic.customerId"));
			customeIsInstallCardChange.setIsCheck(rs.getInt("cic.isCheck"));
			customeIsInstallCardChange.setIsInstallCardFrom(rs.getInt("cic.isInstallCardFrom"));
			customeIsInstallCardChange.setIsInstallCardTo(rs.getInt("cic.isInstallCardTo"));
			customeIsInstallCardChange.setModifier(rs.getInt("cic.modifier"));
			customeIsInstallCardChange.setModifyDate(rs.getString("cic.modifyDate"));

			Customer customer=new Customer();
			customer.setCompanyName(rs.getString("c.companyName"));
			customeIsInstallCardChange.setCustomer(customer);

			return customeIsInstallCardChange;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
		
		return null;
	}
	
	private String sqlWhere(SearchCustomeIsInstallCardChangeParam searchParam){
		StringBuffer sqlBuf=new StringBuffer();
		if(searchParam.getIsCheck()>0){
			sqlBuf.append(" AND cic.isCheck=").append(searchParam.getIsCheck());
		}
		if(!StringFunction.isEmpty(searchParam.getCompanyName())){
			sqlBuf.append(" AND c.companyName like '%").append(searchParam.getCompanyName()).append("%'");
		}
		if(!StringFunction.isEmpty(searchParam.getTaxNO())){
			sqlBuf.append(" AND c.taxNO like '%").append(searchParam.getTaxNO()).append("%'");
		}
		return sqlBuf.toString();
	}
	
	public CustomeIsInstallCardChange getById(int customeIsInstallCardChangeId){
		String sql="SELECT cic.*,c.companyName FROM customer_isInstallCard_change AS cic,customer AS c WHERE cic.customerId=c.customerId";
		sql+=" AND customeIsInstallCardChangeId="+customeIsInstallCardChangeId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				CustomeIsInstallCardChange customeIsInstallCardChange=this.getByRs(rs);
				return customeIsInstallCardChange;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return null;
		
	}
}
