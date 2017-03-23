package com.zkxy.xmoa.common.controller;


import com.zkxy.xmoa.common.constant.ResponseCodeConstant;
import com.zkxy.xmoa.common.vo.HttpResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Title
 *    controller基类
 * @Description
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public abstract class BaseController {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 返回成功 ResponseBody
	 * <li>创建人：Caiming</li>
	 * <li>创建时间：2015年9月5日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param message
	 * @return
	 */
	public HttpResponseBody successResponse(String message) {
		return new HttpResponseBody(ResponseCodeConstant.SUCCESS, message);
	}
	
	/**
	 * @Description
     *     返回成功 ResponseBody
     * <li>创建人：Caiming</li>
     * <li>创建时间：2015年9月5日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param message
     * @return
     */
    public HttpResponseBody successResponse(String message,Map<String,Object> data) {
        return new HttpResponseBody(ResponseCodeConstant.SUCCESS, message,data);
    }

	/**
	 * @Description
	 *  返回失败 ResponseBody
	 * <li>创建人：Caiming</li>
	 * <li>创建时间：2015年9月5日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param message
	 * @return
	 */
	public HttpResponseBody failResponse(String message) {
		return new HttpResponseBody(ResponseCodeConstant.FAIL, message);
	}

	/**
	 * @Description
	 * 获取request
	 * @创建人 lison
	 * @创建时间 2015年10月23日
	 * @创建目的【】
	 * @修改目的【修改人：，修改时间：】
	 * @return
	 */
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * @Description
	 * 获取session
	 * @创建人 lison
	 * @创建时间 2015年10月23日
	 * @创建目的【】
	 * @修改目的【修改人：，修改时间：】
	 * @return
	 */
	public HttpSession getSession(){
		return getRequest().getSession();
	}

	/**
	 * @Description
	 * 获取session
	 * @创建人 lison
	 * @创建时间 2015年10月23日
	 * @创建目的【】
	 * @修改目的【修改人：，修改时间：】
	 * @return
	 */
	public HttpSession getSession(HttpServletRequest request){
	    return request.getSession();
	}

	/**
	 * @Description
	 * 返回地址栏URL 如： http://localhost:8080/o2o-web
	 * <li>创建人：xiaopu</li>
	 * <li>创建时间：2015年9月5日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param request
	 * @return
	 */
	public String getSessionUrl(HttpServletRequest request){
		StringBuffer sb=new StringBuffer();  
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		sb.append(":");
		sb.append(request.getServerPort());
		sb.append(request.getContextPath());
		return sb.toString();
	}
	

}
