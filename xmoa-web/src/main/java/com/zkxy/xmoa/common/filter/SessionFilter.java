package com.zkxy.xmoa.common.filter;


import com.zkxy.xmoa.common.constant.CommonConstant;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class SessionFilter implements Filter {

	/** 要检查的 session 的名称 */
	private String sessionKey;

	/** 需要排除（不拦截）的URL的正则表达式 */
	private Pattern excepUrlPattern;

	/** 检查不通过时，转发的URL */
	private String forwardUrl;

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		sessionKey = cfg.getInitParameter("sessionKey");

		String excepUrlRegex = cfg.getInitParameter("excepUrlRegex");
		if (!StringUtils.isBlank(excepUrlRegex)) {
			excepUrlPattern = Pattern.compile(excepUrlRegex);
		}

		forwardUrl = cfg.getInitParameter("forwardUrl");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String servletPath = request.getServletPath();

		// 如果请求的路径与forwardUrl相同，或请求的路径是排除的URL时，则直接放行
		if (servletPath.equals("/access/validateCodeImage.do")
				|| servletPath.equals("/register/saveUser.do")
				|| servletPath.equals("/register/sendValicode.do")
				|| servletPath.equals("/register/toRegister.do")
				|| servletPath.equals("/register/validUserName.do")
				|| servletPath.equals("/email/toActivePage.do")
				|| servletPath.equals("/email/toActive.do")
				|| servletPath.equals("/email/toRepeatEamil.do")
				|| servletPath.equals("/access/login.do")
				|| servletPath.equals("/user/toSubUserInfoPage.do")
				|| servletPath.equals("/user/checkUniqueness.do")
				||servletPath.equals("/register/toContract_sp.do")
				||servletPath.equals("/register/toContract_cu.do")
				||servletPath.equals("/register/checkValicode.do")
				||servletPath.equals("/user/checkUniqueness.do")
				||servletPath.equals("/file/sFile.do")
				||servletPath.equals("/file/showFile.do")
				||servletPath.equals("/user/activeSubUser.do")
				|| servletPath.equals(forwardUrl)
				|| excepUrlPattern.matcher(servletPath).matches()) {
			chain.doFilter(req, res);
			return;
		}

		Object sessionObj = request.getSession().getAttribute(CommonConstant.SESSION_USER_KEY);
		// 如果Session为空，则跳转到指定页面
		if (sessionObj == null) {
			String contextPath = request.getContextPath();
			String redirect = servletPath + "?"
					+ StringUtils.defaultString(request.getQueryString());
			response.sendRedirect(contextPath
					+ StringUtils.defaultIfEmpty(forwardUrl, "/"));
		} else {
			/*
			 * BeanFactory beans =
			 * WebApplicationContextUtils.getWebApplicationContext
			 * (request.getSession().getServletContext()); IResourceService
			 * resourceService =
			 * (IResourceService)beans.getBean("resourceService"); String menuId
			 * = resourceService.getMenuIdByUrl(servletPath);
			 */
			if(request.getParameter("currentValue")!= null && !"".equals(request.getParameter("currentValue"))){
			request.setAttribute("currentValue",
					request.getParameter("currentValue"));
			request.getSession().setAttribute("currentValue", request.getParameter("currentValue"));
			}else{
				if(request.getSession().getAttribute("currentValue") != null && !"".equals(request.getSession().getAttribute("currentValue"))){
					request.setAttribute("currentValue",
							request.getSession().getAttribute("currentValue"));
				}
			}
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
	}

}
