package cn.qtone.modules.customer.obj;

import java.util.ArrayList;
import java.util.List;

public class BWSJCusExcelHandle {

	public BWSJCusExcelHandle(){}
	
	public List getCancleCusExcel(String excelPath){
		ArrayList list=new ArrayList();
		cn.qtone.utils.excel.ExcelUtil excelUtil=new cn.qtone.utils.excel.ExcelUtil(excelPath);
		cn.qtone.utils.excel.MyHSSFSheet sheet=excelUtil.getSheet(0);	
		int totalRow=sheet.getLastRowNum();
		for(int i=1;i<=totalRow;i++){
			cn.qtone.utils.excel.MyHSSFRow row=sheet.getRow(i);
			if(row==null) continue;
			BWSJCusExcel bWSJCusExcel=new BWSJCusExcel();
			bWSJCusExcel.setTaxNO(row.getStringCellValue(0).trim());
			bWSJCusExcel.setRowNum(row.getRowNum());
			list.add(bWSJCusExcel);
		}
		return list;
	}
}
