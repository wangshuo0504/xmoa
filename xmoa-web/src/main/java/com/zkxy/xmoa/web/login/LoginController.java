package com.zkxy.xmoa.web.login;



import com.zkxy.xmoa.common.constant.UserCodeConstant;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.login.ILoginService;

import com.zkxy.xmoa.system.model.User;
import com.zkxy.xmoa.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("login")
public class LoginController extends BaseController{


    @Resource
    private ILoginService loginService;

    @RequestMapping(value = "/loginIndex.do")
    public String loginIndex(HttpServletRequest req) {
        return "xmoa/login";
    }


    @RequestMapping(value = "/login.do",method= RequestMethod.POST)
    public String login(HttpServletRequest req) {

        User paramUser = new User();
         paramUser.setUserName(req.getParameter("userName"));
         paramUser.setPassword(req.getParameter("password"));
       if( this.loginService.validateUser(paramUser) ){

           HttpSession  session = this.getSession();
           session.setAttribute(UserCodeConstant.UserInfo.getCode(),paramUser.getUserName());
           return "redirect:/module/toMain.do";
       }
        return "xmoa/login";
    }

    @RequestMapping(value = "/outLogin.do")
    public String outLogin( HttpServletRequest req) {
        this.getSession().removeAttribute(UserCodeConstant.UserInfo.getCode());
        return "redirect:/login/loginIndex.do";
    }

    @RequestMapping(value = "/myTest.do")
    public String myTest(HttpServletRequest req) {
        req.setAttribute("root","/xmoa");
        return "xmoa/myTest";
    }

    @ResponseBody
    @RequestMapping(value = "/loadTestData.do")
    public String loadTestData( HttpServletRequest request) {
        return JsonUtils.obj2json(null);
    }


}
