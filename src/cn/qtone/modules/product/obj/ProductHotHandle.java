package cn.qtone.modules.product.obj;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class ProductHotHandle extends BaseHandle {

	public ProductHotHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/***
	 * 保存结果
	 * @param productId
	 * @param hotFlag
	 */
	public void save(int productId,String [] hotFlag){
		String sql="",flagIds="";
		if(hotFlag!=null){
			for(int i=0;i<hotFlag.length;i++){
				if(i==0){
					flagIds=hotFlag[i];
				}else{
					flagIds+=","+hotFlag[i];
				}
				sql=" SELECT COUNT(*) FROM product_hot WHERE productId="+productId+" AND flag="+hotFlag[i];
				if(db.countRow(sql)<=0){
					sql=" INSERT INTO product_hot SET productId="+productId+",flag="+hotFlag[i]+",createDate=CURRENT_TIMESTAMP";
					db.execute(sql);
				}
			}
			if(!StringFunction.isEmpty(flagIds)){
				db.execute(" DELETE FROM product_hot WHERE productId="+productId+" AND  flag NOT IN ("+flagIds+") ");
			}
		}else{
			db.execute(" DELETE FROM product_hot WHERE productId="+productId);			
		}		
	}
	
	/**
	 * 根据productId 取得产品所有显示类型
	 * @param productId
	 * @return
	 */
	public String [] searchByProductId(int productId){
		String sql=" SELECT flag FROM product_hot WHERE productId="+productId;
		String ids= db.getQueryResult(sql,",");
		if(StringFunction.isEmpty(ids)){
			return null;
		}else{
			return ids.split(",");
		}
	}
	
}
