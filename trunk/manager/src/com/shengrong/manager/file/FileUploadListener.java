package com.shengrong.manager.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

/**
 * �ļ��ϴ�������Ϣ
 * @author zhangzheng
 *
 */
public class FileUploadListener implements ProgressListener {

	private HttpSession session;  
	  
    public FileUploadListener(HttpServletRequest request) {  
        session = request.getSession();  
        FileUploadProgress fileUploadProgress = new FileUploadProgress();  
        fileUploadProgress.setComplete(false);   
        session.setAttribute("fileUploadProgress", fileUploadProgress);  
    }  
  
      
    //���½������  
    @Override  
    public void update(long readedBytes, long totalBytes, int currentItem) {   
        //ʵ���ļ��ϴ��ĺ��ķ���  
        Object attribute = session.getAttribute("fileUploadProgress");  
        FileUploadProgress fileUploadProgress  = null;  
        if(null == attribute){   
            fileUploadProgress = new FileUploadProgress();  
            fileUploadProgress.setComplete(false);   
            session.setAttribute("fileUploadProgress", fileUploadProgress);  
        }else{  
             fileUploadProgress = (FileUploadProgress)attribute;  
        }  
  
        fileUploadProgress.setCurrentLength(readedBytes);  
        fileUploadProgress.setLength(totalBytes);   
        if(readedBytes==totalBytes){  
            fileUploadProgress.setComplete(true);  
        }else{   
            fileUploadProgress.setComplete(false);  
        }  
           
        session.setAttribute("progress", fileUploadProgress);  
    }  

}
