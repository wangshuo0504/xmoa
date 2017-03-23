package com.zkxy.xmoa.web.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.zkxy.xmoa.common.IDictDataService;
import com.zkxy.xmoa.common.ISortNoService;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.controller.BaseController;
import com.zkxy.xmoa.system.model.DictData;
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
@RequestMapping("dictData")
public class DictDataController extends BaseController {

    @Resource
    private IDictDataService dictDataService;



    @RequestMapping(value = "/toMain.do")
    public String toMain(HttpServletRequest request) {
        return "xmoa/system/dictDataManage";
    }

    @RequestMapping(value = "/toAddDictData.do")
    public String toAddDictData(HttpServletRequest request,ModelMap model,  @RequestParam(required = false,defaultValue = "",value = "dictDataParentId") String dictDataParentId) {
        model.addAttribute("dictDataParentId",dictDataParentId);
        model.addAttribute("editType", "'add'");
        return "xmoa/system/dictDataEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/addDictData.do")
    public Object addDictData(HttpServletRequest request, DictData dictData) {

        return dictDataService.addDictData(dictData);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDictData.do")
    public Object deleteDictData(HttpServletRequest request,  @RequestParam(required = true) String fids) {

        return dictDataService.deleteDictData(fids);
    }


    @RequestMapping(value = "/toUpdateDictData.do")
    public String toUpdateDictData(HttpServletRequest request, ModelMap model, @RequestParam(required = true) String fid) {
        DictData dictData= dictDataService.selectDictDataById(fid);
        model.addAttribute("dictData",dictData);
        model.addAttribute("dictDataParentId",dictData.getParentId());
        model.addAttribute("editType", "'update'");
        return "xmoa/system/dictDataEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/updateDictData.do")
    public Object updateDictData(HttpServletRequest request, DictData dictData) {

        return dictDataService.updateDictDataById(dictData);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllDictData.do")
    public Object getAllDictData(HttpServletRequest request) {
        ResponseJson rj = new ResponseJson("1");
        rj.setData(dictDataService.findAllDict());
        return rj;
    }

    @ResponseBody
    @RequestMapping(value = "/queryDictData.do")
    public Object queryDictData(HttpServletRequest request,
                                @RequestParam(required = false,value = "queryStr") String queryStr,
                                @RequestParam(required = false,value = "parentId") String parentId,
                                @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize,
                                @RequestParam(required = false,defaultValue = "1",value = "page") int pageNum,
                                @RequestParam(required = false) String orderString) {
        ResponseJson rj = new ResponseJson("1");
        Map<String,Object> param=new HashedMap();
        param.put("queryStr",queryStr);
        param.put("parentId",parentId);
        PageList<Map<String,Object>> mapPageList=dictDataService.selectDictDataByParam(param,pageNum,pageSize,"CODE.ASC");
        rj.setData(mapPageList);
        Paginator paginator=mapPageList.getPaginator();
        rj.setPage(pageNum);
        rj.setPageSize(pageSize);
        rj.setTotalNum(paginator.getTotalCount());
        return rj;

    }

    @ResponseBody
    @RequestMapping(value = "/checkDictDataNameUnique.do")
    public Object checkDictDataNameUnique(HttpServletRequest request, DictData dictData) {
        Map<String,Object> result=new HashedMap();
        if (dictDataService.checkDictDataNameUnique(dictData))
            result.put("valid",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkDictDataCodeUnique.do")
    public Object checkDictDataCodeUnique(HttpServletRequest request, DictData dictData) {
        Map<String,Object> result=new HashedMap();
        if (dictDataService.checkDictDataCodeUnique(dictData))
            result.put("valid",true);
        return result;
    }

}
