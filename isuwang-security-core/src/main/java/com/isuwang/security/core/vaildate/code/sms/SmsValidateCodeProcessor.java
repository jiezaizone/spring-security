package com.isuwang.security.core.vaildate.code.sms;

import com.isuwang.security.core.properties.SecurityConstants;
import com.isuwang.security.core.vaildate.code.ValidateCode;
import com.isuwang.security.core.vaildate.code.AbstractValidateCodeProcessor;
import com.isuwang.security.core.vaildate.code.ValidateCodeException;
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

    // 重写短信校验码校验方法
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }

    // 重写短信验证码校验方法
    @Override
    public void validate(ServletWebRequest request) {
        // TODO 短信校验借口默认只校验 mobile、smsCode 参数，如需要其它参数校验，可自行添加
        // String source = "0"; //请求来源
        String mobile = "";
        String smsCode = "";
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

        // TODO 自定义短信验证码校验逻辑。注：若校验短信验证码不校验，默认通过
        // 短信验证码校验逻辑
    }
}
