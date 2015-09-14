package cn.qtone.modules.product.obj;

public class ProductPic {

	public ProductPic() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int productPicId;
	private String picPath;
	private int productId;
	//是否为列表图片 2是列表图片 1.不是列表图
	private int flag;
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductPicId() {
		return productPicId;
	}
	public void setProductPicId(int productPicId) {
		this.productPicId = productPicId;
	}
	
	/**
	 * 返回指定图片的路径,使用的是默认的图片，如果没有默认图片则拿List第一张
	 * @param picIndex 0返回数据表记录的图片 从1开始越大则图片尺寸越大
	 * @return
	 */
	public String readPicPath(int picIndex){
		String picPath=this.getPicPath();
		if(picPath==null||picPath.equals("")) return "";
		if(picIndex==0) return picPath;				
		int separatorIndex=picPath.lastIndexOf(".");
		String prefix=picPath.substring(0,separatorIndex);
		String suffix=picPath.substring(separatorIndex);
		return prefix+"_"+picIndex+suffix;
	}
	
}
