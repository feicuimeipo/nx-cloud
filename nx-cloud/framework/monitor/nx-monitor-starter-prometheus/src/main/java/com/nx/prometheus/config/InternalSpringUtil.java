/*
 * Copyright (c) 2020-2025, All rights reserved.
 * project name: nianxiMicro
 * Date: 2020-03-22
 * Author: NianXiaoLing (xlnian@163.com)
 * Only use technical communication, please do not use it for business
 */
package com.nx.prometheus.config;


import org.apache.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * org.nianxi.boot.support.MicrometerSpringUtil
 * 获取上下文bean
 *
 * @作者：nianxiaoling
 * @邮箱：xlnian@163.com
 * @创建时间：2018年4月3日
 */
@Component
public class InternalSpringUtil implements ApplicationContextAware {

	protected static final Logger LOGGER = LoggerFactory.getLogger(InternalSpringUtil.class);

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext _context)
			throws BeansException {
		context = _context;
	}

	/**
	 * 获取spring容器上下文。
	 * @return  ApplicationContext
	 */
	public static ApplicationContext getApplicaitonContext(){
		return context;
	}

	/**
	 * 获取Spring容器的Bean
	 *
	 * @param beanClass
	 * @return T
	 * @exception
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> beanClass) {
		T  bean = null;
		try{
			bean= context.getBean(beanClass);
		}
		catch(Exception ex){
			try {
				if ( beanClass !=null) {
					String beanName= beanClass.getSimpleName();
					String beanId = beanName.substring(0, 1).toLowerCase()+beanName.substring(1);
					bean = (T) context.getBean(beanId);
				}
			} catch (BeansException e) {
				LOGGER.debug("getBean:" + beanClass +"," + ex.getMessage());
			}
		}
		return bean;
	}

	/**
	 * 根据指定的接口或基类获取实现类列表。
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class> getImplClass(Class clazz) throws ClassNotFoundException{
		List<Class> list=new ArrayList<Class>();

		Map map= context.getBeansOfType(clazz);
		for(Object obj : map.values()){
			String name=obj.getClass().getName();
			int pos=name.indexOf("$$");
			if(pos>0){
				name=name.substring(0,name.indexOf("$$")) ;
			}
			Class cls= Class.forName(name);

			list.add(cls);
		}
		return list;
	}


	/**
	 * 获取接口的实现类实例。
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String,Object> getImplInstance(Class clazz) throws ClassNotFoundException{
		Map map= context.getBeansOfType(clazz);
		return map;
	}

	/**
	 * 发布事件。
	 * @param event
	 * void
	 */
	public static void publishEvent(ApplicationEvent event){
		if(context!=null && event!=null){
			context.publishEvent(event);
		}
	}

	/**
	 * 发布事件
	 * @param var
	 */
	public static void publishEvent(Object var){
		if(context!=null && var!=null){
			context.publishEvent(var);
		}
	}

	/**
	 * 获取Classpath物理路径
	 * @return
	 */
	public static String getClasspath(){
		 String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		 String rootPath  = "";
		  //windows下
		  if("\\".equals(File.separator)){
		   rootPath  = classPath.substring(1);
		   rootPath = rootPath.replace("/", "\\");
		  }
		  //linux下
		  if("/".equals(File.separator)){
		   rootPath  = classPath.substring(1);
		   rootPath = rootPath.replace("\\", "/");
		  }
		  return rootPath;
	}


	public static String getAndValidateProperty(String key) {
		Environment environment = InternalSpringUtil.getBean(Environment.class);
		String value = environment.getProperty(key);
		if(StringUtils.isBlank(value)){
			throw new IllegalArgumentException(String.format("Property for key:%s not exists", key));
		}
		return value;
	}

	public static String getProperties(String key){
		Environment environment = InternalSpringUtil.getBean(Environment.class);
		return environment.getProperty(key);
	}

	public static String getProperties(String key,String defaultValue){
		Environment environment = InternalSpringUtil.getBean(Environment.class);
		return environment.getProperty(key,defaultValue);
	}
}
