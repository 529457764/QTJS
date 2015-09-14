package cn.qtone.modules.news.obj;

import java.util.*;

public class NewsCategory {
	
	private int newsCategoryId;
	
	private String categoryName;
	
	private int parentId;
	
	private int level;
	private int isWa;
	//private String url;
	
	//下级分类的list
	private List sonCategory;

	public NewsCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNewsCategoryId() {
		return newsCategoryId;
	}

	public void setNewsCategoryId(int newsCategoryId) {
		this.newsCategoryId = newsCategoryId;
	}

	public List getSonCategory() {
		return sonCategory;
	}

	public void setSonCategory(List sonCategory) {
		this.sonCategory = sonCategory;
	}

	public int getIsWa() {
		return isWa;
	}

	public void setIsWa(int isWa) {
		this.isWa = isWa;
	}
}
