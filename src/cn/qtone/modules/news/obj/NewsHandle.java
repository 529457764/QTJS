package cn.qtone.modules.news.obj;
import java.util.*;
import java.util.regex.Matcher;
import java.sql.*;




import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.ParseTool;
import cn.qtone.utils.StringFunction;

public class NewsHandle extends BaseHandle{
	private int isknowclass;//BXP 金税百科中分类表头只按关键字查询，下面的列表按关键字和三级分类名，此变量为0则只查分类表头，为1时查列表
	public NewsHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	/**
	 * 返回最大排序号
	 * @return
	 */
	public int maxOrderId(){
		String sql="SELECT MAX(orderId) FROM news";
		return db.countRow(sql);
	}

	/**
	 * 得到资讯列表的总记录数
	 * @param param 搜索的参数
	 * @return
	 */
	public int newsListTotalRow(SearchNewsParam param){
		String sql="SELECT COUNT(*) FROM news AS n JOIN news_category AS c ON n.newsCategoryId=c.newsCategoryId WHERE newsId>0";
		sql+=this.sqlSearchNews(param);
		return db.countRow(sql);
	}
	/**
	 * 得到金税百科列表的总记录数
	 * @param param 搜索的参数
	 * @return
	 */
	public int newsListTotalRow_op(SearchNewsParam param){
		String sql="SELECT COUNT(*) FROM news AS n JOIN vclass AS c ON n.newsCategoryId=c.newscategoryId WHERE newsId>0";
		isknowclass=1;
		sql+=this.sqlSearchNews_op(param);
		return db.countRow(sql);
	}
	/**
	 * 得到资讯列表数据
	 * @param param 搜索参数
	 * @param int startPos 获取数据的开始位置
	 * @parma int num 获取数据的条数
	 * @return
	 */
	public List newsList(SearchNewsParam param,int startPos,int num){
		String sql="SELECT n.newsId,n.visitNum,n.visitNumIM,n.subject,n.creatDate,n.startDate,u.name,n.orderId,c.categoryName";
		sql+=" FROM news AS n  JOIN news_category AS c ON n.newsCategoryId=c.newsCategoryId";
		sql+=" LEFT JOIN user_bo AS u ON u.userBoId=n.userBoId";
		sql+=" WHERE n.newsId>0";
		sql+=this.sqlSearchNews(param);
		sql+=" ORDER BY n.orderId DESC ,n.creatDate DESC";
		if(num!=-1) sql+=" LIMIT "+startPos+","+num;
		
		List list=new ArrayList();
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				News news=new News();
//				news.setContent(rs.getString("n.content"));
				news.setCreatDate(rs.getString("creatDate"));
				news.setNewsId(rs.getInt("n.newsId"));
				news.setSubject(rs.getString("n.subject"));
				news.setUserBoName(rs.getString("u.name"));
				news.setVisitNum(rs.getInt("n.visitNum"));
				news.setOrderId(rs.getInt("n.orderId"));
				news.setCategoryName(rs.getString("c.categoryName"));
				news.setVisitNumIM(rs.getInt("n.visitNumIM"));
				news.setStartDate(rs.getString("n.startDate"));
				list.add(news);
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
	 * 获取信息的分类--金税百科(电子申报前20条记录)
	 * 
	 * @return 返回信息分类对象List
	 */
	public List knowsbList(SearchNewsParam param){
		List headList=new ArrayList();
		
		String sql="select newsId,creatDate,subject from  news a join vclass b on a.newsCategoryId=b.newsCategoryId WHERE newsId>0 ";
		sql+=" AND (subject LIKE BINARY '%申报V6.0%' OR content LIKE BINARY '%申报V6.0%')";
		sql+=" order by creatDate desc   LIMIT 0, 20 ";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				News news=new News();
				news.setCreatDate(rs.getString("creatDate"));
				news.setNewsId(rs.getInt("newsId"));
				news.setSubject(rs.getString("subject"));
				headList.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return headList;
	}
	
	/**
	 * 获取信息的分类--金税百科(更新前20条记录)
	 * 
	 * @return 返回信息分类对象List
	 */
	public List knowheadList(SearchNewsParam param){
		List headList=new ArrayList();
		
		String sql="select newsId,creatDate,subject from  news a join vclass b on a.newsCategoryId=b.newsCategoryId WHERE newsId>0 ";
		sql+=" order by creatDate desc   LIMIT 0, 20 ";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				News news=new News();
				news.setCreatDate(rs.getString("creatDate"));
				news.setNewsId(rs.getInt("newsId"));
				news.setSubject(rs.getString("subject"));
				headList.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return headList;
	}
	
	/**
	 * 得到金税百科列表数据
	 * @param param 搜索参数
	 * @param int startPos 获取数据的开始位置
	 * @parma int num 获取数据的条数
	 * @return
	 */
	public List newsList_op(SearchNewsParam param,int startPos,int num){
		String sql="select n.newsId,n.visitNum,n.visitNumIM,n.subject,n.creatDate,u.name,n.orderId,c.class1,c.class2,c.class3,c.class4,n.content";
		sql+=" FROM news AS n JOIN vclass AS c ON n.newsCategoryId=c.newscategoryId  ";
		sql+=" LEFT JOIN user_bo AS u ON u.userBoId=n.userBoId";
		sql+=" WHERE n.newsId>0 ";
		isknowclass=1;
		sql+=this.sqlSearchNews_op(param);
		sql+=" ORDER BY n.creatDate DESC";
		if(num!=-1) sql+=" LIMIT "+startPos+","+num;
		//System.out.println(sql);
		List list=new ArrayList();
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				News news=new News();					
				news.setContent(rs.getString("n.content"));
				news.setCreatDate(rs.getString("creatDate"));
				news.setNewsId(rs.getInt("n.newsId"));
				news.setSubject(rs.getString("n.subject"));
				news.setUserBoName(rs.getString("u.name"));
				news.setVisitNum(rs.getInt("n.visitNum"));
				news.setOrderId(rs.getInt("n.orderId"));
				news.setCategoryName(rs.getString("c.class4"));
				news.setVisitNumIM(rs.getInt("n.visitNum"));
				ClassNum vclass=new ClassNum();
				vclass.setClass1(rs.getString("c.class1"));
				vclass.setClass2(rs.getString("c.class2"));
				vclass.setClass3(rs.getString("c.class3"));
				vclass.setClass4(rs.getString("c.class4"));
				news.setVclass(vclass);
				String oldorigin=StringFunction.praseHtml(rs.getString("n.content"));
				String neworigin=oldorigin;
				String lastorigin=oldorigin;
				String[] idskeyword=param.getKeyword().split(" ");
				for(int i=0;i<idskeyword.length;i++){	
					String keyword = "(?i)"+idskeyword[i];//用(?i)来忽略大小写  
					StringBuffer sb = new StringBuffer();  
					Matcher matcher = java.util.regex.Pattern.compile(keyword).matcher(neworigin);  
					while(matcher.find()){  

					 matcher.appendReplacement(sb, "<font color='red'>"+matcher.group()+"</font>");//这样保证了原文的大小写没有发生变化  
					}  
					matcher.appendTail(sb);  
					neworigin = sb.toString(); 
					//String keyword=idskeyword[i];
					
				//neworigin="<font color='red'>"+keyword+"</font>";	
				//neworigin=lastorigin.replaceAll(keyword,neworigin);
				lastorigin=neworigin;
				}				
				news.setOrigin(lastorigin);
				list.add(news);
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
	 * 获取信息的分类--金税百科
	 * 
	 * @return 返回信息分类对象List
	 */
	public List classList(SearchNewsParam param){
		List classList=new ArrayList();
		
		String sql="select trim(a.class3)as class3,count(*) as num from vclass a join news n on a.newscategoryId=n.newsCategoryId ";
		sql+=" WHERE n.newsId>0";
		isknowclass=0;
		sql+=this.sqlSearchNews_op(param);
		sql+=" group by trim(a.class3)";
		//System.out.print(sql);
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				ClassNum classnum=new ClassNum();
				classnum.setClass3(rs.getString("class3"));
				String class3Ids=this.getClass3IdByClass3(rs.getString("class3"));
				classnum.setClass3Ids(class3Ids);
				classnum.setNum(rs.getInt("num"));
				classList.add(classnum);	
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
	 * 根据参数得到搜索资讯的sql
	 * @param param
	 * @return
	 */
	private String sqlSearchNews(SearchNewsParam param){
		if(param==null) return "";
		StringBuffer sqlWhereBuf=new StringBuffer();
		if(!StringFunction.isEmpty(param.getKeyword())){
			String keyword=db.filterStr(param.getKeyword());
			sqlWhereBuf.append(" AND (n.subject LIKE BINARY '%"
					+ keyword + "%' OR n.content LIKE BINARY '%"
					+ keyword + "%')");
		}
		
		
		if(!StringFunction.isEmpty(param.getNewsCategoryIds())){
			
			String allCategoryIds="";
			String[] idsAry=param.getNewsCategoryIds().split(",");
			for(int i=0;i<idsAry.length;i++){			
				int newsCategoryId=ParseTool.parseInt(idsAry[i], 0);
				if(newsCategoryId<=0) continue;
				//获取该分类的所有子类，包括自身
				NewsCategoryHandle categoryHandle=new NewsCategoryHandle();
				String newsCategoryIds=categoryHandle.getAllByParentId(newsCategoryId);
				
				if(!StringFunction.isEmpty(newsCategoryIds)){
					allCategoryIds=StringFunction.isEmpty(allCategoryIds)?newsCategoryIds:allCategoryIds+","+newsCategoryIds;
				}
			}
			
			if(!StringFunction.isEmpty(allCategoryIds)) 
				sqlWhereBuf.append(" AND n.newsCategoryId in ("+allCategoryIds+")");
			if(param.getIsWa()>0)
				sqlWhereBuf.append(" AND c.isWa="+param.getIsWa());
		}
		return sqlWhereBuf.toString();
	}
	
	/**
	 * 根据参数得到搜索金税百科的sql
	 * @param param
	 * @return
	 */
	private String sqlSearchNews_op(SearchNewsParam param){
		if(param==null) return "";
		StringBuffer sqlWhereBuf=new StringBuffer();
		if(!StringFunction.isEmpty(param.getKeyword())){
			//多关键字查询，用空格分开
			String[] idskeyword=param.getKeyword().split(" ");
			for(int i=0;i<idskeyword.length;i++){		
				String keyword=idskeyword[i];
				sqlWhereBuf.append(" AND (n.subject LIKE  '%"
						+ keyword + "%' OR n.content LIKE  '%"
						+ keyword + "%')");
			}
		}
		if (isknowclass==1)
		{
if(!StringFunction.isEmpty(param.getClass3Ids())){
			
			String class3Ids=param.getClass3Ids();
				sqlWhereBuf.append(" AND class3Id in ("+class3Ids+")");
		}
		}
		return sqlWhereBuf.toString();
	}
	
	/**
	 * 将news对象保存到数据库
	 * @param news
	 * @return true 成功 ；false 失败
	 */
	public boolean addNews(News news){
		String sql="INSERT INTO news SET subject='"+db.filterStr(news.getSubject())+"'";
		sql+=",content='"+db.filterStr(news.getContent())+"'";
		sql+=",subtitle='"+db.filterStr(news.getSubtitle())+"'";
		sql+=",newsCategoryId="+news.getNewsCategoryId();
		sql+=",userBoId="+news.getUserBoId();
		sql+=",photo='"+news.getPhoto()+"'";
		if(!StringFunction.isEmpty(news.getStartDate()))
			sql+=",startDate='"+news.getStartDate()+"'";
		sql+=",orderId="+news.getOrderId();
		sql+=",creatDate=CURRENT_TIMESTAMP";
		return db.execute(sql);
	}
	
	/**
	 * 修改news
	 * @param news news对象
	 * @return
	 */
	public boolean editNews(News news){
		String sql="UPDATE news SET subject='"+db.filterStr(news.getSubject())+"'";
		sql+=",content='"+db.filterStr(news.getContent())+"'";
		sql+=",subtitle='"+db.filterStr(news.getSubtitle())+"'";
		sql+=",newsCategoryId="+news.getNewsCategoryId();
		sql+=",userBoId="+news.getUserBoId();
		sql+=",photo='"+news.getPhoto()+"'";
		sql+=",orderId="+news.getOrderId();
		if(!StringFunction.isEmpty(news.getStartDate()))
		sql+=",startDate='"+news.getStartDate()+"'";
		sql+=" WHERE newsId="+news.getNewsId();
		return db.execute(sql);
	}
	
	/****
	 * 添加点击次数
	 * ***/
	public void addVisitNum(int newsId){
		String sql="UPDATE  news SET visitNum=visitNum+1 WHERE newsId="+newsId;
		db.executeUpdate(sql);
	}
	
	/****
	 * 添加从im点击过来的次数
	 * ***/
	public void addVisitNumIM(int newsId){
		String sql="UPDATE  news SET visitNumIM=visitNumIM+1 WHERE newsId="+newsId;
		db.executeUpdate(sql);
	}
	
	public News showNewsById(int newsId){
		if(newsId<=0) return null;
		addVisitNum(newsId);//热度增加1
		String sql="SELECT n.newsId,n.content,n.subject,n.creatDate,n.visitNum,u.name,n.startDate,n.expiryDate,c.class1,c.class2,c.class3,c.class4";
		sql+=" FROM news AS n left JOIN vclass AS c ON n.newsCategoryId=c.newsCategoryId";
		sql+=" LEFT JOIN user_bo AS u ON u.userBoId=n.userBoId";
		sql+=" WHERE n.newsId="+newsId;		
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				News news=new News();
				news.setContent(rs.getString("n.content"));
				//news.setNewsCategoryId(rs.getInt("newsCategoryId"));
				news.setNewsId(rs.getInt("n.newsId"));
				//news.setOrderId(rs.getInt("orderId"));
				//news.setPhoto(rs.getString("photo"));
				news.setSubject(rs.getString("n.subject"));				
				news.setVisitNum(rs.getInt("visitNum"));
				//news.setVisitNumIM(rs.getInt("visitNum"));
				news.setCreatDate(rs.getString("n.creatDate"));
				//news.setSubtitle(rs.getString("subtitle"));
				news.setStartDate(rs.getString("n.startDate"));
				news.setExpiryDate(rs.getString("expiryDate"));
				news.setUserBoName(rs.getString("u.name"));
				ClassNum vclass=new ClassNum();
				vclass.setClass1(rs.getString("c.class1"));
				vclass.setClass2(rs.getString("c.class2"));
				vclass.setClass3(rs.getString("c.class3"));
				vclass.setClass4(rs.getString("c.class4"));
				news.setVclass(vclass);
				//news.setCategoryName(rs.getString("c.categoryName"));
				return news;
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
	 * 根据id得到资讯
	 * @param newsId 资讯id 
	 * @return
	 */
	public News getNewsById(int newsId){
		if(newsId<=0) return null;
		String sql="SELECT * FROM news WHERE newsId="+newsId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				News news=new News();
				news.setContent(rs.getString("content"));
				news.setNewsCategoryId(rs.getInt("newsCategoryId"));
				news.setNewsId(rs.getInt("newsId"));
				news.setOrderId(rs.getInt("orderId"));
				news.setPhoto(rs.getString("photo"));
				news.setSubject(rs.getString("subject"));				
				news.setVisitNum(rs.getInt("visitNum"));
				news.setVisitNumIM(rs.getInt("visitNum"));
				news.setCreatDate(rs.getString("creatDate"));
				news.setSubtitle(rs.getString("subtitle"));
				news.setStartDate(rs.getString("startDate"));
				news.setExpiryDate(rs.getString("expiryDate"));
				return news;
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
	 * 删除资讯
	 * @param newsIds 资讯的id用逗号隔开
	 * @return true 删除成功 ；false 删除失败
	 */
	public boolean delNewsById(String newsIds){
		if(StringFunction.isEmpty(newsIds)) return false;
		
		String sql="DELETE FROM news WHERE newsId IN ("+db.filterStr(newsIds)+")";
		return db.execute(sql);
	}
	
	//最新公告和缴费通知
	public List getTopNews(int num,String categoryIds){
		SearchNewsParam newsParam=new SearchNewsParam();
		newsParam.setNewsCategoryIds(categoryIds);
		List newsList=this.getTopNews(newsParam,num);	
		return newsList;
	}
	
	/**
	 * 得到前几条资讯
	 * @param newsParam 搜索参数
	 * @param num 返回条数
	 * @return
	 */
	private List getTopNews(SearchNewsParam newsParam,int num){
		List newsList=this.newsList(newsParam,0,num);	
		return newsList;		
	}
	
	/***
	 * 根据新闻的编号，获取新闻所属类型的信息
	 * ***/
	public NewsCategory getNewsCategoryByNewsId(int newsId){
		String sql=" SELECT c.* FROM news_category AS c,news AS n WHERE n.newsCategoryId=c.newsCategoryId AND n.newsId="+newsId;
		NewsCategory nc=null;
		ResultSet rs=db.select(sql);
		try{
			if(rs.next()){
				nc=new NewsCategory();
				nc.setCategoryName(rs.getString("categoryName"));
				nc.setNewsCategoryId(rs.getInt("newsCategoryId"));
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			db.closeResultSet(rs);
		}
		return nc;
	}

	/**
	 * 上一条新闻
	 * @param newsId
	 * @param newsCategoryId
	 * @return News
	 */
	public News getLastNewsId(int newsId,int newsCategoryId){
		String sql="SELECT newsId,subject FROM news WHERE newsCategoryId="+newsCategoryId
		      +" AND newsId<"+newsId+" ORDER BY newsId DESC  LIMIT 1 ";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				News news=new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setSubject(rs.getString("subject"));
				return news;
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
	 * 下一条新闻
	 * @param newsId
	 * @param newsCategoryId
	 * @return News
	 */
	public News getNextNewsId(int newsId,int newsCategoryId){
		String sql="SELECT newsId,subject FROM news WHERE newsCategoryId="+newsCategoryId
		       +" AND newsId>"+newsId+"   ORDER BY newsId ASC  LIMIT 1 ";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				News news=new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setSubject(rs.getString("subject"));
				return news;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}
//根据分类三的名字找到对应的IDS
	private String getClass3IdByClass3(String class3){
		if(StringFunction.isEmpty(class3)) return "";
        String classid="";		
		String sql="select distinct class3Id from vclass where class3='"+class3+"'";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				if(!StringFunction.isEmpty(classid)) classid+=",";
				classid+=rs.getString("class3Id");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return classid;
	}
}
