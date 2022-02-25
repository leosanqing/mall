/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.yami.shop.security.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.yami.shop.common.util.RedisUtil;
import com.yami.shop.security.constants.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author lgh
 */
@Controller
@AllArgsConstructor
@ApiIgnore
public class SysLoginController {

    private final CacheManager cacheManager;


    @GetMapping("/captcha.jpg")
    public void login(HttpServletResponse response, String uuid) {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha simpleCaptcha = CaptchaUtil.createLineCaptcha(200, 50, 4, 20);
        try {
            simpleCaptcha.write(response.getOutputStream());
            RedisUtil.set(SecurityConstants.SPRING_SECURITY_RESTFUL_IMAGE_CODE + uuid, simpleCaptcha.getCode(), 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出
     */
    @PostMapping(value = "/sys/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

}
