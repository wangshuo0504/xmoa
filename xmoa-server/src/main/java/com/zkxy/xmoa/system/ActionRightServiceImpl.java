package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.common.dao.ActionRightMapper;
import com.zkxy.xmoa.system.model.ActionRight;
import com.zkxy.xmoa.util.StringUtil;
import com.zkxy.xmoa.util.UUIDGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class ActionRightServiceImpl implements IActionRightService {

    @Resource
    private ActionRightMapper actionRightMapper;

    @Override
    public ActionRight selectActionRightById(String fid) {
        return actionRightMapper.selectByPrimaryKey(fid);
    }

    @Override
    public List<Map<String, Object>> selectAllActionRight() {
        return actionRightMapper.selectAllActionRight();
    }

    @Override
    public ResponseJson addActionRight(ActionRight actionRight) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkActionRightNameUnique(actionRight)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源名已存在!");
            return responseJson;
        }
        if (!checkActionRightCodeUnique(actionRight)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源编码已存在!");
            return responseJson;
        }
        actionRight.setFid(UUIDGenerator.getUUID());
        actionRightMapper.insert(actionRight);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson deleteActionRight(String fids) {
        ResponseJson responseJson = new ResponseJson();
        if (StringUtil.isEmpty(fids)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源id为空!");
            return responseJson;
        }

        String[] fidList = fids.split(",");
        for (String fid : fidList) {
            if (actionRightMapper.selectCountByParentId(fid) > 0) {
                responseJson.setCode("0");
                responseJson.setMsg("资源拥有子级资源,请先删除子级资源!");
                return responseJson;
            }
        }



        for (String fid : fidList) {
            actionRightMapper.deleteByPrimaryKey(fid);
        }
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson updateActionRightById(ActionRight actionRight) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkActionRightNameUnique(actionRight)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源名已存在!");
            return responseJson;
        }
        if (!checkActionRightCodeUnique(actionRight)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源编码已存在!");
            return responseJson;
        }
        actionRightMapper.updateByPrimaryKeySelective(actionRight);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public PageList<Map<String, Object>> selectActionRightByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));
        String moduleId = (String) params.get("moduleId");
        PageList<Map<String, Object>> actionRights;
        if (StringUtil.isNotEmpty(moduleId))
            actionRights = (PageList<Map<String, Object>>) actionRightMapper.selectActionRightByModuleId(params, pageBounds);
        else
            actionRights = (PageList<Map<String, Object>>) actionRightMapper.selectActionRightWithOutModuleId(params, pageBounds);
        return actionRights;
    }

    @Override
    public boolean checkActionRightNameUnique(ActionRight actionRight) {
        if (actionRightMapper.selectCountByName(actionRight) > 0)
            return false;
        else
            return true;
    }


    @Override
    public boolean checkActionRightCodeUnique(ActionRight actionRight) {
        if (actionRightMapper.selectCountByCode(actionRight) > 0)
            return false;
        else
            return true;
    }

    @Override
    public Object selectActionRightTree() {
        List<Map<String, Object>> mapList = actionRightMapper.selectAllActionRight();
        Map<String, Set<String>> parent_child = new HashedMap();
        for (Map<String, Object> actionRight : mapList) {
            Set<String> childSet;
            if (parent_child.get(actionRight.get("PARENT_ID")) == null) {
                childSet = new HashSet<String>();
                parent_child.put((String) actionRight.get("PARENT_ID"), childSet);
            } else {
                childSet = parent_child.get(actionRight.get("PARENT_ID"));
            }
            childSet.add((String) actionRight.get("FID"));
        }
        return parent_child;
    }

    @Override
    public List<ActionRight> selectByModuleId(String moduleId) {
        Map<String, Object> params = new HashedMap();
        params.put("moduleId", moduleId);
        return actionRightMapper.selectByModuleId(params);
    }

}
