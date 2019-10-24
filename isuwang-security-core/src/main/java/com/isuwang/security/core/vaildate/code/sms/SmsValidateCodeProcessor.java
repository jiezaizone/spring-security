package com.isuwang.security.core.vaildate.code.sms;

import com.github.dapeng.core.SoaException;
import com.isuwang.security.core.properties.SecurityConstants;
import com.isuwang.security.core.vaildate.code.ValidateCode;
import com.isuwang.security.core.vaildate.code.AbstractValidateCodeProcessor;
import com.isuwang.security.core.vaildate.code.ValidateCodeException;
import com.isuwang.soa.crmdb.company.domain.wechatcustomer.LoginResponse;
import com.isuwang.soa.crmdb.company.domain.wechatcustomer.WechatLoginByVerifyCodeRequest;
import com.isuwang.soa.crmdb.wechatcustomer.WechatCustomerBizServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 短信验证码创建实现
 */
@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsCodeSender smsCodeSender;

    // 重写短信校验码校验方法 （改用dapeng接口调用）
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }

    // 重写短信验证码校验方法 （改用dapeng接口调用）
    @Override
    public void validate(ServletWebRequest request) {
        String mobile = "";
        String smsCode = "";
        String openId = "";
        int source = 0;
        try {
            mobile = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        } catch (ServletRequestBindingException e) {
            logger.error(e.getMessage(),e);
            throw new ValidateCodeException("获取手机号码的值失败");
        }
        try {
            smsCode = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
        } catch (ServletRequestBindingException e) {
            logger.error(e.getMessage(),e);
            throw new ValidateCodeException("获取验证码的值失败");
        }
        try {
            openId = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID);
        } catch (ServletRequestBindingException e) {
            logger.error(e.getMessage(),e);
        }
        try {
            source = ServletRequestUtils.getIntParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_SOURCE);
        } catch (ServletRequestBindingException e) {
            logger.error(e.getMessage(),e);
            throw new ValidateCodeException("获取来源的值失败");
        }

        try {
            WechatLoginByVerifyCodeRequest verifyCodeRequest = new WechatLoginByVerifyCodeRequest();
            verifyCodeRequest.phone(mobile);
            verifyCodeRequest.verifyCode(smsCode);
            verifyCodeRequest.wechatOpenid(openId);
            verifyCodeRequest.source(source);
            LoginResponse loginResponse = new WechatCustomerBizServiceClient().verifyCodeLogin(verifyCodeRequest);
        } catch (SoaException e) {
            logger.error(e.getMessage(),e);
            throw new ValidateCodeException(e.getMsg());
        }
        // 短信验证码不校验，默认通过
    }
}
