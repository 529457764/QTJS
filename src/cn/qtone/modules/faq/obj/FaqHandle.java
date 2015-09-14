package cn.qtone.modules.faq.obj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.sys.base.BaseHandle;
import cn.qtone.utils.StringFunction;

public class FaqHandle  extends BaseHandle{

	public FaqHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据id得到faq对象
	 * @param faqId
	 * @return
	 */
	public Faq getFaqById(int faqId){
		String sql="SELECT * FROM faq AS f,customer AS c WHERE f.customerId=c.customerId AND faqId="+faqId;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			if(rs.next()){
				Faq faq=new Faq();
				faq.setAnswer(rs.getString("f.answer"));
				faq.setAnswerDate(rs.getString("f.answerDate"));
				faq.setAskDate(rs.getString("f.askDate"));
				faq.setCompanyName(rs.getString("c.companyName"));
				faq.setCustomerAccountId(rs.getInt("f.customerAccountId"));
				faq.setCustomerId(rs.getInt("c.customerId"));
				faq.setFaqCategoryId(rs.getInt("f.faqCategoryId"));
				faq.setFaqId(rs.getInt("f.faqId"));
				faq.setQuestion(rs.getString("f.question"));
				faq.setQuestionTitle(rs.getString("f.questionTitle"));
				faq.setIsChecked(rs.getInt("f.isChecked"));
				return faq;
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
	 * 回答咨询
	 * @param faq
	 * @return
	 */
	public boolean answer(Faq faq){
		String sql="UPDATE faq SET answer='"+db.filterStr(faq.getAnswer())+"'";
		if(StringFunction.isEmpty(faq.getAnswer())){
			//回答没有内容将是否在前台显示设为1，不显示
			sql+=" ,isShow=1";
		}else{
			//回答有内容将是否在前台显示设为2，显示
			sql+=" ,isShow=2";			
		}
		sql+=",userBoId="+faq.getUserBoId();
		sql+=",answerDate=CURRENT_TIMESTAMP";
		sql+=",isChecked="+faq.getIsChecked();
		sql+=" WHERE faqId="+faq.getFaqId();
		return db.execute(sql);
	}

	/**
	 * 提问
	 * @param faq
	 * @return
	 */
	public boolean ask(Faq faq){
		String sql="INSERT INTO faq SET question='"+db.filterStr(faq.getQuestion())+"'";
		sql+=",faqCategoryId="+faq.getFaqCategoryId();
		sql+=",questionTitle='"+db.filterStr(faq.getQuestionTitle())+"'";
		sql+=",customerId="+faq.getCustomerId();
		sql+=",isShow="+faq.getIsShow();
		sql+=",isChecked="+faq.getIsChecked();
		sql+=",customerAccountId="+faq.getCustomerAccountId();
		sql+=",source="+faq.getSource();
		if(!StringFunction.isEmpty(faq.getLinkman())){
			sql+=",linkman='"+db.filterStr(faq.getLinkman())+"'";
		}
		if(!StringFunction.isEmpty(faq.getTel())){
			sql+=",tel='"+db.filterStr(faq.getTel())+"'";
		}
		sql+=",askDate=CURRENT_TIMESTAMP";
		return db.execute(sql);
	}
	
	/**
	 * 审核通过或不通过
	 * @param checkValue 1审核不通过，2审核通过
	 * @param faqId
	 * @return
	 */
	public boolean check(int checkValue,int faqId){
		String sql="UPDATE faq SET isChecked="+checkValue+" WHERE faqId="+faqId;
		return db.execute(sql);
	}

	/**
	 * 根据参数生成查询条件
	 * @param param 查询参数
	 * @return
	 */
	private String sqlSearchFaq(SearchFaqParam param){
		if(param==null) return "";
		StringBuffer sqlBuf=new StringBuffer();
		if(!StringFunction.isEmpty(param.getKeyword())){
			String keyword=db.filterStr(param.getKeyword());
			sqlBuf.append(" AND (f.question LIKE BINARY '%").append(keyword).append("%'");
			sqlBuf.append(" OR f.questionTitle LIKE BINARY '%").append(keyword).append("%'");
			sqlBuf.append(" OR f.answer LIKE BINARY '%").append(keyword).append("%')");
		}
		if(param.getCustomerId()>0){
			sqlBuf.append(" AND f.customerId="+param.getCustomerId());
		}
		if(param.getFaqId()>0){
			sqlBuf.append(" AND f.faqId="+param.getFaqId());
		}
		if(param.getIsAnswer()==1){
			sqlBuf.append(" AND f.userBoId IS NOT NULL AND f.userBoId>0");
		}else if(param.getIsAnswer()==2){
			sqlBuf.append(" AND f.userBoId IS NULL");			
		}
		if(param.getFaqCategoryId()>0){
			sqlBuf.append(" AND f.faqCategoryId="+param.getFaqCategoryId());
		}
		if(param.getCityId()>0){
			sqlBuf.append(" AND c.cityId="+param.getCityId());
		}
		if(param.getUserBoId()>0){
			sqlBuf.append(" AND u.userBoId="+param.getUserBoId());
		}
		if(param.getIsShow()==1){//前台不显示的
			sqlBuf.append(" AND f.isShow!=2");
		}else if(param.getIsShow()==2){
			sqlBuf.append(" AND f.isShow=2");			
		}
		if(param.getIsChecked()==1){//未通过审核的
			sqlBuf.append(" AND f.isChecked!=2");
		}else if(param.getIsChecked()==2){
			sqlBuf.append(" AND f.isChecked=2");			
		}
		
		if(param.getSource()>0){
			sqlBuf.append(" AND f.source="+param.getSource());
		}
		
		return sqlBuf.toString();
	}
	
	/**
	 * 得到搜索的记录总数
	 * @param param
	 * @return
	 */
	public int totalRow(SearchFaqParam param){
		String sql="SELECT COUNT(*)";
		sql+=" FROM faq AS f LEFT JOIN customer AS c ON f.customerId=c.customerId";
		sql+=" LEFT JOIN user_bo AS u ON f.userBoId=u.userBoId";
		sql+=" WHERE 1=1";
		sql+=this.sqlSearchFaq(param);
		return db.countRow(sql);
	}
	
	
	
	
	
	/**
	 * 搜索faq
	 * @param param 搜索参数对象
	 * @param startPos 开始位置
	 * @param num 返回条数
	 * @return
	 */
	public List search(SearchFaqParam param,int startPos,int num){
		ArrayList list=new ArrayList();
		String sql="SELECT f.linkman,f.tel,f.questionTitle,f.faqCategoryId,f.faqId,f.customerId,f.isShow,f.isChecked,f.userBoId,f.question,f.answer,f.askDate,f.answerDate,f.source,c.companyName,u.number";
		sql+=" FROM faq AS f LEFT JOIN customer AS c ON f.customerId=c.customerId";
		sql+=" LEFT JOIN user_bo AS u ON f.userBoId=u.userBoId";
		sql+=" WHERE 1=1";
		sql+=this.sqlSearchFaq(param);
		sql+=" ORDER BY faqId desc";
		sql+=" LIMIT "+startPos+","+num;
		ResultSet rs=null;
		try {
			rs=db.select(sql);
			while(rs.next()){
				Faq faq=new Faq();
				faq.setAnswer(rs.getString("f.answer"));
				faq.setAnswerDate(rs.getString("f.answerDate"));
				faq.setAskDate(rs.getString("f.askDate"));
				faq.setCompanyName(rs.getString("c.companyName"));
				faq.setCustomerId(rs.getInt("f.customerId"));
				faq.setFaqId(rs.getInt("f.faqId"));
				faq.setIsShow(rs.getInt("f.isShow"));
				faq.setQuestion(rs.getString("f.question"));
				faq.setUserBoId(rs.getInt("f.userBoId"));
				faq.setNumber(rs.getString("u.number"));
				faq.setQuestionTitle(rs.getString("f.questionTitle"));
				faq.setFaqCategoryId(rs.getInt("f.faqCategoryId"));
				faq.setIsChecked(rs.getInt("f.isChecked"));
				faq.setLinkman(rs.getString("f.linkman"));
				faq.setTel(rs.getString("f.tel"));
				faq.setSource(rs.getInt("f.source"));
				
				list.add(faq);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} finally{
			db.closeResultSet(rs);
		}
		
		return list;
	}
	
	/**
	 * 删除咨询
	 * @param faqIds 咨询的id，多个用逗号隔开
	 * @return
	 */
	public boolean del(String faqIds){
		if(StringFunction.isEmpty(faqIds)){
			return false;
		}
		
		String sql="DELETE FROM faq WHERE faqId IN ("+faqIds+")";
		return db.execute(sql);
	}
	
	/**
	 * 得到提问的次数
	 * @return
	 */
	public int getAskTimes(int customerId){
		String sql="SELECT COUNT(*) FROM faq WHERE customerId ="+customerId;
		return db.countRow(sql);
	}
	
	public List getFaqList() {
		List faqList = new ArrayList();
		ResultSet rs = null;
		String sql="SELECT f.linkman,f.tel,f.questionTitle,f.faqCategoryId,f.faqId,f.customerId,f.isShow,f.isChecked,f.userBoId,f.question,f.answer,f.askDate,f.answerDate,c.companyName,u.number";
		sql+=" FROM faq AS f LEFT JOIN customer AS c ON f.customerId=c.customerId";
		sql+=" LEFT JOIN user_bo AS u ON f.userBoId=u.userBoId";
		sql+=" WHERE 1=1";
//		sql+=this.sqlSearchFaq(param);
		sql+=" ORDER BY faqId desc";
//		sql+=" LIMIT "+startPos+","+num;
		try {
			rs = db.select(sql);
			while(rs.next()) {
				Faq faq = new Faq();
				faq.setAnswer(rs.getString("f.answer"));
				faq.setAnswerDate(rs.getString("f.answerDate"));
				faq.setAskDate(rs.getString("f.askDate"));
				faq.setCompanyName(rs.getString("c.companyName"));
				faq.setCustomerId(rs.getInt("f.customerId"));
				faq.setFaqId(rs.getInt("f.faqId"));
				faq.setIsShow(rs.getInt("f.isShow"));
				faq.setQuestion(rs.getString("f.question"));
				faq.setUserBoId(rs.getInt("f.userBoId"));
				faq.setNumber(rs.getString("u.number"));
				faq.setQuestionTitle(rs.getString("f.questionTitle"));
				faq.setFaqCategoryId(rs.getInt("f.faqCategoryId"));
				faq.setIsChecked(rs.getInt("f.isChecked"));
				faq.setLinkman(rs.getString("f.linkman"));
				faq.setTel(rs.getString("f.tel"));
				faqList.add(faq);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeResultSet(rs);
		}
		return faqList;
	}
}
