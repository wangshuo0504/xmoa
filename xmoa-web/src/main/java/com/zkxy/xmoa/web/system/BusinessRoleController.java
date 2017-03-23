package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.IBusinessRoleService;
import com.zkxy.xmoa.system.model.BusinessRole;
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
@RequestMapping("businessRole")
public class BusinessRoleController extends BaseController {

    @Resource
    private IBusinessRoleService businessRoleService;

    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/businessRoleManage";
    }

    @RequestMapping(value = "/toAddBusinessRole.do")
    public String toAddBusinessRole(HttpServletRequest request,ModelMap model) {
        model.addAttribute("editType", "'add'");
        return "xmoa/system/businessRoleEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/addBusinessRole.do")
    public Object addBusinessRole(HttpServletRequest request, BusinessRole businessRole) {

        return businessRoleService.addBusinessRole(businessRole);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBusinessRole.do")
    public Object deleteBusinessRole(HttpServletRequest request,  @RequestParam(required = true) String fids) {

        return businessRoleService.deleteBusinessRole(fids);
    }


    @RequestMapping(value = "/toUpdateBusinessRole.do")
    public String toUpdateBusinessRole(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        BusinessRole businessRole= businessRoleService.selectBusinessRoleById(fid);
        model.addAttribute("businessRole",businessRole);
        model.addAttribute("editType", "'update'");
        return "xmoa/system/businessRoleEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateBusinessRole.do")
    public Object updateBusinessRole(HttpServletRequest request, BusinessRole businessRole) {

        return businessRoleService.updateBusinessRoleById(businessRole);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllBusinessRole.do")
    public Object getAllBusinessRole(HttpServletRequest request) {
        ResponseJson rj = new ResponseJson("1");
        rj.setData(businessRoleService.selectAllBusinessRole());
        return rj;
    }

    @ResponseBody
    @RequestMapping(value = "/queryBusinessRole.do")
    public Object queryBusinessRole(HttpServletRequest request,
                                    @RequestParam(required = false,value = "queryStr") String queryStr,
                                    @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                                    @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                                    @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);

        PageList<Map<String,Object>> mapPageList=businessRoleService.selectBusinessRoleByParam(param,pageNum,pageSize,"CODE.ASC");
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkBusinessRoleNameUnique.do")
    public Object checkBusinessRoleNameUnique(HttpServletRequest request, BusinessRole businessRole) {
        Map<String,Object> result=new HashedMap();
        if (businessRoleService.checkBusinessRoleNameUnique(businessRole))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkBusinessRoleCodeUnique.do")
    public Object checkBusinessRoleCodeUnique(HttpServletRequest request, BusinessRole businessRole) {
        Map<String,Object> result=new HashedMap();
        if (businessRoleService.checkBusinessRoleCodeUnique(businessRole))
            result.put("valid",true);
        return result;
    }


}
