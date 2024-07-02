/*
 * Copyright (c) 2020-2025, All rights reserved.
 * project name: eip
 * Date: 2020-03-22
 * Author: NianXiaoLing (xlnian@163.com)
 * Only use technical communication, please do not use it for business
 */
package com.hotent.job.persistence.manager;

import com.pharmcube.mybatis.support.manager.BaseManager;
import com.hotent.job.model.JobDetails;
import com.hotent.job.model.SchedulerVo;

import java.io.IOException;


/**
 * portal_sys_joblog Service类
 *
 * @company 广州宏天软件股份有限公司
 * @author maoww
 * @email maoww@jee-soft.cn
 * @date 2018年6月7日
 */
public interface JobDetailsManager extends BaseManager<JobDetails> {
    public void addJob(SchedulerVo schedulerVo) throws IOException, ClassNotFoundException;

    boolean isJobExists(String jobName);
}
