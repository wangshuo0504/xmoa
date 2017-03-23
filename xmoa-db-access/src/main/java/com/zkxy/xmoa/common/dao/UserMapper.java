package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.Dept;
import com.zkxy.xmoa.system.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int deleteByPrimaryKey(String fid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    /**  ----------------update Method------------------    **/

    List<Map<String,Object>> selectAllUser();

    List<Map<String,Object>> selectUserByDeptId(Map<String,Object> params, PageBounds pageBounds);

    List<Map<String,Object>> selectUserWithOutDeptId(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByUserName(User record);

    int selectCountByPhone(User record);

    int selectCountByDeptId(String fid);

    List<User> selectByDeptId(Map<String,Object> params);

    int updateSortNoByPrimaryKey(User record);
}