package com.zkxy.xmoa.common;


import com.zkxy.xmoa.common.dao.DeptMapper;
import com.zkxy.xmoa.common.dao.ModuleMapper;
import com.zkxy.xmoa.common.dao.UserMapper;
import com.zkxy.xmoa.system.model.Dept;
import com.zkxy.xmoa.system.model.Module;
import com.zkxy.xmoa.system.model.User;
import com.zkxy.xmoa.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SortNoServiceImpl implements ISortNoService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public ResponseJson updateSortNo(String resultList, String type) {
        ResponseJson responseJson = new ResponseJson();
        responseJson.setCode("1");
        if ("user".equals(type)) {
            if (StringUtil.isNotEmpty(resultList)) {
                String[] sortNos = resultList.split(",");
                for (String s : sortNos) {
                    String[] strings = s.split(":");
                    User user = new User();
                    user.setSortNo(formatSortNo(strings[0]));
                    user.setFid(strings[1]);
                    userMapper.updateSortNoByPrimaryKey(user);
                }
            }

        } else if ("dept".equals(type)) {
            if (StringUtil.isNotEmpty(resultList)) {
                String[] sortNos = resultList.split(",");
                for (String s : sortNos) {
                    String[] strings = s.split(":");
                    Dept dept = new Dept();
                    dept.setSortNo(formatSortNo(strings[0]));
                    dept.setFid(strings[1]);
                    deptMapper.updateSortNoByPrimaryKey(dept);
                }
            }
        } else if ("module".equals(type)) {
            if (StringUtil.isNotEmpty(resultList)) {
                String[] sortNos = resultList.split(",");
                for (String s : sortNos) {
                    String[] strings = s.split(":");
                    Module module = new Module();
                    module.setSortNo(formatSortNo(strings[0]));
                    module.setFid(strings[1]);
                    moduleMapper.updateSortNoByPrimaryKey(module);
                }
            }
        }


        return responseJson;
    }


    private String formatSortNo(String sortNo) {
        int zeroNum = 3 - sortNo.length();
        if (zeroNum > 0)
            for (int i = 0; i < zeroNum; i++) {
                sortNo = "0" + sortNo;
            }
        return sortNo;
    }

}

