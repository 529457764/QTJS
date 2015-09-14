package cn.qtone.modules.product.obj;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.*;

public class ProductPicHandle extends BaseHandle {

	public ProductPicHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 取得产品图片列表
	 * @param productId
	 * @return
	 */
	public List getProductPicList(int productId){
		String sql="SELECT * FROM product_pic WHERE productId="+productId+" ORDER BY flag DESC";
		List list=new ArrayList();
		ResultSet rs=null;
		ProductPic productPic=null;
		try{
			rs=db.select(sql);
			while(rs.next()){
				productPic=new ProductPic();
				productPic.setFlag(rs.getInt("flag"));
				productPic.setPicPath(rs.getString("picPath"));
				productPic.setProductId(rs.getInt("productId"));
				productPic.setProductPicId(rs.getInt("productPicId"));
				list.add(productPic);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
		}
		return list;
	}
	
	public void addPic(List productPicList){
		if(productPicList==null) return;
		
		for(int i=0;i<productPicList.size();i++){
			ProductPic productPic=(ProductPic)productPicList.get(i);
			this.addPic(productPic);
		}
	}
	
	/**
	 * 添加图片
	 * @param productId
	 * @param picpatch
	 * @param flag
	 * @return
	 */
	public boolean addPic(ProductPic productPic){
		String sql=" INSERT INTO product_pic SET picPath='"+db.filterStr(productPic.getPicPath())+"'" +
				",flag="+productPic.getFlag()+" ,productId="+productPic.getProductId();
		return db.execute(sql);
	}

	/**
	 *删除单张图片
	 * @param productPicId
	 * @return
	 */
	public boolean delPic(int productPicId,String picPath){
		if(productPicId>0){
			String sql=" DELETE FROM  product_pic  WHERE productPicId="+productPicId;
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
