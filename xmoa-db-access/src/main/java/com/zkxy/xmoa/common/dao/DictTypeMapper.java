package com.zkxy.xmoa.common.dao;

import com.zkxy.xmoa.system.model.DictType;

import java.util.List;

public interface DictTypeMapper {
    int deleteByPrimaryKey(String fid);

    int insert(DictType record);

    int insertSelective(DictType record);

    DictType selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(DictType record);

    int updateByPrimaryKey(DictType record);

    List<DictType> queryAllDictType();
}