package com.project.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 字符集编码拦截器
 * 
 */
public class EncodingInterceptor extends AbstractInterceptor
{

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation actionInvocation) throws Exception
	{
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		return actionInvocation.invoke();
	}

}
