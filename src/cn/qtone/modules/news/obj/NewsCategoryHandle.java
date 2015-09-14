package cn.qtone.modules.news.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class NewsCategoryHandle extends BaseHandle{

	public NewsCategoryHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取信息的分类--金税百科
	 * 
	 * @return 返回信息分类对象List
	 */
	public List categoryList_op(int parentId,int level){
		List categoryList=new ArrayList();
		
		String sql="SELECT * FROM news_category WHERE (parentId="+parentId+" AND level="+level+")";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				NewsCategory newsCategory=new NewsCategory();
				newsCategory.setCategoryName(rs.getString("categoryName"));
				newsCategory.setLevel(rs.getInt("level"));
				newsCategory.setNewsCategoryId(rs.getInt("newsCategoryId"));
				newsCategory.setParentId(rs.getInt("parentId"));
				newsCategory.setSonCategory(this.categoryList(newsCategory.getNewsCategoryId(),newsCategory.getLevel()+1));
				categoryList.add(newsCategory);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return categoryList;
	}
	/**
	 * 获取信息的分类--金税百科
	 * 
	 * @return 返回信息分类对象List
	 */
	public List menuClassList(){
	List classList=new ArrayList();
	
	String sql="SELECT * FROM news_category WHERE (parentId=0) and newsCategoryId in(9,114)";
	ResultSet rs=null;
	try {
		rs=db.select(sql);
		while(rs.next()){
			NewsCategory newsCategory=new NewsCategory();
			newsCategory.setCategoryName(rs.getString("categoryName"));
			newsCategory.setLevel(rs.getInt("level"));
			newsCategory.setNewsCategoryId(rs.getInt("newsCategoryId"));
			newsCategory.setParentId(rs.getInt("parentId"));
			//newsCategory.setUrl("/servlet/bo/News");
			newsCategory.setSonCategory(this.categoryList(newsCategory.getNewsCategoryId(),newsCategory.getLevel()+1));
		classList.add(newsCategory);	
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace(System.out);
	} finally{
		db.closeResultSet(rs);
	}
	
	return classList;
}
	/**
	 * 获取信息的分类
	 * 
	 * @return 返回信息分类对象List
	 */
	public List categoryList(int parentId,int level){
		List categoryList=new ArrayList();
		
		String sql="SELECT * FROM news_category WHERE parentId="+parentId+" AND level="+level;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				NewsCategory newsCategory=new NewsCategory();
				newsCategory.setCategoryName(rs.getString("categoryName"));
				newsCategory.setLevel(rs.getInt("level"));
				newsCategory.setNewsCategoryId(rs.getInt("newsCategoryId"));
				newsCategory.setParentId(rs.getInt("parentId"));
				//newsCategory.setUrl("/servlet/bo/News");
				newsCategory.setSonCategory(this.categoryList(newsCategory.getNewsCategoryId(),newsCategory.getLevel()+1));
				categoryList.add(newsCategory);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return categoryList;
	}

	/**
	 * 是否是最后一级分类，信息只能属于最后一级分类
	 * @param newsCategoryId
	 * @return true：是 false;不是
	 */
	public boolean isFoot(int newsCategoryId){
		String sql="SELECT COUNT(*) FROM news_category WHERE parentId="+newsCategoryId;
		int num=db.countRow(sql);
		if(num==0) return true;
		return false;
	}
	
	/**
	 * 找出所有
	 * @param parentId
	 * @return
	 */
	public String getAllByParentId(int parentId){
		if(parentId<=0) return null;
		String sql="SELECT newsCategoryId FROM news_category WHERE locate('|"+parentId+"|',concat('|',path,'|'))";
		String allIds=db.getQueryResult(sql,",");
		return allIds;
	}

	
	/**
	 * 根据分类id得到分类名称
	 * @param categoryId 分类id
	 * @return
	 */
	public String getCategoryNameById(int categoryId){
		String sql="SELECT categoryName FROM news_category WHERE newsCategoryId="+categoryId;
		return db.getValue(sql);
	}

	/****
	 * 根据类型编号得到最高父类编号
	 * ****/
	public int getParentIdByCategoryId(int categoryId){
		String sql=" SELECT parentId FROM news_category WHERE newsCategoryId="+categoryId;
		int parentId=db.getValueInt(sql);
		return parentId==0?categoryId:getParentIdByCategoryId(parentId);	
	}
}
