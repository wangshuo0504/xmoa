package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.ISortNoService;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.IActionRightService;
import com.zkxy.xmoa.system.model.ActionRight;
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
@RequestMapping("actionRight")
public class ActionRightController extends BaseController {

    @Resource
    private IActionRightService actionRightService;

    @Resource
    private ISortNoService sortNoService;

    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/actionRightManage";
    }

    @RequestMapping(value = "/toAddActionRight.do")
    public String toAddActionRight(HttpServletRequest request,ModelMap model,  @RequestParam(required = false,defaultValue = "",value = "actionRightModuleId") String actionRightModuleId) {
        model.addAttribute("actionRightModuleId",actionRightModuleId);
        model.addAttribute("editType", "'add'");
        return "xmoa/system/actionRightEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/addActionRight.do")
    public Object addActionRight(HttpServletRequest request, ActionRight actionRight) {

        return actionRightService.addActionRight(actionRight);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteActionRight.do")
    public Object deleteActionRight(HttpServletRequest request,  @RequestParam(required = true) String fids) {

        return actionRightService.deleteActionRight(fids);
    }


    @RequestMapping(value = "/toUpdateActionRight.do")
    public String toUpdateActionRight(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        ActionRight actionRight= actionRightService.selectActionRightById(fid);
        model.addAttribute("actionRight",actionRight);
        model.addAttribute("actionRightModuleId",actionRight.getModuleId());
        model.addAttribute("editType", "'update'");
        return "xmoa/system/actionRightEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateActionRight.do")
    public Object updateActionRight(HttpServletRequest request, ActionRight actionRight) {

        return actionRightService.updateActionRightById(actionRight);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllActionRight.do")
    public Object getAllActionRight(HttpServletRequest request) {
        ResponseJson rj = new ResponseJson("1");
        rj.setData(actionRightService.selectAllActionRight());
        return rj;
    }

    @ResponseBody
    @RequestMapping(value = "/queryActionRight.do")
    public Object queryActionRight(HttpServletRequest request,
                                   @RequestParam(required = false,value = "queryStr") String queryStr,
                                   @RequestParam(required = false,value = "moduleId") String moduleId,
                                   @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                                   @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                                   @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);
        param.put("moduleId",moduleId);
        PageList<Map<String,Object>> mapPageList=actionRightService.selectActionRightByParam(param,pageNum,pageSize,"CODE.ASC");
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkActionRightNameUnique.do")
    public Object checkActionRightNameUnique(HttpServletRequest request, ActionRight actionRight) {
        Map<String,Object> result=new HashedMap();
        if (actionRightService.checkActionRightNameUnique(actionRight))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkActionRightCodeUnique.do")
    public Object checkActionRightCodeUnique(HttpServletRequest request, ActionRight actionRight) {
        Map<String,Object> result=new HashedMap();
        if (actionRightService.checkActionRightCodeUnique(actionRight))
            result.put("valid",true);
        return result;
    }



    @RequestMapping(value = "/toRoleMain.do")
    public String toRoleMain(HttpServletRequest request) {
        return "xmoa/system/actionRightManage";
    }

}
