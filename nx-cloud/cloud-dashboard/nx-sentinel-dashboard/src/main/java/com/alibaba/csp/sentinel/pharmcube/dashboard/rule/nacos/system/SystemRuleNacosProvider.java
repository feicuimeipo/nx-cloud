package com.alibaba.csp.sentinel.pharmcube.dashboard.rule.nacos.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.pharmcube.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName SystemRuleNacosProvider
 * @Description TODO
 * @Author NIANXIAOLING
 * @Date 2022/6/15 17:03
 * @Version 1.0
 **/
@Component("systemRuleNacosProvider")
public class SystemRuleNacosProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {
    @Autowired
    private ConfigService configService;

    @Override
    public List<SystemRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntitiesFromNacos(
                this.configService,
                appName,
                NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                SystemRuleEntity.class
        );
    }
}

