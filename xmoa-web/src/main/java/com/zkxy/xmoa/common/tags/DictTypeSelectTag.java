package com.zkxy.xmoa.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @Title 
 * @Description 生成select
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class DictTypeSelectTag extends TagSupport {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1243354156515675049L;

    private String type;//类型
    
    private String selectedCode;//需要选中的字典code

    private String name;
    
    private String withBlank = "true";
    
    private String multiple = "false";
    private String css = "";

    private String otherAttributes;

    private String placeholder;


    
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
      /*  try {
            List<DictData> dataList = DictCacheUtil.getDictDatasByType(type);
            if (dataList != null) {
                StringBuffer sb = new StringBuffer();
                // 构建select
                sb.append("<select id=").append("\"").append(name).append("\"").append(" name=").append(
                    "\"").append(name).append("\"");
                sb.append(" class=").append("\"").append(css).append("\" ");
                if ("true".equals(multiple)) {
                     sb.append(" multiple=").append("\"").append(multiple).append("\"");
                }
//                if ("true".equals(withBlank)) {
//                    sb.append(" data-placeholder=\"请选择\"");
//                }
                if(StringUtil.isNotEmpty(otherAttributes)){//添加其他属性
                    sb.append(" ").append(otherAttributes).append(" ");
                }
                sb.append(">\n");
                // 构建请选择
                if ("true".equals(withBlank)) {
                    if(placeholder!=null&&!placeholder.equals(""))
                        sb.append("<option value=\"\">"+placeholder+"</option>\n");
                    else
                    sb.append("<option value=\"\">请选择</option>\n");
                }
                for (int i = 0; i < dataList.size(); i++ ) {
                    DictData data = dataList.get(i);
                    sb.append("<option value=").append("\"").append(data.getCode()).append("\"");
                    if (data.getCode() != null && data.getCode().equals(selectedCode)) {
                        sb.append(" selected ");
                    }
                    sb.append(" >").append(data.getCname()).append("</option>\n");
                }
                sb.append("</select>\n");
                // 输出字典的中文。
                out.print(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // doStartTag() 方法返回 SKIP_BODY 。当然其原因是我们的简单日期标记没有正文。*/
        return Tag.SKIP_BODY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelectedCode() {
        return selectedCode;
    }

    public void setSelectedCode(String selectedCode) {
        this.selectedCode = selectedCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWithBlank() {
        return withBlank;
    }

    public void setWithBlank(String withBlank) {
        this.withBlank = withBlank;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(String otherAttributes) {
        this.otherAttributes = otherAttributes;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getPlaceholder() {return placeholder;}

    public void setPlaceholder(String placeholder) {this.placeholder = placeholder;}
}
