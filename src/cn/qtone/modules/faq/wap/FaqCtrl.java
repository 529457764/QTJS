package cn.qtone.modules.faq.wap;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.qtone.sys.base.BaseCtrl;
import cn.qtone.utils.Paginate;
import cn.qtone.utils.ParseTool;
import cn.qtone.utils.StringFunction;
import cn.qtone.modules.customer.obj.CustomerHandle;
import cn.qtone.modules.customer.obj.CustomerSession;
import cn.qtone.modules.customer.obj.CustomerSessionHandle;
import cn.qtone.modules.customer.obj.CustomerWx;
import cn.qtone.modules.customer.obj.CustomerWxHandle;
import cn.qtone.modules.faq.obj.*;
import cn.qtone.modules.news.obj.NewsHandle;
import cn.qtone.modules.news.obj.SearchNewsParam;
import cn.qtone.modules.user.obj.User_boHandle;
import cn.qtone.sys.base.BaseCtrl;

public class FaqCtrl extends BaseCtrl {
	CustomerWxHandle customerWxHandle = null;
	
	public FaqCtrl(HttpServletRequest request, HttpServletResponse response) {
		super(request,response);
		customerWxHandle = new CustomerWxHandle();
	}
	
	public void addFaq() {
		CustomerWx curcustomerWx = customerWxHandle.getCustomerWxSession(request);
		String openid = curcustomerWx.getOpenid();
		//CustomerWxHandle customerWxHandle = new CustomerWxHandle();
		CustomerWx customerWx = customerWxHandle.getCustomerWxByOpenid(openid);
		request.setAttribute("customerWx", customerWx);
		this.findForward("/jsp/wap/faq/add-faq.jsp");
	}
	
	public void addFaqPost() {
		//通过openid 找到对应 的customerId
		CustomerWx curcustomerWx = customerWxHandle.getCustomerWxSession(request);
		String openid = curcustomerWx.getOpenid();
		CustomerWx customerWx = this.getCustomrWxByReq(openid);
		
		String taxNO = request.getParameter("taxNO");
		if(StringFunction.isEmpty(taxNO)) {
			request.setAttribute("msg","税号不能为空！");
			//this.findForward("/jsp/wap/faq/add-faq.jsp");
			this.addFaq();
			return;
		}
		
		//CustomerHandle customerHandle=new CustomerHandle();
		int customerId=ParseTool.parseInt(request.getParameter("customerId"), 0);
		if(customerId<=0){
			request.setAttribute("msg","税号不正确！");
			this.addFaq();
			//this.findForward("/jsp/wap/faq/add-faq.jsp");	
			return;			
		}
		
		FaqHandle faqHandle=new FaqHandle();
		Faq faq=this.getFaqAskByReq(customerId);
		if(faqHandle.ask(faq)){//写入数据库
			curcustomerWx.setCompanyName(faq.getCompanyName());
			curcustomerWx.setTaxNO(taxNO);
			curcustomerWx.setCustomerId(customerId);
			customerWxHandle.updateCustomerWx(curcustomerWx);
			//生成提醒
			cn.qtone.modules.remind.bo.RemindCtrl remindCtrl=new cn.qtone.modules.remind.bo.RemindCtrl(request,response);
			remindCtrl.buildRemind(1);
			//request.setAttribute("msg","问题提交成功，我们会尽快给您回复。");
			String msg = "faqOk";
			this.jumpTo("/servlet/wap/WapEnter?handle=faqList&msg="+msg);
			return;
		}else{
			request.setAttribute("msg","意外错误，请重试!");
			//this.findForward("/jsp/wap/faq/add-faq.jsp");
			this.addFaq();
			return;			
		}
		
	}
	
