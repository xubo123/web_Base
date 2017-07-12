package com.project.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.user.entity.User;


public class SystemFilter implements Filter {

	private FilterConfig config;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsr = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String logonStrings = config.getInitParameter("logonStrings");
		String[] logonList = logonStrings.split(";");
		if (this.isContains(hsr.getRequestURI(), logonList)) {// 对登录页面不进行过滤
			chain.doFilter(request, response);
			return;
		}
		HttpSession hs = hsr.getSession();
		User user = (User) hs.getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "您还未登录或者登录超时,请重新登录系统！");
			request.getRequestDispatcher("/page/error/timeOut.jsp").forward(request, response);
			return;
		}
		if (hsr.getServletPath().equals("/druid")) {
			if (user.getRole() != null && user.getRole().getSystemAdmin() == 1) {
				chain.doFilter(hsr, res);
			} else {
				request.setAttribute("msg", "您的访问被拒绝！");
				request.getRequestDispatcher("/page/error/reject.jsp").forward(request, response);
			}
		} else {
			chain.doFilter(hsr, res);
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	public boolean isContains(String container, String[] regx) {
		boolean result = false;

		for (int i = 0; i < regx.length; i++) {
			if (container.indexOf(regx[i]) != -1) {
				return true;
			}
		}
		return result;
	}

}
