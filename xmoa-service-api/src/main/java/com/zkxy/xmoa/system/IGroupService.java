package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.Group;

import java.util.List;
import java.util.Map;


public interface IGroupService {

    Group selectGroupById(String fid);

    List<Map<String,Object>> selectAllGroup();

    ResponseJson addGroup(Group group);

    ResponseJson deleteGroup(String fids);

    ResponseJson  updateGroupById(Group group);

    PageList<Map<String,Object>> selectGroupByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr);

    boolean  checkGroupNameUnique(Group group);

    boolean  checkGroupCodeUnique(Group group);


}
