package cn.qtone.modules.customer.obj.tmp;

/**
 * 从临时表导入企业
 */
import cn.qtone.sys.base.BaseHandle;
import java.util.*;
import java.sql.*;

public class TmpImportCustomerHandle extends BaseHandle{

	public TmpImportCustomerHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean updateState(int tmpImportCustomerId,int importState){
		String sql="update tmp_import_customer set importState="+importState+" where tmpImportCustomerId="+tmpImportCustomerId;
		return db.execute(sql);
	}
	
	public List Search(){
		List resultList=new ArrayList();
		String sql="SELECT * FROM tmp_import_customer WHERE importState IN (0,3)";
		ResultSet rs=null;
		try {
			rs=db.selectMain(sql);
			while(rs.next()){
				TmpImportCustomer tmpImportCustomer=new TmpImportCustomer();
				tmpImportCustomer.setAddress(rs.getString("address"));
				tmpImportCustomer.setBossName(rs.getString("bossName"));
				tmpImportCustomer.setCompanyName(rs.getString("companyName"));
				tmpImportCustomer.setImportState(rs.getInt("importState"));
				tmpImportCustomer.setLinkman(rs.getString("linkman"));
				tmpImportCustomer.setTaxNO(rs.getString("taxNO"));
				tmpImportCustomer.setTel(rs.getString("tel"));
				tmpImportCustomer.setTmpImportCustomerId(rs.getInt("tmpImportCustomerId"));
				resultList.add(tmpImportCustomer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
		}
		return resultList;
	}
}
