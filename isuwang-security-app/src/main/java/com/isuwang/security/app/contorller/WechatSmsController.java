package com.isuwang.security.app.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信询价宝公众号服务
 */
@RestController
public class WechatSmsController {
    private Logger logger = LoggerFactory.getLogger(getClass());

//    /**
//     * 生产验证码
//     * @param request
//     * @param response
//     * @param type
//     * @throws Exception
//     */
//    @GetMapping("/wechat/{type}")
//    public void ImageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
//    }
}
