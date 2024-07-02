/*
 * Copyright (c) 2020-2025, All rights reserved.
 * project name: eip
 * Date: 2020-03-22
 * Author: NianXiaoLing (xlnian@163.com)
 * Only use technical communication, please do not use it for business
 */
package com.nx.cache.annotation;

import com.nx.cache.enums.CacheModeEnum;
import com.nx.redis.RedisConfigFactory;
import com.nx.redis.enums.RedisConstant;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static com.nx.cache.CacheProviderFactory.DEFAULT_CACHE_GROUP_NAME;


/**
 *
 * 先从缓存里取值，如果缓存里有值，则直接从缓存里获取；
 * 如查缓存里没值，则执行方法，取得返回值(并重写记录缓存）
 * 这里的value和key都支持SpEL 表达式
 *
 * @author 佚名
 * @email xlnian@163.com
 * @date 2020年6月16日
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
    //缓存名称
    String cacheName() default DEFAULT_CACHE_GROUP_NAME;

    //key前缀
    String prefix() default "";

    @AliasFor("key")
    String name() default "";

    @AliasFor("key")
    String value() default "";

    /**
     * 缓存key，支持SpEL表达式
     * </ul>
     *
     * @return String
     */
    String key() default "";

    /**
     * key是否为纯文本，即不做SpEL解析
     * @return
     */
    boolean pureKey() default false;


    /**
     * 描述
     * @return String
     */
    String depict() default "";


    /**
     * 是否忽略在操作缓存中遇到的异常，如反序列化异常，默认true。
     * <p>true: 有异常会输出warn级别的日志，并直接执行被缓存的方法（缓存将失效）</p>
     * <p>false:有异常会输出error级别的日志，并抛出异常</p>
     *
     * @return boolean
     */
    boolean ignoreException() default true;

    /**
     * 如果级存值不存在，返回值再写入缓存
     * @return
     */
    boolean getAndSet() default true;


    /**
     * 不存在，写缓存时
     * @return
     */
    CacheModeEnum type() default CacheModeEnum.Both; //both,local,remote

    /**
     * 一级缓存配置
     *
     * @return FirstCache
     */
    FirstCache firstCache() default @FirstCache();

    /**
     * 二级缓存配置
     *
     * @return SecondaryCache
     */
    SecondaryCache secondaryCache() default @SecondaryCache();
}