/*
 * Copyright (c) 2020-2025, All rights reserved.
 * project name: nianxiMicro
 * Date: 2020-03-22
 * Author: NianXiaoLing (xlnian@163.com)
 * Only use technical communication, please do not use it for business
 */
package com.nx.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiGroup {
	String[] group() default {DEFAULT};

	public final static String DEFAULT="default";
}
