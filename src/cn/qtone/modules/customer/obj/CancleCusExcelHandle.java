package cn.qtone.modules.customer.obj;

import java.util.ArrayList;
import java.util.List;

public class CancleCusExcelHandle {

	public CancleCusExcelHandle(){}
	
	public List getCancleCusExcel(String excelPath){
		ArrayList list=new ArrayList();
		cn.qtone.utils.excel.ExcelUtil excelUtil=new cn.qtone.utils.excel.ExcelUtil(excelPath);
		cn.qtone.utils.excel.MyHSSFSheet sheet=excelUtil.getSheet(0);	
		int totalRow=sheet.getLastRowNum();
		for(int i=1;i<=totalRow;i++){
			cn.qtone.utils.excel.MyHSSFRow row=sheet.getRow(i);
			if(row==null) continue;
			CancleCusExcel cancleCusExcel=new CancleCusExcel();
			cancleCusExcel.setCancleStr(row.getStringCellValue(1).trim());
			if("非正常".equals(cancleCusExcel.getCancleStr())) cancleCusExcel.setCancleStr("非正常户");
			cancleCusExcel.setTaxNO(row.getStringCellValue(0).trim());
			cancleCusExcel.setRowNum(row.getRowNum());
			list.add(cancleCusExcel);
		}
		return list;
	}
}
