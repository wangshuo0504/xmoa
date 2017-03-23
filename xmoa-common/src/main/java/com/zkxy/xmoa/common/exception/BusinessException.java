/**
 * Created on 2015年6月24日 by Jack
 */
package com.zkxy.xmoa.common.exception;
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
public class BusinessException extends RuntimeException  {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2963387503320613145L;

	private String code;
	
	public static final String resCodeSplitFlat = "~";
	
	public BusinessException(){
		super();
	}
	
	public BusinessException(String message) {
		super(resCodeSplitFlat + message);
	}

	public BusinessException(String code, String message) {
		super(code + resCodeSplitFlat + message);
		this.code = code;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String code, String message, Throwable cause) {
	    super(code + resCodeSplitFlat + message , cause);
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}

