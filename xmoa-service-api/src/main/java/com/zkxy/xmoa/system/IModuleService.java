package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.Module;

import java.util.List;
import java.util.Map;


public interface IModuleService {

    Module selectModuleById(String fid);

    List<Map<String,Object>> selectAllModule();

    ResponseJson addModule(Module module);

    ResponseJson deleteModule(String fids);

    ResponseJson  updateModuleById(Module module);

    PageList<Map<String,Object>> selectModuleByParam(Map<String,Object> params, int pageNum, int pageSize, String orderStr);

    boolean  checkModuleNameUnique(Module module);

    boolean  checkModuleCodeUnique(Module module);

    Object selectModuleTree();

    List<Module> selectByParentId(String parentId);
}
