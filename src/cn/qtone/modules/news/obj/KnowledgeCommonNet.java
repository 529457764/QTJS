package cn.qtone.modules.news.obj;

/**
 * 按三级分类汇总对象
 * @author baixueping
 *
 */

public class KnowledgeCommonNet {
	private int knowledgeCommonNetId;
	private int netTypeId;
	private String name;
	private String url;
	private String typeName;
	
	public KnowledgeCommonNet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setKnowledgeCommonNetId(int knowledgeCommonNetId) {
		this.knowledgeCommonNetId = knowledgeCommonNetId;
	}

	public int getKnowledgeCommonNetId() {
		return knowledgeCommonNetId;
	}
	public void setNetTypeId(int netTypeId) {
		this.netTypeId = netTypeId;
	}

	public int getNetTypeId() {
		return netTypeId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}

}
