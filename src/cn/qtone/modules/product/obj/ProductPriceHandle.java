package cn.qtone.modules.product.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class ProductPriceHandle  extends BaseHandle{

	public ProductPriceHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据产品id，删除产品价格
	 * @param productIds 产品id，多个用逗号隔开
	 * @return
	 */
	public boolean delPriceByProductId(String productIds){
		if(StringFunction.isEmpty(productIds)){
			return true;
		}
		String sql="DELETE FROM product_price WHERE productId in ("+productIds+")";
		return db.execute(sql);
	}
	/**
	 * 得到产品的最低价，最低价的定义为显示在最前面的价格
	 * @param productId 产品id
	 * @return
	 */
	public ProductPrice getLowestPrice(int productId){
		if(productId<=0) return null;
		String sql="SELECT * FROM product_price WHERE productId="+productId+" ORDER BY productPriceId asc limit 1";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				ProductPrice productPrice=new ProductPrice();
				productPrice.setPrice(rs.getFloat("price"));
				productPrice.setPriceName(rs.getString("priceName"));
				productPrice.setPriceUnit(rs.getInt("priceUnit"));
				productPrice.setProductId(rs.getInt("productId"));
				productPrice.setPriceType(rs.getInt("priceType"));
				productPrice.setProductPriceId(rs.getInt("productPriceId"));
				
				return productPrice;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	/**
	 * 根据产品价格id得到产品价格对象
	 * @param productPriceId
	 * @return
	 */
	public ProductPrice getProductPriceById(int productPriceId){
		String sql="SELECT * FROM product_price WHERE productPriceId="+productPriceId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				ProductPrice productPrice=new ProductPrice();
				productPrice.setPrice(rs.getFloat("price"));
				productPrice.setPriceName(rs.getString("priceName"));
				productPrice.setPriceUnit(rs.getInt("priceUnit"));
				productPrice.setProductId(rs.getInt("productId"));
				productPrice.setPriceType(rs.getInt("priceType"));
				productPrice.setProductPriceId(rs.getInt("productPriceId"));
				return productPrice;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	public List getProductPrice(int productId){
		List productPriceList=new ArrayList();
		
		String sql="SELECT * FROM product_price WHERE productId="+productId+" ORDER BY productPriceId asc";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				ProductPrice productPrice=new ProductPrice();
				productPrice.setPrice(rs.getFloat("price"));
				productPrice.setPriceName(rs.getString("priceName"));
				productPrice.setPriceUnit(rs.getInt("priceUnit"));
				productPrice.setProductId(rs.getInt("productId"));
				productPrice.setProductPriceId(rs.getInt("productPriceId"));
				productPrice.setPriceType(rs.getInt("priceType"));
				productPrice.setPriceUnitName((String)cn.qtone.modules.product.Const.getPriceUnit().get(productPrice.getPriceUnit()+""));
				productPriceList.add(productPrice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		
		return productPriceList;
	}
	/**
	 * 更新产品的价格，
	 * @param productPriceList 产品价格list
	 * @return
	 */
	public boolean updateProductPrice(List productPriceList){
		if(productPriceList==null) return true;
		
		for(int i=0;i<productPriceList.size();i++){
			ProductPrice productPrice=(ProductPrice)productPriceList.get(i);
			if(productPrice.getProductPriceId()>0){//更新
				this.updateProductPrice(productPrice);
			}else{//添加
				this.addProductPrice(productPrice);
			}
		}
		
		return true;
	}
	
	/**
	 * 删除指定产品的排除指定价格id的价格
	 * @param productPriceIds 要排除的价格id
	 * @param productId 要删除产品价格的产品id
	 * @return
	 */
	public boolean deleteOutPriceId(String productPriceIds,int productId){
		if(productId<=0) return false;
		
		String sql="DELETE FROM product_price WHERE productId="+productId;
		if(!StringFunction.isEmpty(productPriceIds)){
			sql+=" AND productPriceId not in ("+productPriceIds+")";
		}
		
		return db.execute(sql);
	}
	
	/**
	 * 更新产品价格
	 * @param productPrice 产品价格对象
	 * @return
	 */
	public boolean updateProductPrice(ProductPrice productPrice){
		String sql="UPDATE product_price SET priceName='"+db.filterStr(productPrice.getPriceName())+"'";
		sql+=",price="+productPrice.getPrice();
		sql+=",priceUnit="+productPrice.getPriceUnit();
		sql+=",productId="+productPrice.getProductId();
		sql+=",priceType="+productPrice.getPriceType();		
		sql+=" WHERE productPriceId="+productPrice.getProductPriceId();
		return db.execute(sql);
	}
	
	/**
	 * 添加产品价格
	 * @param productPrice 产品价格对象
	 * @return
	 */
	public boolean addProductPrice(ProductPrice productPrice){
		String sql="INSERT INTO product_price SET priceName='"+db.filterStr(productPrice.getPriceName())+"'";
		sql+=",price="+productPrice.getPrice();
		sql+=",priceUnit="+productPrice.getPriceUnit();
		sql+=",productId="+productPrice.getProductId();
		sql+=",priceType="+productPrice.getPriceType();
		return db.execute(sql);
	}
	
}
