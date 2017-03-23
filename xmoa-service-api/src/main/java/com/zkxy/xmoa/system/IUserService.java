package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.User;

import java.util.List;
import java.util.Map;


public interface IUserService {

    User selectUserById(String fid);

    List<Map<String,Object>> selectAllUser();

    ResponseJson addUser(User user);

    ResponseJson deleteUser(String fids);

    ResponseJson  updateUserById(User user);

    PageList<Map<String,Object>> selectUserByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr);

    boolean  checkUserNameUnique(User user);

    boolean  checkUserPhoneUnique(User user);

    Object selectUserTree();

    List<User> selectByDeptId(String parentId);
}
