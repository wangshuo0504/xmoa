/**
 * Created on 2015年6月15日 by Jack
 */
package com.zkxy.xmoa.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>Title: 防XSS攻击过滤器</p>
 * <p>Description:  防XSS攻击过滤器</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author Caiming
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class XSSFilter implements Filter {

	 @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	    }
	    @Override
	    public void destroy() {
	    }
	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
	        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	    }

}

