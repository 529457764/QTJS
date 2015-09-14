package cn.qtone.modules.trouble.wap;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.qtone.modules.city.obj.CityHandle;
import cn.qtone.modules.customer.obj.Customer;
import cn.qtone.modules.customer.obj.CustomerHandle;
import cn.qtone.modules.customer.obj.CustomerSession;
import cn.qtone.modules.customer.obj.CustomerSessionHandle;
import cn.qtone.modules.customer.obj.CustomerWx;
import cn.qtone.modules.customer.obj.CustomerWxHandle;
import cn.qtone.modules.faq.obj.Faq;
import cn.qtone.modules.trouble.obj.SearchTroubleParam;
import cn.qtone.modules.trouble.obj.Trouble;
import cn.qtone.modules.trouble.obj.TroubleHandle;
//import cn.qtone.modules.trouble.obj.TroubleWxHandle;
import cn.qtone.sys.base.BaseCtrl;
import cn.qtone.utils.Paginate;
import cn.qtone.utils.ParseTool;
import cn.qtone.utils.StringFunction;



public class TroubleCtrl extends BaseCtrl{
    
	TroubleHandle troubleHandle=new TroubleHandle();
	CustomerWxHandle customerWxHandle = null;
	//CustomerWx customerWx = null;
	//CustomerSessionHandle customerSessionHandle=null;
	//CustomerSession customerSession=null;
	
