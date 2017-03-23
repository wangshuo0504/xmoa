package com.zkxy.xmoa.common.dao;

import com.zkxy.xmoa.system.model.Log;

public interface LogMapper {
    int deleteByPrimaryKey(String fid);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}