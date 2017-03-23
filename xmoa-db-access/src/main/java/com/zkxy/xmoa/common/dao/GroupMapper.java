package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.Group;

import java.util.List;
import java.util.Map;

public interface GroupMapper {
    int deleteByPrimaryKey(String fid);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    /**  ----------------update Method------------------    **/

    List<Map<String,Object>> selectAllGroup();

    List<Map<String,Object>>  selectGroupByParam(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByName(Group record);

    int selectCountByCode(Group record);

}