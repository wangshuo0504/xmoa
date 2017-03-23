package com.zkxy.xmoa.common.tags;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @Title 
 * @Description 获取字典项的值
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class DictDataCNameTag extends TagSupport {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1243354156515675049L;

    private String type;//类型
    
    private String code;//字典code

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
       /* try {
        	if(code!=null && !"".equals(code)){
        		String[] code_str = code.split(",");
            	String result = "";
            	int count = 0;
            	for(String str : code_str){
            		DictData data = DictCacheUtil.getDictDataByTypeAndCode(type,str);
                    if(data!=null){
                        //输出字典的中文。
                    	if(count>0){
                    		result += ",";
                    	}
                    	result += data.getCname();
                    }
                    count++;
            	}
            	out.print(result);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // doStartTag() 方法返回 SKIP_BODY 。当然其原因是我们的简单日期标记没有正文。
        return Tag.SKIP_BODY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
