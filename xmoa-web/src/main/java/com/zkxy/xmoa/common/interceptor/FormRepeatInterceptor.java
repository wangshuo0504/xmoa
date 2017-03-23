/**
 * Created on 2015年6月26日 by Jack
 */
package com.zkxy.xmoa.common.interceptor;


import com.zkxy.xmoa.common.constant.CommonConstant;
import com.zkxy.xmoa.common.springUtil.AvoidDuplicateSubmission;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * <p>Title: 防止表单重复提交</p>
 * <p>Description:</p>
 *
 * @author maojia
 * @version 1.0 修改记录： 修改序号，修改日期，修改人，修改内容
 */
public class FormRepeatInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {


		if(handler instanceof DefaultServletHttpRequestHandler){
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
		if (annotation != null) {
			boolean needSaveSession = annotation.needSaveToken();
			if (needSaveSession) {
				request.getSession(false).setAttribute(CommonConstant.AVOID_REPEAT_SUB_TOKEN,UUID.randomUUID());
			}

			boolean needRemoveSession = annotation.needRemoveToken();
			if (needRemoveSession) {
				if (isRepeatSubmit(request)) {
					//throw new BusinessException(ResponseCodeConstant.ERR_CODE_DUPLICATE_SUBMIT, "请不要重复提交表单或者在多个窗口中打开同一页面");
				}
				request.getSession(false).removeAttribute(CommonConstant.AVOID_REPEAT_SUB_TOKEN);
			}
		}
		return true;
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = String.valueOf(request.getSession(false).getAttribute(CommonConstant.AVOID_REPEAT_SUB_TOKEN));
		if (serverToken.equals("null") || "".equals(serverToken)) {
			return true;
		}
		String clinetToken = request.getParameter(CommonConstant.AVOID_REPEAT_SUB_TOKEN);
		return clinetToken == null || !serverToken.equals(clinetToken);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//		System.out.println("afterCompletion");
	}


}
