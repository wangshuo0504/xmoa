package com.zkxy.xmoa.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * @Description 工具类
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class Tools {
    /**
     * <p><b>Description : <b/> 格式化卡号，前6后4，中间为* </p>
     * @param
     *       card  卡号
     * @return
     */
    public static String formatCard(String card) {
        StringBuffer s = new StringBuffer();
        String a = card.substring(0, 6);
        String b = card.substring(a.length(), card.length() - 4);
        for (char c : b.toCharArray()) {
            b = b.replace(c, '*');
        }
        String c = card.substring(card.length() - 4, card.length());
        return s.append(a).append(b).append(c).toString();
    }

    /**
     * <p><b>Description : <b/> 格式化手机号,前3后4，中间为* </p>
     * @param
     *        mobile 手机号码
     * @return
     */
    public static String formatMobile(String mobile) {
        StringBuilder s = new StringBuilder();
        String a = mobile.substring(0, mobile.length() - 8);
        String c = mobile.substring(mobile.length() - 4, mobile.length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++ ) {
            sb.append("*");
        }
        s.append(a).append(sb.toString()).append(c);
        return s.toString();
    }

    /**
     * <p><b>Description : <b/> 获取卡号后四位 </p>
     * @param
     *        card
     * @return
     */
    public static String getCardLastFour(String card) {
        if (card == null) return "";
        if (card.length() < 4) return card;
        int length = card.length();
        return card.substring(length - 4, length);
    }
    /**
     * <p><b>Description : <b/> 获取运行环境 </p>
     */
    public static String getRunEnv() {
        return  System.getProperty("spring.profiles.active", "local");
    }
    
	/**
	 * <p><b>Description : <b/> 判断是否是生产环境 </p>
	 */
    public static boolean isProductEnv() {
        String profile =  getRunEnv();
        return "prod".equals(profile);
    }

    private static Double parseDouble(Object o) {
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).doubleValue();
        } else if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof Long) {
            return ((Long) o).doubleValue();
        } else if (o instanceof String) {
            return new Double((String) o);
        } else if (o instanceof Integer) {
            return new Double((Integer) o);
        }
        return 0d;
    }

    public static String getPercent(Object o1, Object o2) {
        try {
            Double d1 = parseDouble(o1);
            Double d2 = parseDouble(o2);
            DecimalFormat df = new DecimalFormat("######0.00");
            if (d2 != 0d) {
                return df.format((d1 - d2) * 100 / d2) + "%";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-";
    }


}
