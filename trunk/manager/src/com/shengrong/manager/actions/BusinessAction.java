package com.shengrong.manager.actions;

import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Transaction;

import com.shengrong.hibernate.Business;
import com.shengrong.hibernate.BusinessDAO;
import com.shengrong.system.System;

public class BusinessAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5503964164641959432L;

	private Business business;
	
	private List<Business> businessList;

	public List<Business> getBusinessList(){
		return this.businessList;
	}
	
	public void setBusinessList(List<Business> businessList){
		this.businessList = businessList;
	}
	
	public Business getBusiness(){
		return this.business;
	}
	
	public void setBusiness(Business business){
		this.business = business;
	}
	
	public String execute(){
		BusinessDAO businessDao = new BusinessDAO();
		businessList = businessDao.findAll();
		return SUCCESS;
	}
	
	/**
	 * ���湫˾ҵ��
	 * @return
	 */
	public String save(){
		if(business.getBusinessname() == null || business.getBusinessname().isEmpty()
				||business.getDes() == null || business.getDes().isEmpty() 
				||business.getIcon() == null || business.getIcon().isEmpty()){
			this.setMessage("��Ϣ��д��������");
			this.setHref("homepage/businesses.action");
			return ERROR;
		}
		business.setManager(getManager());
		business.setDatetime(System.sysClock());
		
		//����save����
		BusinessDAO businessDao = new BusinessDAO();
		Transaction tx = businessDao.getSession().getTransaction();
		tx.begin();
		businessDao.save(business);
		tx.commit();
		businessDao.getSession().close();
		return SUCCESS;
	}
	
	/**
	 * ɾ����˾ҵ��
	 * @return
	 */
	public String delete(){
		JSONObject root = new JSONObject();
		if(request.getParameter("id") == null){
			root.put("code", "400");
			root.put("msg", "������ʶidΪ�գ�");
		}
		int businessid = Integer.parseInt(request.getParameter("id"));
		
		BusinessDAO businessDao = new BusinessDAO();
		Business bsn = businessDao.findById(businessid);	
		
		if(bsn == null){
			root.put("code", "500");
			root.put("msg", "û�в���Ϊ" + businessid + "�ļ�¼!");
		}else{
			Transaction tx = businessDao.getSession().getTransaction();
			tx.begin();
			businessDao.delete(bsn);
			tx.commit();
			businessDao.getSession().close();
			root.put("code", "200");
			root.put("msg", "ɾ���ɹ���");
		}
		this.setResult(root.toString());
		return SUCCESS;
	}
}
