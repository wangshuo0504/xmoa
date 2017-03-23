package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.ISortNoService;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.constant.CommonConstant;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.IUserService;
import com.zkxy.xmoa.system.model.User;
import com.zkxy.xmoa.util.DictCacheUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 *
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @Resource
    private ISortNoService sortNoService;

    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/userManage";
    }

    @RequestMapping(value = "/toAddUser.do")
    public String toAddUser(HttpServletRequest request,ModelMap model,  @RequestParam(required = false,defaultValue = "",value = "userDeptId") String userDeptId) {
        model.addAttribute("userDeptId",userDeptId);
        model.addAttribute("editType", "'add'");
        return "xmoa/system/userCreate";
    }

    @ResponseBody
    @RequestMapping(value = "/addUser.do")
    public Object addUser(HttpServletRequest request, User user) {

        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteUser.do")
    public Object deleteUser(HttpServletRequest request,  @RequestParam(required = true) String fids) {
        return userService.deleteUser(fids);
    }


    @RequestMapping(value = "/toUpdateUser.do")
    public String toUpdateUser(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        User user= userService.selectUserById(fid);
        model.addAttribute("user",user);

        model.addAttribute("user_status", DictCacheUtil.getDictDatasByType(CommonConstant.USER_STATUS_TYPE));
        model.addAttribute("userDeptId",user.getDeptId());
        model.addAttribute("editType", "'update'");
        return "xmoa/system/userEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateUser.do")
    public Object updateUser(HttpServletRequest request, User user) {

        return userService.updateUserById(user);
    }



    @ResponseBody
    @RequestMapping(value = "/queryUser.do")
    public Object queryUser(HttpServletRequest request,
                            @RequestParam(required = false,value = "queryStr") String queryStr,
                            @RequestParam(required = false,value = "deptId") String deptId,
                            @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                            @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                            @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);
        param.put("deptId",deptId);
        PageList<Map<String,Object>> mapPageList=userService.selectUserByParam(param,pageNum,pageSize,null);
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkUserNameUnique.do")
    public Object checkUserNameUnique(HttpServletRequest request, User user) {
        Map<String,Object> result=new HashedMap();
        if (userService.checkUserNameUnique(user))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkUserPhoneUnique.do")
    public Object checkUserCodeUnique(HttpServletRequest request, User user) {
        Map<String,Object> result=new HashedMap();
        if (userService.checkUserPhoneUnique(user))
            result.put("valid",true);
        return result;
    }


    @RequestMapping(value = "/toSortUser.do")
    public String toSortUser(HttpServletRequest request, ModelMap model, @RequestParam(required = true,value = "deptId") String deptId) {
        model.addAttribute("userList",userService.selectByDeptId(deptId)) ;
        return "xmoa/system/userSort";
    }


    @ResponseBody
    @RequestMapping(value = "/saveUserOrder.do")
    public Object saveUserOrder(HttpServletRequest request,  @RequestParam(required = true,value = "resultList") String resultList) {
        return  sortNoService.updateSortNo(resultList,"user");
    }
}
