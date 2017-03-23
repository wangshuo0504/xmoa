package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.dao.BusinessRoleMapper;
import com.zkxy.xmoa.system.model.BusinessRole;
import com.zkxy.xmoa.util.StringUtil;
import com.zkxy.xmoa.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class BusinessRoleServiceImpl implements IBusinessRoleService {

    @Resource
    private BusinessRoleMapper businessRoleMapper;

    @Override
    public BusinessRole selectBusinessRoleById(String fid) {
        return businessRoleMapper.selectByPrimaryKey(fid);
    }

    @Override
    public List<Map<String, Object>> selectAllBusinessRole() {
        return businessRoleMapper.selectAllBusinessRole();
    }

    @Override
    public ResponseJson addBusinessRole(BusinessRole businessRole) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkBusinessRoleNameUnique(businessRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("业务角色名已存在!");
            return responseJson;
        }
        if (!checkBusinessRoleCodeUnique(businessRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("业务角色编码已存在!");
            return responseJson;
        }
        businessRole.setFid(UUIDGenerator.getUUID());
        businessRoleMapper.insert(businessRole);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson deleteBusinessRole(String fids) {
        ResponseJson responseJson = new ResponseJson();
        if (StringUtil.isEmpty(fids)) {
            responseJson.setCode("0");
            responseJson.setMsg("业务角色id为空!");
            return responseJson;
        }

        String[] fidList = fids.split(",");

        for (String fid : fidList) {
            businessRoleMapper.deleteByPrimaryKey(fid);
        }
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson updateBusinessRoleById(BusinessRole businessRole) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkBusinessRoleNameUnique(businessRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("业务角色名已存在!");
            return responseJson;
        }
        if (!checkBusinessRoleCodeUnique(businessRole)) {
            responseJson.setCode("0");
            responseJson.setMsg("业务角色编码已存在!");
            return responseJson;
        }
        businessRoleMapper.updateByPrimaryKeySelective(businessRole);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public PageList<Map<String, Object>> selectBusinessRoleByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));

        PageList<Map<String, Object>> businessRoles = (PageList<Map<String, Object>>) businessRoleMapper.selectBusinessRoleByParam(params, pageBounds);
        return businessRoles;
    }

    @Override
    public boolean checkBusinessRoleNameUnique(BusinessRole businessRole) {
        if (businessRoleMapper.selectCountByName(businessRole) > 0)
            return false;
        else
            return true;
    }


    @Override
    public boolean checkBusinessRoleCodeUnique(BusinessRole businessRole) {
        if (businessRoleMapper.selectCountByCode(businessRole) > 0)
            return false;
        else
            return true;
    }



}
