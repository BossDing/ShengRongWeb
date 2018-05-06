package com.shengrong.hibernate.customization;

import java.util.ArrayList;
import java.util.List;

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
		int iEnd = currentPage*itemPerPage - 1;
		query.setFirstResult(iFrom);
		query.setMaxResults(iEnd);
		
		Long count = (Long)this.getSession().createQuery("select count(*) from " + table)
				.uniqueResult();
		
		DataPackage<T> dataPkg = new DataPackage<T>();
		dataPkg.setTotalRecords(count);
		dataPkg.setDatum((List<T>)query.list());
		return dataPkg;
	}
}
