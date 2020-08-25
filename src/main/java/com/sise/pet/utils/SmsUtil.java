package com.sise.pet.utils;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sise.pet.core.CommonResult;
import com.sise.pet.core.ResultCode;
import com.sise.pet.exception.RedisConnectException;
import com.sise.pet.service.RedisService;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Random;

public class SmsUtil {

    public static CommonResult sendSms(String PhoneNumber, String captchaType){
        RedisService redisService = (RedisService)SpringContextUtil.getBean(RedisService.class);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIgdc2tQIF9Klw", "dDFbTl6vt09FG4ylLsrZO5sNhGlwsE");
        IAcsClient client = new DefaultAcsClient(profile);
        String verifyCode = getVerifyCode();
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", PhoneNumber);
        request.putQueryParameter("SignName", "宠物狗知识分享平台");
        request.putQueryParameter("TemplateCode", "SMS_115390177");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + verifyCode + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map json = (Map) JSONObject.parse(data);
            String state = (String) json.get("Code");
            if(StringUtils.equals("isv.BUSINESS_LIMIT_CONTROL",state)){
                return new CommonResult().setCode(ResultCode.FAILURE.getCode()).setMessage("每小时至多发5条短信，每天至多发10条，当前超出上限");
            }
            //发送成功才存入redis
            redisService.set(captchaType + PhoneNumber, verifyCode, new Long(5 * 60 * 1000));
            return new CommonResult().setCode(ResultCode.SUCCESS.getCode());
        } catch (ServerException e) {
            e.printStackTrace();
            return new CommonResult().setCode(ResultCode.FAILURE.getCode()).setMessage("短信发送错误");
        } catch (ClientException e) {
            e.printStackTrace();
            return new CommonResult().setCode(ResultCode.FAILURE.getCode()).setMessage("短信发送错误");
        } catch (RedisConnectException e) {
            e.printStackTrace();
            return new CommonResult().setCode(ResultCode.FAILURE.getCode()).setMessage("服务器内部错误");
        }
    }

    public static String getVerifyCode(){
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }
}
