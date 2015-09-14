package cn.qtone.modules.product.obj;

/**
 * 搜索产品参数对象
 * @author Administrator
 *
 */

public class SearchProductParam {
	
	private int productTypeId;
	
	private int productBrandId;
	
	private String productName;

	
	/**
	 * 1正常，2停用
	 */
	private int pState;
	
	/**
	 * 是否前台显示，1显示，2不显示
	 */
	private int isShow;
	
	/**
	 * 产品的id
	 */
	private String productIds;

	
	private int isShowShop; //是否在商城显示: 1是，2否，3是并且首页显示
	
	private int order;//排序 1销量，2价格，3 上架时间
	
	private int priceOrderFlag;// 1升序 2 降序
	private String shopProductTypeIds;//商城产品类型 用","隔开
	
	private int shopProductBrandId;//商城品牌
	
	/**
	 * 热点商品类型：1最新推荐 2最新上架 3热卖商品 4特卖专区
	 */
	private int hotFlag;
	
	/**
	 * 要排除的产品id
	 */
	private String ridIds;
	
	private int isFrequentlyUsed;//是否常用商品 1 否；2 是
	
	public int getIsFrequentlyUsed() {
		return isFrequentlyUsed;
	}

	public void setIsFrequentlyUsed(int isFrequentlyUsed) {
		this.isFrequentlyUsed = isFrequentlyUsed;
	}

	public int getPriceOrderFlag() {
		return priceOrderFlag;
	}

	public void setPriceOrderFlag(int priceOrderFlag) {
		this.priceOrderFlag = priceOrderFlag;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getIsShowShop() {
		return isShowShop;
	}

	public void setIsShowShop(int isShowShop) {
		this.isShowShop = isShowShop;
	}

	public SearchProductParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(int productBrandId) {
		this.productBrandId = productBrandId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public int getPState() {
		return pState;
	}

	public void setPState(int state) {
		pState = state;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public int getHotFlag() {
		return hotFlag;
	}

	public void setHotFlag(int hotFlag) {
		this.hotFlag = hotFlag;
	}

	public String getRidIds() {
		return ridIds;
	}

	public void setRidIds(String ridIds) {
		this.ridIds = ridIds;
	}

	public int getShopProductBrandId() {
		return shopProductBrandId;
	}

	public String getShopProductTypeIds() {
		return shopProductTypeIds;
	}

	public void setShopProductTypeIds(String shopProductTypeIds) {
		this.shopProductTypeIds = shopProductTypeIds;
	}

	public void setShopProductBrandId(int shopProductBrandId) {
		this.shopProductBrandId = shopProductBrandId;
	}


	
}
