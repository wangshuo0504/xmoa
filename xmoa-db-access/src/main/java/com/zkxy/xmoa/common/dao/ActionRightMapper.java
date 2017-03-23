package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.ActionRight;

import java.util.List;
import java.util.Map;

public interface ActionRightMapper {
    int deleteByPrimaryKey(String fid);

    int insert(ActionRight record);

    int insertSelective(ActionRight record);

    ActionRight selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(ActionRight record);

    int updateByPrimaryKey(ActionRight record);

    /**  ----------------update Method------------------    **/

    List<Map<String,Object>> selectAllActionRight();

    List<Map<String,Object>>  selectActionRightByModuleId(Map<String,Object> params, PageBounds pageBounds);

    List<Map<String,Object>>  selectActionRightWithOutModuleId(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByName(ActionRight record);

    int selectCountByCode(ActionRight record);

    int selectCountByParentId(String fid);

    List<ActionRight> selectByModuleId(Map<String,Object> params);

}