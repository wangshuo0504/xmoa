package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.ISystemRoleService;
import com.zkxy.xmoa.system.model.SystemRole;
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
@RequestMapping("systemRole")
public class SystemRoleController extends BaseController {

    @Resource
    private ISystemRoleService systemRoleService;

    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/systemRoleManage";
    }

    @RequestMapping(value = "/toAddSystemRole.do")
    public String toAddSystemRole(HttpServletRequest request,ModelMap model) {
        model.addAttribute("editType", "'add'");
        return "xmoa/system/systemRoleEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/addSystemRole.do")
    public Object addSystemRole(HttpServletRequest request, SystemRole systemRole) {

        return systemRoleService.addSystemRole(systemRole);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSystemRole.do")
    public Object deleteSystemRole(HttpServletRequest request,  @RequestParam(required = true) String fids) {

        return systemRoleService.deleteSystemRole(fids);
    }


    @RequestMapping(value = "/toUpdateSystemRole.do")
    public String toUpdateSystemRole(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        SystemRole systemRole= systemRoleService.selectSystemRoleById(fid);
        model.addAttribute("systemRole",systemRole);
        model.addAttribute("editType", "'update'");
        return "xmoa/system/systemRoleEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateSystemRole.do")
    public Object updateSystemRole(HttpServletRequest request, SystemRole systemRole) {

        return systemRoleService.updateSystemRoleById(systemRole);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllSystemRole.do")
    public Object getAllSystemRole(HttpServletRequest request) {
        ResponseJson rj = new ResponseJson("1");
        rj.setData(systemRoleService.selectAllSystemRole());
        return rj;
    }

    @ResponseBody
    @RequestMapping(value = "/querySystemRole.do")
    public Object querySystemRole(HttpServletRequest request,
                                  @RequestParam(required = false,value = "queryStr") String queryStr,
                                  @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                                  @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                                  @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);

        PageList<Map<String,Object>> mapPageList=systemRoleService.selectSystemRoleByParam(param,pageNum,pageSize,"CODE.ASC");
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkSystemRoleNameUnique.do")
    public Object checkSystemRoleNameUnique(HttpServletRequest request, SystemRole systemRole) {
        Map<String,Object> result=new HashedMap();
        if (systemRoleService.checkSystemRoleNameUnique(systemRole))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkSystemRoleCodeUnique.do")
    public Object checkSystemRoleCodeUnique(HttpServletRequest request, SystemRole systemRole) {
        Map<String,Object> result=new HashedMap();
        if (systemRoleService.checkSystemRoleCodeUnique(systemRole))
            result.put("valid",true);
        return result;
    }


}
