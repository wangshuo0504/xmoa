package com.zkxy.xmoa.common;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.common.dao.DictDataMapper;
import com.zkxy.xmoa.common.dao.DictTypeMapper;
import com.zkxy.xmoa.system.model.DictData;
import com.zkxy.xmoa.system.model.DictType;
import com.zkxy.xmoa.util.StringUtil;
import com.zkxy.xmoa.util.UUIDGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class DictDataServiceImpl implements IDictDataService {
	
	@Autowired
	private DictDataMapper dictDataMapper;
	
	@Autowired
	private DictTypeMapper dictTypeMapper;

	@Override
	public List<DictData> findDictByType(String type) {
		return dictDataMapper.queryDictDataByType(type);
	}

	@Override
	public List<DictData> findAllDict() {
		return dictDataMapper.queryAllDictData();
	}
	
	@Override
	public List<DictType> findAllDictType(){
		return dictTypeMapper.queryAllDictType();
	}


	@Override
	public DictData selectDictDataById(String fid) {
		return dictDataMapper.selectByPrimaryKey(fid);
	}


	@Override
	public ResponseJson addDictData(DictData dictData) {
		ResponseJson responseJson = new ResponseJson();
		if (!checkDictDataNameUnique(dictData)) {
			responseJson.setCode("0");
			responseJson.setMsg("基础代码名已存在!");
			return responseJson;
		}
		if (!checkDictDataCodeUnique(dictData)) {
			responseJson.setCode("0");
			responseJson.setMsg("基础代码编码已存在!");
			return responseJson;
		}
		dictData.setFid(UUIDGenerator.getUUID());
		dictDataMapper.insert(dictData);
		responseJson.setCode("1");
		return responseJson;
	}

	@Override
	public ResponseJson deleteDictData(String fids) {
		ResponseJson responseJson = new ResponseJson();
		if (StringUtil.isEmpty(fids)) {
			responseJson.setCode("0");
			responseJson.setMsg("基础代码id为空!");
			return responseJson;
		}

		String[] fidList = fids.split(",");

		for (String fid : fidList) {
			dictDataMapper.deleteByPrimaryKey(fid);
		}
		responseJson.setCode("1");
		return responseJson;
	}

	@Override
	public ResponseJson updateDictDataById(DictData dictData) {
		ResponseJson responseJson = new ResponseJson();
		if (!checkDictDataNameUnique(dictData)) {
			responseJson.setCode("0");
			responseJson.setMsg("基础代码名已存在!");
			return responseJson;
		}
		if (!checkDictDataCodeUnique(dictData)) {
			responseJson.setCode("0");
			responseJson.setMsg("基础代码编码已存在!");
			return responseJson;
		}
		dictDataMapper.updateByPrimaryKeySelective(dictData);
		responseJson.setCode("1");
		return responseJson;
	}

	@Override
	public PageList<Map<String, Object>> selectDictDataByParam(Map<String, Object> params, int pageNum, int pageSize, String orderStr) {
		PageBounds pageBounds = new PageBounds(pageNum, pageSize, Order.formString(orderStr));

		PageList<Map<String, Object>> dictDatas	 = (PageList<Map<String, Object>>) dictDataMapper.selectDictDataByParams(params, pageBounds);
		return dictDatas;
	}

	@Override
	public boolean checkDictDataNameUnique(DictData dictData) {
		if (dictDataMapper.selectCountByName(dictData) > 0)
			return false;
		else
			return true;
	}


	@Override
	public boolean checkDictDataCodeUnique(DictData dictData) {
		if (dictDataMapper.selectCountByCode(dictData) > 0)
			return false;
		else
			return true;
	}








}