	/**
	 * 得到问题
	 * @return
	 */
	private Faq getFaqAskByReq(int customerId){
		String questionTitle=request.getParameter("questionTitle");
		String question=request.getParameter("question");
		//int customerId=customerSession.getCustomerId();
		int faqCategoryId=ParseTool.parseInt(request.getParameter("faqCategoryId"),0);
		Faq faq=new Faq();
		faq.setFaqCategoryId(faqCategoryId);
		faq.setQuestion(question);
		faq.setCustomerId(customerId);
		faq.setIsShow(1);
		faq.setIsChecked(1);
		//faq.setCustomerAccountId(customerSession.getCustomerAccountId());
		faq.setQuestionTitle(questionTitle);
		faq.setLinkman(request.getParameter("linkman"));
		faq.setTel(request.getParameter("tel"));
		faq.setCompanyName(request.getParameter("companyName"));
		faq.setSource(2);
		return faq;
		
	}
	
	public CustomerWx getCustomrWxByReq(String openid) {
		CustomerWx customerWx =customerWxHandle.getCustomerWxByOpenid(openid);
		customerWx.setCompanyName(this.getStringValueByReq("companyName"));
		customerWx.setTaxNO(this.getStringValueByReq("taxNO"));
		return customerWx; 
	}
	/***
	 * 根据参数名称，在request取得该参数的值
	 * @return 返回 String 值
	 * **/
	private String getStringValueByReq(String param){
		String value=request.getParameter(param);
		if(StringFunction.isEmpty(value)){
			return "";
		}else{
			return  value;
		}
	}
	/***
	 * 根据参数名称，在request取得该参数的值
	 * @return 返回 Int 值
	 * **/
	private int getIntValueByReq(String param){
		String value=request.getParameter(param);
		return ParseTool.parseInt(value,0);
	}
	
	
	
	public void faq_list() {
		SearchFaqParam param=new SearchFaqParam();
		param.setIsShow(2);
		param.setIsChecked(2);
		String keyword=request.getParameter("keyword");
		if(!StringFunction.isEmpty(keyword)){
			param.setKeyword(keyword);
		}
		
		FaqHandle faqHandle=new FaqHandle();
		int totalRow=faqHandle.totalRow(param);		
		
		//分页对象
		Paginate paginate = new Paginate(totalRow,10,request);	
		
		//当页faq数据
		List faqList=faqHandle.search(param,paginate.startPos(),paginate.getPagesize());
		
		request.setAttribute("faqList",faqList);
		request.setAttribute("paginate",paginate);
		this.findForward("/jsp/wap/faq/faq-list.jsp");
		return;
		
	}
	
	public void faq_detail() {
		int faqId=ParseTool.parseInt(request.getParameter("faqId"),0);
		if(faqId <= 0){
			request.setAttribute("msg","请选择问题！");
			this.jumpTo("/servlet/wa/Channel?handle=faq_list");
			return;
		}
		FaqHandle faqHandle=new FaqHandle();
		Faq faq = faqHandle.getFaqById(faqId);
		request.setAttribute("faq", faq);
		this.findForward("/jsp/wap/faq/faq-detail.jsp");
	}
	
	public void searchFaqJSON() {
		FaqHandle faqHandle=new FaqHandle();		
		SearchFaqParam param=this.getSearchParam();
		int totalRow=faqHandle.totalRow(param);		
		//分页对象
		Paginate paginate = new Paginate(totalRow,10,request);	
		//当页faq数据
		List faqList=faqHandle.search(param,paginate.startPos(),paginate.getPagesize());
		this.printJSONArray(faqList);
	}
		
	/**
	 * 得到搜索的参数对象
	 * @ param customerSession 企业登录的session对象
	 * @return
	 */
	private SearchFaqParam getSearchParam(){
		
		String keyword=request.getParameter("keyword");
		int faqCategoryId=ParseTool.parseInt(request.getParameter("faqCategoryId"),0);
		SearchFaqParam param=new SearchFaqParam();
		param.setFaqCategoryId(faqCategoryId);
		param.setKeyword(keyword);
		
//		if(customerSession!=null){
//			param.setCustomerId(customerSession.getCustomerId());
//		}
		return param;
	}
}
