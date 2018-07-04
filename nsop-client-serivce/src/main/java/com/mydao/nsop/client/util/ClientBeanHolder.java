package com.mydao.nsop.client.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/** 【工程名】
 *     vms
 * 【版权所有】
 *     Copyright ? 2017 蓝盾股份 
 * 【类文件名称】
 *     YggdrasillBeanHolder.java
 * 【类文件描述】
 *     
 *
 * 【历史信息】
 *      版本      日期      作者/修改者     内容描述
 *     -------- ---------   ---------- ------------------------
 *      1.0.0    2017年4月19日     miao        最初版本
 */
 

 
@Component
public class ClientBeanHolder implements ApplicationContextAware{

	private static ApplicationContext applicationContext = null; 
	
	@Override
	public void setApplicationContext(ApplicationContext content) throws BeansException {
		ClientBeanHolder.applicationContext=content;
	}
	
	 /*** 
     * 根据一个bean的id获取配置文件中相应的bean 
     *  
     * @param name 
     * @return 
     * @throws BeansException 
     */  
    public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }  
  
    /*** 
     * 类似于getBean(String name)只是在参数中提供了需要返回到的类型。 
     *  
     * @param name 
     * @param requiredType 
     * @return 
     * @throws BeansException 
     */  
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {  
        return applicationContext.getBean(name, requiredType);  
    }  
  
    /** 
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true 
     *  
     * @param name 
     * @return boolean 
     */  
    public static boolean containsBean(String name) {  
        return applicationContext.containsBean(name);  
    }  

}
