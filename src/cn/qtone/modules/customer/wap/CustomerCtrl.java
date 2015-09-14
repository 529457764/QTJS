package cn.qtone.modules.customer.wap;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.qtone.modules.customer.obj.Customer;
import cn.qtone.modules.customer.obj.CustomerHandle;
import cn.qtone.sys.base.BaseCtrl;
import cn.qtone.utils.ParseTool;

public class CustomerCtrl extends BaseCtrl {
	public CustomerCtrl(HttpServletRequest request,HttpServletResponse response) {
		super(request,response);
	}
	
	public void getInfoByTaxNO() {
		String taxNO = request.getParameter("taxNO");
		CustomerHandle customerHandle = new CustomerHandle();
		Customer customer = customerHandle.getCustomerBaseInfoByTaxNO(taxNO);
		HashMap hm=new HashMap();
		if(customer == null) {
			hm.put("msg","暂无此用户信息");
			hm.put("flag","2");
			this.printJSON(hm);
			return;
		} else {
			hm.put("msg","查询成功");
			hm.put("flag","1");
			hm.put("customer", customer);
			this.printJSON(hm);
			return;
		}
		
	}
}
