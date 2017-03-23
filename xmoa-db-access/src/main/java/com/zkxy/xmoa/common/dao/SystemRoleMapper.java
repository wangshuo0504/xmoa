package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.SystemRole;

import java.util.List;
import java.util.Map;

public interface SystemRoleMapper {
    int deleteByPrimaryKey(String fid);

    int insert(SystemRole record);

    int insertSelective(SystemRole record);

    SystemRole selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(SystemRole record);

    int updateByPrimaryKey(SystemRole record);


    /**  ----------------update Method------------------    **/

    List<Map<String,Object>> selectAllSystemRole();

    List<Map<String,Object>>  selectSystemRoleByParam(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByName(SystemRole record);

    int selectCountByCode(SystemRole record);
}