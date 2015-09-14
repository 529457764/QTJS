package cn.qtone.modules.product.obj;

import java.util.*;


public class Product {
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private int productId;
	
	private String productName;
	
	//型号
	private String std;
	
	private int productTypeId;
	
	private ProductType productType;
	
	private int productBrandId;
	
	private ProductBrand productBrand;
	
	private String typeName;
	
	private String brandName;
	
	/**
	 * 是否前台显示 1是，2否，3是并且首页显示
	 */
	private int isShow;
	
	private String photo;
	
	private String detail;
	
	private List productPriceList;
	
	/**
	 * 产品状态：0正常，1停用
	 */
	private int pState;
	
	/**
	 * 保修期(天数)
	 */
	private int warranty;
	
	/**
	 * 当前日期算起的保修期
	 */
	private String warrantyDate;
	
	/**
	 * 是否记录库存
	 * 1不记录，2记录
	 */
	private int isStorage;
	
	/**
	 * 库存数 金税
	 */
	private int amount;
	
	/**
	 * 待发货数量 金税
	 */
	private int outAmount;
	
	/**
	 * 库存数  数码
	 */
	private int amountQT;
	/**
	 * 待发货数量  数码
	 */
	private int outAmountQT;

	
	/**
	 * 来源于哪个帐套，对应u8_syn_config表的configCode字段
	 */
	private int configCode;
	
	/**
	 * 
	 *排序号
	 */
	private int orderId;

	private String u8SynFlag;//u8库存同步标识
	
	//商品重量
	private float weight;
	
	private int shopProductTypeId;//商品的商城商品类型
	private int shopProductBrandId;//商品的商城品牌类型
	private String productNo;//用于代售第三方公司的商品
	private int point;//赠送积分
	private String pack;//包装清单
//	private int photoBig;//产品大图
//	private int photoSmall;//产品小图
	private int isShowShop;//是否在商城显示: 1是，2否，3是并且首页显示
	
	//供货商id
	private int productOfferId;
	
	//销售总数
	private int sellNum;
	
	private List productPicList; //产品图片
	
	private String [] hotFlag;//商城区块显示设置
	
	//售后服务
	private String service;
	
	/**
	 * 是否包邮 1不是 2是
	 */
	private int isFreeCostPost;
	
	private String creatDate;
	
	private int isFrequentlyUsed;//是否常用商品 1 否；2 是
	
	private String fqUsedName;
	
	public String getFqUsedName() {
		return fqUsedName;
	}

	public void setFqUsedName(String fqUsedName) {
		this.fqUsedName = fqUsedName;
	}

	public int getIsFrequentlyUsed() {
		return isFrequentlyUsed;
	}

	public void setIsFrequentlyUsed(int isFrequentlyUsed) {
		this.isFrequentlyUsed = isFrequentlyUsed;
	} 
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(int productBrandId) {
		this.productBrandId = productBrandId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public int getpState() {
		return pState;
	}

	public void setpState(int state) {
		pState = state;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getIsStorage() {
		return isStorage;
	}

	public void setIsStorage(int isStorage) {
		this.isStorage = isStorage;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List getProductPriceList() {
		return productPriceList;
	}

	public void setProductPriceList(List productPriceList) {
		this.productPriceList = productPriceList;
	}

	/**
	 * 读取价格最低的
	 * @return
	 */
	public ProductPrice getLowestPrice() {
		List productPriceList=this.getProductPriceList();
		if(productPriceList==null||productPriceList.size()==0){
			return null;
		}
		int i=0;
		ProductPrice productPriceLowest=(ProductPrice)productPriceList.get(0);
		for(i=1;i<productPriceList.size();i++){
			ProductPrice productPrice=(ProductPrice)productPriceList.get(i);
			if(productPriceLowest.getPrice()>productPrice.getPrice()){
				productPriceLowest=productPrice;
			}
		}
		return productPriceLowest;
	}

//	public void setLowestPrice(ProductPrice lowestPrice) {
//		this.lowestPrice = lowestPrice;
//	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public String getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getStd() {
		return std;
	}

	public void setStd(String std) {
		this.std = std;
	}

	public int getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(int outAmount) {
		this.outAmount = outAmount;
	}

	public int getConfigCode() {
		return configCode;
	}

	public void setConfigCode(int configCode) {
		this.configCode = configCode;
	}

	public int getAmountQT() {
		return amountQT;
	}

	public void setAmountQT(int amountQT) {
		this.amountQT = amountQT;
	}

	public int getOutAmountQT() {
		return outAmountQT;
	}

	public void setOutAmountQT(int outAmountQT) {
		this.outAmountQT = outAmountQT;
	}

	public String getU8SynFlag() {
		return u8SynFlag;
	}

	public void setU8SynFlag(String synFlag) {
		u8SynFlag = synFlag;
	}


	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getIsShowShop() {
		return isShowShop;
	}

	public void setIsShowShop(int isShowShop) {
		this.isShowShop = isShowShop;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public int getShopProductBrandId() {
		return shopProductBrandId;
	}

	public void setShopProductBrandId(int shopProductBrandId) {
		this.shopProductBrandId = shopProductBrandId;
	}

	public int getShopProductTypeId() {
		return shopProductTypeId;
	}

	public void setShopProductTypeId(int shopProductTypeId) {
		this.shopProductTypeId = shopProductTypeId;
	}

	
	public List getProductPicList() {
		return productPicList;
	}

	public void setProductPicList(List productPicList) {
		this.productPicList = productPicList;
	}

	/**
	 * 获取指定类型的价格
	 * @param priceType 价格类型：1金税家园2商城价3市场价4团购价
	 * @return
	 */
	public ProductPrice readProductPrice(int priceType){
		int i=0;
		List productPriceList=this.getProductPriceList();
		if(productPriceList==null||productPriceList.size()==0){
			return new ProductPrice();
		}
		for(i=0;i<productPriceList.size();i++){
			ProductPrice productPrice=(ProductPrice)productPriceList.get(i);
			if(productPrice.getPriceType()==priceType){
				return productPrice;
			}
		}
		return new ProductPrice();
	}
	
	/**
	 * 读取商城的价格
	 * @return
	 */
	public ProductPrice readShopProductPrice(){
		return this.readProductPrice(2);
	}
	/**
	 * 返回指定图片的路径,拿List第一张,如果有设置列表图片，第一张就是列表图片
	 * @param picIndex 从0开始越大则图片尺寸越大
	 * @return
	 */
	public String readPicPath(int picIndex){
		if(this.productPicList==null||this.productPicList.size()==0) return "";
		ProductPic productPic=(ProductPic)this.productPicList.get(0);
		if(productPic==null) return "";
		String picPath=productPic.getPicPath();
//		if(picIndex==0) return productPic.getPicPath();
		if(picPath==null||picPath.equals("")) return "";
		
		int separatorIndex=picPath.indexOf(".");
		String prefix=picPath.substring(0,separatorIndex);
		String suffix=picPath.substring(separatorIndex);
		return prefix+"_"+picIndex+suffix;
	}

	public int getProductOfferId() {
		return productOfferId;
	}

	public void setProductOfferId(int productOfferId) {
		this.productOfferId = productOfferId;
	}

	public int getSellNum() {
		return sellNum;
	}

	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}

	public String[] getHotFlag() {
		return hotFlag;
	}

	public void setHotFlag(String[] hotFlag) {
		this.hotFlag = hotFlag;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public int getIsFreeCostPost() {
		return isFreeCostPost;
	}

	public void setIsFreeCostPost(int isFreeCostPost) {
		this.isFreeCostPost = isFreeCostPost;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}
	

}
