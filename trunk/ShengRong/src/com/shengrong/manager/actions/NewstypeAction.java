package com.shengrong.manager.actions;

import java.util.List;

import org.hibernate.Transaction;

import net.sf.json.JSONObject;

import com.shengrong.hibernate.Newstype;
import com.shengrong.hibernate.NewstypeDAO;

public class NewstypeAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 48011128135225289L;

	List<Newstype> newstypeList;
	
	Newstype newstype;
	
	public Newstype getNewstype(){
		return this.newstype;
	}
	
	public void setNewstype(Newstype newstypeList){
		this.newstype = newstypeList;
	}
	
	public List<Newstype> getNewstypeList(){
		return this.newstypeList;
	}
	
	public void setNewstypeList(List<Newstype> newstypeList){
		this.newstypeList = newstypeList;
	}
	
	/**
	 * ��ѯ���е��������ͣ�����תҳ��
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute(){
		NewstypeDAO newstypeDao = new NewstypeDAO();
		newstypeList = newstypeDao.findAll();
		return SUCCESS;
	}
	
	/**
	 * ����µ���������
	 * @return
	 */
	public String save(){
		if(this.newstype == null){
			this.setMessage("��������Ϊnull��");
			this.setHref("news/newstypes.action");
			return ERROR;
		}
		NewstypeDAO newstypeDao = new NewstypeDAO();
		Transaction tx = newstypeDao.getSession().getTransaction();
		tx.begin();
		newstypeDao.save(newstype);
		tx.commit();
		newstypeDao.getSession().close();
		
		return SUCCESS;
	}
	
	public String delete(){
		JSONObject root = new JSONObject();
		if(request.getParameter("id") == null ||
				request.getParameter("id").equals("")){
			root.put("code", "400");
			root.put("msg", "������ʶidΪ�գ�");
			this.setResult(root.toString());
			return SUCCESS;
		};
		
		int id = Integer.parseInt(request.getParameter("id"));
		NewstypeDAO newstypeDao = new NewstypeDAO();
		newstype = newstypeDao.findById(id);
		
		if(newstype == null){
			root.put("code", "400");
			root.put("msg", "û�б�ʶ��Ϊ��" + id + "�ļ�¼��");
			this.setResult(root.toString());
			return SUCCESS;
		}
		
		Transaction tx = newstypeDao.getSession().getTransaction();
		tx.begin();
		newstypeDao.delete(newstype);
		tx.commit();
		newstypeDao.getSession().close();
		root.put("code", "200");
		root.put("msg", "ɾ���ɹ���");
		
		this.setResult(root.toString());
		return SUCCESS;
	}
}
