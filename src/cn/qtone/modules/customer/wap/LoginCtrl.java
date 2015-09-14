package cn.qtone.modules.customer.wap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import cn.qtone.modules.customer.obj.CustomerWxHandle;
import cn.qtone.sys.base.BaseCtrl;
import cn.qtone.utils.WxUtil;
import cn.qtone.modules.customer.obj.CustomerWx;

public class LoginCtrl extends BaseCtrl {
	public LoginCtrl(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	/**
	 * 微信客户进入页面
	 */
	public void login(){
		/*
		String code = request.getParameter("code");
		JSONObject baseInfo = JSONObject.fromObject(WxUtil.fretchBaseInfo(code));
		String access_token = baseInfo.getString("access_token");
		String openid = baseInfo.getString("openid");
		//把用户信息写入customer_wx数据库
		CustomerWxHandle customerWxHandle = new CustomerWxHandle();
		//customerWxHandle.updateCustomerWx(access_token, openid);
		
		//把openid写入session
		HttpSession session = request.getSession();
		session.setAttribute("openid", openid);
		*/
		this.findForward("/jsp/wap/index.jsp");
	}
}
