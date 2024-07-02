package com.nx.prometheus.metric.undertow;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName UndertowMetricsConfiguration
 * @Description TODO
 * @Author NIANXIAOLING
 * @Date 2022/6/23 23:19
 * @Version 1.0
 **/
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfiguration(before = ServletWebServerFactoryAutoConfiguration.class)
@ConditionalOnClass(Undertow.class)
public class UndertowMetricsConfiguration {

    @Bean
    @ConditionalOnClass(Undertow.class)
    public UndertowMetrics undertowMetrics() {
        return new UndertowMetrics();
    }

    @Bean
    @ConditionalOnClass(Undertow.class)
    public UndertowBuilderCustomizer undertowBuilderCustomizerEnableStatistics() {
        return builder -> builder.setServerOption(UndertowOptions.ENABLE_STATISTICS, true);
    }
}
