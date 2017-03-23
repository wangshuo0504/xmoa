package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.ActionRight;

import java.util.List;
import java.util.Map;


public interface IActionRightService {

    ActionRight selectActionRightById(String fid);

    List<Map<String,Object>> selectAllActionRight();

    ResponseJson addActionRight(ActionRight actionRight);

    ResponseJson deleteActionRight(String fids);

    ResponseJson  updateActionRightById(ActionRight actionRight);

    PageList<Map<String,Object>> selectActionRightByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr);

    boolean  checkActionRightNameUnique(ActionRight actionRight);

    boolean  checkActionRightCodeUnique(ActionRight actionRight);

    Object selectActionRightTree();

    List<ActionRight> selectByModuleId(String parentId);
}
