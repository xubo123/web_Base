package com.project.interceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.project.user.entity.User;

/**
 * session拦截器
 * 
 */
public class SessionInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);

	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext ctx = actionInvocation.getInvocationContext();
		String actionName = ctx.getName();
		if (actionName.equals("messageBoardAction")) {
			return actionInvocation.invoke();
		}
		User user = (User) ActionContext.getContext().getSession().get("user");
		logger.info("进入session拦截器->访问路径为[" + ServletActionContext.getRequest().getServletPath() + "]");
		if (user == null) {
			String errMsg = "您还没有登录或登录已超时，请重新登录！";
			logger.info(errMsg);
			ServletActionContext.getRequest().setAttribute("msg", errMsg);
			return "noSession";
		}
		return actionInvocation.invoke();
	}

}
