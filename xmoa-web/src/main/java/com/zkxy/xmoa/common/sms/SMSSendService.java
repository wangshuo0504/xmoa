package com.zkxy.xmoa.common.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.util.HashMap;
import java.util.Map;

public class SMSSendService {
	private static final String  SERVICEURL = "http://10.142.46.128:9080/sms/SMSSendService";
	private String user_id;//登陆帐号id	由系统管理员开通
	private String user_pwd;//登陆帐号密码	由系统管理员设定
	private String sub_code;//发送子号	如0101，如果为空，则从该账号的多个子号中随机选取一个
	private static Map<String, String> result_cod;//返回参数
	// private String mobile;
	// private String msg_content;

	static {
		result_cod=new HashMap<String, String>();
		result_cod.put("100", "余额不足");
		result_cod.put("101", "账号关闭");
		result_cod.put("102", "短信内容超过195字或为空或内容编码格式不正确");
		result_cod.put("103", "手机号码超过50个或合法的手机号码为空");
		result_cod.put("104", "用户访问时间间隔低于50毫秒");
		result_cod.put("105", "用户访问方式不是post方式");
		result_cod.put("106", "用户名不存在");
		result_cod.put("107", "密码错误");
		result_cod.put("108", "指定访问ip错误");
		result_cod.put("110", "小号不合法");
		result_cod.put("111", "短信内容内有敏感词");
		result_cod.put("-100", "其他未知错误");
	}

	/**
	 * 
	 * @param user_id  登陆帐号id	由系统管理员开通
	 * @param user_pwd 登陆帐号密码	由系统管理员设定
	 * @param sub_code 发送子号	如0101，如果为空，则从该账号的多个子号中随机选取一个
	 */
	public SMSSendService(String user_id, String user_pwd, String sub_code) {
		super();
		this.user_id = user_id;
		this.user_pwd = user_pwd;
		this.sub_code = sub_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	/**
	 * 
	 * @param mobile 要发送的手机号码	多个号码用英文的逗号隔开，且群发总号码数不超过100个
	 * @param msg_content 短信内容	短信内容长度不超过195个汉字,每个英文或阿拉伯字符也算1个汉字，超过70个字以上的、每条短信65个字；内容里面不要包含&字符
	 * @return
	 */
	public boolean send(String mobile, String msg_content) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(SERVICEURL);
		postMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=GBK");
		NameValuePair nvp_user_id = new NameValuePair("user_id", user_id);
		NameValuePair nvp_user_pwd = new NameValuePair("user_pwd", user_pwd);
		NameValuePair nvp_sub_code = new NameValuePair("sub_code", sub_code);
		NameValuePair nvp_mobile = new NameValuePair("mobile", mobile);
		NameValuePair nvp_msg_content = new NameValuePair("msg_content", msg_content);
		NameValuePair[] data = { nvp_user_id, nvp_user_pwd, nvp_sub_code,
				nvp_mobile, nvp_msg_content };
		postMethod.setRequestBody(data);
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			System.out.println(statusCode);
			String result = postMethod.getResponseBodyAsString();
			// 根据返回状态可判断是否发送成功
			if (result_cod.containsKey(result)) {
				System.out.println(result_cod.get(result));
				return false;
			} else {
				System.out.println(result);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		String mobile="18666666666";
		String msg_content="周先生，你好！";
		SMSSendService smsSendService=new SMSSendService("910", "123456", "9101");
		smsSendService.send(mobile, msg_content);
	}
}
