
/**
 * Created on 2015年6月19日 by xiaopu
 */
package com.zkxy.xmoa.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author xiaopu
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = xssEncode(values[i]);
        }
        return encodedValues;
    }
    
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return xssEncode(value);
    }
    
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return xssEncode(value);
    }
    
    
    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年6月23日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param value
     * @return
     */
    private static String xssEncode(String value) {
        return value;
        /*if (value == null || value.equals("")) {
            return value;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
            case '>':
                sb.append("＞");
                break;
            case '<':
                sb.append("＜");
                break;
            case '\'':
                sb.append("＇");
                break;
            case '`':
            	sb.append("｀");
            	break;
            case '\"':
                sb.append("＂");
                break;
            case '&':
                sb.append('＆');
                break;
            case '\\':
                sb.append('＼');
                break;
            case '#':
                sb.append('＃');
                break;
            case ':':
                sb.append('：');
                break;
            case '=':
            	sb.append("＝");
            	break;
            case '%':
                sb.append("％");
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();*/
    }
    
}
