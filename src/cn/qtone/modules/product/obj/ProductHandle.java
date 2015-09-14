package cn.qtone.modules.product.obj;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class ProductHandle extends BaseHandle{

	public ProductHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 添加产品
	 * @param product 产品对象
	 * @return
	 */
	public boolean add(Product product){
		if(product==null) return false;
		
		String sql = "INSERT INTO product SET productName='"
				+ db.filterStr(product.getProductName()) + "',std='"+db.filterStr(product.getStd())
				+"',productTypeId="+ product.getProductTypeId() + ",productBrandId="
				+ product.getProductBrandId() + ",pState="
				+ product.getpState() + ",warranty=" + product.getWarranty()
				+ ",isStorage=" + product.getIsStorage() + ",amount="
				+ product.getAmount() + ",photo='" + product.getPhoto()
				+ "',isShow=" + product.getIsShow() + ",detail='"
				+ db.filterStr(product.getDetail()) + "',orderId="+product.getOrderId();
		sql+=",shopProductTypeId="+product.getShopProductTypeId();
		sql+=",shopProductBrandId="+product.getShopProductBrandId();
		sql+=",weight="+product.getWeight();
		sql+=",productNo='"+db.filterStr(product.getProductNo())+"'";
		sql+=",point="+product.getPoint();
		sql+=",pack='"+db.filterStr(product.getPack())+"'";
		sql+=",isShowShop="+product.getIsShowShop();
		sql+=",productOfferId="+product.getProductOfferId();
		sql+=",service='"+db.filterStr(product.getService())+"'";
		sql+=",isFreeCostPost="+product.getIsFreeCostPost();
		sql+=",isFrequentlyUsed="+product.getIsFrequentlyUsed();
		sql+=",fqUsedName='"+db.filterStr(product.getFqUsedName())+"'";
		sql+=",creatDate=CURRENT_DATE";
//		if(product.getConfigCode()>0){
//			sql+=",configCode="+product.getConfigCode();
//		}
		
		return db.executeUpdate(sql)>0?true:false;
	}

	/**
	 * 修改产品
	 * @param product 产品对象
	 * @return
	 */
	public boolean edit(Product product){
		if(product==null||product.getProductId()==0) return false;
		
		String sql = "UPDATE product SET productName='"
				+ db.filterStr(product.getProductName()) + "',std='"+db.filterStr(product.getStd())
				+"',productTypeId="+ product.getProductTypeId() + ",productBrandId="
				+ product.getProductBrandId() + ",pState="
				+ product.getpState() + ",warranty=" + product.getWarranty()
				+ ",isStorage=" + product.getIsStorage() + ",amount="
				+ product.getAmount()
				+ ",isShow=" + product.getIsShow() + ",detail='"
				+ db.filterStr(product.getDetail()) + "',orderId="+product.getOrderId();
//		if(product.getConfigCode()>0){
//			sql+=",configCode="+product.getConfigCode();
//		}
		
//		if(!StringFunction.isEmpty(product.getPhoto())){
//			sql+=" ,photo='"+product.getPhoto()+"'";
//		}
		sql+=",shopProductTypeId="+product.getShopProductTypeId();
		sql+=",shopProductBrandId="+product.getShopProductBrandId();
		sql+=",weight="+product.getWeight();
		sql+=",productNo='"+db.filterStr(product.getProductNo())+"'";
		sql+=",point="+product.getPoint();
		sql+=",pack='"+db.filterStr(product.getPack())+"'";
		sql+=",isShowShop="+product.getIsShowShop();
		sql+=",productOfferId="+product.getProductOfferId();
		sql+=",service='"+db.filterStr(product.getService())+"'";
		sql+=",isFreeCostPost="+product.getIsFreeCostPost();
		sql+=",creatDate=CURRENT_DATE";
		sql+=",isFrequentlyUsed="+product.getIsFrequentlyUsed();
		sql+=",fqUsedName='"+db.filterStr(product.getFqUsedName())+"'";
		sql+=" WHERE productId="+product.getProductId();
		
		return db.execute(sql);
	}
	
	/**
	 * 根据产品id删除产品
	 * @param productIds
	 * @return
	 */
	public boolean delProductByIds(String productIds){
		if(StringFunction.isEmpty(productIds)) return false;
		
		String sql="DELETE FROM product WHERE productId in ("+productIds+")";
		return db.executeUpdate(sql)>0?true:false;
	}
	
	/**
	 * 更新产品库存
	 * @param productId 产品id
	 * @param amount 库存数
	 * @return
	 */
	public boolean updateAmount(int productId,int amount){
		if(productId<=0) return false;
		String sql="UPDATE product SET amount="+amount+" WHERE productId="+productId;
		return db.execute(sql);
	}
	
	/**
	 * 减少库存,增加销售数量
	 * @param productId
	 * @param subNUM 减少的数量
	 * @return
	 */
	public boolean subAmount(int productId,int subNUM){
		if(productId<=0) return false;
		String sql="UPDATE product SET amount=amount-"+subNUM+",sellNum=sellNum+"+subNUM+" WHERE productId="+productId;
		return db.execute(sql);		
	}
	
	/**
	 * 增加库存
	 * @param productId
	 * @param subNUM 减少的数量
	 * @return
	 */
	public boolean increaseAmount(int productId,int increaseNUM){
		if(productId<=0) return false;
		String sql="UPDATE product SET amount=amount+"+increaseNUM+" WHERE productId="+productId;
		return db.execute(sql);		
		
	}
	
	/**
	 * 搜索产品
	 * @param param 搜索产品参数对象
	 * @param startPos 从第几个产品开始返回数据
	 * @param num 返回多少个产品
	 * @return 包含产品数据的List
	 */
	public List search(SearchProductParam param,int startPos,int num){
		List list=new ArrayList();
		String sql="SELECT CASE p.warranty WHEN -1 THEN '' WHEN 0 THEN '' ELSE DATE_ADD(CURRENT_DATE, INTERVAL p.warranty month) END AS warrantyDate, ";
		sql+=" p.std,p.creatDate,p.orderId,p.isShow,p.isShowShop,p.productId,p.productName,p.isStorage,p.amount,t.typeName,b.brandName,b.productBrandId,p.productTypeId,p.pState,p.warranty,p.photo,p.detail";
		sql +=" ,p.fqUsedName,p.isFrequentlyUsed ";
		sql+=" FROM product AS p LEFT JOIN product_type as t ON p.productTypeId=t.productTypeId";
		sql+=" LEFT JOIN product_brand AS b ON p.productBrandId=b.productBrandId";
		sql+=" WHERE p.productId>0";
		sql+=this.searchSQL(param);
		sql+=" ORDER BY p.orderId DESC,p.productId DESC";
		if(num!=-1) sql+=" LIMIT "+startPos+","+num;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				Product product=new Product();
				product.setProductId(rs.getInt("p.productId"));
				product.setBrandName(rs.getString("b.brandName"));
				product.setProductName(rs.getString("p.productName"));
				product.setpState(rs.getInt("p.pState"));
				product.setWarranty(rs.getInt("p.warranty"));
				product.setTypeName(rs.getString("t.typeName"));
				product.setAmount(rs.getInt("p.amount"));
				product.setPhoto(rs.getString("photo"));
				product.setDetail(rs.getString("detail"));
				product.setOrderId(rs.getInt("p.orderId"));
				product.setIsShow(rs.getInt("p.isShow"));
				product.setIsShowShop(rs.getInt("p.isShowShop"));
				product.setProductBrandId(rs.getInt("b.productBrandId"));
				product.setIsStorage(rs.getInt("p.isStorage"));
				product.setWarrantyDate(rs.getString("warrantyDate"));
				product.setProductTypeId(rs.getInt("p.productTypeId"));
				product.setStd(rs.getString("p.std"));
				product.setCreatDate(rs.getString("p.creatDate"));
				product.setFqUsedName(rs.getString("p.fqUsedName"));
				product.setIsFrequentlyUsed(rs.getInt("p.isFrequentlyUsed"));
				
				//设置产品的价格
				ProductPriceHandle priceHandle=new ProductPriceHandle();
				List productPriceList=priceHandle.getProductPrice(product.getProductId());
				product.setProductPriceList(productPriceList);
				
				list.add(product);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		//ORDER BY";
		return list; 
	}
	
	/**
	 * 前台搜索产品
	 * @param param 搜索产品参数对象
	 * @param startPos 从第几个产品开始返回数据
	 * @param num 返回多少个产品
	 * @return 包含产品数据的List
	 */
	public List searchWa(SearchProductParam param,int startPos,int num){
		List list=new ArrayList();
		String sql="SELECT p.isStorage,p.productId,p.amount,p.productName,p.std,t.typeName,b.brandName,p.photo,p.detail";
		sql+=" FROM product AS p LEFT JOIN product_type as t ON p.productTypeId=t.productTypeId";
		sql+=" LEFT JOIN product_brand AS b ON p.productBrandId=b.productBrandId";
		sql+=" WHERE p.productId>0";
		sql+=this.searchSQL(param);
		sql+=" ORDER BY p.orderId DESC,p.productId DESC";
		sql+=" LIMIT "+startPos+","+num;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			ProductPriceHandle priceHandle=new ProductPriceHandle();
			ProductPicHandle picHandle=new ProductPicHandle();
			while(rs.next()){
				Product product=new Product();
				product.setProductId(rs.getInt("p.productId"));
				product.setBrandName(rs.getString("b.brandName"));
				product.setProductName(rs.getString("p.productName"));
				product.setTypeName(rs.getString("t.typeName"));
				product.setPhoto(rs.getString("photo"));
				product.setDetail(rs.getString("detail"));
				product.setStd(rs.getString("p.std"));
				product.setAmount(rs.getInt("p.amount"));
				product.setIsStorage(rs.getInt("p.isStorage"));
				
				//设置产品的最低价格
//				ProductPrice lowestPrice=priceHandle.getLowestPrice(product.getProductId());
//				product.setLowestPrice(lowestPrice);
				
				product.setProductPriceList(priceHandle.getProductPrice(product.getProductId()));
				product.setProductPicList(picHandle.getProductPicList(product.getProductId()));
				
				list.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		//ORDER BY";
		return list; 
	}	
	/**
	 * 得到搜索产品的结果总数
	 * @param param
	 * @return
	 */
	public int totalRow(SearchProductParam param){
		String sql="SELECT COUNT(*) FROM product AS p";
		sql+=" LEFT JOIN product_type as t ON p.productTypeId=t.productTypeId";
		if(param.getProductBrandId()>0) sql+=" LEFT JOIN product_brand AS b ON p.productBrandId=b.productBrandId";
		sql+=" WHERE p.productId>0";
		sql+=this.searchSQL(param);
		return db.countRow(sql);
	}
	
	/**
	 * 根据参数生成搜索sql条件
	 * @param param 参数对象
	 * @return
	 */
	private String searchSQL(SearchProductParam param){
		
		if(param==null) return "";
		
		StringBuffer sqlWhereBuf=new StringBuffer();
		if(!StringFunction.isEmpty(param.getProductIds())){
			sqlWhereBuf.append(" AND p.productId IN (").append(param.getProductIds()).append(")");
		}
		
		if(!StringFunction.isEmpty(param.getShopProductTypeIds())) {
			sqlWhereBuf.append(" AND p.shopProductTypeId IN("+param.getShopProductTypeIds()+")");
		}
		if(param.getShopProductBrandId()>0){
			sqlWhereBuf.append(" AND p.shopProductBrandId="+param.getShopProductBrandId());
		}
		
		if(param.getProductTypeId()>0) {
			sqlWhereBuf.append(" AND p.productTypeId="+param.getProductTypeId());
		}
		if(param.getProductBrandId()>0){
			sqlWhereBuf.append(" AND p.productBrandId="+param.getProductBrandId());
		}
		if(param.getPState()>0){
			sqlWhereBuf.append(" AND p.pState="+param.getPState());
		}
		if(!StringFunction.isEmpty(param.getProductName())){
			sqlWhereBuf.append(" AND (p.productName LIKE BINARY '%"+db.filterStr(param.getProductName())+"%'");
			sqlWhereBuf.append(" OR t.typeName LIKE BINARY '%").append(db.filterStr(param.getProductName())).append("%')");
		}
		if(param.getIsShow()>0){
			if(param.getIsShow()==1){//前台显示包括首页也显示的
				sqlWhereBuf.append(" AND p.isShow IN (1,3)");				
			}else{
				sqlWhereBuf.append(" AND p.isShow="+param.getIsShow());
			}
		}
		//商城显示查询
		if(param.getIsShowShop()>0){
			sqlWhereBuf.append(" AND p.isShowShop=").append(param.getIsShowShop());
		}
		//热点类型查询
		if(param.getHotFlag()>0){
			sqlWhereBuf.append(" AND ph.flag=").append(param.getHotFlag());
		}
		//要排除的产品
		if(!StringFunction.isEmpty(param.getRidIds())){
			sqlWhereBuf.append(" AND p.productId NOT IN (").append(param.getRidIds()).append(")");
		}
		if(param.getIsFrequentlyUsed()==2){
			sqlWhereBuf.append(" AND p.isFrequentlyUsed=").append(param.getIsFrequentlyUsed());
		}else if(param.getIsFrequentlyUsed()==1){
			sqlWhereBuf.append(" AND ( p.isFrequentlyUsed <=1 OR p.isFrequentlyUsed IS NULL )");
		}
		
		return sqlWhereBuf.toString();
	}

	/**
	 * 根据产品得到产品的id
	 * @param product
	 * @return
	 */
	public int getProductIdByProduct(Product product){
		if(product==null) return 0;
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("SELECT productId FROM product WHERE 1=1");
		if(!StringFunction.isEmpty(product.getProductName())){
			sqlBuf.append(" AND productName = BINARY '"+product.getProductName()+"'");
		}
		if(product.getProductBrandId()>0) sqlBuf.append(" AND productBrandId="+product.getProductBrandId());
		if(product.getProductTypeId()>0) sqlBuf.append(" AND productTypeId="+product.getProductTypeId());
		sqlBuf.append(" ORDER BY productId DESC LIMIT 1");
		return db.countRowMain(sqlBuf.toString());
	}
	/**
	 * 根据名称和型号得到产品
	 * @param productName
	 * @param std
	 * @return
	 */
	public Product getProduct(String productName,String std){
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("SELECT * FROM product WHERE productName LIKE BINARY '%"+productName+"%'");
		if(!StringFunction.isEmpty(std)){
			sqlBuf.append(" AND std LIKE BINARY '%"+std+"%'");
		}
		ResultSet rs=null;
		try {
			rs=db.select(sqlBuf.toString());
			if(rs.next()){
				Product product=new Product();
				product.setAmount(rs.getInt("amount"));
				product.setIsStorage(rs.getInt("isStorage"));
				product.setProductBrandId(rs.getInt("productBrandId"));
				product.setProductId(rs.getInt("productId"));
				product.setProductTypeId(rs.getInt("productTypeId"));
				product.setpState(rs.getInt("pState"));
				product.setWarranty(rs.getInt("warranty"));
				product.setProductName(rs.getString("productName"));
				product.setDetail(rs.getString("detail"));
				product.setIsShow(rs.getInt("isShow"));
				product.setPhoto(rs.getString("photo"));
				product.setOrderId(rs.getInt("orderId"));
				product.setWarrantyDate(rs.getString("warrantyDate"));
				product.setService(rs.getString("service"));

				return product;
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
	 * 根据id获得product对象
	 * @param productId
	 * @return
	 */
	public Product getProductById(int productId){
		if(productId==0) return null;
		String sql="SELECT CASE p.warranty WHEN -1 THEN '' WHEN 0 THEN '' ELSE DATE_ADD(CURRENT_DATE, INTERVAL p.warranty month) END AS warrantyDate,p.* FROM product AS p ";
		sql+=" WHERE productId="+productId;
		ResultSet rs=null;
		try {
			//因为同步u8订单要用到所以要用主链接池
			rs=db.selectMain(sql);
			if(rs.next()){
				Product product=new Product();
				product.setAmount(rs.getInt("p.amount"));
				product.setIsStorage(rs.getInt("p.isStorage"));
				product.setProductBrandId(rs.getInt("p.productBrandId"));
				product.setProductId(rs.getInt("p.productId"));
				product.setProductTypeId(rs.getInt("p.productTypeId"));
				product.setpState(rs.getInt("p.pState"));
				product.setWarranty(rs.getInt("p.warranty"));
				product.setProductName(rs.getString("p.productName"));
				product.setStd(rs.getString("p.std"));
				product.setDetail(rs.getString("p.detail"));
				product.setIsShow(rs.getInt("p.isShow"));
				product.setPhoto(rs.getString("p.photo"));
				product.setOrderId(rs.getInt("p.orderId"));
				product.setWarrantyDate(rs.getString("warrantyDate"));
				product.setStd(rs.getString("p.std"));
				product.setOutAmount(rs.getInt("p.outAmount"));
				product.setWeight(rs.getFloat("p.weight"));
				product.setService(rs.getString("p.service"));
				product.setIsFreeCostPost(rs.getInt("p.isFreeCostPost"));
				product.setFqUsedName(rs.getString("p.fqUsedName"));
				product.setIsFrequentlyUsed(rs.getInt("p.isFrequentlyUsed"));

				ProductBrandHandle productBrandHandle=new ProductBrandHandle();
				product.setProductBrand(productBrandHandle.getProductBrandById(product.getProductBrandId()));
				
				ProductTypeHandle productTypeHandle=new ProductTypeHandle();
				product.setProductType(productTypeHandle.getProductTypeById(product.getProductTypeId(), false));
				
				product.setShopProductBrandId(rs.getInt("p.shopProductBrandId"));
				product.setShopProductTypeId(rs.getInt("p.shopProductTypeId"));
				product.setProductNo(rs.getString("p.productNo"));
				product.setPoint(rs.getInt("p.point"));
				product.setPack(rs.getString("p.pack"));
				product.setIsShowShop(rs.getInt("p.isShowShop"));
				product.setProductOfferId(rs.getInt("p.productOfferId"));
				product.setSellNum(rs.getInt("p.sellNum"));
				//商城产品显示属性
				ProductHotHandle productHotHandle=new ProductHotHandle();
				product.setHotFlag(productHotHandle.searchByProductId(rs.getInt("p.productId")));
				//产品价格数据
				ProductPriceHandle productPriceHandle=new ProductPriceHandle();
				List productPriceList=productPriceHandle.getProductPrice(product.getProductId());
				product.setProductPriceList(productPriceList);
//				product.setLowestPrice(productPriceHandle.getLowestPrice(productId));
				
				product.setIsShowShop(rs.getInt("p.isShowShop"));
				ProductPicHandle productPicHandle=new ProductPicHandle();
				product.setProductPicList(productPicHandle.getProductPicList(product.getProductId()));
				
				return product;
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
	 * 更新排序id
	 * @param productId 产品id
	 * @param orderId 排序id
	 * @return
	 */
	public boolean updateOrderId(int productId,int orderId){
		String sql="UPDATE product SET orderId="+orderId+" WHERE productId="+productId;
		return db.execute(sql);
	}
	
	/**
	 * 判断是否属于续费的商品
	 * @param product
	 * @return true要续费 false不用续费
	 */
	public boolean isRebuy(Product product){
		if(product.getProductPriceList()==null||product.getProductPriceList().size()==0)
			return false;
		
		ProductPrice productPrice=(ProductPrice)product.getProductPriceList().get(0);
		if(productPrice.getPriceUnit()>1){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 返回最大排序号
	 * @return
	 */
	public int maxOrderId(){
		String sql="SELECT MAX(orderId) FROM product";
		return db.countRow(sql);
	}
	
	/**
	 * 根据商品类型和商品品牌获取商品的id，id用逗号分隔
	 * @param productTypeId
	 * @param productBrandId
	 * @return
	 */
	public String getProductIds(int productTypeId,int productBrandId){
		String sql="SELECT productId FROM product WHERE 1=1";
		if(productTypeId>0){
			sql+=" AND productTypeId="+productTypeId;
		}
		if(productBrandId>0){
			sql+=" AND productBrandId="+productBrandId;
		}
		return db.getQueryResult(sql, ",");
	}
	
	/**
	 * 根据商品类型获取商品的id，id用逗号分隔
	 * @param productTypeId
	 * @param productBrandId
	 * @return
	 */
	public String getProductIds(String productTypeIds){
		if(StringFunction.isEmpty(productTypeIds)) return "";
		String sql="SELECT productId FROM product WHERE 1=1";
		sql+=" AND productTypeId IN ("+productTypeIds+")";
		return db.getQueryResult(sql, ",");
	}	
	
	
	/**
	 * 根据u8的数据，更新产品数量
	 * @param houseId  隶属仓库：2全通数码；3全通金税
	 * @param product
	 * @param synFlag 同步的标识 
	 * @return
	 */
	public boolean updateAmountByU8(Product product,int houseId,String synFlag){		
		String sql=" UPDATE product  ";
		if(houseId==3){
			if(StringFunction.isNull(product.getU8SynFlag()).equals(synFlag)){//如果是同一次同步，则累加
				sql +=" SET amount=amount+"+product.getAmount()+" ,outAmount=outAmount+"+product.getOutAmount();
			}else{
				sql +=" SET amount="+product.getAmount()+" ,outAmount="+product.getOutAmount() ;
			}
		}else{
			if(StringFunction.isNull(product.getU8SynFlag()).equals(synFlag)){//如果是同一次同步，则累加
				sql +=" SET amountQT=amountQT+"+product.getAmountQT()+" ,outAmountQT=outAmountQT+"+product.getOutAmountQT();
			}else{
				sql +=" SET  amountQT="+product.getAmountQT()+" ,outAmountQT="+product.getOutAmountQT();
			}
		}		
		sql +=" WHERE productId="+product.getProductId();
		boolean bln= db.execute(sql);
		if(bln){//更新同步标识
			 db.execute(" UPDATE product SET u8SynFlag='"+synFlag+"' WHERE productId="+product.getProductId());
		}
		return bln;
	}	
	
	
	
	
	
	
	
	/**
	 * 搜索
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 */
	public List searchShopProduct(SearchProductParam param,int start,int limit){
		String sql="SELECT * FROM product AS p  INNER JOIN  product_type AS t  ON p.shopProductTypeId=t.productTypeId AND t.site=2 " ;
		if(param.getOrder()==2){
			sql+=" LEFT JOIN product_price AS pc ON p.productId=pc.productId  AND priceType=2 ";
		}
		if(param.getHotFlag()>0){
			sql+=" LEFT JOIN product_hot AS ph ON p.productId=ph.productId ";			
		}
		sql+=" WHERE p.isShowShop IN (1,3)";

		sql+=this.searchSQL(param);
		 //根据具体搜索要求排序
		if(param.getOrder()==2){//价格
			if(param.getPriceOrderFlag()==2){
				sql+=" ORDER BY pc.price DESC ";
			}else{
				sql+=" ORDER BY pc.price ASC ";
			}
		}else if(param.getOrder()==3){//上架时间
			sql+=" ORDER BY p.creatDate DESC ";
		}else { //(param.getOrder()==1) 销量
			sql+=" ORDER BY p.sellNum DESC ";
		}
		sql+=" LIMIT "+start+","+limit;
		ResultSet rs=null;
		Product product=null;
		List list=new ArrayList();	
		ProductPicHandle productPicHandle=new ProductPicHandle();
		try{
			rs=db.select(sql);
			while(rs.next()){
				product=new Product();				
				product.setProductId(rs.getInt("p.productId"));	
				product.setProductName(rs.getString("p.productName"));	
				product.setProductPicList(productPicHandle.getProductPicList(rs.getInt("p.productId")));
				product.setIsFreeCostPost(rs.getInt("p.isFreeCostPost"));
				//产品价格数据
				ProductPriceHandle productPriceHandle=new ProductPriceHandle();
				List productPriceList=productPriceHandle.getProductPrice(product.getProductId());
				product.setProductPriceList(productPriceList);
				
				list.add(product);
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return list;
				
	}
	
	/**
	 * 得到搜索产品的结果总数
	 * @param param
	 * @return
	 */
	public int shopTotalRow(SearchProductParam param){
		String sql="SELECT COUNT(*) FROM product AS p  INNER JOIN  product_type AS t  ON p.shopProductTypeId=t.productTypeId AND t.site=2 " ;
		if(param.getOrder()==2){
			sql+=" LEFT JOIN product_price AS pc ON p.productId=pc.productId  AND priceType=2 ";
		}	
		if(param.getHotFlag()>0){
			sql+=" LEFT JOIN product_hot AS ph ON p.productId=ph.productId ";			
		}
		sql+=" WHERE p.isShowShop IN (1,3)";
		sql+=this.searchSQL(param);
		return db.countRow(sql);
	}
	
	/**
	 * 获取热点产品
	 * @param productTypeId 产品类型
	 * @param flag 热点商品类型：1最新推荐 2最新上架 3热卖商品 4特卖专区
	 * @param num 读取多少产品
	 * @return
	 */
	public List getHotProduct(int shopProductTypeId,int flag,int num){
		SearchProductParam param=new SearchProductParam();
		int order=3;
		if(flag==3) order=1;
		param.setOrder(order);
		param.setHotFlag(flag);
		ProductTypeHandle productTypeHandle=new ProductTypeHandle();
		String productTypeIds=productTypeHandle.getShopTypeIdsByParentId(shopProductTypeId);
		param.setShopProductTypeIds(productTypeIds);
		List productList=this.searchShopProduct(param, 0, num);
		
		//如果不够则搜索产品补足
		if(productList.size()<num){
			
			String ridIds="";
			for(int i=0;i<productList.size();i++){
				Product product=(Product)productList.get(i);
				if(!StringFunction.isEmpty(ridIds)){
					ridIds+=",";
				}
				ridIds+=product.getProductId();
			}
			
			int appendNum=num-productList.size();
			SearchProductParam appendParam=new SearchProductParam();
			appendParam.setShopProductTypeIds(productTypeIds);
			appendParam.setRidIds(ridIds);
			if(flag==3) {//按销量排序
				appendParam.setOrder(1);
			}else{//按上架时间排序
				appendParam.setOrder(3);
			}
			List appendProduct=this.searchShopProduct(appendParam, 0, appendNum);
			for(int i=0;i<appendProduct.size();i++){
				productList.add(appendProduct.get(i));
			}
		}
		
		return productList;
	}
	
	/**
	 * 根据产品id取得产品名称
	 * @param productIds
	 * @param sepStr 分隔符
	 * @return
	 */
	public String getProductNameByIds(String productIds,String sepStr){
		if(StringFunction.isEmpty(productIds)){
			return "";
		}
		String sql="SELECT  productName FROM product  WHERE productId IN ("+productIds+") ";
		return db.getQueryResult(sql, sepStr);
	}
	/**
	 * 搜索常用产品
	 * @return
	 */
	public List getFrequentlyProduct(){
		List list=new ArrayList();
		String sql="SELECT  *  FROM product  WHERE isFrequentlyUsed=2 AND  pState=1 ORDER BY productTypeId ";
		ResultSet rs=null;
		Product product=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				product=new Product();
				product.setProductId(rs.getInt("productId"));
				product.setFqUsedName(rs.getString("fqUsedName")); 
				list.add(product);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return list;  
	}
	
	/*
	//wap
	public List getProductListByTypeId(int productTypeId) {
		List productList = new ArrayList();
		String sql = "select * from product where productTypeId = '"+productTypeId+"'";
		ResultSet rs = null;
		try {
			rs = db.select(sql);
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("productId"));
				product.setProductName(rs.getString("productName"));
				product.setProductTypeId(rs.getInt("productTypeId"));
				product.setPhoto(rs.getString("photo"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.closeResultSet(rs);
		}
		return productList;
	}
	*/
}
