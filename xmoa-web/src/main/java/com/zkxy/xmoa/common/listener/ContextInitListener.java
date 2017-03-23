package com.zkxy.xmoa.common.listener;



import com.zkxy.xmoa.common.IDictDataService;
import com.zkxy.xmoa.system.model.DictType;
import com.zkxy.xmoa.util.DictCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Iterator;
import java.util.List;


/**
 * 
 * @Title:
 * @Description:
 *  服务器启动的任务调度
 * @Copyright: Copyright (c) 2015</p>
 * @Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录：spring容器初始化监听器
 * @修改序号，修改日期，修改人，修改内容
 */
public class ContextInitListener implements ServletContextListener {
    private final static Logger logger = LoggerFactory.getLogger(ContextInitListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        logger.info("==========spring容器初始化信息进行销毁==========");
    }

    @Override
    public void contextInitialized(ServletContextEvent _e) {
        logger.info("==========spring容器初始化数据开始==========");
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(_e.getServletContext());
        this.putAllDictDataToCache(context);
    }



    private void putAllDictDataToCache(WebApplicationContext context) {
        long begin = System.currentTimeMillis();

        try {
            IDictDataService dictDataService = (IDictDataService)context.getBean("dictDataService");
            List<DictType> dictTypeList = dictDataService.findAllDictType();
            Iterator it = dictTypeList.iterator();

            while(it.hasNext()) {
                DictType dictType = (DictType)it.next();
                List dictDataList = dictDataService.findDictByType(dictType.getType());
                DictCacheUtil.putDictCaches(dictType.getType(), dictDataList);
    
            }

            logger.info("加载[数据字典]到缓存成功...数据量[{}]条，消耗时间[{}]毫秒", Integer.valueOf(dictTypeList.size()), Long.valueOf(System.currentTimeMillis() - begin));
        } catch (Exception var10) {
            logger.error("加载数据字典到缓存失败了，别着急我们会重试的！", var10);
        }

    }
}
