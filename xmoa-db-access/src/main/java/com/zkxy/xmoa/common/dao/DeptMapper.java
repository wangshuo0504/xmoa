package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.Dept;

import java.util.List;
import java.util.Map;

public interface DeptMapper {
    int deleteByPrimaryKey(String fid);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    /**  ----------------update Method------------------    **/

    List<Map<String,Object>> selectAllDept();

    List<Map<String,Object>>  selectDeptByParentId(Map<String,Object> params, PageBounds pageBounds);

    List<Map<String,Object>>  selectDeptWithOutParentId(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByName(Dept record);

    int selectCountByCode(Dept record);

    int selectCountByParentId(String fid);

    List<Dept> selectByParentId(Map<String,Object> params);

    int updateSortNoByPrimaryKey(Dept record);

}