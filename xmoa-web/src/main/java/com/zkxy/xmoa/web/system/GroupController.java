package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.IGroupService;
import com.zkxy.xmoa.system.model.Group;
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
@RequestMapping("group")
public class GroupController extends BaseController {

    @Resource
    private IGroupService groupService;

    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/groupManage";
    }

    @RequestMapping(value = "/toAddGroup.do")
    public String toAddGroup(HttpServletRequest request,ModelMap model) {
        model.addAttribute("editType", "'add'");
        return "xmoa/system/groupEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/addGroup.do")
    public Object addGroup(HttpServletRequest request, Group group) {

        return groupService.addGroup(group);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteGroup.do")
    public Object deleteGroup(HttpServletRequest request,  @RequestParam(required = true) String fids) {

        return groupService.deleteGroup(fids);
    }


    @RequestMapping(value = "/toUpdateGroup.do")
    public String toUpdateGroup(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        Group group= groupService.selectGroupById(fid);
        model.addAttribute("group",group);
        model.addAttribute("editType", "'update'");
        return "xmoa/system/groupEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateGroup.do")
    public Object updateGroup(HttpServletRequest request, Group group) {

        return groupService.updateGroupById(group);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllGroup.do")
    public Object getAllGroup(HttpServletRequest request) {
        ResponseJson rj = new ResponseJson("1");
        rj.setData(groupService.selectAllGroup());
        return rj;
    }

    @ResponseBody
    @RequestMapping(value = "/queryGroup.do")
    public Object queryGroup(HttpServletRequest request,
                             @RequestParam(required = false,value = "queryStr") String queryStr,
                             @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                             @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                             @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);

        PageList<Map<String,Object>> mapPageList=groupService.selectGroupByParam(param,pageNum,pageSize,"CODE.ASC");
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkGroupNameUnique.do")
    public Object checkGroupNameUnique(HttpServletRequest request, Group group) {
        Map<String,Object> result=new HashedMap();
        if (groupService.checkGroupNameUnique(group))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkGroupCodeUnique.do")
    public Object checkGroupCodeUnique(HttpServletRequest request, Group group) {
        Map<String,Object> result=new HashedMap();
        if (groupService.checkGroupCodeUnique(group))
            result.put("valid",true);
        return result;
    }


}
