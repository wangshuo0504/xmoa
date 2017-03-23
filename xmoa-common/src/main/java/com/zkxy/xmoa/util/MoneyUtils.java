package com.zkxy.xmoa.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyUtils {

	/**
	 * 元转为分 8.3 --> 830
	 * 
	 * @param money
	 * @return
	 */
	public static String Yuan2Fen(String money) {
		String result = "0";
		BigDecimal f = BigDecimal.ZERO;
		f = new BigDecimal(money).multiply(new BigDecimal(100));
		result = new DecimalFormat("0").format(f);
		return result;
	}

	/**
	 * 分转为元 830 --> 8.3
	 * 
	 * @param money
	 * @return
	 */
	public static String Fen2Yuan(String money) {
		String result = "0";
		BigDecimal f = BigDecimal.ZERO;
		f = new BigDecimal(money).divide(new BigDecimal(100));
		result = new DecimalFormat("0.00").format(f);
		return result;
	}
	
	public static String Fen2YuanNoDot(String money) {
		String result = null;
		try{
			BigDecimal f = BigDecimal.ZERO;
			f = new BigDecimal(money).divide(new BigDecimal(100));
			result = new DecimalFormat("0").format(f);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	
	/** 修改价格显示形式分到元的转换 */
    public static String formatAmt(Long price) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = "0.00";
        if (price != null) {
            str = df1.format(price.doubleValue() / 100);
        }
        return str;
    }

    /** 修改价格显示形式分到元的转换 */
    public static String formatAmt(Integer price) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = "0.00";
        if (price != null) {
            str = df1.format(price.doubleValue() / 100);
        }
        return str;
    }

    /** 修改价格显示形式分到元的转换 */
    public static String formatAmt(BigDecimal price) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = "0.00";
        if (price != null) {
            str = df1.format(price.doubleValue() / 100);
        }
        return str;
    }

    /** 修改价格显示形式分到元的转换 */
    public static String formatAmtForString(String price) {
        if (price == null || price.trim().equals("")) {
            return "";
        }

        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = "0.00";
        try {
            if (price != null) {
                str = df1.format(Long.valueOf(price).doubleValue() / 100);
            }
        } catch (Exception e) {
            return price;
        }

        return str;
    }

    /** 修改价格显示形式分到元的转换 */
    public static String formatAmtForLong(String price) {
        if (price == null || price.trim().equals("")) {
            return "";
        }
        //DecimalFormat df1 = new DecimalFormat("0.00");
        String str = "0";
        if (price != null) {
            str = (Long.valueOf(price) / 100) + "";
        }
        return str;
    }

    /**
     * 2012-3-27上午10:03:02
     * 
     * @Title: getAmount
     * @Description: 去掉金额前面的0
     * @param amount
     *            设定文件
     * @return void 返回类型
     * @throws
     */
    public static String getAmount(String amount) {
        if (null == amount || "".equals(amount)) {
            return "0";
        }
        if (!NumberUtil.isNumeric(amount)) {
            return "0";
        }
        Long amountl = Long.valueOf(amount);
        String amounts = String.valueOf(amountl);
        return amounts;
    }
    
    
    
    
    /**
     * 格式化输出金额
     * @param money
     * @param mark 输出金额是否含￥  true/false
     * @return
     */
    public static String formatMoney(String money,boolean mark){
    	 if (money == null || money.length() < 1) {  
             return "";  
         } 
    	 String unit_str = "元";
    	 String result= "";
         BigDecimal  bd = new BigDecimal(money);
         BigDecimal a=BigDecimal.valueOf(10000);
         if(bd.compareTo(a) == 1){
        	bd = bd.divide(a,4,BigDecimal.ROUND_HALF_EVEN);
        	unit_str = "万元";
         }
         if(mark){

             NumberFormat currency = NumberFormat.getCurrencyInstance();
             currency.setMaximumFractionDigits(2);
             result= currency.format(bd);
         }else{

             NumberFormat currency = NumberFormat.getInstance();
             currency.setMaximumFractionDigits(2);
             result = currency.format(bd);
         }
         
       
         return result+" "+unit_str;
   
    }
    
	public static void main(String[] args) {
		System.out.println(MoneyUtils.formatMoney("1000123163.001230",true));
	}
}
