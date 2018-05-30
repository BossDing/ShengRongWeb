package com.shengrong.hibernate.customization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.shengrong.hibernate.BaseHibernateDAO;


/**
 * ������Ҫ���ṩ���ݿ��Զ����ѯ��������
 * @author zhangzheng
 *
 */
public class CustomizedDAO<T> extends BaseHibernateDAO {
	
	/**
	 * 
	 * @param currentPage ��ǰҳ��
	 * @param itemPerPage ÿҳ������
	 * @param table ��ѯ�������
	 * @return DataPackage<T> ���ز�ѯ�����
	 */
	@SuppressWarnings("unchecked")
	public DataPackage<T> pagingQuery(int currentPage, int itemPerPage, String table){
		Query query = this.getSession().createQuery("from " + table);
		
		int iFrom = (currentPage - 1)*itemPerPage;
		query.setFirstResult(iFrom);
		query.setMaxResults(itemPerPage);
		
		Long count = (Long)this.getSession().createQuery("select count(*) from " + table)
				.uniqueResult();
		
		DataPackage<T> dataPkg = new DataPackage<T>();
		dataPkg.setTotalRecords(count);
		dataPkg.setDatum((List<T>)query.list());
		return dataPkg;
	}
	
	public DataPackage<T> conditionPagingQuery(Map<String, Object> conditions, int currentPage, int itemPerPage, String table){
		String sql = "from " + table;
		Iterator iter = conditions.entrySet().iterator();
		
		String countSql = "select count(*) from " + table;
		
		boolean bFirst = true;
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next(); 
			String key = (String)entry.getKey();
			if(bFirst){
				sql += " where " + key + "= ?";
				countSql += " where " + key + "= ?";
				bFirst = false;
			}else{
				sql += "and " + key + "= ?";
				countSql += "and " + key + "= ?";
			}
		}
		Query query = this.getSession().createQuery(sql);
		Query countQuery = this.getSession().createQuery(countSql);
				
		int iIndex = 0;
		for (Object value : conditions.values()){
			query.setParameter(iIndex, value);
			countQuery.setParameter(iIndex, value);
			iIndex++;
		}
		
		int iFrom = (currentPage - 1)*itemPerPage;
		query.setFirstResult(iFrom);
		query.setMaxResults(itemPerPage);
		
		Long count = (Long)countQuery.uniqueResult();
		
		DataPackage<T> dataPkg = new DataPackage<T>();
		dataPkg.setTotalRecords(count);
		dataPkg.setDatum((List<T>)query.list());
		return dataPkg;
	}
}
