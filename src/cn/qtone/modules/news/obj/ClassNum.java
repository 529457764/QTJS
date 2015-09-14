package cn.qtone.modules.news.obj;

/**
 * 按三级分类汇总对象
 * @author baixueping
 *
 */

public class ClassNum {
	private String class1;
	private String class2;
	private String class3;
	private String class4;
	private String class3Ids;
	private int num;
	
	public ClassNum() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setClass3Ids(String class3Ids) {
		this.class3Ids = class3Ids;
	}

	public String getClass3Ids() {
		return class3Ids;
	}
	public void setClass1(String class1) {
		this.class1 = class1;
	}

	public String getClass1() {
		return class1;
	}
	public void setClass2(String class2) {
		this.class2 = class2;
	}

	public String getClass2() {
		return class2;
	}
	public void setClass3(String class3) {
		this.class3 = class3;
	}

	public String getClass3() {
		return class3;
	}
	public void setClass4(String class4) {
		this.class4 = class4;
	}

	public String getClass4() {
		return class4;
	}
   
	public void setNum(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}
	

}
