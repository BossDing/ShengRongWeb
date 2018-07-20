package com.shengrong.manager.actions;

import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Transaction;

import com.shengrong.hibernate.Recruit;
import com.shengrong.hibernate.RecruitDAO;
import com.shengrong.system.System;

public class RecruitAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4626237212809350358L;
	
	private Recruit recruit;
	
	private List<Recruit> recruitList;
	
	public List<Recruit> getRecruitList(){
		return this.recruitList;
	}
	
	public void setRecruitList(List<Recruit> recruitList){
		this.recruitList = recruitList;
	}
	
	public Recruit getRecruit(){
		return this.recruit;
	}
	
	public void setRecruit(Recruit recruit){
		this.recruit = recruit;
	}

	public String execute(){
		return SUCCESS;
	}
	
	public String save(){
		if(recruit == null){
			this.setMessage("��Ƹ��Ϣ����Ϊnull");
			this.setHref("recruit/saveRecruitPage.action");
			return ERROR;
		}
		
		recruit.setManager(getManager());
		recruit.setPublishdate(System.sysClock());
		
		RecruitDAO recruitDao = new RecruitDAO();
		Transaction tx = recruitDao.getSession().getTransaction();
		tx.begin();
		recruitDao.save(recruit);
		tx.commit();
		recruitDao.getSession().close();
		
		return SUCCESS;
	}
	
	public String list(){
		RecruitDAO recruitDao = new RecruitDAO();
		recruitList = recruitDao.findAll();
		
		return SUCCESS;
	}
	
	public String check(){
		if(request.getParameter("id") == null){
			this.setHref("recruit/recruitList.action");
			this.setMessage("������ʶΪ�գ�");
			return ERROR;
		};
		
		int recruitid = Integer.parseInt(request.getParameter("id"));
		RecruitDAO recruitDao = new RecruitDAO();
		recruit = recruitDao.findById(recruitid);
		if(recruit == null){
			this.setHref("recruit/recruitList.action");
			this.setMessage("�Ҳ�����ʶΪ" + recruitid + "�ļ�¼��");
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String delete(){
		JSONObject root = new JSONObject();
		if(request.getParameter("id") == null){
			root.put("code", "400");
			root.put("msg", "������ʶidΪ�գ�");
			this.setResult(root.toString());
			return SUCCESS;
		};
		
		int recruitid = Integer.parseInt(request.getParameter("id"));
		RecruitDAO recruitDao = new RecruitDAO();
		recruit = recruitDao.findById(recruitid);
		if(recruit == null){
			root.put("code", "400");
			root.put("msg", "�Ҳ�����ʶΪ" + recruitid + "�ļ�¼��");
			this.setResult(root.toString());
			return SUCCESS;
		}
		
		Transaction tx = recruitDao.getSession().getTransaction();
		tx.begin();
		recruitDao.getSession().delete(recruit);
		tx.commit();
		recruitDao.getSession().close();
		
		root.put("code", "200");
		root.put("msg", "ɾ���ɹ���");
		this.setResult(root.toString());
		return SUCCESS;
	}
}
