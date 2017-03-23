package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.ISortNoService;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.IDeptService;
import com.zkxy.xmoa.system.model.Dept;
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
@RequestMapping("dept")
public class DeptController extends BaseController {

    @Resource
    private IDeptService deptService;

    @Resource
    private ISortNoService sortNoService;

    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/deptManage";
    }

    @RequestMapping(value = "/toAddDept.do")
    public String toAddDept(HttpServletRequest request,ModelMap model,  @RequestParam(required = false,defaultValue = "",value = "deptParentId") String deptParentId) {
        model.addAttribute("deptParentId",deptParentId);
        model.addAttribute("editType", "'add'");
        return "xmoa/system/deptEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/addDept.do")
    public Object addDept(HttpServletRequest request, Dept dept) {

        return deptService.addDept(dept);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDept.do")
    public Object deleteDept(HttpServletRequest request,  @RequestParam(required = true) String fids) {

        return deptService.deleteDept(fids);
    }


    @RequestMapping(value = "/toUpdateDept.do")
    public String toUpdateDept(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        Dept dept= deptService.selectDeptById(fid);
        model.addAttribute("dept",dept);
        model.addAttribute("deptParentId",dept.getParentId());
        model.addAttribute("editType", "'update'");
        return "xmoa/system/deptEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateDept.do")
    public Object updateDept(HttpServletRequest request, Dept dept) {

        return deptService.updateDeptById(dept);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllDept.do")
    public Object getAllDept(HttpServletRequest request) {
        ResponseJson rj = new ResponseJson("1");
        rj.setData(deptService.selectAllDept());
        return rj;
    }

    @ResponseBody
    @RequestMapping(value = "/queryDept.do")
    public Object queryDept(HttpServletRequest request,
                            @RequestParam(required = false,value = "queryStr") String queryStr,
                            @RequestParam(required = false,value = "parentId") String parentId,
                            @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                            @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                            @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);
        param.put("parentId",parentId);
        PageList<Map<String,Object>> mapPageList=deptService.selectDeptByParam(param,pageNum,pageSize,"CODE.ASC");
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkDeptNameUnique.do")
    public Object checkDeptNameUnique(HttpServletRequest request, Dept dept) {
        Map<String,Object> result=new HashedMap();
        if (deptService.checkDeptNameUnique(dept))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkDeptCodeUnique.do")
    public Object checkDeptCodeUnique(HttpServletRequest request, Dept dept) {
        Map<String,Object> result=new HashedMap();
        if (deptService.checkDeptCodeUnique(dept))
            result.put("valid",true);
        return result;
    }


    @RequestMapping(value = "/toSortDept.do")
    public String toSortDept(HttpServletRequest request, ModelMap model, @RequestParam(required = false,defaultValue = "",value = "deptParentId") String deptParentId) {
        model.addAttribute("deptList",deptService.selectByParentId(deptParentId)) ;
        return "xmoa/system/deptSort";
    }

    @ResponseBody
    @RequestMapping(value = "/saveDeptOrder.do")
    public Object saveDeptOrder(HttpServletRequest request,  @RequestParam(required = true,value = "resultList") String resultList) {
        return  sortNoService.updateSortNo(resultList,"dept");
    }
}
