package cn.qtone.modules.customer.obj;


import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class ExportHtHandle extends BaseHandle{
	
	public ExportHtHandle(){
		super();
	}

	private int getFileID(){
		 int fileId=db.getValueIntMain("SELECT htFileId  FROM ht_file_id  ");
		db.execute("UPDATE ht_file_id SET htFileId=htFileId+1 ");//每次取文件编号都更新一次
		return fileId;
		
	}
	
	/***
	 * 获取航天xml
	 * @param customerList
	 * @return
	 */
	public  String getHtXml(List customerList) {
		  
		  Document dom=DocumentHelper.createDocument();//创建xml文件
		  dom.setXMLEncoding("GBK"); 		  
		  Element root=dom.addElement("ResultSetS");//添加根元素 		 	  
		  Element node = root.addElement("InterfaceID"); 
	      node.setText("82");	        
	      node = root.addElement("FileID");   
	     // node.setText("327");//不能重复
	      node.setText(this.getFileID()+"");//不能重复
	      for(int i=0;i<customerList.size();i++){
	    	  Customer customer=(Customer)customerList.get(i);
		      Element rsNode = root.addElement("ResultSet");  //ResultSet  每个 ResultSet 记录一家企业资料
		      node = rsNode.addElement("Master"); 
		      Element rowNode = node.addElement("RowMaster");  //RowMaster
		      rowNode.addAttribute("id",(i+1)+"");
		      Element colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","KHLXID"); //客户类型ID  不能为空
		      colNode.setText(this.getKHLXID(customer.getTaxType())+""); //5 其他;1个人;2税务机关;3企业;4 经销商;6 集团客户
	
		      
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","KHSH");//税号  可以为空
		      colNode.setText(customer.getTaxNO());
		      
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","KHMC"); //公司名称 不能为空
		      colNode.setText(customer.getCompanyName());
		      
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","KHBM"); //税号  客户编码  可以为空
		      colNode.setText(customer.getTaxNO());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","KJM"); //税号  快捷码  可以为空
		      colNode.setText(customer.getTaxNO());
		      
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","SJDWKHID");//上级单位客户ID 数字  一般为空 
		      colNode.setText("");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","QYLXID"); //企业类型ID 不能为空  除了机关单位和个人有对应，其余的全部为其他
		      colNode.setText(this.getQYLXID(customer.getTaxType())+"");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","HYLXID");//行业类型ID  不能为空   分不清，全部设置为其他
		      colNode.setText("21");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","QHID");  //区划ID  不能为空 中山 2293
		      colNode.setText("2293");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","SWJID"); //税务局ID  不能为空    ???
		      colNode.setText(this.getSWJID(customer.getCityId()+""));
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","FJID");//分局所ID  可以为空   ???
		      colNode.setText(this.getSWJID(customer.getCityId()+""));
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","DH1");  //电话1  可以为空 
		      colNode.setText(customer.getBossTel());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","DH2");  //电话2  可以为空 
		      colNode.setText(customer.getTel());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","DH3"); //电话3  可以为空 
		      colNode.setText(customer.getLastTel());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","CZ"); //传真  可以为空
		      colNode.setText(customer.getFax());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","FRDB"); //法人代表  可以为空
		      colNode.setText(customer.getBossName());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","EMAIL"); //邮箱地址  可以为空
		      colNode.setText("");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","URL");//网址  可以为空
		      colNode.setText(""); 
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","JYFW");//经营范围  可以为空
		      colNode.setText("");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","BZ"); //备注   可以为空
		      colNode.setText("");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","KHYH");//开户银行   可以为空
		      colNode.setText(customer.getBank());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","YHZH");//银行账号  可以为空
		      colNode.setText(customer.getBankAccount());
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","ZYXTKHID"); //自有系统客户ID  可以为空
		      colNode.setText(customer.getCustomerId()+"");
		      colNode = rowNode.addElement("col");
		      colNode.addAttribute("name","SFXGM");  //是否小规模(1:是；0:否)  数字  可以为空
		      colNode.setText(customer.getTaxType()==2?"1":"0"); 
		      
		      
		      
		      Element dtsNode=rsNode.addElement("DetailS"); ///DetailS
		      node=dtsNode.addElement("Detail");   //Detail  RowDetail id="1"  组织客户地址表
		      node.addAttribute("NO","1");
		      node.addAttribute("name","组织客户地址表");
		      node=node.addElement("RowDetail"); 
		      node.addAttribute("id",(i+1)+"");	
		      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","TXDZ");//通讯地址(办公地址)  不能为空
		      colNode.setText(this.getAddress(customer.getBusinessAddress(),customer.getAddress()));
		      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","YZBM"); //邮政编码  可以为空
		      colNode.setText("");
		      	      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","LXR");//联系人  可以为空
		      colNode.setText(customer.getLinkman());	      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","DZMC");//地址名称  不能为空
		      colNode.setText(this.getAddress(customer.getAddress(),customer.getBusinessAddress()));
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","DH");//电话  可以为空
		      colNode.setText(customer.getTel());
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","CZ");//传真  可以为空
		      colNode.setText(customer.getFax());
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","SJ");//手机  可以为空
		      colNode.setText(customer.getMobile());
		      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","SFMRDZ"); //是否默认地址（1是 0否） 数字  不能为空
		      colNode.setText("1");
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","ZYXTKHDZID");//自有系统客户地址ID  可以为空
		      colNode.setText(customer.getCustomerId()+"");
		      
		      
		      
		      node=dtsNode.addElement("Detail");   //Detail  RowDetail id="2"  客户服务产品表
		      node.addAttribute("NO","2");
		      node.addAttribute("name","客户服务产品表");
		      node=node.addElement("RowDetail"); 
		      node.addAttribute("id",(i+1)+"");	
		      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","KPY");//开票员   可以为空
		      colNode.setText("");
		      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","PXRQ");//培训日期   可以为空
		      colNode.setText("");
		      	      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","CJ");//成绩   可以为空
		      colNode.setText("");	      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","ZSHM");//证书号码  可以为空
		      colNode.setText("");
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","ZJGXKH");//是否共享系统用户（1是 0否）  不能为空
		      colNode.setText("0");
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","FWRYID");//服务人员ID  可以为空
		      colNode.setText("");
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","FWKH"); //服务卡号  可以为空
		      colNode.setText("");	      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","ZCRQ");//注册日期   不能为空//装卡日期?
		      colNode.setText(this.getRegDate(customer));
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","FWFDQR");//服务到期日  可以为空
		      colNode.setText("");	      
		      colNode= node.addElement("col"); 
		      colNode.addAttribute("name","FWCPDM");//产品分类ID  不能为空
		      colNode.setText("729"); 
	      } 
		  return dom.asXML();
	}
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	private String getRegDate(Customer customer){
		return customer.getInstallCardDate();
	}
	
	/***
	 * 地址
	 * @param businessAddress
	 * @param address
	 * @return
	 */
	private String getAddress(String businessAddress,String address){
		if(!StringFunction.isEmpty(businessAddress)){
			return businessAddress;
		}else if(!StringFunction.isEmpty(address)){
			return address;
		}else{
			return "地址不详";
		}
	}
	
	/***
	 * 对应税务局
	 */
	private HashMap swjHm;
	private String getSWJID(String cityId){
	    	if(swjHm==null){
	    		swjHm=new HashMap();
	    		swjHm.put("1", "5245");//直属    		 
	    		swjHm.put("2", "5236");//南区    
	    		swjHm.put("3", "5241");//三乡    
	    		swjHm.put("4", "5244");//坦洲    
	    		swjHm.put("5", "5242");//板芙    
	    		swjHm.put("6", "5243");//神湾    
	    		swjHm.put("7", "5238");//开发区    
	    		swjHm.put("8", "5237");//南朗    
	    		swjHm.put("9", "5239");//民众    
	    		swjHm.put("10", "5240");//三角    
	    		swjHm.put("11", "5231");//港口    
	    		swjHm.put("12", "5228");//南头    
	    		swjHm.put("13", "5229");//黄圃    
	    		swjHm.put("14", "5230");//阜沙    
	    		swjHm.put("15", "5234");//大涌    
	    		swjHm.put("16", "5227");//东凤    
	    		swjHm.put("17", "5232");//东升    
	    		swjHm.put("18", "5226");//古镇    
	    		swjHm.put("19", "5235");//横栏    
	    		swjHm.put("20", "5233");//沙溪    
	    		swjHm.put("21", "5225");//小榄      
	    		swjHm.put("22", "2");//	北京	    
	    		swjHm.put("23", "4708");//	深圳	    
	    		swjHm.put("24", "5189");//	东莞	    
	    		swjHm.put("25", "4864");//	湛江	    
	    		swjHm.put("26", "5501");//	海南	    
	    		swjHm.put("27", "2645");//	厦门	    
	    		swjHm.put("28", "4580");//	广州	    
	    		swjHm.put("29", "1800");//	上海	    
	    		swjHm.put("30", "4821");//	江门	    
	    		swjHm.put("31", "4731");//	珠海
	    	}
	    	return (String)swjHm.get(cityId);
	    }
	    /***
	     * 客户类型 taxType
	     * 1一般纳税人
		 * 2小规模 
	     * 3机关单位
	     * 4个人
	     * 
	     * KHLXID"); //客户类型ID  不能为空
		 * 5 其他;1个人;2税务机关;3企业;4 经销商;6 集团客户
	     */
	private int getKHLXID(int taxType){
	    	if(taxType==1){
	    		return 3;
	    	}else if(taxType==3){
	    		return 2;
	    	}else if(taxType==4){
	    		return 1;
	    	}else{
	    		return 5;
	    	}
	    }
	
	/***
	 * 1	国有企业
       2	集体企业
       8	其他企业
       17	个体工商户

	 * @param taxType
	 * @return
	 */
	private int getQYLXID(int taxType){
		 if(taxType==4){
			 return 17;
		 }else if(taxType==3){
			 return 1;
		 }else{
			 return 8;
		 }
	}
	
 
	
}
