package com.zkxy.xmoa.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zkxy.xmoa.system.model.DictData;
import com.zkxy.xmoa.system.model.Module;

import java.util.List;
import java.util.Map;

public interface DictDataMapper {
    int deleteByPrimaryKey(String fid);

    int insert(DictData record);

    int insertSelective(DictData record);

    DictData selectByPrimaryKey(String fid);

    int updateByPrimaryKeySelective(DictData record);

    int updateByPrimaryKey(DictData record);




    /** -----------------------------------------
     * 注释以上为自动生成 替换请注意
     * -----------------------------------------*/
    List<DictData> queryDictDataByType(String type);

    List<DictData> queryAllDictData();

    List<Map<String,Object>>  selectDictDataByParams(Map<String,Object> params, PageBounds pageBounds);

    int selectCountByName(DictData record);

    int selectCountByCode(DictData record);
}