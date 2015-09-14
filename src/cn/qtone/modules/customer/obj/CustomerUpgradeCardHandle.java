package cn.qtone.modules.customer.obj;

import cn.qtone.sys.base.BaseHandle;

public class CustomerUpgradeCardHandle extends BaseHandle{

	private static String tableName="customer_upgradeCard";
	
	public CustomerUpgradeCardHandle(){
		super();
	}
	
	public boolean isExist(CustomerUpgradeCard upgradeCard){
		String sql="SELECT COUNT(*) FROM "+this.tableName+" WHERE taxNO='"+upgradeCard.getTaxNO()+"'";
		sql+=" AND deviceNO='"+upgradeCard.getDeviceNO()+"'";
		return db.countRow(sql)>0?true:false;
	}
	
	public boolean insert(CustomerUpgradeCard customerUpgradeCard){
		String sql="INSERT INTO "+this.tableName+" SET taxNO='"+customerUpgradeCard.getTaxNO()+"'";
		sql+=",companyName='"+customerUpgradeCard.getCompanyName()+"'";
		sql+=",cost="+customerUpgradeCard.getCost();
		sql+=",flag="+customerUpgradeCard.getFlag();
		sql+=",deviceNO='"+customerUpgradeCard.getDeviceNO()+"'";
		sql+=",upgradeDate='"+customerUpgradeCard.getUpgradeDate()+"'";
		sql+=",importDate=CURRENT_TIMESTAMP";
		return db.execute(sql);
		
	}
}
