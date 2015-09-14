package cn.qtone.modules.customer.obj;

import java.util.ArrayList;

import cn.qtone.utils.StringFunction;
import cn.qtone.utils.excel.ExcelInfo;
import cn.qtone.utils.excel.ExcelUtil;
import cn.qtone.utils.excel.MyHSSFRow;
import cn.qtone.utils.excel.MyHSSFSheet;



public class ReadExcelHandle {

	public ReadExcelHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 从excel的第一个表中读取导入数据
	 * @param excelPath
	 * @param courseId 指定报读的订单数据
	 * @param createrId 执行导入操作的管理员id
	 * @return list
	 */
	public ExcelInfo getObjFromExcel(String excelPath){	
		cn.qtone.utils.excel.ExcelInfo excelInfo=new ExcelInfo();
		CustomerImportRow imxls=null;
		ArrayList list=new ArrayList();
		ExcelUtil excelUtil=new ExcelUtil(excelPath);
		MyHSSFSheet sheet=excelUtil.getSheet(0);	
		int totalRow=sheet.getLastRowNum();
		MyHSSFRow row=null;
		//检查导入列是否对应
		row=sheet.getRow(0);		
		this.checkRowName(row,excelInfo);
		if(excelInfo.getFlag()==1) return excelInfo;
		for(int i=1;i<=totalRow;i++){
			row=sheet.getRow(i);
			if(row==null) continue;
			imxls=getObjByRow(row);
			list.add(imxls);
		}
		excelInfo.setFlag(2);
		excelInfo.setDataList(list);
		return excelInfo;
	}
	
	private CustomerImportRow getObjByRow(MyHSSFRow row){
		CustomerImportRow imxls=new CustomerImportRow();			
		imxls.setTaxNO(this.trim(row.getStringCellValue(0)));
		imxls.setCompanyName(this.trim(row.getStringCellValue(1)));
		imxls.setTel(this.telFormat(this.trim(row.getStringCellValue(2))));
		imxls.setAddress(this.trim(row.getStringCellValue(3)));
		imxls.setLinkMan(this.trim(row.getStringCellValue(4)));
		imxls.setCityId(this.getCityId(this.trim(row.getStringCellValue(5))));
		imxls.setConfirmDate(this.trim(row.getDateCellValue(6)));
//		imxls.setIsYingZhuanZeng(this.getIsYingZhuanZeng(this.trim(row.getStringCellValue(7))));
		imxls.setIsXgm(this.getIsXgm(this.trim(row.getStringCellValue(7))));
		
		return imxls;
	}
	
	
//	private int getIsYingZhuanZeng(String strValue){
//		  if("是".equals(StringFunction.isNull(strValue))){
//			  return 2;
//		  }else{
//			  return 1;
//		  }
//	}

	private int getIsXgm(String strValue){
		  if("是".equals(StringFunction.isNull(strValue))){
			  return 2;
		  }else{
			  return 1;
		  }
	}
	
