package cn.qtone.modules.customer.obj;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class CustomerPicHandle extends BaseHandle{

	public CustomerPicHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 取得公司图片列表
	 * @param customerId
	 * @param flag 是否为列表图片 2是列表图片 1.不是列表图 0不限
	 * @return
	 */
	public List getCustomerPicList(int customerId,int flag){
		String sql="SELECT * FROM customer_pic WHERE customerId="+customerId;
		if(flag>0) sql+=" AND flag="+flag;
		sql+=" ORDER BY flag DESC";
		List list=new ArrayList();
		ResultSet rs=null;
		CustomerPic customerPic=null;
		try{
			rs=db.select(sql);
			while(rs.next()){
				customerPic=new CustomerPic();
				customerPic.setPicPath(rs.getString("picPath"));
				customerPic.setFlag(rs.getInt("flag"));
				customerPic.setCustomerId(rs.getInt("customerId"));
				customerPic.setCustomerPicId(rs.getInt("customerPicId"));
				list.add(customerPic);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
		}
		return list;
	}
	
	public void addPic(List customerPicList){
		if(customerPicList==null) return;
		
		for(int i=0;i<customerPicList.size();i++){
			CustomerPic customerPic=(CustomerPic)customerPicList.get(i);
			this.addPic(customerPic);
		}
	}
	
	/**
	 * 添加图片
	 * @param productId
	 * @param picpatch
	 * @param flag
	 * @return
	 */
	public boolean addPic(CustomerPic customerPic){
		String sql=" INSERT INTO customer_pic SET picPath='"+db.filterStr(customerPic.getPicPath())+"'" +
				",flag="+customerPic.getFlag()+",customerId="+customerPic.getCustomerId();
		sql+=",createDate=CURRENT_TIMESTAMP";
		return db.execute(sql);
	}

	/**
	 *删除单张图片
	 * @param productPicId
	 * @return
	 */
	public boolean delPic(int customerPicId,String picPath){
		if(customerPicId>0){
			String sql=" DELETE FROM  customer_pic  WHERE customerPicId="+customerPicId;
			if(!db.execute(sql)){
				return false;
			}
		}
		if(!StringFunction.isEmpty(picPath)){
			java.io.File file=new java.io.File(picPath);
			if(file.exists()&&file.isFile()) file.delete(); 
		}
		return true;
		
		
	}
	
}
