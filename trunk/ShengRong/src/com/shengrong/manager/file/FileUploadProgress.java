package com.shengrong.manager.file;

/** 
 * �ϴ��ļ�������Ϣ 
 *  
 * @author zhangzheng 
 * @version 0.1 
 */  
public class FileUploadProgress {
	
	// �ļ��ܳ���(��������Ϊ1�ֽڷ�ֹǰ̨����/0�����)  
    private long length = 1;  
    
    // ���ϴ����ļ�����  
    private long currentLength = 0;  
    
    // �ϴ��Ƿ����  
    private boolean isComplete = false;  
    
    public long getLength() {  
        return length;  
    }  
    public void setLength(long length) {  
        this.length = length;  
    }  
    public long getCurrentLength() {  
        return currentLength;  
    }  
    public void setCurrentLength(long currentLength) {  
        this.currentLength = currentLength;  
    }  
    public boolean isComplete() {  
        return isComplete;  
    }  
    public void setComplete(boolean isComplete) {  
        this.isComplete = isComplete;  
    }  
      
    public FileUploadProgress() {  
        super();  
    }  
}
