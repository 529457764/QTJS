package cn.qtone.modules.product.obj;

public class ProductHot {

	public ProductHot() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int productHotId;
	private int productId;
	private int flag;
	private int createDate;
	public int getCreateDate() {
		return createDate;
	}
	public void setCreateDate(int createDate) {
		this.createDate = createDate;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getProductHotId() {
		return productHotId;
	}
	public void setProductHotId(int productHotId) {
		this.productHotId = productHotId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
