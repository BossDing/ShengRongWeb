package com.shengrong.manager.actions;

import java.security.MessageDigest;
import java.sql.Blob;

import org.hibernate.Transaction;

import sun.misc.BASE64Encoder;

import com.shengrong.hibernate.Manager;
import com.shengrong.hibernate.ManagerDAO;
import com.shengrong.hibernate.Role;
import com.shengrong.hibernate.RoleDAO;
import com.shengrong.system.System;

public class ManagerAction extends ActionBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5144045373593121337L;

	private Manager editManager;
	
	private int sex;
	
	private String imagedata;
	
	public String getImagedata(){
		return this.imagedata;
	}
	
	public void setImagedata(String imagedata){
		this.imagedata = imagedata;
	}
	
	public int getSex(){
		return this.sex;
	}
	
	public void setSex(int sex){
		this.sex = sex;
	}
	
	public Manager getEditManager(){
		return this.editManager;
	}
	
	public void setEditManager(Manager editManager){
		this.editManager = editManager;
	}
	
	/**
	 * ��ͨ����Աע�ᣬע����ɺ���Ҫ�ɳ�������Ա��¼ϵͳ����������ͨ��
	 * @return success���سɹ�ҳ�棬error���ش���ҳ��
	 */
	public String register(){
		if(editManager == null || imagedata == null ||
				imagedata.equals("")){
			this.setMessage("����ʧ�ܣ���Ϣ��������");
			this.setHref("Account/managerRegister.jsp");
			return ERROR;
		}
		
		ManagerDAO managerDao = new ManagerDAO();
		Manager manager = managerDao.findById(editManager.getName());
		if(manager != null){
			this.setMessage("�Ѿ�������Ϊ��" + manager.getName() + "�Ĺ���Ա�����޸���������ע�ᣡ");
			this.setHref("Account/managerRegister.jsp");
			return ERROR;
		}
		
		boolean ret = (sex==1)?true:false; 
		editManager.setSex(ret);
		
		Blob blob = managerDao.getSession().getLobHelper().createBlob(this.imagedata.getBytes());
		editManager.setPortrait(blob);
		
		editManager.setRegdatetime(System.sysClock());
		
		RoleDAO roleDao = new RoleDAO();
		Role role = roleDao.findById("admin");
		if(role == null){
			this.setMessage("���ݿ��ɫ����ڴ�������ϵϵͳ����Ա��");
			this.setHref("Account/managerRegister.jsp");
			return ERROR;
		}
		
		editManager.setRole(role);
		
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte str[] = md5.digest(this.editManager.getPassword().getBytes("utf-8"));
			str[Role.MASTER % 15] ^= Role.MASTER % 127 ; //���ý�ɫ����������ټ���
			BASE64Encoder encoder = new BASE64Encoder();
			//��ȡ���ܺ������
			String encodePassword = encoder.encode(str);
			this.editManager.setPassword(encodePassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//���õ�ǰδ���״̬
		editManager.setPermitted(false);
		
		Transaction tx = managerDao.getSession().getTransaction();
		tx.begin();
		managerDao.save(editManager);
		tx.commit();
		managerDao.getSession().close();
		
		this.setMessage("����Աע��ɹ��������ĵȴ���������Աͨ����������");
		this.setHref("enter.action");
		return SUCCESS;
	}
	
}
