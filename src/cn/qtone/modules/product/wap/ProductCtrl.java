package cn.qtone.modules.product.wap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.qtone.modules.news.obj.NewsHandle;
import cn.qtone.modules.product.obj.*;
import cn.qtone.sys.base.BaseCtrl;
import cn.qtone.utils.Paginate;
import cn.qtone.utils.ParseTool;
import cn.qtone.utils.StringFunction;

public class ProductCtrl extends BaseCtrl{

	public ProductCtrl(HttpServletRequest request,HttpServletResponse response) {
		super(request,response);
	}
	
	/**
	 * 显示产品信息页面
	 *
	 */
	public void showProduct(){
		int productId=ParseTool.parseInt(request.getParameter("productId"),0);
		if(productId<0){
			this.printHTML("error");
			return;
		}
		
		//产品信息
		ProductHandle productHandle=new ProductHandle();
		Product product=productHandle.getProductById(productId);
		
		request.setAttribute("product",product);
		this.findForward("/jsp/wa/channel/product/show_product.jsp");
	}
	
	/**
	 * 搜索产品
	 *
	 */
	public void searchProduct(){
		
		//最新公告
//		NewsHandle newsHandle=new NewsHandle();
//		List newsList1=newsHandle.getTopNews(10,1);
		
		//搜索参数
		SearchProductParam searchProductParam=this.getSearchProductParam();
		
		ProductHandle productHandle=new ProductHandle();
		int totalRow=productHandle.totalRow(searchProductParam);
		
		Paginate paginate = new Paginate(totalRow,12,request);
		
		List productList=productHandle.searchWa(searchProductParam,paginate.startPos(),paginate.getPagesize());
		
//		request.setAttribute("newsList1",newsList1);
		request.setAttribute("paginate",paginate);
		request.setAttribute("productList",productList);
		this.findForward("/jsp/wa/channel/product/search_product.jsp");
		
	}
	
	/**
	 * 得到搜索参数
	 * @return
	 */
	private SearchProductParam getSearchProductParam(){
		SearchProductParam param=new SearchProductParam();
		int productTypeId=ParseTool.parseInt(request.getParameter("productTypeId"),0);
		param.setProductTypeId(productTypeId);
		param.setIsShow(1);
		param.setPState(1);
		return param;
	}
	
	
	//wap页面
	/**
	 * 跳转至wap产品中心
	 *
	 */
	public void showItems() {
		this.findForward("/jsp/wap/product/product.jsp");
	}
	
	/**
	 * 跳转至wap产品详情
	 *
	 */
	public void showDetail() {
		int productTypeId=ParseTool.parseInt(request.getParameter("productTypeId"),0);
		//设置产品类型
		String productType = "";
		if(productTypeId == 3) {
			productType = "电脑计算机";
		} else if(productTypeId == 2) {
			productType = "打印机";
		} else if(productTypeId == 1) {
			productType = "扫描仪";
		} else if(productTypeId == 5) {
			productType = "软件";
		}
		request.setAttribute("productType", productType);
		String active = "true";
		request.setAttribute("active", active);
		
		SearchProductParam productParam = new SearchProductParam();
		productParam.setProductTypeId(productTypeId);
		productParam.setIsShow(1);
		//获取该产品类型的所有产品
		ProductHandle productHandle = new ProductHandle();
		List productList = productHandle.search(productParam, 0, 50);
		request.setAttribute("productList", productList);
		this.findForward("/jsp/wap/product/product-detail.jsp");
	}
	
	/**
	 * 跳转至computer.jsp
	 *
	 */
	public void showComputer() {
		this.findForward("/jsp/wap/product/computer.jsp");
	}
	
	/**
	 * 跳转至printer.jsp
	 *
	 */
	public void showPrinter() {
		this.findForward("/jsp/wap/product/printer.jsp");
	}
	
	/**
	 * 跳转至scanner.jsp
	 *
	 */
	public void showScanner() {
		this.findForward("/jsp/wap/product/scanner.jsp");
	}
	
	/**
	 * 跳转至software.jsp
	 *
	 */
	public void showSoftware() {
		this.findForward("/jsp/wap/product/software.jsp");
	}
}
