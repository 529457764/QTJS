package cn.qtone.modules.product.obj;

public class ProductPrice {
	
	private int productPriceId;
	
	private int productId;
	
	private String priceName;
	
	private float price;
	
	private int priceUnit;
	
	//单位的中文
	private String priceUnitName;
	
	//价格类型：1金税家园2商城价3市场价4团购价
	private int priceType;

	public ProductPrice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public int getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(int priceUnit) {
		this.priceUnit = priceUnit;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductPriceId() {
		return productPriceId;
	}

	public void setProductPriceId(int productPriceId) {
		this.productPriceId = productPriceId;
	}

	public String getPriceUnitName() {
		return priceUnitName;
	}

	public void setPriceUnitName(String priceUnitName) {
		this.priceUnitName = priceUnitName;
	}

	public int getPriceType() {
		return priceType;
	}

	public void setPriceType(int priceType) {
		this.priceType = priceType;
	}

}
