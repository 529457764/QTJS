package cn.qtone.modules.product.obj;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class ProductTypeHandle extends BaseHandle{
	

	public ProductTypeHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/**
	 * 根据父级id，取得类型列表 (可以包含孙级列表)
	 * @param parentId
	 * @param getLevel 要取得的子类型深度
	 * @param currentLevel 当前子类型等级
	 * @return
	 */
	public List searchByParentId(int parentId){
		
		List list=new ArrayList();		
		String sql="SELECT productTypeId,typeName FROM product_type WHERE site=2 AND parentId="+parentId;
		ResultSet rs=null;		
		try {
			rs=db.select(sql);
			while(rs.next()){
				ProductType type=new ProductType();
				type.setProductTypeId(rs.getInt("productTypeId"));
				type.setTypeName(rs.getString("typeName"));		
				if(db.countRow("SELECT COUNT(*) FROM product_type WHERE site=2 AND parentId="+type.getProductTypeId())>0){
					type.setChildrenList(this.searchByParentId(type.getProductTypeId()));
				}
				list.add(type);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return list;
	}
	
	
	/***
	 * 根据父id，取得包括父类型、子类型所有的id
	 * @param parentId
	 * @param ids
	 * @return
	 */
	public String getShopTypeIdsByParentId(int parentId){	
		StringBuffer ids=new StringBuffer();
		ids.append(parentId);
		this.getShopTypeIdsByParentId(parentId,ids);
		return ids.toString();
	}
	/***
	 * 根据父id，取得所有子类型id
	 * @param parentId
	 * @param ids
	 * @return
	 */
	private void getShopTypeIdsByParentId(int parentId,StringBuffer ids){		
		String sql="SELECT productTypeId FROM product_type WHERE site=2 AND parentId="+parentId;
		ResultSet rs=null;		
		try {
			rs=db.select(sql);
			while(rs.next()){
				ids.append(",");
				ids.append(rs.getInt("productTypeId"));
				if(db.countRow("SELECT COUNT(*) FROM product_type WHERE site=2 AND parentId="+rs.getInt("productTypeId"))>0){
					this.getShopTypeIdsByParentId(rs.getInt("productTypeId"),ids);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
	}
	
	
	
	
	public ProductType getProductTypeById(int productTypeId,boolean isIncludeBrand){
		String sql="SELECT productTypeId,typeName FROM product_type WHERE productTypeId="+productTypeId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				ProductType type=new ProductType();
				type.setProductTypeId(rs.getInt("productTypeId"));
				type.setTypeName(rs.getString("typeName"));
				
				if(isIncludeBrand){
					ProductBrandHandle productBrandHandle=new ProductBrandHandle();
					List brandList=productBrandHandle.search(type.getProductTypeId());
					type.setBrandList(brandList);
				}
				return type;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	public List search(int site,boolean includeBrand,boolean isGetChildren){
		return this.search(site,1,0,includeBrand,isGetChildren);
	}
	
	/**
	 * 搜索产品类型
	 * @param includeBrand true包括品牌数据 false不包括品牌数据
	 * @param isUseInRepair 是否只搜索在送修中显示的产品类型 2是
	 * @param siteType 所属站点 1:金税家园 2商城
	 * @param parentId 上级id
	 * @param isGetChildren 是否读取下级分类 true读取
	 * @return
	 */
	public List search(int site,int isUseInRepair,int parentId,boolean includeBrand,boolean isGetChildren){
		List list=new ArrayList();
		String sql="SELECT productTypeId,typeName FROM product_type WHERE 1=1";
		if(isUseInRepair==2){
			sql+=" AND isUseInRepair=2";
		}
		if(site>0){
			sql+=" AND site="+site;
		}
		if(parentId>=0){
			sql+=" AND parentId="+parentId;
		}
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				ProductType type=new ProductType();
				type.setProductTypeId(rs.getInt("productTypeId"));
				type.setTypeName(rs.getString("typeName"));
				
				if(includeBrand){
					ProductBrandHandle productBrandHandle=new ProductBrandHandle();
					List brandList=productBrandHandle.search(type.getProductTypeId());
					type.setBrandList(brandList);
				}
				
				if(isGetChildren){//读取下级类型
					type.setChildrenList(this.search(site,isUseInRepair,type.getProductTypeId(),includeBrand,isGetChildren));
				}
				
				list.add(type);
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
	 * 将所有type放入map
	 * @return
	 */
	public Hashtable creatProductTypeMap(){
		Hashtable hm=new Hashtable();
		String sql="SELECT productTypeId,typeName FROM product_type";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				hm.put(rs.getString("productTypeId"),rs.getString("typeName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return hm;
	}
	
	/**
	 * 判断类型是否存在
	 * @param typeName
	 * @return
	 */
	public boolean isExit(String typeName){
		String sql="SELECT COUNT(*) FROM product_type WHERE typeName='"+db.filterStr(typeName)+"'";
		return db.countRow(sql)>0?true:false;
	}
	
	/**
	 * 添加类型
	 * @return
	 */
	public boolean add(ProductType productType){
		String sql="INSERT INTO product_type SET typeName='"+db.filterStr(productType.getTypeName())+"'";
		return db.execute(sql);
	}
	
	public boolean update(ProductType productType){
		String sql="UPDATE product_type SET typeName='"+db.filterStr(productType.getTypeName())+"' WHERE productTypeId="+productType.getProductTypeId();
		return db.execute(sql);		
	}
	
	/**
	 * 根据类型名称得到类型id
	 * @param typeName
	 * @return
	 */
	public int getTypeId(String typeName){
		String sql="SELECT productTypeId FROM product_type WHERE typeName='"+db.filterStr(typeName)+"'";
		sql+=" ORDER BY productTypeId desc limit 1";
		return db.countRowMain(sql);
	}
}
