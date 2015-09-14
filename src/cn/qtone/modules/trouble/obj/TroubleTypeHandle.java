package cn.qtone.modules.trouble.obj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import cn.qtone.sys.base.BaseHandle;

public class TroubleTypeHandle  extends BaseHandle{

	public TroubleTypeHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 将所有type放入map
	 * @return
	 */
	public Hashtable creatTypeMap(){
		Hashtable hm=new Hashtable();
		String sql="SELECT troubleTypeId,typeName FROM trouble_type ORDER BY troubleTypeId DESC";
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				hm.put(rs.getString("troubleTypeId"),rs.getString("typeName"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return hm;
	}

}
