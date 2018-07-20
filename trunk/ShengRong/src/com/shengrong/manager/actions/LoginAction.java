package com.shengrong.manager.actions;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Transaction;

import sun.misc.BASE64Encoder;

import com.shengrong.hibernate.Manager;
import com.shengrong.hibernate.ManagerDAO;
import com.shengrong.hibernate.Master;
import com.shengrong.hibernate.MasterDAO;
import com.shengrong.hibernate.Role;
import com.shengrong.hibernate.RoleDAO;

public class LoginAction extends ActionBase implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7722879410740390753L;
	
	private String mastername;

	private String password;
	
	private String roleid;
	
	private Map<String, Object> mySession;
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.mySession = session;
	}
	
	public String getRoleid(){
		return this.roleid;
	}
	
	public void setRoleid(String roleid){
		this.roleid = roleid;
	}
	
	public String getMastername(){
		return this.mastername;
	}
	
	public void setMastername(String mastername){
		this.mastername = mastername;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 *	����ϵͳ��ʼ�������û�г�������Ա��Ҫ���г�������Աע�ᣬ����������ϵͳҳ��
	 * @return String ���ء�success����ֱ�ӽ���ϵͳ�����ء�input�����볬��ע��ҳ�棻���ء�error���������ҳ��
	 * 					
	 */
	@Override
	public String execute(){
		RoleDAO roleDao = new RoleDAO();
		Role master = roleDao.findById("master");
		if(master == null){
			this.setMessage("���ݿ���û��master��ɫ������ϵϵͳ����Ա����ά�ޣ�");			
			return ERROR;
		}
		
		MasterDAO managerDao = new MasterDAO();
		@SuppressWarnings("unchecked")
		List<Master> managers = managerDao.findAll();
		if(managers.size() == 0){
			return INPUT;
		}else if(managers.size() == 1){
			return SUCCESS;
		}else {
			this.setMessage("��������Ա��Ψһ������ϵϵͳ����Ա����ά�ޣ�");
			return ERROR;
		}
	}
	
	/**
	 * ��������Աע��
	 * @return String errorʱ�����������룻successʱ�����¼ҳ��
	 */
	public String register(){
		
		if(this.mastername == null || this.password == null){
			this.setMessage("�˺ź����벻����Ϊ�գ�");
			this.setHref("enter.action");
			return ERROR;
		}
		
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte str[] = md5.digest(this.password.getBytes("utf-8"));
			str[Role.MASTER % 15] ^= Role.MASTER % 127 ; //���ý�ɫ����������ټ���
			BASE64Encoder encoder = new BASE64Encoder();
			
			//��ȡ���ܺ������
			String encodePassword = encoder.encode(str);
			
			RoleDAO roleDao = new RoleDAO();
			Role role = roleDao.findById("master");
			
			MasterDAO masterDao = new MasterDAO();
			Master master = new Master(this.mastername, role, encodePassword);
			Transaction tx = masterDao.getSession().beginTransaction();
			masterDao.save(master);
			tx.commit();
			masterDao.getSession().close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.setMessage("���ѳɹ�ע�ᳬ������Ա��");
		this.setHref("enter.action");
		return LOGIN;
	}
	
	/**
	 * ����Ա��¼����������Ա��¼����ͨ����Ա��һ������Ա����ģ��
	 * @return successʱ����ϵͳ(roleid�û��ж������ֵ�¼��ʽ)��errorʱ�������ҳ��
	 */
	public String login(){
		
		if(this.mastername == null || this.password == null || this.roleid == null){
			this.setMessage("�˺š����롢��¼��ݲ�����Ϊ�գ�");
			this.setHref("enter.action");
			return ERROR;
		}
		
		//��֤��¼��Ϣ
		String encodePassword = null;
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte str[] = md5.digest(this.password.getBytes("utf-8"));
			str[Role.MASTER % 15] ^= Role.MASTER % 127 ; //���ý�ɫ����������ټ���
			BASE64Encoder encoder = new BASE64Encoder();
			//��ȡ���ܺ������
			encodePassword = encoder.encode(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		RoleDAO roleDao = new RoleDAO();
		Role role = roleDao.findById(this.roleid);
		
		//��������Ա��ݵ�¼
		if(this.roleid.equals("master")){
			MasterDAO masterDao = new MasterDAO();
			Master master = masterDao.findById(this.mastername);
			if(master != null && master.getPassword().equals(encodePassword)){
				//��¼�ɹ�
				mySession.put("loginFlag", "login");
				mySession.put("loginRole", Role.MASTER);
				mySession.put("loginName", master.getMastername());
				return "master";
			}else{
				this.setMessage("�˺Ż��������Ҳ�п��������ѡ�����");
				this.setHref("enter.action");
				return ERROR;
			}
			
		}else if(this.roleid.equals("admin")){
			//��ͨ����Ա��ݵ�¼
			ManagerDAO managerDao = new ManagerDAO();
			
			Manager manager = managerDao.findById(this.mastername);
			if(manager != null && manager.getPassword().equals(encodePassword)){
				if(manager.getPermitted()){
					//��¼�ɹ�
					mySession.put("loginFlag", "login");
					mySession.put("loginRole", Role.MANAGER);
					mySession.put("loginName", manager.getName());
					mySession.put("loginSex", manager.getSex().toString());
					return SUCCESS;
				}else{
					//��¼�ɹ�
					this.setMessage("�����˺Ż�δͨ����������Ա��ˣ�����ϵ��������Ա������ˣ�");
					this.setHref("enter.action");
					return ERROR;
				}
			}else{
				this.setMessage("�˺Ż��������Ҳ�п��������ѡ�����");
				this.setHref("enter.action");
				return ERROR;
			}
		}else{
			this.setMessage("ϵͳ��ɫ�н���master��admin������¼ʱ�����������ɫ������ϵϵͳ����Ա��");
			this.setHref("enter.action");
			return ERROR;
		}
	}
	
	public String logout(){
		mySession.clear();
		return "logout";
	}
}
