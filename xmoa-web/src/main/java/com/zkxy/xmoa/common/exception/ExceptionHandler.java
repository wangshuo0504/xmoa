package com.zkxy.xmoa.common.exception;


import com.zkxy.xmoa.common.constant.ResponseCodeConstant;
import com.zkxy.xmoa.common.vo.HttpResponseBody;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @Title
 *    异常处理类
 * @Description
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	List<HttpMessageConverter<?>> messageConverters;
    
	public static final String bizExceptionClassFullName= "BusinessException";
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if(handler instanceof DefaultServletHttpRequestHandler){
			return new ModelAndView("common/404/404");
		}
		HandlerMethod mathod = (HandlerMethod) handler;
		ResponseBody body = mathod.getMethodAnnotation(ResponseBody.class);
		ModelAndView mv = new ModelAndView();
		// 判断有没有@ResponseBody的注解没有的话调用父方法
		if (body == null) {
			mv = defaultRequestExceptionHandle(request,ex);
		} else {
			ajaxRequestExceptionHandle(request, response, ex);
		}
		return mv;
	}

	/**
	 * 普通请求异常
	 * <li>创建人：Jack</li>
	 * <li>创建时间：2015年6月29日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param ex
	 * @return
	 */
	private ModelAndView defaultRequestExceptionHandle(HttpServletRequest request,Exception ex) {
		//自定义业务异常
	    if (ExceptionUtil.isBussinessException(ex)) {
	        HttpResponseBody res = ExceptionUtil.parseExceptionMessage(ex);
	        request.setAttribute("errorMsg", res.getMsg());
			logger.info( res.getMsg());
			// 业务异常跳转页面
			return new ModelAndView("common/miniError");
		/*} else if (ex instanceof RpcException && ex.getCause() instanceof ConstraintViolationException) {  //dubbo 验证参数异常捕获
			ConstraintViolationException ve = (ConstraintViolationException) ex.getCause();
			StringBuilder stringBuilder = new StringBuilder();
			for (ConstraintViolation cv : ve.getConstraintViolations()) {
			     stringBuilder.append( cv.getMessage() ).append(" ");
			}
			logger.info(stringBuilder.toString());
			request.setAttribute("errorMsg", stringBuilder.toString());
			return new ModelAndView("common/error");
			*/
		} else {//系统异常
			request.setAttribute("errorMsg", "系统出错啦");
			logger.error(ExceptionUtils.getFullStackTrace(ex));
			return new ModelAndView("common/miniError");
		}
	}

	/**
	 * AJAX JSON请求异常
	 * <li>创建人：Jack</li>
	 * <li>创建时间：2015年6月29日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param request
	 * @param response
	 * @param ex
	 */
	private void ajaxRequestExceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		HttpResponseBody res = new HttpResponseBody();
		res.setCode(ResponseCodeConstant.SYSTEM_EXCEPTION_ERROR);
        res.setMsg("系统出错啦");
		//注意要放在所有RuntimeException的最后面做判断
        if (ExceptionUtil.isBussinessException(ex)) {
            res = ExceptionUtil.parseExceptionMessage(ex);
        } else {
            logger.error("异常处理", ex);
        }
		try {
			handleResponseError(res, request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	private ModelAndView handleResponseError(HttpResponseBody returnValue,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
		List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
		if (acceptedMediaTypes.isEmpty()) {
			acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
		}
		MediaType.sortByQualityValue(acceptedMediaTypes);
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		Class<?> returnValueType = returnValue.getClass();
		List<HttpMessageConverter<?>> messageConverters = this.getMessageConverters();
		if (messageConverters != null) {
			for (MediaType acceptedMediaType : acceptedMediaTypes) {
				for (HttpMessageConverter messageConverter : messageConverters) {
					if (messageConverter.canWrite(returnValueType, acceptedMediaType)) {
						//暂时不回传，很多情况下报错其实是不需要刷新TOKEN的 可能需要根据额外的信息来确定是否需要刷新TOKEN
						//如果异常不是重复提交的异常就回传一个TOKEN给前台  todo 待优化
						/*if(!ResponseCodeConstant.ERR_CODE_DUPLICATE_SUBMIT.equals(returnValue.getCode())){
							Map<String,Object> data = returnValue.getData();//有可能原来就有数据
							if(data == null){
								data = new HashMap<String, Object>();
							}
							String token = UUID.randomUUID().toString();
							data.put(CommonConstant.AVOID_REPEAT_SUB_TOKEN, token);
							request.getSession().setAttribute(CommonConstant.AVOID_REPEAT_SUB_TOKEN, token);
							returnValue.setData(data);
						}*/
						messageConverter.write(returnValue, acceptedMediaType, outputMessage);
						return new ModelAndView();
					}
				}
			}
		}
		return null;
	}

	public List<HttpMessageConverter<?>> getMessageConverters() {
		return messageConverters;
	}

	public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
		this.messageConverters = messageConverters;
	}

}
