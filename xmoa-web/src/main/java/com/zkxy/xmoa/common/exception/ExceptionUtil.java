/**
 * Created on 2016年2月25日 by ZhouMin
 */
package com.zkxy.xmoa.common.exception;


import com.zkxy.xmoa.common.constant.ResponseCodeConstant;
import com.zkxy.xmoa.common.vo.HttpResponseBody;
import com.zkxy.xmoa.util.StringUtil;

/**
 * @Title 
 * @Description
 *   处理业务异常类
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class ExceptionUtil {
    public static final String bizExceptionClassFullName= "BusinessException";
    /**
     * 
     * @创建人 ZhouMin
     * @创建时间 2015年10月12日
     * @创建目的 【判断是否是BussinessException】
     * @修改目的 【修改人：，修改时间：】
     * @param ex Exception
     * @return boolean
     */
    public static boolean isBussinessException(Exception ex) {
        if (!(ex instanceof RuntimeException)) {
            return false;
        }
        if (ex instanceof BusinessException) {// 非dubbo调用产生的异常
            return true;
        }
        // dubbo调用产生的异常
        String message = ex.getMessage();
        StackTraceElement[] stackArray = ex.getStackTrace();
        if (StringUtil.isNotEmpty(message) && stackArray != null && stackArray.length > 0) {
            String dubboExceptionFilter = "com.alibaba.dubbo.rpc.filter.ExceptionFilter";
            String stackArray0ClassName = stackArray[0].getClassName();
            int index = message.indexOf(':');
            if (dubboExceptionFilter.equals(stackArray0ClassName) && index > 1
                    && bizExceptionClassFullName.equals(message.substring(0, index))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @创建人 ZhouMin
     * @创建时间 2015年10月12日
     * @创建目的 【解析BusinessException message】
     * @修改目的 【修改人：，修改时间：】
     */
    public static HttpResponseBody parseExceptionMessage(Exception ex) {
        HttpResponseBody res = new HttpResponseBody();
        res.setCode(ResponseCodeConstant.SYSTEM_EXCEPTION_ERROR);
        String message = ex.getMessage();
        res.setMsg(message);
        int indexResCodeStart = message.indexOf(':');
        int indexResCodeEnd = message.indexOf(BusinessException.resCodeSplitFlat);
        int indexMsgEnd = message.indexOf("\n");

        if (ex instanceof BusinessException) {// 非dubbo调用产生的异常
            res.setCode(((BusinessException)ex).getCode());
            res.setMsg(message.substring(indexResCodeEnd + BusinessException.resCodeSplitFlat.length()));// 去掉code
            return res;
        }
        // dubbo调用产生的异常
        if (indexResCodeStart != -1 && indexResCodeEnd != -1 && indexMsgEnd != -1) {
            if (indexResCodeEnd > indexResCodeStart) {
                String rsCode = message.substring(indexResCodeStart + 1, indexResCodeEnd);
                res.setCode(rsCode);
            }
            if (indexMsgEnd > indexResCodeEnd) {
                String rsMsg = message.substring(indexResCodeEnd + BusinessException.resCodeSplitFlat.length(), indexMsgEnd);
                res.setMsg(rsMsg);
            }
        }

        return res;
    }
}

