package com.zkxy.xmoa.system;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.dao.DeptMapper;
import com.zkxy.xmoa.common.ResponseJson;
import com.zkxy.xmoa.system.model.Dept;
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
public class DeptServiceImpl implements IDeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    public Dept selectDeptById(String fid) {
        return deptMapper.selectByPrimaryKey(fid);
    }

    @Override
    public List<Map<String, Object>> selectAllDept() {
        return deptMapper.selectAllDept();
    }

    @Override
    public ResponseJson addDept(Dept dept) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkDeptNameUnique(dept)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源名已存在!");
            return responseJson;
        }
        if (!checkDeptCodeUnique(dept)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源编码已存在!");
            return responseJson;
        }
        dept.setFid(UUIDGenerator.getUUID());
        deptMapper.insert(dept);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson deleteDept(String fids) {
        ResponseJson responseJson = new ResponseJson();
        if (StringUtil.isEmpty(fids)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源id为空!");
            return responseJson;
        }

        String[] fidList = fids.split(",");

        for (String fid : fidList) {
            if( deptMapper.selectCountByParentId(fid)>0){
                responseJson.setCode("0");
                responseJson.setMsg("资源拥有子级资源,请先删除子级资源!");
                return responseJson;
            }
        }


        //暂时屏蔽
        if (true) {
            responseJson.setCode("0");
            responseJson.setMsg("删除已被后台暂时屏蔽,请联系管理员!");
            return responseJson;
        }


        for (String fid : fidList) {
            deptMapper.deleteByPrimaryKey(fid);
        }
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public ResponseJson updateDeptById(Dept dept) {
        ResponseJson responseJson = new ResponseJson();
        if (!checkDeptNameUnique(dept)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源名已存在!");
            return responseJson;
        }
        if (!checkDeptCodeUnique(dept)) {
            responseJson.setCode("0");
            responseJson.setMsg("资源编码已存在!");
            return responseJson;
        }
        deptMapper.updateByPrimaryKeySelective(dept);
        responseJson.setCode("1");
        return responseJson;
    }

    @Override
    public PageList<Map<String, Object>> selectDeptByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));

        String parentId = (String) params.get("parentId");
        PageList<Map<String, Object>> depts;
        if (StringUtil.isNotEmpty(parentId))
            depts = (PageList<Map<String, Object>>) deptMapper.selectDeptByParentId(params, pageBounds);
        else
            depts = (PageList<Map<String, Object>>) deptMapper.selectDeptWithOutParentId(params, pageBounds);
       return depts;
    }

    @Override
    public boolean checkDeptNameUnique(Dept dept) {
        if (deptMapper.selectCountByName(dept) > 0)
            return false;
        else
            return true;
    }


    @Override
    public boolean checkDeptCodeUnique(Dept dept) {
        if (deptMapper.selectCountByCode(dept) > 0)
            return false;
        else
            return true;
    }

    @Override
    public Object selectDeptTree() {
        List<Map<String, Object>> mapList = deptMapper.selectAllDept();
        Map<String, Set<String>> parent_child = new HashedMap();
        for (Map<String, Object> dept : mapList) {
            Set<String> childSet;
            if (parent_child.get(dept.get("PARENT_ID")) == null) {
                childSet = new HashSet<String>();
                parent_child.put((String) dept.get("PARENT_ID"), childSet);
            } else {
                childSet = parent_child.get(dept.get("PARENT_ID"));
            }
            childSet.add((String) dept.get("FID"));
        }
        return null;
    }

    @Override
    public List<Dept> selectByParentId(String parentId) {
        Map<String,Object> params=new HashedMap();
        params.put("parentId",parentId);
        return deptMapper.selectByParentId(params);
    }

}
