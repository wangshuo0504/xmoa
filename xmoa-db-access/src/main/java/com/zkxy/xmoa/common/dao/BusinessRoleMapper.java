package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.BusinessRole;

import java.util.List;
import java.util.Map;

public interface BusinessRoleMapper {
    int deleteByPrimaryKey(String fid);

    int insert(BusinessRole record);

    int insertSelective(BusinessRole record);

    BusinessRole selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(BusinessRole record);

    int updateByPrimaryKey(BusinessRole record);


    /**  ----------------update Method------------------    **/

    List<Map<String,Object>> selectAllBusinessRole();

    List<Map<String,Object>>  selectBusinessRoleByParam(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByName(BusinessRole record);

    int selectCountByCode(BusinessRole record);
}