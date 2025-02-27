package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.util.RandomCodeUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("code")
public class RandomController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 生成图片验证码（返回图片流）
    @RequestMapping("/image")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = RandomCodeUtil.generateCode(4); // 4位验证码
        String sessionId = request.getSession().getId();

        // 存储到 Redis（5分钟有效期）
        redisTemplate.opsForValue().set(sessionId + ":captcha", code, 5, TimeUnit.MINUTES);

        // 生成图片并返回
        BufferedImage image = RandomCodeUtil.generateImage(code);
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }

    // 校验验证码（供前端调用）
    @RequestMapping("/verify")
    public ResponseMessage verifyCaptcha(@RequestParam String code, HttpServletRequest request) {
        logger.info("verify captcha code:{}", code);
        ResponseMessage message = new ResponseMessage();
        String sessionId = request.getSession().getId();
        String storedCode = redisTemplate.opsForValue().get(sessionId + ":captcha");
        boolean isValid = storedCode != null && storedCode.equalsIgnoreCase(code);
        if (isValid) {
            message.setCode("200");
            message.setMsg("true");
        }else {
            message.setCode("201");
            message.setMsg("false");
        }
        return message;
    }
}
