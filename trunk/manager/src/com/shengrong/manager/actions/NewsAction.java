package com.shengrong.manager.actions;

import java.io.File;

public class NewsAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698849958267326143L;

	/**
	 * ��ת���ŷ���ҳ��
	 */
	@Override
	public String execute(){
		return SUCCESS;
	}
	
	public String uploadImage(){
		File file = (File)request.getAttribute("upfile");
		
		
		return SUCCESS;
	}
}
