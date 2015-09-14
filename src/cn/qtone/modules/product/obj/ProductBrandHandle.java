package cn.qtone.modules.product.obj;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;

public class ProductBrandHandle extends BaseHandle{

	public ProductBrandHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean add(ProductBrand brand){
		String sql="INSERT INTO product_brand SET brandName='"+db.filterStr(brand.getBrandName())+"'";
		return db.execute(sql);
	}
	
	public boolean isExit(String brandName){
		String sql="SELECT COUNT(*) FROM product_brand WHERE brandName='"+brandName+"'";
		return db.countRow(sql)>0?true:false;
	}
	
	public int getBrandId(String brandName){
		String sql="SELECT productBrandId FROM product_brand WHERE brandName='"+brandName+"'";
		return db.countRowMain(sql);
	}


	
	
	/**
	 * 搜索产品的品牌
	 * @ param productTypeId 产品类型
	 */
	public List search(int productTypeId){
		List list=new ArrayList();

		String sql="SELECT b.productBrandId,b.brandName";
		sql+=" FROM product_brand as b";
		if(productTypeId>0){
			sql+=",product_type_brand as tb";
			sql+=" WHERE tb.productTypeId="+productTypeId+" AND b.productBrandId=tb.productBrandId";
		}
		
		sql+=" ORDER BY b.productBrandId desc";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				ProductBrand brand=new ProductBrand();
				brand.setBrandName(rs.getString("b.brandName"));
				brand.setProductBrandId(rs.getInt("b.productBrandId"));
				
				list.add(brand);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return list;
	}

	/**
	 * 将所有brand放入map
	 * @return
	 */
	public Hashtable creatProductBrandMap(){
		Hashtable hm=new Hashtable();
		String sql="SELECT productBrandId,brandName FROM product_brand";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				hm.put(rs.getString("productBrandId"),rs.getString("brandName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return hm;
	}
	
	public ProductBrand getProductBrandById(int productBrandId){
		ProductBrand productBrand=new ProductBrand();
		String sql="SELECT * FROM product_brand WHERE productBrandId="+productBrandId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				productBrand.setBrandName(rs.getString("brandName"));
				productBrand.setProductBrandId(rs.getInt("productBrandId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return productBrand;
	}
}
