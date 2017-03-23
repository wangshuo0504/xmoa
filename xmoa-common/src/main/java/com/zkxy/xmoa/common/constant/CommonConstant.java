package com.zkxy.xmoa.common.constant;


public class CommonConstant {

    public final static int PAGE_SIZE_DEFAULT = 10;

    /**
     * 是否有效:有效
     */
    public final static Integer IS_VALID = 1;

    /**
     * 是否有效:无效
     */
    public final static Integer IS_INVALID = 0;

    /**
     * 用户状态 正常
     */
    public final static String USER_STATUS_TYPE = "USER_STATUS";

    /**
     * 用户状态 正常
     */
    public final static String USER_STATUS_NORMAL = "NORMAL";

    /**
     * 用户状态 冻结
     */
    public final static String USER_STATUS_FREEZE = "FREEZE";

    /**
     * 用户状态：已作废
     */
    public final static String USER_STATUS_CANCEL = "CANCEL";

    //防止重复提交token
    public final static String AVOID_REPEAT_SUB_TOKEN = "avoid_repeat_sub_token";

    /**
     * 当前登录用户session key
     */
    public final static String SESSION_USER_KEY = "login_user";
}