	public TroubleCtrl(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		customerWxHandle = new CustomerWxHandle();
		//customerWx = new CustomerWx();
		//customerSessionHandle=new CustomerSessionHandle();
		//customerSession=customerSessionHandle.getCustomerSession(request);
		
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据request对象获取相关参数
	 * **/
	/*
	private Trouble getTroubleByReq(){
		Trouble trouble=new Trouble();		
		trouble.setAddress(this.getStringValueByReq("address"));
		trouble.setCityId(this.getIntValueByReq("cityId"));
		trouble.setContent(this.getStringValueByReq("content"));
		trouble.setCustomerId(customerSession.getCustomerId());
		trouble.setLinkman(this.getStringValueByReq("linkman"));
		trouble.setTel(this.getStringValueByReq("tel"));
		trouble.setTroubleTypeId(this.getIntValueByReq("troubleTypeId"));
		trouble.setCustometAccountId(customerSession.getCustomerAccountId());
		return trouble;
	}
	*/
	
	/**
	 * 输出客户的保障次数
	 *
	 */
	public void getTroubleTimes(){
		CustomerSessionHandle customerSessionHandle=new CustomerSessionHandle();
		CustomerSession customerSession=customerSessionHandle.getCustomerSession(request);
		
		TroubleHandle troubleHandle=new TroubleHandle();
		int troubleTimes=troubleHandle.getTroubleTimes(customerSession.getCustomerId());
		
		this.printHTML(troubleTimes+"");
	}
	
	
	//wap
	//增加报障表单
	public void addTroubleWx(){
		/*
		HttpSession session = request.getSession();
		CustomerWx curcustomerWx = (CustomerWx)session.getAttribute("curCustomerWx");
		*/
		CustomerWx curcustomerWx = customerWxHandle.getCustomerWxSession(request);
		String openid = curcustomerWx.getOpenid();
			
		CityHandle cityHandle=new CityHandle();
		List cityList=cityHandle.cityList();
		request.setAttribute("cityList",cityList);
		
		//CustomerWxHandle customerWxHandle = new CustomerWxHandle();
		//customerWx表中相关属性赋值到add-trouble.jsp
		CustomerWx customerWx = customerWxHandle.getCustomerWxByOpenid(openid);
		request.setAttribute("customerWx", customerWx);
		
		this.findForward("/jsp/wap/trouble/add-trouble.jsp");
	}
		
	/**
	 * 报障提交
	 */
	public void addTroubleWxPost(){
		/*
		HttpSession session = request.getSession();
		CustomerWx curcustomerWx = (CustomerWx)session.getAttribute("curCustomerWx");
		*/
		CustomerWx curcustomerWx = customerWxHandle.getCustomerWxSession(request);
		//String openid = curcustomerWx.getOpenid();
		String taxNO = request.getParameter("taxNO");
		if(StringFunction.isEmpty(taxNO)) {
			request.setAttribute("msg","税号不能为空！");
			//System.out.println("税号不能为空！");
			this.addTroubleWx();
			//this.findForward("/jsp/wap/trouble/add-trouble.jsp");
			return;
		}
		
		//CustomerHandle customerHandle=new CustomerHandle();
//		int customerId=customerHandle.getCustomerIdByTaxNO(taxNO);
		int customerId=ParseTool.parseInt(request.getParameter("customerId"), 0);
		if(customerId<=0){
			request.setAttribute("msg","税号不正确！");
			//System.out.println("税号不正确！");
			this.addTroubleWx();
			//this.findForward("/jsp/wap/trouble/add-trouble.jsp");	
			this.jumpTo("/servlet/wap/WapEnter?handle=addTrouble");
			return;			
		}
		Trouble troubleWx = this.getTroubleWxByReq(customerId);
		if(troubleHandle.addTrouble(troubleWx)){
			//CustomerWxHandle customerWxHandle = new CustomerWxHandle();
			curcustomerWx.setCompanyName(troubleWx.getCompanyName());
			curcustomerWx.setTaxNO(taxNO);
			curcustomerWx.setCustomerId(customerId);
			customerWxHandle.updateCustomerWx(curcustomerWx);
			//生成提醒
			cn.qtone.modules.remind.bo.RemindCtrl remindCtrl=new cn.qtone.modules.remind.bo.RemindCtrl(request,response);
			remindCtrl.buildRemind(3);
			//request.setAttribute("msg","故障提交成功，我们会尽快安排人员跟进。");
			//String msg = "故障提交成功，我们会尽快安排人员跟进。";
			//System.out.println("数据库更新成功");
			String msg = "ok";
			this.jumpTo("/servlet/wap/WapEnter?handle=troubleList&msg="+msg);
		}else{
			request.setAttribute("msg","报障提交失败，请重新提交");
			//System.out.println("数据库更新失败");
			request.setAttribute("trouble", troubleWx);
			this.addTroubleWx();
		}
	}
	
		
	/**
	 * 获取表单数据
	 * @param openid
	 * @return
	 */
	public Trouble getTroubleWxByReq(int customerId){
		
		Trouble troubleWx = new Trouble();
		troubleWx.setCustomerId(customerId);
		troubleWx.setCompanyName(this.getStringValueByReq("companyName"));
		troubleWx.setAddress(this.getStringValueByReq("address"));
		troubleWx.setCityId(this.getIntValueByReq("cityId"));
		troubleWx.setTroubleTypeId(this.getIntValueByReq("troubleTypeId"));
		troubleWx.setLinkman(this.getStringValueByReq("linkman"));
		troubleWx.setTel(this.getStringValueByReq("tel"));
		troubleWx.setContent(this.getStringValueByReq("content"));
		troubleWx.setSource(2);
		return troubleWx;
	}
	
	public CustomerWx getCustomrWxByReq(String openid) {
		//CustomerWxHandle customerWxHandle = new CustomerWxHandle();
		CustomerWx customerWx =customerWxHandle.getCustomerWxByOpenid(openid);
		customerWx.setCompanyName(this.getStringValueByReq("companyName"));
		customerWx.setTaxNO(this.getStringValueByReq("taxNO"));
		return customerWx; 
	}
	
	public void myTroubleWx() {
		/*
		HttpSession session = request.getSession();
		CustomerWx curcustomerWx = (CustomerWx)session.getAttribute("curCustomerWx");
		*/
		CustomerWx curcustomerWx = customerWxHandle.getCustomerWxSession(request);
		String openid = curcustomerWx.getOpenid();
		//从数据库获取列表信息，返回一个List对象
		//CustomerWxHandle customerWxHandle = new CustomerWxHandle();
		CustomerWx customerWx = customerWxHandle.getCustomerWxByOpenid(openid);
		int customerId = customerWx.getCustomerId();
		SearchTroubleParam param = new SearchTroubleParam();
		param.setCustomerId(customerId);
		TroubleHandle troubleHandle = new TroubleHandle();
		
		//List troubleWxList = troubleHandle.getTroubleWxByCustomerId(customerId);
		List troubleWxList = troubleHandle.search(param, 0, 100);
		//System.out.println(troubleWxList.size());
		request.setAttribute("troubleWxList", troubleWxList);
		this.findForward("/jsp/wap/trouble/trouble-list.jsp");
	}
	
	public void trouble_detail() {
		int troubleId=ParseTool.parseInt(request.getParameter("troubleId"),0);
		TroubleHandle troubleHandle = new TroubleHandle();
		//System.out.println(troubleId);
		Trouble troubleWx = troubleHandle.getTroubleWxByTroubleId(troubleId);
		request.setAttribute("troubleWx", troubleWx);
		this.findForward("/jsp/wap/trouble/trouble-detail.jsp");
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
}
