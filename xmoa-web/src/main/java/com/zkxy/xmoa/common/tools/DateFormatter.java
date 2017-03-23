/**
 * Created on 2015年11月5日 by ZhouMin
 */
package com.zkxy.xmoa.common.tools;



import com.zkxy.xmoa.util.StringUtil;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @Title
 * @Description
 *         spring 格式化转换器
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class DateFormatter implements Formatter<Date> {

    @Override
    public String print(Date object, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = null;
        if (object != null) {
            try {
                dateStr = format.format(object);
            } catch (Exception e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = format.format(object);
            }
        }
        return dateStr;
    }

    @Override
    public Date parse(String text, Locale locale)
        throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (StringUtil.isNotEmpty(text)) {
            try {
                date = format.parse(text);
            } catch (Exception e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(text);
            }
        }
        return date;
    }

}