package cn.qtone.modules.trouble.obj;

import java.util.*;
import java.sql.*;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class TroubleHandle extends BaseHandle{

	public TroubleHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取搜索报障的记录总数
	 * @param param
	 * @return
	 */
	public int totalRow(SearchTroubleParam param){
		String sql="SELECT COUNT(*) FROM trouble AS t LEFT JOIN customer AS c ON t.customerId=c.customerId ";
		sql+=" WHERE 1=1";		

		sql+=this.sqlSearchTrouble(param);
		return this.db.countRow(sql);
	}
	
	
	
	
	
	/****
	 * 更新报障
	 * **/
	public boolean edit(Trouble trouble){
		if(trouble==null||trouble.getTroubleId()==0) return false;
		String sql=" UPDATE trouble SET modifyDate=CURRENT_TIMESTAMP,tState="+trouble.gettState()+
		          ",result='"+db.filterStr(trouble.getResult())+"',userBoId="+trouble.getUserBoId()+" WHERE troubleId="+trouble.getTroubleId();
		return db.execute(sql);
		
	}
	
	/***
	 * 保存保障
	 * @param trouble 故障对象
	 * @return  boolean 是否运行成功
	 * ***/
	public boolean addTrouble(Trouble trouble){
		if(trouble==null) return false;
		String sql = " INSERT INTO trouble(customerId,customerAccountId,cityId,linkman,tel,troubleTypeId,content,creatDate,address,source)"
				+ "  VALUES ("
				+ trouble.getCustomerId()+ ","
				+ trouble.getCustometAccountId()+ ","
				+ trouble.getCityId()+ ",'"+ db.filterStr(trouble.getLinkman())
				+ "','"+ db.filterStr(trouble.getTel())+ "',"
				+ trouble.getTroubleTypeId()+ ",'"+ db.filterStr(trouble.getContent())
				+ "',CURRENT_TIMESTAMP,'"+ db.filterStr(trouble.getAddress()) + "',"+trouble.getSource()+" )";
		return db.execute(sql);		
	}
	
	
	
	/**
	 * 搜索报障
	 * @param param 搜索参数
	 * @param startPos 返回记录的开始位置
	 * @param num 返回记录的条数
	 * @param customerId 公司编号 如果为0表示不限
	 * @return
	 */
	public List search(SearchTroubleParam param,int startPos,int num){
		ArrayList list=new ArrayList();
		String sql="SELECT t.*,c.`companyName` FROM trouble AS t LEFT JOIN customer AS c ON t.customerId=c.customerId ";
		sql+=" WHERE 1=1";		
		sql+=this.sqlSearchTrouble(param);
		sql+=" ORDER BY t.troubleId DESC";
		sql+=" LIMIT "+startPos+","+num;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				Trouble trouble=new Trouble();
			//	trouble.setCityId(rs.getInt("t.cityId"));
				trouble.setContent(rs.getString("t.content"));
				trouble.setCreatDate(rs.getString("t.creatDate"));
				trouble.setCustomerId(rs.getInt("t.customerId"));
				trouble.settState(rs.getInt("t.tState"));
				trouble.setLinkman(rs.getString("t.linkman"));
				trouble.setTel(rs.getString("t.tel"));
				trouble.setTroubleId(rs.getInt("t.troubleId"));
				trouble.setTroubleTypeId(rs.getInt("t.troubleTypeId"));
				trouble.setVisitDate(rs.getString("t.visitDate"));
				trouble.setAddress(rs.getString("t.address"));
				trouble.setModifyDate(rs.getString("t.modifyDate"));
				trouble.setCompanyName(rs.getString("c.companyName"));
			//	trouble.setResult(rs.getString("t.result"));
				
				trouble.setSource(rs.getInt("t.source"));
				list.add(trouble);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return list;
		
	}


	/**
	 * 根据参数生成查询条件
	 * @param param 查询参数
	 * @return
	 */
	private String sqlSearchTrouble(SearchTroubleParam param){
		StringBuffer sqlBuf=new StringBuffer();
		if(param.getCustomerId()>0){
			sqlBuf.append(" AND c.customerId="+param.getCustomerId());		
		}
		
		if(!StringFunction.isEmpty(param.getKeyword())){
			String keyword=db.filterStr(param.getKeyword());
			sqlBuf.append(" AND (t.content LIKE BINARY '%").append(db.filterStr(keyword)).append("%'");
			sqlBuf.append(" OR c.companyName LIKE BINARY '%").append(db.filterStr(keyword)).append("%')");
		}
		if(param.getCityId()>0){
			sqlBuf.append(" AND t.cityId="+param.getCityId());
		}
		if(param.getTroubleId()>0){
			sqlBuf.append(" AND t.troubleId="+param.getTroubleId());
		}
		if(param.getTState()>0){
			sqlBuf.append(" AND t.tState="+param.getTState());
		}
		if(param.getUserBoId()>0){
			sqlBuf.append(" AND t.userBoId="+param.getUserBoId());
		}
		if(!StringFunction.isEmpty(param.getTaxNo())){
			sqlBuf.append(" AND c.taxNo='"+param.getTaxNo()+"'");
		}
		if(param.getTroubleTypeId()>0){
			sqlBuf.append(" AND t.troubleTypeId="+param.getTroubleTypeId());
		}
		
		if(param.getSource()>0) {
			sqlBuf.append(" AND t.source="+param.getSource());
		}
		return sqlBuf.toString();
	}

	/**
	 * 根据编号删除故障
	 * */
	public boolean delTroubleById(int troubleId ){
		return db.execute(" DELETE FROM  trouble WHERE troubleId="+troubleId);
	}
	
	/**
	 * 根据编号取得故障信息
	 * */
	public Trouble getTroubleById(int troubleId) {

		String sql="SELECT t.*,c.`companyName` FROM trouble AS t LEFT JOIN customer AS c ON t.customerId=c.customerId WHERE t.troubleId= "+troubleId;
		ResultSet rs=null;
		Trouble trouble=new Trouble();
		try {
			rs=db.select(sql);
			if(rs.next()){				
				trouble.setCityId(rs.getInt("t.cityId"));
				trouble.setContent(rs.getString("t.content"));
				trouble.setCreatDate(rs.getString("t.creatDate"));
				trouble.setCustomerId(rs.getInt("t.customerId"));
				trouble.settState(rs.getInt("t.tState"));
				trouble.setLinkman(rs.getString("t.linkman"));
				trouble.setTel(rs.getString("t.tel"));
				trouble.setTroubleId(rs.getInt("t.troubleId"));
				trouble.setTroubleTypeId(rs.getInt("t.troubleTypeId"));
				trouble.setVisitDate(rs.getString("t.visitDate"));
				trouble.setAddress(rs.getString("t.address"));
				trouble.setCompanyName(rs.getString("c.companyName"));
				trouble.setResult(rs.getString("t.result"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return trouble;
	}
	
	/**
	 * 得到某客户的保障数
	 * @param customerId
	 * @return
	 */
	public int getTroubleTimes(int customerId){
		String sql="SELECT COUNT(*) FROM trouble WHERE customerId="+customerId;
		return db.countRow(sql);
	}
	
	
	
	/**
	 * 把数据添加到trouble表中
	 * @param troubleWx
	 */
/*
	public void addTroubleWx(Trouble troubleWx) {
		try {
			//获取customerId
			String sql = "insert into trouble(companyName, address, cityId, linkman, tel, troubleTypeId, content, customerId) values( "
					+ "companyName = '"+troubleWx.getCompanyName()+"', "
					+ "address = '"+troubleWx.getAddress()+"', "
					+ "cityId = '"+troubleWx.getCityId()+"', "
					+ "linkman = '"+troubleWx.getLinkman()+"', "
					+ "tel = '"+troubleWx.getTel()+"', "
					+ "troubleTypeId = '"+troubleWx.getTroubleTypeId()+"', "
					+ "content = '"+troubleWx.getContent()+"', "
					+ "customerId = '"+troubleWx.getCustomerId()+"' )";
			db.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
*/
/*
	public List getTroubleWxByCustomerId(int customerId){
		List troubleWxList = new ArrayList();
		ResultSet rs = null;
		try {
			String sql = "select * from trouble where customerId = '"+customerId+"'";
			rs = db.select(sql);
			while(rs.next()) {
				Trouble troubleWx = new Trouble();
				troubleWx.setCustomerId(rs.getInt("customerId"));
				troubleWx.setCompanyName(rs.getString("companyName"));
				troubleWx.setAddress(rs.getString("address"));
				troubleWx.setCityId(rs.getInt("cityId"));
				troubleWx.setCreatDate(rs.getString("creatDate"));
				troubleWx.setContent(rs.getString("content"));
				troubleWx.setLinkman(rs.getString("linkman"));
				troubleWx.setModifyDate(rs.getString("modifyDate"));
				troubleWx.setResult(rs.getString("result"));
				troubleWx.setTel(rs.getString("tel"));
				troubleWx.setTroubleId(rs.getInt("troubleId"));
				troubleWx.setTroubleTypeId(rs.getInt("troubleTypeId"));
				troubleWxList.add(troubleWx);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.closeResultSet(rs);
		}
		return troubleWxList;
	}
*/	

	public Trouble getTroubleWxByTroubleId(int troubleId) {
		Trouble troubleWx = null;
		ResultSet rs = null;
		try {
			String sql = "select * from trouble where troubleId = '"+troubleId+"'";
			rs=db.select(sql);
			if(rs.next()) {
				troubleWx = new Trouble();
				troubleWx.setCustomerId(rs.getInt("customerId"));
				//troubleWx.setCompanyName(rs.getString("companyName"));
				troubleWx.setAddress(rs.getString("address"));
				troubleWx.setCityId(rs.getInt("cityId"));
				troubleWx.setCreatDate(rs.getString("creatDate"));
				troubleWx.setContent(rs.getString("content"));
				troubleWx.setLinkman(rs.getString("linkman"));
				troubleWx.setModifyDate(rs.getString("modifyDate"));
				troubleWx.setResult(rs.getString("result"));
				troubleWx.setTel(rs.getString("tel"));
				troubleWx.setTroubleId(rs.getInt("troubleId"));
				troubleWx.setTroubleTypeId(rs.getInt("troubleTypeId"));
				troubleWx.settState(rs.getInt("tState"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return troubleWx;
	}

}
