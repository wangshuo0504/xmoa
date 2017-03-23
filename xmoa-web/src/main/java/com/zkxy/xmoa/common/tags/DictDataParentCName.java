package com.zkxy.xmoa.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2015</p>
 * @Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author 贺尹红
 * @version 1.0
 * @修改记录：
 * @修改序号，修改日期，修改人，修改内容
 */
public class DictDataParentCName extends TagSupport {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private String type;//类型
    
    private String code;//字典code

    private String parentType;//父亲类型
    
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		/* try {
			    List<DictData> parentList = DictCacheUtil.getDictDatasByType(parentType);
	        	if(code!=null && !"".equals(code)){
	        		String[] code_str = code.split(",");
	            	String result = "";
	            	int count = 0;
	            	for(String str : code_str){
	            		DictData data = DictCacheUtil.getDictDataByTypeAndCode(type,str);
	            		for (DictData parentDictData : parentList) {
							if(parentDictData.getId().trim().equals(data.getParentId().trim())){
								if(count>0){
		                    		result += ",";
		                    	}
								result += parentDictData.getCname();
							}
						}
	            		count++;
	            	}
	            	out.print(result);
	        	}
	        } catch (IOException e) {
	            e.printStackTrace();
	        }*/
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

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
}

