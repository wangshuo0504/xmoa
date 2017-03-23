package com.zkxy.xmoa.common.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by escore on 2016/6/6.
 * @author zhiyi
 * @company 源本信息
 * 重点车辆短信发送
 */
public class SmsSend {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static Map<String ,String> Send(String url,String userId,String userPwd,String mobileNum,String msgContent,String subCode)
    {
        Map<String ,String> mapResult=new HashMap<String ,String>();
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        NameValuePair user_id = new NameValuePair("user_id", userId);
        NameValuePair user_pwd = new NameValuePair("user_pwd", userPwd);
        NameValuePair mobile = new NameValuePair("mobile", mobileNum);
        NameValuePair msg_content = new NameValuePair("msg_content", msgContent);
        NameValuePair sub_code = new NameValuePair("sub_code",subCode);
        NameValuePair[] data ={user_id, user_pwd, mobile, msg_content,sub_code};
        postMethod.setRequestBody(data);
        int statusCode = 0;
        try
        {
            statusCode = httpClient.executeMethod(postMethod);
            String result = postMethod.getResponseBodyAsString();
            mapResult.put("code",String.valueOf(statusCode));
            mapResult.put("msg",String.valueOf(result));
            //根据返回状态可判断是否发送成功
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return mapResult;
    }

}
