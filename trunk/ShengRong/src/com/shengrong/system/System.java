package com.shengrong.system;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class System {
	
	private static SystemSettings systemSettings = new SystemSettings(); 
	
	public static SystemSettings SystemSettings(){
		return System.systemSettings;
	}
	
	/**
	 * ��ϵͳ����һЩ��ʼ�������ü���ز����ļ���
	 * @return ��ʼ�����سɹ�����true����ʼ�����󷵻�false
	 */
	final static public boolean initSys(){
		
		return true;
	}
	
	public static Timestamp sysClock(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		Timestamp ts = Timestamp.valueOf(df.format(new Date()));
		return ts;
	}
}
