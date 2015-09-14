package cn.qtone.modules.product.obj;

import java.util.List;

public class ProductType {
	
	private int productTypeId;
	
	private String typeName;
	
	//包含的品牌数据
	private List brandList;
	
	//下级类型
	private List childrenList;
	
	/**
	 * 所属站点：1金税家园 2商城
	 */
	private int site;

	public ProductType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List getBrandList() {
		return brandList;
	}

	public void setBrandList(List brandList) {
		this.brandList = brandList;
	}

	public List getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List childrenList) {
		this.childrenList = childrenList;
	}

	public int getSite() {
		return site;
	}

	public void setSite(int site) {
		this.site = site;
	}	

	
	
}
