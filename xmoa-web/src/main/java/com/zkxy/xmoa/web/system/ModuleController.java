package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.ISortNoService;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.IModuleService;
import com.zkxy.xmoa.system.model.Module;
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
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private IModuleService moduleService;

    @Resource
    private ISortNoService sortNoService;

    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/moduleManage";
    }

    @RequestMapping(value = "/toAddModule.do")
    public String toAddModule(HttpServletRequest request,ModelMap model,  @RequestParam(required = false,defaultValue = "",value = "moduleParentId") String moduleParentId) {
        model.addAttribute("moduleParentId",moduleParentId);
        model.addAttribute("editType", "'add'");
        return "xmoa/system/moduleEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/addModule.do")
    public Object addModule(HttpServletRequest request, Module module) {

        return moduleService.addModule(module);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteModule.do")
    public Object deleteModule(HttpServletRequest request,  @RequestParam(required = true) String fids) {

        return moduleService.deleteModule(fids);
    }


    @RequestMapping(value = "/toUpdateModule.do")
    public String toUpdateModule(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        Module module= moduleService.selectModuleById(fid);
        model.addAttribute("module",module);
        model.addAttribute("moduleParentId",module.getParentId());
        model.addAttribute("editType", "'update'");
        return "xmoa/system/moduleEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateModule.do")
    public Object updateModule(HttpServletRequest request, Module module) {

        return moduleService.updateModuleById(module);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllModule.do")
    public Object getAllModule(HttpServletRequest request) {
        ResponseJson rj = new ResponseJson("1");
        rj.setData(moduleService.selectAllModule());
        return rj;
    }

    @ResponseBody
    @RequestMapping(value = "/queryModule.do")
    public Object queryModule(HttpServletRequest request,
                              @RequestParam(required = false,value = "queryStr") String queryStr,
                              @RequestParam(required = false,value = "parentId") String parentId,
                              @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                              @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                              @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);
        param.put("parentId",parentId);
        PageList<Map<String,Object>> mapPageList=moduleService.selectModuleByParam(param,pageNum,pageSize,"CODE.ASC");
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkModuleNameUnique.do")
    public Object checkModuleNameUnique(HttpServletRequest request, Module module) {
        Map<String,Object> result=new HashedMap();
        if (moduleService.checkModuleNameUnique(module))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkModuleCodeUnique.do")
    public Object checkModuleCodeUnique(HttpServletRequest request, Module module) {
        Map<String,Object> result=new HashedMap();
        if (moduleService.checkModuleCodeUnique(module))
            result.put("valid",true);
        return result;
    }


    @RequestMapping(value = "/toSortModule.do")
    public String toSortModule(HttpServletRequest request, ModelMap model, @RequestParam(required = false,defaultValue = "",value = "moduleParentId") String moduleParentId) {
        model.addAttribute("moduleList",moduleService.selectByParentId(moduleParentId)) ;
        return "xmoa/system/moduleSort";
    }

    @ResponseBody
    @RequestMapping(value = "/saveModuleOrder.do")
    public Object saveModuleOrder(HttpServletRequest request,  @RequestParam(required = true,value = "resultList") String resultList) {
        return  sortNoService.updateSortNo(resultList,"module");
    }
}
