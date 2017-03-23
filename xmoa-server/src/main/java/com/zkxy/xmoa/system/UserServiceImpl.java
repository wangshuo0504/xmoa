package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.dao.UserMapper;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.constant.CommonConstant;
import com.zkxy.xmoa.system.model.DictData;
import com.zkxy.xmoa.system.model.User;
import com.zkxy.xmoa.util.DictCacheUtil;
import com.zkxy.xmoa.util.StringUtil;
import com.zkxy.xmoa.util.UUIDGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectUserById(String fid) {
        return userMapper.selectByPrimaryKey(fid);
    }

    @Override
    public List<Map<String, Object>> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public ResponseJson addUser(User user) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkUserNameUnique(user)) {
            responseJson.setCode("0");
            responseJson.setMsg("用户名已存在!");
            return responseJson;
        }
        if (!checkUserPhoneUnique(user)) {
            responseJson.setCode("0");
            responseJson.setMsg("用户手机已存在!");
            return responseJson;
        }
        user.setFid(UUIDGenerator.getUUID());
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userMapper.insert(user);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson deleteUser(String fids) {
        ResponseJson responseJson = new ResponseJson();
        if (StringUtil.isEmpty(fids)) {
            responseJson.setCode("0");
            responseJson.setMsg("用户id为空!");
            return responseJson;
        }
        String[] fidList = fids.split(",");
        for (String fid : fidList) {
            userMapper.deleteByPrimaryKey(fid);
        }
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson updateUserById(User user) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkUserNameUnique(user)) {
            responseJson.setCode("0");
            responseJson.setMsg("用户名已存在!");
            return responseJson;
        }
        if (!checkUserPhoneUnique(user)) {
            responseJson.setCode("0");
            responseJson.setMsg("用户手机已存在!");
            return responseJson;
        }
        userMapper.updateByPrimaryKeySelective(user);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public PageList<Map<String, Object>> selectUserByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));
        String deptId = (String) params.get("deptId");
        PageList<Map<String, Object>> users;
        if (StringUtil.isNotEmpty(deptId))
            users = (PageList<Map<String, Object>>) userMapper.selectUserByDeptId(params, pageBounds);
        else
            users = (PageList<Map<String, Object>>) userMapper.selectUserWithOutDeptId(params, pageBounds);

        List<DictData> dictDatas = DictCacheUtil.getDictDatasByType(CommonConstant.USER_STATUS_TYPE);
        //    翻译用户状态
        for (Map<String, Object> user : users) {
            for (DictData dictData : dictDatas) {
                if (dictData.getCode().equals(user.get("STATUS"))) {
                    user.put("STATUS_NAME", dictData.getName());
                    break;
                }
            }
        }
        return users;
    }

    @Override
    public boolean checkUserNameUnique(User user) {
        if (userMapper.selectCountByUserName(user) > 0)
            return false;
        else
            return true;
    }


    @Override
    public boolean checkUserPhoneUnique(User user) {
        if (userMapper.selectCountByPhone(user) > 0)
            return false;
        else
            return true;
    }

    @Override
    public Object selectUserTree() {
        List<Map<String, Object>> mapList = userMapper.selectAllUser();
        Map<String, Set<String>> parent_child = new HashedMap();

        return null;
    }

    @Override
    public List<User> selectByDeptId(String deptId) {
        Map<String, Object> params = new HashedMap();
        params.put("deptId", deptId);
        return userMapper.selectByDeptId(params);
    }

}
