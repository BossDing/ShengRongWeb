package com.shengrong.manager.actions;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.shengrong.hibernate.Introduction;
import com.shengrong.hibernate.IntroductionDAO;
import com.shengrong.hibernate.customization.CustomizedDAO;
import com.shengrong.hibernate.customization.DataPackage;
import com.shengrong.system.System;

import org.hibernate.Transaction;

public class IntroductionAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6483264643066619033L;
	
	private int itemPerPage = 5;
	
	private int currentPage = 1;
	
	List<Introduction> introductions;
	
	private String content;
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public List<Introduction> getIntroductions(){
		return this.introductions;
	}
	
	public void setIntroductions(List<Introduction> introductions){
		this.introductions = introductions;
	}
	
	public int getItemPerPage(){
		return this.itemPerPage;
	}
	
	public void setItemPerPage(int itemPerPage){
		this.itemPerPage = itemPerPage;
	}
	
	public int getCurrentPage(){
		return this.currentPage;
	}
	
	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}
	
	/**
	 * ��ѯ���е�Introduction
	 * @param ����SUCCESSʱ��ת����˾���ܹ���ҳ�棬������ת��������ʾҳ��
	 */
	@Override
	public String execute(){
		//CustomizedDAO<Introduction> introductionDao = new CustomizedDAO<Introduction>();
		//introductions = introductionDao.pagingQuery(currentPage, itemPerPage, "Introduction");
		return SUCCESS;
	}
	
	public String pagingQuery(){
		IntroductionDAO introductionDao = new IntroductionDAO();
		introductions = introductionDao.findAll();
		
		JSONObject root = new JSONObject();
		root.put("draw", 1);
		root.put("recordsTotal", introductions.size());
		root.put("recordsFiltered", introductions.size()); //��ζ�Ų����й���
		JSONArray array = new JSONArray();
		for(int i=0; i<introductions.size(); i++){
			JSONObject json = new JSONObject();
			json.put("id", introductions.get(i).getIntroductionid());
			json.put("content", introductions.get(i).getContent());
			json.put("time", introductions.get(i).getDatetime().toString());
			json.put("name", introductions.get(i).getManager() == null?"":introductions.get(i).getManager().getName());
			array.add(json);
		}
		root.put("data", array);
		this.setResult(root.toString());
		
		/*��ҳ��ѯ���룬Ŀǰ�ǷǷ�ҳ��ѯ״̬
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		itemPerPage = Integer.parseInt(request.getParameter("itemPerPage"));
		
		CustomizedDAO<Introduction> introductionDao = new CustomizedDAO<Introduction>();
		DataPackage<Introduction> dataPkg = introductionDao.pagingQuery(currentPage, itemPerPage, "Introduction");
		introductions = dataPkg.getDatum();
		
		JSONObject root = new JSONObject();
		root.put("draw", 1);
		root.put("recordsTotal", dataPkg.getTotalRecords());
		root.put("recordsFiltered", dataPkg.getTotalRecords()); //��ζ�Ų����й���
		JSONArray array = new JSONArray();
		for(int i=0; i<introductions.size(); i++){
			JSONObject json = new JSONObject();
			json.put("id", introductions.get(i).getIntroductionid());
			json.put("content", introductions.get(i).getContent());
			json.put("time", introductions.get(i).getDatetime().toString());
			json.put("name", introductions.get(i).getManager() == null?"":introductions.get(i).getManager().getName());
			array.add(json);
		}
		root.put("data", array);
		this.setResult(root.toString());*/
		return SUCCESS;
	}
	
	/**
	 * �������Ľ�����б���
	 * @return String �ɹ��򷵻�ˢ��ҳ��,���򷵻ش���ҳ��
	 */
	public String save(){
		if(this.content == null || this.content.isEmpty()){
			this.setMessage("�ύ�Ĺ�˾������ݲ���Ϊ�գ�");
			this.setHref("<%=basePath%>homepage/saveIntroduction.action");
			return ERROR;
		}
		
		Introduction intro = new Introduction(this.content, System.sysClock());
		intro.setManager(getManager());
		
		IntroductionDAO introDao = new IntroductionDAO();
		Transaction tx = introDao.getSession().getTransaction();
		tx.begin();
		introDao.save(intro);
		tx.commit();
		introDao.getSession().close();
		return SUCCESS;
	}
	
	public String delete(){
		JSONObject root = new JSONObject();
		if(request.getParameter("id") == null){
			root.put("code", "400");
			root.put("msg", "������ʶidΪ�գ�");
			return SUCCESS;
		}
		int introid = Integer.parseInt(request.getParameter("id"));
		
		IntroductionDAO introDao = new IntroductionDAO();
		Introduction intro = introDao.findById(introid);
		if(intro == null){
			root.put("code", "500");
			root.put("msg", "û�в���Ϊ" + introid + "�ļ�¼!");
		}else{
			Transaction tx = introDao.getSession().getTransaction();
			tx.begin();
			introDao.delete(intro);
			tx.commit();
			introDao.getSession().close();
			root.put("code", "200");
			root.put("msg", "ɾ���ɹ���");
		}
		this.setResult(root.toString());
		return SUCCESS;
	}
}
