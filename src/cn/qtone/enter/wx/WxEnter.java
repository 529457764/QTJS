package cn.qtone.enter.wx;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.qtone.modules.news.wx.NewsCtrl;

public class WxEnter extends HttpServlet {

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private ServletConfig config;
	
	public WxEnter(){
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding("UTF-8");
		
//		cn.qtone.modules.regCode.wx.YccsCtrl yccsCtrl=new cn.qtone.modules.regCode.wx.YccsCtrl(request,response);
//		yccsCtrl.queryRegCode();
		String handle = request.getParameter("handle");
		
		if("news".equals(handle)){
			NewsCtrl newsCtrl=new NewsCtrl(request,response);
			newsCtrl.searchNews();
		}else if("newsJSON".equals(handle)){
			NewsCtrl newsCtrl=new NewsCtrl(request,response);
			newsCtrl.searchNewsJSON();
		}else if("newsDetail".equals(handle)){
			NewsCtrl newsCtrl=new NewsCtrl(request,response);
			newsCtrl.news_detail();
		}
	}
	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}	
}
