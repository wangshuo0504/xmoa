package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.Module;

import java.util.List;
import java.util.Map;

public interface ModuleMapper {
    int deleteByPrimaryKey(String fid);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);

    /**  ----------------update Method------------------    **/

    List<Map<String,Object>> selectAllModule();

    List<Map<String,Object>>  selectModuleByParentId(Map<String,Object> params, PageBounds pageBounds);

    List<Map<String,Object>>  selectModuleWithOutParentId(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByName(Module record);

    int selectCountByCode(Module record);

    int selectCountByParentId(String fid);

    List<Module> selectByParentId(Map<String,Object> params);

    int updateSortNoByPrimaryKey(Module record);
}