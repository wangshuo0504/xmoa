package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.dao.ModuleMapper;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.Module;
import com.zkxy.xmoa.util.StringUtil;
import com.zkxy.xmoa.util.UUIDGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class ModuleServiceImpl implements IModuleService {

    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public Module selectModuleById(String fid) {
        return moduleMapper.selectByPrimaryKey(fid);
    }

    @Override
    public List<Map<String, Object>> selectAllModule() {
        return moduleMapper.selectAllModule();
    }

    @Override
    public ResponseJson addModule(Module module) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkModuleNameUnique(module)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源名已存在!");
            return responseJson;
        }
        if (!checkModuleCodeUnique(module)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源编码已存在!");
            return responseJson;
        }
        module.setFid(UUIDGenerator.getUUID());
        moduleMapper.insert(module);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson deleteModule(String fids) {
        ResponseJson responseJson = new ResponseJson();
        if (StringUtil.isEmpty(fids)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源id为空!");
            return responseJson;
        }

        String[] fidList = fids.split(",");
        for (String fid : fidList) {
            if (moduleMapper.selectCountByParentId(fid) > 0) {
                responseJson.setCode("0");
                responseJson.setMsg("资源拥有子级资源,请先删除子级资源!");
                return responseJson;
            }
        }

//        //暂时屏蔽
//        if (true) {
//            responseJson.setCode("0");
//            responseJson.setMsg("删除已被后台暂时屏蔽,请联系管理员!");
//            return responseJson;
//        }


        for (String fid : fidList) {
            moduleMapper.deleteByPrimaryKey(fid);
        }
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson updateModuleById(Module module) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkModuleNameUnique(module)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源名已存在!");
            return responseJson;
        }
        if (!checkModuleCodeUnique(module)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源编码已存在!");
            return responseJson;
        }
        moduleMapper.updateByPrimaryKeySelective(module);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public PageList<Map<String, Object>> selectModuleByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));
        String parentId = (String) params.get("parentId");
        PageList<Map<String, Object>> modules;
        if (StringUtil.isNotEmpty(parentId))
            modules = (PageList<Map<String, Object>>) moduleMapper.selectModuleByParentId(params, pageBounds);
        else
            modules = (PageList<Map<String, Object>>) moduleMapper.selectModuleWithOutParentId(params, pageBounds);
        return modules;
    }

    @Override
    public boolean checkModuleNameUnique(Module module) {
        if (moduleMapper.selectCountByName(module) > 0)
            return false;
        else
            return true;
    }


    @Override
    public boolean checkModuleCodeUnique(Module module) {
        if (moduleMapper.selectCountByCode(module) > 0)
            return false;
        else
            return true;
    }

    @Override
    public Object selectModuleTree() {
        List<Map<String, Object>> mapList = moduleMapper.selectAllModule();
        Map<String, Set<String>> parent_child = new HashedMap();
        for (Map<String, Object> module : mapList) {
            Set<String> childSet;
            if (parent_child.get(module.get("PARENT_ID")) == null) {
                childSet = new HashSet<String>();
                parent_child.put((String) module.get("PARENT_ID"), childSet);
            } else {
                childSet = parent_child.get(module.get("PARENT_ID"));
            }
            childSet.add((String) module.get("FID"));
        }
        return parent_child;
    }

    @Override
    public List<Module> selectByParentId(String parentId) {
        Map<String, Object> params = new HashedMap();
        params.put("parentId", parentId);
        return moduleMapper.selectByParentId(params);
    }

}
