package com.shengrong.manager.actions;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.hibernate.Transaction;

import com.shengrong.hibernate.News;
import com.shengrong.hibernate.NewsDAO;
import com.shengrong.hibernate.Newstype;
import com.shengrong.hibernate.NewstypeDAO;
import com.shengrong.hibernate.customization.CustomizedDAO;
import com.shengrong.hibernate.customization.DataPackage;
import com.shengrong.hibernate.customization.NewsEncapsulator;
import com.shengrong.hibernate.customization.PagingInfo;
import com.shengrong.system.System;

public class NewsAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698849958267326143L;

	private List<Newstype> newstypeList;
	
	private NewsEncapsulator encpNews;
	
	private PagingInfo pagingInfo = new PagingInfo(1, 10);
	
	private List<News> newsList;
	
	private Integer currentNewstype;
	
	private Long totalCount;
	
	private News editNews;
	
	public News getEditNews(){
		return this.editNews;
	}
	
	public void setEditNews(News editNews){
		this.editNews = editNews;
	}
	
	public Long getTotalCount(){
		return this.totalCount;
	}
	
	public void setTotalCount(Long totalCount){
		this.totalCount = totalCount;
	}
	
	public int getCurrentNewstype(){
		return this.currentNewstype;
	}
	
	public void setCurrentNewstype(int currentNewstype){
		this.currentNewstype = currentNewstype;
	}
	
	public List<News> getNewsList(){
		return this.newsList;
	}
	
	public void setNewsList(List<News> newsList){
		this.newsList = newsList;
	}
	
	public PagingInfo getPagingInfo(){
		return this.pagingInfo;
	}
	
	public void setPagingInfo(PagingInfo pagingInfo){
		this.pagingInfo = pagingInfo;
	}
	
	public NewsEncapsulator getEncpNews(){
		return this.encpNews;
	}
	
	public void setEncpNews(NewsEncapsulator encpNews){
		this.encpNews = encpNews;
	}
	
	public List<Newstype> getNewstypeList(){
		return this.newstypeList;
	}
	
	public void setNewstypeList(List<Newstype> newstypeList){
		this.newstypeList = newstypeList;
	}
	
	/**
	 * ��ת���ŷ���ҳ��
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute(){
		NewstypeDAO newstypeDao = new NewstypeDAO();
		newstypeList = newstypeDao.findAll();
		return SUCCESS;
	}
	
	public String save(){
		if(encpNews.getEncpImage() == null || encpNews.getEncpNewsDate() == null ){
			this.setMessage("��Ϣ��д��������");
			this.setHref("news/newses.action");
			return ERROR;
		}
		News news = encpNews.dataDecapsulate();
		news.setManager(getManager());
		news.setPublishdatetime(System.sysClock());
		
		NewsDAO newsDao = new NewsDAO();
		Transaction tx = newsDao.getSession().getTransaction();
		tx.begin();
		newsDao.save(news);
		tx.commit();
		newsDao.getSession().close();
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String query(){
		//��ѯ���е�����
		NewstypeDAO newstypeDao = new NewstypeDAO();
		newstypeList = newstypeDao.findAll();
		
		CustomizedDAO<News> customizedDao = new CustomizedDAO<News>();
		if(this.currentNewstype == null){
			//��һ����Ϊ��ǰĬ�ϵ�����
			this.currentNewstype = newstypeList.get(0).getTypeid();
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("newstype.typeid", this.currentNewstype);
		
		@SuppressWarnings("rawtypes")
		DataPackage dataPkg = customizedDao.conditionPagingQuery(condition, this.pagingInfo.getCurrentPage(), 
				this.pagingInfo.getItemPerPage(), "News");
		
		this.newsList = dataPkg.getDatum();
		this.totalCount = dataPkg.getTotalRecords();
		
		return SUCCESS;
	}
	
	/**
	 * �༭����ҳ����ת
	 * @return success����ת�����ű༭ҳ��
	 */
	public String updatePage(){
		if(request.getParameter("newsid") == null){
			this.setMessage("��¼Ϊnull");
			this.setHref("news/queryNewses.action");
			return ERROR;
		};
		
		//��ѯ���е�����
		NewstypeDAO newstypeDao = new NewstypeDAO();
		newstypeList = newstypeDao.findAll();
		
		int newsid = Integer.parseInt(request.getParameter("newsid"));
		NewsDAO newsDao = new NewsDAO();
		this.editNews = newsDao.findById(newsid);
		
		if(this.editNews == null){
			this.setMessage("û�б�־Ϊ" + newsid + "�ļ�¼��");
			this.setHref("news/queryNewses.action");
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String update(){
		if(request.getParameter("newsid") == null ||
				request.getParameter("newsid").equals("")){
			this.setMessage("����ʧ�ܣ�������ʶΪ�գ�");
			this.setHref("news/queryNewses.action");
			return ERROR;
		}
		
		int newsid = Integer.parseInt(request.getParameter("newsid"));
		NewsDAO newsDao = new NewsDAO();
		News news = newsDao.findById(newsid);

		if(news == null){
			this.setMessage("����ʧ�ܣ�û�б�ʶΪ" + newsid + "�ļ�¼��");
			this.setHref("news/queryNewses.action");
			return ERROR;
		}
		newsDao.getSession().close();
		
		News editData = encpNews.dataDecapsulate();
		editData.setNewsid(news.getNewsid());
		editData.setPublishdatetime(System.sysClock());
		editData.setManager(getManager());
		Transaction tx = newsDao.getSession().getTransaction();
		tx.begin();
		newsDao.getSession().saveOrUpdate(editData);
		tx.commit();
		newsDao.getSession().close();
		
		this.setMessage("�����ɹ���");
		this.setHref("news/queryNewses.action");
		return SUCCESS;
	}
	
	/**
	 * ���ݱ�ʶ��ɾ������
	 * @return
	 */
	public String delete(){
		JSONObject root = new JSONObject();
		if(request.getParameter("newsid") == null ||
				request.getParameter("newsid").equals("")){
			root.put("code", "400");
			root.put("msg", "������ʶidΪ�գ�");
			this.setResult(root.toString());
			return SUCCESS;
		};
		
		int newsid = Integer.parseInt(request.getParameter("newsid"));
		NewsDAO newsDao = new NewsDAO();
		News news = newsDao.findById(newsid);
		if(news == null){
			root.put("code", "400");
			root.put("msg", "û�б�ʶΪ" + newsid + "�ļ�¼��");
			this.setResult(root.toString());
			return SUCCESS;
		}
		
		Transaction tx = newsDao.getSession().getTransaction();
		tx.begin();
		newsDao.delete(news);
		tx.commit();
		newsDao.getSession().close();
		
		root.put("code", "200");
		root.put("msg", "�����ɹ���");
		this.setResult(root.toString());
		return SUCCESS;
	}
}
