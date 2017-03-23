/**
 * Created on 2015年7月9日 by Jack
 */
package com.zkxy.xmoa.common.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author Caiming
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class HttpResponseBody implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5406928126690333502L;
	
	private String code;
	
	private String msg;
	
	private Map<String,Object> data;
	
	public HttpResponseBody(){
	}
	
	public HttpResponseBody(String code,String msg,Map<String,Object> data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public HttpResponseBody(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	

}

