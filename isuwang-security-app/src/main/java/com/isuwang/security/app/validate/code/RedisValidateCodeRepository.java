package com.isuwang.security.app.validate.code;

import com.isuwang.security.core.vaildate.code.ValidateCode;
import com.isuwang.security.core.vaildate.code.ValidateCodeRepository;
import com.isuwang.security.core.vaildate.code.enums.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 基于redis的验证码存取器
 *
 * @author zhailiang
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    /**
     * 验证码放入redis时的前缀
     */
    String REDIS_KEY_PREFIX = "REDIS_KEY_FOR_CODE_";
    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return REDIS_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        /**
         * 只取code和过期时间存到session, 因为存入redis的内容需要序列化
         */
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request, validateCodeType));
    }
}
