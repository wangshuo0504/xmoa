package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.SystemRole;

import java.util.List;
import java.util.Map;


public interface ISystemRoleService {

    SystemRole selectSystemRoleById(String fid);

    List<Map<String,Object>> selectAllSystemRole();

    ResponseJson addSystemRole(SystemRole systemRole);

    ResponseJson deleteSystemRole(String fids);

    ResponseJson  updateSystemRoleById(SystemRole systemRole);

    PageList<Map<String,Object>> selectSystemRoleByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr);

    boolean  checkSystemRoleNameUnique(SystemRole systemRole);

    boolean  checkSystemRoleCodeUnique(SystemRole systemRole);


}
