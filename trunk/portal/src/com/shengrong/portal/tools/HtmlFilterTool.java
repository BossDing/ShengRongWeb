package com.shengrong.portal.tools;

import java.util.regex.Pattern;

public class HtmlFilterTool {
	public static String toPlainText(String content){
		if(null==content) return content;     
        
		java.util.regex.Pattern p_script;   
        java.util.regex.Matcher m_script;   
        java.util.regex.Pattern p_style;   
        java.util.regex.Matcher m_style;   
        java.util.regex.Pattern p_html;   
        java.util.regex.Matcher m_html;   
              
        try {   
		  String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";  
		  //����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script> }   
		  String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";   
		  //����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style> }   
		  String regEx_html = "<[^>]+>"; //����HTML��ǩ��������ʽ   
		     
		  p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);   
		  m_script = p_script.matcher(content);   
		  content = m_script.replaceAll(""); //����script��ǩ  
		  p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);   
		  m_style = p_style.matcher(content);   
		  content = m_style.replaceAll(""); //����style��ǩ   
		  p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);   
		  m_html = p_html.matcher(content);   
		  content = m_html.replaceAll(""); //����html��ǩ   
          }catch(Exception e) {  
            	e.printStackTrace();
            	return content;
          }   
       return content;  
	}
}
