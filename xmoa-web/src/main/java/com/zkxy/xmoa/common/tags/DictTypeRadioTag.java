package com.zkxy.xmoa.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @Title 
 * @Description 生成radio
 * @Copyright Copyright (c) 2009</p>
 * 
 * 
 * 
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class DictTypeRadioTag extends TagSupport {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7761990134644166879L;

    private String type;//类型
    
    private String checkedCode;//需要选中的字典code

    private String name;
    
    private String otherAttributes;
    
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
       /* try {
            List<DictData> dataList = DictCacheUtil.getDictDatasByType(type);
            if (dataList != null) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < dataList.size(); i++ ) {
                    DictData data = dataList.get(i);
                    sb.append("<input type=\"radio\"")
                    .append(" id=\"").append(name+i).append("\"")
                    .append(" name=\"").append(name).append("\"")
                    .append(" value=\"").append(data.getCode()).append("\"");
                    if(StringUtil.isNotEmpty(otherAttributes)){//添加其他属性
                        sb.append(" ").append(otherAttributes).append(" ");
                    }
                    if (data.getCode() != null && data.getCode().equals(checkedCode)) {
                        sb.append(" checked=\"checked\"");
                    }
                    sb.append("/>\n");
                    sb.append("<label");
                    sb.append(" class=\"icon\" for=\"").append(name+i).append("\"");
                    sb.append(">");
                    sb.append("</label>\n");
                    sb.append("<label");
                    sb.append(" class=\"text\" for=\"").append(name+i).append("\"");
                    sb.append(">");
                    sb.append(data.getCname());
                    sb.append("</label>\n");
                }
                // 输出字典的中文。
                out.print(sb.toString());
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

    public String getCheckedCode() {
        return checkedCode;
    }

    public void setCheckedCode(String checkedCode) {
        this.checkedCode = checkedCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(String otherAttributes) {
        this.otherAttributes = otherAttributes;
    }
    
}
