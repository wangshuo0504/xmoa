package com.zkxy.xmoa.common;



import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zkxy.xmoa.system.model.DictData;
import com.zkxy.xmoa.system.model.DictType;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * @author 王硕
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public interface IDictDataService {

	/**
	 * 根据TYPE查询字典数据
	 * <li>创建人：王硕</li>
	 * <li>创建时间：2015年9月2日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param type
	 * @return
	 */
	List<DictData> findDictByType(String type);
	/**
	 * 查询所有字典数据
	 * <li>创建人：王硕</li>
	 * <li>创建时间：2015年9月2日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @return
	 */
	List<DictData> findAllDict();

	List<DictType> findAllDictType();

	DictData selectDictDataById(String fid);


	ResponseJson addDictData(DictData dictData);

	ResponseJson deleteDictData(String fids);

	ResponseJson  updateDictDataById(DictData dictData);

	PageList<Map<String,Object>> selectDictDataByParam(Map<String,Object> params, int pageNum, int pageSize, String orderStr);

	boolean  checkDictDataNameUnique(DictData dictData);

	boolean  checkDictDataCodeUnique(DictData dictData);



}

