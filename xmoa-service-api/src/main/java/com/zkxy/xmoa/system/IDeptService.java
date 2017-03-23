package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.Dept;

import java.util.List;
import java.util.Map;


public interface IDeptService {

    Dept selectDeptById(String fid);

    List<Map<String,Object>> selectAllDept();

    ResponseJson addDept(Dept dept);

    ResponseJson deleteDept(String fids);

    ResponseJson  updateDeptById(Dept dept);

    PageList<Map<String,Object>> selectDeptByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr);

    boolean  checkDeptNameUnique(Dept dept);

    boolean  checkDeptCodeUnique(Dept dept);

    Object selectDeptTree();

    List<Dept> selectByParentId(String parentId);
}
