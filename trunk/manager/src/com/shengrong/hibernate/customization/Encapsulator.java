package com.shengrong.hibernate.customization;

/**
 * ������ģ�ͽ��з�װ������Ӧǰ�˴���ӿ�
 * 
 * @author zhangzheng
 *
 * @param <T> ��װ��ģ����
 */
interface Encapsulator<T> {

	/**
	 * ���ݽ��ӿ�
	 * @return
	 */
	public T dataDecapsulate();
}
