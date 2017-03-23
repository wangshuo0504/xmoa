package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.dao.SystemRoleMapper;
import com.zkxy.xmoa.system.model.SystemRole;
import com.zkxy.xmoa.util.StringUtil;
import com.zkxy.xmoa.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class SystemRoleServiceImpl implements ISystemRoleService {

    @Resource
    private SystemRoleMapper systemRoleMapper;

    @Override
    public SystemRole selectSystemRoleById(String fid) {
        return systemRoleMapper.selectByPrimaryKey(fid);
    }

    @Override
    public List<Map<String, Object>> selectAllSystemRole() {
        return systemRoleMapper.selectAllSystemRole();
    }

    @Override
    public ResponseJson addSystemRole(SystemRole systemRole) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkSystemRoleNameUnique(systemRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("系统角色名已存在!");
            return responseJson;
        }
        if (!checkSystemRoleCodeUnique(systemRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("系统角色编码已存在!");
            return responseJson;
        }
        systemRole.setFid(UUIDGenerator.getUUID());
        systemRoleMapper.insert(systemRole);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson deleteSystemRole(String fids) {
        ResponseJson responseJson = new ResponseJson();
        if (StringUtil.isEmpty(fids)) {
            responseJson.setCode("0");
            responseJson.setMsg("系统角色id为空!");
            return responseJson;
        }

        String[] fidList = fids.split(",");

        for (String fid : fidList) {
            systemRoleMapper.deleteByPrimaryKey(fid);
        }
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson updateSystemRoleById(SystemRole systemRole) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkSystemRoleNameUnique(systemRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("系统角色名已存在!");
            return responseJson;
        }
        if (!checkSystemRoleCodeUnique(systemRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("系统角色编码已存在!");
            return responseJson;
        }
        systemRoleMapper.updateByPrimaryKeySelective(systemRole);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public PageList<Map<String, Object>> selectSystemRoleByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));

        PageList<Map<String, Object>> systemRoles = (PageList<Map<String, Object>>) systemRoleMapper.selectSystemRoleByParam(params, pageBounds);
        return systemRoles;
    }

    @Override
    public boolean checkSystemRoleNameUnique(SystemRole systemRole) {
        if (systemRoleMapper.selectCountByName(systemRole) > 0)
            return false;
        else
            return true;
    }


    @Override
    public boolean checkSystemRoleCodeUnique(SystemRole systemRole) {
        if (systemRoleMapper.selectCountByCode(systemRole) > 0)
            return false;
        else
            return true;
    }



}
