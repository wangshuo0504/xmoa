package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.BusinessRole;

import java.util.List;
import java.util.Map;


public interface IBusinessRoleService {

    BusinessRole selectBusinessRoleById(String fid);

    List<Map<String,Object>> selectAllBusinessRole();

    ResponseJson addBusinessRole(BusinessRole businessRole);

    ResponseJson deleteBusinessRole(String fids);

    ResponseJson  updateBusinessRoleById(BusinessRole businessRole);

    PageList<Map<String,Object>> selectBusinessRoleByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr);

    boolean  checkBusinessRoleNameUnique(BusinessRole businessRole);

    boolean  checkBusinessRoleCodeUnique(BusinessRole businessRole);


}
