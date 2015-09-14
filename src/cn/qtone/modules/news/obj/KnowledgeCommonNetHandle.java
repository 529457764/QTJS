package cn.qtone.modules.news.obj;
import java.util.*;
import java.sql.*;
import cn.qtone.modules.news.Const;
import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class KnowledgeCommonNetHandle extends BaseHandle{

	public KnowledgeCommonNetHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取信息的分类--金税百科
	 *  * @param HomeTypeId 分类id,1:资料 2：常用网址
	 * @return 返回信息分类对象List
	 */
	public List NetList(int NetTypeId,int startPos,int num){
		List netList=new ArrayList();
		
		String sql="SELECT * FROM knowledge_common_net WHERE 1=1" ;
		if (NetTypeId>0)
		{
			sql+=" and netTypeId="+NetTypeId;
		}
		sql+=" ORDER BY netTypeId,knowledgeCommonNetId";
		if(num!=-1) sql+=" LIMIT "+startPos+","+num;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				KnowledgeCommonNet knowledgeCommonNet=new KnowledgeCommonNet();
				knowledgeCommonNet.setName(rs.getString("name"));
				knowledgeCommonNet.setKnowledgeCommonNetId(rs.getInt("knowledgeCommonNetId"));
				knowledgeCommonNet.setNetTypeId(rs.getInt("netTypeId"));
				knowledgeCommonNet.setUrl(rs.getString("url"));	
				if(rs.getInt("netTypeId")==1) 
					knowledgeCommonNet.setTypeName("资料");
				else
					knowledgeCommonNet.setTypeName("常用网址");	
				
				netList.add(knowledgeCommonNet);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return netList;
	}	
	/**
	 * 根据百科首页id得到内容
	 * @param homeId 首页id
	 * @return
	 */
	public KnowledgeCommonNet getNetById(int homeId){
		if(homeId<=0) return null;
		String sql="SELECT * FROM knowledge_common_net WHERE knowledgeCommonNetId="+homeId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				KnowledgeCommonNet knowledgeCommonNet=new KnowledgeCommonNet();
				knowledgeCommonNet.setName(rs.getString("name"));
				knowledgeCommonNet.setKnowledgeCommonNetId(rs.getInt("knowledgeCommonNetId"));
				knowledgeCommonNet.setNetTypeId(rs.getInt("netTypeId"));
				knowledgeCommonNet.setUrl(rs.getString("url"));	
				if(rs.getInt("netTypeId")==1) 
					knowledgeCommonNet.setTypeName("资料");
				else
					knowledgeCommonNet.setTypeName("常用网址");	
				return knowledgeCommonNet;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		return null;
	}

	/**
	 * 删除资讯
	 * @param newsIds 资讯的id用逗号隔开
	 * @return true 删除成功 ；false 删除失败
	 */
	public boolean delNetById(String netIds){
		if(StringFunction.isEmpty(netIds)) return false;
		
		String sql="DELETE FROM knowledge_common_net WHERE knowledgeCommonNetId in ("+db.filterStr(netIds)+")";
		return db.execute(sql);
	}
	
	/**
	 * 将KnowledgeHome对象保存到数据库
	 * @param KnowledgeHome
	 * @return true 成功 ；false 失败
	 */
	public boolean addNet(KnowledgeCommonNet net){
		String sql="INSERT INTO knowledge_common_net SET name='"+db.filterStr(net.getName())+"'";
		sql+=",url='"+db.filterStr(net.getUrl())+"'";
		sql+=",netTypeId="+net.getNetTypeId();
		return db.execute(sql);
	}
	
	/**
	 * 修改KnowledgeHome
	 * @param KnowledgeHome KnowledgeHome对象
	 * @return
	 */
	public boolean editNet(KnowledgeCommonNet net){
		String sql="UPDATE knowledge_common_net SET name='"+db.filterStr(net.getName())+"'";
		sql+=",url='"+db.filterStr(net.getUrl())+"'";
		sql+=",netTypeId="+net.getNetTypeId();
		sql+=" WHERE knowledgeCommonNetId="+net.getKnowledgeCommonNetId();
		return db.execute(sql);
	}
	
}
