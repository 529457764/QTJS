package cn.qtone.modules.customer.obj.ht;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.modules.htNumber.obj.HtNumber;
import cn.qtone.modules.htNumber.obj.HtNumberHandle;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class HtCardInstallFormHandle extends BaseHandle {

	public HtCardInstallFormHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HtNumber getHtNumber(int customerId,String cardNO,int isBSP){
		String sql="SELECT htNumber,htNumberPrefix FROM ht_card_install_form WHERE customerId="+customerId;
		sql+=" AND cardNO='"+db.filterStr(cardNO)+"'";
		sql+=" AND isBSP="+isBSP;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				HtNumber htNumber=new HtNumber();
				htNumber.setPrefix(rs.getString("htNumberPrefix"));
				htNumber.setHtNumber(rs.getInt("htNumber"));
				return htNumber;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
		}
		return null;
	}
	
	/**
	 * 保存
	 * @param htCardInstallForm
	 * @return
	 */
	public boolean save(HtCardInstallForm htCardInstallForm){
		  if(this.isExist(htCardInstallForm.getCustomerId(),htCardInstallForm.getCardNO(),htCardInstallForm.getIsBSP(),htCardInstallForm.getHtNumber())){
			  return this.edit(htCardInstallForm);
		  }else{
			  return this.insert(htCardInstallForm);
		  }
	}
	
	/**
	 * 增加
	 * @param htCardInstallForm
	 * @return
	 */
	public boolean insert(HtCardInstallForm htCardInstallForm){
		String sql="INSERT INTO ht_card_install_form SET "
            +" customerId  =" +htCardInstallForm.getCustomerId()
            +", companyName ='"+db.filterStr(htCardInstallForm.getCompanyName())+"'"
            +", taxNO ='"+db.filterStr(htCardInstallForm.getTaxNO())+"'"
            +", address='"+db.filterStr(htCardInstallForm.getAddress())+"'"
            +", cityId=" +htCardInstallForm.getCityId()
            +", linkman='"+db.filterStr(htCardInstallForm.getLinkman())+"'"
            +", tel='"+db.filterStr(htCardInstallForm.getTel())+"'"
            +", mobile='"+db.filterStr(htCardInstallForm.getMobile())+"'"
            +", pc='"+db.filterStr(htCardInstallForm.getPc())+"'"
            +", printer='"+db.filterStr(htCardInstallForm.getPrinter())+"'"
           // +", version='"+db.filterStr(htCardInstallForm.getVersion())+"'"
            +", cardNO='"+db.filterStr(htCardInstallForm.getCardNO())+"'"
            +", ICNO='"+db.filterStr(htCardInstallForm.getICNO())+"'"
            +", pcNO=" +htCardInstallForm.getPcNO()
            +", installType="+htCardInstallForm.getInstallType()
            +",installState=" +htCardInstallForm.getInstallState()
            +", failedInfo='"+db.filterStr(htCardInstallForm.getFailedInfo())+"'"
            +", remark='"+db.filterStr(htCardInstallForm.getRemark())+"'"
            +", installer=" +htCardInstallForm.getInstaller()
            +", softVersion=" +htCardInstallForm.getSoftVersion()
            +", zysbckd='"+db.filterStr(htCardInstallForm.getZysbckd())+"'"
            +",isBSP="+htCardInstallForm.getIsBSP()
            +", signer='"+db.filterStr(htCardInstallForm.getSigner())+"'" ;
		
		    if(!StringFunction.isEmpty(htCardInstallForm.getInstallDate())){
		    	sql += ", installDate='" +db.filterStr(htCardInstallForm.getInstallDate())+"'";
		    } 
//		    if(!StringFunction.isEmpty(htCardInstallForm.getHtNumber())){
		    	sql +=", htNumber="+htCardInstallForm.getHtNumber();
//		    }
			if(!StringFunction.isEmpty(htCardInstallForm.getHtNumberPrefix())){
			  	sql +=", htNumberPrefix='"+db.filterStr(htCardInstallForm.getHtNumberPrefix())+"'";
			}		    	
		
		 return db.execute(sql);
	}

	/**
	 * 修改
	 * @param htCardInstallForm
	 * @return
	 */
	public boolean edit(HtCardInstallForm htCardInstallForm){
		String sql=" UPDATE ht_card_install_form SET "
            +" companyName ='"+db.filterStr(htCardInstallForm.getCompanyName())+"'"
            +", taxNO     ='"+db.filterStr(htCardInstallForm.getTaxNO())+"'"
            +", address  ='"+db.filterStr(htCardInstallForm.getAddress())+"'"
            +", cityId =" +htCardInstallForm.getCityId()
            +", linkman ='"+db.filterStr(htCardInstallForm.getLinkman())+"'"
            +", tel ='"+db.filterStr(htCardInstallForm.getTel())+"'"
            +", mobile ='"+db.filterStr(htCardInstallForm.getMobile())+"'"
            +", pc ='"+db.filterStr(htCardInstallForm.getPc())+"'"
            +", printer ='"+db.filterStr(htCardInstallForm.getPrinter())+"'"
            //+", version ='"+db.filterStr(htCardInstallForm.getVersion())+"'"
            +", cardNO ='"+db.filterStr(htCardInstallForm.getCardNO())+"'"
            +", ICNO ='"+db.filterStr(htCardInstallForm.getICNO())+"'"
            +", pcNO=" +htCardInstallForm.getPcNO()
            +", installType="+htCardInstallForm.getInstallType()
            +", installState=" +htCardInstallForm.getInstallState()
            +", softVersion=" +htCardInstallForm.getSoftVersion()
            +", zysbckd='"+db.filterStr(htCardInstallForm.getZysbckd())+"'"
            +", failedInfo ='"+db.filterStr(htCardInstallForm.getFailedInfo())+"'"
            +", remark ='"+db.filterStr(htCardInstallForm.getRemark())+"'";		
			//if( htCardInstallForm.getInstaller()>0){
		    	sql += ", installer=" +htCardInstallForm.getInstaller();
		   // }
           // if(!StringFunction.isEmpty(htCardInstallForm.getSigner())){
		    	sql +=", signer ='"+db.filterStr(htCardInstallForm.getSigner())+"'" ;
		    //}
           // if(!StringFunction.isEmpty(htCardInstallForm.getInstallDate())){
		    	sql += ", installDate='" +db.filterStr(htCardInstallForm.getInstallDate())+"'";
		   // }            
//		    if(!StringFunction.isEmpty(htCardInstallForm.getHtNumber())){
		    	sql +=", htNumber="+htCardInstallForm.getHtNumber();
//		    }
		//	if(!StringFunction.isEmpty(htCardInstallForm.getHtNumberPrefix())){
		    	sql +=", htNumberPrefix='"+db.filterStr(htCardInstallForm.getHtNumberPrefix())+"'";
		 //   }		    	
            sql +=" WHERE customerId  =" +htCardInstallForm.getCustomerId();
            sql +=" AND cardNO='"+db.filterStr(htCardInstallForm.getCardNO())+"'";
		 return db.execute(sql);
	}
	
	/**
	 * 是否存在
	 * @param customerId
	 * @return
	 */
	public boolean isExist(int customerId,String cardNO,int isBSP,int htNumber){
		  String sql=" SELECT COUNT(*) FROM ht_card_install_form  WHERE customerId  =" +customerId;
		  sql+=" AND (htNumber="+htNumber;
		  sql+=" OR cardNO='"+db.filterStr(cardNO)+"')";
		  sql+=" AND isBSP="+isBSP;
		  return db.countRowMain(sql)>0;
	}
	
	/**
	 * 搜索
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 */
	public List search(SearchHtCardInstallFormParam param,int start,int limit){
		List list=new ArrayList();
		String sql="SELECT h.*,c.cardType FROM  ht_card_install_form h join customer c on h.customerId=c.customerId WHERE 1=1 ";
		sql +=this.getWHereCase(param);
		if(limit>0){
			sql +=" LIMIT "+start+","+limit;
		}
		ResultSet rs=null;
		HtCardInstallForm form=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				form=this.getFormByRs(rs);
				list.add(form);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		return list;
	}
	/**
	 * 得到条数
	 * @param param
	 * @return
	 */
	public int getTotalRows(SearchHtCardInstallFormParam param){
		String sql="SELECT COUNT(*) FROM  ht_card_install_form h join customer c on h.customerId=c.customerId WHERE 1=1 ";
		sql+=this.getWHereCase(param);
		return db.countRow(sql);
	}
	
	private HtCardInstallForm getFormByRs(ResultSet rs) throws Exception{
		HtCardInstallForm form=new HtCardInstallForm();
		form.setAddress(rs.getString("h.address"));
		form.setCardNO(rs.getString("h.cardNO"));
		form.setCityId(rs.getInt("h.cityId"));
		form.setCompanyName(rs.getString("h.companyName"));
		form.setCustomerId(rs.getInt("h.customerId"));
		form.setFailedInfo(rs.getString("h.failedInfo"));
		form.setHtCardInstallFormId(rs.getInt("h.htCardInstallFormId"));
		form.setHtNumber(rs.getInt("h.htNumber"));
		form.setHtNumberPrefix(rs.getString("h.htNumberPrefix"));
		form.setICNO(rs.getString("h.ICNO"));
		form.setInstallDate(rs.getString("h.installDate"));
		form.setInstaller(rs.getInt("h.installer"));
		form.setInstallState(rs.getInt("h.installState"));
		form.setInstallType(rs.getInt("h.installType"));
		form.setLinkman(rs.getString("h.linkman"));
		form.setMobile(rs.getString("h.mobile"));
		form.setPc(rs.getString("h.pc"));
		form.setPcNO(rs.getInt("h.pcNO"));
		form.setPrinter(rs.getString("h.printer"));
		form.setRemark(rs.getString("h.remark"));		
		form.setSigner(rs.getString("h.signer"));
		form.setTaxNO(rs.getString("h.taxNO"));
		form.setTel(rs.getString("h.tel"));
		form.setSoftVersion(rs.getInt("h.softVersion"));
		form.setZysbckd(rs.getString("h.zysbckd"));		
		return form;
	}
	/**
	 * 搜索条件
	 * @param param
	 * @return
	 */
	private String getWHereCase(SearchHtCardInstallFormParam param){
		  if(param==null) return "";
		  StringBuffer bf=new StringBuffer();
		  if(!StringFunction.isEmpty(param.getInstallDateStart())){
			  bf.append(" AND h.installDate>='"+db.filterStr(param.getInstallDateStart())+"'");
		  }
		  if(!StringFunction.isEmpty(param.getInstallDateEnd())){
			  bf.append(" AND h.installDate< DATE_ADD('"+db.filterStr(param.getInstallDateEnd())+"',INTERVAL 1 day )");
		  }
		  if(!StringFunction.isEmpty(param.getHtNumber())){
			  bf.append(" AND h.htNumber ='"+db.filterStr(param.getHtNumber())+"'");
		  }
		  if(param.getIsBSP()>0){
			  bf.append(" AND h.isBSP=").append(param.getIsBSP());
		  }
		  if(param.getCardType()>0){
			  bf.append(" AND c.cardType=").append(param.getCardType());
		  }
		  if(param.getTaxType()>0){
			  bf.append(" AND c.taxType=").append(param.getTaxType());
		  }
		  return bf.toString();
	}
	
	/**
	 * 根据id查询
	 * @param customerId
	 * @return
	 */
	public HtCardInstallForm getFormById(int htCardInstallFormId){
		 String sql=" SELECT h.*,c.cardType  FROM  ht_card_install_form h join customer c on h.customerId=c.customerId  WHERE h.htCardInstallFormId="+htCardInstallFormId;
		 ResultSet rs=null;
		 HtCardInstallForm form=null;
		 try {
			rs=db.select(sql);
			if(rs.next()){
				form=this.getFormByRs(rs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.closeResultSet(rs);
		}
		return form;
	}
	/**
	 * 删除
	 * @param customerId
	 * @return
	 */
	public boolean del(int customerId){
		 String sql=" DELETE FROM  ht_card_install_form WHERE customerId="+customerId;
		 return db.execute(sql);
	}
	/**
	 * 删除
	 * @param htNumber
	 * @return
	 */
	public boolean delByHtNumber(String htNumberPrefix,String htNumber){
		//
		 HtNumberHandle htNumberHandle=new HtNumberHandle();
		 if(htNumberHandle.delByHtNumber(htNumberPrefix, htNumber)){
			 String sql=" DELETE FROM  ht_card_install_form where htNumber='"+db.filterStr(htNumber)+"'";
			 if(!StringFunction.isEmpty(htNumberPrefix)){
				 sql+=" AND htNumberPrefix='"+db.filterStr(htNumberPrefix)+"'";
			 }
			 return db.execute(sql);
		 }else
			 return false;
		
	}
	/**
	 * 更新安装人等信息
	 * @param installer
	 * @param signer
	 * @param customerId
	 * @return
	 */
	public boolean updateInstaller(int installer,String signer,int htCardInstallFormId){
		 String sql="UPDATE ht_card_install_form SET installer="+installer+",signer='"+db.filterStr(signer)+"' WHERE htCardInstallFormId="+htCardInstallFormId;
		 return db.execute(sql);
	}
	
	/***
	 * htNumber是否已占用
	 * @param htNumber
	 * @param customerId
	 * @return
	 */
	public boolean htNumberIsExist(int htNumber ,int customerId){
		String sql=" SELECT  COUNT(*)  FROM  ht_card_install_form WHERE  htNumber="+htNumber;
		if(customerId>0){
               sql+=" AND  customerId !="+customerId;
		}
		return db.countRow(sql)>0;
	}
}
