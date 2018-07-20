package com.shengrong.manager.actions;

import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Transaction;

import com.shengrong.hibernate.Member;
import com.shengrong.hibernate.MemberDAO;
import com.shengrong.hibernate.customization.MemberEncapsulator;

public class AboutMemberAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8516131113424460551L;
	
	private MemberEncapsulator encpMember;
	
	private List<Member> memberList;
	
	public List<Member> getMemberList(){
		return this.memberList;
	}
	
	public void setMemberList(List<Member> memberList){
		this.memberList = memberList;
	}
	
	public MemberEncapsulator getEncpMember(){
		return this.encpMember;
	}
	
	public void setEncpMember(MemberEncapsulator encpMember){
		this.encpMember = encpMember;
	}
	
	/**
	 * ���ڹ�˾��Ա��ҳ����ת
	 */
	@Override
	public String execute(){
		MemberDAO memberDao = new MemberDAO();
		memberList = memberDao.findAll();
		return SUCCESS;
	}
	
	public String save(){
		Member member = encpMember.dataDecapsulate();
		member.setManager(getManager());
		MemberDAO memberDao = new MemberDAO();
		
		Transaction tx = memberDao.getSession().getTransaction();
		tx.begin();
		memberDao.save(member);
		tx.commit();
		memberDao.getSession().close();
		
		return SUCCESS;
	}
	
	public String delete(){
		JSONObject root = new JSONObject();
		if(request.getParameter("memberid") == null ||
				request.getParameter("memberid").equals("")){
			root.put("code", "400");
			root.put("msg", "������ʶidΪ�գ�");
			this.setResult(root.toString());
			return SUCCESS;
		}
		
		int memberid = Integer.parseInt(request.getParameter("memberid"));
		MemberDAO memberDao = new MemberDAO();
		Member member = memberDao.findById(memberid);
		if(member == null){
			root.put("code", "400");
			root.put("msg", "û�б�ʶΪ" + memberid + "�ļ�¼��");
			this.setResult(root.toString());
			return SUCCESS;
		}
		
		Transaction tx = memberDao.getSession().getTransaction();
		tx.begin();
		memberDao.delete(member);
		tx.commit();
		memberDao.getSession().close();
		
		root.put("code", "200");
		root.put("msg", "�����ɹ���");
		this.setResult(root.toString());
		return SUCCESS;
	}
}
