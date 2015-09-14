package cn.qtone.modules.product.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;

public class ProductOfferHandle extends BaseHandle{
	public ProductOfferHandle(){
		super();
	}
	
	public List search(){
		List productOfferList=new ArrayList();
		String sql="SELECT * FROM product_offer";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				ProductOffer productOffer=new ProductOffer();
				productOffer.setName(rs.getString("name"));
				productOffer.setProductOfferId(rs.getInt("productOfferId"));
				productOfferList.add(productOffer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return productOfferList;
	}

}
