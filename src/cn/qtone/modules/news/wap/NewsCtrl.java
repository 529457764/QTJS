package cn.qtone.modules.news.wap;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import cn.qtone.modules.news.obj.News;
import cn.qtone.modules.news.obj.NewsCategory;
import cn.qtone.modules.news.obj.NewsCategoryHandle;
import cn.qtone.modules.news.obj.NewsHandle;
import cn.qtone.modules.news.obj.SearchNewsParam;
import cn.qtone.sys.base.BaseCtrl;
import cn.qtone.utils.Paginate;
import cn.qtone.utils.ParseTool;
import cn.qtone.utils.StringFunction;
import cn.qtone.utils.WxUtil;

public class NewsCtrl extends BaseCtrl{

	public NewsCtrl(HttpServletRequest request,HttpServletResponse response) {
		super(request,response);
	}
	/**
	 * 资讯频道
	 *
	 */
	public void searchNews(){
		NewsHandle newsHandle=new NewsHandle();
//		List newsList1=newsHandle.getTopNews(10,1);
//		cn.qtone.utils.HttpClientProxy httpClientProxy=new cn.qtone.utils.HttpClientProxy();
//		String code=request.getParameter("code");
//		String jsonStr=httpClientProxy.getHtmlByGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+cn.qtone.modules.Const.wxAppid+"&secret="+cn.qtone.modules.Const.wxSecret+"&code="+code+"&grant_type=authorization_code", "UTF-8");
//		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
////		System.out.println(jsonObject.get("access_token"));
////		System.out.println(jsonObject.get("openid"));		
//		//获取微信用户基本信息
//		String jsonBaseInfoStr=httpClientProxy.getHtmlByGet("https://api.weixin.qq.com/sns/userinfo?access_token="+jsonObject.get("access_token")+"&openid="+jsonObject.get("openid")+"&lang=zh_CN","UTF-8");
//		System.out.println("jsonBaseInfoStr="+jsonBaseInfoStr);
		
//		String code=request.getParameter("code");
//		if(!StringFunction.isEmpty(code)){
//			JSONObject jsonOpenid=WxUtil.fretchBaseInfo(code);
//			logger.info("openid="+jsonOpenid.getString("openid"));
//			JSONObject jsonUserinfo=WxUtil.fretchUserInfo(jsonOpenid.getString("access_token"), jsonOpenid.getString("openid"));
//			logger.info("nickname="+jsonUserinfo.getString("nickname"));
//		}
		
		
		SearchNewsParam param=this.getSearchNewsParam();
		
		String categoryName="搜索结果";
		int categoryParentId=0;//类型的父类编号，用于显示图片
		int newsCategoryIds=ParseTool.parseInt(param.getNewsCategoryIds(), 0);
		if(newsCategoryIds>0) {
			NewsCategoryHandle newsCategoryHandle=new NewsCategoryHandle();
			categoryName=newsCategoryHandle.getCategoryNameById(newsCategoryIds);
			//获得父类编号
			categoryParentId=newsCategoryHandle.getParentIdByCategoryId(newsCategoryIds);
			categoryParentId=categoryParentId==0?1:categoryParentId;
		}
		
		int totalRow=newsHandle.newsListTotalRow(param);

		//分页对象
//		Paginate paginate = new Paginate(totalRow,request);

		//信息列表数据
		List newsList=newsHandle.newsList(param,0,param.getPagesize());
		request.setAttribute("categoryName",categoryName);
		request.setAttribute("newsList",newsList);
//		request.setAttribute("paginate",paginate);		

		//this.findForward("/jsp/wap/news/news_list.jsp");
		this.findForward("/jsp/wap/news/news.jsp");
	}
	
	/**
	 * 操作指引频道
	 *
	 */
	public void searchGuideLine(){
		NewsHandle newsHandle=new NewsHandle();
		SearchNewsParam param=this.getSearchNewsParam();	
		String categoryName="搜索结果";
		int categoryParentId=0;//类型的父类编号，用于显示图片
		int newsCategoryIds=ParseTool.parseInt(param.getNewsCategoryIds(), 0);
		if(newsCategoryIds>0) {
			NewsCategoryHandle newsCategoryHandle=new NewsCategoryHandle();
			categoryName=newsCategoryHandle.getCategoryNameById(newsCategoryIds);
			//获得父类编号
			categoryParentId=newsCategoryHandle.getParentIdByCategoryId(newsCategoryIds);
			categoryParentId=categoryParentId==0?1:categoryParentId;
		}
		
		int totalRow=newsHandle.newsListTotalRow(param);
		List newsList=newsHandle.newsList(param,0,param.getPagesize());
		request.setAttribute("categoryName",categoryName);
		request.setAttribute("newsList",newsList);
		this.findForward("/jsp/wap/guideline/guideline.jsp");
	}

	/**
	 * 资讯频道
	 *
	 */
	public void searchNewsJSON(){
		NewsHandle newsHandle=new NewsHandle();
//		List newsList1=newsHandle.getTopNews(10,1);
		
		SearchNewsParam param=this.getSearchNewsParam();

		int startPos=param.getPage()>0?((param.getPage()-1)*param.getPagesize()):0;

		//信息列表数据
		List newsList=newsHandle.newsList(param,startPos,param.getPagesize());
//		HashMap map=new HashMap();
//		map.put("trainList", newsList);
		this.printJSONArray(newsList);
	}
	/**
	 * 资讯详细
	 *
	 */
	public void news_detail(){
		int newsId=ParseTool.parseInt(request.getParameter("newsId"),0);
//		String from=request.getParameter("from");
		NewsHandle newsHandle=new NewsHandle();
		News news=newsHandle.getNewsById(newsId);
//		if(!cn.qtone.utils.StringFunction.isEmpty(from)){
//			if(from.equals("im")){//来自im的访问
//				newsHandle.addVisitNumIM(newsId);
//			}
//		}
		newsHandle.addVisitNum(newsId);//增加浏览次数
//		NewsCategory newsCategory=newsHandle.getNewsCategoryByNewsId(newsId);
//		List newsList1=newsHandle.getTopNotice();
//		
//		request.setAttribute("newsList1",newsList1);
//		request.setAttribute("lastNews",newsHandle.getLastNewsId(newsId,newsCategory.getNewsCategoryId()));
//		request.setAttribute("nextNews",newsHandle.getNextNewsId(newsId,newsCategory.getNewsCategoryId()));
//		request.setAttribute("newsCategory",newsCategory);
		request.setAttribute("news",news);
//		if(news.getNewsCategoryId()>=119 && news.getNewsCategoryId()<=124)//服务项单独显示一个新页面
//		{
//			this.findForward("/jsp/wa/channel/server/server_show.jsp");	
//		}
//		else
		//this.findForward("/jsp/wap/news/news_detail.jsp");
		this.findForward("/jsp/wap/news/news-detail.jsp");
	}
	
	
	/**
	 * 得到搜索资讯的参数对象
	 * @return
	 */
	private SearchNewsParam getSearchNewsParam(){
		String keyword=request.getParameter("keyword");
		String newsCategoryId=request.getParameter("newsCategoryId");
		SearchNewsParam param=new SearchNewsParam();
		param.setKeyword(keyword);
		param.setNewsCategoryIds(newsCategoryId+"");
		param.setIsWa(2);
		param.setPage(ParseTool.parseInt(request.getParameter("page"), 1));
		return param;
	}	
}
