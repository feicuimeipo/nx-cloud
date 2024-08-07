/*
 * Copyright (c) 2020-2025, All rights reserved.
 * project name: eip
 * Date: 2020-03-22
 * Author: NianXiaoLing (xlnian@163.com)
 * Only use technical communication, please do not use it for business
 */
package com.hotent.system.model;

import java.util.ArrayList;
import java.util.List;

public class ListModel {

	private String title="";

	private String key="";

	private int total=0;



	private List rowList=new ArrayList();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List getRowList() {
		return rowList;
	}

	public void addRow(Object model){
		rowList.add(model);
	}

	public void setRowList(List rowList) {
		this.rowList = rowList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}




}
