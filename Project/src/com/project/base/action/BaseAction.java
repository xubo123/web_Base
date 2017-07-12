package com.project.base.action;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;

@Action
public class BaseAction
{
	private static final Logger logger = Logger.getLogger(BaseAction.class);
	public void writeJson(Object object)
	{
		try
		{
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			System.out.println(json);
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e)
		{
			logger.error(e, e);
		}
	}

	public HttpServletRequest getRequest()
	{
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse()
	{
		return ServletActionContext.getResponse();
	}

	public Map<String, Object> getSession()
	{
		return ActionContext.getContext().getSession();

	}

	public PrintWriter getPrintWriter()
	{
		try
		{
			return ServletActionContext.getResponse().getWriter();
		} catch (IOException e)
		{
			logger.error(e, e);
		}
		return null;
	}
	
}
