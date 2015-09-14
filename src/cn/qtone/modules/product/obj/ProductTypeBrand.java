package cn.qtone.modules.product.obj;

public class ProductTypeBrand {
	
	private ProductType productType;
	
	private ProductBrand productBrand;
	
	private int productTypeId;
	
	private int productBrandId;
	
	private int productTypeBrandId;
	
	private int orderId;

	public ProductTypeBrand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductBrand getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public int getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(int productBrandId) {
		this.productBrandId = productBrandId;
	}

	public int getProductTypeBrandId() {
		return productTypeBrandId;
	}

	public void setProductTypeBrandId(int productTypeBrandId) {
		this.productTypeBrandId = productTypeBrandId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
}
