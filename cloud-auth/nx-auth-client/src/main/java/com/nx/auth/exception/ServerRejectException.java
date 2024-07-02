/*
 * Copyright (c) 2020-2025, All rights reserved.
 * project name: eip
 * Date: 2020-03-22
 * Author: NianXiaoLing (xlnian@163.com)
 * Only use technical communication, please do not use it for business
 */
package com.nx.auth.exception;

import com.nx.auth.model.ResultCode;
import com.nx.api.exception.BaseException;

/**
 * 服务器拒绝异常
 * @author heyifan
 * @date 2017年6月30日
 */
public class ServerRejectException extends BaseException {
	private static final long serialVersionUID = 1L;
	
	public ServerRejectException(){
		super(ResultCode.NO_PERMISSION.getMessage());
	}
	
	public ServerRejectException(String detailMessage){
		super(ResultCode.NO_PERMISSION.getMessage() +":"+detailMessage);
	}
}