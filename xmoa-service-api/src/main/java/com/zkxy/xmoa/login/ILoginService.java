package com.zkxy.xmoa.login;


import com.zkxy.xmoa.system.model.User;

public interface ILoginService {

    /**
     * <li>验证用户名密码</li>
     *
     * @return
     */
    boolean  validateUser(User paramUser);
}
