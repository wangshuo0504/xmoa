package com.zkxy.xmoa.common.constant;

/**
 * @Title
 *     用户在Session中的Key的值
 * @Description
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public enum UserCodeConstant {

    UserInfo("userInfo","session中用户信息的Key");

    private String code;
    private String name;

    UserCodeConstant(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return this.name;
    }
}
