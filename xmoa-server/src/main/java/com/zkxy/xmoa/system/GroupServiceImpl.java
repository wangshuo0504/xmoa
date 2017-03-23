package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.dao.GroupMapper;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.Group;
import com.zkxy.xmoa.util.StringUtil;
import com.zkxy.xmoa.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class GroupServiceImpl implements IGroupService {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public Group selectGroupById(String fid) {
        return groupMapper.selectByPrimaryKey(fid);
    }

    @Override
    public List<Map<String, Object>> selectAllGroup() {
        return groupMapper.selectAllGroup();
    }

    @Override
    public ResponseJson addGroup(Group group) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkGroupNameUnique(group)) {
            responseJson.setCode("0");
            responseJson.setMsg("群组名已存在!");
            return responseJson;
        }
        if (!checkGroupCodeUnique(group)) {
            responseJson.setCode("0");
            responseJson.setMsg("群组编码已存在!");
            return responseJson;
        }
        group.setFid(UUIDGenerator.getUUID());
        groupMapper.insert(group);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson deleteGroup(String fids) {
        ResponseJson responseJson = new ResponseJson();
        if (StringUtil.isEmpty(fids)) {
            responseJson.setCode("0");
            responseJson.setMsg("群组id为空!");
            return responseJson;
        }

        String[] fidList = fids.split(",");

        for (String fid : fidList) {
            groupMapper.deleteByPrimaryKey(fid);
        }
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson updateGroupById(Group group) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkGroupNameUnique(group)) {
            responseJson.setCode("0");
            responseJson.setMsg("群组名已存在!");
            return responseJson;
        }
        if (!checkGroupCodeUnique(group)) {
            responseJson.setCode("0");
            responseJson.setMsg("群组编码已存在!");
            return responseJson;
        }
        groupMapper.updateByPrimaryKeySelective(group);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public PageList<Map<String, Object>> selectGroupByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));

        PageList<Map<String, Object>> groups = (PageList<Map<String, Object>>) groupMapper.selectGroupByParam(params, pageBounds);
        return groups;
    }

    @Override
    public boolean checkGroupNameUnique(Group group) {
        if (groupMapper.selectCountByName(group) > 0)
            return false;
        else
            return true;
    }


    @Override
    public boolean checkGroupCodeUnique(Group group) {
        if (groupMapper.selectCountByCode(group) > 0)
            return false;
        else
            return true;
    }



}
