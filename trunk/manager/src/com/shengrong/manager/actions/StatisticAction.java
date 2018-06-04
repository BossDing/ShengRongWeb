package com.shengrong.manager.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;

import net.sf.json.JSONObject;

import com.shengrong.hibernate.DatumDAO;
import com.shengrong.hibernate.ManagerDAO;
import com.shengrong.hibernate.MemberDAO;
import com.shengrong.hibernate.NewsDAO;
import com.shengrong.manager.model.AmountInfo;
import com.shengrong.manager.model.Bytes;
import com.sun.management.OperatingSystemMXBean;

public class StatisticAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 497563937624360827L;
	
	//�������ó�Щ����ֹ�������д˴�ϵͳ���ʱ��cpuռ���ʣ��Ͳ�׼��  
    private static final int CPUTIME = 5000;  
  
    private static final int PERCENT = 100;  
  
    private static final int FAULTLENGTH = 10;  

	private AmountInfo amountInfo = new AmountInfo();
	
	public AmountInfo getAmountInfo(){
		return this.amountInfo;
	}
	
	public void setAmountInfo(AmountInfo amountInfo){
		this.amountInfo = amountInfo;
	}
	
	public String execute(){
		NewsDAO newsDao = new NewsDAO();
		Long newsCount = (Long)newsDao.getSession().createQuery("select count(*) from " + "News").uniqueResult();
		this.amountInfo.setNewsCount(newsCount);
		/**
		ProductDAO productDao = new ProductDAO();**/
		
		DatumDAO datumDao = new DatumDAO();
		Long datumCount = (Long)datumDao.getSession().createQuery("select count(*) from " + "Datum").uniqueResult();
		this.amountInfo.setDatumCount(datumCount);
		
		MemberDAO memberDao = new MemberDAO();
		Long memberCount = (Long)memberDao.getSession().createQuery("select count(*) from " + "Member").uniqueResult();
		this.amountInfo.setMemberCount(memberCount);
		
		return SUCCESS;
	}
	
	public String systemPerformance(){
		 int kb = 1024;
		 
		 OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                 .getOperatingSystemMXBean();
		 
		 // ����ϵͳ
         String osName = System.getProperty("os.name");
         
		 long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
		 // ��ʹ�õ������ڴ�
         long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
                 .getFreePhysicalMemorySize())
                 / kb;
        double cpuPercentage = 0.0;
        try {
			cpuPercentage = getCPUloaderPercentage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		JSONObject root = new JSONObject();
		root.put("memoryRatio", usedMemory*1.0/totalMemorySize*100);
		root.put("osName", osName);
		root.put("cpuRatio", cpuPercentage);
		
		this.setResult(root.toString());
		return SUCCESS;
	}
	
	 /*****  
     * ��ȡCPU��Ϣ.  
     * @param proc  
     * @return  
     */  
	private double getCPUloaderPercentage() throws IOException{
		Process process = Runtime.getRuntime().exec("wmic cpu get LoadPercentage" );
		InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        br.readLine(); // ����������
        br.readLine(); // �����������¿���
        String percentageLine = br.readLine();
        if (percentageLine == null) {
          return 0;
        }
        return Double.parseDouble(percentageLine.trim());
	}
}