	/**
	 * 检查列名
	 * @param imxls
	 * @param excelInfo
	 */
	private void checkRowName(MyHSSFRow row,ExcelInfo excelInfo){
		if(row==null){
			excelInfo.setFlag(1);
			excelInfo.setMsg(" 列名出错，请核对第一行是否包含列名.");
			return ;
		}
		if(!this.trim(row.getStringCellValue(0)).equals("NSRSBH")){
			excelInfo.setMsg(" 税号对应列出错 ");
			excelInfo.setFlag(1);
		}
		if(!this.trim(row.getStringCellValue(1)).equals("NSRMC")){
			excelInfo.setMsg(excelInfo.getMsg()+"  公司名称对应列出错  ");
			excelInfo.setFlag(1);
		}
		if(!this.trim(row.getStringCellValue(2)).equals("DHHM")){
			excelInfo.setMsg(excelInfo.getMsg()+"  联系电话对应列出错  ");
			excelInfo.setFlag(1);
		}
		if(!this.trim(row.getStringCellValue(3)).equals("SCJYDZ")){
			excelInfo.setMsg(excelInfo.getMsg()+"  地址对应列出错  ");
			excelInfo.setFlag(1);
		}
		if(!this.trim(row.getStringCellValue(4)).equals("FDDBRMC")){
			excelInfo.setMsg(excelInfo.getMsg()+"  联系人对应列出错  ");
			excelInfo.setFlag(1);
		}
		if(!this.trim(row.getStringCellValue(5)).equals("镇区")){
			excelInfo.setMsg(excelInfo.getMsg()+"  镇区对应列出错  ");
			excelInfo.setFlag(1);
		}
		if(!this.trim(row.getStringCellValue(6)).equals("YXQ_Q")){
			excelInfo.setMsg(excelInfo.getMsg()+"  认定日期对应列出错  ");
			excelInfo.setFlag(1);
		}	
//		if(!this.trim(row.getStringCellValue(7)).equals("是否营转增")){
//			excelInfo.setMsg(excelInfo.getMsg()+"  是否营转增对应列出错  ");
//			excelInfo.setFlag(1);
//		}	
		if(!this.trim(row.getStringCellValue(7)).equals("是否小规模")){
			excelInfo.setMsg(excelInfo.getMsg()+"  是否小规模对应列出错  ");
			excelInfo.setFlag(1);
		}			
	}	
	/**
	 * 去空格
	 * @param value
	 * @return
	 */
	private String trim(String value){
		return StringFunction.strReplace(value," ","");
	}
	
	
	/**
	 * 根据excel里面的文字，获得企业所属镇区id
	 * @param area
	 * @return
	 */
	private int getCityId(String area){
		if(area.indexOf("直属")!=-1){
			return 1;
		}	
		if(area.indexOf("南区")!=-1){
			return 2;
		}	
		if(area.indexOf("三乡")!=-1){
			return 3;
		}	
		if(area.indexOf("坦洲")!=-1){
			return 4;
		}	
		if(area.indexOf("板芙")!=-1){
			return 5;
		}	
		if(area.indexOf("神湾")!=-1){
			return 6;
		}	
		if(area.indexOf("开发区")!=-1){
			return 7;
		}	
		if(area.indexOf("南朗")!=-1){
			return 8;
		}	
		if(area.indexOf("民众")!=-1){
			return 9;
		}	
		if(area.indexOf("三角")!=-1){
			return 10;
		}	
		if(area.indexOf("港口")!=-1){
			return 11;
		}	
		if(area.indexOf("南头")!=-1){
			return 12;
		}	
		if(area.indexOf("黄圃")!=-1){
			return 13;
		}	
		if(area.indexOf("阜沙")!=-1){
			return 14;
		}	
		if(area.indexOf("大涌")!=-1){
			return 15;
		}	
		if(area.indexOf("东凤")!=-1){
			return 16;
		}	
		if(area.indexOf("东升")!=-1){
			return 17;
		}	
		if(area.indexOf("古镇")!=-1){
			return 18;
		}	
		if(area.indexOf("横栏")!=-1){
			return 19;
		}	
		if(area.indexOf("沙溪")!=-1){
			return 20;
		}	
		if(area.indexOf("小榄")!=-1){
			return 21;
		}
		return 0;
	}
	
	
	/**
	 * 电话格式整理
	 * @param tel
	 * @return
	 */
	private String telFormat(String tel){
		if(StringFunction.isNull(tel).equals("")){
			return "";
		}
		tel=tel.trim();
		tel=StringFunction.strReplace(tel,"、",",");
		tel=StringFunction.strReplace(tel,";",",");
		tel=StringFunction.strReplace(tel,"；",",");
		tel=StringFunction.strReplace(tel,".",",");
		tel = StringFunction.strReplace(tel, "，", ",");
		tel=StringFunction.strReplace(tel,"/",",");
		tel=StringFunction.strReplace(tel,"\\",",");
		tel=StringFunction.strReplace(tel," ",",");
		tel = StringFunction.strReplace(tel, "'", "''");
		
		return tel;
	}
	
	
	
	
	
}
