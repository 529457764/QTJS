package cn.qtone.modules.faq.obj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import cn.qtone.sys.base.BaseHandle;

public class FaqCategoryHandle  extends BaseHandle{

	public FaqCategoryHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 将所有分类放入List
	 * @return
	 */
	public List categoryList(){
		List list=new ArrayList();
		String sql="SELECT faqCategoryId,categoryName FROM faq_category ORDER BY faqCategoryId ASC";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				FaqCategory category=new FaqCategory();
				category.setCategoryName(rs.getString("categoryName"));
				category.setFaqCategoryId(rs.getInt("faqCategoryId"));
				list.add(category);
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
	 * 将所有分类放入List
	 * @return
	 */
	public Hashtable creatCategoryMap(){
		Hashtable hm=new Hashtable();
		String sql="SELECT faqCategoryId,categoryName FROM faq_category ORDER BY faqCategoryId DESC";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				hm.put(rs.getString("faqCategoryId"),rs.getString("categoryName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return hm;
	}
}
