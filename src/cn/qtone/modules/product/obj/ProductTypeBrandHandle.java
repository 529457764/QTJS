package cn.qtone.modules.product.obj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.*;

public class ProductTypeBrandHandle extends BaseHandle{

	public ProductTypeBrandHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据类型和品牌对应表id得到类型和品牌对应关系对象
	 * @param productTypeBrandId
	 * @return
	 */
	public ProductTypeBrand getProTypeBrandById(int productTypeBrandId){
		String sql="SELECT * FROM product_type_brand WHERE productTypeBrandId="+productTypeBrandId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				ProductTypeBrand productTypeBrand=new ProductTypeBrand();
				productTypeBrand.setProductBrandId(rs.getInt("productBrandId"));
				productTypeBrand.setProductTypeId(rs.getInt("productTypeId"));
				productTypeBrand.setProductTypeBrandId(rs.getInt("productTypeBrandId"));
				return productTypeBrand;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	/**
	 * 删除类型对应的品牌
	 * @param productTypeId 类型id
	 * @param brandIds 要删除的品牌
	 * @return
	 */
	public boolean delete(int productTypeId,String brandIds){
		String sql="DELETE FROM product_type_brand WHERE productTypeId="+productTypeId;
		if(!StringFunction.isEmpty(brandIds)){
			sql+=" AND productBrandId IN ("+brandIds+")";
		}
		return db.execute(sql);
	}
	
	/**
	 * 添加类型和品牌对应关系
	 * @param typeId 类型id
	 * @param brandIds 品牌id，多个用逗号隔开
	 * @return
	 */
	public int add(int typeId,String brandIds){
		int successNum=0;
		
		if(cn.qtone.utils.StringFunction.isEmpty(brandIds)){
			return 0;
		}
		
		String[] aryBrandId=brandIds.split(",");
		if(aryBrandId.length==0){
			return 0;
		}
		
		for(int i=0;i<aryBrandId.length;i++){
			int brandId=cn.qtone.utils.ParseTool.parseInt(aryBrandId[i],0);
			if(brandId==0) continue;
			if(this.isExit(typeId, brandId)) continue;
			if(this.add(typeId, brandId)){
				successNum++;
			}
		}
		
		return successNum;
	}

	public boolean isExit(int typeId,int brandId){
		String sql="SELECT COUNT(*) FROM product_type_brand WHERE productTypeId="+typeId;
		sql+=" AND productBrandId="+brandId;
		return db.countRowMain(sql)>0?true:false;
	}
	
	public boolean add(int typeId,int brandId){
		String sql="INSERT INTO product_type_brand SET productTypeId="+typeId+",productBrandId="+brandId;
		return db.execute(sql);
	}
	/**
	 * 获取所有类型和品牌的对应关系
	 * @return
	 */
	public List search(){
		List list=new ArrayList();
		String sql="SELECT t.productTypeId,t.typeName,b.productBrandId,b.brandName";
		sql+=" FROM product_type_brand AS tb LEFT JOIN product_type AS t ON tb.productTypeId=t.productTypeId";
		sql+=" LEFT JOIN product_brand AS b ON tb.productBrandId=b.productBrandId";
		sql+=" ORDER BY tb.orderId desc,tb.productTypeBrandId desc";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				ProductTypeBrand typeBrand=new ProductTypeBrand();

				ProductType type=new ProductType();
				type.setProductTypeId(rs.getInt("t.productTypeId"));
				type.setTypeName(rs.getString("t.typeName"));
				
				ProductBrand brand=new ProductBrand();
				brand.setProductBrandId(rs.getInt("b.productBrandId"));
				brand.setBrandName(rs.getString("b.brandName"));
				
				typeBrand.setProductBrand(brand);
				typeBrand.setProductType(type);
				
				list.add(typeBrand);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return list;
	}
}
