package com.zkxy.xmoa.util;

/**
 * @Title
 * @Description
 *   计算公式 类
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class CalculatorUtils {

    /**
     * <p><b>Description : <b/>将sum分解成几个连续正整数之和，返回这几个连续整数</p>
     * @param
     *      sum 分解数
     */
    public static int[] integerFactorization(int sum) {
        final int oddPart = divideAll(sum, 2);
        final int evenPart = sum / oddPart * 2;
        final int k;    //k是项数减1
        final int n;    //n是最小的一个整数
        if (oddPart > evenPart) {
            k = evenPart - 1;
            n = (oddPart - k) / 2;
        } else {
            k = oddPart - 1;
            n = (evenPart - k) / 2;
        }
        int[] results = new int[k + 1];
        for (int i = 0; i <= k; i++) {
            results[i] = n + i;
        }
        return results;
    }

    /**
     *<p><b>Description : <b/>
     * <li>1. 如果 n 能被factor整除，则用n除以factor；如不能，则返回 n </li>
     * <li>2. 得到的结果再设为 n </li>
     * <li>3. 重复1</li>
     * <li>返回n 除了factor以外的因数</li>
     * </p>
     */
    public static int divideAll(int n, int factor) {
        if (n % factor == 0)
            return divideAll(n / factor, factor);
        else {
            return n;
        }
    }
}
