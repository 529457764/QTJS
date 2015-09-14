package cn.qtone.modules.news.obj;

/**
 * 搜索资讯参数对象
 * @author jiangqingping
 *
 */

public class SearchNewsParam {

	private String keyword;
	
	private String newsCategoryIds;
	
	private String class3Ids;
	private int isWa;
	
	private int page;
	private int pagesize=20;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public SearchNewsParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getClass3Ids() {
		return class3Ids;
	}

	public void setClass3Ids(String class3Ids) {
		this.class3Ids = class3Ids;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getNewsCategoryIds() {
		return newsCategoryIds;
	}

	public void setNewsCategoryIds(String newsCategoryIds) {
		this.newsCategoryIds = newsCategoryIds;
	}
	
	
	public int getIsWa() {
		return isWa;
	}

	public void setIsWa(int isWa) {
		this.isWa = isWa;
	}
}
