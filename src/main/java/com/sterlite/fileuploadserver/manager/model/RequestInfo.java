package com.sterlite.fileuploadserver.manager.model;

import java.util.Date;


public class RequestInfo {

	private String status;
	private Date reqDate;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	
	
}
