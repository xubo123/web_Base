package com.project.user.entity;

import java.io.Serializable;

public class ServiceType implements Serializable {
	private static final long serialVersionUID = 1L;
	private long serviceTypeId;
	private String serviceType;
	public long getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	

}
