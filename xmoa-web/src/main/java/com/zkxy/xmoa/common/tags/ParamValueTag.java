package com.zkxy.xmoa.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/***
 * 参数配置标签
 * @author xiaopu
 *
 */
public class ParamValueTag  extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2435865545973041211L;
	
	private String code;//参数类型

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Override
    public int doStartTag() throws JspException {
/*        JspWriter out = pageContext.getOut();
        try {
        	if(code!=null && !"".equals(code)){
        		String result= ParamCacheUtil.getValueDataByKey(code);

            	out.print(result);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
        // doStartTag() 方法返回 SKIP_BODY 。当然其原因是我们的简单日期标记没有正文。*/
        return Tag.SKIP_BODY;
    }
}
