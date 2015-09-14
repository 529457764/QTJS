package cn.qtone.modules.customer.obj;

import cn.qtone.sys.base.BaseHandle;

public class CustomerFollowerInfoHandle extends BaseHandle {

	public CustomerFollowerInfoHandle(){
		super();
	}
	
	/***
	 * 保存记录
	 * @param info
	 * @return
	 */
	public int saveRow(CustomerFollowerInfo  info){
		if(this.getIsAssign(info.getCustomerId(), info.getFollowType())>0){
			return this.updateRow(info);
		}else{
			return this.insertRow(info);
		}
	}
	/**
	 * 是否已经分配
	 * @param customerId
	 * @param followType
	 * @return
	 */
	public int getIsAssign(int customerId,int followType ){
		String sql="SELECT COUNT(*) FROM  customer_follower_info WHERE customerId="+customerId+" AND followType="+followType;
		return db.getValueInt(sql);
	}
	
	/**
	 * 插入记录
	 * @param info
	 * @return
	 */
	public int insertRow(CustomerFollowerInfo  info){
		String sql=" INSERT INTO customer_follower_info SET customerId="+info.getCustomerId()
		          +",followType="+info.getFollowType()
		          +",followerId="+info.getFollowerId()
		          +",assignerId="+info.getAssignerId()
		          +",assignDate=CURRENT_TIMESTAMP ";
	   return db.executeUpdate(sql);
	}
	/**
	 * 更新记录
	 * @param info
	 * @return
	 */
	public int updateRow(CustomerFollowerInfo  info){
		String sql=" UPDATE customer_follower_info SET customerId="+info.getCustomerId()
		          +",followerId="+info.getFollowerId()
		          +",assignerId="+info.getAssignerId()
		          +",assignDate=CURRENT_TIMESTAMP " 
		          +" WHERE customerId="+info.getCustomerId()+" AND followType="+info.getFollowType();
	   return db.executeUpdate(sql);
	}
}
