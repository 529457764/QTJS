package cn.qtone.enter.wap;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.qtone.modules.news.wap.NewsCtrl;
import cn.qtone.modules.product.wap.ProductCtrl;
import cn.qtone.modules.service.wap.ServiceCtrl;
//import cn.qtone.modules.shop.login.wa.LoginCtrl;
import cn.qtone.modules.trouble.wap.TroubleCtrl;
import cn.qtone.modules.customer.wap.CustomerCtrl;
import cn.qtone.modules.customer.wap.LoginCtrl;
import cn.qtone.modules.faq.wap.FaqCtrl;

public class WapEnter extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private ServletConfig config;
	
	public WapEnter(){
		super();
	}
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding("UTF-8");
		String handle = request.getParameter("handle");
		String type = request.getParameter("type");
		//新闻页面和操作指引页面
		if("news".equals(handle)){
			NewsCtrl newsCtrl=new NewsCtrl(request,response);
			newsCtrl.searchNews();
		//新闻详情
		} else if("news_detail".equals(handle)){
			NewsCtrl newsCtrl=new NewsCtrl(request,response);
			newsCtrl.news_detail();
		//newsJSON
		} else if("newsJSON".equals(handle)){
			NewsCtrl newsCtrl=new NewsCtrl(request,response);
			newsCtrl.searchNewsJSON();	
		//产品列表页面
		} else if("product".equals(handle)){
			ProductCtrl productCtrl = new ProductCtrl(request, response);
			if("computer".equals(type)){
				productCtrl.showComputer();
			} else if("printer".equals(type)){
				productCtrl.showPrinter();
			} else if("scanner".equals(type)){
				productCtrl.showScanner();
			} else if("software".equals(type)){
				productCtrl.showSoftware();
			} else{
				productCtrl.showItems();
			}
		//产品详细页面
		} else if("showProduct".equals(handle)){
			ProductCtrl productCtrl = new ProductCtrl(request, response);
			productCtrl.showDetail();
		//服务中心页面	
		} else if("service".equals(handle)) {
			ServiceCtrl serviceCtrl = new ServiceCtrl(request, response);
			if("operating".equals(type)) {
				serviceCtrl.showOperating();
			} else if("time".equals(type)) {
				serviceCtrl.showTime();
			} else if("standard".equals(type)) {
				serviceCtrl.showStandard();
			} else if("connection".equals(type)) {
				serviceCtrl.showConnection();
			} else {
				serviceCtrl.showItems();
			}
			
		//我的报障--增加报障表单
		} else if("addTrouble".equals(handle)){
			TroubleCtrl troubleCtrl=new TroubleCtrl(request,response);
			troubleCtrl.addTroubleWx();
		//我的报障--增加报障提交
		} else if("addTroublePost".equals(handle)){
			TroubleCtrl troubleCtrl=new TroubleCtrl(request,response);
			troubleCtrl.addTroubleWxPost();
		//我的报障--报障列表
		} else if("troubleList".equals(handle)){
			TroubleCtrl troubleCtrl=new TroubleCtrl(request,response);
			troubleCtrl.myTroubleWx();
		//我的报障--预览报障
		} else if("troubleDetail".equals(handle)) {
			TroubleCtrl troubleCtrl = new TroubleCtrl(request, response);
			//troubleCtrl.preview();
			troubleCtrl.trouble_detail();
		//咨询页面--我要咨询	
		} else if("addFaq".equals(handle)) {
			FaqCtrl faqCtrl = new FaqCtrl(request, response);
			faqCtrl.addFaq();
		//咨询页面--咨询提交	
		} else if("addFaqPost".equals(handle)) {
			FaqCtrl faqCtrl = new FaqCtrl(request, response);
			faqCtrl.addFaqPost();
		//咨询页面--咨询列表
		} else if("faqList".equals(handle)) {
			FaqCtrl faqCtrl = new FaqCtrl(request, response);
			faqCtrl.faq_list();
		//咨询页面--咨询详情
		} else if("faqDetail".equals(handle)) {
			FaqCtrl faqCtrl = new FaqCtrl(request, response);
			faqCtrl.faq_detail();
		//通过TaxNO，动态获取信息
		} else if("getInfoByTaxNO".equals(handle)) {
			CustomerCtrl customerCtrl = new CustomerCtrl(request, response);
			customerCtrl.getInfoByTaxNO();
		//动态加载内容
		} else if("faqJSON".equals(handle)) {
			FaqCtrl faqCtrl = new FaqCtrl(request, response);
			faqCtrl.searchFaqJSON();
		
		//首页	
		}
		else {
			//request.getRequestDispatcher("/jsp/wap/index.jsp").forward(request, response);
			LoginCtrl loginCtrl = new LoginCtrl(request, response);
			loginCtrl.login();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.doGet(request,response);
	}
	public void init() throws ServletException {
		// Put your code here
	}
}
